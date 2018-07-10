package agolubeff.phmeteo.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import agolubeff.phmeteo.R;
import agolubeff.phmeteo.adapter.MeteostatoinsListAdapter;
import agolubeff.phmeteo.app.AppConfig;
import agolubeff.phmeteo.app.AppController;
import agolubeff.phmeteo.helper.SQLiteHandler;
import agolubeff.phmeteo.helper.SessionManager;
import agolubeff.phmeteo.model.Meteostation;

public class MeteostationsListActivity extends AppCompatActivity
{
    private static final String TAG = MeteostationsListActivity.class.getSimpleName();
    RecyclerView recycler_view;
    ArrayList<Meteostation> meteostation_list;
    MeteostatoinsListAdapter adapter;
    HashMap<String, String> user;
    private SQLiteHandler db;
    private SessionManager session;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteostations_list);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        user=db.getUserDetails();
        String uid = user.get("uid");

        getSupportActionBar().setTitle(user.get("name"));

        GetMeteostationList(uid);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MeteostationsListActivity.this, RegisterStationActivity.class));
            }
        });
        //InitRecyclerView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        GetMeteostationList(user.get("uid"));
        //InitRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_logout)
        {
            logoutUser();
        }
        return true;
    }

    private void logoutUser()
    {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MeteostationsListActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void InitRecyclerView()
    {
        LinearLayoutManager layout_manager = new LinearLayoutManager(this);
        recycler_view = findViewById(R.id.speakers_recycler_view);
        adapter = new MeteostatoinsListAdapter(this, meteostation_list);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);
    }

    private void GetMeteostationList(final String uid)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //pDialog.setMessage("Registering ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_GET_METEOSTATION_LIST, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //Log.d(TAG, "Register Response: " + response.toString());
                //hideDialog();

                try
                {
                    //JSONObject jObj = new JSONObject(response);
                    //boolean error = jObj.getBoolean("error");
                    meteostation_list = new ArrayList<>();
                    JSONArray json_array = new JSONArray(response);
                    for (int i=0; i< json_array.length(); i++)
                    {
                        JSONObject json = (JSONObject)json_array.get(i);

                            String name = json.getString("name");
                            int temperature = json.getInt("temperature");
                            long time = json.getLong("time");
                            int humidity = json.getInt("humidity");
                            int atmosphere_pressure = json.getInt("atmosphere_pressure");
                            meteostation_list.add(new Meteostation(name, temperature, time, humidity, atmosphere_pressure));

                    }
                    

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                InitRecyclerView();

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        })
        {

            @Override
            protected Map<String, String> getParams()
            {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", uid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
