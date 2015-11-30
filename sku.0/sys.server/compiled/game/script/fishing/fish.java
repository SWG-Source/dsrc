package script.fishing;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.minigame;
import script.library.resource;

public class fish extends script.base_script
{
    public fish()
    {
    }
    public static final string_id SID_FILET = new string_id(minigame.STF_FISH, "mnu_filet");
    public static final string_id SID_TROPHY = new string_id(minigame.STF_FISH, "mnu_trophy");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithin(self, inv))
        {
            obj_id[] contents = getContents(self);
            if (contents != null && contents.length > 0)
            {
                int mnuFilet = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_FILET);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithin(self, inv))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                minigame.filetFish(player, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnOpenedContainer(obj_id self, obj_id who) throws InterruptedException
    {
        if (isPlayer(who) && !isGod(who))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer) && !isGod(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer) && !isGod(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (hasObjVar(self, minigame.VAR_FISH_BASE))
        {
            int line = utils.getValidAttributeIndex(names);
            if (line == -1)
            {
                return SCRIPT_CONTINUE;
            }
            String type = getStringObjVar(self, minigame.VAR_FISH_NAME);
            if (type != null && !type.equals(""))
            {
                names[line] = "type";
                attribs[line] = "@fish_n:" + type;
                line++;
                if (line > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            float length = getFloatObjVar(self, minigame.VAR_FISH_LENGTH);
            if (length > 0f)
            {
                names[line] = "length";
                attribs[line] = "" + length + "m";
                line++;
                if (line > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            location loc = getLocationObjVar(self, minigame.VAR_FISH_LOCATION);
            if (loc != null)
            {
                names[line] = "planet";
                attribs[line] = "@planet_n:" + loc.area;
                line++;
                if (line > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, minigame.VAR_FISH_TIME_STAMP))
            {
                int timeCaught = getIntObjVar(self, minigame.VAR_FISH_TIME_STAMP);
                String stTimeCaught = getCalendarTimeStringLocal(timeCaught);
                names[line] = "fish_time_caught";
                attribs[line] = stTimeCaught;
                line++;
                if (line > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, minigame.VAR_FISH_CATCHER))
            {
                String playerName = getStringObjVar(self, minigame.VAR_FISH_CATCHER);
                names[line] = "caught_by";
                attribs[line] = playerName;
                line++;
                if (line > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
