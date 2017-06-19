package com.students.spark.androsoft.spark;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.rana.sahaj.myyu.Misc.TextView;
//import com.rana.sahaj.myyu.authentication.constants;
//import com.rana.sahaj.myyu.profile.ProfileInformation;
//import com.rana.sahaj.myyu.profile.ToTal_ProfileModelRealmClass;
//import com.rana.sahaj.myyu.profileNew.EditProfileActivity;
//import com.rana.sahaj.myyu.profileNew.ProfileNewActivity;
import com.stephentuso.welcome.WelcomePage;
//import com.stephentuso.welcome.ui.DoneButton;
import com.stephentuso.welcome.WelcomeFinisher;
//import com.stephentuso.welcome.WelcomeScreenPage;
import com.stephentuso.welcome.WelcomeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeProfileFillFragment extends Fragment implements WelcomePage.OnChangeListener{

    View upperText1;
    View upperText2;
    View upperText3;
    View informationLayout;
    EditText username,hashname;
    FloatingActionButton doneFab;

    private Spinner citySpinner,schoolSpinner;
    private TextInputLayout inputLayoutUserName, inputLayoutHashname;
    private View ErrorSpinner,ErrorYear;
    private static String citySelected;
    private static int cityPosition;
    private static String schoolSelected;
    private static int schoolPosition;

    private static List<String> hashnamesList=new LinkedList<>(Collections.<String>emptySet());

    String[] itemsCity = new String[]{"-Select-", "Meerut", "Ghaziabad","New Delhi"};
    String[] itemsSchool = new String[]{"-Select-", "DPS", "MPS","Deewan"};
    ArrayList<String> years = new ArrayList<String>();
    private static String EditedEmail;
    private static String hashnameString;
    private static String usernameString;

    private static String userNAMEfromFirebase,hashNAMEfromFirebase=null;
    private NetworkInfo activeNetworkInfo;

    public WelcomeProfileFillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_welcome_profile_fill, container, false);
        // Inflate the layout for this fragment
        upperText1=(View)rootView.findViewById(R.id.upperText1);
        upperText2=(View)rootView.findViewById(R.id.upperText2);
        upperText3=(View)rootView.findViewById(R.id.upperText3);

        doneFab=(FloatingActionButton)rootView.findViewById(R.id.doneFabbb);
        citySpinner=(Spinner)rootView.findViewById(R.id.spinner_campus);
        schoolSpinner=(Spinner)rootView.findViewById(R.id.spinner_course);
        informationLayout=(View)rootView.findViewById(R.id.information_layout);
        ErrorSpinner=(View)rootView.findViewById(R.id.error_field_not_selected);
        ErrorYear=(View)rootView.findViewById(R.id.error_field_not_selected_YEAR);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        EditedEmail=prefs.getString("EmailPref", EditedEmail);

        final ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemsCity);
        final ArrayAdapter<String> adapterSchool = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemsSchool);

        citySpinner.setAdapter(adapterCity);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citySelected = (String) parent.getItemAtPosition(position);
                cityPosition = position;
                schoolSpinner.setAdapter(adapterSchool);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schoolSelected = (String) parent.getItemAtPosition(position);
                schoolPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailableBoolean()) {
                    ErrorYear.setVisibility(View.GONE);
                    ErrorSpinner.setVisibility(View.GONE);
                    if (cityPosition == 0 || schoolPosition == 0) {
                        final CharSequence[] items = {"Got It!"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Error: Please Fill Form Completely before continuing");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (items[item].equals("Got It!")) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.show();
                    }
                }

            }
        });

        return rootView;
    }
    private boolean isNetworkAvailableBoolean() {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // Update UI here when network is available.

                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (!(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting())) {
                    Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
    }

   /* private void addOnFirebase(){
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(constants.FIREBASE_URL+"rootssahaj/authGplus/users/students/"+EditedEmail+"/profile");
        DatabaseReference mFirebaseRefPool = FirebaseDatabase.getInstance().getReferenceFromUrl(constants.FIREBASE_URL+"rootssahaj/authGplus/usersPool");

        Map<String, Object> statusChange = new HashMap<String, Object>();
        statusChange.put("userNAME",usernameString);
        statusChange.put("hashname",hashnameString);
        statusChange.put("campus",campusSelected);
        statusChange.put("course",courseSelected);
        statusChange.put("branch",branchSelected);
        statusChange.put("year",yearSelected1);
        statusChange.put("section",sectionSelected);


        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        profileModelRealmClass = new ToTal_ProfileModelRealmClass();
        profileModelRealmClass.setUserEmailKey(EditedEmail);
        profileModelRealmClass.setUserEmail(EditedEmail);
        profileModelRealmClass.setUserNAME(usernameString);
        profileModelRealmClass.setHashname(hashnameString);
        profileModelRealmClass.setIsTeacher("true");
        realm.copyToRealmOrUpdate(profileModelRealmClass);
        realm.commitTransaction();
        realm.close();

        Map<String, Object> HashnameAdd = new HashMap<String, Object>();
        HashnameAdd.put(hashnameString,EditedEmail);

        mFirebaseRef2.updateChildren(HashnameAdd);
        mFirebaseRef.updateChildren(statusChange);
        mFirebaseRefPool.child(EditedEmail).setValue(campusSelected);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.edit().putString("campus",campusSelected).commit();
        prefs.edit().putString("course",courseSelected).commit();
        prefs.edit().putString("branch",branchSelected).commit();
        prefs.edit().putString("year",yearSelected1).commit();
        prefs.edit().putString("section",sectionSelected).commit();
    }*/
    @Override
    public void onWelcomeScreenPageScrolled(int pageIndex, float offset, int offsetPixels) {
        if (Build.VERSION.SDK_INT >= 11 && upperText1 != null) {
            upperText1.setAlpha(1-Math.abs(offset));
            upperText2.setAlpha(1-Math.abs(offset));
            upperText3.setAlpha(1-Math.abs(offset));
        }
        if (informationLayout != null)//ParallaxEffect
            WelcomeUtils.applyParallaxEffect(informationLayout, false, offsetPixels, 0.3f, 0.2f);
    }

    @Override
    public void onWelcomeScreenPageSelected(int pageIndex, int selectedPageIndex) {

    }

    @Override
    public void onWelcomeScreenPageScrollStateChanged(int pageIndex, int state) {

    }

}
