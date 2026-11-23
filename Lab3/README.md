# Lab 3 Mã hóa công khai

## THÔNG TIN SINH VIÊN
- **Họ và tên:** Phạm Ngọc Anh Khôi
- **Mã số sinh viên (MSSV):** 1150070023
- **Lớp:** 11_TMDT
- **Học phần:** An toàn và Bảo mật Thông tin
- **Link Video Demo thực hành:** https://www.youtube.com/watch?v=UiLXNzrJU8A

---

## 1. CẤU TRÚC THƯ MỤC DỰ ÁN

Toàn bộ dự án bài Lab 3 được tổ chức trong thư mục `Lab3` với cấu trúc chuẩn như sau:

```text
Lab3/
├── README.md                                                        # File tài liệu hướng dẫn tổng quan dự án
├── ecc_keys.txt                                                    # File lưu cặp khóa ECC thử nghiệm
├── 11_TMDT_LAB3MAHOAKHOACONGKHAI-1150070023-PhamNgocAnhKhoi.docx    # Báo cáo thực hành Lab 3
├── 03_SQL_SERVER/                                                  # Thư mục chứa các script CSDL SQL Server
│   ├── 01_Tao_CSDL.sql                                             # Script tạo Database LAB3_MaHoaKhoaCongKhai và bảng dbo.History
│   ├── 02_Tao_TaiKhoan_LAB.sql                                     # Script tạo SQL Login/User lab3_user và phân quyền
│   └── 03_KiemTra_LichSu.sql                                       # Script kiểm tra truy vấn thống kê lịch sử thao tác
└── LAB3_MaHoaKhoaCongKhai/                                         # Dự án Java Maven chính mở bằng Apache NetBeans
    ├── pom.xml                                                     # Cấu hình Maven (JDK 17, dependency mssql-jdbc 12.8.1.jre11)
    ├── 03_SQL_SERVER/                                              # Bản sao script SQL bên trong project
    └── src/main/java/week_03/                                      # Mã nguồn Java (Package week_03)
        ├── CryptoSupport.java                                      # Tiện ích chung: Base64, UTF-8, AES-GCM, HKDF, xử lý file khóa
        ├── FileTextIO.java                                         # Tiện ích hộp thoại mở và lưu tệp văn bản .txt (UTF-8)
        ├── DbConfig.java                                           # Quản lý thông tin kết nối và tạo Connection JDBC
        ├── HistoryDao.java                                         # Thao tác CSDL: Thêm và đọc lịch sử bảng dbo.History
        ├── RSACipher.java                                          # Thuật toán RSA với đệm OAEP (SHA-256)
        ├── RSADemo.java                                            # Chương trình RSA chạy trên môi trường Console
        ├── ECCCipher.java                                          # Thuật toán ECC/ECIES (đường cong secp256r1 + AES-256-GCM)
        ├── RSA_AES_Cipher.java                                     # Hệ mã lai RSA + AES-GCM cho văn bản dài
        ├── ECCSignature.java                                       # Chữ ký số ECDSA (SHA256withECDSA)
        ├── frm_RSA.java                                            # Giao diện Swing: Mã hóa và giải mã RSA
        ├── frm_ECC.java                                            # Giao diện Swing: Mã hóa, giải mã ECC và Save/Load Keys
        ├── frm_RSA_AES.java                                        # Giao diện Swing: Hệ mã lai RSA+AES & kiểm tra toàn vẹn
        ├── frm_ECC_Signature.java                                  # Giao diện Swing: Tạo và xác minh chữ ký số ECDSA
        ├── frm_KetNoiSQL.java                                      # Giao diện Swing: Cấu hình và kết nối Microsoft SQL Server
        ├── frm_LichSu.java                                         # Giao diện Swing: Hiển thị bảng lịch sử thao tác từ SQL Server
        └── frm_Menu.java                                           # Cửa sổ chính điều hướng toàn bộ ứng dụng
```

---

## 2. CHUẨN BỊ CƠ SỞ DỮ LIỆU SQL SERVER

### 2.1. Thông số kết nối CSDL
- **Hệ quản trị CSDL:** Microsoft SQL Server (bật chế độ Mixed Authentication Mode - SQL Server and Windows Authentication).
- **Server:** `127.0.0.1` (hoặc `localhost`)
- **Port:** `1433` (đã được cấu hình tự động điền sẵn trên form kết nối)
- **Database Name:** `LAB3_MaHoaKhoaCongKhai`
- **Login / User:** `lab3_user`
- **Password:** `LabUser@2026`

