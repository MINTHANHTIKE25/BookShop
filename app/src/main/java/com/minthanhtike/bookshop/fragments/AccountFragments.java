package com.minthanhtike.bookshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.login.LogIn;
import com.minthanhtike.bookshop.room.usersinfo.Users;
import com.minthanhtike.bookshop.signup.SignUpAndUpdateActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class AccountFragments extends Fragment {
    TextView accNameTv, registerTv, updateTv;
    String userEmail, password, userName,userNameReg,userEmailReg,passReg;
    ImageView imageView;
    AnimationDrawable imageAnimate;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    List<Users> usersList = new ArrayList<>();
    public static final String UPNAME = "name";
    public static final String UPEMAIL = "email";
    public static final String UPAGE = "age";
    public static final String UPPASS = "password";
    public static final String UPADDRESS = "address";
    public static final String UPPHNO = "phno";
    public static final String UPNRC = "nrc";
    public static final String UPTBID = "tbId";
    public static final String ISUPDATE = "is_update";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accNameTv = view.findViewById(R.id.user_name_acc);
        imageView = view.findViewById(R.id.simple_view_animation);
        registerTv = view.findViewById(R.id.register_here);
        updateTv = view.findViewById(R.id.update_info_tv);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        imageView.setBackgroundResource(R.drawable.animation);
        imageAnimate = (AnimationDrawable) imageView.getBackground();
        imageAnimate.start();
        SharedPreferences retrieveRgs = requireActivity().getSharedPreferences(SignUpAndUpdateActivity.REGISTERDATA, Context.MODE_PRIVATE);
        SharedPreferences retriveSh = this.requireActivity().getSharedPreferences(LogIn.USERLOGIN, Context.MODE_PRIVATE);
        if (retriveSh.getBoolean(LogIn.ISLOGIN, false)) {
            userName = retriveSh.getString(LogIn.USERNAME, null);
            userEmail = retriveSh.getString(LogIn.LOGINEMAIL, null);
            password = retriveSh.getString(LogIn.PASSWORD, null);
            Toast.makeText(getContext(), userName, Toast.LENGTH_SHORT).show();
            sendingDataToFun(userName,userEmail,password);
        }
        if (retrieveRgs.getBoolean(SignUpAndUpdateActivity.ALDYRegister, false)){
            userNameReg = retrieveRgs.getString(SignUpAndUpdateActivity.REGISTERNAME, null);
            userEmailReg = retrieveRgs.getString(SignUpAndUpdateActivity.REGISTEREMAIL, null);
            passReg = retrieveRgs.getString(SignUpAndUpdateActivity.REGISTERPASS, null);
            Toast.makeText(getActivity(), userNameReg, Toast.LENGTH_SHORT).show();
            sendingDataToFun(userNameReg,userEmailReg,passReg);
        }

    }


    public void sendingDataToFun(String userName, String userEmail, String password){
        if (userEmail != null & password != null & userName != null) {

            accNameTv.setText(userName);

            registerTv.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), SignUpAndUpdateActivity.class);
                intent.putExtra(LogIn.USERNAME, userName);
                intent.putExtra(LogIn.LOGINEMAIL, userEmail);
                intent.putExtra(LogIn.PASSWORD, password);
                startActivity(intent);
            });
            valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usersList.clear();
                    for (DataSnapshot itemShot : snapshot.getChildren()) {
                        Users users = itemShot.getValue(Users.class);
                        usersList.add(users);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            updateTv.setOnClickListener(v -> {
                usersList.removeIf(new Predicate<Users>() {
                    @Override
                    public boolean test(Users users) {
                        if (!users.getName().equals(userName) &
                                !users.getEmail().equals(userEmail) &
                                !users.getPassword().equals(password)) {
                            return true;
                        }
                        return false;
                    }
                });
                if (!usersList.isEmpty()) {
                    Intent intent = new Intent(getActivity(), SignUpAndUpdateActivity.class);
                    intent.putExtra(ISUPDATE, true);
                    intent.putExtra(UPNAME, usersList.get(0).getName());
                    intent.putExtra(UPEMAIL, usersList.get(0).getEmail());
                    intent.putExtra(UPADDRESS, usersList.get(0).getAddress());
                    intent.putExtra(UPAGE, usersList.get(0).getAge());
                    intent.putExtra(UPNRC, usersList.get(0).getNrc());
                    intent.putExtra(UPPASS, usersList.get(0).getPassword());
                    intent.putExtra(UPPHNO, usersList.get(0).getPhno());
                    intent.putExtra(UPTBID, String.valueOf(usersList.get(0).getTbId()));
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "You have not registered!", Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            Toast.makeText(getActivity(), "Your Account is empty!", Toast.LENGTH_SHORT).show();
        }
    }
}


//        Animation in = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
//        Animation out = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_out_right);

//            for (int i = 0; i < images.length; i++) {
//                ImageView imageView = new ImageView(view.getContext());
//                imageView.setImageResource(images[i]);
//                viewAnimator.addView(imageView);
//            }
//            viewAnimator.setAnimateFirstView(false);
//            viewAnimator.setInAnimation(in);
//            viewAnimator.setOutAnimation(out);
//            for (int i = 0; i < images.length; i++) {
//                viewAnimator.showNext();
//            }
//            if (!usersList.isEmpty()) {
//                    for (int i = 0; i < usersList.size(); i++) {
//        Log.d("USERDATAOFME", usersList.get(i).getName());
//        Log.d("USERDATAOFME", usersList.get(i).getEmail());
//        }
//        }
