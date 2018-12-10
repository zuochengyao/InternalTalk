package com.megvii.blockchain;

import com.megvii.blockchain.entity.Block;
import com.megvii.blockchain.entity.BlockHead;
import com.megvii.blockchain.entity.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class BlockChain
{
    private static final String BLOCK_CHAIN_VERSION = "0x01";
    private Block mRootBlock;
    private LinkedList<Block> mBlockChain;

    private static volatile BlockChain mInstance;

    private BlockChain()
    {
        mBlockChain = new LinkedList<>();
    }

    public static BlockChain getInstance()
    {
        if (mInstance == null)
        {
            synchronized (BlockChain.class)
            {
                if (mInstance == null)
                    mInstance = new BlockChain();
            }
        }
        return mInstance;
    }

    /**
     * 初始化区块链 实际就是创建一个 创世区块
     */
    public Block init()
    {
        Transaction transaction = new Transaction(null, "A", 10);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        BlockHead head = new BlockHead(BLOCK_CHAIN_VERSION, "", Util.getMerkleRoot(transactions), System.currentTimeMillis() + "", "0", "0");
        mRootBlock = new Block(0, head, transactions);
        mBlockChain.add(mRootBlock);
        System.out.println(String.format(Locale.CHINESE, "BlockChain init!\n\nFirst block #%d has been added!\r\nHash: %s", mRootBlock.getIndex(), mRootBlock.hash()));
        return mRootBlock;
    }

    /**
     * 对区块头做两次sha256哈希运算，得到的结果如果小于区块头中规定的难度目标，即挖矿成功
     */
    @SuppressWarnings("unused")
    public Block getNextBlockByNonce(Block last)
    {
        BlockHead head = last.getHead();
        final String str = head.getVersion() + head.getPreviousBlockHash() + head.getMerkleRoot() + head.getTime() + head.getTargetBits();
        for (int nonce = 0; nonce < Math.pow(2, 32); ++nonce)
        {
            String headStr = str + nonce;
            BigInteger sha256 = new BigInteger(Util.SHA256(Util.SHA256(headStr)));
            BigInteger target = new BigInteger(head.getTargetBits());
            if (sha256.compareTo(target) < 0)
                return newBlock(last, nonce);
        }
        return null;
    }

    /**
     * 模拟挖矿 POW
     */
    public Block getNextBlockByTime(Block last)
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return newBlock(last, Util.getRandomNonce());
    }

    public void transaction()
    {

    }

    private Block newBlock(Block last, int nonce)
    {
        BlockHead head = new BlockHead(BLOCK_CHAIN_VERSION, last.hash(), Util.getMerkleRoot(null), System.currentTimeMillis() + "", "0", nonce + "");
        Block block = new Block(last.getIndex() + 1, head, null);
        mBlockChain.add(block);
        return block;
    }

}
