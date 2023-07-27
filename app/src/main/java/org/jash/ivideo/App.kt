package org.jash.ivideo

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.android.arouter.utils.ClassUtils
import org.jash.homepage.database.HomeDatabase
import org.jash.network.gson
import org.jash.network.model.User
import org.jash.network.user

class App : Application() {
    lateinit var homeDatabase: HomeDatabase
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
//            val set =
//                ClassUtils.getFileNameByPackageName(this, "com.alibaba.android.arouter.routes")
//            println(set)
        }
        ARouter.init(this)
        homeDatabase = Room.databaseBuilder(this, HomeDatabase::class.java, "home")
            .build()
        val preferences = getSharedPreferences("login", MODE_PRIVATE)
//        preferences.edit().remove("user").apply()
        preferences.getString("user", null)?.let {
           user = gson.fromJson(it, User::class.java)
        }
    }
}