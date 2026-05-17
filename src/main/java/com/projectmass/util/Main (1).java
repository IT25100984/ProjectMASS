package com.example.medical;

import com.example.medical.model.MedicalRecord;
import com.example.medical.service.MedicalRecordService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MedicalRecordService service = new MedicalRecordService();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("=========================================");
        System.out.println("   Medical Record Management System      ");
        System.out.println("=========================================");

        while (!exit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add New Record");
            System.out.println("2. View Patient Record");
            System.out.println("3. Update Existing Record");
            System.out.println("4. Delete Record");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewRecord(scanner, service);
                    break;
                case "2":
                    viewRecord(scanner, service);
                    break;
                case "3":
                    updateRecord(scanner, service);
                    break;
                case "4":
                    deleteRecord(scanner, service);
                    break;
                case "5":
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void addNewRecord(Scanner scanner, MedicalRecordService service) {
        try {
            MedicalRecord record = new MedicalRecord();
            System.out.println("\n--- Add New Record ---");

            System.out.print("Enter Patient ID (Required): ");
            String id = scanner.nextLine();
            if (id.trim().isEmpty()) {
                System.out.println("Error: Patient ID cannot be empty.");
                return;
            }
            record.setPatientId(id);

            System.out.print("Enter Full Name: ");
            record.setFullName(scanner.nextLine());

            System.out.print("Enter Age: ");
            String ageStr = scanner.nextLine();
            if (!ageStr.trim().isEmpty()) {
                record.setAge(Integer.parseInt(ageStr));
            }

            System.out.print("Enter Diagnosis: ");
            record.setDiagnosis(scanner.nextLine());

            System.out.print("Enter Blood Pressure (e.g. 120/80): ");
            record.setBloodPressure(scanner.nextLine());

            System.out.print("Enter Heart Rate: ");
            String hrStr = scanner.nextLine();
            if (!hrStr.trim().isEmpty()) {
                record.setHeartRate(Integer.parseInt(hrStr));
            }

            service.createRecord(record);
            System.out.println("SUCCESS: Record for " + record.getFullName() + " successfully created!");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid number format entered. Record creation failed.");
        } catch (IOException e) {
            System.out.println("ERROR creating record: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void viewRecord(Scanner scanner, MedicalRecordService service) {
        System.out.print("\nEnter Patient ID to view: ");
        String patientId = scanner.nextLine();

        try {
            MedicalRecord record = service.readRecord(patientId);
            System.out.println("\n--- Record Details ---");
            System.out.println(record.toString());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void updateRecord(Scanner scanner, MedicalRecordService service) {
        System.out.print("\nEnter Patient ID to update: ");
        String patientId = scanner.nextLine();

        try {
            MedicalRecord record = service.readRecord(patientId);
            System.out.println("Record found for: " + record.getFullName());

            System.out.print("Update Diagnosis (current: " + record.getDiagnosis() + ") [Press Enter to skip]: ");
            String newDiagnosis = scanner.nextLine();
            if (!newDiagnosis.trim().isEmpty()) {
                record.setDiagnosis(newDiagnosis);
            }

            System.out.print("Update Blood Pressure (current: " + record.getBloodPressure() + ") [Press Enter to skip]: ");
            String newBp = scanner.nextLine();
            if (!newBp.trim().isEmpty()) {
                record.setBloodPressure(newBp);
            }

            System.out.print("Update Heart Rate (current: " + record.getHeartRate() + ") [Press Enter to skip]: ");
            String newHr = scanner.nextLine();
            if (!newHr.trim().isEmpty()) {
                record.setHeartRate(Integer.parseInt(newHr));
            }

            service.updateRecord(record);
            System.out.println("SUCCESS: Record successfully updated!");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid number format for Heart Rate. Update failed.");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void deleteRecord(Scanner scanner, MedicalRecordService service) {
        System.out.print("\nEnter Patient ID to delete: ");
        String patientId = scanner.nextLine();

        if (service.deleteRecord(patientId)) {
            System.out.println("SUCCESS: Record for Patient ID " + patientId + " successfully deleted.");
        } else {
            System.out.println("ERROR: Record for Patient ID " + patientId + " not found or could not be deleted.");
        }
    }
}
