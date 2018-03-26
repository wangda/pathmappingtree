package me.wangda.pathmappingtree;

public class PathConstants {

    public static String[] path = new String[]{
            "GET /users/search",
            "GET /users",
            "POST /users",
            "PUT /users/{\\d+}",
            "DELETE /users/s*",
            "POST /auth/login",
            "GET /auth/logout",
            "GET|POST /vehicle/{\\d+}/info/*",
            "GET|POST|PUT|DELETE /vehicle/4567/info/*/*"
    };
}
