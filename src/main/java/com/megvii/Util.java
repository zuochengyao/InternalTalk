package com.megvii;

import com.megvii.blockchain.entity.Transaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
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

    public static String MD5(String str)
    {
        String md5 = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] barr = md.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < barr.length; i++)
            {
                sb.append(byte2Hex(barr[i]));
            }
            md5 = sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return md5;
    }

    private static String byte2Hex(byte b)
    {
        String[] h = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int i = b;
        if (i < 0)
            i += 256;
        return h[i / 16] + h[i % 16];
    }

    /**
     * 伪代码 获取默克尔根
     * @param list 交易记录
     */
    public static String getMerkleRoot(List<Transaction> list)
    {
        String str = list != null && list.size() > 0 ? list.toString() : "NullMerkleRoot";
        return SHA256(str);
    }

    public static int getRandomNonce()
    {
        Random random = new Random();
        return random.nextInt((int) Math.pow(2, 32));
    }

    /**
     * 找到一个自然数nonce，使得md5(当天日期+你的用户名+当前的票数+x)的前6位都是0
     */
    public static boolean isPoWNonce(String str, int nonce)
    {
        String md5 = MD5(str + nonce);
        if (md5.startsWith("000000"))
            System.out.println(String.format(Locale.CHINESE, "Nonce %d has been found! MD5 : %s", nonce, md5));
        return md5.startsWith("000000");
    }

    public static String base64Decode(String s)
    {
        return new String(Base64.getDecoder().decode(s));
    }
}
