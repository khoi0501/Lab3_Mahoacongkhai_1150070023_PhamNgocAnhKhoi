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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class frm_ECC_Signature extends javax.swing.JFrame {

    private final ECCSignature ecdsa = new ECCSignature();

    private JTextArea txt_public;
    private JTextArea txt_private;
    private JButton btn_generate;
    private JButton btn_savekeys;
    private JButton btn_loadkeys;
    private JTextArea txt_message;
    private JTextArea txt_signature;
    private JButton btn_sign;
    private JButton btn_verify;
    private JLabel lbl_result;
    private JLabel lbl_status;

    public frm_ECC_Signature() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("ECDSA Signature");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(680, 580));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Tiêu đề
        JLabel lblHeader = new JLabel("CHỮ KÝ SỐ ECDSA (SHA256withECDSA)", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill = GridBagConstraints.BOTH;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font monoFont = new Font("Consolas", Font.PLAIN, 12);

        // Public key label
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0;
        JLabel lblPub = new JLabel("Public key:");
        lblPub.setFont(labelFont);
        centerPanel.add(lblPub, gbc);

        // Public key textarea
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 0.2;
        txt_public = new JTextArea(2, 20);
        txt_public.setLineWrap(true);
        txt_public.setWrapStyleWord(true);
        txt_public.setEditable(false);
        txt_public.setFont(monoFont);
        centerPanel.add(new JScrollPane(txt_public), gbc);

        // Private key label
        gbc.gridx = 0; gbc.gridy = 2; gbc.weighty = 0;
        JLabel lblPri = new JLabel("Private key:");
        lblPri.setFont(labelFont);
        centerPanel.add(lblPri, gbc);

        // Private key textarea
        gbc.gridx = 0; gbc.gridy = 3; gbc.weighty = 0.2;
        txt_private = new JTextArea(2, 20);
        txt_private.setLineWrap(true);
        txt_private.setWrapStyleWord(true);
        txt_private.setEditable(false);
        txt_private.setFont(monoFont);
        centerPanel.add(new JScrollPane(txt_private), gbc);

        // Key buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.weighty = 0;
        JPanel keyBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 4));
        btn_generate = new JButton("Generate Keys");
        btn_generate.setFont(labelFont);
        btn_generate.setPreferredSize(new Dimension(140, 30));
        btn_generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generateActionPerformed(evt);
            }
        });
        keyBtnPanel.add(btn_generate);

        btn_savekeys = new JButton("Save Keys");
        btn_savekeys.setFont(labelFont);
        btn_savekeys.setPreferredSize(new Dimension(120, 30));
        btn_savekeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_savekeysActionPerformed(evt);
            }
        });
        keyBtnPanel.add(btn_savekeys);

        btn_loadkeys = new JButton("Load Keys");
        btn_loadkeys.setFont(labelFont);
        btn_loadkeys.setPreferredSize(new Dimension(120, 30));
        btn_loadkeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loadkeysActionPerformed(evt);
            }
        });
        keyBtnPanel.add(btn_loadkeys);
        centerPanel.add(keyBtnPanel, gbc);

        // Message label
        gbc.gridx = 0; gbc.gridy = 5; gbc.weighty = 0;
        JLabel lblMsg = new JLabel("Message:");
        lblMsg.setFont(labelFont);
        centerPanel.add(lblMsg, gbc);

        // Message textarea
        gbc.gridx = 0; gbc.gridy = 6; gbc.weighty = 0.25;
        txt_message = new JTextArea(2, 20);
        txt_message.setLineWrap(true);
        txt_message.setWrapStyleWord(true);
        txt_message.setFont(textFont);
        centerPanel.add(new JScrollPane(txt_message), gbc);

        // Signature label
        gbc.gridx = 0; gbc.gridy = 7; gbc.weighty = 0;
        JLabel lblSig = new JLabel("Signature:");
        lblSig.setFont(labelFont);
        centerPanel.add(lblSig, gbc);

        // Signature textarea
        gbc.gridx = 0; gbc.gridy = 8; gbc.weighty = 0.25;
        txt_signature = new JTextArea(2, 20);
        txt_signature.setLineWrap(true);
        txt_signature.setWrapStyleWord(true);
        txt_signature.setFont(monoFont);
        centerPanel.add(new JScrollPane(txt_signature), gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Action Buttons + Result + Status
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 8));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btn_sign = new JButton("Sign");
        btn_sign.setFont(labelFont);
        btn_sign.setPreferredSize(new Dimension(110, 30));
        btn_sign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signActionPerformed(evt);
            }
        });
        actionPanel.add(btn_sign);

        btn_verify = new JButton("Verify");
        btn_verify.setFont(labelFont);
        btn_verify.setPreferredSize(new Dimension(110, 30));
        btn_verify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_verifyActionPerformed(evt);
            }
        });
        actionPanel.add(btn_verify);

        lbl_result = new JLabel("", JLabel.CENTER);
        lbl_result.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl_result.setPreferredSize(new Dimension(160, 30));
        actionPanel.add(lbl_result);

        bottomPanel.add(actionPanel, BorderLayout.NORTH);

        lbl_status = new JLabel("Bấm Generate Keys hoặc Load Keys.", JLabel.LEFT);
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setForeground(new Color(50, 50, 50));
        lbl_status.setPreferredSize(new Dimension(600, 30));
        bottomPanel.add(lbl_status, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
    }

    private void showKeys() {
        txt_public.setText(ecdsa.getPublicKeyBase64());
        txt_private.setText(ecdsa.getPrivateKeyBase64());
        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ecdsa.generateKeys();
            showKeys();
            lbl_status.setText("Đã tạo cặp khóa mới. Bản mã/chữ ký cũ sẽ không dùng được với khóa mới.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tạo được khóa: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_savekeysActionPerformed(java.awt.event.ActionEvent evt) {
        if (txt_public.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có khóa để lưu.");
            return;
        }
        String content = CryptoSupport.toKeyFile(txt_public.getText(), txt_private.getText());
        String path = FileTextIO.saveString(this, content, "ecdsa_keys.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu khóa vào: " + path + " (giữ bí mật dòng PRIVATE).");
        }
    }

    private void btn_loadkeysActionPerformed(java.awt.event.ActionEvent evt) {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] keys = CryptoSupport.fromKeyFile(content);
            ecdsa.loadKeys(keys[0], keys[1]);
            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);
            lbl_status.setText("Đã nạp khóa từ tệp.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tệp khóa không hợp lệ: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_signActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            txt_signature.setText(ecdsa.sign(message));
            lbl_result.setText("");
            lbl_status.setText("Đã ký bằng KHÓA RIÊNG. " + HistoryDao.tryInsert("ECDSA", "SIGN", message.length(), true));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ký thất bại: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_verifyActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            boolean ok = ecdsa.verify(message, txt_signature.getText());
            lbl_result.setText(ok ? "VALID" : "INVALID");
            lbl_result.setForeground(ok ? new java.awt.Color(0, 128, 0) : java.awt.Color.RED);
            lbl_status.setText("Đã xác minh bằng KHÓA CÔNG KHAI. " + HistoryDao.tryInsert("ECDSA", "VERIFY", message.length(), ok));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Xác minh thất bại: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_ECC_Signature().setVisible(true);
            }
        });
    }
}
