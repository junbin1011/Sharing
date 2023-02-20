package com.jkb.junbin.sharing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jkb.junbin.sharing.callback.CallBack;
import com.jkb.junbin.sharing.controller.AccountController;
import com.sharing.R;



public class LoginActivity extends AppCompatActivity {
    AccountController accountController = new AccountController();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            accountController.login(LoginActivity.this, username, password, new CallBack() {
                @Override
                public void success(String result) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

                @Override
                public void filed(String message) {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}