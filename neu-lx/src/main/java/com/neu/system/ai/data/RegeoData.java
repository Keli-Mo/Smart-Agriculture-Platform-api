package com.neu.system.ai.data;

import lombok.Data;

@Data
public class RegeoData {
    private String status;
    private Regeocode regeocode;

    @Data
    public static class Regeocode {
        private AddressComponent addressComponent;
    }

    @Data
    public static class AddressComponent {
        private String province;
//        private String city;
//        private String district;
        private String adcode;
    }
}
