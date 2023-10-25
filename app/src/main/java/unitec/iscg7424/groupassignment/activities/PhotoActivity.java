package unitec.iscg7424.groupassignment.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Objects;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.TaskRecord;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;
import unitec.iscg7424.groupassignment.utlities.ImageUtils;

public class PhotoActivity extends AppCompatActivity {
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    private ActivityResultLauncher<Intent> launcher;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ((TextView) findViewById(R.id.txt_group_name)).setText(TaskDetailActivity.studyTask.getGroupName());
        ((TextView) findViewById(R.id.txt_task_name)).setText(TaskDetailActivity.studyTask.getName());

        TextView txtPhoto = findViewById(R.id.txt_photo);
        ImageView imgPhoto = findViewById(R.id.img_photo);
        imgPhoto.setVisibility(View.GONE);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                txtPhoto.setVisibility(View.GONE);
                imgPhoto.setVisibility(View.VISIBLE);

                image = (Bitmap) Objects.requireNonNull(result.getData().getExtras()).get("data");
//                image = ImageUtils.decode(ImageUtils.encode(image));
                imgPhoto.setImageBitmap(image);
            }
        });

        findViewById(R.id.btn_check_task).setOnClickListener(this::onCheckInTask);
        findViewById(R.id.txt_photo).setOnClickListener(this::onTakePhoto);
        findViewById(R.id.btn_cancel).setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void onTakePhoto(View view) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            launcher.launch(cameraIntent);
        }
    }

    private void onCheckInTask(View view) {
        if (image == null) return;

        TaskRecord record = new TaskRecord();
        record.setTaskId(TaskDetailActivity.studyTask.getId());
        record.setUserId(Constants.loginUser.getId());
        record.setDate(Constants.CurrentDate());
        record.setContent(ImageUtils.encode(image));

        TaskDetailActivity.studyTask.addTaskRecord(record);

        Database.saveTask(TaskDetailActivity.studyTask);
        setResult(RESULT_OK);
        finish();
    }
}