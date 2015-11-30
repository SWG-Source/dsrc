package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class rock_crevice extends script.base_script
{
    public rock_crevice()
    {
    }
    public static final String ALTRUISM_OBJVAR = "quest.hero_of_tatooine.altruism";
    public static final String ALTRUISM_COMPLETE = ALTRUISM_OBJVAR + ".complete";
    public static final String ALTRUISM_CONTROL = ALTRUISM_OBJVAR + ".control";
    public static final string_id NO_REASON = new string_id("quest/hero_of_tatooine/system_messages", "altruism_no_reason");
    public static final string_id ALREADY_OPEN = new string_id("quest/hero_of_tatooine/system_messages", "altruism_already_open");
    public static final string_id NOT_WISE_EXAMINE = new string_id("quest/hero_of_tatooine/system_messages", "altruism_not_wise_examine");
    public static final string_id EXPLOSIVE_SET = new string_id("quest/hero_of_tatooine/system_messages", "altruism_explosive_set");
    public static final string_id EXPLOSIVE_REMOVED = new string_id("quest/hero_of_tatooine/system_messages", "altruism_explosive_removed");
    public int OnAboutToOpenContainer(obj_id self, obj_id who) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (isIdValid(owner))
        {
            if (owner != who)
            {
                if (utils.hasScriptVar(self, "hasExplosive"))
                {
                    sendSystemMessage(who, NOT_WISE_EXAMINE);
                }
                else 
                {
                    sendSystemMessage(who, ALREADY_OPEN);
                }
                return SCRIPT_OVERRIDE;
            }
        }
        setOwner(self, who);
        return SCRIPT_CONTINUE;
    }
    public int OnClosedContainer(obj_id self, obj_id who) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "hasExplosive"))
        {
            setOwner(self, obj_id.NULL_ID);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!hasObjVar(transferer, ALTRUISM_OBJVAR))
        {
            sendSystemMessage(transferer, NO_REASON);
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(transferer, ALTRUISM_COMPLETE))
        {
            sendSystemMessage(transferer, NO_REASON);
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(item) || !exists(item))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(item);
        if (!template.equals("object/tangible/item/quest/hero_of_tatooine/explosives.iff"))
        {
            sendSystemMessage(transferer, NO_REASON);
            return SCRIPT_OVERRIDE;
        }
        int exp_id = rand(10000, 99999);
        utils.setScriptVar(self, "hasExplosive", exp_id);
        dictionary d = new dictionary();
        d.put("id", exp_id);
        d.put("player", transferer);
        messageTo(self, "handleExplosiveTimer", d, 10.0f, false);
        sendSystemMessage(transferer, EXPLOSIVE_SET);
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isIdValid(item) || !exists(item))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(item);
        if (!template.equals("object/tangible/item/quest/hero_of_tatooine/explosives.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, "hasExplosive");
        sendSystemMessage(transferer, EXPLOSIVE_REMOVED);
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int handleExplosiveTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "hasExplosive"))
        {
            return SCRIPT_CONTINUE;
        }
        int exp_id = utils.getIntScriptVar(self, "hasExplosive");
        int id = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (exp_id != id)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id control = getObjIdObjVar(self, ALTRUISM_CONTROL);
        if (!isIdValid(control))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(control, "handleExplosion", d, 1.0f, false);
        location loc = getLocation(self);
        location other_loc = new location(150.96f, -65.83f, -97.66f, loc.area, loc.cell);
        obj_id[] contents = getContents(loc.cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (!isPlayer(contents[i]))
            {
                continue;
            }
            playClientEffectLoc(contents[i], "clienteffect/lair_damage_heavy_shake.cef", loc, 0.0f);
            playClientEffectLoc(contents[i], "clienteffect/lair_damage_heavy_shake.cef", other_loc, 0.0f);
            break;
        }
        return SCRIPT_CONTINUE;
    }
}
