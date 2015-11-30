package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.npe;
import script.library.sui;
import script.library.groundquests;

public class trigger_journal extends script.base_script
{
    public trigger_journal()
    {
    }
    public static final float MAX_DISTANCE = 5.0f;
    public static final string_id TOO_FAR = new string_id("npe", "gamma_travel_too_far");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, npe.QUEST_REWORK_VAR))
        {
            int reGrantSeries = getIntObjVar(self, npe.QUEST_REWORK_VAR);
            if (reGrantSeries < npe.QUEST_ENUMERATION)
            {
                npe.reGrantReWorkedQuests(self);
                npe.clearActiveSpaceQuests(self);
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        npe.reGrantReWorkedQuests(self);
        npe.clearActiveSpaceQuests(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        groundquests.sendSignal(self, "npe_elevator_atrium_down");
        groundquests.sendSignal(self, "npe_elevator_droid_up");
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String strAction) throws InterruptedException
    {
        if (strAction.equals("clientReady"))
        {
            if (!utils.hasObjVar(self, "npe.pop_chat"))
            {
                setYaw(self, -78.0f);
                dictionary dic = new dictionary();
                dic.put("player", self);
                messageTo(self, "showChat", null, 1, false);
                messageTo(self, "showEscape", null, 12, false);
                utils.setObjVar(self, "npe.pop_chat", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillGranted(obj_id self, String skill) throws InterruptedException
    {
        String playerTemplate = getSkillTemplate(self);
        if (!utils.isProfession(self, utils.TRADER) && !utils.isProfession(self, utils.ENTERTAINER))
        {
            if (!hasObjVar(self, "npe.firstSkillGranted"))
            {
                setObjVar(self, "npe.firstSkillGranted", true);
                npe.sendDelayed3poPopup(self, 0, 12, "sound/vo_c3po_17c.snd", "npe", "pop_first_ability", "npe.pop_first_ability");
                newbieTutorialHighlightUIElement(self, "/GroundHUD.Toolbar.volume.0", 5.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCombatLevelChanged(obj_id self, int oldCombatLevel, int newCombatLevel) throws InterruptedException
    {
        if (newCombatLevel == 2)
        {
            if (!hasObjVar(self, "npe.secondLevelGranted"))
            {
                setObjVar(self, "npe.secondLevelGranted", true);
                npe.sendDelayed3poPopup(self, 0, 11, "sound/vo_c3po_18c.snd", "npe", "pop_first_level", "npe.pop_first_level");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int showChat(obj_id self, dictionary params) throws InterruptedException
    {
        newbieTutorialHighlightUIElement(self, "/GroundHUD.ChatWindow", 7.0f);
        npe.giveChatPopUp(self);
        return SCRIPT_CONTINUE;
    }
    public int showEscape(obj_id self, dictionary params) throws InterruptedException
    {
        newbieTutorialHighlightUIElement(self, "/GroundHUD.ButtonBar.bigMenuPage", 7.0f);
        npe.giveEscPopUp(self);
        return SCRIPT_CONTINUE;
    }
    public int handTransfer(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (btn == sui.BP_OK)
        {
            npe.removeAllQuests(player);
            npe.movePlayerFromSharedStationToFinishLocation(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int makeUiElementAnimate(obj_id self, dictionary params) throws InterruptedException
    {
        int location = params.getInt("location");
        float time = params.getFloat("time");
        newbieTutorialHighlightUIElement(self, "/GroundHUD.Toolbar.volume." + location, time);
        return SCRIPT_CONTINUE;
    }
    public int doDelayed3POMessage(obj_id self, dictionary params) throws InterruptedException
    {
        String strFile = params.getString("strFile");
        String strMessage = params.getString("strMessage");
        String scriptVarName = params.getString("scriptVarName");
        String soundFile = params.getString("soundFile");
        int duration = params.getInt("duration");
        obj_id player = params.getObjId("player");
        string_id threePioMessage = new string_id(strFile, strMessage);
        int hadNotif = utils.getIntScriptVar(player, scriptVarName);
        if (hadNotif == 0)
        {
            obj_id building = getTopMostContainer(player);
            obj_id droid = utils.getObjIdScriptVar(building, "objDroidInvis");
            npe.commTutorialPlayer(droid, player, duration, threePioMessage, soundFile, "object/mobile/c_3po.iff");
            utils.setScriptVar(player, scriptVarName, 1);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int groupPopUp1(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasObjVar(self, "npe.pop_group_1"))
        {
            npe.giveGroupPopUp1(self);
            setObjVar(self, "npe.pop_group_1", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int groupPopUp2(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasObjVar(self, "npe.pop_group_2"))
        {
            npe.giveGroupPopUp2(self);
            setObjVar(self, "npe.pop_group_2", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int npeGammaTransfer(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        location terminalLoc = utils.getLocationScriptVar(player, "npe.gamma_travel.term_loc");
        location playerLoc = getLocation(player);
        if (btn == sui.BP_OK)
        {
            if (getDistance(terminalLoc, playerLoc) < MAX_DISTANCE)
            {
                npe.movePlayerFromSharedStationToOrdMantellDungeon(player);
                groundquests.sendSignal(player, "leaving_station");
            }
            else 
            {
                sendSystemMessage(self, TOO_FAR);
            }
            utils.removeScriptVarTree(player, "npe.gamma_travel");
        }
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVarTree(player, "npe.gamma_travel");
        }
        utils.removeScriptVarTree(player, "npe.gamma_travel");
        return SCRIPT_CONTINUE;
    }
    public int npeStationTransfer(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        location terminalLoc = utils.getLocationScriptVar(player, "npe.gamma_travel.term_loc");
        location playerLoc = getLocation(player);
        if (btn == sui.BP_OK)
        {
            if (getDistance(terminalLoc, playerLoc) < MAX_DISTANCE)
            {
                npe.movePlayerFromOrdMantellDungeonToSharedStation(player);
                groundquests.sendSignal(player, "leave_station");
            }
            else 
            {
                sendSystemMessage(self, TOO_FAR);
            }
            utils.removeScriptVarTree(player, "npe.gamma_travel");
        }
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVarTree(player, "npe.gamma_travel");
        }
        utils.removeScriptVarTree(player, "npe.gamma_travel");
        return SCRIPT_CONTINUE;
    }
}
