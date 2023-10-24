package unitec.iscg7424.groupassignment.models;

public class StudyTask {
    public static String[] Periods = new String[]{"Daily", "Weekly", "Monthly"};
    private String id;
    private String groupId;
    private String groupName;
    private String name;
    private String description;
    private String period;
    private String startDate;

    private String owner;



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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public enum CheckInMethod {
        Location("location", "Check-In at your location"),
        Photo("photo", "Upload a photo"),
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
    }
}
