# Các mối quan hệ(1..N, N..N) trong JPA (@ManyToMany, @OneToMany,...)

## Mối Quan Hệ One-to-One (Một-đối-Một)

Mối quan hệ One-to-One xảy ra khi một thực thể A được liên kết với **duy nhất một** thực thể B, và ngược lại, thực thể B cũng chỉ được liên kết với **duy nhất một** thực thể A. Ví dụ điển hình là mối quan hệ giữa một người và số căn cước công dân của họ, hoặc một nhân viên và thông tin chi tiết về lương của họ (nếu tách riêng).

Để mô hình hóa mối quan hệ One-to-One trong JPA, chúng ta sử dụng annotation `@OneToOne`. Một trong hai thực thể sẽ là “owning side” (bên sở hữu), nơi khóa ngoại thực sự tồn tại trong bảng CSDL. Bên còn lại sẽ là “inverse side” (bên đảo ngược) và sử dụng thuộc tính `mappedBy` để chỉ ra trường nào ở bên sở hữu định nghĩa mối quan hệ này.

### Ví dụ: Người và Thông Tin Chi Tiết

Giả sử chúng ta có hai thực thể: `Nguoi` (Person) và `ThongTinChiTiet` (Detail Information). Mỗi người có một bộ thông tin chi tiết duy nhất.

```java
@Entity
public class Nguoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;

    // Owning side: Nguoi holds the foreign key to ThongTinChiTiet
    @OneToOne(cascade = CascadeType.ALL) // cascade = ALL means save/delete Nguoi will cascade to ThongTinChiTiet
    @JoinColumn(name = "thong_tin_chi_tiet_id", referencedColumnName = "id") // Specifies the FK column
    private ThongTinChiTiet thongTinChiTiet;

    // Getters and Setters...
}

@Entity
public class ThongTinChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaChi;
    private String soDienThoai;

    // Inverse side: Uses mappedBy to reference the field in the owning side
    @OneToOne(mappedBy = "thongTinChiTiet")
    private Nguoi nguoi;

    // Getters and Setters...
}
```

Trong ví dụ này:

- `Nguoi` là bên sở hữu (owning side). Annotation `@JoinColumn(name = "thong_tin_chi_tiet_id", referencedColumnName = "id")` chỉ ra rằng bảng `nguoi` trong CSDL sẽ có một cột tên là `thong_tin_chi_tiet_id`, đây là khóa ngoại tham chiếu đến cột `id` trong bảng `thong_tin_chi_tiet`.
- `ThongTinChiTiet` là bên đảo ngược (inverse side). Annotation `@OneToOne(mappedBy = "thongTinChiTiet")` chỉ ra rằng mối quan hệ này được quản lý bởi trường `thongTinChiTiet` trong lớp `Nguoi`. Bên đảo ngược không chứa khóa ngoại trong bảng CSDL tương ứng của nó.
- `cascade = CascadeType.ALL` trên bên sở hữu (`Nguoi`) nghĩa là bất kỳ thao tác nào (persist, merge, remove, refresh, detach) được thực hiện trên một thực thể `Nguoi` cũng sẽ tự động được thực hiện trên thực thể `ThongTinChiTiet` liên quan. Đây là một cách quản lý vòng đời của các thực thể liên quan.

Khi lưu một đối tượng `Nguoi` mới cùng với `ThongTinChiTiet` liên quan, JPA sẽ tự động insert cả hai và thiết lập khóa ngoại.

## Mối Quan Hệ One-to-Many / Many-to-One (Một-đối-Nhiều / Nhiều-đối-Một)

Đây là mối quan hệ phổ biến nhất. Một thực thể A có thể liên kết với **nhiều** thực thể B, nhưng mỗi thực thể B chỉ liên kết với **duy nhất một** thực thể A. Ví dụ: một tác giả viết nhiều sách, nhưng mỗi cuốn sách chỉ có một tác giả chính; một danh mục chứa nhiều sản phẩm, nhưng mỗi sản phẩm chỉ thuộc một danh mục.

