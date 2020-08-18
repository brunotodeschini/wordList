package br.com.roomwordsample.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 17/08/20
 */

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val word: String
)