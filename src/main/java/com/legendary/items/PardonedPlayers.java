package com.legendary.items;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PardonedPlayers {
    private static final Set<UUID> PARDONED = ConcurrentHashMap.newKeySet();

    public static void markPardoned(UUID uuid) {
        PARDONED.add(uuid);
    }

    public static boolean isPardoned(UUID uuid) {
        return PARDONED.contains(uuid);
    }

    public static void clearPardoned(UUID uuid) {
        PARDONED.remove(uuid);
    }
}
