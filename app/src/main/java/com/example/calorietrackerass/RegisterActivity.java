package com.example.calorietrackerass;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import SQlite.Credential;
import SQlite.User;

//import SQlite.UserDatabase;

public class RegisterActivity extends AppCompatActivity {


    CreateMD5 md5 = new CreateMD5();
    private EditText birthday;
    private Spinner sp;
    private RadioGroup genderGroup = null;
    private TextView gender_tv = null;
    private TextView gender_tv2 = null;
    private User nowUser = null;
    private String username;
    private  User user;
    private Credential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView tv0 = (TextView) findViewById(R.id.tv0);
        TextView tvusername = (TextView) findViewById(R.id.tvusername);
        final TextView tvpw = findViewById(R.id.tvpw);
        TextView tvpw1 = findViewById(R.id.tvpw1);
        final EditText tvpw2 = findViewById(R.id.tvpw2);
        final TextView tvpw4 = findViewById(R.id.tvpw4);
        TextView tvpw5 = findViewById(R.id.tvpw5);
        final EditText tvpw6 = findViewById(R.id.tvpw6);
        final EditText name_tv = (EditText) findViewById(R.id.editText3);
        final EditText surname_tv = (EditText) findViewById(R.id.editText4);
        final EditText email_tv = (EditText) findViewById(R.id.editText5);
        final EditText height_tv = (EditText) findViewById(R.id.editText6);
        final EditText weight_tv = (EditText) findViewById(R.id.editText7);
        final EditText address_tv = (EditText) findViewById(R.id.editText8);
        final EditText postcode_tv = (EditText) findViewById(R.id.editText9);
        final TextView level_tv = (TextView) findViewById(R.id.tvlevelseleted);
        birthday = (EditText) findViewById(R.id.editTextDob);
        sp = (Spinner) findViewById(R.id.spinner_level);
        gender_tv = findViewById(R.id.genderresult);
        gender_tv2 = findViewById(R.id.genderresult2);
        genderGroup = findViewById(R.id.gender);
        final RadioButton genderM = findViewById(R.id.Male);
        final RadioButton genderF = findViewById(R.id.Female);
        Button submit = findViewById(R.id.submit);
        final TextView spm_tv = findViewById(R.id.steppermile);
        final EditText spm_et = findViewById(R.id.editsteppermile);
//        UserDatabase db = null;
//        db = Room.databaseBuilder(getApplicationContext(),
//                UserDatabase.class, "UserDatabase")
//                .fallbackToDestructiveMigration()
//                .build();

