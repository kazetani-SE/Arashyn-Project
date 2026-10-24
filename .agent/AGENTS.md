# Arashyn — Agent Instructions (`AGENTS.md`)

Arashyn là nền tảng học ngôn ngữ, tập trung vào việc tổ chức, lưu trữ và luyện tập ngữ pháp. Hệ thống hướng đến việc kết hợp kho ngữ pháp có khả năng chia sẻ với không gian học tập riêng của từng người dùng.

Dự án sử dụng kiến trúc Client–Server, với Frontend React + TypeScript, Backend Java + Spring Boot và Supabase làm nền tảng Database/Auth. AWS được sử dụng cho các nhu cầu triển khai và lưu trữ phù hợp.

Các quy tắc trong tài liệu này nhằm giúp AI Agents hiểu đúng domain, tuân thủ kiến trúc hiện có, tránh over-engineering và tạo ra các thay đổi nhất quán với toàn bộ dự án.

---

## 1. Source of Truth

Trước khi thiết kế hoặc triển khai một tính năng, hãy kiểm tra các tài liệu và mã nguồn liên quan.

Các nguồn chuẩn cần ưu tiên:

- **Database Schema & Migrations:** Supabase migrations và schema hiện tại của dự án.
- **Backend:** Controller, Service, Repository, Entity, DTO và các cấu hình liên quan trong `Arashyn-BE/`.
- **Frontend:** Routes, API clients, hooks, types, stores và components trong `Arashyn-FE/`.
- **Domain terminology:** `GLOSSARY.md` tại repository root.
- **Environment & Deployment:** Các file cấu hình, script triển khai và tài liệu AWS hiện có.

Cấu trúc repository có thể thay đổi theo quá trình phát triển. Luôn kiểm tra cấu trúc thực tế trước khi tạo file hoặc đưa ra giả định về đường dẫn.

### Quy tắc ưu tiên

1. Yêu cầu cụ thể của task hiện tại.
2. Schema, migrations và hợp đồng API đã được xác lập.
3. Kiến trúc và coding conventions hiện có.
4. Các quy tắc trong `AGENTS.md` và `GLOSSARY.md`.
5. Đề xuất cải tiến của Agent.

Không tự ý thay đổi một quy ước đã được xác lập chỉ vì có một phương án khác phổ biến hơn.

Nếu tài liệu, schema và code mâu thuẫn nhau, hãy xác định điểm mâu thuẫn trước khi thay đổi. Không âm thầm chọn một phương án có thể gây breaking changes.

---

## 2. Engineering Principles

### 2.1. Keep the implementation simple

- Ưu tiên giải pháp đơn giản, dễ đọc, dễ bảo trì.
- Không tạo abstraction, layer, interface, factory hoặc design pattern nếu chưa có nhu cầu thực tế.
- Không xây dựng hệ thống tổng quát cho những yêu cầu mới chỉ mang tính giả định.
- Không tự ý mở rộng phạm vi task.
- Tái sử dụng cấu trúc và conventions đã có thay vì tạo thêm một cách triển khai song song.
- Khi có nhiều giải pháp, ưu tiên phương án đáp ứng yêu cầu với ít thay đổi và ít chi phí vận hành nhất.

### 2.2. Respect existing boundaries

- Không để Frontend tự quyết định quyền truy cập dữ liệu thay cho Backend hoặc Database.
- Không tin tưởng `user_id` do client gửi lên để xác định danh tính của người đang đăng nhập.
- Không để các service phụ thuộc lẫn nhau không cần thiết.
- Với những tác vụ không yêu cầu cùng một transaction, cân nhắc tách biệt luồng xử lý để lỗi ở một tác vụ không làm hỏng tác vụ còn lại.
- Không tự ý chuyển một hệ thống monolith thành microservices.

### 2.3. Avoid unnecessary cost

Arashyn ưu tiên chi phí thấp trong giai đoạn phát triển và học tập.

