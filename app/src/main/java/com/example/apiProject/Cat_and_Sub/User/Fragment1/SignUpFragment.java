package com.example.apiProject.Cat_and_Sub.User.Fragment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apiProject.Cat_and_Sub.MargeActivity;
import com.example.apiProject.Cat_and_Sub.User.Activity1.UserActivity;
import com.example.apiProject.Cat_and_Sub.User.UserNetwork.UserCommonResponse;
import com.example.apiProject.Cat_and_Sub.network.RestClient;
import com.example.apiProject.Cat_and_Sub.network.Restcall;
import com.example.apiProject.R;
import com.example.apiProject.Utils.VariableBag;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SignUpFragment extends Fragment {

    Button btnSave1;
    EditText etFirst, etLast, etMail1, etPass1;
    Restcall restcall;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        btnSave1 = view.findViewById(R.id.btnSave1);
        etFirst = view.findViewById(R.id.etFirst);
        etLast = view.findViewById(R.id.etLast);
        etMail1 = view.findViewById(R.id.etMail1);
        etPass1 = view.findViewById(R.id.etPass1);
        restcall= RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnSave1.setOnClickListener(v -> {

                String firstName = etFirst.getText().toString().trim();
                String lastName = etLast.getText().toString().trim();
                String email = etMail1.getText().toString().trim();
                String password = etPass1.getText().toString().trim();
                if (TextUtils.isEmpty(firstName)) {
                    etFirst.setError("First name is required");
                    etFirst.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    etLast.setError("Last name is required");
                    etLast.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    etMail1.setError("Email is required");
                    etMail1.requestFocus();
                    return;
                }
                if (!isValidEmail(email)) {
                    etMail1.setError("Invalid email format (format should be abc@gmail.com)");
                    etMail1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPass1.setError("Password is required");
                    etPass1.requestFocus();
                    return;
                } else if (!isValidPassword(password)) {
                    etPass1.setError("password must be 8 character and alphabetic and numeric there");
                } else {
                    AddUser();

//                    Toast.makeText(getActivity(), "Sign up successful", Toast.LENGTH_SHORT).show();

                }
            });return (view);

    }

        private boolean isValidEmail(CharSequence target) {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }

        private boolean isValidPassword(String password) {
            if (TextUtils.isEmpty(password)) {
                etPass1.setError("Password is required");
                etPass1.requestFocus();
                return false;
            }

            if (password.length() < 8) {
                etPass1.setError("Password should be at least 8 characters");
                etPass1.requestFocus();
                return false;
            }
            boolean hasAlphabetic = false;
            boolean hasNumeric = false;

            for (char c : password.toCharArray()) {
                if (Character.isLetter(c)) {
                    hasAlphabetic = true;
                } else if (Character.isDigit(c)) {
                    hasNumeric = true;
                }
                if (hasAlphabetic && hasNumeric) {
                    break;
                }
            }

            if (!hasAlphabetic || !hasNumeric) {
                etPass1.setError("Password must contain both alphabetic and numeric characters");
                etPass1.requestFocus();
                return false;
            }
            if (!containsSpecialCharacter(password)) {
                etPass1.setError("Password must contain at least one special character");
                etPass1.requestFocus();
                return false;
            }
            return true;
        }
        private boolean containsSpecialCharacter(String str) {
            String specialCharacters = "!@#$%^&*()-_+=<>?/[]{}|";
            for (char c : str.toCharArray()) {
                if (specialCharacters.contains(String.valueOf(c))) {
                    return true;
                }
            }
            return false;
        }
             /*   String firstName = etFirst.getText().toString().trim();
                String lastName = etLast.getText().toString().trim();
                String email = etMail1.getText().toString().trim();
                String password = etPass1.getText().toString().trim();
                if (firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(getActivity(), "First name and last name are required", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    etMail1.setError("Invalid email format");
                    etMail1.requestFocus();
                } else if (!isValidPassword(password)) {
                    etPass1.setError("Password must have at least 8 characters, one capital letter, one small letter, one special character, and one number");
                    etPass1.requestFocus();
                } else {
                    AddUser();
     //               Toast.makeText(getActivity(), "Successfully Sign up", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean isValidEmail(String email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }*/


    private void AddUser() {
        restcall.AddUser("AddUser",etFirst.getText().toString().trim(),etLast.getText().toString().trim()
                        ,etMail1.getText().toString().trim(),etPass1.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UserCommonResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", ""+e.getLocalizedMessage());
                                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(UserCommonResponse userCommonResponse) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    etFirst.setText("");
                                    etLast.setText("");
                                    etMail1.setText("");
                                    etPass1.setText("");
                                    startActivity(new Intent(getContext(), UserActivity.class));

                                }
                                Toast.makeText(getActivity(), ""+userCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }
}