Trong CSDL, mối quan hệ này thường được biểu diễn bằng cách đặt khóa ngoại trong bảng “many” (bên “nhiều”) tham chiếu đến khóa chính của bảng “one” (bên “một”). Ví dụ, bảng `Books` có cột `author_id` tham chiếu bảng `Authors`.

Trong JPA, chúng ta sử dụng `@OneToMany` trên bên “một” và `@ManyToOne` trên bên “nhiều”. Theo quy ước và thực tế, bên “nhiều” (sở hữu khóa ngoại trong CSDL) thường là bên sở hữu (owning side) trong JPA. Vì vậy, `@ManyToOne` thường là bên sở hữu.

### Ví dụ: Tác Giả và Sách

Chúng ta có hai thực thể: `TacGia` (Author) và `Sach` (Book). Một tác giả có thể viết nhiều sách.

```java
@Entity
public class TacGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenTacGia;

    // Inverse side: One-to-Many relationship. Mapped by the 'tacGia' field in the Sach entity.
    @OneToMany(mappedBy = "tacGia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sach> danhSachSach;

    // Constructor to initialize the list
    public TacGia() {
        this.danhSachSach = new ArrayList<>();
    }

    // Helper method to add a book and maintain bidirectional link
    public void addSach(Sach sach) {
        danhSachSach.add(sach);
        sach.setTacGia(this); // Set the owning side
    }

    // Helper method to remove a book
    public void removeSach(Sach sach) {
        danhSachSach.remove(sach);
        sach.setTacGia(null); // Break the link
    }

    // Getters and Setters...
}

@Entity
public class Sach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tieuDe;

    // Owning side: Many-to-One relationship. Sach holds the foreign key to TacGia.
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading is often better for performance
    @JoinColumn(name = "tac_gia_id") // Specifies the FK column in the 'sach' table
    private TacGia tacGia;

    // Getters and Setters...
}
```

Trong ví dụ này:

- `Sach` là bên sở hữu (owning side) với `@ManyToOne`. Annotation `@JoinColumn(name = "tac_gia_id")` chỉ ra rằng bảng `sach` có cột `tac_gia_id` là khóa ngoại tham chiếu đến bảng `tac_gia`. Đây là nơi JPA sẽ quản lý việc thiết lập và cập nhật khóa ngoại.
- `TacGia` là bên đảo ngược (inverse side) với `@OneToMany`. `mappedBy = "tacGia"` chỉ ra rằng mối quan hệ này được quản lý bởi trường `tacGia` trong lớp `Sach`.
- Chúng ta thường sử dụng các Collection như `List` hoặc `Set` để biểu diễn bên “nhiều” (`danhSachSach` trong `TacGia`).
- `fetch = FetchType.LAZY` trên `@ManyToOne` (và thường là default cho `@OneToMany`) là một tối ưu hiệu suất. Nó chỉ tải dữ liệu của `TacGia` khi bạn truy cập trường `tacGia` của một đối tượng `Sach`, thay vì tải ngay lập tức khi load đối tượng `Sach` (EAGER).
- `cascade = CascadeType.ALL` và `orphanRemoval = true` trên `@OneToMany` giúp quản lý danh sách sách của tác giả. `orphanRemoval = true` đặc biệt hữu ích: nếu một cuốn sách bị xóa khỏi danh sách `danhSachSach` của một tác giả và cuốn sách đó không còn được liên kết với tác giả nào khác, JPA sẽ tự động xóa cuốn sách đó khỏi CSDL.
- Các phương thức helper `addSach` và `removeSach` trong `TacGia` là cách tốt để đảm bảo tính nhất quán của mối quan hệ hai chiều (bidirectional relationship) bằng cách thiết lập liên kết ở cả hai phía.

## Mối Quan Hệ Many-to-Many (Nhiều-đối-Nhiều)

Mối quan hệ Many-to-Many xảy ra khi một thực thể A có thể liên kết với **nhiều** thực thể B, và một thực thể B cũng có thể liên kết với **nhiều** thực thể A. Ví dụ: một sinh viên có thể đăng ký nhiều khóa học, và một khóa học có thể có nhiều sinh viên đăng ký; một sản phẩm có thể có nhiều thẻ (tags), và một thẻ có thể được áp dụng cho nhiều sản phẩm.

