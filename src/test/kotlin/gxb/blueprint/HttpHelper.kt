package gxb.blueprint

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.util.AssertionErrors.assertEquals
import org.springframework.test.util.AssertionErrors.assertNotNull

object HttpHelper {
    val restTemplate: TestRestTemplate = TestRestTemplate()

    val mapper = ObjectMapper()
            .registerModule(KotlinModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


    fun getResponseData(responseEntity: ResponseEntity<String>): JsonNode {
        val responseBody = responseEntity.body
        val bodyAsJsonNode = mapper.readTree(responseBody)
        return bodyAsJsonNode["data"]
    }

    fun <T> get(url: String, responseTypeClazz: Class<T>): T? {
        val httpEntity = HttpEntity<String>(HttpHeaders())
        val result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String::class.java)
        checkResponse(result)
        val resultObject = mapper.convertValue(getResponseData(result), responseTypeClazz)
        return resultObject as T
    }

    fun <T, U> post(url: String, responseTypeClazz: Class<T>, body: U): T? {
        val httpEntity = HttpEntity(body, HttpHeaders())
        val result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String::class.java)
        checkResponse(result)
        val resultObject = mapper.convertValue(getResponseData(result), responseTypeClazz)
        return resultObject as T
    }

    fun <T, U> put(url: String, responseTypeClazz: Class<T>, body: U): T? {
        val httpEntity = HttpEntity(body, HttpHeaders())
        val result = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String::class.java)
        checkResponse(result)
        val resultObject = mapper.convertValue(getResponseData(result), responseTypeClazz)
        return resultObject as T
    }

    fun checkResponse(result: ResponseEntity<String>) {
        assertEquals("Response status code: ${result.statusCode} [${result.statusCodeValue}]", HttpStatus.OK, result.statusCode)
        assertNotNull("Response body can't be null", result.body)
    }
}
