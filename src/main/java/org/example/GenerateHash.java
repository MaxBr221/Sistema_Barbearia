package org.example;

import org.mindrot.jbcrypt.BCrypt;

public class GenerateHash {
    public static void main(String[] args) {
        String hash = BCrypt.hashpw("igorbr22", BCrypt.gensalt());
        System.out.println("HASH_OUTPUT: " + hash);
    }
}
