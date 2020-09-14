package com.example.michele.lavoro;



/**
 * Created by Michele on 03/07/2019.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.michele.lavoro.connessione.LoginAsync;
import com.example.michele.lavoro.entity.Profilo;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity {
    private EditText nicknameEdit, passwordEdit;
    private Button login;
    private String nickname, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        nicknameEdit = (EditText) findViewById(R.id.nickname);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setBackgroundResource(R.drawable.buttonshape_true);
                nickname = nicknameEdit.getText().toString();
                password = passwordEdit.getText().toString();

                if(nickname.length() > 0 && password.length() > 0) {  //fare controllo sicurezza password se necessario

                        Profilo profilo=new Profilo(nickname,password);
                        String loginProfilo = new Gson().toJson(profilo, Profilo.class);
                        new LoginAsync(LoginActivity.this).execute(loginProfilo);
                }
            }
        });

    }

}
