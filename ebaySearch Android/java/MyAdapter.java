package usc.csci571.ebays;

import android.content.Intent;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

//Author : hao cong
//Adapter就是给相应的动态布局来一个动态分配数据的东西
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ItemPreview[] data;//我们的数据
    RecyclerView resultList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //将数据内容，与子布局对应好
        public ImageView img;
        public TextView title;
        public TextView ship;
        public TextView condition;
        public TextView price;
        public TextView top;
        public MyViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.img);
            title=v.findViewById(R.id.title);
            ship=v.findViewById(R.id.shippingCost);
            condition=v.findViewById(R.id.condition);
            price=v.findViewById(R.id.priceView);
            top=v.findViewById(R.id.topratedView);

        }
    }

    public MyAdapter(ItemPreview[] myDataset,RecyclerView resultList) {
        this.resultList=resultList;
        this.data = myDataset;
    }

    //viewHolder表示选择viewGroup用哪一个形式，这里我们选择用myTextView也就是一个card的View
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // 更改子布局数据，这个函数invoked by manager
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ItemPreview cur = data[position];
        holder.title.setText(cur.title);
        //import glide to load the img...
        if(!cur.imgUrl.equals("https://thumbs1.ebaystatic.com/pict/04040_0.jpg")){
            Glide.with(resultList).load(cur.imgUrl).into(holder.img);
        }


        holder.price.setText("$"+cur.price);
        holder.condition.setText(cur.condition);


        double shippingMoney=Double.parseDouble(cur.shippingCost);
        if(shippingMoney==0.0){
            String free="<b>Free</b> Shipping";
            holder.ship.setText(Html.fromHtml(free));
        }else{
            holder.ship.setText("Ships for: $"+cur.shippingCost);
        }

        String topRated=cur.topRated;
        if(topRated.equals("true")){
            holder.top.setVisibility(View.VISIBLE);
        }else{
            holder.top.setVisibility(View.GONE);
        }


        Log.d("mych",holder.price.getText().toString());

        final String itemId=cur.id;
        final String expedited=cur.expedited;
        final String oneDay=cur.oneDay;
        final String shippingCost=cur.shippingCost;
        final String title=cur.title;
        final String price=cur.price;
        final String shipping=cur.shippingInfo;
        final String productLink=cur.productLink;



        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("mych","dianjile");

                // open another activity on item click
                Intent intent = new Intent(view.getContext(), CardDetail.class);
                intent.putExtra("id", itemId);
                intent.putExtra("expedited", expedited);
                intent.putExtra("oneDay", oneDay);
                intent.putExtra("shippingCost", shippingCost);
                intent.putExtra("title", title);
                intent.putExtra("link", productLink);

                intent.putExtra("price", price);
                intent.putExtra("shipping", shipping);


                view.getContext().startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
