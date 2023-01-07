package com.rso.microservice.graphql;

import java.util.List;

public class ResponseShopPrice {

    private long idShop;
    private String shopName;
    private Double priceTotalEUR;
    private List<ProductPrice> productPrices;

    public long getIdShop() {
        return idShop;
    }

    public void setIdShop(long idShop) {
        this.idShop = idShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Double getPriceTotalEUR() {
        return priceTotalEUR;
    }

    public void setPriceTotalEUR(Double priceTotalEUR) {
        this.priceTotalEUR = priceTotalEUR;
    }

    public List<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }
}
