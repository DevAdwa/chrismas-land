package android_serialport_api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * auto-boot service
 */
public class AutoBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		if("Android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
			context.startService(intent.setClass(context, ScanSerice.class));
//			Intent intentService = new Intent(context, ScanSerice.class);
//			context.startService(intentService);
			Log.d("jokey", "auto wake up");
//		}
	}

}
