package com.example.notas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.notas.NoteApplication
import com.example.notas.R
import com.example.notas.databinding.ActivityNoteFormBinding
import com.example.notas.entities.NoteEntity
import java.util.concurrent.LinkedBlockingQueue

class NoteFormActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNoteFormBinding
    private lateinit var selectedColor: NoteEntity.Color
    private lateinit var colores: Array<NoteEntity.Color>
    private lateinit var note: NoteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteFormBinding.inflate( layoutInflater )
        setContentView( binding.root )

        note = intent.getSerializableExtra("NOTE") as NoteEntity

        binding.teNoteTitle.setText( note.title )
        binding.teNoteText.setText( note.text )

        colores = arrayOf( NoteEntity.Color.GREEN, NoteEntity.Color.BLUE, NoteEntity.Color.RED, NoteEntity.Color.YELLOW );
        setupSpinner( colores )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_update ->{
                note.title = binding.teNoteTitle.text.toString().trim()
                note.text = binding.teNoteText.text.toString().trim()
                note.color = selectedColor
                Thread {
                    NoteApplication.database.NoteDAO().updateNote(note)
                }.start()
                showToast("Se ha guardado la nota")
                returnToMain()
                true
            }
            R.id.action_delete ->{
                Thread {
                    NoteApplication.database.NoteDAO().deleteNote(note)
                }.start()
                showToast("Se ha borrado la nota")
                returnToMain()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun returnToMain() {
        val i = Intent( this, MainActivity::class.java )
        startActivity( i )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate( R.menu.menu_main, menu)
        return true
    }

    fun setupSpinner( colores : Array<NoteEntity.Color> ) {
        val spinner = binding.spinnerColor
        val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, colores )

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item )
        spinner.adapter = adapter

        val seleccion = colores.indexOf( note.color )
        spinner.setSelection( seleccion )

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