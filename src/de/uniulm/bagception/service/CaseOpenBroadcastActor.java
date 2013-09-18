package de.uniulm.bagception.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import de.philipphock.android.lib.BroadcastActor;

public class CaseOpenBroadcastActor extends BroadcastActor<CaseOpenServiceBroadcastReactor> {

	public CaseOpenBroadcastActor(CaseOpenServiceBroadcastReactor reactor) {
		super(reactor);
	}

	@Override
	public void register(Activity a) {
		IntentFilter filter = new IntentFilter(CaseOpenService.BROADCAST_COMMAND_INTENT);
	    a.registerReceiver(this, filter);		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (CaseOpenService.BROADCAST_COMMAND_SHUTDOWN.equals(intent.getStringExtra(CaseOpenService.BROADCAST_COMMAND_INTENT))){
			reactor.serviceShutdown();
		}else if(CaseOpenService.BROADCAST_COMMAND_START.equals(intent.getStringExtra(CaseOpenService.BROADCAST_COMMAND_INTENT))){
			reactor.serviceStarted();
		}
	}

}
