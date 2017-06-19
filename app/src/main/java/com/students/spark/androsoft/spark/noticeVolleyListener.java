package com.students.spark.androsoft.spark;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.students.spark.androsoft.spark.volleyClass.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.students.spark.androsoft.spark.volleyClass.AppController.TAG;

/**
 * Created by Sahaj on 04-06-2017.
 */

public class noticeVolleyListener {
    private static String tag_json_obj="json_obj_req";
    private NoticeCallbacks callbacks;

    public static NoticeListener addNoticeListener(final Context context, final NoticeCallbacks callbacks){
        final NoticeListener listener = new NoticeListener(callbacks);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                constants.NoticeVolleyURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("SahajLOG", response.toString()+" ");
                        try {
                            Log.e("SahajLOG", response.getJSONArray("es_notice").length()+" ");

                            for (int i=0;i<response.getJSONArray("es_notice").length();i++) {
                                NoticeRecycler_ItemForAdapter itemForAdapter = new NoticeRecycler_ItemForAdapter();
                                //Log.e("SahajLOG", response.getJSONArray("es_notice").getJSONObject(i).get("Date")+" ");
                                itemForAdapter.setContent(response.getJSONArray("es_notice").getJSONObject(i).get("Message").toString());
                                itemForAdapter.setUserPic("https://cdn.dribbble.com/users/78433/screenshots/3492405/c_logo_2_1x.png");
                                itemForAdapter.setHashname(response.getJSONArray("es_notice").getJSONObject(i).get("Title").toString());
                                itemForAdapter.setTime(response.getJSONArray("es_notice").getJSONObject(i).get("Date").toString());
                                //itemForAdapter.setPicURL(NoticePrivate.get("picurl"));
                                //itemForAdapter.setPicOffline(SnapshotKey);
                                //itemForAdapter.setTimestamp(SnapshotKey);
                                //itemForAdapter.setPdfUrl(NoticePrivate.get("pdfUrl"));
                                //itemForAdapter.setDocUrl(NoticePrivate.get("docUrl"));
                                itemForAdapter.setContext(context);
                                Log.e("SahajLOG", "notice_item " +itemForAdapter.getContent()+itemForAdapter.getHashname()+ itemForAdapter.getUserPic()+itemForAdapter.getTime());
                                callbacks.onNoticeAdded(itemForAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SahajLOG", "Error: " + error.getMessage());
                // hide the progress dialog
                //pDialog.hide();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        return listener;
    }




    public static void stop(NoticeListener listener,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }




    public static class NoticeListener {

        NoticeListener(NoticeCallbacks callbacks){

        }
        /*public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            //      Log.e("SahajLOG6", "fuyvukyvvkvk");
            Map<String, String> NoticePrivate = (HashMap<String, String>)dataSnapshot.getValue();
            //    Log.e("SahajLOG6", "notice datasnap "+dataSnapshot.getValue());

            if(NoticePrivate!=null){
                String SnapshotKey=dataSnapshot.getKey();
                NoticeRecycler_ItemForAdapter itemForAdapter=new NoticeRecycler_ItemForAdapter();
                // NoticeModelClass noticePrivateModel=new NoticeModelClass();
                itemForAdapter.setContent(NoticePrivate.get("content"));
                itemForAdapter.setUserPic(NoticePrivate.get("picUserProfileUrl"));
                itemForAdapter.setHashname(NoticePrivate.get("userHashname"));
                itemForAdapter.setTime(NoticePrivate.get("time"));
                itemForAdapter.setPicURL(NoticePrivate.get("picurl"));
                itemForAdapter.setPicOffline(SnapshotKey);
                itemForAdapter.setTimestamp(SnapshotKey);
                itemForAdapter.setPdfUrl(NoticePrivate.get("pdfUrl"));
                itemForAdapter.setDocUrl(NoticePrivate.get("docUrl"));
                itemForAdapter.setContext(context);
                prefs = PreferenceManager.getDefaultSharedPreferences(context);
                int a=0;
                a=prefs.getInt(SnapshotKey+"notice",a);
                prefs.edit().putInt(SnapshotKey+"notice",a+1).commit();
                if (callbacks != null) {

                }
            }

        }*/
    }


    public interface NoticeCallbacks{
        void onNoticeAdded(NoticeRecycler_ItemForAdapter noticePrivate);

    }

}