Trong CSDL, mối quan hệ Many-to-Many không thể biểu diễn trực tiếp bằng một khóa ngoại đơn lẻ. Nó yêu cầu một bảng trung gian (linking table, join table) chứa hai khóa ngoại, mỗi khóa ngoại tham chiếu đến khóa chính của một trong hai bảng gốc.

Trong JPA, chúng ta sử dụng `@ManyToMany` trên cả hai thực thể. Một trong hai bên sẽ định nghĩa bảng trung gian bằng `@JoinTable`.

### Ví dụ: Sinh Viên và Khóa Học

Chúng ta có hai thực thể: `SinhVien` (Student) và `KhoaHoc` (Course). Một sinh viên có thể học nhiều khóa học, và một khóa học có nhiều sinh viên.

```java
@Entity
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenSinhVien;

    // Owning side: Defines the join table
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }) // Configure cascade types carefully
    @JoinTable(
        name = "sinh_vien_khoa_hoc", // Name of the linking table
        joinColumns = @JoinColumn(name = "sinh_vien_id"), // FK column in linking table referencing SinhVien
        inverseJoinColumns = @JoinColumn(name = "khoa_hoc_id") // FK column in linking table referencing KhoaHoc
    )
    private Set<KhoaHoc> danhSachKhoaHoc = new HashSet<>();

    // Helper methods to add/remove courses
    public void addKhoaHoc(KhoaHoc khoaHoc) {
        this.danhSachKhoaHoc.add(khoaHoc);
        khoaHoc.getDanhSachSinhVien().add(this); // Maintain bidirectional link
    }

    public void removeKhoaHoc(KhoaHoc khoaHoc) {
        this.danhSachKhoaHoc.remove(khoaHoc);
        khoaHoc.getDanhSachSinhVien().remove(this); // Maintain bidirectional link
    }

    // Getters and Setters...
}

@Entity
public class KhoaHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenKhoaHoc;

    // Inverse side: Mapped by the field in the owning side
    @ManyToMany(mappedBy = "danhSachKhoaHoc")
    private Set<SinhVien> danhSachSinhVien = new HashSet<>();

     // Helper methods to add/remove students (often mirror those in the owning side)
    public void addSinhVien(SinhVien sinhVien) {
        this.danhSachSinhVien.add(sinhVien);
        sinhVien.getDanhSachKhoaHoc().add(this); // Maintain bidirectional link
    }

    public void removeSinhVien(SinhVien sinhVien) {
        this.danhSachSinhVien.remove(sinhVien);
        sinhVien.getDanhSachKhoaHoc().remove(this); // Maintain bidirectional link
    }


    // Getters and Setters...
}
```

Trong ví dụ này:

- Chúng ta sử dụng `Set` cho các collection ở cả hai phía để tránh các bản ghi trùng lặp trong bảng trung gian.
- `SinhVien` được chọn làm bên sở hữu (owning side). Nó định nghĩa bảng trung gian `sinh_vien_khoa_hoc` bằng `@JoinTable`.
  - `name = "sinh_vien_khoa_hoc"`: Tên của bảng trung gian.
  - `joinColumns = @JoinColumn(name = "sinh_vien_id")`: Cột khóa ngoại trong bảng trung gian tham chiếu đến `SinhVien`.
  - `inverseJoinColumns = @JoinColumn(name = "khoa_hoc_id")`: Cột khóa ngoại trong bảng trung gian tham chiếu đến `KhoaHoc`.
- `KhoaHoc` là bên đảo ngược (inverse side). `mappedBy = "danhSachKhoaHoc"` chỉ ra rằng mối quan hệ được quản lý bởi trường `danhSachKhoaHoc` trong lớp `SinhVien`.
- `cascade` trên mối quan hệ `@ManyToMany` cần được cân nhắc kỹ lưỡng. `PERSIST` và `MERGE` là phổ biến để lưu/cập nhật các thực thể liên quan, nhưng `REMOVE` thường không mong muốn (ví dụ: xóa một SinhVien không nên xóa tất cả KhóaHoc mà họ đã đăng ký).

