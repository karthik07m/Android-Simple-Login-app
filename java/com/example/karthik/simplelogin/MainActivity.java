package com.example.karthik.simplelogin;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText userName ;
    private EditText password;
    private Button   login;
    private TextView  attempt, btnSignUp;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText)findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        login  =(Button) findViewById(R.id.btnLogin);
        attempt = (TextView) findViewById(R.id.textView2);
        btnSignUp = (TextView) findViewById(R.id.tvSingUP);

        attempt.setText("No of attempts:" + counter);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser User = firebaseAuth.getCurrentUser();

        if (User !=null )
        {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(userName.getText().toString(), password.getText().toString());
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });

    }

    private void validate(String userName , String pass)

    {

        firebaseAuth.signInWithEmailAndPassword(userName, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Login was Sucessfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    counter --;
                    attempt.setText("No of attempts:" + counter);
                    if (counter == 0)
                    {
                        login.setEnabled(false);
                    }
                }
            }
        });
    }

}
