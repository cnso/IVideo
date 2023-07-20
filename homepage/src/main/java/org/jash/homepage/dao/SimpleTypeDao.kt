package org.jash.homepage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.jash.homepage.model.SimpleType

@Dao
interface SimpleTypeDao {
    @Upsert
    fun insert(vararg types:SimpleType)
    @Query("select * from simpletype")
    fun getAll():List<SimpleType>
}