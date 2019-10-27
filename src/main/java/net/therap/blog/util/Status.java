package net.therap.blog.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sajjad.ahmed
 * @since 10/13/19.
 */
public enum Status {

    PUBLIC("Public"),
    SUBSCRIBER_ONLY("Subscriber only"),
    DRAFT("Draft"),
    RESTRICT_AUTHOR("Restrict author");

    public String val;

    Status(String val) {
        this.val = val;
    }

    public static Map<String, String> getMap() {
        Map<String, String> statusMap = new HashMap<>();
        for (Status status : Status.values()) {
            statusMap.put(status.name(), status.val);
        }
        return statusMap;
    }
}