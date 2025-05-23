package com.example.fake_server

import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Timeout
import java.io.IOException

class FakeCall(private val fakeServer: FakeServer, private val request: Request) : Call {

    init {
        println("FakeCall created for request: ${request.url}")
    }

    override fun execute(): Response {
        println("FakeCall executing for request: ${request.url}")
        val responseBody = runBlocking { fakeServer.handleRequest(request) }
        return Response.Builder()
            .request(request)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(responseBody.toResponseBody())
            .build()
    }

    override fun cancel() {}

    override fun clone(): Call = this

    override fun enqueue(responseCallback: Callback) {
        println("FakeCall enqueue called for request: ${request.url}")
        try {
            val response = execute()
            responseCallback.onResponse(this, response)
        } catch (e: IOException) {
            responseCallback.onFailure(this, e)
        }
    }

    override fun isCanceled() = false

    override fun isExecuted() = false

    override fun request(): Request = request

    override fun timeout(): Timeout = Timeout.NONE


}