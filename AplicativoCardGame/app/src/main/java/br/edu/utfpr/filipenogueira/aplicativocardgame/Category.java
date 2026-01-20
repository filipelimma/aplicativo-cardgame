package br.edu.utfpr.filipenogueira.aplicativocardgame;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    private long id;

    // 1. VOLTANDO A USAR "name" EM VEZ DE "description"
    @ColumnInfo(name = "name")
    private String name;

    // 2. CONSTRUTOR CORRIGIDO
    public Category(String name) {
        this.name = name;
    }

    // 3. GETTERS E SETTERS CORRIGIDOS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
