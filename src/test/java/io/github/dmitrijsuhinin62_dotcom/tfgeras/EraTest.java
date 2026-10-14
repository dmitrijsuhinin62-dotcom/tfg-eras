package io.github.dmitrijsuhinin62_dotcom.tfgeras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EraTest {

    @Nested
    class Next {

        @Test
        void должен_вернуть_следующую_эпоху() {
            assertEquals(Optional.of(Era.COPPER), Era.STONE.next());
            assertEquals(Optional.of(Era.RED_BLUE_STEEL), Era.BLACK_STEEL.next());
        }

        @Test
        void должен_вернуть_пусто_для_последней_эпохи() {
            assertEquals(Optional.empty(), Era.RED_BLUE_STEEL.next());
        }
    }

    @Nested
    class Includes {

        @Test
        void должен_включать_себя_и_младшие_эпохи() {
            assertTrue(Era.STEEL.includes(Era.STEEL));
            assertTrue(Era.STEEL.includes(Era.STONE));
        }

        @Test
        void не_должен_включать_старшие_эпохи() {
            assertFalse(Era.BRONZE.includes(Era.WROUGHT_IRON));
        }
    }

    @Nested
    class Max {

        @Test
        void должен_вернуть_старшую_эпоху_независимо_от_порядка() {
            assertEquals(Era.STEEL, Era.COPPER.max(Era.STEEL));
            assertEquals(Era.STEEL, Era.STEEL.max(Era.COPPER));
        }
    }

    @Nested
    class Stage {

        @Test
        void должен_собрать_имя_этапа_из_id_эпохи() {
            assertEquals("tfgeras.era.wrought_iron", Era.WROUGHT_IRON.stage());
        }
    }

    @Nested
    class IngotMetals {

        @Test
        void каменная_эпоха_не_открывается_слитком() {
            assertEquals(List.of(), Era.STONE.ingotMetals());
        }

        @Test
        void бронзовая_эпоха_открывается_любой_из_трёх_бронз() {
            assertEquals(List.of("bronze", "bismuth_bronze", "black_bronze"), Era.BRONZE.ingotMetals());
        }
    }
}
