package de.uniulm.bagception.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import de.philipphock.android.lib.BroadcastActor;

public class CaseOpenBroadcastActor extends BroadcastActor<CaseOpenServiceBroadcastReactor> {

	public CaseOpenBroadcastActor(CaseOpenServiceBroadcastReactor reactor) {
		super(reactor);
	}

	@Override
	public void register(Context a) {
		IntentFilter filter = new IntentFilter(CaseOpenService.BROADCAST_COMMAND_INTENT);
		filter.addAction(CaseOpenService.BROADCAST_CASE_STATE);
	    a.registerReceiver(this, filter);		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (CaseOpenService.BROADCAST_COMMAND_INTENT.equals(intent.getAction())){
			if (CaseOpenService.BROADCAST_COMMAND_SHUTDOWN.equals(intent.getStringExtra(CaseOpenService.BROADCAST_COMMAND_INTENT))){
				reactor.serviceShutdown();
			}else if(CaseOpenService.BROADCAST_COMMAND_START.equals(intent.getStringExtra(CaseOpenService.BROADCAST_COMMAND_INTENT))){
				reactor.serviceStarted();
			}	
		}else if (CaseOpenService.BROADCAST_CASE_STATE.equals(intent.getAction())){
			if (CaseOpenService.CASE_OPEN_STATE_CHANGED_TO_CLOSED == intent.getIntExtra(CaseOpenService.BROADCAST_CASE_STATE,-1)){
				reactor.caseClosed();
			}else if (CaseOpenService.CASE_OPEN_STATE_CHANGED_TO_OPEN == intent.getIntExtra(CaseOpenService.BROADCAST_CASE_STATE,-1)){
				reactor.caseOpened();
			}
		}
		
	}

}
