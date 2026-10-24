# BR-02 — Ownership và Visibility

Phạm vi: `owner_id` (OI), `is_public` (pub) trên `grammar`, `folder`, `deck`, `meaning`, `example`, `note`. Cách đọc detail Grammar từ system và từ hub.

Ba khái niệm tách biệt, không dùng thay thế nhau:

- **Ownership** (OI): ai là chủ.
- **Visibility** (pub): người khác có thấy/tương tác với nội dung đó không.
- **Authorization** (RBAC): ai được làm hành động gì. Hiện RBAC đảm nhiệm phần này; xem `br/05`.

---

## 1. Grammar: quy tắc theo OI và pub

### 1.1. OI có giá trị, pub = `true` `[Confirmed]`

Grammar được công khai hoàn toàn. **Mọi người** truy cập được Grammar này trực tiếp từ system (đọc detail qua endpoint của Grammar), tìm kiếm được, và các user khác có thể đóng góp thêm Meaning, Example, Note.

### 1.2. OI có giá trị, pub = `false` `[Confirmed]`

Cờ `is_public` của Grammar **không định hình nó là private** (Grammar chỉ private khi `owner_id IS NULL`).
- Vẫn **truy cập, tìm kiếm và đọc detail bình thường từ system**.
- Cờ `pub = false` định hình rằng: **các user khác không thể thêm bớt meaning, example hay note** vào Grammar này.
- Khi chủ sở hữu tạo/sửa Meaning, Example, Note dưới Grammar này, Backend **tự đặt `is_public = false`** cho các nội dung đó.

### 1.3. OI = `NULL` `[Confirmed]`

- **Không cần kiểm tra pub** (Grammar xem như bị soft delete).
- Grammar **bị chặn đọc detail từ system** (`/public/grammar/{id}`) bởi bất kỳ ai. Khi gọi endpoint system sẽ trả về `404 Not Found`.
- Chỉ đọc được khi người dùng **đã clone Grammar về hub** (qua endpoint `/user_grammar/{id}`). Khi đó hub đọc detail bằng service nội bộ (`GrammarReadService.getDetail`).

### Bảng tóm tắt

| OI | pub | Đọc detail từ system | Đọc qua hub (`user_grammar`) | Cho phép user khác đóng góp Meaning/Note |
|---|---|---|---|---|
| có | `true` | Mọi người | Có (người đã clone) | **Có** |
| có | `false` | Mọi người | Có (người đã clone) | **Không** |
| `NULL` | bỏ qua | **Không ai** (404) | Có (người đã clone) | **Không** |

## 2. Meaning, Example, Note `[Confirmed]`

- **OI không bao giờ `NULL`** đối với `meaning`, `example`, `note`.
- **pub** quyết định người khác có thấy nội dung đó không.
- Khi đọc Grammar detail (cả từ system hay hub), danh sách Meaning, Example, Note **được lọc theo quyền xem**:
  - Chỉ trả về các bản ghi có `is_public = true` HOẶC bản ghi do chính người dùng hiện tại sở hữu (`owner_id = currentUserId`).
  - Người dùng ẩn danh chỉ thấy các bản ghi `is_public = true`.
- Nếu owner của Grammar bị khóa (`is_banned = true`), thông tin owner trả về `null` (trong endpoint public thì chặn với lỗi `ACCOUNT_BANNED`).
- Nếu Grammar cha có `pub = false`: nội dung con luôn `pub = false` (tự đặt khi ghi).
- Quyền đọc thực tế = Grammar được phép đọc **và** nội dung con được phép thấy. Không chỉ kiểm tra một vế.
- Sửa nội dung cá nhân của người này không được làm đổi nội dung công khai của người khác.
- Không công khai note chỉ vì Grammar được công khai; note cũng theo `pub` riêng.

`[Needs confirm]`:

- Khi Grammar chuyển từ `pub=true` sang `false`, các nội dung con đang `pub=true` có bị tự hạ về `false` không?
- Schema đang để `owner_id ON DELETE SET NULL` cho `meaning/example/note`, mâu thuẫn với quy tắc "OI không bao giờ NULL". Cần quyết định cách xử lý khi account bị xóa.

## 3. Đọc detail từ hub (vì sao Grammar OI NULL vẫn đọc được)

Luồng hợp lệ:

```
User → endpoint hub → kiểm tra user_grammar.user_id = identity hiện tại
     → gọi GrammarReadService.read(grammar_id) → trả detail
```

Luồng **cấm**:

```
User → hub (lấy được grammar_id) → gọi endpoint đọc Grammar của system
```

Quy tắc:

1. Service read của Grammar là **nội bộ**, chỉ được gọi từ luồng hub sau khi đã xác minh `user_grammar` thuộc người đang request.
2. Endpoint đọc Grammar của **system** (controller) phải từ chối Grammar có OI `NULL` (và Grammar `pub=false` của người khác), **kể cả khi người gọi đã có `user_grammar`**. Hub không được trở thành cửa sau để đọc ngược detail ở system.
3. Chặn ở tầng **controller** (hoặc một lớp tương đương); không chỉ dựa vào việc Frontend ẩn nút.

`[Needs confirm]` Khi đọc qua hub một Grammar OI `NULL`, người dùng thấy những Meaning/Example/Note nào (của chính họ, hay cả nội dung `pub=true` của người khác)? Xem `br/07`.

## 4. Folder và Deck

`folder` và `deck` cũng có OI/pub. Hiểu theo cùng tinh thần: công khai thì người khác có thể xem và clone về hub. Cụ thể từng hành động xem `br/03` và `br/04`.

`[Needs confirm]` Áp dụng chính xác quy tắc OI `NULL` và pub=false cho Folder/Deck (có đọc được không, có clone được không) chưa được chốt riêng.

## 5. Soft delete (tóm tắt) `[Confirmed]`

- Backend thực hiện soft delete bằng cách đặt `owner_id = NULL` và `is_public = false`.
- Không dùng `DELETE` vật lý hay thêm `deleted_at` khi chưa có quyết định đổi schema.
- Mọi truy vấn đọc/tìm kiếm/thống kê trên Grammar hệ thống phải loại Grammar OI `NULL` khỏi kết quả (theo mục 1.3).
- **`user_grammar` của người khác không bị ảnh hưởng** khi Grammar bị soft delete; người dùng vẫn học bình thường qua hub (mục 3).

## 6. Việc cần kiểm tra khi viết code

- [ ] Mọi endpoint đọc Grammar từ system đều kiểm tra OI/pub theo mục 1.
- [ ] Luồng hub không lộ endpoint đọc ngược system.
- [ ] Ghi Meaning/Example/Note: pub bị ép `false` nếu Grammar cha `pub=false`.
- [ ] Query danh sách/tìm kiếm loại Grammar OI `NULL`.
- [ ] Không coi việc ẩn trên giao diện là bảo mật.
