# 1\. HTTP là gì?

## 1.1. Định nghĩa

**HTTP** là viết tắt của **HyperText Transfer Protocol** – **Giao thức Truyền tải Siêu Văn Bản**.

HTTP là một **Application Layer Protocol (Giao thức tầng ứng dụng)** được sử dụng để trao đổi dữ liệu giữa **Client** và **Server**, đặc biệt là nền tảng quan trọng của **World Wide Web (WWW)**.

HTTP cho phép Client gửi yêu cầu đến Server và Server gửi kết quả phản hồi lại Client.

Có thể hiểu đơn giản:

```
Client
   |
   | HTTP Request
   v
Server
   |
   | HTTP Response
   v
Client
```

Ví dụ:

Bạn mở trình duyệt và truy cập:

```
https://example.com
```

Trình duyệt sẽ đóng vai trò **Client**.

Server của `example.com` nhận yêu cầu và trả về nội dung website.

```
Browser (Client)
       |
       | GET / HTTP/1.1
       v
example.com (Server)
       |
       | HTTP/1.1 200 OK
       | HTML + CSS + JavaScript
       v
Browser
```

Trình duyệt sau đó sử dụng dữ liệu nhận được để hiển thị website cho bạn.

---

# 1.2. Mô hình Client – Server

HTTP hoạt động dựa trên mô hình:

```
Client → Request → Server
Client ← Response ← Server
```

## Client

**Client** là phía gửi yêu cầu đến Server.

Ví dụ:

- Google Chrome
- Microsoft Edge
- Firefox
- Mobile App
- Frontend Website
- Postman
- Một chương trình Java/Python/C++ gửi HTTP Request

Ví dụ bạn nhập:

```
https://example.com/products
```

Chrome sẽ tạo HTTP Request gửi đến Server.

---

## Server

**Server** là phía nhận Request, xử lý yêu cầu và trả về Response.

Ví dụ Server có thể:

1.  Nhận Request.
2.  Kiểm tra dữ liệu.
3.  Kiểm tra quyền truy cập.
4.  Truy vấn Database.
5.  Xử lý nghiệp vụ.
6.  Trả kết quả về Client.

Ví dụ với một ứng dụng Spring Boot:

```
Browser
   |
   | GET /products/10
   v
Spring Boot Server
   |
   | Query
   v
MySQL Database
   |
   | Product
   v
Spring Boot Server
   |
   | JSON
   v
Browser
```

---

# 1.3. HTTP là Stateless

**Stateless** nghĩa là mỗi HTTP Request được xử lý độc lập; HTTP không tự động duy trì trạng thái của các Request trước đó cho Request tiếp theo.

Ví dụ:

Request 1:

```
GET /products/10 HTTP/1.1
Host: example.com
```

Server xử lý Request này.

Sau đó Client gửi Request 2:

```
GET /products/20 HTTP/1.1
Host: example.com
```

HTTP không tự động coi Request 2 là một phần của Request 1.

Có thể hiểu:

```
Request 1 → xử lý độc lập
Request 2 → xử lý độc lập
Request 3 → xử lý độc lập
```

Tuy nhiên, ứng dụng có thể sử dụng các cơ chế khác để duy trì trạng thái giữa các Request.

Ví dụ:

- **HTTP Cookies**
- **Session**
- **Session ID**
- **JWT (JSON Web Token)**
- Access Token

Ví dụ:

```
Client
   |
   | Login
   v
Server
   |
   | Session ID
   v
Client
```

Sau đó Client gửi:

http

```
Cookie: session_id=abc123
```

Server dựa vào `session_id` để xác định phiên làm việc của Client.

Vì vậy cần phân biệt:

```
HTTP
→ Stateless

Ứng dụng sử dụng HTTP
→ Có thể duy trì State bằng Cookie/Session/Token
```

---

# 1.4. HTTP có khả năng mở rộng

HTTP sử dụng **HTTP Headers** để truyền thêm nhiều thông tin giữa Client và Server.

Ví dụ:

```
GET /products HTTP/1.1
Host: example.com
Accept: application/json
User-Agent: Mozilla/5.0
Authorization: Bearer abc123
```

Các Header có thể cung cấp thông tin như:

```
Host
User-Agent
Accept
Content-Type
Authorization
Cookie
Content-Length
Cache-Control
```

Điều này giúp HTTP có thể mở rộng bằng cách bổ sung các Header và quy ước mới mà không cần thay đổi hoàn toàn giao thức.

---

# 1.5. HTTP và khả năng đọc

Các phiên bản HTTP/1.x sử dụng dạng thông điệp văn bản tương đối dễ đọc đối với con người.

Ví dụ:

```
GET /products/10 HTTP/1.1
Host: example.com
Accept: application/json
```

Có thể nhìn vào Request và hiểu được:

```
GET
/products/10
HTTP/1.1
Host: example.com
Accept: application/json
```

Tuy nhiên, cần lưu ý rằng **HTTP/2 và HTTP/3 sử dụng binary framing ở tầng giao thức**, vì vậy không nên hiểu rằng mọi HTTP Message trên mạng đều được truyền dưới dạng văn bản dễ đọc như HTTP/1.x.

---

# 1.6. HTTP và HTTPS

HTTP:

```
http://example.com
```

HTTPS:

```
https://example.com
```

**HTTPS** là HTTP được truyền qua **TLS (Transport Layer Security)** để bảo vệ dữ liệu trong quá trình truyền.

TLS giúp cung cấp các đặc tính quan trọng như:

```
Encryption
Authentication
Integrity
```

Có thể hình dung:

```
HTTP:

Client
  |
  | HTTP Data
  v
Server
```

HTTPS:

```
Client
  |
  | Encrypted HTTP Data
  v
Server
```

Vì vậy các website yêu cầu đăng nhập, thanh toán hoặc truyền dữ liệu quan trọng thường sử dụng HTTPS.

---

# 2\. HTTP Methods

## 2.1. HTTP Method là gì?

**HTTP Methods**, còn gọi là **HTTP Verbs**, là các phương thức được sử dụng để mô tả hành động mà Client muốn Server thực hiện đối với một tài nguyên (**Resource**).

Các HTTP Method phổ biến:

```
GET
POST
PUT
PATCH
DELETE
HEAD
OPTIONS
```

Ngoài ra còn có một số Method khác như:

```
TRACE
CONNECT
```

Có thể hiểu đơn giản:

```
GET     → Lấy dữ liệu
POST    → Gửi dữ liệu / tạo Resource hoặc thực hiện thao tác
PUT     → Thay thế/cập nhật toàn bộ Resource
PATCH   → Cập nhật một phần Resource
DELETE  → Xóa Resource
HEAD    → Lấy thông tin Header tương ứng với GET
OPTIONS → Hỏi Server về các khả năng được hỗ trợ
```

---

# 2.2. GET

## Định nghĩa

**GET** được sử dụng để **truy xuất dữ liệu** từ Server.

Ví dụ:

```
GET /products HTTP/1.1
Host: example.com
```

Ý nghĩa:

> Client muốn lấy danh sách sản phẩm.

---

## Ví dụ 1: Lấy danh sách sản phẩm

### Request

```
GET /products HTTP/1.1
Host: example.com
Accept: application/json
```

### Input

```
Không có Request Body
```

### Server xử lý

Server truy vấn Database:

SQL

```
SELECT * FROM products;
```

Database trả về:

```
1 | Laptop   | 20000000
2 | Mouse    | 500000
3 | Keyboard | 1000000
```

### Response

```
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "name": "Laptop",
        "price": 20000000
    },
    {
        "id": 2,
        "name": "Mouse",
        "price": 500000
    },
    {
        "id": 3,
        "name": "Keyboard",
        "price": 1000000
    }
]
```

### Output

Client nhận được:

```
[
    {
        "id": 1,
        "name": "Laptop",
        "price": 20000000
    },
    {
        "id": 2,
        "name": "Mouse",
        "price": 500000
    },
    {
        "id": 3,
        "name": "Keyboard",
        "price": 1000000
    }
]
```

---

# 2.3. GET với Query Parameter

GET thường sử dụng **Query Parameters** để truyền điều kiện tìm kiếm.

Ví dụ:

```
/products?name=laptop
```

Request:

```
GET /products?name=laptop HTTP/1.1
Host: example.com
```

Trong đó:

```
/products
```

là Resource Path.

```
name=laptop
```

là Query Parameter.

---

## Ví dụ

### Input

```
name = laptop
```

Client tạo:

```
GET /products?name=laptop HTTP/1.1
Host: example.com
```

Server có thể thực hiện:

```
SELECT *
FROM products
WHERE name LIKE '%laptop%';
```

Database có thể trả về:

```
1 | Laptop Dell   | 20000000
5 | Laptop Lenovo | 18000000
```

### Output

```
[
    {
        "id": 1,
        "name": "Laptop Dell",
        "price": 20000000
    },
    {
        "id": 5,
        "name": "Laptop Lenovo",
        "price": 18000000
    }
]
```

---

# 2.4. POST

## Định nghĩa

**POST** được sử dụng để gửi dữ liệu đến Server.

POST thường được sử dụng để:

- Tạo mới Resource.
- Gửi dữ liệu để Server xử lý.
- Thực hiện một thao tác không phù hợp với ngữ nghĩa của các Method khác.

Do đó không nên hiểu đơn giản rằng:

```
POST = luôn luôn tạo mới
```

mà chính xác hơn:

```
POST = gửi dữ liệu để Server xử lý theo ngữ nghĩa của Resource.
```

---

## Ví dụ tạo User

```
POST /users HTTP/1.1
Host: example.com
Content-Type: application/json

{
    "name": "Nguyen Van A",
    "email": "a@gmail.com"
}
```

### Input

```
{
    "name": "Nguyen Van A",
    "email": "a@gmail.com"
}
```

Server nhận dữ liệu:

```
name  = Nguyen Van A
email = a@gmail.com
```

Sau đó có thể thực hiện:

```
INSERT INTO users(name, email)
VALUES ('Nguyen Van A', 'a@gmail.com');
```

Database tạo:

```
id | name          | email
1  | Nguyen Van A  | a@gmail.com
```

### Response

```
HTTP/1.1 201 Created
Content-Type: application/json

{
    "id": 1,
    "name": "Nguyen Van A",
    "email": "a@gmail.com"
}
```

### Output

```
{
    "id": 1,
    "name": "Nguyen Van A",
    "email": "a@gmail.com"
}
```

---

# 2.5. PUT

## Định nghĩa

**PUT** được sử dụng để **thay thế trạng thái/representation của Resource** tại URI được chỉ định.

PUT thường được dùng để cập nhật toàn bộ dữ liệu của một Resource.

