import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Assignments extends JFrame {
	 private JTextField assignmentnameField;
    private JTextField workerIdField;
    private JTextField projectIdField;
    private JTextField startDateField;
    private JTextField endDateField;

    public Assignments() {
        setTitle("Assignments");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        
        JLabel assignmentLabel = new JLabel("Assignment name:");
        assignmentLabel.setBounds(20, 10, 80, 25);
        add(assignmentLabel);

        assignmentnameField = new JTextField();
        assignmentnameField.setBounds(120, 10, 200, 25);
        add( assignmentnameField);

        JLabel workerIdLabel = new JLabel("Worker ID:");
        workerIdLabel.setBounds(20, 40, 80, 25);
        add(workerIdLabel);

        workerIdField = new JTextField();
        workerIdField.setBounds(120, 40, 200, 25);
        add(workerIdField);

        JLabel projectIdLabel = new JLabel("Project ID:");
        projectIdLabel.setBounds(20, 70, 80, 25);
        add(projectIdLabel);

        projectIdField = new JTextField();
        projectIdField.setBounds(120, 70, 200, 25);
        add(projectIdField);

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setBounds(20, 110, 80, 25);
        add(startDateLabel);

        startDateField = new JTextField();
        startDateField.setBounds(120, 110, 200, 25);
        add(startDateField);

        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setBounds(20, 150, 80, 25);
        add(endDateLabel);

        endDateField = new JTextField();
        endDateField.setBounds(120, 150, 200, 25);
        add(endDateField);

        JButton addButton = new JButton("Add Assignment");
        addButton.setBounds(20, 180, 150, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAssignment();
            }
        });
        add(addButton);

        JButton viewButton = new JButton("View Assignments");
        viewButton.setBounds(180, 180, 150, 25);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAssignments();
            }
        });
        add(viewButton);

        JButton backButton = new JButton("Back to Admin Dashboard");
        backButton.setBounds(20, 220, 200, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to redirect to AdminDash.java
            	new AdminDash().setVisible(true);
                
            }
        });
        add(backButton);
        
        JButton updateButton = new JButton("update");
        updateButton.setBounds(20, 250, 200, 25);
       updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateAssignment();
                
            }
        });
        add(updateButton);
        
        JButton deleteButton = new JButton("delete");
        deleteButton.setBounds(20, 280, 200, 25);
       deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	deleteAssignment();
                
            }
        });
        add(deleteButton);
        
        
    }

    private void addAssignment() {
        String workerId = workerIdField.getText();
        String projectId = projectIdField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");

            String sql = "INSERT INTO assignments (worker_id, project_id, start_date, end_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, workerId);
                preparedStatement.setString(2, projectId);
                preparedStatement.setString(3, startDate);
                preparedStatement.setString(4, endDate);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Assignment added successfully!");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding assignment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAssignments() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");

            String sql = "SELECT * FROM assignments";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process the result set and display or do something with the data
                while (resultSet.next()) {
                    int workerId = resultSet.getInt("worker_id");
                    int projectId = resultSet.getInt("project_id");
                    String startDate = resultSet.getString("start_date");
                    String endDate = resultSet.getString("end_date");

                    System.out.println("Worker ID: " + workerId + ", Project ID: " + projectId +
                            ", Start Date: " + startDate + ", End Date: " + endDate);
                }
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error viewing assignments: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAssignment() {
    	new updateassignment().setVisible(true);
        
    }

    private void deleteAssignment() {
       new deleteassignment().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Assignments().setVisible(true);
            }
        });
    }
}

