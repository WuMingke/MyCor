package com.example.mycor

import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select
import kotlin.coroutines.ContinuationInterceptor
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val TAG = "wmkwmk"
    private lateinit var btn: Button
    private val viewModel: TestFlowViewModel by viewModels()
    private val viewModel2: TestSharedFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
//            do1()
//            do2()
//            do3()
//            do4()
//            do5()
            do6()
//            do7()
//            do8()

//            do10()

//            do13()
//            do14()
//            do15()
//            do16()
//            do17()
//            do18()

        }

    }

    private fun do19() {
        lifecycleScope.launch {
            val result = select<String> {

            }
        }
    }

    // SharedFlow
    class TestSharedFlowViewModel : ViewModel() {
        private val _state: MutableSharedFlow<Int> = MutableSharedFlow(2)
        val state: SharedFlow<Int> get() = _state
        fun download() {
            for (state in 0..5) {
                viewModelScope.launch(Dispatchers.IO) {
                    delay(state * 1000L)
                    _state.emit(state)
                }
            }

            /************标准做法**************/
            // 1
            // 绑定生命周期
            val job = viewModelScope.launch(Dispatchers.IO) {
                // IO操作
                withContext(Dispatchers.Main) {
                    // UI操作
                }
            }
            // 随时取消
            job.cancel() // 支持取消，实际是阻止进入下一个suspend方法，正在执行的代码块会走完

            // 2
            viewModelScope.launch(Dispatchers.IO) {
                // 并发请求
                val deferred1 = async { /* 网络请求 */ }
                val deferred2 = async { /* 网络请求 */ }
                val result1 = deferred1.await()
                val result2 = deferred2.await()
                // 处理两个请求
            }
            /************标准做法**************/
            /**
             * CoroutineDispatcher 都实现了 ContinuationInterceptor，
             * 通过拦截 resumeWith 实现分发任务到指定线程 or 线程池
             * 最简单的 HandlerDispatcher ，通过 handler 去切换线程
             *
             * 如何做到挂起回调=》续体+状态机（resumeWith，label+1）
             * 父子协程如何联动=》
             * 如何做到减少线程切换、减少闲置线程=》CoroutineScheduler
             * 1 减少线程切换：执行线程<=CPU核心数，由controlState中CpuPermits数量控制
             * 2 减少闲置线程：偷任务，当前线程如果获取不到任务，则会从其它线程中偷任务 （WorkQueue）
             */
        }
    }

    private fun do18() {
        lifecycleScope.launch {
            launch {
                var index = 1
                viewModel2.state.collect {
                    Log.d(TAG, "第${index++}次变化 state : $it   replayCache: ${viewModel.state.replayCache}")
//                    Log.i(TAG, "1::: $it")
                }
            }

//            launch {
//                delay(6000)
//                viewModel2.state.collect {
//                    Log.i(TAG, "2::: $it")
//                }
//            }
        }
        viewModel2.download()
    }

    // StateFlow
    class TestFlowViewModel : ViewModel() {
        private val _state: MutableStateFlow<Int> = MutableStateFlow(0)
        val state: StateFlow<Int> get() = _state
        private val _name: MutableStateFlow<String> = MutableStateFlow("第二个StateFlow")
        val name: StateFlow<String> get() = _name
        fun download() {
            for (state in 0..5) {
                viewModelScope.launch(Dispatchers.IO) {
                    delay(200L * state)
                    _state.value = state
                }
            }
        }
    }

    private fun do17() {
        lifecycleScope.launch {
            try {
                viewModel.state.collect {
                    Log.i(TAG, "do17: state:$it")
                    if (it == 3) {
                        throw NullPointerException("终止第一个StateFlow的数据收集")
                    }
                }
            } catch (e: Exception) {
                Log.i(TAG, "do17: $e")
            }
            viewModel.name.collect {
                Log.i(TAG, "do17: name:$it")
            }
        }
        viewModel.download()
    }

    // Channel
    private fun do16() {
        lifecycleScope.launch {
            val produce = produce<Int> {
                for (i in 1..5) {
                    send(i)
                }
            }
            produce.consumeEach {
                Log.i(TAG, "do16: $it")
            }
            Log.i(TAG, "do16: done")
        }
    }

    private fun do15() {
        lifecycleScope.launch {
//            (1..3).asFlow().transform {
//                emit(it)
//                emit("transform $it")
//            }.collect {
//                Log.i(TAG, "do15: collect $it")
//            }

//            flow<Any?> {
//                emit(1)
//                emit(null)
//            }.mapNotNull {
////                ((it as? Int) ?: 0) * 5
//                it
//            }.map {
//                "map:$it"
//            }.collect() {
//                Log.i(TAG, "do15: collect:$it")
//            }

//            val flow = flowOf("one", "two", "three", null, "four")
//            flow.mapNotNull {
//                it
//            }.collect { value ->
//                Log.i(TAG, "do15: collect:$value")
//            }

//            (1..3).asFlow().filter {
//                it < 2
//            }.collect() {
//                Log.i(TAG, "do15: collect:$it")
//            }

//            (1..3).asFlow().zip(flowOf("one", "two", "three")) { t1, t2 ->
//                "key:$t1,value:$t2"
//            }.collect {
//                Log.i(TAG, "do15: collect:$it")
//            }

//            (1..3).asFlow().take(2).catch {
//                Log.i(TAG, "do15: collect:$it")
//            }.collect {
//                Log.i(TAG, "do15: collect:$it")
//            }

//            flowOf(1, 1, 1, 2, 1, 1).takeWhile {
//                delay(1000)
//                it == 1
//            }.collect() {
//                Log.i(TAG, "do15: collect:$it")
//            }

            val time = measureTimeMillis {
                flow {
                    for (i in 1..3) {
                        delay(100)
                        emit(i)
                    }
                }.buffer().collect { value ->
                    delay(300)
                    Log.d(TAG, "collect :${value}")
                }
            }
            Log.i(TAG, "do15: totalTime:$time")
        }
    }

    private fun do14() {
        lifecycleScope.launch {
            try {
                flow {
                    Log.i(TAG, "do14: emit")
                    emit(1)
                }.onStart {
                    Log.i(TAG, "do14: onStart")
                }.onEach {
                    Log.i(TAG, "do14: onEach:$it")
                    throw NullPointerException("null ----")
                }.catch {
                    Log.i(TAG, "do14: error:${it.toString()}")
                    emit(2)
                }.onCompletion {
                    Log.i(TAG, "do14: onCompletion")
                }.collect {
                    Log.i(TAG, "do14: collect:${it}")
                    throw NullPointerException("null ---- 2")
                }
            } catch (e: Exception) {
                Log.i(TAG, "do14: error:${e.toString()}")
            }

        }
    }


    private fun do13() {
        val job = lifecycleScope.launch {
//            flow {
//                for (i in 1..3) {
//                    delay(1000)
//                    emit(i)
//                }
//            }.collect {
//                Log.i(TAG, "do13: $it")
//            }

//            arrayListOf<Int>().apply {
//                add(1)
//                add(2)
//                add(3)
//            }.asFlow().collect {
//                Log.i(TAG, "do13: $it")
//            }

            flowOf(1, 2, 3).collect {
                Log.i(TAG, "do13: $it")
            }
        }
        job.cancel()
    }


    /****************************************************/

    class MyViewModel : ViewModel() {
        init {
            viewModelScope.launch { }
        }

    }

    private fun do12() {
        // MyViewModel
    }


    private fun do11() {
        lifecycleScope.launch() {

        }
    }

    private fun do10() {
        GlobalScope.async {

        }
        GlobalScope.cancel()
    }

    private suspend fun do9() {

    }

