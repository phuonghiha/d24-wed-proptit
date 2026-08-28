# Các thao tác cơ bản: SELECT, INSERT, UPDATE, DELETE, từ khóa AS, DISTINCT

## I. SELECT — Truy vấn dữ liệu

### 1\. SELECT cơ bản

#### 1.1. Lấy tất cả các cột

```
SELECT *
FROM Movie;
```

#### 1.2. Lấy một cột

```
SELECT name
FROM Movie;
```

#### 1.3. Lấy nhiều cột

```
SELECT name, genre, year
FROM Movie;
```

#### 1.4. Đổi tên cột bằng AS

```
SELECT name AS movie_name
FROM Movie;
```

#### 1.5. Lấy giá trị tính toán

```
SELECT price * 2 AS double_price
FROM Ticket;
```

---

### 2\. SELECT + DISTINCT — Loại dữ liệu trùng

#### 2.1. DISTINCT một cột

```
SELECT DISTINCT genre
FROM Movie;
```

#### 2.2. DISTINCT nhiều cột

```
SELECT DISTINCT genre, year
FROM Movie;
```

> Loại các dòng bị trùng **cả `genre` và `year`**.

#### 2.3. DISTINCT + AS

```
SELECT DISTINCT genre AS movie_genre
FROM Movie;
```

---

### 3\. SELECT + WHERE — Lọc dữ liệu

Cấu trúc:

```
SELECT ...
FROM ...
WHERE điều_kiện;
```

#### 3.1. So sánh bằng

```
SELECT *
FROM Movie
WHERE year = 2020;
```

#### 3.2. Khác

SQL

```
WHERE year != 2020
```

hoặc:

SQL

```
WHERE year <> 2020
```

#### 3.3. Lớn hơn / nhỏ hơn

```
WHERE year > 2020
WHERE year < 2020
```

#### 3.4. Lớn hơn hoặc bằng / nhỏ hơn hoặc bằng

```
WHERE year >= 2020
WHERE year <= 2020
```

---

### 4\. SELECT + WHERE + AND / OR

#### 4.1. AND — đồng thời thỏa nhiều điều kiện

```
SELECT *
FROM Movie
WHERE year > 2000
AND genre = 'Action';
```

→ Phải thỏa **cả hai**.

#### 4.2. OR — thỏa ít nhất một điều kiện

```
SELECT *
FROM Movie
WHERE genre = 'Action'
OR genre = 'Comedy';
```

→ Chỉ cần thỏa **một trong hai**.

#### 4.3. Kết hợp AND + OR

```
SELECT *
FROM Movie
WHERE year > 2000
AND (genre = 'Action' OR genre = 'Comedy');
```

> Nên dùng `()` khi kết hợp `AND` và `OR`.

---

### 5\. SELECT + WHERE + IN

Dùng khi kiểm tra một giá trị thuộc một danh sách.

#### 5.1. IN

```
SELECT *
FROM Movie
WHERE genre IN ('Action', 'Comedy', 'Horror');
```

Tương đương:

```
WHERE genre = 'Action'
   OR genre = 'Comedy'
   OR genre = 'Horror'
```

#### 5.2. NOT IN

```
SELECT *
FROM Movie
WHERE genre NOT IN ('Action', 'Comedy');
```

---

### 6\. SELECT + WHERE + BETWEEN

Dùng để tìm dữ liệu trong một khoảng.

#### 6.1. BETWEEN

```
SELECT *
FROM Movie
WHERE year BETWEEN 2000 AND 2020;
```

Tương đương:

```
WHERE year >= 2000
AND year <= 2020
```

`BETWEEN` bao gồm cả `2000` và `2020`.

#### 6.2. NOT BETWEEN

```
SELECT *
FROM Movie
WHERE year NOT BETWEEN 2000 AND 2020;
```

---

### 7\. SELECT + WHERE + LIKE

Dùng để **tìm kiếm chuỗi**.

#### 7.1. Bắt đầu bằng

SQL

```
WHERE name LIKE 'A%';
```

→ bắt đầu bằng `A`.

#### 7.2. Kết thúc bằng

SQL

```
WHERE name LIKE '%a';
```

→ kết thúc bằng `a`.

#### 7.3. Chứa chuỗi

SQL

```
WHERE name LIKE '%man%';
```

→ chứa `man`.

#### 7.4. Không chứa

SQL

```
WHERE name NOT LIKE '%man%';
```

#### 7.5. `_` — đúng một ký tự

SQL

```
WHERE name LIKE 'A____';
```

→ bắt đầu bằng `A`, sau đó có 4 ký tự bất kỳ.

#### Ký hiệu cần nhớ

```
%  → 0 hoặc nhiều ký tự
_  → đúng 1 ký tự
```

---

### 8\. SELECT + WHERE + NULL

#### 8.1. Kiểm tra NULL

```
SELECT *
FROM Movie
WHERE description IS NULL;
```

#### 8.2. Kiểm tra không NULL

```
SELECT *
FROM Movie
WHERE description IS NOT NULL;
```

- Không viết:

SQL

```
WHERE description = NULL
```

---

### 9\. SELECT + ORDER BY — Sắp xếp

#### 9.1. Tăng dần

```
SELECT *
FROM Movie
ORDER BY year ASC;
```

#### 9.2. Giảm dần

```
SELECT *
FROM Movie
ORDER BY year DESC;
```

#### 9.3. Bỏ ASC

SQL

```
ORDER BY year;
```

Mặc định là `ASC`.

#### 9.4. Sắp xếp nhiều cột

```
SELECT *
FROM Movie
ORDER BY genre ASC, year DESC;
```

SQL sẽ:

