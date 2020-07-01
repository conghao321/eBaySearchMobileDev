

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;


public class Tab2Fragment extends Fragment {
    View rootView;
    Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_two, container, false);
        bundle=getArguments();


        if(getArguments()!=null){
            String json=bundle.getString("json");
            JSONObject obj= null;
            try {
                obj = (JSONObject) new JSONTokener(json).nextValue();
                setFrag2Info(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return  rootView;
    }

    private void setFrag2Info(JSONObject obj) throws JSONException {

        int section=0;
        JSONObject sellerOBJ=obj.getJSONObject("seller");
        JSONObject returnOBJ=obj.getJSONObject("return");
        LinearLayout sellerInfoLayout=rootView.findViewById(R.id.sellerInfoLayout);
        LinearLayout returnPolicyLayout=rootView.findViewById(R.id.returnPolicyLayout);


        for (Iterator<String> it = sellerOBJ.keys(); it.hasNext(); ) {
            String key = it.next();
            String val=sellerOBJ.getString(key);
            TextView newInfoView= new TextView(getContext());
            newInfoView.setTextSize(16);
            newInfoView.setPadding(20,10,0,10);
            key=getNewKey(key);
            String text="&nbsp;&nbsp;&nbsp;&nbsp;&bull;<b>"+key+"</b>:&nbsp;"+val;
            newInfoView.setText(Html.fromHtml(text));
            sellerInfoLayout.addView(newInfoView);
        }

        if(sellerOBJ.length()==0){
            sellerInfoLayout.setVisibility(View.GONE);
        }else{
            section++;
        }

        for (Iterator<String> it = returnOBJ.keys(); it.hasNext(); ) {
            String key = it.next();
            String val=returnOBJ.getString(key);
            TextView newInfoView= new TextView(getContext());
            newInfoView.setTextSize(16);
            newInfoView.setPadding(20,10,0,10);
            key=getNewKey(key);
            String text="&nbsp;&nbsp;&nbsp;&nbsp;&bull;<b>"+key+"</b>:&nbsp;"+val;
            newInfoView.setText(Html.fromHtml(text));
            returnPolicyLayout.addView(newInfoView);
        }

        if(returnOBJ.length()==0){
            returnPolicyLayout.setVisibility(View.GONE);
        }else{
            section++;
        }
        ImageView divider=rootView.findViewById(R.id.divider2);
        if(section==2){
            divider.setVisibility(View.VISIBLE);
        }else{
            divider.setVisibility(View.GONE);
        }

    }

    private String getNewKey(String s){
        StringBuilder sb=new StringBuilder();
        if(s.equals("UserID")){
            return "User ID";
        }

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