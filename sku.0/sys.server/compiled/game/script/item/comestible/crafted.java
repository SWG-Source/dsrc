package script.item.comestible;

import script.attrib_mod;
import script.library.consumable;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class crafted extends script.item.comestible.comestible
{
    public crafted()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        String food_type = getStringObjVar(self, "food_type");
        int count = getCount(self);
        if (count > 0)
        {
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[n] = "quantity";
            attribs[n] = Integer.toString(count);
        }
        if (hasObjVar(self, consumable.VAR_CONSUMABLE_STOMACH_VALUES))
        {
            int[] stomach = getIntArrayObjVar(self, consumable.VAR_CONSUMABLE_STOMACH_VALUES);
            if (stomach[0] > 0)
            {
                int n = utils.getValidAttributeIndex(names);
                if (n == -1)
                {
                    return SCRIPT_CONTINUE;
                }
                names[n] = "stomach_food";
                attribs[n] = Integer.toString(stomach[0]);
            }
            else if (stomach[1] > 0)
            {
                int n = utils.getValidAttributeIndex(names);
                if (n == -1)
                {
                    return SCRIPT_CONTINUE;
                }
                names[n] = "stomach_drink";
                attribs[n] = Integer.toString(stomach[1]);
            }
        }
        if (hasObjVar(self, consumable.VAR_CONSUMABLE_MODS))
        {
            attrib_mod[] am = getAttribModArrayObjVar(self, consumable.VAR_CONSUMABLE_MODS);
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[n] = EXAM_ATTRIB_MOD;
            if ((am == null) || (am.length == 0))
            {
                attribs[n] = EXAM_NONE;
                n++;
                if (n > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                int numMods = am.length;
                attribs[n] = "" + numMods;
                n++;
                if (n > names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                for (attrib_mod attrib_mod : am) {
                    int attrib = attrib_mod.getAttribute();
                    int val = Integer.MIN_VALUE;
                    int duration = (int) (attrib_mod.getDuration());
                    String sVal = "";
                    if (val > 0) {
                        sVal = "+" + val;
                    } else if (val < 0) {
                        sVal = "" + val;
                    }
                    int minutes = duration / 60;
                    int seconds = duration - (minutes * 60);
                    names[n] = toLower(consumable.STAT_NAME[attrib]);
                    attribs[n] = sVal + "% for " + minutes + "m " + seconds + "s";
                    LOG("examine", n + ": " + names[n] + " -> " + attribs[n]);
                    n++;
                    if (n > names.length) {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        if (hasObjVar(self, "skill_mod"))
        {
            String mod = getStringObjVar(self, "skill_mod.name");
            float amount = getFloatObjVar(self, "skill_mod.amount");
            float dur = getFloatObjVar(self, "skill_mod.dur");
            int duration = (int)dur;
            int minutes = duration / 60;
            int seconds = duration - (minutes * 60);
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            String lmod = localize(new string_id("stat_n", mod));
            names[n] = "skill";
            attribs[n] = lmod;
            n++;
            names[n] = "modifier";
            attribs[n] = "+" + amount;
            n++;
            names[n] = "duration";
            attribs[n] = minutes + "m " + seconds + "s";
        }
        if (hasObjVar(self, "mind_heal"))
        {
            int amount = getIntObjVar(self, "mind_heal");
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[n] = "mind_heal";
            attribs[n] = Integer.toString(amount);
        }
        if (hasObjVar(self, "delayed.type"))
        {
            String delay_type = getStringObjVar(self, "delayed.type");
            int delay_eff = getIntObjVar(self, "delayed.eff");
            int duration = getIntObjVar(self, "delayed.dur");
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[n] = "delay_effect";
            attribs[n] = localize(new string_id("obj_attr_n", delay_type + "_d"));
            if (delay_eff > 0)
            {
                n++;
                names[n] = delay_type + "_eff";
                attribs[n] = Integer.toString(delay_eff);
            }
            if (duration > 0)
            {
                n++;
                names[n] = delay_type + "_dur";
                attribs[n] = Integer.toString(duration);
            }
        }
        if (hasObjVar(self, "duration.type"))
        {
            String duration_type = getStringObjVar(self, "duration.type");
            int duration_eff = getIntObjVar(self, "duration.eff");
            int duration = getIntObjVar(self, "duration.dur");
            int minutes = duration / 60;
            int seconds = duration - (minutes * 60);
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[n] = "duration_effect";
            attribs[n] = localize(new string_id("obj_attr_n", duration_type + "_d"));
            if (duration_eff > 0)
            {
                n++;
                names[n] = duration_type + "_eff";
                attribs[n] = Integer.toString(duration_eff);
            }
            if (duration > 0)
            {
                n++;
                names[n] = "duration";
                attribs[n] = minutes + "m " + seconds + "s";
            }
        }
        if (hasObjVar(self, "instant.type"))
        {
            String instant_type = getStringObjVar(self, "instant.type");
            int instant_v1 = getIntObjVar(self, "instant.v1");
            int instant_v2 = getIntObjVar(self, "instant.v2");
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[n] = "instant_effect";
            attribs[n] = localize(new string_id("obj_attr_n", instant_type + "_d"));
            if (instant_v1 > 0)
            {
                n++;
                names[n] = instant_type + "_v1";
                attribs[n] = Integer.toString(instant_v1);
            }
            if (instant_v2 > 0)
            {
                n++;
                names[n] = instant_type + "_v2";
                attribs[n] = Integer.toString(instant_v2);
            }
        }
        if (hasObjVar(self, "race_restriction"))
        {
            int n = utils.getValidAttributeIndex(names);
            if (n == -1)
            {
                return SCRIPT_CONTINUE;
            }
            int race = getIntObjVar(self, "race_restriction");
            names[n] = "race_restriction";
            if (race == -2)
            {
                attribs[n] = localize(new string_id("obj_attr_n", "speciespet"));
            }
            else 
            {
                attribs[n] = localize(new string_id("obj_attr_n", "species" + race));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
