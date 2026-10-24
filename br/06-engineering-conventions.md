# BR-06 — Quy ước kỹ thuật

Phạm vi: đặt tên, DTO/Entity/Mapper, API response, migration, ngôn ngữ xử lý.

---

## 1. Đặt tên

1. Dùng đúng thuật ngữ trong `GLOSSARY.md`.
2. Giữ sự phân biệt dữ liệu **dùng chung** và **cá nhân (hub)**.
3. Không dùng thay thế cho nhau: `Grammar` / `UserGrammar`, `Deck` / `UserDeck`, `Folder` / `UserFolder`.
4. Phân biệt ownership, visibility, authorization.
5. Tên field và enum nhất quán giữa Database, Backend, Frontend (ví dụ `owner_id`, `is_public`, `group_key`, mã ngôn ngữ `VI/EN/JA/KO/ZH`).
6. Tên bảng, cột, biến, enum trong code là **tiếng Anh**.
7. Chỉ thêm thuật ngữ mới khi có khái niệm nghiệp vụ thật sự mới; nếu thuật ngữ có nhiều cách hiểu, cập nhật glossary trước hoặc cùng thay đổi.

## 2. DTO, Entity, Mapper

- **Entity**: model persistence, ánh xạ dữ liệu lưu trữ. Không mặc định là response công khai.
- **DTO**: cấu trúc truyền dữ liệu qua ranh giới API hoặc giữa các tầng. Không mặc định là Entity, không nhất thiết phản ánh toàn bộ Database.
- **Mapper**: chuyển đổi giữa các model khi cần. Không tạo mapper/abstraction mới nếu việc chuyển đổi đơn giản.
- Không trả Entity trực tiếp nếu chứa trường nhạy cảm hoặc trường mà người gọi không được thấy (`br/02`).

## 3. API Response

- Là hợp đồng dữ liệu Backend → Frontend. Frontend dùng đúng cấu trúc thực tế.
- Không thêm hoặc bỏ lớp bọc response ở một phía mà không cập nhật hợp đồng và type tương ứng.

## 4. Migration `[Confirmed]`

- Migration đã chạy không chỉnh sửa. Thay đổi thêm bằng migration mới.
- Thứ tự phụ thuộc: các hàm/trigger cycle và `get_*_depths` cần các bảng hierarchy đã tồn tại.
- Cập nhật `br/` và type API cùng lúc với migration làm đổi ý nghĩa dữ liệu.

## 5. Ngôn ngữ và xử lý ngôn ngữ

- **Language Detection** (nhận diện ngôn ngữ đầu vào) khác với phân tích ngữ pháp hay chấm trình độ. `[Planned]`
- **Language Generation / AI-assisted Learning**: AI chỉ hỗ trợ; không thay thế quy tắc dữ liệu, quyền truy cập hoặc logic xác định của Backend. `[Planned]`

## 6. Duplicate Grammar Check `[Needs confirm]`

Kiểm tra trùng phải tuân thủ semantics Grammar gốc, `user_grammar`, ownership, lifecycle (`br/02`, `br/04`). Hai bản ghi cùng tham chiếu một Grammar không mặc định là trùng; phụ thuộc use case. Quy tắc từng use case chưa chốt.
