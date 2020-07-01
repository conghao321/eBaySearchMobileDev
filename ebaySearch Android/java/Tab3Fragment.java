package usc.csci571.ebays;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;


public class Tab3Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_three, container, false);
        if(getArguments()!=null){
            LinearLayout shippingLayout=rootView.findViewById(R.id.shippingLayout);

            Bundle bundle=getArguments();
            String json=bundle.getString("shippingInfo");
            JSONObject shippingOBJ= null;

            try {
                shippingOBJ = (JSONObject) new JSONTokener(json).nextValue();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("mychapp",shippingOBJ.toString());
            Log.d("mychappkeys",String.valueOf(shippingOBJ.length()));


            for (Iterator<String> it = shippingOBJ.keys(); it.hasNext(); ) {
                String key = it.next();
                if(key.equals("shippingServiceCost"))
                    continue;
                String val= null;
                try {
                    val = shippingOBJ.getJSONArray(key).getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView newInfoView= new TextView(getContext());
                newInfoView.setTextSize(16);
                newInfoView.setPadding(20,10,0,10);
                key=getNewKey(key);
                String text="&nbsp;&nbsp;&nbsp;&nbsp;&bull;<b>"+key+"</b>:&nbsp;"+val;
                newInfoView.setText(Html.fromHtml(text));
                shippingLayout.addView(newInfoView);
            }


        }

        return rootView;
    }

    private String getNewKey(String s){
        StringBuilder sb=new StringBuilder();

        for(int i=0;i<s.length();i++){
            if(i==0) {
                sb.append(Character.toUpperCase(s.charAt(0)));
                continue;
            }
            if(Character.isUpperCase(s.charAt(i))) {
                sb.append(' ');
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}