```
1. Sắp xếp genre tăng dần
2. Nếu genre giống nhau → year giảm dần
```

#### 9.5. ORDER BY + AS

Có thể dùng alias:

```
SELECT name, year AS production_year
FROM Movie
ORDER BY production_year DESC;
```

---

### 10\. SELECT + LIMIT — Giới hạn số dòng

#### 10.1. Lấy N dòng đầu

```
SELECT *
FROM Movie
LIMIT 5;
```

#### 10.2. LIMIT + OFFSET

```
SELECT *
FROM Movie
LIMIT 5 OFFSET 10;
```

→ bỏ qua 10 dòng, lấy 5 dòng tiếp theo.

#### 10.3. Cách viết LIMIT với vị trí bắt đầu

MySQL còn hỗ trợ:

SQL

```
LIMIT 10, 5;
```

Tương đương:

SQL

```
LIMIT 5 OFFSET 10;
```

---

## II. INSERT — Thêm dữ liệu

### 1\. INSERT một dòng

#### 1.1. Cách chuẩn

```
INSERT INTO Movie (name, genre, year)
VALUES ('Avatar', 'Sci-Fi', 2009);
```

Cấu trúc:

```
INSERT INTO table (column1, column2, column3)
VALUES (value1, value2, value3);
```

---

### 2\. INSERT nhiều dòng

```
INSERT INTO Movie (name, genre, year)
VALUES
    ('Avatar', 'Sci-Fi', 2009),
    ('Titanic', 'Romance', 1997),
    ('Avengers', 'Action', 2019);
```

---

### 3\. INSERT không nhập tất cả cột

```
INSERT INTO Movie (name, genre)
VALUES ('Avatar', 'Sci-Fi');
```

Các cột còn lại sẽ nhận:

- `DEFAULT`, nếu có
- hoặc `NULL`, nếu cho phép

---

### 4\. INSERT NULL

```
INSERT INTO Movie (name, genre, description)
VALUES ('Avatar', 'Sci-Fi', NULL);
```

---

### 5\. INSERT từ SELECT

Lấy dữ liệu từ bảng khác để thêm:

```
INSERT INTO OldMovie (name, year)
SELECT name, year
FROM Movie
WHERE year < 2000;
```

---

## III. UPDATE — Cập nhật dữ liệu

### 1\. UPDATE một cột

```
UPDATE Movie
SET genre = 'Action'
WHERE movie_id = 1;
```

Cấu trúc:

```
UPDATE table
SET column = value
WHERE condition;
```

---

### 2\. UPDATE nhiều cột

```
UPDATE Movie
SET
    name = 'Avatar 2',
    genre = 'Sci-Fi',
    year = 2022
WHERE movie_id = 1;
```

---

### 3\. UPDATE bằng phép tính

#### Tăng giá 10%

```
UPDATE Ticket
SET price = price * 1.1
WHERE room_id = 2;
```

#### Cộng thêm một giá trị

```
UPDATE Ticket
SET price = price + 10000
WHERE room_id = 2;
```

---

### 4\. UPDATE với nhiều điều kiện

```
UPDATE Movie
SET genre = 'Action'
WHERE year > 2020
AND genre = 'Adventure';
```

---

### 5\. UPDATE NULL

```
UPDATE Movie
SET description = NULL
WHERE movie_id = 1;
```

---

### 6\. UPDATE bằng CASE

```
UPDATE Movie
SET genre =
    CASE
        WHEN year < 2000 THEN 'Classic'
        WHEN year >= 2020 THEN 'Modern'
        ELSE genre
    END;
```

---

### 7. UPDATE không có WHERE

```
UPDATE Movie
SET genre = 'Action';
```

→ **Tất cả dòng** đều bị sửa.

Vì vậy luôn kiểm tra:

```
SELECT *
FROM Movie
WHERE movie_id = 1;
```

trước khi:

```
UPDATE Movie
...
```

---

## IV. DELETE — Xóa dữ liệu

### 1\. DELETE một dòng

```
DELETE FROM Movie
WHERE movie_id = 1;
```

---

### 2\. DELETE nhiều dòng

```
DELETE FROM Movie
WHERE year < 2000;
```

---

### 3\. DELETE với nhiều điều kiện

```
DELETE FROM Movie
WHERE year < 2000
AND genre = 'Action';
```

---

### 4\. DELETE toàn bộ dữ liệu

SQL

```
DELETE FROM Movie;
```

→ Xóa tất cả **các dòng**.

Bảng `Movie` vẫn tồn tại.

---

### 5. DELETE không có WHERE

SQL

```
DELETE FROM Movie;
```

→ Xóa toàn bộ dữ liệu.

Do đó nếu chỉ muốn xóa một số dòng:

```
DELETE FROM Movie
WHERE movie_id = 5;
```

---

## V. AS — Bí danh (Alias)

`AS` chủ yếu có **2 cách dùng**.

### 1\. AS cho cột

#### 1.1. Đổi tên cột kết quả

```
SELECT name AS movie_name
FROM Movie;
```

#### 1.2. Đổi tên biểu thức

```
SELECT price * 2 AS double_price
FROM Ticket;
```

#### 1.3. Đổi tên kết quả của hàm

```
SELECT COUNT(*) AS total_movies
FROM Movie;
```

#### 1.4. Có thể bỏ AS

```
SELECT name movie_name
FROM Movie;
```

Tương đương:

```
SELECT name AS movie_name
FROM Movie;
```

---

### 2\. AS cho bảng

```
SELECT m.name
FROM Movie AS m;
```

Có thể bỏ `AS`:

```
SELECT m.name
FROM Movie m;
```

Đặc biệt hữu ích khi làm `JOIN`:

