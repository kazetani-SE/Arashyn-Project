# Arashyn — Danh Sách Lỗi Tồn Đọng & Nhiệm Vụ Trì Hoãn (`BACKLOG.md`)

Tài liệu này ghi nhận các tác vụ nghiệp vụ, kỹ thuật đã được thống nhất **hoãn lại để thực hiện sau** theo quyết định của dự án. 
Mỗi khi người dùng hoặc Agent kiểm tra "các lỗi tồn đọng", "deferred tasks" hoặc "backlog", hãy tham chiếu tài liệu này.

---

## 1. Tính năng xóa Form (BR-01 §3, BR-07 A3)

- **Trạng thái:** `[Deferred]` (Làm sau).
- **Vấn đề:** Hiện tại `FormService` và `FormProtectedController` chỉ hỗ trợ tạo mới (`POST /protected/forms`) và xem danh sách theo ngôn ngữ (`GET /public/forms`). Chưa có API và logic xóa Form.
- **Yêu cầu khi triển khai:**
  1. **Phương án 1 (Chặn xóa):** Nếu Form đang được một hoặc nhiều `component` tham chiếu (`component.form_id = form.id`), ném exception nghiệp vụ báo lỗi kèm số lượng component đang sử dụng, chặn không cho xóa.
  2. **Phương án 2 (Di chuyển component):** Cho phép truyền `target_form_id` (cùng ngôn ngữ). Trong cùng 1 transaction, cập nhật toàn bộ `component.form_id = target_form_id`, sau đó thực hiện xóa Form cũ an toàn.

---

## 2. Chuẩn hóa mã lỗi chống vòng Folder Hierarchy (BR-03 §2)

- **Trạng thái:** `[Deferred]` (Làm sau).
- **Vấn đề:** 
  - Database đã có trigger phát hiện chu trình/vòng lặp (`check_folder_cycle`, `check_user_folder_cycle`).
  - Tuy nhiên, ở tầng Backend:
    - `FolderServiceImpl.attachChild` bắt exception và trả về `ErrorCode.INVALID_REQUEST` (HTTP 400).
    - `UserFolderServiceImpl.attachChild` ném `USER_FOLDER_INVALID_HIERARCHY` trả về HTTP 404 (trong khi đây không phải lỗi Not Found).
    - Nếu exception DB trigger lọt ra ngoài, hệ thống có thể trả về HTTP 500 Unexpected Exception.
- **Yêu cầu khi triển khai:**
  - Chuẩn hóa mã lỗi trả về: Phải trả HTTP 409 Conflict hoặc 422 Unprocessable Entity với mã lỗi rõ ràng (ví dụ: `FOLDER_HIERARCHY_CYCLE_DETECTED`).
  - Tuyệt đối không để trả về HTTP 500 hay HTTP 404 cho lỗi nghiệp vụ vòng lặp cây thư mục.

---

## 3. Tích hợp tính độ sâu thư mục qua hàm DB (BR-03 §3, BR-07 A9)

- **Trạng thái:** `[Deferred]` (Làm sau).
- **Vấn đề:** 
  - Database schema đã cung cấp sẵn hai hàm recursive query: `get_folder_depths()` và `get_user_folder_depths()`.
  - Cả Backend và Frontend hiện tại chưa gọi hoặc tiêu thụ kết quả từ 2 hàm này khi hiển thị cấu trúc thư mục DAG.
- **Yêu cầu khi triển khai:**
  - Sử dụng kết quả tính toán độ sâu từ các hàm trên để phân cấp thư mục.
  - Xử lý trường hợp một folder xuất hiện ở nhiều nhánh với các độ sâu khác nhau (do mô hình DAG nhiều cha).
