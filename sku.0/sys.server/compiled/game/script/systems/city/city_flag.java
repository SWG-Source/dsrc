package script.systems.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class city_flag extends script.base_script
{
    public city_flag()
    {
    }
    public static final string_id SID_ALTER = new string_id("city/city", "alter_flag");
    public static final string_id SID_PROMPT = new string_id("city/city", "flag_prompt");
    public static final string_id SID_TITLE = new string_id("city/city", "flag_title");
    public static final String PID_NAME = "cityFlag";
    public static final String FLAG_CORELLIA = "object/tangible/furniture/city/flag_city_01.iff";
    public static final String FLAG_DANTOOINE = "object/tangible/furniture/city/flag_city_02.iff";
    public static final String FLAG_DATHOMIR = "object/tangible/furniture/city/flag_city_03.iff";
    public static final String FLAG_ENDOR = "object/tangible/furniture/city/flag_city_04.iff";
    public static final String FLAG_LOK = "object/tangible/furniture/city/flag_city_05.iff";
    public static final String FLAG_NABOO = "object/tangible/furniture/city/flag_city_06.iff";
    public static final String FLAG_RORI = "object/tangible/furniture/city/flag_city_07.iff";
    public static final String FLAG_TALUS = "object/tangible/furniture/city/flag_city_08.iff";
    public static final String FLAG_TATOOINE = "object/tangible/furniture/city/flag_city_09.iff";
    public static final String FLAG_YAVIN = "object/tangible/furniture/city/flag_city_10.iff";
    public static final String FLAG_IMPERIAL = "object/tangible/furniture/city/flag_city_11.iff";
    public static final String FLAG_REBEL = "object/tangible/furniture/city/flag_city_12.iff";
    public static final String FLAG_LIFEDAY_01 = "object/tangible/furniture/lifeday/lifeday_banner_s01.iff";
    public static final String FLAG_LIFEDAY_02 = "object/tangible/furniture/lifeday/lifeday_banner_s02.iff";
    public static final String FLAG_LIFEDAY_03 = "object/tangible/furniture/lifeday/lifeday_banner_s03.iff";
    public static final String[] FLAG_SELECT_OPTIONS = 
    {
        "Corellia",
        "Dantooine",
        "Dathomir",
        "Endor",
        "Lok",
        "Naboo",
        "Rori",
        "Talus",
        "Tatooine",
        "Yavin 4",
        "Imperial",
        "Rebel"
    };
    public static final String[] LIFEDAY_FLAG_SELECT_OPTIONS = 
    {
        "Wookiee Home Banner",
        "Kachirho Cornucopia Banner",
        "Life Day Orb Banner"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU8, SID_ALTER);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            if (!sui.hasPid(player, PID_NAME))
            {
                String prompt = utils.packStringId(SID_PROMPT);
                String title = utils.packStringId(SID_TITLE);
                if (hasObjVar(self, "lifeday"))
                {
                    int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, LIFEDAY_FLAG_SELECT_OPTIONS, "handleFlagSui", true, false);
                    sui.setPid(player, pid, PID_NAME);
                }
                else 
                {
                    int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, FLAG_SELECT_OPTIONS, "handleFlagSui", true, false);
                    sui.setPid(player, pid, PID_NAME);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFlagSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (!isIdValid(player))
        {
            cleanUpFlagVars(player);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(pInv))
        {
            cleanUpFlagVars(player);
            return SCRIPT_CONTINUE;
        }
        if (getVolumeFree(pInv) <= 0)
        {
            cleanUpFlagVars(player);
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            cleanUpFlagVars(player);
            return SCRIPT_CONTINUE;
        }
        if (idx == -1 || idx > FLAG_SELECT_OPTIONS.length)
        {
            cleanUpFlagVars(player);
            return SCRIPT_CONTINUE;
        }
        String flagName = "";
        if (hasObjVar(self, "lifeday"))
        {
            switch (idx)
            {
                case 0:
                flagName = FLAG_LIFEDAY_01;
                break;
                case 1:
                flagName = FLAG_LIFEDAY_02;
                break;
                case 2:
                flagName = FLAG_LIFEDAY_03;
                break;
                default:
                cleanUpFlagVars(player);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            switch (idx)
            {
                case 0:
                flagName = FLAG_CORELLIA;
                break;
                case 1:
                flagName = FLAG_DANTOOINE;
                break;
                case 2:
                flagName = FLAG_DATHOMIR;
                break;
                case 3:
                flagName = FLAG_ENDOR;
                break;
                case 4:
                flagName = FLAG_LOK;
                break;
                case 5:
                flagName = FLAG_NABOO;
                break;
                case 6:
                flagName = FLAG_RORI;
                break;
                case 7:
                flagName = FLAG_TALUS;
                break;
                case 8:
                flagName = FLAG_TATOOINE;
                break;
                case 9:
                flagName = FLAG_YAVIN;
                break;
                case 10:
                flagName = FLAG_IMPERIAL;
                break;
                case 11:
                flagName = FLAG_REBEL;
                break;
                default:
                cleanUpFlagVars(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (!flagName.equals(""))
        {
            obj_id newFlag = createObject(flagName, pInv, "");
            if (isIdValid(newFlag))
            {
                destroyObject(self);
            }
        }
        cleanUpFlagVars(player);
        return SCRIPT_CONTINUE;
    }
    public void cleanUpFlagVars(obj_id player) throws InterruptedException
    {
        sui.removePid(player, PID_NAME);
    }
}
