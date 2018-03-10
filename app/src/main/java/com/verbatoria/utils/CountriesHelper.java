package com.verbatoria.utils;

/**
 *
 * @author nikitaremnev
 */
public class CountriesHelper {

    public static String getCountryCodeByCountryName(String country) {
        if (country.equals("Россия")) {
            return "ru";
        } else if (country.equals("Украина")) {
            return "ua";
        } else {
            return "az";
        }
    }

}
