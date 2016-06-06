package com.ashleyjain.messmart.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.StartActivity;
import com.ashleyjain.messmart.function.KeyboardDown;
import com.ashleyjain.messmart.function.StringRequestCookies;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class createAccount extends Fragment {

    TextView alreadyhave;
    EditText nm,add,chPass;
    Button createAcc;
    String phone,otp,name,address,choosePass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = getArguments().getString("PHONE");
        otp = getArguments().getString("OTP");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.fragment_create_account);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyboardDown.keyboardDown();
            }

        });
        nm = (EditText) view.findViewById(R.id.name);
        add = (EditText) view.findViewById(R.id.address);
        chPass = (EditText) view.findViewById(R.id.choosepassword);

        createAcc = (Button) view.findViewById(R.id.createb);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nm.getText().toString();
                address = add.getText().toString();
                choosePass = chPass.getText().toString();

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Signing up.....", true);
                String url = StartActivity.host + "index.php/ajaxactions";

                StringRequestCookies postRequest = new StringRequestCookies(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                //response JSON from url
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    Integer ec = jsonResponse.getInt("ec");
                                    if (ec == 1) {
                                        Toast.makeText(getActivity(), "Sent!", Toast.LENGTH_LONG).show();
                                        Intent re = new Intent(getContext(),StartActivity.class);
                                        startActivity(re);
                                        getActivity().finish();
                                    }
                                    else{
                                        Toast.makeText(getActivity(),StartActivity.errorcode.getString(""+ec), Toast.LENGTH_LONG).show();
                                    }
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
                        params.put("phone", phone);
                        params.put("otp",otp);
                        params.put("action", "signup");
                        params.put("name", name);
                        params.put("address", address);
                        params.put("password", choosePass);

                        System.out.println(params);
                        return params;
                    }
                };

                // add it to the RequestQueue
                StartActivity.get().getRequestQueue().add(postRequest);
                KeyboardDown.keyboardDown();
            }
        });

        alreadyhave = (TextView) view.findViewById(R.id.already_conf);
        alreadyhave.setText(Html.fromHtml("Already have an account? Login" + "</font><br><br>"));

//        alreadyhave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().popBackStack();
//            }
//        });



        alreadyhave.setMovementMethod(LinkMovementMethod.getInstance());
        alreadyhave.setText(alreadyhave.getText(), TextView.BufferType.SPANNABLE);
        Spannable alreadyhavespannable = (Spannable)alreadyhave.getText();
        ClickableSpan myAlreadyHaveSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
            }
            @Override
            public void updateDrawState(TextPaint ds) {// override updateDrawState
                ds.setUnderlineText(false); // set to false to remove underline
            }
        };
        alreadyhavespannable.setSpan(myAlreadyHaveSpan, 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        alreadyhavespannable.setSpan(new ForegroundColorSpan(Color.parseColor("#039be5")),25, 30,0);
        /**/
    }
}
