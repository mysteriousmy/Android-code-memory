import 'dart:io';

import 'package:bill/adaptor.dart';
import 'package:bill/bean/bill.dart';
import 'package:bill/colors.dart';
import 'package:bill/widgets/colours.dart';
import 'package:bill/iconfont.dart';
import 'package:bill/methods-icons.dart';
import 'package:bill/router.dart';
import 'package:bill/stores/bill.dart';
import 'package:bill/stores/statistics.dart';
import 'package:bill/stores/stores.dart';
import 'package:bill/stores/user.dart';
import 'package:bill/widgets/empty.dart';
import 'package:bill/widgets/pop-menu.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/material.dart' hide Router;
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:percent_indicator/percent_indicator.dart';

class IndexPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => IndexState();
}

class IndexState extends State<IndexPage> {
  final BillStore billStore = AppStores.billStore;
  final StatisticsStore statisticsStore = AppStores.statisticsStore;
  final UserStore userStore = AppStores.userStore;

  @override
  void initState() {
    super.initState();
    initPage();
  }

  @override
  void didChangeDependencies() {
    print('_HomeItemPageState didChangeDependencies');
    initPage();
    super.didChangeDependencies();
  }

  void initPage() {
    print("发送请求");
    bool logined = userStore.logined;
    print(logined);
    if (logined) {
      statisticsStore.compareLast();
      billStore.getMonthBills({'toast': false});
    } else {
      billStore.homeBills = null;
      billStore.totalIncome = 0.0;
      billStore.totalPay = 0.0;
    }
  }

  @override
  void deactivate() {
    super.deactivate();
    print("dectivated w w w");
    bool current = ModalRoute.of(context).isCurrent;
    if (current) {
      initPage();
    }
  }

  void _toLogin(context) {
    AppRouter.toPage(context, 'record');
  }

  void deleteFunc(int val) async {
    print("deletebill");
    print(val);
    await billStore.deleteBill(val);
    await initPage();
  }

  void _toLimitset(context) {
    AppRouter.toPage(context, 'limit-set');
  }

