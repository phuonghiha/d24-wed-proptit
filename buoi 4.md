# Tối ưu truy vấn

## 1\. Chỉ chọn các cột cần thiết — `SELECT`

Không nên sử dụng `SELECT *` nếu chỉ cần một vài cột.

- Không tối ưu:

```
SELECT *
FROM users;
```

Nếu bảng có 20 cột nhưng bạn chỉ cần `username` và `email`, database vẫn phải lấy toàn bộ dữ liệu cần thiết của các cột đó.

- Nên viết:

```
SELECT username, email
FROM users;
```

### Ví dụ thực tế

Muốn lấy tên và lương nhân viên:

```
SELECT name, salary
FROM employee;
```

thay vì:

```
SELECT *
FROM employee;
```

### Lợi ích

- Giảm lượng dữ liệu phải đọc.
- Giảm dữ liệu truyền từ database → ứng dụng.
- Giảm bộ nhớ.
- Query dễ đọc hơn.

-> **Nhớ:** `SELECT` đúng những gì cần, không lấy thừa.

---

## 2\. Sử dụng INDEX

**INDEX** là cấu trúc dữ liệu giúp database tìm kiếm dữ liệu nhanh hơn.

Ví dụ bảng:

```
users
--------------------------------
id | username | email
--------------------------------
1  | An       | an@gmail.com
2  | Bình     | binh@gmail.com
3  | Nam      | nam@gmail.com
...
```

Ta thường xuyên tìm user bằng email:

```
SELECT *
FROM users
WHERE email = 'nam@gmail.com';
```

Có thể tạo index:

```
CREATE INDEX idx_users_email
ON users(email);
```

Khi đó database có thể tìm kiếm thông qua index thay vì phải quét toàn bộ bảng.

### Nhưng INDEX không phải lúc nào cũng tốt

Index giúp:

```
SELECT nhanh hơn
```

nhưng có thể làm:

```
INSERT
UPDATE
DELETE
```

chậm hơn vì database phải cập nhật index.

Ngoài ra index cũng chiếm thêm bộ nhớ.

-> **Nhớ:** Index phù hợp với các cột thường xuyên được tìm kiếm, JOIN hoặc sắp xếp.

---

## 3\. Thận trọng khi sử dụng JOIN

`JOIN` dùng để kết hợp dữ liệu từ nhiều bảng.

Ví dụ:

### `customers`

```
id | name
---+------
1  | An
2  | Bình
```

### `orders`

```
id | customer_id | total
---+-------------+------
1  | 1           | 500
2  | 2           | 700
```

Muốn lấy tên khách hàng và đơn hàng:

```
SELECT c.name, o.total
FROM orders o
INNER JOIN customers c
    ON o.customer_id = c.id;
```

Kết quả:

```
name  | total
------+------
An    | 500
Bình  | 700
```

### Tối ưu JOIN

Các cột dùng để JOIN thường nên có index.

Ví dụ:

```
CREATE INDEX idx_orders_customer_id
ON orders(customer_id);
```

### Lưu ý

Không phải cứ `INNER JOIN` là nhanh hơn `LEFT JOIN`.

Việc chọn:

```
INNER JOIN
LEFT JOIN
RIGHT JOIN
```

phụ thuộc vào **kết quả dữ liệu bạn muốn lấy**, không đơn thuần là vấn đề tối ưu tốc độ.

---

## 4\. Sử dụng `EXISTS` hoặc `IN` thay vì `JOIN`

Khi bạn **chỉ muốn kiểm tra một dữ liệu có tồn tại hay không**, `EXISTS` có thể phù hợp hơn `JOIN`.

Ví dụ:

> Tìm những khách hàng đã từng đặt hàng.

Có thể dùng:

```
SELECT *
FROM customers c
WHERE EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.customer_id = c.id
);
```

Ở đây:

SQL

```
SELECT 1
```

không có nghĩa là lấy giá trị `1` từ bảng.

Nó chỉ thể hiện rằng:

> "Có tồn tại ít nhất một dòng thỏa mãn điều kiện."

### Dùng `IN`

```
SELECT *
FROM customers
WHERE id IN (
    SELECT customer_id
    FROM orders
);
```

### Khi nào dùng?

Nếu mục đích chỉ là:

> "Có tồn tại dữ liệu không?"

→ `EXISTS` thường rất tự nhiên.

Nếu cần lấy thêm dữ liệu từ bảng thứ hai:

→ `JOIN` thường phù hợp hơn.

---

