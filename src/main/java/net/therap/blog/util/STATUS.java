package net.therap.blog.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sajjad.ahmed
 * @since 10/13/19.
 */
public enum STATUS {
    PUBLIC(777),
    SUBSCRIBER_ONLY(771),
    DRAFT(711),
    RESTRICT_AUTHOR(111);

    public long val;

    STATUS(long val) {
        this.val = val;
    }

    public static Map<String,Long> getMap() {
        Map<String, Long> statusMap = new HashMap<>();
        for (STATUS status : STATUS.values()) {
            statusMap.put(status.name(),status.val);
        }
        return statusMap;
    }
}