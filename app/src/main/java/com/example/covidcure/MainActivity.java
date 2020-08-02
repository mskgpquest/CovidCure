//////////////////////////////////////////////////////// APP - "COVID CURE" //////////////////////////////////////////////////////////////////////////////////
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/
/*  BUILT AND MODIFIED BY -
 *  MAULINDU SARKAR
 *  FOR TRACKING CORONAVIRUS REPORTS IN INDIA
 *  BUILT IN BETWEEN 29 JULY 2020 AND 1 AUGUST 2020
 *  LINK IN THE GOOGLE DRIVE
 *  mskgpquest2020@gmail.com
 *  02-August-2020 12.05 AM
 */
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/
package com.example.covidcure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //News
    private RecyclerView recyclerView;
    private RequestQueue requestQueue_for_news_recyclerview;
    private ExampleAdapter exampleAdapter;
    private ArrayList<ExampleItem> exampleList;

    //Track
    TextView textview_location_latitude,textview_location_longitude,textview_location_locality,textview_location_address,textview_location_countryname;
    FusedLocationProviderClient fusedLocationProviderClient;
    String lat,lang;
    private RequestQueue requestQueuetrack,requestQueuetrack1,requestQueuetrack2;
    Animation animation_side_by_side,animation_side_by_side2,animation_side_by_side3,animation_side_by_side4,animation_side_by_side5;

    //CovidInfo
    TextView confirmed_case_in_my_state,active_case_in_my_state,death_case_in_my_state,recovered_case_in_my_state;
    ProgressBar progressBar;
    GridLayout grid_layout_to_hold_covid_info_from_api_1;
    TextView textview_time_1;
    ///////////
    GridLayout grid_layout_to_hold_covid_info_from_api_2;
    TextView[] tv;
    TextView national_legends;
    ///////////


    //District
    private RecyclerView dist_recyclerview;
    private RequestQueue dist_requestQueue;
    private DistrictAdapter districtAdapter;
    private ArrayList<DistrictItem> distlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(),"THANKS FROM MAULINDU FOR USING THIS APP\nSTAY SAFE !",Toast.LENGTH_LONG).show();

        //News
        Button button= findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        exampleList = new ArrayList<>();
        requestQueue_for_news_recyclerview = Volley.newRequestQueue(this);
        parseJSON_news();

        //Track
        textview_location_latitude = (TextView) findViewById(R.id.textview_location_latitude);
        textview_location_longitude = (TextView) findViewById(R.id.textview_location_longitude);
        textview_location_locality = (TextView) findViewById(R.id.textview_location_locality);
        textview_location_address = (TextView) findViewById(R.id.textview_location_address);
        textview_location_countryname = (TextView) findViewById(R.id.textview_location_countryname);
        requestQueuetrack = Volley.newRequestQueue(this);
        requestQueuetrack1 = Volley.newRequestQueue(this);
        requestQueuetrack2 = Volley.newRequestQueue(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        animation_side_by_side = AnimationUtils.loadAnimation(this,R.anim.animation_side_by_side);
        animation_side_by_side2 = AnimationUtils.loadAnimation(this,R.anim.animation_side_by_side_2);
        animation_side_by_side3 = AnimationUtils.loadAnimation(this,R.anim.animation_side_by_side_3);
        animation_side_by_side4 = AnimationUtils.loadAnimation(this,R.anim.animation_side_by_side_4);
        animation_side_by_side5 = AnimationUtils.loadAnimation(this,R.anim.animation_side_by_side_5);

        //CovidInfo
        confirmed_case_in_my_state = (TextView) findViewById(R.id.confirmed_case_in_my_state);
        active_case_in_my_state = (TextView) findViewById(R.id.active_case_in_my_state);
        death_case_in_my_state = (TextView) findViewById(R.id.death_case_in_my_state);
        recovered_case_in_my_state = (TextView) findViewById(R.id.recovered_case_in_my_state);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        grid_layout_to_hold_covid_info_from_api_1 = (GridLayout) findViewById(R.id.grid_layout_to_hold_covid_info_from_api_1);
        textview_time_1 = (TextView) findViewById(R.id.textview_time_1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        tv = new TextView[38*5];
        grid_layout_to_hold_covid_info_from_api_2 = (GridLayout) findViewById(R.id.grid_layout_to_hold_covid_info_from_api_2);
        national_legends = (TextView) findViewById(R.id.national_legends);
        String text="<font color=#581845>CODE </font><font color=#e58600>CNFM</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color=#0000FF>ACTV</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color=#FF0000>DTH</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color=#008000>RCVD</font>";
        national_legends.setText(Html.fromHtml(text));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //District
        dist_recyclerview = (RecyclerView) findViewById(R.id.dist_recyclerview);
        dist_recyclerview.setHasFixedSize(true);
        dist_recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        distlist = new ArrayList<>();
        dist_requestQueue = Volley.newRequestQueue(this);
        //parseJSON_news();

    }

    //News
    private void parseJSON_news() {

        String url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=0bdb92a2fe57436c8e6f1df59075b89c";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject article = jsonArray.getJSONObject(i);

                                if(article.isNull("description") || article.isNull("urlToImage")) {
                                    continue;
                                }

                                if(article.getString("description").isEmpty() || article.getString("urlToImage").isEmpty())
                                    continue;

                                String title = article.getString("title");
                                String description = article.getString("description");

                                String date = article.getString("publishedAt");
                                String imageurl = article.getString("urlToImage");

                                //Button/////////////////////////////
                                final String newsurl = article.getString("url");
                                //////////////////////////////////////

                                exampleList.add(new ExampleItem(imageurl,title,description,date,newsurl));

                            }

                            exampleAdapter = new ExampleAdapter(MainActivity.this,exampleList);
                            recyclerView.setAdapter(exampleAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue_for_news_recyclerview.add(request);
    }

    //Track
    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //progressBar.setVisibility(View.INVISIBLE);

                        lat = String.valueOf(addresses.get(0).getLatitude());
                        lang = String.valueOf(addresses.get(0).getLongitude());

                        textview_location_latitude.setText(Html.fromHtml(
                                "<font color='#022D36'><b>Latitude :</b></font>"
                                        + addresses.get(0).getLatitude()
                        ));
                        textview_location_latitude.setAnimation(animation_side_by_side);

                        textview_location_longitude.setText(Html.fromHtml(
                                "<font color='#022D36'><b>Longitude :</b></font>"
                                        + addresses.get(0).getLongitude()
                        ));

                        textview_location_longitude.setAnimation(animation_side_by_side2);

                        textview_location_locality.setText(Html.fromHtml(
                                "<font color='#022D36'><b>Locality :</b></font>"
                                        + addresses.get(0).getLocality()
                        ));

                        textview_location_locality.setAnimation(animation_side_by_side3);

                        textview_location_address.setText(Html.fromHtml(
                                "<font color='#022D36'><b>Address :</b></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));

                        textview_location_address.setAnimation(animation_side_by_side4);

                        textview_location_countryname.setText(Html.fromHtml(
                                "<font color='#022D36'><b>Country :</b></font>"
                                        + addresses.get(0).getCountryName()
                        ));

                        textview_location_countryname.setAnimation(animation_side_by_side5);

                        parsejson_to_get_state_name_from_api(lat,lang);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void parsejson_to_get_state_name_from_api(String lat, String lang) {

        String url = "https://www.mapquestapi.com/geocoding/v1/reverse?key=M7RTqvEGAHlgxLarBzH8ZuIH8nVfUxaB&location="+lat+"%2C"+lang+"&outFormat=json&thumbMaps=false";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("results");
                            JSONObject obj1 = jsonArray.getJSONObject(0);
                            JSONArray arr1 = obj1.getJSONArray("locations");
                            JSONObject obj2 = arr1.getJSONObject(0);
                            String msg = "State: " + obj2.getString("adminArea3");

                            // Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                            parseJSON_to_get_statecode(obj2.getString("adminArea3"));
                            parsejson_to_get_district_info(obj2.getString("adminArea3"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueuetrack.add(request);
    }

    //District
    private void parsejson_to_get_district_info(final String dist_state_name) {

        String url = "https://api.covid19india.org/state_district_wise.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                                JSONObject mystate = response.getJSONObject(dist_state_name);
                                JSONObject mystate_district = mystate.getJSONObject("districtData");
                                Iterator<String> keys = mystate_district.keys();
                                while(keys.hasNext()) {
                                    String key = keys.next();
                                    JSONObject dist_data = mystate_district.getJSONObject(key);
                                    String confirm = "Confirm\n" + dist_data.getString("confirmed");
                                    String active = "Active\n" + dist_data.getString("active");
                                    String death = "Death\n" + dist_data.getString("deceased");
                                    String recovered = "Recovered\n" + dist_data.getString("recovered");
                                    distlist.add(new DistrictItem(key, confirm, active, death, recovered));
                                }

                                districtAdapter = new DistrictAdapter(MainActivity.this,distlist);
                                dist_recyclerview.setAdapter(districtAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        dist_requestQueue.add(request);


    }

    //Track
    private void parseJSON_to_get_statecode(final String state_name) {

        String url = "https://api.covid19india.org/state_district_wise.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            JSONObject statename = response.getJSONObject(state_name);
                            String msg = "Statecode : " + statename.getString("statecode");
                            // Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                            parseJSON_to_get_stateinfo(statename.getString("statecode"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueuetrack1.add(request);
    }

    private void parseJSON_to_get_stateinfo(final String statecode) {

        String url = "https://api.covid19india.org/data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {

                            JSONArray statewise = response.getJSONArray("statewise");
                            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            for(int i=0;i<statewise.length();i++)
                            {
                                JSONObject mystate = statewise.getJSONObject(i);
                                for(int j=0;j<5;j++)
                                {
                                    tv[i+j] = new TextView(MainActivity.this);
                                    tv[i+j].setLayoutParams(new ViewGroup.LayoutParams
                                            (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv[i+j].setTextSize(20);
                                    grid_layout_to_hold_covid_info_from_api_2.addView(tv[i+j]);
                                }
                                tv [i].setPadding(20, 25, 10, 25);
                                tv [i].setTextColor(Color.parseColor("#581845"));
                                tv[i].setText(mystate.getString("statecode"));

                                tv [i+1].setPadding(20, 25, 10, 25);
                                tv [i+1].setTextColor(Color.parseColor("#e58600"));
                                tv[i+1].setText(mystate.getString("confirmed"));

                                tv [i+2].setPadding(20, 25, 10, 25);
                                tv [i+2].setTextColor(Color.parseColor("#0000FF"));
                                tv[i+2].setText(mystate.getString("active"));

                                tv [i+3].setPadding(20, 25, 10, 25);
                                tv [i+3].setTextColor(Color.parseColor("#FF0000"));
                                tv[i+3].setText(mystate.getString("deaths"));

                                tv [i+4].setPadding(20, 25, 10, 25);
                                tv [i+4].setTextColor(Color.parseColor("#008000"));
                                tv[i+4].setText(mystate.getString("recovered"));

                            }
                            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                            int f = 0;
                            for(int i=0;i<statewise.length();i++)
                            {
                                JSONObject mystate = statewise.getJSONObject(i);
                                if(mystate.getString("statecode").equals(statecode))
                                {
                                    f = i;
                                    break;
                                }
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            grid_layout_to_hold_covid_info_from_api_1.setVisibility(View.VISIBLE);
                            String confirmed = Html.fromHtml("<font color='#022D36'><b>Confirmed :</b><br></font>") + statewise.getJSONObject(f).getString("confirmed");
                            String active = Html.fromHtml("<font color='#022D36'><b>Active :</b><br></font>") + statewise.getJSONObject(f).getString("active");
                            String death = Html.fromHtml("<font color='#022D36'><b>Death :</b><br></font>") + statewise.getJSONObject(f).getString("deaths");
                            String recovered = Html.fromHtml("<font color='#022D36'><b>Recovered :</b><br></font>") + statewise.getJSONObject(f).getString("recovered");
                            String last_update_time = "Last Update Date : " + statewise.getJSONObject(f).getString("lastupdatedtime");
                            confirmed_case_in_my_state.setText(confirmed);
                            active_case_in_my_state.setText(active);
                            death_case_in_my_state.setText(death);
                            recovered_case_in_my_state.setText(recovered);
                            textview_time_1.setText(last_update_time);
                            textview_time_1.setAnimation(animation_side_by_side5);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueuetrack2.add(request);

    }

}