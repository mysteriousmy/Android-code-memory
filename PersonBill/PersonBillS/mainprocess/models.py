from django.db import models

class user(models.Model):
    id = models.AutoField(primary_key=True)
    token = models.TextField()
    avatar = models.TextField()
    nickname = models.CharField(max_length=20)
    lastLogin = models.CharField(max_length=250)
    lastDevice = models.CharField(max_length=250)
    mobile = models.CharField(max_length=18)
    registrationId = models.CharField(max_length=250)

class bill(models.Model):
    id = models.AutoField(primary_key=True)
    category = models.TextField()
    amount = models.TextField()
    billDate = models.TextField()
    recordType = models.TextField(null=True)
    payMethod = models.TextField()
    billType = models.TextField()
    remark = models.TextField(null=True)
    buid = models.IntegerField(null=False)

class limit(models.Model):
    limit = models.IntegerField()
    limitUserID = models.IntegerField(null=False)
class reminder(models.Model):
    id = models.AutoField(primary_key=True);
    frequency = models.TextField()
    time = models.TextField()
    rule = models.TextField()
    back = models.IntegerField()
    ruid = models.IntegerField(null=False)

class task(models.Model):
    id = models.AutoField(primary_key=True) 
    frequency = models.TextField()
    time= models.TextField()
    amount= models.TextField()
    billType= models.TextField()
    category= models.TextField()
    remark= models.TextField()
    confirm= models.TextField()
    payMethod= models.TextField()
    taskUserId = models.IntegerField(null=False)

class UserBillCount():
    days = 0
    counts = 0
    def __init__(self, days, counts):
        self.days = days
        self.counts = counts

class BillItem():
    billDate = "";
    totalPay = "";
    totalIncome = "";
    bills = [];
    def __init__(self, billDate,totalPay,totalIncome,bills):
        self.billDate = billDate
        self.totalPay = totalPay
        self.totalIncome = totalIncome
        self.bills = bills

class compareLastMonth():
    limitSetted = False
    limit = 0
    percent = 0.00
    totalPay = 0.00
    totalIncome = 0.00
    totalPayCompare = 0.00
    totalIncomeCompare = 0.00
    def __init__(self,percent,totalPay,totalIncome,totalPayCompare,totalIncomeCompare):
        self.percent = percent
        self.totalPay = totalPay 
        self.totalIncome = totalIncome 
        self.totalPayCompare = totalPayCompare 
        self.totalIncomeCompare = totalIncomeCompare
 
class YearlyAmount():
    totalPay = 0.00
    totalIncome = 0.00
    totalRemain = 0.00
    def __init__(self,totalPay,totalIncome,totalRemain):
        self.totalPay = totalPay
        self.totalIncome = totalIncome
        self.totalRemain = totalRemain
 
class YearlyAnalyze():
    yearlyAmounts = {}
    monthlyAmounts = []
    def __init__(self,yearlyAmounts,monthlyAmounts):
        self.yearlyAmounts = yearlyAmounts
        self.monthlyAmounts = monthlyAmounts
class MonthliAmount():
    billMonth = ""
    totalPay = 0.00
    totalIncome = 0.00
    totalRemain = 0.00
    def __init__(self,billMonth,totalPay,totalIncome,totalRemain):
        self.billMonth = billMonth
        self.totalPay = totalPay
        self.totalIncome = totalIncome
        self.totalRemain = totalRemain

class BillAmountsRow():
    totalAmount = 0.00;
    category = ""
    def __init__(self,totalAmount,category):
        self.totalAmount = totalAmount
        self.category = category

class DayAmountsRow():
    totalAmount = 0.00
    billDate = ""
    def __init__(self,totalAmount,billDate):
        self.totalAmount = totalAmount
        self.billDate = billDate

class JsonResultFormat():
    __slots__ = ('success', 'message', 'data')

    def __init__(self, success, message, data):
        self.success = success
        self.message = message
        self.data = data

    def obj2dict(self):
        return {
            'success': self.success,
            'message': self.message,
            'data': self.data
        }

