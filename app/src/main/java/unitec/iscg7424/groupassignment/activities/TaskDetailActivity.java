package unitec.iscg7424.groupassignment.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.StudyTask;
import unitec.iscg7424.groupassignment.models.TaskRecord;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;
import unitec.iscg7424.groupassignment.utlities.ImageUtils;

public class TaskDetailActivity extends AppCompatActivity {
    public static StudyTask studyTask;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if (result.getResultCode() == RESULT_OK) {
                finish();
            }
        });

        ((TextView) findViewById(R.id.txt_group_name)).setText(studyTask.getGroupName());
        ((TextView) findViewById(R.id.txt_task_name)).setText(studyTask.getName());
        ((TextView) findViewById(R.id.txt_description)).setText(studyTask.getDescription());
        ((TextView) findViewById(R.id.txt_task_period)).setText(studyTask.getPeriod());
        ((TextView) findViewById(R.id.txt_date_range)).setText(studyTask.getDateRange());
        ((TextView) findViewById(R.id.txt_check_in_method)).setText(studyTask.getCheckInMethod().getDesc());

        TextView taskStatus = findViewById(R.id.txt_status);
        TextView statusIcon = findViewById(R.id.txt_status_icon);

        Button btnCheckTask = findViewById(R.id.btn_check_task);
        btnCheckTask.setOnClickListener(this::onCheckTask);

        Button btnAcceptTask = findViewById(R.id.btn_accept_task);
        btnAcceptTask.setOnClickListener(this::onAcceptTask);

        Button btnGiveUpTask = findViewById(R.id.btn_give_up);
        btnGiveUpTask.setOnClickListener(this::onGiveUpTask);

        if (studyTask.isFinished(Constants.CurrentDate(), Constants.loginUser.getId())) {
            taskStatus.setText("Finished");
            statusIcon.setText(getText(R.string.icon_checked));
            btnCheckTask.setVisibility(View.GONE);
            btnAcceptTask.setVisibility(View.GONE);
            btnGiveUpTask.setVisibility(View.GONE);
        } else if (studyTask.getRejectedMembers().contains(Constants.loginUser.getId())) {
            taskStatus.setText("Give Up");
            statusIcon.setText(getText(R.string.icon_give_up));
            btnCheckTask.setVisibility(View.GONE);
            btnAcceptTask.setVisibility(View.GONE);
            btnGiveUpTask.setVisibility(View.GONE);
        } else if (studyTask.getAcceptedMembers().contains(Constants.loginUser.getId())) {
            taskStatus.setText("Accepted");
            statusIcon.setText(getText(R.string.icon_checked));
            btnCheckTask.setVisibility(View.VISIBLE);
            btnAcceptTask.setVisibility(View.GONE);
            btnGiveUpTask.setVisibility(View.GONE);
        } else {
            taskStatus.setText("Unaccepted");
            statusIcon.setText(getText(R.string.icon_unchecked));
            btnCheckTask.setVisibility(View.GONE);
            btnAcceptTask.setVisibility(View.VISIBLE);
            btnGiveUpTask.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.btn_cancel).setOnClickListener(view -> {
            studyTask = null;
            finish();
        });
    }

    private void onAcceptTask(View view) {
        studyTask.addAcceptedMember(Constants.loginUser.getId());
        Database.saveTask(studyTask).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Study task accepted successfully", Toast.LENGTH_SHORT)
                 .show();
            studyTask = null;
            finish();
        });
    }

    private void onGiveUpTask(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Do you want to give up this task?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    studyTask.addRejectedMember(Constants.loginUser.getId());
                    Database.saveTask(studyTask).addOnSuccessListener(unused -> {
                        finish();
                    });
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void onCheckTask(View view) {
        switch (studyTask.getCheckInMethod()) {
            case Photo:
                Intent intent = new Intent(this, PhotoActivity.class);
                launcher.launch(intent);
                break;
        }
    }
}