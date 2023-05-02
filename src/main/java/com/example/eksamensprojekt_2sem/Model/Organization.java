package com.example.eksamensprojekt_2sem.Model;

public class Organization {

    private int organization_id;
    private String organization_name;
    private String password;

    public Organization(int organization_id, String organization_name, String password) {
        this.organization_id = organization_id;
        this.organization_name = organization_name;
        this.password = password;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
