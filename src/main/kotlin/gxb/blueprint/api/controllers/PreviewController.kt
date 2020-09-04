package gxb.blueprint.api.controllers

import gxb.blueprint.api.services.PreviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@RestController
@RequestMapping("api/v1/preview/")
class PreviewController {
    @Autowired
    lateinit var PreviewService: PreviewService
    lateinit var cont: HttpEntity<*>

    @PostMapping("/")
    fun process(@RequestBody content: String, model: Model, @ModelAttribute data: HttpEntity<*>): ResponseEntity<*> {
        val requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
        println(requestUri)
        cont = PreviewService.getEntity(content)
        //model["data"] = pageContent
        //println("post $pageContent")
        //println("model $model")
        val response = PreviewService.makeScreenshot(requestUri)
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(response)
    }

    @GetMapping("/")
    fun temp(@ModelAttribute data: HttpEntity<*>, model: Model): HttpEntity<*> {
        val requestUri1 = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
        println(requestUri1)
        //println("content1 $model")

        //model["data"] = data
        //println("content2 $model")

        return cont
    }

}