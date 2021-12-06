package com.zte.reader;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import com.zte.reader.util.Userinfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class MainActivity extends AppCompatActivity {

    public static final Userinfo UI = new Userinfo();

    EditText phone, password;
    ImageView eye;
    Button login;
    boolean isvisible;

    ScrollView sv;
    ProgressBar pb;


    public class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private String phone;
        private String password;

        public LoginTask(String phone, String password) {
            this.password = password;
            this.phone = phone;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (phone.equals("15651885351")) {
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            pb.setVisibility(View.GONE);
            if (!aBoolean) {
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);

                ab.setMessage("手机号或者密码不正确");

                ab.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sv.setVisibility(View.VISIBLE);
                    }
                });
                ab.show();
            } else {
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                ab.setMessage("需要保存用户名和密码吗？");
                ab.setPositiveButton("好的", (dialogInterface, i) -> {
                    Properties p = new Properties();

                    p.put("username", phone);
                    p.put("password", password);
                    FileOutputStream output = null;
                    try {
                        output = new FileOutputStream(info);
                        p.store(output, "ok");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    jump();
                });

                ab.setNegativeButton("不用", (dialogInterface, i) -> {
                    jump();
                });

                ab.show();


            }
        }

        private void jump() {
            Intent it = new Intent();
            it.setClass(MainActivity.this, BottomActivity.class);
            MainActivity.UI.setPhone("15651885351");
            startActivity(it);
            finish();

        }
    }


    private boolean checkPassword(EditText password) {
        String pas = password.getText().toString();

        if (pas.length() < 6 || pas.length() > 16) {
            password.setError("密码长度非法");
            return false;
        }


        return true;
    }

    private boolean checkPhone(EditText phone) {

        String ph = phone.getText().toString().trim();

        String pattern = "^[1][35689][0-9]{9}$";

        if (!ph.matches(pattern)) {
            phone.setError("手机号非法");
            return false;
        }

        return true;
    }


    private File info;
    private String fileName = "info.properties";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);

        String own = getFilesDir().getAbsolutePath() + File.separator + fileName;

        info = new File(own);

        if (!info.exists()) {
            try {
                info.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            Properties p = new Properties();
            try {
                FileInputStream input = new FileInputStream(info);
                p.load(input);

                String phone_s = p.getProperty("username");
                String password_s = p.getProperty("password");

                phone.setText(phone_s);
                password.setText(password_s);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        this.pb = (ProgressBar) findViewById(R.id.progressBar);
        this.sv = (ScrollView) findViewById(R.id.sv);
        eye = (ImageView) findViewById(R.id.eye);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPhone(phone)) {
                    return;
                }
                if (!checkPassword(password)) {
                    return;
                }

                sv.setVisibility(View.GONE);

                View v = getWindow().peekDecorView();
                if (v != null) {
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }


                pb.setVisibility(View.VISIBLE);


                LoginTask task = new LoginTask(phone.getText().toString(), password.getText().toString());

                task.execute();

            }
        });

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isvisible == false) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye.setImageResource(R.drawable.ce);
                    isvisible = true;
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye.setImageResource(R.drawable.oe);
                    isvisible = false;
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
