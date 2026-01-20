package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    public interface TaskListener {
        void onTaskClick(Task task);
        void onTaskLongClick(Task task, View view);
    }

    private final List<Task> tasks;
    private final LayoutInflater inflater;
    private final TaskListener listener;
    private final AppDatabase db;
    private Task lastContextTask = null;

    public TaskAdapter(Context ctx, List<Task> tasks, TaskListener listener) {
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(ctx);
        this.listener = listener;
        this.db = AppDatabase.getInstance(ctx);
    }

    public Task getLastContextTask() { return lastContextTask; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    private String formatDateForDisplay(java.time.LocalDate d, Locale locale) {
        if (d == null) return "";
        return Converters.formatForDisplay(d, locale);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task t = tasks.get(position);
        holder.tvTitle.setText(t.getTitle());
        holder.tvDesc.setText(t.getDescription());
        String meta = holder.itemView.getContext().getString(R.string.priority_label) + t.getPriority();
        String dateStr = formatDateForDisplay(t.getDueDate(), holder.itemView.getResources().getConfiguration().getLocales().get(0));
        if (!dateStr.isEmpty()) meta += " • " + dateStr;

        // show category name if exists
        if (t.getCategoryId() != null) {
            Category c = db.categoryDao().findById(t.getCategoryId());
            if (c != null) meta += " • " + c.getName();
        }

        holder.tvMeta.setText(meta);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTaskClick(t);
        });

        holder.itemView.setOnLongClickListener(v -> {
            lastContextTask = t;
            if (listener != null) listener.onTaskLongClick(t, v);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvMeta;
        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvDesc = itemView.findViewById(R.id.tvTaskDesc);
            tvMeta = itemView.findViewById(R.id.tvTaskMeta);
        }
    }
}
