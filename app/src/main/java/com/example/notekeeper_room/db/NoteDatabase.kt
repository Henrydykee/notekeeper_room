package com.example.notekeeper_room.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database (
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun getNoteDoa():NoteDao
    companion object{
        @Volatile private  var  instance : NoteDatabase?=null
        private val Lock = Any()
         operator fun  invoke (context: Context) = instance?: synchronized(Lock){
             instance  ?: buildDatabase(context).also {
                 instance = it
             }
         }
        private  fun buildDatabase(context: Context) = databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "notedatabase"
        ).build()
    }
}