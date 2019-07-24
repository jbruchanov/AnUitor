package com.scurab.android.anuitor.nanoplugin

import android.app.Activity
import android.view.View
import com.scurab.android.anuitor.reflect.WindowManager
import com.scurab.android.anuitor.tools.HttpTools
import fi.iki.elonen.NanoHTTPD
import java.io.File
import java.util.HashMap

private const val APPLICATION_IS_NOT_ACTIVE = "Application is not active"

abstract class ActivityPlugin protected constructor(private val windowManager: WindowManager) : BasePlugin() {

    val currentActivity: Activity
        get() = windowManager.currentActivity

    val currentRootView: View
        get() = getCurrentRootView(-1)

    override fun serveFile(uri: String, headers: Map<String, String>, session: NanoHTTPD.IHTTPSession, file: File, mimeType: String): NanoHTTPD.Response {
        val viewRootNames = windowManager.viewRootNames
        return if (viewRootNames == null || viewRootNames.isEmpty()) {
            if (mimeType == HttpTools.MimeType.APP_JSON) {
                OKResponse(HttpTools.MimeType.APP_JSON, "[]")
            } else {
                OKResponse(HttpTools.MimeType.TEXT_PLAIN, APPLICATION_IS_NOT_ACTIVE)
            }
        } else {
            handleRequest(uri, headers, session, file, mimeType)
        }
    }

    abstract fun handleRequest(uri: String, headers: Map<String, String>, session: NanoHTTPD.IHTTPSession, file: File, mimeType: String): NanoHTTPD.Response

    fun getCurrentRootView(index: Int): View {
        return if (index < 0) windowManager.currentRootView else windowManager.getRootView(index)
    }

    protected fun getCurrentRootView(qsValue: HashMap<String, String>): View? {
        var view: View?
        try {
            if (qsValue.containsKey(SCREEN_INDEX)) {
                view = getCurrentRootView(qsValue[SCREEN_INDEX]?.toInt() ?: 0)
            } else {
                view = currentRootView
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            view = null
        }

        return view
    }

    companion object {
        const val POSITION = "position"
        const val SCREEN_INDEX = "screenIndex"
    }
}