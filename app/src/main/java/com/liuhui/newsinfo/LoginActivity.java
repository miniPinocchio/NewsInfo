package com.liuhui.newsinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 登录页
 */
public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    @BindView(R.id.iv_settings)
    ImageView ivSettings;
    private String name;
    private boolean isRememberUser = true;
    private int currentType = 1;
    private String password;
    private AppCompatCheckBox remainUserId;
    private EditText etUserid;
    private EditText etPassword;
    private Button btnLogin;
    private ImageView deleteAllUser;
    private ImageView deleteAllPassword;
    /**
     * 登录方式
     */
    private static final int LOGIN_TYPE_CODE = 1, LOGIN_TYPE_SFZH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //判断用户是否存储账号
        name = Hawk.get(ApiConfig.USER_NAME, null);
        remainUserId = (AppCompatCheckBox) findViewById(R.id.cb_remain_user_id);
        etUserid = (EditText) findViewById(R.id.et_login_user_id);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        deleteAllUser = (ImageView) findViewById(R.id.iv_delete_all_user);
        deleteAllPassword = (ImageView) findViewById(R.id.iv_delete_all_password);
        if (name != null) {
            remainUserId.setChecked(true);
            etUserid.setText(name);
        }
        remainUserId.setOnCheckedChangeListener(this);
        etUserid.addTextChangedListener(mTextWatcher);
        etPassword.addTextChangedListener(mTextWatcher);
        btnLogin.setOnClickListener(this);
        deleteAllUser.setOnClickListener(this);
        deleteAllPassword.setOnClickListener(this);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_delete_all_user:
                etUserid.setText("");
                break;
            case R.id.iv_delete_all_password:
                etPassword.setText("");
                break;
            case R.id.btn_login:
                name = etUserid.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (name.length() == 18) {
                    currentType = 2;
                } else {
                    currentType = 1;
                }
//                Login(name,password);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "账号或密码格式不对", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    /**
     * 账号密码限制条件
     *
     * @return
     */
    public boolean validate() {
        boolean valid = true;

        name = etUserid.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (name.isEmpty() || name.length() < 3) {
            etUserid.setError("用户名最少3位");
            valid = false;
        } else {
            etUserid.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("密码必须是4-10位");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        if (currentType == LOGIN_TYPE_CODE) {
            if (TextUtils.isEmpty(name)) {
                etUserid.setError("警号不能为空");
                return false;
            }
            if (name.length() != 6) {
                etUserid.setError("警号输入错误");
                return false;
            }
        }
        if (currentType == LOGIN_TYPE_SFZH) {
            if (TextUtils.isEmpty(name)) {
                etUserid.setError("身份证号不能为空");
                return false;
            }
            if (name.length() != 18) {
                etUserid.setError("身份证号输入错误");
                return false;
            }
        }

        return valid;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isRememberUser = isChecked;
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (etUserid.getText().length() <= 0) {
                deleteAllUser.setVisibility(View.GONE);
            } else {
                deleteAllUser.setVisibility(View.VISIBLE);
            }
            if (etPassword.getText().length() <= 0) {
                deleteAllPassword.setVisibility(View.GONE);
            } else {
                deleteAllPassword.setVisibility(View.VISIBLE);
            }

            if (etUserid.getText().length() == 0 || etPassword.getText().length() == 0) {
                btnLogin.setEnabled(false);
            } else {
                btnLogin.setEnabled(true);
            }

        }
    };

    /**
     * 登录
     *
     * @param encodeUserName 账号
     * @param encodePassWd   密码
     */
    private void Login(String encodeUserName, String encodePassWd) {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        if (isRememberUser) {
            Hawk.put(ApiConfig.USER_NAME, name);
        } else {
            Hawk.delete(ApiConfig.USER_NAME);
        }

    }

}
