package com.revature;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncoder {
    Argon2 encoder;
    PasswordEncoder(){
        this.encoder = Argon2Factory.create();
    }

    String hashPassword(String pass){
        char[] passToEncode = pass.toCharArray();
        // Using low memory and iterations for speed, if using for actual security remember to up these.
        String hashPass = encoder.hash(10, 2048, 1,passToEncode);
        encoder.wipeArray(passToEncode);
        return hashPass;
    }
    Boolean confirmPass(String passToConfirm, String hashedPass){
        char[] passToCheck = passToConfirm.toCharArray();
        boolean confirm =encoder.verify(hashedPass,passToCheck);
        encoder.wipeArray(passToCheck);
        return confirm;

    }
}
