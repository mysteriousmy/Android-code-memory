
import datetime
import json
from django.conf import settings
import jwt
from mainprocess.utils.jwt_auth import get_token
from mainprocess import models
from mainprocess.models import BillItem, JsonResultFormat, MonthliAmount, UserBillCount, YearlyAmount, YearlyAnalyze, user
from mainprocess.checkcode import create_validate_code
from django.http.response import HttpResponse, JsonResponse
from rest_framework.views import APIView
from io import BytesIO
from mainprocess.extensions.auth import JwtQuertParamsAuthentication
checkcode = ""


class LoginView(APIView):
    '''用户登录'''
    def post(self, request, *args, **kwargs):
        info = request.data
        mobilehao = info['mobile']
        passwork = info['password']
        print(mobilehao,passwork)
        try:
            user_obj = user.objects.get(mobile=mobilehao)
        except:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        # if not user_obj.exists():
        #     return Response({"code": 1000, 'error': '不存在用户'})
        userinfo = user_obj.__dict__
        userinfo.pop('_state')
        token = user_obj.token
        decodet = jwt.decode(token,key=settings.SECRET_KEY,verify=False,algorithms=['HS256'])
        if decodet['password'] != passwork:
            return JsonResponse(AllRes(False,"密码错误!",""))
        return JsonResponse(AllRes(True,"登录成功!",userinfo))