### 2.2. Hướng dẫn chạy các script SQL
Mở **SQL Server Management Studio (SSMS)** và thực hiện lần lượt các bước:
1. **Bước 1:** Đăng nhập SSMS bằng quyền Administrator (Windows Authentication). Mở file `03_SQL_SERVER/01_Tao_CSDL.sql` và nhấn **Execute (F5)** để tạo CSDL `LAB3_MaHoaKhoaCongKhai` cùng bảng `dbo.History` (chứa các ràng buộc CHECK cho thao tác `ENCRYPT`, `DECRYPT`, `SIGN`, `VERIFY`).
2. **Bước 2:** Mở file `03_SQL_SERVER/02_Tao_TaiKhoan_LAB.sql` và nhấn **Execute (F5)** để tạo tài khoản `lab3_user` với mật khẩu `LabUser@2026` và cấp quyền `SELECT`, `INSERT` trên bảng `dbo.History`.
3. **Bước 3:** Sau khi thực hiện các bài mã hóa trên ứng dụng Java, mở file `03_SQL_SERVER/03_KiemTra_LichSu.sql` và nhấn **Execute (F5)** để kiểm tra dữ liệu nhật ký và bảng thống kê số lần thao tác theo từng thuật toán.

---

## 3. HƯỚNG DẪN CHẠY ỨNG DỤNG JAVA (NETBEANS)

### 3.1. Mở và biên dịch dự án
1. Khởi động **Apache NetBeans**.
2. Chọn menu **File** -> **Open Project...** (phím tắt `Ctrl + Shift + O`).
3. Điều hướng đến thư mục `Lab3/LAB3_MaHoaKhoaCongKhai` và bấm **Open Project**.
4. Nhấp chuột phải vào tên project -> chọn **Clean and Build** (`Shift + F11`) để tải thư viện `mssql-jdbc` và biên dịch hệ thống.

### 3.2. Chạy ứng dụng Menu chính
- Nhấn phím **F6** (hoặc nhấp chuột phải vào `frm_Menu.java` chọn **Run File** / `Shift + F6`).
- Giao diện trung tâm **LAB 3 - MÃ HÓA KHÓA CÔNG KHAI** sẽ hiển thị với các nút chức năng.

### 3.3. Các bước thực hiện từng bài trên giao diện

#### Bước khởi đầu: Kết nối SQL Server
1. Trên form Menu, bấm nút **Kết nối SQL Server** (mở form `frm_KetNoiSQL`).
2. Các thông số đã được thiết lập sẵn: Server `127.0.0.1`, Port `1433`, Database `LAB3_MaHoaKhoaCongKhai`, User `lab3_user`. Nhập Password `LabUser@2026`.
3. Bấm **Test Connection** -> Xuất hiện dòng chữ `Kết nối thành công: Microsoft SQL Server...`. Đóng form kết nối lại.

#### Bài 3.1 - RSA (Console)
1. Trong cửa sổ Projects của NetBeans, nhấp chuột phải vào `RSADemo.java` -> chọn **Run File** (`Shift + F6`).
2. Tại cửa sổ Output, nhập kích thước khóa `2048` rồi nhấn **Enter**.
3. Nhập bản rõ: `HELLO RSA` rồi nhấn **Enter**.
4. Quan sát thời gian tạo khóa (~150 - 300 ms), bản mã Base64 (344 ký tự), kết quả giải mã và dòng `Khoi phuc dung: true`.
5. Chạy lại với kích thước khóa `3072` (bản mã 512 ký tự, giới hạn bản rõ 318 byte).

#### Bài 3.2 - RSA (Giao diện Swing)
1. Trên form Menu, bấm nút **3.2 RSA (Swing)** (form `frm_RSA`).
2. Chọn *Key size* là `2048` -> bấm **Generate Keys**.
3. Nhập Plaintext: `HELLO RSA` -> bấm **Encrypt** (Bản mã hiển thị ở ô Ciphertext, hệ thống tự động ghi nhật ký vào SQL Server).
4. Xóa trắng ô Plaintext -> bấm **Decrypt** -> Plaintext được giải mã khôi phục chính xác `HELLO RSA`.

#### Bài 3.3 - Mật mã đường cong Elliptic (ECC)
1. Trên form Menu, bấm nút **3.3 ECC** (form `frm_ECC`).
2. Bấm **Generate Keys** để sinh cặp khóa trên đường cong `secp256r1`.
3. Bấm **Save Keys** để lưu cặp khóa ra file `ecc_keys.txt`.
4. Nhập Plaintext: `HELLO ECC` -> bấm **Encrypt** -> Xóa ô Plaintext -> bấm **Decrypt** -> khôi phục thành công.
5. **Kiểm tra khóa sai:** Bấm **Generate Keys** lần nữa (để sinh ra cặp khóa mới) rồi bấm **Decrypt** -> Hệ thống hiển thị hộp thoại báo lỗi `AEADBadTagException: Tag mismatch` (từ chối giải mã do sai khóa).

#### Bài 3.4 - Hệ mã lai RSA + AES (Văn bản dài)
1. Trên form Menu, bấm nút **3.4 RSA + AES** (form `frm_RSA_AES`).
2. Bấm **Generate Keys** để tạo cặp khóa.
3. Dán một đoạn văn bản dài trên 1.000 ký tự vào ô Plaintext -> bấm **Encrypt**.
4. Xóa ô Plaintext -> bấm **Decrypt** -> toàn bộ văn bản dài được khôi phục nguyên vẹn.
5. **Kiểm tra phát hiện sửa đổi bản mã:** Bấm nút **Sửa 1 ký tự bản mã** -> bấm **Decrypt** -> Hệ thống hiển thị hộp thoại báo lỗi `AEADBadTagException: Tag mismatch` và chuyển trạng thái sang `THẤT BẠI`.

