package com.example.bismillahyukbisayuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bismillahyukbisayuk.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText username, password;
    Button masuk;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        masuk = findViewById(R.id.btn_masuk);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usname = username.getText().toString()+"@email.com";
                String pass = password.getText().toString();

                if (usname.isEmpty()) {
                    username.setError("Username is required");
                    username.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Please Wait...");
                pd.show();

                auth.signInWithEmailAndPassword(usname, pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                            .child(auth.getUid());

                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            pd.dismiss();
                                            check();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            pd.dismiss();
                                        }
                                    });
                                }
                                else{
                                    pd.dismiss();
                                    Toast.makeText(LoginActivity.this, "Authetication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    private void check(){
        DatabaseReference reference = firebaseDatabase.getReference().child("Users").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String level = user.getLevel();
                if (level.equals("user")){
                    Intent mv = new Intent(LoginActivity.this, MainActivity.class);
                    mv.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mv);
                    finish();
                }
                else {
                    Intent mv2 = new Intent(LoginActivity.this, MainActivityPosyandu.class);
                    mv2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mv2);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}