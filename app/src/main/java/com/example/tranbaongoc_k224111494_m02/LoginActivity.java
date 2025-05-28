package com.example.tranbaongoc_k224111494_m02;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tranbaongoc_k224111494_m02.models.Account;
import com.example.tranbaongoc_k224111494_m02.models.ListAccount;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName;
    EditText edtPassword;
    CheckBox chkSaveLoginInfor;

    private int backPressCount = 0;
    private static final long BACK_PRESS_INTERVAL = 2000;
    private long lastBackPressTime = 0;

    ListAccount listAccount = new ListAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        addViews();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        chkSaveLoginInfor = findViewById(R.id.chkSaveLoginInfor);
        listAccount.generate_sample_dataset();
    }


    public void do_login(View view) {
        String username = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra thông tin với ListAccount
        boolean isValid = false;
        for (Account acc : listAccount.getAccounts()) {
            if (acc.getUsername().equalsIgnoreCase(username) && acc.getPassword().equals(password)) {
                isValid = true;
                break;
            }
        }

        if (isValid) {
            Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login failed! Check your username and password.", Toast.LENGTH_LONG).show();
        }
    }

    public void do_exit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        Resources res = getResources();
        builder.setTitle(res.getText(R.string.title_confirm_exit_title));
        builder.setMessage(res.getText(R.string.title_confirm_exit_message));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(res.getText(R.string.title_confirm_exit_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        builder.setNegativeButton(res.getText(R.string.title_confirm_exit_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastBackPressTime > BACK_PRESS_INTERVAL) {
            backPressCount = 0;
        }

        backPressCount++;
        lastBackPressTime = currentTime;

        if (backPressCount == 3) {
            do_exit(null);
            backPressCount = 0;
        } else if (backPressCount < 3) {
            Toast.makeText(this, "Nhấn back " + (3 - backPressCount) + " lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
    }

    // Trường hợp gọi do_exit từ onBackPressed
    private void do_exit(Object o) {
        do_exit((View) o);
    }
    public void saveLoginInformation()
    {
        SharedPreferences preferences=getSharedPreferences("LOGIN_PREFERENCE",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String usr=edtUserName.getText().toString();
        String pwd=edtPassword.getText().toString();
        boolean isSave=chkSaveLoginInfor.isChecked();
        editor.putString("USER_NAME",usr);
        editor.putString("PASSWORD",pwd);
        editor.putBoolean("SAVED",isSave);
        editor.commit();
    }
    @Override
    protected void onPause(){
        super.onPause();
        saveLoginInformation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreLoginInformation();
    }

    public void restoreLoginInformation()
    {
        SharedPreferences preferences=getSharedPreferences("LOGIN_PREFERENCE",MODE_PRIVATE);
        String usr=preferences.getString("USER_NAME","");
        String pwd=preferences.getString("PASSWORD","");
        boolean isSave=preferences.getBoolean("SAVED",false);
        if(isSave)
        {
            edtUserName.setText(usr);
            edtPassword.setText(pwd);
            chkSaveLoginInfor.setChecked(isSave);
        }
    }
}