- Không thêm AWS services hoặc tài nguyên tính phí nếu chưa có nhu cầu rõ ràng.
- Ưu tiên sử dụng các dịch vụ hiện có trước khi đề xuất dịch vụ mới.
- Khi đề xuất hạ tầng mới, nêu rõ mục đích, chi phí tiềm năng và phương án đơn giản hơn nếu có.
- Không mặc định rằng một dịch vụ nằm trong Free Tier hoặc credit được cấp sẽ luôn miễn phí.

---

## 3. Backend Standards (`Arashyn-BE/`)

### 3.1. Technology

- Java 21.
- Spring Boot; phiên bản phải theo `pom.xml` hiện tại.
- Supabase PostgreSQL làm Database.
- Supabase Auth cho authentication và các luồng OAuth đã tích hợp.
- Spring Data JPA/Hibernate cho persistence khi phù hợp với cấu trúc hiện có.

Không tự ý nâng cấp Java, Spring Boot hoặc thư viện chính nếu task không yêu cầu.

### 3.2. Package structure

Backend sử dụng tổ chức code theo feature, với các thành phần được đặt gần nhau theo trách nhiệm.

Package gốc hiện tại:

`com.arashi.edu.arashynbe`

Các thành phần thường gặp:

- `controller`: HTTP endpoints và điều phối request/response.
- `service`: Business logic và điều phối use case.
- `repository`: Truy cập dữ liệu.
- `entity`: Persistence model.
- `dto`: Request/response contracts.

Đây là các trách nhiệm logic; không bắt buộc mọi feature phải có đầy đủ tất cả package nếu không cần thiết. Tuân thủ cấu trúc thực tế và quy ước đã có trong repository.

### 3.3. Service design

- Service chịu trách nhiệm xử lý nghiệp vụ của use case tương ứng.
- Không chuyển business logic vào Controller.
- Tránh để các service hỗ trợ gọi lẫn nhau theo chuỗi nếu có thể thiết kế luồng điều phối rõ ràng hơn.
- Với nghiệp vụ liên quan nhiều bước, xác định rõ transaction boundary.
- Không gọi trực tiếp transactional method thông qua `this` nếu Spring proxy không thể áp dụng transaction như mong đợi.
- Không thêm transaction cho các thao tác không cần tính nguyên tử.
- Tách biệt xử lý chính và các tác vụ phụ khi việc tách biệt thực sự mang lại lợi ích.

### 3.4. DTO and API contracts

- DTO phải phản ánh đúng ý nghĩa của request và response.
- Sử dụng Java records cho DTO phù hợp với quy ước hiện tại.
- Không trả Entity trực tiếp ra API nếu điều đó làm lộ persistence model hoặc tạo coupling không cần thiết.
- Không tạo DTO chỉ để bọc một giá trị khi không có lý do rõ ràng.
- Không tạo DTO riêng cho URL parameters nếu một parameter thông thường đã đủ.
- Giữ API response nhất quán với cấu trúc đã được dự án xác lập.

Trước khi thay đổi API, kiểm tra các Frontend hooks, types và nơi sử dụng endpoint tương ứng.

### 3.5. Authentication and authorization

- Danh tính người dùng phải được xác định từ security context đã được xác thực.
- Sử dụng `CurrentUser.get()`, `CurrentUser.getId()` và `CurrentUser.getRole()` theo nhu cầu.
- Không lấy danh tính của người đang đăng nhập từ `user_id` do client truyền lên.
- Role và quyền nghiệp vụ phải tuân thủ nguồn dữ liệu và quy tắc phân quyền hiện tại.
- Không coi việc ẩn hoặc vô hiệu hóa nút trên Frontend là biện pháp bảo mật.
- Kiểm tra quyền truy cập tại Backend và các chính sách Database liên quan.
- Với Supabase Auth, giữ đúng luồng xác thực, token validation và OAuth exchange đang được triển khai.