//    private final Object do9(Continuation $completion) {
//        return Unit.INSTANCE;
//    }


    private fun do8() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "${coroutineContext[CoroutineName]} $throwable")
        }
        GlobalScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {
            supervisorScope {
                Log.i(TAG, "--------- 1")
                launch(CoroutineName("scope2")) {
                    Log.i(TAG, "--------- 2")
                    throw NullPointerException("空指针")
                    Log.i(TAG, "--------- 3")
                    val scope3 = launch(CoroutineName("scope3")) {
                        Log.i(TAG, "--------- 4")
                        delay(2000)
                        Log.i(TAG, "--------- 5")
                    }
                    scope3.join()
                }
                val scope4 = launch(CoroutineName("scope4")) {
                    Log.i(TAG, "--------- 6")
                    delay(2000)
                    Log.i(TAG, "--------- 7")
                }
                scope4.join()
                Log.i(TAG, "--------- 8")
            }
        }

    }

    private fun do7() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "do7: exceptionHandler:${coroutineContext[CoroutineName]},$throwable")
        }
        GlobalScope.launch(
            Dispatchers.Main + CoroutineName("scope1") + exceptionHandler
        ) {
            Log.i(TAG, "do7: scope1")
            launch(CoroutineName("scope2") + exceptionHandler) {
                Log.i(TAG, "do7: scope2")
                throw NullPointerException("null exception")
                Log.i(TAG, "do7: scope3")
            }
            val scope3 = launch(CoroutineName("scope3") + exceptionHandler) {
                Log.i(TAG, "do7: scope4")
                delay(2000)
                Log.i(TAG, "do7: scope5")
            }
            scope3.join()
            Log.i(TAG, "do7: scope6")
        }
    }

    private fun do6() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.i(TAG, "do6: 父协程：${coroutineContext}")
            launch(CoroutineName("mingKe")) {
                Log.i(TAG, "do6: 1协程：${coroutineContext}")
            }
            launch(Dispatchers.Unconfined) {
                Log.i(TAG, "do6: 2协程：${coroutineContext}")
            }
        }
    }

    private fun do5() {

        GlobalScope.launch(Dispatchers.Main) {
            val job = launch(/*Dispatchers.IO, */start = CoroutineStart.UNDISPATCHED) {
                Log.i(TAG, "do5: 挂起前：${Thread.currentThread().name}")
                delay(100)
                Log.i(TAG, "do5: 挂起后：${Thread.currentThread().name}")
            }
            Log.i(TAG, "do5: join前：${Thread.currentThread().name}")
            job.join()
            Log.i(TAG, "do5: join后：${Thread.currentThread().name}")

        }
    }

    private fun do4() {
        val defaultJob = GlobalScope.launch {
            Log.i(TAG, "do4: defaultJob,CoroutineStart.DEFAULT")
        }
        defaultJob.cancel()

        val lazyJob = GlobalScope.launch(start = CoroutineStart.LAZY) {
            Log.i(TAG, "do4: lazyJob,CoroutineStart.LAZY")
        }
        lazyJob.start()

        val atomicJob = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
            Log.i(TAG, "do4: atomicJob 挂起前")
            delay(100)
            Log.i(TAG, "do4: atomicJob 挂起后")
        }
        atomicJob.cancel()

        val undispatchedJob = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            Log.i(TAG, "do4: undispatchedJob 挂起前")
            delay(100)
            Log.i(TAG, "do4: undispatchedJob 挂起后")

        }
        undispatchedJob.cancel()
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