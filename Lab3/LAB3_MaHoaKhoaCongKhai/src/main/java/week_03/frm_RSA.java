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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class frm_RSA extends javax.swing.JFrame {

    /** Đối tượng RSA giữ cặp khóa trong suốt thời gian form mở. */
    private final RSACipher rsa = new RSACipher();

    private JComboBox<String> cbo_bits;
    private JButton btn_generate;
    private JLabel lbl_keyinfo;
    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JLabel lbl_status;

    public frm_RSA() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("RSA Cipher");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(660, 480));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 12));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Tiêu đề
        JLabel lblHeader = new JLabel("MẬT MÃ RSA (OAEP - SHA-256)", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Center Content Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Hàng 0: Key size, ComboBox, Generate button, Key info
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel lblKeySize = new JLabel("Key size:");
        lblKeySize.setFont(labelFont);
        keyPanel.add(lblKeySize);

        cbo_bits = new JComboBox<>(new String[] {"2048", "3072"});
        cbo_bits.setFont(textFont);
        cbo_bits.setPreferredSize(new Dimension(100, 28));
        keyPanel.add(cbo_bits);

        btn_generate = new JButton("Generate Keys");
        btn_generate.setFont(textFont);
        btn_generate.setPreferredSize(new Dimension(140, 28));
        btn_generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generateActionPerformed(evt);
            }
        });
        keyPanel.add(btn_generate);

        lbl_keyinfo = new JLabel("Chưa có khóa.");
        lbl_keyinfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lbl_keyinfo.setForeground(new Color(60, 60, 60));
        keyPanel.add(lbl_keyinfo);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.0;
        centerPanel.add(keyPanel, gbc);

        // Hàng 1: Plaintext Label
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weighty = 0.0;
        JLabel lblPlain = new JLabel("Plaintext:");
        lblPlain.setFont(labelFont);
        centerPanel.add(lblPlain, gbc);

        // Hàng 2: Plaintext TextArea
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weighty = 0.4;
        txt_plaintext = new JTextArea(2, 20);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        txt_plaintext.setFont(textFont);
        JScrollPane scrollPlain = new JScrollPane(txt_plaintext);
        centerPanel.add(scrollPlain, gbc);

        // Hàng 3: Ciphertext Label
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weighty = 0.0;
        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setFont(labelFont);
        centerPanel.add(lblCipher, gbc);

        // Hàng 4: Ciphertext TextArea
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weighty = 0.6;
        txt_ciphertext = new JTextArea(3, 20);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        txt_ciphertext.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollCipher = new JScrollPane(txt_ciphertext);
        centerPanel.add(scrollCipher, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Buttons + Status
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 8));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setFont(labelFont);
        btn_encrypt.setPreferredSize(new Dimension(110, 30));
        btn_encrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_encryptActionPerformed(evt);
            }
        });
        btnPanel.add(btn_encrypt);

        btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setFont(labelFont);
        btn_decrypt.setPreferredSize(new Dimension(110, 30));
        btn_decrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_decryptActionPerformed(evt);
            }
        });
        btnPanel.add(btn_decrypt);

        bottomPanel.add(btnPanel, BorderLayout.NORTH);

        lbl_status = new JLabel("Bấm Generate Keys để tạo cặp khóa.", JLabel.LEFT);
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setForeground(new Color(50, 50, 50));
        lbl_status.setPreferredSize(new Dimension(600, 30));
        bottomPanel.add(lbl_status, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        int bits = Integer.parseInt((String) cbo_bits.getSelectedItem());
        try {
            long start = System.currentTimeMillis();
            rsa.generateKeys(bits);
            long ms = System.currentTimeMillis() - start;
            lbl_keyinfo.setText("RSA-" + bits + ": tạo khóa " + ms + " ms, tối đa " + rsa.maxPlaintextBytes() + " byte");
            lbl_status.setText("Đã tạo cặp khóa. Khóa công khai dùng để mã hóa, khóa riêng để giải mã.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tạo được khóa: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_encryptActionPerformed(java.awt.event.ActionEvent evt) {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = rsa.encrypt(plaintext);
            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);
            lbl_status.setText("Đã mã hóa RSA (" + ciphertext.length() + " ký tự Base64). "
                + HistoryDao.tryInsert("RSA", "ENCRYPT", plaintext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA", "ENCRYPT", plaintext.length(), false);
            JOptionPane.showMessageDialog(this, "Mã hóa thất bại: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_decryptActionPerformed(java.awt.event.ActionEvent evt) {
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
            return;
        }
        try {
            String plaintext = rsa.decrypt(ciphertext);
            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);
            lbl_status.setText("Đã giải mã RSA. " + HistoryDao.tryInsert("RSA", "DECRYPT", ciphertext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA", "DECRYPT", ciphertext.length(), false);
            lbl_status.setText("Giải mã RSA THẤT BẠI (sai khóa hoặc bản mã bị sửa).");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa riêng hoặc bản mã bị sửa.\n" + ex,
                "Giải mã thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_RSA().setVisible(true);
            }
        });
    }
}
