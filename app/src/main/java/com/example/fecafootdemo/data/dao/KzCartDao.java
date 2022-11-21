package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.KzCart;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Dao
public interface KzCartDao {
    @Insert
    void insertKzCart(KzCart kzCart);

    @Insert
    void insertKzCarts(List<KzCart> cartList);

    @Query("SELECT * FROM kzcart")
    List<KzCart> getKzCartList();

    @Query("SELECT * FROM kzcart WHERE qrcode = :code")
    KzCart findCardByCode(String code);

    @Query("SELECT * FROM kzcart WHERE qrqrcd = :qrcode")
    KzCart findCardInfoByQrCode(String qrcode);

    @Query("SELECT qrcode FROM kzcart WHERE qrclas = :catId")
    List<String> getListOfUncontrolled(int catId);
}
