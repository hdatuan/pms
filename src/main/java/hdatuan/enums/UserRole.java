package hdatuan.enums;

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

    public static UserRole fromId(int id) {
        for (UserRole role : values()) {
            if (role.id == id)
                return role;
        }
        return null;
    }
}