Không tự ý thay đổi cơ chế authentication hoặc authorization khi chỉ cần chỉnh sửa một use case thông thường.

### 3.6. Database and migrations

- Supabase PostgreSQL là nguồn dữ liệu chính của ứng dụng.
- Ưu tiên Supabase migrations để quản lý thay đổi schema.
- Không tự ý đưa Flyway vào dự án nếu chưa có quyết định kiến trúc mới.
- Không sửa schema trực tiếp mà bỏ qua migration cần thiết.
- Không tự ý đổi tên cột, bảng, enum, constraint hoặc relationship đang được sử dụng.
- Khi thay đổi schema, kiểm tra ảnh hưởng đến Entity, Repository, DTO, API và Frontend types.
- Ưu tiên migration có thể được review, tái lập và triển khai theo thứ tự rõ ràng.

### 3.7. Error handling

- Tuân thủ cơ chế exception và API error hiện có.
- Không trả stack trace hoặc thông tin nhạy cảm ra client.
- Không nuốt exception mà không có lý do.
- Phân biệt lỗi validation, authentication, authorization, resource not found và lỗi nghiệp vụ.
- Sử dụng HTTP status phù hợp với semantics của endpoint.
- Endpoint không có response body nên sử dụng cơ chế trả response rỗng phù hợp, chẳng hạn `204 No Content`, khi đúng với hợp đồng API.

### 3.8. Scope control

- Không bổ sung unit test hoặc integration test theo mặc định nếu task không yêu cầu và dự án chưa có kế hoạch test tương ứng.
- Tuy nhiên, không được tuyên bố code đã hoạt động nếu chưa có bằng chứng kiểm tra.
- Khi không chạy được kiểm tra, phải nêu rõ giới hạn xác minh.
- Không thay đổi các feature không liên quan chỉ để đồng bộ code theo sở thích cá nhân.

---

## 4. Frontend Standards (`Arashyn-FE/`)

### 4.1. Technology

- React.
- TypeScript.
- Vite.
- TanStack Query cho server state.
- Axios cho HTTP requests.
- Zustand cho client state theo nhu cầu.
- Tailwind CSS và các UI primitives đang được dự án sử dụng.

Phiên bản package thực tế trong `package.json` và lockfile là nguồn chuẩn.

### 4.2. Component and feature design

- Tách UI, API access, hooks và business logic theo cấu trúc feature hiện có.
- Không tạo component dùng chung chỉ vì hai đoạn code tình cờ giống nhau; cần xác định chúng có thực sự cùng một trách nhiệm hay không.
- Không nhồi toàn bộ logic của một trang vào một component lớn khi có thể tách thành các phần rõ ràng.
- Không tạo thêm state nếu giá trị có thể được suy ra từ state hiện có.
- Không sử dụng global state cho dữ liệu chỉ cần tồn tại trong một component.
- Tuân thủ conventions về imports, aliases, naming và component exports trong repository.

### 4.3. API integration and types

- Các type trong Frontend phải khớp với API contract của Backend.
- Không tự ý đổi tên field, enum value hoặc nullability chỉ để thuận tiện cho UI.
- Phân biệt dữ liệu request, response và dữ liệu chỉ phục vụ hiển thị.
- Không giả định response có cấu trúc khác với API thực tế.
- Không lặp lại logic gọi API trong nhiều component khi có thể tái sử dụng hooks hoặc API modules hiện có.

### 4.4. TanStack Query

- Dùng TanStack Query cho server state và cache.
- Đặt query keys theo conventions hiện có.
- Chỉ invalidate các query bị ảnh hưởng bởi mutation.
- Tránh gọi API trùng lặp do cấu hình query hoặc component lifecycle không phù hợp.
- Không thay thế server state bằng Zustand nếu không có nhu cầu cụ thể.

### 4.5. Routing and navigation

