package com.ishmam.feedmee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AdminActivity extends AppCompatActivity {

    private Button AdminLoginBtn;
    private EditText AdminLoginInputEmail,AdminLoginInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        AdminLoginBtn=(Button)findViewById(R.id.adminlogin_btn);
        AdminLoginInputEmail=(EditText)findViewById(R.id.admin_email_input);
        AdminLoginInputPassword=(EditText)findViewById(R.id.admin_password_input);

        AdminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = AdminLoginInputEmail.getText().toString();
                String password = AdminLoginInputPassword.getText().toString();
                if((email.equals("ias")) && (password.equals("123"))){
                    Toast.makeText(AdminActivity.this, "Welcome, Admin!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminActivity.this, AdminWork.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AdminActivity.this, "Wrong email/password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
