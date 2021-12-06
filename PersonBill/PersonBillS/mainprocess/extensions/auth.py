import jwt
from jwt import exceptions
from rest_framework.authentication import BaseAuthentication
from django.conf import settings
from rest_framework.exceptions import AuthenticationFailed


class JwtQuertParamsAuthentication(BaseAuthentication):
    def authenticate(self, request):
        token = request.META['HTTP_AUTHORIZATION']
        salt = settings.SECRET_KEY
        try:
            result = jwt.decode(token, key=salt, verify=True,algorithms=['HS256'])
        except exceptions.ExpiredSignatureError:
            msg = "token失效"
            raise AuthenticationFailed({"code": 1001, "message": msg})
        except exceptions.DecodeError:
            msg = "token认证失败"
            raise AuthenticationFailed({"code": 1002, "message": msg})
        except exceptions.InvalidTokenError:
            msg = "非法token"
            raise AuthenticationFailed({"code": 1003, "message": msg})
        return (result, token)

        # 三种操作
        # 1.抛出错误，后续不再执行
        # 2.return一个元组，（1,2）认证通过，在视图中如果调用request.user 就是第一个值request.auth就是第二个
        # 3.None不做任何操作
