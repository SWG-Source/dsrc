package script.faction_perk.hq;

import script.*;
import script.library.*;

import java.util.Vector;

public class turret_control extends script.terminal.base.base_terminal
{
    public turret_control()
    {
    }
    private static final String BTN_ATTACK = "@" + "hq" + ":btn_attack";
    private static final string_id MNU_TURRET_CONTROL = new string_id("hq", "mnu_turret_control");
    private static final string_id ATTACK_TARGETS = new string_id("hq", "attack_targets");
    private static final string_id ADMIN_ONLY = new string_id("hq", "admin_only");
    private static final string_id CONTROL_TITLE = new string_id("hq", "control_title");
    private static final string_id TURRET_CONTROL = new string_id("hq", "turret_control");
    private static final string_id CURRENT_TARGET = new string_id("hq", "current_target");
    private static final string_id TARGET_HEALTH = new string_id("hq", "target_health");
    private static final string_id NONE_ACTIVE = new string_id("hq", "none_active");
    private static final string_id NO_TARGETS = new string_id("hq", "no_targets");
    private static final string_id INVALID_TARGET = new string_id("hq", "invalid_target");
    private static final string_id DECLARED_ONLY = new string_id("hq", "declared_only");
    private static final string_id NO_LINE_OF_SITE = new string_id("hq", "no_line_of_site");
    private static final string_id IN_USE = new string_id("hq", "in_use");
    private static final string_id ALREADY_ATTACKING = new string_id("hq", "already_attacking");
    private static final String CONTROL_TURRET = "controlling.turret";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume("turret_control_range", 5, true);
        if (hasScript(self, hq.SCRIPT_TERMINAL_DISABLE))
        {
            detachScript(self, hq.SCRIPT_TERMINAL_DISABLE);
        }
        if (utils.hasScriptVar(self, "sui.in_use"))
        {
            utils.removeScriptVar(self, "sui.in_use");
        }
        messageTo(self, "handleSetControl", null, 5f, false);
        return super.OnInitialize(self);
    }
    public int OnTriggerVolumeExited(obj_id self, String volName, obj_id who) throws InterruptedException
    {
        if (!isIdValid(who))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "sui.in_use"))
        {
            obj_id player = utils.getObjIdScriptVar(self, "sui.in_use");
            if (player == who)
            {
                String scriptvar_sui = player + ".control.sui";
                sui.closeSUI(player, utils.getIntScriptVar(self, scriptvar_sui));
                utils.removeScriptVar(self, scriptvar_sui);
                utils.removeScriptVar(self, "sui.in_use");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetControl(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_var_list ovl = getObjVarList(structure, hq.VAR_DEFENSE_TURTERMINALS);
        if (ovl == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numTypes = ovl.getNumItems();
        obj_id[] data;
        for (int i = 0; i < numTypes; i++)
        {
            data = ovl.getObjVar(i).getObjIdArrayData();
            int pos = utils.getElementPositionInArray(data, self);
            if (pos > -1 && pos < 4)
            {
                setObjVar(self, "control" + (pos + 1), 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        if (utils.hasScriptVar(self, "sui.in_use"))
        {
            obj_id oldPlayer = utils.getObjIdScriptVar(self, "sui.in_use");
            if (oldPlayer != player)
            {
                if (oldPlayer.isLoaded())
                {
                    sendSystemMessage(player, IN_USE);
                    return super.OnObjectMenuRequest(self, player, mi);
                }
                else 
                {
                    utils.removeScriptVar(self, "sui.in_use");
                }
            }
        }
        if (pvpGetAlignedFaction(player) == pvpGetAlignedFaction(structure))
        {
            if (factions.isOnLeave(player))
            {
                sendSystemMessage(player, DECLARED_ONLY);
                return super.OnObjectMenuRequest(self, player, mi);
            }
        }
        if (!player_structure.isAdmin(structure, player))
        {
            int rank = pvpGetCurrentGcwRank(player);
            if (rank < 4)
            {
                sendSystemMessage(player, ADMIN_ONLY);
                return super.OnObjectMenuRequest(self, player, mi);
            }
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_TURRET_CONTROL);
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (pvpGetAlignedFaction(player) == pvpGetAlignedFaction(structure))
        {
            if (pvpGetType(player) != PVPTYPE_DECLARED)
            {
                sendSystemMessage(player, DECLARED_ONLY);
                return SCRIPT_CONTINUE;
            }
        }
        if (item == menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            dictionary params = new dictionary();
            params.put("player", player);
            messageTo(self, "handleTurretAssignment", params, 1f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTurretAssignment(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        obj_id player = params.getObjId("player");
        if (!isIdValid(structure) || (!isIdValid(player)))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "sui.in_use", player);
        if (hasObjVar(self, "control1"))
        {
            obj_id turret = getObjIdObjVar(structure, "turret1");
            if (!exists(turret))
            {
                removeObjVar(self, CONTROL_TURRET);
            }
            else 
            {
                setObjVar(self, CONTROL_TURRET, turret);
            }
        }
        else if (hasObjVar(self, "control2"))
        {
            obj_id turret = getObjIdObjVar(structure, "turret2");
            if (!exists(turret))
            {
                removeObjVar(self, CONTROL_TURRET);
            }
            else 
            {
                setObjVar(self, CONTROL_TURRET, turret);
            }
        }
        else if (hasObjVar(self, "control3"))
        {
            obj_id turret = getObjIdObjVar(structure, "turret3");
            if (!exists(turret))
            {
                removeObjVar(self, CONTROL_TURRET);
            }
            else 
            {
                setObjVar(self, CONTROL_TURRET, turret);
            }
        }
        else if (hasObjVar(self, "control4"))
        {
            obj_id turret = getObjIdObjVar(structure, "turret4");
            if (!exists(turret))
            {
                removeObjVar(self, CONTROL_TURRET);
            }
            else 
            {
                setObjVar(self, CONTROL_TURRET, turret);
            }
        }
        showTurretControl(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleTargetSelection(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            utils.removeScriptVar(self, "sui.in_use");
            return SCRIPT_CONTINUE;
        }
        int intState = getState(player, STATE_FEIGN_DEATH);
        if (isDead(player) || isIncapacitated(player) || intState > 0)
        {
            utils.removeScriptVar(self, "sui.in_use");
            return SCRIPT_CONTINUE;
        }
        obj_id cturret = getObjIdObjVar(self, CONTROL_TURRET);
        if (!isIdValid(cturret))
        {
            utils.removeScriptVar(self, "sui.in_use");
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = utils.getObjIdBatchScriptVar(cturret, turret.SCRIPTVAR_TARGETS);
        if (targets == null || targets.length == 0)
        {
            utils.removeScriptVar(self, "sui.in_use");
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(self, "sui.in_use");
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_REVERT)
        {
            showTurretControl(self, player);
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1 || idx > targets.length - 1)
        {
            utils.removeScriptVar(self, "sui.in_use");
            return SCRIPT_CONTINUE;
        }
        obj_id target = targets[idx];
        String tarName = getEncodedName(target);
        if (!isIdValid(target))
        {
            utils.removeScriptVar(self, "sui.in_use");
            return SCRIPT_CONTINUE;
        }
        if (!exists(target) || !target.isLoaded())
        {
            utils.removeScriptVar(self, "sui.in_use");
            sendSystemMessage(player, INVALID_TARGET);
            return SCRIPT_CONTINUE;
        }
        if (!canSee(cturret, target))
        {
            sendSystemMessage(player, NO_LINE_OF_SITE);
            showTurretControl(self, player);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(cturret, turret.SCRIPTVAR_ENGAGED))
        {
            obj_id oldTarget = utils.getObjIdScriptVar(cturret, turret.SCRIPTVAR_ENGAGED);
            if (oldTarget == target)
            {
                sendSystemMessage(player, ALREADY_ATTACKING);
                showTurretControl(self, player);
                return SCRIPT_CONTINUE;
            }
            utils.removeScriptVar(cturret, turret.SCRIPTVAR_ENGAGED);
            utils.setScriptVar(cturret, turret.SCRIPTVAR_ENGAGED, target);
            turret.disengage(cturret);
            turret.engageTarget(cturret, target);
            messageTo(cturret, "handleTurretAttack", null, 1, false);
            prose_package ppAttack = prose.getPackage(ATTACK_TARGETS, tarName);
            sendSystemMessageProse(player, ppAttack);
            showTurretControl(self, player);
        }
        else 
        {
            utils.setScriptVar(cturret, turret.SCRIPTVAR_ENGAGED, target);
            prose_package ppAttack = prose.getPackage(ATTACK_TARGETS, tarName);
            turret.engageTarget(cturret, target);
            messageTo(cturret, "handleTurretAttack", null, 1, false);
            sendSystemMessageProse(player, ppAttack);
            showTurretControl(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    private void showTurretControl(obj_id terminal, obj_id player) throws InterruptedException
    {
        if (!isIdValid(terminal) || !isIdValid(player))
        {
            return;
        }
        String scriptvar_sui = player + ".control.sui";
        if (utils.hasScriptVar(terminal, scriptvar_sui))
        {
            sui.closeSUI(player, utils.getIntScriptVar(terminal, scriptvar_sui));
            utils.removeScriptVar(terminal, scriptvar_sui);
        }
        int intState = getState(player, STATE_FEIGN_DEATH);
        if (isDead(player) || isIncapacitated(player) || intState > 0)
        {
            return;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return;
        }
        Vector entries = new Vector();
        entries.setSize(0);
        obj_id cturret = getObjIdObjVar(terminal, CONTROL_TURRET);
        if (cturret == null)
        {
            sendSystemMessage(player, NONE_ACTIVE);
            return;
        }
        int max_hp = getMaxHitpoints(cturret);
        int tur_hp = getHitpoints(cturret);
        if (!utils.hasScriptVar(cturret, turret.SCRIPTVAR_ENGAGED))
        {
            utils.removeScriptVar(terminal, "sui.in_use");
            sendSystemMessage(player, NO_TARGETS);
            return;
        }
        obj_id ctarget = utils.getObjIdScriptVar(cturret, turret.SCRIPTVAR_ENGAGED);
        String cTarName = getEncodedName(ctarget);
        String turNam = getEncodedName(cturret);
        Vector targets = utils.getResizeableObjIdBatchScriptVar(cturret, turret.SCRIPTVAR_TARGETS);
        int cur_hp = 0;
        obj_id target;
        String tarName;
        for (Object target1 : targets) {
            target = ((obj_id) target1);
            tarName = getEncodedName(target);
            cur_hp = getAttrib(target, 0);
            entries = utils.addElement(entries, tarName + "     - " + " " + localize(TARGET_HEALTH) + " [" + cur_hp + "]");
        }
        String title = utils.packStringId(CONTROL_TITLE);
        String prompt = utils.packStringId(TURRET_CONTROL) + " " + turNam + " [" + tur_hp + "/" + max_hp + "]\n\n" + utils.packStringId(CURRENT_TARGET) + " " + cTarName + " " + utils.packStringId(TARGET_HEALTH) + " " + " [" + cur_hp + "]";
        int pid = sui.listbox(terminal, player, prompt, sui.OK_CANCEL_REFRESH, title, entries, "handleTargetSelection");
        if (pid > -1)
        {
            sui.setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_ATTACK);
            utils.setScriptVar(terminal, scriptvar_sui, pid);
        }
    }
}