---

## 4. HƯỚNG DẪN CHẠY BÀI NÂNG CAO: CHỮ KÝ SỐ ECDSA

### 4.1. Nguyên lý chữ ký số ECDSA
- Sử dụng thuật toán ký số trên đường cong elliptic `secp256r1` kết hợp hàm băm SHA-256 (`SHA256withECDSA`).
- **Khóa riêng (Private Key):** Người gửi dùng để ký lên thông điệp nhằm tạo chữ ký số $(r, s)$.
- **Khóa công khai (Public Key):** Bất kỳ ai cũng có thể sử dụng để kiểm tra, xác minh tính toàn vẹn và nguồn gốc của thông điệp.
- **Tính chất bảo đảm:** Đảm bảo tính toàn vẹn (Integrity), xác thực nguồn gốc (Authentication) và chống chối bỏ (Non-repudiation).

### 4.2. Thao tác thực hiện trên form `frm_ECC_Signature`
1. Trên form Menu, bấm nút **3.5 Chữ ký ECDSA** (form `frm_ECC_Signature`).
2. Bấm **Generate Keys** để tạo cặp khóa ECDSA.
3. **Ký thông điệp A:**
   - Nhập ô Message: `THONG DIEP A` -> bấm **Sign**.
   - Cặp chữ ký số Base64 được tạo và hiển thị ở ô Signature, đồng thời ghi log `SIGN` vào CSDL SQL Server.
4. **Xác minh chữ ký hợp lệ:**
   - Bấm **Verify** -> Hệ thống xác minh thành công và hiển thị nhãn **VALID** to màu xanh lá cây.
5. **Kiểm tra phát hiện thông điệp bị thay đổi:**
   - Trong ô Message, sửa nội dung thành `THONG DIEP B` (giữ nguyên chữ ký Signature).
   - Bấm **Verify** -> Hệ thống lập tức phát hiện sự không đồng nhất và hiển thị nhãn **INVALID** màu đỏ.

### 4.3. Xem lại lịch sử hệ thống
- Quay lại Menu, bấm nút **Xem lịch sử** (form `frm_LichSu`) -> bấm **Tải lại** để quan sát danh sách các thao tác đã được lưu trữ trong bảng `dbo.History`.

---

## 5. DỮ LIỆU KIỂM THỬ BẮT BUỘC (TEST CASES)

Bảng tổng hợp dữ liệu kiểm thử bắt buộc và kết quả nghiệm thu thực tế của bài Lab:

| STT | Bài thực hành | Dữ liệu đầu vào (Input) | Kết quả kỳ vọng (Expected Output) | Kết quả kiểm tra thực tế |
| :---: | :--- | :--- | :--- | :---: |
| **1** | **RSA (Console & Swing)** | Bản rõ: `HELLO RSA`<br>Khóa: 2048 bit & 3072 bit | Giải mã đúng nguyên văn `HELLO RSA`; bản mã 2048 bit dài 344 ký tự, bản mã 3072 bit dài 512 ký tự Base64. Giới hạn bản rõ lần lượt là 190 byte và 318 byte. | **ĐẠT (PASS)** |
| **2** | **ECC (ECIES)** | Bản rõ: `HELLO ECC`<br>Đường cong: `secp256r1` | Giải mã đúng với khóa hợp lệ. Khi đổi sang khóa riêng khác, hệ thống từ chối và ném ngoại lệ `AEADBadTagException: Tag mismatch`. | **ĐẠT (PASS)** |
| **3** | **Hệ mã lai RSA + AES** | Bản rõ: Văn bản dài **trên 1.000 ký tự** (1.688 ký tự) | Giải mã chính xác 100% toàn văn. Khi bấm **Sửa 1 ký tự bản mã**, chế độ AES-GCM phát hiện sửa đổi và từ chối giải mã ngay lập tức. | **ĐẠT (PASS)** |
| **4** | **Chữ ký số ECDSA (Hợp lệ)** | Thông điệp: `THONG DIEP A` | Trạng thái hiển thị chữ **VALID** màu xanh lá cây (chữ ký hợp lệ, đúng người ký và nguyên vẹn). | **ĐẠT (PASS)** |
| **5** | **Xác minh lỗi ECDSA (Sửa đổi)** | Sửa thông điệp thành: `THONG DIEP B` | Trạng thái chuyển sang chữ **INVALID** màu đỏ (phát hiện thông điệp đã bị thay đổi). | **ĐẠT (PASS)** |
| **6** | **SQL Server Logging** | Các thao tác mã hóa/giải mã/ký số/xác minh | Mỗi thao tác ghi nhận thành công 1 dòng vào bảng `dbo.History` gồm `Algorithm`, `Operation`, `InputLength`, `Success` và thời gian thực hiện. | **ĐẠT (PASS)** |
