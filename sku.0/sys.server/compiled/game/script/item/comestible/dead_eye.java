package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_stomach;
import script.library.utils;

public class dead_eye extends script.base_script
{
    public dead_eye()
    {
    }
    public static final string_id SID_DEAD_EYE_ACTIVE = new string_id("combat_effects", "dead_eye_active");
    public static final string_id SID_DEAD_EYE_ALREADY = new string_id("combat_effects", "dead_eye_already");
    public static final string_id SID_DEAD_EYE_WAIT = new string_id("combat_effects", "dead_eye_wait");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            applyDeadEye(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void applyDeadEye(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "dead_eye.active"))
        {
            int dea = utils.getIntScriptVar(player, "dead_eye.active");
            float dur = utils.getFloatScriptVar(player, "dead_eye.dur");
            if (getGameTime() - dea < dur)
            {
                sendSystemMessage(player, SID_DEAD_EYE_ALREADY);
                return;
            }
        }
        int count = getCount(self);
        count--;
        setCount(self, count);
        int[] vol = 
        {
            20,
            0
        };
        boolean wasConsumed = player_stomach.addToStomach(player, player, vol);
        utils.setScriptVar(player, "dead_eye.active", getGameTime());
        utils.setScriptVar(player, "dead_eye.eff", getIntObjVar(self, "deadeye_eff"));
        utils.setScriptVar(player, "dead_eye.dur", getFloatObjVar(self, "deadeye_dur"));
        sendSystemMessage(player, SID_DEAD_EYE_ACTIVE);
        if (count <= 0)
        {
            destroyObject(self);
        }
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "deadeye_eff"))
        {
            names[idx] = "examine_dot_apply_power";
            attribs[idx] = "" + getIntObjVar(self, "deadeye_eff") + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "deadeye_dur"))
        {
            names[idx] = "duration";
            attribs[idx] = utils.formatTime((int)getFloatObjVar(self, "deadeye_dur"));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
