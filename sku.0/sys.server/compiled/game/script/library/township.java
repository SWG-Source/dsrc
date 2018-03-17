package script.library;

import script.*;

import java.util.Vector;

public class township extends script.base_script
{
    public township()
    {
    }
    public static final int MIN_LEVEL = 70;
    public static final int TRAVEL_COST = 2500;
    public static final int RANK_TWO_AMOUNT = 200;
    public static final int RANK_THREE_AMOUNT = 300;
    public static final int RANK_FOUR_AMOUNT = 500;
    public static final int STAR_DESTROYER_COMM_DISTANCE = 500;
    public static final String DATATABLE_TOWNSHIP = "datatables/township/township.iff";
    public static final String DATATABLE_COLUMN_LOCATIONS = "locations";
    public static final String DATATABLE_COLUMN_SUB_GROUPS = "travelSubGroup";
    public static final String PID_VAR = "auriliaTravel";
    public static final String GROUPS_SCRIPT_VAR = "nexus.travel.groups";
    public static final String GROUPS_LOC_SCRIPTVAR = "nexus.travel.locGroups";
    public static final String LOCATIONS_SCRIPT_VAR = "nexus.travel.instances";
    public static final String OBJECT_FOR_SALE_ON_VENDOR = "onSaleVendorLite";
    public static final String MIDLITHE_CRYSTAL = "item_nova_orion_space_resource_01_01";
    public static final String OBJVAR_KNOWS_CRYSTAL = "township.knowsMidlithe";
    public static final String OBJVAR_NOVA_ORION_FACTION = "township.nova_orion_faction";
    public static final String ORION_TRACKER_SLOT_NAME = "orion_rank_tracker_01_01";
    public static final String NOVA_TRACKER_SLOT_NAME = "nova_rank_tracker_01_01";
    public static final String NOVA_ORION_RESET_DATATABLE = "datatables/quest/nova_orion/reset_nova_orion_quests.iff";
    public static final string_id PROMPT = new string_id("nexus", "aurillian_drop_ship_prompt");
    public static final string_id TITLE = new string_id("nexus", "aurillian_drop_ship_title");
    public static final String NOVA_ORION_FINALE_COMPLETED = "novaOrionCompletedFinale";
    public static final String NOVA_ORION_OBJECT_FOR_SALE_SCRIPT = "theme_park.dungeon.nova_orion_station.nova_orion_object_for_sale";
    public static final String MTP_LUMP = "item_meatlump_lump_01_01";
    public static final String MTP_OBJECT_FOR_SALE_SCRIPT = "theme_park.meatlump.mtp_object_for_sale";
    public static boolean isTownshipEligible(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "township.isTownshipEligible -- player is invalid.");
            return false;
        }
        return getLevel(player) >= MIN_LEVEL || space_skill.isMasterPilot(player);
    }
    public static boolean giveTravelListFromAurilia(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(npc) || !exists(npc))
        {
            return false;
        }
        if (sui.hasPid(player, township.PID_VAR))
        {
            int pid = sui.getPid(player, township.PID_VAR);
            forceCloseSUIPage(pid);
        }
        location npcLoc = getLocation(npc);
        utils.setScriptVar(player, "nexus.travel.npc_loc", npcLoc);
        utils.setScriptVar(player, "nexus.travel.pid", true);
        String[] subGroups = dataTableGetStringColumn(DATATABLE_TOWNSHIP, DATATABLE_COLUMN_SUB_GROUPS);
        String[] locations = dataTableGetStringColumn(DATATABLE_TOWNSHIP, DATATABLE_COLUMN_LOCATIONS);
        Vector resizeGroups = new Vector();
        resizeGroups.setSize(0);
        Vector localizedGroups = new Vector();
        localizedGroups.setSize(0);
        if (subGroups == null || locations == null)
        {
            return false;
        }
        if (subGroups.length != locations.length)
        {
            return false;
        }
        if (subGroups.length <= 0 || locations.length <= 0)
        {
            return false;
        }
        boolean foundAlready;
        for (String subGroup : subGroups) {
            foundAlready = false;
            for (Object resizeGroup : resizeGroups) {
                if (subGroup.equals(resizeGroup)) {
                    foundAlready = true;
                }
            }
            if (!foundAlready) {
                utils.addElement(localizedGroups, "@nexus:" + subGroup);
                utils.addElement(resizeGroups, subGroup);
            }
        }
        utils.setScriptVar(player, GROUPS_SCRIPT_VAR, resizeGroups);
        utils.setScriptVar(player, GROUPS_LOC_SCRIPTVAR, localizedGroups);
        int pid = sui.listbox(npc, player, utils.packStringId(PROMPT), sui.OK_CANCEL, utils.packStringId(TITLE), localizedGroups, "nexusTravelFromAurilliaSub", true);
        sui.setPid(player, pid, PID_VAR);
        return true;
    }
    public static boolean displayTravelListBySub(obj_id player, obj_id npc, String sub) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(npc) || !exists(npc))
        {
            return false;
        }
        if (sub == null || sub.equals(""))
        {
            return false;
        }
        if (sui.hasPid(player, township.PID_VAR))
        {
            int pid = sui.getPid(player, township.PID_VAR);
            forceCloseSUIPage(pid);
        }
        String[] subGroups = dataTableGetStringColumn(DATATABLE_TOWNSHIP, DATATABLE_COLUMN_SUB_GROUPS);
        String[] locations = dataTableGetStringColumn(DATATABLE_TOWNSHIP, DATATABLE_COLUMN_LOCATIONS);
        Vector resizeLocations = new Vector();
        resizeLocations.setSize(0);
        Vector localizedLocations = new Vector();
        localizedLocations.setSize(0);
        if (subGroups == null || locations == null)
        {
            return false;
        }
        if (subGroups.length != locations.length)
        {
            return false;
        }
        if (subGroups.length <= 0 || locations.length <= 0)
        {
            return false;
        }
        for (int i = 0; i < subGroups.length; ++i)
        {
            if (subGroups[i].equals(sub))
            {
                if (transition.hasPermissionForZone(player, locations[i], "initialRequiredFlag"))
                {
                    utils.addElement(resizeLocations, locations[i]);
                    utils.addElement(localizedLocations, "@nexus:" + locations[i]);
                }
                else 
                {
                    utils.addElement(resizeLocations, locations[i]);
                    utils.addElement(localizedLocations, "@nexus:" + locations[i] + "_no_perm");
                }
            }
        }
        utils.setScriptVar(player, LOCATIONS_SCRIPT_VAR, resizeLocations);
        int pid = sui.listbox(npc, player, utils.packStringId(PROMPT), sui.OK_CANCEL_REFRESH, utils.packStringId(TITLE), localizedLocations, "nexusTravelFromAurillia", false, false);
        sui.listboxUseOtherButton(pid, "Back");
        sui.showSUIPage(pid);
        sui.setPid(player, pid, PID_VAR);
        return true;
    }
    public static int getMidlitheCrystalCount(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return 0;
        }
        obj_id crystal = utils.getStaticItemInInventory(player, MIDLITHE_CRYSTAL);
        if (!isIdValid(crystal))
        {
            return 0;
        }
        return getCount(crystal);
    }
    public static void clearAllNovaOrionQuestStatus(obj_id player) throws InterruptedException
    {
        String[] questsToReset = dataTableGetStringColumnNoDefaults(NOVA_ORION_RESET_DATATABLE, "quest_name");
        for (String groundQuestName : questsToReset) {
            if (groundquests.isQuestActiveOrComplete(player, groundQuestName)) {
                groundquests.clearQuest(player, groundQuestName);
            }
        }
        if (hasObjVar(player, OBJVAR_NOVA_ORION_FACTION))
        {
            removeObjVar(player, OBJVAR_NOVA_ORION_FACTION);
        }
        String[] slotsToReset = dataTableGetStringColumnNoDefaults(NOVA_ORION_RESET_DATATABLE, "rank_slot_name");
        for (String slotRankName : slotsToReset) {
            long slotValue = getCollectionSlotValue(player, slotRankName);
            modifyCollectionSlotValue(player, slotRankName, (-1 * slotValue));
        }
        if (hasObjVar(player, NOVA_ORION_FINALE_COMPLETED))
        {
            removeObjVar(player, NOVA_ORION_FINALE_COMPLETED);
        }
        obj_var_list questList = getObjVarList(player, space_quest.QUEST_STATUS);
        if (questList != null)
        {
            int spaceQuests = questList.getNumItems();
            String questType;
            obj_var_list typeList;
            obj_var quest;
            String spaceQuestName;
            String questSeries;
            for (int sq = 0; sq < spaceQuests; ++sq)
            {
                questType = questList.getObjVar(sq).getName();
                typeList = questList.getObjVarList(questType);
                int questCount = typeList.getNumItems();
                for (int j = 0; j < questCount; j++)
                {
                    quest = typeList.getObjVar(j);
                    spaceQuestName = quest.getName();
                    questSeries = dataTableGetString("/datatables/spacequest/" + questType + "/" + spaceQuestName + ".iff", 0, "questSeries");
                    if (questSeries != null && questSeries.length() > 0)
                    {
                        if (questSeries.equals("nova_orion"))
                        {
                            space_quest.clearQuestFlags(player, questType, spaceQuestName);
                        }
                    }
                }
            }
        }
    }
    public static string_id getNovaOrionRumor(obj_id player) throws InterruptedException
    {
        String stringFile = "utterance/nova_orion";
        string_id rumor = new string_id(stringFile, "nova_orion_rumor_" + rand(1, 9));
        int otherRumor = rand(0, 19);
        if (groundquests.isQuestActiveOrComplete(player, "no_rank2_04"))
        {
            if (otherRumor > 13 && otherRumor < 16)
            {
                rumor = new string_id(stringFile, "nova_orion_katiara_rumor_" + rand(1, 3));
            }
        }
        if (groundquests.isQuestActiveOrComplete(player, "no_rank5_02") || groundquests.isQuestActiveOrComplete(player, "no_rank5_02_orion"))
        {
            if (otherRumor > 17)
            {
                rumor = new string_id(stringFile, "nova_orion_dark_jedi_rumor_" + rand(1, 3));
            }
        }
        return rumor;
    }
}
