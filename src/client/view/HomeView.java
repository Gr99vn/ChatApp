package client.view;
 
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import client.control.ClientControl;
import model.*;
import utils.Const;
import utils.StringUtil;

import java.awt.*;
import java.util.Map;

public class HomeView extends JFrame {
    private List<User> friends;
    private List<Friendship> friendships;
    private List<Room> roomList;
    private JTable tblFriend;
    private ClientControl clientControl;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane4;
    private JTable tblRoom;
    private JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAddFriend;
    private javax.swing.JLabel lblAddRoom;
    public static final int WINDOW_WIDTH = 202;
    public static final int GAP = 20;


    // Parameter: ClientControl Map
    private static Map<Integer, HomeView> homeViewMap = new HashMap<>();

    public static boolean isAvailable() {
        return homeViewMap.containsKey(Const.HOME_VIEW);
    }

    public static HomeView getInstance(ClientControl clientControl) {
        HomeView homeView = homeViewMap.get(Const.HOME_VIEW);
        if (homeView == null) {
            homeView = new HomeView(clientControl);
            homeViewMap.put(Const.HOME_VIEW, homeView);
        }
        return homeView;
    }

    public static void removeInstance() {
        if (homeViewMap.get(Const.HOME_VIEW) != null) {
            homeViewMap.remove(Const.HOME_VIEW);
        }
    }
     
    public HomeView(ClientControl clientControl){
        this.clientControl = clientControl;
        friends = new ArrayList<>();
        friendships = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblFriend = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRoom = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lblAddFriend = new javax.swing.JLabel();
        lblAddRoom = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Chat Client");

        jLabel2.setText("> Friends");
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tblFriend.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "", ""
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblFriend);
        if (tblFriend.getColumnModel().getColumnCount() > 0) {
            tblFriend.getColumnModel().getColumn(1).setMaxWidth(20);
        }

