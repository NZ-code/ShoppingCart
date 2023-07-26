package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.utils.Constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nphase.utils.Constants.DEFAULT_CATEGORY_NAME;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        Set<String> discountCategories = getDiscountCategories(shoppingCart);
        return shoppingCart.getProducts().stream()
                .map(product-> calculatePriceForProduct(product, discountCategories))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
    private Set<String> getDiscountCategories(ShoppingCart shoppingCart){
        Set<String> discountCategories;
        Stream<Product> productStream =shoppingCart.getProducts().stream();
        Map<String , Integer> categoryItemsMap = productStream
                .filter(product -> !product.getCategory().equals(DEFAULT_CATEGORY_NAME))
                .collect(
                Collectors.groupingBy( Product::getCategory, Collectors.summingInt(Product::getQuantity))
        );
        discountCategories = categoryItemsMap.keySet()
                .stream()
                .filter(category -> categoryItemsMap.get(category) >= Constants.ITEMS_TO_TRIGGER_CATEGORY_TYPE_DISCOUNT)
                .collect(Collectors.toSet());
        for(var cat : discountCategories){
            System.out.println(cat);
        }
        return discountCategories;
    }
    private BigDecimal calculatePriceForProduct(Product product, Set<String> discountCategories){
        BigDecimal discount = BigDecimal.ZERO;
        if(checkForBulkDiscount(product)){
            discount = Constants.PRODUCT_TYPE_DISCOUNT_IN_PERCENT.divide(BigDecimal.valueOf(100));
        }
        if(discountCategories.contains(product.getCategory())){
            discount = discount.add(Constants.CATEG0RY_TYPE_DISCOUNT_IN_PERCENT.divide(BigDecimal.valueOf(100)));
        }
        BigDecimal basePrice =product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
        return  basePrice.subtract(basePrice.multiply(discount));
    }
    private  boolean checkForBulkDiscount(Product product){
        if(product.getQuantity() >= Constants.ITEMS_TO_TRIGGER_PRODUCT_TYPE_DISCOUNT) return true;
        return false;
    }


}
