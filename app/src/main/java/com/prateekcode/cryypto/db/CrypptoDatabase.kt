package com.prateekcode.cryypto.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], exportSchema = false, version = 1)
abstract class CrypptoDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}