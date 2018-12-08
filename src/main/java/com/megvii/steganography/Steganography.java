package com.megvii.steganography;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Steganography // [,stegə'nɔgrəfi]
{
    private static String mInputFilePath = System.getProperty("user.dir") + "/src/data/lenna.png";
    private static String mOutputFilePath = System.getProperty("user.dir") + "/src/data/lenna_red.png";

    public static void getSteganographyImage()
    {
        File file = new File(mInputFilePath);
        if (file.exists())
        {
            try
            {
                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth(), height = image.getHeight();
                for (int w = 0; w < width; w++)
                {
                    for (int h = 0; h < height; h++)
                    {
                        // 获取每个像素点的RGB颜色
                        Color color = new Color(image.getRGB(w, h));
                        int lsb = color.getRed() & 1;
                        image.setRGB(w, h, ((lsb == 0) ? new Color(0, 0, 0) : new Color(255, 255, 255)).getRGB());
                    }
                }
                ImageIO.write(image, "png", new File(mOutputFilePath));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
