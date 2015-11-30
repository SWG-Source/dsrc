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
import script.library.player_stomach;
import script.library.prose;
import script.library.utils;

public class food extends script.base_script
{
    public food()
    {
    }
    public static final string_id SID_PET_ONLY = new string_id("base_player", "food_pet_only");
    public static final string_id SID_TOO_FULL = new string_id("base_player", "food_too_full");
    public static final string_id SID_ALREADY_HAVE_BUFF = new string_id("base_player", "food_already_have_buff");
    public static final string_id SID_BUFF_WONT_STACK = new string_id("base_player", "food_buff_wont_stack");
    public static final string_id SID_BURST_RUN_NOT_READY = new string_id("base_player", "food_burst_run_not_ready");
    public static final string_id SID_NOT_POISONED = new string_id("base_player", "food_not_poisoned");
    public static final string_id SID_NOT_DISEASED = new string_id("base_player", "food_not_diseased");
    public static final string_id SID_STOMACH_EMPTY = new string_id("base_player", "food_stomach_empty");
    public static final string_id SID_NO_EAT_DOWNER = new string_id("base_player", "food_no_eat_downer");
    public static final string_id SID_BUFF_DUR_OVER_MAX = new string_id("base_player", "food_over_max_duration");
    public static final string_id PROSE_CONSUME_ITEM = new string_id("base_player", "prose_consume_item");
    public static final float BURST_RUN_V1_VALUES = 0.33f;
    public static final float BURST_RUN_V2_VALUES = 0.66f;
    public static final float INCREASE_HUNGER_VALUE = 15.0f;
    public static final String[] CLICKY_COMBAT_REMOVED_BUFFS = 
    {
        "none"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String templateName = getTemplateName(self);
        if (templateName.equals("object/tangible/food/crafted/dessert_felbar.iff") || templateName.equals("object/tangible/food/crafted/dessert_blap_biscuit.iff") || templateName.equals("object/tangible/food/crafted/dessert_pastebread.iff") || templateName.equals("object/tangible/food/crafted/dessert_sweesonberry_rolls.iff"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        String buff_name = getStringObjVar(self, "buff_name");
        if (buff_name == null || buff_name.equals("") || isClickyCombatNerfed(buff_name))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        float dur = 1f;
        if (hasObjVar(self, "duration"))
        {
            dur = getFloatObjVar(self, "duration");
        }
        names[idx] = "duration";
        float duration = buff.getDuration(buff_name);
        duration *= dur;
        String durString = formatTime((int)duration);
        attribs[idx] = "" + durString + "\n";
        attribs[idx] += "\n" + "The duration of the buff from this item can be increased cumulatively by eating more of the same food " + "as long as the resulting duration does not exceed 2 hours. \n\n" + "Note that the value of primary buffed modifier of the last one eaten will be the one used by the buff.";
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        float eff = 1f;
        if (hasObjVar(self, "effectiveness"))
        {
            eff = getFloatObjVar(self, "effectiveness");
        }
        
        {
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
                if (param.equals("script_var"))
                {
                    if (i == 1)
                    {
                        value *= eff;
                    }
                    attribs[idx] = "" + value;
                    if (buff_name.equals("drink_ruby_bliel"))
                    {
                        names[idx] = "food_stimpack_roundtime";
                        attribs[idx] = "30";
                    }
                    else if (buff_name.equals("drink_starshine_surprise") || buff_name.equals("dessert_cavaellin_creams"))
                    {
                        names[idx] = "food_incap_recovery";
                    }
                    else if (buff_name.equals("dish_ormachek"))
                    {
                        names[idx] = "food_xp_bonus";
                    }
                    else if (buff_name.equals("dessert_pyollian_cake"))
                    {
                        names[idx] = "food_assembly_bonus";
                    }
                    else if (buff_name.equals("dessert_gorrnar"))
                    {
                        names[idx] = "food_reduce_clone_wound";
                    }
                    else if (buff_name.equals("dessert_smugglers_delight"))
                    {
                        names[idx] = "food_reduce_spice_downer";
                    }
                    else if (buff_name.equals("drink_tssolok"))
                    {
                        names[idx] = "food_inc_buff_time";
                    }
                    else if (buff_name.equals("drink_bespin_port"))
                    {
                        names[idx] = "food_exp_bonus";
                    }
                    else 
                    {
                        attribs[idx] = "";
                    }
                }
                else 
                {
                    param = param.toLowerCase();
                    param = "food_" + param;
                    names[idx] = param;
                    if (i == 1)
                    {
                        value = (int)(value * eff);
                    }
                    if (value >= 0)
                    {
                        attribs[idx] = "+";
                    }
                    attribs[idx] += "" + value;
                    if (param.indexOf("percent") >= 0)
                    {
                        attribs[idx] += "%";
                    }
                }
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
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
            return;
        }
        String buff_name = getStringObjVar(self, "buff_name");
        int[] filling = new int[2];
        if (hasObjVar(self, "filling"))
        {
            filling = getIntArrayObjVar(self, "filling");
        }
        else if (hasObjVar(self, "filling_type") && hasObjVar(self, "filling_amount"))
        {
            filling[0] = 0;
            filling[1] = 0;
        }
        else 
        {
            filling[0] = 0;
            filling[1] = 0;
        }
        if (buff_name == null || buff_name.equals("") || isClickyCombatNerfed(buff_name))
        {
            return;
        }
        if (hasObjVar(self, "pet_only"))
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
        if (!buff.canApplyBuff(player, buff_name))
        {
            sendSystemMessage(player, SID_BUFF_WONT_STACK);
            return;
        }
        float value = buff.getEffectValue(buff_name, 1);
        float duration = buff.getDuration(buff_name);
        value *= eff;
        duration *= dur;
        float timeLeft = 0;
        boolean removeOldBuff = false;
        if (buff.hasBuff(player, buff_name))
        {
            timeLeft = buff.getBuffTimeRemaining(player, buff_name);
            removeOldBuff = true;
        }
        float newDuration = timeLeft + duration;
        float maxDuration = 2 * 60 * 60;
        if (newDuration > maxDuration)
        {
            sendSystemMessage(player, SID_BUFF_DUR_OVER_MAX);
            return;
        }
        if (removeOldBuff)
        {
            buff.removeBuff(player, buff_name);
        }
        success = buff.applyBuff(player, buff_name, newDuration, value);
        if (success)
        {
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
    public boolean instantFoodBurstRunEffect(obj_id target, float costReduction, float cooldownReduction, float dur, float eff) throws InterruptedException
    {
        if (buff.hasBuff(target, "burstRun"))
        {
            return false;
        }
        if (!buff.canApplyBuff(target, "burstRun"))
        {
            return false;
        }
        float timeLeft = getCooldownTimeLeft(target, "burstRun");
        if (timeLeft > 0.0f)
        {
            return false;
        }
        costReduction *= eff;
        cooldownReduction *= dur;
        costReduction = 1.0f - costReduction;
        cooldownReduction = 1.0f - cooldownReduction;
        utils.setScriptVar(target, "food.burst_run.cost", costReduction);
        utils.setScriptVar(target, "food.burst_run.cooldown", cooldownReduction);
        if (!queueCommand(target, (-63103822), null, "", COMMAND_PRIORITY_FRONT))
        {
            return false;
        }
        return true;
    }
    public boolean instantCurePoisonEffect(obj_id target) throws InterruptedException
    {
        if (!dot.isPoisoned(target))
        {
            return false;
        }
        String[] dot_ids = dot.getAllDotsType(target, dot.DOT_POISON);
        if (dot_ids == null || dot_ids.length == 0)
        {
            return false;
        }
        int i = rand(0, (dot_ids.length - 1));
        if (!dot.removeDotEffect(target, dot_ids[i]))
        {
            return false;
        }
        return true;
    }
    public boolean instantCureDiseaseEffect(obj_id target) throws InterruptedException
    {
        if (!dot.isDiseased(target))
        {
            return false;
        }
        String[] dot_ids = dot.getAllDotsType(target, dot.DOT_DISEASE);
        if (dot_ids == null || dot_ids.length == 0)
        {
            return false;
        }
        int i = rand(0, (dot_ids.length - 1));
        if (!dot.removeDotEffect(target, dot_ids[i]))
        {
            return false;
        }
        return true;
    }
    public boolean instantHungryEffect(obj_id target, float eff) throws InterruptedException
    {
        int hunger = (int)(INCREASE_HUNGER_VALUE * eff);
        if (player_stomach.getStomach(target, 0) <= 0)
        {
            return false;
        }
        if (!player_stomach.addToStomach(target, 0, (-1 * hunger)))
        {
            return false;
        }
        return true;
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
    public boolean isClickyCombatNerfed(String buff_name) throws InterruptedException
    {
        for (int nameChecker = 0; nameChecker < CLICKY_COMBAT_REMOVED_BUFFS.length; nameChecker++)
        {
            if (buff_name.equals(CLICKY_COMBAT_REMOVED_BUFFS[nameChecker]))
            {
                return true;
            }
        }
        return false;
    }
}
