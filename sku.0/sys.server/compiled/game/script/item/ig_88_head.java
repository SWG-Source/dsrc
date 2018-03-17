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

public class ig_88_head extends script.base_script
{
    public ig_88_head()
    {
    }
    public static final string_id SID_MUST_BIO_LINK_FROM_INVENTORY = new string_id("base_player", "must_biolink_to_use_from_inventory");
    public static final string_id SID_NOT_YET = new string_id("base_player", "not_yet");
    public static final float REUSE_TIME = 1800;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("set_bonus", "ig_head_activate"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player) || !utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id biolink = getBioLink(self);
        if (isValidId(biolink) && biolink == utils.OBJ_ID_BIO_LINK_PENDING)
        {
            sendSystemMessage(player, SID_MUST_BIO_LINK_FROM_INVENTORY);
            return SCRIPT_CONTINUE;
        }
        if (biolink != player)
        {
            sendSystemMessage(player, new string_id("base_player", "not_linked_to_holder"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            float buffTime = getFloatObjVar(player, "clickItem.ig_head");
            if (getGameTime() > buffTime || isGod(player))
            {
                setObjVar(player, "clickItem.ig_head", (getGameTime() + REUSE_TIME));
                sendCooldownGroupTimingOnly(player, getStringCrc("epic_items"), REUSE_TIME);
                playClientEffectObj(player, "clienteffect/medic_reckless_stimulation.cef", player, "");
                activateHead(self, player);
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
    public void activateHead(obj_id self, obj_id player) throws InterruptedException
    {
        int roll = rand(0, 100);
        int messageNum = -1;
        if (roll >= 11 && roll <= 20)
        {
            messageNum = 0;
            buff.applyBuff(player, "ig_head_debuff_1");
            sendComToPlayer(player, messageNum);
            return;
        }
        if (roll >= 21 && roll <= 30)
        {
            messageNum = 1;
            buff.applyBuff(player, "ig88_droideka_electrify");
            sendComToPlayer(player, messageNum);
            return;
        }
        if (roll >= 31 && roll <= 55)
        {
            messageNum = 2;
            buff.applyBuff(player, "ig_head_buff_1");
            sendComToPlayer(player, messageNum);
            return;
        }
        if (roll >= 56 && roll <= 85)
        {
            messageNum = 3;
            buff.applyBuff(player, player, "ig_head_buff_2");
            sendComToPlayer(player, messageNum);
            return;
        }
        if (roll >= 86 && roll <= 100)
        {
            messageNum = 4;
            buff.applyBuff(player, "ig_head_buff_3");
            sendComToPlayer(player, messageNum);
            return;
        }
        if (messageNum > -1)
        {
            prose_package pp = new prose_package();
            prose.setStringId(pp, new string_id("set_bonus", "ig_head_message_" + messageNum));
            commPlayers(player, "object/mobile/ig_88.iff", "sound/sys_comm_other.snd", 5f, player, pp);
        }
        else 
        {
            sendSystemMessage(player, new string_id("set_bonus", "ig_head_message_nothing"));
        }
        return;
    }
    public void sendComToPlayer(obj_id player, int messageNum) throws InterruptedException
    {
        if (messageNum > -1)
        {
            prose_package pp = new prose_package();
            prose.setStringId(pp, new string_id("set_bonus", "ig_head_message_" + messageNum));
            commPlayers(player, "object/mobile/ig_88.iff", "sound/sys_comm_other.snd", 5f, player, pp);
        }
        return;
    }
}
