package com.ashleyjain.messmart.Fragment.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.Object.MessObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.adapter.messObjectAdapter;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessList extends ListFragment {

    String lord,pd;
    TextView emp;

    private int[] messDishId,messId,t_avai,m_avai;
    private String[] messTitle,messLord;
    private String[] messDescription;
    private int[] messPrice,messBook;
    private boolean[] messIsVeg;
    private String[] messPic,messProfPic;
    private String[] messName,messAddress,messTime,messDatetime;

    private List<MessObject> messObjectList;
    messObjectAdapter adapter;

    public MessList(){
        this.lord = null;
        this.pd = null;
    }

    @SuppressLint("ValidFragment")
    public MessList(String lord,String pd){
        this.lord = lord;
        this.pd = pd;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emp = (TextView) view.findViewById(android.R.id.empty);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, StartActivity.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        //response JSON from url
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject dataobject = jsonResponse.getJSONObject("data");
                            emp.setText(dataobject.getString("isbookingclosed"));
                            emp.setTypeface(StartActivity.font);
                            JSONArray foodarray = dataobject.getJSONArray("foodlist");
                            System.out.println(foodarray);
                            Integer len = foodarray.length();
                            messDishId=new int[len];
                            messLord=new String[len];
                            messId = new int[len];
                            messTitle = new String[len];
                            messDescription = new String[len];
                            messPrice = new int[len];
                            messProfPic = new String[len];
                            messIsVeg = new boolean[len];
                            messPic = new String[len];
                            messName = new String[len];
                            messAddress = new String[len];
                            messTime = new String[len];
                            messDatetime = new String[len];
                            messBook = new int[len];
                            t_avai = new int[len];
                            m_avai = new int[len];
                            for(int i=0;i<len;i++){
                                JSONObject fooditem = foodarray.getJSONObject(i);
                                messDishId[i]=fooditem.getInt("dishid");
                                messLord[i]=fooditem.getString("lord");
                                messId[i] = fooditem.getInt("mid");
                                messTitle[i] = fooditem.getString("title");
                                messProfPic[i] = fooditem.getString("profilepic_small");
                                messDescription[i] = fooditem.getString("descp");
                                messPrice[i] = fooditem.getInt("price");
                                messIsVeg[i] = fooditem.getString("isveg").equals("n")?false:true;
                                messPic[i] = fooditem.getString("pic");
                                messName[i] = fooditem.getString("name");
                                messAddress[i] = fooditem.getString("address");
                                messTime[i] = fooditem.getString("timing");
                                messDatetime[i]=fooditem.getString("datetime");
                                messBook[i]=fooditem.getInt("isbooked");
                                t_avai[i]=fooditem.getInt("t_avai");
                                m_avai[i]=fooditem.getInt("m_avai");
                            }

                            //dialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            //dialog.dismiss();
                        }
                        messObjectList = new ArrayList<MessObject>();
                        for(int i = 0;i<messId.length;i++){
                            MessObject items = new MessObject(messDishId[i],messLord[i],messId[i],messTitle[i],messDescription[i],messPrice[i],messIsVeg[i],messPic[i],messName[i],messAddress[i],messTime[i],messDatetime[i],messBook[i],messProfPic[i],t_avai[i],m_avai[i]);
                            messObjectList.add(items);
                        }
                        System.out.println(messObjectList.size());
                        adapter = new messObjectAdapter(getActivity(), messObjectList);
                        setListAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("debug", "posting param");
                Map<String, String> params = new HashMap<String, String>();

                // the POST parameters:
                params.put("datetime",pd);
                params.put("lord", lord);
                params.put("action", "menulist");
                System.out.println(params);
                return params;
            }
        };
        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mess_list, container, false);

    }


}
