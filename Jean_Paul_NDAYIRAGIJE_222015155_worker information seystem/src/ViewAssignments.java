import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewAssignments extends JFrame implements ActionListener {

    private JTable assignmentsTable;
    private DefaultTableModel assignmentsTableModel;
    private JButton backButton;

    public ViewAssignments() {
        setTitle("View Assignments");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null);

        //  table for displaying assignments
        String[] columnNames = {"ID", "Worker ID", "Project ID", "Start Date", "End Date"};
        assignmentsTableModel = new DefaultTableModel(columnNames, 0);
        assignmentsTable = new JTable(assignmentsTableModel);

        try {
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM assignments");
            while (rs.next()) {
                int id = rs.getInt("assignment_id");
                int workerId = rs.getInt("worker_id");
                int projectId = rs.getInt("project_id");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                assignmentsTableModel.addRow(new Object[]{id, workerId, projectId, startDate, endDate});
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(assignmentsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons for navigation
        JPanel buttonPanel = new JPanel();
        backButton = new JButton("Back to Workers Info");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            
            WorkersInfo workersInfo = new WorkersInfo(); 
            workersInfo.setVisible(true);
            this.dispose(); 
        }
    }

    public static void main(String[] args) {
        ViewAssignments viewAssignments = new ViewAssignments();
        viewAssignments.setVisible(true);
    }
}

