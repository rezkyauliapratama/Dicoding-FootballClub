package android.rezkyaulia.com.hellokotlin.Util

import io.reactivex.Scheduler

/**
 * Created by Rezky Aulia Pratama on 15/9/18.
 */
interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun special(): Scheduler
    // Other schedulers as required…
}