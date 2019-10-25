package com.jj.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class demo {


    public static String text = "{\"QjAwNUdHME1CSw==\":\"<div data-asin=\\\"QjAwNUdHME1CSw==\\\" class=\\\"a-section a-spacing-none asin_container\\\"><div class=\\\"a-section a-padding-none removed hidden\\\"><span class=\\\"a-size-large\\\">Removed</span></div><div class=\\\"a-section a-padding-base asin-faceout\\\"><a class=\\\"a-link-normal asin-faceout-link\\\" href=\\\"/Trail-Spellmans-Document-5-ebook/dp/B005GG0MBK/ref=pd_ybh_a_2?_encoding=UTF8&psc=1&refRID=XF5F5GX7FRXEKYYR8MNC\\\"><img alt=\\\"Trail of the Spellmans: Document #5 (The Spellmans series)\\\" src=\\\"https://images-na.ssl-images-amazon.com/images/I/71D5JMb5IsL.__BG0,0,0,0_FMpng_AC_UL200_SR131,200_.jpg\\\" class=\\\"a-dynamic-image asin-image\\\" title=\\\"Trail of the Spellmans: Document #5 (The Spellmans series)\\\" height=\\\"200\\\" width=\\\"131\\\" data-a-dynamic-image=\\\"{&quot;https://images-na.ssl-images-amazon.com/images/I/71D5JMb5IsL.__BG0,0,0,0_FMpng_AC_UL400_SR262,400_.jpg&quot;:[400,262],&quot;https://images-na.ssl-images-amazon.com/images/I/71D5JMb5IsL.__BG0,0,0,0_FMpng_AC_UL200_SR131,200_.jpg&quot;:[200,131],&quot;https://images-na.ssl-images-amazon.com/images/I/71D5JMb5IsL.__BG0,0,0,0_FMpng_AC_UL600_SR393,600_.jpg&quot;:[600,393]}\\\"><div class=\\\"a-section a-spacing-none a-spacing-top-base asin-title\\\"><span class=\\\"a-text-normal\\\">\\n        <div class=\\\"p13n-sc-truncate p13n-sc-line-clamp-1\\\" aria-hidden=\\\"true\\\" data-rows=\\\"1\\\">\\n            Trail of the Spellmans: Document #5 (The Spellmans series)\\n        </div>\\n    </span></div><div class=\\\"a-section a-spacing-none\\\"><span class=\\\"a-color-tertiary asin-byline\\\">\\n        <div class=\\\"p13n-sc-truncate p13n-sc-line-clamp-1\\\" aria-hidden=\\\"true\\\" data-rows=\\\"1\\\">\\n            by Lisa Lutz\\n        </div>\\n    </span></div><div class=\\\"a-section a-spacing-none\\\"><i class=\\\"a-icon a-icon-star-small a-star-small-4-5\\\"><span class=\\\"a-icon-alt\\\">4.3 out of 5 stars</span></i><span class=\\\"a-letter-space\\\"></span><span class=\\\"a-color-tertiary asin-reviews\\\">(152)</span></div><div class=\\\"a-row\\\"><a class=\\\"a-link-normal a-text-normal\\\" href=\\\"/Trail-Spellmans-Document-5-ebook/dp/B005GG0MBK/ref=pd_sc_0?_encoding=UTF8&psc=1&refRID=XF5F5GX7FRXEKYYR8MNC\\\"><span class=\\\"a-size-base a-color-price\\\"><span class='p13n-sc-price' >$11.99</span></span></a>   </div></a><div class=\\\"a-section a-padding-none asin-faceout-footer\\\"><div class=\\\"a-section a-spacing-none delete-error hidden\\\"><span class=\\\"a-color-error\\\">Error removing</span></div><div class=\\\"a-section\\\"><span class=\\\"a-declarative\\\" data-action=\\\"a-modal\\\" data-a-modal=\\\"{&quot;closeButton&quot;:&quot;false&quot;,&quot;url&quot;:&quot;/gp/recs-more-like-this/ajax/more-like-this.html?ie=UTF8&amp;asin=B005GG0MBK&quot;,&quot;hideHeader&quot;:1}\\\"><span class=\\\"a-button a-button-base a-button-small ybh-show-more margin-right-10\\\"><span class=\\\"a-button-inner\\\"><input class=\\\"a-button-input\\\" type=\\\"submit\\\"><span class=\\\"a-button-text a-text-center\\\" aria-hidden=\\\"true\\\">More like this</span></span></span></span><span class=\\\"a-declarative\\\" data-action=\\\"delete-one\\\" data-delete-one=\\\"{&quot;token&quot;:&quot;gDBRZcjwyiq9GQfj3ROxxAIOcXjr84onFJhc8h8AAAAJAAAAAF2mtrp5YmgAAAAA&quot;}\\\"><span class=\\\"a-button a-button-base a-button-small ybh-remove\\\"><span class=\\\"a-button-inner\\\"><input class=\\\"a-button-input\\\" type=\\\"submit\\\"><span class=\\\"a-button-text\\\" aria-hidden=\\\"true\\\">Remove from view</span></span></span></span></div></div></div></div>\"}";

    public static void main(String[] args) {

        // String pattern = "(?<=\\()[^\\)]+";
        //
        // Pattern r = Pattern.compile(pattern);
        //
        // Matcher m = r.matcher(text);
        //
        // if (m.find()){
        //     System.out.println(m.group());
        // }

        String line = "+！！！0(100000000073);+！！！0(100000000071);";
        String pattern = "(?<=\\()\\d+(?=\\))";
        pattern = "(\\d{16})";
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        while (m.find()) {
            System.out.println("Found value: " + m.group(0));
        }

    }
}
