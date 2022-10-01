package com.kazimasum.qrdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultActivity_adapter extends RecyclerView.Adapter<ResultActivity_adapter.ViewHolder> {
    List<String> titles;
    List<Integer> images;
    List<String> hiddenId;
    List<String> prices;
    List<String> quantity;
    List<String> description;
    Context context;
    LayoutInflater inflater;


    // kapg gusto mag add new Array88888
    public ResultActivity_adapter(Context ctx, List<String> titles, List<Integer> images, List<String> hiddenId, List<String> prices, List<String> quantity, List<String> description){
        this.titles = titles;
        this.images=images;
        this.hiddenId = hiddenId;
        this.prices = prices;
        this.quantity = quantity;// kapg gusto mag add new Array 88888
        this.description  = description;
        this.inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.customlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.hiddenId.setText(hiddenId.get(position));
        //holder.getPrice.setText(prices.get(position));
        holder.gridIcon.setImageResource(images.get(position));
        //holder.quantity.setText(quantity.get(position));// 88888 kapg gusto mag add new Array 88888
        holder.descriptionTV.setText(description.get(position));

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{//======For the click 3:10
        public TextView title, getPrice, hiddenId, descriptionTV,totalCH; // kapg gusto mag add new Array
        public ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemId);
            gridIcon = itemView.findViewById(R.id.imageViewF);
            //getPrice = itemView.findViewById(R.id.priceF);
            hiddenId = itemView.findViewById(R.id.hidden_idF);
            //quantity = itemView.findViewById(R.id.quantityF);// kapg gusto mag add new Array 88888
            descriptionTV = itemView.findViewById(R.id.descriptionF);
          // totalCH = itemView.findViewById(R.id.totalCH);
        }
    }

}

