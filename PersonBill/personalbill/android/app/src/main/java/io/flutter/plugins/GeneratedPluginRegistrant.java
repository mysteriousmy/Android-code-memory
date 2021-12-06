package io.flutter.plugins;

import io.flutter.plugin.common.PluginRegistry;
import com.foo.flutterstatusbarmanager.FlutterStatusbarManagerPlugin;
import io.flutter.plugins.deviceinfo.DeviceInfoPlugin;
import dev.bughub.flt_telephony_info.FltTelephonyInfoPlugin;
import io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin;
import io.flutter.plugins.imagepicker.ImagePickerPlugin;
import com.mob.mobpush_plugin.MobpushPlugin;
import io.flutter.plugins.pathprovider.PathProviderPlugin;
import io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin;

/**
 * Generated file. Do not edit.
 */
public final class GeneratedPluginRegistrant {
  public static void registerWith(PluginRegistry registry) {
    if (alreadyRegisteredWith(registry)) {
      return;
    }
    FlutterStatusbarManagerPlugin.registerWith(registry.registrarFor("com.foo.flutterstatusbarmanager.FlutterStatusbarManagerPlugin"));
    DeviceInfoPlugin.registerWith(registry.registrarFor("io.flutter.plugins.deviceinfo.DeviceInfoPlugin"));
    FltTelephonyInfoPlugin.registerWith(registry.registrarFor("dev.bughub.flt_telephony_info.FltTelephonyInfoPlugin"));
    FlutterAndroidLifecyclePlugin.registerWith(registry.registrarFor("io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin"));
    ImagePickerPlugin.registerWith(registry.registrarFor("io.flutter.plugins.imagepicker.ImagePickerPlugin"));
    MobpushPlugin.registerWith(registry.registrarFor("com.mob.mobpush_plugin.MobpushPlugin"));
    PathProviderPlugin.registerWith(registry.registrarFor("io.flutter.plugins.pathprovider.PathProviderPlugin"));
    SharedPreferencesPlugin.registerWith(registry.registrarFor("io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin"));
  }

  private static boolean alreadyRegisteredWith(PluginRegistry registry) {
    final String key = GeneratedPluginRegistrant.class.getCanonicalName();
    if (registry.hasPlugin(key)) {
      return true;
    }
    registry.registrarFor(key);
    return false;
  }
}
