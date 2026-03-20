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



Hotel Management System (Java)

A simple console-based Hotel Booking System developed in Java. This application allows users to search rooms, book rooms, cancel bookings, and view booking details. It also supports file handling to persist booking data.

- Features

search available rooms
Book a room
Cancel a booking
View all booking details
Save and load bookings using file handling

-Technologies Used

Java
Object-Oriented Programming (OOP)
File Handling (Text File Storage)
Collections Framework (ArrayList)

Once you run the program, you will see a menu:

===== HOTEL BOOKING SYSTEM =====
1. Search Rooms
2. Book Room
3. Cancel Booking
4. View Booking Details
5. Exit
Options Explained:

Search Rooms → Displays all available (unbooked) rooms
Book Room → Enter your name and room number to book
Cancel Booking → Cancel booking using room number
View Booking Details → Shows all current bookings
Exit → Saves bookings and exits program
