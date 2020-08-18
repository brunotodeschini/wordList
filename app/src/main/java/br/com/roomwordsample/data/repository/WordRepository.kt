package br.com.roomwordsample.data.repository

import androidx.lifecycle.LiveData
import br.com.roomwordsample.data.dao.WordDao
import br.com.roomwordsample.entity.Word

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 18/08/20
 */

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(
    private val wordDao: WordDao
) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insertWord(word)
    }

    suspend fun delete(word: Word) {
        wordDao.deleteWord(word.word)
    }

}