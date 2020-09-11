package gxb.blueprint.api.controllers

import gxb.blueprint.api.services.PreviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("api/v1/preview/")
class PreviewController {
    @Autowired
    lateinit var PreviewService: PreviewService
    var deque: Deque<HttpEntity<String>> = LinkedList()

    @PostMapping("/")
    fun process(@RequestBody content: String): ResponseEntity<ByteArray> {
        deque.addLast(PreviewService.getEntity(content))
        val requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + deque.size
        println("ON POST $requestUri")
        val response = PreviewService.makeScreenshot(requestUri, deque.size)
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(response)
    }

    @GetMapping("/{id}")
    fun temp(): HttpEntity<String> {
        val requestUri1 = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
        println("ON GET $requestUri1")

        return deque.removeFirst()
    }

    @GetMapping("/{id}/er")
    fun tep(): HttpEntity<String> {
        //val requestUri1 = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
        //println("ON GET $requestUri1")

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="utf-8">
                        <title>Пример страницы</title>
                        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
                    </head>
                    <body>
                        <div id="scrolling-container">
                            <div id="quill-container">
                       
                            </div>
                        </div>                        
                    </body>
                </html>
                """)
    }

}