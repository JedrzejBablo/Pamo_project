package com.example.beerlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.model.Beer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.BeerListViewHolder> {
    private Context mContext;
    private List<Beer> mExampleList;

    public BeerListAdapter(MenuFragment context, List<Beer> exampleList) {
        mContext = context.getActivity();
        mExampleList = exampleList;
    }

    @Override
    public BeerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.beer_list_item, parent, false);
        return new BeerListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerListViewHolder holder, int position) {
        Beer currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImgUrl();
        String beerName = currentItem.getBrand();
        String description = currentItem.getDescription();
        Double price = currentItem.getPrice();

        holder.mTextViewBeer.setText(beerName);
        holder.mTextViewDescription.setText("Description: " + description);
        holder.mTextViewPrice.setText("Price: " + price);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class BeerListViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        public ImageView mImageView;
        public TextView mTextViewBeer;
        public TextView mTextViewDescription;
        public TextView mTextViewPrice;

        public BeerListViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView_menu);
            mTextViewBeer = itemView.findViewById(R.id.textView_beerName);
            mTextViewDescription = itemView.findViewById(R.id.textView_description);
            mTextViewPrice = itemView.findViewById(R.id.textView_price);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){

        }

    }


}
