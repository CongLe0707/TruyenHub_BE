package com.example.TruyenHub.model.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ResultCode implements IResultCode{
    SUCCESS("0000","Thành công"),
    INVALID_JSON_FORMAT("123", "Dữ liệu json không đúng định dạng"),
    VALIDATION_ERROR("1234", "Lỗi"),
    INTERNAL_SERVER_ERROR("234", "Lỗi server"),

    NO_AUTHOR("411","Không tim thấy tác giả"),
    NO_CATEGORY("412","không tìm thấy Thể loại" ),
    NO_STORY_NAME("413","Không tìm thấy truyện" ),
    NO_COMIC_NAME("414","Không tìm thấy truyện tranh" ),
    DUPLICATE_CHAPTER("415", "Chapter đã ra rồi vui lòng nhập lại chapter"),
    DUPLICATE_IMAGE("416", "Ảnh đã tồn tại hoặc bị trùng lặp"),
    DUPLICATE_CHAPTER_NAME("417", "Tên chapter đã tồn tại" ),
    NO_COMIC_ID("418","Tìm không thấy truyện tranh"),
    NO_CHAPTER_COMIC("419", "Tìm không thấy chapter"),
    USER_NAME("418","User đã tồn tại"),
    USER_NOT_FOUND("418","Bạn đã nhập sai tài khoản hoặc mật khẩu" ),
    NO_USER("420","Tìm không thấy người dùng "),
    ID_NOT_FOUND("42112","Sai thông tin"),
    SUCCESS_RATING("1123","Đánh giá thành công"),
    CMT_SUCCESS("1223","Bình luận thành công"),
    RATING_COMIC("421","Bạn đã đánh giá rồi" );


    private String code;
    private String message;

    ResultCode(String code, String message ) {
        this.code = code;
        this.message = message;

    }
}
