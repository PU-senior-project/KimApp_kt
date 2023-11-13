package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfName
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
//import com.tom_roush.pdfbox.pdmodel.PDDocument
//import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
//import com.tom_roush.pdfbox.pdmodel.font.PDType1Font
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ResultPage : AppCompatActivity() {

    private var currentColor: Int = Color.GREEN
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        val centerText = findViewById<TextView>(R.id.txt_Level)
        val imageButton_retry = findViewById<ImageButton>(R.id.imageButton_retry)
        val imageButton_download = findViewById<ImageButton>(R.id.imageButton_download)

//        val selectedValue_WorkTime_string = DataProvider.getData("selectedValue_WorkTime") as? String
//        val selectedValue_carry_string = DataProvider.getData("selectedValue_carry") as? String
//        val selectedValue_state_string = DataProvider.getData("selectedValue_state") as? String
//        val selectedValue_camera_string = DataProvider.getData("selectedValue_camera") as? String

        val selectedValue_WorkTime = DataProvider.getData("selectedValue_WorkTime") as? Int
        val selectedValue_carry = DataProvider.getData("selectedValue_carry") as? Int
        val selectedValue_state = DataProvider.getData("selectedValue_state") as? Int
        val selectedValue_camera = DataProvider.getData("selectedValue_camera")as? Int


        if (selectedValue_WorkTime != null && selectedValue_carry != null && selectedValue_state != null && selectedValue_camera != null) {
            val Level =
                selectedValue_WorkTime * (selectedValue_carry + selectedValue_state + selectedValue_camera)
            changeColor(Level)
        }

        imageButton_retry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

//        imageButton_download.setOnClickListener {
//            // PDF文件的路径
//            val originalPdfName = "kimapp_newreport.pdf"
//            // 从资源文件夹复制PDF到内部存储
//            val originalPdfFile = File(filesDir, originalPdfName)
//            if (!originalPdfFile.exists()) {
//                resources.openRawResource(R.raw.kimapp_newreport).use { inputStream ->
//                    FileOutputStream(originalPdfFile).use { outputStream ->
//                        inputStream.copyTo(outputStream)
//                    }
//                }
//            }
//
//            // 读取并修改PDF
//            val newPdfName = "kimapp_newreport_1.pdf"
//            val newPdfFile = File(filesDir, newPdfName)
//
//            try {
//                val document = PDDocument.load(originalPdfFile)
//                val page = document.getPage(0)
//                val contentStream = PDPageContentStream(document, page, true, true)
//
//                // 在PDF中添加文本...
//                val workTimeString = selectedValue_WorkTime?.toString() ?: "缺少工作时间"
//                val carryString = selectedValue_carry?.toString() ?: "缺少搬运次数"
//                val stateString = selectedValue_state?.toString() ?: "缺少状态信息"
//                val cameraString = selectedValue_camera?.toString() ?: "缺少摄影信息"
//
//                // 添加内容到PDF
//                contentStream.beginText()
//                contentStream.setFont(PDType1Font.HELVETICA, 12f)
//                contentStream.newLineAtOffset(50f, 650f)
//                contentStream.showText(workTimeString)
//                contentStream.newLineAtOffset(0f, -15f)
//                contentStream.showText(carryString)
//                contentStream.newLineAtOffset(0f, -15f)
//                contentStream.showText(stateString)
//                contentStream.newLineAtOffset(0f, -15f)
//                contentStream.showText(cameraString)
//                contentStream.endText()
//                contentStream.close()
//
//                document.save(newPdfFile)
//                document.close()
//
//                // 打开修改后的PDF文件
//                val uri = FileProvider.getUriForFile(this, "com.example.kimapp_mainkl.fileprovider", newPdfFile)
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.setDataAndType(uri, "application/pdf")
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                startActivity(intent)
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//                // 处理错误
//                Toast.makeText(this, "Error processing the PDF: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
//            }
//        }

}

    fun changeColor(Level: Int) {
        val centerText = findViewById<TextView>(R.id.txt_Level)
        val text_remind = findViewById<TextView>(R.id.text_remind)
        val text_nextremind = findViewById<TextView>(R.id.text_nextremind)

        when {
            Level < 10 -> {
                setRingColor(Color.GREEN)
                setTextColor(Color.GREEN, centerText)
                centerText.text = "1\n低風險"
                text_remind.text =
                    "目前風險等級為第1級為低負荷狀態\n非常良好的姿態！請繼續維持下去。"
                text_nextremind.text =
                    "此種風險等級不易產生生理做載的情形發生\n為最理想標準的姿態。"
            }

            Level in 10..24 -> {
                setRingColor(Color.YELLOW)
                setTextColor(Color.YELLOW, centerText)
                centerText.text = "2\n中風險"
                text_remind.text =
                    "目前風險等級為第2級為中等負載狀態\n姿態稍微不正確!需慢慢調整自我姿態。"
                text_nextremind.text =
                    "此種風險等級需進一步調查及必要時進行改善\n針對此類族群應進行工作再設計。"
            }

            Level in 25..49 -> {
                setRingColor(Color.rgb(255, 179, 0))
                setTextColor(Color.rgb(255, 179, 0), centerText)
                centerText.text = "4\n高風險"
                text_remind.text = "目前風險等級為第4級為中高負載狀態\n姿態不正確!需要定期檢查。"
                text_nextremind.text =
                    "此種風險等級需近日內進行進一步調查及改善\n針對此類族群建議進行工作改善。"
            }

            Level >= 50 -> {
                setRingColor(Color.RED)
                setTextColor(Color.RED, centerText)
                centerText.text = "8\n極高風險"
                text_remind.text =
                    "目前風險等級為第8級為高負載狀態\n姿態嚴重不正確!需立刻進行改善。"
                text_nextremind.text =
                    "此種風險等級必須立即進行調查及改善\n針對此類族群必須進行工作改善。"
            }
        }
    }




        fun setRingColor(color: Int) {
            val ringDrawable =
                ContextCompat.getDrawable(this, R.drawable.green_circle) as GradientDrawable
            ringDrawable.setColor(color)
            val ringView = findViewById<View>(R.id.ringView)
            ringView.background = ringDrawable
            currentColor = color
        }

        //接下來可以用這兩個參數來設定
//    setRingColor(Color.RED)
//    setTextColor(Color.BLUE)

        fun setTextColor(color: Int, centerText: TextView) {
            centerText.setTextColor(color)
        }


}