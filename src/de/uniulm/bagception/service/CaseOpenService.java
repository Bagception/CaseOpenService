package de.uniulm.bagception.service;

import de.uniulm.bagception.sensor.LightSensorService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class CaseOpenService extends LightSensorService implements CaseOpenServiceConstants{
	public synchronized int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		sendBroadcast(BROADCAST_COMMAND_START);

		return Service.START_STICKY;
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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

	public boolean isCaseOpen(){
		Log.d("Service","IS CASE OPEN CHECK");
		if (isCaseOpen == null){
			return false;
		}
		return isCaseOpen; 
	}
	

	

	//IPC
	 
	private final CaseOpenServiceRemote.Stub mBinder = new CaseOpenServiceRemote.Stub() {
		
		@Override
		public boolean isCaseOpened() throws RemoteException {
			return CaseOpenService.this.isCaseOpen();
		}
	};

	@Override
	protected void onCaseChangedToOpen() {
		caseStateChangedBroadcast(CASE_OPEN_STATE_CHANGED_TO_OPEN);
		Log.d("Service","CASE OPEN");

	}


	@Override
	protected void onCaseChangedToClosed() {
		caseStateChangedBroadcast(CASE_OPEN_STATE_CHANGED_TO_CLOSED);
		Log.d("Service","CASE CLOSED");
	}


	@Override
	protected void onCaseChanged(boolean isOpen) {
		//nop
	}
}
