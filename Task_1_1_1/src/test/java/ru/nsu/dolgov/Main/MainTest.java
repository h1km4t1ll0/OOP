package ru.nsu.dolgov.Main;

import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    void testMainClass() {
        String[] testArguments = new String[]{"tetsArg"};
        Main.main(testArguments);
        assert (true);
    }
}
