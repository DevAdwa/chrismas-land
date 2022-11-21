package com.example.fecafootdemo.crypto;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

import com.example.fecafootdemo.data.CoreDataManager;
import com.example.fecafootdemo.data.dao.entities.Cypher;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class E2DeCypher {
    static SimpleDateFormat format = new SimpleDateFormat("y-MM-d HH:mm:ss", Locale.getDefault());
    static SimpleDateFormat formatDefault = new SimpleDateFormat("d MMMM yyyy HH:mm: a", Locale.getDefault());
    static SimpleDateFormat formatDFrench = new SimpleDateFormat("d MMMM yyyy HH'h'mm", Locale.getDefault());
    private static Cypher cypher;
    static boolean isSuccessful = false;
    private static final String[] shd =
            {"personID", "IDCard", "personName", "fidelityCode",
                    "cartCode", "cartCategori", "cartClass", "cartNber",
                    "cartNberRest", "cartStadium", "cartEquipe",
                    "startDate", "endDate"};


    /**
     * This fxn does the decrypting of the data
     * @param data
     * @return JsonObject
     */
    @SuppressLint("GetInstance")
    public static JSONObject OrakoolDecoder(String data) throws Exception {
        /**
         * Extra key & dictionary for each ticket
         */

        JSONObject params = new JSONObject();
        byte[] bytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
        cypher = CoreDataManager.getInstance().getCypher();
        if (cypher == null){
            Log.i("hit_here", new Gson().toJson(cypher));
            params.put("status", false);
            return params;
        }
        //SecretKeySpec secretKeySpec = new SecretKeySpec(cypher.getEncryptionKey().getBytes(), "AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec("31B517590C296025".getBytes(), "AES");
        Cipher cipher = null;
        Map<String, String> mapper = new HashMap<>();
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] original = cipher.doFinal(bytes);
            String[] dataSplit = new String(original).split("#");

            for (int i = 0; i < dataSplit.length; i++) {
                mapper.put(shd[i], dataSplit[i]);
            }


            params.put(shd[0], decoder(Objects.requireNonNull(mapper.get("personID")), cypher.getCypher_dictionary()));
            SimpleDateFormat formatter = new SimpleDateFormat("d/M/y hh:mm:ss", Locale.getDefault());
            Date startDate = new Date((decoder(Objects.requireNonNull(mapper.get("startDate")), cypher.getCypher_dictionary()) + 1596236400) * 1000L);
            Date endDate = new Date((decoder(Objects.requireNonNull(mapper.get("endDate")), cypher.getCypher_dictionary()) + 1596236400) * 1000L);

            params.put(shd[11], startDate.getTime());
            params.put(shd[12], endDate.getTime());

            /** params.put(shd[0], mapper.get("travel"));
             SimpleDateFormat formatter = new SimpleDateFormat("d/M/y hh:mm:ss", Locale.getDefault());

             Date departureDate = new Date((decoder(Objects.requireNonNull(mapper.get("departure")), cypher.getCypher_dictionary()) + 1596236400) * 1000L);
             params.put(shd[1], departureDate.getTime());**/


            /**params.put(shd[3], decoder(Objects.requireNonNull(mapper.get("seat")), cypher.getCypher_dictionary()));
            params.put(shd[4], mapper.get("ticket"));
            params.put(shd[5], decoder(Objects.requireNonNull(mapper.get("person")), cypher.getCypher_dictionary()));
            params.put(shd[6], mapper.get("passenger"));
            params.put(shd[7], mapper.get("idcard"));
            SimpleDateFormat dbFormatter = new SimpleDateFormat("y-M-d", Locale.getDefault());
            Date dbDate = new Date((decoder(Objects.requireNonNull(mapper.get("birthdate")), cypher.getCypher_dictionary()) + 1596236400) * 1000L);
            params.put(shd[8], dbFormatter.format(dbDate));
            params.put(shd[9], decoder(Objects.requireNonNull(mapper.get("amount")), cypher.getCypher_dictionary()));
            params.put(shd[10], mapper.get("discount"));

            Date saleDate = new Date((decoder(Objects.requireNonNull(mapper.get("saledate")), cypher.getCypher_dictionary()) + 1596236400) * 1000L);
            params.put(shd[11], formatter.format(saleDate));
            params.put(shd[12], mapper.get("seller"));
            params.put(shd[13], decoder(Objects.requireNonNull(mapper.get("saleId")), cypher.getCypher_dictionary()));**/

            params.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            params.put("status", false);
        }

        Log.d("params", params.toString());
        return params;
    }


    /**
     * fxn that decrypt each words
     *
     * @param valueToDecode
     * @param dictionary
     * @return
     */
    private static long decoder(String valueToDecode, String dictionary) {
        int base = dictionary.length();
        int length = valueToDecode.length();
        long decoded = 0;
        Map<Character, Integer> flippedMap = array_flipper(dictionary);
        for (int i = 0; i < length; i++) {
            decoded += flippedMap.get(valueToDecode.toCharArray()[i]) * Math.pow(base, length - i - 1);
            Log.i("decoder", decoded + "");
        }

        return decoded;
    }

    /**
     * fxn that takes in dictionary and swap the key and value in the string to char
     * format
     *
     * @param dictionary
     * @return
     */
    private static Map<Character, Integer> array_flipper(String dictionary) {
        Map<Character, Integer> flipped = new HashMap<>();
        for (int i = 0; i < dictionary.length(); i++) {
            char key = dictionary.toCharArray()[i];
            flipped.put(key, i);
        }
        return flipped;
    }
}
