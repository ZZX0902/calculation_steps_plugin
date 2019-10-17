import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:calculation_steps_plugin/calculation_steps_plugin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String stepNum = "0";

  @override
  void initState() {
    super.initState();
    initPlatformState();
    getStep();
  }
  void dispose(){
    super.dispose();
    CalculationStepsPlugin.unregisterSensor;
  }

  void getStep () async{
    stepNum = await CalculationStepsPlugin.prepareGetStep;
    setState(() {});
  }

  void justGetStep () async{
    stepNum = await CalculationStepsPlugin.justGetStep;
    setState(() {});
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await CalculationStepsPlugin.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }


  void initPermission () async {
    await CalculationStepsPlugin.initPermission;

  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text('Running on: $_platformVersion\n'),
            Text("步数$stepNum"),
            GestureDetector(
              onTap: (){
                justGetStep();
                print("点击了");
              },
              child:Container(
                width: 100,
                height: 80,
                color: Colors.lightBlue,
                alignment: Alignment.center,
                child: Text("获取步数"),
              ),
            ),
          GestureDetector(
          onTap: (){
    initPermission();
    print("点击了");
    },
      child:Container(
        width: 100,
        height: 80,
        color: Colors.lightBlue,
        alignment: Alignment.center,
        child: Text("获取权限"),
      ),
    )

          ],

        ),
      ),
    );
  }
}
