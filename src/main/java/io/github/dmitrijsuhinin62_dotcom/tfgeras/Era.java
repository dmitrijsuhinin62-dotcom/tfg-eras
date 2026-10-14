package io.github.dmitrijsuhinin62_dotcom.tfgeras;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public enum Era {
    STONE(List.of()),
    COPPER(List.of("copper")),
    BRONZE(List.of("bronze", "bismuth_bronze", "black_bronze")),
    WROUGHT_IRON(List.of("wrought_iron")),
    STEEL(List.of("steel")),
    BLACK_STEEL(List.of("black_steel")),
    RED_BLUE_STEEL(List.of("red_steel", "blue_steel"));

    private final List<String> ingotMetals;

    Era(List<String> ingotMetals) {
        this.ingotMetals = ingotMetals;
    }

    public List<String> ingotMetals() {
        return ingotMetals;
    }

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public String stage() {
        return TFGEras.MOD_ID + ".era." + id();
    }

    public Optional<Era> next() {
        Era[] eras = values();
        int nextIndex = ordinal() + 1;
        return nextIndex < eras.length ? Optional.of(eras[nextIndex]) : Optional.empty();
    }

    public boolean includes(Era other) {
        return compareTo(other) >= 0;
    }

    public Era max(Era other) {
        return includes(other) ? this : other;
    }
}
