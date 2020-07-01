

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class Tab1Fragment extends Fragment {
    private Bundle bundle;
    private FragsViewModel fragsViewModel;
    View rootView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bundle=getArguments();
        View rootView=inflater.inflate(R.layout.fragment_one, container, false);
        ArrayList imgUrls=null;

        if(getArguments()!=null){
            Log.d("mychfrag1","entering frag1");
            bundle=getArguments();
            imgUrls=bundle.getStringArrayList("urlsList");
            Log.d("mych-frag1","enter11 frag1");
            LinearLayout imgsLayout=rootView.findViewById(R.id.imgsLayout);

            for(int i=0;i<imgUrls.size();i++){
                String curURL=(String)imgUrls.get(i);
                ImageView imgView=new ImageView(getContext());
                Picasso.get().load(curURL).into(imgView);

                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(800, 1000);
                imgsLayout.addView(imgView);
                imgView.setMaxHeight(1000);
                imgView.setLayoutParams(imgParams);
            }
            TextView detailTitle=rootView.findViewById(R.id.detailTitle);
            TextView detailPrice=rootView.findViewById(R.id.detailprice);
            TextView detailShippingCost=rootView.findViewById(R.id.detailshippingcost);

            detailTitle.setText(bundle.getString("title"));
            detailPrice.setText("$"+bundle.getString("price"));
            double ship=Double.parseDouble(bundle.getString("shippingCost"));
            if(ship==0.0){
                String free="<b>Free</b> Shipping";
                detailShippingCost.setText(Html.fromHtml(free));
            }else{
                detailShippingCost.setText("Shipping for: $"+bundle.getString("shippingCost"));
            }

            //frag1
            LinearLayout productfeaturelayout=rootView.findViewById(R.id.productfeaturelayout);
            LinearLayout subtitleLayout=rootView.findViewById(R.id.subtitleLayout);
            LinearLayout brandLayout=rootView.findViewById(R.id.brandLayout);
            productfeaturelayout.setVisibility(View.GONE);




            String json=bundle.getString("json");
            JSONObject obj= null;
            try {
                obj = (JSONObject) new JSONTokener(json).nextValue();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            LinearLayout specLayout=rootView.findViewById(R.id.specLayout);
            productfeaturelayout.setVisibility(View.GONE);
            specLayout.setVisibility(View.GONE);

            String subtitle= null;
            try {
                subtitle = obj.getString("subtitle");
            } catch (JSONException e) {
                subtitle="";
            }
            if(subtitle.length()>0){
                productfeaturelayout.setVisibility(View.VISIBLE);
                TextView subtitleView=(TextView)rootView.findViewById(R.id.subtitleView);
                subtitleView.setText(subtitle);
                subtitleView.setVisibility(View.VISIBLE);
            }else{
                subtitleLayout.setVisibility(View.GONE);
            }

            boolean product=false;
            try {
                JSONArray nameList = obj.getJSONArray("nameList");
                for (int i = 0; i < nameList.length(); i++) {
                    JSONObject brandObj = nameList.getJSONObject(i);
                    if (brandObj.getString("Name").equals("Brand")) {
                        productfeaturelayout.setVisibility(View.VISIBLE);
                        TextView brandView = (TextView) rootView.findViewById(R.id.brandView);
                        String brandName = brandObj.getJSONArray("Value").getString(0);
                        brandView.setText(brandName);
                        brandView.setVisibility(View.VISIBLE);
                        product=true;
                        nameList.remove(i);
                        break;
                    } else {
                        brandLayout.setVisibility(View.GONE);
                    }
                }

                if(!product&&subtitle.length()==0){
                    productfeaturelayout.setVisibility(View.GONE);
                }



                JSONArray values = obj.getJSONArray("nameList");
                Log.d("mych-namelist", values.toString());
                LinearLayout valuesLayout = (LinearLayout) rootView.findViewById(R.id.valuesLayout);
                if (obj.has("nameList")) {
                    int names=0;
                    for (int i = 0; i < values.length(); i++) {
                        TextView valueView = new TextView(getContext());
                        JSONObject valueOBJ = nameList.getJSONObject(i);
                        if (valueOBJ.has("Brand") && valueOBJ.getString("Brand").equals("Brand")) {
                            continue;
                        }
                        String val = valueOBJ.getJSONArray("Value").getString(0);
                        val= "&bull; "+val;
                        valueView.setPadding(200,10,50,10);
                        valueView.setText(Html.fromHtml(val));
                        valuesLayout.addView(valueView);
                        valuesLayout.setVisibility(View.VISIBLE);
                        specLayout.setVisibility(View.VISIBLE);
                        names++;
                        if(names==5) break;
                    }

                } else {
                    valuesLayout.setVisibility(View.GONE);
                    specLayout.setVisibility(View.GONE);
                }
            }
                catch(JSONException e){

                }

            }
        return rootView;
    }

    public void setFrag1Info(JSONObject obj) throws JSONException {

        JSONArray imgsArray=obj.getJSONArray("imgsURL");
        LinearLayout imgsLayout=rootView.findViewById(R.id.imgsLayout);

        for(int i=0;i<imgsArray.length();i++){
            String curURL=imgsArray.getString(i);
            ImageView imgView=new ImageView(getContext());
            Picasso.get().load(curURL).into(imgView);

            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(800, 1000);
            imgsLayout.addView(imgView);
            imgView.setMaxHeight(1000);
            imgView.setLayoutParams(imgParams);
        }


        TextView detailTitle=rootView.findViewById(R.id.detailTitle);
        TextView detailPrice=rootView.findViewById(R.id.detailprice);
        TextView detailShippingCost=rootView.findViewById(R.id.detailshippingcost);

        detailTitle.setText(fragsViewModel.title);
        detailPrice.setText("$"+fragsViewModel.price);
        detailShippingCost.setText("Shipping for: $"+fragsViewModel.shippingCost);

        LinearLayout productfeaturelayout=rootView.findViewById(R.id.productfeaturelayout);
        LinearLayout subtitleLayout=rootView.findViewById(R.id.subtitleLayout);
        LinearLayout brandLayout=rootView.findViewById(R.id.brandLayout);

        LinearLayout specLayout=rootView.findViewById(R.id.specLayout);
        productfeaturelayout.setVisibility(View.GONE);
        specLayout.setVisibility(View.GONE);

        String subtitle=obj.getString("subtitle");
        if(subtitle.length()>0){
            productfeaturelayout.setVisibility(View.VISIBLE);
            TextView subtitleView=(TextView)rootView.findViewById(R.id.subtitleView);
            subtitleView.setText(subtitle);
            subtitleView.setVisibility(View.VISIBLE);
        }else{
            subtitleLayout.setVisibility(View.GONE);
        }


        JSONArray nameList=obj.getJSONArray("nameList");

        for(int i=0;i<nameList.length();i++){
            JSONObject brandObj=nameList.getJSONObject(i);
            if(brandObj.getString("Name").equals("Brand")){
                productfeaturelayout.setVisibility(View.VISIBLE);
                TextView brandView=(TextView)rootView.findViewById(R.id.brandView);
                String brandName=brandObj.getJSONArray("Value").getString(0);
                brandView.setText(brandName);
                brandView.setVisibility(View.VISIBLE);
                nameList.remove(i);
            }else{
                brandLayout.setVisibility(View.GONE);
            }

        }


        JSONArray values=obj.getJSONArray("nameList");
        LinearLayout valuesLayout=(LinearLayout)rootView.findViewById(R.id.valuesLayout);
        if(obj.has("nameList")){

            for(int i=0;i<values.length();i++){
                TextView valueView=new TextView(getContext());
                JSONObject valueOBJ=nameList.getJSONObject(i);
                if(valueOBJ.has("Brand")&&valueOBJ.getString("Brand").equals("Brand")){
                    continue;
                }
                String val=valueOBJ.getJSONArray("Value").getString(0);
                valueView.setText(val);
                valuesLayout.addView(valueView);
                valuesLayout.setVisibility(View.VISIBLE);
                specLayout.setVisibility(View.VISIBLE);
            }

        }else{
            valuesLayout.setVisibility(View.GONE);
            specLayout.setVisibility(View.GONE);
        }
    };
}