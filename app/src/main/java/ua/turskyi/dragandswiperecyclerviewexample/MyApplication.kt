package ua.turskyi.dragandswiperecyclerviewexample

import android.app.Application
import ua.turskyi.dragandswiperecyclerviewexample.utils.Utils

class MyApplication : Application() {
    companion object {
        var instance: MyApplication? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
    }
}