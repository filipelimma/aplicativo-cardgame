package br.edu.utfpr.filipenogueira.aplicativocardgame;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryId",
                onDelete = ForeignKey.SET_NULL))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    // 1. TODOS OS CAMPOS ORIGINAIS RESTAURADOS
    private String title;
    private String description;
    private int priority;

    @ColumnInfo(name = "dueDate")
    private LocalDate dueDate;

    @ColumnInfo(name = "categoryId", index = true) // 'index = true' otimiza a busca
    private Long categoryId;

    // 2. CONSTRUTOR COMPLETO RESTAURADO
    public Task(String title, String description, int priority, LocalDate dueDate, Long categoryId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.categoryId = categoryId;
    }

    // 3. TODOS OS GETTERS E SETTERS RESTAURADOS
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
