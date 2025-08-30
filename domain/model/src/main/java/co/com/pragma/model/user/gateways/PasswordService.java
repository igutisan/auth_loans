package co.com.pragma.model.user.gateways;

public interface PasswordService {
    String encode(String password);

    boolean matches(String password, String encodedPassword);
}
