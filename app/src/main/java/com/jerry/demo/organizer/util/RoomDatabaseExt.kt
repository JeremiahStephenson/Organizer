package com.jerry.demo.organizer.util

import androidx.annotation.WorkerThread
import androidx.room.RoomDatabase


@WorkerThread
fun RoomDatabase.clear() {
    val db = openHelper.writableDatabase

    var id = 0L
    var identityHash = ""
    db.query("SELECT id, identity_hash FROM room_master_table").use {
        if (it.moveToFirst()) {
            id = it.getLong(0)
            identityHash = it.getString(1)
        }
    }

    db.query("SELECT name FROM sqlite_master WHERE type='table'").use {
        while (it.moveToNext()) {
            db.execSQL("DELETE FROM ${it.getString(0)}")
        }
    }

    db.takeIf { id != 0L && identityHash.isNotEmpty() }?.execSQL("INSERT OR REPLACE INTO room_master_table (id, identity_hash) VALUES(?, ?)", arrayOf(id, identityHash))
}