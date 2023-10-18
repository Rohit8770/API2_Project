package com.example.apiProject.Cat_and_Sub.User.UserNetwork;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse  implements Serializable, Parcelable {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private String status;
        public final static Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {

            public LoginResponse createFromParcel(android.os.Parcel in) {
                return new LoginResponse(in);
            }
            public LoginResponse[] newArray(int size) {
                return (new LoginResponse[size]);
            }
        }
                ;
        private final static long serialVersionUID = -8255244651325502182L;

        @SuppressWarnings({
                "unchecked"
        })
        protected LoginResponse(android.os.Parcel in) {
            this.userId = ((String) in.readValue((String.class.getClassLoader())));
            this.firstName = ((String) in.readValue((String.class.getClassLoader())));
            this.lastName = ((String) in.readValue((String.class.getClassLoader())));
            this.message = ((String) in.readValue((String.class.getClassLoader())));
            this.status = ((String) in.readValue((String.class.getClassLoader())));
        }

        public LoginResponse() {
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void writeToParcel(android.os.Parcel dest, int flags) {
            dest.writeValue(userId);
            dest.writeValue(firstName);
            dest.writeValue(lastName);
            dest.writeValue(message);
            dest.writeValue(status);
        }

        public int describeContents() {
            return  0;
        }

    }