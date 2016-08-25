package com.example.meghana.products.model;

/**
 * Created by meghana on 11/8/16.
 */
public class Orders {


    private String id;
    private String quantity;


    private String amount;

    public Orders(String id, String quantity, String amount) {
        this.id = id;
        this.quantity = quantity;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}