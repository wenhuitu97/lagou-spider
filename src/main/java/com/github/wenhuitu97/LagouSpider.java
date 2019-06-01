package com.github.wenhuitu97;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import sun.awt.OSInfo;

import java.util.List;

public class LagouSpider {
    public static void main(String[] args) {
        // 设置webdriver路径
        initialProperties();

        // 创建webdriver
        WebDriver webDriver = new ChromeDriver();

        // 跳转页面
        webDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");

        // 通过 xpath 选中元素
        clickOption(webDriver, "工作经验", "应届毕业生");
        clickOption(webDriver, "学历要求", "本科");
        clickOption(webDriver, "融资阶段", "不限");
        clickOption(webDriver, "公司规模", "不限");
        clickOption(webDriver, "行业领域", "移动互联网");
        extractJobsByPagination(webDriver);


    }

    private static void initialProperties() {
        switch (OSInfo.getOSType()) {
            case LINUX:
                System.setProperty("webdriver.chrome.driver", LagouSpider.class.getClassLoader().getResource("chromedriver_linux64").getPath());
                break;
            case MACOSX:
                System.setProperty("webdriver.chrome.driver", LagouSpider.class.getClassLoader().getResource("chromedriver").getPath());
                break;
            case WINDOWS:
                System.setProperty("webdriver.chrome.driver", LagouSpider.class.getClassLoader().getResource("chromedriver_win32.exe").getPath());
                break;
            default:
                throw new RuntimeException("不支持当前操作系统类型");
        }
    }

    private static void extractJobsByPagination(WebDriver webDriver) {
        // 解析页面元素
        List<WebElement> jobElements = webDriver.findElements(By.className("con_list_item"));
        for(WebElement jobElement : jobElements) {
            String companyName = jobElement.findElement(By.className("company_name")).getText();
            WebElement moneyElement = jobElement.findElement(By.className("position")).findElement(By.className("money"));
            System.out.println(companyName + " : " + moneyElement.getText());
        }

        WebElement nextPageBtn = webDriver.findElement(By.className("pager_next"));
        if(!nextPageBtn.getAttribute("class").contains("pager_next_disabled")) {
            nextPageBtn.click();
            System.out.println("解析下一页");
            try{
                Thread.sleep(1000L);
            }catch (InterruptedException e) {
            }
            extractJobsByPagination(webDriver);
        }

    }

    private static void clickOption(WebDriver webDriver, String chosenTitle, String optionTitle) {
        WebElement chosenElement = webDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(), '" + chosenTitle + "')]"));
        WebElement optionElement = chosenElement.findElement(By.xpath("../a[contains(text(), '" + optionTitle + "')]"));
        optionElement.click();
    }
}
