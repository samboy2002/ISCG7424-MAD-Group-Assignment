package unitec.iscg7424.groupassignment.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import unitec.iscg7424.groupassignment.R;

public class GroupFragment extends Fragment {
    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        view.findViewById(R.id.btn_create_group).setOnClickListener(this::onCreateGroup);
        return view;
    }

    private void onCreateGroup(View view) {
        Intent intent = new Intent(this.getContext(), GroupCreateActivity.class);
        startActivity(intent);
    }
}