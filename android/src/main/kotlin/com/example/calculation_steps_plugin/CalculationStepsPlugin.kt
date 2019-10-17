package com.example.calculation_steps_plugin

import android.Manifest
import android.content.Context.SENSOR_SERVICE
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.util.ArrayList



class CalculationStepsPlugin: MethodCallHandler {

  companion object {
    private var registrar: Registrar? = null
    private var mystepcounter: Sensor? = null//单次步伐传感器
    private var stepSensor = "0"
    private var stepCounterListener: SensorEventListener? = null//步伐总数传感器事件监听器

    //传感器
    private var mSensorManager: SensorManager? = null
    @JvmStatic

    fun registerWith(registrar: Registrar) {
      this.registrar = registrar
      val channel = MethodChannel(registrar.messenger(), "calculation_steps_plugin")
      channel.setMethodCallHandler(CalculationStepsPlugin())
    }

    //申请权限
     fun initPermission (){
        val permissions = arrayOf(Manifest.permission.BODY_SENSORS)
        val toApplyList = ArrayList<String>()

        for (perm in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(registrar?.activity()!!, perm)) {
                toApplyList.add(perm)
                //进入到这里代表没有权限.

            }
        }
        val tmpList = arrayOfNulls<String>(toApplyList.size)
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(registrar?.activity()!!, toApplyList.toTypedArray(), 123)
        }
    }
     //测试SDK版本
    fun  judgementVersion(){
       Log.e("测试SDK版本","运行啦！")
       val VERSION_CODES = Build.VERSION.SDK_INT
      if (VERSION_CODES >= 19) {
        initSensorManager()
      } else {
        Log.e("测试", "VERSION_CODES < 19")

      }
    }
    //初始化传感器
    fun initSensorManager(){
      Log.e("初始化传感器","运行啦！")

      mSensorManager = registrar?.activity()?.getSystemService(SENSOR_SERVICE) as SensorManager//获取传感器系统服务
      mystepcounter = mSensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)//获取计步总数传感器
      initListener()
    }
    //注册监听
    fun  initListener (){
      Log.e("注册监听","运行啦！")
      stepCounterListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
          Log.e("onSensorChanged","运行啦！")
          Log.e("Counter-SensorChanged", event.values[0].toString() + "---" + event.accuracy + "---" + event.timestamp)
          stepSensor = event.values[0].toString()
          Log.e("获取步数",event.values[0].toString())

        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
          Log.e("onAccuracyChanged","运行啦！")

          Log.e("Counter-Accuracy", sensor.name + "---" + accuracy)

        }
      }


    }



    //注册传感器事件监听器
    fun registerSensor (){
      Log.e("registerSensor", "registerSensor注册传感器事件监听器")

      if (registrar?.activity()?.getPackageManager()!!.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER) && registrar?.activity()?.getPackageManager()!!.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)) {
        mSensorManager?.registerListener(stepCounterListener, mystepcounter, SensorManager.SENSOR_DELAY_FASTEST)
      }
    }


    //注销传感器事件监听器
    fun unregisterSensor(){
      if (registrar?.activity()?.getPackageManager()!!.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER) && registrar?.activity()?.getPackageManager()!!.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)) {
        mSensorManager?.unregisterListener(stepCounterListener)
      }
    }


//    fun initPermission(){
//      val permissions = arrayOf(Manifest.permission.BODY_SENSORS)
//      val toApplyList = ArrayList<String>()
//
//      for (perm in permissions) {
//        if (PackageManager.PERMISSION_GRANTED != registrar?.activity().ContextCompat.checkSelfPermission(this, perm)) {
//          toApplyList.add(perm)
//          //进入到这里代表没有权限.
//
//        }
//      }
//      val tmpList = arrayOfNulls<String>(toApplyList.size)
//      if (!toApplyList.isEmpty()) {
//        registrar?.activity().ActivityCompat.requestPermissions(this, toApplyList.toTypedArray<T>(), 123)
//      }
//    }





  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
        "getPlatformVersion" -> {
          Log.e("getPlatformVersion","运行啦！")
          result.success("Android ${android.os.Build.VERSION.RELEASE}")
        }
        "startGetStep" -> {
          Log.e("startGetStep","运行啦！")

          judgementVersion()
          registerSensor()

          result.success(stepSensor)
        }
        "justGetStep" -> {
          Log.e("justGetStep","运行啦！")
          result.success(stepSensor)

        }
        "registerSensor" -> {
          Log.e("registerSensor","运行啦！")

          registerSensor()
          result.success("suc")
        }
        "unregisterSensor" -> {
          Log.e("unregisterSensor","运行啦！")
          unregisterSensor()
          result.success("suc")

        }
        "initPermission" -> {
            Log.e("initPermission","申请权限运行啦！")
            initPermission()
            result.success("suc")

        }
        else -> result.notImplemented()
    }






  }
}







