package com.kata.bank.models;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    public User() {

    }

    public User(
        Integer id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String address,
        String phoneNumber,
        String email,
        List<Account> accounts) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        if (CollectionUtils.isNotEmpty(accounts)) {
            this.accounts.addAll(accounts);
        }
    }

    public static Builder builder() {

        return new Builder();
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
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

    public LocalDate getDateOfBirth() {

        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {

        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public List<Account> getAccounts() {

        return Collections.unmodifiableList(accounts);
    }

    public void setAccounts(List<Account> accounts) {

        this.accounts = accounts;
    }

    public void addAccount(Account account) {

        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        return new EqualsBuilder()
            .append(id, user.id)
            .append(firstName, user.firstName)
            .append(lastName, user.lastName)
            .append(dateOfBirth, user.dateOfBirth)
            .append(address, user.address)
            .append(phoneNumber, user.phoneNumber)
            .append(email, user.email)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(firstName)
            .append(lastName)
            .append(dateOfBirth)
            .append(address)
            .append(phoneNumber)
            .append(email)
            .toHashCode();
    }

    public static class Builder {

        private Integer id;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String address;
        private String phoneNumber;
        private String email;
        private List<Account> accounts;

        Builder() {

        }

        public Builder id(Integer id) {

            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {

            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {

            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {

            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder address(String address) {

            this.address = address;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {

            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {

            this.email = email;
            return this;
        }

        public Builder accounts(List<Account> accounts) {

            this.accounts = accounts;
            return this;
        }

        public User build() {

            return new User(id, firstName, lastName, dateOfBirth, address, phoneNumber, email, accounts);
        }
    }
}