- Tuân thủ route definitions hiện có.
- Với các trang detail được truy cập từ nhiều context, giữ đúng context cần thiết khi thiết kế route và điều hướng.
- Không tự ý đổi route path làm ảnh hưởng đến breadcrumb, navigation, deep links hoặc các component đang sử dụng route đó.
- Khi sửa routing, kiểm tra các trường hợp direct navigation, refresh trang và back/forward navigation.

### 4.6. UI conventions

- Ưu tiên tái sử dụng UI primitives và component library đã được cài đặt.
- Tuân thủ theme, CSS variables và conventions hiện tại.
- Không đưa plain CSS vào dự án nếu cách triển khai đó mâu thuẫn với hệ thống styling hiện có.
- Không tự ý thay đổi toàn bộ design system trong một task chỉ sửa một component.

---

## 5. Domain Rules

Arashyn kết hợp kho ngữ pháp có thể dùng chung với dữ liệu học tập riêng của người dùng.

Các quy tắc sau cần được bảo toàn:

1. Phân biệt dữ liệu ngữ pháp dùng chung với dữ liệu cá nhân của người dùng.
2. Phân biệt quyền sở hữu dữ liệu với khả năng công khai và tương tác với dữ liệu.
3. Không tạo bản sao của toàn bộ kho ngữ pháp chỉ để mỗi người dùng có không gian học tập riêng.
4. Thông tin cá nhân như meaning, example, note và progress phải được xử lý theo đúng ownership và privacy rules đã xác lập.
5. Folder, Deck và Grammar có các vai trò nghiệp vụ riêng; không coi chúng là các tên gọi thay thế cho nhau.
6. Các grammar forms, keywords, ordering và grouping phải tuân thủ schema hiện tại.
7. Các thao tác trùng lặp grammar phải sử dụng cơ chế kiểm tra và semantics đã được xác lập.
8. Các tính năng AI chỉ được triển khai trong phạm vi use case đã được chấp thuận.

Tham khảo `GLOSSARY.md` trước khi đặt tên entity, DTO, endpoint hoặc biến nghiệp vụ mới.

---

## 6. File and Media Storage

- Không lưu file nhị phân lớn trực tiếp trong PostgreSQL nếu không có quyết định kiến trúc rõ ràng.
- Với avatar và các tệp cần lưu trữ, ưu tiên phương án object storage đã được xác lập, hiện hướng đến Amazon S3.
- Không để AWS credentials hoặc secrets trong Frontend source code.
- Không tạo public bucket hoặc public object mặc định nếu dữ liệu cần được bảo vệ.
- Phân biệt public asset và private asset.
- Khi sử dụng pre-signed URL, xác định rõ mục đích upload/download, thời hạn hiệu lực và quyền truy cập.
- Kiểm tra loại file, kích thước và quyền sở hữu ở phía phù hợp trước khi cho phép lưu hoặc truy cập tệp.

Chi tiết storage, domain, HTTPS và deployment phải theo tài liệu hạ tầng thực tế của dự án.

---

## 7. AI and Language Processing

AI/NLP là một phần mở rộng của nền tảng học ngôn ngữ, không phải lý do để đưa thêm các hệ thống phức tạp vào mọi feature.

- Chỉ triển khai AI cho use case đã được xác định.
- Phân biệt chức năng sinh nội dung ngôn ngữ với chức năng nhận diện hoặc phân tích ngôn ngữ.
- Không mặc định mọi tác vụ AI đều cần AWS Bedrock.
- Không thêm vector database, RAG pipeline hoặc model-serving infrastructure nếu chưa có yêu cầu cụ thể.
- Giữ API và business logic độc lập nhất có thể với nhà cung cấp AI khi việc đó không làm tăng độ phức tạp quá mức.
- Xác định rõ dữ liệu đầu vào, đầu ra, validation và cách xử lý lỗi của AI.

