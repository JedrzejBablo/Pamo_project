package com.example.beerlab.model;

/**
 * Class that holds Beer object for requesting
 */
public class Beer {
    private Long id;
    private String description;
    private String imgUrl;
    private String brand;
    private Double price;
    private Integer quantity;
    private Double minimalPrice;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getMinimalPrice() {
        return minimalPrice;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", brand=" + brand +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", minimalPrice=" + minimalPrice +
                '}';
    }
}
