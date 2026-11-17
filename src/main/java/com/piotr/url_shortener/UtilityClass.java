package com.piotr.url_shortener;

import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class UtilityClass {
    public static <T> T checkFound(T resource) throws NoSuchElementException {
        if (resource == null) {
            throw new NoSuchElementException();
        }
        return resource;
    }

    public static String uuidToBase64(UUID uuid) { //didnt use it
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base64.encodeBase64URLSafeString(bb.array());
    }

    public static UUID uuidFromBase64(String str) { //didnt use it
        byte[] bytes = Base64.decodeBase64(str);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }
}
