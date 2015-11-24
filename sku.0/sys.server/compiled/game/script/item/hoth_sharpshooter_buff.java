package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;
import script.library.trial;
import script.library.buff;

public class hoth_sharpshooter_buff extends script.base_script
{
    public hoth_sharpshooter_buff()
    {
    }
    public static final string_id SID_MUST_BIO_LINK_FROM_INVENTORY = new string_id("base_player", "must_biolink_to_use_from_inventory");
    public static final string_id SID_NOT_YET = new string_id("base_player", "not_yet");
    public static final float REUSE_TIME = 1800;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", "item_use"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player) || !utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            float buffTime = getFloatObjVar(player, "clickItem.hoth_sharpshooter");
            if (getGameTime() > buffTime || isGod(player))
            {
                setObjVar(player, "clickItem.hoth_sharpshooter", (getGameTime() + REUSE_TIME));
                sendCooldownGroupTimingOnly(player, getStringCrc("epic_items"), REUSE_TIME);
                playClientEffectObj(player, "clienteffect/medic_reckless_stimulation.cef", player, "");
                activateSharpshooterBuff(self, player);
            }
            else 
            {
                int timeDiff = (int)buffTime - getGameTime();
                prose_package pp = prose.getPackage(SID_NOT_YET, timeDiff);
                sendSystemMessageProse(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateSharpshooterBuff(obj_id self, obj_id player) throws InterruptedException
    {
        buff.applyBuff(player, "hoth_sharpshooter_buff");
    }
}
