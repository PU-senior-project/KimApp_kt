package com.example.kimapp_mainkl

import android.content.Context
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
//import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfName
import com.itextpdf.kernel.pdf.PdfName.Document
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.Document
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ResultPage : AppCompatActivity() {

    private var currentColor: Int = Color.GREEN
//    private var Level: Int = 0  // 將 Level 定義為成員變數
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        val imageButton_retry = findViewById<ImageButton>(R.id.imageButton_retry)
        val imageButton_download = findViewById<ImageButton>(R.id.imageButton_download)

        val selectedValue_WorkTime = DataProvider.getData("selectedValue_WorkTime") as? Int
        val selectedValue_carry = DataProvider.getData("selectedValue_carry") as? Int
        val selectedValue_state = DataProvider.getData("selectedValue_state") as? Int
        val selectedValue_camera = DataProvider.getData("selectedValue_camera")as? Int

        finalLevel(selectedValue_WorkTime?: 0,selectedValue_carry ?: 0, selectedValue_state ?: 0, selectedValue_camera ?: 0)




        imageButton_retry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

        imageButton_download.setOnClickListener {
            // 假設你的 PDF 文件的 resource ID 是 R.raw.kimapp_newreport
            val originalPath = copyPdfFromRawToInternalStorage(this, R.raw.kimapp_newreport, "kimapp_report")
            val modifiedPath = filesDir.path + "/modified_report.pdf"

            modifyPdf(originalPath, modifiedPath, selectedValue_WorkTime ?: 0, selectedValue_carry ?: 0, selectedValue_state ?: 0, selectedValue_camera ?: 0)

            val file = File(modifiedPath)
            val uri = FileProvider.getUriForFile(this, "com.example.kimapp_mainkl.fileprovider", file)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        }

}

    fun finalLevel(workTime: Int, carry: Int, state: Int, camera: Int){
        if (workTime != null && carry != null && state != null && camera != null) {
            val Level :Int  = workTime * (carry + state + camera)
            DataProvider.saveData("finalData",Level)
            changeColor(Level)
        }
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

        fun copyPdfFromRawToInternalStorage(context: Context, resourceId: Int, outputFileName: String): String {
            val inputStream = context.resources.openRawResource(resourceId)
            val outputPath = context.filesDir.path + "/" + outputFileName
            val outputStream = FileOutputStream(outputPath)

            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            return outputPath
        }

        fun modifyPdf(originalFilePath: String, outputFilePath: String, workTime: Int, carry: Int, state: Int, camera: Int,) {

            val reader = PdfReader(originalFilePath)
            val writer = PdfWriter(outputFilePath)
            val pdf = PdfDocument(reader, writer)
            val document = Document(pdf)

            document.add(Paragraph("時間評級 $workTime").setFixedPosition(1, 328f, 505f, 100f))
            document.add(Paragraph("姿態評級 $camera").setFixedPosition(1, 328f, 560f, 100f))
            document.add(Paragraph("工作狀態評級 $state").setFixedPosition(1, 328f, 590f, 100f))
            if(DataProvider.getData("Gender_Of_value")==0){
                document.add(Paragraph("荷重評級女 $carry").setFixedPosition(1, 395f, 620f, 100f))
            }else if (DataProvider.getData("Gender_Of_value")==1){
                document.add(Paragraph("荷重評級男 $carry").setFixedPosition(1, 258f, 620f, 100f))
            }
            document.add(Paragraph("最後得分 ${DataProvider.getData("finalData")}").setFixedPosition(1, 395f, 465f, 100f))


            document.close()
            reader.close()
            writer.close()

        }


}