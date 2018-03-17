package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;

public class fs_survey_quest extends script.base_script
{
    public fs_survey_quest()
    {
    }
    public int forceSensitiveQuestInfo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id resource = params.getObjId("resource");
        String sample = "";
        if (hasObjVar(self, "fs_quest.searchingFor"))
        {
            sample = getStringObjVar(self, "fs_quest.searchingFor");
        }
        boolean lookingFor = isResourceDerivedFrom(resource, sample);
        if (lookingFor == true)
        {
            int tries = 0;
            int needs = 0;
            if (utils.hasScriptVar(self, "fs_quest.fsNeeds"))
            {
                needs = utils.getIntScriptVar(self, "fs_quest.fsNeeds");
            }
            else 
            {
                needs = rand(5, 10);
                utils.setScriptVar(self, "fs_quest.fsNeeds", needs);
            }
            if (utils.hasScriptVar(self, "fs_quest.fsTries"))
            {
                tries = utils.getIntScriptVar(self, "fs_quest.fsTries");
                tries = tries + 1;
                utils.setScriptVar(self, "fs_quest.fsTries", tries);
            }
            else 
            {
                tries = 1;
                utils.setScriptVar(self, "fs_quest.fsTries", tries);
            }
            if (tries > needs)
            {
                obj_id backpack = utils.getInventoryContainer(self);
                obj_id madeObject = createObject("object/tangible/loot/quest/endrine.iff", backpack, null);
                if (madeObject != null)
                {
                    utils.removeScriptVar(self, "fs_quest");
                }
                else 
                {
                    debugSpeakMsg(self, "You got too much stuff in your backpack.  Get rid of some and keep sampling to find the resource you need.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
