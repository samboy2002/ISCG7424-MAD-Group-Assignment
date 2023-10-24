package unitec.iscg7424.groupassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.StudyGroup;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;

public class GroupCreateActivity extends AppCompatActivity {
    private EditText txtGroupName;
    private EditText txtMaxMemberCount;
    private EditText txtStartDate;
    private EditText txtTag;
    private EditText txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);

        txtGroupName = findViewById(R.id.txt_group_name);
        txtMaxMemberCount = findViewById(R.id.txt_max_member_count);
        txtStartDate = findViewById(R.id.txt_start_date);
        txtTag = findViewById(R.id.txt_tag);
        txtDescription = findViewById(R.id.txt_description);

        txtStartDate.setInputType(InputType.TYPE_NULL);
        txtStartDate.setOnClickListener(view -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            DatePickerDialog picker = new DatePickerDialog(this,
                                                           (view1, year1, monthOfYear, dayOfMonth) -> txtStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            picker.show();
        });

        findViewById(R.id.btn_save).setOnClickListener(this::onCreateGroup);
        findViewById(R.id.btn_cancel).setOnClickListener(unused -> finish());
    }

    private void onCreateGroup(View view) {
        if (TextUtils.isEmpty(txtGroupName.getText())) {
            txtGroupName.setError("Group name is required");
            return;
        }
        if (TextUtils.isEmpty(txtMaxMemberCount.getText())) {
            txtMaxMemberCount.setError("The count of members is required.");
            return;
        }
        if (TextUtils.isEmpty(txtStartDate.getText())) {
            txtStartDate.setError("Start date is required");
            return;
        }
        if (TextUtils.isEmpty(txtTag.getText())) {
            txtTag.setError("Tag is required");
            return;
        }
        if (TextUtils.isEmpty(txtDescription.getText())) {
            txtDescription.setError("Description is required");
            return;
        }

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName(txtGroupName.getText().toString());
        studyGroup.setMaxMembers(Integer.valueOf(txtMaxMemberCount.getText().toString(), 10));
        studyGroup.setStartDate(txtStartDate.getText().toString());
        studyGroup.setTag(txtTag.getText().toString());
        studyGroup.setDescription(txtDescription.getText().toString());
        studyGroup.setOwner(Constants.loginUser.getId());
        studyGroup.addMember(Constants.loginUser.getId());

        Database.saveGroup(studyGroup).addOnSuccessListener(unused -> {
            Toast.makeText(this, "New group was created successfully.", Toast.LENGTH_SHORT)
                 .show();
            finish();
        });
    }
}