## 5\. Phân trang — `LIMIT/OFFSET`

Khi bảng có hàng triệu bản ghi, không nên lấy tất cả:

❌

```
SELECT *
FROM products;
```

Thay vào đó chia thành từng trang.

Ví dụ lấy 10 sản phẩm đầu:

```
SELECT *
FROM products
ORDER BY name
LIMIT 10 OFFSET 0;
```

Trang 2:

```
SELECT *
FROM products
ORDER BY name
LIMIT 10 OFFSET 10;
```

Trang 3:

```
SELECT *
FROM products
ORDER BY name
LIMIT 10 OFFSET 20;
```

Nếu dùng SQL Server thì có thể dùng:

```
SELECT *
FROM products
ORDER BY name
OFFSET 10 ROWS
FETCH NEXT 10 ROWS ONLY;
```

-> **Lưu ý:** `OFFSET` rất lớn có thể vẫn chậm vì database phải bỏ qua nhiều dòng. Với bảng cực lớn, có thể dùng **keyset/cursor pagination**.

---

## 6\. Tối ưu hóa cấu trúc bảng

Không nên lưu cùng một thông tin lặp lại quá nhiều lần.

Ví dụ không tốt:

```
orders
------------------------------------------------
order_id | customer_name | customer_phone | ...
------------------------------------------------
1        | Nguyễn An     | 0912...         |
2        | Nguyễn An     | 0912...         |
3        | Nguyễn An     | 0912...         |
```

Tên và số điện thoại khách hàng bị lặp lại.

Có thể tách thành:

### `customers`

```
customer_id | name       | phone
------------+------------+---------
1           | Nguyễn An  | 0912...
```

### `orders`

```
order_id | customer_id | total
---------+-------------+------
1        | 1           | 500
2        | 1           | 700
3        | 1           | 300
```

Khi đó:

```
customers
    1
    │
    │ 1:N
    ▼
orders
```

### Lợi ích

- Giảm dữ liệu trùng lặp.
- Dễ cập nhật.
- Giảm kích thước database.
- Duy trì tính nhất quán dữ liệu.

Đây chính là một phần của **database normalization (chuẩn hóa)**.

---

## 7\. Partitioning

**Partitioning** là chia một bảng lớn thành nhiều phần nhỏ gọi là **partition**.

Ví dụ bảng:

```
orders
```

có 100 triệu đơn hàng.

Có thể chia theo năm:

```
orders_2024
orders_2025
orders_2026
```

Ví dụ truy vấn:

```
SELECT *
FROM orders
WHERE order_date >= '2026-01-01'
  AND order_date < '2027-01-01';
```

Database có thể chỉ cần đọc partition tương ứng thay vì toàn bộ dữ liệu.

Đây gọi là:

> **Partition pruning**

### Khi nào phù hợp?

Đặc biệt hữu ích với bảng rất lớn, dữ liệu có tính phân vùng rõ ràng như:

- Ngày/tháng/năm
- Khu vực
- Loại dữ liệu

-> Không phải bảng nào cũng cần partition.

---

## 8\. Sử dụng VIEW

`VIEW` là một **bảng ảo** được tạo từ một câu SQL.

Ví dụ thường xuyên phải viết:

```
SELECT c.name, COUNT(o.id) AS total_orders
FROM customers c
JOIN orders o
    ON c.id = o.customer_id
GROUP BY c.id, c.name;
```

Có thể tạo:

```
CREATE VIEW customer_order_count AS
SELECT c.name, COUNT(o.id) AS total_orders
FROM customers c
JOIN orders o
    ON c.id = o.customer_id
GROUP BY c.id, c.name;
```

Sau đó:

```
SELECT *
FROM customer_order_count;
```

### Lợi ích

- Giảm việc viết lại SQL.
- Dễ sử dụng các truy vấn phức tạp.
- Có thể giúp kiểm soát quyền truy cập dữ liệu.

⚠️ **Quan trọng:** `VIEW` không mặc định làm truy vấn nhanh hơn.

Nó chủ yếu giúp:

> **Tái sử dụng + đơn giản hóa truy vấn.**

Một số hệ quản trị có **materialized view** có thể lưu kết quả và cải thiện hiệu suất, nhưng đó là khái niệm khác.

---

## 9\. Stored Procedure

**Stored Procedure** là một nhóm câu lệnh SQL được lưu trong database và có thể gọi lại.

Ví dụ:

