package com.example.stockup.models;


import org.hibernate.validator.constraints.Range;
import javax.persistence.*;

@Entity
public class Purchase
{
    @Id
    @GeneratedValue
    private Long productId;

    private double cost;
    @Range(min=1)
    private int quantity;
    private String productName;


    public Long getProductId()
    {
        return productId;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }


}
