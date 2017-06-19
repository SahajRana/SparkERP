package com.students.spark.androsoft.spark.drawer;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.students.spark.androsoft.spark.MainActivity;
import static com.students.spark.androsoft.spark.MainActivity.mPager;
import com.students.spark.androsoft.spark.R;
import com.students.spark.androsoft.spark.adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;



public class NavigationDrawerFragment extends Fragment implements RecyclerAdapter.ClickListener{

    private RecyclerView recyclerView;
    //private TextView textViewProfile;
    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private RecyclerAdapter adapter;

    private View containerView;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    public static String PhotoUrlFromActivity;
    public static String NameFromActivity;
    private Intent intent;
    private SharedPreferences prefs;
    private RelativeLayout mRelativeLayout;
    private NetworkInfo activeNetworkInfo;
    private Spanned content1;
    private Spanned content2;
    //private int selected_position=0;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer=prefs.getBoolean("mUserLearnedDrawer",false);//Boolean.valueOf(readToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
      //  textViewProfile=(TextView) view.findViewById(R.id.UserProileName);
        recyclerView = (RecyclerView) view.findViewById(R.id.drawerRecyclerList);
        mRelativeLayout=(RelativeLayout)view.findViewById(R.id.containerDrawerImage);
       // InformationReclyclerDrawer Profileee=new InformationReclyclerDrawer();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        MainActivity activity = (MainActivity) getActivity();
      //  PhotoUrlFromActivity = activity.ImageUrl();
      //  NameFromActivity=activity.UserNamee();
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    content1= Html.fromHtml("<b>" + "View Profile" + "</b>", Html.FROM_HTML_MODE_LEGACY);
                    content2= Html.fromHtml("<b>" + "Edit Profile" + "</b>", Html.FROM_HTML_MODE_LEGACY);
                } else {
                    content1=Html.fromHtml("<b>" + "View Profile" + "</b>");
                    content2=Html.fromHtml("<b>" + "Edit Profile" + "</b>");
                }
                final CharSequence[] items = {content1,  content2};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        String userChoosenTask;
                        if (item==0) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            String EmailPrefs = prefs.getString("EmailPref", "error");
                           // intent = new Intent(getActivity(), ProfileNewActivity.class);
                            intent.putExtra("EmailPrefGeneral", EmailPrefs);
                            startActivity(intent);
                        } else if (item==1) {
                            if (isNetworkAvailableBoolean()) {
                               // Intent myIntent = new Intent(getActivity(), EditProfileActivity.class);
                              ///  startActivity(myIntent);
                                prefs.edit().putBoolean("isActivityRunning", false).commit();
                                getActivity().finish();
                            }

                        }
                    }
                });
                builder.show();

            }
        });

        adapter=new RecyclerAdapter(getActivity(),getData());
        //Log.e("pavan", "data INFODRAWER "+getData());

        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
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




    public static List<InformationReclyclerDrawer> getData(){
        List<InformationReclyclerDrawer>data=new ArrayList<>();
        int[] icons={R.drawable.home_1,R.drawable.user_navi, R.drawable.academic, R.drawable.lace,R.drawable.settings_work_tool,R.drawable.like__1_};
        String[] titles={"Home","Profile","Academic","Events","Settings","About Us",};
        for(int i=0;i<titles.length&&i<icons.length;i++){
            InformationReclyclerDrawer current=new InformationReclyclerDrawer();
            current.title=titles[i];
            current.iconID=icons[i];
            data.add(current);
        }
        return data;
    }


    public void setUp(int fragmentId,DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar, R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!prefs.getBoolean("mUserLearnedDrawer", false)){
                    mUserLearnedDrawer=true;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    //prefs.getBoolean("mUserLearnedDrawer", mUserLearnedDrawer);
                    prefs.edit().putBoolean("mUserLearnedDrawer", true).commit();

                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

        };

        if(!prefs.getBoolean("mUserLearnedDrawer", false)){
            mDrawerLayout.openDrawer(containerView);
            prefs.edit().putBoolean("mUserLearnedDrawer", true).commit();
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
    public static void saveToPreferences(Context context,String preferenceName,String preferenceValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }
    public static String readToPreferences(Context context,String preferenceName,String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {


        if(position==0){
            mPager.setCurrentItem(position, true);
        }else if(position==1){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String EmailPrefs=prefs.getString("EmailPref", "error");
            //intent = new Intent(getActivity(), ProfileNewActivity.class);
            intent.putExtra("EmailPrefGeneral",EmailPrefs);
            startActivity(intent);
        }else if(position==2){
            mPager.setCurrentItem(1, true);
        }else if(position==3){
            mPager.setCurrentItem(5, true);
        }else if(position==4){
            Toast.makeText(getActivity(), "Music Coming Soon", Toast.LENGTH_LONG).show();
        }else if(position==5){
            Toast.makeText(getActivity(), "Store Coming Soon", Toast.LENGTH_LONG).show();
        }else if(position==6){
           // Intent intent=new Intent(getActivity(),SettingsActivity.class);
           // startActivity(intent);
            // Toast.makeText(getActivity(), "Settings Comming Soon", Toast.LENGTH_LONG).show();
        }else if(position==7){
            // intent=new Intent(getActivity(),AboutUsActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //  this.startActivity(intent);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
          //  ft.replace(R.id.mainFrame, new AboutUsFragment(), "about");
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_from_left);
            ft.addToBackStack(null);
            ft.commit();
        }

        mDrawerLayout.closeDrawers();



    }
}