```
CREATE PROCEDURE GetOrdersByCustomer
    @customer_id INT
AS
BEGIN
    SELECT *
    FROM orders
    WHERE customer_id = @customer_id;
END;
```

Sau đó gọi:

SQL

```
EXEC GetOrdersByCustomer 1;
```

### Lợi ích

- Tái sử dụng SQL.
- Có thể đóng gói logic xử lý.
- Giảm việc gửi nhiều câu SQL riêng lẻ từ application.
- Có thể hỗ trợ bảo mật/quyền truy cập.

⚠️ Chỗ này trong tài liệu của bạn cần hiểu cẩn thận:

> **Không nên nói đơn giản rằng Stored Procedure luôn nhanh hơn vì "lưu execution plan vào cache".**

Các hệ quản trị hiện đại có cơ chế tối ưu và cache execution plan theo nhiều cách; stored procedure **không tự động đảm bảo query nhanh hơn**.

---

## 10\. Hạn chế `DISTINCT`

`DISTINCT` dùng để loại bỏ các dòng trùng nhau.

Ví dụ:

```
SELECT DISTINCT department_id
FROM employee;
```

Nếu dữ liệu:

```
10
10
10
20
20
30
```

kết quả:

```
10
20
30
```

Nhưng `DISTINCT` có thể khiến database phải thực hiện thêm việc:

```
Sort / Hash
     ↓
Loại bỏ trùng
```

Vì vậy nếu có thể thiết kế query để **không tạo ra dữ liệu trùng ngay từ đầu**, thường tốt hơn.

Ví dụ nếu chỉ muốn kiểm tra tồn tại:

❌ Có thể tạo nhiều dòng rồi `DISTINCT`:

```
SELECT DISTINCT c.id
FROM customers c
JOIN orders o
    ON c.id = o.customer_id;
```

✅ Có thể dùng:

```
SELECT c.id
FROM customers c
WHERE EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.customer_id = c.id
);
```

---

## 11\. Sử dụng `ORDER BY` khi cần thiết

`ORDER BY` dùng để sắp xếp:

```
SELECT *
FROM products
ORDER BY price DESC;
```

Database có thể phải thực hiện thao tác sort:

```
100
50
200
20
 ↓
200
100
50
20
```

Nếu bảng có rất nhiều dữ liệu thì việc sort có thể tốn tài nguyên.

### Nếu cần lấy 10 sản phẩm đắt nhất

```
SELECT *
FROM products
ORDER BY price DESC
LIMIT 10;
```

Có thể tạo index:

```
CREATE INDEX idx_products_price
ON products(price);
```

Database có thể tận dụng index tùy vào query và execution plan.

👉 **Không cần sắp xếp nếu ứng dụng không yêu cầu thứ tự.**

---

## 12\. Table-Valued Parameters — TVP

**TVP (Table-Valued Parameter)** là tính năng của **SQL Server**, cho phép truyền nhiều dòng dữ liệu vào stored procedure dưới dạng một bảng.

Ví dụ bạn có danh sách:

```
1
5
10
20
30
```

Thay vì gọi database nhiều lần:

```
SELECT ... WHERE id = 1
SELECT ... WHERE id = 5
SELECT ... WHERE id = 10
...
```

Có thể truyền cả danh sách vào một lần.

Ví dụ ý tưởng:

```
CREATE TYPE IdList AS TABLE
(
    id INT
);
```

Sau đó procedure nhận:

```
CREATE PROCEDURE GetUsers
    @ids IdList READONLY
AS
BEGIN
    SELECT u.*
    FROM users u
    JOIN @ids i
        ON u.id = i.id;
END;
```

### Lợi ích

```
Nhiều request
     ↓
   TVP
     ↓
Một lần truyền dữ liệu
     ↓
Database xử lý
```

⚠️ **TVP là tính năng đặc trưng của SQL Server**, không phải cú pháp chung của MySQL.

---

## 13\. Phân tích câu truy vấn bằng Execution Plan

Đây là phần **rất quan trọng khi tối ưu SQL**.

Thay vì đoán:

> "Query này chắc nhanh."

Ta cho database phân tích.

## MySQL

Dùng:

```
EXPLAIN
SELECT *
FROM employee
WHERE department_id = 10;
```

Có thể dùng thêm:

```
EXPLAIN ANALYZE
SELECT *
FROM employee
WHERE department_id = 10;
```

## SQL Server

Có thể xem **Actual Execution Plan** trong SQL Server Management Studio.

---

### Ví dụ

Có query:

```
SELECT *
FROM orders
WHERE customer_id = 100;
```

