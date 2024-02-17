package com.example.notas

import com.example.notas.entities.NoteEntity

interface onClickListener {
    fun onClick(noteEntity: NoteEntity )
    fun onTaskDone( noteEntity: NoteEntity )
}