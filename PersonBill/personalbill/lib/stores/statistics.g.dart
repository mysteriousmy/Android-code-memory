// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'statistics.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$StatisticsStore on _StatisticsStore, Store {
  final _$comparedAtom = Atom(name: '_StatisticsStore.compared');

  @override
  CompareLastMonth get compared {
    _$comparedAtom.context.enforceReadPolicy(_$comparedAtom);
    _$comparedAtom.reportObserved();
    return super.compared;
  }

  @override
  set compared(CompareLastMonth value) {
    _$comparedAtom.context.conditionallyRunInAction(() {
      super.compared = value;
      _$comparedAtom.reportChanged();
    }, _$comparedAtom, name: '${_$comparedAtom.name}_set');
  }

  final _$yearsAtom = Atom(name: '_StatisticsStore.years');

  @override
  List<String> get years {
    _$yearsAtom.context.enforceReadPolicy(_$yearsAtom);
    _$yearsAtom.reportObserved();
    return super.years;
  }

  @override
  set years(List<String> value) {
    _$yearsAtom.context.conditionallyRunInAction(() {
      super.years = value;
      _$yearsAtom.reportChanged();
    }, _$yearsAtom, name: '${_$yearsAtom.name}_set');
  }

  final _$yearlyAnalyzeAtom = Atom(name: '_StatisticsStore.yearlyAnalyze');

  @override
  YearlyAnalyze get yearlyAnalyze {
    _$yearlyAnalyzeAtom.context.enforceReadPolicy(_$yearlyAnalyzeAtom);
    _$yearlyAnalyzeAtom.reportObserved();
    return super.yearlyAnalyze;
  }

  @override
  set yearlyAnalyze(YearlyAnalyze value) {
    _$yearlyAnalyzeAtom.context.conditionallyRunInAction(() {
      super.yearlyAnalyze = value;
      _$yearlyAnalyzeAtom.reportChanged();
    }, _$yearlyAnalyzeAtom, name: '${_$yearlyAnalyzeAtom.name}_set');
  }

  final _$loginedAtom = Atom(name: '_StatisticsStore.logined');

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

  final _$monthAnalysisAtom = Atom(name: '_StatisticsStore.monthAnalysis');

  @override
  MonthAnalysis get monthAnalysis {
    _$monthAnalysisAtom.context.enforceReadPolicy(_$monthAnalysisAtom);
    _$monthAnalysisAtom.reportObserved();
    return super.monthAnalysis;
  }

  @override
  set monthAnalysis(MonthAnalysis value) {
    _$monthAnalysisAtom.context.conditionallyRunInAction(() {
      super.monthAnalysis = value;
      _$monthAnalysisAtom.reportChanged();
    }, _$monthAnalysisAtom, name: '${_$monthAnalysisAtom.name}_set');
  }

  final _$compareLastAsyncAction = AsyncAction('compareLast');

  @override
  Future<void> compareLast() {
    return _$compareLastAsyncAction.run(() => super.compareLast());
  }

  final _$getBillYearsAsyncAction = AsyncAction('getBillYears');

  @override
  Future<void> getBillYears() {
    return _$getBillYearsAsyncAction.run(() => super.getBillYears());
  }

  final _$getYearlyBillsAsyncAction = AsyncAction('getYearlyBills');

  @override
  Future<void> getYearlyBills(Map<String, dynamic> param) {
    return _$getYearlyBillsAsyncAction.run(() => super.getYearlyBills(param));
  }

  final _$getMonthAnalysisAsyncAction = AsyncAction('getMonthAnalysis');

  @override
  Future<bool> getMonthAnalysis() {
    return _$getMonthAnalysisAsyncAction.run(() => super.getMonthAnalysis());
  }
}