```
SELECT m.name, r.name
FROM Movie AS m
JOIN Room AS r;
```

---

## VI. DISTINCT — Loại bỏ dữ liệu trùng

### 1\. DISTINCT một cột

```
SELECT DISTINCT genre
FROM Movie;
```

---

### 2\. DISTINCT nhiều cột

```
SELECT DISTINCT genre, year
FROM Movie;
```

SQL xét **cả tổ hợp**:

```
(genre, year)
```

---

### 3\. DISTINCT + WHERE

```
SELECT DISTINCT genre
FROM Movie
WHERE year >= 2000;
```

→ Lọc phim từ 2000 trước, sau đó lấy các thể loại không trùng.

---

### 4\. DISTINCT + AS

```
SELECT DISTINCT genre AS movie_genre
FROM Movie;
```

---

## VII. Kết hợp các cách dùng

Đây là phần rất quan trọng khi làm bài.

### 1\. SELECT + WHERE + ORDER BY

```
SELECT name, year
FROM Movie
WHERE year >= 2000
ORDER BY year DESC;
```

---

### 2\. SELECT + DISTINCT + WHERE

```
SELECT DISTINCT genre
FROM Movie
WHERE year >= 2000;
```

---

### 3\. SELECT + AS + WHERE + ORDER BY

```
SELECT
    name AS movie_name,
    year AS production_year
FROM Movie
WHERE year >= 2000
ORDER BY production_year DESC;
```

---

### 4\. SELECT + DISTINCT + AS + WHERE + ORDER BY + LIMIT

```
SELECT DISTINCT
    genre AS movie_genre
FROM Movie
WHERE year >= 2000
ORDER BY movie_genre
LIMIT 5;
```

## VIII. Thứ tự viết một câu SELECT

```
SELECT ...
FROM ...
WHERE ...
GROUP BY ...
HAVING ...
ORDER BY ...
LIMIT ...;
```

# Lọc dữ liệu: `WHERE`, `HAVING`

## I. `WHERE` — Lọc dữ liệu theo từng dòng

### 1\. Định nghĩa

`WHERE` dùng để **lọc các dòng dữ liệu** theo một hoặc nhiều điều kiện.

```
SELECT ...
FROM ...
WHERE điều_kiện;
```

Ví dụ:

```
SELECT *
FROM Movie
WHERE year >= 2020;
```

→ Chỉ lấy những dòng có `year >= 2020`.

---

### 2\. Các điều kiện trong `WHERE`

#### 2.1. Toán tử so sánh

```
=       -- bằng
<>      -- khác
!=      -- khác
>       -- lớn hơn
<       -- nhỏ hơn
>=      -- lớn hơn hoặc bằng
<=      -- nhỏ hơn hoặc bằng
```

Ví dụ:

SQL

```
WHERE year >= 2020
```

---

#### 2.2. `AND`, `OR`

SQL

```
WHERE condition1 AND condition2
```

→ Cả hai điều kiện phải đúng.

SQL

```
WHERE condition1 OR condition2
```

→ Chỉ cần một điều kiện đúng.

Có thể dùng `()`:

```
WHERE year >= 2020
AND (genre = 'Action' OR genre = 'Comedy');
```

---

#### 2.3. `IN`, `NOT IN`

Kiểm tra giá trị có nằm trong một danh sách hay không.

SQL

```
WHERE genre IN ('Action', 'Comedy')
```

SQL

```
WHERE genre NOT IN ('Action', 'Comedy')
```

---

#### 2.4. `BETWEEN`, `NOT BETWEEN`

Kiểm tra giá trị nằm trong khoảng.

SQL

```
WHERE year BETWEEN 2000 AND 2020
```

`BETWEEN` bao gồm cả hai đầu mút.

---

#### 2.5. `LIKE`, `NOT LIKE`

Tìm chuỗi theo mẫu.

SQL

```
WHERE name LIKE 'A%'
```

```
% → 0 hoặc nhiều ký tự
_ → đúng 1 ký tự
```

---

#### 2.6. `IS NULL`, `IS NOT NULL`

Kiểm tra giá trị `NULL`.

SQL

```
WHERE description IS NULL
```

SQL

```
WHERE description IS NOT NULL
```

- Không dùng:

SQL

```
WHERE description = NULL
```

---

# II. `WHERE` — Các cách dùng quan trọng

## 1\. Lọc trước khi `GROUP BY`

Đây là điểm quan trọng nhất cần hiểu thêm.

```
SELECT genre, COUNT(*)
FROM Movie
WHERE year >= 2020
GROUP BY genre;
```

Quá trình:

```
Bảng Movie
    ↓
WHERE
    ↓
Chỉ giữ phim >= 2020
    ↓
GROUP BY genre
    ↓
Đếm từng nhóm
```

→ `WHERE` lọc **trước khi gom nhóm**.

---

## 2\. `WHERE` không dùng để lọc kết quả của hàm tổng hợp

Ví dụ muốn:

> Tìm thể loại có ít nhất 5 phim.

- Không viết:

```
SELECT genre, COUNT(*)
FROM Movie
WHERE COUNT(*) >= 5
GROUP BY genre;
```

Vì `WHERE` lọc **dòng**, trong khi `COUNT(*)` được tính **sau khi gom nhóm**.

Phải dùng `HAVING`.

---

# III. `HAVING` — Lọc nhóm dữ liệu

### 1\. Định nghĩa

`HAVING` dùng để **lọc các nhóm được tạo bởi `GROUP BY`**.

Cú pháp:

```
SELECT column, aggregate_function(...)
FROM table
GROUP BY column
HAVING condition;
```

Ví dụ:

