import com.google.zxing.*
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
	val file = File("/Users/yangentao/a.png")
	qrImage(file, "http://www.haresoft.cn/appupdate/app?pkg=net.yet.hare")
	val s = qrScan(file)
	println("Hello? $s")

}

fun qrImage(file: File, content: String): Boolean {
	val width = 360      //图片的宽度
	val height = 360     //图片的高度
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

fun qrScan(file: File): String? {
	val bufImage = ImageIO.read(file)
	val bmp = BinaryBitmap(HybridBinarizer(BufferedImageLuminanceSource(bufImage)))
	val hints = HashMap<DecodeHintType, Any>()
	hints.put(DecodeHintType.CHARACTER_SET, "UTF-8")
	val r = MultiFormatReader().decode(bmp, hints)
	return r?.text
}