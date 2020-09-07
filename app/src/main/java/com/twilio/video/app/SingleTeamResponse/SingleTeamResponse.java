
package com.twilio.video.app.SingleTeamResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleTeamResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