PUT cũng có thể được sử dụng để **tạo Resource mới** nếu Resource đó chưa tồn tại và Server định nghĩa URI đó theo cách cho phép tạo mới bằng PUT.

Ví dụ:

```
PUT /users/123 HTTP/1.1
Host: example.com
Content-Type: application/json

{
    "name": "Nguyen Van B",
    "email": "b@gmail.com",
    "age": 22
}
```

Resource cần cập nhật:

```
/users/123
```

---

## Input

Giả sử dữ liệu cũ:

```
{
    "id": 123,
    "name": "Nguyen Van A",
    "email": "a@gmail.com",
    "age": 20
}
```

Client gửi:

```
{
    "name": "Nguyen Van B",
    "email": "b@gmail.com",
    "age": 22
}
```

Server thay thế dữ liệu Resource bằng dữ liệu mới theo định nghĩa của API.

### Output

```
{
    "id": 123,
    "name": "Nguyen Van B",
    "email": "b@gmail.com",
    "age": 22
}
```

Điểm quan trọng:

```
PUT
→ Thay thế/cập nhật toàn bộ Resource.

PATCH
→ Cập nhật một phần Resource.
```

---

# 2.6. PATCH

## Định nghĩa

**PATCH** được sử dụng để **cập nhật một phần Resource**.

Ví dụ User hiện tại:

```
{
    "id": 123,
    "name": "Nguyen Van A",
    "email": "a@gmail.com",
    "age": 20
}
```

Bạn chỉ muốn thay đổi tuổi.

Client gửi:

```
PATCH /users/123 HTTP/1.1
Host: example.com
Content-Type: application/json

{
    "age": 21
}
```

### Input

```
{
    "age": 21
}
```

Server giữ nguyên:

```
name
email
```

và chỉ thay đổi:

```
age
```

### Output

```
{
    "id": 123,
    "name": "Nguyen Van A",
    "email": "a@gmail.com",
    "age": 21
}
```

---

# 2.7. DELETE

## Định nghĩa

**DELETE** được sử dụng để yêu cầu Server xóa Resource được xác định bởi URI.

Ví dụ:

```
DELETE /users/123 HTTP/1.1
Host: example.com
```

Server có thể thực hiện:

```
DELETE FROM users
WHERE id = 123;
```

### Input

```
user_id = 123
```

### Database trước khi xóa

```
id  | name
123 | Nguyen Van A
124 | Nguyen Van B
```

### Database sau khi xóa

```
id  | name
124 | Nguyen Van B
```

### Response

Server có thể trả:

http

```
HTTP/1.1 204 No Content
```

Điều này có nghĩa Request thành công nhưng Response không có nội dung.

---

# 2.8. HEAD

**HEAD** tương tự GET về mặt Request Target và các điều kiện xử lý, nhưng Server **không gửi Response Body**.

Ví dụ:

```
HEAD /image.jpg HTTP/1.1
Host: example.com
```

Response:

```
HTTP/1.1 200 OK
Content-Type: image/jpeg
Content-Length: 250000
```

Không có:

```
image data
```

Client có thể sử dụng HEAD để kiểm tra thông tin về Resource mà không cần nhận nội dung Resource.

Ví dụ có thể kiểm tra:

```
Resource có tồn tại không?
Content-Type là gì?
Content-Length là bao nhiêu?
```

---

# 2.9. OPTIONS

**OPTIONS** được sử dụng để hỏi Server về các khả năng được hỗ trợ đối với Resource.

Ví dụ:

```
OPTIONS /users/123 HTTP/1.1
Host: example.com
```

Server có thể trả:

```
HTTP/1.1 204 No Content
Allow: GET, PUT, PATCH, DELETE
```

Ý nghĩa:

```
/users/123
```

cho phép:

```
GET
PUT
PATCH
DELETE
```

Header:

http

```
Allow: GET, PUT, PATCH, DELETE
```

cho Client biết những Method nào được phép đối với Resource đó.

---

# 2.10. TRACE

**TRACE** được sử dụng để kiểm tra đường đi của HTTP Request thông qua các thành phần trung gian.

TRACE ít được sử dụng trong API thông thường và thường bị vô hiệu hóa vì lý do bảo mật.

Ví dụ:

```
TRACE / HTTP/1.1
Host: example.com
```

---

# 2.11. CONNECT

**CONNECT** được sử dụng để yêu cầu Server thiết lập một tunnel đến một Server khác.

CONNECT thường liên quan đến **Proxy**.

Ví dụ:

```
CONNECT example.com:443 HTTP/1.1
Host: example.com:443
```

CONNECT không phải Method thường gặp khi lập trình REST API.

---

# 2.12. Bảng tổng hợp HTTP Methods

| Method  | Mục đích                                        | Request Body thường dùng?        | Ví dụ              |
| ------- | ----------------------------------------------- | -------------------------------- | ------------------ |
| GET     | Lấy dữ liệu                                     | Thường không                     | Lấy danh sách User |
| POST    | Gửi dữ liệu / tạo Resource / thực hiện thao tác | Có                               | Tạo User           |
| PUT     | Thay thế/cập nhật toàn bộ Resource              | Có                               | Cập nhật User      |
| PATCH   | Cập nhật một phần Resource                      | Có                               | Chỉ đổi email      |
| DELETE  | Xóa Resource                                    | Thường không                     | Xóa User           |
| HEAD    | Lấy thông tin Header tương ứng với GET          | Không                            | Kiểm tra Resource  |
| OPTIONS | Hỏi khả năng/Method được hỗ trợ                 | Không                            | Hỏi Server         |
| TRACE   | Kiểm tra đường đi của Request                   | Không                            | Debug              |
| CONNECT | Thiết lập tunnel qua Proxy                      | Không theo cách API thông thường | Proxy              |

---

# 3\. Safe Method

## 3.1. Định nghĩa

**Safe Method** là HTTP Method mà theo **ngữ nghĩa của Method**, Request không yêu cầu Server thay đổi trạng thái của Resource.

Các Safe Method là:

```
GET
HEAD
OPTIONS
TRACE
```

Trong đó các Method thường gặp nhất trong API là:

```
GET
HEAD
OPTIONS
```

Safe **không có nghĩa là Server tuyệt đối không thực hiện bất kỳ tác động phụ nào**.

Ví dụ:

http

```
GET /products
```

có thể làm Server:

```
ghi Log
cập nhật Metric
ghi thống kê
```

Nhưng những tác động đó không phải là mục đích/ngữ nghĩa chính của GET đối với Resource.

Điều quan trọng:

```
GET → không dùng để cập nhật/xóa Resource theo ngữ nghĩa của GET.
```

---

# 4\. Idempotent

## 4.1. Định nghĩa

**Idempotent** nghĩa là:

> Nếu gửi cùng một HTTP Request nhiều lần thì tác dụng của nhiều lần gửi đối với trạng thái của Server giống với tác dụng của việc gửi Request đó một lần.

Điều này **không có nghĩa Response của tất cả các lần gọi phải giống hệt nhau**.

Các Method Idempotent:

```
GET
HEAD
OPTIONS
PUT
DELETE
TRACE
```

Các Method phổ biến không Idempotent:

```
POST
PATCH
```

PATCH không được HTTP định nghĩa là Idempotent một cách mặc định; một API cụ thể có thể thiết kế một PATCH có tính Idempotent.

---

# 4.2. Ví dụ PUT

Giả sử:

```
User 123
age = 20
```

Gửi:

```
PUT /users/123

{
    "age": 25
}
```

Kết quả:

```
age = 25
```

Gửi lại Request giống hệt:

```
PUT /users/123

{
    "age": 25
}
```

Kết quả:

```
age = 25
```

Gửi 10 lần:

```
age = 25
```

Trạng thái cuối cùng vẫn là:

```
age = 25
```

→ PUT là **Idempotent**.

---

# 4.3. Ví dụ DELETE

Ban đầu:

```
User 123 tồn tại
```

Gửi:

http

```
DELETE /users/123
```

Kết quả:

```
User 123 bị xóa
```

Gửi lại:

http

```
DELETE /users/123
```

Kết quả cuối cùng vẫn là:

```
User 123 không tồn tại
```

Có thể xảy ra:

```
Lần 1 → 204 No Content
Lần 2 → 404 Not Found
```

Điều này **không làm DELETE mất tính Idempotent**, vì Idempotent xét chủ yếu đến **tác dụng lên trạng thái của Server**, không yêu cầu Status Code phải giống nhau.

---

# 4.4. POST không Idempotent

Giả sử:

```
POST /orders

{
    "product_id": 10,
    "quantity": 2
}
```

Server tạo:

```
Order 1
```

Nếu gửi lại Request:

```
POST /orders

{
    "product_id": 10,
    "quantity": 2
}
```

Server có thể tạo:

```
Order 2
```

Gửi 3 lần có thể tạo:

```
Order 1
Order 2
Order 3
```

→ POST thường **không Idempotent**.

Đây là lý do các hệ thống thanh toán/đặt hàng thường cần thêm cơ chế chống gửi trùng, ví dụ **Idempotency Key**.

---

# 5\. HTTP Request

## 5.1. Request là gì?

**HTTP Request** là thông điệp do **Client gửi đến Server** để yêu cầu Server thực hiện một hành động.

Ví dụ:

```
Browser
   |
   | HTTP Request
   v
Server
```

Một HTTP Request có cấu trúc khái quát:

```
Request Line
Headers

Request Body (optional)
```

Giữa Headers và Body có một dòng trống.

Ở HTTP/1.x, Request Line và Headers là dạng text. Với HTTP/2 và HTTP/3, chúng được biểu diễn bằng các cấu trúc binary framing tương ứng.

---

# 5.2. Request Line

Trong HTTP/1.1, Request Line thường có dạng:

```
METHOD /path HTTP/version
```

Ví dụ:

http

```
GET /products/10 HTTP/1.1
```

Trong đó:

```
GET          → HTTP Method
/products/10 → Request Target
HTTP/1.1     → HTTP Version
```

---

# 5.3. Request Headers

Headers chứa các thông tin bổ sung.

Ví dụ:

```
Host: example.com
User-Agent: Mozilla/5.0
Accept: application/json
Content-Type: application/json
Authorization: Bearer abc123
```

Một số Header quan trọng:

### Host

Xác định Host mà Client muốn truy cập.

http

```
Host: example.com
```

### User-Agent

Cho Server biết thông tin về Client.

http

```
User-Agent: Mozilla/5.0
```

### Accept

Cho biết Client có thể nhận loại dữ liệu nào.

http

```
Accept: application/json
```

### Content-Type

Cho biết kiểu dữ liệu của Request Body.

http

```
Content-Type: application/json
```

### Authorization

Thường chứa thông tin xác thực.

http

