package com.example.handlerthreaddemo

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.Button
import kotlin.concurrent.thread

// Handler thread provides us with messageQueue and Looper but not handler

class MainActivity : AppCompatActivity() {

//    private val handlerThread = HandlerThread("HandlerThread")

    // Inherited Example class from handler thread
    private val handlerThread = ExampleHandlerThread()
    private lateinit var threadHandler:Handler
    val exampleRunnable1 = ExampleRunnable1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handlerThread.start()
       // threadHandler = Handler() // This way it is handler fou UI Thread

        // But if we pass looper of our own thread then, handler becomes handler of looper's thread
//        threadHandler = Handler(handlerThread.looper)


        val btnDoWork = findViewById<Button>(R.id.button)
        val btnRemoveMsg = findViewById<Button>(R.id.button2)

//        btnDoWork.setOnClickListener{
//            threadHandler.postDelayed(ExampleRunnable1(),2000)
//            threadHandler.post(ExampleRunnable2())
//        }

        btnDoWork.setOnClickListener{
            handlerThread.getHandler().postDelayed(exampleRunnable1,2000)
            handlerThread.getHandler().post(ExampleRunnable2())
        }


        btnRemoveMsg.setOnClickListener{
            handlerThread.getHandler().removeCallbacks(exampleRunnable1)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quit()
    }

    // We should not  provide runnable as inner classes in activity bcoz it will cause memory leak
    // if runnable is still there in msgQueue and activity gets destroyed(ed on screen orientation
    // change )
    // Instead use static runnable class

    companion object{
        class ExampleRunnable1: Runnable{
            override fun run() {
                for (i in 1..5){
                    Log.d("MainWork","Runnable 1 $i")
                    Thread.sleep(1000)
                }
            }

        }

        class ExampleRunnable2: Runnable{
            override fun run() {
                for (i in 1..5){
                    Log.d("MainWork","Runnable 2 $i")
                    Thread.sleep(1000)
                }
            }

        }
    }
}