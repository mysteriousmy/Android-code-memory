
from mainprocess import models
from django.http.response import JsonResponse
from django.test import TestCase
import json

from jwt import encode


class JsonResultFormat():
    __slots__ = ('success', 'message', 'data')

    def __init__(self, success, message, data):
        self.success = success
        self.message = message
        self.data = data

    def obj2dict(self):
        return json.dumps({
            'success': self.success,
            'message': self.message,
            'data': self.data
        }, ensure_ascii=False)


# Create your tests here.
s = JsonResponse(JsonResultFormat(False, "失败", "").obj2dict())
print(s)

billdates = models.bill.objects.filter(buid=1).values("billDate").order_by("billDate").distinct()
print(billdates)
origindata = models.bill.objects.filter(buid=1).values()
origindata = list(origindata)
