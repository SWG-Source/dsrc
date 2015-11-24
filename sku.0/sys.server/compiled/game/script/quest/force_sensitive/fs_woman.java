package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.prose;
import script.library.chat;
import script.library.quests;
import script.library.fs_quests;

public class fs_woman extends script.base_script
{
    public fs_woman()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("fs_woman_messagerange", 12, true);
        messageTo(self, "approach", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int approach(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = null;
        owner = getObjIdObjVar(self, "fs_dath_woman.holder");
        setMovementRun(self);
        if ((isIdValid(owner)) && (exists(owner)))
        {
            ai_lib.aiFollow(self, owner);
        }
        messageTo(self, "approachFail", null, 180, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id target) throws InterruptedException
    {
        if (volumeName.equals("fs_woman_messagerange"))
        {
            obj_id owner = getObjIdObjVar(self, "fs_dath_woman.holder");
            if ((isIdValid(owner)) && (exists(owner)))
            {
                if (owner == target)
                {
                    if (!hasObjVar(self, "contact"))
                    {
                        setObjVar(self, "contact", 1);
                        messageTo(self, "timeout", null, 180, false);
                        prose_package pp = prose.getPackage(new string_id("quest/force_sensitive/intro", "woman_greeting"), target);
                        chat.publicChat(self, target, null, null, pp);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int approachFail(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "contact"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = null;
        owner = getObjIdObjVar(self, "fs_dath_woman.holder");
        int quest_id = quests.getQuestId("fs_goto_dath");
        clearCompletedQuest(owner, quest_id);
        quest_id = quests.getQuestId("fs_dath_woman");
        clearCompletedQuest(owner, quest_id);
        quest_id = quests.getQuestId("fs_dath_woman_talk");
        clearCompletedQuest(owner, quest_id);
        messageTo(self, "cleanUp", null, 180, false);
        return SCRIPT_CONTINUE;
    }
    public int timeout(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = null;
        owner = getObjIdObjVar(self, "fs_dath_woman.holder");
        if ((isIdValid(owner)) && (exists(owner)))
        {
            sendSystemMessage(owner, new string_id("quest/force_sensitive/intro", "woman_leave"));
        }
        int quest_id = quests.getQuestId("fs_goto_dath");
        clearCompletedQuest(owner, quest_id);
        quest_id = quests.getQuestId("fs_dath_woman");
        clearCompletedQuest(owner, quest_id);
        quest_id = quests.getQuestId("fs_dath_woman_talk");
        clearCompletedQuest(owner, quest_id);
        messageTo(self, "cleanUp", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
