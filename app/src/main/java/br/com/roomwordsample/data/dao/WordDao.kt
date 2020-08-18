package br.com.roomwordsample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.roomwordsample.entity.Word

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 17/08/20
 */

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY name ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Query("DELETE FROM word_table WHERE name =:word")
    suspend fun deleteWord(word: String)


}