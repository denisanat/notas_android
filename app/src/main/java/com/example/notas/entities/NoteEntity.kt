package com.example.notas.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notas.R
import java.io.Serializable

@Entity( tableName = "notes" )
data class NoteEntity (

    @PrimaryKey( autoGenerate = true)
    var id: Long = 0,

    var title: String,

    var text: String,

    var isDone: Boolean = false,

    var color: Color

) : Serializable {

    enum class Color(
        val primaryColor: String,
        val secondaryColor: String
    ) {
        RED( "#F38B83", "#EFA6A0" ),
        GREEN( "#92F197", "#A2DFA4" ),
        BLUE( "#87C2F1", "#B2D7F4" ),
        YELLOW( "#F1E682", "#EDE5A1" )
    }
}
