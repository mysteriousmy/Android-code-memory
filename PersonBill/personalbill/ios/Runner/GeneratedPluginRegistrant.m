//
//  Generated file. Do not edit.
//

#import "GeneratedPluginRegistrant.h"

#if __has_include(<flutter_statusbar_manager/FlutterStatusbarManagerPlugin.h>)
#import <flutter_statusbar_manager/FlutterStatusbarManagerPlugin.h>
#else
@import flutter_statusbar_manager;
#endif

#if __has_include(<device_info/FLTDeviceInfoPlugin.h>)
#import <device_info/FLTDeviceInfoPlugin.h>
#else
@import device_info;
#endif

#if __has_include(<flt_telephony_info/FltTelephonyInfoPlugin.h>)
#import <flt_telephony_info/FltTelephonyInfoPlugin.h>
#else
@import flt_telephony_info;
#endif

#if __has_include(<image_picker/FLTImagePickerPlugin.h>)
#import <image_picker/FLTImagePickerPlugin.h>
#else
@import image_picker;
#endif

#if __has_include(<mobpush_plugin/MobpushPlugin.h>)
#import <mobpush_plugin/MobpushPlugin.h>
#else
@import mobpush_plugin;
#endif

#if __has_include(<path_provider/FLTPathProviderPlugin.h>)
#import <path_provider/FLTPathProviderPlugin.h>
#else
@import path_provider;
#endif

#if __has_include(<shared_preferences/FLTSharedPreferencesPlugin.h>)
#import <shared_preferences/FLTSharedPreferencesPlugin.h>
#else
@import shared_preferences;
#endif

@implementation GeneratedPluginRegistrant

+ (void)registerWithRegistry:(NSObject<FlutterPluginRegistry>*)registry {
  [FlutterStatusbarManagerPlugin registerWithRegistrar:[registry registrarForPlugin:@"FlutterStatusbarManagerPlugin"]];
  [FLTDeviceInfoPlugin registerWithRegistrar:[registry registrarForPlugin:@"FLTDeviceInfoPlugin"]];
  [FltTelephonyInfoPlugin registerWithRegistrar:[registry registrarForPlugin:@"FltTelephonyInfoPlugin"]];
  [FLTImagePickerPlugin registerWithRegistrar:[registry registrarForPlugin:@"FLTImagePickerPlugin"]];
  [MobpushPlugin registerWithRegistrar:[registry registrarForPlugin:@"MobpushPlugin"]];
  [FLTPathProviderPlugin registerWithRegistrar:[registry registrarForPlugin:@"FLTPathProviderPlugin"]];
  [FLTSharedPreferencesPlugin registerWithRegistrar:[registry registrarForPlugin:@"FLTSharedPreferencesPlugin"]];
}

@end
