package de.uniulm.bagception.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import de.uniulm.bagception.sensor.LightSensorService;

public class CaseOpenService extends LightSensorService implements CaseOpenServiceConstants{
	public synchronized int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return Service.START_STICKY;
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	
	private void caseStateChangedBroadcast(int caseStateChanged){
		Intent intent=new Intent();
		intent.setAction(BROADCAST_CASE_STATE);
		intent.putExtra(BROADCAST_CASE_STATE,caseStateChanged);
		sendBroadcast(intent);
	}
	

	public boolean isCaseOpen(){
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

	}


	@Override
	protected void onCaseChangedToClosed() {
		caseStateChangedBroadcast(CASE_OPEN_STATE_CHANGED_TO_CLOSED);
	}


	@Override
	protected void onCaseChanged(boolean isOpen) {
		//nop
	}


	@Override
	protected void handleMessage(Message m) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onFirstInit() {
		// TODO Auto-generated method stub
		
	}
}
