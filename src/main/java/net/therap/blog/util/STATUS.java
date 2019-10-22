package net.therap.blog.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sajjad.ahmed
 * @since 10/13/19.
 */
public enum STATUS {
    PUBLIC("Public"),
    SUBSCRIBER_ONLY("Subscriber only"),
    DRAFT("Draft"),
    RESTRICT_AUTHOR("Restrict author");

    public String val;

    STATUS(String val) {
        this.val = val;
    }

    public static Map<String, String> getMap() {
        Map<String, String> statusMap = new HashMap<>();
        for (STATUS status : STATUS.values()) {
            statusMap.put(status.name(), status.val);
        }
        return statusMap;
    }
}