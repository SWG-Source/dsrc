package script.working.jfreeman;

import script.obj_id;

public class skilltest extends script.base_script
{
    public skilltest()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equalsIgnoreCase("choose"))
        {
            playUiEffect(self, "showMediator=ws_professiontemplateselect");
            return SCRIPT_CONTINUE;
        }
        if (text.equalsIgnoreCase("reset"))
        {
            debugSpeakMsg(self, "resetting");
            setSkillTemplate(self, "");
            setWorkingSkill(self, "");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        setHealth(self, getMaxHealth(self));
        return SCRIPT_CONTINUE;
    }
}
