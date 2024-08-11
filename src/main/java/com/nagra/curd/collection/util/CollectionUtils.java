package com.nagra.curd.collection.util;

import java.util.Collection;

public final class CollectionUtils {

  private CollectionUtils() {}

  public static boolean isEmpty(Collection<?> list) {
    return (list == null || list.isEmpty());
  }
}
