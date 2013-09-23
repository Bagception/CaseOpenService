package de.uniulm.bagception.sensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.IBinder;
import android.util.Log;
import de.philipphock.android.lib.services.SensorService;

public class LightSensorService extends SensorService{

	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Log.d("Sensor", event.values[0]+"");
	}

	@Override
	protected int getSensorType() {
		return Sensor.TYPE_LIGHT;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
