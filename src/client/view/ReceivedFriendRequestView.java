package client.view;

import client.control.ClientControl;
import model.DataWrapper;
import model.FriendRequest;
import utils.Const;
import utils.StringUtil;
import utils.TimeUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceivedFriendRequestView extends JFrame {
    private ClientControl clientControl;
    private List<FriendRequest> friendRequests = new ArrayList<>();
    private javax.swing.JLabel lblModeTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFriendRequests;
    private javax.swing.JButton btnBack;


    // Parameter: ClientControl Map
    private static Map<Integer, ReceivedFriendRequestView> friendRequestViewMap = new HashMap<>();

    public static boolean isAvailable() {
        return friendRequestViewMap.containsKey(Const.RECEIVED_FRIEND_REQUEST_VIEW);
    }

    public static ReceivedFriendRequestView getInstance(ClientControl clientControl) {
        ReceivedFriendRequestView receivedFriendRequestView = friendRequestViewMap.get(Const.RECEIVED_FRIEND_REQUEST_VIEW);
        if (receivedFriendRequestView == null) {
            receivedFriendRequestView = new ReceivedFriendRequestView(clientControl);
            friendRequestViewMap.put(Const.RECEIVED_FRIEND_REQUEST_VIEW, receivedFriendRequestView);
        }
        return receivedFriendRequestView;
    }

    public static void removeInstance() {
        if (friendRequestViewMap.get(Const.RECEIVED_FRIEND_REQUEST_VIEW) != null) {
            friendRequestViewMap.remove(Const.RECEIVED_FRIEND_REQUEST_VIEW);
        }
    }


    public ReceivedFriendRequestView(ClientControl clientControl) {
        this.clientControl = clientControl;
        initComponents();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                removeInstance();
            }
        });

        clientControl.sendData(new DataWrapper(Const.GET_RECEIVED_FRIEND_REQUEST_LIST, ""));
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        lblModeTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFriendRequests = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        lblModeTitle.setText("Received Friendship Requests");
        lblModeTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        tblFriendRequests.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No", "User", "Time"
                }
        ));
        tblFriendRequests.setToolTipText("Click to a friend request to react!");
        jScrollPane1.setViewportView(tblFriendRequests);
        if (tblFriendRequests.getColumnModel().getColumnCount() > 0) {
            tblFriendRequests.getColumnModel().getColumn(0).setMaxWidth(40);
            tblFriendRequests.getColumnModel().getColumn(1).setMaxWidth(200);
            tblFriendRequests.getColumnModel().getColumn(2).setMaxWidth(300);
        }

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblModeTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblModeTitle)
                                        .addComponent(btnBack))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        tblFriendRequests.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblFriendRequests.getSelectedRow() != -1 && tblFriendRequests.getSelectedRow() < tblFriendRequests.getRowCount()) {
                    int selectedRowIndex = tblFriendRequests.getSelectedRow();
                    selectedRowIndex = tblFriendRequests.convertRowIndexToModel(selectedRowIndex);
                    FriendRequest selectedFriendRequest = friendRequests.get(selectedRowIndex);
                    answerFriendRequest(selectedFriendRequest);
                }
            }
        });

        this.setTitle(StringUtil.doTitle(clientControl.getClientUser().getName()) + " - Received friend request view");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(HomeView.getInstance(clientControl));
    }

    private void answerFriendRequest(FriendRequest friendRequest) {
        String message = String.format("Accept the friend request from %s ?\nYes. Accept\nNo. Reject", friendRequest.getSender().getName());
        int userChoice = JOptionPane.showConfirmDialog(
                this, message,
                "Friendship request action",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (userChoice == 0) {
            clientControl.sendData(
                    new DataWrapper(Const.ACCEPT_RECEIVED_FRIEND_REQUEST, friendRequest)
            );
        } else if (userChoice == 1) {
            clientControl.sendData(
                    new DataWrapper(Const.REJECT_RECEIVED_FRIEND_REQUEST, friendRequest)
            );
        }
    }

    private void btnBackActionPerformed(ActionEvent e) {
        FriendView.getInstance(clientControl);
        this.dispose();
        removeInstance();
    }

    public void processReceivedFriendRequestList(DataWrapper dataWrapper) {
        this.friendRequests = (ArrayList<FriendRequest>) dataWrapper.getData();
        updateReceiveFriendRequestViewTable();
    }

    public void updateReceiveFriendRequestViewTable() {
        String[] columnNames = new String[]{"No", "sender", "Time"};
        String[][] value = new String[friendRequests.size()][columnNames.length];
        for(int i=0; i<friendRequests.size(); i++){
            value[i][0] = (i + 1) + "";
            value[i][1] = friendRequests.get(i).getSender().getName();
            value[i][2] = TimeUtils.convertDateTime(friendRequests.get(i).getSentTime());
        }
        updateTblFriendRequests(columnNames, value);
    }
    public void updateTblFriendRequests(String[] columnNames, String[][] value) {
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }

        };
        tblFriendRequests.getColumnModel().setColumnMargin(20);
        tblFriendRequests.setModel(tableModel);
        tblFriendRequests.getColumnModel().getColumn(0).setMaxWidth(30);
        tblFriendRequests.getColumnModel().getColumn(1).setMaxWidth(200);
        tblFriendRequests.getColumnModel().getColumn(2).setMaxWidth(300);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tblFriendRequests.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblFriendRequests.getTableHeader().setPreferredSize(new Dimension(100, 25));
        tblFriendRequests.setRowHeight(25);
        this.setVisible(true);
    }
}
