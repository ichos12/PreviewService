package gxb.media.api.services

import com.lowagie.text.pdf.BaseFont
import mu.KotlinLogging
import org.htmlcleaner.CleanerProperties
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.PrettyXmlSerializer
import org.htmlcleaner.TagNode
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.*

@Service
class PreviewService {
    private val logger = KotlinLogging.logger {}
    fun makePdf(driver: WebDriver): String {
        val html = driver.pageSource
        logger.debug { "\nHTML\n$html" }

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
        cleaner.setInnerHtml(styleNodes[0], "@page { size: A4 landscape;}\n@media print\n* { font-family: Arial;}$styleContent")

        PrettyXmlSerializer(props).writeToStream(node, out)

        logger.debug { "\nCLEAN HTML\n$out" }
        val window = driver.windowHandle
        logger.debug { window }
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
        return out.toString()
    }

    fun makeScreenshot(driver: WebDriver, urlValue: String?, requestsCount: Int): ByteArray {
        driver.get(urlValue)

        //makePdf(driver)

        val screenshot = (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)

        driver.quit()
        return screenshot
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