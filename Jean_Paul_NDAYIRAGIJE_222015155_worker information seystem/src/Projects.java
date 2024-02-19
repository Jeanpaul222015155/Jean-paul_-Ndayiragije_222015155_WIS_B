import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Projects extends JFrame implements ActionListener {

    
    private JLabel projectNameLabel,projectidLabel;
    private JTextField projectNameField;
    private JButton addButton, deleteButton, backButton;
    private JTable projectsTable;
    private DefaultTableModel projectsTableModel;

    public Projects() {
        setTitle("Projects");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500); 
        setLocationRelativeTo(null); 

        
        projectNameLabel = new JLabel("project Name:");
        projectNameLabel.setBounds(20, 20, 80, 25);
        add(projectNameLabel);
        
        projectNameField = new JTextField();
        projectNameField.setBounds(120, 20, 200, 25);
        add(projectNameField);
        
        addButton = new JButton("Add");
        addButton.setBounds(140, 200, 80, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProject();
            }
        });
        add(addButton);

        // table for displaying projects
        
        String[] columnNames = {"project id","Project Name"};
        projectsTableModel = new DefaultTableModel(columnNames, 0);
        projectsTable = new JTable(projectsTableModel);
        try {
            
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");
         

          
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM projects");

          
           DefaultTableModel model = new DefaultTableModel(columnNames, 0);
           while (rs.next()) {
               Object[] row = {rs.getInt("project_id"), rs.getString("project_name")};
               model.addRow(row);
           }

          
           projectsTable = new JTable(model);
           JScrollPane scrollPane = new JScrollPane(projectsTable);
           add(scrollPane, BorderLayout.CENTER);

           
           rs.close();
           stmt.close();
           conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(projectsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons for navigation and actions
        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Delete Project");
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
            addProject();
        } else if (e.getSource() == deleteButton) {
            deleteProject();
        } else if (e.getSource() == backButton) {
            
        	new AdminDash().setVisible(true);
        }
    }

    
    private void addProject() {
        try {
            // Connect to database
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");

            // Prepared statement
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO projects (project_name) VALUES (?)");
            stmt.setString(1, projectNameField.getText());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Project added successfully!");
                projectNameField.setText(""); 
                refreshProjectsTable(); 
            } else {
                JOptionPane.showMessageDialog(this, "Insertion failed. Please try again.");
            }

            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

    private void deleteProject() {
        try {
        	
        	 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");
            
            int selectedRow = projectsTable.getSelectedRow();
            if (selectedRow >= 0) {
                
                int project_id = (int) projectsTable.getValueAt(selectedRow, 0);


                PreparedStatement stmt = conn.prepareStatement("DELETE FROM projects WHERE project_id = ?");
                stmt.setInt(1, project_id);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Project deleted successfully!");
                    refreshProjectsTable(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion failed. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a project to delete.");
            }

            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void refreshProjectsTable() {
        try {
            
            projectsTableModel.setRowCount(0);

          
          
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
        Projects projects = new Projects();
        projects.setVisible(true);
    }
}

