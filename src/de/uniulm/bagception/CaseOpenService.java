package de.uniulm.bagception;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class CaseOpenService extends Service implements Runnable{
	private boolean keepAlive=true;

	private Thread serviceThread = null;
	
	@Override
	public synchronized int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Service","start cmd recv");

		if (serviceThread == null){
			serviceThread = new Thread(this);
			serviceThread.start();
		}else{
			Log.d("Service","already started");
		}
		return Service.START_STICKY;
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void run() {
		while(keepAlive){
			SystemClock.sleep(1000);
			Log.d("Service", "now I would check  the light");
		}
	}

}
