package br.com.roomwordsample.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.roomwordsample.data.dao.WordDao
import br.com.roomwordsample.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 18/08/20
 */

@Database(entities = [Word::class], version = 1, exportSchema = false)
public abstract class WordRoomDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    // Delete all content here.
//                    wordDao.deleteAll()
//
//                    // Add sample words.
//                    var word = Word("Hello")
//                    wordDao.insertWord(word)
//                    word = Word("World!")
//                    wordDao.insertWord(word)
//
//                    // TODO: Add your own words!
//                    word = Word("TODO!")
//                    wordDao.insertWord(word)
                }
            }
        }

    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}