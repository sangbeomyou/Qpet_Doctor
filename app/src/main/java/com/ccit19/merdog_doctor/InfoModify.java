package com.ccit19.merdog_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ccit19.merdog_doctor.custom_dialog.CustomAnimationDialog;
import com.ccit19.merdog_doctor.variable.MyGlobals;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.widget.EditText;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoModify extends AppCompatActivity {
    private CustomAnimationDialog customAnimationDialog;
    private EditText cert_num, s_doctorPass, s_doctorPassVeri, s_doctorPhoneModify, certNum;
    private TextView textView20, textView17, Passcheck, PassVericheck, doctorphone, certnumcheck, phonecheck;
    private Button cert_send, certnum_check, button_Modify, numbercheck, passmodify;
    String doctor_num;
    String cert_number;
    private static final int MILLISINFUTURE = 180 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    int check = 0;
    int check2 = 0;
    int check3 = 0;
    int check4 =0;
    private int count = 180;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_info_modify);
        certNum = findViewById(R.id.certNum);
        passmodify = findViewById(R.id.passmodify);
        s_doctorPass = findViewById(R.id.s_doctorPass);
        s_doctorPassVeri = findViewById(R.id.s_doctorPassVeri);
        s_doctorPhoneModify = findViewById(R.id.s_doctorPhoneModify);
        doctor_num = SaveSharedPreference.getdoctornum(getApplication().getApplicationContext());
        textView20 = findViewById(R.id.textView20);
        cert_num = findViewById(R.id.cert_num);
        textView17 = findViewById(R.id.textView17);
        numbercheck = findViewById(R.id.numbercheck);
        Passcheck = findViewById(R.id.Passcheck);
        PassVericheck = findViewById(R.id.PassVericheck);
        PassVericheck = findViewById(R.id.PassVericheck);
        doctorphone = findViewById(R.id.doctorphone);
        certnumcheck = findViewById(R.id.certnumcheck);
        phonecheck = findViewById(R.id.phonecheck);
        cert_send = findViewById(R.id.cert_send);
        certnum_check = findViewById(R.id.certnum_check);
        button_Modify = (Button) findViewById(R.id.button_Modify);
        customAnimationDialog = new CustomAnimationDialog(InfoModify.this);
        super.onCreate(savedInstanceState);
        cert_send.setVisibility(View.GONE);
        certnum_check.setEnabled(false);
        String u = MyGlobals.getInstance().getData();

        String url = u + "/doctorapp/mypage_load";
        /* Create request */
        Map<String, String> params = new HashMap<String, String>();
        params.put("doctor_id", doctor_num);
        JsonObjectRequest inForm = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean success = false;
                        try {
                            success = response.getBoolean("result");
                            if (success == true) {
                                if(response.getString("doctor_id")=="null"){
                                    Toast.makeText(getApplicationContext(), "????????? ????????? ????????? ?????????.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    textView20.setText(response.getString("doctor_id"));
                                }
                                textView17.setText(response.getString("doctor_name"));
                                doctorphone.setText(response.getString("doctor_phone"));
                            } else {
                                Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance(getApplicationContext()).addToRequestQueue(inForm);


        //?????????
        s_doctorPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{5,15}$";
                Matcher matcher = Pattern.compile(pwPattern).matcher(s_doctorPass.getText());

                pwPattern = "(.)\\1\\1\\1";
                Matcher matcher2 = Pattern.compile(pwPattern).matcher(s_doctorPass.getText());

                if (!matcher.matches()) {
                    Passcheck.setText("??????, ??????, ???????????? ?????? 5~15????????? ??????????????????.");
                    check4=0;
                } else if (matcher2.find()) {
                    Passcheck.setText("??????????????? 4????????? ???????????? ????????????.");
                    check4=0;
                } else if (s_doctorPass.getText().toString().contains(" ")) {
                    Passcheck.setText("????????? ????????? ???????????????.");
                    check4=0;
                } else {
                    Passcheck.setText("");
                    check4=1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (s_doctorPass.getText().toString().equals(s_doctorPassVeri.getText().toString()) && !s_doctorPassVeri.getText().toString().isEmpty()) {
                    PassVericheck.setText("??????????????? ???????????????.");
                    PassVericheck.setTextColor(Color.parseColor("#5CAB7D"));
                    check3 = 1;
                } else {
                    PassVericheck.setText("??????????????? ???????????? ????????????.");
                    PassVericheck.setTextColor(Color.parseColor("#E53A40"));
                    check3 = 0;
                }
            }
        });

        s_doctorPassVeri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (s_doctorPass.getText().toString().equals(s_doctorPassVeri.getText().toString()) && !s_doctorPassVeri.getText().toString().isEmpty()) {
                    PassVericheck.setText("??????????????? ???????????????.");
                    PassVericheck.setTextColor(Color.parseColor("#5CAB7D"));
                    check3 = 1;
                } else {
                    PassVericheck.setText("??????????????? ???????????? ????????????.");
                    PassVericheck.setTextColor(Color.parseColor("#E53A40"));
                    check3 = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //????????????
        cert_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimationDialog.show();
                String url = u + "/ajax/sms";
                /* Create request */
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", s_doctorPhoneModify.getText().toString());
                cert_number = numberGen(6, 1);
                params.put("number", cert_number);
                JsonObjectRequest certForm = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                        url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                customAnimationDialog.dismiss();
                                String pnPattern = "^01[016789]{1}-?[0-9]{3,4}-?[0-9]{4}$";

                                boolean success = false;
                                try {
                                    success = response.getBoolean("result");
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                                        countDownTimer();
                                        countDownTimer.start();
                                        cert_send.setEnabled(false);
                                        certnum_check.setEnabled(true);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customAnimationDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "??????????????? ????????? ????????????.", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "?????????????????????.\n???????????? ?????? ??????????????????.", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "????????? ????????? ??????????????????.", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                AppController.getInstance(getApplicationContext()).addToRequestQueue(certForm);
                certNum.requestFocus();
            }
        });

        certnum_check.setOnClickListener(new View.OnClickListener() {// ???????????? ???????????? ?????????
            @Override
            public void onClick(View v) {
                if (cert_number.equals(certNum.getText().toString())) {
                    certnumcheck.setText("?????????????????????.");
                    Toast.makeText(getApplicationContext(), "?????????????????????.", Toast.LENGTH_LONG).show();
                    certnum_check.setEnabled(false);
                    cert_send.setText("????????????");
                    certNum.setEnabled(false);
                    certnum_check.setEnabled(false);
                    countDownTimer.cancel();
                    check2 = 1;
                } else {
                    certnumcheck.setText("????????? ??????????????? ???????????????.");
                    certnumcheck.setTextColor(Color.parseColor("#E53A40"));
                    check2 = 0;
                }
            }
        });
        //????????? ?????????
        s_doctorPhoneModify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phonePattern = "^01[016789]{1}-?[0-9]{3,4}-?[0-9]{4}$";
                Matcher matcher = Pattern.compile(phonePattern).matcher(s_doctorPhoneModify.getText());

                if (!matcher.matches()) {
                    phonecheck.setText("????????? ?????? ?????? ???????????????.");
                    phonecheck.setTextColor(Color.parseColor("#E53A40"));
                    check = 0;
                } else if (matcher.matches()) {
                    phonecheck.setText("??????????????? ???????????????.");
                    phonecheck.setTextColor(Color.parseColor("#5CAB7D"));
                    check = 1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //????????????
        numbercheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimationDialog.show();
                if (!s_doctorPhoneModify.getText().toString().isEmpty()) {
                    String url = u + "/doctorapp/check_phone";
                    /* Create request */
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("doctor_phone", s_doctorPhoneModify.getText().toString());

                    JsonObjectRequest loginForm = new JsonObjectRequest(Request.Method.POST,
                            url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    customAnimationDialog.dismiss();
                                    boolean success = false;
                                    try {
                                        success = response.getBoolean("result");
//                                        if (success){
//                                            Toast.makeText(getApplicationContext(),"?????????????????????.",Toast.LENGTH_LONG).show();
//
//                                        }else {
//                                            Toast.makeText(getApplicationContext(),"?????? ?????? ???????????????.\n?????? ????????? ??????????????????.",Toast.LENGTH_LONG).show();
//
//                                        }
                                        if (check == 0) {
                                            phonecheck.setText("????????? ??????????????? ??????????????????.");
                                            phonecheck.setTextColor(Color.parseColor("#E53A40"));
                                        } else if (check == 1) {
                                            if (!success) {
                                                phonecheck.setText("?????? ???????????? ?????????????????????.");
                                                phonecheck.setTextColor(Color.parseColor("#E53A40"));
                                                check = 0;
                                            } else {
                                                phonecheck.setText("?????????????????????.");
                                                phonecheck.setTextColor(Color.parseColor("#5CAB7D"));
                                                check = 2;
                                                numbercheck.setVisibility(View.GONE);
                                                cert_send.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        //    }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            customAnimationDialog.dismiss();
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "??????????????? ????????? ????????????.", Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getApplicationContext(), "?????????????????????.\n???????????? ?????? ??????????????????.", Toast.LENGTH_LONG).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(), "????????? ????????? ??????????????????.", Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    AppController.getInstance(view.getContext()).addToRequestQueue(loginForm);
                } else {
                    Toast.makeText(getApplicationContext(), "????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    customAnimationDialog.dismiss();
                }
            }
        });
        //??????????????????
        button_Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                customAnimationDialog.show();
                String url = u + "/doctorapp/mypage_update";
                /* Create request */
                Map<String, String> params = new HashMap<String, String>();
                params.put("doctor_id", doctor_num);
                params.put("doctor_phone", s_doctorPhoneModify.getText().toString());

                JsonObjectRequest updateForm = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                        url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                customAnimationDialog.dismiss();
                                boolean success = false;
                                try {
                                    success = response.getBoolean("result");
                                    if (success == true) {
                                        if ((check2 == 1)) {
                                            Toast.makeText(getApplicationContext(), "???????????? ?????? ??????", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        } else if (!(check2 == 1)) {
                                            Toast.makeText(getApplicationContext(), "????????? ????????? ????????????.", Toast.LENGTH_LONG).show();
                                        } else if (!(check == 2)) {
                                            Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "???????????? ??????????????????", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customAnimationDialog.dismiss();
                    }
                });
                AppController.getInstance(getApplicationContext()).addToRequestQueue(updateForm);
            }
        });

        //????????????????????????
        passmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                customAnimationDialog.show();
                String url = u + "/doctorapp/mypage_update";
                /* Create request */
                Map<String, String> params = new HashMap<String, String>();
                params.put("doctor_id", doctor_num);
                params.put("doctor_pw", s_doctorPass.getText().toString());

                JsonObjectRequest updateForm = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                        url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                customAnimationDialog.dismiss();
                                boolean success = false;
                                try {
                                    success = response.getBoolean("result");
                                    if (success == true) {
                                        if (s_doctorPass.getText().toString().equals(s_doctorPassVeri.getText().toString())) {
                                            if ((check3 == 1 && check4==1)) {
                                                Toast.makeText(getApplicationContext(), "???????????? ?????? ??????", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            } else if (!(check4 == 1)) {
                                                Toast.makeText(getApplicationContext(), "??????????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "???????????? ??????????????????", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customAnimationDialog.dismiss();
                    }
                });
                AppController.getInstance(getApplicationContext()).addToRequestQueue(updateForm);
            }
        });


    }// oncreate

    public static String numberGen(int len, int dupCd) {

        Random rand = new Random();
        String numStr = ""; //????????? ????????? ??????

        for (int i = 0; i < len; i++) {

            //0~9 ?????? ?????? ??????
            String ran = Integer.toString(rand.nextInt(10));

            if (dupCd == 1) {
                //?????? ????????? numStr??? append
                numStr += ran;
            } else if (dupCd == 2) {
                //????????? ???????????? ????????? ????????? ?????? ????????? ????????????
                if (!numStr.contains(ran)) {
                    //????????? ?????? ????????? numStr??? append
                    numStr += ran;
                } else {
                    //????????? ????????? ???????????? ????????? ?????? ????????????
                    i -= 1;
                }
            }
        }
        return numStr;
    }

    public void countDownTimer() {//????????? ?????? ??????????????? ?????????
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                cert_send.setText((count / 60) + ":" + String.format("%02d", count % 60));//???:?????? ???????????? ?????????
                count--;
            }

            public void onFinish() {
                count = 180;
                cert_number = numberGen(6, 1);// ??????????????? ??????????????? ???????????? ????????? ????????????
                cert_send.setText("?????????");
                cert_send.setEnabled(true);// ???????????? ?????????
                certNum.setEnabled(false);// ?????????????????? ????????????
                certnum_check.setEnabled(false);// ?????????????????? ????????????
            }
        };
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        } catch (Exception e) {
        }
        countDownTimer = null;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customAnimationDialog.dismiss();
    }
}
