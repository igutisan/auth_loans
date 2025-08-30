package co.com.pragma.r2dbc.entity;



import co.com.pragma.model.user.UserRoles;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.UUID;

@Table(name = "user_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private UUID id;

    @Column("name")
    private String names;

    @Column("last_names")
    private String lastNames;

    @Column("password")
    private String password;

    @Column("dni")
    private String dni;

    @Column("dob")
    private LocalDate dob;

    @Column("phone")
    private String phone;

    @Column("address")
    private String address;

    @Column("email")
    private String email;

    @Column("salary")
    private BigDecimal salary;

    @Column("rol")
    private UserRoles rol;


}
