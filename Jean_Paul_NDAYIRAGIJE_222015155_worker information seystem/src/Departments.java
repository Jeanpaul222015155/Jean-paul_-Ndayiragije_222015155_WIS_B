import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Departments extends JFrame implements ActionListener {

    // UI components declaration
    private JLabel departmentNameLabel;
    private JTextField departmentNameField;
    private JButton addButton, deleteButton, backButton;
    private JTable departmentsTable;
    private DefaultTableModel departmentsTableModel;

    public Departments() {
    	
    	  
    	  departmentNameLabel = new JLabel("Department Name:");
          departmentNameLabel.setBounds(20, 20, 80, 25);
          add(departmentNameLabel);
          
          departmentNameField = new JTextField();
          departmentNameField.setBounds(120, 20, 200, 25);
          add(departmentNameField);
          
          addButton = new JButton("Add");
          addButton.setBounds(140, 200, 80, 25);
          addButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  addDepartment();
              }
          });
          add(addButton);
          
    	
    	
        setTitle("Departments");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); 
        setLocationRelativeTo(null); 

        
      

        
        String[] columnNames = {"Department Name"};
        departmentsTableModel = new DefaultTableModel(columnNames, 0);
        departmentsTable = new JTable(departmentsTableModel);
        try {
           
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");
         

          
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM departments");
           
           rs.close();
           stmt.close();
           conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(departmentsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons for navigation and actions
        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Delete Department");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        backButton = new JButton("Back to Admin Dashboard");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addDepartment();
        } else if (e.getSource() == deleteButton) {
            deleteDepartment();
        } else if (e.getSource() == backButton) {
            
        	new AdminDash().setVisible(true);
        }
    }

   
    private void addDepartment() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");

           
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO departments (department_name) VALUES (?)");
            stmt.setString(1, departmentNameField.getText());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Department added successfully!");
                departmentNameField.setText(""); 
                refreshDepartmentsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Insertion failed. Please try again.");
            }

            
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

    private void deleteDepartment() {
        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");
        	
            int selectedRow = departmentsTable.getSelectedRow();
            if (selectedRow >= 0) {
                
                int departmentId = (int) departmentsTable.getValueAt(selectedRow, 0);

              
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM departments WHERE id = ?");
                stmt.setInt(1, departmentId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Department deleted successfully!");
                    refreshDepartmentsTable(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion failed. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a department to delete.");
            }

            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void refreshDepartmentsTable() {
        try {
            
            departmentsTableModel.setRowCount(0);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");
            

            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM projects");
            
            conn.close();
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Departments departments = new Departments();
        departments.setVisible(true);
    }
}

