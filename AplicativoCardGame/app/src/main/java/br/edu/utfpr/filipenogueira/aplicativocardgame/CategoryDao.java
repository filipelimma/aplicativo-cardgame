package br.edu.utfpr.filipenogueira.aplicativocardgame;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    // Em CategoryDao.java
    @Query("SELECT * FROM categories ORDER BY name COLLATE NOCASE") // CORRIGIDO
    List<Category> getAll();


    @Insert
    long insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    Category findById(long id);
}
