import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.File
import java.util.*

fun main(args: Array<String>) {
	val file = File("/Users/entaoyang/a.png")
	makeQRImage(file, "Hello")

}

fun makeQRImage(file: File, content: String): Boolean {
	val width = 300      //图片的宽度
	val height = 300     //图片的高度
	val format = "png"    //图片的格式
	/**
	 * 定义二维码的参数
	 */
	val hints = HashMap<EncodeHintType, Any>()
	hints.put(EncodeHintType.CHARACTER_SET, "utf-8")    //指定字符编码为“utf-8”
	hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M)  //指定二维码的纠错等级为中级
	hints.put(EncodeHintType.MARGIN, 2)    //设置图片的边距

	/**
	 * 生成二维码
	 */
	try {
		val bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints)
		MatrixToImageWriter.writeToPath(bitMatrix, format, file.toPath())
		return file.isFile && file.exists()
	} catch (e: Exception) {
		e.printStackTrace()
	}
	return false
}