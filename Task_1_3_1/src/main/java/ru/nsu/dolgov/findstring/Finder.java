package ru.nsu.dolgov.findstring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Finder {
    List<Long> search() throws IOException;
}
