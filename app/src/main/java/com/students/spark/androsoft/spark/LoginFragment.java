package com.students.spark.androsoft.spark;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.Manifest;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.greenfrvr.rubberloader.interpolator.PulseInterpolator;




/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    // private EditText mPasswordView;
    // private EditText mEmailView;
    private View mLoginForm;
    private View mProgressSpinner;
    private boolean mLoggingIn;
    private OnLoginListener mListener;
    private Button mGoogleSignInButton;
    View contextcustom;
    private static NetworkInfo activeNetworkInfo;
    public static CoordinatorLayout coordinatorLayout;
    protected RubberLoaderView loaderView1;
    private View rootView;
    private FrameLayout mFinalLayout;
    private in.workarounds.typography.TextView mFinalText,mVersionTextView,mForTextView;
    private EditText mLoginUsername,mLoginPassword;
    private ImageView mFinalImageView;
    private SharedPreferences prefs;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            rootView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            getActivity().finish();
        }
        contextcustom=rootView;
        //ButterKnife.bind(this, rootView);
        // mEmailView = (EditText) rootView.findViewById(R.id.email);
        //mPasswordView = (EditText) rootView.findViewById(R.id.password);
        mLoginForm = rootView.findViewById(R.id.login_form);

        // View loginButton = rootView.findViewById(R.id.email_sign_in_button);
        mGoogleSignInButton = (Button) rootView.findViewById(R.id.login_button);
        mFinalLayout = (FrameLayout) rootView.findViewById(R.id.finaledition);
        mFinalText = (in.workarounds.typography.TextView) rootView.findViewById(R.id.finaleditionText);
        mVersionTextView = (in.workarounds.typography.TextView) rootView.findViewById(R.id.finaleditionVersion);
        mFinalImageView = (ImageView) rootView.findViewById(R.id.finaleditionImage);
        mForTextView = (in.workarounds.typography.TextView) rootView.findViewById(R.id.forText);
        loaderView1=(RubberLoaderView)rootView.findViewById(R.id.loader1);
        mLoginPassword=(EditText)rootView.findViewById(R.id.login_password);
        mLoginUsername=(EditText)rootView.findViewById(R.id.login_username);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("FinalCalled",false)){
            FinalAnimation();
        }



        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id
                .loginId);
        Log.e("SahajLOG", "coordinator layout  " + coordinatorLayout);
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mLoginUsername.getText().toString().equals("")) {
                    if (!mLoginPassword.getText().toString().equals("")) {
                        if (isNetworkAvailable()) {
                            new Permissive.Request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .whenPermissionsGranted(new PermissionsGrantedListener() {
                                        @Override
                                        public void onPermissionsGranted(String[] permissions) throws SecurityException {
                                            // given permissions are granted
                                            new Permissive.Request(Manifest.permission.READ_EXTERNAL_STORAGE)
                                                    .whenPermissionsGranted(new PermissionsGrantedListener() {
                                                        @Override
                                                        public void onPermissionsGranted(String[] permissions) throws SecurityException {
                                                            new Permissive.Request(Manifest.permission.ACCESS_NETWORK_STATE)
                                                                    .whenPermissionsGranted(new PermissionsGrantedListener() {
                                                                        @Override
                                                                        public void onPermissionsGranted(String[] permissions) throws SecurityException {
                                                                            new Permissive.Request(Manifest.permission.GET_ACCOUNTS)
                                                                                    .whenPermissionsGranted(new PermissionsGrantedListener() {
                                                                                        @Override
                                                                                        public void onPermissionsGranted(String[] permissions) throws SecurityException {
                                                                                            new Permissive.Request(Manifest.permission.INTERNET)
                                                                                                    .whenPermissionsGranted(new PermissionsGrantedListener() {
                                                                                                        @Override
                                                                                                        public void onPermissionsGranted(String[] permissions) throws SecurityException {
                                                                                                            new Permissive.Request(Manifest.permission.VIBRATE)
                                                                                                                    .whenPermissionsGranted(new PermissionsGrantedListener() {
                                                                                                                        @Override
                                                                                                                        public void onPermissionsGranted(String[] permissions) throws SecurityException {
                                                                                                                            enableDisableView(coordinatorLayout, true);

                                                                                                                            loginWithGoogle(mLoginUsername.getText().toString(), mLoginPassword.getText().toString());

                                                                                                                        }
                                                                                                                    }).execute(getActivity());

                                                                                                        }
                                                                                                    }).execute(getActivity());

                                                                                        }
                                                                                    }).execute(getActivity());

                                                                        }
                                                                    }).execute(getActivity());

                                                        }
                                                    }).execute(getActivity());

                                        }
                                    })
                                    .whenPermissionsRefused(new PermissionsRefusedListener() {
                                        @Override
                                        public void onPermissionsRefused(String[] permissions) {
                                            // given permissions are refused
                                        }
                                    })
                                    .execute(getActivity());


                        }
                    }else
                        isValid();
                }else
                    isValid();

            }
        });

        return rootView;
    }
    private void FinalAnimation(){
        // prefs.edit().putBoolean("glassesDialogCalledd",true).commit();
        mFinalLayout.setVisibility(View.VISIBLE);
        mFinalImageView.setVisibility(View.GONE);
        mFinalText.setVisibility(View.VISIBLE);
        mVersionTextView.setVisibility(View.GONE);
        mFinalText.setAlpha(0.0f);

        mFinalText.animate()
                .alpha(1.0f)
                .setDuration(600)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        CountDownTimer countDownTimer = new CountDownTimer(900, 900) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                mFinalText.animate()
                                        .alpha(0.0f)
                                        .setDuration(600)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                mFinalText.setVisibility(View.GONE);
                                                mFinalImageView.setVisibility(View.VISIBLE);
                                                mFinalImageView.setAlpha(0.0f);

                                                mFinalImageView.animate()
                                                        .alpha(1.0f)
                                                        .setDuration(800)
                                                        .setListener(new AnimatorListenerAdapter() {
                                                            @Override
                                                            public void onAnimationEnd(Animator animation) {
                                                                super.onAnimationEnd(animation);
                                                                CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
                                                                    @Override
                                                                    public void onTick(long millisUntilFinished) {

                                                                    }

                                                                    @Override
                                                                    public void onFinish() {
                                                                        mFinalImageView.animate()
                                                                                .alpha(0.0f)
                                                                                .setDuration(600)
                                                                                .setListener(new AnimatorListenerAdapter() {
                                                                                    @Override
                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                        super.onAnimationEnd(animation);
                                                                                        mFinalImageView.setVisibility(View.GONE);
                                                                                        mVersionTextView.setVisibility(View.VISIBLE);
                                                                                        mVersionTextView.setAlpha(0.0f);

                                                                                        mVersionTextView.animate()
                                                                                                .alpha(1.0f)
                                                                                                .setDuration(600)
                                                                                                .setListener(new AnimatorListenerAdapter() {
                                                                                                    @Override
                                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                                        super.onAnimationEnd(animation);
                                                                                                        CountDownTimer countDownTimer = new CountDownTimer(900, 900) {
                                                                                                            @Override
                                                                                                            public void onTick(long millisUntilFinished) {

                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onFinish() {
                                                                                                                mVersionTextView.animate()
                                                                                                                        .alpha(0.0f)
                                                                                                                        .setDuration(600)
                                                                                                                        .setListener(new AnimatorListenerAdapter() {
                                                                                                                            @Override
                                                                                                                            public void onAnimationEnd(Animator animation) {
                                                                                                                                super.onAnimationEnd(animation);
                                                                                                                                mVersionTextView.setVisibility(View.GONE);

                                                                                                                                mForTextView.setVisibility(View.VISIBLE);
                                                                                                                                mForTextView.setAlpha(0.0f);
                                                                                                                                mForTextView.animate()
                                                                                                                                        .alpha(1.0f)
                                                                                                                                        .setDuration(600)
                                                                                                                                        .setListener(new AnimatorListenerAdapter() {
                                                                                                                                            @Override
                                                                                                                                            public void onAnimationEnd(Animator animation) {
                                                                                                                                                super.onAnimationEnd(animation);
                                                                                                                                                CountDownTimer countDownTimer = new CountDownTimer(900, 900) {
                                                                                                                                                    @Override
                                                                                                                                                    public void onTick(long millisUntilFinished) {

                                                                                                                                                    }

                                                                                                                                                    @Override
                                                                                                                                                    public void onFinish() {
                                                                                                                                                        mForTextView.animate()
                                                                                                                                                                .alpha(0.0f)
                                                                                                                                                                .setDuration(600)
                                                                                                                                                                .setListener(new AnimatorListenerAdapter() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                                                                                                        super.onAnimationEnd(animation);
                                                                                                                                                                        mForTextView.setVisibility(View.GONE);
                                                                                                                                                                        mFinalLayout.setAlpha(1.0f);
                                                                                                                                                                        mFinalLayout.animate()
                                                                                                                                                                                .alpha(0.0f)
                                                                                                                                                                                .setDuration(600)
                                                                                                                                                                                .setListener(new AnimatorListenerAdapter() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                                                                                                                        super.onAnimationEnd(animation);
                                                                                                                                                                                        mFinalLayout.setVisibility(View.GONE);
                                                                                                                                                                                        prefs.edit().putBoolean("FinalCalled", true).commit();
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                    }
                                                                                                                                                                });
                                                                                                                                                    }
                                                                                                                                                };
                                                                                                                                                countDownTimer.start();

                                                                                                                                            }
                                                                                                                                        });
                                                                                                                            }
                                                                                                                        });
                                                                                                            }
                                                                                                        };
                                                                                                        countDownTimer.start();

                                                                                                    }
                                                                                                });


                                                                                    }
                                                                                });
                                                                    }
                                                                };
                                                                countDownTimer.start();

                                                            }
                                                        });


                                            }
                                        });
                            }
                        };
                        countDownTimer.start();

                    }
                });



    }

    private void loginWithGoogle(String user,String pass) {
        if (mLoggingIn) {
            getActivity().finish();
            return;
        }
        // mEmailView.setError(null);
        //  mPasswordView.setError(null);


        showProgress(true);
        mLoggingIn = true;
        mListener.signInWithGplus(user, pass);
        hideKeyboard();


    }
    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
    private boolean isNetworkAvailable() {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // Update UI here when network is available.

                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (!(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting())) {

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isNetworkAvailable();
                                }
                            });
                    View sbView = snackbar.getView();
                    snackbar.setActionTextColor(Color.WHITE);
                    sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.snackbar_back_color2));
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    //textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                }
            }
        });

        return (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
    }
    private void isValid() {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // Update UI here when network is available.
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Please Fill Credentials", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isNetworkAvailable();
                                }
                            });
                    View sbView = snackbar.getView();
                    snackbar.setActionTextColor(Color.WHITE);
                    sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.snackbar_back_color2));
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    //textView.setTextColor(Color.YELLOW);
                    snackbar.show();
            }
        });
    }




    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
    }

    public void onLoginError(String message) {
        new MaterialDialog.Builder(getActivity())
                .title(getActivity().getString(R.string.login_error))
                .content(message)
                .positiveText(android.R.string.ok)
                .positiveColorRes(R.color.colorAccent)
                .show();
        showProgress(false);
        mLoggingIn = false;
    }

    private void showProgress(boolean show) {
        //ButterKnife.bind(this,contextcustom);
        if (show){loaderView1.setVisibility(View.VISIBLE);
            loaderView1.startLoading();
            loaderView1.setInterpolator(new PulseInterpolator());}
        //mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        mGoogleSignInButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }



    public interface OnLoginListener {

        void signInWithGplus(String username,String password);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoginListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