Chạy:

```
EXPLAIN
SELECT *
FROM orders
WHERE customer_id = 100;
```

Nếu thấy database phải đọc gần như toàn bộ bảng:

```
Full Table Scan
        ↓
1.000.000 rows
```

→ Có thể cân nhắc index:

```
CREATE INDEX idx_orders_customer
ON orders(customer_id);
```

Sau đó kiểm tra lại execution plan.

Có thể chuyển thành:

```
Index lookup
      ↓
     100 rows
```

→ Query có khả năng nhanh hơn rất nhiều.

---

# 🎯 Tổng hợp 13 cách

| #   | Kỹ thuật                           | Mục đích                               |
| --- | ---------------------------------- | -------------------------------------- |
| 1   | Chọn cột cần thiết                 | Giảm dữ liệu                           |
| 2   | `INDEX`                            | Tìm kiếm nhanh                         |
| 3   | Tối ưu `JOIN`                      | Giảm dữ liệu xử lý                     |
| 4   | `EXISTS` / `IN`                    | Kiểm tra tồn tại                       |
| 5   | Pagination                         | Không lấy quá nhiều dữ liệu            |
| 6   | Thiết kế bảng                      | Giảm dư thừa                           |
| 7   | Partitioning                       | Chia bảng lớn                          |
| 8   | `VIEW`                             | Tái sử dụng query                      |
| 9   | Stored Procedure                   | Đóng gói logic SQL                     |
| 10  | Hạn chế `DISTINCT`                 | Giảm sort/hash                         |
| 11  | Hạn chế `ORDER BY` không cần thiết | Giảm sorting                           |
| 12  | TVP                                | Truyền nhiều dòng một lần — SQL Server |
| 13  | Execution Plan                     | Tìm nguyên nhân query chậm             |

# Sử dụng INDEX trong MySQL

## 1\. INDEX là gì?

`INDEX` là cấu trúc dữ liệu giúp MySQL tìm kiếm dữ liệu nhanh hơn, đặc biệt với các truy vấn có điều kiện.

Không có INDEX, MySQL có thể phải quét toàn bộ bảng.

Có INDEX, MySQL có thể tìm dữ liệu thông qua INDEX trước, sau đó lấy bản ghi tương ứng.

Ví dụ:

```
SELECT *
FROM users
WHERE email = 'a@gmail.com';
```

Nếu bảng có 1.000.000 bản ghi, việc tìm kiếm sẽ tốn thời gian hơn nếu không có INDEX.

Tạo INDEX:

```
CREATE INDEX idx_users_email
ON users(email);
```

---

# 2\. Khi nào nên sử dụng INDEX?

Nên cân nhắc tạo INDEX cho các cột:

- Thường xuyên xuất hiện trong `WHERE`.
- Thường xuyên được sử dụng để `JOIN`.
- Thường xuyên dùng trong `ORDER BY`.
- Thường xuyên dùng trong `GROUP BY`.
- Có nhiều giá trị khác nhau.
- Bảng có nhiều bản ghi.
- Được truy vấn thường xuyên.

Ví dụ:

```
SELECT *
FROM orders
WHERE customer_id = 100;
```

Có thể tạo:

```
CREATE INDEX idx_orders_customer
ON orders(customer_id);
```

---

# 3\. Khi nào INDEX ít hiệu quả?

Không nên tạo INDEX một cách máy móc cho mọi cột.

INDEX thường ít hiệu quả khi:

- Bảng có rất ít bản ghi.
- Cột có quá ít giá trị khác nhau, ví dụ `gender` chỉ có `Male`, `Female`.
- Cột hiếm khi được dùng để tìm kiếm.
- Bảng thường xuyên `INSERT`, `UPDATE`, `DELETE`.
- Có quá nhiều INDEX khiến việc cập nhật dữ liệu tốn thêm thời gian.

---

# 4\. Ưu điểm của INDEX

- Tăng tốc độ tìm kiếm dữ liệu.
- Có thể cải thiện hiệu suất `WHERE`, `JOIN`, `ORDER BY`, `GROUP BY`.
- Giảm số lượng bản ghi phải đọc.
- `UNIQUE INDEX` giúp đảm bảo dữ liệu không bị trùng.

---

# 5\. Nhược điểm của INDEX

- Chiếm thêm bộ nhớ.
- Làm `INSERT` chậm hơn.
- Làm `UPDATE` chậm hơn.
- Làm `DELETE` chậm hơn.
- Tốn thời gian và tài nguyên để tạo, cập nhật INDEX.

