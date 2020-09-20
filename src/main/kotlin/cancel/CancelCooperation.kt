package cancel


import kotlinx.coroutines.*
import java.lang.System.currentTimeMillis


fun main() = runBlocking {
    val startTime = currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (currentTimeMillis() >= nextPrintTime) {
                // ここにdelay関数のようなCancellableなsuspend関数を入れると、1300ms後にキャンセルが走る。
                // しかし、Cancellableなsuspend関数が無い場合、isActiveなどでキャンセル状態を見なければ、処理は実行される。
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}
