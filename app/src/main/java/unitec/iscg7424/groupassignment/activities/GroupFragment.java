package unitec.iscg7424.groupassignment.activities;

import android.content.Intent;
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
import unitec.iscg7424.groupassignment.models.StudyGroup;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;
import unitec.iscg7424.groupassignment.views.GroupCardAdapter;

public class GroupFragment extends Fragment {
    private final GroupCardAdapter groupCardAdapter = new GroupCardAdapter();

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        view.findViewById(R.id.btn_create_group).setOnClickListener(this::onCreateGroup);
        view.findViewById(R.id.btn_join_group).setOnClickListener(this::onJoinGroup);

        RecyclerView recyclerView = view.findViewById(R.id.view_group_list);
        recyclerView.setAdapter(groupCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        loadGroup();

        return view;
    }

    private void onCreateGroup(View view) {
        Intent intent = new Intent(this.getContext(), GroupCreateActivity.class);
        startActivity(intent);
    }

    private void onJoinGroup(View view) {
        Intent intent = new Intent(this.getContext(), GroupJoinActivity.class);
        startActivity(intent);
    }

    private void loadGroup() {
        Database.allGroups()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<StudyGroup> groups = new ArrayList<>();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            StudyGroup group = item.getValue(StudyGroup.class);
                            if (group != null && group.getMembers().contains(Constants.loginUser.getId())) {
                                groups.add(group);
                            }
                        }

                        groupCardAdapter.refresh(groups);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(GroupFragment.this.getContext(), error.getMessage(), Toast.LENGTH_SHORT)
                             .show();
                    }
                });
    }
}