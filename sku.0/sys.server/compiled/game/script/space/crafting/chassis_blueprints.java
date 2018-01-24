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
        switch(type){
            case "z95":
                return "@skl_n:pilot_rebel_navy_novice";
            case "ywing":
                return "@skl_n:pilot_rebel_navy_starships_01";
            case "ywing_longprobe":
                return "@skl_n:pilot_rebel_navy_starships_02";
            case "xwing":
                return "@skl_n:pilot_rebel_navy_starships_03";
            case "awing":
            case "twing":
                return "@skl_n:pilot_rebel_navy_starships_04";
            case "bwing":
            case "yk137r":
            case "gunship_rebel":
                return "@skl_n:pilot_rebel_navy_master";
            case "hutt_light_s01":
            case "hutt_light_s02":
                return "@skl_n:pilot_neutral_novice";
            case "hutt_medium_s01":
            case "hutt_medium_s02":
                return "@skl_n:pilot_neutral_starships_01";
            case "hutt_heavy_s01":
            case "hutt_heavy_s02":
            case "blacksun_light_s01":
            case "blacksun_light_s02":
            case "blacksun_light_s03":
            case "blacksun_light_s04":
                return "@skl_n:pilot_neutral_starships_02";
            case "blacksun_medium_s01":
            case "blacksun_medium_s02":
            case "blacksun_medium_s03":
            case "blacksun_medium_s04":
                return "@skl_n:pilot_neutral_starships_03";
            case "blacksun_heavy_s01":
            case "blacksun_heavy_s02":
            case "blacksun_heavy_s03":
            case "blacksun_heavy_s04":
            case "havoc":
                return "@skl_n:pilot_neutral_starships_04";
            case "yt1300":
            case "hutt_turret_ship":
            case "gunship_neutral":
            case "blacksun_heavy_vaksai":
                return "@skl_n:pilot_neutral_master";
            case "tie_light_duty":
                return "@skl_n:pilot_imperial_navy_novice";
            case "tiefighter":
                return "@skl_n:pilot_imperial_navy_starships_01";
            case "ti_in":
                return "@skl_n:pilot_imperial_navy_starships_02";
            case "tieinterceptor":
            case "tiebomber":
                return "@skl_n:pilot_imperial_navy_starships_03";
            case "tieadvanced":
            case "tieaggressor":
                return "@skl_n:pilot_imperial_navy_starships_04";
            case "tieoppressor":
            case "decimator":
            case "gunship_imperial":
            case "tiedefender":
                return "@skl_n:pilot_imperial_navy_master";
            case "firespray":
                return "@space_crafting_n:all_master";
            default:
                return "";
        }
    }
}
