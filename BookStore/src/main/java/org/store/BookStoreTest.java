package org.store;

import org.store.factory.BookFactory;
import org.store.factory.IBookFactory;
import org.store.model.IBook;
import org.store.repository.Inventory;
import org.store.repository.IBookRepository;
import org.store.delivery.ShippingService;
import org.store.delivery.MailService;
import org.store.service.IOrderService;
import org.store.service.OrderService;

import java.util.List;

public class BookStoreTest {

    public static void main(String[] args) {
        BookStoreTest tester = new BookStoreTest();
        tester.testAddAndRemoveOutdated();
        tester.testBuyPaperBook();
        tester.testBuyEBook();
        tester.testBuyInsufficientStock();
        System.out.println("All manual tests completed.");
    }

    private IBookFactory factory;
    private IBookRepository inventory;
    private IOrderService orderService;

    public BookStoreTest() {
        this.factory = new BookFactory();
        this.inventory = new Inventory();
        this.orderService = new OrderService(inventory, new ShippingService(), new MailService());
    }

    public void testAddAndRemoveOutdated() {
        System.out.println("=== testAddAndRemoveOutdated ===");
        IBook oldDemo = factory.create("demo", "DEM100", "Ancient Tales", 1985, 0.0, "Author X", null);
        IBook recentPaper = factory.create("paper", "PAP200", "Modern Java", 2024, 40.0, "Author Y", 5);

        inventory.addBook(oldDemo, 1);
        inventory.addBook(recentPaper, 5);
        System.out.println("  before removal, inventory contains: " + inventory.findByIsbn("DEM100").getTitle()
                + " and " + inventory.findByIsbn("PAP200").getTitle());

        List<IBook> removed = inventory.removeOutdated(2000);
        System.out.println("  removed count: " + removed.size());
        for (IBook b : removed) {
            System.out.println("    removed ISBN=" + b.getIsbn() + ", title=" + b.getTitle());
        }

        try {
            inventory.findByIsbn("DEM100");
            System.err.println("  ERROR: old demo still found after removal!");
        } catch (Exception e) {
            System.out.println("  old demo correctly not found");
        }

        IBook stillThere = inventory.findByIsbn("PAP200");
        System.out.println("  still present: ISBN=" + stillThere.getIsbn() + ", title=" + stillThere.getTitle());
        System.out.println();
    }

    public void testBuyPaperBook() {
        System.out.println("=== testBuyPaperBook ===");
        IBook paper = factory.create("paper", "PAP300", "Data Structures", 2023, 50.0, "Author Z", 3);
        inventory.addBook(paper, 3);

        System.out.println("  initial stock for PAP300: " + inventory.getStock("PAP300"));
        double paid = orderService.buy("PAP300", 2, "buyer@example.com", "42 Elm Street");
        System.out.println("  paid amount: " + paid);
        System.out.println("  remaining stock: " + inventory.getStock("PAP300"));

        if (inventory.getStock("PAP300") != 1) {
            System.err.println("  ERROR: stock did not reduce correctly!");
        } else {
            System.out.println("  stock reduced correctly");
        }
        System.out.println();
    }

    public void testBuyEBook() {
        System.out.println("=== testBuyEBook ===");
        IBook ebook = factory.create("ebook", "EBK400", "Clean Architecture", 2017, 20.0, "Uncle Bob", "epub");
        inventory.addBook(ebook, 100); // stock is irrelevant for e‑book

        System.out.println("  initial stock for EBK400: " + inventory.getStock("EBK400"));
        double paid = orderService.buy("EBK400", 1, "reader@example.com", "");
        System.out.println("  paid amount: " + paid);
        System.out.println("  stock after purchase (should remain unchanged): " + inventory.getStock("EBK400"));

        if (inventory.getStock("EBK400") != 100) {
            System.err.println("  ERROR: stock changed for e‑book!");
        } else {
            System.out.println("  e‑book stock unchanged as expected");
        }
        System.out.println();
    }

    public void testBuyInsufficientStock() {
        System.out.println("=== testBuyInsufficientStock ===");
        IBook paper = factory.create("paper", "PAP500", "Algorithms", 2020, 60.0, "Author A", 1);
        inventory.addBook(paper, 1);

        System.out.println("  initial stock for PAP500: " + inventory.getStock("PAP500"));
        try {
            orderService.buy("PAP500", 2, "user@example.com", "123 Main St");
            System.err.println("  ERROR: purchase should have failed due to insufficient stock!");
        } catch (IllegalArgumentException ex) {
            System.out.println("  caught expected exception: " + ex.getMessage());
        }
        System.out.println();
    }
}
