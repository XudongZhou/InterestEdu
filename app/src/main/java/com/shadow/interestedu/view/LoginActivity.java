package com.shadow.interestedu.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.shadow.interestedu.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2016/9/26.
 */

public class LoginActivity extends Activity {

    @BindView(R.id.username)
    EditText userEditText;
    @BindView(R.id.password)
    EditText pwdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void login(View view) {

    }

    public void register(View view) {

    }
}
