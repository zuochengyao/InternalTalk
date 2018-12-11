package com.megvii.blockchain;

import com.megvii.blockchain.entity.Block;
import com.megvii.blockchain.entity.BlockHead;
import com.megvii.blockchain.entity.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlockChain
{
    private static final String LOCALHOST_ADDR = "127.0.0.1:8080";
    private static final String BLOCK_CHAIN_VERSION = "0x01";
    private static final String COIN_BASE = "CoinBase";

    private ArrayList<Block> mBlockChain;
    private ArrayList<Transaction> mTransactions;

    private int mTarget;

    private static volatile BlockChain mInstance;

    private BlockChain()
    {
        mBlockChain = new ArrayList<>();
        mTransactions = new ArrayList<>();
        mTarget = 0;
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

    private Block getLastBlock()
    {
        return mBlockChain.size() > 0 ? mBlockChain.get(mBlockChain.size() - 1) : null;
    }

    /**
     * 初始化区块链
     * 实际就是创建一个 创世区块
     */
    void init()
    {
        System.out.println("BlockChain init!\nReady to create the ROOT block!");
        Block mRootBlock = newBlock(Util.MD5(LOCALHOST_ADDR));
        int nonce = 0;
        while (!Util.isPoWNonce(mRootBlock.getHead().toPowString(), nonce))
            nonce++;
        // 创建区块头
        ensureBlock(mRootBlock, nonce);
    }

    /**
     * 伪代码
     * 比特币 POW
     * 对区块头做两次sha256哈希运算，得到的结果如果小于区块头中规定的难度目标，即挖矿成功
     */
    @SuppressWarnings("unused")
    private Block getNextBlockByPOW()
    {
        Block block = newBlock(Util.MD5(LOCALHOST_ADDR));
        for (int nonce = 0; nonce < Math.pow(2, 32); ++nonce)
        {

            String headStr = block.getHead().toPowString() + nonce;
            Integer sha256 = new Integer(Util.SHA256(Util.SHA256(headStr)));
            // 计算的sha256值 <= 目标值 表示找到新的区块
            if (sha256.compareTo(mTarget) <= 0)
                ensureBlock(block, nonce);
        }
        return block;
    }

    /**
     * 自定义 POW
     * 找到一个自然数nonce，使得md5(version + previous_block_hash + merkle_root + time + target_bits + nonce)的前6位都是0
     */
    @SuppressWarnings("all")
    public Block getNextBlockByPOW(String host, int port)
    {
        int nonce = 0;
        Block block = newBlock(Util.MD5(host + port));
        while (!Util.isPoWNonce(block.getHead().toPowString(), nonce))
            nonce++;
        ensureBlock(block, nonce);
        return block;
    }

    /**
     * 模拟挖矿
     */
    public void getNextBlock(String host, int port)
    {
        int nonce = Util.getRandomNonce();
        System.out.println(String.format(Locale.CHINESE, "Nonce %d has been found! MD5 : %s", nonce, Util.MD5(nonce + "")));
        Block block = newBlock(Util.MD5(host + port));
        ensureBlock(block, Util.getRandomNonce());
    }

    public void newTransaction(String from, String to, int amount)
    {
        Transaction transaction = new Transaction(from, to, amount);
        mTransactions.add(transaction);
    }

    /**
     * 通过POW创建一个区块实体
     * @param toAddr 区块所属地址，是一个MD5的串
     */
    private Block newBlock(String toAddr)
    {
        // 区块奖励 交易记录
        newTransaction(COIN_BASE, toAddr, 10);
        List<Transaction> transactions = new ArrayList<>(mTransactions);
        Block last = getLastBlock();
        // 创建区块头
        BlockHead head = new BlockHead(BLOCK_CHAIN_VERSION, last != null ? last.toString() : "", Util.getMerkleRoot(transactions), System.currentTimeMillis() + "", mTarget);
        return new Block(last != null ? last.getIndex() + 1 : 0, head, transactions);
    }

    /**
     * 区块入链
     */
    private void ensureBlock(Block block, int nonce)
    {
        block.getHead().setNonce(nonce);
        // 区块入链
        mBlockChain.add(block);
        // 更新目标值
        mTarget++;
        // 清空当前交易记录
        mTransactions.removeAll(block.getTransactions());
        System.out.println(String.format(Locale.CHINESE, "Block #%d has been added!\r\nHash: %s", block.getIndex(), block.toString()));
        pushBlockChainToAll();
    }

    private void pushBlockChainToAll()
    {
        // TODO 将新产生的区块推送给所有节点
    }
}
