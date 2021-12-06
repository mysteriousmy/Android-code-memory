import 'package:flutter/material.dart';

class Colours {
  //0xFF2D2E32  0xff002c7d  0xff291b34 0xff002c7d Color(0xff291b34)
  static Color app_main = HexColor("#00BFFF");
  static const Color bg_color = Color(0xfff1f1f1);
  static const Color line = Color(0xFFEEEEEE);
  static Color black = HexColor("#00BFFF");
  static Color dark = HexColor("#00BFFF");
  static Color normalBlue = HexColor("#30A9DE");
  static Color normalBlueLight = HexColor("#30A9DE");
  static Color normalBlueShadow = HexColor("#30A9DE");

  static const Color normalBlack = Color(0xFF666666);
  static const Color gray = Color(0xFF999999);
  static const Color gray_c = Color(0xFFcccccc);
}

class HexColor extends Color {
  static int _getColorFromHex(String hexColor) {
    hexColor = hexColor.toUpperCase().replaceAll("#", "");
    if (hexColor.length == 6) {
      hexColor = "FF" + hexColor;
    }
    return int.parse(hexColor, radix: 16);
  }

  HexColor(final String hexColor) : super(_getColorFromHex(hexColor));
}
