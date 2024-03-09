package ru.nsu.dolgov.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.dolgov.pizzeria.service.init.Parser;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        Parser parser = new Parser("config.json");
        Field field = parser.getClass().getDeclaredField("filename");
        field.setAccessible(true);
        Object value = field.get(parser);
        assertEquals("config.json", value);
    }

    @Test
    void testParseMethod() {
        Parser parser = new Parser("config.json");
        var config = parser.parse();

        assertNotNull(config);
        assertEquals(60, config.bakery.durationOfTheDayInSeconds);
        assertEquals(20, config.bakery.deliveryQueueCapacity);
        assertEquals(20, config.bakery.waitingQueueCapacity);
        assertEquals(2, config.deliverers.capacity);
        assertEquals(10, config.deliverers.quantity);
        assertEquals(20, config.warehouse.capacity);
        assertEquals(4, config.bakers.quantity);
    }
}
