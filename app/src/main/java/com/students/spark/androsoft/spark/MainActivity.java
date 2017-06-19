package com.students.spark.androsoft.spark;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.stephentuso.welcome.WelcomeHelper;
import com.students.spark.androsoft.spark.drawer.NavigationDrawerFragment;
import com.students.spark.androsoft.spark.tabs.SlidingTabsLayout;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginListener,NoticeFragment.OnFragmentInteractionListener,HomeworkFragment.OnFragmentInteractionListener,TimetableFragment.OnFragmentInteractionListener{

    private static FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Toolbar mToolbar;
    public static ViewPager mPager;
    private MyPagerAdapter myPagerAdapter;
    private View decorView;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private Picasso mPicasso;
    private SharedPreferences prefs;
    private String EmailPref;
    private ImageView imageView_profile_image;
    private TextView textView_name;
    private SlidingTabsLayout mTabs;
    private WelcomeHelper welcomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=(Toolbar)findViewById(R.id.app_bar);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        setSupportActionBar(mToolbar);
        setTitle("");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mPicasso= Picasso.with(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        imageView_profile_image = (ImageView) findViewById(R.id.ProfileDP);
        textView_name = (TextView) findViewById(R.id.UserProileName);

        welcomeScreen = new WelcomeHelper(this, MyWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);
        //welcomeScreen.forceShow();

       // //LayoutInflater inflator = LayoutInflater.from(this);
       // View v = inflator.inflate(R.layout.appbar_tittle_view, null);
       // ((TextView)v.findViewById(R.id.titleAppBar)).setText(this.getTitle());
      //  getSupportActionBar().setCustomView(v);

      //  v.setOnClickListener(new View.OnClickListener() {
        //    @Override
       //     public void onClick(View view) {
      //          Toast.makeText(MainActivity.this, "Checking..", Toast.LENGTH_SHORT).show();
                //checkUpdate();
      //      }
      //  });

        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_frag);
        drawerFragment.setUp(R.id.nav_frag, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .main_content);
        decorView=this.getWindow().getDecorView();
        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        mPager=(ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(myPagerAdapter);
        mTabs=(SlidingTabsLayout)findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        // mTabs.setBackgroundResource(R.drawable.blursvgleaves_crop);
        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTabs.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        mTabs.setViewPager(mPager);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.e("SahajLOG", "onAuthStateChanged:signed_in:" + user.getUid());
                    prefs.edit().putString("UserEmailPref", user.getUid());
                    prefs.edit().putString("UserEmailId",user.getEmail());
                    if (user.getDisplayName()!=null)
                    prefs.edit().putString("UserName",user.getDisplayName());
                    else {
                        EmailPref=user.getEmail().substring(0, user.getEmail().length() - 10);
                        EmailPref=EmailPref.replaceAll("[.]","_");
                        EmailPref=EmailPref.replaceAll("@", "_");
                        EmailPref=EmailPref.replaceAll("#", "_");
                        EmailPref=EmailPref.replaceAll("\\[", "_");
                        EmailPref=EmailPref.replaceAll("]", "_");
                        EmailPref=EmailPref.replace("$","_");

                        prefs.edit().putString("UserName", EmailPref);

                    }


                } else {
                    // User is signed out
                    Log.e("SahajLOG", "onAuthStateChanged:signed_out");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.mainFrame, new LoginFragment(), "login");
                    ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_from_left);
                    ft.commit();
                }
                textView_name.setText(""+prefs.getString("UserName","NewUser"));
                mPicasso.load(R.drawable.user_login).placeholder(R.drawable.user_login).transform(new CircleTransform()).into(imageView_profile_image);
            }
        };
        textView_name.setText(""+prefs.getString("UserName","NewUser"));
        mPicasso.load(R.drawable.user_login).placeholder(R.drawable.user_login).transform(new CircleTransform()).into(imageView_profile_image);

    }


    @Override
    public void onResume() {
        super.onResume();
        textView_name.setText(""+prefs.getString("UserName","NewUser"));
        mPicasso.load(R.drawable.user_login).placeholder(R.drawable.user_login).transform(new CircleTransform()).into(imageView_profile_image);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if( outState!=null){
            welcomeScreen.onSaveInstanceState(outState);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (intent!=null)
            if(requestCode != RESULT_CANCELED) {
                if (requestCode == WelcomeHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
                    // The key of the welcome screen is in the Intent
                    String welcomeKey = intent.getStringExtra(MyWelcomeActivity.WELCOME_SCREEN_KEY);

                    if (responseCode == RESULT_OK) {
                        // Code here will run if the welcome screen was completed
                    } else {
                        finish();
                        // Code here will run if the welcome screen was canceled
                        // In most cases you'll want to call finish() here
                    }

                }
            }

    }

    @Override
    public void signInWithGplus(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("SahajLOG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.e("SahajLOG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed. Please enter correct details.",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                            //getSupportFragmentManager().popBackStack();
                                   // remove(getSupportFragmentManager().findFragmentById(R.id.loginId)).commit();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class MyPagerAdapter  extends FragmentStatePagerAdapter {
        private  final String[] tabs = new String[]{
                "Home","Notice","Timetable","Homework"
        };
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
        Fragment fragment;


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }



        @Override
        public Fragment getItem(int position) {
            fragment = null;
            switch (position) {
                case 0:
                    appBarLayout.setExpanded(true, true);
                    fragment = HomeFragment.newInstance("ssasa","sssaaa");
                    break;
                case 1:
                    appBarLayout.setExpanded(true, true);
                    fragment = NoticeFragment.newInstance("ll","dsd");
                    break;
                case 2:
                    appBarLayout.setExpanded(true, true);
                    fragment = TimetableFragment.newInstance("sss","sss");
                    break;
                case 3:
                    appBarLayout.setExpanded(true, true);
                    fragment =HomeworkFragment.newInstance("ssas","sasa");
                    break;
            }
            return fragment;
        }



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
