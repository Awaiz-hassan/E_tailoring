package apps.webscare.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apps.webscare.myapplication.Model.RegisterUser;
import apps.webscare.myapplication.Model.User;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.RetrofitClient.RetrofitClient;
import apps.webscare.myapplication.SharedPreference.SharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signup extends AppCompatActivity {

    ImageButton back;
    FirebaseAuth mAuth;
    Button signUp;
    SharedPreference sharedPreference;
    EditText name,address,phone,email,password,otp;
    ConstraintLayout Loading;
    Call<RegisterUser> registerUserCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        back=findViewById(R.id.back);
        mAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.editTextName);
        Loading=findViewById(R.id.Loading);
        address=findViewById(R.id.editTextAddress);
        phone=findViewById(R.id.editText2);
        password=findViewById(R.id.editTextPassword);
        email=findViewById(R.id.editTextEmail);
        signUp=findViewById(R.id.button);
        sharedPreference=new SharedPreference(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });

    }

    void SignUp(){
        if(name.getText().toString().length()<3){
            name.setError("Enter valid name");
            return;
        }
        if(address.getText().toString().length()<5){
            address.setError("Enter valid address");
            return;
        }
        if(phone.getText().toString().length()!=10){
            phone.setError("Enter valid phone number");
            return;
        }
        if(!isEmailValid(email.getText().toString())){
            email.setError("Enter valid email");
            return;
        }
        if(password.getText().toString().length()<6){
            password.setError("Please choose a strong password");
            return;
        }
       CreateUserInDataBase(name.getText().toString().trim(),email.getText().toString().trim(),"+92"+phone.getText().toString().trim(),password.getText().toString().trim(),address.getText().toString().trim() );


    }

    void CreateUserInDataBase(String name, String email, String phoneNumber , String password, String address){

        showLoading();
        RetrofitClient.connect("https://etailoring.pk/store/api/user/");
        registerUserCall=RetrofitClient.getInstance().getApi().registerUser(name,email,phoneNumber,address,password);
        registerUserCall.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                if(response.isSuccessful()){
                    Intent intent=new Intent(Signup.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(Signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Signup.this, "Server Error! Try again later!", Toast.LENGTH_SHORT).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                if(t.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 25 path $.data")){
                    Toast.makeText(Signup.this, "Email already taken!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Signup.this, "Server Error! Try again later!", Toast.LENGTH_SHORT).show();

                }
                Log.d("TAG1", "onFailure: ");
                hideLoading();

            }
        });


    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    void showLoading(){
        Loading.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.GONE);
    }
    void hideLoading(){
        Loading.setVisibility(View.GONE);
        signUp.setVisibility(View.VISIBLE);
    }




}