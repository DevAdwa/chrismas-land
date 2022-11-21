package android_serialport_api;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.utils.DataUtils;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.ivsign.android.IDCReader.IDCReaderSDK;
import com.ivsign.android.IDCReader.SfzFileManager;

import java.io.UnsupportedEncodingException;




public class SFZAPI {
	private byte[] read_sfz = { 0x07, 0x00, 0x0d, (byte) 0xca, (byte) 0xdf,
			0x03, 0x00, 0x07, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
			(byte) 0x96, 0x69, 0x00, 0x00, (byte) 0xe3, 0x30 };
	private final byte[] REQB = { (byte) 0x05, (byte) 0x00, (byte) 0x00 };// 获取uid第一步指令
	private final byte[] Attrib = { (byte) 0x1d, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x01,
			(byte) 0x08 };
	private final byte[] getUID = { (byte) 0x00, (byte) 0x36, (byte) 0x00,
			(byte) 0x00, (byte) 0x08 };
	private static final String CARD_SUCCESS = "AAAAAA96690508000090";
	private static final String CARD_SUCCESS2 = "AAAAAA9669090A000090";
	private static final String TIMEOUT_RETURN = "AAAAAA96690005050505";
	private static final String CMD_ERROR = "CMD_ERROR";
	private byte[] buffer = new byte[2312];
	private byte[] buffer1; 
	private byte[] buffer2; 
	private byte[] buffer3; 
	private Result result;
	private UIDResult uidResult;
	private Context m_Context;
	private final Logger logger = LoggerFactory.getLogger();

	public SFZAPI(Looper looper, String rootPath, Context context) {
		this.m_Context = context;
	}

