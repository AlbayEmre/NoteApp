package com.albayemre.localeroom.Data.Local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.albayemre.localeroom.Data.Local.Entity.DataEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDatabaseDao {

    @Query("SELECT * FROM notes_table")
    fun getAllNotesFromDatabase(): Flow<List<DataEntity>>//Flow zaten asenkron bir stream döner.


    @Insert(onConflict = OnConflictStrategy.REPLACE)//Çkışma varsa üsütne yenini yaz
    suspend fun insertNoteToDatabase(newNote:DataEntity)



    @Query("SELECT * FROM notes_table WHERE note_ID = :selectedNoteID")
    suspend fun getNoteFromDatabase(selectedNoteID: String): DataEntity




    //başlıkta veya alt başlıkta aranan metin varsa sonuç döner.
    @Query("SELECT * FROM notes_table WHERE note_title LIKE '%' || :noteSearchText || '%' OR note_subtitle LIKE '%' || :noteSearchText || '%' ")
            suspend fun  searchNoteToDatabase(noteSearchText: String):List<DataEntity>
     //||  → SQL’de string birleştirme



    @Update
    suspend fun updateNoteToDatabase(newNote: DataEntity)


    @Query("DELETE FROM notes_table")//hepsini sil
    suspend fun  deleteAllNotesFromDatabase()


    @Delete
    suspend fun deleteNote(note: DataEntity)


}






/*











deleteAllNotesFromDatabase

deleteNote


*/
