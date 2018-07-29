package com.dev.runtimepermissionsutill

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings

import androidx.appcompat.app.AppCompatActivity

open class PermissionClass : AppCompatActivity() {
    internal var onPermissionCallBack: RequestPermissionAction? = null

    private fun checkPermission(permissions: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(permissions) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun getPermission(onPermissionCallBack: RequestPermissionAction?, permissions: String) {
        this.onPermissionCallBack = onPermissionCallBack
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission(permissions)) {
                requestPermissions(arrayOf(permissions), REQUEST_PERMISSION)
                return
            }
        }
        onPermissionCallBack?.permissionGranted()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            if (onPermissionCallBack != null) {
                onPermissionCallBack!!.permissionGranted()
            }

        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (REQUEST_PERMISSION == requestCode) {


                val i = Intent()
                i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                i.addCategory(Intent.CATEGORY_DEFAULT)
                i.data = Uri.parse("package:$packageName")
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                startActivity(i)
            }
            if (onPermissionCallBack != null) {
                onPermissionCallBack!!.permissionDenied()
            }
        }
    }

    interface RequestPermissionAction {
        fun permissionDenied()

        fun permissionGranted()
    }

    companion object {

        private val REQUEST_PERMISSION = 123
    }

}
