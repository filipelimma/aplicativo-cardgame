package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private AppDatabase db;
    private ListView lvCategories;
    private CategoryAdapter adapter;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleHelper.setLocale(this, PrefUtils.getLanguage(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);
        lvCategories = findViewById(R.id.lvCategories);

        categories = db.categoryDao().getAll();
        adapter = new CategoryAdapter(this, categories);
        lvCategories.setAdapter(adapter);

        findViewById(R.id.btnAddCategory).setOnClickListener(v -> {
            startActivity(new Intent(this, CategoryFormActivity.class));
        });

        lvCategories.setOnItemClickListener((parent, view, position, id) -> {
            Category c = categories.get(position);
            Intent i = new Intent(this, CategoryFormActivity.class);
            i.putExtra("categoryId", c.getId());
            startActivity(i);
        });

        lvCategories.setOnItemLongClickListener((parent, view, position, id) -> {
            Category c = categories.get(position);
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(R.string.context_delete)
                    .setMessage(getString(R.string.confirm_delete_category, c.getName()))
                    .setPositiveButton(android.R.string.yes, (d, w) -> {
                        db.categoryDao().delete(c);
                        categories.clear();
                        categories.addAll(db.categoryDao().getAll());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, R.string.toast_category_deleted, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        categories.clear();
        categories.addAll(db.categoryDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
