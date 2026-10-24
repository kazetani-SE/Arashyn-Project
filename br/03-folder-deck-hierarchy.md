# BR-03 — Folder, Deck và Hierarchy (system)

Phạm vi: `folder`, `folder_hierarchy`, `folder_deck`, `deck`, `deck_grammar`. Phần tương ứng của người dùng nằm ở `br/04`.

---

## 1. Mô hình `[Confirmed]`

```
folder ──< folder_hierarchy >── folder      (cha ↔ con, nhiều-nhiều)
folder ──< folder_deck >── deck             (nhiều-nhiều)
deck   ──< deck_grammar >── grammar         (nhiều-nhiều)
```

- **Folder lồng nhiều cấp và có thể có nhiều cha** (DAG, không phải cây).
- **Một Deck thuộc được nhiều Folder.**
- **Một Grammar nằm được trong nhiều Deck.**
- Các cột `parent_id` / `folder_id` cũ trên `folder` và `deck` đã bị bỏ; không dùng lại.
- Deck và Folder **không chứa bản sao** Grammar; chỉ tham chiếu.
- Folder/Deck có `owner_id`, `is_public`: xem `br/02`.

## 2. Chống vòng `[Confirmed]`

- `folder_hierarchy` có CHECK `parent_id <> child_id` và trigger `prevent_folder_hierarchy_cycle` chặn vòng gián tiếp khi INSERT/UPDATE.
- Backend không được tự ghi `folder_hierarchy` theo cách bỏ qua trigger.
- Lỗi do trigger là lỗi nghiệp vụ (409/422), không phải lỗi 500.

## 3. Depth và stage `[Confirmed]`

- Cột `stage` đã bị **xóa** khỏi `user_folder` và `user_deck`. Không thêm lại.
- **Depth không được lưu, chỉ tính khi đọc** bằng hàm `get_folder_depths(root_id)` (system) hoặc `get_user_folder_depths(root_id)` (user).
- Depth luôn **tương đối theo một root cụ thể**, vì một Folder có thể có nhiều cha nên không có một depth tuyệt đối.
- Lưu ý: trong DAG, nếu một Folder đi tới được bằng nhiều đường, hàm có thể trả **nhiều dòng cùng `folder_id` với depth khác nhau**. Code dùng kết quả phải xử lý trường hợp này (ví dụ lấy depth nhỏ nhất, hoặc giữ cả hai tùy mục đích).

## 3a. Điều không được làm

- Không giả định mỗi Folder chỉ có một cha.
- Không lưu progress/depth/stage ở cấp Folder.
- Không sao chép toàn bộ Grammar khi tạo hoặc gắn Deck.
- Không đảo ngữ nghĩa `parent_id`/`child_id` trong `folder_hierarchy`.

## 4. Xóa và cascade

- Xóa Folder: cascade xóa các dòng `folder_hierarchy`, `folder_deck` liên quan (không xóa Deck).
- Xóa Deck: cascade xóa `deck_grammar`, `folder_deck`.
- Xóa Grammar (vật lý): cascade xóa `deck_grammar`.
- Hành vi nghiệp vụ khi chủ sở hữu xóa/soft delete Folder/Deck mà người khác đã clone: `[Needs confirm]`, xem `br/07`. Với Grammar, đã chốt: `user_grammar` không bị ảnh hưởng (`br/02`).

## 5. Index

Các index chiều ngược đã có cho `folder_deck`, `folder_hierarchy`, `deck_grammar`. Truy vấn từ phía con/phía Grammar nên dùng chúng.
