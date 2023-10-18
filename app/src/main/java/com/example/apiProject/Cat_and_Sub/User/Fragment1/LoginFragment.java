package com.example.apiProject.Cat_and_Sub.User.Fragment1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.MargeActivity;
import com.example.apiProject.Utils.Tools;
import com.example.apiProject.Cat_and_Sub.User.UserNetwork.LoginResponse;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.VariableBag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class LoginFragment extends Fragment {

    Button btnSubmit;
    EditText etEmail,etPass;
    Tools tools;
    String email1,password1;
    private SharedPreference sharedPreference;
    Restcall restcall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);

       btnSubmit =view.findViewById(R.id.btnSubmit);
       etEmail=view.findViewById(R.id.etEmail);
       etPass=view.findViewById(R.id.etPass);
       sharedPreference=new SharedPreference(getContext());
       restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

       if (sharedPreference.isLoggedIn()){
           openHomePage();
       }else{
           getContext();
       }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email1=etEmail.getText().toString().trim();
                password1=etPass.getText().toString().trim();

                if (!isValidEmail(email1)) {
                    etEmail.setError("Invalid email format");
                    etEmail.requestFocus();
                } else if (!isValidPassword(password1)) {
                    etPass.setError("Invalid password format");
                    etPass.requestFocus();
                } else {
                    LoginUser(email1,password1);
                }
            }
        });return (view);
    }
    private void openHomePage(){
        Intent i=new Intent(getContext(), MargeActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public  void LoginUser(String email ,String password){
        restcall.LoginUser("LoginUser",email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error",e.getMessage());
                                Toast.makeText(getActivity(), "This is Wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (loginResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    etEmail.setText("");
                                    etPass.setText("");
                                    sharedPreference.setLoggedIn(true);
                                    openHomePage();
                                    sharedPreference.setStringvalue("user_id", loginResponse.getUserId());
                                    sharedPreference.setStringvalue("first_name", loginResponse.getFirstName());
                                    sharedPreference.setStringvalue("last_name", loginResponse.getLastName());

                                }
                                Toast.makeText(getActivity(), ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }
}