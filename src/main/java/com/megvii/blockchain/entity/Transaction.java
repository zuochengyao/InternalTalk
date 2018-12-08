package com.megvii.blockchain.entity;

import org.json.JSONObject;

/**
 * 交易记录
 */
public class Transaction
{
    private String from;
    private String to;
    private int amount;

    public Transaction(String from, String to, int amount)
    {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }

    public int getAmount()
    {
        return amount;
    }

    @Override
    public String toString()
    {
        JSONObject json = new JSONObject();
        json.append("from", this.from);
        json.append("to", this.to);
        json.append("amount", this.amount);
        return json.toString();
    }
}
