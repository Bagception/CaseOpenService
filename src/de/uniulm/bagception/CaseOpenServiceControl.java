package de.uniulm.bagception;

import de.philipphock.android.lib.services.ServiceUtil;
import de.uniulm.bagception.service.CaseOpenBroadcastActor;
import de.uniulm.bagception.service.CaseOpenService;
import de.uniulm.bagception.service.CaseOpenServiceBroadcastReactor;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CaseOpenServiceControl extends Activity implements CaseOpenServiceBroadcastReactor{

	private CaseOpenBroadcastActor caseOpenBroadcastActor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case_open_service_control);
		
		caseOpenBroadcastActor = new CaseOpenBroadcastActor(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.case_open_service_control, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ServiceUtil.isServiceRunning(this, CaseOpenService.class)){
			onServiceStarted();
		}else{
			onServiceShutdown();
		}
		caseOpenBroadcastActor.register(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		caseOpenBroadcastActor.unregister(this);
	}
	
	
	private void onServiceShutdown(){
		TextView serviceStatusText = (TextView) findViewById(R.id.serviceStatusText);
		serviceStatusText.setText("offline");
		serviceStatusText.setTextColor(Color.RED);
		
		Button startStopService = (Button) findViewById(R.id.startStopService);
		startStopService.setText("Start Service");
		startStopService.setEnabled(true);
				
	}
	private void onServiceStarted(){
		TextView serviceStatusText = (TextView) findViewById(R.id.serviceStatusText);
		serviceStatusText.setText("online");
		serviceStatusText.setTextColor(Color.GREEN);
		
		Button startStopService = (Button) findViewById(R.id.startStopService);
		startStopService.setText("Stop Service");
		startStopService.setEnabled(true);
	}
	
	private void startService(){
		 Intent serviceIntent = new Intent(this, CaseOpenService.class);
	        this.startService(serviceIntent);		
	}
	private void stopService(){
		 Intent serviceIntent = new Intent(this, CaseOpenService.class);
	        this.stopService(serviceIntent);
	}
	public void onStartStopServiceClicked(View v){
		Button startStopService = (Button) findViewById(R.id.startStopService);
		startStopService.setEnabled(false);
		if (ServiceUtil.isServiceRunning(this, CaseOpenService.class)){
			Log.d("Service", "stop Service click command");
			stopService();
		}else{
			Log.d("Service", "start Service click command");
			startService();
		}
	}
	
	/* 
	 * CaseOpenServiceBroadcastReactor 
	 */


	@Override
	public void serviceShutdown() {
		onServiceShutdown();
		TextView serviceStatusText = (TextView) findViewById(R.id.casestatus);
		serviceStatusText.setText("unknown");
		serviceStatusText.setTextColor(Color.RED);
	}

	@Override
	public void serviceStarted() {
		onServiceStarted();
	}

	@Override
	public void caseOpened() {
		TextView serviceStatusText = (TextView) findViewById(R.id.casestatus);
		serviceStatusText.setText("opened");
		serviceStatusText.setTextColor(Color.BLUE);
		
	}

	@Override
	public void caseClosed() {
		TextView serviceStatusText = (TextView) findViewById(R.id.casestatus);
		serviceStatusText.setText("closed");
		serviceStatusText.setTextColor(Color.GREEN);
		
	}

	
}
