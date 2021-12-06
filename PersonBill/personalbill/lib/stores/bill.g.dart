// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'bill.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$BillStore on _BillStore, Store {
  final _$homeBillsAtom = Atom(name: '_BillStore.homeBills');

  @override
  List<BillItem> get homeBills {
    _$homeBillsAtom.context.enforceReadPolicy(_$homeBillsAtom);
    _$homeBillsAtom.reportObserved();
    return super.homeBills;
  }

  @override
  set homeBills(List<BillItem> value) {
    _$homeBillsAtom.context.conditionallyRunInAction(() {
      super.homeBills = value;
      _$homeBillsAtom.reportChanged();
    }, _$homeBillsAtom, name: '${_$homeBillsAtom.name}_set');
  }

  final _$totalPayAtom = Atom(name: '_BillStore.totalPay');

  @override
  double get totalPay {
    _$totalPayAtom.context.enforceReadPolicy(_$totalPayAtom);
    _$totalPayAtom.reportObserved();
    return super.totalPay;
  }

  @override
  set totalPay(double value) {
    _$totalPayAtom.context.conditionallyRunInAction(() {
      super.totalPay = value;
      _$totalPayAtom.reportChanged();
    }, _$totalPayAtom, name: '${_$totalPayAtom.name}_set');
  }

  final _$totalIncomeAtom = Atom(name: '_BillStore.totalIncome');

  @override
  double get totalIncome {
    _$totalIncomeAtom.context.enforceReadPolicy(_$totalIncomeAtom);
    _$totalIncomeAtom.reportObserved();
    return super.totalIncome;
  }

  @override
  set totalIncome(double value) {
    _$totalIncomeAtom.context.conditionallyRunInAction(() {
      super.totalIncome = value;
      _$totalIncomeAtom.reportChanged();
    }, _$totalIncomeAtom, name: '${_$totalIncomeAtom.name}_set');
  }

  final _$createBillAsyncAction = AsyncAction('createBill');

  @override
  Future<bool> createBill(Map<String, dynamic> bill) {
    return _$createBillAsyncAction.run(() => super.createBill(bill));
  }

  final _$getMonthBillsAsyncAction = AsyncAction('getMonthBills');

  @override
  Future<void> getMonthBills(Map<String, dynamic> param) {
    return _$getMonthBillsAsyncAction.run(() => super.getMonthBills(param));
  }
}
