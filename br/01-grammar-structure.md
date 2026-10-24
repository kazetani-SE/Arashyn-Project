# BR-01 — Cấu trúc Grammar

Phạm vi: `grammar`, `form`, `component`, `meaning`, `group_key`, mã ngôn ngữ.

---

## 1. Grammar

`grammar(id, title, language, owner_id, is_public, created_at, updated_at)`

- Là nội dung ngữ pháp **dùng chung**, không phải bản ghi học tập cá nhân.
- Quy tắc đọc/ghi theo `owner_id` và `is_public`: xem `br/02`.
- Bản cá nhân là `user_grammar`: xem `br/04`.

## 2. Language

| Code | Ngôn ngữ |
|---|---|
| `VI` | Vietnamese |
| `EN` | English |
| `JA` | Japanese |
| `KO` | Korean |
| `ZH` | Chinese |

- Chỉ dùng đúng các code trên ở DB, API, Frontend. Không dùng tên đầy đủ hay mã khác (`ja`, `jp`, `Japanese`...).
- `[Needs confirm]` Schema chưa có CHECK cho `language`; nên bổ sung nếu chốt danh sách này là cố định.

## 3. Form

`form(id, name, type, language, created_at)`, `UNIQUE(name, type, language)`.

- Form mô tả **dạng** của một thành phần: Word Class (Noun, Verb, Adjective, particle...) hoặc Conjugation/Form (`V-て`, `V-ない`, base, `-ing`...).
- Hai form cùng `name` nhưng khác `type` hoặc `language` là **hai form khác nhau**. Không gộp.
- `[Needs confirm]` Các giá trị của `type` và cách biểu diễn Word Class dùng chung giữa các ngôn ngữ (do `language` là NOT NULL) chưa được chốt. Xem `br/07`.

### Quy tắc xóa Form `[Confirmed]`

Một Form **không được xóa** khi vẫn còn component tham chiếu nó. Khi xóa Form, Backend phải thực hiện một trong hai cách:

1. **Chặn**: kiểm tra không có component nào dùng Form; nếu có thì từ chối xóa và trả lỗi rõ ràng (kèm số component đang dùng).
2. **Di chuyển rồi xóa**: chuyển toàn bộ component đang dùng Form cũ sang một Form đích, sau đó xóa Form cũ. Hai bước phải nằm trong **một transaction**.

Yêu cầu bổ sung:

- Form đích phải tồn tại và khác Form cũ.
- `[Needs confirm]` Form đích nên cùng `language` (và có thể cùng `type`) với Form cũ.
- **Không dựa vào `ON DELETE SET NULL`** để xử lý. Xem mục 4.

## 4. Component

`component(id, grammar_id, form_id, order, keyword, optional, group_key)`

### Quy tắc XOR `[Confirmed]`

Mỗi component **chính xác một trong hai**: có `form_id` (khác NULL) **hoặc** có `keyword` (khác NULL, không rỗng), không bao giờ cả hai và không bao giờ không có gì.

Cách đảm bảo (cần thực hiện trong code/schema, xem `br/07`):

- **DB**: CHECK constraint `(form_id IS NOT NULL) <> (keyword IS NOT NULL)`, cộng thêm kiểm tra `keyword` không chỉ gồm khoảng trắng.
- **FK `form_id`**: đổi `ON DELETE SET NULL` thành `ON DELETE RESTRICT`. Nếu để `SET NULL`, xóa Form sẽ làm component rơi vào trạng thái không có gì, vi phạm quy tắc XOR.
- **Backend**: validate ở service khi tạo/sửa component, trả lỗi 4xx thay vì để DB ném lỗi.
- **Frontend**: form nhập chỉ cho chọn một trong hai chế độ (Form hoặc Keyword).

Không nới quy tắc này chỉ để tiện render Frontend.

### Các thuộc tính khác

| Thuộc tính | Ý nghĩa |
|---|---|
| `order` | Thứ tự component trong Grammar. **Được phép trùng.** Khi trùng, phải có tiêu chí sắp xếp phụ cố định (khuyến nghị theo `id`) để thứ tự hiển thị ổn định. |
| `optional` | Component có phải thành phần tùy chọn không. Chỉ mô tả cấu trúc, không liên quan quyền truy cập. |
| `group_key` | Xem mục 5. |

## 5. group_key `[Confirmed]`

Một Grammar có thể có **nhiều nhóm component** (ví dụ một ngữ pháp tiếng Nhật có nhiều dạng ghép với cùng một từ khóa). `group_key` dùng để:

1. Nhóm các component thuộc cùng một dạng cấu trúc (`component.group_key`).
2. Gắn Meaning (và Example, Note) với nhóm component tương ứng: các nội dung có `group_key` trùng với nhóm component thì thuộc nhóm đó.

Ví dụ: Grammar G có hai nhóm.

- Nhóm 0: `V-て + ほしい` → meaning nhóm 0: "muốn (người khác) làm gì".
- Nhóm 1: `N + が + ほしい` → meaning nhóm 1: "muốn có (vật)".

Quy tắc:

- `group_key` mặc định 0; kiểu SMALLINT.
- Meaning/Example/Note phải dùng `group_key` đã tồn tại trong nhóm component của cùng Grammar. `[Needs confirm]` chưa có ràng buộc DB cho việc này.
- Không dùng `group_key` cho mục đích khác (phân quyền, sắp xếp...).

## 6. Meaning và Example

- `meaning(grammar_id, owner_id, content, group_key, is_public)`: nghĩa/cách dùng thuộc một Grammar và một group.
- `example(meaning_id, owner_id, sentence, translation, note, group_key, is_public)`: ví dụ thuộc một Meaning.
- Quy tắc OI/pub của Meaning/Example/Note: xem `br/02`.

## 7. Grammar Level

`[Deferred]` Schema hiện **không có** cột level. Các cấp N5–N2 (có thể có N1) chưa được gắn vào mô hình. Không tự thêm cột hoặc suy ra level từ tên Grammar. Xem `br/07`.