```
Authorization: Bearer abc123
```

---

# 5.4. Request Body

**Request Body** chứa dữ liệu mà Client gửi lên Server.

Ví dụ:

```
POST /users HTTP/1.1
Host: example.com
Content-Type: application/json

{
    "name": "Nguyen Van A",
    "age": 20
}
```

Phần:

```
{
    "name": "Nguyen Van A",
    "age": 20
}
```

chính là **Request Body**.

---

# 5.5. Ví dụ Request hoàn chỉnh

```
POST /users HTTP/1.1
Host: example.com
User-Agent: Mozilla/5.0
Accept: application/json
Content-Type: application/json
Authorization: Bearer abc123

{
    "name": "Nguyen Van A",
    "email": "a@gmail.com",
    "age": 20
}
```

Có thể chia thành:

```
Request Line
↓
POST /users HTTP/1.1

Headers
↓
Host: example.com
User-Agent: Mozilla/5.0
Accept: application/json
Content-Type: application/json
Authorization: Bearer abc123

Body
↓
{
    "name": "Nguyen Van A",
    "email": "a@gmail.com",
    "age": 20
}
```

---

# 6\. HTTP Response

## 6.1. Response là gì?

**HTTP Response** là thông điệp do **Server gửi lại Client** sau khi Server nhận và xử lý HTTP Request.

Mô hình:

```
Client
   |
   | Request
   v
Server
   |
   | xử lý
   v
Database
   |
   | dữ liệu
   v
Server
   |
   | Response
   v
Client
```

HTTP Response thường gồm:

```
Status Line
Headers

Response Body (optional)
```

Giữa Headers và Body có một dòng trống.

---

# 6.2. Status Line

Ví dụ:

http

```
HTTP/1.1 200 OK
```

Bao gồm:

```
HTTP/1.1 → HTTP Version
200      → HTTP Status Code
OK       → Reason Phrase
```

---

# 6.3. Response Headers

Headers chứa thông tin về Response.

Ví dụ:

```
Content-Type: application/json
Content-Length: 120
Cache-Control: no-cache
```

### Content-Type

Cho biết kiểu dữ liệu trong Response Body.

Ví dụ:

http

```
Content-Type: application/json
```

nghĩa là Body chứa JSON.

Hoặc:

http

```
Content-Type: text/html
```

nghĩa là Body chứa HTML.

Hoặc:

http

```
Content-Type: image/jpeg
```

nghĩa là Body chứa hình ảnh JPEG.

---

# 6.4. Response Body

Response Body là dữ liệu Server trả về.

Ví dụ:

```
{
    "id": 10,
    "name": "Laptop",
    "price": 20000000
}
```

Đây chính là Response Body.

---

# 7\. HTTP Status Code

## 7.1. Định nghĩa

**HTTP Status Code** là mã số do Server trả về để cho Client biết kết quả xử lý Request.

Status Code có 3 chữ số.

Có 5 nhóm chính:

```
1xx → Informational
2xx → Success
3xx → Redirection
4xx → Client Error
5xx → Server Error
```

---

# 7.2. 1xx – Informational

Nhóm `1xx` cung cấp thông tin tạm thời về tiến trình xử lý Request.

Ví dụ:

```
100 Continue
101 Switching Protocols
```

Nhóm `1xx` ít gặp khi lập trình API thông thường.

---

# 7.3. 2xx – Success

Cho biết Request đã được xử lý thành công.

Các mã quan trọng:

```
200 OK
201 Created
204 No Content
```

---

## 200 OK

Request thành công và Server có thể trả dữ liệu.

Ví dụ:

```
GET /users/10 HTTP/1.1
Host: example.com
```

Response:

```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 10,
    "name": "Nguyen Van A"
}
```

### Input

```
GET /users/10
```

### Output

```
{
    "id": 10,
    "name": "Nguyen Van A"
}
```

---

## 201 Created

Resource mới được tạo thành công.

Ví dụ:

```
POST /users HTTP/1.1
Content-Type: application/json

{
    "name": "Nguyen Van A"
}
```

Response:

```
HTTP/1.1 201 Created
Content-Type: application/json

{
    "id": 15,
    "name": "Nguyen Van A"
}
```

Ý nghĩa:

```
Request thành công
+
Resource mới đã được tạo
```

---

## 204 No Content

Request thành công nhưng Response không có nội dung.

Ví dụ:

```
DELETE /users/15 HTTP/1.1
Host: example.com
```

Response:

http

```
HTTP/1.1 204 No Content
```

`204 No Content` không có Response Body.

---

# 7.4. 3xx – Redirection

Nhóm `3xx` liên quan đến **Redirection**, tức Client cần thực hiện hành động tiếp theo hoặc truy cập Resource ở vị trí khác.

Ví dụ:

```
301 Moved Permanently
302 Found
304 Not Modified
```

Ví dụ:

```
HTTP/1.1 301 Moved Permanently
Location: https://example.com/new-page
```

Client biết rằng Resource đã chuyển sang:

```
https://example.com/new-page
```

---

# 7.5. 4xx – Client Error

Nhóm `4xx` thường biểu thị Request có vấn đề hoặc không thể được thực hiện dựa trên Request của Client.

Các mã rất quan trọng:

```
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
405 Method Not Allowed
```

---

## 400 Bad Request

Request không hợp lệ.

Ví dụ Server yêu cầu:

```
{
    "age": 20
}
```

nhưng Client gửi:

```
{
    "age": "abc"
}
```

Server có thể trả:

```
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
    "message": "age must be a number"
}
```

### Input

```
{
    "age": "abc"
}
```

### Output

```
400 Bad Request
```

---

## 401 Unauthorized

Request cần xác thực nhưng Client chưa xác thực hoặc thông tin xác thực không hợp lệ.

Ví dụ:

```
GET /profile HTTP/1.1
Host: example.com
```

Server yêu cầu xác thực nhưng Client không gửi thông tin xác thực hợp lệ.

Response:

```
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer
Content-Type: application/json

{
    "message": "Authentication required"
}
```

Phân biệt:

```
401 → Chưa xác thực / xác thực không hợp lệ
403 → Đã xác thực nhưng không có quyền
```

---

## 403 Forbidden

Client đã được xác thực nhưng **không có quyền thực hiện hành động**.

Ví dụ:

```
User bình thường
    ↓
GET /admin/users
```

Server kiểm tra:

```
Đã đăng nhập? → Có
Có quyền ADMIN? → Không
```

Response:

```
HTTP/1.1 403 Forbidden
Content-Type: application/json

{
    "message": "Access denied"
}
```

---

## 404 Not Found

Resource không tồn tại hoặc Server không muốn tiết lộ rằng Resource đó tồn tại.

Ví dụ:

http

```
GET /users/999999
```

Nếu User `999999` không tồn tại:

```
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "message": "User not found"
}
```

---

## 405 Method Not Allowed

**405 Method Not Allowed** xảy ra khi HTTP Method được sử dụng **không được Server cho phép đối với Resource cụ thể**.

Ví dụ Server hỗ trợ:

```
GET /users
POST /users
```

nhưng Client gửi:

http

```
DELETE /users
```

Server có thể trả:

```
HTTP/1.1 405 Method Not Allowed
Allow: GET, POST
Content-Type: application/json

{
    "message": "DELETE method is not allowed for /users"
}
```

### Input

```
Method: DELETE
URL: /users
```

### Server kiểm tra

```
/users hỗ trợ:
GET
POST
```

Client gửi:

```
DELETE
```

→ Không được phép.

### Output

```
405 Method Not Allowed
```

Header:

http

```
Allow: GET, POST
```

Header `Allow` cho Client biết những Method nào được phép.

---

# 7.6. 5xx – Server Error

Nhóm `5xx` biểu thị Server gặp lỗi khi xử lý Request.

Một số mã:

```
500 Internal Server Error
502 Bad Gateway
503 Service Unavailable
504 Gateway Timeout
```

---

## 500 Internal Server Error

Server gặp lỗi ngoài dự kiến.

Ví dụ:

```
Client
  |
  | GET /users
  v
Server
  |
  | SELECT ...
  v
Database
  |
  | Database connection error
  v
Server
```

Server có thể trả:

```
HTTP/1.1 500 Internal Server Error
Content-Type: application/json

{
    "message": "Internal server error"
}
```

Client biết rằng:

```
Request đã được gửi
nhưng Server không thể xử lý thành công
```

---

# 8\. Tổng hợp HTTP Status Code

| Status Code | Ý nghĩa               | Ví dụ                                     |
| ----------- | --------------------- | ----------------------------------------- |
| 200         | OK                    | GET thành công                            |
| 201         | Created               | POST tạo User thành công                  |
| 204         | No Content            | DELETE thành công, không có Body          |
| 301         | Moved Permanently     | URL đã chuyển vĩnh viễn                   |
| 302         | Found                 | Redirect                                  |
| 304         | Not Modified          | Resource chưa thay đổi                    |
| 400         | Bad Request           | Request không hợp lệ                      |
| 401         | Unauthorized          | Chưa xác thực                             |
| 403         | Forbidden             | Không có quyền                            |
| 404         | Not Found             | Resource không tồn tại/không được tiết lộ |
| 405         | Method Not Allowed    | Method không được phép                    |
| 500         | Internal Server Error | Server gặp lỗi                            |
| 502         | Bad Gateway           | Gateway/Proxy nhận Response không hợp lệ  |
| 503         | Service Unavailable   | Server tạm thời không phục vụ             |
| 504         | Gateway Timeout       | Gateway/Proxy chờ Server quá lâu          |

---

# 9\. Ví dụ tổng hợp Request → Server → Database → Response

Giả sử có API:

```
GET /products/10
```

Client gửi:

```
GET /products/10 HTTP/1.1
Host: example.com
Accept: application/json
```

Server nhận Request.

Server xử lý:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

Repository thực hiện:

```
SELECT *
FROM products
WHERE product_id = 10;
```

Database trả:

```
10 | Laptop Dell | 20000000
```

Server chuyển thành JSON:

```
{
    "id": 10,
    "name": "Laptop Dell",
    "price": 20000000
}
```

Server gửi Response:

```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 10,
    "name": "Laptop Dell",
    "price": 20000000
}
```

Client nhận:

```
Status Code = 200
Content-Type = application/json
Body = JSON
```

---

# 10\. HTTP trong Spring Boot

Đây là phần rất quan trọng nếu bạn đang học **Java Spring Boot**.

Ví dụ tạo API lấy User.

## Controller

```
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return new User(id, "Nguyen Van A");
    }
}
```

Khi Client gửi:

http

```
GET /users/10
```

Spring Boot gọi:

Java

```
getUser(10)
```

và trả:

```
{
    "id": 10,
    "name": "Nguyen Van A"
}
```

