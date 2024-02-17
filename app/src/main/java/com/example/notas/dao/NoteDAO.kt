package com.example.notas.dao

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notas.entities.NoteEntity

@Dao
interface NoteDAO {

    @Query( "SELECT * FROM notes" )
    fun getAllNotes() : MutableList<NoteEntity>

    //fun insertAll( notesEntityList : List<NoteEntity> )

    @Insert
    fun addNote( noteEntity: NoteEntity )

    @Update
    fun updateNote( noteEntity: NoteEntity )

    @Delete
    fun deleteNote( noteEntity: NoteEntity )
}