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

public class frm_ECC extends javax.swing.JFrame {

    private final ECCCipher ecc = new ECCCipher();

    private JTextArea txt_public;
    private JTextArea txt_private;
    private JButton btn_generate;
    private JButton btn_savekeys;
    private JButton btn_loadkeys;
    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JLabel lbl_status;

    public frm_ECC() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("ECC Cipher");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(660, 560));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Tiêu đề
        JLabel lblHeader = new JLabel("MẬT MÃ ĐƯỜNG CONG ELLIPTIC (ECC - secp256r1)", JLabel.CENTER);
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

        // Key action buttons
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

        // Plaintext label
        gbc.gridx = 0; gbc.gridy = 5; gbc.weighty = 0;
        JLabel lblPlain = new JLabel("Plaintext:");
        lblPlain.setFont(labelFont);
        centerPanel.add(lblPlain, gbc);

        // Plaintext textarea
        gbc.gridx = 0; gbc.gridy = 6; gbc.weighty = 0.25;
        txt_plaintext = new JTextArea(2, 20);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        txt_plaintext.setFont(textFont);
        centerPanel.add(new JScrollPane(txt_plaintext), gbc);

        // Ciphertext label
        gbc.gridx = 0; gbc.gridy = 7; gbc.weighty = 0;
        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setFont(labelFont);
        centerPanel.add(lblCipher, gbc);

        // Ciphertext textarea
        gbc.gridx = 0; gbc.gridy = 8; gbc.weighty = 0.35;
        txt_ciphertext = new JTextArea(3, 20);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        txt_ciphertext.setFont(monoFont);
        centerPanel.add(new JScrollPane(txt_ciphertext), gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Buttons + Status
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 8));

        JPanel cryptBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setFont(labelFont);
        btn_encrypt.setPreferredSize(new Dimension(110, 30));
        btn_encrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_encryptActionPerformed(evt);
            }
        });
        cryptBtnPanel.add(btn_encrypt);

        btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setFont(labelFont);
        btn_decrypt.setPreferredSize(new Dimension(110, 30));
        btn_decrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_decryptActionPerformed(evt);
            }
        });
        cryptBtnPanel.add(btn_decrypt);

        bottomPanel.add(cryptBtnPanel, BorderLayout.NORTH);

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
        txt_public.setText(ecc.getPublicKeyBase64());
        txt_private.setText(ecc.getPrivateKeyBase64());
        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ecc.generateKeys();
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
        String path = FileTextIO.saveString(this, content, "ecc_keys.txt");
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
            ecc.loadKeys(keys[0], keys[1]);
            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);
            lbl_status.setText("Đã nạp khóa từ tệp.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tệp khóa không hợp lệ: " + ex.getMessage(),
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
            String ciphertext = ecc.encrypt(plaintext);
            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);
            lbl_status.setText("Đã mã hóa ECC (" + ciphertext.length() + " ký tự Base64). "
                + HistoryDao.tryInsert("ECC", "ENCRYPT", plaintext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("ECC", "ENCRYPT", plaintext.length(), false);
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
            String plaintext = ecc.decrypt(ciphertext);
            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);
            lbl_status.setText("Đã giải mã ECC. " + HistoryDao.tryInsert("ECC", "DECRYPT", ciphertext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("ECC", "DECRYPT", ciphertext.length(), false);
            lbl_status.setText("Giải mã ECC THẤT BẠI (sai khóa hoặc bản mã bị sửa).");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa riêng hoặc bản mã bị sửa.\n" + ex,
                "Giải mã thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_ECC().setVisible(true);
            }
        });
    }
}
