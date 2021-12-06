from django.urls import path
from django.urls.conf import re_path
from . import views

urlpatterns = [
    path('user/login', views.LoginView.as_view()),
    path('user/get-vcode', views.createCode),
    path('user/validate-vcode', views.validateCode),
    path('user/register-login', views.JwtRegister.as_view()),
    # path('group/create', views.GroupCreate.as_view()),
    # path('group/list', views.GroupQuery.as_view()),
    path('bill/create', views.CreateBill.as_view()),
    path('user/bill-count', views.GetUserBillCount.as_view()),
    path('limit/set', views.SetUserLimit.as_view()),
    path('limit/query', views.GetUserLimit.as_view()),
    path('reminder/query', views.GetUserReminder.as_view()),
    path('reminder/update', views.UpdateUserReminder.as_view()),
    path('reminder/create', views.CreateUserReminder.as_view()),
    path('task/query', views.GetUserTask.as_view()),
    path('task/update', views.UpdateUserTask.as_view()),
    path('task/create', views.CreateUserTask.as_view()),
    path('bill/monthly', views.GetBill.as_view()),
    path('statistics/years', views.GetBillYears.as_view()),
    path('user/ensure-logined', views.EnsureLogin.as_view()),
    re_path(r'reminder/delete/(.+)/$', views.DeleteUserReminder.as_view()),
    re_path(r'bill/delete/(.+)/$', views.DeleteBill.as_view()),
    re_path(r'reminder/detail/(.+)/$', views.QueryReminderById.as_view()),
    re_path(r'task/delete/(.+)/$', views.DeleteUserTask.as_view()),
    re_path(r'task/detail/(.+)/$', views.QueryTaskById.as_view()),
    re_path(r'statistics/yearly-bills', views.GetYearlyBill.as_view()),
    # re_path(r'^group/detail/(.+)/$',views.GroupQueryById.as_view())
]


