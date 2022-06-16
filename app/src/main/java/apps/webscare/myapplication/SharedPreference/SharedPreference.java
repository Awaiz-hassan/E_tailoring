package apps.webscare.myapplication.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import apps.webscare.myapplication.Model.CartModel;

public class SharedPreference {
    public static final String SHARED_PREF_NAME = "SHARED_PREFS_ETAILORING";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;


    public SharedPreference(Context context) {
        this.context = context;
    }

    public void setLoggedIn(boolean loggedIn) {

            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.putBoolean("IS_LOGGED_IN", loggedIn);
            editor.apply();
        }
    }




    public void setUser(String name,String phone, String email,String address, String Token){
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.putString("phone_number", phone);
            editor.putString("user_name", name);
            editor.putString("user_email", email);
            editor.putString("user_address", address);
            editor.putString("user_token",Token);
            editor.apply();
        }
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    private void set(String key, String value) {
        if (sharedPreferences != null) {
            sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public List<CartModel> getCart(String key) {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            Gson gson = new Gson();
            List<CartModel> cartList;
            String string = sharedPreferences.getString(key, null);
            Type type = new TypeToken<List<CartModel>>() {
            }.getType();
            cartList = gson.fromJson(string, type);
            return cartList;
        }
        return null;
    }


    public String getName(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name", null);

    }
    public String getUserToken(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_token", null);
    }

    public String getAddress(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_address", null);
    }

    public String getGender(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString("gender", null);
    }
    public String getEmail(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString("user_email", null);
    }

    public String getJoinedDate(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString("joined_date", null);
    }
    public boolean isLoggedIn(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean("IS_LOGGED_IN", false);
    }

    public String getPhone(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString("phone_number", null);
    }




    public void clearAllData(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }
}