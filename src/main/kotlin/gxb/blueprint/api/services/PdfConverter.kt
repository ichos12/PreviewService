//package gxb.blueprint.api.services
//
//import org.apache.commons.exec.util.StringUtils
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import java.io.IOException
//import java.nio.file.Files
//import java.nio.file.Paths
//import java.util.*
//import javax.servlet.ServletContext
//import javax.servlet.http.HttpServletResponse
//
//
//class PdfConverter(
//        m: Map<String?, String?>?,
//        s: ServletContext,
//        fileName: String?,
//        template: String
//) {
//    /**
//     * getGeneratedPDF
//     *
//     * @return the generatedPDF
//     */
//    /**
//     * setGeneratedPDF
//     *
//     * @param generatedPDF the generatedPDF to set
//     */
//    /**
//     * Generated PDF file.
//     */
//    var generatedPDF: File? = null
//    /**
//     * getPdfName
//     *
//     * @return the pdfName
//     */
//    /**
//     * setPdfName
//     *
//     * @param pdfName the pdfName to set
//     */
//    /**
//     * PDF file name.
//     */
//    var pdfName: String? = null
//
//    /**
//     * writePdfToResponse
//     * Write the PDF file into the response and delete it from temp directory
//     * afterwards.
//     * @param response
//     * @throws IOException
//     */
////    fun writePdfToResponse(
////            response: HttpServletResponse
////    ) {
////        try {
////            FileInputStream(generatedPDF).use { fis ->
////                response.contentType = MediaType.APPLICATION_PDF_VALUE
////                response.setHeader(
////                        "Content-Disposition",
////                        "inline; filename=$pdfName"
////                )
////                response.addHeader(
////                        "Content-Length",
////                        generatedPDF?.length().toString()
////                )
////                val servletOutputStream = response.outputStream
////                var read = 0
////                val bytes = ByteArray(1024)
////                while (fis.read(bytes).also { read = it } != -1) {
////                    servletOutputStream.write(bytes, 0, read)
////                }
////                response.flushBuffer()
////            }
////        } catch (ioe: IOException) {
////            response.contentType = MediaType.TEXT_PLAIN_VALUE
////            response.writer.print("Cannot render PDF file.")
////            response.flushBuffer()
////        } finally {
////            // Delete generated PDF after writing it to the response
////            generatedPDF?.delete()
////        }
////    }
//
//    companion object {
//        /**
//         * Temp directory.
//         */
//        private val TMP_DIR = System.getProperty("java.io.tmpdir") + "/"
//
//        /**
//         * Directory to HTML templates (dedicated to PDF generation).
//         */
//        private const val PDF_DIR = "pdf/"
//
//        /**
//         * Directory to the image folders (dedicated to PDF generation).
//         */
//        private const val PDF_IMG_DIR = "pdf/img/"
//
//        /**
//         * Prefixes for templates expressions.
//         */
//        private const val PREFIX_TEMPLATE = "\${ "
//
//        /**
//         * Suffixes for template expressions.
//         */
//        private const val SUFFIX_TEMPLATE = " }"
//    }
//
//    /**
//     * PdfConverter
//     * @param m map key, value to replace, to replace expressions in HTML
//     * template.
//     * @param s ServletContext to get resources from context path.
//     * @param fileName desired name of the generated PDF.
//     * @param template name of the HTML template to make the PDF.
//     * @throws IOExceptio
//     */
//    init {
//        // Set PDF filename
//        pdfName = fileName
//
//        // Fetch HTML template
////        val html: String = Scanner(
////                s.getResourceAsStream(PDF_DIR + template),
////                StandardCharsets.UTF_8.toString()
////        ).useDelimiter("\\A").next()
//
//        /*
//         * Replace template expressions "${ }" in HTML
//         */
////        val sub = StringSubstitutor(
////                m,
////                PREFIX_TEMPLATE,
////                SUFFIX_TEMPLATE
////        )
////        var resolvedString: String = sub.replace(html)
//
//        /*
//         * Replace images like <img src="image.png" /> by
//         * <img src=\"data:image/png;base64," + base64Image
//         */
////        val imgs: Array<String> = StringUtils.substringsBetween(
////                template,
////                "<img src=\"", "\""
////        )
////        for (s1 in imgs) {
////            val mime: String = Files.probeContentType(Paths.get(PDF_IMG_DIR + s1))
////            resolvedString = resolvedString.replace(
////                    s1,
////                    "data:$mime;base64,"
////                            + Base64.getEncoder().encodeToString(
////                            IOUtils.toByteArray(
////                                    s.getResourceAsStream(PDF_IMG_DIR + s1)
////                            )
////                    )
////            )
//        }
//
//        // Make the PDF file
//        val fos = FileOutputStream(TMP_DIR + pdfName)
//        val it = ITextRenderer()
//        it.setDocumentFromString(resolvedString)
//        it.layout()
//        it.createPDF(fos)
//        fos.close()
//
//        // Set the PDF generated file to this PdfConverter instance
//        generatedPDF = File(TMP_DIR + pdfName)
//    }
//}