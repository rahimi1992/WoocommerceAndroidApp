
package com.test.newshop1.data.database.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuccessMassage {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("default")
    @Expose
    private String _default;
    @SerializedName("tip")
    @Expose
    private String tip;
    @SerializedName("placeholder")
    @Expose
    private String placeholder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefault() {
        return _default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

}
