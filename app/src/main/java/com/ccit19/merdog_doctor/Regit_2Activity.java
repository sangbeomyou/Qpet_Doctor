package com.ccit19.merdog_doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ccit19.merdog_doctor.databinding.ActivityRegit2Binding;
import com.ccit19.merdog_doctor.custom_dialog.CustomAnimationDialog;
import com.ccit19.merdog_doctor.variable.MyGlobals;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regit_2Activity extends AppCompatActivity {
    private CustomAnimationDialog customAnimationDialog;
    ActivityRegit2Binding binding;
    String phone_number;
    String cert_number;
    private static final int MILLISINFUTURE = 180 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    int check = 0;
    int check2 = 0;
    int check3 = 0;
    private int count = 180;
    private CountDownTimer countDownTimer;
    String u = MyGlobals.getInstance().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regit_2);
        binding.setActivity(this);
        phone_number = getIntent().getStringExtra("phone");
        binding.sDoctorPhone.setText(phone_number);
        //setContentView(R.layout.activity_regit_2);
        customAnimationDialog = new CustomAnimationDialog(Regit_2Activity.this);
        binding.idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!binding.sDoctorId.getText().toString().isEmpty()) {
                    String url = u+ "/doctorapp/check_id";
                    /* Create request */
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("doctor_id", binding.sDoctorId.getText().toString());

                    JsonObjectRequest idcheckForm = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                            url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    String idPattern = "^[a-z0-9\\-_]{5,20}$";   //???????????? , -_??????, ????????????????????? 5-20??????
                                    //String idPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{5,15}$";
                                    Matcher matcher = Pattern.compile(idPattern).matcher(binding.sDoctorId.getText());

                                    boolean success = false;
                                    try {
                                        success = response.getBoolean("result");
                                        if (check == 0) {
                                            binding.idAlt.setText("5~20?????? ????????????, ??????, ????????????(_),(-)??? ???????????????");
                                            binding.idAlt.setTextColor(Color.parseColor("#E53A40"));
                                        } else if (check == 1) {
                                            if (!success) {
                                                binding.idAlt.setText("?????? ???????????? ??????????????????");
                                                binding.idAlt.setTextColor(Color.parseColor("#E53A40"));
                                                check = 0;
                                            } else if (success) {
                                                binding.idAlt.setText("??????????????? ??????????????????.");
                                                binding.idAlt.setTextColor(Color.parseColor("#5CAB7D"));
                                                check = 2;
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
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
                    AppController.getInstance(getApplicationContext()).addToRequestQueue(idcheckForm);
                } else {
                    binding.idAlt.setText("???????????? ??????????????????.");
                    binding.idAlt.setTextColor(Color.parseColor("#E53A40"));
                }

            }
        });
        binding.sDoctorId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String idPattern = "^[a-z0-9\\-_]{5,20}$";   //???????????? , -_??????, ????????????????????? 5-20??????
                Matcher matcher = Pattern.compile(idPattern).matcher(binding.sDoctorId.getText());
                if (!matcher.matches()) {   //????????? ??????????????? ????????? ???????????? ??????
                    binding.idAlt.setText("5~20?????? ????????????, ??????, ????????????(_),(-)??? ???????????????");
                    binding.idAlt.setTextColor(Color.parseColor("#E53A40"));
                    check = 0;
                } else if (matcher.matches()) {       //??????????????? ??????????????? ????????? ???????????? ????????? ??????
                    binding.idAlt.setText("???????????? ????????? ???????????????");
                    binding.idAlt.setTextColor(Color.parseColor("#E53A40"));
                    check = 1;
                }
