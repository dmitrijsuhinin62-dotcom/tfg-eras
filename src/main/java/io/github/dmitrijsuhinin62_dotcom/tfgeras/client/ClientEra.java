package io.github.dmitrijsuhinin62_dotcom.tfgeras.client;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;

public final class ClientEra {

    private static Era current = Era.STONE;

    private ClientEra() {}

    public static Era get() {
        return current;
    }

    public static void set(Era era) {
        current = era;
    }
}
