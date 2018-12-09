
package com.test.newshop1.data.database.order;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.newshop1.data.database.customer.Billing;
import com.test.newshop1.data.database.customer.Shipping;
import com.test.newshop1.data.database.order.converter.BillingConverter;
import com.test.newshop1.data.database.order.converter.LineItemConverter;
import com.test.newshop1.data.database.order.converter.ShippingConverter;
import com.test.newshop1.data.database.shoppingcart.CartItem;

@Entity(tableName = "order")
public class Order {

    public static final String PENDING = "pending";
    public static final String PROCESSING = "processing";
    public static final String ON_HOLD = "on-hold";
    public static final String COMPLETED = "completed";
    public static final String CANCELLED = "cancelled";
    public static final String REFUNDED = "refunded";
    public static final String FAILED = "failed";
    public static final String TRASH = "trash";

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    @SerializedName("date_modified")
    @Expose
    private String dateModified;


    @SerializedName("discount_total")
    @Expose
    private String discountTotal;

    @SerializedName("shipping_total")
    @Expose
    private String shippingTotal;


    @SerializedName("total")
    @Expose
    private String total;


    @SerializedName("customer_id")
    @Expose
    private Integer customerId;

    @SerializedName("customer_note")
    @Expose
    private String customerNote;

    @SerializedName("billing")
    @Expose
    @TypeConverters(BillingConverter.class)
    private Billing billing;

    @SerializedName("shipping")
    @Expose
    @TypeConverters(ShippingConverter.class)
    private Shipping shipping;

    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;

    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;

    @SerializedName("line_items")
    @Expose
    @TypeConverters(LineItemConverter.class)
    private List<CartItem> lineItems = null;

    @SerializedName("transaction_id")
    @Expose
    private String transactionId;





    @SerializedName("set_paid")
    @Ignore
    private boolean paid;

    @SerializedName("parent_id")
    @Expose
    @Ignore
    private Integer parentId;

    @SerializedName("number")
    @Expose
    @Ignore
    private String number;

    @SerializedName("order_key")
    @Expose
    @Ignore
    private String orderKey;

    @SerializedName("created_via")
    @Expose
    @Ignore
    private String createdVia;

    @SerializedName("version")
    @Expose
    @Ignore
    private String version;

    @SerializedName("date_created_gmt")
    @Expose
    @Ignore
    private String dateCreatedGmt;

    @SerializedName("date_modified_gmt")
    @Expose
    @Ignore
    private String dateModifiedGmt;

    @SerializedName("discount_tax")
    @Expose
    @Ignore
    private String discountTax;

    @SerializedName("shipping_tax")
    @Expose
    @Ignore
    private String shippingTax;

    @SerializedName("cart_tax")
    @Expose
    @Ignore
    private String cartTax;

    @SerializedName("total_tax")
    @Expose
    @Ignore
    private String totalTax;

    @SerializedName("prices_include_tax")
    @Expose
    @Ignore
    private Boolean pricesIncludeTax;

    @SerializedName("customer_ip_address")
    @Expose
    @Ignore
    private String customerIpAddress;

    @SerializedName("customer_user_agent")
    @Expose
    @Ignore
    private String customerUserAgent;

    @SerializedName("date_paid")
    @Expose
    @Ignore
    private Object datePaid;

    @SerializedName("date_paid_gmt")
    @Expose
    @Ignore
    private Object datePaidGmt;

    @SerializedName("date_completed")
    @Expose
    @Ignore
    private Object dateCompleted;

    @SerializedName("date_completed_gmt")
    @Expose
    @Ignore
    private Object dateCompletedGmt;

    @SerializedName("cart_hash")
    @Expose
    @Ignore
    private String cartHash;

    @SerializedName("meta_data")
    @Expose
    @Ignore
    private List<Object> metaData = null;

    @SerializedName("tax_lines")
    @Expose
    @Ignore
    private List<Object> taxLines = null;

    @SerializedName("shipping_lines")
    @Expose
    @Ignore
    private List<ShippingLine> shippingLines = null;

    @SerializedName("fee_lines")
    @Expose
    @Ignore
    private List<Object> feeLines = null;

    @SerializedName("coupon_lines")
    @Expose
    @Ignore
    private List<CouponLine> couponLines = null;

    @SerializedName("refunds")
    @Expose
    @Ignore
    private List<Object> refunds = null;

    @SerializedName("_links")
    @Expose
    @Ignore
    private Links links;


    public Order(Integer id, String status, String currency, String dateCreated, String dateModified, String discountTotal, String shippingTotal, String total, Integer customerId, String customerNote, Billing billing, Shipping shipping, String paymentMethod, String paymentMethodTitle, List<CartItem> lineItems, String transactionId) {
        this.id = id;
        this.status = status;
        this.currency = currency;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.discountTotal = discountTotal;
        this.shippingTotal = shippingTotal;
        this.total = total;
        this.customerId = customerId;
        this.customerNote = customerNote;
        this.billing = billing;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.paymentMethodTitle = paymentMethodTitle;
        this.lineItems = lineItems;
        this.transactionId = transactionId;
    }

    @Ignore
    public Order(String status, Integer customerId, String customerNote, Billing billing, Shipping shipping, String paymentMethod, String paymentMethodTitle, List<CartItem> lineItems, String transactionId) {
        this.status = status;
        this.customerId = customerId;
        this.customerNote = customerNote;
        this.billing = billing;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.paymentMethodTitle = paymentMethodTitle;
        this.lineItems = lineItems;
        this.transactionId = transactionId;
    }


    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getDiscountTotal() {
        return discountTotal;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public String getTotal() {
        return total;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public Billing getBilling() {
        return billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public List<CartItem> getLineItems() {
        return lineItems;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
