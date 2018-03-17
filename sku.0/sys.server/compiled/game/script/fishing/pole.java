package script.fishing;

import script.library.minigame;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class pole extends script.base_script
{
    public pole()
    {
    }
    public static final String STF = "som/som_item";
    public static final string_id MNU_TACKLE = new string_id(minigame.STF_FISH, "mnu_open_tackle");
    public static final string_id MNU_BAIT = new string_id(minigame.STF_FISH, "mnu_set_bait");
    public static final string_id MNU_CLEAR_BAIT = new string_id(minigame.STF_FISH, "mnu_clear_bait");
    private static final string_id MNU_START_FISHING = new string_id(minigame.STF_FISH, "mnu_start_fishing");
    private static final string_id MNU_STOP_FISHING = new string_id(minigame.STF_FISH, "mnu_stop_fishing");
    private static final string_id SID_NO_FISH_IN_SPACE = new string_id("space/space_interaction", "no_fish_in_space");
    private static final String STF_FISH = "fishing";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        if (planetName.startsWith("mustafar"))
        {
            sendSystemMessage(self, new string_id(STF, "cannot_fish"));
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id held = getObjectInSlot(player, "hold_r");
        if (utils.isNestedWithin(self, inv) || (self == held))
        {
            boolean isPoleInUse = minigame.isPoleInUse(self);
            if (!isPoleInUse)
            {
                if (held == self)
                {
                    mi.addRootMenu(menu_info_types.SERVER_MENU2, MNU_START_FISHING);
                }
            }
            else 
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU3, MNU_STOP_FISHING);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (isSpaceScene())
        {
            sendSystemMessage(player, SID_NO_FISH_IN_SPACE);
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id held = getObjectInSlot(player, "hold_r");
        if (utils.isNestedWithin(self, inv) || (self == held))
        {
            if (item == menu_info_types.SERVER_MENU2)
            {
                queueCommand(player, (138370278), null, "", COMMAND_PRIORITY_DEFAULT);
            }
            else if (item == menu_info_types.SERVER_MENU3)
            {
                minigame.stopFishing(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer) && getGameObjectType(item) != GOT_misc_fishing_bait)
        {
            sendSystemMessage(self, new string_id(STF_FISH, "bait_only"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.hasScriptVar(self, minigame.SCRIPTVAR_IN_USE))
        {
            obj_id user = utils.getObjIdScriptVar(self, minigame.SCRIPTVAR_IN_USE);
            if (!isIdValid(user))
            {
                utils.removeScriptVar(self, minigame.SCRIPTVAR_IN_USE);
            }
            else 
            {
                sendSystemMessage(self, new string_id(STF_FISH, "cannot_remove_bait"));
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "useModifier"))
        {
            names[idx] = "useModifier";
            float attrib = getIntObjVar(self, "useModifier");
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