**Lưu ý quan trọng:** Khi mối quan hệ Many-to-Many cần chứa thêm dữ liệu (ví dụ: ngày đăng ký khóa học, điểm số của sinh viên trong khóa học đó), mô hình bảng trung gian đơn thuần sẽ không đủ. Trong trường hợp này, cách tốt hơn là tạo một thực thể riêng cho bảng trung gian (ví dụ: `Enrollment`) và mô hình hóa mối quan hệ Many-to-Many thành hai mối quan hệ One-to-Many: `SinhVien` One-to-Many `Enrollment` và `KhoaHoc` One-to-Many `Enrollment`. Đây là một kỹ thuật rất phổ biến và linh hoạt.

## Các Khía Cạnh Cần Cân Nhắc

Khi làm việc với các mối quan hệ trong JPA, có vài điều quan trọng cần lưu ý:

1.  **Owning Side và Inverse Side:** Hiểu rõ bên nào là bên sở hữu là rất quan trọng. Bên sở hữu là bên quản lý (thêm/xóa/cập nhật) khóa ngoại trong CSDL. Trong mối quan hệ hai chiều, chỉ thao tác trên bên sở hữu mới thực sự ảnh hưởng đến dữ liệu trong CSDL. Bên đảo ngược chỉ là một cách thuận tiện để điều hướng qua mối quan hệ từ phía kia.
2.  **`mappedBy`:** Thuộc tính này chỉ được sử dụng trên bên đảo ngược của mối quan hệ hai chiều. Nó trỏ đến tên trường ở bên sở hữu định nghĩa cùng một mối quan hệ.
3.  **`@JoinColumn` và `@JoinTable`:**
    - `@JoinColumn`: Được sử dụng trên bên sở hữu của mối quan hệ One-to-One và Many-to-One để chỉ định tên cột khóa ngoại trong bảng của bên sở hữu.
    - `@JoinTable`: Được sử dụng trên bên sở hữu của mối quan hệ Many-to-Many để định nghĩa bảng trung gian và các cột khóa ngoại của nó.
4.  **Fetch Types (`FetchType.LAZY` vs `FetchType.EAGER`):**
    - `EAGER` (mặc định cho `@OneToOne`, `@ManyToOne`): Tải dữ liệu của các thực thể liên quan ngay lập tức khi thực thể chính được tải. Có thể gây lãng phí tài nguyên nếu bạn không luôn cần dữ liệu liên quan.
    - `LAZY` (mặc định cho `@OneToMany`, `@ManyToMany`): Chỉ tải dữ liệu của các thực thể liên quan khi bạn truy cập trường tương ứng lần đầu tiên. Giúp tối ưu hiệu suất bằng cách chỉ tải những gì cần thiết, nhưng có thể dẫn đến vấn đề N+1 Select nếu không được quản lý đúng cách (ví dụ: lặp qua một danh sách và truy cập mối quan hệ lazy cho từng mục trong vòng lặp).

    Nên ưu tiên sử dụng `LAZY` và sử dụng các kỹ thuật như fetch joins trong JPQL/HQL hoặc các tính năng của Spring Data JPA để tải trước dữ liệu EAGERLY khi cần thiết cho các trường hợp sử dụng cụ thể.

5.  **Cascade Types (`CascadeType`):**
    - `PERSIST`: Thao tác persist (lưu) thực thể chính sẽ cascade đến thực thể liên quan.
    - `MERGE`: Thao tác merge (cập nhật) thực thể chính sẽ cascade đến thực thể liên quan.
    - `REMOVE`: Thao tác remove (xóa) thực thể chính sẽ cascade đến thực thể liên quan. Cần rất cẩn thận với cái này, đặc biệt trong mối quan hệ Many-to-Many.
    - `ALL`: Cascade tất cả các thao tác (PERSIST, MERGE, REMOVE, REFRESH, DETACH).
    - `orphanRemoval = true` (chỉ trên `@OneToMany`, `@OneToOne`): Nếu một thực thể con bị gỡ bỏ khỏi collection của thực thể cha, nó sẽ bị xóa khỏi CSDL.

    Việc cấu hình cascade đúng cách giúp quản lý vòng đời của các thực thể liên quan một cách tự động, nhưng cấu hình sai có thể dẫn đến mất dữ liệu không mong muốn.

