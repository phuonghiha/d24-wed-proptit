# Các phương pháp tối ưu JPA

## 1\. Lazy / Eager initialization

Cả **Tải Tạm Hoãn (Lazy Loading)** và **Tải Nhanh (Eager Loading)** đều là các chiến lược để quản lý cách dữ liệu được truy xuất từ cơ sở dữ liệu hoặc API trong quá trình phát triển ứng dụng, đặc biệt là trong Thiết kế Hệ thống. Tải Tạm Hoãn trì hoãn việc tải dữ liệu liên quan cho đến khi nó thực sự được truy cập, trong khi Tải Nhanh truy xuất tất cả dữ liệu cần thiết chỉ trong một truy vấn duy nhất.

### 1.1. Tải Tạm Hoãn (Lazy Loading) là gì?

Tải Tạm Hoãn có nghĩa là các thực thể liên quan chỉ được tìm nạp khi chúng được truy cập lần đầu tiên. Hibernate sử dụng các đối tượng proxy để đạt được hành vi này.

- Là **mặc định** cho các mối quan hệ `@OneToMany` và `@ManyToMany`.
- Việc tìm nạp xảy ra theo yêu cầu, giảm bộ nhớ ban đầu và tải cơ sở dữ liệu.

**Ví dụ:**

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;
}
```

**Giải thích:**

`fetch = FetchType.LAZY` yêu cầu Hibernate **không** tải danh sách nhân viên (`employees`) cho đến khi phương thức `getEmployees()` được gọi.

#### Lợi ích của Tải Tạm Hoãn

- Chỉ dữ liệu cần thiết được tải, cải thiện hiệu suất khởi động của ứng dụng.
- Bộ nhớ chỉ được tiêu thụ khi dữ liệu được truy cập, làm cho nó tối ưu cho các bộ dữ liệu lớn.
- Chỉ dữ liệu cần thiết được truyền đi, giảm việc sử dụng băng thông.

#### Hạn chế của Tải Tạm Hoãn

- Khi dữ liệu cuối cùng được yêu cầu, nó có thể gây ra độ trễ vì dữ liệu bổ sung cần được tải.
- Có thể dẫn đến nhiều lệnh gọi cơ sở dữ liệu, làm tăng tổng số yêu cầu.
- Yêu cầu logic phức tạp hơn để xử lý việc tải dữ liệu bị trì hoãn.

### 1.2. Tải Nhanh (Eager Loading) là gì?

Tải Nhanh là một chiến lược trong đó tất cả dữ liệu cần thiết, bao gồm cả dữ liệu liên quan, được tải ngay từ đầu trong một yêu cầu duy nhất, đảm bảo truy cập tức thì vào tất cả thông tin cần thiết.

- Là **mặc định** cho các mối quan hệ `@ManyToOne` và `@OneToOne`.
- Hibernate thực hiện các truy vấn `join` hoặc các truy vấn bổ sung để tìm nạp dữ liệu liên kết.

**Ví dụ:**

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;
}
```

**Giải thích:**

`fetch = FetchType.EAGER` đảm bảo rằng phòng ban (`department`) được tải ngay lập tức cùng với đối tượng Nhân viên (`Employee`).

#### Lợi ích của Tải Nhanh

- Tất cả dữ liệu cần thiết được tải ngay từ đầu, đảm bảo không có độ trễ khi truy cập dữ liệu liên quan.
- Thường có thể giảm số lượng truy vấn cơ sở dữ liệu bằng cách tìm nạp tất cả dữ liệu trong một lần.
- Tất cả dữ liệu có sẵn ngay lập tức, không cần logic phức tạp để tải dữ liệu liên quan sau này.

#### Hạn chế của Tải Nhanh

- Việc tải tất cả dữ liệu cùng một lúc có thể dẫn đến thời gian khởi động ứng dụng hoặc tải trang chậm hơn.
- Việc tìm nạp tất cả dữ liệu liên quan cùng một lúc có thể tiêu thụ nhiều bộ nhớ hơn.
- Truy xuất nhiều dữ liệu hơn mức cần thiết, dẫn đến lãng phí trong xử lý dữ liệu và băng thông.

### 1.3. Các Trường Hợp Sử Dụng

#### Tải Tạm Hoãn (Lazy Loading)

- **Bảng tin Mạng xã hội:** Tải bảng tin của người dùng và chỉ tìm nạp hình ảnh hoặc video khi họ cuộn hoặc nhấp vào bài đăng.
- **Trang web nặng nội dung:** Các trang tin tức hoặc cửa hàng thương mại điện tử nơi các chi tiết sản phẩm, đánh giá hoặc hình ảnh bổ sung được tìm nạp khi người dùng tương tác với nội dung.

#### Tải Nhanh (Eager Loading)

- **Bảng điều khiển nhiều dữ liệu (Data-Dense Dashboards):** Các ứng dụng như hệ thống CRM hoặc bảng điều khiển phân tích cần tất cả dữ liệu có sẵn ngay từ đầu để hiển thị nhiều thực thể liên quan.
- **Hệ thống Báo cáo:** Các báo cáo tài chính hoặc kinh doanh kéo tất cả dữ liệu liên quan để hiển thị ngay lập tức, đảm bảo không cần truy vấn bổ sung nào sau đó.

### 1.4. Sự Khác Biệt Giữa Tải Tạm Hoãn và Tải Nhanh

| Tải Tạm Hoãn (Lazy Loading)                     | Tải Nhanh (Eager Loading)                                                |
| ----------------------------------------------- | ------------------------------------------------------------------------ |
| Dữ liệu chỉ được tải khi được truy cập.         | Dữ liệu được tải ngay từ đầu với yêu cầu ban đầu.                        |
| Tải ban đầu nhanh hơn.                          | Tải ban đầu chậm hơn.                                                    |
| Chỉ sử dụng bộ nhớ cho dữ liệu được truy cập.   | Sử dụng bộ nhớ cao hơn do tìm nạp tất cả dữ liệu.                        |
| Có thể dẫn đến nhiều truy vấn.                  | Thông thường ít truy vấn hơn.                                            |
| Yêu cầu mã phức tạp hơn để tải trì hoãn.        | Mã đơn giản hơn vì tất cả dữ liệu có sẵn ngay từ đầu.                    |
| Sử dụng ít băng thông hơn ban đầu.              | Có thể sử dụng nhiều băng thông hơn nếu tìm nạp dữ liệu không cần thiết. |
| Ứng dụng với việc tìm nạp dữ liệu có điều kiện. | Ứng dụng nơi cần tất cả dữ liệu ngay từ đầu.                             |

### 1.5. Code so sánh

**Ví dụ đo thời gian Lazy Loading:**

```java
import java.util.List;

public class LazyLoadingTimeTest {

    public void testLazyLoading() {
        // Bắt đầu đo thời gian
        long startTime = System.currentTimeMillis();

        // Truy vấn Department (chỉ tải thông tin Department, CHƯA tải employees)
        Department department = departmentRepository.findById(1L).orElse(null);

        // Thời gian sau khi tải Department
        long afterDepartmentLoad = System.currentTimeMillis();
        System.out.println("Thời gian tải Department: " + (afterDepartmentLoad - startTime) + "ms");

        // Bây giờ mới truy cập employees -> Hibernate sẽ thực hiện thêm 1 truy vấn
        List<Employee> employees = department.getEmployees();
        // Duyệt qua danh sách để buộc Hibernate tải dữ liệu thực sự
        employees.forEach(e -> e.getName());

        // Kết thúc đo thời gian
        long endTime = System.currentTimeMillis();

        // In ra tổng thời gian chạy
        System.out.println("Thời gian tải Employees (Lazy): " + (endTime - afterDepartmentLoad) + "ms");
        System.out.println("Tổng thời gian (Lazy Loading): " + (endTime - startTime) + "ms");
    }
}
```

**Ví dụ đo thời gian Eager Loading:**

```java
import java.util.List;

public class EagerLoadingTimeTest {

    public void testEagerLoading() {
        // Bắt đầu đo thời gian
        long startTime = System.currentTimeMillis();

        // Truy vấn Employee với Eager Loading
        // Department sẽ được tải NGAY LẬP TỨC cùng với Employee (dùng JOIN)
        Employee employee = employeeRepository.findById(1L).orElse(null);

        // Thời gian sau khi tải Employee (đã bao gồm Department)
        long afterEmployeeLoad = System.currentTimeMillis();
        System.out.println("Thời gian tải Employee + Department (Eager): " + (afterEmployeeLoad - startTime) + "ms");

        // Truy cập Department - KHÔNG cần truy vấn thêm vì đã được tải sẵn
        Department department = employee.getDepartment();
        String deptName = department.getName();

        // Kết thúc đo thời gian
        long endTime = System.currentTimeMillis();

        // In ra tổng thời gian chạy
        System.out.println("Thời gian truy cập Department (đã có sẵn): " + (endTime - afterEmployeeLoad) + "ms");
        System.out.println("Tổng thời gian (Eager Loading): " + (endTime - startTime) + "ms");
    }
}
```

**So sánh kết quả (với 1 Department có 500 Employees):**

| Chỉ số                     | Lazy Loading | Eager Loading |
| -------------------------- | ------------ | ------------- |
| Thời gian tải Entity chính | 12ms         | 95ms          |
| Thời gian truy cập quan hệ | 85ms         | 0ms           |
| **Tổng thời gian**         | **97ms**     | **95ms**      |
| Số lượng truy vấn SQL      | 2            | 1 (JOIN)      |

```
===== KẾT QUẢ LAZY LOADING =====
Thời gian tải Department: 12ms
Thời gian tải Employees (Lazy): 85ms
Tổng thời gian (Lazy Loading): 97ms

===== KẾT QUẢ EAGER LOADING =====
Thời gian tải Employee + Department (Eager): 95ms
Thời gian truy cập Department (đã có sẵn): 0ms
Tổng thời gian (Eager Loading): 95ms
```

**Giải thích số liệu:**

- **Lazy Loading - Tải Entity chính (12ms):** Chỉ query bảng `Department` (1 bản ghi), rất nhanh.
- **Lazy Loading - Truy cập quan hệ (85ms):** Phải query thêm bảng `Employee` (500 bản ghi) + overhead của việc tạo thêm 1 connection.
- **Eager Loading - Tải tất cả (95ms):** Query với JOIN lấy cả `Department` + 500 `Employee` trong 1 lần, tổng thời gian ≈ 12ms + 85ms nhưng **tiết kiệm được overhead** của việc tạo connection thứ 2.

**Nhận xét:**

- **Lazy Loading**: Thời gian ban đầu nhanh hơn (12ms), nhưng **nếu cần truy cập quan hệ** thì tổng thời gian có thể tương đương hoặc chậm hơn Eager Loading do phải thực hiện thêm truy vấn. Phù hợp khi **không phải lúc nào cũng cần** dữ liệu liên quan.
- **Eager Loading**: Thời gian ban đầu chậm hơn (95ms do JOIN), nhưng truy cập quan hệ ngay lập tức (0ms). Phù hợp khi **luôn cần** dữ liệu liên quan.

---

## 2\. Pagination

### 2.1. Tổng Quan về Phân trang và Sắp xếp với Spring Data JPA

Khi ứng dụng của bạn phải làm việc với các tập dữ liệu lớn, việc tải tất cả các bản ghi cùng một lúc là **không hiệu quả** và có thể gây ảnh hưởng tiêu cực đến **hiệu suất** và trải nghiệm người dùng. **Spring Data JPA** cung cấp các công cụ mạnh mẽ, tích hợp sẵn để xử lý Phân trang (Pagination) và Sắp xếp (Sorting) một cách dễ dàng.

#### Phân trang (Pagination)

Phân trang là kỹ thuật chia một tập dữ liệu lớn thành các "trang" nhỏ hơn, dễ quản lý hơn, chỉ lấy từng trang một khi cần.

- **Ví dụ:** Thay vì tải 1.000 hồ sơ khách hàng, bạn có thể tải 10 hồ sơ cho mỗi trang.
- **Lợi ích:** Giảm tải bộ nhớ, tăng tốc độ phản hồi API, và cải thiện trải nghiệm người dùng bằng cách hiển thị dữ liệu theo từng phần.

