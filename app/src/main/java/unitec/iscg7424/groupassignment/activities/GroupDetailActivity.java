package unitec.iscg7424.groupassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.StudyGroup;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;

public class GroupDetailActivity extends AppCompatActivity {
    public static StudyGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        findViewById(R.id.btn_cancel).setOnClickListener(unused -> finish());
        findViewById(R.id.btn_join_group).setOnClickListener(this::onJoinGroup);
        findViewById(R.id.btn_leave_group).setOnClickListener(this::onLeaveGroup);

        ((TextView) findViewById(R.id.txt_group_name)).setText(group.getName());
        ((TextView) findViewById(R.id.txt_description)).setText(group.getDescription());
        ((TextView) findViewById(R.id.txt_member_count)).setText(group.getMembers().size() + " / " + group.getMaxMembers());

        if (group.getMembers().contains(Constants.loginUser.getId())) {
            findViewById(R.id.btn_join_group).setVisibility(View.GONE);
            findViewById(R.id.btn_leave_group).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.btn_join_group).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_leave_group).setVisibility(View.GONE);
        }

        if (group.getOwner().equals(Constants.loginUser.getId())) {
            ((Button) findViewById(R.id.btn_leave_group)).setText("Delete Group");
            findViewById(R.id.btn_create_task).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.btn_leave_group)).setText("Leave Group");
            findViewById(R.id.btn_create_task).setVisibility(View.GONE);
        }
    }

    private void onJoinGroup(View view) {
        group.getMembers().add(Constants.loginUser.getId());
        Database.saveGroup(group);
        group = null;
        finish();
    }

    private void onLeaveGroup(View view) {
        String message = "Do you want to leave this group?";
        if (group.getOwner().equals(Constants.loginUser.getId())) {
            message = "Do you want to delete this group?";
        }
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> {
                    group.getMembers().remove(Constants.loginUser.getId());

                    if (group.getOwner().equals(Constants.loginUser.getId())) {
                        Database.removeGroup(group.getId());
                    } else {
                        Database.saveGroup(group);
                    }
                    group = null;
                    finish();
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}