HTTP Response có thể là:

```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 10,
    "name": "Nguyen Van A"
}
```

---

# 11\. Ví dụ POST trong Spring Boot

```
@PostMapping
public User createUser(@RequestBody User user) {
    return userService.createUser(user);
}
```

Client gửi:

```
POST /users HTTP/1.1
Content-Type: application/json

{
    "name": "Nguyen Van A",
    "email": "a@gmail.com"
}
```

Spring Boot:

```
POST /users
     ↓
UserController
     ↓
UserService
     ↓
UserRepository
     ↓
MySQL
```

Database tạo User.

Server có thể trả:

```
HTTP/1.1 201 Created
Content-Type: application/json

{
    "id": 1,
    "name": "Nguyen Van A",
    "email": "a@gmail.com"
}
```

---

# 12\. HTTP và TCP

HTTP thuộc:

```
Application Layer
```

Trong khi **TCP (Transmission Control Protocol)** thuộc:

```
Transport Layer
```

Có thể hình dung:

```
Application Layer
        ↓
       HTTP
        ↓
Transport Layer
        ↓
       TCP
        ↓
   Network Layer
        ↓
        IP
```

HTTP tập trung vào:

```
Request
Response
Method
Header
Status Code
Body
```

TCP tập trung vào việc cung cấp cơ chế truyền dữ liệu tin cậy, có thứ tự và kiểm soát luồng/tắc nghẽn.

---

# 13\. HTTP/1.0

HTTP/1.0 là một phiên bản HTTP cũ.

Trong cách sử dụng phổ biến của HTTP/1.0, một Request thường sử dụng một TCP connection riêng và sau khi Response hoàn thành connection thường được đóng.

Ví dụ:

```
TCP Connection 1
    ↓
Request 1
    ↓
Response 1
    ↓
Close

TCP Connection 2
    ↓
Request 2
    ↓
Response 2
    ↓
Close
```

Điều này tạo ra thêm chi phí khi một website cần tải nhiều Resource.

---

# 14\. HTTP/1.1

HTTP/1.1 hỗ trợ **Persistent Connection**, cho phép tái sử dụng TCP connection cho nhiều Request/Response.

Thay vì:

```
TCP 1 → Request 1 → Response 1 → Close
TCP 2 → Request 2 → Response 2 → Close
TCP 3 → Request 3 → Response 3 → Close
```

có thể:

```
TCP Connection
      |
      ├── Request 1
      ├── Response 1
      ├── Request 2
      ├── Response 2
      └── Request 3
```

Giảm việc phải tạo TCP connection liên tục.

HTTP/1.1 cũng định nghĩa **pipelining**, nhưng tính năng này không được sử dụng rộng rãi trong thực tế hiện đại.

---

# 15\. HTTP/2

HTTP/2 cải thiện hiệu suất bằng nhiều cơ chế, trong đó quan trọng là **Multiplexing**.

Một TCP connection có thể chứa nhiều HTTP streams đồng thời.

```
             TCP Connection
                    |
       ┌────────────┼────────────┐
       ↓            ↓            ↓
    Stream 1     Stream 2     Stream 3
       ↓            ↓            ↓
   Request A    Request B    Request C
```

Nhiều Stream có thể được truyền đồng thời trên cùng một TCP connection.

HTTP/2 cũng sử dụng **Binary Framing**, giúp giao thức xử lý dữ liệu hiệu quả hơn so với cách biểu diễn text của HTTP/1.x.

Tuy nhiên, HTTP/2 vẫn sử dụng TCP nên có thể gặp vấn đề **TCP Head-of-Line (HOL) Blocking** ở tầng transport khi packet bị mất.

---

# 16\. HTTP/3

HTTP/3 sử dụng:

```
HTTP/3
   ↓
QUIC
   ↓
UDP
```

Trong khi:

```
HTTP/1.1 → TCP
HTTP/2   → TCP
```

HTTP/3 sử dụng **QUIC**, một giao thức Transport chạy trên UDP.

QUIC cung cấp các cơ chế như:

```
Reliable delivery
Multiplexed streams
Connection migration
Encryption
```

QUIC sử dụng TLS 1.3 để bảo vệ kết nối.

Điểm quan trọng cần nhớ:

```
HTTP/1.1 → TCP
HTTP/2   → TCP
HTTP/3   → QUIC → UDP
```

Vì vậy không nên nói:

> HTTP luôn chạy trên TCP.

Cách nói chính xác hơn là:

> HTTP/1.1 và HTTP/2 sử dụng TCP, còn HTTP/3 sử dụng QUIC trên UDP.

---

# 17\. Sơ đồ tổng hợp cần nhớ

```
                         HTTP
                          |
              ┌───────────┴───────────┐
              ↓                       ↓
            Request                Response
              |                       |
       ┌──────┼──────┐          ┌─────┼─────┐
       ↓      ↓      ↓          ↓     ↓     ↓
    Method  Headers Body     Status Headers Body
       |
 ┌─────┼─────┬─────┬─────┬─────┬─────┬─────────┐
 ↓     ↓     ↓     ↓     ↓     ↓     ↓         ↓
GET   POST   PUT PATCH DELETE HEAD OPTIONS    TRACE
```

---

# 18\. Cách nhớ HTTP Methods

Có thể nhớ theo CRUD:

```
CRUD
```

Trong đó:

```
C = Create
R = Read
U = Update
D = Delete
```

Mapping phổ biến:

```
Create → POST

Read   → GET

Update → PUT / PATCH

Delete → DELETE
```

Chi tiết:

```
GET
→ Tôi muốn lấy dữ liệu.

POST
→ Tôi muốn gửi dữ liệu để Server xử lý.

PUT
→ Tôi muốn thay thế/cập nhật toàn bộ Resource.

PATCH
→ Tôi chỉ muốn sửa một phần Resource.

DELETE
→ Tôi muốn xóa Resource.

HEAD
→ Tôi muốn lấy thông tin Header mà không lấy Body.

OPTIONS
→ Tôi muốn biết Server hỗ trợ những khả năng/Method nào.
```

---

# 19\. Cách nhớ Safe và Idempotent

## Safe

```
GET
HEAD
OPTIONS
TRACE
```

Ý nghĩa:

```
Không yêu cầu thay đổi trạng thái Resource
```

---

## Idempotent

```
GET
HEAD
OPTIONS
PUT
DELETE
TRACE
```

Ý nghĩa:

```
Gửi cùng Request nhiều lần
→ trạng thái cuối cùng tương đương với gửi một lần
```

Ví dụ:

```
PUT /users/10
age = 20
```

Gửi:

```
1 lần  → age = 20
10 lần → age = 20
```

---

# 20\. Cách nhớ Status Code

```
1xx → Informational
2xx → Success
3xx → Redirection
4xx → Client Error
5xx → Server Error
```

Đặc biệt cần nhớ:

```
200 → OK
201 → Created
204 → No Content

400 → Bad Request
401 → Unauthorized
403 → Forbidden
404 → Not Found
405 → Method Not Allowed

500 → Internal Server Error
```

Một chuỗi dễ nhớ:

```
400 → Request không hợp lệ
401 → Chưa xác thực
403 → Không có quyền
404 → Không tìm thấy
405 → Method không được phép
500 → Server lỗi
```

---

# 21\. Tổng kết toàn bộ

HTTP là:

```
HyperText Transfer Protocol
```

và hoạt động theo mô hình:

```
Client
   |
   | HTTP Request
   v
Server
   |
   | HTTP Response
   v
Client
```

Một **Request** thường có:

```
Request Line
Headers
Body
```

Một **Response** thường có:

```
Status Line
Headers
Body
```

HTTP Methods quan trọng:

```
GET
POST
PUT
PATCH
DELETE
HEAD
OPTIONS
```

Trong đó:

```
GET     → lấy dữ liệu
POST    → gửi dữ liệu / tạo Resource / thực hiện thao tác
PUT     → cập nhật/thay thế toàn bộ
PATCH   → cập nhật một phần
DELETE  → xóa
HEAD    → lấy Header, không có Body
OPTIONS → hỏi khả năng được hỗ trợ
```

Safe Methods:

```
GET
HEAD
OPTIONS
TRACE
```

Idempotent Methods:

```
GET
HEAD
OPTIONS
PUT
DELETE
TRACE
```

Status Code:

```
2xx → thành công
3xx → chuyển hướng
4xx → lỗi liên quan đến Request/Client
5xx → lỗi Server
```

Các phiên bản HTTP:

```
HTTP/1.0 → TCP Connection thường được tạo riêng cho Request
HTTP/1.1 → Persistent Connection
HTTP/2   → Multiplexing + Binary Framing trên TCP
HTTP/3   → HTTP trên QUIC/UDP
```

HTTPS:

```
HTTPS
  ↓
HTTP + TLS
```

Và khi làm Backend với **Spring Boot**:

```
Frontend / Postman
        |
        | HTTP Request
        ↓
@RestController
        ↓
@Service
        ↓
@Repository
        ↓
MySQL
        ↓
@Repository
        ↓
@Service
        ↓
@RestController
        |
        | HTTP Response
        ↓
Frontend / Postman
```

Đây là nền tảng cần nắm trước khi học tiếp:

```
HTTP
 ↓
API
 ↓
REST API
 ↓
JSON
 ↓
Spring Boot Controller
 ↓
Service
 ↓
Repository
 ↓
Database
 ↓
DI
 ↓
IoC
```

**Lưu ý quan trọng nhất khi học:** đừng học thuộc đơn thuần `GET = lấy`, `POST = thêm`, `PUT = sửa`... Hãy hiểu rằng **HTTP Method mô tả ngữ nghĩa của hành động**, còn API cụ thể sẽ thiết kế Resource và cách xử lý dựa trên những ngữ nghĩa đó.

# API và REST API

---

# 1\. API là gì?

## 1.1. Định nghĩa

**API** là viết tắt của **Application Programming Interface** – **Giao diện lập trình ứng dụng**.

API là một tập hợp các **quy tắc và cách thức** cho phép các phần mềm khác nhau **giao tiếp và trao đổi dữ liệu với nhau**.

Hiểu đơn giản:

> **API là cầu nối giúp một ứng dụng yêu cầu và sử dụng chức năng hoặc dữ liệu của một ứng dụng/hệ thống khác mà không cần biết cách hệ thống bên trong được xây dựng như thế nào.**

Ví dụ:

```
Frontend
   |
   | API Request
   ↓
Backend
   |
   | xử lý
   ↓
Database
```

Frontend không cần biết:

```
Database nằm ở đâu?
SQL được viết thế nào?
Backend xử lý dữ liệu ra sao?
```

Frontend chỉ cần biết:

```
Gửi Request như thế nào?
Gửi dữ liệu gì?
API nào được gọi?
Nhận Response có cấu trúc gì?
```

---

# 2\. Ví dụ thực tế về API

Giả sử có một ứng dụng bán hàng.

Frontend muốn lấy danh sách sản phẩm.

Frontend gọi:

```
GET /api/products
```

Backend nhận Request và xử lý:

```
Frontend
   |
   | GET /api/products
   ↓
Backend
   |
   | SELECT * FROM products
   ↓
Database
   |
   | Products
   ↓
Backend
   |
   | JSON
   ↓
Frontend
```

Frontend nhận:

```
[
    {
        "id": 1,
        "name": "Laptop",
        "price": 20000000
    },
    {
        "id": 2,
        "name": "Mouse",
        "price": 500000
    }
]
```

Ở đây:

```
/api/products
```

là một **API Endpoint**.

---

# 3\. API Endpoint là gì?

## 3.1. Định nghĩa

**Endpoint** là một địa chỉ cụ thể mà Client có thể sử dụng để giao tiếp với API nhằm thực hiện một chức năng hoặc thao tác trên một Resource.

Ví dụ:

```
GET    /api/products
GET    /api/products/10
POST   /api/products
PUT    /api/products/10
DELETE /api/products/10
```

Mỗi Endpoint xác định:

```
Resource nào?
Thao tác gì?
Dữ liệu đầu vào là gì?
Dữ liệu đầu ra là gì?
```

Ví dụ:

```
GET /api/products/10
```

có nghĩa:

> Lấy thông tin Product có ID = 10.

---

# 4\. API hoạt động như thế nào?

Một API thường hoạt động theo quy trình:

```
Client
   |
   | 1. Gửi Request
   ↓
API Endpoint
   |
   | 2. Controller nhận Request
   ↓
Service
   |
   | 3. Xử lý Business Logic
   ↓
Repository
   |
   | 4. Truy cập Database
   ↓
Database
   |
   | 5. Trả dữ liệu
   ↓
Repository
   ↓
Service
   ↓
Controller
   |
   | 6. Tạo Response
   ↓
Client
```

Trong **Spring Boot**, mô hình thường gặp:

```
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

---

# 5\. API không nhất thiết phải là REST API

Đây là điểm rất quan trọng.

**API** là khái niệm rộng.

Có nhiều cách xây dựng API:

```
API
├── REST API
├── SOAP API
├── GraphQL API
├── gRPC API
└── WebSocket API
```

Do đó:

```
REST API ⊂ API
```

Có thể hiểu:

> **REST API là một loại API được thiết kế theo các nguyên tắc của REST.**

Không phải:

> API = REST API.

---

# 6\. REST API là gì?

## 6.1. REST là gì?

**REST** là viết tắt của **Representational State Transfer**.

REST là một **Architectural Style (Kiểu kiến trúc)** dùng để thiết kế các hệ thống phân tán, đặc biệt phổ biến khi xây dựng Web API.

Vì vậy:

> **REST không phải là một Protocol.**

HTTP mới là Protocol mà REST API thường sử dụng.

---

# 6.2. REST API là gì?

**REST API** là API được thiết kế dựa trên các nguyên tắc và ràng buộc của **REST**.

REST API thường:

- sử dụng HTTP
- tổ chức hệ thống xoay quanh **Resource**
- sử dụng URI để xác định Resource
- sử dụng HTTP Methods để biểu diễn thao tác
- thường sử dụng JSON để trao đổi dữ liệu
- là **Stateless** ở cấp giao tiếp
- sử dụng HTTP Status Code để biểu diễn kết quả

Ví dụ:

```
/api/products
/api/products/10
/api/users
/api/users/20
/api/orders
/api/orders/100
```

---

# 7\. Resource trong REST API

## 7.1. Resource là gì?

**Resource** là đối tượng hoặc dữ liệu mà API cung cấp khả năng truy cập hoặc thao tác.

Ví dụ hệ thống bán hàng có:

```
Product
User
Order
Category
Payment
```

Có thể biểu diễn thành Resource:

```
/products
/users
/orders
/categories
/payments
```

Một Resource cụ thể:

```
/products/10
```

có thể đại diện cho:

```
Product có ID = 10
```

---

# 8\. Thiết kế URL trong REST API

REST API thường sử dụng **Noun (Danh từ)** để biểu diễn Resource thay vì dùng động từ trong URL.

### Không nên thiết kế:

```
/getProducts
/createProduct
/updateProduct
/deleteProduct
```

Vì hành động đã được biểu diễn bằng HTTP Method.

### Thiết kế theo REST:

```
GET    /products
POST   /products
GET    /products/10
PUT    /products/10
PATCH  /products/10
DELETE /products/10
```

Ở đây:

```
/products
```

là Resource.

Còn:

```
GET
POST
PUT
PATCH
DELETE
```

xác định hành động/ngữ nghĩa đối với Resource.

---

# 9\. Collection và Individual Resource

Đây là cách rất quan trọng để hiểu REST URL.

## Collection

```
/products
```

đại diện cho tập hợp Products.

Ví dụ:

http

```
GET /products
```

→ Lấy danh sách Product.

---

## Individual Resource

```
/products/10
```

đại diện cho một Product cụ thể.

Ví dụ:

http

```
GET /products/10
```

→ Lấy Product có ID `10`.

Có thể hình dung:

```
/products
    |
    ├── /1
    ├── /2
    ├── /3
    └── /10
```

---

# 10\. REST API và CRUD

**CRUD** là viết tắt của:

```
C = Create
R = Read
U = Update
D = Delete
```

REST API thường ánh xạ CRUD với HTTP Methods như sau:

| CRUD   | HTTP Method | Endpoint       | Ý nghĩa                      |
| ------ | ----------- | -------------- | ---------------------------- |
| Create | POST        | `/products`    | Tạo Product                  |
| Read   | GET         | `/products`    | Lấy danh sách                |
| Read   | GET         | `/products/10` | Lấy Product 10               |
| Update | PUT         | `/products/10` | Cập nhật/thay thế Product 10 |
| Update | PATCH       | `/products/10` | Cập nhật một phần            |
| Delete | DELETE      | `/products/10` | Xóa Product 10               |

Điểm cần hiểu:

```
/products
```

không cần:

```
/createProduct
/getProducts
```

vì Method đã thể hiện hành động.

---

# 11\. Ví dụ REST API hoàn chỉnh

Giả sử có Resource:

```
Product
```

## 11.1. Lấy danh sách Product

### Request

http

```
GET /api/products
```

### Output

```
[
    {
        "id": 1,
        "name": "Laptop",
        "price": 20000000
    },
    {
        "id": 2,
        "name": "Mouse",
        "price": 500000
    },
    {
        "id": 3,
        "name": "Keyboard",
        "price": 1000000
    }
]
```

---

# 11.2. Lấy một Product

### Input

```
id = 2
```

### Request

http

```
GET /api/products/2
```

### Output

```
{
    "id": 2,
    "name": "Mouse",
    "price": 500000
}
```

---

# 11.3. Tạo Product

### Input

```
{
    "name": "Monitor",
    "price": 5000000
}
```

### Request

```
POST /api/products
Content-Type: application/json

{
    "name": "Monitor",
    "price": 5000000
}
```

### Output

```
{
    "id": 4,
    "name": "Monitor",
    "price": 5000000
}
```

---

# 11.4. Cập nhật Product

Giả sử Product hiện tại:

```
{
    "id": 4,
    "name": "Monitor",
    "price": 5000000
}
```

Client muốn đổi giá.

### Input

```
{
    "price": 5500000
}
```

Nếu sử dụng PATCH:

```
PATCH /api/products/4
Content-Type: application/json

{
    "price": 5500000
}
```

### Output

```
{
    "id": 4,
    "name": "Monitor",
    "price": 5500000
}
```

---

# 11.5. Xóa Product

### Input

```
id = 4
```

### Request

http

```
DELETE /api/products/4
```

### Output

```
204 No Content
```

---

# 12\. Path Parameter

**Path Parameter** là dữ liệu được đặt trực tiếp trong URL Path để xác định một Resource cụ thể.

Ví dụ:

```
/products/10
```

Trong đó:

```
10
```

là Path Parameter.

Trong Spring Boot:

```
@GetMapping("/products/{id}")
public Product getProduct(@PathVariable int id) {
    ...
}
```

Request:

http

```
GET /products/10
```

Spring Boot nhận:

```
id = 10
```

---

# 13\. Query Parameter

**Query Parameter** được sử dụng để truyền các điều kiện, bộ lọc, sắp xếp, phân trang...

Ví dụ:

```
/products?name=laptop
```

Hoặc:

```
/products?minPrice=1000000&maxPrice=10000000
```

Hoặc:

```
/products?page=1&size=20
```

Ví dụ:

http

```
GET /products?name=laptop&page=1&size=10
```

Có thể hiểu:

```
/products
```

→ Resource.

```
name=laptop
page=1
size=10
```

→ Các Query Parameters.

---

# 14\. Path Parameter và Query Parameter

| Loại            | Ví dụ              | Mục đích                     |
| --------------- | ------------------ | ---------------------------- |
| Path Parameter  | `/products/10`     | Xác định Resource cụ thể     |
| Query Parameter | `/products?page=1` | Lọc, tìm kiếm, phân trang... |

Ví dụ:

```
GET /products/10
```

→ Lấy Product có ID `10`.

```
GET /products?name=laptop
```

→ Tìm các Product có tên liên quan đến `laptop`.

---

# 15\. JSON trong REST API

REST API thường sử dụng **JSON (JavaScript Object Notation)** để trao đổi dữ liệu.

Ví dụ:

```
{
    "id": 10,
    "name": "Laptop",
    "price": 20000000
}
```

JSON có dạng:

```
Object
{
    "key": "value"
}
```

Ví dụ:

```
{
    "name": "Laptop",
    "price": 20000000
}
```

Trong đó:

```
name  → key
Laptop → value

price → key
20000000 → value
```

JSON cũng có thể biểu diễn Array:

```
[
    {
        "id": 1,
        "name": "Laptop"
    },
    {
        "id": 2,
        "name": "Mouse"
    }
]
```

---

# 16\. Request Body và Response Body trong REST API

Phần này đã được giải thích ở HTTP, nên ở REST API chỉ cần nhớ cách sử dụng.

Ví dụ Client tạo Product:

```
POST /products
Content-Type: application/json

