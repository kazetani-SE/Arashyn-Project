# BR-04 — Hub của người dùng (`user_*`)

Phạm vi: `user_grammar`, `user_deck`, `user_folder`, các bảng nối và hierarchy của người dùng, clone, progress.

**Hub** = toàn bộ không gian `user_*` của một người dùng.

---

## 1. Mô hình `[Confirmed]`

| Bảng | Tham chiếu | Dữ liệu riêng |
|---|---|---|
| `user_grammar` | `grammar_id` | `name`, `proficiency`, `last_review_at` |
| `user_deck` | `deck_id` | `name`, `proficiency`, `last_open_at` |
| `user_folder` | `folder_id` | `name` |

`user_grammar` có `UNIQUE(grammar_id, user_id)`: mỗi người chỉ có tối đa một bản `user_grammar` cho mỗi Grammar. 
Đối với `user_deck` và `user_folder`, theo migration `20261001110835_user_clone_sync_tracking.sql`, các ràng buộc `UNIQUE(deck_id, user_id)` và `UNIQUE(folder_id, user_id)` đã được gỡ bỏ; `deck_id` và `folder_id` chuyển thành *soft pointers* (nullable) phục vụ cơ chế **Sync Drift Tracking** (`synced_version`, `user_folder_sync_base`, `user_deck_sync_base`), cho phép người dùng theo dõi và đồng bộ thay đổi từ Deck/Folder gốc.

Bảng nối (đều nhiều-nhiều):

```
user_folder ──< user_folder_hierarchy >── user_folder
user_folder ──< user_folder_deck >── user_deck
user_deck   ──< user_deck_grammar >── user_grammar
```

- Một `user_grammar` nằm được trong nhiều `user_deck`; một `user_deck` thuộc nhiều `user_folder`; `user_folder` lồng nhiều cấp, nhiều cha.
- Các cột `parent_id` cũ đã bỏ; cấu trúc nằm hoàn toàn ở bảng nối.

## 2. Clone `[Confirmed]`

"Clone" = tạo bản `user_*` **tham chiếu** tới bản gốc (Grammar/Deck/Folder), **không sao chép nội dung**.

- Clone Grammar: tạo `user_grammar(grammar_id, user_id)`. Nội dung (component, meaning...) vẫn đọc từ Grammar gốc qua service read (`br/02` mục 3).
- Một người dùng gắn Deck/Folder của người khác vào hub của mình là **bình thường**, vì đó là clone tạo ra bản `user_*` của chính họ.
- Cá nhân hóa (đổi tên, thêm meaning/example/note của riêng mình) không được sửa nội dung dùng chung của người khác (`br/02` mục 2).
- Với `user_grammar`: `UNIQUE(grammar_id, user_id)` đảm bảo không clone hai lần. Với `user_deck`/`user_folder`: cơ chế sync base theo dõi phiên bản đồng bộ. Backend trả kết quả rõ ràng thay vì để lỗi DB nổi lên.

`[Needs confirm]`:

- Clone một Deck có tự động clone các Grammar bên trong thành `user_grammar` không?
- Điều kiện được clone một Grammar/Deck/Folder của người khác (chỉ khi `pub = true`?).

## 3. Quyền sở hữu hub `[Confirmed]`

- `user_deck`, `user_grammar`, `user_folder` của B **người khác (A) không được dùng**: không đọc, không sửa, không gắn vào cấu trúc của mình.
- Mọi dòng nối (`user_folder_hierarchy`, `user_folder_deck`, `user_deck_grammar`) chỉ được nối **các bản `user_*` cùng `user_id`**, và `user_id` đó phải trùng danh tính đang request.
- **DB hiện không có constraint** đảm bảo cùng chủ. Backend **bắt buộc** kiểm tra ở service; không chỉ dựa vào Frontend.
- Danh tính lấy từ security context (`br/05`), không từ `user_id` client gửi.

## 4. Progress `[Confirmed]` (phạm vi hiện tại)

- Progress thuộc người dùng, nằm trên `user_*`, **không** phải thuộc tính của Grammar chung.
- Hiện chỉ có: `proficiency` (SMALLINT) và `last_review_at` (`user_grammar`), `last_open_at` (`user_deck`).
- Progress cấp Folder **đã bị bỏ**; nếu cần thì tính khi đọc từ các Deck/Grammar bên trong, không lưu.
- Cập nhật progress của người này không ảnh hưởng người khác.
- `[Planned]` Mô hình trạng thái ghi nhớ, lịch ôn, spaced repetition: chưa có trong schema. Không tự thêm cột khi chưa có đặc tả.

## 5. Soft delete Grammar và hub `[Confirmed]`

Grammar gốc bị soft delete (OI `NULL`) **không** ảnh hưởng `user_grammar`. Người dùng vẫn thấy và học bình thường qua hub. Xem `br/02` mục 3 về luồng đọc hợp lệ và luồng bị cấm.

## 6. Việc cần kiểm tra khi viết code

- [ ] Mọi thao tác trên `user_*` kiểm tra `user_id = identity hiện tại`.
- [ ] Thao tác nối bảng kiểm tra hai phía cùng `user_id`.
- [ ] Clone không sao chép nội dung; xử lý trùng nhẹ nhàng.
- [ ] Không có endpoint nào cho phép đi từ `user_*` sang đọc detail system trái quy tắc `br/02`.