        tblRoom.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "", ""
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblRoom);

        jLabel3.setText("> Rooms");
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N


        lblAddFriend.setText(" Add+ ");
        lblAddFriend.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblAddFriend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddFriendMouseClicked(evt);
            }
        });

        lblAddRoom.setText(" Add+ ");
        lblAddRoom.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblAddRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddRoomMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblAddRoom))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblAddFriend)))
                                                .addContainerGap())))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane3, jScrollPane4});

        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(lblAddFriend))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(lblAddRoom))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane3, jScrollPane4});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();

        tblFriend.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblFriend.getSelectedRow() != -1 && tblFriend.getSelectedRow() < tblFriend.getRowCount()) {
                    int selectedRowIndex = tblFriend.getSelectedRow();
                    selectedRowIndex = tblFriend.convertRowIndexToModel(selectedRowIndex);
                    Friendship friendship = friendships.get(selectedRowIndex);
                    ChatBoxView.getInstance(clientControl, friendship.getRoom());
                }
            }
        });

        tblRoom.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblRoom.getSelectedRow() != -1 && tblRoom.getSelectedRow() < tblRoom.getRowCount()) {
                    int selectedRowIndex = tblRoom.getSelectedRow();
                    selectedRowIndex = tblRoom.convertRowIndexToModel(selectedRowIndex);
                    Room room = roomList.get(selectedRowIndex);
                    ChatBoxView.getInstance(clientControl, room);
                }
            }
        });

        this.setResizable(false);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();
        this.setTitle(StringUtil.doTitle(clientControl.getClientUser().getName()));
        this.setLocation(width - client.view.HomeView.WINDOW_WIDTH - client.view.HomeView.GAP, client.view.HomeView.GAP);
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    private void lblAddFriendMouseClicked(java.awt.event.MouseEvent evt) {
        FriendView.getInstance(this.clientControl);
    }

    private void lblAddRoomMouseClicked(java.awt.event.MouseEvent evt) {
        RoomView.getInstance(this.clientControl);
    }
     
    /**
     * Treatment of search result received from the server
     * @param data
     */
    public void processFriendList(DataWrapper data) {
        if(data.getData() instanceof ArrayList<?>) {
            friends = (ArrayList<User>) data.getData();
            updateTblFriend();
        }else {
            tblFriend.setModel(null);
        }
    }

    public void processHomeViewData(DataWrapper data) {
        if(data.getData() instanceof ArrayList<?>) {
            List<Object> returnList = (ArrayList<Object>) data.getData();

            friendships = (ArrayList<Friendship>) returnList.get(0);
            getFriendsFromFriendships();
            roomList = (ArrayList<Room>) returnList.get(1);
            updateTblFriend();
            updateTblRoom();

        }else {
            tblFriend.setModel(null);
            tblRoom.setModel(null);
        }
    }

    private void updateTblFriend() {
        String[] columnNames = {"User", "Status"};
        String[][] value = new String[friends.size()][columnNames.length];
        for(int i=0; i<friends.size(); i++){
            value[i][0] = friends.get(i).getName();
            value[i][1] = friends.get(i).getStatus();
        }
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tblFriend.setModel(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tblFriend.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblFriend.getTableHeader().setPreferredSize(new Dimension(100, 25));
        tblFriend.setRowHeight(25);
        tblFriend.getColumnModel().setColumnMargin(20);
        tblFriend.getColumnModel().getColumn(1).setMaxWidth(60);
    }

    private void updateTblRoom() {
        String[] columnNames = {"Room"};
        String[][] value = new String[roomList.size()][columnNames.length];
        for(int i=0; i<roomList.size(); i++){
            value[i][0] = roomList.get(i).getName();
        }
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }
        };
        tblRoom.setModel(tableModel);
        tblRoom.getTableHeader().setPreferredSize(new Dimension(100, 25));
        tblRoom.setRowHeight(25);
        tblRoom.getColumnModel().setColumnMargin(20);
        this.setVisible(true);
    }

    private void getFriendsFromFriendships() {
        friends.clear();
        for (Friendship friendship : friendships) {
            if (friendship.getUser().getId() != clientControl.getClientUser().getId()) {
                friends.add(friendship.getUser());
            }
            if (friendship.getFriend().getId() != clientControl.getClientUser().getId()) {
                friends.add(friendship.getFriend());
            }
        }
    }

    public void processNewFriendRequest(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        if (friendRequest.getId() != 0) {
            String message = String.format("You has a new friend request from %s.\nDo you want to process?", friendRequest.getSender().getName());
            int userChoice = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "New friend request",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (userChoice == 0) {
                ReceivedFriendRequestView.getInstance(clientControl);
            }
        }
    }

    public void processFriendshipList(DataWrapper dataWrapper) {
        if(dataWrapper.getData() instanceof ArrayList<?>) {
            friendships = (ArrayList<Friendship>) dataWrapper.getData();
            getFriendsFromFriendships();
            updateTblFriend();
        }else {
            tblFriend.setModel(null);
        }
    }

    public void processAcceptReceivedFriendRequest(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        String message;
        if (clientControl.getClientUser().getId() == friendRequest.getSender().getId()) {
            String name = StringUtil.doTitle(friendRequest.getReceiver().getName());
            message = String.format("%s accept your friend request.\nYou and %s now are friends.", name, name);
        } else if (clientControl.getClientUser().getId() == friendRequest.getReceiver().getId()){
            String name = StringUtil.doTitle(friendRequest.getSender().getName());
            message = String.format("You and %s now are friends.", name);
        } else {
            return;
        }
        JOptionPane.showMessageDialog(
                this,
                message,
                "Answer friend request",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void processRejectReceivedFriendRequest(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        String message;
        if (clientControl.getClientUser().getId() == friendRequest.getSender().getId()) {
            String name = StringUtil.doTitle(friendRequest.getReceiver().getName());
            message = String.format("Your friend request to %s have been rejected.", name);
        } else if (clientControl.getClientUser().getId() == friendRequest.getReceiver().getId()){
            String name = StringUtil.doTitle(friendRequest.getSender().getName());
            message = String.format("%s's friend request rejected.", name);
        } else {
            return;
        }
        JOptionPane.showMessageDialog(
                this,
                message,
                "Answer friend request",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void announceResult(DataWrapper dataWrapper) {
        if (dataWrapper.getData() instanceof String) {
            String message = (String) dataWrapper.getData();
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Multiple member room",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void processRoomList(DataWrapper dataWrapper) {
        if(dataWrapper.getData() instanceof ArrayList<?>) {
            roomList = (ArrayList<Room>) dataWrapper.getData();
            updateTblRoom();
        }else {
            tblRoom.setModel(null);
        }
    }
}