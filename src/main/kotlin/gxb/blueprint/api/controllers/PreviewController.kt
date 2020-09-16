package gxb.blueprint.api.controllers

import gxb.blueprint.api.services.PreviewService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.File
import java.util.*
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}

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
        logger.debug { "ON POST $requestUri" }
        val response = PreviewService.makeScreenshot(requestUri, deque.size)
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(response)
    }

    @GetMapping("/{id}")
    fun temp(): HttpEntity<String> {
        val requestUri1 = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
        logger.debug { "ON GET $requestUri1" }

        return deque.removeFirst()
    }
}