6.  **Bidirectional vs Unidirectional:**
    - **Unidirectional:** Mối quan hệ chỉ được định nghĩa ở một phía (chỉ có liên kết từ A sang B, không có từ B về A). Đơn giản hơn để code, nhưng bạn không thể điều hướng ngược lại từ B về A thông qua mối quan hệ JPA.
    - **Bidirectional:** Mối quan hệ được định nghĩa ở cả hai phía (có liên kết từ A sang B và từ B về A). Cho phép điều hướng linh hoạt hơn, nhưng đòi hỏi code phức tạp hơn một chút để quản lý tính nhất quán ở cả hai phía (như các helper methods `addSach`/`removeSach` đã thấy).

    Chọn loại nào phụ thuộc vào yêu cầu của ứng dụng.

## Tóm Tắt Các Loại Mối Quan Hệ

Để dễ hình dung, đây là bảng tóm tắt các điểm chính của ba loại mối quan hệ:

| Mối Quan Hệ                           | Annotation (Bên Sở hữu)          | Annotation (Bên Đảo ngược, nếu hai chiều) | Cấu Trúc Bảng CSDL                                                             | Vai Trò Khóa Ngoại/Bảng Trung Gian     | Ví Dụ Phổ Biến                                 |
| ------------------------------------- | -------------------------------- | ----------------------------------------- | ------------------------------------------------------------------------------ | -------------------------------------- | ---------------------------------------------- |
| One-to-One (1:1)                      | `@OneToOne` (với `@JoinColumn`)  | `@OneToOne` (với `mappedBy`)              | Một FK trong bảng của bên sở hữu trỏ đến PK của bên kia.                       | Bên sở hữu chứa FK.                    | Người <-> Thông tin chi tiết cá nhân           |
| One-to-Many (1:N) / Many-to-One (N:1) | `@ManyToOne` (với `@JoinColumn`) | `@OneToMany` (với `mappedBy`)             | Một FK trong bảng “nhiều” (Many side) trỏ đến PK của bảng “một” (One side).    | Bên “nhiều” (`@ManyToOne`) chứa FK.    | Tác giả <-> Sách, Danh mục <-> Sản phẩm        |
| Many-to-Many (N:M)                    | `@ManyToMany` (với `@JoinTable`) | `@ManyToMany` (với `mappedBy`)            | Một bảng trung gian chứa hai FK, mỗi FK trỏ đến PK của một trong hai bảng gốc. | Bên sở hữu định nghĩa bảng trung gian. | Sinh viên <-> Khóa học, Sản phẩm <-> Thẻ (Tag) |

# Derived query methods (truy vấn theo tên hàm) && JPQL với @Query && Native query (SQL thuần)

## 1\. Viết truy vấn tự động theo tên hàm

Spring Data JPA cung cấp cú pháp đặt tên đặc biệt:

```
List<Employee> findByName(String name);
List<Employee> findBySalaryGreaterThan(double salary);
List<Employee> findByEmailContainingIgnoreCase(String email);
List<Employee> findByPositionAndSalary(String position, double salary);
```

> Spring sẽ phân tích cú pháp tên hàm và tự sinh câu lệnh SQL tương ứng.

### Quy tắc đặt tên method

Tên hàm trong Repository phải bắt đầu bằng tiền tố như:

- `findBy`, `readBy`, `getBy`, `queryBy`

Sau đó là tên field trong entity viết đúng CamelCase, nối với các từ khóa điều kiện:

- `And`, `Or`, `Between`, `LessThan`, `GreaterThan`, `Like`, `IsNull`, `IsNotNull`, `In`, `NotIn`, `StartingWith`, `EndingWith`, `Containing`, `IgnoreCase`…

Ví dụ:

```
List<Employee> findByDepartmentAndSalaryGreaterThan(String department, double salary);
Optional<Employee> findFirstByNameIgnoreCase(String name);
Page<Employee> findByPositionContaining(String position, Pageable pageable);
```

> Field name **phải trùng với tên thuộc tính trong entity**, không phải tên cột trong database.

## 2\. Truy vấn nâng cao với tên hàm

Spring Data JPA hỗ trợ nhiều loại truy vấn nâng cao chỉ bằng cách đặt tên hàm:

