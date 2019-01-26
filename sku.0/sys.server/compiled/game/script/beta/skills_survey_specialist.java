package script.beta;

import script.library.skill;
import script.obj_id;

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
        for (String req : reqs) {
            grantSkill(self, req);
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
        for (String req : reqs) {
            revokeSkill(self, req);
        }
        return SCRIPT_CONTINUE;
    }
}
