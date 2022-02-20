package com.kata.bank.models;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User user;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @OneToMany(mappedBy = "account")
    private List<Operation> operations = new ArrayList<>();

    public Account() {

    }

    public Account(Integer id, String number, User user, LocalDate dateCreation, Double balance, LocalDate lastUpdate, List<Operation> operations) {

        this.id = id;
        this.number = number;
        this.user = user;
        this.dateCreation = dateCreation;
        this.balance = balance;
        this.lastUpdate = lastUpdate;
        if (CollectionUtils.isNotEmpty(operations)) {
            this.operations.addAll(operations);
        }
    }

    public static Builder builder() {

        return new Builder();
    }

    public Integer getId() {

        return id;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public LocalDate getDateCreation() {

        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {

        this.dateCreation = dateCreation;
    }

    public Double getBalance() {

        return balance;
    }

    public void setBalance(Double balance) {

        this.balance = balance;
    }

    public LocalDate getLastUpdate() {

        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {

        this.lastUpdate = lastUpdate;
    }

    public List<Operation> getOperations() {

        return Collections.unmodifiableList(operations);
    }

    public void setOperations(List<Operation> operations) {

        this.operations = operations;
    }

    public void addOperation(Operation operation) {

        if (!operations.contains(operation)) {
            operations.add(operation);
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Account account = (Account) o;

        return new EqualsBuilder()
            .append(id, account.id)
            .append(number, account.number)
            .append(user, account.user)
            .append(dateCreation, account.dateCreation)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(number)
            .append(user)
            .append(dateCreation)
            .toHashCode();
    }

    public static class Builder {

        private Integer id;
        private String accountNumber;
        private User user;
        private LocalDate dateCreation;
        private Double balance;
        private LocalDate lastUpdate;
        private List<Operation> operations;

        Builder() {

        }

        public Builder id(Integer id) {

            this.id = id;
            return this;
        }

        public Builder accountNumber(String accountNumber) {

            this.accountNumber = accountNumber;
            return this;
        }

        public Builder user(User user) {

            this.user = user;
            return this;
        }

        public Builder dateCreation(LocalDate dateCreation) {

            this.dateCreation = dateCreation;
            return this;
        }

        public Builder balance(Double balance) {

            this.balance = balance;
            return this;
        }

        public Builder lastUpdate(LocalDate lastUpdate) {

            this.lastUpdate = lastUpdate;
            return this;
        }

        public Builder operations(List<Operation> operations) {

            this.operations = operations;
            return this;
        }

        public Account build() {

            return new Account(id, accountNumber, user, dateCreation, balance, lastUpdate, operations);
        }
    }
}
