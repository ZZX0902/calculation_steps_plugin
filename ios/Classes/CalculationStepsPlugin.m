#import "CalculationStepsPlugin.h"
#import <calculation_steps_plugin/calculation_steps_plugin-Swift.h>

@implementation CalculationStepsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftCalculationStepsPlugin registerWithRegistrar:registrar];
}
@end
