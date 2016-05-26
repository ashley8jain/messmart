package com.ashleyjain.messmart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.adapter.PagerAdapter;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessListTabLayout extends Fragment {

    String days,days2;
    JSONArray day,day2;
    String particularday;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        days = getArguments().getString("days");
        days2 = getArguments().getString("days2");
        System.out.println("days: "+days);
        try {
            day = new JSONArray(days);
            day2 = new JSONArray(days2);
            particularday = day2.getString(0);
//            for(int i=0;i<day2.length();i++){
//                if(i==0){
//                    keydays.put("Today",day2.getString(i));
//                }
//                keydays.put(day.getString(i),day2.getString(i));
//            }
//            System.out.println(keydays.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mess_list_tab_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Lunch"));
        tabLayout.addTab(tabLayout.newTab().setText("Dinner"));

        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading.....", true);
        String url = StartActivity.host+"index.php/ajaxactions";

        StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        //response JSON from url
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject dataobject = jsonResponse.getJSONObject("data");
                            final String lord = dataobject.getString("lord");
                            viewPager.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    viewPager.setCurrentItem(lord=="l"?0:1);
                                }
                            }, 100);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("debug", "posting param");
                Map<String, String> params = new HashMap<String, String>();

                // the POST parameters:
                params.put("action", "menulist");
                System.out.println(params);
                return params;
            }
        };

        // add it to the RequestQueue
        StartActivity.get().getRequestQueue().add(postRequest);


        // Spinner element
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    particularday = day2.getString(position);
                    final PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(),particularday);
                    viewPager.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for(int i= 0;i<day.length();i++) {
            try {
                if(i==0)
                    categories.add("Today");
                else
                    categories.add(day.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);




        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });



    }
}
