package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class ManageBooksPage extends JDialog {
    private JButton backButton;
    private JPanel ManagebksPanel;
    private JTextField tfBookID;
    private JTextField tfBookName;
    private JTextField tfAuthorName;
    private JTextField tfQuantity;
    private JButton ADDButton;
    private JButton UPDATEButton;


    private JButton DELETEButton;
    private JTable mngbksTable;
    private JPanel p4;
    String book_name, author;
    int book_id, quantity;
    DefaultTableModel model;

    public void SetbookDetailsToTable() {
        mngbksTable.setModel(new DefaultTableModel(null,
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
                model = (DefaultTableModel) mngbksTable.getModel();
                model.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean addBook() {
        boolean isAdded = false;
        book_id = Integer.parseInt(tfBookID.getText());
        book_name = tfBookName.getText();
        author = tfAuthorName.getText();
        quantity = Integer.parseInt(tfQuantity.getText());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            String sql = "insert into book_details values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            pst.setString(2, book_name);
            pst.setString(3, author);
            pst.setInt(4, quantity);
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

    public boolean updateBooks() {
        boolean isUpdated = false;
        book_id = Integer.parseInt(tfBookID.getText());
        book_name = tfBookName.getText();
        author = tfAuthorName.getText();
        quantity = Integer.parseInt(tfQuantity.getText());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            String sql = "update book_details set book_name = ?,author = ?,quantity = ? where book_id =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, book_name);
            pst.setString(2, author);
            pst.setInt(3, quantity);
            pst.setInt(4, book_id);
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

    public boolean deleteBooks() {
        boolean isDeleted = true;
        book_id = Integer.parseInt(tfBookID.getText());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            String sql = "delete from book_details where book_id =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
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

    public ManageBooksPage(JFrame parent) {
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
        setTitle("Manage books");
        setContentPane(ManagebksPanel);
        setMinimumSize(new Dimension(200, 370));
        tfBookID.setToolTipText("Enter book ID");
        tfBookName.setToolTipText("Enter book name");
        tfAuthorName.setToolTipText("Enter author name");
        tfQuantity.setToolTipText("Enter quantity of books");
        setModal(true);
        setLocationRelativeTo(parent);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        SetbookDetailsToTable();


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage home = new HomePage(null);
                home.setVisible(true);
                dispose();

            }

        });

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addBook() == true) {
                    JOptionPane.showMessageDialog(null,
                            "Book added");
                    SetbookDetailsToTable();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Book Not added");
                }

            }
        });

        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (updateBooks() == true) {
                    JOptionPane.showMessageDialog(null,
                            "Book updated");
                    SetbookDetailsToTable();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Book Not updated");
                }

            }

        });

        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deleteBooks() == true) {
                    JOptionPane.showMessageDialog(null,
                            "Book deleted");
                    SetbookDetailsToTable();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Book Not deleted");
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        ManageBooksPage manageBooksPage = new ManageBooksPage(null);

    }
}
