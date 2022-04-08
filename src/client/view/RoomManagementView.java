
package client.view;

import client.control.ClientControl;
import model.DataWrapper;
import model.Room;
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

public class RoomManagementView extends javax.swing.JFrame {
    private ClientControl clientControl;
    private Room room;

    private List<User> friends;
    private List<User> roomMembers;

    // Parameter: ClientControl Map
    private static Map<Integer, RoomManagementView> roomManagementViewMap = new HashMap<>();

    public static RoomManagementView getInstance(ClientControl clientControl, Room room) {
        RoomManagementView roomManagementView = roomManagementViewMap.get(Const.ROOM_MANAGEMENT_VIEW);
        if (roomManagementView == null) {
            roomManagementView = new RoomManagementView(clientControl, room);
            roomManagementViewMap.put(Const.ROOM_MANAGEMENT_VIEW, roomManagementView);
        }
        return roomManagementView;
    }

    public static boolean isAvailable() {
        return roomManagementViewMap.containsKey(Const.ROOM_MANAGEMENT_VIEW);
    }

    public static void removeInstance() {
        if (roomManagementViewMap.get(Const.ROOM_MANAGEMENT_VIEW) != null) {
            roomManagementViewMap.remove(Const.ROOM_MANAGEMENT_VIEW);
        }
    }

