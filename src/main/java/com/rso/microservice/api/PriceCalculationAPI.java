package com.rso.microservice.api;

import com.rso.microservice.api.dto.*;
import com.rso.microservice.service.PriceCalculationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/price")
@OpenAPIDefinition(info = @Info(title = "Price calculation API",
        description = "This is API documentation for Price Calculation Microservice",
        version = "0.1"))
@Tag(name = "Price Calculation")
public class PriceCalculationAPI {
    private static final Logger log = LoggerFactory.getLogger(PriceCalculationAPI.class);

    final PriceCalculationService priceCalculationService;

    @Autowired

    public PriceCalculationAPI(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Calculates price of products for all shops",
            description = "Calculates price of products in request for all shops")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prices",
                    content = @Content(schema = @Schema(implementation = PriceCalculationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<PriceCalculationResponseDto> calculatePrice(
            @Valid @RequestBody CalculatePriceRequestDto calculatePriceRequest) {
        log.info("fetchProductPrices ENTRY");
        PriceCalculationResponseDto priceCalculationResponse = new PriceCalculationResponseDto();
        priceCalculationResponse.setShopPrices(
                priceCalculationService.calculatePriceAllShops(calculatePriceRequest.getProductList()));
        log.info("fetchProductPrices EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(priceCalculationResponse);
    }

    @PostMapping(value = "/calculate/shop", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Calculates price of products for specific shop",
            description = "Calculates price of products in request for specific shop")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price",
                    content = @Content(schema = @Schema(implementation = ShopPriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ShopPriceDto> calculatePriceSpecificShop(
            @Valid @RequestBody CalculatePriceSpecificShopRequestDto calculatePriceSpecificShopRequest) {
        log.info("fetchProductPrices ENTRY");
        Long idShop = Long.parseLong(calculatePriceSpecificShopRequest.getIdShop());
        ShopPriceDto shopPrice = new ShopPriceDto();
        List<ShopPriceDto> shopPriceList = priceCalculationService.calculatePriceShop(idShop,
                calculatePriceSpecificShopRequest.getProductList());
        if (shopPriceList != null && shopPriceList.size() > 0) {
            shopPrice = shopPriceList.get(0);
        }
        log.info("fetchProductPrices EXIT");
        return ResponseEntity.status(HttpStatus.OK).body(shopPrice);
    }

}
