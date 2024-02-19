import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class deleteassignment extends JFrame {
	private JTextField assignmentidField;
	 

    public deleteassignment() {
        setTitle("Assignments");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        
        
        

     

        JLabel assignmentidLabel = new JLabel("ID:");
        assignmentidLabel.setBounds(20, 110, 80, 25);
        add(assignmentidLabel);

        assignmentidField = new JTextField();
        assignmentidField.setBounds(120, 110, 200, 25);
        add(assignmentidField);

        

        JButton updateButton = new JButton("DELETE Assignment");
        updateButton.setBounds(20, 180, 200, 25);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAssignment();
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

    private void deleteAssignment() {
    	String assignmentId = assignmentidField.getText();
    	

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");

            String sql = "DELETE FROM assignments  WHERE assignment_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                preparedStatement.setString(1, assignmentId);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Assignment DELETED successfully!");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error DELETING assignment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new deleteassignment().setVisible(true);
            }
        });
    }
}

