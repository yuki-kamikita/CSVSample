package com.akaiyukiusagi.csvsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.FileProvider
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var fileDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileDirectory = File(filesDir, "test.csv")

        button_save.setOnClickListener {
            val data = mutableListOf("data1","data2","data3")
            writeCsv(fileDirectory, data)
        }
        button_share.setOnClickListener {
            shareCsv()
        }
    }

    /**
     * @param fileName    出力先logファイル
     * @param dataList    挿入する一行分のデータ
     * @param dateTime    日時
     *
     * 保存
     */
    private fun writeCsv(fileName: File, dataList: MutableList<String>) {
        // 一行追加
        dataList.add(LocalDateTime.now().toString())
        val csvData = listOf(dataList)
        csvWriter().writeAll(csvData, fileName, true)
    }

    /** 出力 */
    private fun shareCsv() {
        if (fileDirectory.exists()) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                type = "*/*"
                putExtra(
                        Intent.EXTRA_STREAM,
                        FileProvider.getUriForFile(
                                this@MainActivity,
                                "${applicationContext.packageName}.provider",
                                fileDirectory)
                )
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "ファイルがありません", Toast.LENGTH_SHORT).show()
        }
    }

    /** 削除 */
    private fun deleteCsv() {

    }
}

// TODO: 表示