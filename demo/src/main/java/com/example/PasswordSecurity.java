package com.example;

import com.password4j.types.Argon2;
import java.security.SecureRandom;
import com.password4j.Argon2Function;
import com.password4j.Hash;
import com.password4j.Password;

public class PasswordSecurity {
    
    // Generates a random salt (double value between 0 and 1)
    public Double generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextDouble(0, 1);            
   }

    // Hashes the password using Argon2    
    public String hashPassword(String password, Double salt)    {

        // Declare an instance of Argon2 class
        Argon2Function argon2 = Argon2Function.getInstance(15, 2, 1, 32, Argon2.ID);
        
        // Generating a hash
        Hash hash = Password.hash("password")
                            .addSalt(salt.toString())
                            .with(argon2);
        
        return hash.getResult();
    }

    // Verify password
    public boolean verifyPassword(String passwordToVerify, Student student)
    {
        if (hashPassword(passwordToVerify, student.getSalt()).equals(student.getPasswordHash()))
        {
            return true;
        }
        return false;
    }
}

