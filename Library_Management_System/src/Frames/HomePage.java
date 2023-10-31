package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class HomePage extends JDialog{
    private JPanel homePagePanel;
    private JButton manageBooksButton;
    private JButton manageStudentsButton;
    private JButton issueBookButton;
    private JButton returnBookButton;
    private JButton logoutButton;
    private JTable table1;
    private JTable table2;
    private JPanel p6;
    DefaultTableModel model;
    public void SetbookDetailsToTable()  {
        table1.setModel(new DefaultTableModel(null,
                new String[]{"book_id", "book_name", "author", "quantity"}));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from book_details");
            while (rs.next()) {

                int bookID = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                String Author = rs.getString("author");
                int Quantity = rs.getInt("quantity");
                Object[] obj = {bookID, bookName, Author, Quantity};
                model = (DefaultTableModel) table1.getModel();
                model.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void SetStudentDetailsToTable() {
        table2.setModel(new DefaultTableModel(null,
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
                model = (DefaultTableModel) table2.getModel();
                model.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public HomePage(JFrame parent){
        super(parent);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception ignored){

        }
        setTitle("Home page");
        setContentPane(homePagePanel);
        setMinimumSize(new Dimension(200,370));
        setModal(true);
        setLocationRelativeTo(parent);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        SetbookDetailsToTable();
        SetStudentDetailsToTable();
        manageBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageBooksPage books = new ManageBooksPage(null);
                books.setVisible(true);
                dispose();
            }
        });

        manageStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageStudents managestudents = new ManageStudents(null);
                managestudents.setVisible(true);
                dispose();
            }
        });
        setVisible(true);
        returnBookButton.addKeyListener(new KeyAdapter() {
        });
    }
    public static void main(String[] args) {
        HomePage homePage = new HomePage(null);

    }


}
