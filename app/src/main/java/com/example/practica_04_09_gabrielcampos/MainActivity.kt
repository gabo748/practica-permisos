package com.example.practica_04_09_gabrielcampos

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.provider.Settings
import android.widget.Button
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private val REQUEST_LOCATION_PERMISSION = 1
    private val REQUEST_CODE_OVERLAY_PERMISSION = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRequestLocation: Button = findViewById(R.id.btnRequestLocation)
        val btnShowWindow: Button = findViewById(R.id.btnShowWindow)
        val btnDownload: Button = findViewById(R.id.btnDownload)

        btnRequestLocation.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            } else {
                Toast.makeText(this, "Permiso de ubicación ya concedido", Toast.LENGTH_SHORT).show()
            }
        }

        btnShowWindow.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                    startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION)
                } else {
                    showOverlayWindow()
                }
            } else {
                showOverlayWindow()
            }
        }

        btnDownload.setOnClickListener {
            // Aquí puedes realizar una solicitud HTTP (esto es solo un ejemplo)
            Toast.makeText(this, "Permiso de Internet concedido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showOverlayWindow() {
        Toast.makeText(this, "Permiso para mostrar ventanas sobre otras aplicaciones concedido", Toast.LENGTH_SHORT).show()
        // Implementa la lógica para mostrar una ventana flotante aquí
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                showOverlayWindow()
            } else {
                Toast.makeText(this, "Permiso para mostrar ventanas sobre otras aplicaciones no concedido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de ubicación concedido", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}