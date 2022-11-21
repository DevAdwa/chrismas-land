package com.example.fecafootdemo.ui.dashboard;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.fecafootdemo.AppLoader;
import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Control;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.data.network.ApiCallback;
import com.example.fecafootdemo.data.network.ApiRequests;
import com.example.fecafootdemo.ui.base.BasePresenter;
import com.example.fecafootdemo.utils.ErrorUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class MainPresenter<V extends MainView> extends BasePresenter<V> implements IMain<V> {
    private final String TAG = MainPresenter.class.getSimpleName();
    public MainPresenter(V v) {
        super(v);
    }

    @Override
    public void startControl() {
        getView().onStartControlActivity();
    }

    @Override
    public void resumeView() {
        /**int vipCapacity = coreDataManager.findCapacityByClass(2);
        int premiumCapacity = coreDataManager.findCapacityByClass(3);
        int prestigeCapacity = coreDataManager.findCapacityByClass(1);
        Game game = coreDataManager.getCurrentGame();

        int vipControl = coreDataManager.findAllCardClassByCardId(2).size();
        int premiumControl = coreDataManager.findAllCardClassByCardId(3).size();
        int prestigeControl = coreDataManager.findAllCardClassByCardId(1).size();

        double vipPercentage = ((float)(vipControl/100.0)) * vipCapacity;
        double premiumPercentage =  ((float)(premiumControl)/100.0) * premiumCapacity;
        double prestigePercentage =   ((float)(prestigeControl)/100.0) * prestigeCapacity;

        List<String> vipUnControlled = coreDataManager.getListOfByCatId(2);
        List<String> prestigeUnControlled = coreDataManager.getListOfByCatId(1);
        List<String> premiumUnControlled = coreDataManager.getListOfByCatId(3);

        HashSet<String> vipUnC = new HashSet<>(vipUnControlled);
        HashSet<String> prestUnC = new HashSet<>(prestigeUnControlled);
        HashSet<String> premiumUnC = new HashSet<>(premiumUnControlled);

        Log.i(TAG, "vipUnC: " + vipControl);
        Log.i(TAG, "presUnC: " + prestigePercentage);
        Log.i(TAG, "premUnC: " + premiumControl);

        for (String s : coreDataManager.findAllCardClassByCardId(2)) {
            vipUnC.remove(s);
        }

        for (String s : coreDataManager.findAllCardClassByCardId(1)) {
            prestUnC.remove(s);
        }

        for (String s : coreDataManager.findAllCardClassByCardId(3)) {
            premiumUnC.remove(s);
        }
        Log.i(TAG, "vipUnC2: " + new Gson().toJson(vipUnC));
        Log.i(TAG, "presUnC: " + new Gson().toJson(prestUnC));
        Log.i(TAG, "premUnC: " + new Gson().toJson(premiumUnC));
         **/
        //getView().onResumeView();
        setNavBarInfo();

    }

    @Override
    public void logout() {
        coreDataManager.signedIn(false);
        coreDataManager.saveUser(null);
        coreDataManager.resetTicketTable();
        getView().onStartLoginActivity();
    }

    @Override
    public void exportData() {}

    @Override
    public void setNavBarInfo() {
        User user = coreDataManager.getUser();
        ArrayList<Ticket> tickets = (ArrayList<Ticket>) coreDataManager.getTicketList();
        getView().onNavBarSetup(user, tickets);
    }
}
