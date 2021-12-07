package com.example.handlerthreaddemo

import android.os.Handler
import android.os.HandlerThread

// MOre -ve means higher priority
class ExampleHandlerThread:HandlerThread("ExampleHandlerThread") {

    // We make handler in onRun() method when we need to associate handler to a specific thread
    // between looper.prepare and looper.loop() . But looper.run is already there in super class
    // and there is a method onLooperPrepared() between these 2 so we can override this method
    lateinit var exampleHandler:Handler

    override fun onLooperPrepared() {
        exampleHandler = Handler()
    }

    fun getHandler():Handler{
        return exampleHandler
    }



}