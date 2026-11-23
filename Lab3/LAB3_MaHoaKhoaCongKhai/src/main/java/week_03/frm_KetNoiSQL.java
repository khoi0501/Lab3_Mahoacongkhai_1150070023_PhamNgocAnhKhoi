package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class frm_KetNoiSQL extends javax.swing.JFrame {

    private JTextField txt_server;
    private JTextField txt_port;
    private JTextField txt_database;
    private JTextField txt_user;
    private JPasswordField txt_password;
    private JButton btn_test;
    private JButton btn_history;
    private JLabel lbl_status;

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(520, 360));
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Tiêu đề
        JLabel lblHeader = new JLabel("KẾT NỐI MICROSOFT SQL SERVER", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Form Fields Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Server
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        JLabel lblServer = new JLabel("Server:");
        lblServer.setFont(labelFont);
        formPanel.add(lblServer, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.75;
        txt_server = new JTextField("127.0.0.1");
        txt_server.setFont(fieldFont);
        formPanel.add(txt_server, gbc);

        // Port
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.25;
        JLabel lblPort = new JLabel("Port:");
        lblPort.setFont(labelFont);
        formPanel.add(lblPort, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.75;
        txt_port = new JTextField("1433");
        txt_port.setFont(fieldFont);
        formPanel.add(txt_port, gbc);

        // Database
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.25;
        JLabel lblDb = new JLabel("Database:");
        lblDb.setFont(labelFont);
        formPanel.add(lblDb, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.75;
        txt_database = new JTextField("LAB3_MaHoaKhoaCongKhai");
        txt_database.setFont(fieldFont);
        formPanel.add(txt_database, gbc);

        // User
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.25;
        JLabel lblUser = new JLabel("User:");
        lblUser.setFont(labelFont);
        formPanel.add(lblUser, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.75;
        txt_user = new JTextField("lab3_user");
        txt_user.setFont(fieldFont);
        formPanel.add(txt_user, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.25;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(labelFont);
        formPanel.add(lblPass, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.75;
        txt_password = new JPasswordField();
        txt_password.setFont(fieldFont);
        formPanel.add(txt_password, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Bottom Panel: Buttons + Status
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 10));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btn_test = new JButton("Test Connection");
        btn_test.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_test.setPreferredSize(new Dimension(150, 32));
        btn_test.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_testActionPerformed(evt);
            }
        });
        btnPanel.add(btn_test);

        btn_history = new JButton("Xem lịch sử");
        btn_history.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_history.setPreferredSize(new Dimension(130, 32));
        btn_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_historyActionPerformed(evt);
            }
        });
        btnPanel.add(btn_history);

        bottomPanel.add(btnPanel, BorderLayout.NORTH);

        lbl_status = new JLabel("Chưa kết nối.", JLabel.LEFT);
        lbl_status.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lbl_status.setForeground(new Color(60, 60, 60));
        lbl_status.setPreferredSize(new Dimension(450, 36));
        bottomPanel.add(lbl_status, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
    }

    private void btn_testActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int port = Integer.parseInt(txt_port.getText().trim());
            DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
                txt_user.getText().trim(), txt_password.getPassword());
            lbl_status.setText("<html>" + DbConfig.test() + "</html>");
        } catch (Exception ex) {
            DbConfig.clear();
            lbl_status.setText("<html>Kết nối thất bại: " + ex.getMessage() + "</html>");
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Không kết nối được SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_LichSu().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_KetNoiSQL().setVisible(true);
            }
        });
    }
}
