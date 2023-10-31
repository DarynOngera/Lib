package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class ManageStudents extends JDialog{
    private JPanel ManageStudentsPanel;
    private JTextField tfStudentID;
    private JTextField tfStudentName;
    private JComboBox comboCourse;
    private JComboBox comboBranch;
    private JButton backButton;
    private JTable studentsTable;
    private JScrollPane Students;
    private JButton addButton;
    private JButton UPDATEButton;
    private JButton DELETEButton;
    private JPanel p5;
    DefaultTableModel model;
    String student_name,sCourse,sBranch;
    int student_ID;

    public ManageStudents(JFrame parent) {
        super(parent);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception ignored){

        }
        setTitle("Manage Students");
        setContentPane(ManageStudentsPanel);
        setMinimumSize(new Dimension(200, 370));
        tfStudentID.setToolTipText("Enter student ID");
        tfStudentName.setToolTipText("Enter name");
        comboBranch.setToolTipText("Select branch");
        comboCourse.setToolTipText("Select course");
        setModal(true);
        setLocationRelativeTo(parent);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        SetStudentDetailsToTable();

        backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            HomePage home = new HomePage(null);
            home.setVisible(true);
            dispose();
        }

        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addStudent()== true){
                    JOptionPane.showMessageDialog(null,"Student details added");
                    SetStudentDetailsToTable();
                }else {
                    JOptionPane.showMessageDialog(null,"Student details not added");
                }
            }
        });

        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (updateStudents() == true) {
                    JOptionPane.showMessageDialog(null,
                            "Student details updated");
                    SetStudentDetailsToTable();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Student details not updated");
                }

            }
        });
        setVisible(true);
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deleteStudents() == true) {
                    JOptionPane.showMessageDialog(null,
                            "Student details deleted");
                    SetStudentDetailsToTable();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Student details not deleted");
                }
            }
        });
    }
    public void SetStudentDetailsToTable() {
        studentsTable.setModel(new DefaultTableModel(null,
                new String[]{"student_ID", "name", "course", "branch"}));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from student_details");
            while (rs.next()) {

                int studentID = rs.getInt("student_ID");
                String Name = rs.getString("name");
                String Course  = rs.getString("course");
                String Branch = rs.getString("branch");
                Object[] obj = {studentID, Name, Course, Branch};
                model = (DefaultTableModel) studentsTable.getModel();
                model.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public boolean addStudent() {
        boolean isAdded = false;
        student_ID = Integer.parseInt(tfStudentID.getText());
        student_name = tfStudentName.getText();
        sCourse = String.valueOf(comboCourse.getSelectedItem());
        sBranch  = String.valueOf(comboBranch.getSelectedItem());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            String sql = "insert into student_details values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, student_ID);
            pst.setString(2, student_name);
            pst.setString(3, sCourse);
            pst.setString(4, sBranch);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isAdded = true;

            } else {
                isAdded = false;
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
        return isAdded;

    }
    public boolean updateStudents() {
        boolean isUpdated = false;
        student_ID = Integer.parseInt(tfStudentID.getText());
        student_name = tfStudentName.getText();
        sCourse = String.valueOf(comboCourse.getSelectedItem());
        sBranch  = String.valueOf(comboBranch.getSelectedItem());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            String sql = "update student_details set name = ?,course = ?,branch = ? where student_ID =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, student_name);
            pst.setString(2, sCourse);
            pst.setString(3, sBranch);
            pst.setInt(4, student_ID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
            } else {
                isUpdated = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }
    public boolean deleteStudents() {
        boolean isDeleted = true;
        student_ID = Integer.parseInt(tfStudentID.getText());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            String sql = "delete from book_details where book_id =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, student_ID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;

            } else {
                isDeleted = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }




    public static void main(String[] args) {
        ManageStudents managestudents = new ManageStudents(null);

    }

}