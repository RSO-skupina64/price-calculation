package com.rso.microservice.graphql;

import java.util.List;

public class ResponseCalculatePrice {

    private List<ResponseShopPrice> shopPrices;

    public List<ResponseShopPrice> getShopPrices() {
        return shopPrices;
    }

    public void setShopPrices(List<ResponseShopPrice> shopPrices) {
        this.shopPrices = shopPrices;
    }
}
