package com.example.apiProject.Cat_and_Sub.User.Activity1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.apiProject.Cat_and_Sub.User.Fragment1.LoginFragment;
import com.example.apiProject.Cat_and_Sub.User.Fragment1.SignUpFragment;
import com.example.apiProject.Utils.SharedPreference;
import com.example.apiProject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserActivity extends AppCompatActivity {
    TabLayout tab1;
    ViewPager2 view1;
    TextView txt1,txt2;
    LoginFragment loginFragment;
    SignUpFragment signUpFragment;
    SharedPreference sharedPreference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tab1=findViewById(R.id.tab1);
        view1=findViewById(R.id.view1);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        loginFragment=new LoginFragment();
        signUpFragment=new SignUpFragment();

        view1.setAdapter(new ViewPagerAdapter(UserActivity.this));
        new TabLayoutMediator(tab1, view1, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position==0)
                    tab.setText("Sign_In");
                else
                    tab.setText("Sign_Up");
            }
        }).attach();
        view1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    txt1.setText("Welcome");
                    txt2.setText("Log in to your account to get an update");

                } else {
                    txt1.setText("Hello!!");
                    txt2.setText("Register to create a new account");
                }
            }
        });
    }
    public static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position==0)
                return new LoginFragment();
            else
                return  new SignUpFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


}