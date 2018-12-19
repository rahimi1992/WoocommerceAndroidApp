
package com.test.newshop1.data.database.customer;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "customer")
public class Customer {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("billing")
    @Expose
    @Embedded(prefix = "bill_")
    private Billing billing;

    @SerializedName("shipping")
    @Expose
    @Embedded(prefix = "ship_")
    private Shipping shipping;

    @SerializedName("is_paying_customer")
    @Expose
    private Boolean isPayingCustomer;

    @SerializedName("orders_count")
    @Expose
    private Integer ordersCount;

    @SerializedName("total_spent")
    @Expose
    private String totalSpent;

    private Boolean isLoggedIn;

    public Customer(Integer id, String email, String firstName, String lastName, String role, String username, Billing billing, Shipping shipping, Boolean isPayingCustomer, Integer ordersCount, String totalSpent, Boolean isLoggedIn) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.billing = billing;
        this.shipping = shipping;
        this.isPayingCustomer = isPayingCustomer;
        this.ordersCount = ordersCount;
        this.totalSpent = totalSpent;
        this.isLoggedIn = isLoggedIn;
    }

    @Ignore
    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public Billing getBilling() {
        return billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public Boolean getPayingCustomer() {
        return isPayingCustomer;
    }

    public Integer getOrdersCount() {
        return ordersCount;
    }

    public String getTotalSpent() {
        return totalSpent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public void setPayingCustomer(Boolean payingCustomer) {
        isPayingCustomer = payingCustomer;
    }

    public void setOrdersCount(Integer ordersCount) {
        this.ordersCount = ordersCount;
    }

    public void setTotalSpent(String totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean hasBilling() {
        return billing != null;
    }
}
