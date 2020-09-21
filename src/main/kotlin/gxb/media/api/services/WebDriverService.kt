package gxb.media.api.services

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class WebDriverService {

    init{
        //create web driver etc.
    }
    @RabbitListener(queues = ["query-example-2"])
    @Throws(InterruptedException::class)
    fun worker(message: String) {
        //previewservice.makescreen
        //logger.info("worker 1 : $message")
        //Thread.sleep(100 * random.nextInt(20))
    }
}