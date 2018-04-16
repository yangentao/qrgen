package net.yet

import com.google.zxing.*
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class QRImage {
	var format = "png"    //图片的格式
	var qrSize = 400      //二维码图片的大小
	var iconPercent = 0.20  // 图标的高宽是二维码高宽的1/5

	fun make(saveToFile: File, content: String): Boolean {
		try {
			val img = make(content)
			return ImageIO.write(img, format, saveToFile)
		} catch (ex: Exception) {
		}
		return false
	}

	fun make(content: String): BufferedImage {
		val hints = HashMap<EncodeHintType, Any>()
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8")    //指定字符编码为“utf-8”
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)  //指定二维码的纠错等级为中级
		hints.put(EncodeHintType.MARGIN, 2)    //设置图片的边距
		val bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize, hints)
		val qrImg = MatrixToImageWriter.toBufferedImage(bitMatrix)
		return qrImg
	}

	fun makeWithIcon(saveToFile: File, content: String, iconFile: File): Boolean {
		return makeWithIcon(saveToFile, content, ImageIO.read(iconFile))
	}

	fun makeWithIcon(saveToFile: File, content: String, iconImage: BufferedImage): Boolean {
		try {
			val qrImg = make(content)
			val dst = BufferedImage(qrSize, qrSize, BufferedImage.TYPE_INT_RGB)
			val g = dst.graphics
			g.drawImage(qrImg, 0, 0, qrSize, qrSize, null)

			val iW = (qrSize * iconPercent).toInt()
			val iH = (qrSize * iconPercent).toInt()

			val scaledIcon = iconImage.getScaledInstance(iW, iH, Image.SCALE_SMOOTH)
			g.drawImage(scaledIcon, (qrSize - iW) / 2, (qrSize - iH) / 2, iW, iH, null)

			return ImageIO.write(dst, format, saveToFile)
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return false
	}

	companion object {
		fun scan(file: File): String? {
			return scan(ImageIO.read(file))
		}

		fun scan(bufImage: BufferedImage): String? {
			val bmp = BinaryBitmap(HybridBinarizer(BufferedImageLuminanceSource(bufImage)))
			val hints = HashMap<DecodeHintType, Any>()
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8")
			hints.put(DecodeHintType.POSSIBLE_FORMATS, listOf(BarcodeFormat.QR_CODE))
			val r = MultiFormatReader().decode(bmp, hints)
			return r?.text
		}
	}
}