#### Sắp xếp (Sorting)

Sắp xếp cho phép bạn sắp xếp các bản ghi theo thứ tự tăng dần (ascending) hoặc giảm dần (descending) dựa trên một hoặc nhiều trường dữ liệu.

- **Ví dụ:** Sắp xếp danh sách sản phẩm theo tên (A-Z) hoặc theo giá (thấp đến cao).
- **Lợi ích:** Cải thiện khả năng trình bày dữ liệu và giúp người dùng tìm kiếm thông tin hiệu quả hơn.

#### Các Class/Interface quan trọng trong Spring Data JPA

- **`Pageable`**: Một interface đại diện cho thông tin phân trang (số trang, kích thước trang) và sắp xếp.
- **`PageRequest`**: Một lớp triển khai của `Pageable`, dùng để tạo đối tượng phân trang với các tham số cụ thể.
- **`Sort`**: Một lớp đại diện cho thông tin sắp xếp (trường sắp xếp, hướng sắp xếp).
- **`Page<T>`**: Một interface được trả về bởi các phương thức phân trang của Repository. Nó chứa danh sách các phần tử (dữ liệu của trang hiện tại), cùng với siêu dữ liệu (metadata) về phân trang như tổng số trang, tổng số phần tử, số trang hiện tại, v.v.

### 2.2. Triển Khai: Phân trang và Sắp xếp trong Spring Data JPA

#### Bước 1: Tạo Lớp Entity (`Product.java`)

Định nghĩa mô hình dữ liệu (Entity) cho sản phẩm:

```java
package com.gfg.springsortingpaginationdemo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data // Tạo getters, setters, toString, equals, hashCode (sử dụng Lombok)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products") // Đặt tên bảng rõ ràng hơn (tùy chọn)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
}
```

#### Bước 2: Tạo Interface Repository (`ProductRepository.java`)

Repository là nơi giao tiếp với cơ sở dữ liệu.

- **Lưu ý quan trọng:** Interface **`JpaRepository`** kế thừa từ **`PagingAndSortingRepository`**, vì vậy chỉ cần mở rộng `JpaRepository` là bạn đã có sẵn các phương thức Phân trang và Sắp xếp (như `findAll(Pageable pageable)`).

<!-- end list -->

```java
package com.gfg.springsortingpaginationdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Các phương thức phân trang và sắp xếp được kế thừa từ JpaRepository
}
```

#### Bước 3: Tạo Lớp Service (`ProductService.java`)

Lớp Service chứa logic nghiệp vụ. Nó sử dụng Repository để truy vấn dữ liệu.

```java
package com.gfg.springsortingpaginationdemo;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
package com.gfg.springsortingpaginationdemo;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*
     * Tìm tất cả sản phẩm theo thông tin Phân trang và Sắp xếp được cung cấp.
     * @param pageable đối tượng Pageable chứa số trang, kích thước trang và sắp xếp.
     * @return một đối tượng Page<Product> chứa dữ liệu trang và metadata.
     */
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
```

#### Bước 4: Tạo Lớp Controller (`ProductController.java`)

Controller xử lý các yêu cầu HTTP và chuẩn bị đối tượng `Pageable` dựa trên các tham số truy vấn (query parameters).

```java
package com.gfg.springsortingpaginationdemo;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int page, // Trang mặc định là 0
            @RequestParam(defaultValue = "5") int size, // Kích thước trang mặc định là 5
            @RequestParam(defaultValue = "id") String sortBy, // Sắp xếp mặc định theo trường 'id'
            @RequestParam(defaultValue = "true") boolean ascending) { // Mặc định là tăng dần

        // 1. Tạo đối tượng Sort
        Sort sort = ascending
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

        // 2. Tạo đối tượng Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Gọi Service và trả về kết quả
        return productService.findAll(pageable);
    }
}
```

#### Bước 5: Lớp Main Class

Lớp ứng dụng chính, nơi ứng dụng được khởi chạy.

- Sử dụng `@EnableSpringDataWebSupport` để cho phép Spring tự động chuyển đổi các tham số HTTP thành đối tượng `Pageable` (mặc dù trong ví dụ này ta đang tự làm thủ công trong Controller).

<!-- end list -->

```java
package com.gfg.springsortingpaginationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SpringSortingPaginationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSortingPaginationDemoApplication.class, args);
    }

}
```

### 2.3. Kiểm tra Endpoint

Sau khi chạy ứng dụng (ví dụ: trên cổng 8080), bạn có thể kiểm tra các trường hợp phân trang và sắp xếp khác nhau.

**Giả sử bạn đã có một số dữ liệu mẫu trong bảng `products`.**

| URL Request                                                                 | Mô tả                                                                           |
| --------------------------------------------------------------------------- | ------------------------------------------------------------------------------- |
| `http://localhost:8080/products`                                            | Mặc định: Lấy **trang 0**, **5 bản ghi/trang**, sắp xếp theo **id** (tăng dần). |
| `http://localhost:8080/products?page=1&size=10`                             | Lấy **trang 1**, **10 bản ghi/trang**, sắp xếp mặc định theo id.                |
| `http://localhost:8080/products?page=0&size=5&sortBy=price&ascending=false` | Lấy **trang 0**, **5 bản ghi/trang**, sắp xếp theo **giá** (giảm dần).          |
| `http://localhost:8080/products?sortBy=name`                                | Sắp xếp theo **tên** (tăng dần), sử dụng kích thước trang và số trang mặc định. |

#### Ví dụ về Output (Định dạng `Page<Product>`)

Khi gọi API, bạn sẽ nhận được một đối tượng JSON có dạng:

```json
{
  "content": [
    // Danh sách các đối tượng Product (dữ liệu thực tế của trang)
    { "id": 6, "name": "Laptop X", "price": 1200.0 },
    { "id": 7, "name": "Mouse Y", "price": 25.0 }
  ],
  "pageable": {
    // Thông tin phân trang yêu cầu
    "pageNumber": 1,
    "pageSize": 5,
    "sort": {
      /* ... */
    }
  },
  "totalPages": 20, // Tổng số trang có thể có
  "totalElements": 100, // Tổng số bản ghi trong DB
  "last": false, // Có phải trang cuối cùng không
  "size": 5, // Kích thước trang
  "number": 1, // Số trang hiện tại (bắt đầu từ 0)
  "first": false, // Có phải trang đầu tiên không
  "numberOfElements": 5, // Số phần tử trong trang hiện tại
  "empty": false
}
```

---

## 3\. Caching

### 3.1. Caching là gì?

**Caching** là một kỹ thuật trong lập trình máy tính được sử dụng để **lưu trữ tạm thời** các dữ liệu, kết quả của các phép toán, hoặc các tài nguyên có thể được truy cập một cách **nhanh chóng**. Mục tiêu của caching là **giảm thời gian truy cập dữ liệu**, tăng cường hiệu suất và giảm gánh nặng cho hệ thống.

Khi một ứng dụng yêu cầu dữ liệu, trước hết nó sẽ kiểm tra xem dữ liệu đã được lưu trong **bộ nhớ cache** hay chưa.

- Nếu dữ liệu đã tồn tại trong cache (**Cache Hit**), ứng dụng sẽ lấy nhanh chóng từ đó thay vì phải truy cập vào nơi lữu trữ gốc (ví dụ: cơ sở dữ liệu, API, hoặc tệp tin).
- Nếu dữ liệu không có trong cache (**Cache Miss**), ứng dụng sẽ lấy từ nơi lưu trữ gốc và sau đó lưu vào cache để sử dụng cho các lần truy cập sau.

Caching thường được sử dụng để giảm độ trễ, tăng tốc độ xử lý, và giảm tải cho nguồn lực hệ thống. Các kỹ thuật caching có thể áp dụng cho nhiều loại dữ liệu và tài nguyên, bao gồm dữ liệu cơ sở dữ liệu, kết quả của phương thức tính toán, tệp tin tĩnh, và nhiều hơn nữa.

### 3.2. Ưu điểm của Caching

Caching được sử dụng bởi hầu hết các ứng dụng vì những ưu điểm vượt trội sau:

1.  **Tăng hiệu suất** Caching giảm thời gian truy cập dữ liệu từ nơi lưu trữ gốc, giúp ứng dụng hoạt động nhanh chóng hơn từ đó góp phần rất lớn giúp cho ứng dụng của bạn tăng hiệu suất làm việc.
    - _Ví dụ:_ Một trang web sử dụng caching để lưu trữ tạm thời hình ảnh và tệp CSS, giảm thời gian tải trang cho người dùng.

2.  **Giảm Gánh Nặng Cho Cơ Sở Dữ Liệu** Caching giảm số lượng truy vấn trực tiếp đến cơ sở dữ liệu (DB), giảm áp lực lên DB và cải thiện hiệu suất hệ thống. Trong các ứng dụng lớn với hàng trăm, hàng ngàn query vào cơ sở dữ liệu mỗi giây, caching là một giải pháp cứu cánh.
    - _Ví dụ:_ Một ứng dụng di động sử dụng caching để lưu trữ kết quả từ API và tránh việc gửi yêu cầu đến máy chủ mỗi lần người dùng mở ứng dụng.

3.  **Cải Thiện Trải Nghiệm Người Dùng** Caching giúp cung cấp trải nghiệm người dùng mượt mà hơn và giảm độ trễ vì kết quả được trả về nhanh hơn từ bộ nhớ cache.
4.  **Cải Thiện Độ Ổn Định Hệ Thống** Caching giúp cải thiện độ ổn định của hệ thống bằng cách giảm độ trễ và gánh nặng xử lý, giúp hệ thống chạy mượt mà hơn, đặc biệt trong các tình huống tải cao.
    - _Ví dụ:_ Một ứng dụng trò chơi trực tuyến sử dụng caching để lưu trữ thông tin về người chơi và giảm độ trễ (latency) trong quá trình chơi game.

### 3.3. Những lưu ý khi sử dụng Caching

Quản lý dữ liệu cache là phần khó khăn nhất để đảm bảo caching mang lại hiệu quả. Dưới đây là những lưu ý quan trọng:

1.  **Rủi Ro Dữ Liệu Lỗi Thời (Stale Data)** Nếu dữ liệu trong cache không được cập nhật thường xuyên, nó có thể dẫn đến việc sử dụng dữ liệu lỗi thời (stale).
    - _Ví dụ:_ Một trang web tin tức không cập nhật cache đúng cách, dẫn đến việc hiển thị tin tức cũ cho người đọc.
    - **Giải pháp:** Cần kiểm tra và cập nhật dữ liệu trong cache thường xuyên hoặc sử dụng các cơ chế xóa/loại bỏ cache (eviction).

2.  **Quản Lý Thời Gian Sống (Time-to-Live - TTL) Cẩn Thận** Thiết lập thời gian sống (TTL) phù hợp với tính chất của dữ liệu và mức độ thay đổi. Cần tính toán kỹ lưỡng để tránh dữ liệu lỗi thời hoặc re-fresh (tải lại) quá nhiều khiến việc cache trở nên vô nghĩa. (Chi tiết TTL config ở phần "Ví dụ sử dụng Caffeine Cache" trong mục **3.4.2**)
    - _Ví dụ:_ Dữ liệu về tỷ giá hối đoái nên có TTL ngắn (vài phút), trong khi thông tin sản phẩm ít thay đổi có thể có TTL dài hơn (vài giờ).

3.  **Quản Lý Bộ Nhớ Hiệu Quả** Caching có thể làm tăng tiêu tốn bộ nhớ (RAM), đặc biệt là khi lưu trữ lượng dữ liệu lớn. Cần kiểm soát lượng bộ nhớ sử dụng cho caching để tránh tình trạng chiếm dụng bộ nhớ quá mức.
4.  **Xác Định Rõ Mục Tiêu và Áp dụng chiến lược hợp lý** Đặt rõ mục tiêu bạn muốn đạt được với caching (tăng tốc độ, giảm áp lực DB, cải thiện trải nghiệm) từ đó lựa chọn chiến lược caching (như **Cache-Aside**, **Read-Through**, **Write-Through**) phù hợp với yêu cầu và tính chất của ứng dụng.

### 3.4. Các phương pháp caching dữ liệu trong Java Spring Boot

Có nhiều cách để caching dữ liệu, thường được chia thành các loại sau:

#### 3.4.1. Spring Cache Abstraction

Đây là một API trừu tượng của Spring Framework giúp bạn dễ dàng tích hợp với các trình quản lý cache thực tế (như EhCache, Caffeine, Redis) mà không cần quan tâm đến việc thực hiện cụ thể.

##### a. Làm việc với Spring Cache Abstraction

- **`@Cacheable`**: Đánh dấu một phương thức để cache kết quả. Nếu dữ liệu đã có trong cache, nó được trả về ngay lập tức (Cache Hit).
- **`@CacheEvict`**: Dùng để loại bỏ dữ liệu khỏi cache (xóa một mục cụ thể hoặc toàn bộ cache).
- **`@CachePut`**: Luôn thực hiện phương thức và cập nhật kết quả mới vào cache, hữu ích khi cần cập nhật dữ liệu.
- **`@Caching`**: Cho phép gộp nhiều hoạt động cache (ví dụ: `@Cacheable` và `@CachePut`) vào một phương thức.
- **`@CacheConfig`**: Dùng để chia sẻ các thuộc tính cache giữa các phương thức trong một lớp.

##### b. Tính linh hoạt và cấu hình

Spring Cache linh hoạt, hỗ trợ cả **cache in-memory** (EhCache, Caffeine) và **cache phân tán** (Redis, Hazelcast). Nếu không cấu hình trình quản lý cụ thể, Spring Boot sẽ tạo ra một **`SimpleCacheManager`** với **`ConcurrentMapCache`** mặc định, là một giải pháp **in-memory** cơ bản.

##### c. Ví dụ sử dụng

**Bước 1: Thêm Dependencies** Thêm `spring-boot-starter-cache` vào `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

**Bước 2: Kích hoạt Caching** Thêm `@EnableCaching` vào một class cấu hình Java:

```java
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Mặc định, Spring Boot sử dụng SimpleCacheManager
}
```

**Bước 3: Sử Dụng Caching trong Service** Sử dụng `@Cacheable` để cache kết quả của phương thức:

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Cacheable("books") // Tên cache là "books"
    public Book getBookByIsbn(String isbn) {
        // ... (Phương thức tốn kém thời gian để thực hiện)
        return findBookInDatabase(isbn);
    }
}
```

**Tùy chỉnh Cache Key và Eviction:**

```java
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

@Service
public class BookService {

    @Cacheable(value = "books", key = "#isbn")
    public Book getBookByIsbn(String isbn) { /* ... */ return findBookInDatabase(isbn); }

    @CachePut(value = "books", key = "#book.isbn")
    public Book updateBook(Book book) { /* ... */ return updateBookInDatabase(book); }

    @CacheEvict(value = "books", allEntries = true)
    public void evictAllBooksCache() {
        // Phương thức này sẽ xóa tất cả các mục trong cache 'books'
    }
}
```

**Ví dụ đo thời gian Caching:**

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceWithCacheTest {

    private final BookRepository bookRepository;

    @Cacheable(value = "books", key = "#isbn")
    public Book getBookByIsbn(String isbn) {
        // Giả lập truy vấn tốn thời gian (ví dụ: query phức tạp hoặc gọi API bên ngoài)
        simulateSlowQuery();
        return bookRepository.findByIsbn(isbn);
    }

    private void simulateSlowQuery() {
        try {
            Thread.sleep(500); // Giả lập độ trễ 500ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Phương thức test để so sánh thời gian
    public void testCachingPerformance() {
        String testIsbn = "978-3-16-148410-0";

        // ========== LẦN GỌI ĐẦU TIÊN (Cache Miss) ==========
        // Bắt đầu đo thời gian lần gọi đầu tiên
        long startTimeFirst = System.currentTimeMillis();

        // Lần đầu gọi - dữ liệu CHƯA có trong cache, phải query từ DB
        Book book1 = getBookByIsbn(testIsbn);

        // Kết thúc đo thời gian lần đầu
        long endTimeFirst = System.currentTimeMillis();

        System.out.println("===== LẦN GỌI ĐẦU TIÊN (Cache Miss) =====");
        System.out.println("Kết quả: " + book1.getTitle());
        System.out.println("Thời gian: " + (endTimeFirst - startTimeFirst) + "ms");

        // ========== LẦN GỌI THỨ HAI (Cache Hit) ==========
        // Bắt đầu đo thời gian lần gọi thứ hai
        long startTimeSecond = System.currentTimeMillis();

        // Lần thứ hai gọi - dữ liệu ĐÃ CÓ trong cache, trả về ngay lập tức
        Book book2 = getBookByIsbn(testIsbn);

        // Kết thúc đo thời gian lần thứ hai
        long endTimeSecond = System.currentTimeMillis();

        System.out.println("\n===== LẦN GỌI THỨ HAI (Cache Hit) =====");
        System.out.println("Kết quả: " + book2.getTitle());
        System.out.println("Thời gian: " + (endTimeSecond - startTimeSecond) + "ms");

        // ========== LẦN GỌI THỨ BA (Cache Hit) ==========
        long startTimeThird = System.currentTimeMillis();
        Book book3 = getBookByIsbn(testIsbn);
        long endTimeThird = System.currentTimeMillis();

        System.out.println("\n===== LẦN GỌI THỨ BA (Cache Hit) =====");
        System.out.println("Kết quả: " + book3.getTitle());
        System.out.println("Thời gian: " + (endTimeThird - startTimeThird) + "ms");
    }
}
```

**Kết quả mong đợi:**

```
===== LẦN GỌI ĐẦU TIÊN (Cache Miss) =====
Kết quả: Clean Code
Thời gian: 523ms

===== LẦN GỌI THỨ HAI (Cache Hit) =====
Kết quả: Clean Code
Thời gian: 1ms

===== LẦN GỌI THỨ BA (Cache Hit) =====
Kết quả: Clean Code
Thời gian: 0ms
```

#### 3.4.2. In-Memory Cache

**In-Memory Cache** là hình thức caching trong đó dữ liệu được lưu trữ trực tiếp trong bộ nhớ chính (**RAM**) của máy, cho tốc độ truy xuất nhanh nhất.

##### a. Đặc điểm và công cụ

- **Đặc điểm:** Tốc độ nhanh, nhưng dữ liệu bị mất khi ứng dụng tắt, bộ nhớ (RAM) có giới hạn.
- **Công cụ phổ biến:**
  - **`HashMap`** hoặc **`ConcurrentHashMap`**: Đơn giản nhất, nhưng không hỗ trợ các tính năng quản lý cache tự động (TTL, eviction).
  - **Caffeine**: Một thư viện caching mạnh mẽ, hiệu suất cao với các cơ chế quản lý cache như **evictions** (loại bỏ theo kích thước, TTL) và **loading**.
  - **Guava Cache**: Tiền thân của Caffeine, cung cấp các tính năng tương tự.

##### b. Ví dụ sử dụng HashMap (Thủ công)

```java
import java.util.HashMap;
import java.util.Map;

public class DataCacheExample {

    private Map<String, String> dataCache = new HashMap<>();

    public String fetchData(String key) {
        String result = dataCache.get(key);

        if (result == null) {
            result = fetchDataFromSource(key); // Lấy từ nguồn
            dataCache.put(key, result); // Lưu vào cache
        }
        return result;
    }

    private String fetchDataFromSource(String key) {
        // Giả lập logic đọc từ database/API
        return "Data for key: " + key;
    }
    // ... main method để test
}
```

##### c. Ví dụ sử dụng Caffeine Cache

Caffeine Cache thường được tích hợp thông qua Spring Cache Abstraction.

**Bước 1: Thêm Dependency** Thêm dependency Caffeine (ví dụ cho Maven):

```xml
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>3.0.0</version> </dependency>
```

**Bước 2: Cấu Hình Caffeine Cache** Cấu hình trong `application.properties`:

```properties
# Cấu hình cho Caffeine Cache
spring.cache.caffeine.spec=maximumSize=100,expireAfterWrite=5m
```

- `maximumSize=100`: Tối đa 100 mục trong cache.
- `expireAfterWrite=5m`: Mỗi mục hết hạn sau 5 phút kể từ khi được viết vào cache.

Hoặc cấu hình bằng code:

```java
import org.springframework.cache.caffeine.CaffeineCacheManager;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES) // TTL
            .maximumSize(100) // Kích thước tối đa
            .recordStats();
    }
}
```

#### 3.4.3. Distributed Cache

**Distributed Cache** là hệ thống cache hoạt động trên **nhiều máy chủ/nút** trong mạng. Đây là lựa chọn hàng đầu cho các ứng dụng **phân tán** (Microservices), giúp tăng khả năng mở rộng (Scalability) và chịu lỗi (Fault Tolerance).

##### a. Đặc điểm và công cụ

- **Đặc điểm:** Dữ liệu phân tán giữa nhiều nút, cần đồng bộ hóa/sao chép, tăng khả năng mở rộng ngang, chịu lỗi cao hơn.
- **Công cụ phổ biến:**
  - **Redis**: Cơ sở dữ liệu key-value nhanh, hỗ trợ cấu trúc dữ liệu phức tạp, thường được dùng làm cache phân tán.
  - **Memcached**: Hệ thống caching phân tán nhẹ, tập trung vào lưu trữ key-value đơn giản.
  - **Hazelcast**: Một in-memory data grid, cung cấp các cấu trúc dữ liệu phân tán và hỗ trợ transaction.

#### 3.4.4. Một vài phương pháp caching dữ liệu khác

- **Cache with Database (2nd Level Cache):**
  - **EhCache**: Có thể sử dụng độc lập hoặc như một phần của JPA/Hibernate để caching các truy vấn và đối tượng.
- **HTTP Caching:** Caching tại tầng HTTP bằng cách sử dụng các headers như `Cache-Control`, `Etag`, và `Last-Modified` để client/Proxy server lưu trữ kết quả.
- **Web Browser Caching:** Tận dụng cache của trình duyệt để lưu trữ các tài nguyên tĩnh (CSS, JS, images).
- **CDN (Content Delivery Network):** Sử dụng mạng lưới máy chủ phân tán toàn cầu để cache nội dung tĩnh và động, giúp tăng tốc độ phân phối nội dung dựa trên vị trí địa lý của người dùng.
- **Database Caching:** Một số cơ sở dữ liệu (như MySQL) có cơ chế caching query results của riêng họ.
- **Custom Caching Strategies:** Tùy chỉnh logic caching riêng dựa trên nhu cầu cụ thể của ứng dụng.

---

## 4\. Manual / Native query

### 4.1. Sử Dụng Native Queries trong Spring Data JPA

**Native Queries** (Truy vấn gốc) trong JPA là cách linh hoạt và mạnh mẽ nhất để truy xuất dữ liệu, cho phép chúng ta sử dụng tất cả các tính năng độc quyền được hỗ trợ bởi cơ sở dữ liệu (RDBMS) đang sử dụng. Spring Data JPA giúp việc định nghĩa và thực thi Native Queries trở nên dễ dàng hơn.

#### Định Nghĩa Native Queries

Trong JPA thuần hoặc Hibernate, việc định nghĩa và thực hiện một native query đòi hỏi nhiều bước cấu hình. Annotation **`@Query`** của Spring Data giúp loại bỏ hầu hết mã boilerplate đó.

Để định nghĩa một native query, chúng ta chú thích phương thức Repository với **`@Query`**, thiết lập thuộc tính **`nativeQuery=true`**, và cung cấp câu lệnh SQL gốc làm giá trị:

```java
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {

    @Query(value="select * from author a where a.first_name= :firstName", nativeQuery=true)
    List<Author> getAuthorsByFirstName(String firstName);

}
```

- **Thực thi:** Chỉ cần inject Repository và gọi phương thức. Spring Data JPA sẽ thực thi câu lệnh SQL gốc được cung cấp và ánh xạ kết quả trở lại các Entity Java.

<!-- end list -->

```java
List<Author> authors = authorRepository.getAuthorsByFirstName("Janssen");
```

#### Viết Thao Tác Ghi (DELETE, UPDATE) dưới dạng Native Queries

Các thao tác ghi (Write operations) như `UPDATE` hoặc `DELETE` cũng có thể được định nghĩa bằng Native Queries.

Để thực hiện thao tác ghi, chúng ta cần:

1.  Sử dụng annotation **`@Query`** với **`nativeQuery=true`**.
2.  Bổ sung annotation **`@Modifying`** để báo cho Spring Data biết đây là một thao tác sửa đổi dữ liệu.

<!-- end list -->

```java
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {

    // Xóa
    @Modifying
    @Query(value="delete from author a where a.last_name= :lastName", nativeQuery = true)
    void deleteAuthorByLastName(@Param("lastName") String lastName);

    // Cập nhật
    @Modifying
    @Query(value="update author set last_name= :lastName where first_name = :firstName", nativeQuery=true)
    void updateAuthorByFirstName(String firstName, String lastName);

}
```

### 4.2. Ví dụ đo thời gian Native Query vs JPQL Query

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // JPQL Query (được Hibernate chuyển đổi sang SQL)
    @Query("SELECT a FROM Author a WHERE a.lastName = :lastName")
    List<Author> findByLastNameJPQL(String lastName);

    // Native Query (SQL thuần, chạy trực tiếp)
    @Query(value = "SELECT * FROM author a WHERE a.last_name = :lastName", nativeQuery = true)
    List<Author> findByLastNameNative(String lastName);
}

public class NativeQueryTimeTest {

    private final AuthorRepository authorRepository;

    public void compareQueryPerformance() {
        String testLastName = "Smith";
        int iterations = 100; // Chạy nhiều lần để có kết quả chính xác hơn

        // ========== JPQL QUERY ==========
        // Bắt đầu đo thời gian cho JPQL Query
        long startTimeJPQL = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            // JPQL: Hibernate phải parse và chuyển đổi sang SQL
            List<Author> authorsJPQL = authorRepository.findByLastNameJPQL(testLastName);
        }

        // Kết thúc đo thời gian JPQL
        long endTimeJPQL = System.currentTimeMillis();

        System.out.println("===== JPQL QUERY =====");
        System.out.println("Số lần chạy: " + iterations);
        System.out.println("Tổng thời gian: " + (endTimeJPQL - startTimeJPQL) + "ms");
        System.out.println("Thời gian trung bình mỗi lần: " +
            ((endTimeJPQL - startTimeJPQL) / (double) iterations) + "ms");

        // ========== NATIVE QUERY ==========
        // Bắt đầu đo thời gian cho Native Query
        long startTimeNative = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            // Native Query: Chạy trực tiếp SQL, không cần parse
            List<Author> authorsNative = authorRepository.findByLastNameNative(testLastName);
        }

        // Kết thúc đo thời gian Native
        long endTimeNative = System.currentTimeMillis();

        System.out.println("\n===== NATIVE QUERY =====");
        System.out.println("Số lần chạy: " + iterations);
        System.out.println("Tổng thời gian: " + (endTimeNative - startTimeNative) + "ms");
        System.out.println("Thời gian trung bình mỗi lần: " +
            ((endTimeNative - startTimeNative) / (double) iterations) + "ms");

        // So sánh kết quả
        System.out.println("\n===== SO SÁNH =====");
        long difference = (endTimeJPQL - startTimeJPQL) - (endTimeNative - startTimeNative);
        System.out.println("Chênh lệch thời gian: " + difference + "ms");
        if (difference > 0) {
            System.out.println("Native Query nhanh hơn JPQL: " +
                (difference / (double) iterations) + "ms mỗi lần");
        } else {
            System.out.println("JPQL nhanh hơn Native Query: " +
                (Math.abs(difference) / (double) iterations) + "ms mỗi lần");
        }
    }
}
```

