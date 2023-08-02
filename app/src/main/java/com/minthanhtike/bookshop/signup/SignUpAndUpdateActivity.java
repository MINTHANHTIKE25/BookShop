package com.minthanhtike.bookshop.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minthanhtike.bookshop.BookStoreActivity;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.fragments.AccountFragments;
import com.minthanhtike.bookshop.login.LogIn;
import com.minthanhtike.bookshop.room.usersinfo.Users;
import com.minthanhtike.bookshop.room.usersinfo.UsersViewModel;

import java.util.Objects;

public class SignUpAndUpdateActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    EditText nameEdt, ageEdt, phnoEdt, addressEdt, nrcEdt, emailEdt, passwordEdt;
    Button confirmBtn;
    UsersViewModel usersViewModel;
    private DatabaseReference databaseReference;
    RadioGroup radioGroup;
    String payment;
    long id = 0;
    boolean isUpdate;
    public static final String REGISTERDATA = "REGISTERDATA";
    public static final String REGISTEREMAIL = "email";
    public static final String REGISTERNAME = "name";
    public static final String REGISTERPASS = "password";
    public static final String ALDYRegister = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        initEvent();
    }

    public void initView() {
        radioGroup = findViewById(R.id.radio_group);
        nameEdt = findViewById(R.id.name_edt_regist);
        ageEdt = findViewById(R.id.age_edt_regist);
        phnoEdt = findViewById(R.id.phno_edt_regist);
        addressEdt = findViewById(R.id.address_edt_regist);
        nrcEdt = findViewById(R.id.nrc_edt_regist);
        emailEdt = findViewById(R.id.email_edt_regist);
        passwordEdt = findViewById(R.id.password_edt_regist);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        confirmBtn = findViewById(R.id.confirm_register);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    id = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void initEvent() {
        isUpdate = getIntent().getBooleanExtra(AccountFragments.ISUPDATE, false);
        if (isUpdate) {
            update();
        } else {
            nameEdt.setText(getIntent().getStringExtra(LogIn.USERNAME));
            emailEdt.setText(getIntent().getStringExtra(LogIn.LOGINEMAIL));
            passwordEdt.setText(getIntent().getStringExtra(LogIn.PASSWORD));
            radioGroup.setOnCheckedChangeListener(this);
            confirmBtn.setOnClickListener(v -> {

                String name = nameEdt.getText().toString();
                String age = ageEdt.getText().toString();
                String phno = phnoEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String nrc = nrcEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(this, "Your name is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (age.isEmpty()) {
                    Toast.makeText(this, "Your age is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phno.isEmpty()) {
                    Toast.makeText(this, "Your phno is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (address.isEmpty()) {
                    Toast.makeText(this, "Your address is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nrc.isEmpty()) {
                    Toast.makeText(this, "Your nrc is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(this, "Your email is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(this, "Your password is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Your email is not format.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() <= 6) {
                    Toast.makeText(this, "Your password must have more than 6 words.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "You didn't select the payments methods.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches() & !payment.isEmpty()) {
                    SharedPreferences sh2=getSharedPreferences(LogIn.USERLOGIN,MODE_PRIVATE);
                    SharedPreferences.Editor editor2=sh2.edit();
                    editor2.putBoolean(LogIn.ISREGISTER,true);
                    editor2.putBoolean(LogIn.ISLOGIN,false);
                    editor2.commit();
                    SharedPreferences sh = getSharedPreferences(REGISTERDATA, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString(REGISTERNAME, name);
                    editor.putString(REGISTEREMAIL, email);
                    editor.putString(REGISTERPASS, password);
                    editor.putBoolean(ALDYRegister, getIntent().getBooleanExtra(LogIn.REGISTERBTNCLICK, false));
                    editor.commit();
                    writeUsers(name, email, password, age, phno, address, nrc);
                }
            });
        }
    }

    //writing the database to the firebase realtime db
    void writeUsers(String name, String email, String password, String age, String phno, String address, String nrc) {
        Users users = new Users(Integer.parseInt(Long.toString(id + 1)), name, password, age, email, address, phno, nrc);
        databaseReference.child("U" + String.valueOf(id + 1)).setValue(users);
        Toast.makeText(this, "Your info is saved in our cloud systems.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, BookStoreActivity.class));
        finish();
    }

    //chcecking the radio btn
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.kpz_pay:
                payment = getResources().getString(R.string.kbz_pay);
                break;
            case R.id.wave_pay:
                payment = getResources().getString(R.string.wave_pay);
                break;
            case R.id.aya_pay:
                payment = getResources().getString(R.string.aya_pay);
                break;
            case R.id.cb_pay:
                payment = getResources().getString(R.string.cb_pay);
                break;
        }
    }

    //checking is update or not
    void update() {
        radioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        String upName = getIntent().getStringExtra(AccountFragments.UPNAME);
        String upEmail = getIntent().getStringExtra(AccountFragments.UPEMAIL);
        String upPass = getIntent().getStringExtra(AccountFragments.UPPASS);
        String upAdr = getIntent().getStringExtra(AccountFragments.UPADDRESS);
        String upAge = getIntent().getStringExtra(AccountFragments.UPAGE);
        String upNrc = getIntent().getStringExtra(AccountFragments.UPNRC);
        String upPh = getIntent().getStringExtra(AccountFragments.UPPHNO);
        String upTbId = getIntent().getStringExtra(AccountFragments.UPTBID);

        nameEdt.setText(upName);
        emailEdt.setText(upEmail);
        passwordEdt.setText(upPass);
        addressEdt.setText(upAdr);
        ageEdt.setText(upAge);
        nrcEdt.setText(upNrc);
        phnoEdt.setText(upPh);

        confirmBtn.setOnClickListener(v -> {
            String name = nameEdt.getText().toString();
            String age = ageEdt.getText().toString();
            String phno = phnoEdt.getText().toString();
            String address = addressEdt.getText().toString();
            String nrc = nrcEdt.getText().toString();
            String email = emailEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, "Your name is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (age.isEmpty()) {
                Toast.makeText(this, "Your age is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (phno.isEmpty()) {
                Toast.makeText(this, "Your phno is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (address.isEmpty()) {
                Toast.makeText(this, "Your address is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (nrc.isEmpty()) {
                Toast.makeText(this, "Your nrc is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "Your email is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Your password is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Your email is not format.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() <= 6) {
                Toast.makeText(this, "Your password must have more than 6 words.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "You didn't select the payments methods.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() & !payment.isEmpty()) {

                updateUsers(Integer.parseInt(upTbId), name, email, password, age, phno, address, nrc);
            } else {
                Toast.makeText(this, "Your email format is wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updateUsers(int tbId, String name, String email, String password, String age, String phno, String address, String nrc) {
        int dbId = Integer.parseInt(Long.toString(id));
        Log.d("DBID", String.valueOf(dbId));
        Log.d("DBID", String.valueOf(id));
        Users users = new Users(tbId, name, password, age, email, address, phno, nrc);
        for (int i = 0; i <= dbId; i++) {
            if (tbId == i) {
                databaseReference.child("U" + i).setValue(users);
                Toast.makeText(this, "Update Success!", Toast.LENGTH_SHORT).show();
                finish();
                break;
            }
        }
        Toast.makeText(this, "Update Fails!", Toast.LENGTH_SHORT).show();

    }
}