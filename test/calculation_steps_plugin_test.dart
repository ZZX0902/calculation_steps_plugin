import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:calculation_steps_plugin/calculation_steps_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('calculation_steps_plugin');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await CalculationStepsPlugin.platformVersion, '42');
  });
}
