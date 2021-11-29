package com.example.greenmarket.database

import androidx.room.*

@Dao
interface TodoDao {
    @Insert
    suspend fun addItem(item : Todo)

    @Update
    suspend fun updateItem(item : Todo)

    @Delete
    suspend fun deleteItem(item : Todo)

    @Query("SELECT * FROM Todo")
    suspend fun loadItems() : List<Todo>

    @Query("SELECT * FROM Todo WHERE id=:todo_id")
    suspend fun loadItem(todo_id: Int) : List<Todo>
}