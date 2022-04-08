package client.view;

import client.control.ClientControl;
import model.DataWrapper;
import model.Message;
import model.Room;
import model.User;
import utils.Const;
import utils.StringUtil;
import utils.TimeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatBoxView extends javax.swing.JFrame {
    private ClientControl clientControl;
    private User friend;
    private Room room;
    private javax.swing.JButton btnSendMsg;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextArea txtMsgDisplay;
    private javax.swing.JTextField txtMsgInput;
    public static final int WINDOW_WIDTH = 500;
    public static final int GAP = 20;

    private static Map<Integer, ChatBoxView> chatBoxViewsMap = new HashMap<>();

    public static ChatBoxView getInstance(ClientControl clientControl, Room room) {
        ChatBoxView chatBoxView = chatBoxViewsMap.get(room.getId());
        if (chatBoxView == null) {
            chatBoxView = new ChatBoxView(clientControl, room);
            chatBoxViewsMap.put(room.getId(), chatBoxView);
        }
        return chatBoxView;
    }


    public ChatBoxView(ClientControl clientControl, Room room) {
        this.clientControl = clientControl;
        this.room = room;

        initComponents();
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                chatBoxViewsMap.remove(room.getId());
            }
        });

        clientControl.sendData(new DataWrapper(Const.GET_CHAT_BOX_DATA, room));
    }

    private void destroyData() {
        this.room = null;
        this.friend = null;
    }

    public Room getRoom() {
        return this.room;
    }

    private void initComponents() {
        String myName = StringUtil.doTitle(clientControl.getClientUser().getName());
        this.setTitle(myName + " Chat Box - " + room.getName());

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMsgDisplay = new javax.swing.JTextArea();
        txtMsgInput = new javax.swing.JTextField();
        btnSendMsg = new javax.swing.JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(500, 527));

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtMsgDisplay.setBorder(BorderFactory.createCompoundBorder(
                txtMsgDisplay.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        txtMsgDisplay.setColumns(20);
        txtMsgDisplay.setRows(5);
        txtMsgDisplay.setEditable(false);
        jScrollPane2.setViewportView(txtMsgDisplay);

        txtMsgInput.setPreferredSize(new java.awt.Dimension(5, 20));
        txtMsgInput.setBorder(BorderFactory.createCompoundBorder(
                txtMsgInput.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));

        btnSendMsg.setText("Send");
        btnSendMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMsgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblTitle)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtMsgInput, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSendMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnSendMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                        .addComponent(txtMsgInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();
        this.setLocation(width - HomeView.WINDOW_WIDTH - HomeView.GAP - client.view.ChatBoxView.WINDOW_WIDTH - client.view.ChatBoxView.GAP , client.view.ChatBoxView.GAP);
        this.setVisible(true);
    }

    public void processChatBoxData(List<Object> returnItems) {
        List<User> users = (ArrayList<User>) returnItems.get(1);
        List<Message> messages = (ArrayList<Message>) returnItems.get(2);
        room.setRoomUsers(users);
        room.setRoomMessages(messages);
        displayHeader();
        displayUserChats();
        txtMsgInput.requestFocusInWindow();
    }

    private void displayHeader() {
        String friendName = "";
        if (friend != null) {
            friendName = StringUtil.doTitle(friend.getName());
        } else {
            for (User u : room.getRoomUsers()) {
                if (u.getId() != clientControl.getClientUser().getId()) {
                    friendName += u.getName() + " ";
                }
            }
            friendName = friendName.trim();
        }
        lblTitle.setText("Chat to \""+ friendName +"\"");
        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

    }

    private void displayUserChats() {
        for (Message message : room.getRoomMessages()) {
            printChatToTextBox(message);
        }
    }

    private void printChatToTextBox(Message message) {
        User sender = getMessageSender(message);
        String senderName = "";
        String messageContent = "";
        if (sender != null) {
            senderName = sender.getName();
            if (sender.getId() == clientControl.getClientUser().getId()) {
                senderName = "You";
            }
        } else {
            senderName = "Unknown";
        }
        messageContent = "> " + senderName + " - " + TimeUtils.convertDateTime(message.getCreatedDate()) + "\n  " + message.getContent() + "\n\n";
        txtMsgDisplay.append(messageContent);
        txtMsgDisplay.setCaretPosition(txtMsgDisplay.getDocument().getLength());
    }

    private void btnSendMsgActionPerformed(java.awt.event.ActionEvent evt) {
        Message message = new Message();
        message.setRoomId(room.getId());
        message.setOwnerId(clientControl.getClientUser().getId());
        message.setContent(txtMsgInput.getText());
        clientControl.sendData(new DataWrapper(Const.SEND_MESSAGE, message));
        txtMsgInput.setText("");
    }

    public void processMessageData(Message message) {
        room.getRoomMessages().add(message);
        printChatToTextBox(message);
    }

    private User getMessageSender(Message message) {
        User sender = null;
        for (User us : room.getRoomUsers()) {
            if (us.getId() == message.getOwnerId()) {
                sender = us;
            }
        }
        return sender;
    }

    public void processCloseRoomChat() {
        this.dispose();
        chatBoxViewsMap.remove(room.getId());
    }
}