- **Truy vấn kết hợp nhiều điều kiện**:

```
List<Employee> findByPositionOrDepartment(String position, String department);
```

- **Truy vấn với mệnh đề BETWEEN**:

```
List<Employee> findBySalaryBetween(double min, double max);
```

- **Truy vấn có sắp xếp (dynamic)**:

```
List<Employee> findByDepartment(String dept, Sort sort);
```

- **Truy vấn với danh sách (IN)**:

```
List<Employee> findByIdIn(List<Long> ids);
```

- **Truy vấn null/not null**:

```
List<Employee> findByEmailIsNull();
List<Employee> findByEmailIsNotNull();
```

Kết hợp với `Pageable`, bạn có thể truy vấn nâng cao + phân trang cùng lúc:

```
Page<Employee> findByPositionContainingIgnoreCase(String keyword, Pageable pageable);
```

## 3\. Sử dụng @Query – viết truy vấn tùy chỉnh

Khi tên hàm không đủ linh hoạt, bạn có thể viết truy vấn với annotation `@Query`.

```
@Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
List<Employee> findHighSalary(@Param("minSalary") double minSalary);
```

Bạn có thể dùng `@Query` cho cả truy vấn `SELECT`, `UPDATE`, `DELETE`. Có thể dùng thêm `@Modifying` nếu truy vấn thay đổi dữ liệu.

## 4\. JPQL là gì? Khác gì với HQL?

**JPQL (Java [Persistence] Query Language)** là ngôn ngữ truy vấn hướng đối tượng, chuẩn của **JPA**. JPQL sử dụng **tên class, tên thuộc tính**, không dùng tên bảng/column trong DB.

Ví dụ JPQL:

```
@Query("SELECT e FROM Employee e WHERE e.email LIKE %:email%")
```

**HQL (Hibernate Query Language)** là ngôn ngữ truy vấn tương tự JPQL nhưng là **một phần riêng của Hibernate**.

> Về cơ bản, cú pháp JPQL và HQL rất giống nhau. Tuy nhiên:
>
> - **JPQL** là chuẩn của JPA – dùng được với các JPA provider khác (như EclipseLink)
> - **HQL** là độc quyền của Hibernate, hỗ trợ thêm một vài tính năng mở rộng không có trong JPQL

**Bạn nên ưu tiên dùng JPQL để đảm bảo tương thích về sau.** Nhưng nếu chắc chắn bạn chỉ dùng Hibernate, bạn hoàn toàn có thể tận dụng cú pháp mở rộng của HQL trong query.

**Nếu bạn muốn dùng HQL trong Spring Data JPA, thực chất bạn vẫn đang dùng nó gián tiếp, vì:**

- Hibernate là JPA provider
- HQL là tiền thân và tương thích với JPQL
- Khi dùng `@Query` và entity/field (như ví dụ trên), Hibernate thực thi chính là HQL

\=> **JPQL và HQL gần như tương thích** vì JPQL được sinh ra từ HQL. Hibernate có thể xử lý cả hai theo cách tương tự khi biên dịch truy vấn sang SQL.

Tuy nhiên, điểm khác biệt quan trọng nằm ở **API mà bạn sử dụng để chạy truy vấn**:

- Nếu bạn làm việc thông qua `EntityManager` hoặc annotation `@Query` của Spring Data JPA, bạn đang sử dụng **JPQL** – ngôn ngữ truy vấn chuẩn của JPA.
- Nếu bạn làm việc trực tiếp với API gốc của Hibernate như `Session` và gọi `session.createQuery(...)`, lúc đó bạn đang sử dụng **HQL**.

Các cú pháp như `class(e)` hay `TYPE(e)` vốn có nguồn gốc từ HQL nhưng đã được **chuẩn hóa trong JPQL**, vì vậy chúng **không còn là “mở rộng riêng” của Hibernate nữa** và có thể sử dụng an toàn trong Spring Data JPA:

```
@Query("SELECT class(e) FROM Employee e")List<Class<? extends Employee>> getEntityClass();﻿
```

