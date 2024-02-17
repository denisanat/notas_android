package com.example.notas

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notas.database.NoteDatabase

class NoteApplication : Application() {

    companion object {
        lateinit var database: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder( this, NoteDatabase::class.java, "NoteDatabase" ).build()
    }
}