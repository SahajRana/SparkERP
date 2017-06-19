package com.students.spark.androsoft.spark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.github.meness.timelinepostcontainer.TimelinePostContainer;
import io.github.meness.timelinepostcontainer.Type;

/**
 * Created by Sahaj on 03-06-2017.
 */

public class NoticeRecycler_ItemForAdapter extends AbstractItem<NoticeRecycler_ItemForAdapter, NoticeRecycler_ItemForAdapter.ViewHolder> {
    private static final ViewHolderFactory<? extends ViewHolder> FACTORY = new ItemFactory();
   // public static DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(constants.FIREBASE_URL+"rootssahaj/authGplus/hashnamePool");

    public String header;

    private String mContent;
    private String mTime;
    private Context mContext;
    //private String mPicURL;
    //private String mPicOffline;
    //private String mTimestamp;
    private String mUserPic;
    private String mHashname;
    //private String mPdfUrl;
    //private String mDocUrl;
    //private String mMetaType;


    public String getUserPic() {
        return mUserPic;
    }

    public void setUserPic(String userPic) {
        mUserPic = userPic;
    }

    public String getHashname() {
        return mHashname;
    }

    public void setHashname(String hashname) {
        mHashname = hashname;
    }

    public NoticeRecycler_ItemForAdapter withHeader(String header) {
        this.header = header;
        return this;
    }




    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }



    @Override
    public int getType() {
        return R.id.fastadapter_NoticeRecycler_ItemForAdapter_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.notice_item ;
    }



    public interface OnItemClickListener {

    }



    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String Text=getContent();

        if(Text.length()>37){
            Text=Text.substring(0,37);
            Text=Text.concat("...");
        }else{
            Text=Text;
        }


        viewHolder.mHashname.setText("@"+getHashname());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewHolder.mContent.setText(Html.fromHtml(Text, Html.FROM_HTML_MODE_LEGACY));
            //content.setText(Html.fromHtml(contentString, Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.mContent.setText(Html.fromHtml(Text));
            //content.setText(Html.fromHtml(contentString));
        }
        viewHolder.mTimelineLinearLayout.setVisibility(View.GONE);
       // //final String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
       // final File file = new File(baseDir+"/roots/downloads", getPicOffline()+".jpg" );
        //if (getPicURL()!=null){
          //  viewHolder.mTimelineLinearLayout.setVisibility(View.VISIBLE);
            //viewHolder.mTimelinePostContainer.setImagePath(getPicURL());
            //viewHolder.mTimelinePostContainer.build(Type.IMAGE);
        //}else{
         //   viewHolder.mTimelineLinearLayout.setVisibility(View.GONE);
        //}
        //viewHolder.mImageContent.setVisibility(View.GONE);




        Picasso.with(getContext()).load(R.drawable.n_alpha).transform(new CircleTransform()).into(viewHolder.mImageUserPic);
        //viewHolder.mtime.setReferenceTime(Long.parseLong(getTime()));
        /*int a=0;
        final String key= getTimestamp();
        a=prefs.getInt(key+"notice",a);
        if(a==0){
            viewHolder.mRedLinearView.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.mRedLinearView.setVisibility(View.VISIBLE);
        }*/
        /*viewHolder.mHashname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query mQuery = mFirebaseRef.orderByKey().equalTo(getHashname());
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();
                        if(data!=null) {
                            Intent myIntent = new Intent(getContext(), ProfileNewActivity.class);
                            myIntent.putExtra("EmailPrefGeneral", data.get(getHashname()));
                            getContext().startActivity(myIntent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        viewHolder.mImageUserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query mQuery = mFirebaseRef.orderByKey().equalTo(getHashname());
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();
                        Intent myIntent = new Intent(getContext(), ProfileNewActivity.class);
                        myIntent.putExtra("EmailPrefGeneral", data.get(getHashname()));
                        getContext().startActivity(myIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        viewHolder.mLinearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoticeOnItemSelectedActivity.class);

                // String UserEmail = prefs.getString("UserEmail", EmailPref);
                intent.putExtra("heading_notice", getHashname());
                intent.putExtra("content_notice", getContent());
                intent.putExtra("image_notice_url", getPicURL());
                intent.putExtra("image_notice_offline", getPicOffline());
                intent.putExtra("image_user_notice", getUserPic());
                intent.putExtra("notice_timestamp", getTimestamp());
                intent.putExtra("notice_pdfurl", getPdfUrl());
                intent.putExtra("notice_docurl", getDocUrl());
                intent.putExtra("notice_time", getTime());
                intent.putExtra("notice_metatype", getMetaType());
                // intent.putExtra("UserEmailIntent", UserEmail);
                getContext().startActivity(intent);
            }
        });
        viewHolder.mJugad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoticeOnItemSelectedActivity.class);

                // String UserEmail = prefs.getString("UserEmail", EmailPref);
                intent.putExtra("heading_notice", getHashname());
                intent.putExtra("content_notice", getContent());
                intent.putExtra("image_notice_url", getPicURL());
                intent.putExtra("image_notice_offline", getPicOffline());
                intent.putExtra("image_user_notice", getUserPic());
                intent.putExtra("notice_timestamp", getTimestamp());
                intent.putExtra("notice_pdfurl", getPdfUrl());
                intent.putExtra("notice_docurl", getDocUrl());
                intent.putExtra("notice_time", getTime());
                intent.putExtra("notice_metatype", getMetaType());
                getContext().startActivity(intent);
            }
        });
        */

    }

    protected static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }
    @Override
    public ViewHolderFactory<? extends ViewHolder> getFactory() {
        return FACTORY;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       // ImageView mImageContent;
        //ImageView mImageContent_other;
       // ImageView mImageContent_other_doc;
        private TimelinePostContainer mTimelinePostContainer;
        private FrameLayout mTimelineLinearLayout;
        public LinearLayout mJugad;

        ImageView mImageUserPic;
        TextView mHashname;
        TextView mContent;
        RelativeTimeTextView mtime;
        View mRedLinearView;
        View mLinearView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTimelinePostContainer=(TimelinePostContainer)itemView.findViewById(R.id.notice_image_view_timelinePostContainer);
            mTimelineLinearLayout=(FrameLayout)itemView.findViewById(R.id.notice_image_view_frameree);
            mJugad=(LinearLayout)itemView.findViewById(R.id.notice_image_view_jugad);

            mImageUserPic=(ImageView)itemView.findViewById(R.id.notice_userPic);
           // mImageContent=(ImageView)itemView.findViewById(R.id.notice_image_viewER);
       //     mImageContent_other=(ImageView)itemView.findViewById(R.id.notice_image_view_other);
       //     mImageContent_other_doc=(ImageView)itemView.findViewById(R.id.notice_image_view_other_doc);
            mHashname=(TextView)itemView.findViewById(R.id.notice_hashnamee);
            mContent=(TextView)itemView.findViewById(R.id.notice_content_textview);
            mtime=(RelativeTimeTextView)itemView.findViewById(R.id.notice_time);
            mRedLinearView=(View)itemView.findViewById(R.id.notice_new_view);
            mLinearView=(View)itemView.findViewById(R.id.notice_itemm);

        }
    }
}

