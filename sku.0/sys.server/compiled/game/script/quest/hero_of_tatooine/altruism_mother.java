package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class altruism_mother extends script.base_script
{
    public altruism_mother()
    {
    }
    public static final String ALTRUISM_OBJVAR = "quest.hero_of_tatooine.altruism";
    public static final String ALTRUISM_LEADER = ALTRUISM_OBJVAR + ".leader";
    public static final String ALTRUISM_ACTIVE = ALTRUISM_OBJVAR + ".active";
    public static final String ALTRUISM_DAUGHTER = ALTRUISM_ACTIVE + ".daughter";
    public int handleStartFollowing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id leader = getObjIdObjVar(self, ALTRUISM_LEADER);
        ai_lib.aiFollow(self, leader, 1.5f, 3.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id daughter = getObjIdObjVar(self, ALTRUISM_DAUGHTER);
        if (isIdValid(daughter))
        {
            ai_lib.aiFollow(daughter, self, 1.5f, 3.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        attachScript(self, "conversation.quest_hero_of_tatooine_mother");
        return SCRIPT_CONTINUE;
    }
    public int OnFollowPathNotFound(obj_id self, obj_id target) throws InterruptedException
    {
        attachScript(self, "conversation.quest_hero_of_tatooine_mother");
        return SCRIPT_CONTINUE;
    }
}
