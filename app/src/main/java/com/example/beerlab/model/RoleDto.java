package com.example.beerlab.model;

public class RoleDto {
    private Long id;
    private String roleName;

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
