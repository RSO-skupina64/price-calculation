package com.rso.microservice.graphql.mapper;

import com.rso.microservice.api.dto.ProductCalculationDto;
import com.rso.microservice.api.dto.ShopPriceDto;
import com.rso.microservice.graphql.ProductToCalculate;
import com.rso.microservice.graphql.ResponseShopPrice;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GraphQLResolverMapper {

    List<ProductCalculationDto> toModelProductCalculationDto(List<ProductToCalculate> productToCalculate);

    List<ResponseShopPrice> toModelResponseShopPrice(List<ShopPriceDto> shopPriceDto);
}
