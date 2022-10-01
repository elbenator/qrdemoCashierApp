package com.kazimasum.qrdemo;

import java.util.HashMap;

public class newItemHash {
    HashMap itemlists = new HashMap();

    public newItemHash() {
        this.itemlists = itemlists;
    }

    public HashMap newItemHash() {
        itemlists.put("ALCSNuggets", 0);
        itemlists.put("ALCSporksisig", 0);
        itemlists.put("ALCSshanghai", 0);
        itemlists.put("ALCSsharks", 0);
        itemlists.put("ALCSsiomai", 0);
        itemlists.put("FNallMeat", 0);
        itemlists.put("FNbacon", 0);
        itemlists.put("FNbeefTeri", 0);
        itemlists.put("FNshanghai", 0);
        itemlists.put("FNsharksfin", 0);
        itemlists.put("FNsiomai", 0);
        itemlists.put("FNtotkatsu", 0);
        itemlists.put("MTCHblackForest", 0);
        itemlists.put("MTCHbrownSLatte", 0);
        itemlists.put("MTCHchocoHzl", 0);
        itemlists.put("MTCHchocolate", 0);
        itemlists.put("MTCHdoubleCheese", 0);
        itemlists.put("MTCHmatcha", 0);
        itemlists.put("MTCHokinawa", 0);
        itemlists.put("MTCHoreoDoubleCheese", 0);
        itemlists.put("MTCHoreoNcream", 0);
        itemlists.put("MTCHredVelvet", 0);
        itemlists.put("MTCHstrawberry", 0);
        itemlists.put("MTCHwintermelon", 0);
        itemlists.put("MTCPeaerl", 0);
        itemlists.put("MTCblackf", 0);
        itemlists.put("MTCbrownSugarLa", 0);
        itemlists.put("MTCcheesecake", 0);
        itemlists.put("MTCchocolate", 0);
        itemlists.put("MTChazel", 0);
        itemlists.put("MTChokaido", 0);
        itemlists.put("MTCmatcha", 0);
        itemlists.put("MTCokinawa", 0);
        itemlists.put("MTCoreo", 0);
        itemlists.put("MTCredVelvet", 0);
        itemlists.put("MTCstrawberry", 0);
        itemlists.put("MTCwinter", 0);
        itemlists.put("RBAllmeat", 0);
        itemlists.put("RBchzBacon", 0);
        itemlists.put("RBchzNugget", 0);
        itemlists.put("RBchzPorktonk", 0);
        itemlists.put("RBchzTapa", 0);
        itemlists.put("RBporkSisig", 0);
        itemlists.put("RBshanhai", 0);
        itemlists.put("RBsharksfin", 0);
        itemlists.put("RBsiomai", 0);

        return itemlists;
    }

    public static boolean returnIfExisting(String itemId) {
        boolean kungMeron;
        switch (itemId){
            case "ALCSNuggets":
            case "ALCSporksisig":
            case "ALCSshanghai":
            case "ALCSsharks":
                kungMeron = true;
                break;
            case "ALCSsiomai":
                kungMeron = true;
                break;
            case "FNallMeat":
                kungMeron = true;
                break;
            case "FNbacon":
                kungMeron = true;
                break;
            case "FNbeefTeri":
                kungMeron = true;
                break;
            case "FNshanghai":
                kungMeron = true;
                break;
            case "FNsharksfin":
                kungMeron = true;
                break;
            case "FNsiomai":
                kungMeron = true;
                break;
            case "FNtotkatsu":
                kungMeron = true;
                break;
            case "MTCHblackForest":
                kungMeron = true;
                break;
            case "MTCHbrownSLatte":
                kungMeron = true;
                break;
            case "MTCHchocoHzl":
                kungMeron = true;
                break;
            case "MTCHchocolate":
                kungMeron = true;
                break;
            case "MTCHdoubleCheese":
                kungMeron = true;
                break;
            case "MTCHmatcha":
                kungMeron = true;
                break;
            case "MTCHokinawa":
                kungMeron = true;
                break;
            case "MTCHoreoDoubleCheese":
                kungMeron = true;
                break;
            case "MTCHoreoNcream":
                kungMeron = true;
                break;
            case "MTCHredVelvet":
                kungMeron = true;
                break;
            case "MTCHstrawberry":
                kungMeron = true;
                break;
            case "MTCHwintermelon":
                kungMeron = true;
                break;
            case "MTCPeaerl":
                kungMeron = true;
                break;
            case "MTCblackf":
                kungMeron = true;
                break;
            case "MTCbrownSugarLa":
                kungMeron = true;
                break;
            case "MTCcheesecake":
                kungMeron = true;
                break;
            case "MTCchocolate":
                kungMeron = true;
                break;
            case "MTChazel":
                kungMeron = true;
                break;
            case "MTChokaido":
                kungMeron = true;
                break;
            case "MTCmatcha":
                kungMeron = true;
                break;
            case "MTCokinawa":
                kungMeron = true;
                break;
            case "MTCoreo":
                kungMeron = true;
                break;
            case "MTCredVelvet":
                kungMeron = true;
                break;
            case "MTCstrawberry":
                kungMeron = true;
                break;
            case "MTCwinter":
                kungMeron = true;
                break;
            case "RBAllmeat":
                kungMeron = true;
                break;
            case "RBchzBacon":
                kungMeron = true;
                break;
            case "RBchzNugget":
                kungMeron = true;
                break;
            case "RBchzPorktonk":
                kungMeron = true;
                break;
            case "RBchzTapa":
                kungMeron = true;
                break;
            case "RBporkSisig":
                kungMeron = true;
                break;
            case "RBshanhai":
                kungMeron = true;
                break;
            case "RBsharksfin":
                kungMeron = true;
                break;
            case "RBsiomai":
                kungMeron = true;
                break;
            default:
                kungMeron =false;
        }
        return kungMeron;
    }

}
