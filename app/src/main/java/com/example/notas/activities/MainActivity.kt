package com.example.notas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notas.NoteApplication
import com.example.notas.adapter.NoteAdapter
import com.example.notas.databinding.ActivityMainBinding
import com.example.notas.entities.NoteEntity
import com.example.notas.onClickListener
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity(), onClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: NoteAdapter
    private lateinit var mGridLayout: GridLayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView(binding.root )

        setupRecyclerView()

        binding.addNote.setOnClickListener {
            val i = Intent( this, NewNoteActivity::class.java )
            startActivity( i )
        }
    }

    private fun getNotes() {
        val queue = LinkedBlockingQueue<MutableList<NoteEntity>>()

        Thread {
            val notes = NoteApplication.database.NoteDAO().getAllNotes()
            queue.add( notes )
        }.start()

        mAdapter.setNotes( queue.take() )
    }

    private fun setupRecyclerView() {
        mAdapter = NoteAdapter( mutableListOf(), this )
        mGridLayout = GridLayoutManager( this, 2 )
        getNotes()
        binding.recyclerView.apply {
            setHasFixedSize( true )
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    override fun onClick(noteEntity: NoteEntity) {
        val intent = Intent( this, NoteFormActivity::class.java )
        intent.putExtra( "NOTE", noteEntity)
        startActivity( intent )
    }

    override fun onTaskDone(noteEntity: NoteEntity) {
        noteEntity.isDone = !noteEntity.isDone
        val queue = LinkedBlockingQueue<NoteEntity>()

        Thread {
            NoteApplication.database.NoteDAO().updateNote(noteEntity)
            queue.add( noteEntity )
        }.start()
        mAdapter.update( queue.take() )
    }
}