import 'dart:core';

import 'package:bill/event.dart';
import 'package:bill/stores/stores.dart';
import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart' hide Router;

const String LOGIN_PATH = 'login';

class AppRouter {
  static Router router;

  static toPage(BuildContext context, String path, [bool checkLogin = true]) {
    final userStore = AppStores.userStore;

    if (checkLogin && userStore.logined != true) {
      String finalPath = '$LOGIN_PATH?target=${Uri.encodeComponent(path)}';
      return router.navigateTo(context, finalPath);
    }
    return router.navigateTo(context, path);
  }

  static redirectTo(BuildContext context, String path,
      [bool checkLogin = false]) {
    Navigator.pop(context);
    toPage(context, path, checkLogin);
  }

  static back(BuildContext context) {
    Navigator.pop(context);
  }

  static toHome(BuildContext context) {
    AppEvent.emit('switchIndex', 0);
    Navigator.popUntil(context, ModalRoute.withName('/'));
  }
}