{
    "name": "Laptop",
    "price": 20000000
}
```

JSON:

```
{
    "name": "Laptop",
    "price": 20000000
}
```

là **Request Body**.

Server trả:

```
{
    "id": 1,
    "name": "Laptop",
    "price": 20000000
}
```

là **Response Body**.

---

# 17\. Authentication trong REST API

REST API thường cần xác định:

> Client là ai?

Đây là **Authentication (Xác thực)**.

Ví dụ Client đăng nhập:

```
username
password
```

Server xác thực thành công và có thể cấp:

```
Access Token
```

Sau đó Client gửi Token trong các Request cần xác thực.

Ví dụ:

```
GET /api/users/me
Authorization: Bearer eyJhbGciOi...
```

Server kiểm tra Token.

```
Token hợp lệ
      ↓
Cho phép xử lý

Token không hợp lệ
      ↓
401 Unauthorized
```

---

# 18\. Authorization trong REST API

**Authorization (Phân quyền)** trả lời câu hỏi:

> User này có quyền thực hiện hành động này không?

Ví dụ:

```
ADMIN
USER
```

API:

```
DELETE /api/users/10
```

Nếu:

```
ADMIN → Có quyền
USER  → Không có quyền
```

User thường có thể nhận:

```
403 Forbidden
```

Cần phân biệt:

```
Authentication
→ Bạn là ai?

Authorization
→ Bạn được phép làm gì?
```

---

# 19\. REST API Stateless

REST kế thừa nguyên tắc **Stateless**.

Điều đó có nghĩa mỗi Request phải chứa đủ thông tin cần thiết để Server xử lý Request đó.

Ví dụ Client sử dụng Access Token:

```
GET /api/profile
Authorization: Bearer abc123
```

Request tiếp theo cũng phải cung cấp thông tin xác thực cần thiết:

```
GET /api/orders
Authorization: Bearer abc123
```

Server không nên phụ thuộc vào việc:

```
Request trước đó là gì?
```

để hiểu Request hiện tại.

Điều này giúp hệ thống dễ mở rộng bằng nhiều Server:

```
             Load Balancer
                   |
        ┌──────────┼──────────┐
        ↓          ↓          ↓
     Server 1   Server 2   Server 3
```

Request nào cũng có thể được chuyển đến Server phù hợp nếu Server có đủ thông tin để xử lý.

---

# 20\. Versioning trong REST API

Khi API phát triển, cấu trúc API có thể thay đổi.

Ví dụ phiên bản:

```
/api/v1/products
/api/v2/products
```

Trong đó:

```
v1 → Version 1
v2 → Version 2
```

Ví dụ:

http

```
GET /api/v1/products
```

và:

http

```
GET /api/v2/products
```

Có thể có Response khác nhau.

Versioning giúp tránh việc thay đổi API mới làm hỏng các Client đang sử dụng API cũ.

---

# 21\. Pagination

Khi Database có hàng triệu Product, không nên trả toàn bộ dữ liệu trong một Response.

Thay vào đó sử dụng **Pagination (Phân trang)**.

Ví dụ:

http

```
GET /api/products?page=1&size=10
```

Ý nghĩa:

```
page = 1
size = 10
```

→ Lấy 10 Product của trang 1.

Response có thể:

```
{
    "content": [
        {
            "id": 1,
            "name": "Laptop"
        },
        {
            "id": 2,
            "name": "Mouse"
        }
    ],
    "page": 1,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10
}
```

---

# 22\. Filtering

**Filtering** là lọc dữ liệu theo điều kiện.

Ví dụ:

http

```
GET /api/products?category=laptop
```

Hoặc:

http

```
GET /api/products?minPrice=10000000&maxPrice=30000000
```

Server sử dụng các điều kiện này để truy vấn Database.

Ví dụ:

```
SELECT *
FROM products
WHERE price >= 10000000
  AND price <= 30000000;
```

---

# 23\. Sorting

**Sorting** là sắp xếp dữ liệu.

Ví dụ:

http

```
GET /api/products?sort=price
```

Hoặc:

http

```
GET /api/products?sort=price,desc
```

Có thể hiểu:

```
sort=price
→ Sắp xếp theo price.

sort=price,desc
→ Sắp xếp price giảm dần.
```

---

# 24\. REST API và Spring Boot

Trong Spring Boot, REST API thường được xây dựng bằng:

```
@RestController
@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@PatchMapping
@DeleteMapping
```

Ví dụ:

```
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.delete(id);
    }
}
```

API sẽ có:

```
GET    /api/products
GET    /api/products/{id}
POST   /api/products
DELETE /api/products/{id}
```

---

# 25\. Luồng xử lý REST API trong Spring Boot

Ví dụ:

http

```
GET /api/products/10
```

Luồng xử lý:

```
Client
   |
   | GET /api/products/10
   ↓
ProductController
   |
   | getProduct(10)
   ↓
ProductService
   |
   | getById(10)
   ↓
ProductRepository
   |
   | Query Database
   ↓
MySQL
   |
   | Product 10
   ↓
Repository
   ↓
Service
   ↓
Controller
   |
   | JSON
   ↓
Client
```

Đây chính là lý do khi học **Spring Boot Backend**, bạn thường gặp:

```
Controller
Service
Repository
Entity
DTO
Database
```

---

# 26\. DTO trong REST API

**DTO** là viết tắt của **Data Transfer Object**.

DTO được sử dụng để định nghĩa dữ liệu được truyền giữa các thành phần của hệ thống, đặc biệt thường dùng giữa Client và Backend.

Ví dụ Database có Entity:

```
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
}
```

Không nên trả trực tiếp Password về Client.

Có thể tạo:

```
public class UserResponseDTO {

    private int id;
    private String username;
    private String email;
}
```

Response:

```
{
    "id": 1,
    "username": "admin",
    "email": "admin@gmail.com"
}
```

Không trả:

```
{
    "password": "123456"
}
```

DTO giúp kiểm soát dữ liệu được gửi vào và trả ra.

---

# 27\. API Contract

**API Contract** là thỏa thuận về cách Client và Server giao tiếp với nhau.

Một API Contract thường mô tả:

```
Endpoint
HTTP Method
Request Parameters
Request Body
Authentication
Response
Status Code
Error Response
```

Ví dụ:

```
GET /api/products/{id}
```

### Request

```
GET /api/products/10
```

### Response thành công

```
{
    "id": 10,
    "name": "Laptop",
    "price": 20000000
}
```

### Response khi không tìm thấy

```
{
    "message": "Product not found"
}
```

Contract giúp Frontend và Backend thống nhất cách làm việc.

---

# 28\. Error Response

API không chỉ trả dữ liệu khi thành công mà còn cần trả thông tin lỗi rõ ràng.

Ví dụ:

http

```
GET /api/products/999
```

Response:

```
{
    "status": 404,
    "message": "Product not found",
    "path": "/api/products/999"
}
```

Client có thể dựa vào:

```
status
message
```

để xử lý lỗi.

Trong Spring Boot có thể sử dụng:

```
@RestControllerAdvice
@ExceptionHandler
```

để xử lý lỗi tập trung.

---

# 29\. REST API tốt nên có cấu trúc như thế nào?

Ví dụ một hệ thống bán hàng:

```
/api/v1/products
/api/v1/products/{id}

/api/v1/users
/api/v1/users/{id}

/api/v1/orders
/api/v1/orders/{id}

/api/v1/categories
/api/v1/categories/{id}
```

Thay vì:

```
/api/getAllProducts
/api/createProduct
/api/updateProduct
/api/deleteProduct
```

REST API nên tập trung vào **Resource**, còn hành động được thể hiện thông qua HTTP Method.

---

# 30\. API và REST API khác nhau như thế nào?

| API                                         | REST API                                     |
| ------------------------------------------- | -------------------------------------------- |
| Khái niệm rộng                              | Một kiểu API                                 |
| Có nhiều cách triển khai                    | Dựa trên Architectural Style REST            |
| Có thể dùng nhiều Protocol/cơ chế khác nhau | Thường sử dụng HTTP                          |
| Có thể là SOAP, GraphQL, gRPC...            | Thường sử dụng Resource + URI + HTTP Methods |
| Không bắt buộc JSON                         | Thường sử dụng JSON                          |
| Không nhất thiết tuân theo REST Constraints | Được thiết kế theo REST Constraints          |

Quan hệ:

```
API
│
├── REST API
├── SOAP API
├── GraphQL API
├── gRPC API
└── ...
```

---

# 31\. REST API cần nhớ những nguyên tắc chính

REST có một số **Architectural Constraints** quan trọng.

## 1\. Client–Server

Client và Server có trách nhiệm tương đối độc lập.

```
Client
  ↕
Server
```

---

## 2\. Stateless

Mỗi Request phải chứa đủ thông tin cần thiết để Server xử lý.

```
Request 1 → độc lập
Request 2 → độc lập
Request 3 → độc lập
```

---

## 3\. Cacheable

Response có thể được Cache khi phù hợp.

Điều này giúp giảm số lần phải xử lý Request và cải thiện hiệu suất.

---

## 4\. Uniform Interface

Các thành phần giao tiếp với nhau thông qua một Interface thống nhất.

Đây là một trong những đặc điểm quan trọng của REST.

Trong Web API thường thể hiện qua:

```
Resource
URI
HTTP Methods
Representation
```

---

## 5\. Layered System

Client không nhất thiết biết Request đang được xử lý trực tiếp bởi Server nào.

Có thể có:

```
Client
   ↓
Load Balancer
   ↓
API Gateway
   ↓
Application Server
   ↓
Database
```

Các tầng có thể được thêm vào mà Client không nhất thiết phải biết chi tiết bên trong.

---

## 6\. Code-on-Demand

Đây là Constraint tùy chọn của REST.

Server có thể gửi Code cho Client để Client thực thi.

Trong các REST API Backend hiện đại, Constraint này thường không phải trọng tâm.

---

# 32\. REST API – Sơ đồ tổng hợp

```
                         REST API
                            |
              ┌─────────────┴─────────────┐
              ↓                           ↓
          Resource                    Endpoint
              |                           |
       /products                    /api/products
       /users                       /api/users
       /orders                      /api/orders
              |
              ↓
       HTTP Methods
              |
    ┌─────────┼─────────┐
    ↓         ↓         ↓
   GET       POST      PUT/PATCH
                         |
                      DELETE
              |
              ↓
             JSON
              |
       ┌──────┴──────┐
       ↓             ↓
    Request       Response
       |             |
       ↓             ↓
    Input         Output
```

---

# 33\. API → REST API → Spring Boot

Khi học Backend, có thể hiểu theo thứ tự:

```
API
│
├── API là gì?
│
├── Endpoint là gì?
│
├── Request/Response
│
└── API Contract
        ↓
REST
│
├── REST là gì?
├── Resource
├── URI
├── Stateless
├── Uniform Interface
└── REST Constraints
        ↓
REST API
│
├── Resource
├── Endpoint
├── HTTP Methods
├── JSON
├── Authentication
├── Authorization
├── Pagination
├── Filtering
├── Sorting
├── Versioning
└── Error Handling
        ↓
