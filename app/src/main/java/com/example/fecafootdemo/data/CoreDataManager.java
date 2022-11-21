package com.example.fecafootdemo.data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import com.example.fecafootdemo.AppLoader;
import com.example.fecafootdemo.data.dao.CapacityDao;
import com.example.fecafootdemo.data.dao.ControlDao;
import com.example.fecafootdemo.data.dao.CypherDao;
import com.example.fecafootdemo.data.dao.GameDao;
import com.example.fecafootdemo.data.dao.KzCartDao;
import com.example.fecafootdemo.data.dao.KzGareDao;
import com.example.fecafootdemo.data.dao.KzListDao;
import com.example.fecafootdemo.data.dao.SeatDao;
import com.example.fecafootdemo.data.dao.TicketDaO;
import com.example.fecafootdemo.data.dao.entities.Capacity;
import com.example.fecafootdemo.data.dao.entities.Control;
import com.example.fecafootdemo.data.dao.entities.Cypher;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.KzCart;
import com.example.fecafootdemo.data.dao.entities.KzList;
import com.example.fecafootdemo.data.dao.entities.Seat;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.data.shared.SharedPref;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class CoreDataManager implements ICoreDataManager {
    private static CoreDataManager instance;
    private final SharedPref sharedPref;
    private final CypherDao cypherDao;
    private final GameDao gameDao;
    private final CapacityDao capacityDao;
    private final KzCartDao kzCartDao;
    private final KzListDao kzListDao;
    private final KzGareDao kzGareDao;
    private final SeatDao seatDao;
    private final ControlDao controlDao;
    private final TicketDaO ticketDaO;

    private CoreDataManager(Context context) {
        sharedPref = new SharedPref(context);
        cypherDao = AppDB.getInstance(context).getCypherDao();
        gameDao = AppDB.getInstance(context).getGameDao();
        capacityDao = AppDB.getInstance(context).getCapacityDao();
        kzCartDao = AppDB.getInstance(context).getKzCartDao();
        kzListDao = AppDB.getInstance(context).getKzListDao();
        kzGareDao = AppDB.getInstance(context).getKzGareDao();
        seatDao = AppDB.getInstance(context).getSeatDao();
        controlDao = AppDB.getInstance(context).getControlDao();
        ticketDaO = AppDB.getInstance(context).getTicketDaO();

    }

    public static synchronized CoreDataManager getInstance() {
        if (instance == null) {
            instance = new CoreDataManager(AppLoader.getInstance());
        }
        return instance;
    }

    @Override
    public void signedIn(boolean state) {
        sharedPref.signedIn(state);
    }

    @Override
    public boolean getSignedIn() {
        return sharedPref.getSignedIn();
    }

    @Override
    public void saveUser(User user) {
        sharedPref.saveUser(user);
    }

    @Override
    public User getUser() {
        return sharedPref.getUser();
    }

    @Override
    public void clearUser() {
        sharedPref.clearUser();
    }

    @Override
    public void saveCypher(Cypher cypher) {
        cypherDao.insertCypher(cypher);
    }

    @Override
    public Cypher getCypher() {
        return cypherDao.getCypher();
    }

    @SuppressLint("HardwareIds")
    @Override
    public String deviceID() {
        TelephonyManager tm = (TelephonyManager) AppLoader.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        assert tm != null;
        if (ActivityCompat.checkSelfPermission(AppLoader.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return Settings.Secure.getString(AppLoader.getInstance().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public void setGameImported(boolean state) {
        sharedPref.setGameImported(state);
    }

    @Override
    public boolean isGameImported() {
        return sharedPref.isGameImported();
    }

    @Override
    public void insertGame(Game game) {
        gameDao.insertGame(game);
    }

    @Override
    public void insertGames(List<Game> games) {
        gameDao.insertGames(games);
    }

    @Override
    public List<Game> getGameList() {
        return gameDao.getGameList();
    }

    @Override
    public void clearGameTable() {
        gameDao.clearGameTable();
    }

    @Override
    public void insertCapacity(Capacity capacity) {
        capacityDao.insertCapacity(capacity);
    }

    @Override
    public void insertCapacity(List<Capacity> capacities) {
        capacityDao.insertCapacities(capacities);
    }

    @Override
    public List<Capacity> getCapacityList() {
        return capacityDao.getCapacityList();
    }

    @Override
    public void clearCapacityTable() {
        capacityDao.clearCapacityTable();
    }

    @Override
    public void insertSeat(Seat seat) {
        seatDao.insertSeat(seat);
    }

    @Override
    public void insertSeats(List<Seat> seats) {
        seatDao.insertSeats(seats);
    }

    @Override
    public List<Seat> getSeatList() {
        return seatDao.getSeatList();
    }

    @Override
    public void clearSeatsTable() {
        seatDao.clearSeatsTable();
    }

    @Override
    public KzCart getCardInfo(String code) {
        return kzCartDao.findCardByCode(code);
    }

    @Override
    public void saveCurrentGame(Game game) {
        sharedPref.saveCurrentGame(game);
    }

    @Override
    public Game getCurrentGame() {
        return sharedPref.getCurrentGame();
    }

    @Override
    public void clearCurrentGame() {
        sharedPref.clearCurrentGame();
    }

    @Override
    public KzList findCategoryBy(String qrcate) {
        return kzListDao.findCategoryBy(qrcate);
    }

    @Override
    public void insertControl(Control control) {
        controlDao.insertControl(control);
    }

    @Override
    public List<Control> getControlsByCode(String code) {
        return controlDao.getControlsByCode(code);
    }

    @Override
    public List<Control> getControls() {
        return controlDao.getControls();
    }

    @Override
    public void clearControlsTable() {
        controlDao.clearControlsTable();
    }

    @Override
    public KzCart findCardInfoByQrCode(String qrcode) {
        return kzCartDao.findCardInfoByQrCode(qrcode);
    }

    @Override
    public List<String> findAllCardCategoryByCategoryId(int cardCategory) {
        return controlDao.findAllCardCategoryByCategoryId(cardCategory);
    }

    @Override
    public List<String> findAllCardClassByCardId(int cardClz) {
        return controlDao.findAllCardClassByCardId(cardClz);
    }

    @Override
    public int findCapacityByClass(int clazCode) {
        return capacityDao.findCapacity(clazCode);
    }

    @Override
    public List<String> getListOfByCatId(int categoryCode) {
        return kzCartDao.getListOfUncontrolled(categoryCode);
    }

    @Override
    public void insertKzCard(List<KzCart> cartList) {
        kzCartDao.insertKzCarts(cartList);
    }

    @Override
    public String getRandomSeat() {
        return seatDao.getRandomSeat();
    }

    @Override
    public void setLang(String lang) {
        sharedPref.setLang(lang);
    }

    @Override
    public String getLang() {
        return sharedPref.getLang();
    }

    @Override
    public void insert(Ticket ticket) {
        ticketDaO.insert(ticket);
    }

    @Override
    public List<Ticket> getTicketList() {
        return ticketDaO.getTicketList();
    }

    @Override
    public void resetTicketTable() {
        ticketDaO.resetTicketTable();
    }
}
