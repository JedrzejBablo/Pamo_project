package com.example.beerlab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.fragment.MenuFragment;
import com.example.beerlab.R;
import com.example.beerlab.model.Beer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Class BeerListAdapter is responsible for setting values to recycler view
 */
public class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.BeerListViewHolder> {
    /**
     Context of a fragment
     */
    private Context mContext;
    /**
     * List of beers that will be used
     */
    private List<Beer> mExampleList;
    /**
     * Listener to handle with button clicks
     */
    private OnItemClickListener listener;


    /**
     * Interface of a listener methods that have to be implemented in activity/fragment class
     */
    public interface OnItemClickListener {
        /**
         * onItemClick method is responsible on what to do when you click the button
         * @param position
         */
        void onItemClick(int position);
    }

    /**
     * setOnItemClickListener method is responsible for setting appropriate listener
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener;}


    /**
     * getBeer function is responsible for get appropriate beer from mExampleList
     * @param position
     * @return
     */
    public Beer getBeer(int position){
        return mExampleList.get(position);
    }


    /**
     * Constructor of a BeerListAdapterClass. Params above are required to use other methods in this class
     * @param context
     * @param exampleList
     */
    public BeerListAdapter(MenuFragment context, List<Beer> exampleList) {
        mContext = context.getActivity();
        mExampleList = exampleList;

    }

    /**
     * onCreateViewHolder function is responsible for what to do when we create a holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BeerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.beer_list_item, parent, false);
        return new BeerListViewHolder(v, listener);
    }

    /**
     * onBindViewHolder function is responsible for binding beer data from mExampleList to BeerListViewHolder function
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull BeerListViewHolder holder, int position) {
        Beer currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImgUrl();
        String beerName = currentItem.getBrand();
        String description = currentItem.getDescription();
        Double price = currentItem.getPrice();

        holder.mTextViewBeer.setText(beerName);
        holder.mTextViewDescription.setText(description);
        holder.mTextViewPrice.setText( "Price: " + price);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    /**
     * getItemCount function is responsible for counting mExample list length
     * @return
     */
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    /**
     * BeerListViewHolder class is responsible for set values to view
     */
    public static class BeerListViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewBeer;
        public TextView mTextViewDescription;
        public TextView mTextViewPrice;
        public Button orderAddToCartButton;

        /**
         * BeerListViewHolder class is responsible for set values to itemView and also for manage the button clicks
         * @param itemView
         * @param onItemClickListener
         */
        public BeerListViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView_menu);
            mTextViewBeer = itemView.findViewById(R.id.textView_beerName);
            mTextViewDescription = itemView.findViewById(R.id.textView_description);
            mTextViewPrice = itemView.findViewById(R.id.textView_price);
            orderAddToCartButton = itemView.findViewById(R.id.button_addToCart);


            orderAddToCartButton.setOnClickListener(new View.OnClickListener(){

                /**
                 * onclick function here is responsible for what function call when button is clicked,
                 * it wont't do anything if onItemClickListener is null or recycler view have no position
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }

            });
        }

    }


}
