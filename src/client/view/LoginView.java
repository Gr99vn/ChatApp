package client.view;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import client.control.ClientControl;
import model.DataWrapper;
import model.User;
import utils.Const;

public class LoginView extends JFrame implements ActionListener {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private ClientControl clientControl;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JLabel lblRegister;
    
    
    // Parameter: ClientControl Map
    private static Map<Integer, LoginView> loginViewMap = new HashMap<>();

    public static boolean isAvailable() {
        return loginViewMap.containsKey(Const.LOGIN_VIEW);
    }

    public static LoginView getInstance(ClientControl clientControl) {
        LoginView loginView = loginViewMap.get(Const.LOGIN_VIEW);
        if (loginView == null) {
            loginView = new LoginView(clientControl);
            loginViewMap.put(Const.LOGIN_VIEW, loginView);
        }
        return loginView;
    }
    
    public static void removeInstance() {
        if (loginViewMap.get(Const.LOGIN_VIEW) != null) {
            loginViewMap.remove(Const.LOGIN_VIEW);
        }
    }

    public LoginView(ClientControl clientControl) {
        super("Login");
        this.clientControl = clientControl;
        this.initComponents();
    }

    public void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblRegister = new JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                txtUsername.getBorder(),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                txtPassword.getBorder(),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        btnLogin = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Login");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        btnLogin.setText("Login");
        btnLogin.addActionListener(this);

        lblRegister.setText("Or register");
        lblRegister.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegisterMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(70, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(59, 59, 59)
                                                .addComponent(jLabel1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnLogin)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblRegister)))
                                .addGap(70, 70, 70))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3});

        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnLogin)
                                        .addComponent(lblRegister))
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();

        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }


    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnLogin)) {
            User user = new User();
            user.setUsername(txtUsername.getText());
            user.setPassword(String.valueOf(txtPassword.getPassword()));
            if (user.getUsername().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Username can not be empty!",
                        "Login input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (user.getPassword().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Password can not be empty!",
                        "Login input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            clientControl.sendData(new DataWrapper(Const.LOGIN, user));
        }
    }

    public void lblRegisterMouseClicked(MouseEvent evt) {
        RegisterView.getInstance(clientControl);
        this.dispose();
        removeInstance();
    }

    public void processLoginResult(DataWrapper data) {
        User user = (User) data.getData();
        if (user.getId() != 0) {
            clientControl.setClientUser(user);
            HomeView.getInstance(clientControl);
            this.dispose();
            removeInstance();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Incorrect username and/or password!",
                    "Login result",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