Nếu bạn muốn dùng **tính năng mở rộng thực sự của HQL** – tức là cú pháp hoặc hàm mà JPA không hỗ trợ – bạn phải làm việc trực tiếp với `Session`. Ví dụ:

```
Session session = entityManager.unwrap(Session.class);List<Integer> indexes = session.createQuery(    "SELECT index(e.phones) FROM Employee e", Integer.class).getResultList();﻿
```

Trong ví dụ trên, `index()` là một hàm mở rộng đặc thù của Hibernate, không nằm trong chuẩn JPQL.

## 5\. Native Query

Khi cần viết truy vấn SQL thuần túy:

```
@Query(value = "SELECT * FROM employee WHERE salary > ?1", nativeQuery = true)
List<Employee> findNative(double minSalary);
```

Dùng khi cần tận dụng cú pháp SQL riêng của từng DB (JOIN, subquery phức tạp…).

## 6\. Truy vấn phân trang và sắp xếp

Spring Data hỗ trợ sẵn:

```
Page<Employee> findAll(Pageable pageable);
List<Employee> findByPosition(String position, Sort sort);
```

```
Pageable pageable = PageRequest.of(0, 10, Sort.by("salary").descending());
Page<Employee> page = employeeRepository.findAll(pageable);
```

> Bạn có thể dễ dàng phân trang kết quả (Page), sắp xếp (Sort), đếm tổng số dòng (`page.getTotalElements()`).

# Truy vấn sử dụng EntityManager trong Spring Boot

Trong Spring Boot, `EntityManager` là một interface thuộc JPA, dùng để tương tác với cơ sở dữ liệu thông qua các Entity (lớp Java ánh xạ với bảng trong database).

`EntityManager` cho phép thực hiện các thao tác như truy vấn dữ liệu, thêm, sửa, xóa mà không cần viết SQL thuần trong mọi trường hợp.

## 1. Các cách truy vấn bằng EntityManager

Có 3 cách phổ biến:

- JPQL: Truy vấn dựa trên tên Entity và thuộc tính Java.
- Native SQL: Viết câu SQL trực tiếp để truy vấn bảng trong database.
- Criteria API: Xây dựng truy vấn bằng Java, phù hợp với điều kiện tìm kiếm động.

## 2. Chuẩn bị ví dụ

Giả sử có Entity `Student`:

```
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

    // Constructors, getters, setters
}
```

Inject `EntityManager` vào Service:

```
@Service
public class StudentService {

    @PersistenceContext
    private EntityManager entityManager;
}
```

`@PersistenceContext` giúp Spring cung cấp EntityManager để làm việc với persistence context.

## 3. Truy vấn bằng JPQL

JPQL truy vấn trên Entity và thuộc tính Java, không trực tiếp dựa vào tên bảng và tên cột trong database.

### 3.1. Lấy tất cả sinh viên

```
public List<Student> getAllStudents() {
    return entityManager
            .createQuery("SELECT s FROM Student s", Student.class)
            .getResultList();
}
```

Giải thích:

- `Student`: tên Entity, không phải tên bảng `students`.
- `s`: bí danh của Entity.
- `Student.class`: kiểu dữ liệu kết quả.
- `getResultList()`: trả về danh sách kết quả.

### 3.2. Tìm sinh viên theo ID

```
public Student getStudentById(Long id) {
    return entityManager.find(Student.class, id);
}
```

`find()` tìm Entity theo khóa chính. Nếu không tìm thấy, kết quả là `null`.

### 3.3. Tìm sinh viên theo tên

```
public List<Student> findByName(String name) {
    return entityManager.createQuery(
            "SELECT s FROM Student s WHERE s.name = :name",
            Student.class
    )
    .setParameter("name", name)
    .getResultList();
}
```

`:name` là tham số truy vấn, được gán giá trị bằng `setParameter()`. Cách này giúp tránh việc nối trực tiếp dữ liệu đầu vào vào câu truy vấn.

### 3.4. Tìm sinh viên theo khoảng tuổi

```
public List<Student> findByAge(Integer minAge, Integer maxAge) {
    return entityManager.createQuery(
            "SELECT s FROM Student s " +
            "WHERE s.age BETWEEN :minAge AND :maxAge",
            Student.class
    )
    .setParameter("minAge", minAge)
    .setParameter("maxAge", maxAge)
    .getResultList();
}
```

