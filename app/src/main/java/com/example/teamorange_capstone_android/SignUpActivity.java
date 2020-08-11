package com.example.teamorange_capstone_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DatePicker datePicker;
    TextInputLayout fullName, emailID, passWord, phone_number;
    Button registerBtn;
    CountryCodePicker countryCodePicker;


    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

       //Initializing awesomeValidation object
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Hooks
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);
        fullName = findViewById(R.id.signup_fullname);
        emailID = findViewById(R.id.signup_emailId);
        passWord = findViewById(R.id.signup_password);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phone_number = findViewById(R.id.signup_phone_number);
        registerBtn = findViewById(R.id.signup_register_button);


        //Adding validation to editTexts
        awesomeValidation.addValidation(this, R.id.signup_fullname, "[a-zA-Z\\s]+", R.string.firstNameError);
        awesomeValidation.addValidation(this, R.id.signup_emailId, Patterns.EMAIL_ADDRESS, R.string.EmailError);
        awesomeValidation.addValidation(this, R.id.signup_phone_number, "^[2-9]{2}[0-9]{8}$", R.string.PhoneNumberError);
        awesomeValidation.addValidation(this, R.id.signup_password, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}", R.string.PasswordError);



    }



    }