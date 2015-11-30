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
import script.library.static_item;
import script.library.storyteller;
import script.library.sui;
import script.library.utils;

public class consume_fragment extends script.base_script
{
    public consume_fragment()
    {
    }
    public static final String PID_NAME = "chronicleFragmentReconstruct";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithinAPlayer(self) && getCount(self) >= 10)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU11, new string_id("saga_system", "fragment_recontruct"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (utils.isNestedWithinAPlayer(self))
        {
            if (item == menu_info_types.SERVER_MENU11)
            {
                if (getCount(self) >= 10)
                {
                    reconstructFragment(player, self);
                }
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
    public int handlerSuiFragmentReconstruct(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        reconstructFragment(player, self);
        return SCRIPT_CONTINUE;
    }
    public void reconstructFragment(obj_id player, obj_id self) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return;
        }
        obj_id playerInventory = utils.getInventoryContainer(player);
        int relicQualitySkillmod = getEnhancedSkillStatisticModifierUncapped(player, pgc_quests.PGC_SKILLMOD_RELIC_QUALITY);
        Vector relicReferences = new Vector();
        relicReferences.setSize(0);
        int num_items = dataTableGetNumRows(storyteller.STORYTELLER_DATATABLE);
        for (int i = 0; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(storyteller.STORYTELLER_DATATABLE, i);
            if (row != null && !row.isEmpty())
            {
                int relic_tier = row.getInt("relic_tier");
                if (relic_tier > 0 && relicQualitySkillmod >= relic_tier)
                {
                    String row_lootCategory = row.getString("relicLootCatergory");
                    if (row_lootCategory.indexOf("no_loot") < 0)
                    {
                        String token_reference = row.getString("name");
                        relicReferences = utils.addElement(relicReferences, token_reference);
                    }
                }
            }
        }
        int relicMasterySkillmod = getEnhancedSkillStatisticModifierUncapped(player, pgc_quests.PGC_SKILLMOD_RELIC_MASTERY);
        String relicName = ((String)relicReferences.get(rand(0, relicReferences.size() - 1)));
        int relicCount = 1;
        if (relicQualitySkillmod > 0)
        {
            relicCount += rand(1, relicQualitySkillmod);
        }
        obj_id relic = static_item.createNewItemFunction(relicName, playerInventory, relicCount);
        pgc_quests.sendPlacedInInventorySystemMessage(player, relic);
        pgc_quests.logRelic(player, obj_id.NULL_ID, "Player reconstructed some fragments into a chronicle relic and received " + relicCount + " x " + relicName + "(" + relic + ")");
        incrementCount(self, -10);
        if (getCount(self) <= 0)
        {
            destroyObject(self);
        }
        sui.removePid(player, PID_NAME);
        return;
    }
}