### 3.5. Sắp xếp và giới hạn kết quả

```
public List<Student> getTopStudents() {
    return entityManager.createQuery(
            "SELECT s FROM Student s ORDER BY s.age DESC",
            Student.class
    )
    .setMaxResults(5)
    .getResultList();
}
```

- `ORDER BY s.age DESC`: sắp xếp tuổi giảm dần.
- `setMaxResults(5)`: lấy tối đa 5 kết quả.

## 4. Truy vấn bằng Native SQL

Native SQL cho phép viết SQL theo cú pháp của hệ quản trị cơ sở dữ liệu.

```
public List<Student> getAllStudents() {
    return entityManager.createNativeQuery(
            "SELECT * FROM students",
            Student.class
    ).getResultList();
}
```

Ví dụ tìm sinh viên theo tuổi:

```
public List<Student> findByAge(Integer age) {
    return entityManager.createNativeQuery(
            "SELECT * FROM students WHERE age = :age",
            Student.class
    )
    .setParameter("age", age)
    .getResultList();
}
```

Khác biệt chính:

| JPQL                                   | Native SQL                        |
| -------------------------------------- | --------------------------------- |
| Dùng tên Entity                        | Dùng tên bảng                     |
| Dùng thuộc tính Java                   | Dùng tên cột database             |
| Tương đối độc lập với hệ quản trị CSDL | Có thể phụ thuộc hệ quản trị CSDL |
| Ví dụ: `Student`, `s.name`             | Ví dụ: `students`, `name`         |

## 5. Thêm, sửa và xóa dữ liệu

Các thao tác ghi dữ liệu thường cần `@Transactional` để quản lý giao dịch.

### 5.1. Thêm sinh viên

```
@Transactional
public void addStudent(Student student) {
    entityManager.persist(student);
}
```

`persist()` đưa Entity mới vào trạng thái được quản lý bởi persistence context.

### 5.2. Sửa sinh viên

```
@Transactional
public Student updateStudent(Long id, String name) {
    Student student = entityManager.find(Student.class, id);

    if (student != null) {
        student.setName(name);
    }

    return student;
}
```

Nếu Entity đang được quản lý, thay đổi thuộc tính sẽ được đồng bộ xuống database khi giao dịch commit; thông thường không cần gọi `merge()` trong trường hợp này.

### 5.3. Xóa sinh viên

```
@Transactional
public boolean deleteStudent(Long id) {
    Student student = entityManager.find(Student.class, id);

    if (student == null) {
        return false;
    }

    entityManager.remove(student);
    return true;
}
```

`remove()` đánh dấu Entity để xóa khi thay đổi được đồng bộ xuống database.

## 6. Criteria API

Criteria API hữu ích khi các điều kiện tìm kiếm thay đổi tùy theo dữ liệu đầu vào.

Ví dụ tìm sinh viên theo tuổi nếu người dùng truyền tuổi vào:

```
public List<Student> findByAge(Integer age) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Student> query =
            cb.createQuery(Student.class);

    Root<Student> student = query.from(Student.class);

    query.select(student)
         .where(cb.equal(student.get("age"), age));

    return entityManager.createQuery(query).getResultList();
}
```

Cần import các lớp:

```
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
```

## 7. Phân biệt EntityManager và JpaRepository

| Tiêu chí           | EntityManager           | JpaRepository            |
| ------------------ | ----------------------- | ------------------------ |
| Cách sử dụng       | Viết truy vấn trực tiếp | Có sẵn nhiều phương thức |
| Truy vấn tùy chỉnh | JPQL, SQL, Criteria API | Method naming, `@Query`  |
| Mức độ kiểm soát   | Cao                     | Đơn giản, thuận tiện     |
| Lượng code         | Thường nhiều hơn        | Thường ít hơn            |

Kết luận: Nếu chỉ cần các thao tác CRUD thông thường, `JpaRepository` thường thuận tiện hơn. Nếu cần kiểm soát chi tiết truy vấn, thao tác với persistence context hoặc xây dựng truy vấn động phức tạp, `EntityManager` là lựa chọn phù hợp.
