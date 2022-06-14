package com.prateekcode.cryypto.db

import android.content.Context
import androidx.room.Room
import com.prateekcode.cryypto.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CrypptoDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideDao(database: CrypptoDatabase) = database.favoriteDao()

}