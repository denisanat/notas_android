package com.example.notas.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.R
import com.example.notas.databinding.ItemNotaBinding
import com.example.notas.entities.NoteEntity
import com.example.notas.onClickListener

class NoteAdapter (
    private var notes : MutableList<NoteEntity>,
    private var listener: onClickListener
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from( mContext ).inflate( R.layout.item_nota, parent, false )
        return ViewHolder( view );
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes.get( position )

        with( holder ) {
            setListener( note )
            binding.noteTitle.text = note.title
            binding.noteText.text = note.text
            binding.cbDone.isChecked = note.isDone
            binding.noteTitle.setBackgroundColor( Color.parseColor( note.color.primaryColor) )
            binding.cbDone.setBackgroundColor( Color.parseColor( note.color.primaryColor) )
            binding.background.setBackgroundColor( Color.parseColor( note.color.secondaryColor) )
        }
    }

    override fun getItemCount(): Int = notes.size

    fun setNotes(notes: MutableList<NoteEntity>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun update(noteEntity: NoteEntity) {
        val index = notes.indexOf( noteEntity )
        if (index != -1)
            notes.set( index, noteEntity )
        notifyDataSetChanged()
    }

    inner class ViewHolder( view : View) : RecyclerView.ViewHolder( view ) {
        val binding = ItemNotaBinding.bind( view )

        fun setListener(noteEntity : NoteEntity ) {

            binding.root.setOnClickListener {
                listener.onClick( noteEntity )
            }

            binding.cbDone.setOnClickListener {
                listener.onTaskDone( noteEntity )
            }
        }
    }


}