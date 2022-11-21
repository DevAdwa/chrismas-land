package com.example.fecafootdemo.asynctask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.CoreDataManager;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.ui.dashboard.control.ControlActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.lang.ref.WeakReference;

import android_serialport_api.PrinterAPI;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/30/22)
 */
public class PrintReceipt extends AsyncTask<Void, Void, Bitmap> {
    private final String content;
    private final WeakReference<ControlActivity> reference;
    private WeakReference<View> viewWeakReference;
    private WeakReference<ImageView> imReference;

    public PrintReceipt(ControlActivity activity, String content){
        this.content = content;
        this.reference = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        View view = reference.get().findViewById(R.id.print_container_ll);
        viewWeakReference = new WeakReference<>(view);
        imReference = new WeakReference<>(view.findViewById(R.id.qrcode));
        Game game = CoreDataManager.getInstance().getCurrentGame();
        String match = game.getEquipeLabel1().concat(" vs ").concat(game.getEquipeLabel2());
        ((TextView)view.findViewById(R.id.match_tv)).setText(match);
        ((TextView)view.findViewById(R.id.print_content_tv)).setText(content);
        ((TextView)view.findViewById(R.id.user_tv)).setText("utilisateur: ".concat(CoreDataManager.getInstance().getUser().getUsuser()));
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try{
            if (reference.get() == null || viewWeakReference.get() == null) return null;
            return PrinterAPI.createBarCode(reference.get(), "snapcen ltd", BarcodeFormat.QR_CODE, 384, 381, false);
        }catch (WriterException we){
            we.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (reference.get() == null || viewWeakReference.get() == null) return;
        if (bitmap == null){
            reference.get().onShowError(reference.get().getString(R.string.printing_ticket_failed));
        }else{
            imReference.get().setImageBitmap(bitmap);
            Bitmap b = Bitmap.createBitmap(viewWeakReference.get().getWidth(), viewWeakReference.get().getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            viewWeakReference.get().draw(c);
            reference.get().onPrintReceipt(b);
        }
    }
}
