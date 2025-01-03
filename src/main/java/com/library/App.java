package com.library;

import com.library.model.Book;

import java.io.*;
import java.util.*;

public class App {
    private static final String FILE_NAME = "library.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add a book");
            System.out.println("2. View all books");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    removeBook(scanner);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        Book book = new Book(title, author, isbn);
        List<Book> books = readBooksFromFile();
        books.add(book);
        writeBooksToFile(books);

        System.out.println("Book added successfully.");
    }

    private static void viewBooks() {
        List<Book> books = readBooksFromFile();
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void removeBook(Scanner scanner) {
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        List<Book> books = readBooksFromFile();

        boolean removed = books.removeIf(book -> book.getIsbn().equals(isbn));
        if (removed) {
            writeBooksToFile(books);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book with ISBN " + isbn + " not found.");
        }
    }

    private static List<Book> readBooksFromFile() {
        List<Book> books = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Book> readObject2 = (List<Book>) ois.readObject();
			List<Book> readObject = readObject2;
			books = readObject;
        } catch (EOFException e) {
            // File is empty
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return books;
    }

    private static void writeBooksToFile(List<Book> books) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
