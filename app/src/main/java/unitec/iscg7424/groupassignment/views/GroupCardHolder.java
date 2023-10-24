package unitec.iscg7424.groupassignment.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import unitec.iscg7424.groupassignment.R;

public class GroupCardHolder extends RecyclerView.ViewHolder {
    protected TextView txtGroupName;
    protected TextView txtDescription;
    protected TextView txtMemberCount;
    protected TextView txtStartDate;
    protected TextView txtTag;
    protected TextView txtIdentify;

    public GroupCardHolder(@NonNull View itemView) {
        super(itemView);
        txtGroupName = itemView.findViewById(R.id.txt_group_name);
        txtDescription = itemView.findViewById(R.id.txt_description);
        txtMemberCount = itemView.findViewById(R.id.txt_member_count);
        txtStartDate = itemView.findViewById(R.id.txt_start_date);
        txtTag = itemView.findViewById(R.id.txt_tag);
        txtIdentify = itemView.findViewById(R.id.txt_identify);
    }
}
