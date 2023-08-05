package org.jash.ivideo

import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import org.jash.common.BaseApplication
import org.jash.common.logDebug
import org.jash.homepage.database.HomeDatabase
import org.jash.live.xmpp.XMPPManager
import org.jash.network.gson
import org.jash.network.model.User
import org.jash.network.user
import kotlin.concurrent.thread

class App : BaseApplication() {
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
        thread {
            XMPPManager.init()
            user?.let {
                logDebug(user)
                XMPPManager.register(it.username, it.password)
                val login = XMPPManager.login(it.username, it.password)
                logDebug("登录 : $login" )
//                val muc = XMPPManager.createChatRoom("${it.username}room", it.username)
//                logDebug("有一个聊天室" + XMPPManager.getCharRooms())
//                XMPPManager.destroyCharRoom(muc)
//                logDebug("没有聊天室" + XMPPManager.getCharRooms())


            }
        }
    }
}