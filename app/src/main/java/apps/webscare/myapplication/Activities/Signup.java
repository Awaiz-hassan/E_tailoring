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

import apps.webscare.myapplication.Model.User;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;

public class Signup extends AppCompatActivity {

    ImageButton back;
    FirebaseAuth mAuth;
    Spinner selectGender;
    Button signUp;
    SharedPreference sharedPreference;
    EditText name,address,phone,email,password,otp;
    ConstraintLayout Loading;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference().child("Users");
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

        selectGender = findViewById(R.id.Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGender.setAdapter(adapter);

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
       CreateUserInDataBase();


    }

    void CreateUserInDataBase(){

        showLoading();
        HashMap<String , String> userMap = new HashMap<>();

        userMap.put("name" , name.getText().toString());
        userMap.put("email" , email.getText().toString());
        userMap.put("phone" , "+92"+phone.getText().toString());
        userMap.put("password" , password.getText().toString());
        userMap.put("gender" , selectGender.getSelectedItem().toString());
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        userMap.put("joined_on",currentDate);
        userMap.put("homeaddress" , address.getText().toString());



        Query query=db.getReference().child("Users").orderByChild("phone").equalTo("+92"+phone.getText().toString());
        query.addListenerForSingleValueEvent(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                Toast.makeText(Signup.this,"Phone number already exists!", Toast.LENGTH_SHORT).show();

                            }
                            hideLoading();
                        }
                        else{
                            myRef.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        hideLoading();
                                        Intent intent=new Intent(Signup.this, Login.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                    else {
                                        hideLoading();
                                        Toast.makeText(Signup.this,"Failed! Try again later", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }


    public static boolean isEmailValid(String email) {
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