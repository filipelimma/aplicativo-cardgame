package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

// AQUI ESTÁ A CORREÇÃO!
// Importar a classe Task do SEU pacote, e não do 'kotlinx.coroutines'
import br.edu.utfpr.filipenogueira.aplicativocardgame.Task;


@Database(entities = {Category.class, Task.class}, version = 1, exportSchema = false) // Mudei exportSchema para false para simplificar
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "cardgame_app.db";
    private static volatile AppDatabase INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract TaskDao taskDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // Permite consultas na thread principal para simplificar
                            .fallbackToDestructiveMigration() // Adicionado para facilitar upgrades de versão durante o desenvolvimento
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