---

# 6\. Tạo INDEX trong MySQL

Cú pháp:

```
CREATE INDEX ten_index
ON ten_bang(cot);
```

Ví dụ:

```
CREATE INDEX idx_users_email
ON users(email);
```

Input:

```
users
+----+----------+----------------+
| id | username | email          |
+----+----------+----------------+
| 1  | An       | an@gmail.com   |
| 2  | Nam      | nam@gmail.com  |
| 3  | Hoa      | hoa@gmail.com  |
+----+----------+----------------+
```

Query:

```
SELECT *
FROM users
WHERE email = 'nam@gmail.com';
```

Output:

```
+----+----------+----------------+
| id | username | email          |
+----+----------+----------------+
| 2  | Nam      | nam@gmail.com  |
+----+----------+----------------+
```

INDEX giúp MySQL tìm `nam@gmail.com` nhanh hơn, đặc biệt khi bảng có nhiều dữ liệu.

---

# 7\. Composite INDEX

Có thể tạo INDEX trên nhiều cột:

```
CREATE INDEX idx_user_name
ON users(first_name, last_name);
```

Ví dụ:

```
SELECT *
FROM users
WHERE first_name = 'Hoai'
AND last_name = 'Phuong';
```

INDEX:

```
(first_name, last_name)
```

Thứ tự cột rất quan trọng.

Với:

SQL

```
INDEX(a, b, c)
```

thường hỗ trợ tốt các điều kiện bắt đầu từ `a`:

```
a
a, b
a, b, c
```

Còn chỉ:

```
b
c
```

thì không tận dụng đầy đủ INDEX theo nguyên tắc tiền tố trái.

---

# 8\. UNIQUE INDEX

Dùng để tạo INDEX và đồng thời không cho phép dữ liệu trùng nhau.

```
CREATE UNIQUE INDEX idx_users_email
ON users(email);
```

Input:

```
INSERT INTO users(email)
VALUES ('a@gmail.com');
```

Nếu `a@gmail.com` đã tồn tại:

Output:

```
ERROR: Duplicate entry 'a@gmail.com'
```

---

# 9\. Xóa INDEX

```
DROP INDEX ten_index
ON ten_bang;
```

Ví dụ:

```
DROP INDEX idx_users_email
ON users;
```

---

# 10\. Xem INDEX

SQL

```
SHOW INDEX FROM users;
```

Dùng để xem các INDEX đang tồn tại trong bảng.

---

# 11\. Kiểm tra INDEX có được sử dụng không

Dùng `EXPLAIN`:

```
EXPLAIN
SELECT *
FROM users
WHERE email = 'nam@gmail.com';
```

Ví dụ kết quả:

```
possible_keys: idx_users_email
key:           idx_users_email
```

Trong đó:

```
possible_keys
```

là các INDEX MySQL có thể sử dụng.

```
key
```

là INDEX MySQL thực tế lựa chọn.

Nếu:

```
key: NULL
```

thì query không sử dụng INDEX.

---

# 12\. Ví dụ tổng hợp

Bảng:

```
users
+----+----------+----------------+
| id | username | email          |
+----+----------+----------------+
| 1  | An       | an@gmail.com   |
| 2  | Nam      | nam@gmail.com  |
| 3  | Hoa      | hoa@gmail.com  |
+----+----------+----------------+
```

Tạo INDEX:

```
CREATE INDEX idx_users_email
ON users(email);
```

Query:

```
SELECT username
FROM users
WHERE email = 'nam@gmail.com';
```

Output:

```
+----------+
| username |
+----------+
| Nam      |
+----------+
```

Kiểm tra:

```
EXPLAIN
SELECT username
FROM users
WHERE email = 'nam@gmail.com';
```

Nếu:

```
key: idx_users_email
```

thì MySQL đang sử dụng INDEX.

# Khái niệm Transaction, ACID, dirty read, dirty write

## 1\. Transaction

**Định nghĩa:**  
Transaction là một nhóm các thao tác SQL được thực hiện như **một đơn vị công việc**. Tất cả thao tác phải cùng thành công hoặc nếu có lỗi thì toàn bộ thay đổi được hoàn tác.

**Ví dụ:** Chuyển 100.000đ từ tài khoản A sang tài khoản B.

**Input:**

```
A = 1.000.000
B =   500.000
Số tiền chuyển = 100.000
```

```
START TRANSACTION;

UPDATE account
SET balance = balance - 100000
WHERE id = 1;

UPDATE account
SET balance = balance + 100000
WHERE id = 2;

COMMIT;
```

