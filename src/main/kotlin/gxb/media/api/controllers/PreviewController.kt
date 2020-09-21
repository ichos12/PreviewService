package gxb.media.api.controllers

import gxb.media.api.services.PreviewService
import mu.KotlinLogging
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.awt.Dimension
import java.awt.Toolkit
import java.util.*

@RestController
@RequestMapping("api/v1/preview/")
class PreviewController {
    @Autowired
    lateinit var PreviewService: PreviewService
    lateinit var driver: WebDriver
    var deque: Deque<HttpEntity<String>> = LinkedList()
    private val logger = KotlinLogging.logger {}

    @PostMapping("/")
    fun process(@RequestBody content: String): ResponseEntity<ByteArray> {
        val chromeDriverPath = "src/main/resources/chromedriver.exe"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
        System.setProperty("java.awt.headless", "false")
        val sSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        val width: Int = sSize.width
        val height: Int = sSize.height
        val options = ChromeOptions()
        options.addArguments("--disable-gpu", "--hide-scrollbars", "--window-size=$width,$height")
        driver = ChromeDriver(options)

        deque.addLast(PreviewService.getEntity(content))
        val requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + deque.size
        logger.debug { "ON POST $requestUri" }
        val response = PreviewService.makeScreenshot(driver, requestUri, deque.size)

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(response)
    }

    @GetMapping("/{id}")
    fun temp(): HttpEntity<String> {
        val requestUri1 = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
        logger.debug { "ON GET $requestUri1" }

        return deque.removeFirst()
    }
}