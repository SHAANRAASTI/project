package com.example.project.project.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.project.pojos.ResponseLogin;

import com.example.project.project.api.ApiClient;
import com.example.project.project.models.ModelUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import storage.SharedPrefHandler;


public class LoginActivity extends AppCompatActivity {

    private TextView tvRegister;
    private EditText inputEmail,inputPassword;
    private Button btnLogin;
    private ProgressBar progressBar2;
    private SharedPrefHandler sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar2 = findViewById(R.id.progressBar2);
        sp = SharedPrefHandler.getInstance(getApplicationContext());

        if(sp.isLoggedIn())
            //sendToHome();

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateData();
                }
            });
    }

    private void validateData()
    {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (email.isEmpty())
        {
            inputEmail.setError("Enter Email");
            inputEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            inputEmail.setError("Enter Valid Email");
            inputEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            inputPassword.setError("Enter Password");
            inputPassword.requestFocus();
        }
        else {
            doLogin(email,password);
        }


    }

    private void doLogin(String email, String password)
    {
        btnLogin.setEnabled(false);
        progressBar2.setVisibility(View.VISIBLE);
        Call<ResponseLogin> call = ApiClient.getInstance().getApi().login(email,password);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                btnLogin.setEnabled(true);
                progressBar2.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    ResponseLogin resp = response.body();
                    if (resp.isError())
                    {
                        Toast.makeText(LoginActivity.this, String.valueOf(resp.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ModelUser user = resp.getUser();
                        sp.saveUser(user);
                        Toast.makeText(LoginActivity.this, "Welcome ", Toast.LENGTH_SHORT).show();
                        //sendToHome();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                t.printStackTrace();
                btnLogin.setEnabled(true);
                progressBar2.setVisibility(View.GONE);
            }
        });
    }

//    private void sendToHome()
//    {
//        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);
//        finishAffinity();
//    }
}