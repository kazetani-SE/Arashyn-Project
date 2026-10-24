# BR-05 — Account, Authentication, Role, Hạ tầng

Phạm vi: `account`, role, session, đăng ký OTP, OAuth, avatar, môi trường.

---

## 1. Account `[Confirmed]`

`account(id, username, avatar, role, is_banned, created_at)`

- `account.id` tham chiếu `auth.users(id)` (Supabase Auth), `ON DELETE CASCADE`.
- Account là hồ sơ nghiệp vụ; **không** đồng nhất với toàn bộ dữ liệu authentication do Supabase quản lý.
- `username`: duy nhất. Không dùng thay account ID hay làm cơ chế xác thực.
- `is_banned`: người dùng bị cấm. Mọi luồng cần xác thực phải tôn trọng cờ này. `[Needs confirm]` hành vi chi tiết khi bị ban (chặn đăng nhập hay chỉ chặn ghi).

## 2. User Identity `[Confirmed]`

- Backend xác định danh tính qua cơ chế authentication và security context.
- **Không tin UUID người dùng do client gửi.** Mọi `user_id`/`owner_id` khi ghi lấy từ identity đã xác thực.

## 3. Role `[Confirmed]`

Enum `account_role`: `guest`, `user` (mặc định), `contributor`, `admin`.

- **RBAC** hiện đảm nhiệm authorization chi tiết. Bảng quyền theo role không nằm trong tài liệu này; khi cần, bổ sung vào file riêng trước khi code phụ thuộc vào nó.
- Nguồn role nghiệp vụ là **Account repository**, không lấy trực tiếp từ JWT claims.
- Role cao hơn **không** mặc nhiên bỏ qua ownership hoặc quy tắc privacy (`br/02`).
- `[Needs confirm]` `guest` là giá trị enum của bản ghi Account hay chỉ là người chưa đăng nhập.

## 4. Account Session `[Confirmed]`

- `account_sessions` dùng `account_id` làm khóa chính, quan hệ một-một với Account (migration không nằm trong các file đã xem).
- Refresh token phải tuân thủ thời hạn và cơ chế bảo vệ đang triển khai.
- **Không log** refresh token, không đưa vào response hay debug.

## 5. Đăng ký và OTP `[Confirmed]`

- Luồng đăng ký dùng OTP và dữ liệu đăng ký chờ xử lý.
- OTP, hạn hiệu lực, giới hạn resend, điều kiện hoàn tất theo cấu hình Backend.
- Tài khoản chờ xác minh **chưa** phải tài khoản hoàn tất.

## 6. OAuth `[Confirmed]`

- Có tích hợp Supabase OAuth cho Google và GitHub.
- Không tự chuyển việc trao đổi code hoặc xử lý token sang tầng khác khi chưa kiểm tra luồng hiện tại.

## 7. Avatar `[Deferred]`

Chưa chốt: avatar public hay private, lưu ở S3 hay Supabase Storage. Khi chốt, phân biệt metadata (`account.avatar` trong DB) với file thật trong object storage. Không mặc định mọi avatar public.

## 8. Hạ tầng và môi trường

- **Supabase**: PostgreSQL + Authentication. Không mặc định mọi thành phần chạy trong Supabase.
- **Migration**: quản lý bằng source control, theo quy trình triển khai của dự án. Các migration đã chạy không sửa lại; thay đổi mới thêm migration mới.
- **Object storage**: tách khỏi PostgreSQL; Amazon S3 là hướng được định sẵn.
- **Môi trường**: API base URL, Supabase URL... theo cấu hình từng môi trường. **Secrets không đưa vào Frontend bundle hoặc commit lên repo.**
- **RLS**: không thấy trong các migration đã xem; hiện authorization do RBAC ở Backend đảm nhiệm. `[Deferred]` Nếu bảng ở schema `public` có thể truy cập qua Supabase Data API thì nên xem xét lại.
- **Deployment**: chưa chốt quyết định chính thức cho Frontend/Backend/object storage. `[Deferred]`
