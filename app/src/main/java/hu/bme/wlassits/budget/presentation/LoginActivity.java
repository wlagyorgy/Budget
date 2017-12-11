package hu.bme.wlassits.budget.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.User;

public class LoginActivity extends BaseActivity {


    private static final String TAG = "LoginActivity";
    CallbackManager callbackManager;
    LoginButton btnFacebookLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Facebook api-hoz kell egy hash, azt itt generáljuk
        printHashKey(this);


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Globals.user = new User();
        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
        btnFacebookLogin.setVisibility(View.VISIBLE);

        if (isLoggedIn()) {
            navigateToDashboard();
        } else {


            callbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code

                            GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.d("JSON", "" + response.getJSONObject().toString());

                                    try {

                                        String name = object.getString("name");
                                        String first_name = object.optString("first_name");
                                        String last_name = object.optString("last_name");
                                        String facebookId = object.getString("id");

                                        Globals.user.setFirst_name(first_name);
                                        Globals.user.setFacebookIdentifier(facebookId);


                                        btnFacebookLogin.setVisibility(View.GONE);
                                        navigateToDashboard();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,first_name,last_name");
                            graphRequest.setParameters(parameters);
                            graphRequest.executeAsync();


                        }

                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                        }
                    });


            btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void navigateToDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }


    private boolean isLoggedIn() {
        //check login
        Profile profile = Profile.getCurrentProfile();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            return false;
        } else {
            Globals.user.setFacebookIdentifier(profile.getId());
            Globals.user.setFirst_name(profile.getFirstName());
            return true;
        }
    }

    public  void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }


}
