package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class Measurements extends Fragment {



    RecyclerView rw_measurements;
    DatabaseReference Measurements;
    Spinner selectGender;
    ConstraintLayout Loading;
    SharedPreference sharedPreference;
    apps.webscare.myapplication.Model.Measurements measurements;
    Button saveMeasurements;
    ImageButton back;
    EditText chest,waist,seat,bicep,shirtlength,shirtwidth,sleevelength,cuffcircumference,collarsize;
    private FirebaseDatabase db=FirebaseDatabase.getInstance();

    public Measurements() {
        // Required empty public constructor
    }

    public static apps.webscare.myapplication.Fragments.Measurements newInstance(String Phone, String Measurements) {
        apps.webscare.myapplication.Fragments.Measurements fragment = new Measurements();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_measurements, container, false);
        Constants.CurrentFrag="measurements";

        Loading=view.findViewById(R.id.Loading);
        chest=view.findViewById(R.id.value);
        waist=view.findViewById(R.id.waistValue);
        seat=view.findViewById(R.id.valueSeat);
        bicep=view.findViewById(R.id.value1);
        sleevelength=view.findViewById(R.id.SleeveLengthValue);
        shirtwidth=view.findViewById(R.id.widthValue);
        shirtlength=view.findViewById(R.id.valueShirtLength);
        cuffcircumference=view.findViewById(R.id.valueCuffCircumference);
        collarsize=view.findViewById(R.id.valuecollarSize);
        sharedPreference=new SharedPreference(getActivity());
        selectGender = view.findViewById(R.id.Spinner);
        measurements=new apps.webscare.myapplication.Model.Measurements();
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getActivity(),
                R.array.Gender, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGender.setAdapter(adapterSpinner);
        saveMeasurements=view.findViewById(R.id.button4);
        selectGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMeasurements();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        saveMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMeasurements();
            }
        });

        back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    void getMeasurements(){
        Loading.setVisibility(View.VISIBLE);
        Query query=db.getReference().child("Measurements").child(sharedPreference.getPhone()).orderByChild("Gender").equalTo(selectGender.getSelectedItem().toString());
        query.addListenerForSingleValueEvent(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            measurements=null;
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                               apps.webscare.myapplication.Model.Measurements measurements=dataSnapshot.getValue(apps.webscare.myapplication.Model.Measurements.class);
                                Loading.setVisibility(View.GONE);
                                chest.setText(measurements.getChest());
                                waist.setText(measurements.getWaist());
                                seat.setText(measurements.getSeat());
                                bicep.setText(measurements.getBicep());
                                shirtlength.setText(measurements.getShirtLength());
                                shirtwidth.setText(measurements.getShirtWidth());
                                sleevelength.setText(measurements.getSleeveLength());
                                cuffcircumference.setText(measurements.getCuffCircumference());
                                collarsize.setText(measurements.getCollarSize());
                            }
                        }
                        else {
                            Loading.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Loading.setVisibility(View.GONE);

                    }
                }
        );

    }


    public void SaveMeasurements(){

        HashMap<String , String> userMap = new HashMap<>();
        userMap.put("Chest", chest.getText().toString());
        userMap.put("Gender", selectGender.getSelectedItem().toString());
        userMap.put("Waist" , waist.getText().toString());
        userMap.put("Seat" , seat.getText().toString());
        userMap.put("Bicep" , bicep.getText().toString());
        userMap.put("ShirtLength" , shirtlength.getText().toString());
        userMap.put("ShirtWidth",shirtwidth.getText().toString());
        userMap.put("SleeveLength" , sleevelength.getText().toString());
        userMap.put("CuffCircumference" ,cuffcircumference.getText().toString());
        userMap.put("CollarSize" , collarsize.getText().toString());

        DatabaseReference MeasurementsRef=db.getReference().child("Measurements").child(sharedPreference.getPhone());
        Query query=db.getReference().child("Measurements").child(sharedPreference.getPhone()).orderByChild("Gender").equalTo(selectGender.getSelectedItem().toString());
        query.addListenerForSingleValueEvent(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                String key= dataSnapshot.getKey();
                                MeasurementsRef.child(key).removeValue();
                                MeasurementsRef.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getActivity(),"Measurements saved",Toast.LENGTH_SHORT).show();
                                        }



                                    }
                                });
                            }
                        }
                        else{
                            MeasurementsRef.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Measurements saved",Toast.LENGTH_SHORT).show();
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

}