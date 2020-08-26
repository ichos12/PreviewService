package gxb.blueprint

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication(scanBasePackages=["gxb.blueprint.api"])
class MediaApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
    runApplication<MediaApplication>(*args)
}
