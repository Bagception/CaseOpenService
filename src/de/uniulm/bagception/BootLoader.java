package de.uniulm.bagception;

import de.uniulm.bagception.service.CaseOpenService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootLoader extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		 Intent startServiceIntent = new Intent(context, CaseOpenService.class);
	        context.startService(startServiceIntent);		
	}

}
