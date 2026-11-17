package com.piotr.url_shortener;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtillityTests {
    @Test
    public void UUIDShouldBeTheSameBeforeAndAfterDecoding() {
        UUID uuid = UUID.randomUUID();
        System.out.println(UtilityClass.uuidToBase64(uuid));
        assertEquals(uuid, UtilityClass.uuidFromBase64(
                UtilityClass.uuidToBase64(uuid)));
    }
}
