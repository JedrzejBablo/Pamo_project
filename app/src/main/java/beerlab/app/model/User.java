package beerlab.app.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class User {
    private Long id;
    private String username;
    private String password;
    private Gender gender;
    private String email;
    private List<RoleDto> rolesDto = new LinkedList<>();
    private Double balance;
    private LocalDate dateOfBirth;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", rolesDto=" + rolesDto +
                ", balance=" + balance +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
