package br.edu.utfpr.filipenogueira.aplicativocardgame;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    // Em TaskDao.java
    @Query("SELECT * FROM tasks ORDER BY title") // CORRIGIDO
    List<Task> getAll();

    @Insert
    long insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    Task findById(long id);
}
