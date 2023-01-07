package com.rso.microservice.graphql;

import java.util.List;

public class CalculatePriceInput {

    private List<ProductToCalculate> productList;

    public List<ProductToCalculate> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductToCalculate> productList) {
        this.productList = productList;
    }
}