	/**
	 * 读身份证
	 * Read ID card
	 * @return id card information
	 */
	public Result readSFZ() {
		try {
//			SerialPortManager.getInstance().setDownGpioIDCard();
//			Thread.sleep(1000);
//			Log.d("jokey", "SFZ:"+SerialPortManager.getInstance().readGpioIDCardStatus());
//			SerialPortManager.getInstance().setUpGpioIDCard();
//			Thread.sleep(1000);
//			Log.d("jokey", "SFZ:"+SerialPortManager.getInstance().readGpioIDCardStatus());
//			SerialPortManager.getInstance().openSerialPortIDCard();
//			Thread.sleep(500);
//			Log.d("jokey", "SFZ:"+SerialPortManager.getInstance().isOpen());
			People people = null;
			int length = 0;
			result = new Result();
			SerialPortManager.getInstance().write(read_sfz);
			length = SerialPortManager.getInstance().read(buffer, 2500, 200);
			
			long time = System.currentTimeMillis();
			while(length==0&&(System.currentTimeMillis()-time)<=4000&&SerialPortManager.getInstance().isOpen()) {
				//add by yjj at 2017/1/13 11:00
				SerialPortManager.getInstance().setDownGpioIDCard();
				Thread.sleep(1250);
				Log.d("jokey", "SFZ:"+SerialPortManager.getInstance().readGpioIDCardStatus());
				SerialPortManager.getInstance().setUpGpioIDCard();
				Thread.sleep(1250);
				Log.d("jokey", "SFZ:"+SerialPortManager.getInstance().readGpioIDCardStatus());
				SerialPortManager.getInstance().openSerialPortIDCard();
				Thread.sleep(500);
				Log.d("jokey", "SFZ:"+SerialPortManager.getInstance().isOpen());
				SerialPortManager.getInstance().write(read_sfz);
				length = SerialPortManager.getInstance().read(buffer, 2500, 200);
				//end
			}
			if(length == 0){
				result.confirmationCode = Result.TIME_OUT;
				return result;
			}
			people = decode(buffer, length);
	
			if (people == null) {
				result.confirmationCode = Result.FIND_FAIL;
			} else {
				result.confirmationCode = Result.SUCCESS;
				result.resultInfo = people;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取身份证uid
	 * Get id card uid
	 * @return uid字节数组
	 */
	public UIDResult getUID() {
		buffer1 = new byte[60];
		buffer2 = new byte[60];
		buffer3 = new byte[60];
		uidResult = new UIDResult();
		SerialPortManager.getInstance().write(topackage(REQB));
		int length1 = SerialPortManager.getInstance().read(buffer1, 4000, 200);
		Log.d("jokey", "getUID-->length:"+length1+"buffer1:"+ DataUtils.toHexString(buffer1));
		if (length1 == 13) {
			SerialPortManager.getInstance().write(topackage(Attrib));
			int length2 = SerialPortManager.getInstance().read(buffer2, 4000,
					200);
			if (length2 == 2) {
				SerialPortManager.getInstance().write(topackage(getUID));
				int length3 = SerialPortManager.getInstance().read(buffer3,
						4000, 200);
				if (length3 == 11 && buffer3[length3 - 2] == 0x00
						&& buffer3[length3 - 3] == -112) {
					uidResult.code = 1;
					byte[] newbuffer = new byte[8];
					Log.d("jokey", "getUID-->newbuffer:"+DataUtils.toHexString(newbuffer));
					System.arraycopy(buffer3, 0, newbuffer, 0, 8);
					uidResult.result = newbuffer;
				} else {
					uidResult.code = UIDResult.UID_FAIL;
				}
			} else {
				uidResult.code = UIDResult.Attrib_FAIL;
			}
		} else {
			uidResult.code = UIDResult.REQB_FAIL;
		}
		return uidResult;
	}

	private People decode(byte[] buffer, int length) {
		if (buffer == null) {
			return null;
		}
		byte[] b = new byte[10];
		System.arraycopy(buffer, 0, b, 0, 10);
		String result = DataUtils.toHexString(b);
		People people = null;
		if (result.equalsIgnoreCase(CARD_SUCCESS)
				|| result.equalsIgnoreCase(CARD_SUCCESS2)) {
			byte[] data = new byte[buffer.length - 10];
			System.arraycopy(buffer, 10, data, 0, buffer.length - 10);
			people = decodeInfo(data, length);
		} else if (result.equalsIgnoreCase(TIMEOUT_RETURN)) {
			logger.debug(TIMEOUT_RETURN);
		} else if (result.startsWith(CMD_ERROR)) {
			logger.debug(CMD_ERROR);
		}
		return people;
	}

	private People decodeInfo(byte[] buffer, int length) {
		short textSize = getShort(buffer[0], buffer[1]);
		short imageSize = getShort(buffer[2], buffer[3]);
		byte[] model = null;
		short skipLength = 0;
		byte[] text = new byte[textSize];
		System.arraycopy(buffer, 4 + skipLength, text, 0, textSize);
		byte[] image = new byte[imageSize];
		System.arraycopy(buffer, 4 + skipLength + textSize, image, 0, imageSize);

		People people = null;
		try {
			String temp = null;
			people = new People();
			people.setHeadImage(image);
			// 姓名
			temp = new String(text, 0, 30, "UTF-16LE").trim();
			people.setPeopleName(temp);

			// 性别
			temp = new String(text, 30, 2, "UTF-16LE");
			if (temp.equals("1"))
				temp = "男";
			else
				temp = "女";
			people.setPeopleSex(temp);

			// 民族
			temp = new String(text, 32, 4, "UTF-16LE");
			try {
				int code = Integer.parseInt(temp.toString());
				temp = decodeNation(code);
			} catch (Exception e) {
				temp = "";
			}
			people.setPeopleNation(temp);

			// 出生
			temp = new String(text, 36, 16, "UTF-16LE").trim();
			people.setPeopleBirthday(temp);

			// 住址
			temp = new String(text, 52, 70, "UTF-16LE").trim();
			people.setPeopleAddress(temp);

			// 身份证号
			temp = new String(text, 122, 36, "UTF-16LE").trim();
			people.setPeopleIDCode(temp);

			// 签发机关
			temp = new String(text, 158, 30, "UTF-16LE").trim();
			people.setDepartment(temp);

			// 有效起始日期
			temp = new String(text, 188, 16, "UTF-16LE").trim();
			people.setStartDate(temp);

			// 有效截止日期
			temp = new String(text, 204, 16, "UTF-16LE").trim();
			people.setEndDate(temp);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}

		people.setPhoto(parsePhoto(image));
		people.setModel(model);
		return people;
	}

	private byte[] parsePhoto(byte[] wltdata) {
		SfzFileManager sfzFileManager = new SfzFileManager();
		if (sfzFileManager.initDB(this.m_Context, R.raw.base, R.raw.license)) {
			int ret = IDCReaderSDK.Init();
			if (0 == ret) {
				ret = IDCReaderSDK.unpack(buffer);
				if (1 == ret) {
					byte[] image = IDCReaderSDK.getPhoto();
					return image;
				}
			}
		}
		return null;
	}

	private short getShort(byte b1, byte b2) {
		short temp = 0;
		temp |= (b1 & 0xff);
		temp <<= 8;
		temp |= (b2 & 0xff);
		return temp;
	}

	private String decodeNation(int code) {
		String nation;
		switch (code) {
		case 1:
			nation = "汉";
			break;
		case 2:
			nation = "蒙古";
			break;
		case 3:
			nation = "回";
			break;
		case 4:
			nation = "藏";
			break;
		case 5:
			nation = "维吾尔";
			break;
		case 6:
			nation = "苗";
			break;
		case 7:
			nation = "彝";
			break;
		case 8:
			nation = "壮";
			break;
		case 9:
			nation = "布依";
			break;
		case 10:
			nation = "朝鲜";
			break;
		case 11:
			nation = "满";
			break;
		case 12:
			nation = "侗";
			break;
		case 13:
			nation = "瑶";
			break;
		case 14:
			nation = "白";
			break;
		case 15:
			nation = "土家";
			break;
		case 16:
			nation = "哈尼";
			break;
		case 17:
			nation = "哈萨克";
			break;
		case 18:
			nation = "傣";
			break;
		case 19:
			nation = "黎";
			break;
		case 20:
			nation = "傈僳";
			break;
		case 21:
			nation = "佤";
			break;
		case 22:
			nation = "畲";
			break;
		case 23:
			nation = "高山";
			break;
		case 24:
			nation = "拉祜";
			break;
		case 25:
			nation = "水";
			break;
		case 26:
			nation = "东乡";
			break;
		case 27:
			nation = "纳西";
			break;
		case 28:
			nation = "景颇";
			break;
		case 29:
			nation = "柯尔克孜";
			break;
		case 30:
			nation = "土";
			break;
		case 31:
			nation = "达斡尔";
			break;
		case 32:
			nation = "仫佬";
			break;
		case 33:
			nation = "羌";
			break;
		case 34:
			nation = "布朗";
			break;
		case 35:
			nation = "撒拉";
			break;
		case 36:
			nation = "毛南";
			break;
		case 37:
			nation = "仡佬";
			break;
		case 38:
			nation = "锡伯";
			break;
		case 39:
			nation = "阿昌";
			break;
		case 40:
			nation = "普米";
			break;
		case 41:
			nation = "塔吉克";
			break;
		case 42:
			nation = "怒";
			break;
		case 43:
			nation = "乌孜别克";
			break;
		case 44:
			nation = "俄罗斯";
			break;
		case 45:
			nation = "鄂温克";
			break;
		case 46:
			nation = "德昂";
			break;
		case 47:
			nation = "保安";
			break;
		case 48:
			nation = "裕固";
			break;
		case 49:
			nation = "京";
			break;
		case 50:
			nation = "塔塔尔";
			break;
		case 51:
			nation = "独龙";
			break;
		case 52:
			nation = "鄂伦春";
			break;
		case 53:
			nation = "赫哲";
			break;
		case 54:
			nation = "门巴";
			break;
		case 55:
			nation = "珞巴";
			break;
		case 56:
			nation = "基诺";
			break;
		case 97:
			nation = "其他";
			break;
		case 98:
			nation = "外国血统中国籍人士";
			break;
		default:
			nation = "";
		}
		return nation;
	}

	public static class People {
		/**
		 * 姓名
		 */
		private String peopleName;

		/**
		 * 性别
		 */
		private String peopleSex;

		/**
		 * 民族
		 */
		private String peopleNation;

		/**
		 * 出生日期
		 */
		private String peopleBirthday;

		/**
		 * 住址
		 */
		private String peopleAddress;

		/**
		 * 身份证号
		 */
		private String peopleIDCode;

		/**
		 * 签发机关
		 */
		private String department;

		/**
		 * 有效期限：开始
		 */
		private String startDate;

		/**
		 * 有效期限：结束
		 */
		private String endDate;

		/**
		 * 身份证头像
		 */
		private byte[] photo;

		/**
		 * 没有解析成图片的数据大小一般为1024字节
		 */
		private byte[] headImage;

		/**
		 * 三代证指纹模板数据，正常位1024，如果为null，说明为二代证，没有指纹模板数据
		 */
		private byte[] model;

		public String getPeopleName() {
			return peopleName;
		}

		public void setPeopleName(String peopleName) {
			this.peopleName = peopleName;
		}

		public String getPeopleSex() {
			return peopleSex;
		}

		public void setPeopleSex(String peopleSex) {
			this.peopleSex = peopleSex;
		}

		public String getPeopleNation() {
			return peopleNation;
		}

		public void setPeopleNation(String peopleNation) {
			this.peopleNation = peopleNation;
		}

		public String getPeopleBirthday() {
			return peopleBirthday;
		}

		public void setPeopleBirthday(String peopleBirthday) {
			this.peopleBirthday = peopleBirthday;
		}

		public String getPeopleAddress() {
			return peopleAddress;
		}

		public void setPeopleAddress(String peopleAddress) {
			this.peopleAddress = peopleAddress;
		}

		public String getPeopleIDCode() {
			return peopleIDCode;
		}

		public void setPeopleIDCode(String peopleIDCode) {
			this.peopleIDCode = peopleIDCode;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public byte[] getPhoto() {
			return photo;
		}

		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}

		public byte[] getHeadImage() {
			return headImage;
		}

		public void setHeadImage(byte[] headImage) {
			this.headImage = headImage;
		}

		public byte[] getModel() {
			return model;
		}

		public void setModel(byte[] model) {
			this.model = model;
		}
	}

	public static class Result {
		public static final int SUCCESS = 1;
		public static final int FIND_FAIL = 2;
		public static final int TIME_OUT = 3;
		public static final int OTHER_EXCEPTION = 4;

		/**
		 * 确认码 1: 成功 2：失败 3: 超时 4：其它异常5:不是三代证
		 */
		public int confirmationCode;

		/**
		 * 结果集:当确认码为1时，再判断是否有结果
		 */
		public Object resultInfo;
	}

	public static class UIDResult {
		public static final int SUCCESS = 1;
		public static final int REQB_FAIL = 2;
		public static final int Attrib_FAIL = 3;
		public static final int UID_FAIL = 4;

		public int code;

		public byte[] result;
	}

	/**
	 * 封装指令并返回
	 * 
	 * @param old
	 *            原始指令
	 * @return 新的指令
	 */
	private byte[] topackage(byte[] old) {
		int len = old.length;
		byte[] heade = { (byte) 0x0C };
		byte[] cmd = new byte[1 + 2 + len + 1];// 头+2字节长度+指令+校验
		System.arraycopy(heade, 0, cmd, 0, heade.length);
		cmd[heade.length] = (byte) 0;
		cmd[heade.length + 1] = (byte) len;
		System.arraycopy(old, 0, cmd, heade.length + 2, len);
		cmd[cmd.length - 1] = (byte) 0xff;
		return cmd;
	}
}