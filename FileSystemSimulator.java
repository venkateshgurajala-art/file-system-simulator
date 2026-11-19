import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int size = 10;
    static String[] disk = new String[size];
    static ArrayList<String> fileList = new ArrayList<String>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = 0;

        while (choice != 5) {
            printMenu();
            System.out.print("Enter choice: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
                System.out.println("Please enter a number between 1 and 5.");
                System.out.println();
                continue;
            }

            switch (choice) {
                case 1:
                    createFile();
                    break;
                case 2:
                    deleteFile();
                    break;
                case 3:
                    displayFiles();
                    break;
                case 4:
                    showStorage();
                    break;
                case 5:
                    System.out.println("Program ended.");
                    break;
                default:
                    System.out.println("Wrong choice, try again.");
            }
            System.out.println();
        }
    }

    static void printMenu() {
        System.out.println("===== SIMPLE FILE SYSTEM =====");
        System.out.println("1. Create File");
        System.out.println("2. Delete File");
        System.out.println("3. Display Files");
        System.out.println("4. Show Storage");
        System.out.println("5. Exit");
        System.out.println();
    }

    static void createFile() {
        System.out.print("File name: ");
        String name = sc.nextLine();

        if (name.trim().equals("")) {
            System.out.println("File name cannot be empty.");
            return;
        }

        if (fileList.contains(name)) {
            System.out.println("This file already exists.");
            return;
        }

        System.out.print("File size: ");
        if (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.println("Size must be a number.");
            return;
        }
        int fileSize = sc.nextInt();
        sc.nextLine();

        if (fileSize <= 0) {
            System.out.println("Size must be at least 1.");
            return;
        }

        if (fileSize > freeBlocks()) {
            System.out.println("Not enough space. Only " + freeBlocks() + " blocks are free.");
            return;
        }

        int count = 0;
        for (int i = 0; i < size; i++) {
            if (disk[i] == null && count < fileSize) {
                disk[i] = name;
                count++;
            }
        }

        fileList.add(name);
        System.out.println("File created successfully.");
    }

    static void deleteFile() {
        System.out.print("File name: ");
        String name = sc.nextLine();

        if (!fileList.contains(name)) {
            System.out.println("File not found.");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (disk[i] != null && disk[i].equals(name)) {
                disk[i] = null;
            }
        }

        fileList.remove(name);
        System.out.println("File deleted successfully.");
    }

    static void displayFiles() {
        if (fileList.size() == 0) {
            System.out.println("No files found.");
            return;
        }

        System.out.println("Files:");
        for (int i = 0; i < fileList.size(); i++) {
            System.out.println((i + 1) + ". " + fileList.get(i));
        }
    }

    static void showStorage() {
        System.out.println("Storage:");
        for (int i = 0; i < size; i++) {
            if (disk[i] == null) {
                System.out.print("[ ]");
            } else {
                System.out.print("[" + disk[i] + "]");
            }

            if (i == 4 || i == 9) {
                System.out.println();
            } else {
                System.out.print(" ");
            }
        }
    }

    static int freeBlocks() {
        int free = 0;
        for (int i = 0; i < size; i++) {
            if (disk[i] == null) {
                free++;
            }
        }
        return free;
    }
}
