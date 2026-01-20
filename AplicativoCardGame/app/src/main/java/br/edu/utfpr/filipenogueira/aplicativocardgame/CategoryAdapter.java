package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    private final LayoutInflater inflater;

    public CategoryAdapter(Context ctx, List<Category> items) {
        super(ctx, 0, items);
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category c = getItem(position);
        if (convertView == null) convertView = inflater.inflate(R.layout.item_category, parent, false);
        TextView tv = convertView.findViewById(R.id.tvCategoryName);
        tv.setText(c.getName());
        return convertView;
    }
}
