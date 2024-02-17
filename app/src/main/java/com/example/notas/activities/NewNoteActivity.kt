package com.example.notas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.notas.NoteApplication
import com.example.notas.adapter.NoteAdapter
import com.example.notas.databinding.ActivityNewNoteBinding
import com.example.notas.entities.NoteEntity

class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    private lateinit var mAdapter: NoteAdapter
    private lateinit var selectedColor: NoteEntity.Color

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate( layoutInflater )
        setContentView( binding.root )

        val colores = arrayOf( NoteEntity.Color.GREEN, NoteEntity.Color.BLUE, NoteEntity.Color.RED, NoteEntity.Color.YELLOW );
        setupSpinner( colores )

        binding.btnCancel.setOnClickListener {
            val i = Intent( this, NewNoteActivity::class.java )
            startActivity( i )
        }

        binding.btnSave.setOnClickListener {
            val note = NoteEntity(
                title = binding.teNoteTitle.text.toString().trim(),
                text = binding.teNoteText.text.toString().trim(),
                color = selectedColor
            )

            Thread {
                NoteApplication.database.NoteDAO().addNote(note)
            }.start()

            val i = Intent( this, MainActivity::class.java )
            startActivity( i )
        }

    }

    fun setupSpinner( colores : Array<NoteEntity.Color> ) {
        val spinner = binding.spinnerColor
        val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, colores )

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item )
        spinner.adapter = adapter

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                selectedColor = colores[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

                selectedColor = NoteEntity.Color.GREEN
            }
        })
    }
}