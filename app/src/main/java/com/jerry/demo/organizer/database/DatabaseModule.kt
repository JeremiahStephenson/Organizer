package com.jerry.demo.organizer.database

import androidx.room.Room
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), MainDatabase::class.java, MainDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<MainDatabase>().categoryDao() }
    single { get<MainDatabase>().itemDao() }
}