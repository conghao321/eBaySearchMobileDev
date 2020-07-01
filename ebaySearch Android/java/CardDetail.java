import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Iterator;

public class CardDetail extends AppCompatActivity {
    /*
     * DATA FIELDS
     * */
    StringBuilder sb=new StringBuilder();
    public String productLink;
    public String title;
    public String shippingCost;
    public String price;
    public String itemId;
    public String itemURL;

    /*
    *
    * */

    private RelativeLayout progressLayout;
    private ProgressBar progressBar;
    private TextView itemTitleText;
    private TextView progresTextView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;
    private String shippingInfo;
    private LinearLayout shippingLayout;
    private Fragment frag1;
    private Fragment frag2;
    private Fragment frag3;
    private JSONObject obj;
    private FragsViewModel fragsViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        //adding a back-button to the new toolbar
        Toolbar cardToolBar =  (Toolbar) findViewById(R.id.cardTooBar);
        setSupportActionBar(cardToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        Intent intent =getIntent();
        this.itemId=intent.getStringExtra("id");
        this.itemURL="https://chebaymobilebackend.wm.r.appspot.com/itemCard?id="+this.itemId+"";
        this.shippingCost=intent.getStringExtra("shippingCost");
        this.title=intent.getStringExtra("title");
        this.price=intent.getStringExtra("price");
        this.shippingInfo=intent.getStringExtra("shipping");
        this.productLink=intent.getStringExtra("link");


        cardToolBar.setTitle(this.title);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        adapter = new TabAdapter(getSupportFragmentManager());
        frag1=new Tab1Fragment();
        frag2=new Tab2Fragment();
        frag3=new Tab3Fragment();

        fetchItem();
    }

    @SuppressLint("ResourceAsColor")
    private void setTabs(){
        Resources resources = getResources();
        Drawable icon1 = resources.getDrawable(R.drawable.information_variant_unselected);
        Drawable icon2 = resources.getDrawable(R.drawable.ic_seller);
        icon2.setTint(getResources().getColor(R.color.app_blue_eBay));
        Drawable icon3 = resources.getDrawable(R.drawable.truck_delivery_selector);
        tabLayout.getTabAt(0).setIcon(icon1);
        tabLayout.getTabAt(1).setIcon(icon2);
        tabLayout.getTabAt(2).setIcon(icon3);
    }

    public void fetchItem() {

        progresTextView=(TextView) findViewById(R.id.cardSearchingText);
        progressBar = (ProgressBar)findViewById(R.id.cardProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progresTextView.setVisibility(View.VISIBLE);
        progressLayout = (RelativeLayout) findViewById(R.id.cardProgressLayout);
        progressLayout.setVisibility(View.VISIBLE);


        //This is the MAIN METHOD to request data from backend
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.itemURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // pass the data by a mutable string builder object
                        sb.append(response);
                        try {
                            showDetails();
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

    public void showDetails () throws JSONException{

        obj= (JSONObject) new JSONTokener(sb.toString()).nextValue();

        Bundle bundle1=getBundle1(obj);
        Bundle bundle2=getBundle2(obj);
        Bundle bundle3=getBundle3(shippingInfo);

        Log.d("mych","bundle1");
        adapter.addFragment(frag1, "Product",bundle1);
        adapter.addFragment(frag2, "Seller Info",bundle2);
        adapter.addFragment(frag3, "Shipping",bundle3);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabs();

        progressBar.setVisibility(View.GONE);
        progresTextView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);

    }
    //get three bundles...
    private Bundle getBundle1(JSONObject obj) throws JSONException {
        Bundle bundle=new Bundle();
        JSONArray imgsArray=obj.getJSONArray("imgsURL");
        ArrayList<String>  urlsList=new ArrayList<>();

        for(int i=0;i<imgsArray.length();i++){
            String curURL=imgsArray.getString(i);
            urlsList.add(curURL);
        }
        bundle.putString("price",this.price);
        bundle.putString("title",this.title);
        bundle.putString("shippingCost",this.shippingCost);
        bundle.putStringArrayList("urlsList",urlsList);
        bundle.putString("json",obj.toString());

        return bundle;
    }

    private Bundle getBundle2(JSONObject obj) throws JSONException{
        Bundle bundle=new Bundle();

        bundle.putString("json",obj.toString());

        return bundle;
    }

    private Bundle getBundle3(String shipInfo){
        Bundle bundle=new Bundle();
        bundle.putString("shippingInfo",shipInfo);
        return bundle;
    }



    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.redirect, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

            if (id == R.id.redirect) {
                Uri uri=Uri.parse(this.productLink);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}