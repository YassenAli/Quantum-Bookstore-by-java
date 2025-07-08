# Quantum-Bookstore-by-java

# BookStore Application

A simple, extensible online bookstore implemented in Java (Maven project).

![Class Diagram](/assets/classDiagram.jpg)

## Project Structure

```
BookStore/
├── pom.xml
├── src
│   ├── main
│   │   └── java/org/store
│   │       ├── factory
│   │       │   ├── IBookFactory.java
│   │       │   └── BookFactory.java
│   │       ├── model
│   │       │   ├── IBook.java
│   │       │   ├── IStockable.java
│   │       │   ├── IShippable.java
│   │       │   ├── IEmailable.java
│   │       │   ├── IPurchasable.java
│   │       │   ├── Book.java
│   │       │   ├── PaperBook.java
│   │       │   ├── EBook.java
│   │       │   └── DemoBook.java
│   │       ├── repository
│   │       │   ├── IBookRepository.java
│   │       │   └── Inventory.java
│   │       ├── delivery
│   │       │   ├── ShippingService.java
│   │       │   └── MailService.java
│   │       └── service
│   │       │   ├── IOrderService.java
│   │       │   └── OrderService.java
│   │       └── Main.java
│   │       └── BookStoreTest.java
```

## How Requirements Are Achieved

1. **Multiple Book Types**

   * **PaperBook** implements `IStockable`, `IShippable`, `IPurchasable`.
   * **EBook** implements `IEmailable`, `IPurchasable`.
   * **DemoBook** (showcase) extends `Book` but does not implement purchase, ship or email interfaces.

2. **Inventory Management**

   * `Inventory` (implements `IBookRepository`) holds a `Map<ISBN,IBook>` and a `Map<ISBN,Integer>` for stock.
   * `addBook(book, qty)` lets you add any book type with quantity.
   * `removeOutdated(cutoffYear)` returns and removes all books published before `cutoffYear`.

3. **Buying a Book**

   * Call `OrderService.buy(isbn, qty, email, address)` which:

     1. Looks up the book in `Inventory`.
     2. Calls `reduceStock(isbn, qty)`—throws `IllegalArgumentException` if insufficient.
     3. If the book is `IStockable`, invokes `ShippingService.ship(...)`; else calls `MailService.send(...)`.
     4. Returns `price × qty` as the paid amount.

4. **Extensibility**

   * **Factory Pattern** (`BookFactory`) registers constructors by type string; to add a new book type, implement `IBook` (and any capability interfaces) and call `BookFactory.register(...)`—no existing code changes.
   * **Repository Interface** (`IBookRepository`) allows swapping `Inventory` for another persistence mechanism.
   * **Delivery & Purchase Interfaces** decouple capabilities, following **ISP**.
   * **OrderService** depends only on abstractions, following **DIP**.

5. **Test Harness**

   * `BookStoreTest` (not a JUnit test) runs four manual scenarios:

     * Adding/removing outdated books
     * Buying paper books
     * Buying e‑books
     * Handling insufficient stock

## Sample Run

![Sample Run](/assets/Screenshot.png)
