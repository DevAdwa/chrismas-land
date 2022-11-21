package android_serialport_api;

import android.util.Log;

import com.example.fecafootdemo.utils.DataUtils;

import java.io.UnsupportedEncodingException;



public class IcCardOrderAPI {
	public static class Result {
		public static final int SUCCESS = 1;
		public static final int FAILURE = 0;
		public Object resultInfo;
	}
	private static final byte[] CHECK_CARD_PRESENT = {(byte)0x03,(byte)0x00,(byte)0x00,(byte)0x03};
	private byte[] buffer = new byte[1024];

	public IcCardOrderAPI() {
	}

	/*
	 * IC卡通用操作接口
	 */
	public synchronized byte[] operateICApdu(byte[] calcLrc, int lenLrc) {
		Log.i("cy", "Enter function IcCardOrderAPI-operdateICApdu()");
		byte[] calcCrc = getCmd(calcLrc);
		
		SerialPortManager.getInstance().write(calcCrc);
		int length = SerialPortManager.getInstance().read(buffer, 4000, 200);
		if (length > 0) {
			byte[] recvData = new byte[length];
			System.arraycopy(buffer, 0, recvData, 0, length);
			Log.d("jokey", "operateICApdu--->recvData:"+ DataUtils.toHexString(recvData));
			
			byte[] retData = new byte[length - 4];
			System.arraycopy(recvData, 3, retData, 0, retData.length);
			return retData;
		}
		return null;
	}
	/**
	 * 复位
	 * @param calcLrc
	 * @return
	 */
	public synchronized boolean reset(byte[] calcLrc) {
		SerialPortManager.getInstance().write(calcLrc);

		int length = SerialPortManager.getInstance().read(buffer, 4000, 200);
		Log.d("jokey", "reset--->length:"+length);
		Log.d("jokey", "reset--->buffer:"+DataUtils.toHexString(buffer));
		if (length > 0) {
			byte[] recvData = new byte[length];
			System.arraycopy(buffer, 0, recvData, 0, length);
			Log.d("jokey", "reset--->recvData:"+DataUtils.toHexString(recvData));
			if(length == 3||length == 4){
				if(recvData[0]==0x03&&recvData[1]==0x01&&recvData[2]==0x01)
					return false;
			}
			return true;
		}
		return false;
	}
	
	private byte[] getCmd(byte[] calcLrc){
		// 前2字节的头和长度改为放在此处封装
		byte[] operCmd = new byte[calcLrc.length + 3];
		operCmd[0] = 0x02; // kt2001q命令字
		byte[] lenBuffer = DataUtils.int2Byte2(calcLrc.length);
		operCmd[1] = (byte) lenBuffer[0]; // kt2001q数据长度高字节
		operCmd[2] = (byte) lenBuffer[1]; // kt2001q数据长度低字节
		System.arraycopy(calcLrc, 0, operCmd, 3, calcLrc.length); // iso整个命令包
		byte[] calcCrc = DataUtils.getFirstCmd(operCmd, operCmd.length); // lrc校验和
		
		return calcCrc;
	}
	private void printlog(String tag, byte[] toLog) {
		Log.i("cy", "Enter function IcCardOrderAPI-printlog()");

		String toLogStr = DataUtils.toHexString(toLog);
		Log.i("cy", tag + "=" + toLogStr);
	}
	/**
	 * 
	 * @return  true: present
	 * 			false: absent
	 */
	public boolean checkCardPresent(){
		SerialPortManager.getInstance().write(CHECK_CARD_PRESENT);
		int length = SerialPortManager.getInstance().read(buffer, 4000, 200);
		if(length>0){
			byte[] rcvData = new byte[length];
			System.arraycopy(buffer, 0, rcvData, 0, length);
			try {
				String result = new String(rcvData, "utf-8");
				Log.d("jokey", "result: "+result);
				if("ABSENT".equals(result)){
					return false;
				}else if("PRESENT".equals(result)){
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}