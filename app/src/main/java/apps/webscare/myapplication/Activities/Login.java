package apps.webscare.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import apps.webscare.myapplication.Model.User;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;

public class Login extends AppCompatActivity {


    TextView signup;
    Button loginButton;
    EditText phoneNumber,password;
    ImageView showHidePass;
    ConstraintLayout Loading;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference(this);

        if(sharedPreference.isLoggedIn()){
            Intent intent=new Intent(Login.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference().child("Users");
        setContentView(R.layout.activity_login);
        signup=findViewById(R.id.signUp);
        loginButton=findViewById(R.id.button);
        phoneNumber=findViewById(R.id.editText2);
        password=findViewById(R.id.editTextPassword);
        showHidePass=findViewById(R.id.pass_visible);
        Loading=findViewById(R.id.Loading);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        showHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHidePass(view);
            }
        });
    }

    void Login(){
        if(phoneNumber.getText().toString().length()!=10){
            phoneNumber.setError("Enter valid phone number");
            return;
        }
        if(password.getText().length()<6){
            password.setError("Invalid Password");
            return;
        }
        Validation();

    }

    void Validation(){
        showLoading();
        Query query=db.getReference().child("Users").orderByChild("phone").equalTo("+92"+phoneNumber.getText().toString());
        query.addListenerForSingleValueEvent(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                User user=dataSnapshot.getValue(User.class);
                                if(user.getPassword().equals(password.getText().toString())){
                                    hideLoading();
                                    sharedPreference.setLoggedIn(true);
                                    sharedPreference.setUser(user.getName(),user.getPhone(),user.getEmail(),user.getJoined_on(),user.getGender());
                                    Intent intent=new Intent(Login.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                                else{
                                    hideLoading();
                                    password.setError("Invalid password!");
                                    password.requestFocus();
                                }

                            }
                        }
                        else{
                            hideLoading();
                           phoneNumber.setError("Phone number does't exist in database!");
                           phoneNumber.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        hideLoading();
                    }
                }
        );
    }


    public void ShowHidePass(View view) {
        if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);
            //Show Password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setSelection(password.getText().length());

        }
        else{
            ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);
            //Hide Password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setSelection(password.getText().length());

        }
    }

    void showLoading(){
        Loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
    }
    void hideLoading(){
        Loading.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

}