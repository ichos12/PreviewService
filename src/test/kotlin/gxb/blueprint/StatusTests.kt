package gxb.blueprint

import gxb.blueprint.api.controllers.PreviewController
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class StatusTests : MediaApplicationTests() {

//    @Test
//    fun `status should be hello world`() {
//        val result = HttpHelper.get("http://localhost:8080/api/v1/anyurl/status", String::class.java)
//        assertEquals(result, "hello world")
//    }

    @Test
    fun `post request should response an image` () {

    }
}

@ExtendWith(SpringExtension.class)
@WebMvcTest(PreviewController.class)
class PreviewControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Test
//    fun testReturn200() {
//        mockMvc.get("/person/42").andExpect(status().isOk).andExpect(content()
//                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//    }
}