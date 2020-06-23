package com.escapesanctuary.kotless.example

import io.kotless.dsl.ktor.Kotless
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

class Server : Kotless() {
    override fun prepare(app: Application) {
        app.routing {
            get("/") {
                call.respondText { "Hello World!" }
            }

            get("/hoge") {
                call.respondText { "hoge" }
            }
        }
    }
}
