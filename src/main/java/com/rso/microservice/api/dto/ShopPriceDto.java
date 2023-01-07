package com.rso.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ShopPriceDto {

    @JsonProperty("id_shop")
    private Long idShop;

    @JsonProperty("shop_name")
    private String shopName;

    @JsonProperty("price_total_EUR")
    private Double priceTotalEUR;

    @JsonProperty("product_prices")
    private List<ProductPriceDto> productPrices;

    public ShopPriceDto() {
        this.productPrices = new ArrayList<>();
    }

    public Long getIdShop() {
        return idShop;
    }

    public void setIdShop(Long idShop) {
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

    public List<ProductPriceDto> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPriceDto> productPrices) {
        this.productPrices = productPrices;
    }

}
