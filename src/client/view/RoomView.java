package client.view;

import client.control.ClientControl;
import model.DataWrapper;
import model.Room;
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

public class RoomView extends JFrame {
    private ClientControl clientControl;
    private List<Room> rooms = new ArrayList<>();
    private Room selectedRoom;
    private javax.swing.JButton btnCreateRoom;
    private javax.swing.JButton btnModifyRoom;
    private javax.swing.JButton btnOutRoom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRoomMemberSize;
    private javax.swing.JLabel lblRoomName;
    private javax.swing.JTable tblRoomList;

    // Parameter: ClientControl Map
    private static Map<Integer, RoomView> roomViewMap = new HashMap<>();

    public static boolean isAvailable() {
        return roomViewMap.containsKey(Const.ROOM_VIEW);
    }

    public static RoomView getInstance(ClientControl clientControl) {
        RoomView roomView = roomViewMap.get(Const.ROOM_VIEW);
        if (roomView == null) {
            roomView = new RoomView(clientControl);
            roomViewMap.put(Const.ROOM_VIEW, roomView);
        }
        return roomView;
    }

    public static void removeInstance() {
        if (roomViewMap.get(Const.ROOM_VIEW) != null) {
            roomViewMap.remove(Const.ROOM_VIEW);
        }
    }

    public RoomView(ClientControl clientControl) {
        this.clientControl = clientControl;

        initComponents();

        clientControl.sendData(new DataWrapper(
                Const.GET_FULLY_ROOM_LIST,
                ""
        ));

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                removeInstance();
            }
        });
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoomList = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnModifyRoom = new javax.swing.JButton();
        btnOutRoom = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblRoomName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblRoomMemberSize = new javax.swing.JLabel();
        btnCreateRoom = new javax.swing.JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Rooms");

        tblRoomList.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No", "Room"
                }
        ));
        jScrollPane1.setViewportView(tblRoomList);
        if (tblRoomList.getColumnModel().getColumnCount() > 0) {
            tblRoomList.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(244, 256));

        jLabel2.setText("Action");

        btnModifyRoom.setText("Modify room");

        btnOutRoom.setText("Out room");

        jLabel3.setText("Name:");

        lblRoomName.setText("Unknown");

        jLabel5.setText("Member:");

        lblRoomMemberSize.setText("0");

        btnCreateRoom.setText("Create room");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(61, 61, 61)
                                                .addComponent(jLabel2)
                                                .addGap(0, 63, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel5)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblRoomMemberSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(lblRoomName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnOutRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnModifyRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(btnCreateRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel5});

        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRoomName)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(lblRoomMemberSize))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCreateRoom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnModifyRoom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnOutRoom)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();

        btnCreateRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateRoomActionPerformed(evt);
            }
        });

        btnModifyRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyRoomActionPerformed(evt);
            }
        });

        btnOutRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutRoomActionPerformed(evt);
            }
        });

        tblRoomList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tblRoomList.getSelectedRow() != -1 && tblRoomList.getSelectedRow() < tblRoomList.getRowCount()) {
                    int selectedRowIndex = tblRoomList.getSelectedRow();
                    selectedRowIndex = tblRoomList.convertRowIndexToModel(selectedRowIndex);
                    selectedRoom = rooms.get(selectedRowIndex);
                    if (selectedRoom != null) {
                        lblRoomName.setText(selectedRoom.getName());
                        lblRoomMemberSize.setText(String.valueOf(selectedRoom.getRoomUsers().size()));
                        btnModifyRoom.setEnabled(true);
                        btnOutRoom.setEnabled(true);
                    } else {
                        btnModifyRoom.setEnabled(false);
                        btnOutRoom.setEnabled(false);
                    }
                }
            }
        });

        btnModifyRoom.setEnabled(false);
        btnOutRoom.setEnabled(false);
        this.setResizable(false);
        this.setTitle(StringUtil.doTitle(clientControl.getClientUser().getName()) + " - Room view");
        this.setLocationRelativeTo(HomeView.getInstance(clientControl));
    }

    private void btnCreateRoomActionPerformed(ActionEvent evt) {
        RoomManagementView.getInstance(clientControl, new Room());
        this.dispose();
        removeInstance();
    }

    private void btnOutRoomActionPerformed(ActionEvent evt) {
        String message = "Are you sure to leave this room?";
        int userChoice = JOptionPane.showConfirmDialog(
                rootPane,
                message,
                "Leave room",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (userChoice == 0) {
            Room userLeaveRoom = new Room(selectedRoom.getId());
            userLeaveRoom.setName(selectedRoom.getName());
            userLeaveRoom.getRoomUsers().add(clientControl.getClientUser());

            clientControl.sendData(new DataWrapper(
                    Const.SEND_LEAVE_ROOM_REQUEST,
                    userLeaveRoom
            ));

            this.dispose();
            removeInstance();
        }
    }

    private void btnModifyRoomActionPerformed(ActionEvent evt) {
        RoomManagementView.getInstance(clientControl, selectedRoom);
        this.dispose();
        removeInstance();
    }

    public void processFullyRoomListResult(DataWrapper dataWrapper) {
        this.rooms = (ArrayList<Room>) dataWrapper.getData();
        updateTblRoom();
    }

    private void updateTblRoom() {
        String[] columnNames = {"No", "Room"};
        String[][] value = new String[rooms.size()][columnNames.length];
        for(int i=0; i<rooms.size(); i++){
            value[i][0] = String.valueOf(i + 1);
            value[i][1] = rooms.get(i).getName();
        }
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRoomList.getColumnModel().setColumnMargin(20);
        tblRoomList.setModel(tableModel);
        tblRoomList.getColumnModel().getColumn(0).setMaxWidth(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tblRoomList.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblRoomList.getTableHeader().setPreferredSize(new Dimension(100, 25));
        tblRoomList.setRowHeight(25);
        this.setVisible(true);
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
}