```
SELECT genre, COUNT(*) AS total
FROM Movie
GROUP BY genre
HAVING COUNT(*) >= 5;
```

→ Chỉ lấy những `genre` có ít nhất 5 phim.

---

# IV. `HAVING` — Các cách dùng

## 1\. Lọc theo `COUNT()`

```
SELECT genre, COUNT(*) AS total
FROM Movie
GROUP BY genre
HAVING COUNT(*) >= 5;
```

→ Nhóm có ít nhất 5 dòng.

---

## 2\. Lọc theo `SUM()`

```
SELECT movie_id, SUM(amount) AS revenue
FROM Ticket
GROUP BY movie_id
HAVING SUM(amount) > 1000000;
```

→ Phim có tổng doanh thu > 1 triệu.

---

## 3\. Lọc theo `AVG()`

```
SELECT genre, AVG(rating) AS avg_rating
FROM Movie
GROUP BY genre
HAVING AVG(rating) >= 8;
```

→ Thể loại có điểm trung bình ≥ 8.

---

## 4\. Lọc theo `MAX()`

```
SELECT movie_id, MAX(price) AS max_price
FROM Ticket
GROUP BY movie_id
HAVING MAX(price) > 100000;
```

→ Nhóm có giá cao nhất > 100.000.

---

## 5\. Lọc theo `MIN()`

```
SELECT movie_id, MIN(price) AS min_price
FROM Ticket
GROUP BY movie_id
HAVING MIN(price) >= 50000;
```

→ Nhóm có giá thấp nhất ≥ 50.000.

---

# V. `WHERE` + `GROUP BY` + `HAVING`

Đây là phần **quan trọng nhất của chủ đề này**.

Ví dụ:

> Tìm các thể loại có ít nhất 5 phim được sản xuất từ năm 2020 trở đi.

```
SELECT genre, COUNT(*) AS total
FROM Movie
WHERE year >= 2020
GROUP BY genre
HAVING COUNT(*) >= 5;
```

Thứ tự xử lý:

```
FROM Movie
     ↓
WHERE year >= 2020
     ↓
GROUP BY genre
     ↓
COUNT(*)
     ↓
HAVING COUNT(*) >= 5
     ↓
Kết quả
```

### Ý nghĩa từng phần

SQL

```
WHERE year >= 2020
```

→ **Lọc phim**

SQL

```
GROUP BY genre
```

→ **Gom phim thành nhóm**

SQL

```
COUNT(*)
```

→ **Đếm từng nhóm**

SQL

```
HAVING COUNT(*) >= 5
```

→ **Lọc nhóm**

---

# VI. `WHERE` vs `HAVING`

|                 | `WHERE`              | `HAVING`               |
| --------------- | -------------------- | ---------------------- |
| Lọc             | Dòng                 | Nhóm                   |
| Thời điểm       | Trước `GROUP BY`     | Sau `GROUP BY`         |
| Thường dùng với | Điều kiện trên cột   | Hàm tổng hợp           |
| Ví dụ           | `WHERE year >= 2020` | `HAVING COUNT(*) >= 5` |

### Cách nhớ

```
WHERE
↓
"Lọc từng dòng"

GROUP BY
↓
"Gom các dòng thành nhóm"

HAVING
↓
"Lọc các nhóm"
```

---

# VII. Trường hợp `HAVING` không có `GROUP BY`

`HAVING` **có thể dùng mà không cần `GROUP BY`** khi toàn bộ dữ liệu được xem như một nhóm.

Ví dụ:

```
SELECT COUNT(*) AS total
FROM Movie
HAVING COUNT(*) > 100;
```

→ Nếu bảng có hơn 100 phim thì trả về kết quả.

---

# VIII. `HAVING` + nhiều điều kiện

Có thể dùng `AND`, `OR` như `WHERE`.

Ví dụ:

```
SELECT genre, COUNT(*) AS total, AVG(rating) AS avg_rating
FROM Movie
GROUP BY genre
HAVING COUNT(*) >= 5
AND AVG(rating) >= 8;
```

→ Nhóm phải đồng thời:

```
Có >= 5 phim
VÀ
Điểm trung bình >= 8
```

---

# IX. `HAVING` + `AS`

Trong MySQL có thể dùng alias trong `HAVING`:

```
SELECT genre, COUNT(*) AS total
FROM Movie
GROUP BY genre
HAVING total >= 5;
```

Thay vì:

SQL

```
HAVING COUNT(*) >= 5;
```

---

# X. Công thức cần nhớ

### `WHERE`

```
SELECT ...
FROM ...
WHERE điều_kiện;
```

### `WHERE` + `GROUP BY`

```
SELECT ...
FROM ...
WHERE điều_kiện
GROUP BY ...;
```

### `GROUP BY` + `HAVING`

```
SELECT ...
FROM ...
GROUP BY ...
HAVING điều_kiện;
```

### `WHERE` + `GROUP BY` + `HAVING`

```
SELECT ...
FROM ...
WHERE điều_kiện_trên_dòng
GROUP BY ...
HAVING điều_kiện_trên_nhóm;
```

---

## Phần này chỉ cần nhớ 2 ý mới

```
WHERE  → lọc DÒNG
HAVING → lọc NHÓM
```

Ví dụ kinh điển:

```
SELECT genre, COUNT(*) AS total
FROM Movie
WHERE year >= 2020       -- lọc dòng
GROUP BY genre            -- gom nhóm
HAVING COUNT(*) >= 5;    -- lọc nhóm
```

# Kết hợp bảng và kết quả: `JOIN`, `UNION`

> Điểm khác nhau quan trọng:
>
> **`JOIN` → ghép các BẢNG theo cột có liên quan.**  
> **`UNION` → ghép các KẾT QUẢ `SELECT` theo chiều dọc.**

