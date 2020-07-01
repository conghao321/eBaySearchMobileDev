

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import java.lang.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String url="";
    public InputData inputData;
    public Validator validator;
    public String resString;

    public static final String  INPUT_DATA="edu.usc.CSCI571.ebays.FORM_DATA";
    public static final String  KEY_WORD="edu.usc.CSCI571.ebays.KEY_WORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Hide the status bar.
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    //
    @Override
    protected void onResume() {
        super.onResume();
        this.inputData=new InputData();
    };

    public void search(View view){
        associateData(view);
        validator=new Validator(this.inputData);
        displayError(validator,view);
        Log.d("mychapp","searched");

        if(validator.keyWordRequired&&validator.priceOk){
            Intent formDataIntent = new Intent(this, SearchResult.class);
            String searchedUrl = getUrl();
            formDataIntent.putExtra(INPUT_DATA,searchedUrl);
            formDataIntent.putExtra(KEY_WORD,this.inputData.keyWord);
            startActivity(formDataIntent);
        }
    }

    public void clear(View view){
        inputData=new InputData();
        Log.d("mychapp","chongzhile");
        setContentView(R.layout.activity_main);

    }

    public void associateData(View view){
        EditText keywordText=(EditText)findViewById(R.id.keywordinput);
        String keyWord=keywordText.getText().toString();
        EditText price1Text=(EditText)findViewById(R.id.price1);
        String price1=price1Text.getText().toString();
        EditText price2Text=(EditText)findViewById(R.id.price2);
        String price2=price2Text.getText().toString();
        CheckBox newItemText=(CheckBox)findViewById(R.id.newItem);
        boolean newItem=newItemText.isChecked();
        CheckBox usedText=(CheckBox)findViewById(R.id.used);
        boolean used=usedText.isChecked();
        CheckBox unspecText=(CheckBox)findViewById(R.id.unspec);
        boolean unspec=unspecText.isChecked();
        Spinner sortByText=(Spinner)findViewById(R.id.sortby);
        String sortby=sortByText.getSelectedItem().toString();

        this.inputData.keyWord=keyWord;
        this.inputData.newItem=newItem;
        this.inputData.used=used;
        this.inputData.unspec=unspec;
        this.inputData.sortBy=sortby;
        if(price1.length()==0){
            this.inputData.price1=0.0;
        }else{
            this.inputData.price1=Double.parseDouble(price1);
        }
        if(price2.length()==0){
            this.inputData.price2=999999.0;
        }else{
            this.inputData.price2=Double.parseDouble(price2);
        }
    }

    private void displayError(Validator validator,View view){
        if(!validator.isKeyWordRequired()){
            TextView keyError=(TextView)findViewById(R.id.errorView);
            keyError.setVisibility(view.VISIBLE);
        }

        if(!validator.isPriceOk()){
            TextView priceError=(TextView)findViewById(R.id.priceError);
            priceError.setVisibility(view.VISIBLE);
        }
    }

    private String getUrl(){
        this.url="";
        //url+="http://192.168.1.17:3000/itemsList?";
        url+="https://chebaymobilebackend.wm.r.appspot.com/itemsList?";
        String paramsUrl="keyWord="+this.inputData.keyWord+"&price1="+this.inputData.price1+
                "&price2="+this.inputData.price2 +"&newItem="+this.inputData.newItem+"&used="
                +this.inputData.used+"&unspec="+this.inputData.unspec+"&sortBy=";

        //four different sort order
        if(this.inputData.sortBy.equals("Best Match"))
            paramsUrl+="BestMatch";
        else if(this.inputData.sortBy.equals("Price: Highest first"))
            paramsUrl+="CurrentPriceHighest";
        else if (this.inputData.sortBy.equals("Price + Shipping: Highest first"))
            paramsUrl+="PricePlusShippingHighest";
        else
            paramsUrl+="PricePlusShippingLowest";



        this.url+=paramsUrl;
        return this.url;
    }

}