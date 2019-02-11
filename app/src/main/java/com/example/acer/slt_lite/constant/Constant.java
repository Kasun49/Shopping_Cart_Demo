package com.example.acer.slt_lite.constant;

import com.example.acer.slt_lite.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Constant {
    public static final List<Integer> QUANTITY_LIST = new ArrayList<Integer>();

    static {
        for (int i = 1; i < 11; i++) QUANTITY_LIST.add(i);
    }

    public static final Product PRODUCT1 = new Product(1, "Samsung Galaxy S6", BigDecimal.valueOf(199.996), "Worldly looks and top-notch specs make the impressive, metal Samsung Galaxy S6 the Android phone to beat for 2015", "samsung_galaxy_s6");
    public static final Product PRODUCT2 = new Product(2, "Uniden AS 7412 CLI Telephone", BigDecimal.valueOf(3499.996), "Excellent overall phone. Beautifull, battery life more than 20 hours daily and great customization in any way. 100% configuration on any aspect", "land");
    public static final Product PRODUCT3 = new Product(3, "LB-Link BL-WA730RE Wi-Fi Extender", BigDecimal.valueOf(4219.998140), "Xiaomi's Mi 3 is a showcase of how Chinese phonemakers can create quality hardware without breaking the bank. If you don't need 4G LTE, and you can get hold of it, this is one of the best smartphones you can buy in its price range.", "cable");
    public static final Product PRODUCT4 = new Product(4, "Motorola Cordless Telephone Twin PacK", BigDecimal.valueOf(10499.996), "Worldly looks and top-notch specs make the impressive, metal Samsung Galaxy S6 the Android phone to beat for 2015", "moto");
    public static final Product PRODUCT5 = new Product(5, "Bitdefender Internet Security Solution ( 1 User", BigDecimal.valueOf(1249.996), "Excellent overall phone. Beautifull, battery life more than 20 hours daily and great customization in any way. 100% configuration on any aspect", "virus");
    public static final Product PRODUCT6 = new Product(6, "Prolink PRS1841 \"ac\" type ADSL Router", BigDecimal.valueOf(4499.998140), "Xiaomi's Mi 3 is a showcase of how Chinese phonemakers can create quality hardware without breaking the bank. If you don't need 4G LTE, and you can get hold of it, this is one of the best smartphones you can buy in its price range.", "router");
    public static final Product PRODUCT7 = new Product(7, "Samsung Galaxy S6", BigDecimal.valueOf(199.996), "Worldly looks and top-notch specs make the impressive, metal Samsung Galaxy S6 the Android phone to beat for 2015", "samsung_galaxy_s6");
    public static final Product PRODUCT8 = new Product(8, "HTC One M8", BigDecimal.valueOf(499.9947), "Excellent overall phone. Beautifull, battery life more than 20 hours daily and great customization in any way. 100% configuration on any aspect", "htc_one_m8");
    public static final Product PRODUCT9 = new Product(9, "PEO TV Remote Controller", BigDecimal.valueOf(849.998140), "Xiaomi's Mi 3 is a showcase of how Chinese phonemakers can create quality hardware without breaking the bank. If you don't need 4G LTE, and you can get hold of it, this is one of the best smartphones you can buy in its price range.", "remote");

    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>();

    static {
        PRODUCT_LIST.add(PRODUCT1);
        PRODUCT_LIST.add(PRODUCT2);
        PRODUCT_LIST.add(PRODUCT3);
        PRODUCT_LIST.add(PRODUCT4);
        PRODUCT_LIST.add(PRODUCT5);
        PRODUCT_LIST.add(PRODUCT6);
        PRODUCT_LIST.add(PRODUCT7);
        PRODUCT_LIST.add(PRODUCT8);
        PRODUCT_LIST.add(PRODUCT9);
    }

    public static final String CURRENCY = "$";
}
