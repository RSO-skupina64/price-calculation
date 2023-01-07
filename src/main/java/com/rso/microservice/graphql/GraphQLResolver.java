package com.rso.microservice.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLResolver implements GraphQLQueryResolver {
    private static final Logger log = LoggerFactory.getLogger(GraphQLResolver.class);

    public ResponseCalculatePrice calculatePrice(CalculatePriceInput input) {
        //todo: replace with actual price calculation
        log.info("calculate price with graphql");
        log.info("id[0]: {}", input.getProductList().get(0).getQuantity());

        ResponseCalculatePrice response = new ResponseCalculatePrice();

        ResponseShopPrice price = new ResponseShopPrice();
        price.setIdShop(1);
        price.setPriceTotalEUR(12.0);

        ResponseShopPrice price2 = new ResponseShopPrice();
        price2.setIdShop(2);
        price2.setPriceTotalEUR(36.0);

        List<ResponseShopPrice> list = List.of(
                 price, price2
        );

        response.setShopPrices(list);

        return response;
    }

}
