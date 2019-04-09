package digitalcreative.web.id.wbmobile_user.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03/04/2019.
 */

public class DataSplashScreen {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public DataSplashScreen() {
    }

    public DataSplashScreen(Context context) {
        this.context = context;
    }

    public void saveArrayListString(ArrayList<String> list, String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveArrayList(ArrayList<List> list, String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveString(String str, String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        editor.putString(key, str);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<List> getArrayList(String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<List>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<String> getArrayListString(String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public String getString(String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String value = prefs.getString(key, null);
        return value;
    }
}
