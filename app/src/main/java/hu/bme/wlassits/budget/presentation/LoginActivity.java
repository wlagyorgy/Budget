package hu.bme.wlassits.budget.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

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

        Log.e(TAG, "onCreate()");

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
                                        Globals.user = new User(first_name);
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
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Log.d(TAG, ">>>" + "Signed Out");
            return false;
        } else {
            Log.d(TAG, ">>>" + "Signed In");
            return true;
        }
    }


}
