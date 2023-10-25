package unitec.iscg7424.groupassignment.activities;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import unitec.iscg7424.groupassignment.R;

public class ToolsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        view.findViewById(R.id.icon_temperature).setOnClickListener(unused -> {
            Intent intent = new Intent(view.getContext(), TemperatureActivity.class);
            view.getContext().startActivity(intent);
        });
        return view;
    }
}