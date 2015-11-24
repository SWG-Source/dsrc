package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.space_utils;
import script.library.utils;

public class chassis_blueprints extends script.base_script
{
    public chassis_blueprints()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ship_chassis.mass"))
        {
            float value = getFloatObjVar(self, "ship_chassis.mass");
            names[idx] = "chassisMassMax";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "ship_chassis.hp"))
        {
            float value = getFloatObjVar(self, "ship_chassis.hp");
            names[idx] = "hitPointsMax";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "shiptype"))
        {
            names[idx] = "pilotSkillRequired";
            attribs[idx] = getSkillRequiredForShip(self);
            idx++;
        }
        names[idx] = "ship_assembly_n";
        String text = " \\#pcontrast2 " + "$@obj_attr_n:ship_assembly_d$" + " \\# ";
        attribs[idx] = text;
        idx++;
        return SCRIPT_CONTINUE;
    }
    public String getSkillRequiredForShip(obj_id deed) throws InterruptedException
    {
        String type = getStringObjVar(deed, "shiptype");
        if (type == null)
        {
            return "";
        }
        else if (type.equals("z95"))
        {
            return "@skl_n:pilot_rebel_navy_novice";
        }
        else if (type.equals("xwing"))
        {
            return "@skl_n:pilot_rebel_navy_starships_03";
        }
        else if (type.equals("hutt_light_s01"))
        {
            return "@skl_n:pilot_neutral_novice";
        }
        else if (type.equals("hutt_light_s02"))
        {
            return "@skl_n:pilot_neutral_novice";
        }
        else if (type.equals("tiefighter"))
        {
            return "@skl_n:pilot_imperial_navy_starships_01";
        }
        else if (type.equals("tieinterceptor"))
        {
            return "@skl_n:pilot_imperial_navy_starships_03";
        }
        else if (type.equals("tieadvanced"))
        {
            return "@skl_n:pilot_imperial_navy_starships_04";
        }
        else if (type.equals("tieaggressor"))
        {
            return "@skl_n:pilot_imperial_navy_starships_04";
        }
        else if (type.equals("tiebomber"))
        {
            return "@skl_n:pilot_imperial_navy_starships_03";
        }
        else if (type.equals("tieoppressor"))
        {
            return "@skl_n:pilot_imperial_navy_master";
        }
        else if (type.equals("awing"))
        {
            return "@skl_n:pilot_rebel_navy_starships_04";
        }
        else if (type.equals("bwing"))
        {
            return "@skl_n:pilot_rebel_navy_master";
        }
        else if (type.equals("ywing"))
        {
            return "@skl_n:pilot_rebel_navy_starships_01";
        }
        else if (type.equals("firespray"))
        {
            return "@space_crafting_n:all_master";
        }
        else if (type.equals("yt1300"))
        {
            return "@skl_n:pilot_neutral_master";
        }
        else if (type.equals("decimator"))
        {
            return "@skl_n:pilot_imperial_navy_master";
        }
        else if (type.equals("ykl37r"))
        {
            return "@skl_n:pilot_rebel_navy_master";
        }
        else if (type.equals("hutt_heavy_s01"))
        {
            return "@skl_n:pilot_neutral_starships_02";
        }
        else if (type.equals("hutt_heavy_s02"))
        {
            return "@skl_n:pilot_neutral_starships_02";
        }
        else if (type.equals("hutt_medium_s01"))
        {
            return "@skl_n:pilot_neutral_starships_01";
        }
        else if (type.equals("hutt_medium_s02"))
        {
            return "@skl_n:pilot_neutral_starships_01";
        }
        else if (type.equals("tie_in"))
        {
            return "@skl_n:pilot_imperial_navy_starships_02";
        }
        else if (type.equals("tie_light_duty"))
        {
            return "@skl_n:pilot_imperial_navy_novice";
        }
        else if (type.equals("ywing_longprobe"))
        {
            return "@skl_n:pilot_rebel_navy_starships_02";
        }
        else if (type.equals("blacksun_light_s01"))
        {
            return "@skl_n:pilot_neutral_starships_02";
        }
        else if (type.equals("blacksun_light_s02"))
        {
            return "@skl_n:pilot_neutral_starships_02";
        }
        else if (type.equals("blacksun_light_s03"))
        {
            return "@skl_n:pilot_neutral_starships_02";
        }
        else if (type.equals("blacksun_light_s04"))
        {
            return "@skl_n:pilot_neutral_starships_02";
        }
        else if (type.equals("blacksun_heavy_s01"))
        {
            return "@skl_n:pilot_neutral_starships_04";
        }
        else if (type.equals("blacksun_heavy_s02"))
        {
            return "@skl_n:pilot_neutral_starships_04";
        }
        else if (type.equals("blacksun_heavy_s03"))
        {
            return "@skl_n:pilot_neutral_starships_04";
        }
        else if (type.equals("blacksun_heavy_s04"))
        {
            return "@skl_n:pilot_neutral_starships_04";
        }
        else if (type.equals("blacksun_medium_s01"))
        {
            return "@skl_n:pilot_neutral_starships_03";
        }
        else if (type.equals("blacksun_medium_s02"))
        {
            return "@skl_n:pilot_neutral_starships_03";
        }
        else if (type.equals("blacksun_medium_s03"))
        {
            return "@skl_n:pilot_neutral_starships_03";
        }
        else if (type.equals("blacksun_medium_s04"))
        {
            return "@skl_n:pilot_neutral_starships_03";
        }
        else if (type.equals("hutt_turret_ship"))
        {
            return "@skl_n:pilot_neutral_master";
        }
        else if (type.equals("gunship_neutral"))
        {
            return "@skl_n:pilot_neutral_master";
        }
        else if (type.equals("gunship_imperial"))
        {
            return "@skl_n:pilot_imperial_navy_master";
        }
        else if (type.equals("gunship_rebel"))
        {
            return "@skl_n:pilot_rebel_navy_master";
        }
        return "";
    }
}
