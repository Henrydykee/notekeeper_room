package com.example.notekeeper_room.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper_room.R
import com.example.notekeeper_room.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return  NoteViewHolder(
            LayoutInflater.from (parent.context).inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
       return  notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       holder.view.textview_title.text = notes[position].title
        holder.view.textview_body.text= notes[position].note
        holder.view.setOnClickListener{
            val action =HomeFragmentDirections.actionAddnote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    class  NoteViewHolder (val view: View):RecyclerView.ViewHolder(view)
}