---

# I. `JOIN` — Kết hợp các bảng

## 1\. Định nghĩa

`JOIN` dùng để lấy dữ liệu từ **nhiều bảng** bằng cách liên kết chúng thông qua các cột có quan hệ.

Ví dụ:

### `Movie`

| movie\\\_id | name    |
| ----------- | ------- |
| 1           | Avatar  |
| 2           | Titanic |

### `Screening`

| screening\\\_id | movie\\\_id | room\\\_id |
| --------------- | ----------- | ---------- |
| 101             | 1           | 2          |
| 102             | 2           | 1          |

Hai bảng liên kết qua:

```
Movie.movie_id = Screening.movie_id
```

---

# II. `INNER JOIN`

## 1\. Định nghĩa

`INNER JOIN` chỉ lấy những dòng **có dữ liệu khớp ở cả hai bảng**.

```
SELECT ...
FROM table1
INNER JOIN table2
ON điều_kiện_liên_kết;
```

Ví dụ:

```
SELECT m.name, s.screening_id
FROM Movie AS m
INNER JOIN Screening AS s
ON m.movie_id = s.movie_id;
```

Kết quả:

| name    | screening\\\_id |
| ------- | --------------- |
| Avatar  | 101             |
| Titanic | 102             |

Nếu một phim **không có suất chiếu**, phim đó không xuất hiện.

> `JOIN` viết ngắn thường được hiểu là `INNER JOIN`.

```
FROM Movie m
JOIN Screening s
ON m.movie_id = s.movie_id;
```

---

# III. `LEFT JOIN`

## 1\. Định nghĩa

`LEFT JOIN` lấy:

- **tất cả dòng của bảng bên trái**
- dòng tương ứng của bảng bên phải nếu có
- nếu không có dữ liệu tương ứng → `NULL`

```
SELECT ...
FROM table1
LEFT JOIN table2
ON điều_kiện;
```

Ví dụ:

```
SELECT m.name, s.screening_id
FROM Movie AS m
LEFT JOIN Screening AS s
ON m.movie_id = s.movie_id;
```

Nếu `Avatar` có suất chiếu nhưng `Titanic` không có:

| name    | screening\\\_id |
| ------- | --------------- |
| Avatar  | 101             |
| Titanic | NULL            |

### Nhớ:

```
LEFT JOIN
→ Giữ TOÀN BỘ bảng bên trái
```

---

# IV. `RIGHT JOIN`

## 1\. Định nghĩa

Ngược lại với `LEFT JOIN`.

`RIGHT JOIN` giữ **tất cả dòng của bảng bên phải**.

```
SELECT ...
FROM Movie AS m
RIGHT JOIN Screening AS s
ON m.movie_id = s.movie_id;
```

Nếu suất chiếu nào không có phim tương ứng:

| name   | screening\\\_id |
| ------ | --------------- |
| Avatar | 101             |
| NULL   | 103             |

### Nhớ:

```
LEFT JOIN
→ giữ bảng bên trái

RIGHT JOIN
→ giữ bảng bên phải
```

Trong thực tế, `RIGHT JOIN` ít được dùng hơn. Có thể đổi thứ tự bảng để dùng `LEFT JOIN`.

---

# V. `FULL OUTER JOIN`

## 1\. Định nghĩa

`FULL OUTER JOIN` lấy **tất cả dòng của cả hai bảng**:

```
Khớp → ghép lại
Chỉ có bên trái → vẫn giữ
Chỉ có bên phải → vẫn giữ
```

Cú pháp ở các hệ quản trị hỗ trợ:

```
SELECT ...
FROM table1
FULL OUTER JOIN table2
ON điều_kiện;
```

**MySQL không hỗ trợ trực tiếp `FULL OUTER JOIN`.**

Trong MySQL thường mô phỏng bằng `LEFT JOIN + RIGHT JOIN + UNION`.

Ví dụ:

```
SELECT m.name, s.screening_id
FROM Movie m
LEFT JOIN Screening s
ON m.movie_id = s.movie_id

UNION

SELECT m.name, s.screening_id
FROM Movie m
RIGHT JOIN Screening s
ON m.movie_id = s.movie_id;
```

---

# VI. `JOIN` nhiều bảng

Có thể nối **3, 4, 5... bảng**.

Ví dụ hệ thống rạp:

```
Movie
  ↓
Screening
  ↓
Room
  ↓
Cinema
```

Truy vấn:

```
SELECT
    m.name AS movie_name,
    r.name AS room_name,
    c.name AS cinema_name
FROM Movie AS m
JOIN Screening AS s
    ON m.movie_id = s.movie_id
JOIN Room AS r
    ON s.room_id = r.room_id
JOIN Cinema AS c
    ON r.cinema_id = c.cinema_id;
```

Quá trình:

```
Movie
   ↓ JOIN
Screening
   ↓ JOIN
Room
   ↓ JOIN
Cinema
```

---

# VII. `JOIN` + `WHERE`

Sau khi JOIN có thể tiếp tục lọc bằng `WHERE`.

```
SELECT
    m.name,
    s.screening_time
FROM Movie AS m
JOIN Screening AS s
    ON m.movie_id = s.movie_id
WHERE s.screening_time >= '2026-08-28';
```

Ở đây:

```
JOIN
→ kết hợp Movie + Screening

WHERE
→ lọc các suất chiếu
```

---

# VIII. `JOIN` + `GROUP BY` + `HAVING`

Có thể kết hợp toàn bộ kiến thức đã học.

Ví dụ:

> Tìm những phim có ít nhất 3 suất chiếu.

