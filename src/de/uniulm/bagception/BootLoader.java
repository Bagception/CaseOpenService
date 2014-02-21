package de.uniulm.bagception;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import de.uniulm.bagception.service.CaseOpenService;

public class BootLoader extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		 Intent startServiceIntent = new Intent(context, CaseOpenService.class);
	        context.startService(startServiceIntent);		
	}

}
