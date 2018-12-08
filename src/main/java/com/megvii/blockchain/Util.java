package com.megvii.blockchain;

import com.megvii.blockchain.entity.Transaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

public class Util
{
    public static String SHA256(String str)
    {
        String strResult = null;
        if (str != null && str.length() > 0)
        {
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(str.getBytes());
                byte byteBuffer[] = messageDigest.digest();
                StringBuilder strHexString = new StringBuilder();
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                        strHexString.append('0');
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    /**
     * 伪代码 获取默克尔根
     * @param list 交易记录
     */
    static String getMerkleRoot(List<Transaction> list)
    {
        return list != null && list.size() > 0 ? list.toString() : "NullMerkleRoot";
    }

    static int getRandomNonce()
    {
        Random random = new Random();
        return random.nextInt((int) Math.pow(2,32));
    }
}
