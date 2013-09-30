package de.uniulm.bagception.sensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.IBinder;
import android.util.Log;
import de.philipphock.android.lib.services.SensorService;

public abstract class LightSensorService extends SensorService{

	protected Boolean isCaseOpen=null;
	private final int THRESHOLD = 10;
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	protected abstract void onCaseChangedToOpen();
	protected abstract void onCaseChangedToClosed();
	protected abstract void onCaseChanged(boolean isOpen);
	@Override
	public void onSensorChanged(SensorEvent event) {
		Log.d("EVAL",event.values[0]+"");
		int light = (int)event.values[0];
		if (light < THRESHOLD){//close 
			if (isCaseOpen == null || isCaseOpen){
				isCaseOpen = false;
				onCaseChangedToClosed();
				onCaseChanged(isCaseOpen);

			}
		}else{
			if (isCaseOpen == null || !isCaseOpen){
				isCaseOpen = true;
				onCaseChangedToOpen();
				onCaseChanged(isCaseOpen);

			}
		}

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
