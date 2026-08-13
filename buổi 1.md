# CSDL là gì ?

Cơ sở dữ liệu (CSDL) là một tập hợp các dữ liệu có cấu trúc, được tổ chức và lưu trữ trong các hệ thống máy tính để có thể truy xuất, quản lý và cập nhật một cách hiệu quả. CSDL đóng vai trò quan trọng trong việc quản lý thông tin, hỗ trợ các ứng dụng phần mềm và giúp các doanh nghiệp, tổ chức lưu trữ, xử lý dữ liệu một cách có hệ thống.
**Các thành phần của cơ sở dữ liệu**

- Dữ liệu: Đây là thành phần cốt lõi của CSDL, bao gồm các thông tin, số liệu, hoặc tập hợp các dữ liệu được lưu trữ theo một cấu trúc nhất định. Dữ liệu có thể bao gồm văn bản, số, hình ảnh, hoặc các loại tệp tin khác.
- Hệ quản trị cơ sở dữ liệu (DBMS): DBMS là phần mềm quản lý dữ liệu, cho phép người dùng tạo, truy cập, chỉnh sửa và xóa dữ liệu trong CSDL. DBMS còn đảm nhiệm việc kiểm soát các giao dịch, bảo mật dữ liệu và duy trì tính toàn vẹn của dữ liệu.
- Ngôn ngữ truy vấn: SQL (Structured Query Language) là ngôn ngữ truy vấn tiêu chuẩn được sử dụng để tương tác với CSDL. SQL giúp người dùng truy xuất dữ liệu, thực hiện các thao tác như thêm, xóa, cập nhật dữ liệu và quản lý quyền truy cập vào dữ liệu.
- Siêu dữ liệu (Metadata): Đây là dữ liệu về dữ liệu, cung cấp thông tin về cấu trúc, định dạng, và mối quan hệ giữa các dữ liệu trong CSDL. Siêu dữ liệu giúp người dùng hiểu rõ hơn về dữ liệu đang được lưu trữ và cách chúng được tổ chức.
- Người dùng và ứng dụng: Người dùng cuối (end-users) và các ứng dụng phần mềm là các thành phần tương tác trực tiếp với CSDL. Người dùng có thể là những người quản trị hệ thống, lập trình viên hoặc người dùng thông thường. Các ứng dụng phần mềm kết nối với CSDL để xử lý và hiển thị dữ liệu cho người dùng.
- Các chỉ mục (Indexes): Chỉ mục là cấu trúc dữ liệu đặc biệt được sử dụng để tăng tốc độ truy vấn và tìm kiếm dữ liệu trong CSDL. Chỉ mục giúp truy xuất dữ liệu nhanh hơn bằng cách cung cấp một đường dẫn nhanh đến các dòng dữ liệu trong bảng

# Hệ quản trị CSDL là gì ?

Hệ quản trị cơ sở dữ liệu (DBMS -Database Management System) là một hệ thống phần mềm giúp doanh nghiệp tổ chức, quản lý và sử dụng dữ liệu hiệu quả. DBMS cung cấp giao diện giữa cơ sở dữ liệu và người dùng hoặc các ứng dụng để thực hiện các thao tác như tạo, truy vấn, sửa đổi và xóa dữ liệu.

- Cài đặt MS SQL Server

# Câu lệnh tạo database, table trong MS SQL Server

## 1. Tạo database

- Tạo một cơ sở dữ liệu mới

```
CREATE DATABASE QuanLySinhVien;
GO
```

- Sử dụng cơ sở dữ liệu vừa tạo

```
USE QuanLySinhVien;
GO
```

Giải thích:

- CREATE DATABASE <Tên>: Tạo cơ sở dữ liệu mới.
- GO: Kết thúc batch lệnh (SQL Server Management Studio sử dụng).
- USE <Tên>: Chuyển sang làm việc với database đó.

## 2. Tạo table

**Các Ràng Buộc Phổ Biến trong SQL**

- PRIMARY KEY: Đảm bảo rằng mỗi giá trị trong cột là duy nhất và không NULL. Đây là một trong những ràng buộc quan trọng nhất trong việc xác định các bản ghi duy nhất trong bảng. Mỗi bảng chỉ có thể có một PRIMARY KEY.
- FOREIGN KEY: Dùng để liên kết một bảng này với một bảng khác, giúp đảm bảo mối quan hệ giữa các bảng trong cơ sở dữ liệu. Cột có FOREIGN KEY sẽ tham chiếu đến cột có PRIMARY KEY hoặc UNIQUE trong bảng khác.
- UNIQUE: Đảm bảo rằng tất cả các giá trị trong một cột đều là duy nhất. Cột này có thể chứa giá trị NULL, nhưng nếu có giá trị NULL thì chỉ có một giá trị NULL duy nhất được phép trong cột đó.
- NOT NULL: Đảm bảo rằng cột không chứa giá trị NULL. Đây là ràng buộc thường được sử dụng khi bạn muốn một cột nhất định luôn phải có giá trị.
- CHECK: Dùng để xác định các điều kiện mà giá trị trong cột phải thỏa mãn. Ví dụ, có thể sử dụng để đảm bảo rằng giá trị trong cột tuổi phải là một số dương.
- DEFAULT: Dùng để cung cấp một giá trị mặc định cho cột khi không có giá trị nào được chỉ định. Nếu không có giá trị, SQL sẽ tự động sử dụng giá trị mặc định.
  **VD**

```
CREATE TABLE SinhVien (
    MaSV INT PRIMARY KEY,               -- Khóa chính
    HoTen NVARCHAR(100) NOT NULL,        -- Không được để trống
    NgaySinh DATE,                       -- Kiểu ngày
    GioiTinh BIT DEFAULT 1,              -- 1: Nam, 0: Nữ (mặc định Nam)
    Email NVARCHAR(100) UNIQUE,          -- Không trùng lặp
    DiemTrungBinh DECIMAL(4,2) CHECK (DiemTrungBinh >= 0 AND DiemTrungBinh <= 10) -- Ràng buộc điểm
);
GO
```

```
CREATE TABLE LopHoc (
    MaLop INT PRIMARY KEY,
    TenLop NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE SinhVien_Lop (
    MaSV INT,
    MaLop INT,
    NgayVaoLop DATE DEFAULT GETDATE(),
    PRIMARY KEY (MaSV, MaLop),
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV),
    FOREIGN KEY (MaLop) REFERENCES LopHoc(MaLop)
);
GO
```
