package client.view;

import client.control.ClientControl;
import model.DataWrapper;
import model.FriendRequest;
import model.Friendship;
import model.User;
import utils.Const;
import utils.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendView extends JFrame {
    private ClientControl clientControl;
    private List<User> userResults = new ArrayList<>();
    private User selectedUser;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSendFriendRequest;
    private javax.swing.JButton btnUnfriend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblDob;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblResultCount;
    private javax.swing.JLabel lblShowFriendRequests;
    private javax.swing.JLabel lblShowSentRequests;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JTable tblUserResults;
    private javax.swing.JTextField txtSearchInput;

    // Parameter: ClientControl Map
    private static Map<Integer, FriendView> friendViewMap = new HashMap<>();

    public static boolean isAvailable() {
        return friendViewMap.containsKey(Const.FRIEND_VIEW);
    }

    public static FriendView getInstance(ClientControl clientControl) {
        FriendView friendView = friendViewMap.get(Const.FRIEND_VIEW);
        if (friendView == null) {
            friendView = new FriendView(clientControl);
            friendViewMap.put(Const.FRIEND_VIEW, friendView);
        }
        return friendView;
    }

    public static void removeInstance() {
        if (friendViewMap.get(Const.FRIEND_VIEW) != null) {
            friendViewMap.remove(Const.FRIEND_VIEW);
        }
    }

    public FriendView(ClientControl clientControl) {
        this.clientControl = clientControl;
        initComponents();
        clientControl.sendData(new DataWrapper(Const.GET_ALL_USER, ""));

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                removeInstance();
            }
        });
    }

    public boolean isAlreadyFriend(User user) {
        List<User> clientUserFriends = HomeView.getInstance(clientControl).getFriends();
        for (User us : clientUserFriends) {
            if (us.getId() == user.getId()) {
                return true;
            }
        }
        return false;
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblShowSentRequests = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUserResults = new javax.swing.JTable();
        lblResultCount = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtSearchInput = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblDob = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        btnSendFriendRequest = new javax.swing.JButton();
        btnUnfriend = new javax.swing.JButton();
        lblShowFriendRequests = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Friend");

        lblShowSentRequests.setText(" View sent requests ");
        lblShowSentRequests.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblShowSentRequests.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShowSentRequestsMouseClicked(evt);
            }
        });

        tblUserResults.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No", "User"
                }
        ));
        jScrollPane1.setViewportView(tblUserResults);
        if (tblUserResults.getColumnModel().getColumnCount() > 0) {
            tblUserResults.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        lblResultCount.setText("Result: ");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txtSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblResultCount))
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblResultCount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("User: ");

        lblUserName.setFont(new Font("Tahoma", 2, 14));
        lblUserName.setText("Unknown");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Selected User Info");

        jLabel8.setText("Dob: ");

        jLabel9.setText("Address: ");

        jLabel10.setText("Email: ");

        jLabel11.setText("Phone: ");

        lblDob.setText("Unknown");

        lblAddress.setText("Unknown");

        lblEmail.setText("Unknown");

        lblPhone.setText("Unknown");

        btnSendFriendRequest.setText("Send friend request");
        btnSendFriendRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendFriendRequestActionPerformed(evt);
            }
        });

        btnUnfriend.setText("Unfriend");
        btnUnfriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnfriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblDob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(btnSendFriendRequest, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addComponent(jLabel11)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addContainerGap())))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnUnfriend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel4, jLabel8, jLabel9});

        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(lblDob, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnSendFriendRequest)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUnfriend)
                                .addContainerGap())
        );

        lblShowFriendRequests.setText(" Requests ");
        lblShowFriendRequests.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblShowFriendRequests.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShowFriendRequestsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblShowFriendRequests)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblShowSentRequests))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(lblShowSentRequests)
                                        .addComponent(lblShowFriendRequests))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 9, Short.MAX_VALUE)))
                                .addGap(0, 0, 0))
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

        tblUserResults.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblUserResults.getSelectedRow() != -1 && tblUserResults.getSelectedRow() < tblUserResults.getRowCount()) {
                    int selectedRowIndex = tblUserResults.getSelectedRow();
                    selectedRowIndex = tblUserResults.convertRowIndexToModel(selectedRowIndex);
                    selectedUser = userResults.get(selectedRowIndex);
                    if (selectedUser != null) {
                        lblUserName.setText(selectedUser.getName());
                        if (isAlreadyFriend(selectedUser)) {
                            btnSendFriendRequest.setEnabled(false);
                            btnUnfriend.setEnabled(true);
                        } else {
                            btnSendFriendRequest.setEnabled(true);
                            btnUnfriend.setEnabled(false);
                        }
                    }
                }
            }
        });

        txtSearchInput.setBorder(BorderFactory.createCompoundBorder(
                txtSearchInput.getBorder(),
                BorderFactory.createEmptyBorder(4, 4, 3, 4)));
        btnSendFriendRequest.setEnabled(false);
        btnUnfriend.setEnabled(false);
        this.setResizable(false);
        this.setTitle(StringUtil.doTitle(clientControl.getClientUser().getName()) + " - Friend view");
        this.setLocationRelativeTo(HomeView.getInstance(clientControl));
    }

    private void lblShowFriendRequestsMouseClicked(java.awt.event.MouseEvent evt) {
        ReceivedFriendRequestView.getInstance(this.clientControl);
        this.dispose();
        removeInstance();
    }

    private void lblShowSentRequestsMouseClicked(MouseEvent evt) {
        SentFriendRequestView.getInstance(this.clientControl);
        this.dispose();
        removeInstance();
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchData = this.txtSearchInput.getText();
        clientControl.sendData(new DataWrapper(Const.SEARCH_USER, searchData));
    }

    private void btnUnfriendActionPerformed(ActionEvent evt) {
        try {
            Friendship friendship = new Friendship();
            friendship.setUser(this.clientControl.getClientUser());
            friendship.setFriend(this.selectedUser);
            clientControl.sendData(new DataWrapper(Const.SEND_UNFRIEND_REQUEST, friendship));
        } catch (NullPointerException e) {
            String message = "Please select a user to unfriend!";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Friend request",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btnSendFriendRequestActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setSender(this.clientControl.getClientUser());
            friendRequest.setReceiver(this.selectedUser);
            clientControl.sendData(new DataWrapper(Const.SEND_FRIEND_REQUEST, friendRequest));
        } catch (NullPointerException e) {
            String message = "Please select a user in the list to send an friend request!";
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Friend request",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void processReceivedUsersData(DataWrapper dataWrapper) {
        if (dataWrapper.getData() instanceof ArrayList<?>) {
            this.userResults = (ArrayList<User>) dataWrapper.getData();
            updateTblUserResults();
        }
    }

    private void removeYourself() {
        User removeUser = null;
        for (User user : userResults) {
            if (user.getId() == clientControl.getClientUser().getId()) {
                removeUser = user;
            }
        }
        if (removeUser != null) {
            userResults.remove(removeUser);
        }
    }

    private void updateTblUserResults() {
        removeYourself();
        this.lblResultCount.setText("Result: " + userResults.size());
        String[] columnNames = {"No", "User"};
        String[][] value = new String[userResults.size()][columnNames.length];
        for(int i=0; i<userResults.size(); i++){
            value[i][0] = (i + 1) + "";
            value[i][1] = userResults.get(i).getName();
        }
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }

        };
        tblUserResults.getColumnModel().setColumnMargin(20);
        tblUserResults.setModel(tableModel);
        tblUserResults.getColumnModel().getColumn(0).setMaxWidth(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tblUserResults.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblUserResults.getTableHeader().setPreferredSize(new Dimension(100, 25));
        tblUserResults.setRowHeight(25);
        this.setVisible(true);
    }

    public void processSendFriendRequestResult(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        if (friendRequest.getId() != 0) {
            String message = String.format("Sent friend request to %s successfully!", selectedUser.getName());
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Friend request result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void processUnfriendRequestResult(DataWrapper dataWrapper) {
        String result = (String) dataWrapper.getData();
        if (result.equals("Success")) {
            String message = String.format("Unfriend %s successfully!", selectedUser.getName());
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Unfriend request result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
