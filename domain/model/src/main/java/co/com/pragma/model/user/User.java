package co.com.pragma.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    private String names;
    private String lastNames;
    private String password;
    private String dni;
    private LocalDate dob;
    private String phone;
    private String address;
    private String email;
    private BigDecimal salary;
    private UserRoles role;
}
