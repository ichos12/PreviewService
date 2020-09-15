package gxb.blueprint.api.services

//import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.string

import com.lowagie.text.pdf.BaseFont
import org.htmlcleaner.CleanerProperties
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.PrettyXmlSerializer
import org.htmlcleaner.TagNode
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.xhtmlrenderer.extend.FontResolver
import org.xhtmlrenderer.pdf.ITextRenderer
import java.awt.Dimension
import java.awt.Toolkit
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@Service
class PreviewService {
    private final val driver: WebDriver
    private final val originalWindow: String

    init {
        val chromeDriverPath: String = "src/main/resources/chromedriver.exe"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
        System.setProperty("java.awt.headless", "false");
        val sSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        val width: Int = sSize.width
        val height: Int = sSize.height
        val options: ChromeOptions = ChromeOptions()
//        val downloadFilepath = "src/main/resources/pdfs"
//        val chromePrefs = HashMap<String, Any>()
//        chromePrefs["download.prompt_for_download"] = false
//        chromePrefs["download.default_directory"] = downloadFilepath
//        chromePrefs["download.directory_upgrade"] = true
//        chromePrefs["plugins.plugins_disabled"] = "Chrome PDF Viewer"
//        chromePrefs["plugins.always_open_pdf_externally"] = true
//        chromePrefs["pdfjs.disabled"] = true
        //add --headless argument
        options.addArguments("--disable-gpu", "--hide-scrollbars", "--window-size=$width,$height")
//        options.setExperimentalOption("prefs", chromePrefs)
        driver = ChromeDriver(options)
        originalWindow = driver.windowHandle
    }
    fun makePdf() {
        val html = driver.pageSource
        print("\nHTML\n$html")

        val out = ByteArrayOutputStream()
        val cleaner = HtmlCleaner()
        val props: CleanerProperties = cleaner.properties
        props.charset = "utf-8"
        props.isOmitComments = true
        props.pruneTags = "script"
        props.isRecognizeUnicodeChars = true
        val node: TagNode = cleaner.clean(html)

        val styleNodes = node.getElementListByName("style", true) as MutableList<TagNode>
        if (styleNodes.isEmpty()) {
            val style = TagNode("style")
            node.traverse { _, htmlNode ->
                if (htmlNode is TagNode) {
                    val tagName = htmlNode.name
                    if ("head" == tagName) {
                        htmlNode.addChild(style)
                    }
                }
                styleNodes.add(style)
            }
        }
        node.traverse { _, htmlNode ->
            if (htmlNode is TagNode) {
                val tagName = htmlNode.name
                if ("link" == tagName) {
                    val rel = htmlNode.getAttributeByName("rel")
                    if (rel == "stylesheet") {
                        htmlNode.addAttribute("media", "all")
                    }
                }
            }
            true
        }
        val styleContent = cleaner.getInnerHtml(styleNodes[0])
        cleaner.setInnerHtml(styleNodes[0], "@page { size: A4 landscape;}\n@media print\n* { font-family: Arial;}$styleContent");

        PrettyXmlSerializer(props).writeToStream(node, out)

        print("\nCLEAN HTML\n$out")
        val window = driver.windowHandle
        print(window)
        val session = (driver as ChromeDriver).sessionId
        val folder = File("src/main/resources/$session")
        if (!folder.exists()){
            folder.mkdir()
        }

        val os: OutputStream = FileOutputStream(File(folder, "$window.pdf"))
        val renderer = ITextRenderer(36f, 25)
        renderer.fontResolver.addFont("C:\\Windows\\Fonts\\ARIAL.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)
        renderer.setDocumentFromString(out.toString())
        renderer.layout()
        renderer.createPDF(os)
        renderer.finishPDF()
        out.flush()
        out.close()

        os.close()

    }
    fun makeScreenshot(urlValue: String?, requestsCount: Int): ByteArray {
        (driver as JavascriptExecutor).executeScript("window.open('$urlValue','_blank');")
        //(driver as JavascriptExecutor).executeScript("window.open('https://vk.com','_blank');")
        val newWindow = ArrayList(driver.windowHandles)
        newWindow.remove(originalWindow)
        println("windows(reqcount) -- $newWindow $requestsCount")
        driver.switchTo().window(newWindow[newWindow.size - 1])
//        val options = ChromeOptions()
//        options.addArguments("--enable-logging", "--disable-gpu", "--print-to-pdf", "$urlValue")
//        val newDriver = ChromeDriver(options)
//        newDriver.get(urlValue)

        makePdf()

        val screenshot = (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
        val screenshot1 = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
        screenshot1.copyTo(File("example3.png"), true)
        if (newWindow[newWindow.size - 1] != originalWindow) {
            driver.close()
        }

//        newDriver.quit()
        driver.switchTo().window(originalWindow)
        return screenshot

        //driver.switchTo().alert().accept()
        //driver.findElement(By.cssSelector("html")).sendKeys(Keys.CONTROL, "t")
        //driver.get("$urlValue")
        //driver.switchTo().window(tabs[1])
        //WebDriverWait(driver, 2).until(numberOfWindowsToBe(requestsCount))


//        for (windowHandle in driver.windowHandles) {
//            if (!originalWindow.contentEquals(windowHandle)) {
//                driver.switchTo().window(windowHandle)
//                break
//            }
//        }


        //driver.switchTo().window(tabs[0])
    }
    fun getEntity(content: String?): HttpEntity<String> {
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