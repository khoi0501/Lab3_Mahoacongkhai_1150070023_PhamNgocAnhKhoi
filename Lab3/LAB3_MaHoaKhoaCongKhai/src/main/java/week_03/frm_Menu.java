package week_03;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class frm_Menu extends javax.swing.JFrame {

    private JButton btn_rsa;
    private JButton btn_ecc;
    private JButton btn_rsaaes;
    private JButton btn_signature;
    private JButton btn_sql;
    private JButton btn_history;

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 3 - Mã hóa khóa công khai");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 360));
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 8));
        JLabel lblTitle = new JLabel("LAB 3 - MÃ HÓA KHÓA CÔNG KHAI", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(lblTitle);

        JLabel lblSub = new JLabel("Chọn một bài để mở form tương ứng (bài 3.1 chạy RSADemo.java bằng Run File):", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        headerPanel.add(lblSub);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Buttons Grid (3 rows, 2 columns)
        JPanel gridPanel = new JPanel(new GridLayout(3, 2, 20, 15));
        Font btnFont = new Font("Segoe UI", Font.PLAIN, 14);

        btn_rsa = new JButton("3.2 RSA (Swing)");
        btn_rsa.setFont(btnFont);
        btn_rsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rsaActionPerformed(evt);
            }
        });
        gridPanel.add(btn_rsa);

        btn_ecc = new JButton("3.3 ECC");
        btn_ecc.setFont(btnFont);
        btn_ecc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eccActionPerformed(evt);
            }
        });
        gridPanel.add(btn_ecc);

        btn_rsaaes = new JButton("3.4 RSA + AES");
        btn_rsaaes.setFont(btnFont);
        btn_rsaaes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rsaaesActionPerformed(evt);
            }
        });
        gridPanel.add(btn_rsaaes);

        btn_signature = new JButton("3.5 Chữ ký ECDSA");
        btn_signature.setFont(btnFont);
        btn_signature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signatureActionPerformed(evt);
            }
        });
        gridPanel.add(btn_signature);

        btn_sql = new JButton("Kết nối SQL Server");
        btn_sql.setFont(btnFont);
        btn_sql.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sqlActionPerformed(evt);
            }
        });
        gridPanel.add(btn_sql);

        btn_history = new JButton("Xem lịch sử");
        btn_history.setFont(btnFont);
        btn_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_historyActionPerformed(evt);
            }
        });
        gridPanel.add(btn_history);

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        pack();
    }

    private void btn_rsaActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_RSA().setVisible(true);
    }

    private void btn_eccActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_ECC().setVisible(true);
    }

    private void btn_rsaaesActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_RSA_AES().setVisible(true);
    }

    private void btn_signatureActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_ECC_Signature().setVisible(true);
    }

    private void btn_sqlActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_KetNoiSQL().setVisible(true);
    }

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_LichSu().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_Menu().setVisible(true);
            }
        });
    }
}
