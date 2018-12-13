package com.megvii.blockchain.entity;

import org.json.JSONObject;

import java.util.List;

/**
 * 完整区块
 *
 * 节点监听全网交易，通过验证的交易进入节点的交易内存池，并更新交易数据的Merkle Hash值
 * 更新时间戳
 * 尝试不同的随机数(Nonce)，进行hash计算
 * 重复该过程至找到合理的hash
 * 打包block：先装入block meta信息，然后是交易数据
 * 对外部广播出新block
 * 其他节点验证通过后，链接至Block Chain，主链高度加一，然后切换至新block后面挖矿
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
