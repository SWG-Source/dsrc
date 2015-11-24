package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.dot;
import script.library.prose;
import script.library.utils;

public class target_buff extends script.base_script
{
    public target_buff()
    {
    }
    public static final string_id SID_BUFF_NPC_ONLY = new string_id("spam", "item_buff_npc_only");
    public static final string_id SID_BUFF_WONT_STACK = new string_id("spam", "item_buff_no_stack");
    public static final string_id SID_BUFF_PLAYER_ONLY = new string_id("spam", "item_buff_player_only");
    public static final string_id USED_EFFECT = new string_id("spam", "item_buff_used_other_player");
    public static final string_id BUFF_OUT_OF_RANGE = new string_id("spam", "item_buff_out_of_range");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_OVERRIDE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, null);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(player, "feign_death"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getIntendedTarget(player);
        if (!isIdValid(target))
        {
            target = getLookAtTarget(player);
            if (!isIdValid(target))
            {
                target = null;
            }
        }
        if (item == menu_info_types.ITEM_USE)
        {
            buff(self, player, target);
        }
        return SCRIPT_CONTINUE;
    }
    public void buff(obj_id self, obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(player))
        {
            return;
        }
        String buff_name = getStringObjVar(self, "buff_name");
        if (buff_name == null || buff_name.equals(""))
        {
            return;
        }
        float eff = 1f;
        float dur = 1f;
        if (hasObjVar(self, "effectiveness"))
        {
            eff = getFloatObjVar(self, "effectiveness");
        }
        if (hasObjVar(self, "duration"))
        {
            dur = getFloatObjVar(self, "duration");
        }
        boolean success = true;
        if (hasObjVar(self, "npc_only"))
        {
            if (isPlayer(target) || !isIdValid(target))
            {
                sendSystemMessage(player, SID_BUFF_NPC_ONLY);
                return;
            }
        }
        if (hasObjVar(self, "self_only") || !isIdValid(target))
        {
            target = player;
        }
        if (hasObjVar(self, "player_only") && !isPlayer(target))
        {
            sendSystemMessage(player, SID_BUFF_PLAYER_ONLY);
            return;
        }
        if (buff.hasBuff(target, buff_name) && !hasObjVar(self, "can_stack"))
        {
            sendSystemMessage(player, SID_BUFF_WONT_STACK);
            return;
        }
        if (hasObjVar(self, "max_range"))
        {
            if (isIdValid(target))
            {
                float maxRange = getFloatObjVar(self, "max_range");
                location selfLoc = getLocation(player);
                location targetLoc = getLocation(target);
                float fltDistance = getDistance(selfLoc, targetLoc);
                if (fltDistance > maxRange)
                {
                    prose_package pp = prose.getPackage(BUFF_OUT_OF_RANGE);
                    prose.setDF(pp, maxRange);
                    sendSystemMessageProse(player, pp);
                    return;
                }
            }
        }
        float value = buff.getEffectValue(buff_name, 1);
        float duration = buff.getDuration(buff_name);
        value *= eff;
        duration *= dur;
        success = buff.applyBuff(target, buff_name, duration, value);
        if (success)
        {
            if (isPlayer(target) && target != player)
            {
                prose_package pp = prose.getPackage(USED_EFFECT);
                prose.setTT(pp, player);
                prose.setTU(pp, "@ui_buff:" + buff_name);
                sendSystemMessageProse(target, pp);
            }
            int count = getCount(self);
            count--;
            if (count <= 0)
            {
                destroyObject(self);
            }
            else 
            {
                setCount(self, count);
            }
        }
    }
}
