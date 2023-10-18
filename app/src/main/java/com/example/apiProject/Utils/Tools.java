package com.example.apiProject.Utils;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apiProject.R;

public class Tools {

        Context context;
        private Dialog dialog;

        public Tools() {
        }

        public Tools(Context context) {
            this.context = context;
            this.dialog = dialog; // Assign dialog parameter to the class member
        }

        public static boolean isValidEmail(String str) {
            if (!TextUtils.isEmpty(str)) {
                return Patterns.EMAIL_ADDRESS.matcher(str.toLowerCase()).matches();
            }
            return false;
        }

        public void showLoading() {
            try {
                if (dialog != null) {
                    dialog.setContentView(R.layout.loadingdialog);
                    dialog.setCancelable(false);
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stopLoading() {
            try {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void displayImage(Context context, ImageView img, String urlimg) {
            try {
                Glide.with(context)
                        .load(urlimg)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.chanel))
                        .into(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }