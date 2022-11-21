package finger;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fecafootdemo.R;


public class FPDisplayActivity extends Activity
{
	public static final int FPDR_NONE   =0;
	public static final int FPDR_PASS   =1;
	public static Bitmap mImage = null;
	public static String msTitle = null;
	private ImageView mImgv = null;
	
	
@Override
protected void onCreate(Bundle savedInstanceState)
 {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fpdisplay_fbi);
		Button passButton = findViewById(R.id.pass);
        
        mImgv = this.findViewById(R.id.img);
		
		if(mImage != null)
		{
			mImgv.setImageBitmap(mImage);
		}
		if(msTitle != null)
		{
			this.setTitle(msTitle);
		}
	
		
		passButton.setOnClickListener(view -> {
			setResult(FPDR_PASS);
			finish();
		});
 }

}
