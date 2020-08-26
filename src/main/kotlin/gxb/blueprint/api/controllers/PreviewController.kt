package gxb.blueprint.api.controllers

import gxb.blueprint.api.services.PreviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/preview")
class PreviewController {
    @Autowired
    lateinit var PreviewService: PreviewService
    lateinit var cont: HttpEntity<*>

    @PostMapping("temp")
    fun postTemp(@RequestBody content: String): ResponseEntity<*> {
        cont = ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body("""
                <!DOCTYPE html>
                <html>
                    <head>
                        <meta charset="utf-8">
                        <title>Пример страницы</title>
                        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
                        <style>
                            html {
                              height: 100%;
                            }
                            body {
                              margin: 0;
                              height: 100%;
                            }
                    
                            /* Tell Quill not to scroll */
                            #quill-container {
                              height: auto;
                              min-height: 100%;
                              padding: 50px;
                            }
                            #quill-container .ql-editor {
                              font-size: 18px;
                              overflow-y: visible; 
                            }
                    
                            /* Specify our own scrolling container */
                            #scrolling-container {
                              height: 100%;
                              min-height: 100%;
                              overflow-y: auto;
                            }
                        </style>
                    </head>
                    <body>
                        <div id="scrolling-container">
                            <div id="quill-container">
                       
                            </div>
                        </div>
                    
                        <!-- Include the Quill library -->
                        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
                    
                        <!-- Initialize Quill editor -->
                        <script>
                            var quill = new Quill('#quill-container', {
                            modules: {
                              toolbar: false
                            },
                            readOnly: true,
                            scrollingContainer: '#scrolling-container', 
                            placeholder: 'Compose an epic...',
                            theme: 'bubble'
                            });
                    
                            quill.setContents($content)
                        </script>
                    </body>
                </html>
                """)
        val response = PreviewService.makeScreenshot()
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(response)
    }

    @GetMapping("temp")
    fun temp(): HttpEntity<*> {
        return cont
    }
}