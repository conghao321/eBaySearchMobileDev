package usc.csci571.ebays;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;


public class SearchResult extends AppCompatActivity {
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private String itemsListUrl;
    private String keyword;
    private ProgressBar progressBar;
    private TextView progresTextView;
    private TextView resultTextView;
    private RelativeLayout progressLayout;
    private StringBuilder sb=new StringBuilder();

    //recycle view components:
    private RecyclerView resultListView;
    private RecyclerView.Adapter adapter;
    private RelativeLayout noRecordsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        //adding a back-button to the new toolbar
        Toolbar searchToolBar =  (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(searchToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //refresh layout:
        mSwipeRefreshLayout = findViewById(R.id.swiperefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        Log.i("mych=refresh", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        refresh();
                    }
                }
        );


        //receive the intent
        Intent intent = getIntent();
        this.itemsListUrl=intent.getStringExtra(MainActivity.INPUT_DATA);
        Log.d("mychtotalUrl",itemsListUrl);
        this.keyword=intent.getStringExtra(MainActivity.KEY_WORD);
        search();
    }

    //adding a back-top button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void search(){
        //we need to get the views at first. and hide some of them.
        resultTextView = (TextView) findViewById(R.id.resultText);
        resultListView = (RecyclerView) findViewById(R.id.resultList);


        progresTextView=(TextView) findViewById(R.id.searchingText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progresTextView.setVisibility(View.VISIBLE);
        progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.VISIBLE);

        resultTextView.setVisibility(View.GONE);
        resultListView.setVisibility(View.GONE);

        noRecordsLayout=(RelativeLayout)findViewById(R.id.noRecordLayout);
        noRecordsLayout.setVisibility(View.GONE);


        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        resultListView.setLayoutManager(layoutManager);

        //This is the MAIN METHOD to request data from backend
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.itemsListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // pass the data by a mutable string builder object
                        sb.append(response);
                        Log.d("mychapp",sb.toString());
                        try {
                            display();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mychapp",error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void refresh(){
        this.sb.setLength(0);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        resultListView.setLayoutManager(layoutManager);

        //This is the MAIN METHOD to request data from backend
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.itemsListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // pass the data by a mutable string builder object
                        sb.append(response);
                        Log.d("mychapp",sb.toString());
                        try {
                            display();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mychapp",error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    //this method is used to deal the data and display them
    public void display() throws JSONException {
        final TextView resultView = (TextView) findViewById(R.id.resultText);

        /*
        * After receiving data by asynchronous call, we need to change the data type
        * 1.parse the json string to json array
        * 2.build the item previews object---->we need to show it to clients
        * 3.build an adapter to enable us using flexible data.
        * */
        String data=sb.toString();
        JSONArray jsonArray=new JSONArray(data);
        int len=jsonArray.length();
        Log.d("mychlen",String.valueOf(len));

        if(len>0){

            ItemPreview[] itemPreviews=new ItemPreview[len];
            for(int i=0;i<len;i++){
                itemPreviews[i]=new ItemPreview(jsonArray.get(i).toString());
            }

            //binding adapter with current activity
            adapter = new MyAdapter(itemPreviews,resultListView);
            resultListView.setAdapter(adapter);

            Log.d("mych",this.itemsListUrl);

            /*
             * Now we need to decorate the result page
             * include:
             * 1.set the search result's title
             * 2.add divider lines
             * 3.change the visibility
             * */
            //change the titleText:
            String searchTitle="Showing <font color=\"#3b68ee\">"+len+"</font> results for <font color=\"#3b68ee\">"+this.keyword+"</font>";
            CharSequence charSequence= Html.fromHtml(searchTitle);
            resultTextView.setText(charSequence);
            resultListView.addItemDecoration(new DividerItemDecoration(resultView.getContext(), DividerItemDecoration.HORIZONTAL));
            resultListView.addItemDecoration(new DividerItemDecoration(resultView.getContext(), DividerItemDecoration.VERTICAL));
            progressBar.setVisibility(View.GONE);
            progresTextView.setVisibility(View.GONE);
            progressLayout.setVisibility(View.GONE);
            resultTextView.setVisibility(View.VISIBLE);
            resultListView.setVisibility(View.VISIBLE);

        }else{
            noRecordsLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            progresTextView.setVisibility(View.GONE);
            progressLayout.setVisibility(View.GONE);

            Toast.makeText(getBaseContext(), "No records",
                    Toast.LENGTH_LONG).show();
        }
    }



}