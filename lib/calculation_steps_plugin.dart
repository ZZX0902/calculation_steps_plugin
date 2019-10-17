import 'dart:async';

import 'package:flutter/services.dart';

class CalculationStepsPlugin {
  static const MethodChannel _channel =
      const MethodChannel('calculation_steps_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  ///准备获取步数
  static Future<String> get prepareGetStep async {
    final String version = await _channel.invokeMethod('startGetStep');
    return version;
  }
///获取步数
  static Future<String> get justGetStep async {
    final String version = await _channel.invokeMethod('justGetStep');
    return version;
  }
  static Future<String> get registerSensor async {
    final String version = await _channel.invokeMethod('registerSensor');
    return version;
  }

 ///销毁
  static Future<String> get unregisterSensor async {
    final String version = await _channel.invokeMethod('unregisterSensor');
    return version;
  }

  ///申请权限
  static Future<String> get initPermission async {
    final String version = await _channel.invokeMethod('initPermission');
    return version;
  }
}
