package com.example.mycor

class InvokeClass {
    // 对于Kotlin 类来说，都可以重写其invoke函数
    operator fun invoke(): String {
        return "override fun invoke"
    }
}

fun main() {
    val invokeClass = InvokeClass()
    val result = invokeClass.invoke()
    println("wmkwmk=== result:${result}")
    println("wmkwmk=== result:${invokeClass()}") // kotlin语法糖，对象可以像函数一样调用
}