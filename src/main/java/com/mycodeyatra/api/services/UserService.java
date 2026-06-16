package com.mycodeyatra.api.services;
 
import com.mycodeyatra.api.ApiClient;
import com.mycodeyatra.api.models.UserPayload;
import io.restassured.response.Response;
 
public class UserService {
 
    private static final String ENDPOINT = "/v1/users";
 
    public static Response createUser(UserPayload payload) {
        return ApiClient.getBaseSpec()
                .body(payload)
                .post(ENDPOINT);
    }
 
    public static Response getUser(String userId) {
        return ApiClient.getBaseSpec()
                .get(ENDPOINT + "/" + userId);
    }
 
    public static Response deleteUser(String userId) {
        return ApiClient.getBaseSpec()
                .delete(ENDPOINT + "/" + userId);
    }
}