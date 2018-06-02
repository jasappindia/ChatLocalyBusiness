package com.chatlocalybusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;

/**
 * Created by Shiv on 12/6/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_notMember;
    private Button btn_adminLogin,btn_subAdminLogin,btn_workerLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_adminLogin=(Button)findViewById(R.id.btn_adminLogin);
        btn_subAdminLogin=(Button)findViewById(R.id.btn_subAdminLogin);
        btn_workerLogin=(Button)findViewById(R.id.btn_workerLogin);
        tv_notMember=(TextView)findViewById(R.id.tv_notMember);

        btn_adminLogin.setOnClickListener(this);
        btn_subAdminLogin.setOnClickListener(this);
        btn_workerLogin.setOnClickListener(this);
        tv_notMember.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId()))
        {
            case R.id.btn_adminLogin:
//               getSupportFragmentManager().beginTransaction().add(R.id.container_login, new LoginFragment()).commit();
                Intent intent=new Intent(this,OtpGenerateActivity.class);
                intent.putExtra(Constants.REGISTER_FLAG,Constants.ALREADY_REGISTER);
                intent.putExtra(Constants.LOGIN_AS,Constants.ADMIN);
                startActivity(intent);
                break;

            case R.id.btn_subAdminLogin:
                Intent intentSubAdmin=new Intent(this,OtpGenerateActivity.class);
                intentSubAdmin.putExtra(Constants.REGISTER_FLAG,Constants.ALREADY_REGISTER);
                intentSubAdmin.putExtra(Constants.LOGIN_AS,Constants.SUB_ADMIN);
                startActivity(intentSubAdmin);

//                startActivity(new Intent(this,OtpGenerateActivity.class));

                break;
            case R.id.btn_workerLogin:
                Intent intentWorker=new Intent(this,OtpGenerateActivity.class);
                intentWorker.putExtra(Constants.REGISTER_FLAG,Constants.ALREADY_REGISTER);
                intentWorker.putExtra(Constants.LOGIN_AS,Constants.WORKER);
                startActivity(intentWorker);

// startActivity(new Intent(this,OtpGenerateActivity.class));

                break;
            case R.id.tv_notMember:
                Intent intentNotMember=new Intent(this,GetStartedActivity.class);
//                intentNotMember.putExtra(Constants.REGISTER_FLAG,Constants.NEW_REGISTER);
//                intentNotMember.putExtra(Constants.LOGIN_AS,Constants.ADMIN);
                intentNotMember.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intentNotMember);
//                overridePendingTransition(R.anim.rotate_in,R.anim.rotate_out);

                finish();
                break;

        }
    }
}
