package com.example.notekeeper_room.ui


import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.notekeeper_room.R
import com.example.notekeeper_room.db.Note
import com.example.notekeeper_room.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

    var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let{
            note=AddNoteFragmentArgs.fromBundle(it).note
            editText_note.setText(note?.note)
            editText_title.setText(note?.title)
        }
        Save_note.setOnClickListener{view ->
            val noteTitle = editText_title.text.toString().trim()
            val noteBody = editText_note.text.toString().trim()

            if (noteTitle.isEmpty()){
                editText_title.error ="Title Required"
                editText_title.requestFocus()
                return@setOnClickListener
            }
            if (noteBody.isEmpty()){
                editText_note.error="Body is empty"
                editText_note.requestFocus()
                return@setOnClickListener
            }
            launch {

                context?.let {
                    val mnote = Note(noteTitle,noteBody)
                    if (note == null){
                        NoteDatabase(it).getNoteDoa().addNote(mnote)
                        it.toast("Saved")
                    }else{
                        mnote.id = note!!.id
                        NoteDatabase(it).getNoteDoa().updateNote(mnote)
                        it.toast("Updated")
                    }

                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> if (note!=null) deleteNote() else context?.toast("Empty Fields")
        }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply{
            setTitle("Are You Sure ?")
            setMessage("You Cannot undo this operation")
            setPositiveButton("Yes"){ _, _ ->
            launch {
                NoteDatabase(context).getNoteDoa().deleteNote(note!!)
                val action = AddNoteFragmentDirections.actionSaveNote()
                Navigation.findNavController(view!!).navigate(action)
            }
            }
            setNegativeButton("No"){ _, _ ->

            }
                .create().show()
        }
    }
}


