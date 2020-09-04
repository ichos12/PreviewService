package gxb.blueprint.api.services

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.awt.Dimension
import java.awt.Toolkit

@Service
class PreviewService {
    private final val driver: WebDriver

    init {
        val chromeDriverPath: String = "src/main/resources/chromedriver.exe"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
        System.setProperty("java.awt.headless", "false");
        val sSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        val width: Int = sSize.width
        val height: Int = sSize.height
        val options: ChromeOptions = ChromeOptions()
        options.addArguments("--headless", "--disable-gpu", "--window-size=$width,$height")
        driver = ChromeDriver(options)
    }
    fun makeScreenshot(urlValue: String?): ByteArray {
        driver.get("$urlValue")
        val screenshot = (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
        driver.close();
        return screenshot
    }
    fun getEntity(content: String?): HttpEntity<*> {
         return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body("""
                <!DOCTYPE html>
                <html>
                    <head>
                        <meta charset="utf-8">
                        <title>Пример страницы</title>
                        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
                        <style>
                            html {
                              height: 100%;
                            }
                            body {
                              margin: 0;
                              height: 100%;
                            }
                    
                            /* Tell Quill not to scroll */
                            #quill-container {
                              height: auto;
                              min-height: 100%;
                              padding: 50px;
                            }
                            #quill-container .ql-editor {
                              font-size: 18px;
                              overflow-y: visible; 
                            }
                    
                            /* Specify our own scrolling container */
                            #scrolling-container {
                              height: 100%;
                              min-height: 100%;
                              overflow-y: auto;
                            }
                        </style>
                    </head>
                    <body>
                        <div id="scrolling-container">
                            <div id="quill-container">
                       
                            </div>
                        </div>
                    
                        <!-- Include the Quill library -->
                        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
                    
                        <!-- Initialize Quill editor -->
                        <script>
                            var quill = new Quill('#quill-container', {
                            modules: {
                              toolbar: false
                            },
                            readOnly: true,
                            scrollingContainer: '#scrolling-container', 
                            placeholder: 'Compose an epic...',
                            theme: 'bubble'
                            });
                    
                            quill.setContents($content)
                        </script>
                    </body>
                </html>
                """)
    }
}