import 'package:bill/adaptor.dart';
import 'package:bill/bean/group.dart';
import 'package:bill/colors.dart';
import 'package:bill/widgets/colours.dart';
import 'package:bill/methods-icons.dart';
import 'package:bill/router.dart';
import 'package:bill/stores/group.dart';
import 'package:bill/stores/stores.dart';
import 'package:bill/widgets/empty.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';

class GroupsPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => GroupsState();
}

class GroupsState extends State<GroupsPage> {
  final GroupStore groupStore = AppStores.groupStote;

  @override
  void initState() {
    super.initState();
    groupStore.queryGroups();
  }

  void _createGroup() {
    AppRouter.toPage(context, 'create-group');
  }

  void _toDetail(int id) {
    AppRouter.toPage(context, 'group-detail?id=${id}');
  }

  void _share() {}

  @override
  void deactivate() {
    super.deactivate();
    bool current = ModalRoute.of(context).isCurrent;
    if (current) {
      groupStore.queryGroups();
    }
  }

  Widget _buildGroupIcon(GroupItem group) {
    if (group.usage == '0') {
      return Container(
        width: Adaptor.px(100.0),
        height: Adaptor.px(100.0),
        margin: EdgeInsets.only(right: Adaptor.px(18.0)),
        decoration: BoxDecoration(
            color: Colours.normalBlue,
            borderRadius: BorderRadius.all(Radius.circular(Adaptor.px(80.0)))),
        child: Center(
            child: Text(group.name.substring(0, 1),
                style: TextStyle(
                    fontSize: Adaptor.px(50.0), color: AppColors.appWhite))),
      );
    }

    IconItem _groupItem = MethodsIcons.circleTypeMaps[group.type];
    return Container(
        width: Adaptor.px(100.0),
        height: Adaptor.px(100.0),
        margin: EdgeInsets.only(right: Adaptor.px(18.0)),
        decoration: BoxDecoration(
            color: Colours.normalBlue,
            borderRadius: BorderRadius.all(Radius.circular(Adaptor.px(80.0)))),
        child: Icon(
          _groupItem.icon,
          size: Adaptor.px(50.0),
          color: AppColors.appWhite,
        ));
  }

  Widget _buildGroups() {
    return Observer(
        builder: (_) => Container(
            width: Adaptor.screenW(),
            child: (groupStore.groups != null && groupStore.groups.length > 0)
                ? SingleChildScrollView(
                    child: Container(
                      margin: EdgeInsets.only(
                          top: Adaptor.px(10.0),
                          left: Adaptor.px(10.0),
                          right: Adaptor.px(10.0),
                          bottom: Adaptor.px(100)),
                      child: Wrap(
                          children:
                              List.generate(groupStore.groups.length, (int i) {
                        GroupItem _group = groupStore.groups[i];
                        IconItem groupItem =
                            MethodsIcons.circleTypeMaps[_group.type];

                        return GestureDetector(
                            onTap: () => _toDetail(_group.id),
                            child: Container(
                                width: Adaptor.px(1040.0),
                                margin: EdgeInsets.only(
                                    top: Adaptor.px(10.0),
                                    left: Adaptor.px(10.0),
                                    right: Adaptor.px(10.0),
                                    bottom: Adaptor.px(10.0)),
                                padding: EdgeInsets.all(Adaptor.px(16.0)),
                                decoration: BoxDecoration(
                                    color: AppColors.appWhite,
                                    borderRadius: const BorderRadius.all(
                                        Radius.circular(5.0)),
                                    boxShadow: [
                                      BoxShadow(
                                          color: AppColors.appBlackShadow,
                                          blurRadius: 5.0,
                                          offset: Offset(0, 1.0))
                                    ]),
                                child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    Row(
                                      children: <Widget>[
                                        _buildGroupIcon(_group),
                                        Column(
                                            crossAxisAlignment:
                                                CrossAxisAlignment.start,
                                            children: <Widget>[
                                              Text(
                                                  _group.usage == '0'
                                                      ? '????????????'
                                                      : groupItem.desc,
                                                  style: TextStyle(
                                                      fontSize:
                                                          Adaptor.px(32.0),
                                                      color: AppColors
                                                          .appTextDark)),
                                              Padding(
                                                  padding: EdgeInsets.only(
                                                      top: Adaptor.px(0)),
                                                  child: Text(_group.name,
                                                      style: TextStyle(
                                                          fontSize:
                                                              Adaptor.px(28.0),
                                                          color: AppColors
                                                              .appTextNormal)))
                                            ])
                                      ],
                                    )
                                  ],
                                )));
                      }).toList()),
                    ),
                  )
                : Empty()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            title: Text('????????????',
                style: TextStyle(
                    fontSize: Adaptor.px(32.0), color: AppColors.appTextDark))),
        body: Container(
            height: double.infinity,
            child: Stack(children: <Widget>[
              _buildGroups(),
              Positioned(
                  left: 0,
                  bottom: 0,
                  right: 0,
                  child: GestureDetector(
                      onTap: _createGroup,
                      child: Container(
                          height: Adaptor.px(80.0),
                          color: Colours.normalBlue,
                          child: Center(
                              child: Text('??????????????????',
                                  style: TextStyle(
                                      fontSize: Adaptor.px(28.0),
                                      color: AppColors.appWhite))))))
            ])));
  }
}
