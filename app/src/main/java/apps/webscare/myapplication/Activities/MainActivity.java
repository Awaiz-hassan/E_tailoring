package apps.webscare.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import apps.webscare.myapplication.Fragments.Gallery;
import apps.webscare.myapplication.Fragments.Home;
import apps.webscare.myapplication.Fragments.Profile;
import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.R;

public class MainActivity extends AppCompatActivity {
    ImageView home_unchecked,profile_unchecked,grid_unchecked;
    CardView home_checked,profile_checked,grid_checked;
    private long backPressedTime = 0;    // used by onBackPressed()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_unchecked=findViewById(R.id.imageView);
        profile_unchecked=findViewById(R.id.imageView2);
        grid_unchecked=findViewById(R.id.grid);
        home_checked=findViewById(R.id.cardView);
        profile_checked=findViewById(R.id.profileChecked);
        grid_checked=findViewById(R.id.cardView2);

        setHome_checked();
        home_unchecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setHome_checked();
            }
        });
        profile_unchecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile_checked();
            }
        });
        grid_unchecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGrid_checked();
            }
        });

    }


    void setHome_checked(){
        home_checked.setVisibility(View.VISIBLE);
        profile_checked.setVisibility(View.GONE);
        grid_checked.setVisibility(View.GONE);
        home_unchecked.setVisibility(View.GONE);
        grid_unchecked.setVisibility(View.VISIBLE);
        profile_unchecked.setVisibility(View.VISIBLE);

        Animation translateAnim= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_up);
        home_checked.startAnimation(translateAnim);

        loadFragment(Home.newInstance());

    }

    void setProfile_checked(){
        home_checked.setVisibility(View.GONE);
        profile_checked.setVisibility(View.VISIBLE);
        grid_checked.setVisibility(View.GONE);
        profile_unchecked.setVisibility(View.GONE);
        grid_unchecked.setVisibility(View.VISIBLE);
        home_unchecked.setVisibility(View.VISIBLE);
        Animation translateAnim= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_up);
        profile_checked.startAnimation(translateAnim);
        loadFragment(Profile.newInstance());

    }

    void setGrid_checked(){
        home_checked.setVisibility(View.GONE);
        profile_checked.setVisibility(View.GONE);
        grid_checked.setVisibility(View.VISIBLE);
        grid_unchecked.setVisibility(View.GONE);
        home_unchecked.setVisibility(View.VISIBLE);
        profile_unchecked.setVisibility(View.VISIBLE);
        Animation translateAnim= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_up);
        grid_checked.startAnimation(translateAnim);
        loadFragment(Gallery.newInstance());

    }

    void loadFragment(Fragment fragment){
        androidx.fragment.app.FragmentManager fm=getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.fragHolder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0& !Constants.CurrentFrag.equals("home")) {
            androidx.fragment.app.FragmentManager fm=getSupportFragmentManager();
            androidx.fragment.app.FragmentTransaction fragmentTransaction=fm.beginTransaction();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.replace(R.id.fragHolder, new Home());
            fragmentTransaction.commit();

        }else if(backStackEntryCount == 0& Constants.CurrentFrag.equals("home")){
            long t = System.currentTimeMillis();
            if (t - backPressedTime > 2000) {
                backPressedTime = t;
                Toast.makeText(this, "Press back again to exit",
                        Toast.LENGTH_SHORT).show();
            } else {

                super.onBackPressed();
            }
        }


        else {
            super.onBackPressed();
        }

    }
}