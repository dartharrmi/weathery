package com.dartharrmi.weathery.coroutines

/**
 * Utilities for coroutines
 */
object CoroutinesUtils {

    private const val TAG = "CoroutinesUtils"

    private const val DEFAULT_RETRIES = 3
    private const val DEFAULT_INITIAL_DELAY = 100L
    private const val DEFAULT_MAX_DELAY = 1000L
    private const val DEFAULT_FACTOR = 2.0

    /**
     * Handy method for retrying coroutines when an exception happens.
     *
     * @param [times]           The number of times to repeat the function.
     * @param [initialDelay]    The initial delay for repeating the functions.
     * @param [maxDelay]        The maximum delay.
     * @param [factor]          The factor to increase the delay time.
     * @param [block]           The function to be repeated.
     */
    /*suspend fun <T> retryOnException(
        times: Int = DEFAULT_RETRIES,
        initialDelay: Long = DEFAULT_INITIAL_DELAY,
        maxDelay: Long = DEFAULT_MAX_DELAY,
        factor: Double = DEFAULT_FACTOR,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            YpLog.LOGD(TAG, "Attempt number $times of calling $block")
            try {
                return block()
            } catch (e: Exception) {
                YpLog.LOGE(TAG, "An exception occurred when attempted to call the block", e)
            }
            YpLog.LOGD(TAG, "Delaying next call by $currentDelay ms")
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        // The final countdown
        return block()
    }*/
}