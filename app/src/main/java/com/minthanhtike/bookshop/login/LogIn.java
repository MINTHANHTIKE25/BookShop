package com.minthanhtike.bookshop.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minthanhtike.bookshop.BookStoreActivity;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.fragments.AccountFragments;
import com.minthanhtike.bookshop.room.usersinfo.Users;
import com.minthanhtike.bookshop.signup.SignUpAndUpdateActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {
    Animation bounceAnim;
    TextView bounceTv, registerTv;
    ImageButton rememberBtn;
    EditText usernameEdt, confirmEdt, emailEdt, passwordEdt;
    Button confirmBtn;
    //for remember me button
    public static final String REMEMBERNAME = "REMEMBER_CHECK";
    public static final String REMEMBERVALUE = "remember";
    private boolean remember = false;
    //for admin
    public static final String ISADMIN = "CHECK_ADMIN";
    private static final String ADMINMAIL = "admin25@gmail.com";
    public static final String ISADMINVALUE = "is_admin";
    private boolean isAdmin = false;
    private FirebaseAuth firebaseAuth;
    private List<Users> usersList = new ArrayList<>();
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    //saving data of the users in shared preference retrieve from the account fragments and bag dialog frags
    public static final String USERLOGIN = "LOGIN_DATA";
    public static final String LOGINEMAIL = "users_email";
    public static final String PASSWORD = "passwords";
    public static final String USERNAME = "username";
    //this ISREGISTER used to check that the user's data is in the db or not
    public static final String ISREGISTER = "is_register";
    public static final String REGISTERBTNCLICK = "already_register";
    //islogin is used to know that when the account fragments has to retrieve data which is login's data or register data
    public static final String ISLOGIN="islogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //checking does the users remember me or not
        SharedPreferences retrievSh = getSharedPreferences(REMEMBERNAME, MODE_PRIVATE);
        if (retrievSh.getBoolean(REMEMBERVALUE, false) == true) {
            startActivity(new Intent(this, BookStoreActivity.class));
            finish();
        }
        makeAnimate();
        checkEdt();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bounceTv.startAnimation(bounceAnim);
    }

    private void makeAnimate() {
        bounceTv = findViewById(R.id.bouncing_text);
        bounceAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bouncing);
        bounceTv.startAnimation(bounceAnim);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkEdt() {
        Log.d("CHECKDB", "oncreate" + usersList.size());
        usernameEdt = findViewById(R.id.edt_name_login);
//        confirmEdt = findViewById(R.id.edt_confirm_login);
        emailEdt = findViewById(R.id.edt_email_login);
        passwordEdt = findViewById(R.id.edt_pass_login);
        confirmBtn = (Button) findViewById(R.id.confirm_button);
        registerTv = findViewById(R.id.to_register_tv);
        rememberBtn = findViewById(R.id.remember_img_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot items : snapshot.getChildren()) {
                    Users users = items.getValue(Users.class);
                    usersList.add(users);
                }
                Toast.makeText(LogIn.this, "" + usersList.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        confirmBtn.setOnClickListener(v -> {
            String email = emailEdt.getText().toString();
            String pass = passwordEdt.getText().toString();
            String userName = usernameEdt.getText().toString();
//            String conPass = confirmEdt.getText().toString();
            if (userName.isEmpty()) {
                Toast.makeText(this, "Your username is empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "Your email is empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.isEmpty()) {
                Toast.makeText(this, "Your password is empty.", Toast.LENGTH_SHORT).show();
                return;
            }
//            if (conPass.isEmpty()) {
//                Toast.makeText(this, "Your Confirm Password is empty.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (!conPass.equals(pass)) {
//                Toast.makeText(this, "Your Confirm password is not equal with your passwords.", Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Your email format is wrong!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.equals(ADMINMAIL) & pass.equals("12345678")) {
                isAdmin = true;
                SharedPreferences sh = getSharedPreferences(ISADMIN, MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.putBoolean(ISADMINVALUE, isAdmin);
                editor.commit();
            }
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() & pass.length() > 6) {
                //checking the users from the data base
                boolean result = usersList.stream().anyMatch(new Predicate<Users>() {
                    @Override
                    public boolean test(Users users) {
                        if (users.getName().equals(userName) & users.getEmail().equals(email) & users.getPassword().equals(pass)) {
                            return true;
                        }
                        return false;
                    }
                });
                Log.d("CHECKDB", String.valueOf(result));
                if (!result) {
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPreferences sh = getSharedPreferences(USERLOGIN, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sh.edit();
                                editor.putString(USERNAME, userName);
                                editor.putString(LOGINEMAIL, email);
                                editor.putString(PASSWORD, pass);
                                editor.putBoolean(ISLOGIN,true);
                                editor.putBoolean(ISREGISTER,false);
                                SharedPreferences sh2=getSharedPreferences(SignUpAndUpdateActivity.REGISTERDATA,MODE_PRIVATE);
                                SharedPreferences.Editor editor1= sh2.edit();
                                editor1.putBoolean(SignUpAndUpdateActivity.ALDYRegister,false);
                                editor1.commit();
                                editor.commit();
                                Toast.makeText(LogIn.this, "LogIn Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogIn.this, BookStoreActivity.class));
                                finish();
                            } else {
                                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isComplete()) {
                                            Toast.makeText(LogIn.this, "Sign Up Your Info successfully!", Toast.LENGTH_SHORT).show();
                                            SharedPreferences sh = getSharedPreferences(USERLOGIN, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sh.edit();
                                            editor.putString(USERNAME, userName);
                                            editor.putString(LOGINEMAIL, email);
                                            editor.putString(PASSWORD, pass);
                                            editor.putBoolean(ISLOGIN,true);
                                            editor.putBoolean(ISREGISTER,false);
                                            SharedPreferences sh2=getSharedPreferences(SignUpAndUpdateActivity.REGISTERDATA,MODE_PRIVATE);
                                            SharedPreferences.Editor editor1= sh2.edit();
                                            editor1.putBoolean(SignUpAndUpdateActivity.ALDYRegister,false);
                                            editor1.commit();
                                            editor.commit();
                                            startActivity(new Intent(LogIn.this, BookStoreActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    SharedPreferences sh = getSharedPreferences(USERLOGIN, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString(USERNAME, userName);
                    editor.putString(LOGINEMAIL, email);
                    editor.putString(PASSWORD, pass);
                    editor.putBoolean(ISREGISTER, true);
                    editor.putBoolean(ISLOGIN,true);
                    editor.commit();
                    SharedPreferences sh2=getSharedPreferences(SignUpAndUpdateActivity.REGISTERDATA,MODE_PRIVATE);
                    SharedPreferences.Editor editor1= sh2.edit();
                    editor1.putBoolean(SignUpAndUpdateActivity.ALDYRegister,false);
                    editor1.commit();
                    Toast.makeText(LogIn.this, "Log In Success from our db!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogIn.this, BookStoreActivity.class));
                    finish();
                }
            }
        });

        rememberBtn.setOnClickListener(v -> {
            if (!remember) {
                rememberBtn.setBackground(getDrawable(R.drawable.check));
                remember = true;
                SharedPreferences sh = getSharedPreferences(REMEMBERNAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.putBoolean(REMEMBERVALUE, remember);
                editor.commit();
            } else {
                rememberBtn.setBackground(getDrawable(R.drawable.circular));
                remember = false;
            }
        });

        registerTv.setOnClickListener(v -> {
            Intent intent=new Intent(this,SignUpAndUpdateActivity.class);
            intent.putExtra(REGISTERBTNCLICK,true);
            startActivity(intent);
        });
    }

}