package com.glacier.glacierdiary.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 验证码工具类
 * @since 2025/4/5 21:16
 */
public class CaptchaGenerator {


    /**
     * 验证码字符集
     */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    /**
     * 图片宽度
     */
    private static final int WIDTH = 120;
    /**
     * 图片高度
     */
    private static final int HEIGHT = 40;
    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 30;
    /**
     * 验证码长度
     */
    private static final int CODE_LENGTH = 4;

    public static Map<String, String> generateCaptcha() throws IOException {
        // 创建图片缓冲区
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // 设置背景颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // 设置字体
        graphics.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

        // 生成随机验证码
        StringBuilder captchaText = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char ch = CHARACTERS.charAt(index);
            captchaText.append(ch);

            // 绘制字符
            // 字符颜色
            graphics.setColor(getRandomColor(50, 150));
            // 字符位置
            graphics.drawString(String.valueOf(ch), 20 + i * 20, 30);
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            graphics.setColor(getRandomColor(100, 200));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            graphics.drawLine(x1, y1, x2, y2);
        }

        // 添加噪点
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            graphics.setColor(getRandomColor(150, 250));
            graphics.fillRect(x, y, 1, 1);
        }

        // 释放资源
        graphics.dispose();

        // 将图片转换为Base64格式
        String base64Image = "";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "JPEG", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 构造返回结果
        Map<String, String> result = new HashMap<>();
        // 验证码值
        result.put("code", captchaText.toString());
        // Base64图片
        result.put("image", "data:image/jpeg;base64," + base64Image);

        return result;
    }

    /**
     * 生成随机颜色
     */
    private static Color getRandomColor(int min, int max) {
        Random random = new Random();
        int r = min + random.nextInt(max - min);
        int g = min + random.nextInt(max - min);
        int b = min + random.nextInt(max - min);
        return new Color(r, g, b);
    }

    public static void main(String[] args) throws IOException {
        // 测试生成验证码
        Map<String, String> result = generateCaptcha();
        System.out.println("生成的验证码：" + result.get("code"));
    }

}
