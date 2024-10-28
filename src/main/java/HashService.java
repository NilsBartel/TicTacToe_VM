import at.favre.lib.crypto.bcrypt.BCrypt;

public class HashService {






    public static String hash(String password) {
        String hashed;
        hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        return hashed;
    }



    public static boolean verify(String password, String hashed) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashed);
        return result.verified;
    }



}