Spring Boot
│
├── @RestController
├── @RequestMapping
├── @GetMapping
├── @PostMapping
├── @PutMapping
├── @PatchMapping
├── @DeleteMapping
├── @RequestBody
├── @PathVariable
├── @RequestParam
├── DTO
├── Service
└── Repository
```

---

# 34\. Ví dụ thực tế hoàn chỉnh

Giả sử xây dựng **Product API**.

## Endpoint 1: Lấy danh sách

http

```
GET /api/v1/products
```

Output:

```
[
    {
        "id": 1,
        "name": "Laptop",
        "price": 20000000
    },
    {
        "id": 2,
        "name": "Mouse",
        "price": 500000
    }
]
```

---

## Endpoint 2: Lấy Product theo ID

http

```
GET /api/v1/products/1
```

Output:

```
{
    "id": 1,
    "name": "Laptop",
    "price": 20000000
}
```

---

## Endpoint 3: Tạo Product

### Input

```
{
    "name": "Keyboard",
    "price": 1000000
}
```

Request:

```
POST /api/v1/products
Content-Type: application/json
```

Output:

```
{
    "id": 3,
    "name": "Keyboard",
    "price": 1000000
}
```

---

## Endpoint 4: Cập nhật Product

```
PATCH /api/v1/products/3
Content-Type: application/json
```

Input:

```
{
    "price": 1200000
}
```

Output:

```
{
    "id": 3,
    "name": "Keyboard",
    "price": 1200000
}
```

---

## Endpoint 5: Xóa Product

http

```
DELETE /api/v1/products/3
```

Output:

```
204 No Content
```

---

# 35\. Những điều cần phân biệt

### API và REST API

```
API
→ Khái niệm chung.

REST API
→ Một loại API được thiết kế theo REST.
```

### REST và HTTP

```
REST
→ Architectural Style.

HTTP
→ Protocol.
```

### Endpoint và Resource

```
Resource
→ Đối tượng dữ liệu.

Endpoint
→ Địa chỉ cụ thể để Client giao tiếp với API.
```

Ví dụ:

```
Resource:
Product

Endpoint:
GET /api/products/10
```

### Authentication và Authorization

```
Authentication
→ Bạn là ai?

Authorization
→ Bạn có quyền làm gì?
```

### Path Parameter và Query Parameter

```
/products/10
→ Path Parameter
→ xác định Product cụ thể.

 /products?name=laptop
→ Query Parameter
→ tìm kiếm/lọc dữ liệu.
```

# DESIGN PATTERN TRONG JAVA

## Từ Design Pattern → SOLID → DIP → IoC → DI → Spring

---

# PHẦN I. DESIGN PATTERN

## 1\. Design Pattern là gì?

**Design Pattern** là một **mẫu thiết kế** mô tả cách giải quyết một vấn đề thiết kế thường xuyên xuất hiện trong phần mềm.

Design Pattern **không phải**:

```
Một thư viện
Một Framework
Một đoạn code bắt buộc phải copy
Một thuật toán
```

Mà là:

```
Problem
   ↓
Context
   ↓
Solution Pattern
   ↓
Có thể áp dụng lại
```

Điểm quan trọng:

> Design Pattern cung cấp **một cách tư duy và cấu trúc giải quyết vấn đề**, chứ không cung cấp một đoạn code cố định.

---

# 2\. Tại sao cần Design Pattern?

Khi chương trình nhỏ:

```
Class A
Class B
Class C
```

mọi thứ tương đối dễ quản lý.

Nhưng khi hệ thống lớn:

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

và:

```
Payment
 ├── Momo
 ├── VnPay
 ├── Paypal
 └── CreditCard
```

thì sẽ xuất hiện:

- Coupling
- Code duplication
- Khó mở rộng
- Khó bảo trì
- Khó testing
- Khó thay đổi implementation

Design Pattern cung cấp những **mẫu giải quyết đã được sử dụng nhiều lần** cho các vấn đề kiểu này.

---

# 3\. Design Pattern không phải để dùng mọi lúc

Đây là điểm rất quan trọng.

Không phải:

```
Có Design Pattern
        ↓
Phải dùng Design Pattern
```

Mà:

```
Có vấn đề thiết kế
        ↓
Phân tích vấn đề
        ↓
Xác định nguyên nhân
        ↓
Chọn Pattern phù hợp
```

Nếu code đơn giản:

Java

```
User user = new User();
```

thì không cần cố đưa Factory, Builder, Singleton... vào.

> **Pattern phải giải quyết vấn đề, không phải tạo thêm vấn đề.**

---

# 4\. GoF Design Patterns

**GoF** = **Gang of Four**.

Đây là tên gọi của bốn tác giả cuốn:

> _Design Patterns: Elements of Reusable Object-Oriented Software_

Ta có thể tổng hợp Design Patterns thành 3 nhóm

```
Design Patterns
│
├── Creational Patterns
│
├── Structural Patterns
│
└── Behavioral Patterns
```

---

# PHẦN II. CREATIONAL DESIGN PATTERNS

Creational Patterns tập trung vào:

> **Cách tạo Object.**

Có 5 Pattern:

```
Creational
│
├── Singleton
├── Factory Method
├── Abstract Factory
├── Builder
└── Prototype
```

---

# 5\. Singleton Pattern

## Mục đích

Đảm bảo một Class chỉ có một Instance theo cơ chế Singleton.

Ví dụ:

```
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

Sử dụng:

```
Singleton a = Singleton.getInstance();
Singleton b = Singleton.getInstance();

System.out.println(a == b);
```

Output:

```
true
```

### Khi nào dùng?

Ví dụ:

```
Configuration
Logger
Một số resource dùng chung
```

### Nhưng cần lưu ý

Trong ứng dụng Spring, việc quản lý object theo Singleton Scope thường được **IoC Container đảm nhiệm**, nên không nên mặc định tự viết Singleton bằng `static`.

---

# 6\. Factory Method

Mục đích:

> Tách việc **tạo Object** khỏi code sử dụng Object.

Ví dụ:

```
interface Payment {
    void pay();
}
```

```
class MomoPayment implements Payment {

    public void pay() {
        System.out.println("Momo");
    }
}
```

```
class VnPayPayment implements Payment {

    public void pay() {
        System.out.println("VNPay");
    }
}
```

Factory:

```
class PaymentFactory {

    public static Payment create(String type) {

        if (type.equals("momo")) {
            return new MomoPayment();
        }

        if (type.equals("vnpay")) {
            return new VnPayPayment();
        }

        throw new IllegalArgumentException();
    }
}
```

Sử dụng:

```
Payment payment =
    PaymentFactory.create("momo");

payment.pay();
```

---

# 7\. Abstract Factory

Factory Method thường tạo một Object.

**Abstract Factory** dùng để tạo **một họ các Object liên quan**.

Ví dụ:

```
GUI
│
├── Windows
│   ├── WindowsButton
│   └── WindowsCheckbox
│
└── Mac
    ├── MacButton
    └── MacCheckbox
```

---

# 8\. Builder Pattern

Dùng khi Object có nhiều thuộc tính, đặc biệt khi constructor có quá nhiều tham số.

Thay vì:

```
User user = new User(
    1,
    "Phuong",
    "email",
    20,
    "Hanoi",
    ...
);
```

Builder:

```
User user = new UserBuilder()
        .id(1)
        .name("Phuong")
        .email("email")
        .age(20)
        .address("Hanoi")
        .build();
```

Builder rất phổ biến trong Java.

---

# 9\. Prototype Pattern

Tạo Object mới bằng cách **copy một Object có sẵn**.

```
Original Object
      ↓
    clone
      ↓
New Object
```

Dùng khi việc tạo Object từ đầu tốn kém hoặc phức tạp.

---

# PHẦN III. STRUCTURAL DESIGN PATTERNS

Structural Patterns tập trung vào:

> **Cách kết hợp các Class và Object thành cấu trúc lớn hơn.**

Có 7 Pattern:

```
Structural
│
├── Adapter
├── Bridge
├── Composite
├── Decorator
├── Facade
├── Flyweight
└── Proxy
```

---

# 10\. Adapter Pattern

Dùng khi hai interface không tương thích nhưng cần làm việc với nhau.

```
Client
  ↓
Adapter
  ↓
Old System
```

Ví dụ hệ thống cũ:

```
class OldPayment {
    void payOldWay() {}
}
```

Hệ thống mới cần:

```
interface Payment {
    void pay();
}
```

Adapter:

```
class PaymentAdapter implements Payment {

    private OldPayment oldPayment;

    public PaymentAdapter(OldPayment oldPayment) {
        this.oldPayment = oldPayment;
    }

    @Override
    public void pay() {
        oldPayment.payOldWay();
    }
}
```

---

# 11\. Decorator Pattern

Cho phép thêm hành vi cho Object mà không sửa Class gốc.

Ví dụ:

```
Coffee
  ↓
MilkDecorator
  ↓
SugarDecorator
```

Trong Java, Decorator xuất hiện rất rõ trong hệ thống I/O.

---

# 12\. Facade Pattern

Cung cấp một Interface đơn giản cho một hệ thống phức tạp.

Ví dụ:

```
Client
  ↓
OrderFacade
  ↓
Payment
Inventory
Shipping
Email
```

Client chỉ cần:

Java

```
orderFacade.createOrder();
```

thay vì phải gọi:

```
Payment
Inventory
Shipping
Email
```

riêng lẻ.

---

# 13\. Proxy Pattern

Proxy đứng giữa Client và Object thật.

```
Client
  ↓
Proxy
  ↓
Real Object
```

Proxy có thể thực hiện:

```
Logging
Security
Caching
Lazy Loading
Remote Access
```

## Đây là Pattern đặc biệt quan trọng khi học Spring vì Spring sử dụng Proxy trong nhiều cơ chế như AOP, transaction, security...

# PHẦN IV. BEHAVIORAL DESIGN PATTERNS

Behavioral Patterns tập trung vào:

> **Cách các Object giao tiếp và phân chia trách nhiệm.**

Có 11 Pattern:

```
Behavioral
│
├── Chain of Responsibility
├── Command
├── Interpreter
├── Iterator
├── Mediator
├── Memento
├── Observer
├── State
├── Strategy
├── Template Method
└── Visitor
```

---

# 14\. Strategy Pattern

Đây là một Pattern **rất đáng học đối với Java Backend**.

Cho phép thay đổi thuật toán/behavior mà không thay đổi Client.

Ví dụ:

```
PaymentService
      |
      ↓
PaymentStrategy
   ↑        ↑
   |        |
Momo      VnPay
```

Interface:

```
interface PaymentStrategy {
    void pay();
}
```

