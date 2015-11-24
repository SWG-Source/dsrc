package script.item.bug_jar;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.resource;
import script.library.utils;

public class bug_habitat extends script.base_script
{
    public bug_habitat()
    {
    }
    public static final string_id SID_RELEASE_CREATURES = new string_id("lair_n", "release_creatures");
    public static final string_id SID_RELEASE = new string_id("lair_n", "release");
    public static final string_id SID_NO_ACCESS = new string_id("lair_n", "no_access");
    public static final String[] BUG_FX = 
    {
        "clienteffect/item_bugs_bats.cef",
        "clienteffect/item_bugs_bees.cef",
        "clienteffect/item_bugs_butterflies.cef",
        "clienteffect/item_bugs_flies.cef",
        "clienteffect/item_bugs_glowzees.cef",
        "clienteffect/item_bugs_moths.cef"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_RELEASE_CREATURES);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id topContainer = getTopMostContainer(self);
            String playerName = getPlayerName(player);
            if (topContainer == player)
            {
                releaseCreatures(self, player);
            }
            else if (player_structure.isNameOnAdminList(topContainer, playerName))
            {
                releaseCreatures(self, player);
            }
            else 
            {
                sendSystemMessage(player, SID_NO_ACCESS);
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public void releaseCreatures(obj_id self, obj_id player) throws InterruptedException
    {
        sendSystemMessage(player, SID_RELEASE);
        int type = getIntObjVar(self, "bugsample");
        playClientEffectLoc(player, BUG_FX[type - 1], getLocation(player), 1.5f);
        destroyObject(self);
    }
}