---

## 8. Deployment and Infrastructure

- Ưu tiên kiến trúc triển khai đơn giản, chi phí thấp và phù hợp với quy mô hiện tại.
- Supabase tiếp tục đảm nhiệm Database/Auth theo kiến trúc hiện có.
- Không mặc định cần Amazon RDS khi PostgreSQL đã được cung cấp bởi Supabase.
- Không mặc định cần EC2, Lambda, API Gateway hoặc các dịch vụ AWS khác cho mọi workload.
- Frontend, Backend, object storage và domain phải được thiết kế theo nhu cầu thực tế, không theo mục tiêu sử dụng càng nhiều dịch vụ càng tốt.
- Không commit secrets, credentials, access tokens hoặc private keys.
- Giữ cấu hình môi trường local, staging và production tách biệt.
- Khi thay đổi deployment configuration, kiểm tra API base URL, CORS, HTTPS và các callback URLs liên quan.

Các quyết định hạ tầng chưa được xác nhận phải được xem là phương án đề xuất, không phải kiến trúc đã hoàn tất.

---

## 9. Agent Workflow

Khi xử lý một task:

1. **Inspect:** Đọc yêu cầu, kiểm tra code và tài liệu liên quan.
2. **Understand:** Xác định business rule, API contract và các thành phần bị ảnh hưởng.
3. **Plan:** Đề xuất các thay đổi tối thiểu cần thiết; xác định migration nếu có.
4. **Implement:** Thực hiện thay đổi nhất quán với conventions hiện tại.
5. **Validate:** Kiểm tra type errors, build hoặc các bước xác minh phù hợp với task.
6. **Report:** Tóm tắt file đã sửa, hành vi thay đổi, migration/configuration cần thiết và những gì chưa được xác minh.

### Không được tự ý

- Thay đổi schema trái với nghiệp vụ hiện tại.
- Thêm dependency chỉ để giải quyết một vấn đề nhỏ khi có phương án phù hợp hơn.
- Xóa hoặc viết lại code không liên quan.
- Thay đổi public API mà không kiểm tra các caller.
- Giả định rằng một migration đã được chạy chỉ vì file migration đã được tạo.
- Tự tạo dữ liệu, endpoint hoặc business rules chưa được xác nhận rồi trình bày chúng như yêu cầu chính thức.

---

## 10. Definition of Done

Một task được xem là hoàn thành khi:

- Đáp ứng đúng yêu cầu nghiệp vụ.
- Tuân thủ schema và API contract hiện có.
- Các thay đổi FE/BE nhất quán khi cả hai phía bị ảnh hưởng.
- Không đưa secrets vào source code.
- Có migration hoặc cập nhật cấu hình cần thiết nếu task yêu cầu.
- Các bước kiểm tra phù hợp đã được thực hiện hoặc giới hạn xác minh được nêu rõ.
- Không đưa thêm phạm vi ngoài yêu cầu mà không có lý do.

---

## 11. Agent Skills and Repository Conventions

Các hướng dẫn chuyên biệt có thể được bổ sung trong các tài liệu riêng, ví dụ:

- `docs/agents/issue-tracker.md`: quản lý issue và workflow GitHub.
- `docs/agents/domain.md`: quy tắc domain và terminology.
- `docs/agents/deployment.md`: quy trình build và deploy.
- `docs/agents/database.md`: quy trình thay đổi schema và migrations.

Khi có tài liệu chuyên biệt, hãy đọc đúng tài liệu liên quan thay vì đưa toàn bộ hướng dẫn chi tiết vào `AGENTS.md`.

- **Lỗi tồn đọng / Backlog:** Khi người dùng có yêu cầu liên quan đến "các lỗi tồn đọng", "deferred tasks" hay "backlog", hãy luôn tìm đọc file [`BACKLOG.md`](file:///d:/Arashyn-Project/BACKLOG.md) tại repository root.
