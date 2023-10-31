package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;


public class SignUpPage extends JDialog  {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JTextField tfUserEmail;
    private JTextField tfUserContact;
    private JButton signUpButton;
    private JPanel signUpPanel;
    private JButton cancelButton;
    private JPasswordField pfConfirmPassword;
    private JButton loginButton;
    private JPanel p2;


    public boolean checkDuplicateUser(){
        String name = tfUsername.getText();
        boolean isExist = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library","root","");
            PreparedStatement pst = con.prepareStatement("select * from users where name =?");
            pst.setString(1,name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                isExist = true;
            }else{
                isExist = false;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;

    }





    public SignUpPage(JFrame parent) {
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
        setTitle("Create Account");
        setContentPane(signUpPanel);
        setMinimumSize(new Dimension(200,370));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tfUsername.setToolTipText("Enter username");
        tfUserContact.setToolTipText("Enter contact");
        tfUserEmail.setToolTipText("Enter email");
        pfConfirmPassword.setToolTipText("Confirm password");
        pfPassword.setToolTipText("Enter password");


        signUpButton.addActionListener(e -> {
            insertSignupDetails();
        });
        cancelButton.addActionListener(e -> dispose());


        tfUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (checkDuplicateUser() == true){
                    JOptionPane.showMessageDialog(tfUsername,"This username already exists");
                }

            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage login = new LoginPage(null);
                login.setVisible(true);
                dispose();
            }
        });
        setVisible(true);
    }

    private void insertSignupDetails() {
        String name = tfUsername.getText();
        String pwd = pfPassword.getText();
        String cfpwd = pfConfirmPassword.getText();
        String email = tfUserEmail.getText();
        String contact = tfUserContact.getText();
        if (name.isEmpty() || pwd.isEmpty() ||cfpwd.isEmpty()||  email.isEmpty()|| contact.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again ", JOptionPane.ERROR_MESSAGE
            );
            return;
         }

            if (!pwd.equals(cfpwd)){
                JOptionPane.showMessageDialog(this,
                       "Password does not match",
                        "Try again",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")){
                JOptionPane.showMessageDialog(this,
                        "Please enter valid email");

            }

        try {
            Class.forName("com.mysql.jdbc.Driver" +
                    "");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
            String sql = "insert into users(name,password,email,contact) values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, pwd);
            pst.setString(3, email);
            pst.setString(4, contact);
            int updatedRowCount = pst.executeUpdate();
            if (updatedRowCount > 0) {
                JOptionPane.showMessageDialog(this, "Successfully signed up");
                LoginPage page = new LoginPage(null);
                page.setVisible(true);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Record Insertion Failure");


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SignUpPage signUpPage = new SignUpPage(null);
    }
    }






