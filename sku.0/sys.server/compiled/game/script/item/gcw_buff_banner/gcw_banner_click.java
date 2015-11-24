package script.item.gcw_buff_banner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.create;
import script.library.trial;
import script.library.static_item;
import script.library.utils;

public class gcw_banner_click extends script.base_script
{
    public gcw_banner_click()
    {
    }
    public static final int FACTION_NONE = -1;
    public static final int FACTION_REBEL = 0;
    public static final int FACTION_IMPERIAL = 1;
    public static final String REBEL_BANNER = "object/tangible/event_perk/banner_rebel_style_01.iff";
    public static final String IMPERIAL_BANNER = "object/tangible/event_perk/banner_imperial_style_01.iff";
    public static final string_id SID_ALREADY_HAVE = new string_id("gcw", "banner_already_inplay");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "flagCoolDown"))
        {
            sendSystemMessage(player, SID_ALREADY_HAVE);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            int faction = FACTION_NONE;
            if (factions.isRebel(player))
            {
                faction = FACTION_REBEL;
            }
            if (factions.isImperial(player))
            {
                faction = FACTION_IMPERIAL;
            }
            if (faction == FACTION_NONE)
            {
                return SCRIPT_CONTINUE;
            }
            if (getCount(self) > 0)
            {
                static_item.decrementStaticItem(self);
            }
            createBannerItem(player, faction);
            utils.setScriptVar(self, "flagCoolDown", 1);
            messageTo(self, "handleRemoveTimer", null, 180f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void createBannerItem(obj_id player, int faction) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return;
        }
        obj_id banner = null;
        switch (faction)
        {
            case FACTION_REBEL:
            banner = create.object(REBEL_BANNER, getLocation(player));
            setObjVar(banner, "parent.faction", 0);
            break;
            case FACTION_IMPERIAL:
            banner = create.object(IMPERIAL_BANNER, getLocation(player));
            setObjVar(banner, "parent.faction", 1);
            break;
        }
        if (!isIdValid(banner))
        {
            return;
        }
        attachScript(banner, "item.gcw_buff_banner.banner_buff_manager");
        trial.setParent(player, banner, false);
    }
    public int handleRemoveTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "flagCoolDown"))
        {
            utils.removeScriptVar(self, "flagCoolDown");
        }
        return SCRIPT_CONTINUE;
    }
}
