// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

User _$UserFromJson(Map<String, dynamic> json) {
  return User(
    json['id'] as int,
    json['token'] as String,
    json['avatar'] as String,
    json['nickname'] as String,
    json['lastDevice'] as String,
    json['lastLogin'] as String,
    json['mobile'] as String,
    json['registrationId'] as String,
  );
}

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
      'id': instance.id,
      'token': instance.token,
      'avatar': instance.avatar,
      'nickname': instance.nickname,
      'lastLogin': instance.lastLogin,
      'lastDevice': instance.lastDevice,
      'mobile': instance.mobile,
      'registrationId': instance.registrationId,
    };

UserBillCount _$UserBillCountFromJson(Map<String, dynamic> json) {
  return UserBillCount(
    json['days'] as int,
    json['counts'] as int,
  );
}

Map<String, dynamic> _$UserBillCountToJson(UserBillCount instance) =>
    <String, dynamic>{
      'days': instance.days,
      'counts': instance.counts,
    };
