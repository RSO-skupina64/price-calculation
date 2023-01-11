package com.rso.microservice.graphql;

import com.rso.microservice.graphql.mapper.GraphQLResolverMapper;
import com.rso.microservice.service.PriceCalculationService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLResolver implements GraphQLQueryResolver {
    private static final Logger log = LoggerFactory.getLogger(GraphQLResolver.class);

    final PriceCalculationService priceCalculationService;

    final GraphQLResolverMapper graphQLResolverMapper;

    @Autowired

    public GraphQLResolver(PriceCalculationService priceCalculationService, GraphQLResolverMapper graphQLResolverMapper) {
        this.priceCalculationService = priceCalculationService;
        this.graphQLResolverMapper = graphQLResolverMapper;
    }

    public ResponseCalculatePrice calculatePrice(CalculatePriceInput input) {
        log.info("calculate price with graphql");

        ResponseCalculatePrice response = new ResponseCalculatePrice();
        response.setShopPrices(graphQLResolverMapper.toModelResponseShopPrice(
                priceCalculationService.calculatePriceAllShops(
                        graphQLResolverMapper.toModelProductCalculationDto(input.getProductList()))));

        return response;
    }

}
