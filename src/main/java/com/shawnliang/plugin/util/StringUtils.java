package com.shawnliang.plugin.util;

/**
 * @author shawnLiang
 */
public class StringUtils {
  public static boolean isBlank(String str) {
    int length;
      if (str == null || (length = str.length()) == 0) {
          return true;
      }
    for (int i = 0; i < length; i++) {
        if (!Character.isWhitespace(str.charAt(i))) {
            return false;
        }
    }
    return true;
  }

  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  public static String toFirstUpperCase(String str) {
      if (isBlank(str)) {
          return str;
      }
    char[] cs = str.toCharArray();
    cs[0] = (char)(cs[0] - 32);
    return String.valueOf(cs);
  }
}
