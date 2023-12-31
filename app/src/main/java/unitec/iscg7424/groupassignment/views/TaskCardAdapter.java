package unitec.iscg7424.groupassignment.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.activities.TaskDetailActivity;
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

        if (task.isFinished(Constants.CurrentDate(), Constants.loginUser.getId())) {
            holder.txtTaskStatus.setText("Finished");
            holder.txtTaskStatus.setBackgroundColor(Color.CYAN);
        } else if (task.getAcceptedMembers().contains(Constants.loginUser.getId())) {
            holder.txtTaskStatus.setText("Accepted");
            holder.txtTaskStatus.setBackgroundColor(Color.GREEN);
        } else if (task.getRejectedMembers().contains(Constants.loginUser.getId())) {
            holder.txtTaskStatus.setText("Give Up");
            holder.txtTaskStatus.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.txtTaskStatus.setText("Unaccepted");
            holder.txtTaskStatus.setBackgroundColor(Color.YELLOW);
        }

        holder.itemView.setOnClickListener(view -> {
            TaskDetailActivity.studyTask = task;
            Intent intent = new Intent(view.getContext(), TaskDetailActivity.class);
            view.getContext().startActivity(intent);
        });
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
