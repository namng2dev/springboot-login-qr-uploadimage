# springboot-login-qr-uploadimage
Hướng dẫn:
Các chức năng:
-Tạo 1 tài khoản
-Xóa tài khoản
-Thay đổi mật khẩu
-Tự động gửi email
- Tự động sinh mã QR
- Upload ảnh

Mô tả chương trình:
Chương trình này tạo ra 1 khung đăng nhập để mở tài khoản nhanh cho 1 ngân hàng.

Đăng kí tài khoản mới: 
Yêu cầu người dùng nhập thông tin. Sẽ có 2 tùy chọn Bạn có muốn chọn số điện thoại làm tài khoản thanh toán không? Nếu có thì số tài khoản sẽ là số điện thoại
Nếu Số điện thoại hoặc số tài khoản đã được đăng kí thì sẽ báo đỏ cho người dùng biết
Chương trình sẽ kiểm tra thông tin nhập vào xem có hợp lệ không nếu hợp lệ thì chuyển sang bước sau

Đăng nhập
Nếu đăng nhập thành công thì chuyển tới trang view hiển thị thông tin người dùng
Có 3 chức năng khác: 
Sinh mã QR : Tạo ra 1 mã QR dựa trên thông tin người đó đã đăng kí
Upload ảnh: Cho người đó tải lên 1 ảnh. nếu tải ảnh thành công thì trạng thái activate sẽ là true(Tức là người đó đã gửi ảnh rồi)
Xóa tài khoản: Xóa toàn bộ thông tin của người đó(Bao gồm cả ảnh người dùng tải lên và mã QR được sinh ra)

Quên mật khẩu:
Nhập 1 số thông tin
Nếu thông tin chính xác thì khi ấn nút lấy lại mật khẩu thì sẽ có 1 email tự động gửi về email đã đăng kí tài khoản(Trong email sẽ có 1 mật khẩu mới được tạo ra. Người dùng có thể sử dụng mật khẩu này để đăng nhập)

Database: Hệ quản trị cơ sở dữ liệu là Oracle
Sau khi chạy chương trình hãy truy cập đường dẫn như sau: http://localhost:8080/
Chương trình sẽ hiển thị ra 1 khung đăng nhập từ đó có thể thực hiện các chức năng




