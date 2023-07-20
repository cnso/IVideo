package org.jash.ivideo

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.android.arouter.utils.ClassUtils
import org.jash.homepage.database.HomeDatabase

class App : Application() {
    lateinit var homeDatabase: HomeDatabase
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
//            val set =
//                ClassUtils.getFileNameByPackageName(this, "com.alibaba.android.arouter.routes")
//            println(set)
        }
        homeDatabase = Room.databaseBuilder(this, HomeDatabase::class.java, "home")
            .build()
    }
}