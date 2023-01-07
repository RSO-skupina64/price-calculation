package com.rso.microservice.service;


import com.rso.microservice.api.dto.*;
import com.rso.microservice.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceCalculationService {

    final ProductService productService;

    public PriceCalculationService(ProductService productService) {
        this.productService = productService;
    }

    public List<ShopPriceDto> calculatePriceAllShops(List<ProductCalculationDto> productList) {
        return calculatePriceShop(null, productList);
    }

    public List<ShopPriceDto> calculatePriceShop(Long idShop, List<ProductCalculationDto> productList) {
        List<ShopPriceDto> shopPrices = new ArrayList<>();

        // for each of the products, we need to update the price, based on the shop, price and quantity
        for (ProductCalculationDto productCalculationDto : productList) {
            Optional<Product> optionalProduct = productService.findById(productCalculationDto.getId());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                // filter to only the selected shop
                if (idShop != null) {
                    List<ProductShop> productShops =
                            product.getShopProducts().stream()
                                    .filter(ps -> ps.getShop().getId().equals(idShop))
                                    .toList();
                    product.setShopProducts(productShops);
                }

                // for each of the shops that have the product, update the price in the response
                for (ProductShop productShop : product.getShopProducts()) {
                    // in the shop does not exist yet in the response, we need to create it
                    ShopPriceDto shopPrice = shopPrices.stream()
                            .filter(sp -> sp.getIdShop().equals(productShop.getShop().getId()))
                            .findFirst()
                            .orElseGet(() -> {
                                ShopPriceDto sp = new ShopPriceDto();
                                sp.setIdShop(productShop.getShop().getId());
                                sp.setShopName(productShop.getShop().getName());
                                sp.setPriceTotalEUR(0D);
                                shopPrices.add(sp);
                                return sp;
                            });

                    shopPrice.setPriceTotalEUR(
                            shopPrice.getPriceTotalEUR() + productShop.getPriceEUR().doubleValue() * productCalculationDto.getQuantity());

                    ProductPriceDto productPriceDto = new ProductPriceDto(productShop.getPriceEUR().doubleValue(),
                            product.getId(), product.getName(), productCalculationDto.getQuantity(), null);
                    shopPrice.getProductPrices().add(productPriceDto);
                }
            }
        }

        return shopPrices;
    }

}
