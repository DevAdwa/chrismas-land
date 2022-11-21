package android_serialport_api;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MagStripeCardAPI {
	private byte[] toCalcCrc = { 0x04, 0x00, 0x00, 0x04 };
	private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	private OnMagStripeListener listener;
	public static class Result {
		public static final int SUCCESS = 1;
		public static final int FAILURE = 0;
		public Object resultInfo;
	}
	public MagStripeCardAPI(OnMagStripeListener listener) {
		this.listener = listener;
	}
	private byte[] buffer = new byte[1024];

	/**
	 * 打开模块,上电
	 * power on
	 */
	public void openMagStripeCard() {
		SerialPortManager.getInstance().openSerialPort();
	}

	/**
	 * 关闭模块，下电
	 * power off
	 */
	public void closeMagStripeCard() {
		SerialPortManager.getInstance().closeSerialPort(1);
	}

	/**
	 * 读磁条卡
	 * Read card
	 * @return 返回的数据  magnetic stripe card data
	 */
	public void readCard() {
		buffer = null;
		buffer = new byte[1024];
		singleThreadExecutor.execute(task);
	}
	private Runnable task = new Runnable() {
		
		@Override
		public void run() {
			int length = 0;
			long time = System.currentTimeMillis();
			do{
				SerialPortManager.getInstance().write(toCalcCrc);
				SystemClock.sleep(100);
				length = SerialPortManager.getInstance().read(buffer, 3000, 100);
			}while(length<=1&&(System.currentTimeMillis()-time)<=8000);
			Log.d("jokey", "readCard--->length=  "+length);
			if (length<=1) {
				listener.callback(null);
			}else{
				byte[] recvData = new byte[length];
				System.arraycopy(buffer, 0, recvData, 0, length);
				listener.callback(recvData);
			}
			
		}
	};
	
	public interface OnMagStripeListener{
		void callback(byte[] buffer);
	}
}