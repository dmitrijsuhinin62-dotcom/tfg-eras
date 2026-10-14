package io.github.dmitrijsuhinin62_dotcom.tfgeras.client;

import io.github.dmitrijsuhinin62_dotcom.tfgeras.Era;

public final class ClientEra {

    /** volatile: читается ещё и из рабочего потока перезагрузки EMI. */
    private static volatile Era current = Era.STONE;

    private ClientEra() {}

    public static Era get() {
        return current;
    }

    public static boolean set(Era era) {
        boolean changed = current != era;
        current = era;
        return changed;
    }
}
