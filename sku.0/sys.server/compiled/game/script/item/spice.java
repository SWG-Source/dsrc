package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.prose;
import script.library.utils;

public class spice extends script.base_script
{
    public spice()
    {
    }
    public static final string_id PROSE_CONSUME_ITEM = new string_id("base_player", "prose_consume_item");
    public static final string_id SID_ALREADY_HAVE_BUFF = new string_id("base_player", "food_already_have_buff");
    public static final string_id SID_BUFF_WONT_STACK = new string_id("base_player", "food_buff_wont_stack");
    public static final string_id SID_NO_EAT_DOWNER = new string_id("base_player", "food_no_eat_downer");
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        String buff_name = getStringObjVar(self, "spice.name");
        if (buff_name == null || buff_name.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        float duration = buff.getDuration(buff_name);
        String durString = formatTime((int)duration);
        names[idx] = "duration";
        attribs[idx] = "" + durString + "\n";
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "effect";
        attribs[idx] = " ";
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 1; i <= 5; i++)
        {
            String param = buff.getEffectParam(buff_name, i);
            float value = buff.getEffectValue(buff_name, i);
            if (param == null || param.equals(""))
            {
                continue;
            }
            param = param.toLowerCase();
            param = "food_" + param;
            names[idx] = param;
            if (value >= 0)
            {
                attribs[idx] = "+";
            }
            else 
            {
                attribs[idx] = "";
            }
            attribs[idx] += (int)value;
            if (param.indexOf("percent") >= 0)
            {
                attribs[idx] += "%";
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
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
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(player, "feign_death"))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            comedere(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void comedere(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(player))
        {
            return;
        }
        if (buff.hasBuff(player, "spice_downer"))
        {
            sendCombatSpamMessage(player, SID_NO_EAT_DOWNER, COMBAT_RESULT_GENERIC);
            return;
        }
        String buff_name = getStringObjVar(self, "spice.name");
        if (buff_name == null || buff_name.equals(""))
        {
            return;
        }
        if (buff.hasBuff(player, buff_name))
        {
            sendCombatSpamMessage(player, SID_ALREADY_HAVE_BUFF, COMBAT_RESULT_GENERIC);
            return;
        }
        if (!buff.canApplyBuff(player, buff_name))
        {
            sendCombatSpamMessage(player, SID_BUFF_WONT_STACK, COMBAT_RESULT_GENERIC);
            return;
        }
        buff.applyBuff(player, buff_name);
        String snd = "clienteffect/";
        switch (getSpecies(player))
        {
            case SPECIES_MON_CALAMARI:
            case SPECIES_RODIAN:
            case SPECIES_TRANDOSHAN:
            snd += "reptile_";
            break;
            case SPECIES_WOOKIEE:
            snd += "wookiee_";
            break;
            default:
            snd += "human_";
        }
        switch (getGender(player))
        {
            case GENDER_FEMALE:
            snd += "female_eat.cef";
            break;
            default:
            snd += "male_eat.cef";
        }
        playClientEffectLoc(player, snd, getLocation(player), getScale(player));
        prose_package pp = prose.getPackage(PROSE_CONSUME_ITEM, player, self);
        sendCombatSpamMessageProse(player, pp, COMBAT_RESULT_GENERIC);
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
    public String formatTime(int seconds) throws InterruptedException
    {
        String result = "";
        int hours = (seconds / 3600);
        seconds -= (hours * 3600);
        int minutes = (seconds / 60);
        seconds -= (minutes * 60);
        if (hours > 0)
        {
            result += "" + hours + ":";
        }
        if (minutes > 0 || hours > 0)
        {
            if (hours > 0 && minutes < 10)
            {
                result += "0";
            }
            result += "" + minutes + ":";
        }
        if (minutes > 0 && seconds < 10)
        {
            result += "0";
        }
        result += "" + seconds;
        return result;
    }
}
