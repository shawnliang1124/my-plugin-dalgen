package com.shawnliang.plugin.rule;

import com.shawnliang.plugin.util.StringUtils;

public enum NamingStrategy {
  nochange, underline_to_camel, remove_prefix, remove_prefix_and_camel;

  public static String underlineToCamel(String name) {
      if (StringUtils.isBlank(name)) {
          return "";
      }
    StringBuilder result = new StringBuilder();
    String[] camels = name.toLowerCase().split("_");
    for (String camel : camels) {
        if (!StringUtils.isBlank(camel)) {
            if (result.length() == 0) {
                result.append(camel);
            } else {
                result.append(capitalFirst(camel));
            }
        }
    }
    return result.toString();
  }

  public static String removePrefix(String name) {
      if (StringUtils.isBlank(name)) {
          return "";
      }
    int idx = name.indexOf("_");
      if (idx == -1) {
          return name;
      }
    return name.substring(idx + 1);
  }

  public static String removePrefix(String name, String prefix) {
      if (StringUtils.isBlank(name)) {
          return "";
      }
    int idx = -1;
    idx = name.indexOf("_");
      if (prefix != null && !"".equals(prefix.trim()) && name.toLowerCase()
              .matches("^" + prefix.toLowerCase() + ".*")) {
          idx = prefix.length() - 1;
      }
      if (idx == -1) {
          return name;
      }
    return name.substring(idx + 1);
  }

  public static String removePrefixAndCamel(String name, String tablePrefix) {
    return underlineToCamel(removePrefix(name, tablePrefix));
  }

  public static String capitalFirst(String name) {
    if (StringUtils.isNotBlank(name)) {
      char[] array = name.toCharArray();
      array[0] = (char)(array[0] - 32);
      return String.valueOf(array);
    }
    return "";
  }
}
