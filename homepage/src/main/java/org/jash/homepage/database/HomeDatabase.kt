package org.jash.homepage.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import org.jash.homepage.dao.SimpleTypeDao
import org.jash.homepage.dao.VideoDao
import org.jash.homepage.model.SimpleType
import org.jash.homepage.model.VideoModel

@Database(entities = [SimpleType::class, VideoModel::class], version = 2)
abstract class HomeDatabase: RoomDatabase() {
    abstract fun getSimpleTypeDao(): SimpleTypeDao
    abstract fun getVideoDao(): VideoDao
}



