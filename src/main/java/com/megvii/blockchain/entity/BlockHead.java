package com.megvii.blockchain.entity;

import com.megvii.blockchain.Util;

/**
 * 区块头
 */
@SuppressWarnings("all")
public class BlockHead
{
    /** 版本号：无需关心 */
    private String version;

    /** 前一区块的哈希值：SHA256(head = version + previous_block_hash + merkle_root + time + target_bits + nonce) */
    private String previousBlockHash;

    /** 默克尔根：默克尔树（Merkle Tree）算法生成，并不是直接计算整个字符串的Hash值，而是每个交易都计算一个Hash值，然后两两连接再次计算Hash，一直到最顶层的Merkle根 */
    private String merkleRoot;

    /** 时间戳 */
    private String time;

    /**
     * 目标值 = 最大目标值 （0x00000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF） / 难度系数（https://btc.com/stats/diff）
     * 与挖矿难度有关，一个区块头的SHA256值必定要小于或等于目标HASH值，该区块才能被网络所接受，目标HASH越低，产生一个新区块的难度越大。
     */
    private String targetBits;

    /**
     * 是一个32位的二进制值，即最大可以到21.47亿。
     * 当前区块的哈希由区块头唯一决定。如果要对同一个区块反复计算哈希，就意味着，区块头必须不停地变化，否则不可能算出不一样的哈希。
     * 区块头里面所有的特征值都是固定的，为了让区块头产生变化，中本聪故意增加了一个随机项，矿工的作用其实就是猜出 Nonce 的值，使得区块头的哈希可以小于目标值，从而能够写入区块链
     */
    private String nonce;

    public BlockHead(String version, String previousBlockHash, String merkleRoot, String time, String targetBits, String nonce)
    {
        this.version = version;
        this.previousBlockHash = previousBlockHash;
        this.merkleRoot = merkleRoot;
        this.time = time;
        this.targetBits = targetBits;
        this.nonce = nonce;
    }

    public String getVersion()
    {
        return version;
    }

    public String getPreviousBlockHash()
    {
        return previousBlockHash;
    }

    public String getMerkleRoot()
    {
        return merkleRoot;
    }

    public String getTime()
    {
        return time;
    }

    public String getTargetBits()
    {
        return targetBits;
    }

    public String getNonce()
    {
        return nonce;
    }

    @Override
    public String toString()
    {
        return Util.SHA256(version + previousBlockHash + merkleRoot + time + targetBits + nonce);
    }
}
