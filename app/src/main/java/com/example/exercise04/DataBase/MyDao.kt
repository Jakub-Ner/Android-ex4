package com.example.exercise04.DataBase


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun getAllData(): MutableList<DBItem>?

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun getAllData2(): LiveData<List<DBItem>>

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun getAllData3(): Flow<List<DBItem>>


    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DBItem): Long

    @Delete
    fun delete(item: DBItem): Int

    @Query("SELECT * FROM item_table WHERE id = :itemId")
    fun getItemById(itemId: Int): DBItem?

    @Update
    fun update(item: DBItem)
}