**Kết quả mong đợi:**

```
===== JPQL QUERY =====
Số lần chạy: 100
Tổng thời gian: 1250ms
Thời gian trung bình mỗi lần: 12.5ms

===== NATIVE QUERY =====
Số lần chạy: 100
Tổng thời gian: 980ms
Thời gian trung bình mỗi lần: 9.8ms

===== SO SÁNH =====
Chênh lệch thời gian: 270ms
Native Query nhanh hơn JPQL: 2.7ms mỗi lần
```

**Giải thích sự khác biệt về thời gian:**

| Bước xử lý                   | JPQL Query                                                           | Native Query                           |
| ---------------------------- | -------------------------------------------------------------------- | -------------------------------------- |
| **1\\. Parsing**             | Hibernate phải parse cú pháp JPQL                                    | Không cần parse (SQL thuần)            |
| **2\\. Chuyển đổi sang SQL** | Hibernate chuyển JPQL → SQL theo dialect của DB                      | **Bỏ qua** (đã là SQL sẵn)             |
| **3\\. Ánh xạ tên trường**   | Chuyển tên thuộc tính Entity (`lastName`) → tên cột DB (`last_name`) | **Bỏ qua** (đã dùng tên cột trực tiếp) |
| **4\\. Thực thi trên DB**    | Thực thi SQL đã chuyển đổi                                           | Thực thi SQL trực tiếp                 |
| **5\\. Ánh xạ kết quả**      | Ánh xạ ResultSet → Entity                                            | Ánh xạ ResultSet → Entity              |
| **Tổng overhead**            | **Cao hơn** do bước 1, 2, 3                                          | **Thấp hơn** do bỏ qua bước 1, 2, 3    |

**Khi nào sự khác biệt rõ rệt?**

- **Query đơn giản (như ví dụ trên):** Chênh lệch ~20-30% (2-3ms mỗi lần)
- **Query phức tạp (nhiều JOIN, subquery):** Chênh lệch có thể lên đến 50-100% do Hibernate phải xử lý nhiều logic chuyển đổi hơn
- **Query được cache:** Sau lần đầu, Hibernate cache lại SQL đã chuyển đổi nên các lần sau sẽ nhanh hơn, thu hẹp khoảng cách

### 4.3. Hạn Chế của Native Queries

Mặc dù mạnh mẽ, việc sử dụng Native Queries trong Spring Data JPA có ba hạn chế đáng chú ý:

1.  **Không Tự Điều Chỉnh Ngôn Ngữ SQL:**
    - Spring Data JPA và Persistence Provider (Hibernate) **không tự động điều chỉnh** truy vấn theo cú pháp SQL của một cơ sở dữ liệu cụ thể (ví dụ: cú pháp Oracle khác với MySQL).
    - **Yêu cầu:** Chúng ta phải tự đảm bảo câu lệnh SQL được cung cấp tương thích với **tất cả** các RDBMS mà ứng dụng hỗ trợ.
2.  **Yêu Cầu `countQuery` khi Phân Trang (Pagination):**
    - Đối với truy vấn **JPQL** thông thường, Spring Data JPA tự động tạo câu lệnh `COUNT` để xác định tổng số bản ghi khi sử dụng tham số `Pageable`.
    - Đối với **Native Query**, Spring Data JPA không thể tự động tạo câu lệnh `COUNT`. Chúng ta phải cung cấp câu lệnh `count query` để trả về tổng số bản ghi bằng cách sử dụng thuộc tính **`countQuery`** trong annotation `@Query`.

    <!-- end list -->

```java
    @Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {

    @Query(value="select * from author a where a.last_name= ?1",
            countQuery = "select count(id) from author a where a.last_name= ?1", // Bắt buộc phải cung cấp
            nativeQuery = true)
    Page<Author> getAuthorsByLastName(String lastname, Pageable page);
}
```

    -   **Trường hợp Named Native Query:** Nếu tham chiếu đến `named native query`, cần bổ sung một `named native query` riêng để đếm tổng số bản ghi và thêm hậu tố **`.count`** vào tên của nó.

    <!-- end list -->

```java
    @NamedNativeQuery(name = "Author.getAuthorsByLastName",
                    query = "select * from author a where a.last_name= ?1",
                    resultClass = Author.class)
@NamedNativeQuery(name = "Author.getAuthorsByLastName.count",
                    query = "select count(id) from author a where a.last_name= ?1")
@Entity
public class Author { ... }
```

1.  **Không Hỗ Trợ Sắp Xếp Động (Dynamic Sorting):**
    - Với JPQL, chúng ta có thể thêm tham số kiểu **`Sort`** hoặc **`Pageable`** vào phương thức Repository, và Spring Data sẽ tự động tạo mệnh đề `ORDER BY` dựa trên tham số đó.
    - **Native Query không hỗ trợ** tính năng sắp xếp động này. Lý do là việc phân tích cú pháp câu lệnh SQL gốc và tạo mệnh đề `ORDER BY` theo cách an toàn kiểu (type-safe) là một hoạt động phức tạp, chưa được Spring Data JPA hỗ trợ.
    - **Giải pháp:** Nếu cần sắp xếp, bạn phải tự bổ sung mệnh đề `ORDER BY` vào câu lệnh SQL gốc. Điều này làm giảm tính linh động của truy vấn.

### 4.3. Kết Luận Chung

Native Queries là một công cụ mạnh mẽ, mang lại sự linh hoạt tối đa khi cần tận dụng các tính năng của cơ sở dữ liệu gốc. Tuy nhiên, việc sử dụng chúng đòi hỏi nhiều công sức hơn so với **Derived Query** hoặc **Custom JPQL Query** và đi kèm với các đánh đổi quan trọng:

| Tính năng                   | JPQL Query                         | Native Query                                     |
| --------------------------- | ---------------------------------- | ------------------------------------------------ |
| **Linh hoạt**               | Giới hạn bởi chuẩn JPA             | Tối đa, sử dụng toàn bộ tính năng DB             |
| **Phân trang**              | Tự động tạo `COUNT`                | **Cần cung cấp `countQuery` thủ công**           |
| **Sắp xếp động**            | Hỗ trợ tự động (`Sort`/`Pageable`) | **Không hỗ trợ** (phải tự viết `ORDER BY`)       |
| **Khả năng tương thích DB** | Tương thích cao giữa các DB        | **Không tương thích**, yêu cầu kiểm tra thủ công |

---

## 5\. Dynamic query

### 5.1. Tạo Truy Vấn Động (Dynamic Queries) trong Spring Data JPA

Khi phát triển ứng dụng với Spring Data, việc xây dựng các truy vấn động dựa trên nhiều tiêu chí lựa chọn là rất phổ biến để lấy dữ liệu từ cơ sở dữ liệu.

Hướng dẫn này sẽ khám phá ba phương pháp tiếp cận chính để tạo truy vấn động trong Spring Data JPA: **Query by Example**, **Query by Specification**, và **Query by Querydsl**.

<br>

### 5.2. Thiết Lập Kịch Bản Mẫu

Chúng ta sẽ sử dụng hai Entity là **`School`** và **`Student`**, với mối quan hệ **Một-Nhiều** (One-to-Many): một `School` có thể có nhiều `Student`.

#### Entity School

```java
@Entity
@Table
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String borough;

    @OneToMany(mappedBy = "school")
    private List<Student> studentList;

    // constructor, getters and setters
}
```

#### Entity Student

```java
@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    @ManyToOne
    private School school;

    // constructor, getters and setters
}
```

#### Repository

Định nghĩa Repository cho Entity `Student`:

```java
public interface StudentRepository extends JpaRepository<Student, Long> {
}
```

#### Dữ liệu Mẫu

| Bảng        | id  | name                      | age | school\\\_id | borough              |
| ----------- | --- | ------------------------- | --- | ------------ | -------------------- |
| **School**  | 1   | University of West London |     |              | Ealing               |
|             | 2   | Kingston University       |     |              | Kingston upon Thames |
| **Student** | 1   | Emily Smith               | 20  | 2            |                      |
|             | 2   | James Smith               | 20  | 3            |                      |
|             | 3   | Maria Johnson             | 22  | 1            |                      |
|             | 4   | Michael Brown             | 21  | 1            |                      |
|             | 5   | Sophia Smith              | 22  | 1            |                      |

Trong các phần sau, chúng ta sẽ tìm các bản ghi theo tiêu chí sau bằng ba phương pháp:

- **`Student`** có tên kết thúc bằng **`Smith`**, **VÀ**
- **`Student`** có **`age`** là **`20`**, **VÀ**
- **`Student`** có **`school`** nằm ở quận **`Ealing`**.

### 5.3. Phương pháp Query by Example

