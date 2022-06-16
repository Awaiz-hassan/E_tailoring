package apps.webscare.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apps.webscare.myapplication.Model.RegisterUser;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.RetrofitClient.RetrofitClient;
import apps.webscare.myapplication.SharedPreference.SharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    TextView signup;
    Button loginButton;
    EditText email, password;
    ImageView showHidePass;
    ConstraintLayout Loading;

    SharedPreference sharedPreference;
    Call<RegisterUser> loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference(getApplicationContext());
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signUp);
        loginButton = findViewById(R.id.button);
        email = findViewById(R.id.editText2);
        password = findViewById(R.id.editTextPassword);
        showHidePass = findViewById(R.id.pass_visible);
        Loading = findViewById(R.id.Loading);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
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

    void Login() {
        if (!isEmailValid(email.getText().toString().trim())) {
            email.setError("Enter valid email!");
            email.requestFocus();
            return;
        }
        if (password.getText().length() < 6) {
            password.setError("Invalid Password");
            password.requestFocus();
            return;
        }
        Validation(email.getText().toString().trim(), password.getText().toString().trim());

    }

    void Validation(String email, String password) {
        showLoading();
        RetrofitClient.connect("https://etailoring.pk/store/api/user/");
        loginUser = RetrofitClient.getInstance().getApi().loginUser(email, password);

        loginUser.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sharedPreference.setUser(response.body().getData().getUser().getFullName(), response.body().getData().getUser().getPhone(), response.body().getData().getUser().getEmail(), response.body().getData().getUser().getAddress(), response.body().getData().getToken());
                        sharedPreference.setLoggedIn(true);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(Login.this, "User loggedIn Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Login.this, "Server Error! Try again later!", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(Login.this, "Server Error! Try again later!", Toast.LENGTH_SHORT).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                if (t.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 25 path $.data")) {
                    Toast.makeText(Login.this, "Invalid Email/Password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Server Error! Try again later!", Toast.LENGTH_SHORT).show();

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

    public void ShowHidePass(View view) {
        if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_visibility_24);
            //Show Password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setSelection(password.getText().length());

        } else {
            ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);
            //Hide Password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setSelection(password.getText().length());

        }
    }

    void showLoading() {
        Loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
    }

    void hideLoading() {
        Loading.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

}