```
SELECT
    m.name,
    COUNT(s.screening_id) AS total_screenings
FROM Movie AS m
JOIN Screening AS s
    ON m.movie_id = s.movie_id
GROUP BY m.movie_id, m.name
HAVING COUNT(s.screening_id) >= 3;
```

Quá trình:

```
JOIN
 ↓
Kết hợp Movie + Screening
 ↓
GROUP BY
 ↓
Gom các suất chiếu theo phim
 ↓
COUNT
 ↓
HAVING
 ↓
Giữ phim có >= 3 suất
```

---

# IX. `JOIN` với nhiều điều kiện

Điều kiện trong `ON` có thể có `AND`.

```
SELECT *
FROM Screening AS s
JOIN Movie AS m
ON s.movie_id = m.movie_id
AND m.year >= 2020;
```

Có thể kết hợp với `WHERE`:

```
SELECT *
FROM Screening AS s
JOIN Movie AS m
ON s.movie_id = m.movie_id
WHERE s.room_id = 2;
```

---

# X. Bảng tổng hợp các loại `JOIN`

| Loại              | Kết quả                         |
| ----------------- | ------------------------------- |
| `INNER JOIN`      | Chỉ lấy dòng khớp ở cả hai bảng |
| `LEFT JOIN`       | Giữ toàn bộ bảng trái           |
| `RIGHT JOIN`      | Giữ toàn bộ bảng phải           |
| `FULL OUTER JOIN` | Giữ toàn bộ cả hai bảng         |

### Nhớ nhanh:

```
INNER → chỉ phần GIAO

LEFT  → giữ TẤT CẢ bên TRÁI

RIGHT → giữ TẤT CẢ bên PHẢI

FULL  → giữ TẤT CẢ hai bên
```

---

# XI. `UNION` — Kết hợp kết quả SELECT

## 1\. Định nghĩa

`UNION` dùng để **ghép kết quả của nhiều câu `SELECT` thành một kết quả duy nhất**.

```
SELECT ...
FROM ...

UNION

SELECT ...
FROM ...;
```

Khác với `JOIN`:

```
JOIN
→ ghép BẢNG theo chiều ngang

UNION
→ ghép KẾT QUẢ theo chiều dọc
```

---

# XII. Điều kiện để dùng `UNION`

Hai câu `SELECT` phải có:

### 1\. Cùng số lượng cột

✅

```
SELECT name, year
FROM Movie

UNION

SELECT name, year
FROM OldMovie;
```

❌

```
SELECT name, year
FROM Movie

UNION

SELECT name
FROM OldMovie;
```

---

### 2\. Các cột tương ứng phải có kiểu dữ liệu tương thích

Ví dụ:

```
VARCHAR ↔ VARCHAR
INT ↔ INT
DATE ↔ DATE
```

Không nhất thiết tên cột phải giống nhau.

---

# XIII. `UNION` tự loại bỏ trùng

Ví dụ:

```
SELECT name
FROM Movie

UNION

SELECT name
FROM OldMovie;
```

Nếu cả hai bảng đều có:

```
Avatar
```

thì kết quả chỉ có một `Avatar`.

```
Movie       OldMovie
  │             │
  ├─ Avatar ────┤
  │             │
  └─ Titanic    └─ Avatar

       UNION

Avatar
Titanic
```

---

# XIV. `UNION ALL`

## 1\. Định nghĩa

`UNION ALL` cũng ghép kết quả nhưng **không loại bỏ dòng trùng**.

```
SELECT name
FROM Movie

UNION ALL

SELECT name
FROM OldMovie;
```

Nếu cả hai có `Avatar`:

```
Avatar
Titanic
Avatar
```

---

# XV. `UNION` vs `UNION ALL`

|                        | `UNION` | `UNION ALL` |
| ---------------------- | ------- | ----------- |
| Ghép kết quả           | ✅      | ✅          |
| Loại dòng trùng        | ✅      | ❌          |
| Giữ nguyên tất cả dòng | ❌      | ✅          |
| Thường nhanh hơn       | ❌      | ✅          |

### Nhớ:

```
UNION
→ Gộp + bỏ trùng

UNION ALL
→ Gộp + giữ nguyên
```

---

# XVI. `UNION` với `ORDER BY`

Nếu muốn sắp xếp toàn bộ kết quả:

```
SELECT name, year
FROM Movie

UNION

SELECT name, year
FROM OldMovie

ORDER BY year DESC;
```

`ORDER BY` đặt **ở cuối**.

---

# XVII. `UNION` với `LIMIT`

```
SELECT name
FROM Movie

UNION

SELECT name
FROM OldMovie

ORDER BY name
LIMIT 10;
```

→ Gộp → sắp xếp → lấy 10 dòng.

---

# XVIII. `UNION` với điều kiện riêng

Mỗi `SELECT` có thể có `WHERE` riêng:

```
SELECT name, year
FROM Movie
WHERE year >= 2020

UNION

SELECT name, year
FROM OldMovie
WHERE year < 2000;
```

Quá trình:

```
Movie
 ↓
WHERE year >= 2020
 ↓
SELECT

       UNION

OldMovie
 ↓
WHERE year < 2000
 ↓
SELECT
```

---

# XIX. `JOIN` vs `UNION`

Đây là phần **cần nhớ nhất**.

| `JOIN`                     | `UNION`                            |
| -------------------------- | ---------------------------------- |
| Kết hợp **bảng**           | Kết hợp **kết quả SELECT**         |
| Ghép theo **chiều ngang**  | Ghép theo **chiều dọc**            |
| Cần điều kiện `ON`         | Không cần `ON`                     |
| Các bảng thường có quan hệ | Các SELECT có cấu trúc tương thích |
| Tăng số cột kết quả        | Tăng số dòng kết quả               |