        //set the username from login page
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        tvusername.setText(username);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioButton();
            }
        });


        //set spinner of levels
        Integer[] levels = {1, 2, 3, 4, 5};
        ArrayAdapter<Integer> a = new ArrayAdapter<Integer>(RegisterActivity.this, android.R.layout.simple_spinner_item, levels);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(a);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer level = Integer.parseInt(sp.getSelectedItem().toString());
                level_tv.setText(level.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set birthday  calendar
        birthday.setInputType(InputType.TYPE_NULL);
        birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    showDatePickerDialog();
                }
            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePickerDialog();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //username已经有了
                String pw = tvpw2.getText().toString();
                String confirmpw = tvpw6.getText().toString();
                String pwhash = md5.encrypt(pw);
                String confirmpwhash = md5.encrypt(confirmpw);
                String name = name_tv.getText().toString();
                String surname = surname_tv.getText().toString();
                String email = email_tv.getText().toString();
                String address = address_tv.getText().toString();
                Double height = 0.0;
                Double weight = 0.0;
                Integer postcode = 0;
                Integer steppermile = 0;
                Calendar c = Calendar.getInstance();
                Date signUpDate = c.getTime();
                try {
                    height = Double.parseDouble(height_tv.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Height should be number!", Toast.LENGTH_LONG).show();
                    height_tv.setText("0");
                    height = 0.0;
                }
                try {
                    weight = Double.parseDouble(weight_tv.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Weight should be number!", Toast.LENGTH_LONG).show();
                    weight_tv.setText("0");
                    weight = 0.0;
                }
                try {
                    postcode = Integer.parseInt(postcode_tv.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Postcode should be number!", Toast.LENGTH_LONG).show();
                    postcode_tv.setText("0");
                    postcode = 0;
                }
                try {
                    steppermile = Integer.parseInt(spm_et.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Step per mile should be number!", Toast.LENGTH_LONG).show();
                    spm_et.setText("0");
                    steppermile = 0;
                }
                String dob = birthday.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birth = null;
                try {
                    birth = sdf.parse(dob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String gender = gender_tv.getText().toString();
                Integer level = Integer.parseInt(level_tv.getText().toString());
                int checkCode = 0;
                String hash = "";
                //check password
                if (pw.length() < 4) {
                    Toast.makeText(getApplicationContext(), "password shoule be at laest 4 characters!", Toast.LENGTH_LONG).show();
                    tvpw2.setText("");
                    tvpw6.setText("");
                } else {
                    if (!confirmpw.equals(pw)) {
                        Toast.makeText(getApplicationContext(), "The confirm password is not the same!", Toast.LENGTH_LONG).show();
                        tvpw6.setText("");
                    } else {
                        CreateMD5 md5 = new CreateMD5();
                        hash = md5.encrypt(pw);
                        checkCode++;
                    }
                }
                //check name
                if (name.equals("")) {
                    name_tv.setHint("Please input the name!");
                } else {
                    if (name.length() > 30) {
                        name_tv.setText("");
                        name_tv.setHint("Name should be less than 30 characters!");
                    } else {
                        if (name.contains(" ") | name.contains("-") | name.contains("'")) {
                            name_tv.setText("");
                            name_tv.setHint("Can't contain the special characters!");
                        } else {
                            checkCode++;
                        }
                    }
                }
                //check surname
                if (surname.equals("")) {
                    surname_tv.setHint("Please input the surname!");
                } else {
                    if (surname.length() > 30) {
                        surname_tv.setText("");
                        surname_tv.setHint("Surname should be less than 30 characters!");
                    } else {
                        if (surname.contains(" ") | surname.contains("-") | surname.contains("'")) {

                            surname_tv.setText("");
                            surname_tv.setHint("Can't contain the special characters!");
                        } else {
                            checkCode++;
                        }
                    }
                }
                //check email
                if (email.equals("")) {
                    email_tv.setHint("Please input the email!");
                } else {
                    if (surname.length() > 50) {
                        email_tv.setText("");
                        email_tv.setHint("Email should be less than 50 characters!");
                    } else {
                        if (email.contains(" ") | email.contains("-") | !email.contains("@")) {
                            email_tv.setText("");
                            email_tv.setHint("Please input the right email format!");
                        } else {
                            checkCode++;
                        }
                    }
                }
                //check Height
                if (height == 0.0) {
                    height_tv.setText("");
                    height_tv.setHint("Please input the height!");
                } else {
                    checkCode++;
                }
                //check Weight
                if (weight == 0.0) {
                    weight_tv.setText("");
                    weight_tv.setHint("Please input the weight!");
                } else {
                    checkCode++;
                }
                //check address
                if (address.equals("")) {
                    address_tv.setHint("Please input the address!");
                } else {
                    if (address.length() > 100) {
                        address_tv.setText("");
                        address_tv.setHint("Address should be less than 100 characters!");
                    } else {
                        checkCode++;
                    }
                }
                //check Postcode
                if (postcode == 0) {
                    postcode_tv.setText("");
                    postcode_tv.setHint("Please input the Postcode!");
                } else {
                    checkCode++;
                }
                //check Birthday
                if (dob.equals("")) {
                    birthday.setHint("Please select the birthday!");
                } else {
                    checkCode++;
                }
                //check Gender
                if (gender.equals("M") || gender.equals("F")) {
                    gender_tv2.setText("Right!");
                    checkCode++;
                } else {
                    gender_tv2.setHint("Please select the Gender!");
                }
                //check SPM
                if (steppermile == 0) {
                    spm_et.setText("");
                    spm_et.setHint("Please input the step per mile!");
                } else {
                    checkCode++;
                }
                //checkcode = 11 means check complete!
                if (checkCode == 11) {

                    user = new User(name, surname, email, height, weight, address, postcode, level, steppermile, gender, birth);
                    credential = new Credential(username,signUpDate,hash);
                    CheckEmailAsyncTask checkEmailAsyncTask = new CheckEmailAsyncTask();
                    checkEmailAsyncTask.execute(email);

                }

            }
        });
    }

    //show date
    public void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                birthday.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void selectRadioButton() {
        RadioButton rb = (RadioButton) RegisterActivity.this.findViewById(genderGroup.getCheckedRadioButtonId());
        String gender = rb.getText().toString();
        gender_tv.setText(gender);
        gender_tv2.setHint("");
    }

    private class CheckEmailAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            return RestConnect.findUserByEmail(param[0]);
        }
        @Override
        protected void onPostExecute(String response) {
            if(response.length() > 10)
            {
                Toast.makeText(getApplicationContext(),"The email is existing!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                PostUserAsyncTask postUser = new PostUserAsyncTask();
                PostCredentialAsyncTask  postCredential = new PostCredentialAsyncTask();
                postUser.execute(user);
                postCredential.execute(credential);
            }
        }

    }

    private class PostUserAsyncTask extends AsyncTask<User, Void, String> {
        @Override
        protected String doInBackground(User... user) {
            Integer userid = RestConnect.findLargestID("users") + 1;
            User newUser = user[0];
            newUser.setID(userid);
            nowUser = newUser;
            RestConnect.postNewUser(newUser);
            return "success!";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getApplicationContext(),"SUCCEED",Toast.LENGTH_SHORT).show();
        }

    }

    private class PostCredentialAsyncTask extends AsyncTask<Credential, Void, String> {
        @Override
        protected String doInBackground(Credential... credential) {
            Integer userid = RestConnect.findLargestID("users");
            Integer credentialid = RestConnect.findLargestID("credential") +1;
            Credential newCredential = credential[0];
            newCredential.setID(credentialid);
            newCredential.setUserid(nowUser);
            RestConnect.postNewCredential(newCredential);
            return "credential success!";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getApplicationContext(),"SUCCEED",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}



//    private class CheckPWAsyncTask extends AsyncTask<String, Void, String>
//    {
//        @Override
//        protected String doInBackground(String... params) {
//            if(params[0].equals(params[1]))
//            return "Course was added";
//        }
//        @Override
//        protected void onPostExecute(String response) {
//            resultTextView.setText(response);
//        }
//    }









