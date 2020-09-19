package cancel

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking{
    launch {
        val s = testFunction()
        println("Hello World $s")
    }
    println("start coroutine")
}

suspend fun testFunction():String {
    println("test start")
    val s = suspendCoroutine<String> {
        println("suspend ****")
        it.resume("suspend")
        println("suspend ----")
    }
    println("test end")
    return s
}