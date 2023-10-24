package unitec.iscg7424.groupassignment.utlities;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import unitec.iscg7424.groupassignment.models.StudyGroup;
import unitec.iscg7424.groupassignment.models.StudyTask;
import unitec.iscg7424.groupassignment.models.User;

public class Database {
    private static final String USER_LIST = "MGA_Users";
    private static final String GROUP_LIST = "MGA_Groups";
    private static final String TASK_LIST = "MGA_Tasks";

    private final static FirebaseDatabase database = FirebaseDatabase.getInstance("https://iscg7424-mda-default-rtdb.firebaseio.com");

    public static Task<Void> saveUser(User user) {
        DatabaseReference reference = database.getReference(USER_LIST);

        if (user.getId() == null) {
            String id = reference.push().getKey();
            assert id != null;
            user.setId(id);
        }

        return reference.child(user.getId()).setValue(user);
    }

    public static Query getUserByEmail(String email) {
        DatabaseReference reference = database.getReference();
        return reference.child(USER_LIST).orderByChild("email").equalTo(email).limitToFirst(1);
    }

    public static Query allGroups() {
        DatabaseReference reference = database.getReference();
        return reference.child(GROUP_LIST);
    }

    public static Task<Void> saveGroup(StudyGroup group) {
        DatabaseReference reference = database.getReference(GROUP_LIST);
        if (group.getId() == null) {
            String id = reference.push().getKey();
            assert id != null;
            group.setId(id);
        }
        return reference.child(group.getId()).setValue(group);
    }

    public static Task<Void> removeGroup(String groupId) {
        DatabaseReference reference = database.getReference(GROUP_LIST);
        return reference.child(groupId).removeValue();
    }

    public static Task<Void> saveTask(StudyTask task) {
        DatabaseReference reference = database.getReference(TASK_LIST);
        if (task.getId() == null) {
            String id = reference.push().getKey();
            assert id != null;
            task.setId(id);
        }
        return reference.child(task.getId()).setValue(task);
    }
    public static Query allTasks() {
        DatabaseReference reference = database.getReference();
        return reference.child(TASK_LIST);
    }
}
