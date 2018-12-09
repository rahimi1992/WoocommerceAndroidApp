
package com.test.newshop1.data.database.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {

    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("direct_redirect")
    @Expose
    private DirectRedirect directRedirect;
    @SerializedName("api")
    @Expose
    private Api api;
    @SerializedName("sandbox")
    @Expose
    private Sandbox sandbox;
    @SerializedName("completed_massage")
    @Expose
    private CompletedMassage completedMassage;
    @SerializedName("failed_massage")
    @Expose
    private FailedMassage failedMassage;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public DirectRedirect getDirectRedirect() {
        return directRedirect;
    }

    public void setDirectRedirect(DirectRedirect directRedirect) {
        this.directRedirect = directRedirect;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public Sandbox getSandbox() {
        return sandbox;
    }

    public void setSandbox(Sandbox sandbox) {
        this.sandbox = sandbox;
    }

    public CompletedMassage getCompletedMassage() {
        return completedMassage;
    }

    public void setCompletedMassage(CompletedMassage completedMassage) {
        this.completedMassage = completedMassage;
    }

    public FailedMassage getFailedMassage() {
        return failedMassage;
    }

    public void setFailedMassage(FailedMassage failedMassage) {
        this.failedMassage = failedMassage;
    }

}
