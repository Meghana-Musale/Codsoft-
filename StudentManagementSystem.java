import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

class Student implements Serializable {
   
	private static final long serialVersionUID = 1L;
	String name, rollNo, grade, email;

    public Student(String name, String rollNo, String grade, String email) {
        this.name = name;
        this.rollNo = rollNo;
        this.grade = grade;
        this.email = email;
    }

    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNo + ", Grade: " + grade + ", Email: " + email;
    }
}

public class StudentManagementSystem extends JFrame {
   
	private static final long serialVersionUID = 1L;
	private ArrayList<Student> students = new ArrayList<>();
    private final String fileName = "students.dat";

    private JTextField nameField = new JTextField(15);
    private JTextField rollField = new JTextField(15);
    private JTextField gradeField = new JTextField(15);
    private JTextField emailField = new JTextField(15);
    private JTextArea displayArea = new JTextArea(10, 40);

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Input Fields
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Roll No:"));
        add(rollField);
        add(new JLabel("Grade:"));
        add(gradeField);
        add(new JLabel("Email:"));
        add(emailField);

        // Buttons
        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View All");
        JButton searchBtn = new JButton("Search");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");
        JButton exitBtn = new JButton("Exit");

        add(addBtn); add(viewBtn); add(searchBtn);
        add(editBtn); add(deleteBtn);
        add(saveBtn); add(loadBtn); add(exitBtn);

       
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        // Actions
        addBtn.addActionListener(e -> addStudent());
        viewBtn.addActionListener(e -> viewStudents());
        searchBtn.addActionListener(e -> searchStudent());
        editBtn.addActionListener(e -> editStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        saveBtn.addActionListener(e -> saveToFile());
        loadBtn.addActionListener(e -> loadFromFile());
        exitBtn.addActionListener(e -> System.exit(0));

        loadFromFile(); 
        setVisible(true);
    }

    void addStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String grade = gradeField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || grade.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }

        for (Student s : students) {
            if (s.rollNo.equals(roll)) {
                JOptionPane.showMessageDialog(this, "Student with this roll number already exists.");
                return;
            }
        }

        students.add(new Student(name, roll, grade, email));
        JOptionPane.showMessageDialog(this, "Student added.");
        clearFields();
    }

    void viewStudents() {
        displayArea.setText("");
        if (students.isEmpty()) {
            displayArea.setText("No students available.");
        } else {
            for (Student s : students) {
                displayArea.append(s + "\n");
            }
        }
    }

    void searchStudent() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number to Search:");
        if (roll == null || roll.trim().isEmpty()) return;

        for (Student s : students) {
            if (s.rollNo.equals(roll.trim())) {
                displayArea.setText("Student Found:\n" + s);
                return;
            }
        }

        displayArea.setText("Student not found.");
    }

    void editStudent() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number to Edit:");
        if (roll == null || roll.trim().isEmpty()) return;

        for (Student s : students) {
            if (s.rollNo.equals(roll.trim())) {
                String newName = JOptionPane.showInputDialog(this, "Enter new name:", s.name);
                String newGrade = JOptionPane.showInputDialog(this, "Enter new grade:", s.grade);
                String newEmail = JOptionPane.showInputDialog(this, "Enter new email:", s.email);

                if (newName == null || newGrade == null || newEmail == null ||
                    newName.trim().isEmpty() || newGrade.trim().isEmpty() || newEmail.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                    return;
                }

                if (!isValidEmail(newEmail.trim())) {
                    JOptionPane.showMessageDialog(this, "Invalid email format.");
                    return;
                }

                s.name = newName.trim();
                s.grade = newGrade.trim();
                s.email = newEmail.trim();

                JOptionPane.showMessageDialog(this, "Student updated.");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Student not found.");
    }

    void deleteStudent() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number to Delete:");
        if (roll == null || roll.trim().isEmpty()) return;

        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student s = iterator.next();
            if (s.rollNo.equals(roll.trim())) {
                iterator.remove();
                JOptionPane.showMessageDialog(this, "Student deleted.");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Student not found.");
    }

    void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(students);
            JOptionPane.showMessageDialog(this, "Data saved.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    @SuppressWarnings("unchecked")
	void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            students = (ArrayList<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }

    void clearFields() {
        nameField.setText("");
        rollField.setText("");
        gradeField.setText("");
        emailField.setText("");
    }

    boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$", email);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}
