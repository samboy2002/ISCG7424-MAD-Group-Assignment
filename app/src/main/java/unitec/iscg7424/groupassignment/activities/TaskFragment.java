package unitec.iscg7424.groupassignment.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.StudyTask;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;
import unitec.iscg7424.groupassignment.views.TaskCardAdapter;

public class TaskFragment extends Fragment {
    private final TaskCardAdapter taskCardAdapter = new TaskCardAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.view_task_list);
        recyclerView.setAdapter(taskCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        loadTasks();

        return view;
    }

    private void loadTasks() {
        Database.allTasks().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<StudyTask> tasks = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    StudyTask task = item.getValue(StudyTask.class);
                    if (task != null && task.getAcceptedMembers().contains(Constants.loginUser.getId())) {
                        tasks.add(task);
                    }
                }
                taskCardAdapter.refresh(tasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TaskFragment.this.getContext(), error.getMessage(), Toast.LENGTH_SHORT)
                     .show();
            }
        });
    }
}