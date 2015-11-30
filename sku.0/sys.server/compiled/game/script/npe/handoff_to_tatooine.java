package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class handoff_to_tatooine extends script.base_script
{
    public handoff_to_tatooine()
    {
    }
    public static final String questNewbieStart = "quest/speeder_quest";
    public static final String questNewbieStartBH = "quest/speeder_quest";
    public static final String questCrafterEntertainer = "quest/tatooine_eisley_noncombat";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        location origin = getLocation(self);
        location fighting = new location(3521f, 0.0f, -4821f, origin.area);
        location crafty = new location(3309.0f, 6.0f, -4785.0f, origin.area);
        String profession = getSkillTemplate(self);
        int crafter = profession.indexOf("trader");
        int entertainer = profession.indexOf("entertainer");
        int bountyhunter = profession.indexOf("bounty_hunter");
        if (crafter > -1 || entertainer > -1)
        {
            if (!groundquests.isQuestActiveOrComplete(self, questCrafterEntertainer))
            {
                groundquests.grantQuest(self, questCrafterEntertainer);
            }
        }
        else if (bountyhunter > -1)
        {
            if (groundquests.hasCompletedQuest(self, questNewbieStartBH) || groundquests.isQuestActive(self, questNewbieStartBH))
            {
                detachScript(self, "npe.handoff_to_tatooine");
            }
            else 
            {
                groundquests.requestGrantQuest(self, questNewbieStartBH);
            }
        }
        else 
        {
            if (groundquests.hasCompletedQuest(self, questNewbieStart) || groundquests.isQuestActive(self, questNewbieStart))
            {
                detachScript(self, "npe.handoff_to_tatooine");
            }
            else 
            {
                groundquests.requestGrantQuest(self, questNewbieStart);
            }
        }
        if (crafter > -1)
        {
            newbieTutorialSetToolbarElement(self, 10, "/survey");
        }
        newbieTutorialEnableHudElement(self, "radar", true, 0);
        detachScript(self, "npe.handoff_to_tatooine");
        return SCRIPT_CONTINUE;
    }
}
