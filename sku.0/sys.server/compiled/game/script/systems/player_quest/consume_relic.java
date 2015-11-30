package script.systems.player_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.collection;
import script.library.groundquests;
import script.library.pgc_quests;
import script.library.prose;
import script.library.static_item;
import script.library.storyteller;
import script.library.sui;
import script.library.utils;

public class consume_relic extends script.base_script
{
    public consume_relic()
    {
    }
    public static final String PID_NAME = "chronicleRelicConsume";
    public static final int RELIC_CONSUME_ONE = menu_info_types.SERVER_MENU11;
    public static final int RELIC_DECONSTRUCT_ONE = menu_info_types.SERVER_MENU12;
    public static final int RELIC_DECONSTRUCT_ALL = menu_info_types.SERVER_MENU13;
    public static final int RELIC_CONSUME_ALL = menu_info_types.SERVER_MENU14;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithinAPlayer(self))
        {
            String relicData = getStringObjVar(self, pgc_quests.PGC_RELIC_SLOT_DATA_OBJVAR);
            String[] splitSlotNames = split(relicData, ':');
            String slotName = splitSlotNames[1];
            String collectionName = splitSlotNames[0];
            string_id consumeSid = new string_id("saga_system", "relic_consume");
            string_id consumeAllSid = new string_id("saga_system", "relic_consume_all");
            if (!pgc_quests.canUseRelic(player, self, slotName, collectionName, false))
            {
                consumeSid = new string_id("saga_system", "relic_consume_red");
                consumeAllSid = new string_id("saga_system", "relic_consume_all_red");
            }
            int menuConsume = mi.addRootMenu(RELIC_CONSUME_ONE, consumeSid);
            mi.addSubMenu(menuConsume, RELIC_CONSUME_ALL, consumeAllSid);
            int menuDeconstruct = mi.addRootMenu(RELIC_DECONSTRUCT_ONE, new string_id("saga_system", "relic_deconstruct"));
            mi.addSubMenu(menuDeconstruct, RELIC_DECONSTRUCT_ALL, new string_id("saga_system", "relic_deconstruct_all"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (utils.isNestedWithinAPlayer(self))
        {
            if (item == RELIC_CONSUME_ONE || item == RELIC_CONSUME_ALL)
            {
                if (sui.hasPid(player, PID_NAME))
                {
                    int pid = sui.getPid(player, PID_NAME);
                    forceCloseSUIPage(pid);
                }
                String staticItemName = getStaticItemName(self);
                String relicData = getStringObjVar(self, pgc_quests.PGC_RELIC_SLOT_DATA_OBJVAR);
                String[] splitSlotNames = split(relicData, ':');
                String slotName = splitSlotNames[1];
                String collectionName = splitSlotNames[0];
                if (!hasCompletedCollection(player, collectionName))
                {
                    if (!hasCompletedCollectionSlot(player, slotName) && !slotName.equals(""))
                    {
                        if (item == RELIC_CONSUME_ONE)
                        {
                            pgc_quests.addRelicToQuestBuilder(player, self, false);
                        }
                        else if (item == RELIC_CONSUME_ALL)
                        {
                            pgc_quests.addRelicToQuestBuilder(player, self, true);
                        }
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("saga_system", "relic_count_at_max"));
                        pgc_quests.logRelic(player, obj_id.NULL_ID, "Player attempted to add " + staticItemName + "(slot: " + slotName + ") to their relic collection (" + collectionName + "), but that relic is already at max amount.");
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    sendSystemMessage(player, new string_id("saga_system", "relic_count_at_max"));
                    pgc_quests.logRelic(player, obj_id.NULL_ID, "Player attempted to add " + staticItemName + "(slot: " + slotName + ") to their relic collection, but they have already maxed out every single available relic of this type, " + collectionName + ".");
                    return SCRIPT_CONTINUE;
                }
            }
            else if (item == RELIC_DECONSTRUCT_ONE)
            {
                deconstructRelic(player, self, false);
            }
            else if (item == RELIC_DECONSTRUCT_ALL)
            {
                deconstructRelic(player, self, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self))
        {
            String staticItemName = getStaticItemName(self);
            dictionary row = dataTableGetRow(storyteller.STORYTELLER_DATATABLE, staticItemName);
            int relic_tier = row.getInt("relic_tier");
            names[idx] = "pgc_relic_quality";
            attribs[idx] = "" + relic_tier;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean getUiConsumeMessageBox(obj_id self, obj_id player, String prompt, String title, String handler) throws InterruptedException
    {
        if (!isValidId(self) || !isValidId(player))
        {
            return false;
        }
        string_id prompt_sid = new string_id("saga_system", prompt);
        string_id title_sid = new string_id("saga_system", title);
        int pid = sui.msgbox(self, player, "@" + prompt_sid, sui.YES_NO, "@" + title_sid, handler);
        sui.setPid(player, pid, PID_NAME);
        return true;
    }
    public int handlerSuiAddToQuestBuilder(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            if (utils.hasScriptVar(self, "relic_addQuestBuilderAll"))
            {
                utils.removeScriptVar(self, "relic_addQuestBuilderAll");
            }
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            if (utils.hasScriptVar(self, "relic_addQuestBuilderAll"))
            {
                utils.removeScriptVar(self, "relic_addQuestBuilderAll");
            }
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            if (utils.hasScriptVar(self, "relic_addQuestBuilderAll"))
            {
                utils.removeScriptVar(self, "relic_addQuestBuilderAll");
            }
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            if (utils.hasScriptVar(self, "relic_addQuestBuilderAll"))
            {
                utils.removeScriptVar(self, "relic_addQuestBuilderAll");
            }
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            sui.removePid(player, PID_NAME);
            if (utils.hasScriptVar(self, "relic_addQuestBuilderAll"))
            {
                utils.removeScriptVar(self, "relic_addQuestBuilderAll");
            }
            return SCRIPT_CONTINUE;
        }
        boolean addEntireStack = false;
        if (utils.hasScriptVar(self, "relic_addQuestBuilderAll"))
        {
            addEntireStack = true;
            utils.removeScriptVar(self, "relic_addQuestBuilderAll");
        }
        pgc_quests.addRelicToQuestBuilder(player, self, addEntireStack);
        sui.removePid(player, PID_NAME);
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiRelicDeconstruct(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            if (utils.hasScriptVar(self, "relic_deconstructAll"))
            {
                utils.removeScriptVar(self, "relic_deconstructAll");
            }
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            if (utils.hasScriptVar(self, "relic_deconstructAll"))
            {
                utils.removeScriptVar(self, "relic_deconstructAll");
            }
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            if (utils.hasScriptVar(self, "relic_deconstructAll"))
            {
                utils.removeScriptVar(self, "relic_deconstructAll");
            }
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            if (utils.hasScriptVar(self, "relic_deconstructAll"))
            {
                utils.removeScriptVar(self, "relic_deconstructAll");
            }
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            sui.removePid(player, PID_NAME);
            if (utils.hasScriptVar(self, "relic_deconstructAll"))
            {
                utils.removeScriptVar(self, "relic_deconstructAll");
            }
            return SCRIPT_CONTINUE;
        }
        boolean deconstructEntireStack = false;
        if (utils.hasScriptVar(self, "relic_deconstructAll"))
        {
            deconstructEntireStack = true;
            utils.removeScriptVar(self, "relic_deconstructAll");
        }
        deconstructRelic(player, self, deconstructEntireStack);
        sui.removePid(player, PID_NAME);
        return SCRIPT_CONTINUE;
    }
    public void deconstructRelic(obj_id player, obj_id self, boolean deconstructEntireStack) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return;
        }
        obj_id playerInventory = utils.getInventoryContainer(player);
        String relicData = getStringObjVar(self, pgc_quests.PGC_RELIC_SLOT_DATA_OBJVAR);
        String[] splitSlotNames = split(relicData, ':');
        String slotName = splitSlotNames[1];
        String collectionName = splitSlotNames[0];
        String[] relicCategoryData = getCollectionSlotCategoryInfo(slotName);
        int relicLevel = pgc_quests.getTaskLevel(relicCategoryData);
        int numToDeconstruct = 1;
        int stackcount = getCount(self);
        if (deconstructEntireStack && stackcount > 0)
        {
            numToDeconstruct = stackcount;
        }
        int actualCount = 0;
        obj_id fragment = obj_id.NULL_ID;
        for (int i = 0; i < numToDeconstruct; i++)
        {
            int fragmentCount = 1 + rand(0, 1 + relicLevel / 20);
            actualCount += fragmentCount;
            fragment = static_item.createNewItemFunction(pgc_quests.PGC_CHRONICLES_RELIC_FRAGMENT, playerInventory, fragmentCount);
            pgc_quests.logRelic(player, obj_id.NULL_ID, "Player deconstructed a relic into fragments and received " + fragmentCount + " x " + pgc_quests.PGC_CHRONICLES_RELIC_FRAGMENT + "(" + fragmentCount + ")");
            decrementCount(self);
        }
        if (isIdValid(fragment))
        {
            pgc_quests.sendPlacedInInventorySystemMessage(player, fragment, actualCount);
        }
        return;
    }
}