class EnsureLogin(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            user_obj = user.objects.get(mobile=userInfo['mobile'],token=userInfo['token'])
            user_obj = user_obj.__dict__
            user_obj.pop('_state')
            return JsonResponse(AllRes(True,"自动登录成功!",user_obj))
        except Exception as ex:
            print(ex)
            return JsonResponse(AllRes(False,"token失效,请重新登录!",""))
def createCode(request):
    f = BytesIO()
    img, code = create_validate_code()
    img.save(f, 'PNG')
    global checkcode
    checkcode = code
    return HttpResponse(f.getvalue())


def validateCode(request):
    info = json.loads(request.body)
    global checkcode
    print(info["code"], checkcode)
    if(info["code"].lower() == checkcode.lower()):
        return JsonResponse(JsonResultFormat(True, "成功", "").obj2dict())
    return JsonResponse(JsonResultFormat(False, "失败", "").obj2dict())


class JwtRegister(APIView):
    def post(self, request, *args, **kwargs):
        info = request.data
        token = get_token({"mobile": info['mobile'],'password': info['password'],'registrationId': info['registrationId']},9999)
        logintime = datetime.datetime.now().strftime('%Y-%m-%d')
        newUser = models.user(token=token, avatar="http://api.btstu.cn/sjtx/api.php", nickname=info['mobile'], lastLogin=logintime, lastDevice=info['device'], mobile=info['mobile'], registrationId=info['registrationId'])
        newUser.save()
        models.limit(limit=0,limitUserID=newUser.id).save()
        newUserDict = newUser.__dict__
        newUserDict.pop('_state')
        return JsonResponse(AllRes(True,"注册成功",newUserDict))
class GroupCreate(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def post(self, request, *args, **kwargs):
        info = request.data
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        print(userInfo)
        try:
            models.group(name=info['name'],type=info['type'],usage=info['usage'],isDefault=info['isDefault'],desc=info['desc'],isPersonal="1",creatorId=userInfo['id']).save()
            return JsonResponse(AllRes(True,"添加圈子成功!",""))
        except:
            return JsonResponse(AllRes(False,"发生错误,请重试",""))

# class GroupQuery(APIView):
#     authentication_classes = [JwtQuertParamsAuthentication]
#     def get(self, request, *args, **kwargs):
#         grouplist = group.objects.all().values()
#         datalist = list(grouplist)
#         return JsonResponse(AllRes(True,"获取成功",datalist))

# class GroupQueryById(APIView):
#     authentication_classes = [JwtQuertParamsAuthentication]
#     def get(self, request, params, *args, **kwargs):
#         print(params)
#         groupDetail = group.objects.get(creatorId=params)
#         groupDetail = groupDetail.__dict__
#         groupDetail.pop('_state')
#         return JsonResponse(AllRes(True,"获取成功",groupDetail))
class CreateBill(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def post(self, request, *args, **kwargs):
        billinfo = request.data
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        print(billinfo)
        try:
            if billinfo['billType'] == "0":
                models.bill(buid=userInfo['id'],category=billinfo['category'],billDate=billinfo['billDate'],amount=billinfo['amount'],payMethod=billinfo['payMethod'],remark=billinfo['remark'],billType=billinfo['billType']).save()
            else:
                models.bill(buid=userInfo['id'],category=billinfo['category'],billDate=billinfo['billDate'],amount=billinfo['amount'],remark=billinfo['remark'],billType=billinfo['billType']).save()
            return JsonResponse(AllRes(True,"创建记账单成功",""))
        except Exception as ex:
            print(ex)
            return JsonResponse(AllRes(True,"创建失败!请重试!",""))

class GetUserBillCount(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            userId = userInfo['id']
            billCount = models.bill.objects.filter(buid=userId).count()
            billDay = models.bill.objects.filter(buid=userId).values('billDate').order_by('billDate').distinct().count()
            newUserBillCount = UserBillCount(billDay,billCount)
            newUserBillCount = newUserBillCount.__dict__
            print(billCount,billDay)
            return JsonResponse(AllRes(True,"取得数据成功!",newUserBillCount))
        except Exception as ex:
            print(ex)
            return JsonResponse(AllRes(False,"发生异常,请稍候重试!",""))


class GetUserReminder(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            reminder = models.reminder.objects.filter(ruid=userInfo['id']).values()
            reminder = list(reminder)
            return JsonResponse(AllRes(True,"",reminder))
        except:
            return JsonResponse(AllRes(False,"发生异常,请稍候重试!",""))
class CreateUserReminder(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def post(self, request, *args, **kwargs):
        reminderinfo = request.data
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            models.reminder(frequency=reminderinfo['frequency'],time=reminderinfo['time'],rule=reminderinfo['rule'],back=reminderinfo['back'],ruid=userInfo['id']).save()
            return JsonResponse(AllRes(True,"创建提醒成功!",""))
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
        
class UpdateUserReminder(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def put(self, request, *args, **kwargs):
        reminderinfo = request.data
        try:
            models.reminder.objects.filter(id=reminderinfo['id']).update(frequency=reminderinfo['frequency'],time=reminderinfo['time'],rule=reminderinfo['rule'])
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
        return JsonResponse(AllRes(True,"更新提醒成功!",""))
class DeleteUserReminder(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, param, *args, **kwargs):
        try:
            models.reminder.objects.filter(id=param).delete()
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
        return JsonResponse(AllRes(True,"删除提醒成功!",""))
class QueryReminderById(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, param, *args, **kwargs):
        try:
            print(param)
            reminder = models.reminder.objects.get(id=param)
            reminder = reminder.__dict__
            print(reminder)
            reminder.pop('_state')
            return JsonResponse(AllRes(True,"",reminder))
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
class SetUserLimit(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def post(self, request, *args, **kwargs):
        info = request.data
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            models.limit.objects.filter(limitUserID=userInfo['id']).update(limit=info['limit'],limitUserID=userInfo['id'])
        except:
            return JsonResponse(AllRes(False,"发生异常,请稍候重试!",""))
        return JsonResponse(AllRes(True,"更新预算成功!",""))

class GetUserLimit(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            limit = models.limit.objects.get(limitUserID=userInfo['id'])
            limit = limit.__dict__
            limit.pop('_state')
            return JsonResponse(AllRes(True,"",limit))
        except:
            return JsonResponse(AllRes(False,"发生异常,请稍候重试!",""))

class GetUserTask(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            tasks = models.task.objects.filter(taskUserId=userInfo['id']).values()
            tasks = list(tasks)
            return JsonResponse(AllRes(True,"",tasks))
        except:
            return JsonResponse(AllRes(False,"发生异常,请稍候重试!",""))

class CreateUserTask(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def post(self, request, *args, **kwargs):
        Taskinfo = request.data
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            models.task(frequency=Taskinfo['frequency'],time=Taskinfo['time'],amount=Taskinfo['amount'],billType=Taskinfo['billType'],category=Taskinfo['category'],remark=Taskinfo['remark'],confirm=Taskinfo['confirm'],payMethod=Taskinfo['payMethod'],taskUserId=userInfo['id']).save()
        except Exception as ex:
            print(ex)
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
        return JsonResponse(AllRes(True,"创建任务成功!",""))
class UpdateUserTask(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def put(self, request, *args, **kwargs):
        Taskinfo = request.data
        try:
            models.task.objects.filter(id=Taskinfo['id']).update(frequency=Taskinfo['frequency'],time=Taskinfo['time'],amount=Taskinfo['amount'],billType=Taskinfo['billType'],category=Taskinfo['category'],remark=Taskinfo['remark'],confirm=Taskinfo['confirm'],payMethod=Taskinfo['payMethod'])
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
        return JsonResponse(AllRes(True,"更新任务成功!",""))
class DeleteUserTask(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, param, *args, **kwargs):
        try:
            models.task.objects.filter(id=param).delete()
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
        return JsonResponse(AllRes(True,"删除提醒成功!",""))
class QueryTaskById(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, param, *args, **kwargs):
        try:
            Task = models.task.objects.get(id=param)
            Task = Task.__dict__
            Task.pop('_state')
            return JsonResponse(AllRes(True,"",Task))
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))

class GetBill(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        billItem = []
        
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            billdates = models.bill.objects.filter(buid=userInfo['id']).values("billDate").order_by("billDate").distinct()
            billdates = list(billdates)
            for billdate in billdates:
                ob_sum = 0.0
                ic_sum = 0.0
                origindata_bill = models.bill.objects.filter(buid=userInfo['id'],billDate=billdate['billDate']).values()
                origindata_bill = list(origindata_bill)
                for ob in origindata_bill:
                    if ob['billType'] == "0":
                        ob_sum += float(ob['amount'])
                    else:
                        ic_sum += float(ob['amount'])
                billItem.append(BillItem(billdate['billDate'],str(ob_sum),str(ic_sum),origindata_bill).__dict__)
            return JsonResponse(AllRes(True,"取得数据成功!",billItem))
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))

class DeleteBill(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, param, *args, **kwargs):
        try:
            models.bill.objects.filter(id=param).delete()
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
        return JsonResponse(AllRes(True,"删除该账单成功!",""))
class GetBillYears(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        years = []
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            yearsdata = models.bill.objects.filter(buid=userInfo['id']).values('billDate').order_by('billDate').distinct()
            yearsdata = list(yearsdata)
            for yd in yearsdata:
                result = yd['billDate'].split('-')
                years.append(result[0])
            years = list(set(years))
            return JsonResponse(AllRes(True,"",years))
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
class GetYearlyBill(APIView):
    authentication_classes = [JwtQuertParamsAuthentication]
    def get(self, request, *args, **kwargs):
        queryYear = request.GET.get('year')
        userInfo = decodeUserToken(request.META['HTTP_AUTHORIZATION'])
        monthly = []
        results = []
        if userInfo == False:
            return JsonResponse(AllRes(False,"不存在的用户",""))
        try:
            fir = models.bill.objects.filter(buid=userInfo['id']).values()
            fir = list(fir)
            y_ob_sum = 0.0
            y_ic_sum = 0.0
            for fd in fir:
                dates = fd['billDate'].split('-')
                if dates[0] == queryYear:
                    if fd['billType'] == "0":
                        print(fd['amount'])
                        y_ob_sum += float(fd['amount'])
                    else:
                        print(fd['amount'])
                        y_ic_sum += float(fd['amount'])
                    monthly.append(dates[1])
                    print(monthly)
                    monthly = list(set(monthly))
                    print(monthly)
            for od in monthly:
                m_ob_sum = 0.0
                m_ic_sum = 0.0
                for fd in fir:
                    dates = fd['billDate'].split('-')
                    if dates[0] == queryYear:
                        if dates[1] == od:
                            if fd['billType'] == "0":
                                m_ob_sum += float(fd['amount'])
                            else:
                                m_ic_sum += float(fd['amount'])
                results.append(MonthliAmount(od,m_ob_sum,m_ic_sum,m_ic_sum-m_ob_sum).__dict__)
            yearanalyze = YearlyAnalyze(YearlyAmount(y_ob_sum,y_ic_sum,y_ic_sum-y_ob_sum).__dict__,results).__dict__
            print(yearanalyze)
            return JsonResponse(AllRes(True,"获取统计成功!",yearanalyze))
        except:
            return JsonResponse(AllRes(False,"发生错误,请稍候重试!",""))
    
def AllRes(isScu,str,data):
    return JsonResultFormat(isScu,str,data).obj2dict()

def decodeUserToken(meta):
    token = meta
    salt = settings.SECRET_KEY
    decodet = jwt.decode(token,key=salt,verify=False,algorithms=['HS256'])
    try:
        user_obj = user.objects.get(mobile=decodet['mobile'])
    except:
        return False
    userInfo = user_obj.__dict__
    return userInfo