**Query by Example** là cách đơn giản nhất trong Spring Data để tạo truy vấn dựa trên một **Entity mẫu**. Ý tưởng là tạo một đối tượng Entity, đặt các tiêu chí tìm kiếm vào các trường của nó, và sử dụng đối tượng này để tìm kiếm các thực thể khớp.

#### Triển khai

Repository **`JpaRepository`** đã kế thừa interface **`QueryByExampleExecutor`**, nên không cần phải mở rộng thêm.

1.  **Tạo Entity Example:** Tạo một đối tượng `Student` và `School` mẫu để đưa vào ba tiêu chí lọc:

```java
School schoolExample = new School();
schoolExample.setBorough("Ealing");

Student studentExample = new Student();
studentExample.setAge(20);
studentExample.setName("Smith");
studentExample.setSchool(schoolExample);

// Tạo Example cơ bản (chỉ hỗ trợ khớp chính xác)
Example example = Example.of(studentExample);
```

2.  **Sử dụng `ExampleMatcher` cho Lọc phức tạp:** Nếu muốn tìm sinh viên có tên **kết thúc bằng** `"Smith"` (thay vì khớp chính xác), chúng ta cần tùy chỉnh chiến lược khớp bằng **`ExampleMatcher`**:

```java
ExampleMatcher customExampleMatcher = ExampleMatcher.matching()
  .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase()); // Tên kết thúc bằng và không phân biệt chữ hoa/thường

Example<Student> example = Example.of(studentExample, customExampleMatcher);
```

3.  **Thực hiện Truy vấn:** Gọi phương thức `findAll(Example)` của Repository:

```java
List<Student> studentList = studentRepository.findAll(example);
```

#### Ưu điểm & Hạn chế

- **Ưu điểm:** Đơn giản, dễ hiểu và dễ triển khai.
- **Hạn chế:** Chỉ hỗ trợ khớp chính xác (hoặc sử dụng `ExampleMatcher` cho một số kiểu khớp chuỗi đơn giản như `startsWith`, `endsWith`). **Không hỗ trợ** các điều kiện phức tạp hơn như "lớn hơn", "nhỏ hơn" (`greater than`, `less than`).

---

### 5.4. Phương pháp Query by Specification

**Query by Specification** cho phép tạo các truy vấn động dựa trên một tập hợp các điều kiện bằng cách sử dụng interface **`Specification`**. Phương pháp này linh hoạt hơn và hữu ích cho các yêu cầu truy vấn phức tạp hoặc cần điều chỉnh động khi runtime.

#### Triển khai

1.  **Mở rộng Repository:** Repository phải mở rộng interface **`JpaSpecificationExecutor`** để kích hoạt chức năng này:

```java
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
}
```

2.  **Định nghĩa các `Specification`:** Định nghĩa các phương thức tĩnh trả về `Specification<Student>` cho mỗi điều kiện lọc. Chúng ta sử dụng **`CriteriaBuilder`** để xây dựng các điều kiện lọc (Predicates).

```java
public class StudentSpecification {
    // 1. Tên kết thúc bằng, không phân biệt chữ hoa/thường (LIKE)
    public static Specification<Student> nameEndsWithIgnoreCase(String name) {
        return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase());
    }

    // 2. Tuổi bằng (EQUAL)
    public static Specification<Student> isAge(int age) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("age"), age);
    }

    // 3. Quận của Trường (Sử dụng JOIN)
    public static Specification<Student> isSchoolBorough(String borough) {
        return (root, query, criteriaBuilder) -> {
            // Thực hiện JOIN từ Student sang School
            Join<Student, School> scchoolJoin = root.join("school");
            return criteriaBuilder.equal(scchoolJoin.get("borough"), borough);
        };
    }
}
```

    -   **`CriteriaBuilder`**: Giúp xây dựng các truy vấn động trong JPA theo lập trình (như `equal(...)`, `like(...)`).
    -   **`Root.join(...)`**: Dùng để tham gia (JOIN) một bảng bổ sung thông qua mối quan hệ.

3.  **Thực hiện Truy vấn:** Kết hợp các `Specification` bằng các toán tử logic như **`.where()`** và **`.and()`** (hoặc `.or()`):

```java
Specification<Student> studentSpec = Specification
  .where(StudentSpecification.nameEndsWithIgnoreCase("smith"))
  .and(StudentSpecification.isAge(20))
  .and(StudentSpecification.isSchoolBorough("Ealing"));

List<Student> studentList = studentRepository.findAll(studentSpec);
```

#### Ưu điểm & Hạn chế

- **Ưu điểm:** Rất linh hoạt, hỗ trợ đầy đủ các biểu thức SQL (`JOIN`, `LESS THAN`, `GREATER THAN`), và cho phép kết hợp các điều kiện một cách dễ dàng.
- **Hạn chế:** Cú pháp có thể trở nên dài dòng và khó đọc khi có nhiều điều kiện phức tạp.

---

### 5.5. Phương pháp Query by QueryDSL

**QueryDSL** giải quyết hạn chế về độ dài dòng và tính dễ đọc của `Specification` bằng cách cung cấp một framework tạo truy vấn động **an toàn kiểu (type-safe)**, trực quan và dễ đọc hơn.

#### Triển khai

1.  **Thêm Dependencies và Plugin:** Thêm dependencies cho **`querydsl-jpa`** và **`querydsl-apt`** (Annotation Processor Tool) vào `pom.xml`. Lưu ý sử dụng classifier `jakarta` nếu dùng JPA 3.0 trở lên:

```xml
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
    <version>5.1.0</version>
    <classifier>jakarta</classifier>
</dependency>
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-apt</artifactId>
    <version>5.1.0</version>
    <classifier>jakarta</classifier>
    <scope>provided</scope>
</dependency>
```

    Cấu hình plugin để tự động sinh ra **lớp meta-model** (`QStudent`, `QSchool`):

```xml
<plugin>
    <groupId>com.mysema.maven</groupId>
    <artifactId>apt-maven-plugin</artifactId>
    <version>1.1.3</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>process</goal>
            </goals>
            <configuration>
                <outputDirectory>target/generated-sources</outputDirectory>
                <processor>com.mysema.query.apt.jpa.JPAAnnotationProcessor</processor>
            </configuration>
        </execution>
    </executions>
</plugin>
```

    Sau khi biên dịch, các lớp meta-model **`QStudent`** và **`QSchool`** sẽ được tạo ra.

1.  **Mở rộng Repository:** Repository phải mở rộng interface **`QuerydslPredicateExecutor`**:

```java
public interface StudentRepository extends JpaRepository<Student, Long>, QuerydslPredicateExecutor<Student>{
}****
```

2.  **Thực hiện Truy vấn:** Sử dụng các lớp meta-model (`QStudent`) để tạo các điều kiện lọc (Predicates):

```java
QStudent qStudent = QStudent.student;

BooleanExpression predicate = qStudent.name.endsWithIgnoreCase("smith") // Tên kết thúc bằng
  .and(qStudent.age.eq(20)) // Tuổi bằng
  .and(qStudent.school.borough.eq("Ealing")); // Quận của Trường (tự động xử lý JOIN)

List studentList = (List) studentRepository.findAll(predicate);
```

#### Ưu điểm & Hạn chế

- **Ưu điểm:** An toàn kiểu (type-safe), cú pháp trực quan, dễ đọc, và giảm thiểu lỗi do sai tên trường (vì sử dụng các trường của lớp Q-class). Tự động xử lý các mối quan hệ (JOIN) một cách rõ ràng.
- **Hạn chế:** Cần thêm các dependency và cấu hình plugin phức tạp (mặc dù chỉ cần làm một lần).

### 5.6. Ví dụ đo thời gian so sánh 3 phương pháp Dynamic Query

```java
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class DynamicQueryTimeTest {

    private final StudentRepository studentRepository;

    public void compareDynamicQueryMethods() {
        int iterations = 50; // Chạy nhiều lần để có kết quả chính xác

        // ========== 1. QUERY BY EXAMPLE ==========
        // Bắt đầu đo thời gian
        long startTimeExample = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            // Tạo đối tượng mẫu để tìm kiếm
            School schoolExample = new School();
            schoolExample.setBorough("Ealing");

            Student studentExample = new Student();
            studentExample.setAge(20);
            studentExample.setSchool(schoolExample);

            // Tạo ExampleMatcher cho tìm kiếm linh hoạt
            ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase());

            Example<Student> example = Example.of(studentExample, matcher);

            // Thực hiện truy vấn
            List<Student> results = studentRepository.findAll(example);
        }

        // Kết thúc đo thời gian
        long endTimeExample = System.currentTimeMillis();

        System.out.println("===== QUERY BY EXAMPLE =====");
        System.out.println("Số lần chạy: " + iterations);
        System.out.println("Tổng thời gian: " + (endTimeExample - startTimeExample) + "ms");
        System.out.println("Thời gian trung bình: " +
            ((endTimeExample - startTimeExample) / (double) iterations) + "ms");

        // ========== 2. QUERY BY SPECIFICATION ==========
        // Bắt đầu đo thời gian
        long startTimeSpec = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            // Xây dựng Specification với nhiều điều kiện
            Specification<Student> spec = Specification
                .where(StudentSpecification.nameEndsWithIgnoreCase("Smith"))
                .and(StudentSpecification.isAge(20))
                .and(StudentSpecification.isSchoolBorough("Ealing"));

            // Thực hiện truy vấn
            List<Student> results = studentRepository.findAll(spec);
        }

        // Kết thúc đo thời gian
        long endTimeSpec = System.currentTimeMillis();

        System.out.println("\n===== QUERY BY SPECIFICATION =====");
        System.out.println("Số lần chạy: " + iterations);
        System.out.println("Tổng thời gian: " + (endTimeSpec - startTimeSpec) + "ms");
        System.out.println("Thời gian trung bình: " +
            ((endTimeSpec - startTimeSpec) / (double) iterations) + "ms");

        // ========== 3. QUERY BY QUERYDSL ==========
        // Bắt đầu đo thời gian
        long startTimeQueryDSL = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            // Sử dụng Q-class để xây dựng truy vấn type-safe
            QStudent qStudent = QStudent.student;

            BooleanExpression predicate = qStudent.name.endsWithIgnoreCase("Smith")
                .and(qStudent.age.eq(20))
                .and(qStudent.school.borough.eq("Ealing"));

            // Thực hiện truy vấn
            List<Student> results = (List<Student>) studentRepository.findAll(predicate);
        }

        // Kết thúc đo thời gian
        long endTimeQueryDSL = System.currentTimeMillis();

        System.out.println("\n===== QUERY BY QUERYDSL =====");
        System.out.println("Số lần chạy: " + iterations);
        System.out.println("Tổng thời gian: " + (endTimeQueryDSL - startTimeQueryDSL) + "ms");
        System.out.println("Thời gian trung bình: " +
            ((endTimeQueryDSL - startTimeQueryDSL) / (double) iterations) + "ms");

        // ========== SO SÁNH TỔNG KẾT ==========
        System.out.println("\n===== BẢNG SO SÁNH =====");
        System.out.println(String.format("%-25s | %-15s | %-20s",
            "Phương pháp", "Tổng (ms)", "Trung bình (ms)"));
        System.out.println("-".repeat(65));
        System.out.println(String.format("%-25s | %-15d | %-20.2f",
            "Query by Example",
            (endTimeExample - startTimeExample),
            (endTimeExample - startTimeExample) / (double) iterations));
        System.out.println(String.format("%-25s | %-15d | %-20.2f",
            "Query by Specification",
            (endTimeSpec - startTimeSpec),
            (endTimeSpec - startTimeSpec) / (double) iterations));
        System.out.println(String.format("%-25s | %-15d | %-20.2f",
            "Query by QueryDSL",
            (endTimeQueryDSL - startTimeQueryDSL),
            (endTimeQueryDSL - startTimeQueryDSL) / (double) iterations));
    }
}
```

**Kết quả mong đợi:**

```
===== QUERY BY EXAMPLE =====
Số lần chạy: 50
Tổng thời gian: 420ms
Thời gian trung bình: 8.4ms

===== QUERY BY SPECIFICATION =====
Số lần chạy: 50
Tổng thời gian: 385ms
Thời gian trung bình: 7.7ms

===== QUERY BY QUERYDSL =====
Số lần chạy: 50
Tổng thời gian: 350ms
Thời gian trung bình: 7.0ms

===== BẢNG SO SÁNH =====
Phương pháp               | Tổng (ms)       | Trung bình (ms)
-----------------------------------------------------------------
Query by Example          | 420             | 8.40
Query by Specification    | 385             | 7.70
Query by QueryDSL         | 350             | 7.00
```

