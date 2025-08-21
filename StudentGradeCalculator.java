import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Input number of subjects
        System.out.print("Enter number of subjects: ");
        int n = sc.nextInt();

        int[] marks = new int[n];
        int total = 0;

        // Step 2: Take marks input
        for (int i = 0; i < n; i++) {
            System.out.print("Enter marks of subject " + (i + 1) + " (out of 100): ");
            marks[i] = sc.nextInt();
            total = total + marks[i];
        }

        // Step 3: Calculate average percentage
        double average = (double) total / n;

        // Step 4: Grade Calculation
        char grade;
        if (average >= 90) {
            grade = 'A';
        } else if (average >= 80) {
            grade = 'B';
        } else if (average >= 70) {
            grade = 'C';
        } else if (average >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        // Step 5: Display Results
        System.out.println("\n----- Result -----");
        System.out.println("Total Marks: " + total);
        System.out.println("Average Percentage: " + String.format("%.2f", average));
        System.out.println("Grade: " + grade);

        sc.close();
    }
}