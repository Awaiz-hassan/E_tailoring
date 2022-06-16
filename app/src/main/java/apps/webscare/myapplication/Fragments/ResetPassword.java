package apps.webscare.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import apps.webscare.myapplication.Activities.Login;
import apps.webscare.myapplication.Activities.MainActivity;
import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.Model.User;
import apps.webscare.myapplication.Model.resetPass.ResetPassModel;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.RetrofitClient.RetrofitClient;
import apps.webscare.myapplication.SharedPreference.SharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ResetPassword extends Fragment {

    EditText pass1,pass2;
    Button Save;
    ConstraintLayout loading;
    SharedPreference sharedPreference;
    public ResetPassword() {
        // Required empty public constructor
    }

    public static ResetPassword newInstance() {
        ResetPassword fragment = new ResetPassword();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        Constants.CurrentFrag="resetpassword";

        sharedPreference=new SharedPreference(getActivity());
        pass1=view.findViewById(R.id.pas1);
        pass2=view.findViewById(R.id.pass2);
        Save=view.findViewById(R.id.save);
        loading=view.findViewById(R.id.Loading);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass2.getText().toString().length()<5){
                    pass2.setError("Please choose a strong password");
                    return;
                }

                if(pass1.getText().toString().trim().isEmpty()){
                    pass1.setError("Enter old password");
                    return;
                }

                updatePassword(pass1.getText().toString().trim(),pass2.getText().toString().trim());
            }
        });
        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    public void updatePassword(String oldPass, String newPass){
        loading.setVisibility(View.VISIBLE);
        RetrofitClient.connect("https://etailoring.pk/store/api/user/password/");
        String base= sharedPreference.getUserToken();
        String authHeader = "Bearer " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<ResetPassModel>  resetPasswordCall=RetrofitClient.getInstance().getApi().resetPass(authHeader,oldPass,newPass,newPass);
        resetPasswordCall.enqueue(new Callback<ResetPassModel>() {
            @Override
            public void onResponse(Call<ResetPassModel> call, Response<ResetPassModel> response) {
                if(response.isSuccessful()){
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_SHORT).show();
                    Log.d("TAG1", "onFailure: "+response.message());

                }
                else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error Occurred! Logout and try again!", Toast.LENGTH_SHORT).show();
                    Log.d("TAG1", "onFailure: "+response.message());

                }
            }

            @Override
            public void onFailure(Call<ResetPassModel> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error Occurred! Logout and try again", Toast.LENGTH_SHORT).show();
                Log.d("TAG1", "onFailure: "+t.getMessage());
            }
        });

    }
}