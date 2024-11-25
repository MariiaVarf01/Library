import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class Library {
    // Creating  a book class, that has id, title, author, and quantity
    static class Book {
        String id;
        String title;
        String author;
        int quantity;

        public Book(String title, String author, int quantity) {
            this.id = title + author;
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }

        // Method to print book string with title, author, and quantity
        public String toString() {
            return getStockBlock(title) + getStockBlock(author) + quantity;
        }
    }

    // Make a stock block sting that consists of block name and spaces
    public static String getStockBlock(String blockName) {
        // Limit of chars in the block
        int blockLength = 20;
        String space = " ";

        return blockName.substring(0, Math.min(blockName.length(), blockLength - 1)) + space.repeat((blockLength - blockName.length()) > 0 ? (blockLength - blockName.length()) : 1 );
    }

    public static void main(String[] args) {
        Map<String, Book> library = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu until user doesn't insert chars from 1 to 5
            System.out.println("\nLibrary System:");
            System.out.println("1. Add Books");
            System.out.println("2. Borrow Books");
            System.out.println("3. Return Books");
            System.out.println("4. Check Stocks");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice;
            // Reading users input
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 5.");
                continue;
            }

            // According to inserted number provided activity
            switch (choice) {
                case 1:
                    addBooks(library, scanner);
                    break;
                case 2:
                    borrowBook(library, scanner);
                    break;
                case 3:
                    returnBooks(library, scanner);
                    break;
                case 4:
                    checkStocks(library);
                    break;
                case 5:
                    System.out.println("Exiting the library system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid input! Please enter a number between 1 and 5.");
            }
        }
    }

    // Getting value from user
    private static String getValue (String msg, String errorMsg, Scanner scanner) {
        System.out.print(msg);
        String value = scanner.nextLine().trim();
        // Until user doesn't insert at least 1 character she/he will be asked to enter value
        while (value.isEmpty()){
            System.out.println(errorMsg);
            value = scanner.nextLine().trim();
        }

        return value;
    }

    // Getting book title
    private static String getBookTitle (Scanner scanner) {
        String msg = "Enter book title: ";
        String errorMsg = "Book title should have at least 1 character. Please try again.";
        return getValue(msg, errorMsg, scanner);
    }

    // Getting book author
    private static String getBookAuthor (Scanner scanner) {
        String msg = "Enter book author: ";
        String errorMsg = "Book author should have at least 1 character. Please try again.";
        return getValue(msg, errorMsg, scanner);
    }

    // Getting book quantity
    private static int getQuantity (Scanner scanner) {
        int quantity = 0;
        // Until user doesn't insert a number she/he will be asked to enter quantity
        do {
            System.out.print("Enter quantity: ");
            // Verified user's quantity input
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity == 0) {
                    System.out.println("Invalid quantity! Please number greater than 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity! Please enter a valid number.");
            }
        }
        while (quantity <= 0);

        return quantity;
    }

    // Method to add books
    private static void addBooks(Map<String, Book> library, Scanner scanner) {
        String title = getBookTitle(scanner);
        String author = getBookAuthor(scanner);
        int quantity = getQuantity(scanner);

        // Adding quantity to existed
        if (library.containsKey(title + author)) {
            // Getting book by id
            Book existingBook = library.get(title + author);
            existingBook.quantity += quantity;
            System.out.println("Book quantity updated successfully.");
        }
        // Add new book by id
        else {
            library.put(title + author, new Book(title, author, quantity));
            System.out.println("Book added successfully.");
        }
    }

    // Method to borrow books
    private static void borrowBook(Map<String, Book> library, Scanner scanner) {
        String title = getBookTitle(scanner);
        String author = getBookAuthor(scanner);

        if (library.containsKey(title + author)) {
            while (true) {
                // Getting quantity
                int quantity = getQuantity(scanner);
                // Getting book by id
                Book book = library.get(title + author);
                //  If inserted number is less than actual quantity, than this number removed from stocks
                if (book.quantity >= quantity) {
                    book.quantity -= quantity;
                    System.out.println("You have successfully borrowed " + quantity + " of \"" + title + "\".");
                    break;
                }
                // if the number of borrowed books is more than present, the loop will iterate
                else {
                    System.out.println("Insufficient stock! Only " + book.quantity + " available.");
                }
            }
        } else {
            System.out.println("Book not found in the library.");
        }
    }

    // Method to return books
    private static void returnBooks(Map<String, Book> library, Scanner scanner) {
        String title = getBookTitle(scanner);
        String author = getBookAuthor(scanner);
        int quantity = getQuantity(scanner);

        // Find book by id, if it exists than update quantity, otherwise don't get it
        if (library.containsKey(title + author)) {
            Book book = library.get(title + author);
            book.quantity += quantity;
            System.out.println("You have successfully returned " + quantity + " of \"" + title + "\".");
        } else {
            System.out.println("This book does not belong to the library.");
        }
    }

    // Method to check stocks
    private static void checkStocks(Map<String, Book> library) {
        // Check if the library is empty
        if (library.isEmpty()) {
            System.out.println("No books available in the library.");
        }
        // if not print all the book in the library
        else {
            String author= "Author";
            String title = "Title";
            String quantity = "Quantity";

            System.out.println("Current library stock:");
            System.out.println(getStockBlock(title) + getStockBlock(author) + quantity);
            for (Book book : library.values()) {
                System.out.println(book);
            }
        }
    }
}
