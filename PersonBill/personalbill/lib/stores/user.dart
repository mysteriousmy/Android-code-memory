import 'dart:async';

import 'package:bill/api.dart';
import 'package:bill/bean/user.dart';
import 'package:bill/http/http-util.dart';
import 'package:bill/pages/tab/index.dart';
import 'package:bill/stores/base.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:mobx/mobx.dart';
import 'package:shared_preferences/shared_preferences.dart';

part 'user.g.dart';

class UserStore = _UserStore with _$UserStore;

abstract class _UserStore extends BaseStore with Store {
  @observable
  bool logined = false;

  @observable
  User userInfo;

  @observable
  UserBillCount billCount;

  Future<String> getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();

    String token = prefs.getString('userToken');
    return token;
  }

  Future<void> setToken(token) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString('userToken', token);
  }

  Future<void> removeToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.remove('userToken');
  }

  @action
  Future<bool> ensureLogin() async {
    String token = await getToken();

    if (token == null) {
      logined = false;
      return false;
    }

    Map<String, dynamic> resp =
        await HttpUtil.request(Api.ensureLogined, {}, HttpUtil.GET);
    HttpResponse data = new HttpResponse.formJson(resp);
    print(data.message);
    if (data.success) {
      BotToast.showText(text: data.message);
      logined = true;
      userInfo = new User.fromJson(data.data);
      IndexState indexState = new IndexState();
      await indexState.initPage();
      await setToken(userInfo.token);
    } else {
      removeToken();
      logined = false;
    }

    return logined;
  }

  @action
  Future<bool> login(String mobile, String password, [String device]) async {
    try {
      switchLoading(true);
      Map<String, dynamic> resp = await HttpUtil.request(
          Api.login, {'mobile': mobile, 'password': password}, HttpUtil.POST);

      switchLoading(false);

      HttpResponse data = new HttpResponse.formJson(resp);
      print(data.success);

      if (data.success) {
        logined = true;
        userInfo = new User.fromJson(data.data);
        print(userInfo.avatar);
        await setToken(data.data['token']);
      } else {
        logined = false;
        BotToast.showText(text: data.message);
      }
      return logined;
    } catch (e) {
      print(e);
      switchLoading(false);
      BotToast.showText(text: HttpUtil.UNKNOWN_ERROR);
      return false;
    }
  }

  @action
  Future<bool> validateCode(String mobile, String code) async {
    try {
      Map<String, dynamic> resp = await HttpUtil.request(
          Api.validateVCode, {'phone': mobile, 'code': code}, HttpUtil.POST);

      HttpResponse data = new HttpResponse.formJson(resp);

      bool validateSuccess = data.success;

      if (!validateSuccess) {
        BotToast.showText(text: data.message);
      }

      return validateSuccess;
    } catch (e) {
      BotToast.showText(text: HttpUtil.UNKNOWN_ERROR);
      return false;
    }
  }

  @action
  Future<bool> registerLogin(String mobile, String password, String rid,
      [String device]) async {
    try {
      switchLoading(true);
      Map<String, dynamic> resp = await HttpUtil.request(
          Api.registerLogin,
          {
            'mobile': mobile,
            'password': password,
            'registrationId': rid,
            'device': device
          },
          HttpUtil.POST);
      switchLoading(false);
      print("gi：");
      print(resp);
      HttpResponse data = new HttpResponse.formJson(resp);
      print("hi：");
      print(data);
      if (data.success) {
        logined = true;
        userInfo = new User.fromJson(data.data);
        await setToken(data.data['token']);
      } else {
        logined = false;
        BotToast.showText(text: data.message);
      }
      return logined;
    } catch (e) {
      print(e);
      switchLoading(false);
      BotToast.showText(text: HttpUtil.UNKNOWN_ERROR);
      return false;
    }
  }

  @action
  Future<bool> forgot(String mobile, String password) async {
    try {
      switchLoading(true);
      Map<String, dynamic> resp = await HttpUtil.request(
          Api.forgot, {'mobile': mobile, 'password': password}, HttpUtil.PUT);
      switchLoading(false);

      HttpResponse data = new HttpResponse.formJson(resp);

      if (!data.success) {
        BotToast.showText(text: data.message);
      }

      return data.success;
    } catch (e) {
      switchLoading(false);
      BotToast.showText(text: HttpUtil.UNKNOWN_ERROR);
      return false;
    }
  }

  @action
  Future<bool> getBillCount() async {
    try {
      switchLoading(true);
      Map<String, dynamic> resp =
          await HttpUtil.request(Api.billCount, {}, HttpUtil.GET);

      HttpResponse data = new HttpResponse.formJson(resp);
      BotToast.showText(text: data.message);
      if (data.success) {
        billCount = new UserBillCount.fromJson(data.data);
        print("你好:");
        print(billCount);
      } else {
        billCount = null;
      }

      switchLoading(false);

      return data.success;
    } catch (e) {
      billCount = null;
      switchLoading(false);

      return false;
    }
  }

  @action
  bool logout() {
    removeToken();
    logined = false;
    Future.delayed(new Duration(seconds: 1)).whenComplete(() {
      userInfo = null;
    });
    return logined;
  }
}
