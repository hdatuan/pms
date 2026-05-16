package hdatuan.enums;

/**
 * Enum định nghĩa các Role của người dùng trong hệ thống.
 * Mỗi Role ánh xạ tới ID tương ứng trong bảng `roles` của Database.
 * 
 * Sử dụng enum này để tránh hardcode "magic numbers" (VD: != 1, == 2)
 * rải rác khắp codebase.
 */
public enum UserRole {

    ADMIN(1),
    MANAGER(2),
    STAFF(3);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Tìm UserRole tương ứng theo ID.
     * @return UserRole hoặc null nếu không tìm thấy.
     */
    public static UserRole fromId(int id) {
        for (UserRole role : values()) {
            if (role.id == id) return role;
        }
        return null;
    }
}