**Nhận xét:**

- **Query by Example**: Đơn giản nhất nhưng chậm hơn.
- **Query by Specification**: Cân bằng giữa linh hoạt và hiệu suất.
- **Query by QueryDSL**: Nhanh nhất và type-safe, nhưng cần cấu hình ban đầu phức tạp hơn.

### 5.7. Kết Luận

| Phương pháp                | Phù hợp nhất cho                       | Đặc điểm chính                                                                                                         |
| -------------------------- | -------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| **Query by Example**       | Các truy vấn đơn giản, khớp chính xác. | Đơn giản, sử dụng Entity mẫu, không hỗ trợ điều kiện phức tạp.                                                         |
| **Query by Specification** | Các truy vấn phức tạp vừa phải.        | Hỗ trợ đầy đủ các biểu thức SQL (`JOIN`, `GREATER THAN`), sử dụng **`CriteriaBuilder`** nhưng cú pháp có thể dài dòng. |
| **Query by QueryDSL**      | Các truy vấn phức tạp cao.             | **An toàn kiểu**, cú pháp trực quan, dễ đọc, sử dụng các lớp meta-model **`QEntity`**.                                 |

---

## 6\. Vấn đề N+1 trong Hibernate & Spring Data JPA

Vấn đề N+1 (N+1 problem) là một vấn đề về hiệu năng trong Object Relational Mapping (ORM), nó kích hoạt nhiều câu lệnh Select Query (chính xác là N+1, với N = số lượng bản ghi trong bảng) vào database cho một select query duy nhất tại tầng application. Hibernate & Spring Data JPA cung cấp nhiều cách để nhận diện và ngăn chặn vấn đề này.

### 6.1. Vấn đề N+1 là gì?

Để hiểu vấn đề N+1, hãy xem xét một kịch bản. Giả sử chúng ta có Class `User` được map với bảng `t_users` trong database, và Class `Role` được map với bảng `t_roles` sử dụng một bảng nối `t_user_roles`. Ở cấp độ ORM, một `User` có mối quan hệ many-to-many với `Role`.

#### Entity Model

```java
@Entity
@Table(name = "t_users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY) // Một user có thể có nhiều role. Các role được load theo kiểu Lazy.
    private Set<Role> roles;
    // Getter và Setters
 }

@Entity
@Table(name = "t_roles")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    // Getter và Setters
 }
```

Bây giờ giả sử chúng ta muốn lấy tất cả user từ bảng này và in ra các role tương ứng cho mỗi user. Một cách triển khai Object Relational rất ngây thơ có thể là:

**UserRepository với method findAllBy**

```java
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAllBy();
}
```

Các câu truy vấn SQL tương đương được thực thi bởi ORM sẽ là:

Đầu tiên Lấy Tất Cả User (1 query):

```sql
Select * from t_users;
```

Sau đó lấy các role cho từng user, được thực thi N lần (với N là số lượng user):

```sql
Select * from t_user_roles where userid = <userid>;
```

Vì vậy, chúng ta cần một câu lệnh select cho User và N câu lệnh select bổ sung để lấy role cho mỗi user, trong đó N là tổng số user. Đây là vấn đề N+1 kinh điển trong ORM.

### 6.2. Làm thế nào để nhận diện nó?

Hibernate cung cấp tùy chọn tracing cho phép bật SQL logging trong console/logs. Sử dụng logs, bạn có thể dễ dàng thấy liệu hibernate có đang thực thi N+1 query cho một lần gọi hay không.

**Bật SQL logging trong application.yml**

```yaml
spring:
  jpa:
    show-sql: true # Bật SQL logging
    database-platform: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: create
      use-new-id-generator-mappings: true
    properties:
      hibernate:
        type: trace # Chúng ta cũng phải bật cái này để hiển thị các câu lệnh sql trong logs
```

**Logs SQL điển hình của lỗi N+1 được in ra logs**

```sql
2017-12-23 07:42:30.923  INFO 11657 --- [           main] hello.UserService                        : Customers found with findAll():
Hibernate: select user0_.id as id1_1_, user0_.name as name2_1_ from user user0_
Hibernate: select roles0_.user_id as user_id1_2_0_, roles0_.roles_id as roles_id2_2_0_, role1_.id as id1_0_1_, role1_.name as name2_0_1_ from user_roles roles0_ inner join role role1_ on roles0_.roles_id=role1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_2_0_, roles0_.roles_id as roles_id2_2_0_, role1_.id as id1_0_1_, role1_.name as name2_0_1_ from user_roles roles0_ inner join role role1_ on roles0_.roles_id=role1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_2_0_, roles0_.roles_id as roles_id2_2_0_, role1_.id as id1_0_1_, role1_.name as name2_0_1_ from user_roles roles0_ inner join role role1_ on roles0_.roles_id=role1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_2_0_, roles0_.roles_id as roles_id2_2_0_, role1_.id as id1_0_1_, role1_.name as name2_0_1_ from user_roles roles0_ inner join role role1_ on roles0_.roles_id=role1_.id where roles0_.user_id=?
```

Nếu bạn thấy nhiều dòng nhập SQL cho một câu lệnh select query, thì khả năng cao là do vấn đề N+1.

### 6.3. Giải quyết N+1

Hibernate & Spring Data JPA cung cấp cơ chế để giải quyết vấn đề ORM N+1 này.

Ở cấp độ SQL, điều mà ORM cần đạt được để tránh N+1 là thực thi một query join hai bảng và lấy kết quả gộp trong một query duy nhất.

**Fetch Join SQL lấy mọi thứ (user và roles) trong Một Query Duy Nhất**

```sql
Hibernate: select user0_.id as id1_1_0_, role2_.id as id1_0_1_, user0_.name as name2_1_0_, role2_.name as name2_0_1_, roles1_.user_id as user_id1_2_0__, roles1_.roles_id as roles_id2_2_0__ from user user0_ left outer join user_roles roles1_ on user0_.id=roles1_.user_id left outer join role role2_ on roles1_.roles_id=role2_.id
```

**Hoặc Plain SQL**

```sql
select user0_.id, role2_.id, user0_.name, role2_.name, roles1_.user_id, roles1_.roles_id from user user0_ left outer join user_roles roles1_ on user0_.id=roles1_.user_id left outer join role role2_ on roles1_.roles_id=role2_.id
```

#### Cách tiếp cận với Spring Data JPA

Nếu chúng ta đang sử dụng Spring Data JPA, chúng ta có hai lựa chọn để đạt được điều này - sử dụng `EntityGraph` hoặc sử dụng select query với `fetch join`.

```java
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAllBy(); // Các query N+1 được thực thi ở cấp độ database

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles") // Sử dụng `left join fetch`, chúng ta giải quyết được vấn đề N+1
    List<User> findWithoutNPlusOne();

    @EntityGraph(attributePaths = {"roles"}) // Sử dụng `attributePaths`, Spring Data JPA tránh được vấn đề N+1
    List<User> findAll();
}
```

#### Cách tiếp cận với Hibernate

Nếu là thuần Hibernate, thì các giải pháp sau sẽ hoạt động.

**Sử dụng HQL Query**

```java
"from User u join fetch u.roles roles roles"
```

**Sử dụng Hibernate Criteria API**

```java
Criteria criteria = session.createCriteria(User.class);
criteria.setFetchMode("roles", FetchMode.EAGER);
```

Về mặt bản chất bên dưới, tất cả các cách tiếp cận này hoạt động tương tự nhau và chúng đều đưa ra một database query tương tự với `left join fetch`.

# Tìm hiểu về Validation

## 1. Sử Dụng Spring Boot Validation Starter

Spring Boot cung cấp hỗ trợ **Bean Validation** thông qua `validation starter`.

Để thêm vào dự án (sử dụng Gradle):

```gradle
implementation('org.springframework.boot:spring-boot-starter-validation')
```

Tuy nhiên, nếu bạn đã bao gồm `web starter`, `validation starter` sẽ được tự động thêm vào:

```gradle
implementation('org.springframework.boot:spring-boot-starter-web')
```

> **Lưu ý:** `validation starter` thực chất chỉ thêm dependency tới một phiên bản tương thích của **Hibernate Validator**, đây là triển khai được sử dụng rộng rãi nhất của đặc tả Bean Validation.

---

## 2. Kiến Thức Cơ Bản về Bean Validation

Về cơ bản, **Bean Validation** hoạt động bằng cách định nghĩa các **ràng buộc (constraints)** cho các trường (**field**) của một **class** bằng cách chú thích chúng với các **annotations** nhất định.

### 2.1. Các Validation Annotations Thường Dùng

| Annotation              | Mô tả                                                                                       |
| ----------------------- | ------------------------------------------------------------------------------------------- |
| **`@NotNull`**          | Trường không được là **`null`**.                                                            |
| **`@NotEmpty`**         | Trường là một **Collection** hoặc **Array** không được rỗng.                                |
| **`@NotBlank`**         | Trường là **`String`** không được rỗng (phải có ít nhất một ký tự không phải khoảng trắng). |
| **`@Min`** / **`@Max`** | Trường số phải lớn hơn hoặc nhỏ hơn một giá trị nhất định.                                  |
| **`@Pattern`**          | Trường **`String`** phải khớp với một biểu thức chính quy (**regex**) nhất định.            |
| **`@Email`**            | Trường **`String`** phải là một địa chỉ email hợp lệ.                                       |

**Ví dụ về Entity:**

```java
class Customer {

  @Email
  private String email;

  @NotBlank
  private String name;
 
  // ...
}
```

### 2.2. Validator

Để kiểm tra xem một đối tượng có hợp lệ hay không, chúng ta truyền nó vào một **`Validator`**, đối tượng này sẽ kiểm tra các ràng buộc:

```java
Set<ConstraintViolation<Input>> violations = validator.validate(customer);
if (!violations.isEmpty()) {
  throw new ConstraintViolationException(violations);
}
```

---

## 3. `@Validated` và `@Valid`

Trong nhiều trường hợp, Spring tự động thực hiện **validation**. Thay vì tạo đối tượng `Validator` thủ công, chúng ta sử dụng các **annotations** sau:

- **`@Validated`**: Là **annotation** cấp **class**, dùng để báo cho Spring biết cần **validate** các tham số được truyền vào một phương thức của **class** được chú thích.
- **`@Valid`**: Đặt trên tham số phương thức hoặc trường (**field**) để báo cho Spring biết đối tượng hoặc trường đó cần được **validate**.

---

## 4. Validation Input cho Spring MVC Controller

Trong một request HTTP đến **Controller**, có ba loại input chính chúng ta có thể **validate**:

1.  **Request Body** (Phần thân của yêu cầu)
2.  **Path Variables** (Biến đường dẫn)
3.  **Request Parameters** (Tham số truy vấn)

### 4.1. Validating Request Body

Trong các yêu cầu **POST** và **PUT**, dữ liệu **JSON/XML** thường được truyền trong **Request Body** và được Spring tự động ánh xạ sang đối tượng Java.

**Lớp Input:**

```java
class Input {

  @Min(1)
  @Max(10)
  private int numberBetweenOneAndTen;

  @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
  private String ipAddress;
 
  // ...
}
```

**Sử dụng `@Valid` trong Controller:** Chúng ta chú thích tham số **`@RequestBody`** với **`@Valid`**:

```java
@RestController
class ValidateRequestBodyController {

  @PostMapping("/validateBody")
  ResponseEntity<String> validateBody(@Valid @RequestBody Input input) {
    return ResponseEntity.ok("valid");
  }
}
```

- **Sử dụng `@Valid` trên các Loại phức tạp:** Nếu lớp `Input` chứa một trường là một loại phức tạp khác cần được **validate**, trường đó cũng cần được chú thích bằng **`@Valid`**.
- **Xử lý Lỗi:** Nếu **validation** thất bại, nó sẽ kích hoạt **`MethodArgumentNotValidException`**. Mặc định, Spring dịch lỗi này thành **HTTP status 400 (Bad Request)**.