    public RoomManagementView(ClientControl clientControl, Room room) {
        this.clientControl = clientControl;
        this.room = room;
        this.roomMembers = new ArrayList<>();
        for (User member : room.getRoomUsers()) {
            this.roomMembers.add(member);
        }
        if (room.getId() == 0) {
            room.setName("Enter room name");
            room.getRoomUsers().add(clientControl.getClientUser());
            this.roomMembers.add(clientControl.getClientUser());
        }
        this.friends = HomeView.getInstance(clientControl).getFriends();
        initComponents();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                removeInstance();
            }
        });
    }



    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFriendList = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRoomMember = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        txtRoomName = new javax.swing.JTextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Room management");

        jLabel2.setText("Room name: ");

        jLabel4.setText("Room Users ");

        tblFriendList.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No", "Friend"
                }
        ));
        tblFriendList.setToolTipText("Click to a friend to invite them to the room");
        jScrollPane1.setViewportView(tblFriendList);
        if (tblFriendList.getColumnModel().getColumnCount() > 0) {
            tblFriendList.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        tblRoomMember.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No", "Room member"
                }
        ));
        tblRoomMember.setToolTipText("Click to a member to remove from room.");
        jScrollPane2.setViewportView(tblRoomMember);
        if (tblRoomMember.getColumnModel().getColumnCount() > 0) {
            tblRoomMember.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel5.setText("<=>");

        btnSave.setText("Save");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSaveActionPerformed(e);
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
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtRoomName))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btnSave)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel5)
                                                                .addGap(20, 20, 20)
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtRoomName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(72, 72, 72)
                                                .addComponent(jLabel5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(btnSave)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        txtRoomName.setBorder(BorderFactory.createCompoundBorder(
                txtRoomName.getBorder(),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        tblRoomMember.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblRoomMember.getSelectedRow() != -1 && tblRoomMember.getSelectedRow() < tblRoomMember.getRowCount()) {
                    int selectedRowIndex = tblRoomMember.getSelectedRow();
                    selectedRowIndex = tblRoomMember.convertRowIndexToModel(selectedRowIndex);
                    User selectedUser = roomMembers.get(selectedRowIndex);
                    if (selectedUser != null && selectedUser.getId() == clientControl.getClientUser().getId()) {
                        JOptionPane.showMessageDialog(
                                rootPane,
                                "Cant not remove yourself!",
                                "Multiple member room",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    else if (selectedUser != null && selectedUser.getId() != clientControl.getClientUser().getId()) {
                        String message = String.format("Remove %s from this room?", selectedUser.getName());
                        int userChoice = JOptionPane.showConfirmDialog(
                                rootPane,
                                message,
                                "Remove user",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (userChoice == 0) {
                            removeRoomMember(roomMembers, selectedUser);

                            friends.add(selectedUser);
                            updateTblRoomMember();
                            updateTblFriendList();
                        }
                    }
                }
            }
        });

        tblFriendList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblFriendList.getSelectedRow() != -1 && tblFriendList.getSelectedRow() < tblFriendList.getRowCount()) {
                    int selectedRowIndex = tblFriendList.getSelectedRow();
                    selectedRowIndex = tblFriendList.convertRowIndexToModel(selectedRowIndex);
                    User selectedUser = friends.get(selectedRowIndex);
                    if (selectedUser != null) {
                        String message = String.format("Invite %s to this room?", selectedUser.getName());
                        int userChoice = JOptionPane.showConfirmDialog(
                                rootPane,
                                message,
                                "Invite user",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );
                        if (userChoice == 0) {
                            roomMembers.add(selectedUser);
                            updateTblRoomMember();
                            updateTblFriendList();
                        }
                    }
                }
            }
        });

        txtRoomName.setText(room.getName());
        updateTblRoomMember();
        updateTblFriendList();

        this.setResizable(false);
        this.setTitle(StringUtil.doTitle(clientControl.getClientUser().getName()) + " - Room management view");
        this.setLocationRelativeTo(HomeView.getInstance(clientControl));
    }

    private void btnSaveActionPerformed(ActionEvent e) {
        List<User> originalRoomMembers = new ArrayList<>();
        List<User> presentRoomMembers = this.roomMembers;
        if (presentRoomMembers.size() < 3) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "A multiple room must have at least 3 users!",
                    "Multiple member room",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if (room.getId() == 0) {
            room.setName(txtRoomName.getText());
            room.setRoomUsers(presentRoomMembers);
            room.setType(Room.MULTIPLE);
            clientControl.sendData(new DataWrapper(
                    Const.CREATE_MULTIPLE_MEMBER_ROOM,
                    room
            ));
            this.dispose();
            removeInstance();
            RoomView.getInstance(clientControl);

        } else {
            List<User> removedUsers = new ArrayList<>();
            List<User> invitedUsers = new ArrayList<>();
            originalRoomMembers = this.room.getRoomUsers();
            boolean hasChangeName = !room.getName().equals(txtRoomName.getText());
            if (hasChangeName) {
                room.setName(txtRoomName.getText());
            }
            for (User user : originalRoomMembers) {
                if (!presentRoomMembers.contains(user)) {
                    removedUsers.add(user);
                }
            }
            for (User user : presentRoomMembers) {
                if (!originalRoomMembers.contains(user)) {
                    invitedUsers.add(user);
                }
            }
            if (hasChangeName || !removedUsers.isEmpty() || !invitedUsers.isEmpty()) {
                List<Object> data = new ArrayList<>();
                data.add(clientControl.getClientUser());
                data.add(room);
                data.add(removedUsers);
                data.add(invitedUsers);
                clientControl.sendData(new DataWrapper(
                        Const.MODIFY_MULTIPLE_MEMBER_ROOM,
                        data
                ));
                this.dispose();
                removeInstance();
                RoomView.getInstance(clientControl);
            } else {
                JOptionPane.showMessageDialog(
                        rootPane,
                        "You must do some change!",
                        "Multiple member room",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public void removeRoomMember(List<User> list, User user) {
        User excludeMember =  null;
        for (User member : list) {
            if (member.getId() == user.getId()) {
                excludeMember = member;
            }
        }
        if (excludeMember != null) {
            list.remove(excludeMember);
        }
    }

    public void filterRoomMember() {
        for (User roomMember : roomMembers) {
            User excludeUser = null;
            for (User friend : friends) {
                if (roomMember.getId() == friend.getId()) {
                    excludeUser = friend;
                }
            }
            if (excludeUser != null) {
                friends.remove(excludeUser);
            }
        }
    }

    private void updateTblFriendList() {
        filterRoomMember();
        String[] columnNames = {"No", "Friend"};
        String[][] value = new String[friends.size()][columnNames.length];
        for(int i=0; i<friends.size(); i++){
            value[i][0] = (i + 1) + "";
            value[i][1] = friends.get(i).getName();
        }
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }

        };
        tblFriendList.getColumnModel().setColumnMargin(20);
        tblFriendList.setModel(tableModel);
        tblFriendList.getColumnModel().getColumn(0).setMaxWidth(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tblFriendList.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblFriendList.getTableHeader().setPreferredSize(new Dimension(100, 25));
        tblFriendList.setRowHeight(25);
        this.setVisible(true);
    }

    private void updateTblRoomMember() {
        String[] columnNames = {"No", "Room member"};
        String[][] value = new String[roomMembers.size()][columnNames.length];
        for(int i=0; i<roomMembers.size(); i++){
            value[i][0] = (i + 1) + "";
            value[i][1] = roomMembers.get(i).getName();
        }
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }

        };
        tblRoomMember.getColumnModel().setColumnMargin(20);
        tblRoomMember.setModel(tableModel);
        tblRoomMember.getColumnModel().getColumn(0).setMaxWidth(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tblRoomMember.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblRoomMember.getTableHeader().setPreferredSize(new Dimension(100, 25));
        tblRoomMember.setRowHeight(25);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtRoomName;
    private javax.swing.JTable tblFriendList;
    private javax.swing.JTable tblRoomMember;
    // End of variables declaration//GEN-END:variables
}
