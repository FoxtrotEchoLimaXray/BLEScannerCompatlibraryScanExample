package com.example.blescannercompatlibraryscanexample

import android.os.Bundle
import android.os.ParcelUuid
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanFilter
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings



class MainActivity : ComponentActivity() {
    private val TAG = "AndroidBLEScannerCompatlibraryExample"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ScanScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        startScan()
    }

    private fun startScan(){
        val scanner = BluetoothLeScannerCompat.getScanner()
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .setReportDelay(5000)
            .build();
        val filters = mutableListOf<ScanFilter>()
        val rogerServiceUuid = ParcelUuid.fromString("5a791800-0d19-4fd9-87f9-e934aedbce59")
        filters.add(ScanFilter.Builder().setServiceUuid(rogerServiceUuid).build())
        scanner.startScan(filters,settings,scanCallback)
    }
    private fun stopScan(){
        val scanner = BluetoothLeScannerCompat.getScanner()
        scanner.stopScan(scanCallback)
    }
    private val scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.i(TAG, result.device.address)
        }
        override fun onBatchScanResults(results: List<ScanResult>) {
            Log.i(TAG, results.size.toString() + "")
            for(result in results){
                Log.i(TAG, result.device.address)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.i(TAG, errorCode.toString() + "")
        }
    }
}

@Composable
fun ScanScreen(modifier: Modifier=Modifier){
    Box(
        modifier = Modifier.fillMaxSize()
        ){
        Text(
            text = "ScanExample see Logcat"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScanScreenPreview(){
    ScanScreen()
}