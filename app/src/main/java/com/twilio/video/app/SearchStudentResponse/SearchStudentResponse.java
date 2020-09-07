
package com.twilio.video.app.SearchStudentResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchStudentResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
