package com.github.shiro8613.barnotificationplugin;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isHexColor(String text) {
        Pattern pattern = Pattern.compile("^(#([a-fA-Z0-9]{6}|))$");
        return pattern.matcher(text).find();
    }
}
