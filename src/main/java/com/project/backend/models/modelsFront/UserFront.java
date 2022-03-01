package com.project.backend.models.modelsFront;

import com.project.backend.models.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.math.BigDecimal;

@ApiModel(description = "Model for User")
public class UserFront {

    @ApiModelProperty(notes = "Holds User Entity info")
    private User user;
    @ApiModelProperty(notes = "User's id")
    private Long id;
    @ApiModelProperty(notes = "User's username")
    private String userName;
    @ApiModelProperty(notes = "User's total money spent on purchases")
    private BigDecimal moneySpent;
    @ApiModelProperty(notes = "User's total times of placing order")
    private Long freq;
    @ApiModelProperty(notes = "User's first name")
    private String firstName;
    @ApiModelProperty(notes = "User's last name")
    private String lastName;
    @ApiModelProperty(notes = "User's email")
    private String email;
    @ApiModelProperty(notes = "User's role")
    private String role;

    public UserFront() {
    }

    public UserFront(User user, Long id, String userName, BigDecimal moneySpent, Long freq, String firstName, String lastName, String email, String role) {
        this.user = user;
        this.id = id;
        this.userName = userName;
        this.moneySpent = moneySpent;
        this.freq = freq;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(BigDecimal moneySpent) {
        this.moneySpent = moneySpent;
    }

    public Long getFreq() {
        return freq;
    }

    public void setFreq(Long freq) {
        this.freq = freq;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
