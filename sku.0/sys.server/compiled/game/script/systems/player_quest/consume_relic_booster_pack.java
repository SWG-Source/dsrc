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

public class consume_relic_booster_pack extends script.base_script
{
    public consume_relic_booster_pack()
    {
    }
    public static final String PID_NAME = "chronicleBoosterPack";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        string_id openMenu = new string_id("saga_system", "booster_pack_open");
        if (hasObjVar(self, "chronicles.isStarterPack"))
        {
            openMenu = new string_id("saga_system", "booster_pack_starter_kit_menu");
        }
        if (utils.isNestedWithinAPlayer(self))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU11, openMenu);
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
                String openBoosterConfirm_prompt = "booster_pack_open_prompt";
                String openBoosterConfirm_title = "booster_pack_open_title";
                if (hasObjVar(self, "chronicles.isStarterPack"))
                {
                    openBoosterConfirm_prompt = "booster_pack_starter_kit_use_prompt";
                    openBoosterConfirm_title = "booster_pack_starter_kit_use_title";
                }
                getUiConsumeMessageBox(self, player, openBoosterConfirm_prompt, openBoosterConfirm_title, "handlerSuiBoosterPackOpen");
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
    public int handlerSuiBoosterPackOpen(obj_id self, dictionary params) throws InterruptedException
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
        boolean isStarterKit = false;
        if (hasObjVar(self, "chronicles.isStarterPack"))
        {
            isStarterKit = true;
        }
        String professionTemplate = getSkillTemplate(player);
        obj_id playerInventory = utils.getInventoryContainer(player);
        int relicQualitySkillmod = getEnhancedSkillStatisticModifierUncapped(player, pgc_quests.PGC_SKILLMOD_RELIC_QUALITY);
        Vector relicReferences = new Vector();
        relicReferences.setSize(0);
        Vector filteredRelicReferences = new Vector();
        filteredRelicReferences.setSize(0);
        String relicFilter = "";
        int filterChance = 49;
        if (hasObjVar(self, "chronicles.boosterPackCategory"))
        {
            relicFilter = getStringObjVar(self, "chronicles.boosterPackCategory");
            filterChance = 99;
        }
        else if (professionTemplate.startsWith("trader"))
        {
            relicFilter = "crafting";
        }
        else if (professionTemplate.startsWith("entertainer"))
        {
        }
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
                        if (relicFilter.length() > 0)
                        {
                            String[] relicLootCategories = split(row_lootCategory, ',');
                            boolean no_loot = false;
                            for (int k = 0; k < relicLootCategories.length; k++)
                            {
                                String relicCategory = relicLootCategories[k];
                                if (relicCategory.equals(relicFilter))
                                {
                                    filteredRelicReferences = utils.addElement(filteredRelicReferences, token_reference);
                                }
                            }
                        }
                    }
                }
            }
        }
        int numRelics = pgc_quests.BOOSTER_PACK_SIZE;
        if (isStarterKit)
        {
            numRelics = pgc_quests.STARTER_PACK_SIZE;
        }
        for (int j = 0; j < numRelics; j++)
        {
            String relicName = "";
            int choice = rand(0, 99);
            if (choice <= filterChance && filteredRelicReferences.size() > 0)
            {
                relicName = ((String)filteredRelicReferences.get(rand(0, filteredRelicReferences.size() - 1)));
            }
            else 
            {
                relicName = ((String)relicReferences.get(rand(0, relicReferences.size() - 1)));
            }
            int relicCount = rand(3, 6);
            if (relicName.startsWith("saga_relic_comm") || relicName.startsWith("saga_relic_goto"))
            {
                relicCount = rand(5, 10);
            }
            obj_id relic = static_item.createNewItemFunction(relicName, playerInventory, relicCount);
            if (isStarterKit)
            {
                pgc_quests.addRelicToQuestBuilder(player, relic, true);
            }
            else 
            {
                pgc_quests.sendPlacedInInventorySystemMessage(player, relic);
            }
            pgc_quests.logRelic(player, obj_id.NULL_ID, "Player opened a chronicles booster pack and received " + relicCount + " x " + relicName + "(" + relic + ")");
        }
        decrementCount(self);
        sui.removePid(player, PID_NAME);
        return SCRIPT_CONTINUE;
    }
}
