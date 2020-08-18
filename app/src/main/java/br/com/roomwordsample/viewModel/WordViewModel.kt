package br.com.roomwordsample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.roomwordsample.data.dao.WordDao
import br.com.roomwordsample.data.database.WordRoomDatabase
import br.com.roomwordsample.data.repository.WordRepository
import br.com.roomwordsample.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 18/08/20
 */
class WordViewModel(
    private val database: WordRoomDatabase
) : ViewModel() {

    private val repository: WordRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = database.wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    fun  delete(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(word)
    }
}