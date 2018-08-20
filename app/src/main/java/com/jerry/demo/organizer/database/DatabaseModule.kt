package com.jerry.demo.organizer.database

import android.app.Application
import androidx.room.Room
import com.jerry.demo.organizer.database.MainDatabase
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.database.item.ItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMainDatabase(application: Application): MainDatabase {
        return Room.databaseBuilder(application, MainDatabase::class.java, MainDatabase.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(mainDatabase: MainDatabase): CategoryDao {
        return mainDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(mainDatabase: MainDatabase): ItemDao {
        return mainDatabase.itemDao()
    }
}