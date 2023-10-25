package unitec.iscg7424.groupassignment.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import unitec.iscg7424.groupassignment.R;
import unitec.iscg7424.groupassignment.utlities.Constants;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((TextView) view.findViewById(R.id.txt_first_name)).setText(Constants.loginUser.getFirstName());
        ((TextView) view.findViewById(R.id.txt_last_name)).setText(Constants.loginUser.getLastName());
        ((TextView) view.findViewById(R.id.txt_email)).setText(Constants.loginUser.getEmail());

        view.findViewById(R.id.btn_logout).setOnClickListener(view1 -> {
            Constants.loginUser = null;
            requireActivity().finish();
        });
        return view;
    }
}