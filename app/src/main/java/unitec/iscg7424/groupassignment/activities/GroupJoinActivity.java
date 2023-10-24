package unitec.iscg7424.groupassignment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.StudyGroup;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;
import unitec.iscg7424.groupassignment.views.GroupCardAdapter;

public class GroupJoinActivity extends AppCompatActivity {
    private final GroupCardAdapter groupCardAdapter = new GroupCardAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_join);

        findViewById(R.id.btn_cancel).setOnClickListener(unused->finish());

        RecyclerView recyclerView = findViewById(R.id.view_group_list);
        recyclerView.setAdapter(groupCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadGroup();
    }

    private void loadGroup() {
        Database.allGroups()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<StudyGroup> groups = new ArrayList<>();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            StudyGroup group = item.getValue(StudyGroup.class);
                            if (group != null && !group.getMembers().contains(Constants.loginUser.getId())) {
                                groups.add(group);
                            }
                        }

                        groupCardAdapter.refresh(groups);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(GroupJoinActivity.this, error.getMessage(), Toast.LENGTH_SHORT)
                             .show();
                    }
                });
    }
}