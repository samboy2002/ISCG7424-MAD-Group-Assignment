package unitec.iscg7424.groupassignment.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudyTask {
    public static String[] Periods = new String[]{"Daily"};
    private String id;
    private String groupId;
    private String groupName;
    private String name;
    private String description;
    private String period;
    private String startDate;
    private String endDate;
    private String owner;
    private CheckInMethod checkInMethod;

    private List<String> acceptedMembers = new ArrayList<>();
    private List<String> rejectedMembers = new ArrayList<>();

    private List<TaskRecord> records = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public CheckInMethod getCheckInMethod() {
        return checkInMethod;
    }

    public void setCheckInMethod(CheckInMethod checkInMethod) {
        this.checkInMethod = checkInMethod;
    }

    public List<String> getAcceptedMembers() {
        return acceptedMembers;
    }

    public void setAcceptedMembers(List<String> acceptedMembers) {
        this.acceptedMembers = acceptedMembers;
    }

    public List<String> getRejectedMembers() {
        return rejectedMembers;
    }

    public void setRejectedMembers(List<String> rejectedMembers) {
        this.rejectedMembers = rejectedMembers;
    }

    public List<TaskRecord> getRecords() {
        return records;
    }

    public void setRecords(List<TaskRecord> records) {
        this.records = records;
    }

    public void addAcceptedMember(String userId) {
        this.acceptedMembers.add(userId);
        this.rejectedMembers.remove(userId);
    }

    public void addRejectedMember(String userId) {
        this.acceptedMembers.remove(userId);
        this.rejectedMembers.add(userId);
    }

    public void addTaskRecord(TaskRecord record) {
        this.records.add(record);
    }

    @Exclude
    public String getDateRange() {
        return startDate + " ~ " + endDate;
    }

    @Exclude
    public boolean isFinished(String date, String userId) {
        for (TaskRecord record : records) {
            if (record.getDate().equals(date) && record.getUserId().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    public enum CheckInMethod {
//        Location("location", "Check-In at your location"),
        Photo("photo", "Upload your photo"),
//        Voice("voice", "Upload your voice record"),
        ;
        private final String code;
        private final String desc;

        CheckInMethod(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode() {
            return code;
        }
        public String getDesc() {
            return desc;
        }

        public static CheckInMethod findByDesc(String desc) {
            for (CheckInMethod item : values()) {
                if (item.getDesc().equals(desc)) return item;
            }

            return null;
        }
    }
}