//                binding.idAlt.setText("5~20?????? ????????????, ??????, ????????????(_),(-)??? ???????????????");
            }
        });

        binding.sDoctorPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{5,15}$";
                Matcher matcher = Pattern.compile(pwPattern).matcher(binding.sDoctorPass.getText());

                pwPattern = "(.)\\1\\1\\1";
                Matcher matcher2 = Pattern.compile(pwPattern).matcher(binding.sDoctorPass.getText());

                if (!matcher.matches()) {
                    binding.passAlt.setText("??????, ??????, ???????????? ?????? 5~15????????? ??????????????????.");
                } else if (matcher2.find()) {
                    binding.passAlt.setText("??????????????? 4????????? ???????????? ????????????.");
                } else if (binding.sDoctorPass.getText().toString().contains(" ")) {
                    binding.passAlt.setText("????????? ????????? ???????????????.");
                } else {
                    binding.passAlt.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.sDoctorPass.getText().toString().equals(binding.sPassVeri.getText().toString()) && !binding.sPassVeri.getText().toString().isEmpty()) {
                    binding.passvrifAlt.setText("??????????????? ???????????????.");
                    binding.passvrifAlt.setTextColor(Color.parseColor("#5CAB7D"));
                    check3 = 1;
                } else {
                    binding.passvrifAlt.setText("??????????????? ???????????? ????????????.");
                    binding.passvrifAlt.setTextColor(Color.parseColor("#E53A40"));
                    check3 = 0;
                }
                //test.setText(BCrypt.hashpw(userpw.getText().toString(), BCrypt.gensalt(10)));//???????????????
                //boolean isValidPassword = BCrypt.checkpw(password, passwordHashed); ????????????
            }
        });
        binding.sPassVeri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.sDoctorPass.getText().toString().equals(binding.sPassVeri.getText().toString()) && !binding.sPassVeri.getText().toString().isEmpty()) {
                    binding.passvrifAlt.setText("??????????????? ???????????????.");
                    binding.passvrifAlt.setTextColor(Color.parseColor("#5CAB7D"));
                    check3 = 1;
                } else {
                    binding.passvrifAlt.setText("??????????????? ???????????? ????????????.");
                    binding.passvrifAlt.setTextColor(Color.parseColor("#E53A40"));
                    check3 = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.sDoctorname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String NicPattern = "^[???-???]*$";
                Matcher matcher = Pattern.compile(NicPattern).matcher(binding.sDoctorname.getText());
                if (!matcher.matches()) {
                    binding.doctornameAlt.setText("????????? ???????????? ????????????.");
                    binding.doctornameAlt.setTextColor(Color.parseColor("#E53A40"));
                } else if (binding.sDoctorname.getText().toString().contains(" ")) {
                    binding.doctornameAlt.setText("????????? ????????? ???????????????.");
                } else {
                    binding.doctornameAlt.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.certSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = u + "/ajax/sms";
                /* Create request */
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone_number);
                cert_number = numberGen(6, 1);
                params.put("number", cert_number);
                JsonObjectRequest regitForm = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                        url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                String pnPattern = "^01[016789]{1}-?[0-9]{3,4}-?[0-9]{4}$";

                                boolean success = false;
                                try {
                                    success = response.getBoolean("result");
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                                        countDownTimer();
                                        countDownTimer.start();
                                        binding.certSend.setEnabled(false);
                                        binding.certnumCheck.setEnabled(true);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                AppController.getInstance(getApplicationContext()).addToRequestQueue(regitForm);
                binding.certNum.requestFocus();
            }
        });

        binding.certnumCheck.setOnClickListener(new View.OnClickListener() {// ???????????? ???????????? ?????????
            @Override
            public void onClick(View v) {
                if (cert_number.equals(binding.certNum.getText().toString())) {
                    binding.certnumAlt.setText("?????????????????????.");
                    Toast.makeText(getApplicationContext(), "?????????????????????.", Toast.LENGTH_LONG).show();
                    binding.certnumCheck.setEnabled(false);
                    binding.certSend.setText("????????????");
                    binding.certNum.setEnabled(false);
                    binding.certnumCheck.setEnabled(false);
                    countDownTimer.cancel();
                    check2 = 1;
                } else {
                    binding.certnumAlt.setText("????????? ??????????????? ???????????????.");
                    check2 = 0;
                }
            }
        });



        binding.finButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (binding.sDoctorPass.getText().toString().equals(binding.sPassVeri.getText().toString())//???????????? ????????????
                        && !binding.sDoctorId.getText().toString().isEmpty()) {
                    if ((check == 2) && (check2 == 1) && (check3 == 1) && !binding.sDoctorname.getText().toString().isEmpty() && binding.AgreedCheck.isChecked()) {
                        startProgress();
                        String doctor_id = binding.sDoctorId.getText().toString();
                        String doctor_pw = binding.sPassVeri.getText().toString();
                        String doctor_name = binding.sDoctorname.getText().toString();
                        String doctor_phone = getIntent().getStringExtra("phone");
                        Intent intent = new Intent(Regit_2Activity.this, Regit_3Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("doctor_id", doctor_id);
                        intent.putExtra("doctor_pw", doctor_pw);
                        intent.putExtra("doctor_name", doctor_name);
                        intent.putExtra("doctor_phone", doctor_phone);
                        startActivity(intent);
                    } else if (!(check == 2)) {
                        Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                    } else if (!(check3 == 1)) {
                        Toast.makeText(getApplicationContext(), "??????????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    } else if (binding.sDoctorname.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "????????? ????????? ?????????.", Toast.LENGTH_LONG).show();
                    }
                    else if (!(check2 == 1)) {
                        Toast.makeText(getApplicationContext(), "????????? ????????? ????????????.", Toast.LENGTH_LONG).show();
                    }
                    else if (!binding.AgreedCheck.isChecked()){
                        Toast.makeText(getApplicationContext(), "?????? ????????? ????????????.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "???????????? ??????????????????", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

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
                binding.certSend.setText((count / 60) + ":" + String.format("%02d", count % 60));//???:?????? ???????????? ?????????
                count--;
            }

            public void onFinish() {
                count = 180;
                cert_number = numberGen(6, 1);// ??????????????? ??????????????? ???????????? ????????? ????????????
                binding.certSend.setText("?????????");
                binding.certSend.setEnabled(true);// ???????????? ?????????
                binding.certNum.setEnabled(false);// ?????????????????? ????????????
                binding.certnumCheck.setEnabled(false);// ?????????????????? ????????????
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

    private void startProgress() {

        customAnimationDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                customAnimationDialog.dismiss();
            }
        }, 1000);

    }
}