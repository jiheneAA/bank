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

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role role;

    @Column(name = "enable")
    private boolean isEnabled;

    @Column(name = "email")
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    public User() {

    }

    public User(
        Integer id,
        String firstName,
        String lastName,
        String username,
        String password,
        Role role,
        boolean isEnabled,
        String email,
        LocalDate birthDate,
        List<Account> accounts) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isEnabled = isEnabled;
        this.email = email;
        this.birthDate = birthDate;
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

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Role getRole() {

        return role;
    }

    public void setRole(Role role) {

        this.role = role;
    }

    public boolean isEnabled() {

        return isEnabled;
    }

    public void setEnabled(boolean enabled) {

        isEnabled = enabled;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public LocalDate getBirthDate() {

        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {

        this.birthDate = birthDate;
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
            .append(username, user.username)
            .append(password, user.password)
            .append(email, user.email)
            .append(birthDate, user.birthDate)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(firstName)
            .append(lastName)
            .append(username)
            .append(password)
            .append(email)
            .append(birthDate)
            .toHashCode();
    }

    public static class Builder {

        private Integer id;
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private Role role;
        private boolean isActive;
        private String email;
        private LocalDate dateOfBirth;
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

        public Builder username(String username) {

            this.username = username;
            return this;
        }

        public Builder password(String password) {

            this.password = password;
            return this;
        }

        public Builder role(Role role) {

            this.role = role;
            return this;
        }

        public Builder isActive(boolean isActive) {

            this.isActive = isActive;
            return this;
        }

        public Builder email(String email) {

            this.email = email;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {

            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder accounts(List<Account> accounts) {

            this.accounts = accounts;
            return this;
        }

        public User build() {

            return new User(id, firstName, lastName, username, password, role, isActive, email, dateOfBirth, accounts);
        }
    }
}
