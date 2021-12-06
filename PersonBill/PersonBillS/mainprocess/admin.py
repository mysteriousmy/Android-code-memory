from django.contrib import admin
from mainprocess import models

# Register your models here.
admin.site.register(models.user)
admin.site.register(models.bill)
admin.site.register(models.limit)
admin.site.register(models.task)
admin.site.register(models.reminder)