  Widget _buildBills() {
    if (billStore.homeBills == null || billStore.homeBills.length == 0) {
      return Empty(
          top: Adaptor.px(200.0),
          child: userStore.logined
              ? Wrap(
                  children: <Widget>[
                    Text('暂无月度账单数据,',
                        style: TextStyle(
                            fontSize: Adaptor.px(24.0),
                            color: AppColors.appTextLight)),
                    Text('快去',
                        style: TextStyle(
                            fontSize: Adaptor.px(24.0),
                            color: AppColors.appTextLight)),
                    Text('吧~',
                        style: TextStyle(
                            fontSize: Adaptor.px(24.0),
                            color: AppColors.appTextLight)),
                  ],
                )
              : Wrap(
                  children: <Widget>[
                    Text('暂未登录,',
                        style: TextStyle(
                            fontSize: Adaptor.px(24.0),
                            color: AppColors.appTextLight)),
                    Text('快去',
                        style: TextStyle(
                            fontSize: Adaptor.px(24.0),
                            color: AppColors.appTextLight)),
                    GestureDetector(
                      onTap: () => _toLogin(context),
                      child: Text('登录/注册',
                          style: TextStyle(
                              fontSize: Adaptor.px(24.0),
                              color: Colours.normalBlue)),
                    ),
                    Text('吧~',
                        style: TextStyle(
                            fontSize: Adaptor.px(24.0),
                            color: AppColors.appTextLight)),
                  ],
                ));
    }
    return Container(
        child: Expanded(
            child: ListView.builder(
                itemCount: billStore.homeBills.length,
                itemBuilder: (BuildContext context, int index) {
                  BillItem _currentBill = billStore.homeBills[index];
                  return Container(
                      child: Container(
                          margin: EdgeInsets.only(
                              left: Adaptor.px(6.0),
                              right: Adaptor.px(6.0),
                              bottom: Adaptor.px(10.0),
                              top: Adaptor.onePx()),
                          padding: EdgeInsets.all(Adaptor.px(16.0)),
                          decoration: BoxDecoration(
                              color: AppColors.appWhite,
                              borderRadius:
                                  BorderRadius.all(Radius.circular(5.0)),
                              boxShadow: [
                                BoxShadow(
                                    color: AppColors.appBlackShadow,
                                    blurRadius: 5.0,
                                    offset: Offset(0, 1.0))
                              ]),
                          child: Wrap(children: <Widget>[
                            Wrap(children: <Widget>[
                              Container(
                                  padding: EdgeInsets.only(
                                      top: Adaptor.px(16.0),
                                      bottom: Adaptor.px(16.0)),
                                  decoration: BoxDecoration(
                                      border: Border(
                                    bottom: BorderSide(
                                        width: Adaptor.onePx(),
                                        color: AppColors.appBorder),
                                  )),
                                  child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.spaceBetween,
                                      children: <Widget>[
                                        Text('${_currentBill.billDate}',
                                            style: TextStyle(
                                                fontSize: Adaptor.px(28.0),
                                                color:
                                                    AppColors.appTextNormal)),
                                        Wrap(
                                          children: <Widget>[
                                            Text('支出${_currentBill.totalPay}',
                                                style: TextStyle(
                                                    fontSize: Adaptor.px(28.0),
                                                    color:
                                                        AppColors.appOutlay)),
                                            Padding(
                                              padding: EdgeInsets.only(
                                                  left: Adaptor.px(20.0)),
                                              child: Text(
                                                  '收入${_currentBill.totalIncome}',
                                                  style: TextStyle(
                                                      fontSize:
                                                          Adaptor.px(28.0),
                                                      color:
                                                          AppColors.appIncome)),
                                            )
                                          ],
                                        ),
                                      ])),
                              ...List.generate(_currentBill.bills.length,
                                  (int billIndex) {
                                BillRow _billRow =
                                    _currentBill.bills[billIndex];
                                bool _isIncome = _billRow.billType == '1';
                                IconItem _rowType;

                                if (_isIncome) {
                                  _rowType = MethodsIcons
                                      .incomeIconMaps[_billRow.category];
                                } else {
                                  _rowType = MethodsIcons
                                      .paymentIconMaps[_billRow.category];
                                }
                                return PopupMenu(
                                    callback: (int val) => {
                                          BotToast.showAnimationWidget(
                                            onlyOne: true,
                                            backgroundColor: Colors.black26,
                                            animationDuration:
                                                Duration(milliseconds: 300),
                                            toastBuilder: (cancelFunc) =>
                                                AlertDialog(
                                              title: Text('您确定要删除此条账单吗?',
                                                  style: TextStyle(
                                                      fontSize:
                                                          Adaptor.px(32.0),
                                                      color: AppColors
                                                          .appTextDark)),
                                              actions: <Widget>[
                                                FlatButton(
                                                  onPressed: () {
                                                    cancelFunc();
                                                  },
                                                  highlightColor:
                                                      const Color(0x55FF8A80),
                                                  splashColor:
                                                      const Color(0x99FF8A80),
                                                  child: const Text(
                                                    '取消',
                                                    style: TextStyle(
                                                        color: AppColors
                                                            .appWarning),
                                                  ),
                                                ),
                                                FlatButton(
                                                  onPressed: () {
                                                    deleteFunc(val);
                                                    initPage();
                                                    cancelFunc();
                                                  },
                                                  child: const Text(
                                                    '确定',
                                                    style: TextStyle(
                                                        color: AppColors
                                                            .appDanger),
                                                  ),
                                                ),
                                              ],
                                            ),
                                          )
                                        },
                                    menus: [
                                      PopupMenuItem(
                                        value: _billRow.id,
                                        child: Text('删除',
                                            style: TextStyle(
                                                fontSize: Adaptor.px(28.0),
                                                color: AppColors.appDanger)),
                                      )
                                    ],
                                    child: Container(
                                        padding: EdgeInsets.only(
                                            top: Adaptor.px(20.0),
                                            bottom: Adaptor.px(20.0)),
                                        decoration: BoxDecoration(
                                            border: Border(
                                          bottom: BorderSide(
                                              width: Adaptor.onePx(),
                                              color: AppColors.appBorder),
                                        )),
                                        child: Row(
                                            mainAxisAlignment:
                                                MainAxisAlignment.spaceBetween,
                                            crossAxisAlignment:
                                                CrossAxisAlignment.center,
                                            children: <Widget>[
                                              Column(children: <Widget>[
                                                Row(
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .center,
                                                    children: <Widget>[
                                                      Container(
                                                        width: Adaptor.px(80.0),
                                                        height:
                                                            Adaptor.px(80.0),
                                                        decoration: BoxDecoration(
                                                            color: _billRow
                                                                        .billType ==
                                                                    "0"
                                                                ? Colours
                                                                    .normalBlue
                                                                : AppColors
                                                                    .appGreen,
                                                            borderRadius: BorderRadius.all(
                                                                Radius.circular(
                                                                    Adaptor.px(
                                                                        40.0)))),
                                                        child: Center(
                                                            child: Icon(
                                                                _rowType.icon,
                                                                color: AppColors
                                                                    .appWhite,
                                                                size:
                                                                    Adaptor.px(
                                                                        50.0))),
                                                      ),
                                                      Padding(
                                                          padding:
                                                              EdgeInsets.only(
                                                                  left: Adaptor
                                                                      .px(
                                                                          20.0)),
                                                          child: Column(
                                                              mainAxisAlignment:
                                                                  MainAxisAlignment
                                                                      .spaceBetween,
                                                              crossAxisAlignment:
                                                                  CrossAxisAlignment
                                                                      .start,
                                                              children: <
                                                                  Widget>[
                                                                Text(
                                                                    _rowType
                                                                        .desc,
                                                                    style: TextStyle(
                                                                        fontSize:
                                                                            Adaptor.px(
                                                                                30.0),
                                                                        color: AppColors
                                                                            .appTextDark)),
                                                                Text(
                                                                    _billRow.remark ??
                                                                        '',
                                                                    style: TextStyle(
                                                                        fontSize:
                                                                            Adaptor.px(
                                                                                24.0),
                                                                        color: AppColors
                                                                            .appTextDark))
                                                              ]))
                                                    ])
                                              ]),
                                              Row(
                                                crossAxisAlignment:
                                                    CrossAxisAlignment.center,
                                                children: <Widget>[
                                                  Text(_billRow.amount,
                                                      style: TextStyle(
                                                          fontSize:
                                                              Adaptor.px(36.0),
                                                          color: _isIncome
                                                              ? AppColors
                                                                  .appIncome
                                                              : AppColors
                                                                  .appOutlay))
                                                ],
                                              )
                                            ])));
                              }).toList()
                            ])
                          ])));
                })));
  }

  Widget _buildProgress() {
    if (statisticsStore.compared == null) {
      return SizedBox.shrink();
    }

    bool _isSetted = statisticsStore.compared.limitSetted;
    double _percent = statisticsStore.compared.percent;

    Color _lineColor;

    if (_isSetted) {
      if (_percent <= 0.2) {
        _lineColor = AppColors.appSufficient;
      } else if (_percent <= 0.6) {
        _lineColor = AppColors.appRegular;
      } else if (_percent <= 0.8) {
        _lineColor = AppColors.appWarning;
      } else if (_percent > 0.8) {
        _lineColor = AppColors.appDanger;
      }
    }

    return Container(
        margin: EdgeInsets.only(top: Adaptor.px(20.0)),
        width: Adaptor.px(1020),
        padding: EdgeInsets.all(Adaptor.px(20.0)),
        decoration: BoxDecoration(
            color: AppColors.appWhite,
            borderRadius: const BorderRadius.all(Radius.circular(5.0)),
            boxShadow: [
              BoxShadow(
                  color: AppColors.appBlackShadow,
                  blurRadius: 5.0,
                  offset: Offset(0, 1.0))
            ]),
        child: Wrap(children: <Widget>[
          Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Text(
                  '本月消费',
                  style: TextStyle(
                      color: AppColors.appTextDark, fontSize: Adaptor.px(28.0)),
                ),
                GestureDetector(
                  onTap: () => _toLimitset(context),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Icon(
                        IconFont.iconSettings,
                        size: Adaptor.px(26.0),
                        color: AppColors.appTextDark,
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: Adaptor.px(4.0)),
                        child: Text('设置预算',
                            style: TextStyle(
                                color: AppColors.appTextDark,
                                fontSize: Adaptor.px(28.0))),
                      )
                    ],
                  ),
                )
              ]),
          Container(
            margin: const EdgeInsets.only(top: 6.0, bottom: 6.0),
            child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Text('￥${statisticsStore.compared.totalPay}',
                      style: TextStyle(
                          color: AppColors.appTextDark,
                          fontSize: Adaptor.px(28.0))),
                  Text('￥${statisticsStore.compared.limit}',
                      style: TextStyle(
                          color: AppColors.appTextDark,
                          fontSize: Adaptor.px(28.0))),
                ]),
          ),
          _isSetted
              ? LinearPercentIndicator(
                  lineHeight: Adaptor.px(28.0),
                  percent: _percent > 1 ? 1 : _percent,
                  animation: true,
                  center: Text(
                    '${(_percent * 100).toStringAsFixed(2)}%',
                    style: TextStyle(
                        fontSize: Adaptor.px(20.0),
                        color: AppColors.appTextDark),
                  ),
                  linearStrokeCap: LinearStrokeCap.roundAll,
                  backgroundColor: Colors.transparent,
                  progressColor: _lineColor,
                )
              : SizedBox.shrink()
        ]));
  }

  Widget _buildIncomePayment() {
    double totalPay = billStore.totalPay;
    double totalIncome = billStore.totalIncome;

    if (statisticsStore.compared != null) {
      totalPay = statisticsStore.compared.totalPay;
      // totalPayCompare = statisticsStore.compared.totalPayCompare;
      totalIncome = statisticsStore.compared.totalIncome;
      // totalIncomeCompare = statisticsStore.compared.totalIncomeCompare;
    }

    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Expanded(
          flex: 1,
          child: Container(
              height: Adaptor.px(180.0),
              margin: EdgeInsets.only(
                  left: Adaptor.px(8.0), right: Adaptor.px(8.0)),
              decoration: BoxDecoration(
                  borderRadius: const BorderRadius.all(Radius.circular(5.0)),
                  boxShadow: [
                    BoxShadow(
                        color: Colours.normalBlueShadow,
                        blurRadius: 8.0,
                        offset: Offset(2.0, 1.0))
                  ],
                  gradient: LinearGradient(
                      begin: Alignment.bottomLeft,
                      end: Alignment.topRight,
                      colors: [Colours.normalBlue, Colours.normalBlueLight])),
              child: Container(
                margin: const EdgeInsets.only(left: 20.0),
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text(
                        '总支出',
                        style: TextStyle(
                          color: AppColors.appWhite,
                          fontSize: Adaptor.px(26.0),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(
                            top: Adaptor.px(6.0), bottom: Adaptor.px(6.0)),
                        child: Text('${totalPay}',
                            style: TextStyle(
                                color: AppColors.appWhite,
                                fontSize: Adaptor.px(34.0),
                                fontWeight: FontWeight.w500)),
                      ),
                    ]),
              )),
        ),
        Expanded(
            flex: 1,
            child: Container(
                height: Adaptor.px(180.0),
                margin: EdgeInsets.only(
                    left: Adaptor.px(8.0), right: Adaptor.px(8.0)),
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.all(Radius.circular(5.0)),
                    boxShadow: [
                      BoxShadow(
                          color: AppColors.appGreenShadow,
                          blurRadius: 8.0,
                          offset: Offset(2.0, 1.0))
                    ],
                    gradient: const LinearGradient(
                        begin: Alignment.bottomLeft,
                        end: Alignment.topRight,
                        colors: [AppColors.appGreen, AppColors.appGreenLight])),
                child: Container(
                  margin: const EdgeInsets.only(left: 20.0),
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Text('总收入',
                            style: TextStyle(
                                color: AppColors.appWhite,
                                fontSize: Adaptor.px(26.0))),
                        Padding(
                          padding: EdgeInsets.only(
                              top: Adaptor.px(6.0), bottom: Adaptor.px(6.0)),
                          child: Text('${totalIncome}',
                              style: TextStyle(
                                  color: AppColors.appWhite,
                                  fontSize: Adaptor.px(34.0),
                                  fontWeight: FontWeight.w500)),
                        ),
                      ]),
                )))
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text('首页', style: TextStyle(fontSize: 16.0))),
        body: Observer(builder: (_) {
          return Container(
            padding: EdgeInsets.all(Adaptor.px(16.0)),
            child: Column(children: <Widget>[
              _buildIncomePayment(),
              _buildProgress(),
              _buildBills()
            ]),
          );
        }));
  }
}
