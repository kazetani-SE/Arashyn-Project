# BR-07 — Câu hỏi còn mở, việc cần sửa, mục hoãn

Agent **phải kiểm tra file này** trước khi code các phần liên quan. Với mục `[Needs confirm]`, hỏi lại người phụ trách thay vì tự suy diễn.

---

## A. Việc cần sửa code/schema (đã chốt quy tắc)

| # | Việc | Căn cứ |
|---|---|---|
| A1 | Thêm CHECK XOR cho `component`: `(form_id IS NOT NULL) <> (keyword IS NOT NULL)`, `keyword` không rỗng. | `br/01` §4 |
| A2 | Đổi FK `component.form_id` từ `ON DELETE SET NULL` sang `RESTRICT`. | `br/01` §3–4 |
| A3 | Cài đặt xóa Form theo hai cách: chặn nếu đang dùng, hoặc di chuyển component sang Form đích rồi xóa (một transaction). | `br/01` §3 |
| A4 | Validate XOR ở service khi tạo/sửa component; Frontend chỉ cho chọn một trong hai. | `br/01` §4 |
| A5 | Endpoint đọc Grammar từ system chặn OI `NULL` ở tầng controller; luồng hub dùng service read nội bộ, không thành cửa sau. | `br/02` §3 |
| A6 | Ghi Meaning/Example/Note: ép `is_public=false` khi Grammar cha `pub=false`. | `br/02` §2 |
| A7 | Backend kiểm tra cùng `user_id` khi nối `user_folder_deck`, `user_deck_grammar`, `user_folder_hierarchy`. | `br/04` §3 |
| A8 | Query danh sách/tìm kiếm Grammar hệ thống loại OI `NULL`. | `br/02` §5 |
| A9 | Xử lý trường hợp `get_*_depths` trả trùng folder với depth khác nhau. | `br/03` §3 |

## B. Cần xác nhận `[Needs confirm]`

1. **Grammar OI có, pub = false**: người khác đọc được từ system hay không? (Câu gốc ghi "mọi người đọc được"; đang hiểu là chỉ chủ sở hữu và người đã clone.) Người khác có clone được Grammar loại này không?
2. Khi Grammar đổi từ `pub=true` sang `false`, nội dung con `pub=true` có tự hạ về `false`?
3. `owner_id ON DELETE SET NULL` trên `meaning/example/note` mâu thuẫn quy tắc "OI không bao giờ NULL": xử lý thế nào khi account bị xóa?
4. Khi đọc Grammar OI `NULL` qua hub, người dùng thấy những Meaning/Example/Note nào?
5. Quy tắc OI `NULL`/pub cho Folder và Deck.
6. Clone Deck có tự clone các Grammar bên trong không? Điều kiện được clone nội dung của người khác?
7. Form đích khi di chuyển component có bắt buộc cùng `language`/`type`?
8. `form.type` có những giá trị nào? `V-て` là một dòng Form hay Word Class `V` + Form `て`? Word Class dùng chung (Noun/Verb/Adjective) biểu diễn thế nào khi `form.language` NOT NULL?
9. "Slot" trong Overview có chính là Component không?
10. Hành vi khi chủ sở hữu xóa Folder/Deck mà người khác đã clone.
11. `guest` là bản ghi Account hay người chưa đăng nhập; hành vi chi tiết của `is_banned`.
12. Có ràng buộc `group_key` của Meaning/Example/Note phải thuộc một group component có sẵn không.

## C. Hoãn `[Deferred]` (không code, không suy diễn)

- Default Private Deck: thời điểm tạo, số lượng, xóa được không.
- Filter/Tag: `system_filter`, `grammar_filter`, `user_tag`, `deck_filter` (kể cả việc `deck_filter` trỏ tới `user_tag`).
- Grammar Level (N5–N2, N1?): chưa có trong schema.
- Bảng quyền RBAC chi tiết theo role.
- Avatar: public/private, nơi lưu.
- Deployment chính thức.
- Độ dài tên không đồng nhất giữa bảng system và `user_*`.
- Index tìm kiếm cho `component` theo `form_id`/`keyword`.
- Phân biệt chi tiết Meaning/Example dùng chung và cá nhân ở mức chính sách.

## D. Tính năng định hướng `[Planned]`

Component-based search, exercise/practice, mastery tracking đầy đủ, spaced repetition, adaptive quiz, AI generation (giải thích, ví dụ), Language Detection, grammar similarity, public/shared collections, collaborative study packs, learning analytics.
