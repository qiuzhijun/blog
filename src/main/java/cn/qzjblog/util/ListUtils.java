package cn.qzjblog.util;

import java.util.List;

/**
 * Create by qzj on 2021/01/17 19:26
 **/
public class ListUtils {
    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
