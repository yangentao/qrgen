package net.yet

import net.yet.QRImage
import java.io.File

fun main(args: Array<String>) {
	val text = "http://www.haresoft.cn/appupdate/app?pkg=net.yet.hare"
	val file = File("/Users/entaoyang/b.png")
	val icon = File("/Users/entaoyang/app.png")
	QRImage().makeWithIcon(file, text, icon)
//	qrImageX(file, text, File("/Users/entaoyang/app.png"))
//	val s = qrScan(file)
	val s = QRImage.scan(file)
	println("Hello? $s")

}
