package com.students.spark.androsoft.spark;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.fastadapter.adapters.FastItemAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeFragment extends Fragment implements noticeVolleyListener.NoticeCallbacks{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    public static LinearLayout mToolbarContainer2;

    private noticeVolleyListener.NoticeListener mListenerForPri;

    private static Intent intent;
    private Activity activity;
    //private SwipeMenuListView mListView;                                                           <---
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    //private NoticeAdapter adapter;

    private static final String[] headers = new String[]{"Saved", "New","New-Public"};
    private FastItemAdapter<NoticeRecycler_ItemForAdapter> fastAdapter;
    private int p=0;

    private Context context;
    private ViewGroup footer,footer_th;
    private TextView percen_in_num,percen_in_num_th;
    private static int alreadyExpanded=0;
    private static int alreadyContracted=0;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int firstVisibleItem=0;
    private int totalItemCount=0;
    private int visibleItemCount=0,iniHeightQuote;

    public NoticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticeFragment newInstance(String param1, String param2) {
        NoticeFragment fragment = new NoticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notice, container, false);

        mRecyclerView=(RecyclerView)view.findViewById(R.id.notice_recyclerview);
        fastAdapter = new FastItemAdapter<>();
        fastAdapter.withSavedInstanceState(savedInstanceState);
        context = view.getContext();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(fastAdapter);


        //mListView.setAdapter(adapter);
        footer = (ViewGroup)view.findViewById(R.id.attendance_view);
        footer_th=(ViewGroup)view.findViewById(R.id.thought_view);
        percen_in_num=(TextView)view.findViewById(R.id.percentage_in_num);
        percen_in_num.setAlpha(0.0f);
        percen_in_num_th=(TextView)view.findViewById(R.id.thought_text);
        percen_in_num_th.setAlpha(1.0f);
        activity=getActivity();


        footer_th.post(new Runnable(){
            public void run(){
                iniHeightQuote=footer_th.getHeight();
                Log.e("SahajLOG6", "notice called "+iniHeightQuote);
            }
        });

         Log.e("SahajLOG6", "notice called "+iniHeightQuote);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("SahajLOG", "Mrecy scrollX" + scrollX+" scrolly ->"+scrollY+ "  oldX>"+oldScrollX+" oldy>"+oldScrollY);
            }
        });
        else{
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Log.e("SahajLOG", "recy State" +newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Log.e("SahajLOG", "recy dX" + dx+" dy ->"+dy);
                    visibleItemCount = mRecyclerView.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();//4
                    firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                    Log.e("SahajLOG7", "on scroll " + (totalItemCount - visibleItemCount) + "  total visible item  "+totalItemCount+"   Visible items  " +visibleItemCount+"  firstVisibleItem  "+firstVisibleItem);
                    if (firstVisibleItem == 0) {

                        // check if we reached the top or bottom of the list
                        View v = mRecyclerView.getChildAt(0);
                        int offset = (v == null) ? 0 : v.getTop();
                        Log.e("SahajLOG2", "Inside first visi 1 offset>"+offset);
                        if (offset <7 && offset>=0) {
                            Log.e("SahajLOG2", "Inside first visi 2");
                            // reached the top: visible header and footer
                            if (alreadyExpanded>0){

                                setViewStatus(footer,percen_in_num,0.0f,1.0f,370,120,true);
                                setViewStatus(footer_th,percen_in_num_th,1.0f,0.0f,iniHeightQuote,0,true);
                                alreadyExpanded=0;
                                alreadyContracted=0;
                            }
                            Log.e("SahajLOG7", "top reached");
                        }
                    } else if (totalItemCount - visibleItemCount > firstVisibleItem-1){
                        // on scrolling
                        Log.e("SahajLOG2", "Inside Scrolling" +alreadyContracted);
                        if (alreadyContracted==0 || alreadyContracted==1) {
                            setViewStatus(footer,percen_in_num,0.0f,1.0f, 370,120, false);
                            setViewStatus(footer_th,percen_in_num_th,1.0f,0.0f,iniHeightQuote,0, false);
                            alreadyContracted++;
                        }
                        //Log.e("SahajLOG7", "on scroll" + (totalItemCount - visibleItemCount) + "  total visible item  "+totalItemCount+"   Visible items  " +visibleItemCount+"  firstVisibleItem  "+firstVisibleItem);
                    }else if (totalItemCount - firstVisibleItem>2) {
                        Log.e("SahajLOG2", "Inside Empty");
                        View v = mRecyclerView.getChildAt(totalItemCount - 1);
                        int offset = (v == null) ? 0 : v.getTop();
                        if (offset == 0) {
                            // reached the bottom: visible header and footer
                            Log.e("SahajLOG7", "bottom reached!");
                        }
                    }

                }
            });
        }
        // mListView.setOnScrollListener(onScrollListener());                                               <---
        p=0;


        mListenerForPri = noticeVolleyListener.addNoticeListener(context, this);



        return view;
    }

    private void setViewStatus(final ViewGroup vg1,final TextView txtV,float textViewAlphaInitial,float textViewAlphaFinal, int targetHeight, int newHeightRed,boolean expand) {
        DropDownAnim dropDownAnim=new DropDownAnim(
                vg1,
                targetHeight,
                newHeightRed,
                expand
        );
        dropDownAnim.setDuration(500);
        vg1.startAnimation(dropDownAnim);
        alreadyExpanded++;
        if (expand){
            txtV.setAlpha(textViewAlphaFinal);
            txtV.animate().alpha(textViewAlphaInitial).setDuration(450);
        }else{
            txtV.setAlpha(textViewAlphaInitial);
            txtV.animate().alpha(textViewAlphaFinal).setDuration(500);
        }

    }
    public class DropDownAnim extends Animation {
        private final int targetHeight,newHeightRed;
        private final View view;
        private final boolean down;

        public DropDownAnim(View view, int targetHeight,int newHeightRed, boolean down) {
            this.view = view;
            this.targetHeight = targetHeight;
            this.newHeightRed = newHeightRed;
            this.down = down;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight;
            if (down) {
                newHeight = (int) (targetHeight * interpolatedTime);
                Log.e("SahajLOG7", "new height EXPAND" +newHeight +" target Heg. "+targetHeight +" interpolatedTime "+(int)interpolatedTime +" "+interpolatedTime);
            } else {
                newHeight = (int) (targetHeight * (1 - interpolatedTime));
                Log.e("SahajLOG7", "new height Collapse" +newHeight +" target Heg. "+targetHeight +" interpolatedTime "+(int)interpolatedTime +" "+interpolatedTime);
            }
            if (newHeight>newHeightRed){
                view.getLayoutParams().height = newHeight;
            }else
                view.getLayoutParams().height = newHeightRed;

            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onNoticeAdded(final NoticeRecycler_ItemForAdapter noticePrivate) {
        Log.e("SahajLOG", "notice-> "+noticePrivate);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    CountDownTimer countDownTimer = new CountDownTimer(700, 700) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            fastAdapter.add(noticePrivate);
                            mRecyclerView.scrollToPosition(0);
                        }
                    };
                    countDownTimer.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
