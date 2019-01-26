package script.beta;

import script.library.player_version;
import script.library.xp;
import script.obj_id;

public class tc_jedi extends script.base_script
{
    public tc_jedi()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        revokeSkills(self);
        revokeExperience(self);
        if (hasObjVar(self, "tcTester"))
        {
            setObjVar(self, "jedi.totalPoints", 32);
        }
        else 
        {
            setObjVar(self, "jedi.totalPoints", 250);
        }
        attachScript(self, "player.player_jedi_conversion");
        detachScript(self, "beta.tc_jedi");
        return SCRIPT_CONTINUE;
    }
    public void revokeSkills(obj_id objPlayer) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(objPlayer);
        int count = 0;
        while (skillList != null && count < 15)
        {
            skillList = player_version.orderSkillListForRevoke(skillList);
            if ((skillList != null) && (skillList.length > 0))
            {
                for (String s : skillList) {
                    revokeSkill(objPlayer, s);
                }
            }
            skillList = getSkillListingForPlayer(objPlayer);
            count++;
        }
        if (hasScript(objPlayer, "player.species_innate"))
        {
            detachScript(objPlayer, "player.species_innate");
            attachScript(objPlayer, "player.species_innate");
        }
    }
    public void revokeExperience(obj_id objPlayer) throws InterruptedException
    {
        if (objPlayer == null || !isIdValid(objPlayer))
        {
            return;
        }
        String xpList[] = xp.getXpTypes(objPlayer);
        if (xpList != null && xpList.length > 0)
        {
            for (String s : xpList) {
                int xpAmount = getExperiencePoints(objPlayer, s);
                if (xpAmount > 0) {
                    grantExperiencePoints(objPlayer, s, -xpAmount);
                }
            }
        }
    }
}
