package com.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mycor.R
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class ThreadActivity : AppCompatActivity() {

    private val pool = ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS, LinkedBlockingQueue())

    private fun sleep(index: Int) {
        val sleepTime = (Math.random() * 10000).toLong()
        Thread.sleep(sleepTime)
        Log.i("wmkwmk== ", "当前线程执行结束 $index")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
//        shutdownTest()
//        taskCountTest()
//        countDownLatchTest()
        staticCountTest()
    }

    private fun shutdownTest() {
        repeat(10) {
            pool.execute {
                sleep(it)
            }
        }
        pool.shutdown()
        while (!pool.isTerminated) {
            Thread.sleep(1000)
            Log.i("wmkwmk== ", "wmkwmk== 还没有停止")
        }
        Log.i("wmkwmk== ", "全部执行完毕")

        /// 操作简单，但是需要关闭线程池，会造成开销，并且不太合理。
    }

    private fun taskCountTest() {
        repeat(10) {
            pool.execute {
                sleep(it)
            }
        }
        while (pool.taskCount != pool.completedTaskCount) {
            Log.i("wmkwmk== ", "任务总数：${pool.taskCount},已完成任务数：${pool.completedTaskCount}")
            Thread.sleep(1000)
            Log.i("wmkwmk== ", "wmkwmk== 还没有停止")
        }
        Log.i("wmkwmk== ", "全部执行完毕")

        // 不会关闭线程池，但是不能增加新的任务
    }

    private fun countDownLatchTest() {
        val taskLatch = CountDownLatch(10)
        repeat(10) {
            pool.execute {
                sleep(it)
                taskLatch.countDown()
                Log.i("wmkwmk== ", "当前执行了 ${taskLatch.count}")
            }
        }
        taskLatch.await()
        Log.i("wmkwmk== ", "全部执行完毕")

        // 不需要对线程池进行操作，但是需要提前知道线程数量，
    }

    private var taskNum = 0
    private fun staticCountTest() {
        val lock = ReentrantLock()
        repeat(10) {
            pool.execute {
                sleep(it)
                lock.lock()
                taskNum++
                lock.unlock()
            }
        }
        while (taskNum < 10) {
            Thread.sleep(1000)
            Log.i("wmkwmk== ", "当前执行了 $taskNum")
        }

        Log.i("wmkwmk== ", "全部执行完毕")

        /// 加锁计数，
    }

}