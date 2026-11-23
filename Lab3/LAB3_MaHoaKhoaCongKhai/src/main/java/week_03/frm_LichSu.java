package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class frm_LichSu extends javax.swing.JFrame {

    private JTable tbl_history;
    private JButton btn_refresh;
    private JLabel lbl_status;

    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        tbl_history.setModel(HistoryDao.emptyModel());
        loadData();
    }

    private void initComponents() {
        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 420));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Tiêu đề
        JLabel lblHeader = new JLabel("LỊCH SỬ THAO TÁC - BẢNG DBO.HISTORY", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Bảng dữ liệu
        tbl_history = new JTable();
        tbl_history.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tbl_history.setRowHeight(22);
        tbl_history.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(tbl_history);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 5));

        btn_refresh = new JButton("Tải lại");
        btn_refresh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_refresh.setPreferredSize(new Dimension(110, 30));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnPanel.add(btn_refresh);
        bottomPanel.add(btnPanel, BorderLayout.WEST);

        lbl_status = new JLabel("Đang tải...");
        lbl_status.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lbl_status.setForeground(new Color(60, 60, 60));
        lbl_status.setBorder(new EmptyBorder(0, 15, 0, 0));
        bottomPanel.add(lbl_status, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
    }

    /** Đọc 50 dòng mới nhất từ SQL Server và hiện lên JTable. */
    private void loadData() {
        try {
            tbl_history.setModel(HistoryDao.loadLatest(50));
            lbl_status.setText("Đã tải " + tbl_history.getRowCount() + " dòng mới nhất từ dbo.History.");
        } catch (java.sql.SQLException ex) {
            lbl_status.setText("Không đọc được lịch sử: " + ex.getMessage());
        }
    }

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadData();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_LichSu().setVisible(true);
            }
        });
    }
}
