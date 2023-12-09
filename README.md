# **KimApp**
#### **一個能夠隨時進行姿態檢測，改變您生活中的姿態不正確的工具！**
***
## 摘要

人因工程是探討人在工作中與工具、機器、設備等等之間的交互作用關係，若是使用錯誤的方式往往會導致嚴重後遺症，因此我們將根據KIM-LHC量表中抬舉、搬運、握舉作業，透過AI技術協助快速精準判斷人因危害的風險，並且即時給予改善回饋之建議。為達到此目的，因此，本計畫預計開發「AI肌肉骨骼傷病判斷及回饋App系統」(AI-based WMSD Judgement and Feedback App)，透過此App系統，一方面可使得臨場服務的醫師、護士即時了解作業人員搬運姿勢的角度是否有可能造成危害。同時，亦可即時判斷危害的風險程度並給予回饋建議。另一方面，藉由此系統，雇主和作業人員也可以了解員工日常搬運作業的危害程度而調整其搬運姿勢、荷重與工作狀況。本計畫主要目的是協助雇主對於重複性作業促發肌肉骨骼傷病，提供快速簡易且正確的危害辨識與風險評估，以利後續針對高風險作業進行善措施之運用參考，並且能納入事業單位安全衛生管理制度中落實執行。

## 畫面

1.主頁

![broke](https://i.imgur.com/UTypRrl.jpg)

2.目標頁

![broke](https://i.imgur.com/HoK8U1b.jpg)

3.結果頁

![broke](https://i.imgur.com/SDRXMHa.jpg)

4.PDF報告書

![broke](https://i.imgur.com/w2mKNvH.jpg)

## 安裝

以下會引導您如何完成安裝專案至您的電腦上 Flutter sdk版本建議為: 3.13.0 Dart 版本建議為: 3.0.5

### 取得專案

```
git clone -b ft_Fan https://github.com/PU-senior-project/KimApp_kt.git
```

### 進入到專題目錄

### 進行依賴獲取

`到build.gradle.kts裡點擊上方Sync Now即可獲取。`

### 檢查設備

至少要有一個設備(實體手機、虛擬機)能夠連接到您的開發機器。

### 運行

`點擊工具欄上的「Run」按鈕（一個綠色的三角形圖標），或者使用快捷鍵 Shift + F10 來運行您的應用。Android Studio 會自動編譯您的應用，並將其部署到選定的設備或模擬器上。`

## 技術
- Kotlin 1.9.0-release-358
- Java SDK JavaVersion.VERSION_1_8
- tflite
- com.itextpdf.android:layout-android 和 com.itextpdf.android:kernel-android: 8.0.2
- org.tensorflow:tensorflow-lite 系列: 2.14.0