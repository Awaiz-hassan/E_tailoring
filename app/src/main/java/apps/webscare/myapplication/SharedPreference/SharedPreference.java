package apps.webscare.myapplication.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String SHARED_PREF_NAME = "SHARED_PREFS_E_TAILORING";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;


    public SharedPreference(Context context) {
        this.context = context;
    }

    public void setLoggedIn(boolean loggedIn) {
        if (sharedPreferences != null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putBoolean("IS_LOGGED_IN", loggedIn);
            editor.apply();
        }
    }

    public void setGender(String gender){
        if (sharedPreferences != null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("gender", gender);
            editor.apply();
        }
    }


    public void setUser(String name,String phone, String email,String dateJoined,String gender){
        if (sharedPreferences != null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("phone_number", phone);
            editor.putString("user_name", name);
            editor.putString("user_email", email);
            editor.putString("joined_date", dateJoined);
            editor.putString("gender", gender);
            editor.apply();
        }
    }

    public String getName(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString("user_name", null);
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


    public void removeGender() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.remove("gender");
            editor.apply();
        }
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