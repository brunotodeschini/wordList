package br.com.roomwordsample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.roomwordsample.data.dao.WordDao
import br.com.roomwordsample.data.database.WordRoomDatabase

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 18/08/20
 */
class WordViewModelFactory(
    private val database: WordRoomDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(WordRoomDatabase::class.java).newInstance(database)
    }
}