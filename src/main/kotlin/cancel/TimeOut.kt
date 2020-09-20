package cancel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
    }
}