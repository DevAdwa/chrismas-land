package android_serialport_api;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.fecafootdemo.utils.DataUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class M1CardAPI {
	private byte[] buffer;
	private final byte[] getUID = { 0x01 };
	private final byte cmd = 0x02;
	private final byte write = (byte) 0xa0;
	private final byte read = 0x30;
	private final byte add = (byte) 0xc1;
	private final byte minus = (byte) 0xc0;
	private final byte transfer_storage = (byte) 0xc2;
	private final byte[] password = { (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff };
	private final byte[] data = {};
	public static final byte TYPE_A = 0x00;
	public static final byte TYPE_B = 0x01;
	private Handler mhHandler;
	private ExecutorService Executor = Executors.newSingleThreadExecutor();
	private OnM1CardListener listener;

	public M1CardAPI(Context context, OnM1CardListener listener) {
		mhHandler = new Handler();
		this.listener = listener;
	}

	/**
	 * 上电
	 */
	public void open() {
		SerialPortManager.getInstance().openSerialPort();
	}

	/**
	 * 不使用时候下电
	 */
	public void close() {
		SerialPortManager.getInstance().closeSerialPort(1);
	}

	/**
	 * 获取卡号
	 */
	public void getCardID() throws Exception {
		Executor.execute(new Runnable() {

			@Override
			public void run() {
				buffer = new byte[1024*10];
				SerialPortManager.getInstance().write(Package(getUID));
				final int length = SerialPortManager.getInstance().read(buffer,
						3000, 100);
				if(length>0){
					mhHandler.post(new Runnable() {
	
						@Override
						public void run() {
							if (length == 4&&buffer[0]==8&&buffer[1]==1&&buffer[3]==4) {
								listener.getCardIDFail();
							} else {
								byte[] data = new byte[length];
								System.arraycopy(buffer, 0, data, 0, length);
								Log.d("jokey", "getCardID: "+ DataUtils.toHexString(data));
								listener.getCardIDSuccess(DataUtils.toHexString(data));
							}
							buffer = null;
						}
					});
				}
			}
		});
	}

	/**
	 * 向某一扇区某一块写数据,不超过16个字节
	 * 
	 * @param hex
	 *            写的数据，16进制
	 * @param cardtype
	 *            卡片类型
	 * @param section
	 *            扇区
	 * @param block
	 *            块号
	 */
	public void writeData(String hex, final byte cardtype, final int section,
                          final int block, final String hexPwd) throws Exception {
		// 长度超过16个字节报错
		if (hex.length() > 32) {
			listener.writeDataFail(OnM1CardListener.WRITE_ERROR_LENGTH);
		} else {// 长度不足16个字节补0
			while (hex.length() < 32) {
				hex = hex + "00";
			}
			final String newhex = hex;
			Executor.execute(new Runnable() {

				@Override
				public void run() {
					buffer = new byte[1024*50];
					byte[] data = DataUtils.hexStringTobyte(newhex);
					SerialPortManager.getInstance().write(
							Package(Command(cmd, cardtype, write,
									(byte) section, (byte) block, (byte) 0,
									DataUtils.hexStringTobyte(hexPwd), getNewData(data))));
					final int length = SerialPortManager.getInstance().read(
							buffer, 3000, 100);
					mhHandler.post(new Runnable() {

						@Override
						public void run() {
							if(length>0){
								if (buffer[length - 1] == 0x00
										&& buffer[length - 2] == (byte)0x90) {
									listener.writeDataSuccess();
								} else {
									listener.writeDataFail(OnM1CardListener.WRITE_ERROR_FAIL);
								}
								buffer = null;
							}else{
								listener.writeDataFail(OnM1CardListener.WRITE_ERROR_FAIL);
							}
						}
					});
				}
			});
		}
	}

	/**
	 * 读某类卡的某一扇区的某一块数据
	 * 
	 * @param cardtype
	 *            卡片类型
	 * @param section
	 *            扇区
	 * @param block
	 *            块号
	 */
	public void readData(final byte cardtype, final int section, final int block,final String hexPwd) throws Exception {
		Executor.execute(new Runnable() {

			@Override
			public void run() {
				buffer = new byte[1024*10];
				SerialPortManager.getInstance().write(
						Package(Command(cmd, cardtype, read, (byte) section,
								(byte) block, (byte) 0, DataUtils.hexStringToByteReverse(hexPwd), data)));
				final int length = SerialPortManager.getInstance().read(buffer,
						3000, 100);
				mhHandler.post(new Runnable() {

					@Override
					public void run() {
						if (length > 4 && buffer[0] == 8 && buffer[1] == 0) {
							byte[] data = new byte[length - 3];
							System.arraycopy(buffer, 3, data, 0, length - 3);
							listener.readDataSuccess(data);
						} else {
							listener.readDataFail();
						}
						buffer = null;
					}
				});
			}
		});
	}

	/**
	 * 加一
	 * 
	 * @param cardtype
	 *            卡片类型
	 * @param section
	 *            扇区号
	 * @param block
	 *            块号
	 */
	public void add(byte cardtype, int section, int block) {
		generalCmd(add, cardtype, section, block);
	}

	/**
	 * 减一
	 * @param cardtype 卡片类型
	 * @param section 扇区号
	 * @param block 块号
	 */
	public void subtract(byte cardtype, int section, int block) {
		generalCmd(minus, cardtype, section, block);
	}

	/**
	 * 转存
	 * @param cardtype 卡片类型
	 * @param section 扇区号
	 * @param block 块号
	 */
	public void transferStorage(byte cardtype, int section, int block) {
		generalCmd(transfer_storage, cardtype, section, block);
	}

	/**
	 * 加一减一转存
	 * 
	 * @param cmdtype
	 *            操作类型
	 * @param cardtype
	 *            卡片类型
	 * @param section
	 *            扇区号
	 * @param block
	 *            块号
	 */
	private void generalCmd(final byte cmdtype, final byte cardtype,
			final int section, final int block) {
		Executor.execute(new Runnable() {

			@Override
			public void run() {
				buffer = new byte[100];
				SerialPortManager.getInstance().write(
						Package(Command(cmd, cardtype, cmdtype, (byte) section,
								(byte) block, (byte) 0, password, data)));
				final int length = SerialPortManager.getInstance().read(buffer,
						3000, 100);
				mhHandler.post(new Runnable() {

					@Override
					public void run() {
						if (length > 3 && buffer[0] == 8 && buffer[1] == 0) {
							listener.generalSuccess();
						} else {
							listener.generalFail();
						}
						buffer = null;
					}
				});
			}
		});
	}

	/**
	 * 封装操作指令
	 * 
	 * @param cmd
	 *            0x01:寻卡;0x02：操作卡
	 * @param cardtype
	 *            0x00:S50;0x01:S70
	 * @param opcmd
	 *            0x30:读块;0xa0:写块;0xc1:加一;0xc0:减一;0xc2:转存
	 * @param section
	 *            扇区号
	 * @param block1
	 * @param block2
	 * @param password
	 *            6个字节密码
	 * @param data
	 *            数据
	 * @return 封装后的操作指令
	 */
	private byte[] Command(byte cmd, byte cardtype, byte opcmd, byte section,
			byte block1, byte block2, byte[] password, byte[] data) {
		int len = data.length;
		byte[] command = new byte[6 + 6 + len];
		command[0] = cmd;
		command[1] = cardtype;
		command[2] = opcmd;
		command[3] = section;
		command[4] = block1;
		command[5] = block2;
		System.arraycopy(password, 0, command, 6, 6);
		if (len > 0) {
			System.arraycopy(data, 0, command, 12, len);
		}
		return command;
	}

	/**
	 * 封装指令
	 * 
	 * @param data
	 *            原始指令
	 * @return 发送的指令
	 */
	private byte[] Package(byte[] data) {
		int len = data.length;
		byte[] cmd = new byte[1 + 2 + len + 1];
		System.arraycopy(data, 0, cmd, 3, len);
		cmd[0] = (byte) 0x08;
		cmd[2] = (byte) len;
		cmd[cmd.length - 1] = (byte) 0xe3;
		Log.d("jokey", "cmd-->   "+DataUtils.toHexString(cmd));
		return cmd;
	}

	/**
	 * 写卡的每组数据要高位在后，低位在前，4字节一组
	 * 
	 * @param data
	 *            写卡数据
	 * @return
	 */
	private byte[] reData(byte[] data) {
		byte[] cmd = new byte[4];
		cmd[0] = data[3];
		cmd[1] = data[2];
		cmd[2] = data[1];
		cmd[3] = data[0];
		return cmd;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private byte[] getNewData(byte[] data) {
		byte[] newdata = new byte[16];
		byte[] data1 = new byte[4];
		for (int i = 0; i < 4; i++) {
			System.arraycopy(data, i * 4, data1, 0, 4);
			System.arraycopy(reData(data1), 0, newdata, i * 4, 4);
		}
		return newdata;
	}

	public interface OnM1CardListener {
		static final int WRITE_ERROR_LENGTH = 1;
		static final int WRITE_ERROR_FAIL = 2;

		void getCardIDFail();

		void getCardIDSuccess(String data);

		void writeDataFail(int errorcode);

		void writeDataSuccess();

		void readDataFail();

		void readDataSuccess(byte[] data);

		void generalFail();

		void generalSuccess();
	}
}