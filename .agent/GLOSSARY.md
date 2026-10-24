# Arashyn — Glossary (`GLOSSARY.md`)

File này là **điểm vào** cho mọi AI agent và developer. Nó cho biết Arashyn là gì, các thuật ngữ cốt lõi ứng với bảng DB nào, và **file business rule nào cần đọc cho từng loại công việc**.

Chi tiết quy tắc nghiệp vụ nằm trong thư mục [`br/`](br/). Glossary chỉ tóm tắt; khi có mâu thuẫn, file trong `br/` là nguồn đúng.

Nhãn trạng thái dùng trong toàn bộ tài liệu:

- `[Confirmed]`: đã chốt, phải tuân thủ.
- `[Needs confirm]`: đang hiểu như vậy nhưng chưa được xác nhận; không tự ý code dựa trên nó mà không hỏi.
- `[Planned]`: định hướng, chưa tồn tại trong schema/code.
- `[Deferred]`: cố ý chưa xử lý, sẽ tính sau.

---

## 1. Arashyn là gì

Arashyn là nền tảng **học, lưu trữ và truy xuất ngữ pháp đa ngôn ngữ**. Ý tưởng cốt lõi: ngữ pháp không chỉ là văn bản để đọc, mà là một **cấu trúc gồm các component** có thể tìm kiếm, tổ chức, luyện tập và theo dõi tiến độ.

Ví dụ một mẫu ngữ pháp được phân rã: `N + V-て + ほしい` gồm ba component (Word Class `N`, Form `V-て`, Keyword `ほしい`).

Hai phần của sản phẩm:

1. **Kho ngữ pháp dùng chung (system)**: Grammar, Folder, Deck, Form, Filter do hệ thống hoặc người dùng đóng góp.
2. **Không gian cá nhân (hub)**: mỗi người dùng "clone" Grammar/Deck/Folder về thành các bản `user_*` của riêng mình, đặt tên riêng, theo dõi tiến độ, thêm meaning/example/note.

Stack hiện biết: Supabase (PostgreSQL + Auth), Backend riêng, Frontend dùng Vite. Chi tiết hạ tầng: xem `br/05-account-auth-roles.md`.

---

## 2. Thuật ngữ cốt lõi và bảng DB tương ứng

| Thuật ngữ | Bảng / cột | Tóm tắt | Chi tiết |
|---|---|---|---|
| **Grammar** | `grammar` | Một mẫu ngữ pháp dùng chung (title, language, owner, public). | `br/01`, `br/02` |
| **Form** | `form` | Dạng/loại của một component: Word Class hoặc Conjugation. Duy nhất theo `(name, type, language)`. | `br/01` |
| **Component** | `component` | Một thành phần của Grammar. **Đúng một trong** `form_id` hoặc `keyword`. | `br/01` |
| **Keyword** | `component.keyword` | Từ khóa của mẫu ngữ pháp, nằm trực tiếp trong component. | `br/01` |
| **group_key** | `component/meaning/example/note.group_key` | Nhóm component và nội dung đi kèm của nó. | `br/01` |
| **Meaning** | `meaning` | Nghĩa/cách dùng của Grammar, thuộc một group. | `br/01`, `br/02` |
| **Example** | `example` | Câu ví dụ, thuộc một Meaning. | `br/02` |
| **Note** | `note` | Ghi chú gắn với Grammar. | `br/02` |
| **Folder** | `folder`, `folder_hierarchy`, `folder_deck` | Thư mục hệ thống, lồng nhiều cấp, nhiều cha (DAG). | `br/03` |
| **Deck** | `deck`, `deck_grammar` | Tập Grammar hệ thống; thuộc nhiều Folder. | `br/03` |
| **UserGrammar** | `user_grammar` | Bản của người dùng tham chiếu một Grammar + tên riêng + progress. | `br/04` |
| **UserDeck** | `user_deck`, `user_deck_grammar` | Bản của người dùng tham chiếu một Deck (`deck_id` là soft pointer nullable, phục vụ sync drift tracking). | `br/04` |
| **UserFolder** | `user_folder`, `user_folder_hierarchy`, `user_folder_deck` | Bản của người dùng tham chiếu một Folder (`folder_id` là soft pointer nullable, phục vụ sync drift tracking). | `br/04` |
| **Hub** | (khái niệm) | Toàn bộ không gian `user_*` của một người dùng. | `br/04` |
| **Owner (OI)** | `owner_id` | Chủ sở hữu dữ liệu. Khi OI `NULL`, đối tượng bị soft-delete. | `br/02` |
| **Pub** | `is_public` | Cờ đóng góp nội dung: Grammar `pub=false` người khác vẫn tìm kiếm và đọc được, nhưng không được thêm Meaning/Note vào. Grammar chỉ private khi OI `NULL`. | `br/02` |
| **Account** | `account` | Hồ sơ người dùng, gắn với `auth.users`. | `br/05` |
| **Role** | `account.role` | `guest`, `user`, `contributor`, `admin`. | `br/05` |
| **Filter / Tag** | `system_filter`, `grammar_filter`, `user_tag`, `deck_filter` | `system_filter`, `grammar_filter` `[Confirmed]`; `user_tag`, `deck_filter` `[Deferred]`. | `br/07` |

