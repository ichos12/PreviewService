package gxb.blueprint.api.services

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Service
import java.awt.Dimension
import java.awt.Toolkit


@Service
class PreviewService {
    fun makeScreenshot(): ByteArray {
        val chromeDriverPath: String = "src/main/resources/chromedriver.exe"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
        System.setProperty("java.awt.headless", "false");
        val sSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        val width: Int = sSize.width
        val height: Int = sSize.height
        val options: ChromeOptions = ChromeOptions()
        options.addArguments("--headless", "--disable-gpu", "--window-size=$width,$height")
        val driver: WebDriver = ChromeDriver(options)
        driver.get("http://localhost:8080/api/v1/preview/temp")
        val screenshot = (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
        driver.quit()
        return screenshot
    }
}