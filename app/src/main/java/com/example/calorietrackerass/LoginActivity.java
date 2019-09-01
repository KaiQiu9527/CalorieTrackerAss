package com.example.calorietrackerass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    String username = "";
    String hash = "";
    Boolean exist;
    int code = -1 ;
    EditText tv_name;
    EditText tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences userinfo = getSharedPreferences("userinfo",0);
        SharedPreferences.Editor editor = userinfo.edit();
        mActionBar = getSupportActionBar();
        mActionBar.hide();

        final CreateMD5 md5 = new CreateMD5();
        tv_name = (EditText) findViewById(R.id.editText);
        tv_password = (EditText) findViewById(R.id.editText2);
        //for test
        tv_name.setText("Kai");
        tv_password.setText("1234");
        Button login = (Button) findViewById(R.id.button2);
        Button register = findViewById(R.id.button3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = tv_name.getText().toString();
                editor.putString("username",username);
                editor.commit();
                String password = tv_password.getText().toString();
                hash = md5.encrypt(password);
                LoginAsyncTask login = new LoginAsyncTask();

                if(!username.equals("") && !password.equals("")){
                    login.execute(username,hash);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter username and password!",Toast.LENGTH_LONG).show();
                }
            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = tv_name.getText().toString();
                ExistAsyncTask exist = new ExistAsyncTask();

                if(!username.equals("")){
                    exist.execute(username,hash);
                    editor.putString("username",username);
                    editor.commit();
                    tv_name.setText("");
                    tv_password.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter the username you like!",Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode== KeyEvent.KEYCODE_HOME){
            return true;
        } else if( keyCode== KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()- exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "Press return again to exit!", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ExistAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...param) {
            return RestConnect.findUserByName(param[0]);
        }

        @Override
        protected void onPostExecute(String user)
        {
            String credential = user;
            if(credential.length()>20) {
                Toast.makeText(getApplicationContext(), "The username is existed!",
                        Toast.LENGTH_SHORT).show();
            }
            else
                {
                Toast.makeText(getApplicationContext(), "The username is not existed!",
                        Toast.LENGTH_SHORT).show();
                    Intent intent1 =  new Intent(LoginActivity.this,RegisterActivity.class);
                    intent1.putExtra("username",username);
                    startActivity(intent1);
                }
        }

    }

    private class RegisterAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...param) {
            return RestConnect.findUserByName(param[0]);
        }

        @Override
        protected void onPostExecute(String user)
        {
            String credential = user;
            if(credential.length()>20) {
                Toast.makeText(getApplicationContext(), "The username is existed!",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "The username is not existed!\nNow Register! ",
                        Toast.LENGTH_SHORT).show();
                Intent intent1 =  new Intent(LoginActivity.this,RegisterActivity.class);
                intent1.putExtra("username",username);
                startActivity(intent1);
            }
        }

    }

    private class LoginAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...param) {
            return RestConnect.findUserByNameAndHash(param[0],param[1]);
        }

        @Override
        protected void onPostExecute(String user)
        {
            String credential = user;
            if(credential.equals("timeout"))
            {
                Toast.makeText(getApplicationContext(), "Connection Timeout! ",
                        Toast.LENGTH_SHORT).show();
            }
            else
                {
                    if (credential.length() > 20) {
                        Toast.makeText(getApplicationContext(), "The password is correct!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        intent1.putExtra("username", username);
                        intent1.putExtra("hash", hash);
                        tv_name.setText("");
                        tv_password.setText("");
                        startActivity(intent1);
                    } else {
                        Toast.makeText(getApplicationContext(), "The password is incorrect! ",
                                Toast.LENGTH_SHORT).show();

                    }
                }
        }

    }

}
