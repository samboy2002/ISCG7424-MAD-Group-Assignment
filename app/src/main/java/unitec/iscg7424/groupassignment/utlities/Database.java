package unitec.iscg7424.groupassignment.utlities;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import unitec.iscg7424.groupassignment.models.User;

public class Database {
    private static final String USER_LIST = "MGA_Users";
    private final static FirebaseDatabase database = FirebaseDatabase.getInstance("https://iscg7424-mda-default-rtdb.firebaseio.com");

    public static Task<Void> saveUser(User user) {
        DatabaseReference reference = database.getReference(USER_LIST);

        if (user.getId() == null) {
            String id = reference.push().getKey();
            assert id != null;
            user.setId(id);
        }

        Log.d("Init-User", "Save a user with id=" + user.getId());
        return reference.child(user.getId()).setValue(user);
    }

    public static Query getUserByEmail(String email) {
        DatabaseReference reference = database.getReference();
        return reference.child(USER_LIST).orderByChild("email").equalTo(email).limitToFirst(1);
    }
}