### Hình dung

```
JOIN:

A                  B
┌────┬────┐        ┌────┬────┐
│ id │name│        │ id │age │
├────┼────┤   +    ├────┼────┤
│ 1  │ An │        │ 1  │ 20 │
│ 2  │ Bình│       │ 2  │ 21 │
└────┴────┘        └────┴────┘

        ↓ JOIN

┌────┬─────┬─────┐
│ id │ name│ age │
├────┼─────┼─────┤
│ 1  │ An  │ 20  │
│ 2  │ Bình│ 21  │
└────┴─────┴─────┘

→ Tăng CỘT
```

```
UNION:

SELECT name FROM Movie
        +
SELECT name FROM OldMovie

        ↓ UNION

┌─────────┐
│ name    │
├─────────┤
│ Avatar  │
│ Titanic │
│ Joker   │
│ Batman  │
└─────────┘

→ Tăng DÒNG
```

---

# XX. Công thức cần nhớ

### JOIN

```
SELECT ...
FROM A
JOIN B
ON A.key = B.key;
```

### LEFT JOIN

```
SELECT ...
FROM A
LEFT JOIN B
ON A.key = B.key;
```

### JOIN nhiều bảng

```
SELECT ...
FROM A
JOIN B ON ...
JOIN C ON ...
JOIN D ON ...;
```

### UNION

```
SELECT column1, column2
FROM A

UNION

SELECT column1, column2
FROM B;
```

### UNION ALL

```
SELECT column1, column2
FROM A

UNION ALL

SELECT column1, column2
FROM B;
```

---

## Chốt phần này

```
KẾT HỢP DỮ LIỆU
│
├── JOIN
│   │
│   ├── INNER JOIN → phần khớp
│   ├── LEFT JOIN  → giữ bên trái
│   ├── RIGHT JOIN → giữ bên phải
│   └── FULL JOIN  → giữ cả hai
│
└── UNION
    │
    ├── UNION     → gộp + bỏ trùng
    └── UNION ALL → gộp + giữ trùng
```

**Một câu để phân biệt:**

> `JOIN` = **ghép bảng theo cột** → thường làm **tăng số cột**.  
> `UNION` = **ghép kết quả SELECT** → thường làm **tăng số dòng**.

#### Bạn đã nói:

cho ngan laij

#### ChatGPT đã nói:

# Kết hợp bảng và kết quả: `JOIN`, `UNION`

## I. `JOIN` — Kết hợp bảng

### 1\. Định nghĩa

Ghép dữ liệu từ nhiều bảng dựa trên điều kiện liên kết.

```
SELECT ...
FROM A
JOIN B
ON A.id = B.id;
```

### 2\. Các loại JOIN

| Loại              | Ý nghĩa                       |
| ----------------- | ----------------------------- |
| `INNER JOIN`      | Chỉ lấy dòng khớp ở cả 2 bảng |
| `LEFT JOIN`       | Giữ tất cả dòng bảng trái     |
| `RIGHT JOIN`      | Giữ tất cả dòng bảng phải     |
| `FULL OUTER JOIN` | Giữ tất cả dòng 2 bảng        |

Ví dụ:

```
SELECT m.name, s.screening_time
FROM Movie m
JOIN Screening s
ON m.movie_id = s.movie_id;
```

### 3\. JOIN nhiều bảng

```
SELECT ...
FROM A
JOIN B ON ...
JOIN C ON ...;
```

---

# II. `UNION` — Kết hợp kết quả SELECT

### 1\. Định nghĩa

Ghép kết quả của nhiều câu `SELECT` thành một kết quả.

```
SELECT name FROM Movie
UNION
SELECT name FROM OldMovie;
```

Hai `SELECT` phải có **cùng số cột** và kiểu dữ liệu tương thích.

### 2\. `UNION` và `UNION ALL`

|                | `UNION` | `UNION ALL` |
| -------------- | ------- | ----------- |
| Ghép kết quả   | ✅      | ✅          |
| Bỏ dòng trùng  | ✅      | ❌          |
| Giữ dòng trùng | ❌      | ✅          |

```
SELECT name FROM Movie
UNION ALL
SELECT name FROM OldMovie;
```

# Tổng hợp và nhóm dữ liệu: `COUNT`, `SUM`, `AVG`, `GROUP BY`

## I. Hàm tổng hợp

### `COUNT()`

Đếm số dòng / giá trị.

SQL

```
SELECT COUNT(*) FROM Movie;
```

### `SUM()`

Tính tổng.

SQL

```
SELECT SUM(price) FROM Ticket;
```

### `AVG()`

Tính trung bình.

SQL

```
SELECT AVG(price) FROM Ticket;
```

> Các hàm khác: `MAX()` → lớn nhất, `MIN()` → nhỏ nhất.

---

## II. `GROUP BY`

**Định nghĩa:** Gom các dòng có cùng giá trị thành từng nhóm.

```
SELECT genre, COUNT(*) AS total
FROM Movie
GROUP BY genre;
```

→ Đếm số phim theo từng thể loại.

### Kết hợp

```
SELECT genre, AVG(rating)
FROM Movie
GROUP BY genre;
```

→ Tính điểm trung bình của từng thể loại.

**Nhớ:** `GROUP BY` → **gom nhóm**, `COUNT/SUM/AVG` → **tính toán trên từng nhóm**.

# Truy vấn con: `Subquery`

## I. Định nghĩa

**Subquery** là một câu lệnh `SELECT` được đặt **bên trong một câu SQL khác**.

```
SELECT ...
FROM ...
WHERE column = (
    SELECT ...
    FROM ...
);
```

