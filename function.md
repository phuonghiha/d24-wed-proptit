| Hàm          | Công dụng            | Ví dụ                                     |
| ------------ | -------------------- | ----------------------------------------- |
| `DATEDIFF()` | Khoảng cách **ngày** | `DATEDIFF('2026-09-04','2026-09-01')` → 3 |
| `DATE_ADD()` | Cộng thời gian       | `DATE_ADD(date, INTERVAL 1 DAY)`          |
| `DATE_SUB()` | Trừ thời gian        | `DATE_SUB(date, INTERVAL 1 DAY)`          |
| `YEAR()`     | Lấy năm              | `YEAR('2026-09-04')` → 2026               |
| `MONTH()`    | Lấy tháng            | `MONTH('2026-09-04')` → 9                 |
| `DAY()`      | Lấy ngày             | `DAY('2026-09-04')` → 4                   |
| `CURDATE()`  | Ngày hiện tại        | `2026-09-04`                              |
| `NOW()`      | Ngày + giờ hiện tại  | `2026-09-04 12:...`                       |
| `DATE()`     | Lấy phần ngày        | `DATE('2026-09-04 12:30:00')`             |
| `TIME()`     | Lấy phần giờ         | `TIME('2026-09-04 12:30:00')`             |
