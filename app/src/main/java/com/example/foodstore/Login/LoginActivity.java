package com.example.foodstore.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.MainActivity;
import com.example.foodstore.R;
import com.example.foodstore.Registration.RegistrationActivity;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private String url = "http://192.168.56.1/foodstore/checkPass.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        editTextUsername = findViewById(R.id.input_username);
        editTextPassword = findViewById(R.id.input_password);
    }

    public void Registration(View view) {
        Intent intent = new Intent(LoginActivity.this , RegistrationActivity.class);
        startActivity(intent);
    }

    public void Login(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.trim().equals("success")) {
                        startActivity(new Intent(LoginActivity.this , MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this , "Thông tin sai", Toast.LENGTH_LONG).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this , error.toString() , Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("user",editTextUsername.getText().toString().trim());
                params.put("password" ,editTextPassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void DangNhap(View view) {
        if (editTextUsername.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this , "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
        }else {
            Login(url);
        }
    }

}
