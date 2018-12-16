package com.test.newshop1.ui.checkoutActivity;

import com.test.newshop1.data.database.coupon.Coupon;
import com.test.newshop1.data.database.product.SimpleCategory;
import com.test.newshop1.data.database.shoppingcart.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CouponValidator {

    private static final String FIXED_CART = "fixed_cart";
    private static final String PERCENT = "percent";
    private static final String FIXED_PRODUCT = "fixed_product";

    private String resultStatus;
    private Float discountAmount;
    private List<CartItem> resultItems;
    private Float cartTotal;

    private final List<CartItem> initialItems;
    private final List<Coupon> coupons;

    public CouponValidator(List<CartItem> initialItems, List<Coupon> coupons){
        this.initialItems = initialItems;
        this.coupons = coupons;
        this.resultItems = new ArrayList<>();
        this.resultStatus = "";
        this.discountAmount = (float) 0;
        validate();
    }

    private void validate() {

        if (coupons == null || coupons.size() == 0) {
            invalidCouponException();
            return;
        }

        if (initialItems == null || initialItems.size() == 0) {
            emptyCartException();
            return;
        }

        this.cartTotal = calculateCartTotal(initialItems);

        final Coupon coupon = coupons.get(0);
        if (!validMinAmount(coupon)) {
            return;
        }

        for (CartItem item : initialItems) {
            if (!isValidCategories(coupon , item.getCategories())){
                return;
            }

            if (!isValidProducts(coupon, item)){
                return;
            }

            switch (coupon.getDiscountType()) {
                case FIXED_CART:
                    calcFixedCartDiscount(coupon);
                    break;

                case FIXED_PRODUCT:
                    calcFixedProductDiscount(coupon);
                    break;

                case PERCENT:
                    calcPercentDiscount(coupon);
                    break;

                default:
                    invalidCouponException();
            }

            updateItems(item);
        }

        this.resultStatus = (cartTotal.intValue() - calculateCartTotal(resultItems).intValue()) + " تومان تخفیف اعمال شد ";


    }

    private boolean isValidProducts(Coupon coupon, CartItem item) {
        List<Integer> validProducts = coupon.getProductIds();
        List<Integer> invalidProducts = coupon.getExcludedProductIds();

        boolean isContainValid = validProducts.contains(item.getProductId()) || validProducts.isEmpty();
        boolean isContainInvalid = invalidProducts.contains(item.getProductId());

        if (!isContainValid || isContainInvalid){
            invalidProductException(item.getName());
            return false;
        }

        return true;
    }



    private boolean isValidCategories(Coupon coupon, List<SimpleCategory> categories) {

        List<Integer> validCategories = coupon.getProductCategories();
        List<Integer> invalidCategories = coupon.getExcludedProductCategories();

        for (SimpleCategory category : categories) {
            boolean isContainValid = validCategories.contains(category.getId()) || validCategories.isEmpty();
            boolean isContainInvalid = invalidCategories.contains(category.getId());

            if (!isContainValid || isContainInvalid){
                invalidCategoryException(category.getName());
                return false;
            }
        }

        return true;
    }




    private boolean validMinAmount(Coupon coupon) {
        int minAmount = Float.valueOf(coupon.getMinimumAmount()).intValue();

        if (cartTotal < minAmount && minAmount != 0){
            minimumCartException(minAmount);
            return false;
        }
        return true;
    }

    private Float calculateCartTotal(List<CartItem> items) {
        Float total = (float) 0;
        for (CartItem item : items) {
            total += item.getQuantity() * Float.valueOf(item.getTotal());
        }
        return total;
    }

    private void calcPercentDiscount(Coupon coupon) {
        int discountPercent = Float.valueOf(coupon.getAmount()).intValue();
        int maxAmount = Float.valueOf(coupon.getMaximumAmount()).intValue();
        this.discountAmount = Math.min(cartTotal, maxAmount) * discountPercent / 100;
    }

    private void calcFixedProductDiscount(Coupon coupon) {
        //TODO
    }

    private void calcFixedCartDiscount(Coupon coupon) {
        Float discountAmount = Float.valueOf(coupon.getAmount());
        if (discountAmount < cartTotal){
            this.discountAmount = discountAmount;
        } else {
            this.discountAmount = cartTotal;
        }

    }

    private void updateItems(CartItem item){
        Float newPrice = Integer.valueOf(item.getSubtotal())*(1-discountAmount/cartTotal);
        CartItem resultItem = item.cloneItem();
        resultItem.setTotal(String.valueOf(newPrice));
        this.resultItems.add(resultItem);
    }

    private void invalidProductException(String productName) {
        discountAmount = (float) 0;
        resultStatus = "این کد برای محصول "+ productName + " قابل استفاده نیست" ;
        resultItems = initialItems;
    }

    private void invalidCategoryException(String categoryName) {
        discountAmount = (float) 0;
        resultStatus = "این کد برای دسته " + categoryName + " قابل استفاده نیست";
        resultItems = initialItems;
    }

    private void minimumCartException(int minAmount) {
        discountAmount = (float) 0;
        resultStatus = "حداقل خرید " + minAmount + " تومان";
        resultItems = initialItems;
    }

    private void invalidCouponException(){
        discountAmount = (float) 0;
        resultStatus = "کد تخفیف نا معتبر است";
        resultItems = initialItems;
    }

    private void emptyCartException(){
        discountAmount = (float) 0;
        resultStatus = "هیچ محصولی در سبد خرید وجود ندارد";
        resultItems = initialItems;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public List<CartItem> getResultItems() {
        return resultItems;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }
}
