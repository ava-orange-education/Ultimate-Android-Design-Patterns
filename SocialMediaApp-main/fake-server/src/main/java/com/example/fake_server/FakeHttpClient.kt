package com.example.fake_server

import okhttp3.Call

class FakeHttpClient(private val fakeServer: FakeServer) : Call.Factory {
    override fun newCall(request: okhttp3.Request): Call {
        return FakeCall(fakeServer, request)
    }
}