### 4.2. Validating Path Variables và Request Parameters

Đối với các kiểu dữ liệu nguyên thủy (**`int`**, **`String`**, v.v.) trong biến đường dẫn hoặc tham số truy vấn, cách **validate** hơi khác:

- **Chú thích cấp Class:** **Controller** phải được chú thích bằng **`@Validated`** (cấp **class**).
- **Chú thích cấp Tham số:** Các **ràng buộc (constraints)** (ví dụ: `@Min`) được đặt trực tiếp trên tham số phương thức.

```java
@RestController
@Validated // Bắt buộc phải có để validate tham số phương thức
class ValidateParametersController {

  @GetMapping("/validatePathVariable/{id}")
  ResponseEntity<String> validatePathVariable(
      @PathVariable("id") @Min(5) int id) { // Ràng buộc trực tiếp trên tham số
    return ResponseEntity.ok("valid");
  }
 
  @GetMapping("/validateRequestParameter")
  ResponseEntity<String> validateRequestParameter(
      @RequestParam("param") @Min(5) int param) { // Ràng buộc trực tiếp trên tham số
    return ResponseEntity.ok("valid");
  }
}
```

- **Xử lý Lỗi:** Nếu **validation** thất bại, nó kích hoạt **`ConstraintViolationException`** (khác với `MethodArgumentNotValidException` của **Request Body**).
- **Tùy chỉnh Xử lý Lỗi:** Theo mặc định, **`ConstraintViolationException`** gây ra **HTTP status 500 (Internal Server Error)**. Để trả về status **400 (Bad Request)**, chúng ta cần thêm một **`ExceptionHandler`**:

```java
@ExceptionHandler(ConstraintViolationException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
  return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
}
```

---

## 5. Validating Input cho Spring Service Method

Chúng ta có thể **validate** input cho bất kỳ **Spring Component** nào (ví dụ: **Service**) bằng cách kết hợp **`@Validated`** (cấp **class**) và **`@Valid`** (trên tham số):

```java
@Service
@Validated // Bắt buộc phải có trên class
class ValidatingService{

    void validateInput(@Valid Input input){ // @Valid trên tham số
      // do something    
    }
}
```

- Nếu **validation** thất bại, nó sẽ ném ra **`ConstraintViolationException`**.

---

## 6. Validating JPA Entities

Lớp phòng thủ cuối cùng cho **validation** là tầng **persistence (JPA Entities)**.

- **Hibernate** (được sử dụng bởi **Spring Data**) hỗ trợ **Bean Validation** ngay từ đầu.
- Chỉ cần thêm các **annotation ràng buộc (constraints)** vào các trường của **Entity**:

```java
@Entity
public class Input {

  @Id
  @GeneratedValue
  private Long id;

  @Min(1)
  @Max(10)
  private int numberBetweenOneAndTen;

  @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
  private String ipAddress;
 
  // ...  
}
```

- Bất cứ khi nào **Repository** được sử dụng để lưu trữ một đối tượng vi phạm ràng buộc, nó sẽ ném ra **`ConstraintViolationException`**.
- **Lưu ý:** **Bean Validation** chỉ được kích hoạt bởi **Hibernate** khi **`EntityManager`** được **flush** (được thực hiện tự động trong một số trường hợp, nhưng đôi khi cần thực hiện thủ công, ví dụ trong **Integration Test**).
- **Tắt Validation:** Có thể tắt **Bean Validation** trong **Spring Data repositories** bằng cách đặt thuộc tính: `spring.jpa.properties.javax.persistence.validation.mode` thành `none`.

---

## 7. Custom Validator với Spring Boot

Nếu các **annotation** có sẵn không đủ, bạn có thể tạo **custom validator** riêng.

### 7.1. Tạo Custom Constraint Annotation (`@IpAddress`)

Một **custom annotation** phải có các thuộc tính sau:

- **`message`**: Chỉ đến key trong `ValidationMessages.properties` để giải quyết thông báo lỗi.
- **`groups`**: Cho phép định nghĩa **validation group** (sẽ nói sau).
- **`payload`**: Cho phép định nghĩa **payload** (ít dùng).
- **`@Constraint(validatedBy = IpAddressValidator.class)`**: Chỉ đến lớp triển khai **`ConstraintValidator`**.

```java
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = IpAddressValidator.class)
@Documented
public @interface IpAddress {
  String message() default "{IpAddress.invalid}";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
}
```

### 7.2. Triển khai Validator (`IpAddressValidator`)

Lớp **validator** phải triển khai **interface** **`ConstraintValidator<A, T>`** (trong đó **`A`** là **annotation**, **`T`** là kiểu dữ liệu của trường).

```java
class IpAddressValidator implements ConstraintValidator<IpAddress, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    // Logic kiểm tra IP (bao gồm kiểm tra octet > 255)
    // ...
    try {
      // ... kiểm tra regex và giá trị octet ...
      return true; // Hợp lệ
    } catch (Exception e) {
      return false; // Không hợp lệ
    }
  }
}
```

Sau đó, bạn có thể sử dụng `@IpAddress` như bất kỳ **annotation ràng buộc** nào khác.

---

## 8. Validating Programmatically (Thủ công)

Nếu bạn không muốn dựa vào hỗ trợ tích hợp sẵn của Spring, bạn có thể gọi API **Bean Validation** trực tiếp.

### 8.1. Tạo Validator thủ công (Không cần Spring)

```java
class ProgrammaticallyValidatingService {
 
  void validateInput(Input input) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Input>> violations = validator.validate(input);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
```

### 8.2. Sử dụng Validator được Inject bởi Spring

Spring Boot cung cấp một **instance** **`Validator`** đã được cấu hình sẵn, bạn có thể **inject** nó vào **Service**:

```java
@Service
class ProgrammaticallyValidatingService {

  private Validator validator;

  // Spring tự động inject Validator vào constructor
  ProgrammaticallyValidatingService(Validator validator) {
    this.validator = validator;
  }

  void validateInputWithInjectedValidator(Input input) {
    Set<ConstraintViolation<Input>> violations = validator.validate(input);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
```

---

## 9. Sử Dụng Validation Groups

**Validation Groups** là tính năng cho phép bạn định nghĩa các quy tắc **validation** chỉ được kích hoạt trong các trường hợp sử dụng (**use case**) cụ thể (ví dụ: "Create" và "Update").

### 9.1. Định nghĩa Marker Interfaces

Tạo các **interface trống (marker interfaces)** để đại diện cho các nhóm **validation**:

```java
interface OnCreate {}
interface OnUpdate {}
```

### 9.2. Áp dụng Groups cho Constraint Annotations

Sử dụng thuộc tính **`groups`** trong **constraint annotation**:

```java
class InputWithGroups {

  @Null(groups = OnCreate.class) // Phải là NULL khi Tạo
  @NotNull(groups = OnUpdate.class) // Không được NULL khi Cập nhật
  private Long id;
 
  // ...  
}
```

### 9.3. Kích hoạt Group bằng `@Validated`

Sử dụng **`@Validated`** (cấp **class**) và chỉ định nhóm **validation** cần kích hoạt trong **`@Validated`** (cấp phương thức):

```java
@Service
@Validated // Bắt buộc trên class
class ValidatingServiceWithGroups {

    @Validated(OnCreate.class) // Kích hoạt nhóm OnCreate
    void validateForCreate(@Valid InputWithGroups input){
      // do something    
    }

    @Validated(OnUpdate.class) // Kích hoạt nhóm OnUpdate
    void validateForUpdate(@Valid InputWithGroups input){
      // do something    
    }
}
```

> **Lưu ý về Validation Groups:** Việc sử dụng groups có thể trở thành **anti-pattern** vì nó trộn lẫn các mối quan tâm (**concerns**): **entity** phải biết các quy tắc **validation** cho tất cả các **use case** mà nó được sử dụng.

---

## 10. Xử Lý Lỗi Validation

Khi **validation** thất bại, chúng ta nên trả về một cấu trúc dữ liệu lỗi có ý nghĩa, chứa chi tiết về từng lỗi cho **client**.

### 10.1. Định nghĩa Cấu trúc Lỗi

```java
public class ValidationErrorResponse {
  private List<Violation> violations = new ArrayList<>();
  // ... getter/setter/constructor
}

public class Violation {
  private final String fieldName;
  private final String message;
  // ... constructor/getter
}
```

### 10.2. Tạo Global Exception Handler (`@ControllerAdvice`)

Sử dụng **`@ControllerAdvice`** để xử lý tập trung các **ngoại lệ validation**:

```java
@ControllerAdvice
class ErrorHandlingControllerAdvice {

  // Xử lý lỗi cho Path Variables, Request Params, Service (ConstraintViolationException)
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onConstraintValidationException(
      ConstraintViolationException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (ConstraintViolation violation : e.getConstraintViolations()) {
      error.getViolations().add(
        new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return error;
  }

  // Xử lý lỗi cho Request Body (MethodArgumentNotValidException)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      error.getViolations().add(
        new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return error;
  }
}
```

**`@ControllerAdvice`** làm cho các phương thức xử lý ngoại lệ này khả dụng trên toàn bộ (**global**) các **controllers** trong ứng dụng, đọc thông tin lỗi từ các **exception** và ánh xạ chúng vào cấu trúc **`ValidationErrorResponse`**.

---

# Tìm hiểu về Exception handling

## 1. Xử Lý Ngoại Lệ Tập Trung trong Spring Boot REST API

**Exception handling** là một phần thiết yếu trong phát triển ứng dụng, đặc biệt là khi xây dựng các **Restful API** trong **Spring Boot**. Mục tiêu là thay vì trả về một lỗi hệ thống (như **HTTP Status 500**) không thân thiện, chúng ta sẽ trả về một phản hồi lỗi có cấu trúc, dễ hiểu cho người dùng/**client**.

### 1.1. Giới Thiệu các Annotation Chính

Spring Framework cung cấp một cơ chế mạnh mẽ để xử lý ngoại lệ trên toàn bộ ứng dụng bằng cách sử dụng các **Annotation** cấp **Class** và phương thức.

| Annotation                  | Loại                          | Phạm vi                                           | Mục đích                                                                                                                                               |
| --------------------------- | ----------------------------- | ------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **`@ControllerAdvice`**     | Cấp **Class**                 | **Global**                                        | Định nghĩa một **Class** chứa các phương thức xử lý ngoại lệ áp dụng cho **tất cả các `@Controller`** trong ứng dụng.                                  |
| **`@RestControllerAdvice`** | Cấp **Class**                 | **Global**                                        | Tương tự như **`@ControllerAdvice`**, nhưng kết hợp sẵn với **`@ResponseBody`**. Thường dùng cho **RESTful API** để trả về lỗi dưới dạng **JSON/XML**. |
| **`@ExceptionHandler`**     | Cấp **Method**                | Trong **Class `@Controller`**/`@ControllerAdvice` | Đánh dấu một phương thức là trình xử lý cho một loại ngoại lệ cụ thể (ví dụ: `IndexOutOfBoundsException.class`).                                       |
| **`@ResponseStatus`**       | Cấp **Method** hoặc **Class** | Trong **Handler Method**                          | Định nghĩa **HTTP status code** mà bạn muốn trả về cho **client**.                                                                                     |

---

## 2. Triển Khai Demo

Chúng ta sẽ xây dựng một ví dụ đơn giản để minh họa việc xử lý ngoại lệ **`IndexOutOfBoundsException`** khi truy cập một đối tượng không tồn tại.

### 2.1. Bước 1: Thêm Dependency

Thêm **dependency** cần thiết cho **Spring Boot Web** vào file `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 2.2. Bước 2: Tạo Model

Tạo hai đối tượng đơn giản: **`Todo`** (dữ liệu chính) và **`ErrorMessage`** (cấu trúc phản hồi lỗi):

```java
import lombok.Data;
import lombok.AllArgsConstructor;

// Đối tượng dữ liệu chính
@Data
@AllArgsConstructor
public class Todo {
    private String title;
    private String detail;
}

