import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminDash extends JFrame implements ActionListener {

    private JTable workersTable;
    private JButton departmentsButton, assignmentsButton, projectsButton,delButton;

    public AdminDash() {
        setTitle("Admin Dashboard");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null); 

       
        String[] columnNames = {"ID", "First Name", "Last Name", "DOB", "Gender", "Contact Number", "Email", "Address", "Skills"};

        
        try {
            
        	 Class.forName("com.mysql.cj.jdbc.Driver");
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis", "222015155", "222015155");
          

           
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM workersinfo");

           
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getInt("worker_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("date_of_birth"),
                        rs.getString("gender"), rs.getString("contact_number"), rs.getString("email"),
                        rs.getString("address"), rs.getString("skills")};
                model.addRow(row);
            }

           
            workersTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(workersTable);
            add(scrollPane, BorderLayout.CENTER);

            workersTable.setModel(model);
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }

        
        JPanel buttonPanel = new JPanel();
        departmentsButton = new JButton("Departments");
        departmentsButton.addActionListener(this);
        buttonPanel.add(departmentsButton);

        assignmentsButton = new JButton("Assignments");
        assignmentsButton.addActionListener(this);
        buttonPanel.add(assignmentsButton);

        projectsButton = new JButton("Projects");
        projectsButton.addActionListener(this);
        buttonPanel.add(projectsButton);
        
        delButton = new JButton("DELETE selected data");
        buttonPanel.add(delButton);
        delButton.addActionListener(this);
       

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == departmentsButton) {
            
        	new Departments().setVisible(true);
        } else if (e.getSource() == assignmentsButton) {
            
        	new Assignments().setVisible(true);
        } else if (e.getSource() == projectsButton) {
            
        	new Projects().setVisible(true);
        }else if(e.getSource() == delButton) {
        	
        	 int selectedRow = workersTable.getSelectedRow();
             if (selectedRow == -1) {
                 JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                 return;
             }
             
             int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
             if (confirm == JOptionPane.YES_OPTION) {
                 try {
                	 
                	 Class.forName("com.mysql.cj.jdbc.Driver");
                     Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis", "222015155", "222015155");
                	 
                     int idToDelete = (int) workersTable.getValueAt(selectedRow, 0);
                     PreparedStatement ps = conn.prepareStatement("DELETE FROM workersinfo WHERE worker_id = ?");
                     ps.setInt(1, idToDelete);
                     int rowsAffected = ps.executeUpdate();
                     if (rowsAffected > 0) {
                         JOptionPane.showMessageDialog(this, "Row deleted successfully.");
                         DefaultTableModel model = (DefaultTableModel) workersTable.getModel();
                         model.removeRow(selectedRow);
                     } else {
                         JOptionPane.showMessageDialog(this, "Failed to delete row.");
                     }
                     ps.close();
                 } catch (Exception e1) {
                     e1.printStackTrace();
                 }
             }
         }
        	
        }
    

    public static void main(String[] args) {
        AdminDash adminDash = new AdminDash();
        adminDash.setVisible(true);
    }


}
