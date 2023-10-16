package com.example.mycor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_2)
    }


    // 锁
    // 更多的方式 读写锁
    // 弊端
    // 并发集合
    // 不同场景不同方案
    // 无锁化 CAS  AtomicBoolean等

    // 启动优化
    // 后面的人操作拉低了优化怎么办？写一个阈值，报错

    //
    suspend fun testSuspend() {
        withContext(Dispatchers.IO) {
            delay(1000)
            println("finish")
        }

        runBlocking {

        }

        measureTimeMillis {

        }
    }
}