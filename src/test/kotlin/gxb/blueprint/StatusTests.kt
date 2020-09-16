package gxb.blueprint

import gxb.blueprint.HttpHelper.restTemplate
import org.junit.Ignore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import java.awt.image.BufferedImage
import java.io.*
import java.util.*
import javax.imageio.ImageIO


class StatusTests : MediaApplicationTests() {

//    @Test
//    fun `status should be hello world`() {
//        val result = HttpHelper.get("http://localhost:8080/api/v1/anyurl/status", String::class.java)
//        assertEquals(result, "hello world")
//    }

    @Test
    fun `post request should response an image` () {
        val bodyString = """
            [
              {
                "insert": {
                  "image": "https://picsum.photos/id/1003/536/354"
                }
              },
              {
                "insert": "\n",
                "attributes": {
                  "align": "center"
                }
              },
              {
                "insert": "2nd REQUEST Lorem ipsum",
                "attributes": {
                  "bold": true
                }
              },
              {
                "insert": " dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Purus in massa tempor nec. Viverra ipsum nunc aliquet bibendum. Convallis convallis tellus id interdum velit. Eget sit amet tellus cras adipiscing enim eu. Quisque id diam vel quam. Aliquet bibendum enim facilisis gravida neque convallis. Accumsan tortor posuere ac ut consequat semper viverra nam libero. Non odio euismod lacinia at quis risus sed vulputate. Nunc sed augue lacus viverra vitae congue eu consequat ac. Pellentesque eu tincidunt tortor aliquam nulla facilisi cras. Sit amet nisl purus in mollis nunc sed id semper. Vel pharetra vel turpis nunc eget lorem dolor sed viverra. Enim blandit volutpat maecenas volutpat blandit aliquam. Urna nec tincidunt praesent semper feugiat nibh sed pulvinar. Tincidunt praesent semper feugiat nibh sed pulvinar. Enim sed faucibus turpis in eu.\n"
              },
              {
                "insert": {
                  "image": "https://picsum.photos/id/237/536/354"
                  }
              },
              {
                "insert": "\n",
                "attributes": {
                  "align": "center"
                }
              },
              {
                "insert": "Quis varius quam quisque id diam vel. Tortor posuere ac ut consequat. Arcu non odio euismod lacinia. Enim diam vulputate ut pharetra sit amet. Sit amet massa vitae tortor condimentum lacinia quis vel eros. Fringilla est ullamcorper eget nulla facilisi etiam dignissim. Nunc mi ipsum faucibus vitae aliquet nec ullamcorper sit amet. Nulla facilisi cras fermentum odio. Amet dictum sit amet justo donec enim diam. In hac habitasse platea dictumst quisque sagittis. Sem viverra aliquet eget sit. In mollis nunc sed id semper. Tempor nec feugiat nisl pretium fusce id velit ut. Vulputate mi sit amet mauris commodo quis. Viverra nam libero justo laoreet sit amet cursus sit. Neque convallis a cras semper auctor neque vitae tempus quam. Ultrices neque ornare aenean euismod elementum. Tortor consequat id porta nibh venenatis cras. Feugiat vivamus at augue eget arcu. Urna cursus eget nunc scelerisque.\n"
              },
              {
                "insert": {
                  "image": "https://picsum.photos/seed/picsum/536/354"
                }
              },
              {
                "insert": " "
              },
              {
                "insert": "\n",
                "attributes": {
                  "align": "center"
                }
              },
              {
                "insert": "Tortor condimentum lacinia quis vel. Penatibus et magnis dis parturient montes nascetur ridiculus mus. Eget felis eget nunc lobortis mattis aliquam. Placerat orci nulla pellentesque dignissim enim sit amet. Tincidunt vitae semper quis lectus nulla at volutpat diam. Ullamcorper a lacus vestibulum sed arcu non. Eu sem integer vitae justo eget. Amet dictum sit amet justo donec enim diam vulputate. Porttitor rhoncus dolor purus non enim praesent. Placerat in egestas erat imperdiet sed euismod nisi porta lorem. Varius morbi enim nunc faucibus a pellentesque sit amet. Hendrerit dolor magna eget est lorem. Sit amet tellus cras adipiscing enim. Gravida dictum fusce ut placerat orci nulla pellentesque dignissim enim. Vitae auctor eu augue ut lectus arcu.\n"
              },
              {
                "insert": {
                  "image": "https://picsum.photos/id/1084/536/354?grayscale"
                }
              },
              {
                "insert": " "
              },
              {
                "insert": "\n",
                "attributes": {
                  "align": "center"
                }
              },
              {
                "insert": "Nulla pharetra diam sit amet nisl suscipit adipiscing bibendum est. Nunc id cursus metus aliquam eleifend mi in. Faucibus vitae aliquet nec ullamcorper. Pretium lectus quam id leo in vitae turpis massa sed. Quis imperdiet massa tincidunt nunc. Consequat mauris nunc congue nisi vitae suscipit tellus mauris a. Odio pellentesque diam volutpat commodo sed egestas egestas fringilla. Sem nulla pharetra diam sit. Et magnis dis parturient montes nascetur ridiculus. Viverra adipiscing at in tellus integer feugiat scelerisque. Vitae elementum curabitur vitae nunc sed. Felis imperdiet proin fermentum leo vel orci porta non. Adipiscing tristique risus nec feugiat in fermentum posuere urna nec. Malesuada fames ac turpis egestas maecenas pharetra. Mauris sit amet massa vitae.\n"
              },
              {
                "insert": {
                  "image": "https://picsum.photos/id/870/536/354?grayscale&blur=2"
                }
              },
              {
                "insert": " "
              },
              {
                "insert": "\n",
                "attributes": {
                  "align": "center"
                }
              },
              {
                "insert": "Commodo elit at imperdiet dui accumsan. Mi in nulla posuere sollicitudin aliquam ultrices. Vitae semper quis lectus nulla at volutpat. Sagittis orci a scelerisque purus semper eget duis. Libero volutpat sed cras ornare arcu dui. Ut diam quam nulla porttitor massa id. Ullamcorper morbi tincidunt ornare massa eget egestas purus viverra accumsan. Magna eget est lorem ipsum dolor. Egestas maecenas pharetra convallis posuere morbi. Viverra aliquet eget sit amet tellus cras adipiscing enim eu.\n"
              }
            ]
        """
        val response: ResponseEntity<ByteArray> = restTemplate.exchange("http://localhost:8080/api/v1/preview/", HttpMethod.POST, HttpEntity(bodyString), ByteArray::class.java)
        val result = response.body
        val baos = ByteArrayOutputStream()
        val requestBaos = ByteArrayOutputStream()
        val image: BufferedImage = ImageIO.read(File("example.png"))
        ImageIO.write(image, "png", baos)
        baos.flush()
        val bais = ByteArrayInputStream(result)
        val requestImage = ImageIO.read(bais)

        ImageIO.write(requestImage, "png", requestBaos)
        requestBaos.flush()
        val originalBase64String: String = Base64.getEncoder().encodeToString(baos.toByteArray())
        val requestedBase64String: String = Base64.getEncoder().encodeToString(requestBaos.toByteArray())
        baos.close()
        requestBaos.close()
        var writer = FileWriter("orig.txt", false)
        writer.write(originalBase64String)
        writer = FileWriter("req.txt", false)
        writer.write(requestedBase64String)
        assertEquals(originalBase64String, requestedBase64String)
    }
}