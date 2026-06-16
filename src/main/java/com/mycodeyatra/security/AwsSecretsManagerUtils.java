package com.mycodeyatra.security;
 
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import org.json.JSONObject;
 
public class AwsSecretsManagerUtils {
 
    public static String getSecret(String secretName, String key) {
 
        Region region = Region.US_EAST_1;
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();
 
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
 
        GetSecretValueResponse getSecretValueResponse;
 
        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch secret from AWS", e);
        }
 
        String secretString = getSecretValueResponse.secretString();
 
        // AWS stores secrets as JSON strings. Parse it to get the specific key.
        JSONObject jsonObject = new JSONObject(secretString);
        return jsonObject.getString(key);
    }
}