package BroadcastPractice

import kotlinx.coroutines.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking {
    println("start")
    launch(Dispatchers.IO) {
        try {
            sendResponsiveBroadcast()
        } catch (e: TimeoutCancellationException) {
            println("Time out...")
        }
    }
    println("end")
}

val context = BroadcastContext()

suspend fun sendResponsiveBroadcast() {
    val receiver = SuspendBroadcastReceiver()
    context.registerReceiver(receiver)
    try {
        withTimeout(1000) {
            suspendCancellableCoroutine<String> {
                receiver.continuation = it
                context.sendBroadcast()
            }
        }
    } finally {
        context.unregisterReceiver(receiver)
    }
}

class SuspendBroadcastReceiver(): BroadcastReceiver {
    var continuation:Continuation<String>? = null
    override fun onReceive() {
        continuation?.resume("Success")
    }
}

interface BroadcastReceiver {
    fun onReceive()
}

class BroadcastContext() {
    val arrayList = CopyOnWriteArrayList<BroadcastReceiver>()
    fun registerReceiver(broadcastReceiver: BroadcastReceiver) {
        println("register receiver")
        arrayList.add(broadcastReceiver)
    }
    fun unregisterReceiver(broadcastReceiver: BroadcastReceiver) {
        println("unregister receiver")
        arrayList.remove(broadcastReceiver)
    }
    fun sendBroadcast() {
        Thread{
            Thread.sleep(100000)
            arrayList.forEach { it.onReceive() }
        }.start()
    }
}