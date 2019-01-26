package script.test;

import script.library.pgc_quests;
import script.location;
import script.obj_id;

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
            for (obj_id player : players) {
                sendSystemMessage(player, message, "");
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
            for (String command1 : commands) {
                sendSystemMessage(self, command1, "");
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
                    for (String slotName : collctionSlots) {
                        if (slotName != null && slotName.length() > 0) {
                            if (getCollectionSlotValue(self, slotName) <= 0) {
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
                    for (String slotName : collctionSlots) {
                        if (slotName != null && slotName.length() > 0) {
                            while (getCollectionSlotValue(self, slotName) > 0) {
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
