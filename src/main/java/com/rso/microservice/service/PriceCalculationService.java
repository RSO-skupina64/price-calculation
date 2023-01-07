package com.rso.microservice.service;


import com.rso.microservice.api.dto.*;
import com.rso.microservice.entity.*;
import com.rso.microservice.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceCalculationService {
    private static final Logger log = LoggerFactory.getLogger(PriceCalculationService.class);

    final ProductService productService;

    public PriceCalculationService(ProductService productService) {
        this.productService = productService;
    }

    public PriceCalculationResponseDto calculatePrice(CalculatePriceRequestDto favoriteProductRequest) {
        PriceCalculationResponseDto priceCalculationResponseDto = new PriceCalculationResponseDto();

        // for each of the products, we need to update the price, based on the shop, price and quantity
        for (ProductCalculationDto productCalculationDto : favoriteProductRequest.getProductList()) {
            Optional<Product> optionalProduct = productService.findById(productCalculationDto.getId());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                // for each of the shops that have the product, update the price in the response
                for (ProductShop productShop : product.getShopProducts()) {
                    // in the shop does not exist yet in the response, we need to create it
                    ShopPriceDto shopPriceDto = priceCalculationResponseDto.getShopPrices().stream()
                            .filter(sp -> sp.getIdShop().equals(productShop.getShop().getId()))
                            .findFirst()
                            .orElseGet(() -> {
                                ShopPriceDto sp = new ShopPriceDto();
                                sp.setIdShop(productShop.getShop().getId());
                                sp.setShopName(productShop.getShop().getName());
                                sp.setPriceTotalEUR(0D);
                                priceCalculationResponseDto.getShopPrices().add(sp);
                                return sp;
                            });

                    shopPriceDto.setPriceTotalEUR(
                            shopPriceDto.getPriceTotalEUR() + productShop.getPriceEUR().doubleValue() * productCalculationDto.getQuantity());

                    ProductPriceDto productPriceDto = new ProductPriceDto(productShop.getPriceEUR().doubleValue(),
                            product.getId(), product.getName(), productCalculationDto.getQuantity(), null);
                    shopPriceDto.getProductPrices().add(productPriceDto);
                }
            }
        }

        return priceCalculationResponseDto;
    }

}