**Output:**

```
A = 900.000
B = 600.000
```

Nếu câu lệnh thứ 2 bị lỗi:

SQL

```
ROLLBACK;
```

**Output:**

```
A = 1.000.000
B = 500.000
```

Không xảy ra trường hợp A đã bị trừ tiền nhưng B chưa nhận được tiền.

---

## 2\. ACID

**Định nghĩa:**  
ACID là 4 tính chất đảm bảo Transaction được thực hiện **an toàn, chính xác và đáng tin cậy**.

ACID gồm:

```
A - Atomicity
C - Consistency
I - Isolation
D - Durability
```

### 2.1 Atomicity - Tính nguyên tử

**Định nghĩa:**  
Atomicity đảm bảo **Transaction được thực hiện toàn bộ hoặc không thực hiện gì cả**.

**Ví dụ:**

Transaction chuyển tiền gồm:

```
1. Trừ tiền A
2. Cộng tiền B
```

Nếu bước 2 bị lỗi:

```
Trừ tiền A       Thành công
Cộng tiền B      Lỗi
       ↓
    ROLLBACK
       ↓
Hoàn tác việc trừ tiền A
```

**Output:**

```
A và B trở về trạng thái ban đầu
```

---

### 2.2 Consistency - Tính nhất quán

**Định nghĩa:**  
Consistency đảm bảo dữ liệu **luôn hợp lệ và tuân thủ các ràng buộc của database trước và sau Transaction**.

**Ví dụ:**

Tài khoản không được có số dư âm.

**Input:**

```
A = 500.000
Chuyển = 100.000
```

**Output:**

```
A = 400.000
```

Dữ liệu vẫn hợp lệ vì số dư không âm.

---

### 2.3 Isolation - Tính cô lập

**Định nghĩa:**  
Isolation đảm bảo các Transaction chạy đồng thời **không nhìn thấy hoặc ảnh hưởng sai đến dữ liệu chưa hoàn thành của nhau**.

**Ví dụ:**

Transaction A:

```
balance = 500.000 → 400.000
```

A chưa `COMMIT`.

Transaction B thực hiện:

```
SELECT balance
FROM account
WHERE id = 1;
```

B không nên đọc được giá trị `400.000` chưa được xác nhận của A.

**Output mong muốn:**

```
B vẫn đọc được dữ liệu hợp lệ
```

---

### 2.4 Durability - Tính bền vững

**Định nghĩa:**  
Durability đảm bảo **dữ liệu sau khi `COMMIT` sẽ được lưu lại và không bị mất do các sự cố thông thường của hệ thống**.

**Ví dụ:**

```
UPDATE account
SET balance = 400000
WHERE id = 1;

COMMIT;
```

**Output:**

```
balance = 400.000
```

Sau đó database bị khởi động lại, dữ liệu vẫn phải là:

```
balance = 400.000
```

---

## 3\. Dirty Read

**Định nghĩa:**  
Dirty Read là tình trạng **một Transaction đọc dữ liệu mà Transaction khác đã thay đổi nhưng chưa `COMMIT`**.

**Ví dụ:**

Ban đầu:

```
balance = 500.000
```

Transaction A:

```
START TRANSACTION;

UPDATE account
SET balance = 400000
WHERE id = 1;
```

A chưa `COMMIT`.

Transaction B:

```
SELECT balance
FROM account
WHERE id = 1;
```

**Output của B:**

```
400.000
```

Sau đó A:

SQL

```
ROLLBACK;
```

Dữ liệu thực tế:

```
500.000
```

Như vậy B đã đọc phải dữ liệu `400.000` **chưa được xác nhận và sau đó bị hủy**.

---

## 4\. Dirty Write

**Định nghĩa:**  
Dirty Write là tình trạng **một Transaction ghi đè lên dữ liệu mà Transaction khác đã thay đổi nhưng chưa `COMMIT`**.

**Ví dụ:**

Ban đầu:

```
balance = 500.000
```

Transaction A:

```
UPDATE account
SET balance = 400000
WHERE id = 1;
```

A chưa `COMMIT`.

Transaction B:

```
UPDATE account
SET balance = 300000
WHERE id = 1;
```

Quá trình:

```
A: 500.000 → 400.000
                  ↓
B:            400.000 → 300.000
```

B đã ghi đè lên thay đổi của A khi A chưa hoàn thành.

**Output:**

```
balance = 300.000
```
