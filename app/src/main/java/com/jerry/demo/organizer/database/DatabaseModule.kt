package com.jerry.demo.organizer.database

import androidx.room.Room
import com.jerry.demo.organizer.database.MainDatabase
import org.koin.dsl.module.module

val databaseModule = module {
    single { Room.databaseBuilder(get(), MainDatabase::class.java, MainDatabase.DATABASE_NAME).build() }
    single { get<MainDatabase>().categoryDao() }
    single { get<MainDatabase>().itemDao() }
}