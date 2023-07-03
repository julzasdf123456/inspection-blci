package com.lopez.julz.inspectionv2.api;

public class BaseURL {

    public static String baseUrl() {
//        return "http://192.168.2.12/blci/public/api/";
        return "http://192.168.10.161/blci/public/api/";
    }

    public static String baseUrl(String ip) {
        return "http://" + ip + "/blci/public/api/";
    }
}
