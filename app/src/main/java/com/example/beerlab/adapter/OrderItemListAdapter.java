package com.example.beerlab.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.fragment.CartFragment;
import com.example.beerlab.R;
import com.example.beerlab.model.OrderItem;
import com.example.beerlab.utils.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.OrderItemViewHolder> {

    private Context orderItemContext;
    private List<OrderItem> orderItems;
    private OrderItemListAdapter.OnItemClickListener listener;
    private MyApplication myApplication;



    public interface OnItemClickListener {
        void increaseQuantity(int position);
        void decreaseQuantity(int position);
        void deleteFromCart(int position);
    }

    public void setOnItemClickListener(OrderItemListAdapter.OnItemClickListener listener) { this.listener = listener;}



    public OrderItem getOrderItem(int position){
        return orderItems.get(position);
    }

    public OrderItemListAdapter(CartFragment orderItemContext, List<OrderItem> orderItems) {
        this.orderItemContext = orderItemContext.getActivity();
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(orderItemContext).inflate(R.layout.order_item_list_item, parent, false);
        OrderItemViewHolder holder = new OrderItemViewHolder(view, listener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        OrderItem currentItem = orderItems.get(position);

        String imageUrl = currentItem.getBeerDto().getImgUrl();
        String beerName = currentItem.getBeerDto().getBrand();
        int quantity = currentItem.getQuantity();
        double price = currentItem.getUnitPrice();

        holder.orderItemBrandView.setText(beerName);
        holder.orderItemQuantityView.setText("Quantity: " + quantity);
        holder.orderItemUnitPriceView.setText("Unit price: "+ (int) price);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.orderItemImageView);

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }


    public class OrderItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView orderItemImageView;
        public TextView orderItemBrandView;
        public TextView orderItemUnitPriceView;
        public TextView orderItemQuantityView;
        public Button increaseQuantityButton;
        public Button decreaseQuantityButton;
        public Button deleteFromCartButton;

        public OrderItemViewHolder(View orderItemView, final OrderItemListAdapter.OnItemClickListener onItemClickListener){
            super(orderItemView);
            orderItemImageView = itemView.findViewById(R.id.imageView_menu);
            orderItemBrandView = itemView.findViewById(R.id.textView_beerName);
            orderItemUnitPriceView = itemView.findViewById(R.id.textView_price);
            orderItemQuantityView = itemView.findViewById(R.id.textView_quantity);

            increaseQuantityButton = orderItemView.findViewById(R.id.button_increaseAmount);
            decreaseQuantityButton = orderItemView.findViewById(R.id.button_decreaseAmount);
            deleteFromCartButton = orderItemView.findViewById(R.id.button_deleteFromCart);

            increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.increaseQuantity(position);
                        }
                    }
                }

            });

            decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.decreaseQuantity(position);
                        }
                    }
                }
            });

            deleteFromCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.deleteFromCart(position);
                        }
                    }
                }
            });


        }



    }

}
