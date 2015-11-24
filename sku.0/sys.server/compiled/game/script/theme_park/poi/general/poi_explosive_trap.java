package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class poi_explosive_trap extends script.base_script
{
    public poi_explosive_trap()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "trap"))
        {
            setObjVar(self, "trap", rand(1, 100));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id opener) throws InterruptedException
    {
        int trapLevel = getIntObjVar(self, "trap");
        int skillLevel = 25;
        location here = getLocation(self);
        if (hasSkill(opener, "adventure_slicing_structure_specialist"))
        {
            skillLevel = rand(85, 100);
            if (skillLevel >= trapLevel)
            {
                String dialog = "pass";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String dialog = "fail";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                playClientEffectLoc(opener, "clienteffect/lair_hvy_damage_fire.cef", here, 0);
                destroyObject(self);
                return SCRIPT_OVERRIDE;
            }
        }
        if (hasSkill(opener, "adventure_slicing_structure_expert"))
        {
            skillLevel = rand(70, 90);
            if (skillLevel >= trapLevel)
            {
                String dialog = "pass";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String dialog = "fail";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                playClientEffectLoc(opener, "clienteffect/lair_hvy_damage_fire.cef", here, 0);
                destroyObject(self);
                return SCRIPT_OVERRIDE;
            }
        }
        if (hasSkill(opener, "adventure_slicing_structure_advanced"))
        {
            skillLevel = rand(45, 75);
            if (skillLevel >= trapLevel)
            {
                String dialog = "pass";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(self, passFail);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String dialog = "fail";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(self, passFail);
                playClientEffectLoc(opener, "clienteffect/lair_hvy_damage_fire.cef", here, 0);
                destroyObject(self);
                return SCRIPT_OVERRIDE;
            }
        }
        if (hasSkill(opener, "adventure_slicing_structure_intermediate"))
        {
            skillLevel = rand(20, 50);
            if (skillLevel >= trapLevel)
            {
                String dialog = "pass";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String dialog = "fail";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                playClientEffectLoc(opener, "clienteffect/lair_hvy_damage_fire.cef", here, 0);
                destroyObject(self);
                return SCRIPT_OVERRIDE;
            }
        }
        if (hasSkill(opener, "adventure_slicing_structure_basic"))
        {
            skillLevel = rand(1, 25);
            if (skillLevel >= trapLevel)
            {
                String dialog = "pass";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String dialog = "fail";
                string_id passFail = new string_id("skill.slicing", dialog);
                sendSystemMessage(opener, passFail);
                playClientEffectLoc(opener, "clienteffect/lair_hvy_damage_fire.cef", here, 0);
                destroyObject(self);
                return SCRIPT_OVERRIDE;
            }
        }
        else 
        {
            String dialog = "no_skill";
            string_id passFail = new string_id("skill.slicing", dialog);
            sendSystemMessage(opener, passFail);
            return SCRIPT_OVERRIDE;
        }
    }
}
