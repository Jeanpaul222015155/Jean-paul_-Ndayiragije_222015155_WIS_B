import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class updateassignment extends JFrame {
	private JTextField assignmentidField;
	 private JTextField assignmentnameField;
    private JTextField workerIdField;
  
    private JTextField startDateField;
    private JTextField endDateField;

    public updateassignment() {
        setTitle("Assignments");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        
        JLabel assignmentidLabel = new JLabel("Id:");
        assignmentidLabel.setBounds(20, 1, 80, 25);
        add(assignmentidLabel);

        assignmentidField = new JTextField();
        assignmentidField.setBounds(120, 1, 200, 25);
        add( assignmentidField);
        
        JLabel assignmentLabel = new JLabel("Assignment name:");
        assignmentLabel.setBounds(20, 40, 80, 25);
        add(assignmentLabel);

        assignmentnameField = new JTextField();
        assignmentnameField.setBounds(120, 40, 200, 25);
        add( assignmentnameField);

        JLabel workerIdLabel = new JLabel("Worker ID:");
        workerIdLabel.setBounds(20, 70, 80, 25);
        add(workerIdLabel);

        workerIdField = new JTextField();
        workerIdField.setBounds(120, 70, 200, 25);
        add(workerIdField);

     

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

        JButton updateButton = new JButton("update Assignment");
        updateButton.setBounds(20, 180, 150, 25);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAssignment();
            }
        });
        add(updateButton);

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
        
     
    }

    private void updateAssignment() {
    	String assignmentId = assignmentidField.getText();
    	String assignmentname = assignmentnameField.getText();
        String workerId = workerIdField.getText();
       
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");

            String sql = "UPDATE assignments SET assignment_name = ?,worker_id = ?,start_date = ?,end_date = ? WHERE assignment_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, assignmentname);
                preparedStatement.setString(2, workerId);
                preparedStatement.setString(3, startDate);
                preparedStatement.setString(4, endDate);
                preparedStatement.setString(5, assignmentId);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Assignment update successfully!");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating assignment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new updateassignment().setVisible(true);
            }
        });
    }
}

