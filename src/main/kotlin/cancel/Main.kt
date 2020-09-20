package cancel

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking{
    launch {
        val s = try {
        testFunction()
    } catch (e: SampleException) {
            println("Ahoya..")
    }
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
    val ss = suspendCoroutine<String> {
        println("ss suspend ****")
        it.resumeWithException(SampleException())
        println("ss suspend ----")
    }
    println("test end")
    return s
}

class SampleException():Exception() {
    override val message: String?
        get() = "Sample Exception"
}