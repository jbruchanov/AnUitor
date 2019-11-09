package com.scurab.android.anuitor.nanoplugin

import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.scurab.android.anuitor.Constants
import com.scurab.android.anuitor.extract2.DetailExtractor
import com.scurab.android.anuitor.extract2.ExtractingContext
import com.scurab.android.anuitor.extract2.getActivity
import com.scurab.android.anuitor.reflect.ActivityThreadReflector
import com.scurab.android.anuitor.reflect.WindowManager
import com.scurab.android.anuitor.tools.HttpTools.MimeType.*
import com.scurab.android.anuitor.tools.atLeastApi
import fi.iki.elonen.NanoHTTPD
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*

private const val FILE_SIMPLE = "screencomponents.json"
private typealias Node = SimpleViewNode

class ScreenComponentsPlugin(private val windowManager: WindowManager) : BasePlugin() {

    private val activityThread = ActivityThreadReflector()
    private val files = arrayOf(FILE_SIMPLE)
    private val path = "/$FILE_SIMPLE"

    override fun files(): Array<String> = files
    override fun mimeType(): String = APP_JSON
    override fun canServeUri(uri: String, rootDir: File): Boolean = path == uri

    override fun serveFile(uri: String, headers: Map<String, String>,
                           session: NanoHTTPD.IHTTPSession,
                           file: File,
                           mimeType: String): NanoHTTPD.Response {

        return try {
            val simpleStructure = simpleStructure(activityThread.application)
            return OKResponse("text/plain", JSON.toJson(simpleStructure))
        } catch (e: Throwable) {
            Response(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                    NanoHTTPD.MIME_PLAINTEXT,
                    ByteArrayInputStream((e.message ?: "Null exception message").toByteArray()))
        }
    }

    private fun simpleStructure(item: Any): Node {
        val items = mutableListOf<Node>()
        val name = "${item.javaClass.name}@0x${Integer.toHexString(item.hashCode())}"
        when (item) {
            is View -> {
                //nothing, just keep name of view
            }
            is Application -> {
                windowManager.viewRootNames
                        .mapNotNull { Pair(it, windowManager.getRootView(it)) }
                        .forEach { (name, item) ->
                            //naive activity detection => app/activity/view
                            if (name.indexOfFirst { it == '/' } != name.indexOfLast { it == '/' }) {
                                //view without activity as a context ?
                                //might be the app or some very unusual case
                                val activity = item.getActivity()
                                if (activity != null) {
                                    items.add(simpleStructure(activity))
                                } else {
                                    items.add(simpleStructure(item))
                                }
                            } else {
                                items.add(simpleStructure(item))
                            }
                        }
            }
            is Activity -> {
                atLeastApi(Build.VERSION_CODES.O) {
                    items.addAll(item.fragmentManager.fragmentsAsNodes())
                }
                if (item is FragmentActivity) {
                    items.addAll(item.supportFragmentManager.fragmentsAsNodes())
                }
            }
            is android.app.Fragment -> {
                atLeastApi(Build.VERSION_CODES.O) {
                    items.addAll(item.childFragmentManager.fragmentsAsNodes())
                }
            }
            is Fragment -> items.addAll(item.childFragmentManager.fragmentsAsNodes())
            else -> throw IllegalArgumentException("Unsupported type:${item.javaClass.name}")
        }
        return SimpleViewNode(name, items)
    }

    private fun FragmentManager.fragmentsAsNodes(): List<Node> {
        return fragments
                .filterNotNull()
                .map { f -> simpleStructure(f) }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun android.app.FragmentManager.fragmentsAsNodes(): List<Node> {
        return fragments
                .filterNotNull()
                .map { f -> simpleStructure(f) }
    }
}

private class SimpleViewNode(val name: String, val children: List<SimpleViewNode>)