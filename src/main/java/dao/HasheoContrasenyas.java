package dao;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HasheoContrasenyas {
    public String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        try {
            return argon2.hash(2, 10000, 1, password.toCharArray());
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }
}
