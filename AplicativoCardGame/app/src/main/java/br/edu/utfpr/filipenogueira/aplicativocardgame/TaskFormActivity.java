package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskFormActivity extends AppCompatActivity {

    private EditText etTitle, etDescription;
    private NumberPicker npPriority;
    private TextView tvDueDate;
    private Spinner spinnerCategory;
    private AppDatabase db;
    private long editingTaskId = -1;
    private LocalDate selectedDate = null;
    private List<Category> categories;
    private ArrayAdapter<Category> spinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // aplicar idioma antes de criar
        LocaleHelper.setLocale(this, PrefUtils.getLanguage(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        // habilitar Up button
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);

        etTitle = findViewById(R.id.etTaskTitle);
        etDescription = findViewById(R.id.etTaskDesc);
        npPriority = findViewById(R.id.npTaskPriority);
        tvDueDate = findViewById(R.id.tvTaskDueDate);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        npPriority.setMinValue(1);
        npPriority.setMaxValue(5);

        categories = db.categoryDao().getAll();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        findViewById(R.id.btnPickDate).setOnClickListener(v -> showDatePicker());
        findViewById(R.id.btnSaveTask).setOnClickListener(v -> saveTask());
        findViewById(R.id.btnCancelTask).setOnClickListener(v -> finish());

        Intent in = getIntent();
        if (in.hasExtra("taskId")) {
            editingTaskId = in.getLongExtra("taskId", -1);
            if (editingTaskId != -1) {
                Task t = db.taskDao().findById(editingTaskId);
                if (t != null) {
                    etTitle.setText(t.getTitle());
                    etDescription.setText(t.getDescription());
                    npPriority.setValue(t.getPriority());
                    selectedDate = t.getDueDate();
                    updateDueDateText();
                    if (t.getCategoryId() != null) {
                        for (int i = 0; i < categories.size(); i++) {
                            if (categories.get(i).getId() == t.getCategoryId()) {
                                spinnerCategory.setSelection(i);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = selectedDate != null ? selectedDate.getYear() : c.get(Calendar.YEAR);
        int month = selectedDate != null ? selectedDate.getMonthValue() - 1 : c.get(Calendar.MONTH);
        int day = selectedDate != null ? selectedDate.getDayOfMonth() : c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, (view, y, m, d) -> {
            selectedDate = LocalDate.of(y, m + 1, d);
            updateDueDateText();
        }, year, month, day);
        dpd.show();
    }

    private void updateDueDateText() {
        if (selectedDate == null) {
            tvDueDate.setText(getString(R.string.no_date_selected));
        } else {
            tvDueDate.setText(Converters.formatForDisplay(selectedDate, getResources().getConfiguration().getLocales().get(0)));
        }
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        if (title.isEmpty()) {
            Toast.makeText(this, R.string.toast_title_required, Toast.LENGTH_SHORT).show();
            return;
        }
        String desc = etDescription.getText().toString();
        int prio = npPriority.getValue();
        Category cat = (Category) spinnerCategory.getSelectedItem();
        Long categoryId = cat != null ? cat.getId() : null;

        if (editingTaskId == -1) {
            Task t = new Task(title, desc, prio, selectedDate, categoryId);
            db.taskDao().insert(t);
        } else {
            Task t = db.taskDao().findById(editingTaskId);
            if (t != null) {
                t.setTitle(title);
                t.setDescription(desc);
                t.setPriority(prio);
                t.setDueDate(selectedDate);
                t.setCategoryId(categoryId);
                db.taskDao().update(t);
            }
        }
        finish();
    }

    // menu (Salvar / Limpar) - opcional
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_salvar) {
            saveTask();
            return true;
        } else if (item.getItemId() == R.id.menu_limpar) {
            etTitle.setText("");
            etDescription.setText("");
            npPriority.setValue(1);
            selectedDate = null;
            updateDueDateText();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish(); // Up button
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
