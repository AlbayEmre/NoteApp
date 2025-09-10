package com.albayemre.localeroom.Data.Local.Repository

import com.albayemre.localeroom.Data.Local.Dao.NoteDatabaseDao
import com.albayemre.localeroom.Data.Local.Entity.DataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDatabaseDao: NoteDatabaseDao
) {

    fun getAllNotesDatabase() = noteDatabaseDao.getAllNotesFromDatabase()
        // DAO'dan gelen Flow, hangi thread üzerinde çalışsın diye belirtiyoruz
        .flowOn(Dispatchers.IO)
        // Çok hızlı yeni veri gelirse aradaki eski değerleri atar, sadece sonuncuyu işler
        .conflate()

    suspend fun insertNoteToDatabase(dataEntity: DataEntity) {
        return noteDatabaseDao.insertNoteToDatabase(dataEntity)
    }

    suspend fun getNoteFromDatabase(selectedNoteID: String): DataEntity {
        return noteDatabaseDao.getNoteFromDatabase(selectedNoteID)
    }

    suspend fun searchNoteToDatabase(noteSearchText: String): List<DataEntity> {
        return noteDatabaseDao.searchNoteToDatabase(noteSearchText)
    }

    suspend fun updateNoteToDatabase(dataEntity: DataEntity) {
        return noteDatabaseDao.updateNoteToDatabase(dataEntity)
    }

    suspend fun deleteAllNotesFromDatabase() {
        return noteDatabaseDao.deleteAllNotesFromDatabase()
    }

    suspend fun deleteNote(dataEntity: DataEntity) {
        return noteDatabaseDao.deleteNote(dataEntity)
    }
}
