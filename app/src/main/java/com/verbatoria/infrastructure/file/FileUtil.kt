package com.verbatoria.infrastructure.file

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.os.StatFs
import org.slf4j.LoggerFactory
import java.io.*

/**
 * @author n.remnev
 */

private const val QUALITY_JPEG = 75
private const val MIN_AVAILABLE_PERCENT = 5
private const val PERCENT_100 = 100
private const val BYTE_ARRAY_SIZE = 1024
private const val VERBATORIA_PATH_NAME = "verbatoria"
private const val BYTES_PER_MEGABYTE = 1048576f // 1 mb
private const val APPLICATION_FILES_DIRECTORY = "Verbatoria"

interface FileUtil {

    fun createApplicationDirectory()

    fun getApplicationDirectory(): File

    fun saveFile(filePath: String, fileName: String, data: ByteArray): String

    fun saveFile(filePath: String, data: ByteArray)

    fun saveFile(filePath: String, fileName: String, bitmap: Bitmap): String

    fun copyFile(sourceFile: File, destinationFile: File)

    fun moveFile(inputPathFull: String, outputPath: String, outputFileName: String? = null)

    fun deleteFile(pathFull: String): Boolean

    fun deleteDirectory(pathFull: String): Boolean

    fun isAvailableExternalStorage(
        file: File?,
        ifExist: () -> Unit = {},
        ifNotExist: () -> Unit = {}
    ): Boolean

    fun isAvailableExternalStorage(ifExist: () -> Unit = {}, ifNotExist: () -> Unit = {}): Boolean

    fun getAvailableStorageSize(): Long

    fun getCacheDirPath(): String

    fun getInternalFilesDirPath(): String

    fun getCacheDirWithChildPath(): String

    fun getExternalDirPath(): String

    fun getExternalFilesDir(type: String? = null): File

    fun createFile(filePath: String, fileName: String): File

    fun copyFileToCache(originalFile: File): File

}

class FileUtilImpl(
    private val context: Context
) : FileUtil {

    private val logger = LoggerFactory.getLogger("FileUtil")

    override fun createApplicationDirectory() {
        val applicationDirectory = getApplicationDirectory()
        if (!applicationDirectory.exists()) {
            applicationDirectory.mkdirs()
        }
    }

    override fun getApplicationDirectory(): File =
        File(Environment.getExternalStorageDirectory(), APPLICATION_FILES_DIRECTORY)

    override fun saveFile(filePath: String, fileName: String, data: ByteArray): String {
        val file = createFile(filePath, fileName)
        BufferedOutputStream(FileOutputStream(file)).use { bos ->
            bos.write(data)
            bos.flush()
        }
        return file.absolutePath
    }

    override fun saveFile(filePath: String, data: ByteArray) {
        val file = File(filePath)
        file.parentFile.mkdirs()
        file.writeBytes(data)
    }

    override fun saveFile(filePath: String, fileName: String, bitmap: Bitmap): String {
        val file = createFile(filePath, fileName)
        BufferedOutputStream(FileOutputStream(file)).use { bos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_JPEG, bos)
            bos.flush()
            bitmap.recycle()
        }
        return file.absolutePath
    }

    override fun copyFile(sourceFile: File, destinationFile: File) {
        FileInputStream(sourceFile).use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                val buffer = ByteArray(BYTE_ARRAY_SIZE)
                var length = inputStream.read(buffer)
                while (length > 0) {
                    outputStream.write(buffer, 0, length)
                    length = inputStream.read(buffer)
                }
            }
        }
    }

    override fun moveFile(inputPathFull: String, outputPath: String, outputFileName: String?) {
        try {
            val dir = File(outputPath)
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw Exception("Directory not created $outputPath")
                }
            }
            val fileName = outputFileName ?: dir.name
            FileInputStream(inputPathFull).use { input ->
                FileOutputStream(outputPath + fileName).use { output ->
                    val buffer = ByteArray(1024)
                    var read: Int
                    while (true) {
                        read = input.read(buffer)
                        if (read != -1) {
                            output.write(buffer, 0, read)
                        } else {
                            break
                        }
                    }
                    output.flush()
                    File(inputPathFull).delete()
                }
            }
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }

    override fun deleteFile(pathFull: String): Boolean {
        try {
            return File(pathFull).delete()
        } catch (e: Exception) {
            logger.error(e.message)
        }

        return false
    }

    override fun deleteDirectory(pathFull: String): Boolean {
        try {
            return File(pathFull).deleteRecursively()
        } catch (e: Exception) {
            logger.error(e.message)
        }

        return false
    }

    override fun isAvailableExternalStorage(
        file: File?,
        ifExist: () -> Unit,
        ifNotExist: () -> Unit
    ): Boolean {
        if (file == null) {
            ifNotExist()
            return false
        } else {
            val stat = StatFs(file.path)
            val availablePercent = PERCENT_100 * stat.availableBytes / stat.totalBytes
            return (availablePercent >= MIN_AVAILABLE_PERCENT)
                .also { available ->
                    if (available) ifExist() else ifNotExist()
                }
        }
    }

    override fun isAvailableExternalStorage(ifExist: () -> Unit, ifNotExist: () -> Unit): Boolean =
        isAvailableExternalStorage(getExternalFilesDir(), ifExist, ifNotExist)

    override fun getAvailableStorageSize(): Long {
        val directory = Environment.getDataDirectory()
        val statFs = StatFs(directory.path)
        return statFs.blockSizeLong * statFs.availableBlocksLong / BYTES_PER_MEGABYTE.toLong()
    }

    override fun getCacheDirPath(): String =
        context.cacheDir.absolutePath

    override fun getInternalFilesDirPath(): String =
        context.filesDir.absolutePath

    override fun getCacheDirWithChildPath(): String =
        File(context.externalCacheDir, VERBATORIA_PATH_NAME).apply { mkdirs() }.absolutePath

    override fun getExternalDirPath(): String =
        context.externalCacheDir?.absolutePath ?: ""

    override fun getExternalFilesDir(type: String?): File =
        context.getExternalFilesDir(type) ?: context.filesDir

    override fun createFile(filePath: String, fileName: String): File {
        val dir = File(filePath)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw Exception("Directory not created $filePath")
            }
        }
        val file = File(filePath + fileName)
        if (file.exists()) {
            deleteFile(file.absolutePath)
        }
        if (!file.createNewFile()) {
            throw RuntimeException("failed creating file: $filePath$fileName")
        }
        return file
    }

    override fun copyFileToCache(originalFile: File): File {
        val fileName = originalFile.name.run { substring(0, indexOf(".")) }
        val expansion = originalFile.name.run { substring(fileName.length, length) }
        return originalFile.copyTo(File.createTempFile(fileName, expansion, context.cacheDir), true)
    }

    private fun getExternalFilesDir(): File =
        context.getExternalFilesDir(null) ?: context.filesDir

}