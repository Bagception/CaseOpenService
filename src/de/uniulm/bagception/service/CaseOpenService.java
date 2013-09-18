package de.uniulm.bagception.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class CaseOpenService extends Service implements Runnable{
	public static final String BROADCAST_COMMAND_INTENT = "de.uniulm.bagception.broadcast.CMD";
	
	public static final String BROADCAST_COMMAND_SHUTDOWN = "SHUTDOWN";
	public static final String BROADCAST_COMMAND_START = "START";
	
	public static final String BROADCAST_CASE_STATE = "de.uniulm.bagception.broadcast.casestate";
	
	public static final int CASE_OPEN_STATE_CHANGED_TO_OPEN=1;
	public static final int CASE_OPEN_STATE_CHANGED_TO_CLOSED=2;
	private final int CASE_OPEN_STATE_NO_CHANGE=0;
	
	private final int CASE_STATE_OPEN = 1;
	private final int CASE_STATE_CLOSED = 0;
	
	private int last_case_state=-1;
	
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
			int case_state = hasCaseOpenStateChanged(); 
			if (case_state != CASE_OPEN_STATE_NO_CHANGE){
				caseStateChangedBroadcast(case_state);
			}
			
//			if (case_state==CASE_OPEN_STATE_CHANGED_TO_CLOSED){
//				//case now closed TODO send bc
//				Log.d("Service", "CASE CHANGED TO CLOSED");
//			}else if (case_state==CASE_OPEN_STATE_CHANGED_TO_OPEN){
//				//case now opened TODO send bc
//				Log.d("Service", "CASE CHANGED TO OPEN");
//			}
		}
		stopSelf();
		sendBroadcast(BROADCAST_COMMAND_SHUTDOWN);
	}
	
	private void caseStateChangedBroadcast(int caseStateChanged){
		Intent intent=new Intent();
		intent.setAction(BROADCAST_CASE_STATE);
		intent.putExtra(BROADCAST_CASE_STATE,caseStateChanged);
		sendBroadcast(intent);
	}
	
	private void sendBroadcast(String actionExtra){
		Intent intent=new Intent();
		intent.setAction(BROADCAST_COMMAND_INTENT);
		intent.putExtra(BROADCAST_COMMAND_INTENT,actionExtra);
		sendBroadcast(intent);
	}

	private boolean isCaseOpen(){
		return false; //TODO check sensor
	}
	
	private int hasCaseOpenStateChanged(){
		boolean curCaseOpen = isCaseOpen();
		int ret = CASE_OPEN_STATE_NO_CHANGE;
		if (curCaseOpen){
			if (last_case_state != CASE_STATE_OPEN){
				ret =  CASE_OPEN_STATE_CHANGED_TO_OPEN;
			}
		}else{
			if (last_case_state != CASE_STATE_CLOSED){
				ret =  CASE_OPEN_STATE_CHANGED_TO_CLOSED;
			}
		}
		if (curCaseOpen){
			last_case_state = CASE_STATE_OPEN;	
		}else{
			last_case_state = CASE_STATE_CLOSED;
		}
		
		return ret;
	}
	

}
