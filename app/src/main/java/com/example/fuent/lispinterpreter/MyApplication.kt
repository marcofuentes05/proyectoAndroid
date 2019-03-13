package com.example.fuent.lispinterpreter

import android.app.Application

    class MyApplication: Application() {
    private lateinit var archivo: String
    private lateinit var user : String

    public fun getUser () : String {
        return user
    }

    public fun setUser(u : String){
        user = u
    }
}