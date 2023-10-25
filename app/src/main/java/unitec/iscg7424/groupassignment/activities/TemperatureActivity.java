package unitec.iscg7424.groupassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import unitec.iscg7424.groupassignment.R;

public class TemperatureActivity extends AppCompatActivity implements SensorEventListener {
    private TextView txtTemperature;
    private SensorManager sensorManager;
    private Sensor temperature;
    private final static String NOT_SUPPORTED_MESSAGE = "Sorry, sensor not available for this device.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        txtTemperature = (TextView) findViewById(R.id.txt_temperature);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);	// requires API level 14.

        if (temperature == null) {
            txtTemperature.setText(NOT_SUPPORTED_MESSAGE);
        }

        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);

        findViewById(R.id.btn_cancel).setOnClickListener(view -> {
            sensorManager.unregisterListener(this);
            finish();
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float ambient_temperature = sensorEvent.values[0];
        txtTemperature.setText(ambient_temperature + getResources().getString(R.string.celsius));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}