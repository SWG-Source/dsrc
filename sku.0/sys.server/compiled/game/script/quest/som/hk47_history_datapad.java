package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class hk47_history_datapad extends script.base_script
{
    public hk47_history_datapad()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id ACCESS = new string_id(STF, "hk_history_datapad");
    public static final string_id SID_PART1 = new string_id(STF, "hk_history_datapad_01");
    public static final string_id SID_PART2 = new string_id(STF, "hk_history_datapad_02");
    public static final string_id SID_PART3 = new string_id(STF, "hk_history_datapad_03");
    public static final string_id SID_PART4 = new string_id(STF, "hk_history_datapad_04");
    public static final string_id SID_PART5 = new string_id(STF, "hk_history_datapad_05");
    public static final string_id SID_PART6 = new string_id(STF, "hk_history_datapad_06");
    public static final string_id SID_PART7 = new string_id(STF, "hk_history_datapad_07");
    public static final string_id SID_PART8 = new string_id(STF, "hk_history_datapad_08");
    public static final string_id SID_PART9 = new string_id(STF, "hk_history_datapad_09");
    public static final string_id SID_PART10 = new string_id(STF, "hk_history_datapad_10");
    public static final string_id CHOOSE = new string_id(STF, "hk_history_datapad_select");
    public static final string_id SID_MESSAGE01 = new string_id(STF, "hk_history_message_01");
    public static final string_id SID_MESSAGE02 = new string_id(STF, "hk_history_message_02");
    public static final string_id SID_MESSAGE03 = new string_id(STF, "hk_history_message_03");
    public static final string_id SID_MESSAGE04 = new string_id(STF, "hk_history_message_04");
    public static final string_id SID_MESSAGE05 = new string_id(STF, "hk_history_message_05");
    public static final string_id SID_MESSAGE06 = new string_id(STF, "hk_history_message_06");
    public static final string_id SID_MESSAGE07 = new string_id(STF, "hk_history_message_07");
    public static final string_id SID_MESSAGE08 = new string_id(STF, "hk_history_message_08");
    public static final string_id SID_MESSAGE09 = new string_id(STF, "hk_history_message_09");
    public static final string_id SID_MESSAGE10 = new string_id(STF, "hk_history_message_10");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ACCESS);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU1, SID_PART1);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU2, SID_PART2);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU3, SID_PART3);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, SID_PART4);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU5, SID_PART5);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU6, SID_PART6);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU7, SID_PART7);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU8, SID_PART8);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU9, SID_PART9);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU10, SID_PART10);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            String appearance = "object/mobile/som/neimoidian.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE01;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            String appearance = "object/mobile/som/neimoidian.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE02;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            String appearance = "object/mobile/som/neimoidian.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE03;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            String appearance = "object/mobile/som/neimoidian.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE04;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            String appearance = "object/mobile/som/neimoidian.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE05;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            String appearance = "object/mobile/som/neimoidian.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE06;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            String appearance = "object/mobile/som/hk47.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE07;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            String appearance = "object/mobile/som/hk47.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE08;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            String appearance = "object/mobile/som/hk47.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE09;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU10)
        {
            String appearance = "object/mobile/som/hk47.iff";
            prose_package pp = new prose_package();
            pp.stringId = SID_MESSAGE10;
            commPlayer(self, player, pp, appearance);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(player, CHOOSE);
        }
        return SCRIPT_CONTINUE;
    }
}
