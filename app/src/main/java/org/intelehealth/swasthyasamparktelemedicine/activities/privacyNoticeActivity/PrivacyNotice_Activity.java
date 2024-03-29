package org.intelehealth.swasthyasamparktelemedicine.activities.privacyNoticeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import org.intelehealth.swasthyasamparktelemedicine.R;
import org.intelehealth.swasthyasamparktelemedicine.app.AppConstants;
import org.intelehealth.swasthyasamparktelemedicine.utilities.FileUtils;
import org.intelehealth.swasthyasamparktelemedicine.utilities.SessionManager;

import org.intelehealth.swasthyasamparktelemedicine.activities.identificationActivity.IdentificationActivity;

public class PrivacyNotice_Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_DISABLE_ACTIONS = "EXTRA_DISABLE_ACTIONS";
    TextView privacy_textview;
    SessionManager sessionManager = null;
    private boolean hasLicense = false;
    Button accept, reject;
    MaterialCheckBox checkBox_cho;
    boolean disableActions;

    public static void start(Context context, boolean disableActions) {
        Intent starter = new Intent(context, PrivacyNotice_Activity.class);
        starter.putExtra(EXTRA_DISABLE_ACTIONS, disableActions);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);
        String language = sessionManager.getAppLanguage();
        //In case of crash still the app should hold the current lang fix.
        if (!language.equalsIgnoreCase("")) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        sessionManager.setCurrentLang(getResources().getConfiguration().locale.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_notice_2);
        setTitle(getString(R.string.privacy_notice_title));

        /*
         * Toolbar which displays back arrow on action bar
         * Add the below lines for every activity*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTheme);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        privacy_textview = findViewById(R.id.privacy_text);
        privacy_textview.setAutoLinkMask(Linkify.ALL);
        accept = findViewById(R.id.button_accept);
        reject = findViewById(R.id.button_reject);
        checkBox_cho = findViewById(R.id.checkbox_CHO);


        if (!sessionManager.getLicenseKey().isEmpty())
            hasLicense = true;

        //Check for license key and load the correct config file
        try {
            JSONObject obj = null;
            if (hasLicense) {
                obj = new JSONObject(Objects.requireNonNullElse(
                        FileUtils.readFileRoot(AppConstants.CONFIG_FILE_NAME, this),
                        String.valueOf(FileUtils.encodeJSON(this, AppConstants.CONFIG_FILE_NAME)))); //Load the config file

            } else {
                obj = new JSONObject(String.valueOf(FileUtils.encodeJSON(this, AppConstants.CONFIG_FILE_NAME)));
            }

//            SharedPreferences sharedPreferences = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
//            if(sharedPreferences.getAll().values().contains("cb"))
            Locale current = getResources().getConfiguration().locale;
//            if (current.toString().equals("cb")) {
//                String privacy_string = obj.getString("privacyNoticeText_Cebuano");
//                if (privacy_string.isEmpty()) {
//                    privacy_string = obj.getString("privacyNoticeText");
//                    privacy_textview.setText(privacy_string);
//                } else {
//                    privacy_textview.setText(privacy_string);
//                }
//
//            } else
            if (current.toString().equals("or")) {
                String privacy_string = obj.getString("privacyNoticeText_Odiya");
                if (privacy_string.isEmpty()) {
                    privacy_string = obj.getString("privacyNoticeText");
                    privacy_textview.setText(privacy_string);
                } else {
                    privacy_textview.setText(privacy_string);
                }

            } else if (current.toString().equals("hi")) {
                String privacy_string = obj.getString("privacyNoticeText_Hindi");
                if (privacy_string.isEmpty()) {
                    privacy_string = obj.getString("privacyNoticeText");
                    privacy_textview.setText(privacy_string);
                } else {
                    privacy_textview.setText(privacy_string);
                }

            } else if (current.toString().equals("kn")) {          //Privacy text support for Kannada
                String privacy_string = obj.getString("privacyNoticeText_Kannada");
                if (privacy_string.isEmpty()) {
                    privacy_string = obj.getString("privacyNoticeText");
                    privacy_textview.setText(privacy_string);
                } else {
                    privacy_textview.setText(privacy_string);
                }

            } else if (current.toString().equals("mr")) {
                String privacy_string = obj.getString("privacyNoticeText_Marathi");
                if (privacy_string.isEmpty()) {
                    privacy_string = obj.getString("privacyNoticeText");
                    privacy_textview.setText(privacy_string);
                } else {
                    privacy_textview.setText(privacy_string);
                }

            } else if (current.toString().equals("gu")) {
                String privacy_string = obj.getString("privacyNoticeText_Gujrathi");
                if (privacy_string.isEmpty()) {
                    privacy_string = obj.getString("privacyNoticeText");
                    privacy_textview.setText(privacy_string);
                } else {
                    privacy_textview.setText(privacy_string);
                }

            }
            //privacy notice support for Assamese language...
            else if (current.toString().equals("as")) {
                String privacy_string = obj.getString("privacyNoticeText_Assamese");
                if (privacy_string.isEmpty()) {
                    privacy_string = obj.getString("privacyNoticeText");
                    privacy_textview.setText(privacy_string);
                } else {
                    privacy_textview.setText(privacy_string);
                }
            }
            else {
                String privacy_string = obj.getString("privacyNoticeText");
                privacy_textview.setText(privacy_string);
            }

            accept.setOnClickListener(this);
            reject.setOnClickListener(this);

        } catch (JSONException e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            Toast.makeText(getApplicationContext(), "JsonException" + e, Toast.LENGTH_LONG).show();
        }

        disableActions = getIntent().getBooleanExtra(EXTRA_DISABLE_ACTIONS, false);
        // no need to accept here if redirected from identification activity
        if (disableActions) {
            accept.setVisibility(View.GONE);
            reject.setVisibility(View.GONE);
            checkBox_cho.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        if (checkBox_cho.isChecked() && v.getId() == R.id.button_accept) {

            //Clear HouseHold UUID from Session for new registration
            sessionManager.setHouseholdUuid("");

            Intent intent = new Intent(getApplicationContext(), IdentificationActivity.class);
            intent.putExtra("privacy", accept.getText().toString()); //privacy value send to identificationActivity
            Log.d("Privacy", "selected radio: " + accept.getText().toString());
            startActivity(intent);
        } else if (checkBox_cho.isChecked() && v.getId() == R.id.button_reject) {
            Toast.makeText(PrivacyNotice_Activity.this,
                    getString(R.string.privacy_reject_toast), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(PrivacyNotice_Activity.this,
                    getString(R.string.please_read_out_privacy_consent_first), Toast.LENGTH_SHORT).show();
        }

    }
}
