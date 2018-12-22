package com.test.newshop1.ui.loginActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.newshop1.R;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel mViewModel;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(LoginActivityViewModel.class);
        mViewModel.getStatus().observe(this, this::updateUI);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        progressBar = findViewById(R.id.login_progress);

        findViewById(R.id.email_sign_in_button).setOnClickListener(view -> mViewModel.login(mEmailView.getText().toString(), mPasswordView.getText().toString(), false));



    }

    private void updateUI(LoginStatus loginStatus) {
        progressBar.setVisibility(View.INVISIBLE);
        switch (loginStatus) {
            case ON_PROGRESS:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case SUCCESSFUL:
                Toast.makeText(this,R.string.successful_login_toast_message, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case FAILED:
                Toast.makeText(this, R.string.failed_login_toast_message, Toast.LENGTH_SHORT).show();
                break;
            case WRONG_PASS:
                Toast.makeText(this, R.string.wrong_pass_login_toast_message, Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_acticity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
