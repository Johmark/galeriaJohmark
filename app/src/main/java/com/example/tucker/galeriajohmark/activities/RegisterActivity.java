package com.example.tucker.galeriajohmark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tucker.galeriajohmark.R;
import com.example.tucker.galeriajohmark.database.models.Users;
import com.example.tucker.galeriajohmark.database.models.Users_Table;
import com.example.tucker.galeriajohmark.utli.Functions;
import com.example.tucker.galeriajohmark.utli.Session;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by John on 3/26/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText roll;
    private ImageView image;

    private Session session;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registryview);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        roll = findViewById(R.id.roll);
        image = findViewById(R.id.imageLogin);

        session = new Session(this);
        Button registrar = findViewById(R.id.register);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    validar(username.getText().toString(), password.getText().toString(), roll.getText().toString());
                    goToRegistrar(username.getText().toString(), password.getText().toString(), roll.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        Functions.loadImage("http://reconciliasian.com/wp-content/uploads/2018/03/gallery-perfect-7-piece-wide-frame-set-modern-picture-frames-with-prepare.jpg", image, this);
    }

    private  void goToMain(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void validar(String username, String password, String roll) throws Exception
    {
        if (username.isEmpty())
        {
            throw new Exception("El nombre de usuario esta vacio");
        }
        if (password.isEmpty())
        {
            throw new Exception("Ingrese una contraseña");
        }
        if (roll.isEmpty())
        {
            throw new Exception("El roll del usuario esta vacio");
        }
    }

    private boolean goToRegistrar(String username, String password, String roll) throws Exception{
        boolean isLoggedIn= SQLite.selectCountOf().from(Users.class).where(Users_Table.username.eq(username))
                .and(Users_Table.password.eq(Functions.md5(password))).hasData();

        if (isLoggedIn)
        {
            throw new Exception("El Ussuario ya existe");
        }else{
            Users user = new Users();
            user.nombre = username;
            user.username = username;
            user.password = Functions.md5(password);
            user.roll = roll;
            user.save();
            Session session = new Session(getApplicationContext());
            session.createLoginSession(user.id, user.nombre, user.roll);
            goToMain();
        }
        return isLoggedIn;
    }

}
