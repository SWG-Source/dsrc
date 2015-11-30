package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.skill;

public class skills_survey_specialist extends script.base_script
{
    public skills_survey_specialist()
    {
    }
    public static final String SKILL_NAME = "crafting_artisan_master";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String[] reqs = skill.getAllRequiredSkills(SKILL_NAME);
        if ((reqs == null) || (reqs.length == 0))
        {
            debugSpeakMsg(self, "Unable to assign all skills related to " + SKILL_NAME);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < reqs.length; i++)
        {
            grantSkill(self, reqs[i]);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        String[] reqs = skill.getAllRequiredSkills(SKILL_NAME);
        if ((reqs == null) || (reqs.length == 0))
        {
            debugSpeakMsg(self, "Unable to revoke all skills related to " + SKILL_NAME);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < reqs.length; i++)
        {
            revokeSkill(self, reqs[i]);
        }
        return SCRIPT_CONTINUE;
    }
}
