package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryFormActivity extends AppCompatActivity {

    private EditText etName;
    private AppDatabase db;
    private long categoryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleHelper.setLocale(this, PrefUtils.getLanguage(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_form);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);
        etName = findViewById(R.id.etCategoryName);
        Button btnSave = findViewById(R.id.btnSaveCategory);
        Button btnCancel = findViewById(R.id.btnCancelCategory);

        Intent in = getIntent();
        if (in.hasExtra("categoryId")) {
            categoryId = in.getLongExtra("categoryId", -1);
            Category c = db.categoryDao().findById(categoryId);
            if (c != null) etName.setText(c.getName());
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, R.string.toast_category_name_required, Toast.LENGTH_SHORT).show();
                return;
            }
            if (categoryId == -1) {
                db.categoryDao().insert(new Category(name));
            } else {
                Category c = db.categoryDao().findById(categoryId);
                if (c != null) {
                    c.setName(name);
                    db.categoryDao().update(c);
                }
            }
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
