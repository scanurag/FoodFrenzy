package com.example.demo.loginCredentials;

public class Login {
        private String role; //Admin or User

        private String email;
        
        private String password;

        public String getRole(){
            return role;
        }

        public void setRole(String role){
            this.role = role;
        }
        
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        @Override
        public String toString() {
            return "Login [name=" + email + ", password=" + password + "]";
        }	
}