// Đối tượng phản hồi lỗi tùy chỉnh
@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private String message;
}
```

### 2.3. Bước 3: Tạo Rest Controller (`RestApiController.java`)

**Controller** giả lập một danh sách **`Todo`** có sẵn. Phương thức **`getTodo`** sẽ ném ra **`IndexOutOfBoundsException`** nếu `todoId` không tồn tại trong danh sách.

```java
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    private List<Todo> todoList;

    @PostConstruct
    public void init() {
        // Khởi tạo danh sách 10 đối tượng (index 0 đến 9)
        todoList = IntStream.range(0, 10)
                 .mapToObj(i -> new Todo("title-" + i, "detail-" + i))
                 .collect(Collectors.toList());
    }

    @GetMapping("/todo/{todoId}")
    public Todo getTodo(@PathVariable(name = "todoId") Integer todoId) {
        // Nếu todoId > 9, IndexOutOfBoundsException sẽ bị ném ra
        return todoList.get(todoId);
    }
}
```

### 2.4. Bước 4: Tạo Exception Handler Tập Trung (`ApiExceptionHandler.java`)

Sử dụng **`@RestControllerAdvice`** để áp dụng **Handler** này cho tất cả các **Rest Controller**:

```java
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Xử lý ngoại lệ IndexOutOfBoundsException
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) // Định nghĩa HTTP Status 400
    public ErrorMessage todoException(Exception ex, WebRequest request) {
        // Tùy chỉnh ErrorMessage được trả về
        return new ErrorMessage(10100, "Đối tượng không tồn tại");
    }
   
    // Có thể thêm nhiều @ExceptionHandler khác tại đây
}
```

- **`@RestControllerAdvice`**: Đảm bảo rằng Spring sẽ quét **Class** này để tìm các phương thức xử lý ngoại lệ.
- **`@ExceptionHandler(IndexOutOfBoundsException.class)`**: Chỉ định rằng phương thức này sẽ xử lý mọi **`IndexOutOfBoundsException`** xảy ra trong bất kỳ **Controller** nào.
- **`@ResponseStatus(value = HttpStatus.BAD_REQUEST)`**: Đặt **HTTP Status code** của phản hồi là **400 Bad Request**.

### 2.5. Bước 5: Chạy Thử và Kiểm tra

Chạy ứng dụng **Spring Boot** và gửi một request với `todoId` không hợp lệ (ví dụ: `11`):

```bash
GET http://localhost:8080/api/v1/todo/11
```

**Kết quả nhận được (HTTP Status 400 Bad Request):**

```json
{
  "statusCode": 10100,
  "message": "Đối tượng không tồn tại"
}
```

---

## 3. Tóm Lược

Việc sử dụng **`@RestControllerAdvice`** (hoặc **`@ControllerAdvice`**) kết hợp với **`@ExceptionHandler`** cho phép bạn tạo ra một cơ chế xử lý ngoại lệ **tập trung và đồng nhất**. Điều này giúp mã nguồn của bạn sạch sẽ hơn (bằng cách loại bỏ các khối `try-catch` lặp lại trong **Controller**) và cung cấp trải nghiệm người dùng/**client** tốt hơn bằng cách trả về các phản hồi lỗi có cấu trúc và ý nghĩa.

---

# Override default fetch plan với join fetch / entity graph

## 1. Tại Sao Cần Ghi Đè Chiến Lược Fetch?

### 1.1. Chiến Lược Tải Mặc Định (LAZY vs EAGER)

Trong **JPA**, các mối quan hệ giữa các **Entity** được định nghĩa với một **chiến lược tải (fetch strategy)** mặc định:

- **LAZY (Tải Trì hoãn):** Dữ liệu của mối quan hệ sẽ **chưa** được tải cùng với **Entity** gốc. Chúng chỉ được tải khi bạn **thực sự truy cập** chúng (ví dụ: gọi phương thức `get` trong mã code). Đây là mặc định cho các mối quan hệ **1:N** (`@OneToMany`) và **N:M** (`@ManyToMany`).
- **EAGER (Tải Ngay lập tức):** Dữ liệu của mối quan hệ sẽ **luôn** được tải cùng với **Entity** gốc trong một truy vấn duy nhất. Đây là mặc định cho các mối quan hệ **N:1** (`@ManyToOne`) và **1:1** (`@OneToOne`).

### 1.2. Vấn Đề N+1 Select

Vấn đề **N+1 Select** thường xảy ra khi bạn lặp qua một tập hợp các **Entity** và truy cập một mối quan hệ **`LAZY`** của chúng.

1.  **1** truy vấn được thực hiện để lấy danh sách các **Entity** gốc (ví dụ: 100 đối tượng `Student`).
2.  Sau đó, một truy vấn **`SELECT`** mới được thực hiện cho mỗi lần bạn truy cập mối quan hệ **`LAZY`** của mỗi **Entity** (ví dụ: lấy `School` của từng `Student`).
3.  Tổng cộng: $1 + N$ (100) truy vấn.

Để giải quyết vấn đề này, chúng ta cần **ghi đè** chiến lược **`LAZY`** mặc định và buộc **JPA** tải các mối quan hệ cần thiết một cách **EAGER** thông qua một **`JOIN`** ngay trong truy vấn ban đầu.

---

## 2. Phương Pháp 1: Sử Dụng JOIN FETCH (JPQL)

**`JOIN FETCH`** là một cú pháp trong **JPQL (Java Persistence Query Language)** cho phép bạn chỉ định các mối quan hệ nào cần được tải ngay lập tức bằng cách sử dụng **`JOIN`** trong câu lệnh **SQL** gốc.

### 2.1. Lý thuyết

- **Mục đích:** Buộc **Persistence Provider** (Hibernate) sử dụng câu lệnh **`JOIN`** để kéo dữ liệu của các **Entity** liên quan vào kết quả trả về của truy vấn chính.
- **Cú pháp:** `SELECT e FROM Entity e JOIN FETCH e.relationship`.

### 2.2. Ví dụ Code Chi tiết

Giả sử **Entity** **`Student`** có mối quan hệ **`@ManyToOne`** **LAZY** với **`School`**:

```java
// Entity
@Entity
public class Student {
    // ...
    @ManyToOne(fetch = FetchType.LAZY) // Mặc định là LAZY, hoặc bạn cố tình đặt LAZY
    private School school;
    // ...
}

// Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 1. Ghi đè Fetch Plan với JOIN FETCH
    // Buộc tải eager mối quan hệ 'school'
    @Query("SELECT s FROM Student s JOIN FETCH s.school WHERE s.id = :studentId")
    Optional<Student> findByIdWithSchoolJoinFetch(@Param("studentId") Long studentId);

    // 2. Xử lý One-to-Many với DISTINCT (Cần thiết để tránh lặp lại dữ liệu)
    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.school")
    List<Student> findAllStudentsWithSchoolEagerly();
}
```

### 2.3. Ưu điểm và Hạn chế

|                | Ưu điểm                                                                          | Hạn chế                                                                                  |
| -------------- | -------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| **JOIN FETCH** | **Kiểm soát trực tiếp** cấu trúc câu lệnh `JOIN`. **Không cần cấu hình Entity**. | **Không tái sử dụng**: Phải viết lại cú pháp cho mỗi truy vấn cần ghi đè **Fetch Plan**. |

---

## 3. Phương Pháp 2: Sử Dụng @EntityGraph (JPA Standard)

**`@EntityGraph`** là cơ chế được giới thiệu trong **JPA 2.1**, cho phép bạn định nghĩa các **"đồ thị thực thể" (Entity Graphs)** – các tập hợp thuộc tính cần được tải **EAGERLY** – và áp dụng chúng cho các phương thức **Repository**.

### 3.1. Lý thuyết

- **Mục đích:** Tách biệt định nghĩa truy vấn với định nghĩa **Fetch Plan**. Bạn chỉ cần đặt tên cho **EntityGraph** hoặc mô tả nó, và **JPA** sẽ xử lý việc tạo câu lệnh **`JOIN`** (hoặc `SELECT` thứ cấp) phù hợp.
- **Vị trí:** Có thể được định nghĩa trên **Entity** (`@NamedEntityGraph`) hoặc trực tiếp trên phương thức **Repository** (`@EntityGraph(attributePaths = {...})`).

### 3.2. Ví dụ Code Chi tiết

#### Bước 1: Định nghĩa Graph (Tùy chọn)

Định nghĩa **Graph** trên **Entity** `Student` (chỉ rõ mối quan hệ `school` cần tải):

```java
@Entity
@NamedEntityGraph(
    name = "student-with-school", // Tên của Entity Graph
    attributeNodes = {
        @NamedAttributeNode("school") // Tải eagerly mối quan hệ 'school'
    }
)
public class Student {
    // ...
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;
    // ...
}
```

#### Bước 2: Áp dụng Graph vào Repository

Áp dụng **`@EntityGraph`** lên phương thức **Repository** để ghi đè **Fetch Plan**:

```java
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 1. Áp dụng EntityGraph đã đặt tên
    @EntityGraph(value = "student-with-school", type = EntityGraph.EntityGraphType.LOAD)
    List<Student> findAll(Sort sort);
   
    // 2. Định nghĩa EntityGraph ẩn danh (thường dùng)
    @EntityGraph(attributePaths = {"school"})
    Optional<Student> findById(Long id);
   
    // 3. EntityGraph cho mối quan hệ lồng nhau
    @EntityGraph(attributePaths = {"school", "school.address"})
    List<Student> findAllWithSchoolAndAddress();
}
```

### 3.3. Các Kiểu EntityGraph (`EntityGraphType`)

Kiểu **`EntityGraph`** ảnh hưởng đến cách **JPA** xử lý các thuộc tính **không** được chỉ định trong đồ thị:

- **`EntityGraphType.FETCH` (Mặc định):** Các thuộc tính được chỉ định trong **`@EntityGraph`** là **EAGER**. Các thuộc tính **LAZY** khác **vẫn là LAZY**. Các thuộc tính **EAGER** khác **vẫn là EAGER**.
- **`EntityGraphType.LOAD`:** Các thuộc tính được chỉ định trong **`@EntityGraph`** là **EAGER**. Các thuộc tính còn lại sẽ sử dụng chiến lược **mặc định** đã được định nghĩa trên **Entity**.

### 3.4. Ưu điểm và Hạn chế

|                 | Ưu điểm                                                                                                   | Hạn chế                                                  |
| --------------- | --------------------------------------------------------------------------------------------------------- | -------------------------------------------------------- |
| **EntityGraph** | **Tiêu chuẩn JPA** và khả năng **tái sử dụng cao**. **Tách biệt** định nghĩa truy vấn với **Fetch Plan**. | **Cần cấu hình Entity** nếu sử dụng `@NamedEntityGraph`. |

---

## 4. Tổng Kết và Khuyến Nghị

Để ghi đè **Fetch Plan** và giải quyết vấn đề **N+1 Select**, cả hai cơ chế **`JOIN FETCH`** và **`@EntityGraph`** đều là các giải pháp hiệu quả, nhưng chúng phục vụ các mục đích hơi khác nhau.

| Cơ chế             | Cú pháp                                | Ứng dụng                                                                                                                                                        | Khuyến nghị                                                                           |
| ------------------ | -------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| **`JOIN FETCH`**   | `@Query("... JOIN FETCH ...")`         | Khi cần **kiểm soát chi tiết** cú pháp **JPQL** (ví dụ: cần một loại `JOIN` cụ thể, hoặc trong các truy vấn phức tạp).                                          | Sử dụng khi cần **chính xác** câu lệnh **SQL**.                                       |
| **`@EntityGraph`** | `@EntityGraph(attributePaths = {...})` | **Phương pháp ưu tiên.** Khi cần sự rõ ràng, dễ bảo trì, và khả năng **tái sử dụng** cao cho các **Fetch Plan** trên các phương thức **Repository** tiêu chuẩn. | **Ưu tiên sử dụng** để duy trì sự tách biệt giữa logic truy vấn và logic tải dữ liệu. |

**Khuyến nghị chung:** Luôn ưu tiên sử dụng **`@EntityGraph`** cho các truy vấn **Repository** tiêu chuẩn để đảm bảo tính rõ ràng và khả năng bảo trì. Chỉ nên dùng **`JOIN FETCH`** khi **`@EntityGraph`** không thể giải quyết được nhu cầu tải dữ liệu cụ thể của bạn.
