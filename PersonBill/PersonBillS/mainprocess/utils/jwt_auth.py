import jwt
import datetime
from django.conf import settings


def get_token(payload, timeout):
    salt = settings.SECRET_KEY
    headers = {
        "typ": "jwt_",
        "alg": "HS256",
    }
    payload["exp"] = datetime.datetime.utcnow() + datetime.timedelta(minutes=timeout)
    token = jwt.encode(payload=payload, key=salt, headers=headers)
    return token
