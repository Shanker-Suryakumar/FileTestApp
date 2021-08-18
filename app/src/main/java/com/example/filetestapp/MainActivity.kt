package com.example.filetestapp

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// https://www.codevoila.com/post/46/android-tutorial-android-external-storage - Reference URL for Java Source Code

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = MainActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "File Test App onCreate() method called.")
    }

    private fun checkSDCardStatus(): Boolean {
        Log.d(TAG, "File Test App checkSDCardStatus() method called.")
        return when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED -> {
                Log.d(TAG, "Media is mounted.")
                true
            }
            Environment.MEDIA_MOUNTED_READ_ONLY -> {
                Log.d(TAG, "SD card is ready only.")
                Toast.makeText(this, "SD card is ready only.", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                Log.d(TAG, "SD card is not available.")
                Toast.makeText(this, "SD card is not available.", Toast.LENGTH_LONG).show()
                false
            }
        }
    }

    private fun writeDataToPath(path: File, fileName: String, data: String) {
        Log.d(TAG, "File Test App writeDataToPath() method called.")
        val targetFilePath = File(path, fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(targetFilePath)
            fos.write(data.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "Failed: " + e.message)
            Toast.makeText(this, "Failed: " + e.message, Toast.LENGTH_LONG).show()
        } finally {
            if (fos != null) {
                try {
                    Log.d(
                        TAG, "Write to <" + targetFilePath.absolutePath
                            .toString() + "> successfully!"
                    )
                    Toast.makeText(
                        this,
                        "Write to <" + targetFilePath.absolutePath
                            .toString() + "> successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.d(TAG, "IOException while writing data to path.")
                }
            } else {
                Log.d(TAG, "Failed to write!")
                Toast.makeText(this, "Failed to write!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "File Test App onClick() method called.")
        if (!checkSDCardStatus()) {
            Log.d(TAG, "SD card status check failed.")
            return
        }

        var path: File?
        var fileName: String?
        var contentData: String?

        when (v?.id) {
            R.id.btn_get_external_storage_directory -> {
                path = Environment.getExternalStorageDirectory()
                Log.d(TAG, path?.absolutePath.toString())
                Toast.makeText(this, path?.absolutePath, Toast.LENGTH_LONG).show()
            }
            R.id.btn_write_to_external_storage_directory -> {
                path = Environment.getExternalStorageDirectory()
                Log.d(TAG, path?.absolutePath.toString())
                fileName = "GetExternalStorageDirectory.txt"
                contentData = "GetExternalStorageDirectory() demo"
                this.writeDataToPath(path, fileName, contentData)
            }
            R.id.btn_get_external_files_dir -> {
                path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                Log.d(TAG, path?.absolutePath.toString())
                Toast.makeText(this, path?.absolutePath, Toast.LENGTH_LONG).show()
            }
            R.id.btn_write_to_external_files_dir -> {
                path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                Log.d(TAG, path?.absolutePath.toString())
                fileName = "GetExternalFilesDir.txt";
                contentData = "GetExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) demo";
                path?.let { this.writeDataToPath(it, fileName!!, contentData!!) };
            }
            R.id.btn_get_external_cache_dir -> {
                path = externalCacheDir
                Log.d(TAG, path?.absolutePath.toString())
                Toast.makeText(this, path?.absolutePath, Toast.LENGTH_LONG).show()
            }
            R.id.btn_write_to_external_cache_dir -> {
                path = externalCacheDir
                Log.d(TAG, path?.absolutePath.toString())
                fileName = "GetExternalCacheDir.txt"
                contentData = "GetExternalCacheDir() demo"
                path?.let { this.writeDataToPath(it, fileName!!, contentData!!) }
            }
            R.id.btn_get_external_storage_public_directory -> {
                path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                Log.d(TAG, path?.absolutePath.toString())
                Toast.makeText(this, path?.absolutePath, Toast.LENGTH_LONG).show()
            }
            R.id.btn_write_to_external_storage_public_directory -> {
                path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                Log.d(TAG, path?.absolutePath.toString())
                fileName = "GetExternalStoragePublicDirectory.txt"
                contentData =
                    "GetExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) demo"
//                Files.createDirectories(Paths.get(path.toString()))
                this.writeDataToPath(path, fileName, contentData)
            }
            else -> {
                Log.d(TAG, R.string.no_match_found.toString())
                Toast.makeText(this, R.string.no_match_found, Toast.LENGTH_LONG).show()
            }
        }
    }
}