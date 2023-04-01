package com.example.mycor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val TAG = "wmkwmk"
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
//            do1()
//            do2()
            do3()
        }

    }

    private fun do3() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.i(TAG, "do3  1: ${Thread.currentThread()}")
            val result = withContext(Dispatchers.IO) {
                Log.i(TAG, "do3  2: ${Thread.currentThread()}")
                delay(2000)
                "2"
            }
            btn.text = result
            Log.i(TAG, "do3  3: ${Thread.currentThread()}")
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun do2() {
        GlobalScope.launch(Dispatchers.Main) {
            val launchJob = launch {
                Log.i(TAG, "launch: start")
            }
            Log.i(TAG, "launch: $launchJob")

            val asyncJob = async(Dispatchers.IO) {
                Log.i(TAG, "async: start")
                "1"
            }
            Log.i(TAG, "async.await: ${asyncJob.await()}")
            Log.i(TAG, "async: $asyncJob")

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun do1() {

        val value1 = runBlocking {
            Log.i(TAG, "runBlocking: start")
            7
        }
        Log.i(TAG, "runBlocking: $value1")

        val launch = GlobalScope.launch {
            Log.i(TAG, "launch: start")
        }
        Log.i(TAG, "launch: $launch")

        val async = GlobalScope.async {
            Log.i(TAG, "async: start")
        }
        Log.i(TAG, "async:$async")

    }
}