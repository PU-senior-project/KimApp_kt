package com.example.kimapp_mainkl

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.app.ActionBar
import android.os.Handler
import android.os.Looper
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.kimapp_mainkl.camera.CameraSource
import com.example.kimapp_mainkl.data.Device
import com.example.kimapp_mainkl.data.Camera
import com.example.kimapp_mainkl.MoveNet
import com.example.kimapp_mainkl.ModelType
import com.example.kimapp_mainkl.PoseClassifier

class VideoPage : AppCompatActivity() {
    companion object {
        private const val FRAGMENT_DIALOG = "dialog"
    }



    private lateinit var surfaceView: SurfaceView

    private var device = Device.CPU
    private var selectedCamera = Camera.BACK

    /** 判斷是否進行分類判斷的部分 */
    private var isPoseClassificationEnabled = false

    private var forwardheadCounter = 0
    private var crosslegCounter = 0
    private var standardCounter = 0
    private var missingCounter = 0

    private var label0Counter = 0
    private var label5Counter = 0
    private var label15Counter = 0
    private var label20Counter = 0

    /** 最终姿态 */
    private var poseLevel = "label_0"

    /** 定义一个历史姿态寄存器 */
    private var poseRegister = "label_0"

    /** 设置一个用来显示 Debug 信息的 TextView */
    private lateinit var tvDebug: TextView

    /** 设置一个用来显示当前坐姿状态的 ImageView */
    private lateinit var ivStatus: ImageView

    private lateinit var tvFPS: TextView
    private lateinit var tvScore: TextView
    private lateinit var spnDevice: Spinner
    private lateinit var spnCamera: Spinner

    private var cameraSource: CameraSource? = null
    private var isClassifyPose = true

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                /** 得到用户相机授权后，程序开始运行 */
                openCamera()
            } else {
                /** 提示用户“未获得相机权限，应用无法运行” */
                ErrorDialog.newInstance(getString(R.string.tfe_pe_request_permission))
                    .show(supportFragmentManager, FRAGMENT_DIALOG)
            }
        }

    private var changeDeviceListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            changeDevice(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            /** 如果用户未选择运算设备，使用默认设备进行计算 */
        }
    }

    private var changeCameraListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, view: View?, direction: Int, id: Long) {
            changeCamera(direction)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            /** 如果用户未选择摄像头，使用默认摄像头进行拍摄 */
        }
    }


    private var seconds = 0
    private lateinit var timerTextView: TextView
    private var timerHandler: Handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            val time = "錄製時長: $seconds 秒"
            timerTextView.text = time
            seconds++
            timerHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val doneButton = findViewById<Button>(R.id.doneButton)
        val imageButton_cameraback = findViewById<ImageButton>(R.id.imageButton_cameraback)


        imageButton_cameraback.setOnClickListener {
            val intent = Intent(this,SelectPage::class.java)
            startActivity(intent);
        }

        doneButton.setOnClickListener {
            if(seconds<10){
                TimeDialog()
            }else{
                val intent = Intent(this,SelectPage::class.java)
                startActivity(intent);
                DataProvider.saveData("checkcamera_done",1)
            }

        }

        /** 程序运行时保持屏幕常亮 */
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val upArrow = ResourcesCompat.getDrawable(resources, R.drawable.back_icon, null)
        actionBar?.setHomeAsUpIndicator(upArrow)

        // 拍攝按鈕
        val shutterButton: Button = findViewById(R.id.button3)
        shutterButton.setOnClickListener {
            if (isPoseClassificationEnabled) {
                println("停止拍攝")
                // 如果判断已经启用，则停止判断
                stopPoseClassification()
            } else {
                println("開始拍攝")
                // 如果判断未启用，则开始判断
                startPoseClassification()
            }
        }

        tvScore = findViewById(R.id.tvScore)

        /** 用来显示 Debug 信息 */
        tvDebug = findViewById(R.id.tvDebug)

        /** 用来显示当前坐姿状态 */
