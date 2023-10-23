package unitec.iscg7424.groupassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.models.User;
import unitec.iscg7424.groupassignment.utlities.Constants;
import unitec.iscg7424.groupassignment.utlities.Database;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView txtErrorMessage;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        txtErrorMessage = findViewById(R.id.txt_error_message);

        findViewById(R.id.btn_sign_in).setOnClickListener(this::onLogin);
        findViewById(R.id.btn_sign_up).setOnClickListener(this::onRegister);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if (result.getResultCode() == RESULT_OK) {
                openHomeActivity();
            }
        });
    }

    private void onRegister(View view) {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        launcher.launch(intent);
    }

    private void onLogin(View view) {
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

        Database.getUserByEmail(txtEmail.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = null;
                    for (DataSnapshot item : snapshot.getChildren()) {
                        user = item.getValue(User.class);
                        break;
                    }

                    if (user != null && user.isValid(txtPassword.getText().toString())) {
                        Constants.loginUser = user;
                        openHomeActivity();
                    } else {
                        txtErrorMessage.setText(R.string.err_invalid_account);
                    }
                } else {
                    txtErrorMessage.setText(R.string.err_invalid_account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txtErrorMessage.setText(error.getMessage());
            }
        });
    }

    private void openHomeActivity() {
        if (Constants.loginUser != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}