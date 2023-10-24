package unitec.iscg7424.groupassignment.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private List<AcceptedTask> acceptedTasks = new ArrayList<>();
    private List<String> giveUpTasks = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid(String password) {
        return this.password.equals(password);
    }

    public List<AcceptedTask> getAcceptedTasks() {
        return acceptedTasks;
    }

    public void setAcceptedTasks(List<AcceptedTask> acceptedTasks) {
        this.acceptedTasks = acceptedTasks;
    }

    public List<String> getGiveUpTasks() {
        return giveUpTasks;
    }

    public void setGiveUpTasks(List<String> giveUpTasks) {
        this.giveUpTasks = giveUpTasks;
    }

    @Exclude
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void addAcceptTask(AcceptedTask acceptedTask) {
        this.acceptedTasks.add(acceptedTask);
    }

    public void addGiveUpTask(String taskId) {
        this.giveUpTasks.add(taskId);
    }
}
