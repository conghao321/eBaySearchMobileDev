
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ItemPreview {
    public String id;
    public String condition;
    public String imgUrl;
    public String topRated;
    public String handle;
    public String shipToLocations;
    public String expedited;
    public String oneDay;
    public String title;
    public String shippingCost;
    public String price;
    public String productLink;
    public String shippingInfo;




    public ItemPreview(String json) throws JSONException {
        JSONObject obj= (JSONObject) new JSONTokener(json).nextValue();
        this.id=obj.getString("id");
        this.title=obj.getString("title");
        this.imgUrl=obj.getString("imageURL");
        this.shippingCost=obj.getString("shippingCost");
        this.condition=obj.getString("condition");
        this.price=obj.getString("price");
        this.topRated=obj.getString("topRated");
        handle=obj.getString("handle");
        shipToLocations=obj.getString("shipToLocations");
        expedited=obj.getString("expedited");
        oneDay=obj.getString("oneDay");
        this.productLink=obj.getString("productLink");

        this.shippingInfo=obj.getString("shippingInfo");
    }
}
