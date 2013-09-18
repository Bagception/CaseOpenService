package de.uniulm.bagception;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootLoader extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Service","system boot recv");
		 Intent startServiceIntent = new Intent(context, CaseOpenService.class);
	        context.startService(startServiceIntent);		
	}

}
