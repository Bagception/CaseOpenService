package de.uniulm.bagception.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class CaseOpenService extends Service implements Runnable{
	public static final String BROADCAST_COMMAND_INTENT = "de.uniulm.bagception.service.CMD";
	
	public static final String BROADCAST_COMMAND_SHUTDOWN = "SHUTDOWN";
	public static final String BROADCAST_COMMAND_START = "START";
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
	public void onDestroy() {
		super.onDestroy();
		keepAlive = false;
	}
	@Override
	public void run() {
		sendBroadcast(BROADCAST_COMMAND_START);
		while(keepAlive){
			SystemClock.sleep(1000);
			Log.d("Service", "now I would check  the light");
		}
		sendBroadcast(BROADCAST_COMMAND_SHUTDOWN);
	}
	
	private void sendBroadcast(String actionExtra){
		Intent intent=new Intent();
		intent.setAction(BROADCAST_COMMAND_INTENT);
		intent.putExtra(BROADCAST_COMMAND_INTENT,actionExtra);
		sendBroadcast(intent);
	}

	
}
