package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class colors extends script.base_script
{
    public colors()
    {
    }
    public static final color BLACK = new color(0, 0, 0, 255);
    public static final color GREY = new color(190, 190, 190, 255);
    public static final color DIMGREY = new color(105, 105, 105, 255);
    public static final color LIGHTGREY = new color(211, 211, 211, 255);
    public static final color LIGHTSLATEGREY = new color(119, 136, 153, 255);
    public static final color SLATEGREY = new color(112, 128, 144, 255);
    public static final color GRAY = new color(190, 190, 190, 255);
    public static final color DIMGRAY = new color(105, 105, 105, 255);
    public static final color LIGHTGRAY = new color(211, 211, 211, 255);
    public static final color LIGHTSLATEGRAY = new color(119, 136, 153, 255);
    public static final color SLATEGRAY = new color(112, 128, 144, 255);
    public static final color ALICEBLUE = new color(240, 248, 255, 255);
    public static final color BLUEVIOLET = new color(138, 43, 226, 255);
    public static final color CADETBLUE = new color(95, 158, 160, 255);
    public static final color CORNFLOWERBLUE = new color(100, 149, 237, 255);
    public static final color DARKSLATEBLUE = new color(72, 61, 139, 255);
    public static final color DARKTURQUOISE = new color(0, 206, 209, 255);
    public static final color DEEPSKYBLUE = new color(0, 191, 255, 255);
    public static final color DODGERBLUE = new color(30, 144, 255, 255);
    public static final color LIGHTBLUE = new color(173, 216, 230, 255);
    public static final color LIGHTCYAN = new color(224, 255, 255, 255);
    public static final color LIGHTSKYBLUE = new color(135, 206, 250, 255);
    public static final color LIGHTSLATEBLUE = new color(132, 112, 255, 255);
    public static final color LIGHTSTEELBLUE = new color(176, 196, 222, 255);
    public static final color MEDIUMAQUAMARINE = new color(102, 205, 170, 255);
    public static final color MEDIUMBLUE = new color(0, 0, 205, 255);
    public static final color MEDIUMSLATEBLUE = new color(123, 104, 238, 255);
    public static final color MEDIUMTURQUOISE = new color(72, 209, 204, 255);
    public static final color MIDNIGHTBLUE = new color(25, 25, 112, 255);
    public static final color NAVYBLUE = new color(0, 0, 128, 255);
    public static final color PALETURQUOISE = new color(175, 238, 238, 255);
    public static final color POWDERBLUE = new color(176, 224, 230, 255);
    public static final color ROYALBLUE = new color(65, 105, 225, 255);
    public static final color SKYBLUE = new color(135, 206, 235, 255);
    public static final color SLATEBLUE = new color(106, 90, 205, 255);
    public static final color STEELBLUE = new color(70, 130, 180, 255);
    public static final color AQUAMARINE = new color(127, 255, 212, 255);
    public static final color AZURE = new color(240, 255, 255, 255);
    public static final color BLUE = new color(0, 0, 255, 255);
    public static final color CYAN = new color(0, 255, 255, 255);
    public static final color NAVY = new color(0, 0, 128, 255);
    public static final color TURQUOISE = new color(64, 224, 208, 255);
    public static final color DARKSLATEGRAY = new color(47, 79, 79, 255);
    public static final color ROSYBROWN = new color(188, 143, 143, 255);
    public static final color SADDLEBROWN = new color(139, 69, 19, 255);
    public static final color SANDYBROWN = new color(244, 164, 96, 255);
    public static final color BEIGE = new color(245, 245, 220, 255);
    public static final color BROWN = new color(165, 42, 42, 255);
    public static final color BURLYWOOD = new color(222, 184, 135, 255);
    public static final color CHOCOLATE = new color(210, 105, 30, 255);
    public static final color PERU = new color(205, 133, 63, 255);
    public static final color TAN = new color(210, 180, 140, 255);
    public static final color DARKGREEN = new color(0, 100, 0, 255);
    public static final color DARKKHAKI = new color(189, 183, 107, 255);
    public static final color DARKOLIVEGREEN = new color(85, 107, 47, 255);
    public static final color DARKSEAGREEN = new color(143, 188, 143, 255);
    public static final color FORESTGREEN = new color(34, 139, 34, 255);
    public static final color GREENYELLOW = new color(173, 255, 47, 255);
    public static final color LAWNGREEN = new color(124, 252, 0, 255);
    public static final color LIGHTSEAGREEN = new color(32, 178, 170, 255);
    public static final color LIMEGREEN = new color(50, 205, 50, 255);
    public static final color MEDIUMSEAGREEN = new color(60, 179, 113, 255);
    public static final color MEDIUMSPRINGGREEN = new color(0, 250, 154, 255);
    public static final color MINTCREAM = new color(245, 255, 250, 255);
    public static final color OLIVEDRAB = new color(107, 142, 35, 255);
    public static final color PALEGREEN = new color(152, 251, 152, 255);
    public static final color SEAGREEN = new color(46, 139, 87, 255);
    public static final color SPRINGGREEN = new color(0, 255, 127, 255);
    public static final color YELLOWGREEN = new color(154, 205, 50, 255);
    public static final color CHARTREUSE = new color(127, 255, 0, 255);
    public static final color GREEN = new color(0, 255, 0, 255);
    public static final color KHAKI = new color(240, 230, 140, 255);
    public static final color DARKORANGE = new color(255, 140, 0, 255);
    public static final color DARKSALMON = new color(233, 150, 122, 255);
    public static final color LIGHTCORAL = new color(240, 128, 128, 255);
    public static final color LIGHTSALMON = new color(255, 160, 122, 255);
    public static final color PEACHPUFF = new color(255, 218, 185, 255);
    public static final color BISQUE = new color(255, 228, 196, 255);
    public static final color CORAL = new color(255, 127, 80, 255);
    public static final color HONEYDEW = new color(240, 255, 240, 255);
    public static final color ORANGE = new color(255, 165, 0, 255);
    public static final color SALMON = new color(250, 128, 114, 255);
    public static final color SIENNA = new color(160, 82, 45, 255);
    public static final color DEEPPINK = new color(255, 20, 147, 255);
    public static final color HOTPINK = new color(255, 105, 180, 255);
    public static final color INDIANRED = new color(205, 92, 92, 255);
    public static final color LIGHTPINK = new color(255, 182, 193, 255);
    public static final color MEDIUMVIOLETRED = new color(199, 21, 133, 255);
    public static final color MISTYROSE = new color(255, 228, 225, 255);
    public static final color ORANGERED = new color(255, 69, 0, 255);
    public static final color PALEVIOLETRED = new color(219, 112, 147, 255);
    public static final color VIOLETRED = new color(208, 32, 144, 255);
    public static final color FIREBRICK = new color(178, 34, 34, 255);
    public static final color PINK = new color(255, 192, 203, 255);
    public static final color RED = new color(255, 0, 0, 255);
    public static final color TOMATO = new color(255, 99, 71, 255);
    public static final color DARKORCHID = new color(153, 50, 204, 255);
    public static final color DARKVIOLET = new color(148, 0, 211, 255);
    public static final color LAVENDERBLUSH = new color(255, 240, 245, 255);
    public static final color MEDIUMORCHID = new color(186, 85, 211, 255);
    public static final color MEDIUMPURPLE = new color(147, 112, 219, 255);
    public static final color LAVENDER = new color(230, 230, 250, 255);
    public static final color MAGENTA = new color(255, 0, 255, 255);
    public static final color MAROON = new color(176, 48, 96, 255);
    public static final color ORCHID = new color(218, 112, 214, 255);
    public static final color PLUM = new color(221, 160, 221, 255);
    public static final color PURPLE = new color(160, 32, 240, 255);
    public static final color THISTLE = new color(216, 191, 216, 255);
    public static final color VIOLET = new color(238, 130, 238, 255);
    public static final color ANTIQUEWHITE = new color(250, 235, 215, 255);
    public static final color FLORALWHITE = new color(255, 250, 240, 255);
    public static final color GHOSTWHITE = new color(248, 248, 255, 255);
    public static final color NAVAJOWHITE = new color(255, 222, 173, 255);
    public static final color OLDLACE = new color(253, 245, 230, 255);
    public static final color WHITESMOKE = new color(245, 245, 245, 255);
    public static final color GAINSBORO = new color(220, 220, 220, 255);
    public static final color IVORY = new color(255, 255, 240, 255);
    public static final color LINEN = new color(250, 240, 230, 255);
    public static final color SEASHELL = new color(255, 245, 238, 255);
    public static final color SNOW = new color(255, 250, 250, 255);
    public static final color WHEAT = new color(245, 222, 179, 255);
    public static final color WHITE = new color(255, 255, 255, 255);
    public static final color BLANCHEDALMOND = new color(255, 235, 205, 255);
    public static final color DARKGOLDENROD = new color(184, 134, 11, 255);
    public static final color LEMONCHIFFON = new color(255, 250, 205, 255);
    public static final color LIGHTGOLDENROD = new color(238, 221, 130, 255);
    public static final color LIGHTGOLDENRODYELLOW = new color(250, 250, 210, 255);
    public static final color LIGHTYELLOW = new color(255, 255, 224, 255);
    public static final color PALEGOLDENROD = new color(238, 232, 170, 255);
    public static final color PAPAYAWHIP = new color(255, 239, 213, 255);
    public static final color CORNSILK = new color(255, 248, 220, 255);
    public static final color GOLDYELLOW = new color(255, 215, 0, 255);
    public static final color GOLDENROD = new color(218, 165, 32, 255);
    public static final color MOCCASIN = new color(255, 228, 181, 255);
    public static final color YELLOW = new color(255, 255, 0, 255);
    public static final color COPPER = new color(184, 115, 51, 255);
    public static final color GOLD = new color(205, 127, 50, 255);
    public static final color SILVER = new color(230, 232, 250, 255);
    public static final String TBL_COLOR_RGB = "datatables/color/rgb.iff";
    public static color getColorByName(String name) throws InterruptedException
    {
        if (name == null || name.equals(""))
        {
            return null;
        }
        dictionary row = dataTableGetRow(TBL_COLOR_RGB, toUpper(name));
        if (row == null || row.isEmpty())
        {
            return null;
        }
        int r = row.getInt("R");
        int g = row.getInt("G");
        int b = row.getInt("B");
        return new color(r, g, b, 255);
    }
}
