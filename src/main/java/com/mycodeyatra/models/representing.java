package com.mycodeyatra.models;
/**
 * User class representing the test data payload for the practice form.
 * Implements the Builder design pattern to construct complex data payloads cleanly.
 */
public class User {
    private final String fullName;
    private final String email;
    private final String phone;
    private final String gender;
    private final String country;
    private final String tool;
    private final String bio;
    private User(Builder builder) {
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.gender = builder.gender;
        this.country = builder.country;
        this.tool = builder.tool;
        this.bio = builder.bio;
    }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getCountry() { return country; }
    public String getTool() { return tool; }
    public String getBio() { return bio; }
    public static class Builder {
        private String fullName;
        private String email;
        private String phone;
        private String gender = "Male"; // Default value
        private String country = "India"; // Default value
        private String tool = "Selenium"; // Default value
        private String bio = "";
        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }
        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }
        public Builder setTool(String tool) {
            this.tool = tool;
            return this;
        }
        public Builder setBio(String bio) {
            this.bio = bio;
            return this;
        }
        public User build() {
            if (fullName == null || email == null) {
                throw new IllegalStateException("FullName and Email are required fields for a User.");
            }
            return new User(this);
        }
    }
}