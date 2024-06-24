package com.challenge.uala.util;

public class UserIdentifierExtractor {

    public static Long extractUserId(Long headerUserId, Long paramUserId, Long bodyUserId) {
        if (headerUserId != null) {
            return headerUserId;
        }
        if (paramUserId != null) {
            return paramUserId;
        }
        return bodyUserId;
    }
}
