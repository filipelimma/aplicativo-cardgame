package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskListener {

    private RecyclerView rvTasks;
    private TaskAdapter adapter;
    private AppDatabase db;
    private List<Task> tasks = new ArrayList<>();

    private static final int REQ_NEW_TASK = 100;
    private static final int REQ_EDIT_TASK = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Aplicar prefs (tema/idioma) antes do super
        SharedPreferences prefs = PrefUtils.getPrefs(this);
        String theme = prefs.getString(getString(R.string.pref_key_theme), "light");
        switch (theme) {
            case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
            case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
            default: AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;
        }
        String lang = prefs.getString(getString(R.string.pref_key_language), "en");
        LocaleHelper.setLocale(this, lang);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        rvTasks = findViewById(R.id.recyclerTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this, tasks, this);
        rvTasks.setAdapter(adapter);
        registerForContextMenu(rvTasks);

        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, TaskFormActivity.class);
            startActivityForResult(i, REQ_NEW_TASK);
        });

        findViewById(R.id.btnOpenCategories).setOnClickListener(v -> {
            startActivity(new Intent(this, CategoryListActivity.class));
        });

        loadTasks();
    }

    private void loadTasks() {
        tasks.clear();
        tasks.addAll(db.taskDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskClick(Task task) {
        Intent i = new Intent(this, TaskFormActivity.class);
        i.putExtra("taskId", task.getId());
        startActivityForResult(i, REQ_EDIT_TASK);
    }

    @Override
    public void onTaskLongClick(Task task, View view) {
        // Abrir menu contextual manualmente: mostramos opções ao manter pressionado.
        // Aqui abrimos o contexto padrão.
        view.showContextMenu();
    }

    // Menu de opções (Settings / About)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_autoria) {
            startActivity(new Intent(this, AutoriaActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Context menu para cada item (delete)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Recupera posição via adapter (convenção: adapter armazena último item com menu aberto)
        if (item.getItemId() == R.id.context_delete) {
            Task t = adapter.getLastContextTask();
            if (t != null) {
                // confirmar exclusão com AlertDialog
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle(R.string.context_delete)
                        .setMessage(R.string.confirm_delete_task)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            db.taskDao().delete(t);
                            loadTasks();
                            Toast.makeText(this, R.string.toast_task_deleted, Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadTasks();
    }
}
