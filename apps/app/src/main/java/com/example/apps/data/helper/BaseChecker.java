package com.example.apps.data.helper;

public class BaseChecker {
    public static String Checker(Boolean bool, String erroinfo) throws Exception {
        if(bool){
            return "ok";
        }
        throw new Exception(erroinfo);
    }
}
