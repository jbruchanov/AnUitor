package com.scurab.android.anuitor.feature

import com.scurab.android.anuitor.ContentTypes
import com.scurab.android.anuitor.FeaturePlugin
import com.scurab.android.anuitor.json.JsonSerializer
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

class Config(
        private val configs: Map<String, Any>,
        private val json: JsonSerializer
) : FeaturePlugin {
    override fun registerRoute(routing: Routing) {
        routing.get("/config") {
            call.respondText(json.toJson(configs), ContentTypes.json)
        }
    }
}