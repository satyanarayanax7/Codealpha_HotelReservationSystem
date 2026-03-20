import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    boolean isBooked;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }
}

class Booking {
    String customerName;
    int roomNumber;
    String category;

    Booking(String customerName, int roomNumber, String category) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
    }
}

public class HotelManagementSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {

        initializeRooms();
        loadBookings();

        int choice;

        do {
            System.out.println("\n===== HOTEL BOOKING SYSTEM =====");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    searchRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    viewBookings();
                    break;
            }

        } while (choice != 5);

        saveBookings();
        System.out.println("Thank you for using the system.");
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
    }

    static void searchRooms() {

        System.out.println("\nAvailable Rooms:");

        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.println("Room " + r.roomNumber + " - " + r.category);
            }
        }
    }

    static void bookRoom() {

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter room number: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {

            if (r.roomNumber == roomNo && !r.isBooked) {

                r.isBooked = true;

                System.out.println("Processing Payment...");
                System.out.println("Payment Successful!");

                bookings.add(new Booking(name, r.roomNumber, r.category));

                System.out.println("Room booked successfully!");
                return;
            }
        }

        System.out.println("Room not available.");
    }

    static void cancelBooking() {

        System.out.print("Enter room number to cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Booking> it = bookings.iterator();

        while (it.hasNext()) {

            Booking b = it.next();

            if (b.roomNumber == roomNo) {

                it.remove();

                for (Room r : rooms) {
                    if (r.roomNumber == roomNo) {
                        r.isBooked = false;
                    }
                }

                System.out.println("Booking cancelled.");
                return;
            }
        }

        System.out.println("Booking not found.");
    }

    static void viewBookings() {

        System.out.println("\nBooking Details:");

        for (Booking b : bookings) {
            System.out.println("Name: " + b.customerName +
                    " | Room: " + b.roomNumber +
                    " | Category: " + b.category);
        }
    }

    static void saveBookings() {

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));

            for (Booking b : bookings) {
                pw.println(b.customerName + "," + b.roomNumber + "," + b.category);
            }

            pw.close();

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadBookings() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists())
                return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String data[] = fileScanner.nextLine().split(",");

                String name = data[0];
                int roomNo = Integer.parseInt(data[1]);
                String category = data[2];

                bookings.add(new Booking(name, roomNo, category));

                for (Room r : rooms) {
                    if (r.roomNumber == roomNo) {
                        r.isBooked = true;
                    }
                }
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading bookings.");
        }
    }
}
