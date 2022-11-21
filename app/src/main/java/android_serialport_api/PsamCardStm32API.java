package android_serialport_api;

import android.util.Log;

import com.example.fecafootdemo.utils.DataUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Psam card
 * 
 * @author YJJ 2016.11.29
 */
public class PsamCardStm32API {
	private byte[] getRandom = { (byte) 0xCA, (byte) 0xDF, (byte) 0x05, (byte) 0x35, (byte) 0x00, (byte) 0x09,
			(byte) 0x72, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x84, (byte) 0x00, (byte) 0x00, (byte) 0x08,
			(byte) 0x73, (byte) 0xE3 };
	private byte[] reset = { (byte) 0xCA, (byte) 0xDF, (byte) 0x05, (byte) 0x36, (byte) 0x00, (byte) 0xE3 };
	private byte[] psam0 = { (byte) 0xCA, (byte) 0xDF, (byte) 0x05, (byte) 0x36, (byte) 0x02, (byte) 0xE3 };
	private byte[] psam1 = { (byte) 0xCA, (byte) 0xDF, (byte) 0x05, (byte) 0x36, (byte) 0x03, (byte) 0xE3 };
	private byte[] powerOff = { (byte) 0xCA, (byte) 0xDF, (byte) 0x05, (byte) 0x36, (byte) 0x01, (byte) 0xE3 };
	private OnPsamCardListener listener;
	private byte[] buffer;
	public static final String RESET_OK = "reset_ok";
	public static final String RESET_FAIL = "reset_fail";
	public static final String GET_RANDOM_OK = "get_random_ok";
	public static final String GET_RANDOM_Fail = "get_random_fail";
	public static final String SELECT_PSAM_OK = "select_psam_ok";
	public static final String SELECT_PSAM_FAIL = "select_psam_fail";
	public static final String TEST = "test";
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private static String STM32_VERSION;
	private GetVersion Verapi;

	public PsamCardStm32API(OnPsamCardListener listener) {
		this.listener = listener;
		Verapi = new GetVersion();
		STM32_VERSION = Verapi.getStm32Version();
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

	/**
	 * Select psam 0
	 */
	public void psam0() {
		if (STM32_VERSION.contains("1.3"))
			executor.execute(selectPsam0);
		else
			executor.execute(selectPsam0_12);
	}

	/**
	 * Select psam 1
	 */
	public void psam1() {
		if (STM32_VERSION.contains("1.3"))
			executor.execute(selectPsam1);
		else
			executor.execute(selectPsam1_12);
	}

	/**
	 * Power off
	 */
	public void powerOff() {
		executor.execute(poff);
	}

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
					String newRandom = randomStr.substring(2, randomStr.length() - 4);
					listener.callback(GET_RANDOM_OK, newRandom);
				} else {
					listener.callback(GET_RANDOM_Fail, randomStr);
				}
			} else {
				listener.callback(GET_RANDOM_Fail, "0");
			}
		}
	};
	private Runnable selectPsam0 = new Runnable() {

		@Override
		public void run() {
			buffer = null;
			buffer = new byte[200];
			SerialPortManager.getInstance().write(psam0);
			int length = SerialPortManager.getInstance().read(buffer, 100, 10);
			Log.d("jokey", "psam0--->length==  " + length);
			if (length > 0) {
				byte[] getData = new byte[length];
				System.arraycopy(buffer, 0, getData, 0, length);
				Log.d("jokey", "selectPsam0==  " + DataUtils.toHexString(getData));
				if (getData[0] == 0x4F && getData[1] == 0x4B)
					listener.callback(SELECT_PSAM_OK, "Select Psam0 OK " + DataUtils.toHexString(getData));
				else
					// listener.callback(SELECT_PSAM_FAIL,"Select Psam0 fail
					// "+DataUtils.toHexString(getData));
					psam0();
			} else
				// listener.callback(SELECT_PSAM_FAIL,"Select Psam0 fail
				// "+length);
				psam0();
		}
	};
	private Runnable selectPsam1 = new Runnable() {

		@Override
		public void run() {
			buffer = null;
			buffer = new byte[200];
			SerialPortManager.getInstance().write(psam1);
			int length = SerialPortManager.getInstance().read(buffer, 100, 10);
			Log.d("jokey", "psam1--->length==  " + length);
			if (length > 0) {
				byte[] getData = new byte[length];
				System.arraycopy(buffer, 0, getData, 0, length);
				Log.d("jokey", "selectPsam1==  " + DataUtils.toHexString(getData));
				if (getData[0] == 0x4F && getData[1] == 0x4B)
					listener.callback(SELECT_PSAM_OK, "Select Psam1 OK " + DataUtils.toHexString(getData));
				else
					// listener.callback(SELECT_PSAM_FAIL,"Select Psam1 fail
					// "+DataUtils.toHexString(getData));
					psam1();
			} else
				// listener.callback(SELECT_PSAM_FAIL,"Select Psam0 fail
				// "+length);
				psam1();
		}
	};
	private Runnable selectPsam0_12 = new Runnable() {

		@Override
		public void run() {
			buffer = null;
			buffer = new byte[200];
			SerialPortManager.getInstance().write(psam0);
			int length = SerialPortManager.getInstance().read(buffer, 800, 50);
			Log.d("jokey", "psam0--->length==  " + length);
			listener.callback(SELECT_PSAM_OK, "Select Psam0 OK ");
		}
	};
	private Runnable selectPsam1_12 = new Runnable() {

		@Override
		public void run() {
			buffer = null;
			buffer = new byte[200];
			SerialPortManager.getInstance().write(psam1);
			int length = SerialPortManager.getInstance().read(buffer, 800, 50);
			Log.d("jokey", "psam1--->length==  " + length);
			listener.callback(SELECT_PSAM_OK, "Select Psam1 OK ");
		}
	};
	private Runnable rst = new Runnable() {

		@Override
		public void run() {
			buffer = null;
			buffer = new byte[200];
			SerialPortManager.getInstance().write(reset);
			int length = SerialPortManager.getInstance().read(buffer, 2000, 100);
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
	private Runnable poff = new Runnable() {

		@Override
		public void run() {
			buffer = null;
			buffer = new byte[200];
			SerialPortManager.getInstance().write(powerOff);
			int length = SerialPortManager.getInstance().read(buffer, 4000, 100);
			Log.d("jokey", "powerOff--->length==  " + length);
			if (length > 0) {
				byte[] getData = new byte[length];
				System.arraycopy(buffer, 0, getData, 0, length);
				Log.d("jokey", "getData==  " + DataUtils.toHexString(getData));
			}
		}
	};

	public interface OnPsamCardListener {
		void callback(String str, String data);
	}
}
