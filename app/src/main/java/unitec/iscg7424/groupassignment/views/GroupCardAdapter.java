package unitec.iscg7424.groupassignment.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.activities.GroupDetailActivity;
import unitec.iscg7424.groupassignment.models.StudyGroup;
import unitec.iscg7424.groupassignment.utlities.Constants;

public class GroupCardAdapter extends RecyclerView.Adapter<GroupCardHolder> {
    private final List<StudyGroup> groups = new ArrayList<>();

    @NonNull
    @Override
    public GroupCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.study_group_card, parent, false);
        return new GroupCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupCardHolder holder, int position) {
        StudyGroup group = groups.get(position);

        holder.txtGroupName.setText(group.getName());
        holder.txtDescription.setText(group.getDescription());
        holder.txtMemberCount.setText(group.getMembers().size() + " / " + group.getMaxMembers());
        holder.txtStartDate.setText(group.getStartDate());
        holder.txtTag.setText(group.getTag());
        if (group.getOwner().equals(Constants.loginUser.getId())) {
            holder.txtIdentify.setText("Owner");
            holder.txtIdentify.setBackgroundColor(Color.GREEN);
        } else if (group.getMembers().contains(Constants.loginUser.getId())) {
            holder.txtIdentify.setText("Member");
            holder.txtIdentify.setBackgroundColor(Color.YELLOW);
        }

        holder.itemView.setOnClickListener(view -> {
            GroupDetailActivity.group = group;
            Intent intent = new Intent(view.getContext(), GroupDetailActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.groups.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refresh(List<StudyGroup> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
        notifyDataSetChanged();
    }
}
