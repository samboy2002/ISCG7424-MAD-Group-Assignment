package unitec.iscg7424.groupassignment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.User;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;

public class UserRegisterActivity extends AppCompatActivity {
    private TextView txtErrorMessage;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        txtErrorMessage = findViewById(R.id.txt_error_message);
        txtFirstName = findViewById(R.id.txt_first_name);
        txtLastName = findViewById(R.id.txt_last_name);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        txtConfirmPassword = findViewById(R.id.txt_confirm_password);

        findViewById(R.id.btn_register).setOnClickListener(this::onRegister);
        findViewById(R.id.btn_cancel).setOnClickListener(this::onCancel);
    }

    private void onRegister(View view) {
        if (TextUtils.isEmpty(txtFirstName.getText())) {
            txtFirstName.setError(getText(R.string.err_first_name_required));
            txtErrorMessage.setText(R.string.err_first_name_required);
            return;
        }
        if (TextUtils.isEmpty(txtLastName.getText())) {
            txtLastName.setError(getText(R.string.err_last_name_required));
            txtErrorMessage.setText(R.string.err_last_name_required);
            return;
        }
        if (TextUtils.isEmpty(txtEmail.getText())) {
            txtEmail.setError(getText(R.string.err_email_required));
            txtErrorMessage.setText(R.string.err_email_required);
            return;
        }
        if (TextUtils.isEmpty(txtPassword.getText())) {
            txtPassword.setError(getText(R.string.err_password_required));
            txtErrorMessage.setText(R.string.err_password_required);
            return;
        }
        if (!TextUtils.equals(txtPassword.getText(), txtConfirmPassword.getText())) {
            txtConfirmPassword.setError(getText(R.string.err_invalid_confirm_password));
            txtErrorMessage.setText(R.string.err_invalid_confirm_password);
            return;
        }

        Database.getUserByEmail(txtEmail.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            txtErrorMessage.setText(R.string.err_email_exist);
                        } else {
                            createNewUser();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        txtErrorMessage.setText(error.getMessage());
                    }
                });
    }

    private void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void createNewUser() {
        User user = new User();
        user.setFirstName(txtFirstName.getText().toString());
        user.setLastName(txtLastName.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setPassword(txtPassword.getText().toString());
        Database.saveUser(user).addOnSuccessListener(unused -> {
            Constants.loginUser = user;

            Toast.makeText(UserRegisterActivity.this,
                           "Register successfully",
                           Toast.LENGTH_SHORT).show();

            setResult(RESULT_OK);
            finish();
        });
    }
}