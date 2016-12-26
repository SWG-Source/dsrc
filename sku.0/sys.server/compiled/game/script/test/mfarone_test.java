package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.badge;
import script.library.buff;
import script.library.chat;
import script.library.corpse;
import script.library.create;
import script.library.factions;
import script.library.groundquests;
import script.library.holiday;
import script.library.hue;
import script.library.instance;
import script.library.pet_lib;
import script.library.pgc_quests;
import script.library.prose;
import script.library.skill_template;
import script.library.static_item;
import script.library.storyteller;
import script.library.sui;
import script.library.trial;
import script.library.township;
import script.library.utils;
import script.library.vehicle;

public class mfarone_test extends script.base_script
{
    public mfarone_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.mfarone_test");
        }
        else if (hasObjVar(self, "idiot_testing"))
        {
            sendSystemMessage(self, "mfarone_test script attached.", "");
        }
        else 
        {
            sendSystemMessage(self, "Attaching script failed.", "");
            detachScript(self, "test.mfarone_test");
        }
        return SCRIPT_CONTINUE;
    }
    public void areaDebugMessaging(obj_id self, String message) throws InterruptedException
    {
        obj_id[] players = getAllPlayers(getLocation(getTopMostContainer(self)), 35.0f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessage(players[i], message, "");
            }
        }
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = self;
        location here = getLocation(player);
        int stringCheck = -1;
        obj_id target = getIntendedTarget(self);
        if (!isIdValid(target))
        {
            target = getLookAtTarget(self);
        }
        String[] commands = 
        {
            "idiot_pgc_grant_all_tasks",
            "idiot_pgc_revoke_all_tasks"
        };
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String command = st.nextToken();
        stringCheck = text.indexOf("idiot_pgc_list");
        if (stringCheck > -1)
        {
            for (int i = 0; i < commands.length; i++)
            {
                sendSystemMessage(self, commands[i], "");
            }
        }
        stringCheck = text.indexOf("idiot_pgc_grant_all_tasks");
        if (stringCheck > -1)
        {
            for (int i = 0; i < pgc_quests.ALL_PGC_COLLECTION_TASK_NAMES.length; i++)
            {
                String collectionName = pgc_quests.ALL_PGC_COLLECTION_TASK_NAMES[i];
                String[] collctionSlots = getAllCollectionSlotsInCollection(collectionName);
                if (collctionSlots != null && collctionSlots.length > 0)
                {
                    for (int j = 0; j < collctionSlots.length; j++)
                    {
                        String slotName = collctionSlots[j];
                        if (slotName != null && slotName.length() > 0)
                        {
                            if (getCollectionSlotValue(self, slotName) <= 0)
                            {
                                modifyCollectionSlotValue(self, slotName, 1);
                            }
                        }
                    }
                }
            }
            return SCRIPT_OVERRIDE;
        }
        stringCheck = text.indexOf("idiot_pgc_revoke_all_tasks");
        if (stringCheck > -1)
        {
            for (int i = 0; i < pgc_quests.ALL_PGC_COLLECTION_TASK_NAMES.length; i++)
            {
                String collectionName = pgc_quests.ALL_PGC_COLLECTION_TASK_NAMES[i];
                String[] collctionSlots = getAllCollectionSlotsInCollection(collectionName);
                if (collctionSlots != null && collctionSlots.length > 0)
                {
                    for (int j = 0; j < collctionSlots.length; j++)
                    {
                        String slotName = collctionSlots[j];
                        if (slotName != null && slotName.length() > 0)
                        {
                            while (getCollectionSlotValue(self, slotName) > 0)
                            {
                                modifyCollectionSlotValue(self, slotName, -1);
                            }
                        }
                    }
                }
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
