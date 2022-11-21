package android_serialport_api;

import android.util.Log;

import com.example.fecafootdemo.utils.DataUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android_serialport_api.PsamCardStm32API.OnPsamCardListener;

public class PsamCard32555API {
	private byte[] reset = { (byte) 0x05, (byte) 0x00, (byte) 0x01,
			(byte) 0x00, (byte) 0xff };
	private byte[] getRandom = { (byte) 0x05, (byte) 0x00, (byte) 0x08,
			(byte) 0x04, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x84,
			(byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0xff };
	public static final String RESET_OK = "reset_ok";
	public static final String RESET_FAIL = "reset_fail";
	public static final String GET_RANDOM_OK = "get_random_ok";
	public static final String GET_RANDOM_Fail = "get_random_fail";
	private byte[] buffer;
	private OnPsamCardListener listener;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public PsamCard32555API(OnPsamCardListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Reset
	 */
	public void reset() {
		executor.execute(rst);
	}
	
	/**
	 * Get random number
	 */
	public void getRandom() {
		executor.execute(getRandomNumber);
	}
	
	private Runnable rst = new Runnable() {

		@Override
		public void run() {
			buffer = new byte[50];
			SerialPortManager.getInstance().write(reset);
			int length = SerialPortManager.getInstance().read(buffer, 4000, 200);
			Log.d("jokey", "reset--->length==  " + length);
			byte[] getData = new byte[length];
			System.arraycopy(buffer, 0, getData, 0, length);
			if (length > 4) {
				Log.d("jokey", "getData==  " + DataUtils.toHexString(getData));
				listener.callback(RESET_OK, DataUtils.toHexString(getData));
			} else {
				listener.callback(RESET_FAIL, DataUtils.toHexString(getData));
			}
		}
	};

	private Runnable getRandomNumber = new Runnable() {

		@Override
		public void run() {
			buffer = null;
			buffer = new byte[200];
			SerialPortManager.getInstance().write(getRandom);
			int length = SerialPortManager.getInstance().read(buffer, 3000, 300);
			Log.d("jokey", "getRandom--->length==  " + length);
			if (length > 0) {
				byte[] getData = new byte[length];
				System.arraycopy(buffer, 0, getData, 0, length);
				String randomStr = DataUtils.toHexString(getData);
				Log.d("jokey", "getData==  " + randomStr);
				if (randomStr.endsWith("9000")) {
					listener.callback(GET_RANDOM_OK, randomStr);
				} else {
					listener.callback(GET_RANDOM_Fail, randomStr);
				}
			} else {
				listener.callback(GET_RANDOM_Fail, "0");
			}
		}
	};
}