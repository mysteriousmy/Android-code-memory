import 'package:bill/bottom_navigation_widget.dart';
import 'package:bill/colors.dart';
import 'package:bill/widgets/colours.dart';
import 'package:bill/pages/user/forgot.dart';
import 'package:bill/pages/group/create-group.dart';
import 'package:bill/pages/group/edit-group.dart';
import 'package:bill/pages/group/group-detail.dart';
import 'package:bill/pages/group/groups.dart';
import 'package:bill/pages/limit/limit-set.dart';
import 'package:bill/pages/user/login.dart';
import 'package:bill/pages/user/profile.dart';
import 'package:bill/pages/bill/record.dart';
import 'package:bill/pages/user/register.dart';
import 'package:bill/pages/reminder/create-reminder.dart';
import 'package:bill/pages/reminder/edit-reminder.dart';
import 'package:bill/pages/reminder/reminder-detail.dart';
import 'package:bill/pages/reminder/reminders.dart';
import 'package:bill/pages/task/create-task.dart';
import 'package:bill/pages/task/edit-task.dart';
import 'package:bill/pages/task/task-detail.dart';
import 'package:bill/pages/task/tasks.dart';
import 'package:bill/pages/bill/month-bill-list.dart';
import 'package:bill/router.dart';
import 'package:bill/stores/stores.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart' hide Router;
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_statusbar_manager/flutter_statusbar_manager.dart';
// import 'package:moblink/moblink.dart';

void main() {
  try {
    WidgetsFlutterBinding.ensureInitialized();
  } catch (e) {}

  Router router = new Router();

  router.define('tasks',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              TaskPage()),
      transitionType: TransitionType.native);
  router.define('create-task',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              CreateTaskPage()),
      transitionType: TransitionType.native);
  router.define('edit-task',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              EditTaskPage(
                  id: (params['id'] != null && params['id'].length > 0)
                      ? params['id'][0]
                      : '')),
      transitionType: TransitionType.native);
  router.define('task-detail',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              TaskDetailPage(
                  id: (params['id'] != null && params['id'].length > 0)
                      ? params['id'][0]
                      : '')),
      transitionType: TransitionType.native);
  router.define('record',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              RecordPage()),
      transitionType: TransitionType.native);
  router.define('save-reminder',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              RemindersPage()),
      transitionType: TransitionType.native);
  router.define('create-reminder',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              CreateReminderPage()),
      transitionType: TransitionType.native);
  router.define('reminder-detail',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              ReminderDetailPage(
                  id: (params['id'] != null && params['id'].length > 0)
                      ? params['id'][0]
                      : '')),
      transitionType: TransitionType.native);
  router.define('edit-reminder',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              EditReminderPage(
                  id: (params['id'] != null && params['id'].length > 0)
                      ? params['id'][0]
                      : '')),
      transitionType: TransitionType.native);
  router.define('limit-set',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              LimitSetPage()),
      transitionType: TransitionType.native);
  router.define('groups',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              GroupsPage()),
      transitionType: TransitionType.native);
  router.define('create-group',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              CreateGroupPage()),
      transitionType: TransitionType.native);
  router.define('edit-group',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              EditGroupPage(
                  id: (params['id'] != null && params['id'].length > 0)
                      ? params['id'][0]
                      : '')),
      transitionType: TransitionType.native);
  router.define('group-detail',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              GroupDetailPage(
                  id: (params['id'] != null && params['id'].length > 0)
                      ? params['id'][0]
                      : '')),
      transitionType: TransitionType.native);
  router.define('login',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              LoginPage(
                  target:
                      (params['target'] != null && params['target'].length > 0)
                          ? params['target'][0]
                          : '')),
      transitionType: TransitionType.native);
  router.define('register',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              RegisterPage(
                  target:
                      (params['target'] != null && params['target'].length > 0)
                          ? params['target'][0]
                          : '')),
      transitionType: TransitionType.native);
  router.define('forgot',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              ForgotPage()),
      transitionType: TransitionType.native);
  router.define('profile',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              ProfilePage()),
      transitionType: TransitionType.native);
  router.define('month-bill-list',
      handler: Handler(
          handlerFunc: (BuildContext context, Map<String, dynamic> params) =>
              MonthBillListPage(
                  month: (params['month'] != null && params['month'].length > 0)
                      ? params['month'][0]
                      : '')));

  AppRouter.router = router;

  runApp(BillApp());
}

class BillApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    AppStores.initStores();

    FlutterStatusbarManager.setColor(Colors.white);

    return BotToastInit(
        child: new MaterialApp(
            title: '个人记账',
            debugShowCheckedModeBanner: false,
            theme: ThemeData(
                splashColor: Colors.transparent,
                highlightColor: Colors.transparent,
                buttonTheme: ButtonThemeData(
                    minWidth: double.infinity,
                    splashColor: Colors.transparent,
                    highlightColor: Colors.transparent,
                    textTheme: ButtonTextTheme.accent),
                accentColor: AppColors.appTextNormal,
                scaffoldBackgroundColor: AppColors.appWhite,
                primaryColor: Colors.white,
                appBarTheme: AppBarTheme(elevation: 0 // app标题栏阴影
                    )),
            localizationsDelegates: [
              GlobalMaterialLocalizations.delegate,
              GlobalWidgetsLocalizations.delegate
            ],
            supportedLocales: [Locale('zh', 'CH'), Locale('en', 'US')],
            navigatorObservers: [BotToastNavigatorObserver()],
            home: BottomNavigationWidget(),
            onGenerateRoute: AppRouter.router.generator));
  }
}
