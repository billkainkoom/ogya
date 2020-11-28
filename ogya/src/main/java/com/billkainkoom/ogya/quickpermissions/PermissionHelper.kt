package com.billkainkoom.ogya.quickpermissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.billkainkoom.ogya.R
import com.billkainkoom.ogya.quickdialog.QuickDialog
import com.billkainkoom.ogya.quickdialog.QuickDialogType
import com.billkainkoom.ogya.shared.QuickObject

/**
 * Created by Bill on 10/25/2017.
 */
class PermissionHelper(private var activity: Activity) {


    var requestCode = 829
    lateinit var permissionList: Array<out String>

    fun arePermissionsGranted(vararg permissionList: String): Boolean {
        this.permissionList = permissionList
        var allGranted = true

        for (permission in permissionList) {
            if (ContextCompat.checkSelfPermission(activity.applicationContext, permission) != PERMISSION_GRANTED) {
                allGranted = false
            }
        }

        //if true it means all permissions have been granted or no permissions were requested
        return allGranted
    }

    fun requestPermissions(requestCode: Int, vararg permissionList: String): Boolean {
        this.permissionList = permissionList
        this.requestCode = requestCode
        var allGranted = true

        for (permission in permissionList) {
            if (ContextCompat.checkSelfPermission(activity.applicationContext, permission) != PERMISSION_GRANTED) {
                allGranted = false
            }
        }

        if (!allGranted) {
            ActivityCompat.requestPermissions(activity, permissionList, requestCode)
        }
        //if true it means all permissions have been granted or no permissions were requested
        return allGranted
    }

    fun handlePermissionRequestResponse(quickObject: QuickObject, requestCode: Int, permissions: Array<String>, grantResults: IntArray, permissionRequestListener: PermissionRequestListener) {
        var allGranted = true

        for (i in permissions.indices) {
            if (grantResults[i] != PERMISSION_GRANTED) {
                allGranted = false
            }
        }
        if (!allGranted) {
            QuickDialog(context = activity, style = QuickDialogType.Alert, title = quickObject.title, message = quickObject.subtitle, image = quickObject.image)
                    .overrideClicks({ dismiss ->
                        requestPermissions(requestCode, *permissionList)
                        dismiss()
                        permissionRequestListener.onPermissionRequestResponse(false)
                    }, { dismiss ->
                        dismiss()
                        permissionRequestListener.onPermissionRequestResponse(false)
                    }).overrideButtonNames(R.string.string_continue, R.string.string_cancel).show()
        } else {
            permissionRequestListener.onPermissionRequestResponse(true)
        }
    }

    interface PermissionRequestListener {
        fun onPermissionRequestResponse(granted: Boolean)
    }

    companion object {
        const val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
    }

}