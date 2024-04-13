package ru.kampaii.examples.domain.entities;

public class OperationsEntity implements Entity{
    private final Integer toAccount;
    private final Integer fromAccount;
    private final Float sum;
    private final String comment;
    private final Integer id;

    public OperationsEntity(Integer toAccount, Integer fromAccount, Float sum, String comment, Integer id) {
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.sum = sum;
        this.comment = comment;
        this.id = id;
    }

    public Integer getToAccount() {
        return toAccount;
    }

    public Integer getFromAccount() {
        return fromAccount;
    }

    public Float getSum() {
        return sum;
    }

    public String getComment() {
        return comment;
    }

    public Integer getId() {
        return id;
    }

}