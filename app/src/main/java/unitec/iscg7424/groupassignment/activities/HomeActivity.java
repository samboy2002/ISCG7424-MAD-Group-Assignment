package unitec.iscg7424.groupassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.utlities.Constants;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        ((TextView) findViewById(R.id.txt_username)).setText(Constants.loginUser.getFullName());
    }
}