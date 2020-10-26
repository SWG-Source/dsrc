package script.space.quest_logic;

import script.dictionary;
import script.library.space_quest;
import script.obj_id;

public class delivery_no_pickup extends script.space.quest_logic.delivery
{
    public delivery_no_pickup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "delivering", 1);
        setObjVar(self, "delivery_nopickup", 1);
		String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if ((questName == null) || (questType == null))
        {
            return SCRIPT_CONTINUE;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
		dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null && isGod(player))
        {
            sendSystemMessageTestingOnly(player, "Debug: Failed to open quest table " + qTable);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "pickupShip", questInfo.getString("pickupShip"));
        setObjVar(self, "deliveryShip", questInfo.getString("deliveryShip"));
        setObjVar(self, "pickupPoint", questInfo.getString("pickupPoint"));
        setObjVar(self, "deliveryPoint", questInfo.getString("deliveryPoint"));
        int numAttackShipLists = questInfo.getInt("numAttackShipLists");
        setObjVar(self, "numAttackShipLists", numAttackShipLists);
        for (int i = 0; i < numAttackShipLists; i++)
        {
            String[] attackShips = dataTableGetStringColumn(qTable, "attackShips" + (i + 1));
            space_quest.cleanArray(self, "attackShips" + (i + 1), attackShips);
        }
        setObjVar(self, "attackPeriod", questInfo.getInt("attackPeriod"));
        setObjVar(self, "attackListType", questInfo.getInt("attackListType"));
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        if ((getCurrentSceneName()).startsWith(questZone))
        {
            dictionary outparams = new dictionary();
            outparams.put("player", player);
            messageTo(self, "initializedQuestPlayer", outparams, 1.0f, false);
        }
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 0, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int deliveryNoPickupAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        if (attackPeriod > 0)
        {
            messageTo(self, "launchAttack", null, attackPeriod, false);
        }
        return SCRIPT_OVERRIDE;
    }
}
