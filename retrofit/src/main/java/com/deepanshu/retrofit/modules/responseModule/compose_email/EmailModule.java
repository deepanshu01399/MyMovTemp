package com.deepanshu.retrofit.modules.responseModule.compose_email;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailModule implements Serializable {
        @SerializedName("type")
        private String type;
        @SerializedName("id")
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

}
