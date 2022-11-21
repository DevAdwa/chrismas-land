package com.example.fecafootdemo.data;

import com.example.fecafootdemo.data.dao.GameDao;
import com.example.fecafootdemo.data.dao.SeatDao;
import com.example.fecafootdemo.data.dao.entities.Capacity;
import com.example.fecafootdemo.data.dao.entities.Control;
import com.example.fecafootdemo.data.dao.entities.Cypher;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.KzCart;
import com.example.fecafootdemo.data.dao.entities.KzList;
import com.example.fecafootdemo.data.dao.entities.Seat;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.data.dao.entities.User;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
interface ICoreDataManager {
    void signedIn(boolean state);

    boolean getSignedIn();

    void saveUser(User user);

    User getUser();

    void clearUser();

    void saveCypher(Cypher cypher);

    Cypher getCypher();

    String deviceID();

    void setGameImported(boolean state);

    boolean isGameImported();

    void insertGame(Game game);

    void insertGames(List<Game> games);

    List<Game> getGameList();

    void clearGameTable();

    void insertCapacity(Capacity capacity);

    void insertCapacity(List<Capacity> capacities);

    List<Capacity> getCapacityList();

    void clearCapacityTable();


    void insertSeat(Seat seat);

    void insertSeats(List<Seat> seats);

    List<Seat> getSeatList();

    void clearSeatsTable();

    KzCart getCardInfo(String code);

    void saveCurrentGame(Game game);

    Game getCurrentGame();

    void clearCurrentGame();

    KzList findCategoryBy(String qrcate);

    void insertControl(Control control);

    List<Control> getControlsByCode(String code);

    List<Control> getControls();

    void clearControlsTable();

    KzCart findCardInfoByQrCode(String qrcode);

    List<String> findAllCardCategoryByCategoryId(int cardCategory);

    List<String> findAllCardClassByCardId(int cardClz);

    int findCapacityByClass(int clazCode);

    List<String> getListOfByCatId(int categoryCode);

    void insertKzCard(List<KzCart> cartList);

    String getRandomSeat();

    void setLang(String lang);
    String getLang();

    void insert(Ticket ticket);

    List<Ticket> getTicketList();

    void resetTicketTable();
}
