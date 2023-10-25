package unitec.iscg7424.groupassignment.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import unitec.iscg7424.groupassignment.R;

public class TaskCardHolder extends RecyclerView.ViewHolder {
    public TextView txtTaskName;
    public TextView txtTaskStatus;
    public TextView txtDescription;
    public TextView txtTaskPeriod;
    public TextView txtDateRange;

    public TaskCardHolder(@NonNull View itemView) {
        super(itemView);
        txtTaskName = itemView.findViewById(R.id.txt_task_name);
        txtTaskStatus = itemView.findViewById(R.id.txt_status);
        txtDescription = itemView.findViewById(R.id.txt_description);
        txtTaskPeriod = itemView.findViewById(R.id.txt_task_period);
        txtDateRange = itemView.findViewById(R.id.txt_date_range);
    }
}
