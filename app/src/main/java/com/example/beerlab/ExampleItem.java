package com.example.beerlab;

public class ExampleItem {
    private String mImageUrl;
    private String mBeer;
    private String mDescription;
    private Long mPrice;

    public ExampleItem(String mImageUrl, String mBeer, String mDescription, Long mPrice) {
        this.mImageUrl = mImageUrl;
        this.mBeer = mBeer;
        this.mDescription = mDescription;
        this.mPrice = mPrice;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmBeer() {
        return mBeer;
    }

    public void setmBeer(String mBeer) {
        this.mBeer = mBeer;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Long getmPrice() {
        return mPrice;
    }

    public void setmPrice(Long mPrice) {
        this.mPrice = mPrice;
    }
}
