package unitec.iscg7424.groupassignment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.utlities.Constants;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView menuHome;
    private GroupFragment groupFragment = new GroupFragment();
    private TaskFragment taskFragment = new TaskFragment();
    private ToolsFragment toolsFragment = new ToolsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuHome = findViewById(R.id.menu_home);
        menuHome.setOnItemSelectedListener(this::onMenuItemSelected);
        menuHome.setSelectedItemId(R.id.btn_group);
    }

    private boolean onMenuItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.btn_group) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.homeFragment, groupFragment)
                                       .commit();
            return true;
        }

        if (menuItem.getItemId() == R.id.btn_task) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.homeFragment, taskFragment)
                                       .commit();
            return true;
        }

        if (menuItem.getItemId() == R.id.btn_tools) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.homeFragment, toolsFragment)
                                       .commit();
            return true;
        }

        if (menuItem.getItemId() == R.id.btn_profile) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.homeFragment, profileFragment)
                                       .commit();
            return true;
        }

        return false;
    }
}