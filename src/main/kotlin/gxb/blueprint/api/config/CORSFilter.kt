package gxb.blueprint.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class CORSFilter : Filter {
    @Throws(ServletException::class)
    override fun init(fc: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) {
        val response = servletResponse as HttpServletResponse
        val request = servletRequest as HttpServletRequest
        response.setHeader("Access-Control-Allow-Origin", ACCESS_CONTROL_ALLOW_ORIGIN)
        response.setHeader("Access-Control-Allow-Methods", ACCESS_CONTROL_ALLOW_METHODS)
        response.setHeader("Access-Control-Allow-Headers", ACCESS_CONTROL_ALLOW_HEADERS)
        response.setHeader("Access-Control-Max-Age", ACCESS_CONTROL_MAX_AGE)
        val REQUEST_METHOD = request.method
        if (REQUEST_METHOD.equals("OPTIONS", ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            chain.doFilter(servletRequest, servletResponse)
        }
    }

    override fun destroy() {}

    companion object {
        const val ACCESS_CONTROL_ALLOW_ORIGIN = "*"
        const val ACCESS_CONTROL_ALLOW_METHODS = "POST, GET, OPTIONS, DELETE"
        const val ACCESS_CONTROL_ALLOW_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN"
        const val ACCESS_CONTROL_MAX_AGE = "3600"
    }
}
