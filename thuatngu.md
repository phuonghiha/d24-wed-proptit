## 📊 BẢNG TỔNG HỢP (Sắp xếp A → Z)

| #   | Thuật ngữ                   | Loại              | Giải thích ngắn gọn                                          |
| --- | --------------------------- | ----------------- | ------------------------------------------------------------ |
| 1   | **Advice**                  | Khái niệm AOP     | Hành vi được thêm vào method (log, transaction...)           |
| 2   | **Annotation**              | Java              | "Nhãn dán" vào code, bắt đầu bằng `@`                        |
| 3   | **ApplicationContext**      | Interface         | BeanFactory mở rộng — Spring Boot dùng cái này               |
| 4   | **ApplicationContextAware** | Interface         | Cho Bean truy cập ApplicationContext                         |
| 5   | **AOP**                     | Khái niệm         | Aspect-Oriented Programming — lập trình hướng khía cạnh      |
| 6   | **Aspect**                  | Khái niệm AOP     | "Khía cạnh" — tập hợp các advice                             |
| 7   | **@Async**                  | Annotation        | Đánh dấu method chạy bất đồng bộ                             |
| 8   | **@Autowired**              | Annotation        | Tự động inject dependency                                    |
| 9   | **@Bean**                   | Annotation        | Method này tạo ra một Bean                                   |
| 10  | **Bean**                    | Khái niệm cốt lõi | Đối tượng Java do Spring tạo và quản lý                      |
| 11  | **BeanDefinition**          | Class             | "Bản thiết kế" mô tả cách tạo Bean                           |
| 12  | **BeanFactory**             | Interface         | Container gốc của Spring, tạo & quản lý Bean                 |
| 13  | **BeanFactoryAware**        | Interface         | Cho Bean truy cập BeanFactory                                |
| 14  | **BeanNameAware**           | Interface         | Cho Bean biết tên mình trong container                       |
| 15  | **BeanPostProcessor**       | Interface         | "Trạm kiểm soát" — can thiệp mọi Bean trước/sau init         |
| 16  | **@Cacheable**              | Annotation        | Cache kết quả của method                                     |
| 17  | **CGLIB Proxy**             | Kỹ thuật          | Proxy không cần interface (kế thừa class)                    |
| 18  | **Circular Dependency**     | Lỗi               | A cần B, B cần A → vòng lặp                                  |
| 19  | **Compile-time**            | Khái niệm         | Lúc biên dịch code                                           |
| 20  | **@Component**              | Annotation        | Đánh dấu class là một Bean                                   |
| 21  | **Component Scan**          | Khái niệm         | Spring quét package tìm class có annotation                  |
| 22  | **@ConditionalOnProperty**  | Annotation        | Chỉ tạo Bean nếu có property X                               |
| 23  | **@Configuration**          | Annotation        | Class chứa cấu hình Bean                                     |
| 24  | **Constructor**             | Java              | Hàm khởi tạo của class                                       |
| 25  | **Container**               | Khái niệm         | "Cái hộp" chứa và quản lý Bean                               |
| 26  | **@Controller**             | Annotation        | Bean tầng web MVC                                            |
| 27  | **Custom init-method**      | Kỹ thuật          | Method tùy chọn làm init trong `@Bean`                       |
| 28  | **Custom destroy-method**   | Kỹ thuật          | Method tùy chọn làm destroy trong `@Bean`                    |
| 29  | **Dependency**              | Khái niệm         | Phụ thuộc — class A cần class B                              |
| 30  | **DI**                      | Khái niệm cốt lõi | Dependency Injection — tiêm phụ thuộc                        |
| 31  | **DisposableBean**          | Interface         | Có method `destroy()` chạy khi Bean hủy                      |
| 32  | **DispatcherServlet**       | Spring MVC        | "Người điều phối" request                                    |
| 33  | **DTO**                     | Khái niệm         | Data Transfer Object — object chuyên chở dữ liệu             |
| 34  | **Eager Initialization**    | Khái niệm         | Tạo Bean ngay khi app start (mặc định)                       |
| 35  | **Embedded Server**         | Khái niệm         | Server (Tomcat) nhúng vào app                                |
| 36  | **Fat Jar / Uber Jar**      | Khái niệm         | File JAR chứa cả app + dependency                            |
| 37  | **Field**                   | Java              | Biến thành viên của class                                    |
| 38  | **Idempotent**              | Khái niệm         | Thao tác lặp lại vẫn cho kết quả như 1 lần                   |
| 39  | **InitializingBean**        | Interface         | Có method `afterPropertiesSet()` chạy sau inject             |
| 40  | **Instance**                | Java              | Một đối tượng cụ thể được tạo từ class                       |
| 41  | **Instantiation**           | Java              | Việc tạo instance từ class                                   |
| 42  | **Interface**               | Java              | "Hợp đồng" quy định các method phải có                       |
| 43  | **IoC**                     | Khái niệm cốt lõi | Inversion of Control — đảo ngược điều khiển                  |
| 44  | **Java Bean**               | Java              | Class có constructor rỗng + getter/setter (khác Spring Bean) |
| 45  | **JDK Dynamic Proxy**       | Kỹ thuật          | Proxy dùng interface                                         |
| 46  | **JSR-250**                 | Chuẩn Java        | Định nghĩa `@PostConstruct`, `@PreDestroy`                   |
| 47  | **@Lazy**                   | Annotation        | Chỉ tạo Bean khi thực sự cần                                 |
| 48  | **Lazy Initialization**     | Khái niệm         | Chỉ tạo Bean khi cần (không tạo lúc start)                   |
| 49  | **Method**                  | Java              | Hàm/hành vi của class                                        |
| 50  | **@Override**               | Annotation        | Method ghi đè method của cha                                 |
| 51  | **@PostConstruct**          | Annotation        | Method chạy SAU khi Bean inject xong                         |
| 52  | **@PreDestroy**             | Annotation        | Method chạy TRƯỚC khi Bean bị hủy                            |
| 53  | **@Primary**                | Annotation        | Ưu tiên Bean này khi có nhiều Bean cùng loại                 |
| 54  | **@Profile**                | Annotation        | Chỉ tạo Bean khi profile khớp                                |
| 55  | **Pointcut**                | Khái niệm AOP     | Điểm "cắt" — nơi advice áp dụng                              |
| 56  | **POJO**                    | Java              | Plain Old Java Object — class Java thuần                     |
| 57  | **Property**                | Cấu hình          | Cấu hình trong `application.properties` / `.yml`             |
| 58  | **Proxy**                   | Khái niệm         | "Người đại diện" bọc quanh Bean gốc                          |
| 59  | **@Qualifier**              | Annotation        | Inject Bean có tên cụ thể                                    |
| 60  | **Reflection**              | Java              | Java "tự soi" chính mình lúc runtime                         |
| 61  | **@Repository**             | Annotation        | Bean tầng truy cập dữ liệu (DAO)                             |
| 62  | **@Resource**               | Annotation        | Tương tự `@Autowired` nhưng theo tên                         |
| 63  | **@RestController**         | Annotation        | `@Controller` + trả về JSON                                  |
| 64  | **Runtime**                 | Khái niệm         | Lúc chương trình đang chạy                                   |
| 65  | **Scope**                   | Khái niệm         | Phạm vi tồn tại của Bean                                     |
| 66  | **@Scope("prototype")**     | Annotation        | Bean có scope prototype                                      |
| 67  | **@Service**                | Annotation        | Bean tầng nghiệp vụ (Business Logic)                         |
| 68  | **Singleton**               | Scope             | 1 instance duy nhất cho cả app (mặc định)                    |
| 69  | **Prototype**               | Scope             | Mỗi lần gọi → instance mới                                   |
| 70  | **Request**                 | Scope             | 1 instance / 1 HTTP request                                  |
| 71  | **Session**                 | Scope             | 1 instance / 1 user session                                  |
| 72  | **Application**             | Scope             | 1 instance / ServletContext                                  |
| 73  | **WebSocket**               | Scope             | 1 instance / WebSocket session                               |
| 74  | **Stereotype**              | Khái niệm         | Nhóm annotation `@Component`, `@Service`...                  |
| 75  | **@Transactional**          | Annotation        | Method chạy trong 1 transaction                              |
| 76  | **@Value**                  | Annotation        | Inject giá trị từ file cấu hình                              |
| 77  | **Wiring**                  | Khái niệm         | Việc "nối dây" các Bean với nhau                             |
| 78  | **Autowiring**              | Khái niệm         | Spring tự động nối dây dependency                            |
| 79  | **Context**                 | Viết tắt          | Viết tắt của `ApplicationContext`                            |
| 80  | **Bean Lifecycle**          | Khái niệm         | Vòng đời Bean từ lúc tạo đến lúc hủy                         |
| 81  | **Starter**                 | Spring Boot       | Gói dependency tiện lợi                                      |
| 82  | **Auto-configuration**      | Spring Boot       | Spring Boot tự cấu hình dựa trên dependency                  |
| 83  | **Actuator**                | Spring Boot       | Thư viện cung cấp endpoint giám sát app                      |
| 84  | **Aware**                   | Nhóm Interface    | Nhóm interface cho Bean "nhận biết" môi trường               |
