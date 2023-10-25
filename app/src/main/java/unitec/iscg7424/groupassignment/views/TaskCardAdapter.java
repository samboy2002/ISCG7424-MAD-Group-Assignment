package unitec.iscg7424.groupassignment.views;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.StudyTask;
import unitec.iscg7424.groupassignment.utlities.Constants;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardHolder> {
    private List<StudyTask> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.study_task_card, parent, false);
        return new TaskCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskCardHolder holder, int position) {
        StudyTask task = tasks.get(position);

        holder.txtTaskName.setText(task.getName());
        holder.txtDescription.setText(task.getDescription());
        holder.txtDateRange.setText(task.getDateRange());
        holder.txtTaskPeriod.setText(task.getPeriod());

        if (Constants.loginUser.getAcceptedTasks().contains(task.getId())) {
            holder.txtTaskStatus.setText("Accepted");
            holder.txtTaskStatus.setBackgroundColor(Color.GREEN);
        } else if (Constants.loginUser.getGiveUpTasks().contains(task.getId())) {
            holder.txtTaskStatus.setText("Give Up");
            holder.txtTaskStatus.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.txtTaskStatus.setText("Unaccepted");
            holder.txtTaskStatus.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refresh(List<StudyTask> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }
}
