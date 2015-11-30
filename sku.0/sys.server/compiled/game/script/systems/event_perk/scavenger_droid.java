package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.prose;

public class scavenger_droid extends script.base_script
{
    public scavenger_droid()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "storytellerid"))
        {
            messageTo(self, "cleanupProp", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "event_perk.scavenger.initialized", 0);
        setObjVar(self, "event_perk.scavenger.item_count", 1);
        String[] itemTemplateList = 
        {
            "Default"
        };
        String[] itemNameList = 
        {
            "Default"
        };
        int[] nameTypeList = 
        {
            0
        };
        setObjVar(self, "event_perk.scavenger.item_template_list", itemTemplateList);
        setObjVar(self, "event_perk.scavenger.item_name_list", itemNameList);
        setObjVar(self, "event_perk.scavenger.name_type_list", nameTypeList);
        setObjVar(self, "event_perk.scavenger.leader_name", "Nobody");
        setObjVar(self, "event_perk.scavenger.top_score", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "storytellerid"))
        {
            messageTo(self, "cleanupProp", null, 3, false);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "event_perk.scavenger.patched"))
        {
            setObjVar(self, "event_perk.scavenger.patched", 030105);
            resetList(self, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.scavenger.initialized");
        int itemCount = getIntObjVar(self, "event_perk.scavenger.item_count");
        obj_id owner = getObjIdObjVar(self, "storytellerid");
        String[] itemTemplateList = getStringArrayObjVar(self, "event_perk.scavenger.item_template_list");
        String[] itemNameList = getStringArrayObjVar(self, "event_perk.scavenger.item_name_list");
        int[] nameTypeList = getIntArrayObjVar(self, "event_perk.scavenger.name_type_list");
        if (initialized == 0 && player == owner)
        {
            if (itemCount > 10)
            {
                sendSystemMessage(player, new string_id("event_perk", "scavenger_max_items"));
                return SCRIPT_CONTINUE;
            }
            String itemTemplate = getTemplateName(item);
            String itemName = getName(item);
            if (itemTemplateList != null || itemTemplateList.length != 0)
            {
                for (int i = 0; i < itemTemplateList.length; i++)
                {
                    if (itemTemplateList[i].equals(itemTemplate))
                    {
                        sendSystemMessage(player, new string_id("event_perk", "scavenger_already_added"));
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            String[] newItemTemplateList = new String[itemCount + 1];
            String[] newItemNameList = new String[itemCount + 1];
            int[] newNameTypeList = new int[itemCount + 1];
            newItemTemplateList[itemCount] = itemTemplate;
            newItemNameList[itemCount] = itemName;
            if ((hasObjVar(item, "crafting.source_schematic") && !itemTemplate.startsWith("object/tangible/component")) || itemTemplate.startsWith("object/resource_container") || hasScript(item, "name.name") || hasScript(item, "theme_park.utils.datapad_text"))
            {
                newNameTypeList[itemCount] = 1;
            }
            else 
            {
                newNameTypeList[itemCount] = 0;
            }
            for (int i = 0; i < itemTemplateList.length; i++)
            {
                newItemTemplateList[i] = itemTemplateList[i];
                newItemNameList[i] = itemNameList[i];
                newNameTypeList[i] = nameTypeList[i];
            }
            itemCount++;
            setObjVar(self, "event_perk.scavenger.item_template_list", newItemTemplateList);
            setObjVar(self, "event_perk.scavenger.item_name_list", newItemNameList);
            setObjVar(self, "event_perk.scavenger.name_type_list", newNameTypeList);
            setObjVar(self, "event_perk.scavenger.item_count", itemCount);
            if ((hasObjVar(item, "crafting.source_schematic") && !itemTemplate.startsWith("object/tangible/component")) || itemTemplate.startsWith("object/resource_container") || hasScript(item, "name.name") || hasScript(item, "theme_park.utils.datapad_text"))
            {
                sendSystemMessage(player, itemName, null);
            }
            else 
            {
                string_id itemNameSID = parseNameToStringId(itemName, player);
                sendSystemMessage(player, itemNameSID);
            }
        }
        if (initialized == 1)
        {
            if (!hasScript(player, "systems.event_perk.scavenger_player"))
            {
                attachScript(player, "systems.event_perk.scavenger_player");
            }
            if (!hasObjVar(player, "event_perk.scavenger.items_found"))
            {
                int[] itemsFound = new int[itemNameList.length];
                for (int i = 0; i < itemsFound.length; i++)
                {
                    itemsFound[i] = 0;
                }
                setObjVar(player, "event_perk.scavenger.items_found", itemsFound);
            }
            dictionary params = new dictionary();
            String itemTemplate = getTemplateName(item);
            String itemName = getName(item);
            if ((hasObjVar(item, "crafting.source_schematic") && !itemTemplate.startsWith("object/tangible/component")) || itemTemplate.startsWith("object/resource_container") || hasScript(item, "name.name") || hasScript(item, "theme_park.utils.datapad_text"))
            {
                sendSystemMessage(player, new string_id("event_perk", "scavenger_you_showed_me"));
                sendSystemMessage(player, itemName, null);
            }
            else 
            {
                string_id itemNameSID = parseNameToStringId(itemName, player);
                sendSystemMessage(player, new string_id("event_perk", "scavenger_you_showed_me"));
                sendSystemMessage(player, itemNameSID);
            }
            if (itemTemplateList != null || itemTemplateList.length != 0)
            {
                for (int i = 0; i < itemTemplateList.length; i++)
                {
                    if (itemTemplateList[i].equals(itemTemplate))
                    {
                        params.put("itemIndex", i);
                        params.put("droid", self);
                        messageTo(player, "checkForPossibleScore", params, 0.5f, false);
                    }
                }
            }
        }
        if (initialized == 2)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.scavenger.initialized");
        obj_id owner = getObjIdObjVar(self, "storytellerid");
        if (initialized == 0 && owner == player)
        {
            int menu1 = mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("event_perk", "scavenger_instruction"));
            int menu2 = mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("event_perk", "scavenger_list_items"));
            int menu3 = mi.addRootMenu(menu_info_types.SERVER_MENU5, new string_id("event_perk", "scavenger_reset_list"));
            int menu4 = mi.addRootMenu(menu_info_types.SERVER_MENU6, new string_id("event_perk", "scavenger_start"));
        }
        if (initialized == 1)
        {
            int menu5 = mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("event_perk", "scavenger_list_items"));
            int menu6 = mi.addRootMenu(menu_info_types.SERVER_MENU8, new string_id("event_perk", "scavenger_instruction"));
            int menu8 = mi.addRootMenu(menu_info_types.SERVER_MENU7, new string_id("event_perk", "scavenger_list_leader"));
        }
        if (initialized == 2)
        {
            int menu7 = mi.addRootMenu(menu_info_types.SERVER_MENU9, new string_id("event_perk", "scavenger_show_winner"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        int initialized = getIntObjVar(self, "event_perk.scavenger.initialized");
        if (item == menu_info_types.SERVER_MENU3)
        {
            if (initialized == 1 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            showSetupInstructions(self, player);
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            if (initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            String[] itemNameList = getStringArrayObjVar(self, "event_perk.scavenger.item_name_list");
            String[] itemNameSui = new String[itemNameList.length];
            int[] nameTypeList = getIntArrayObjVar(self, "event_perk.scavenger.name_type_list");
            if (itemNameSui == null || itemNameSui.length == 0 || itemNameSui.length == 1)
            {
                sendSystemMessage(player, new string_id("event_perk", "scavenger_no_items"));
                return SCRIPT_CONTINUE;
            }
            for (int i = 1; i < itemNameList.length; i++)
            {
                if (nameTypeList[i] == 0)
                {
                    itemNameSui[i - 1] = "@" + itemNameList[i];
                }
                if (nameTypeList[i] == 1)
                {
                    itemNameSui[i - 1] = itemNameList[i];
                }
            }
            sui.listbox(self, player, "@event_perk:scavenger_list_d", sui.OK_ONLY, "@event_perk:scavenger_list_t", itemNameSui, "absolutelyWorthlessSuiHandler", true);
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            if (initialized == 1 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            resetList(self, player);
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            if (initialized == 1 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            String[] itemNameList = getStringArrayObjVar(self, "event_perk.scavenger.item_name_list");
            String[] itemNameSui = new String[itemNameList.length];
            if (itemNameSui == null || itemNameSui.length == 0 || itemNameSui.length == 1)
            {
                sendSystemMessage(player, new string_id("event_perk", "scavenger_no_items"));
                return SCRIPT_CONTINUE;
            }
            float rightNow = getGameTime();
            setObjVar(self, "event_perk.scavenger.game_start_time", rightNow);
            setObjVar(self, "event_perk.scavenger.initialized", 1);
            announceStatusToPlayers(self, "scavenger_game_start");
            CustomerServiceLog("EventPerk", "(Scavenger Droid - [" + self + "] was initialized for play by player [" + player + "].");
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            if (initialized == 0 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            showGameInstructions(self, player);
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            if (initialized == 0 || initialized == 2)
            {
                return SCRIPT_CONTINUE;
            }
            String leaderName = getStringObjVar(self, "event_perk.scavenger.leader_name");
            prose_package showLeader = new prose_package();
            showLeader = prose.getPackage(new string_id("event_perk", "scavenger_show_current_leader"), leaderName);
            sendSystemMessageProse(player, showLeader);
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            if (initialized == 1 || initialized == 0)
            {
                return SCRIPT_CONTINUE;
            }
            int winnerTime = getIntObjVar(self, "event_perk.scavenger.winner_time");
            String leaderName = getStringObjVar(self, "event_perk.scavenger.leader_name");
            prose_package showWinner = new prose_package();
            showWinner = prose.getPackage(new string_id("event_perk", "scavenger_show_leader"), leaderName, winnerTime);
            sendSystemMessageProse(player, showWinner);
            messageTo(player, "handleScavengerCleanup", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int updateMasterScoreCard(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String leaderName = getStringObjVar(self, "event_perk.scavenger.leader_name");
        String playerName = getName(player);
        int itemCount = getIntObjVar(self, "event_perk.scavenger.item_count");
        int maxScore = itemCount - 1;
        int topScore = getIntObjVar(self, "event_perk.scavenger.top_score");
        int playerScore = getIntObjVar(player, "event_perk.scavenger.my_score");
        if (playerScore == maxScore)
        {
            float rightNow = getGameTime();
            float gameStartTime = getFloatObjVar(self, "event_perk.scavenger.game_start_time");
            float rawTimeToWin = rightNow - gameStartTime;
            float timeToWinMinutesFloat = rawTimeToWin / 60;
            int timeToWinMinutes = (int)timeToWinMinutesFloat;
            setObjVar(self, "event_perk.scavenger.winner_time", timeToWinMinutes);
            setObjVar(self, "event_perk.scavenger.initialized", 2);
            setObjVar(self, "event_perk.scavenger.leader_name", playerName);
            sendSystemMessage(player, new string_id("event_perk", "scavenger_you_win"));
            announceStatusToPlayers(self, "scavenger_winner");
            return SCRIPT_CONTINUE;
        }
        if (playerScore >= topScore)
        {
            setObjVar(self, "event_perk.scavenger.leader_name", playerName);
            setObjVar(self, "event_perk.scavenger.top_score", playerScore);
            sendSystemMessage(player, new string_id("event_perk", "scavenger_you_lead"));
            return SCRIPT_CONTINUE;
        }
        if (playerScore < topScore)
        {
            prose_package notLeader = new prose_package();
            notLeader = prose.getPackage(new string_id("event_perk", "scavenger_not_leader"), leaderName);
            sendSystemMessageProse(player, notLeader);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public string_id parseNameToStringId(String itemName, obj_id player) throws InterruptedException
    {
        String[] parsedString = split(itemName, ':');
        string_id itemNameSID;
        if (parsedString.length > 1)
        {
            String stfFile = parsedString[0];
            String reference = parsedString[1];
            itemNameSID = new string_id(stfFile, reference);
        }
        else 
        {
            String stfFile = parsedString[0];
            itemNameSID = new string_id(stfFile, " ");
        }
        return itemNameSID;
    }
    public int showSetupInstructions(obj_id pcd, obj_id player) throws InterruptedException
    {
        string_id textMsg = new string_id("event_perk", "scavenger_setup_instruction");
        String prompt = getString(textMsg);
        string_id titleMsg = new string_id("event_perk", "scavenger_instruction");
        String title = getString(titleMsg);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, pcd, player, "");
        setSUIProperty(pid, "", "Size", "250,250");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        sui.showSUIPage(pid);
        return pid;
    }
    public int showGameInstructions(obj_id pcd, obj_id player) throws InterruptedException
    {
        string_id textMsg = new string_id("event_perk", "scavenger_game_instruction");
        String prompt = getString(textMsg);
        string_id titleMsg = new string_id("event_perk", "scavenger_instruction");
        String title = getString(titleMsg);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, pcd, player, "");
        setSUIProperty(pid, "", "Size", "250,250");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        sui.showSUIPage(pid);
        return pid;
    }
    public void resetList(obj_id self, obj_id player) throws InterruptedException
    {
        setObjVar(self, "event_perk.scavenger.item_count", 1);
        String[] itemTemplateList = 
        {
            "Default"
        };
        String[] itemNameList = 
        {
            "Default"
        };
        int[] nameTypeList = 
        {
            0
        };
        setObjVar(self, "event_perk.scavenger.item_template_list", itemTemplateList);
        setObjVar(self, "event_perk.scavenger.item_name_list", itemNameList);
        setObjVar(self, "event_perk.scavenger.name_type_list", nameTypeList);
        sendSystemMessage(player, new string_id("event_perk", "scavenger_list_reset_msg"));
        return;
    }
    public void announceStatusToPlayers(obj_id self, String messageId) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 256.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (int i = 0; i < objPlayers.length; i++)
            {
                sendSystemMessage(objPlayers[i], new string_id("event_perk", messageId));
            }
        }
    }
}