Implementation:

```
class MomoPayment implements PaymentStrategy {

    public void pay() {
        System.out.println("Momo");
    }
}
```

```
class VnPayPayment implements PaymentStrategy {

    public void pay() {
        System.out.println("VNPay");
    }
}
```

Client:

```
class PaymentService {

    private PaymentStrategy strategy;

    public PaymentService(
            PaymentStrategy strategy) {

        this.strategy = strategy;
    }

    public void pay() {
        strategy.pay();
    }
}
```

Đây cũng giúp bạn thấy một điểm rất quan trọng:

```
Strategy
+
Interface
+
Dependency Injection
```

có thể kết hợp với nhau.

---

# 15\. Observer Pattern

Một Object thay đổi → thông báo cho nhiều Object khác.

```
Subject
   |
   ├── Observer 1
   ├── Observer 2
   └── Observer 3
```

Ví dụ:

```
User đăng ký
     ↓
Event
     ↓
Email Service
     ↓
Notification Service
     ↓
Analytics Service
```

---

# 16\. Template Method

Định nghĩa một **skeleton của algorithm**, còn các bước cụ thể được subclass triển khai.

```
process()
  ↓
step1()
  ↓
step2()
  ↓
step3()
```

Một số bước cố định, một số bước cho subclass override.

---

# 17\. Chain of Responsibility

Request đi qua một chuỗi Handler.

```
Request
   ↓
Handler 1
   ↓
Handler 2
   ↓
Handler 3
   ↓
Handler 4
```

Rất dễ liên tưởng đến:

```
HTTP Request
   ↓
Filter
   ↓
Authentication
   ↓
Authorization
   ↓
Controller
```

---

# PHẦN V. SOLID VÀ DESIGN PATTERN

Design Pattern và SOLID có quan hệ rất chặt.

```
SOLID
  ↓
Nguyên tắc thiết kế
  ↓
Giúp tạo ra thiết kế tốt
  ↓
Design Pattern
  ↓
Mẫu giải quyết vấn đề cụ thể
```

Đặc biệt:

```
DIP
 ↓
IoC
 ↓
DI
```

Đây là phần bạn cần học sâu nhất nếu muốn đi Spring Boot.

---

# PHẦN VI. DEPENDENCY

## 18\. Dependency là gì?

Ví dụ:

```
class OrderService {

    private PaymentService paymentService;

}
```

`OrderService` cần `PaymentService`.

Ta nói:

```
OrderService
      ↓
PaymentService
```

`PaymentService` là **Dependency** của `OrderService`.

---

# 19\. Tight Coupling

Code:

```
class OrderService {

    private PaymentService paymentService =
        new PaymentService();

}
```

`OrderService` tự tạo Dependency.

```
OrderService
      |
      ↓
new PaymentService()
```

Nếu muốn thay:

```
PaymentService
      ↓
VnPayService
```

thì phải sửa `OrderService`.

Đây là **Tight Coupling**.

---

# 20\. Loose Coupling

Thay vì phụ thuộc implementation:

```
class OrderService {

    private PaymentService paymentService;

}
```

Ta sử dụng abstraction:

```
interface PaymentService {

    void pay();
}
```

Implementation:

```
class MomoPayment implements PaymentService {

    public void pay() {
        System.out.println("Momo");
    }
}
```

```
class VnPayPayment implements PaymentService {

    public void pay() {
        System.out.println("VNPay");
    }
}
```

Ta có:

```
                 PaymentService
                 <<interface>>
                   ↑       ↑
                   |       |
                 Momo    VNPay
```

`OrderService` chỉ biết:

```
PaymentService
```

không cần biết implementation cụ thể.

---

# PHẦN VII. DIP

## 21\. Dependency Inversion Principle

**DIP** = **Dependency Inversion Principle**.

DIP là một **Design Principle**, không phải một Design Pattern.

GP Coder phân biệt rất rõ:

```
DIP
→ Principle

IoC
→ Pattern

DI
→ Pattern / cách triển khai IoC
```

---

## 22\. DIP giải quyết vấn đề gì?

Thiết kế không tốt:

```
High-level Module
       |
       ↓
Low-level Module
```

Ví dụ:

```
OrderService
     ↓
MySQLPaymentService
```

Thiết kế tốt:

```
OrderService
     ↓
PaymentService
<<interface>>
     ↑
     |
MySQLPaymentService
```

Cả hai phụ thuộc vào abstraction.

---

# PHẦN VIII. IoC

## 23\. Inversion of Control

**IoC** = **Inversion of Control**.

Nghĩa là:

> Quyền kiểm soát việc tạo và kết nối Object được chuyển từ chính Object sang một thành phần bên ngoài.

Không IoC:

```
OrderService
     |
     | tự tạo
     ↓
PaymentService
```

Có IoC:

```
IoC Container
      |
      | tạo
      ↓
PaymentService
      |
      | inject
      ↓
OrderService
```

Spring chính thức mô tả IoC Container là thành phần quản lý các object và dependencies của chúng.

---

# PHẦN IX. DI

## 24\. Dependency Injection

**DI** = **Dependency Injection**.

DI là việc:

> Dependency được cung cấp từ bên ngoài vào Object thay vì Object tự tạo hoặc tự tìm Dependency.

Không DI:

```
class OrderService {

    private PaymentService paymentService =
        new PaymentService();
}
```

DI:

```
class OrderService {

    private final PaymentService paymentService;

    public OrderService(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }
}
```

Object nhận dependency từ bên ngoài.

Spring cũng định nghĩa DI theo hướng này: object khai báo dependency thông qua constructor, factory method hoặc property; container sẽ inject dependency khi tạo bean.

---

# 25\. Constructor Injection

Đây là loại DI nên ưu tiên học đầu tiên.

```
class OrderService {

    private final PaymentService paymentService;

    public OrderService(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }
}
```

Khởi tạo:

```
PaymentService payment =
    new MomoPayment();

OrderService order =
    new OrderService(payment);
```

Luồng:

```
MomoPayment
     ↓
PaymentService
     ↓
OrderService
```

---

# 26\. Setter Injection

```
class OrderService {

    private PaymentService paymentService;

    public void setPaymentService(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }
}
```

---

# 27\. Field Injection

Ví dụ Spring:

```
@Autowired
private PaymentService paymentService;
```

Cách này tồn tại nhưng khi học Spring nên hiểu rõ **Constructor Injection** trước.

---

# PHẦN X. IoC CONTAINER

## 28\. IoC Container là gì?

IoC Container là thành phần chịu trách nhiệm:

```
Tạo Object
Quản lý Object
Xác định Dependency
Inject Dependency
Quản lý Lifecycle
```

Ví dụ:

```
             IoC Container
                   |
        ┌──────────┼──────────┐
        ↓          ↓          ↓
     UserService OrderService PaymentService
        |
        ↓
   UserRepository
```

---

# 29\. Spring IoC Container

Spring cung cấp IoC Container.

Các thành phần quan trọng:

```
BeanFactory
ApplicationContext
Bean
Dependency Injection
Bean Scope
Bean Lifecycle
```

Spring documentation hiện tại có riêng một chương về IoC Container, bao gồm Bean, Dependencies, Bean Scopes, component scanning, Java configuration và `ApplicationContext`/`BeanFactory`.

---

# 30\. Bean là gì?

Trong Spring:

> Object được Spring IoC Container quản lý được gọi là **Bean**.

Ví dụ:

```
@Service
public class UserService {
}
```

Spring phát hiện class này và tạo object quản lý trong Container.

```
Spring Container
       |
       ↓
UserService Object
       |
       ↓
Bean
```

---

# 31\. @Component, @Service, @Repository, @Controller

Đây là các Annotation bạn sẽ gặp rất nhiều trong Spring Boot.

Java

```
@Component
```

Component chung.

Java

```
@Service
```

Thường dùng cho Business Logic.

Java

```
@Repository
```

Thường dùng cho Data Access.

Java

```
@Controller
```

MVC Controller.

Java

```
@RestController
```

REST API Controller.

---

# 32\. DI trong Spring Boot

Ví dụ:

```
public interface PaymentService {

    void pay();
}
```

Implementation:

```
@Service
public class MomoPaymentService
        implements PaymentService {

    @Override
    public void pay() {
        System.out.println("Momo");
    }
}
```

Controller:

```
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }
}
```

Spring sẽ:

```
        Spring IoC Container
                 |
                 ↓
     MomoPaymentService Bean
                 |
                 ↓
       PaymentService
                 |
                 ↓
      PaymentController
```

Bạn không cần:

Java

```
new MomoPaymentService();
```

trong Controller.

---

# PHẦN XI. DESIGN PATTERN TRONG SPRING

Đây là phần **rất đáng học sau khi hiểu 23 GoF + DI/IoC**.

Spring không chỉ sử dụng một Pattern.

Có thể gặp:

```
Spring
│
├── Dependency Injection
├── Factory
├── Singleton
├── Proxy
├── Template Method
├── Strategy
├── Observer
├── Adapter
├── Decorator
└── ...
```

## Ví dụ tài liệu về Spring Design Patterns cũng tập trung vào GoF trước, sau đó DI, Proxy, Decorator và AOP.

# 33\. Factory trong Spring

Spring Container phải tạo Bean.

Ví dụ:

```
ApplicationContext
       |
       ↓
getBean(...)
       |
       ↓
Bean
```

Do đó tư duy Factory rất quan trọng để hiểu cách Framework quản lý Object.

---

# 34\. Singleton trong Spring

Spring có Scope:

```
singleton
prototype
request
session
...
```

Mặc định Bean Scope của Spring là Singleton.

Điểm quan trọng:

```
Singleton Pattern
≠
Spring Singleton Scope
```

Không nên đánh đồng hai khái niệm.

---

# 35\. Proxy trong Spring

Một số cơ chế Spring sử dụng Proxy.

Ví dụ:

```
Client
  ↓
Proxy
  ↓
Real Object
```

Có thể dùng cho:

```
@Transactional
Security
AOP
Caching
```

Đây là lý do **Proxy Pattern** rất đáng học khi bạn muốn hiểu sâu Spring Boot.

---

# 36\. Strategy trong Spring

Ví dụ có nhiều phương thức thanh toán:

```
PaymentStrategy
       ↑
 ┌─────┼──────┐
 ↓     ↓      ↓
Momo  VNPay  Paypal
```

Spring có thể quản lý các implementation:

```
@Component
class MomoPayment implements PaymentStrategy {
}
```

```
@Component
class VnPayPayment implements PaymentStrategy {
}
```

Sau đó inject nhiều implementation vào một Service.

Đây là ví dụ rất thực tế về:

```
Design Pattern
+
Interface
+
DI
+
Spring IoC
```