→ Có thể hiểu là: **truy vấn nhỏ chạy trước → lấy kết quả → truy vấn ngoài sử dụng kết quả đó.**

---

## II. Subquery trong `WHERE`

### 1\. Trả về một giá trị

Ví dụ: tìm phim có năm sản xuất mới nhất.

```
SELECT *
FROM Movie
WHERE year = (
    SELECT MAX(year)
    FROM Movie
);
```

```
Subquery → tìm MAX(year)
        ↓
Truy vấn ngoài → tìm phim có year đó
```

---

### 2\. Trả về nhiều giá trị: `IN`

Ví dụ:

```
SELECT *
FROM Movie
WHERE genre IN (
    SELECT genre
    FROM Movie
    WHERE year >= 2020
);
```

→ `Subquery` trả về nhiều `genre`, truy vấn ngoài kiểm tra bằng `IN`.

---

## III. Subquery với `GROUP BY`

Có thể dùng kết quả `GROUP BY` làm bảng tạm:

```
SELECT *
FROM (
    SELECT genre, COUNT(*) AS total
    FROM Movie
    GROUP BY genre
) AS result
WHERE total >= 5;
```

→ `result` là **kết quả tạm**, không phải bảng thật.

---

## IV. Subquery trong `FROM`

Subquery nằm trong `FROM` được xem như **một bảng tạm**.

```
SELECT result.genre, result.total
FROM (
    SELECT genre, COUNT(*) AS total
    FROM Movie
    GROUP BY genre
) AS result;
```

⚠️ Phải đặt **alias** cho subquery:

SQL

```
AS result
```

---

## V. Subquery trong `SELECT`

Có thể đặt subquery ngay trong `SELECT`.

```
SELECT
    name,
    (SELECT COUNT(*) FROM Movie) AS total_movies
FROM Movie;
```

→ Mỗi dòng phim đều hiển thị tổng số phim.

---

## VI. `EXISTS`

Kiểm tra **có tồn tại dữ liệu phù hợp hay không**.

```
SELECT *
FROM Movie m
WHERE EXISTS (
    SELECT 1
    FROM Screening s
    WHERE s.movie_id = m.movie_id
);
```

→ Chỉ lấy phim **có ít nhất một suất chiếu**.

### `NOT EXISTS`

```
SELECT *
FROM Movie m
WHERE NOT EXISTS (
    SELECT 1
    FROM Screening s
    WHERE s.movie_id = m.movie_id
);
```

→ Lấy phim **không có suất chiếu**.

---

## VII. `ANY`, `ALL`

Ít dùng hơn nhưng nên biết.

### `ANY`

Điều kiện đúng nếu so sánh đúng với **ít nhất một** giá trị.

```
WHERE price > ANY (
    SELECT price
    FROM Ticket
);
```

### `ALL`

Điều kiện phải đúng với **tất cả** giá trị.

```
WHERE price > ALL (
    SELECT price
    FROM Ticket
);
```

---

## VIII. Subquery lồng nhau

Subquery có thể chứa một subquery khác:

```
SELECT *
FROM Movie
WHERE year = (
    SELECT MAX(year)
    FROM Movie
    WHERE genre = (
        SELECT genre
        FROM Movie
        WHERE movie_id = 1
    )
);
```

→ Có thể lồng nhiều cấp, nhưng nên hạn chế nếu câu SQL quá khó đọc.

---

# IX. Tóm tắt

| Vị trí   | Mục đích                        |
| -------- | ------------------------------- |
| `WHERE`  | Lọc dựa trên kết quả subquery   |
| `FROM`   | Dùng subquery như một bảng tạm  |
| `SELECT` | Tạo một giá trị tính toán       |
| `EXISTS` | Kiểm tra có dữ liệu tồn tại     |
| `IN`     | Kiểm tra thuộc tập kết quả      |
| `ANY`    | So sánh với ít nhất một giá trị |
| `ALL`    | So sánh với tất cả giá trị      |

# Thứ tự thực thi logic của truy vấn

SQL **không thực thi theo đúng thứ tự ta viết**.

### 1\. Thứ tự logic

```
1. FROM
2. JOIN
3. WHERE
4. GROUP BY
5. HAVING
6. SELECT
7. DISTINCT
8. ORDER BY
9. LIMIT
```

### 2\. Ý nghĩa từng bước

| Bước | Lệnh       | Làm gì?              |
| ---- | ---------- | -------------------- |
| 1    | `FROM`     | Xác định bảng        |
| 2    | `JOIN`     | Kết hợp bảng         |
| 3    | `WHERE`    | Lọc từng dòng        |
| 4    | `GROUP BY` | Gom nhóm             |
| 5    | `HAVING`   | Lọc nhóm             |
| 6    | `SELECT`   | Chọn cột / tính toán |
| 7    | `DISTINCT` | Loại dòng trùng      |
| 8    | `ORDER BY` | Sắp xếp              |
| 9    | `LIMIT`    | Giới hạn số dòng     |

### 3\. Ví dụ

```
SELECT genre, COUNT(*) AS total
FROM Movie
WHERE year >= 2020
GROUP BY genre
HAVING COUNT(*) >= 5
ORDER BY total DESC
LIMIT 3;
```

Logic:

```
FROM Movie
   ↓
WHERE year >= 2020
   ↓
GROUP BY genre
   ↓
HAVING COUNT(*) >= 5
   ↓
SELECT genre, COUNT(*)
   ↓
ORDER BY total DESC
   ↓
LIMIT 3
```

→ **Lọc phim → gom nhóm → lọc nhóm → chọn kết quả → sắp xếp → lấy 3 dòng.**
