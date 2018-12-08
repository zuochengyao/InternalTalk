package com.megvii;

import com.megvii.blockchain.BlockChain;
import com.megvii.blockchain.entity.Block;
import com.megvii.steganography.Steganography;

import java.util.Locale;

@SuppressWarnings("all")
public class Program
{
    public static void main(String[] args)
    {
//        getSteganographyImage();

//        Block block = initBlockChain();
//        genBlock(5, block);
    }

    private static void getSteganographyImage()
    {
        Steganography.getSteganographyImage();
    }

    private static Block initBlockChain()
    {
        return BlockChain.getInstance().init();
    }

    private static void genBlock(int size, Block last)
    {
        if (size > 0)
        {
            Block block = BlockChain.getInstance().getNextBlockByTime(last);
            System.out.println(String.format(Locale.CHINESE, "Block #%d has been added!\r\nHash: %s", block.getIndex(), block.hash()));
            genBlock(--size, block);
        }
    }
}
