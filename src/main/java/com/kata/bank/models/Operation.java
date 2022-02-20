package com.kata.bank.models;

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
import java.time.LocalDate;

@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "type")
    private OperationType type;

    public Operation() {

    }

    public Operation(Integer id, Account account, Double amount, LocalDate date, OperationType type) {

        this.id = id;
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public static Builder builder() {

        return new Builder();
    }

    public Integer getId() {

        return id;
    }

    public Account getAccount() {

        return account;
    }

    public void setAccount(Account account) {

        this.account = account;
    }

    public Double getAmount() {

        return amount;
    }

    public void setAmount(Double amount) {

        this.amount = amount;
    }

    public LocalDate getDate() {

        return date;
    }

    public void setDate(LocalDate date) {

        this.date = date;
    }

    public OperationType getType() {

        return type;
    }

    public void setType(OperationType type) {

        this.type = type;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Operation operation = (Operation) o;

        return new EqualsBuilder()
            .append(id, operation.id)
            .append(account.getId(), operation.account.getId())
            .append(amount, operation.amount)
            .append(date, operation.date)
            .append(type, operation.type)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(account.getId())
            .append(amount)
            .append(date)
            .append(type)
            .toHashCode();
    }

    public static class Builder {

        private Integer id;
        private Account account;
        private Double amount;
        private LocalDate date;
        private OperationType type;

        Builder() {

        }

        public Builder id(Integer id) {

            this.id = id;
            return this;
        }

        public Builder account(Account account) {

            this.account = account;
            return this;
        }

        public Builder amount(Double amount) {

            this.amount = amount;
            return this;
        }

        public Builder date(LocalDate date) {

            this.date = date;
            return this;
        }

        public Builder type(OperationType type) {

            this.type = type;
            return this;
        }

        public Operation build() {

            return new Operation(id, account, amount, date, type);
        }
    }
}
