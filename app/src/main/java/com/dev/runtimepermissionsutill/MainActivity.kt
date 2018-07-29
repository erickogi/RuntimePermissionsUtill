package com.dev.runtimepermissionsutill

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast

class MainActivity : PermissionClass() {

    internal lateinit var spinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.spinner)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i != 0) {
                    prosecutePermission("android.permission." + spinner.selectedItem.toString())
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }


    }

    private fun prosecutePermission(permision: String) {
        getPermission(object : PermissionClass.RequestPermissionAction {
            override fun permissionDenied() {
                Toast.makeText(this@MainActivity, "Permision denied", Toast.LENGTH_LONG).show()

            }

            override fun permissionGranted() {
                Toast.makeText(this@MainActivity, "Permision Granted", Toast.LENGTH_LONG).show()

            }
        }, permision)
    }
}
