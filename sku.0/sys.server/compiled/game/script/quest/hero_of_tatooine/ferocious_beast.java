package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.create;
import script.library.groundquests;
import script.library.group;
import script.library.utils;
import script.library.xp;

public class ferocious_beast extends script.base_script
{
    public ferocious_beast()
    {
    }
    public static final String SCRIPT_NAME = "quest.hero_of_tatooine.ferocious_beast";
    public static final String NOMOVE_SCRIPT = "item.special.nomove_furniture";
    public static final String NOMOVE_OBJVAR = "noTrade";
    public static final String MARK_OF_COURAGE = "object/tangible/loot/quest/hero_of_tatooine/mark_courage.iff";
    public static final String COURAGE_OBJVAR = "quest.hero_of_tatooine.courage";
    public static final String COURAGE_COMPLETE = COURAGE_OBJVAR + ".complete";
    public static final string_id NOTICE_OBJECT = new string_id("quest/hero_of_tatooine/system_messages", "courage_notice_object");
    public static final string_id ALREADY_HAVE_MARK = new string_id("quest/hero_of_tatooine/system_messages", "courage_already_have_mark");
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(item);
        if (!template.equals(MARK_OF_COURAGE))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(transferer))
        {
            if (hasObjVar(transferer, COURAGE_COMPLETE))
            {
                sendSystemMessage(transferer, ALREADY_HAVE_MARK);
                return SCRIPT_OVERRIDE;
            }
        }
        obj_id owner = getOwner(destContainer);
        if (isIdValid(owner) && isPlayer(owner))
        {
            if (hasObjVar(owner, COURAGE_COMPLETE))
            {
                sendSystemMessage(owner, ALREADY_HAVE_MARK);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(item);
        if (!template.equals(MARK_OF_COURAGE))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getOwner(destContainer);
        if (isIdValid(player) && isPlayer(player))
        {
            badge.grantBadge(player, "poi_rabidbeast");
            setObjVar(player, COURAGE_COMPLETE, 1);
            groundquests.sendSignal(player, "hero_of_tatooine_main_courage");
            CustomerServiceLog("quest", "HERO OF TATOOINE - %TU has acquired the Mark of Courage", player);
        }
        attachScript(item, NOMOVE_SCRIPT);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(self);
        if (!isIdValid(inv))
        {
            return SCRIPT_CONTINUE;
        }
        attachScript(inv, SCRIPT_NAME);
        obj_id mark_courage = createObject(MARK_OF_COURAGE, inv, "");
        if (!isIdValid(mark_courage))
        {
            CustomerServiceLog("quest", "HERO OF TATOOINE: Wild Bladeback Boar was killed, but the Mark of Courage did not spawn");
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("item", mark_courage);
        messageTo(self, "handlePrepItem", d, 1.0f, false);
        obj_id winner = getObjIdObjVar(self, xp.VAR_TOP_GROUP);
        if (isIdValid(winner))
        {
            obj_id[] members = null;
            if (group.isGroupObject(winner))
            {
                members = getGroupMemberIds(winner);
            }
            else 
            {
                members = new obj_id[1];
                members[0] = winner;
            }
            if ((members == null) || (members.length == 0))
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < members.length; i++)
            {
                if (isIdValid(members[i]))
                {
                    sendSystemMessage(members[i], NOTICE_OBJECT);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePrepItem(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id item = params.getObjId("item");
        if (hasScript(item, NOMOVE_SCRIPT))
        {
            detachScript(item, NOMOVE_SCRIPT);
        }
        if (hasObjVar(item, NOMOVE_OBJVAR))
        {
            removeObjVar(item, NOMOVE_OBJVAR);
        }
        return SCRIPT_CONTINUE;
    }
}
