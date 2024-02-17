package com.example.notas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notas.dao.NoteDAO
import com.example.notas.entities.NoteEntity

@Database( entities = arrayOf(NoteEntity::class), version = 1 )
abstract class NoteDatabase : RoomDatabase() {

    abstract fun NoteDAO() : NoteDAO
}