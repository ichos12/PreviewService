package gxb.blueprint

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class StatusTests : MediaApplicationTests() {

    @Test
    fun `status should be hello world`() {
        val result = HttpHelper.get("http://localhost:8080/api/v1/anyurl/status", String::class.java)
        assertEquals(result, "hello world")
    }
}