//        ivStatus = findViewById(R.id.ivStatus)

        tvFPS = findViewById(R.id.tvFps)
        spnDevice = findViewById(R.id.spnDevice)
        spnCamera = findViewById(R.id.spnCamera)
        surfaceView = findViewById(R.id.surfaceView)

        initSpinner()
        if (!isCameraPermissionGranted()) {
            requestPermission()
        }
    }


    private fun startPoseClassification() {
        val shutterButton: Button = findViewById(R.id.button3)
        shutterButton.text = "拍攝中"
        poseLevel = "label_0"
        poseRegister = "label_0"
        label0Counter = 0
        label5Counter = 0
        label15Counter = 0
        label20Counter = 0
        missingCounter = 0
        isPoseClassificationEnabled = true
        timerTextView = findViewById(R.id.timerTextView)
        seconds = 0
        timerHandler.post(timerRunnable)
        isPoseClassificationEnabled = true
    }

    private fun stopPoseClassification() {
        val shutterButton: Button = findViewById(R.id.button3)
        shutterButton.text = "拍攝"
        println(poseLevel)
        when{
            poseLevel == "label_0"->{
                DataProvider.saveData("selectedValue_camera",0)
            }
            poseLevel == "label_5"->{
                DataProvider.saveData("selectedValue_camera",5)
            }
            poseLevel == "label_15"->{
                DataProvider.saveData("selectedValue_camera",10)
            }
            poseLevel == "label_20"->{
                DataProvider.saveData("selectedValue_camera",20)
            }
        }
        timerHandler.removeCallbacks(timerRunnable)
        isPoseClassificationEnabled = false
    }

    override fun onStart() {
        super.onStart()
        openCamera()
    }

    override fun onResume() {
        cameraSource?.resume()
        super.onResume()
    }

    override fun onPause() {
        cameraSource?.close()
        cameraSource = null
        super.onPause()
    }

    /** 檢查相機權限 */
    private fun isCameraPermissionGranted(): Boolean {
        return checkPermission(
            Manifest.permission.CAMERA,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openCamera() {
        if (isCameraPermissionGranted()) {
            println("權限授權成功 Granted")
            if (cameraSource == null) {
                cameraSource =
                    CameraSource(surfaceView, selectedCamera, object : CameraSource.CameraSourceListener {
                        override fun onFPSListener(fps: Int) {

                            /** 解释一下，tfe_pe_tv 的意思：tensorflow example、pose estimation、text view */
                            tvFPS.text = getString(R.string.tfe_pe_tv_fps, fps)
                        }

                        /** 對分類結果進行處理 */
                        override fun onDetectedInfo(
                            personScore: Float?,
                            poseLabels: List<Pair<String, Float>>?
                        ) {
                            tvScore.text = getString(R.string.tfe_pe_tv_score, personScore ?: 0f)

                            /** 分析目標姿態，给出提示 */
                            if (isPoseClassificationEnabled && poseLabels != null && personScore != null && personScore > 0.3) {
                                missingCounter = 0
                                val sortedLabels = poseLabels.sortedByDescending { it.second }
                                when (sortedLabels[0].first) {
                                    "label_0" -> {
                                        label5Counter = 0
                                        label15Counter = 0
                                        label20Counter = 0
                                        if (poseRegister == "label_0") {
                                            label0Counter++
                                        }
                                        poseRegister = "label_0"

                                        /** 設定姿態為 label_0 */
                                        if (label0Counter > 30 && poseLevel != "label_20" && poseLevel != "label_15" && poseLevel != "label_5") {
                                            poseLevel = "label_0"
                                        }

                                        /** 顯示 Debug 訊息 */
                                        tvDebug.text = getString(
                                            R.string.tfe_pe_tv_debug,
                                            "${sortedLabels[0].first} $label0Counter"
                                        )
                                    }

                                    "label_5" -> {
                                        label0Counter = 0
                                        label15Counter = 0
                                        label20Counter = 0
                                        if (poseRegister == "label_5") {
                                            label5Counter++
                                        }
                                        poseRegister = "label_5"

                                        /** 設定姿態為 label_5 */
                                        if (label5Counter > 30 && poseLevel != "label_20" && poseLevel != "label_15") {
                                            poseLevel = "label_5"
                                        }

                                        /** 顯示 Debug 訊息 */
                                        tvDebug.text = getString(
                                            R.string.tfe_pe_tv_debug,
                                            "${sortedLabels[0].first} $label5Counter"
                                        )
                                    }

                                    "label_15" -> {
                                        label0Counter = 0
                                        label5Counter = 0
                                        label20Counter = 0
                                        if (poseRegister == "label_15") {
                                            label15Counter++
                                        }
                                        poseRegister = "label_15"

                                        /** 設定姿態為 label_15 */
                                        if (label15Counter > 30 && poseLevel != "label_20") {
                                            poseLevel = "label_15"
                                        }

                                        /** 顯示 Debug 訊息 */
                                        tvDebug.text = getString(
                                            R.string.tfe_pe_tv_debug,
                                            "${sortedLabels[0].first} $label15Counter"
                                        )
                                    }

                                    else -> {
                                        label0Counter = 0
                                        label5Counter = 0
                                        label15Counter = 0
                                        if (poseRegister == "label_20") {
                                            label20Counter++
                                        }
                                        poseRegister = "label_20"

                                        /** 設定姿態為 label_20 */
                                        if (label20Counter > 30) {
                                            poseLevel = "label_20"
                                        }
                                        /** 顯示 Debug 訊息 */
                                        tvDebug.text = getString(
                                            R.string.tfe_pe_tv_debug,
                                            "${sortedLabels[0].first} $label20Counter"
                                        )
                                    }
                                }
                            } else {
                                missingCounter++
                                if (missingCounter > 30) {
//                                    遺失30禎
                                }

                                /** 顯示 Debug 訊息 */
                                tvDebug.text =
                                    getString(R.string.tfe_pe_tv_debug, "missing $missingCounter")
                            }

                        }
                    }).apply {
                        prepareCamera()
                    }
                isPoseClassifier()
                lifecycleScope.launch(Dispatchers.Main) {
                    cameraSource?.initCamera()
                }
            }
            createPoseEstimator()
        }
    }

    private fun isPoseClassifier() {
        cameraSource?.setClassifier(if (isClassifyPose) PoseClassifier.create(this) else null)
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.tfe_pe_device_name, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spnDevice.adapter = adapter
            spnDevice.onItemSelectedListener = changeDeviceListener
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.tfe_pe_camera_name, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spnCamera.adapter = adapter
            spnCamera.onItemSelectedListener = changeCameraListener
        }
    }

    private fun changeDevice(position: Int) {
        val targetDevice = when (position) {
            0 -> Device.CPU
            1 -> Device.GPU
            else -> Device.NNAPI
        }
        if (device == targetDevice) return
        device = targetDevice
        createPoseEstimator()
    }

    private fun changeCamera(direaction: Int) {
        val targetCamera = when (direaction) {
            0 -> Camera.BACK
            else -> Camera.FRONT
        }
        if (selectedCamera == targetCamera) return
        selectedCamera = targetCamera

        cameraSource?.close()
        cameraSource = null
        openCamera()
    }

    private fun createPoseEstimator() {
        val poseDetector = MoveNet.create(this, device, ModelType.Thunder)
        poseDetector.let { detector ->
            cameraSource?.setDetector(detector)
        }
    }

    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                openCamera()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun TimeDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("提醒")
            .setMessage("錄影時長未超過10秒！請重新錄製。")
            .setPositiveButton("確定", null)
            .show()
    }

    class ErrorDialog : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(activity)
                .setMessage(requireArguments().getString(ARG_MESSAGE))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // pass
                }
                .create()

        companion object {

            @JvmStatic
            private val ARG_MESSAGE = "message"

            @JvmStatic
            fun newInstance(message: String): ErrorDialog = ErrorDialog().apply {
                arguments = Bundle().apply { putString(ARG_MESSAGE, message) }
            }
        }
    }
}
