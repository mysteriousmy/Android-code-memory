// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$UserStore on _UserStore, Store {
  final _$loginedAtom = Atom(name: '_UserStore.logined');

  @override
  bool get logined {
    _$loginedAtom.context.enforceReadPolicy(_$loginedAtom);
    _$loginedAtom.reportObserved();
    return super.logined;
  }

  @override
  set logined(bool value) {
    _$loginedAtom.context.conditionallyRunInAction(() {
      super.logined = value;
      _$loginedAtom.reportChanged();
    }, _$loginedAtom, name: '${_$loginedAtom.name}_set');
  }

  final _$userInfoAtom = Atom(name: '_UserStore.userInfo');

  @override
  User get userInfo {
    _$userInfoAtom.context.enforceReadPolicy(_$userInfoAtom);
    _$userInfoAtom.reportObserved();
    return super.userInfo;
  }

  @override
  set userInfo(User value) {
    _$userInfoAtom.context.conditionallyRunInAction(() {
      super.userInfo = value;
      _$userInfoAtom.reportChanged();
    }, _$userInfoAtom, name: '${_$userInfoAtom.name}_set');
  }

  final _$billCountAtom = Atom(name: '_UserStore.billCount');

  @override
  UserBillCount get billCount {
    _$billCountAtom.context.enforceReadPolicy(_$billCountAtom);
    _$billCountAtom.reportObserved();
    return super.billCount;
  }

  @override
  set billCount(UserBillCount value) {
    _$billCountAtom.context.conditionallyRunInAction(() {
      super.billCount = value;
      _$billCountAtom.reportChanged();
    }, _$billCountAtom, name: '${_$billCountAtom.name}_set');
  }

  final _$ensureLoginAsyncAction = AsyncAction('ensureLogin');

  @override
  Future<bool> ensureLogin() {
    return _$ensureLoginAsyncAction.run(() => super.ensureLogin());
  }

  final _$loginAsyncAction = AsyncAction('login');

  @override
  Future<bool> login(String mobile, String password, [String device]) {
    return _$loginAsyncAction.run(() => super.login(mobile, password, device));
  }

  final _$validateCodeAsyncAction = AsyncAction('validateCode');

  @override
  Future<bool> validateCode(String mobile, String code) {
    return _$validateCodeAsyncAction
        .run(() => super.validateCode(mobile, code));
  }

  final _$registerLoginAsyncAction = AsyncAction('registerLogin');

  @override
  Future<bool> registerLogin(String mobile, String password, String rid,
      [String device]) {
    return _$registerLoginAsyncAction
        .run(() => super.registerLogin(mobile, password, rid, device));
  }

  final _$forgotAsyncAction = AsyncAction('forgot');

  @override
  Future<bool> forgot(String mobile, String password) {
    return _$forgotAsyncAction.run(() => super.forgot(mobile, password));
  }

  final _$getBillCountAsyncAction = AsyncAction('getBillCount');

  @override
  Future<bool> getBillCount() {
    return _$getBillCountAsyncAction.run(() => super.getBillCount());
  }

  final _$_UserStoreActionController = ActionController(name: '_UserStore');

  @override
  bool logout() {
    final _$actionInfo = _$_UserStoreActionController.startAction();
    try {
      return super.logout();
    } finally {
      _$_UserStoreActionController.endAction(_$actionInfo);
    }
  }
}
