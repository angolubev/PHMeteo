package agolubeff.phmeteo.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import agolubeff.phmeteo.R;
import agolubeff.phmeteo.app.AppConfig;
import agolubeff.phmeteo.app.AppController;
import agolubeff.phmeteo.helper.SQLiteHandler;

public class RegisterStationActivity extends AppCompatActivity
{
    private TextInputLayout code;
    private TextInputLayout name;
    private TextInputLayout description;
    private FloatingActionButton fab;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_station);

        db = new SQLiteHandler(getApplicationContext());

        code = findViewById(R.id.register_code);
        name = findViewById(R.id.register_name);
        description = findViewById(R.id.register_desc);

        fab = findViewById(R.id.fab_done);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (code.getEditText().getText().length() == 0) code.setError("empty!");
                else if(name.getEditText().getText().length() == 0) name.setError("empty!");
                else if(description.getEditText().getText().length() == 0) description.setError("empty!");
                else
                {

                    RegisterStation(db.getUserDetails().get("uid"), code.getEditText().getText().toString(), name.getEditText().getText().toString(), description.getEditText().getText().toString());
                    finish();
                }
            }
        });

    }



    private void RegisterStation(final String id_user, final String code, final String name, final String description)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //pDialog.setMessage("Registering ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER_STATION, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //Log.d(TAG, "Register Response: " + response.toString());
                //hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(error)
                    {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();*/
            }
        })
        {

            @Override
            protected Map<String, String> getParams()
            {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);
                params.put("id_station", code);
                params.put("name", name);
                params.put("description", description);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
