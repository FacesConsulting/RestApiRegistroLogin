package com.mx.consultaya.utils;

public class RandomString {
    public static String make(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    // public static void main(String[] args) {

    //     // Get the size n
    //     int n = 64;

    //     // Get and display the alphanumeric string
    //     System.out.println(RandomString
    //             .getAlphaNumericString(n));
    // }
}
