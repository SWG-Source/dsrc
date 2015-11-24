package script.gm;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.gm;
import script.library.sui;
import script.library.utils;
import script.library.pclib;
import script.library.hue;
import script.library.money;

public class handler extends script.base_script
{
    public handler()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, gm.SCRIPT_HANDLER);
        return SCRIPT_CONTINUE;
    }
    public int confirmWipeItems(obj_id self, dictionary params) throws InterruptedException
    {
        gm.decrementMessageCount(self);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = utils.getObjIdScriptVar(self, gm.SCRIPTVAR_WIPEITEMS_TARGET);
        utils.removeScriptVar(self, gm.SCRIPTVAR_WIPEITEMS_PID);
        utils.removeScriptVar(self, gm.SCRIPTVAR_WIPEITEMS_TARGET);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "/wipeItems: unable to confirm a valid target!");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "/wipeItems: wiping items from (" + target + ")" + utils.getStringName(target));
        pclib.destroyPlayerEquipment(target);
        pclib.destroyPlayerInventory(target);
        pclib.destroyPlayerBank(target);
        return SCRIPT_CONTINUE;
    }
    public int handleHueVarUI(obj_id self, dictionary params) throws InterruptedException
    {
        gm.decrementMessageCount(self);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = utils.getObjIdScriptVar(self, gm.SCRIPTVAR_SETHUE_TARGET);
        String[] dta = utils.getStringArrayScriptVar(self, gm.SCRIPTVAR_SETHUE_DATA);
        gm.cleanupSetHueScriptVars(self);
        int selrow = sui.getListboxSelectedRow(params);
        if (selrow == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "/setHue: unable to confirm a valid target!");
            return SCRIPT_CONTINUE;
        }
        if (dta == null || dta.length == 0)
        {
            sendSystemMessageTestingOnly(self, "/setHue: unable to confirm valid data!");
            return SCRIPT_CONTINUE;
        }
        String toPass = "-target " + dta[selrow].substring(dta[selrow].length() - 1);
        queueCommand(self, (606342220), target, toPass, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int handleHueColorUI(obj_id self, dictionary params) throws InterruptedException
    {
        gm.decrementMessageCount(self);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = utils.getObjIdScriptVar(self, gm.SCRIPTVAR_SETHUE_TARGET);
        String varPath = utils.getStringScriptVar(self, gm.SCRIPTVAR_SETHUE_DATA);
        gm.cleanupSetHueScriptVars(self);
        int idx = sui.getColorPickerIndex(params);
        if (idx > -1)
        {
            hue.setColor(target, varPath, idx);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanupShowFaction(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, gm.SCRIPTVAR_SHOWFACTION_PID);
        gm.decrementMessageCount(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupMoneyStatus(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, gm.SCRIPTVAR_MONEY_PID);
        gm.decrementMessageCount(self);
        return SCRIPT_CONTINUE;
    }
    public int handleMoneyPass(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            gm.decrementMessageCount(self);
            return SCRIPT_CONTINUE;
        }
        int nextAction = params.getInt("nextAction");
        if (nextAction == gm.MA_NONE)
        {
            sendSystemMessageTestingOnly(self, "[CREDITS] Transaction request for (" + player + ")" + utils.getStringName(player) + " completed.");
            queueCommand(self, (2070221263), player, "-target balance", COMMAND_PRIORITY_DEFAULT);
            gm.decrementMessageCount(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            params.remove("nextAction");
            int amt = params.getInt(money.DICT_AMOUNT);
            switch (nextAction)
            {
                case gm.MA_WITHDRAW:
                withdrawCashFromBank(player, amt, gm.HANDLER_MONEY_PASS, gm.HANDLER_MONEY_FAIL, params);
                break;
                case gm.MA_TO_ACCT:
                transferBankCreditsToNamedAccount(player, money.ACCT_CUSTOMER_SERVICE, amt, gm.HANDLER_MONEY_PASS, gm.HANDLER_MONEY_FAIL, params);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMoneyFail(obj_id self, dictionary params) throws InterruptedException
    {
        gm.decrementMessageCount(self);
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "[CREDITS] Transaction request for (" + player + ") " + utils.getStringName(player) + " FAILED!");
        return SCRIPT_CONTINUE;
    }
    public int handleSearchCorpseSui(obj_id self, dictionary params) throws InterruptedException
    {
        gm.decrementMessageCount(self);
        obj_id target = utils.getObjIdScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_TARGET);
        utils.removeScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_TARGET);
        utils.removeScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_PID);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: your target(" + target + ") is not invalid!");
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] corpses = utils.getObjIdBatchObjVar(target, pclib.VAR_CORPSE_ID);
        if (corpses == null || corpses.length == 0)
        {
            removeObjVar(target, pclib.VAR_CORPSE_BASE);
            sendSystemMessageTestingOnly(self, "/searchCorpse: (" + target + ") " + utils.getStringName(target) + " has no corpse history!");
            return SCRIPT_CONTINUE;
        }
        obj_id corpseId = corpses[idx];
        if (!isIdValid(corpseId))
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: the selected index does not contain a valid corpse!");
            return SCRIPT_CONTINUE;
        }
        if (!exists(corpseId) || !corpseId.isLoaded())
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: the selected corpse either doesn't exist or is not loaded");
            return SCRIPT_CONTINUE;
        }
        obj_id wp = createWaypointInDatapad(self, corpseId);
        if (isIdValid(wp))
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: a waypoint to the selected corpse is now in your datapad!");
            setName(wp, "searchCorpse=" + corpseId);
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: the system was unable to create a waypoint for you to the selected corpse!");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetPlayerStateUI(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_OK && idx > -1)
        {
            if (exists(player))
            {
                if (utils.hasScriptVar(player, "setPlayerStateGM.pid"))
                {
                    String stateName = gm.PLAYER_STATE_NAMES[idx];
                    String newState = null;
                    int curState = getState(player, idx);
                    if (curState == 0)
                    {
                        setState(player, idx, true);
                        newState = "*** ON ***";
                    }
                    else 
                    {
                        setState(player, idx, false);
                        newState = "*** OFF ***";
                    }
                    String setStateMsg = "Set " + stateName + " state " + newState;
                    sendSystemMessageTestingOnly(self, setStateMsg);
                    CustomerServiceLog("player_state", getFirstName(self) + " (" + self + ") set the '" + stateName + "' state to " + newState + " for player " + getFirstName(player) + " (" + player + ")");
                    gm.showSetPlayerStateUI(player);
                }
            }
        }
        gm.decrementMessageCount(self);
        return SCRIPT_CONTINUE;
    }
    public int handleSetRankSelection(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id target = utils.getObjIdScriptVar(self, "setRank.target");
        utils.removeScriptVarTree(self, "setRank");
        if (btn == sui.BP_OK && idx > -1 && isIdValid(target))
        {
            queueCommand(self, (-912442852), target, Integer.toString(idx), COMMAND_PRIORITY_DEFAULT);
        }
        gm.decrementMessageCount(self);
        return SCRIPT_CONTINUE;
    }
    public int foundObject(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        location loc = params.getLocation("location");
        String scene = params.getString("scene");
        loc.area = scene;
        String sharedTemplateName = params.getString("sharedTemplateName");
        int pid = params.getInt("pid");
        obj_id[] containers = params.getObjIdArray("containers");
        boolean isAuthoritative = params.getBoolean("isAuthoritative");
        int cityId = params.getInt("cityId");
        String cityName = params.getString("cityName");
        obj_id residenceOf = params.getObjId("residenceOfId");
        String residenceOfName = params.getString("residenceOfName");
        String text = target + " (";
        if (playerExists(target))
        {
            text = text + getPlayerFullName(target);
        }
        else 
        {
            text = text + sharedTemplateName;
        }
        text = text + ") ";
        if (isAuthoritative)
        {
            text = text + "<authoritative>";
        }
        else 
        {
            text = text + "<proxy>";
        }
        text = text + " on server " + pid + ". " + loc.area + "(" + loc.x + ", " + loc.y + ", " + loc.z + ")";
        if (cityId != 0)
        {
            text = text + " in city (" + cityId + ":" + cityName + ")";
        }
        if (isIdValid(residenceOf))
        {
            text = text + " residence of (" + residenceOf;
            if ((residenceOfName != null) && (residenceOfName.length() > 0))
            {
                text = text + ":" + residenceOfName;
            }
            text = text + ")";
        }
        if (containers != null && containers.length > 0)
        {
            text = text + " contained by:";
            for (int i = 0; i < containers.length; ++i)
            {
                text = text + " " + containers[i];
            }
        }
        sendSystemMessageTestingOnly(self, text);
        return SCRIPT_CONTINUE;
    }
}