Viết tắt dùng trong tài liệu: **OI** = `owner_id`, **pub** = `is_public`, **hub** = không gian `user_*` của người dùng.

---

## 3. Quy tắc cốt lõi (đọc dù làm việc gì)

1. **Grammar ≠ UserGrammar.** Grammar là nội dung dùng chung. UserGrammar là quan hệ cá nhân; không sao chép nội dung Grammar.
2. **OI và pub quyết định cách đọc và tương tác Grammar.** OI `NULL` nghĩa là Grammar đã bị soft-delete, không được đọc detail từ hệ thống bằng bất kỳ đường nào; chỉ đọc được từ hub của người đã clone. Cờ `is_public` không làm ẩn Grammar khỏi tìm kiếm hay detail system, mà chỉ quyết định việc người khác có được phép thêm thắt Meaning, Example, Note vào hay không.
3. **Component luôn là Form hoặc Keyword, không bao giờ cả hai hoặc không có gì.** Xem `br/01`.
4. **Không xóa Form đang có component sử dụng.** Phải chặn hoặc di chuyển component trước (tính năng xóa form đang hoãn trong [`BACKLOG.md`](file:///d:/Arashyn-Project/BACKLOG.md)). Xem `br/01`.
5. **`user_*` chỉ thuộc về người tạo.** Không ai khác dùng `user_deck`/`user_grammar`/`user_folder` của người khác. Xem `br/04`.
6. **Danh tính lấy từ security context**, không tin UUID client gửi lên. Xem `br/05`.
7. **Không đổi mã ngôn ngữ**: chỉ dùng `VI`, `EN`, `JA`, `KO`, `ZH`.

---

## 4. Bản đồ đọc cho agent

| Khi làm việc với... | Đọc |
|---|---|
| Grammar, Component, Form, Keyword, group_key, Meaning, Language | [`br/01-grammar-structure.md`](br/01-grammar-structure.md) |
| Quyền đọc/ghi, OI, pub, Meaning/Example/Note, soft delete, đọc detail từ hub | [`br/02-ownership-visibility.md`](br/02-ownership-visibility.md) |
| Folder, Deck, hierarchy, depth, chống vòng | [`br/03-folder-deck-hierarchy.md`](br/03-folder-deck-hierarchy.md) |
| `user_*`, clone, progress, hub | [`br/04-user-hub.md`](br/04-user-hub.md) |
| Account, role, auth, session, OTP, OAuth, avatar | [`br/05-account-auth-roles.md`](br/05-account-auth-roles.md) |
| DTO, Entity, Mapper, API response, đặt tên, migration | [`br/06-engineering-conventions.md`](br/06-engineering-conventions.md) |
| Tính năng chưa làm, câu hỏi còn mở, việc cần sửa code/schema | [`br/07-open-and-deferred.md`](br/07-open-and-deferred.md) |
| Các lỗi tồn đọng, nhiệm vụ hoãn lại để làm sau | [`BACKLOG.md`](file:///d:/Arashyn-Project/BACKLOG.md) |

Quy trình khuyến nghị: đọc `GLOSSARY.md` → đọc các file `br/` liên quan → đối chiếu `BACKLOG.md` và `br/07` xem phần đang làm có đang `[Needs confirm]` hay `[Deferred]` không → nếu có, hỏi trước khi code.

---

## 5. Trạng thái tính năng

| Tính năng | Trạng thái |
|---|---|
| Component-based grammar model | `[Confirmed]` (schema đã có) |
| Lưu/clone Grammar, Deck, Folder về hub | `[Confirmed]` |
| Tổ chức Folder/Deck nhiều cấp, nhiều cha | `[Confirmed]` |
| Tìm kiếm theo component | `[Planned]` (chỉ là truy vấn trên `component`, chưa có thiết kế riêng) |
| Bài tập/luyện tập theo component | `[Confirmed]` (Arrange, Fill-in-the-blank, proficiency calculation, AES answer encryption) |
| Mastery tracking | Một phần: chỉ có `proficiency` + `last_review_at`/`last_open_at` |
| Spaced repetition | `[Planned]` (chưa có cột lịch ôn) |
| AI generation, Language Detection | `[Planned]` |
| Public/shared collections | `[Planned]` |
| Default Private Deck | `[Deferred]` |
| Filter/Tag | `system_filter`, `grammar_filter` `[Confirmed]`; `user_tag`, `deck_filter` `[Deferred]` |

---

## 6. Bảo trì

- Đổi schema hoặc quy tắc nghiệp vụ thì cập nhật file `br/` tương ứng trước hoặc cùng lúc.
- Chỉ thêm thuật ngữ khi có khái niệm nghiệp vụ thật sự mới.
- Khi một mục `[Needs confirm]` được trả lời, đổi nhãn thành `[Confirmed]` và xóa khỏi `br/07`.
- Tài liệu viết bằng tiếng Việt; tên bảng, cột, enum, biến trong code luôn là tiếng Anh như schema.