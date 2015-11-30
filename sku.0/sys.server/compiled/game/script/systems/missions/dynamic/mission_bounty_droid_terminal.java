package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.money;

public class mission_bounty_droid_terminal extends script.systems.missions.base.mission_dynamic_base
{
    public mission_bounty_droid_terminal()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        string_id strBuyDroid = new string_id("mission/mission_generic", "buy_probe_droid");
        int mnu = mi.addRootMenu(menu_info_types.SERVER_PROBE_DROID_BUY, strBuyDroid);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_PROBE_DROID_BUY)
        {
            if (!hasSkill(player, "class_bountyhunter_phase1_novice"))
            {
                string_id strSpam = new string_id("mission/mission_generic", "not_bounty_hunter_terminal");
                sendSystemMessage(self, strSpam);
                return SCRIPT_CONTINUE;
            }
            String[] strEntries = new String[2];
            string_id strTextId = new string_id("mission/mission_generic", "droid_price_seeker");
            strEntries[0] = utils.packStringId(strTextId);
            strTextId = new string_id("mission/mission_generic", "droid_price_probot");
            strEntries[1] = utils.packStringId(strTextId);
            string_id strPromptId = new string_id("mission/mission_generic", "droid_purchase_prompt");
            String strPrompt = utils.packStringId(strPromptId);
            sui.listbox(self, player, strPrompt, strEntries, "buyDroid");
        }
        return SCRIPT_CONTINUE;
    }
    public int buyDroid(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerId = sui.getPlayerId(params);
        if ((playerId == null) || (playerId == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        int selRow = sui.getListboxSelectedRow(params);
        if (selRow == -1)
        {
            return SCRIPT_CONTINUE;
        }
        string_id strPromptId;
        String strPrompt;
        if (selRow == 0)
        {
            strPromptId = new string_id("mission/mission_generic", "droid_seeker_buy_confirmation");
            strPrompt = utils.packStringId(strPromptId);
            setObjVar(playerId, "intDroidToBuy", DROID_SEEKER);
        }
        else if (selRow == 1)
        {
            strPromptId = new string_id("mission/mission_generic", "droid_probot_buy_confirmation");
            strPrompt = utils.packStringId(strPromptId);
            setObjVar(playerId, "intDroidToBuy", DROID_PROBOT);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        String[] strText = new String[2];
        string_id strTextId = new string_id("mission/mission_generic", "yes");
        strText[0] = utils.packStringId(strTextId);
        strTextId = new string_id("mission/mission_generic", "no");
        strText[1] = utils.packStringId(strTextId);
        sui.listbox(self, playerId, strPrompt, strText, "finalize_Purchase");
        return SCRIPT_CONTINUE;
    }
    public int finalize_Purchase(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("droid_purchase", "Droid purchased");
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerId = sui.getPlayerId(params);
        if ((playerId == null) || (playerId == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        int selRow = sui.getListboxSelectedRow(params);
        LOG("droid_purchase", "Row was " + selRow);
        if (selRow == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (selRow == 0)
        {
            int intDroidToBuy = getIntObjVar(playerId, "intDroidToBuy");
            int intPrice = 0;
            removeObjVar(playerId, "intDroidToBuy");
            if (intDroidToBuy == DROID_PROBOT)
            {
                intPrice = 1000;
            }
            else if (intDroidToBuy == DROID_SEEKER)
            {
                intPrice = 200;
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
            dictionary dctParams = new dictionary();
            dctParams.put("intDroidToBuy", intDroidToBuy);
            dctParams.put("objPlayer", playerId);
            dctParams.put("intPrice", intPrice);
            transferBankCreditsToNamedAccount(playerId, money.ACCT_BOUNTY, intPrice, "droid_Purchase_Success", "droid_Purchase_Fail", dctParams);
            utils.moneyOutMetric(playerId, money.ACCT_BOUNTY, intPrice);
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_Purchase_Success(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = params.getObjId("objPlayer");
        obj_id objInventory = utils.getInventoryContainer(objPlayer);
        int intPrice = params.getInt("intPrice");
        if (objInventory == null)
        {
            transferBankCreditsFromNamedAccount(money.ACCT_BOUNTY, objPlayer, intPrice, "test1", "test2", params);
            utils.moneyInMetric(objPlayer, money.ACCT_BOUNTY, intPrice);
        }
        int intDroidToBuy = params.getInt("intDroidToBuy");
        String strTemplate;
        if (intDroidToBuy == DROID_SEEKER)
        {
            strTemplate = "object/tangible/mission/mission_bounty_droid_seeker.iff";
        }
        else if (intDroidToBuy == DROID_PROBOT)
        {
            strTemplate = "object/tangible/mission/mission_bounty_droid_probot.iff";
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objProbot = createObject(strTemplate, objInventory, "");
        if (objProbot == null)
        {
            string_id strSpam = new string_id("mission/mission_generic", "droid_buy_no_space");
            sendSystemMessage(objPlayer, strSpam);
            transferBankCreditsFromNamedAccount(money.ACCT_BOUNTY, objPlayer, intPrice, "test1", "test2", params);
            utils.moneyInMetric(objPlayer, money.ACCT_BOUNTY, intPrice);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id strSpam = new string_id("mission/mission_generic", "droid_purchase_complete");
            sendSystemMessage(objPlayer, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_Purchase_Fail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = params.getObjId("objPlayer");
        string_id strSpam = new string_id("mission/mission_generic", "droid_buy_nsf");
        sendSystemMessage(objPlayer, strSpam);
        return SCRIPT_CONTINUE;
    }
}
