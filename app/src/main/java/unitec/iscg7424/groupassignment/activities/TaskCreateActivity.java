package unitec.iscg7424.groupassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.StudyGroup;
import unitec.iscg7424.groupassignment.models.StudyTask;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;

public class TaskCreateActivity extends AppCompatActivity {
    public static StudyGroup studyGroup;

    private TextView txtGroupName;
    private EditText txtTaskName;
    private EditText txtDescription;
    private EditText txtStartDate;
    private EditText txtEndDate;
    private Spinner txtPeriod;
    private Spinner txtCheckInMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        txtGroupName = findViewById(R.id.txt_group_name);
        txtTaskName = findViewById(R.id.txt_task_name);
        txtDescription = findViewById(R.id.txt_description);
        txtStartDate = findViewById(R.id.txt_start_date);
        txtEndDate = findViewById(R.id.txt_end_date);
        txtPeriod = findViewById(R.id.txt_task_period);
        txtCheckInMethod = findViewById(R.id.txt_check_in_method);

        txtGroupName.setInputType(InputType.TYPE_NULL);
        txtGroupName.setText(studyGroup.getName());

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
        txtEndDate.setInputType(InputType.TYPE_NULL);
        txtEndDate.setOnClickListener(view -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            DatePickerDialog picker = new DatePickerDialog(this,
                                                           (view1, year1, monthOfYear, dayOfMonth) -> txtEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            picker.show();
        });

        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, StudyTask.Periods);
        txtPeriod.setAdapter(periodAdapter);

        List<String> checkInMethods = new ArrayList<>();
        for (StudyTask.CheckInMethod item : StudyTask.CheckInMethod.values()) {
            checkInMethods.add(item.getDesc());
        }
        ArrayAdapter<String> methodAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, checkInMethods);
        txtCheckInMethod.setAdapter(methodAdapter);

        findViewById(R.id.btn_create_task).setOnClickListener(this::onCreateTask);
        findViewById(R.id.btn_cancel).setOnClickListener(unused -> {
            studyGroup = null;
            finish();
        });
    }

    private void onCreateTask(View view) {
        StudyTask task = new StudyTask();
        task.setGroupId(studyGroup.getId());
        task.setGroupName(studyGroup.getName());
        task.setName(txtTaskName.getText().toString());
        task.setDescription(txtDescription.getText().toString());
        task.setOwner(Constants.loginUser.getId());
        task.setStartDate(txtStartDate.getText().toString());
        task.setEndDate(txtEndDate.getText().toString());
        task.setPeriod(txtPeriod.getSelectedItem().toString());
        task.setCheckInMethod(StudyTask.CheckInMethod.findByDesc(txtCheckInMethod.getSelectedItem().toString()));

        Database.saveTask(task).addOnSuccessListener(unused -> {
            Constants.loginUser.addAcceptTask(task.getId());
            Database.saveUser(Constants.loginUser);
            Toast.makeText(this, "Task created successfully.", Toast.LENGTH_SHORT).show();
            studyGroup = null;
            finish();
        });
    }
}