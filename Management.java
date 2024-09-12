import java.io.*;
import java.util.*;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Student{" + "Name='" + name + '\'' + ", Roll Number=" + rollNumber + ", Grade='" + grade + '\'' + '}';
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.ser";

    public StudentManagementSystem() { students = loadStudentsFromFile(); }

    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
        System.out.println("Student added successfully.");
    }

    public void removeStudent(int rollNumber) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            students.remove(student);
            saveStudentsToFile();
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    public Student searchStudent(int rollNumber) {
        return students.stream().filter(s -> s.getRollNumber() == rollNumber).findFirst().orElse(null);
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    public void editStudent(int rollNumber, String name, String grade) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            student.setName(name);
            student.setGrade(grade);
            saveStudentsToFile();
            System.out.println("Student information updated successfully.");
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    private List<Student> loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}

public class Management {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nStudent Management System Menu:");
            System.out.println("1. Add Student 2. Remove Student 3. Search Student 4. Display All Students 5. Edit Student 6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    scanner.nextLine(); // Consume newline
                    String name = scanner.nextLine();
                    System.out.print("Enter roll number: ");
                    int rollNumber = scanner.nextInt();
                    System.out.print("Enter grade: ");
                    scanner.nextLine(); // Consume newline
                    String grade = scanner.nextLine();

                    if (!name.isEmpty() && !grade.isEmpty()) {
                        sms.addStudent(new Student(name, rollNumber, grade));
                    } else {
                        System.out.println("Name and grade cannot be empty.");
                    }
                    break;

                case 2:
                    System.out.print("Enter roll number to remove: ");
                    int rollNumToRemove = scanner.nextInt();
                    sms.removeStudent(rollNumToRemove);
                    break;

                case 3:
                    System.out.print("Enter roll number to search: ");
                    int rollNumToSearch = scanner.nextInt();
                    Student student = sms.searchStudent(rollNumToSearch);
                    System.out.println(student != null ? "Student found: " + student : "Student not found.");
                    break;

                case 4:
                    sms.displayAllStudents();
                    break;

                case 5:
                    System.out.print("Enter roll number to edit: ");
                    int rollNumToEdit = scanner.nextInt();
                    System.out.print("Enter new name: ");
                    scanner.nextLine(); // Consume newline
                    String newName = scanner.nextLine();
                    System.out.print("Enter new grade: ");
                    String newGrade = scanner.nextLine();

                    if (!newName.isEmpty() && !newGrade.isEmpty()) {
                        sms.editStudent(rollNumToEdit, newName, newGrade);
                    } else {
                        System.out.println("Name and grade cannot be empty.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting the Student Management System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }
}
