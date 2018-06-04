package com.example.jwick.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String EMAIL = "email";

    LoginButton loginButton;
    CallbackManager callbackManager;
    private AccessToken mAccessToken;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        logout = (Button) findViewById(R.id.logout);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                      Intent intent =new Intent(MainActivity.this,TwitterActivity.class);
                      startActivity(intent);
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

        // Callback registration

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            //    loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.e("DataFb32323", "==" + object.toString() + "///" + response.toString());

                                try {
                                    String fname = object.getString("first_name");
                                 /*   String email = object.getString("email");
                                    String fb_id = object.getString("id");
                                    String name = object.getString("name");*/
                                  //  String t = name.replaceAll("\\s+", "");
                                    Toast.makeText(MainActivity.this,fname,Toast.LENGTH_SHORT).show();

                                    // String urll = App.FACEBOOK_URL + "fb_id=" + fb_id + "&email=" + email + "&name=" + t;
                                    Intent intent =new Intent(MainActivity.this,TwitterActivity.class);
                                    startActivity(intent);
                                    //  Log.e("urll", "==" + urll + "///");
                                    Log.e("DataFb32323", "==" + object.toString() + "///" + response.toString());
                                    // loginapp(urll);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Application code
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
            }
        });
  /*  private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //You can fetch user info like this…
                            //object.getJSONObject(“picture”).
                        String ul=  object.getJSONObject("data").getString("url");
                        Toast.makeText(MainActivity.this,ul,Toast.LENGTH_SHORT).show();
                            Log.e("URL", ul);
                           // object.getString("name");
                            //object.getString(“email”));
                            //object.getString(“id”));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });*/
   /*     Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();*/


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
