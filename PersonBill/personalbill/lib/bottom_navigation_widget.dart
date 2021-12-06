import 'dart:ui';

import 'package:bill/adaptor.dart';
import 'package:bill/event.dart';
import 'package:bill/iconfont.dart';
import 'package:bill/pages/tab/analysis/analysis.dart';
import 'package:bill/pages/tab/index.dart';
import 'package:bill/pages/tab/mine.dart';
import 'package:bill/pages/tab/wealth.dart';
import 'package:bill/router.dart';
import 'package:flutter/material.dart';

import 'widgets/HighLightWell.dart';
import 'widgets/colours.dart';

class BottomNavigationWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => BottomNavigationWidgetState();
}

class BottomNavigationWidgetState extends State<BottomNavigationWidget> {
  int _currentIndex = 0;
  List<Widget> list = List();

  @override
  void initState() {
    list
      ..add(IndexPage())
      ..add(AnalysisPage())
      ..add(WealthPage())
      ..add(MinePage());

    AppEvent.on('switchIndex', (dynamic index) {
      setState(() {
        _currentIndex = index;
      });
    });

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: list[_currentIndex],
        bottomNavigationBar: BottomAppBar(
          child: Row(
            mainAxisSize: MainAxisSize.max,
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
              _buildBottomItem(0, '首页', Icons.description),
              _buildBottomItem(1, '统计分析', Icons.analytics),
              _buildBottomItem(-1, '记账', null),
              _buildBottomItem(2, '资产分析', Icons.attach_money_rounded),
              _buildBottomItem(3, '个人中心', Icons.person)
            ],
          ),
        ));
  }

  void _toRecord(context) {
    AppRouter.toPage(context, 'record');
  }

  _buildBottomItem(int index, String title, IconData data) {
    //未选中样式
    TextStyle textStyle = TextStyle(fontSize: 12.0, color: Colours.gray);
    TextStyle selectedTextStyle =
        TextStyle(fontSize: 12.0, color: Colours.black);
    Color iconColor = Colours.gray;
    Color selectedIconColor = Colours.black;
    double iconSize = 25;

    return data != null
        ? Expanded(
            flex: 1,
            child: HighLightWell(
              isPressingEffect: false,
              onTap: () {
                setState(() {
                  _currentIndex = index;
                });
              },
              child: Container(
                height: 49,
                padding: const EdgeInsets.only(top: 5.5),
                child: Column(
                  children: <Widget>[
                    Icon(
                      data,
                      size: iconSize,
                      color: _currentIndex == index
                          ? selectedIconColor
                          : iconColor,
                    ),
                    Text(
                      title,
                      style: _currentIndex == index
                          ? selectedTextStyle
                          : textStyle,
                    )
                  ],
                ),
              ),
            ),
          )
        : Expanded(
            flex: 1,
            child: Container(
              height: 49,
              child: OverflowBox(
                minHeight: 49,
                maxHeight: 80,
                child: HighLightWell(
                  onTap: () {
                    print(index);
                    setState(() {
                      _currentIndex = index;
                    });
                  },
                  isPressingEffect: false,
                  child: Stack(
                    alignment: Alignment.center,
                    children: <Widget>[
                      Positioned(
                        top: 0,
                        child: Container(
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(48 / 2),
                            border: Border.all(width: 2, color: Colors.white),
                            boxShadow: [
                              BoxShadow(
                                  color: Colours.line,
                                  blurRadius: 0,
                                  offset: Offset(0, -1)),
                            ],
                          ),
                          child: HighLightWell(
                            onTap: () {
                              if (index == -1) {
                                // onTap(index);
                                _toRecord(context);
                              } else {
                                setState(() {
                                  _currentIndex = index;
                                });
                              }
                            },
                            isForeground: true,
                            borderRadius: BorderRadius.circular(48 / 2),
                            child: SizedBox(
                              width: 44,
                              height: 44,
                              child: CircleAvatar(
                                backgroundColor: Colours.app_main,
                                child:
                                    const Icon(Icons.add, color: Colors.white),
                              ),
                            ),
                          ),
                        ),
                      ),
                      Positioned(
                        bottom: 17,
                        child: Text(
                          title,
                          style: textStyle,
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
          );
  }
}
