package com.example.fecafootdemo.ui.dashboard.control;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.StringRes;

import com.android.volley.VolleyError;
import com.example.fecafootdemo.AppLoader;
import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.CardInfo;
import com.example.fecafootdemo.data.dao.entities.Control;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.KzCart;
import com.example.fecafootdemo.data.dao.entities.KzList;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.data.network.ApiCallback;
import com.example.fecafootdemo.data.network.ApiRequests;
import com.example.fecafootdemo.ui.base.BasePresenter;
import com.example.fecafootdemo.utils.DataUtils;
import com.example.fecafootdemo.utils.ErrorUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */

public class ControlPresenter<V extends ControlView> extends BasePresenter<V> implements IControl<V> {
    private final String TAG = ControlPresenter.class.getSimpleName();
    private final SimpleDateFormat format = new SimpleDateFormat("y-M-d HH:mm:ss", Locale.getDefault());//2022-03-28 12:33:03
    private final SimpleDateFormat display = new SimpleDateFormat("d-M-y", Locale.getDefault());//2022-03-28 12:33:03
    private Ticket ticket;
    public ControlPresenter(V v) {
        super(v);
    }

    @Override
    public void checkCode(String code, boolean isQrcode) {
        getView().onShowLoading(R.string.searching);
        new ApiRequests().checkCard(isQrcode ? "" : code.toUpperCase(), isQrcode ? code : "", new ApiCallback() {
            @Override
            public <T> void Success(T t) {
                JSONObject data = (JSONObject) t;
                Log.i(TAG, data.toString());
                ticket = new Gson().fromJson(data.toString(), Ticket.class);
                getView().onHideLogin();
                getView().onShowSummary(ticket);
            }

            @Override
            public void Failure(Exception exception) {
                getView().onHideLogin();
                Log.e(TAG, new Gson().toJson(exception.getMessage()));
                getView().onShowError(R.string.invalid_code);
            }

            @Override
            public void VolleyException(VolleyError error) {
                getView().onHideLogin();
                Log.e(TAG, new Gson().toJson(error.getMessage()));
                getView().onShowError(R.string.server_error);
            }

            @Override
            public void ServerErrorCode(int code) {
                String message = ErrorUtils.getMessage(code, AppLoader.getInstance());
                getView().onShowError(message);
                Log.e(TAG, "" + code);
            }
        });
    }

    @Override
    public void controlCardCode(String data) {
        Log.i(TAG, data);

        if (!isNetworkAvailable()) {
            KzCart card = coreDataManager.findCardInfoByQrCode(data);
            if (card != null) {
                checkCode(card.getQrcode(), false);
            } else {
                getView().onShowError(R.string.invalid_code);
            }

        } else {
            getView().onShowLoading(R.string.searching);
            new ApiRequests().checkCard("", data, new ApiCallback() {
                @Override
                public <T> void Success(T t) {
                    JSONObject data = (JSONObject) t;
                    Log.i(TAG, data.toString());
                    getView().onHideLogin();
                    try {
                        if (data.getString("ctrlStatus").equalsIgnoreCase("ok")) {
                            CardInfo cardInfo = new Gson().fromJson(data.getJSONObject("cardInfo").toString(), CardInfo.class);
                            String[] datetime = cardInfo.getCardEndDate().split(" ")[0].split("-");
                            cardInfo.setCardEndDate(datetime[2].concat("-").concat(datetime[1].concat("-").concat(datetime[0])));
                            int count = coreDataManager.getControlsByCode(cardInfo.getCardCode()).size();
                            if (Integer.parseInt(cardInfo.getCartNber()) > count) {
                                new Handler().postDelayed(() -> {
                                    getView().onShowControlView(cardInfo, coreDataManager.getUser().getUsuser(), 0);
                                }, 300);
                            } else {
                                getView().onShowControlView(cardInfo, coreDataManager.getUser().getUsuser(), R.string.access_limit_reached);
                            }
                        } else {
                            Log.i(TAG, data.toString());
                            int message;
                            switch (data.getInt("errorCode")) {
                                case 2040:
                                    message = (R.string.access_card_not_found);
                                    getView().onShowError(R.string.access_card_not_found);
                                    return;
                                case 2030:
                                    message = (R.string.card_already_controlled);
                                    break;
                                case 2020:
                                    message = (R.string.card_already_controlled_for_this_game);
                                    break;
                                case 2010:
                                    message = (R.string.card_fan_not_authorized_for_this_match);
                                    break;
                                case 2019:
                                    message = (R.string.access_not_allowed_at_this_stadium);
                                    break;
                                default:
                                    message = (R.string.unknow_error);
                                    break;
                            }

                            CardInfo cardInfo = new Gson().fromJson(data.getJSONObject("cardInfo").toString(), CardInfo.class);
                            cardInfo.setCardEndDate(cardInfo.getCardEndDate().split(" ")[0]);
                            new Handler().postDelayed(() -> {
                                getView().onShowControlView(cardInfo, coreDataManager.getUser().getUsuser(), message);
                            }, 300);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        getView().onShowError(R.string.unknow_error);
                    }

                }

                @Override
                public void Failure(Exception exception) {
                    getView().onHideLogin();
                    Log.e(TAG, new Gson().toJson(exception));
                    getView().onShowError(R.string.unknow_error);
                }

                @Override
                public void VolleyException(VolleyError error) {
                    getView().onHideLogin();
                    Log.e(TAG, new Gson().toJson(error));
                    getView().onShowError(R.string.server_error);
                }

                @Override
                public void ServerErrorCode(int code) {
                    String message = ErrorUtils.getMessage(code, AppLoader.getInstance());
                    getView().onShowError(message);
                    Log.e(TAG, "" + code);
                }
            });
        }
    }

    @Override
    public void control(Ticket cTicket) {
        coreDataManager.insert(cTicket);
    }

    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date) {
        return date.getTime() <= max.getTime() && date.getTime() >= max.getTime();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) AppLoader.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
