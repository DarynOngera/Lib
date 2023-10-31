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


public class LoginPage extends JDialog{
    private JPanel loginPanel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton loginButton;
    private JButton SignUpButton;
    private JPanel p3;

    public LoginPage(JFrame parent) {
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
    setTitle("Login");
    setContentPane(loginPanel);
    setMinimumSize(new Dimension(200,370));
    setModal(true);
    setLocationRelativeTo(parent);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    tfUsername.setToolTipText("Enter username");
    pfPassword.setToolTipText("Enter password");
    tfUsername.addFocusListener(new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent e) {

        }
    });
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           if( validateLogin()){
               login();
           }
        }
    });

    SignUpButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SignUpPage signUpPage = new SignUpPage(null);
            signUpPage.setVisible(true);
            dispose();
        }
    });setVisible(true);
}



    public boolean validateLogin(){
    String name = tfUsername.getText();
    String pwd = pfPassword.getText();
    if (name.isEmpty()){
        JOptionPane.showMessageDialog(this,
                "Please enter username");
        return false;
    }
    if (pwd.isEmpty()){
        JOptionPane.showMessageDialog(this,
                "Please enter password");
        return false;
    }
    return true;
   }
   public void login(){
       String name = tfUsername.getText();
       String pwd = pfPassword.getText();
       try{
           Class.forName("com.mysql.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/futuristic_library", "root", "");
           PreparedStatement pst = con.prepareStatement("select * from users where name = ? and password = ?");
           pst.setString(1,name);
           pst.setString(2,pwd);
           ResultSet rs = pst.executeQuery();
           if (rs.next()){
               JOptionPane.showMessageDialog(this,
                       "Login successful");
               HomePage home = new HomePage(null);
               home.setVisible(true);

               this.dispose();
           }
           else{
               JOptionPane.showMessageDialog(this,
                       "Incorrect username or password");

           }

       }
       catch (Exception e){
           e.printStackTrace();

       }

   }
    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage(null);
    }
}
