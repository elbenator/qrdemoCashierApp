package com.kazimasum.qrdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class sessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public sessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences("session", 0);
        editor =sharedPreferences.edit();
        editor.apply();
    }

    //SET login method
    public void setLogin(boolean login){
        editor.putBoolean("key_login", login);
        editor.commit();
    }
    //GET Login
    public boolean getLogin(){
        return sharedPreferences.getBoolean("key_login",false);
    }
    //SET username
    public void setUsername(String username){
        editor.putString("key_username", username);
        editor.commit();
    }
    //GET username
    public String getUsername(){
        return sharedPreferences.getString("key_username","");
    }

    //============ result activity =============
    //SET tempOrderId
    public void setTempId(String username){
        editor.putString("key_TempId", username);
        editor.commit(); }
    //GET tempOrderId
    public String getTempId(){
        return sharedPreferences.getString("key_TempId","");
    }

    //username ng umorder
    public void setUsernameNgUmorder(String username2){
        editor.putString("key_orderer", username2);
        editor.commit(); }
    //GET tempOrderId
    public String getUsernameNgUmrder(){
        return sharedPreferences.getString("key_orderer","");
    }

    //SET location ng Order
    public void setLocationOrder(String location){
        editor.putString("key_location", location);
        editor.commit(); }
    //GET location
    public String getLocationOrder(){
        return sharedPreferences.getString("key_location","");
    }

    //SET location ng Order
    public void setUserIdUmorder(String userIdNg){
        editor.putString("key_userIdNg", userIdNg);
        editor.commit(); }
    //GET location
    public String getUserIdUmorder(){
        return sharedPreferences.getString("key_userIdNg","");
    }

    //SET thisShit
    public void setThisShit(String userIdNg){
        editor.putString("key_thisShit", userIdNg);
        editor.commit(); }
    //GET thisshit
    public String getThisShit(){
        return sharedPreferences.getString("key_thisShit","");
    }

    //SET this orderId
    public void setThisOrderId(String userIdNg){
        editor.putString("key_OrderThis", userIdNg);
        editor.commit(); }
    //GET this order id
    public String getThisOrderId(){
        return sharedPreferences.getString("key_OrderThis","");
    }

    //SET array
    public void setThisArray(List<String> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        editor.putString("key_array", jsonString);
        editor.commit(); }
    //GET array
    public List<String> getThisArray(){
        String jsonString = sharedPreferences.getString("key_array","");
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> list = gson.fromJson(jsonString, type);
        return list;
    }

    //SET arrayItemIncre
    public void setThisArrayIncre(List<String> list2){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list2);
        editor.putString("key_arrayIncre", jsonString);
        editor.commit(); }
    //GET arrayItem Incre
    public List<String> getThisArrayIncre(){
        String jsonString = sharedPreferences.getString("key_arrayIncre","");
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> list = gson.fromJson(jsonString, type);
        return list;
    }

    //SET step
    public void setStep(int eto){
        editor.putInt("last_step", eto);
        editor.commit();
    }
    //GET lastStep
    public int getStep(){
        return sharedPreferences.getInt("last_step",0);
    }

    //SET mobileno
    public void setMobileno(String mobileno){
        editor.putString("key_mobileno", mobileno);
        editor.commit();
    }
    //GET mobileno
    public String getMobileno(){
        return sharedPreferences.getString("key_mobileno", "");
    }
    //SET Fullname
    public void setFullname(String username){
        editor.putString("key_fullname", username);
        editor.commit();
    }
    //GET Fullname
    public String getFullname(){
        return sharedPreferences.getString("key_fullname","");
    }
    //SET Orders sa QUEUE
    public  void  setIlanorder(String ilanorder){
        editor.putString("key_ilan", ilanorder);
        editor.commit();
    }

    public String getIlanorder(){
        return sharedPreferences.getString("key_ilan", "");
    }

    //SET random
    public  void  setRandom(int ilanorder){
        editor.putInt("key_rand", ilanorder); editor.commit(); }
    //GET randomNum
    public Integer getRandom(){
        return sharedPreferences.getInt("key_rand" ,0);
    }

}

