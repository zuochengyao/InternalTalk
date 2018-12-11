package com.megvii.blockchain.entity;

import org.json.JSONObject;

import java.util.List;

/**
 * 完整区块
 */
public class Block
{
    private int index;
    private BlockHead mHead;
    private final int mMagicNo = 0xd9b4bef9;
    private int mTransactionSize;
    private List<Transaction> mTransactions;

    public Block(int index, BlockHead mHead, List<Transaction> mTransactions)
    {
        this.index = index;
        this.mHead = mHead;
        this.mTransactions = mTransactions;
        this.mTransactionSize = mTransactions != null && mTransactions.size() > 0 ? mTransactions.size() : 0;
    }

    public int getIndex()
    {
        return index;
    }

    public BlockHead getHead()
    {
        return mHead;
    }

    public int getMagicNo()
    {
        return mMagicNo;
    }

    public int getTransactionSize()
    {
        return mTransactionSize;
    }

    public List<Transaction> getTransactions()
    {
        return mTransactions;
    }

    @Override
    public String toString()
    {
        return mHead.toString();
    }

    public String toJsonString()
    {
        return new JSONObject(this).toString();
    }
}
