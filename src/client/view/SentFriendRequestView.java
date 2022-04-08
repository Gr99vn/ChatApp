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

public class SentFriendRequestView extends JFrame {
    private ClientControl clientControl;
    private List<FriendRequest> friendRequests = new ArrayList<>();
    private JLabel lblModeTitle;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable tblFriendRequests;
    private JButton btnBack;


    // Parameter: ClientControl Map
    private static Map<Integer, SentFriendRequestView> friendRequestViewMap = new HashMap<>();

    public static boolean isAvailable() {
        return friendRequestViewMap.containsKey(Const.SENT_FRIEND_REQUEST_VIEW);
    }

    public static SentFriendRequestView getInstance(ClientControl clientControl) {
        SentFriendRequestView friendRequestView = friendRequestViewMap.get(Const.SENT_FRIEND_REQUEST_VIEW);
        if (friendRequestView == null) {
            friendRequestView = new SentFriendRequestView(clientControl);
            friendRequestViewMap.put(Const.SENT_FRIEND_REQUEST_VIEW, friendRequestView);
        }
        return friendRequestView;
    }

    public static void removeInstance() {
        if (friendRequestViewMap.get(Const.SENT_FRIEND_REQUEST_VIEW) != null) {
            friendRequestViewMap.remove(Const.SENT_FRIEND_REQUEST_VIEW);
        }
    }


    public SentFriendRequestView(ClientControl clientControl) {
        this.clientControl = clientControl;
        initComponents();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                removeInstance();
            }
        });

        clientControl.sendData(new DataWrapper(Const.GET_SENT_FRIEND_REQUEST_LIST, ""));
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        lblModeTitle = new JLabel();
        jScrollPane1 = new JScrollPane();
        tblFriendRequests = new JTable();
        btnBack = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        lblModeTitle.setText("Sent Friendship Requests");
        lblModeTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        tblFriendRequests.setModel(new DefaultTableModel(
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
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblModeTitle, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblModeTitle)
                                        .addComponent(btnBack))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        tblFriendRequests.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblFriendRequests.getSelectedRow() != -1 && tblFriendRequests.getSelectedRow() < tblFriendRequests.getRowCount()) {
                    int selectedRowIndex = tblFriendRequests.getSelectedRow();
                    selectedRowIndex = tblFriendRequests.convertRowIndexToModel(selectedRowIndex);
                    FriendRequest selectedFriendRequest = friendRequests.get(selectedRowIndex);
                    doSentFriendRequestAction(selectedFriendRequest);
                }
            }
        });

        this.setTitle(StringUtil.doTitle(clientControl.getClientUser().getName()) + " - Sent friend request view");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(HomeView.getInstance(clientControl));
    }

    private void doSentFriendRequestAction(FriendRequest friendRequest) {
        String message = String.format("Do you want to cancel the friend request to %s?", friendRequest.getReceiver().getName());
        int userChoice = JOptionPane.showConfirmDialog(
                this, message,
                "Friendship request action",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (userChoice == 0) {
            clientControl.sendData(new DataWrapper(
                    Const.CANCEL_SENT_FRIEND_REQUEST,
                    friendRequest
            ));
        }
    }

    private void btnBackActionPerformed(ActionEvent e) {
        FriendView.getInstance(clientControl);
        this.dispose();
        removeInstance();
    }

    public void processSentFriendRequestList(DataWrapper dataWrapper) {
        this.friendRequests = (ArrayList<FriendRequest>) dataWrapper.getData();
        updateSentFriendRequestViewTable();
    }

    public void updateSentFriendRequestViewTable() {
        String[] columnNames = new String[]{"No", "Receiver", "Time"};
        String[][] value = new String[friendRequests.size()][columnNames.length];
        for(int i=0; i<friendRequests.size(); i++){
            value[i][0] = (i + 1) + "";
            value[i][1] = friendRequests.get(i).getReceiver().getName();
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
