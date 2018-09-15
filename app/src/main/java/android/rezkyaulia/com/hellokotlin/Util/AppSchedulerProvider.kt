package android.rezkyaulia.com.hellokotlin.Util

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers.io
import io.reactivex.schedulers.Schedulers.computation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Rezky Aulia Pratama on 15/9/18.
 */
class AppSchedulerProvider : SchedulerProvider {
    override fun special(): Scheduler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

}