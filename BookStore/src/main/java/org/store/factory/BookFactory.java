package org.store.factory;

import org.store.model.DemoBook;
import org.store.model.EBook;
import org.store.model.IBook;
import org.store.model.PaperBook;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BookFactory implements IBookFactory {
    private Map<String, Function<Object[], IBook>> registry = new HashMap<>();

    public BookFactory() {
        // register built‑in types
        register("paper", args -> new PaperBook(
                (String) args[0], (String) args[1], (int) args[2],
                (double) args[3], (String) args[4], (int) args[5]
        ));
        register("ebook", args -> new EBook(
                (String) args[0], (String) args[1], (int) args[2],
                (double) args[3], (String) args[4], (String) args[5]
        ));
        register("demo", args -> new DemoBook(
                (String) args[0], (String) args[1], (int) args[2],
                (double) args[3], (String) args[4]
        ));
    }

    @Override
    public void register(String type, Function<Object[], IBook> ctor) {
        registry.put(type.toLowerCase(), ctor);
    }

    @Override
    public IBook create(String type, String isbn, String title, int year, double price, String author, Object extra) {
        Function<Object[], IBook> ctor = registry.get(type.toLowerCase());
        if (ctor == null) {
            throw new IllegalArgumentException("Unknown book type: " + type);
        }
        // build args array in order expected by the lambda
        Object[] args = { isbn, title, year, price, author, extra };
        return ctor.apply(args);
    }
}
