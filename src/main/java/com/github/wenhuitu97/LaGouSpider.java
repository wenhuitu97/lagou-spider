package com.github.wenhuitu97;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LaGouSpider {
    public static void main(String[] args) {
        // 设置webdriver路径
        System.setProperty("webdriver.chrome.driver", LaGouSpider.class.getClassLoader().
                getResource("chromedriver").getPath());

        // 创建webdriver
        WebDriver webDriver = new ChromeDriver();

        webDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");

    }
}
