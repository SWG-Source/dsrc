package script.faction_perk.minefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.pclib;
import script.library.combat;
import script.library.ai_lib;
import script.library.factions;
import script.library.faction_perk;
import script.library.player_structure;

public class field extends script.systems.combat.combat_base_old
{
    public field()
    {
    }
    public static final String BLAST_PARTICLE_TEMPLATE = "object/static/particle/particle_sm_explosion.iff";
    public static final String WARNING_PARTICLE_TEMPLATE = "object/static/particle/particle_mine_warning.iff";
    public static final String VOL_MINEFIELD = "volMineField";
    public static final String HANDLER_MINEFIELD_TICK = "handleMinefieldTick";
    public static final String HANDLER_MINEFIELD_EMPTY = "handleMinefieldEmpty";
    public static final String VAR_EMPTY_BASE = "minefield.empty";
    public static final String VAR_EMPTY_COUNT = "minefield.empty.count";
    public static final String VAR_EMPTY_STAMP = "minefield.empty.stamp";
    public static final String SCRIPTVAR_MINEFIELD_NEAR = "minefield.near";
    public static final string_id SID_MINEFIELD_NEAR = new string_id("faction_perk", "minefield_near");
    public static final string_id SID_MINEFIELD_EXIT = new string_id("faction_perk", "minefield_exit");
    public static final string_id SID_NO_MINES_TO_DONATE = new string_id("faction_perk", "no_mines_to_donate");
    public static final string_id SID_NO_MINES_FROM_CRATE = new string_id("faction_perk", "no_mines_from_crate");
    public static final string_id SID_NO_MINES_OUTSIDE_HQ = new string_id("faction_perk", "no_mines_outside_hq");
    public static final string_id SID_TRAPPING_AVOID_MINE = new string_id("faction_perk", "trapping_avoid_mine");
    public static final string_id SID_SUCCESS_LOCATE_MINE = new string_id("faction_perk", "success_locate_mine");
    public static final string_id SID_CANNOT_DEFUSE_STATE = new string_id("faction_perk", "cannot_defuse_state");
    public static final string_id SID_RECOVER_FUMBLED = new string_id("faction_perk", "recover_fumbled");
    public static final string_id SID_SUCCESS_DISARM_MINE = new string_id("faction_perk", "success_disarm_mine");
    public static final string_id SID_NO_LOCATE_MINE = new string_id("faction_perk", "no_locate_mine");
    public static final string_id SID_NO_SUCCESS_DISARM = new string_id("faction_perk", "no_success_disarm");
    public static final string_id SID_SUCCESSFULLY_DONATE = new string_id("faction_perk", "successfully_donate");
    public static final string_id SID_UNSUCCESSFUL_DONATE = new string_id("faction_perk", "unsuccessful_donate");
    public static final string_id SID_WISH_TO_DONATE = new string_id("faction_perk", "wish_to_donate");
    public static final string_id SID_MINE_DONATION = new string_id("faction_perk", "mine_donation");
    public static final string_id SID_MINE_COUNT = new string_id("faction_perk", "mine_count");
    public static final String VAR_GOT = "container.got";
    public static final string_id SID_ADD_ONLY_CONTAINER = new string_id("error_message", "add_only");
    public static final string_id PROSE_WRONG_ITEM_TYPE = new string_id("error_message", "wrong_item_type");
    public static final string_id SID_MINE_INV = new string_id("player_structure", "management_mine_inv");
    public static final string_id SID_DEFUSE_MINE = new string_id("player_structure", "disarm_minefield");
    public static final string_id SID_DONATE_MINES = new string_id("player_structure", "mnu_donate_mines");
    public static final String HANDLER_BLAST_CLEANUP = "handleBlastCleanup";
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (volumeName.equals(VOL_MINEFIELD))
        {
            int myFac = pvpGetAlignedFaction(self);
            int whoFac = pvpGetAlignedFaction(who);
            if (pvpGetType(who) == PVPTYPE_NEUTRAL)
            {
                whoFac = 0;
            }
            if (pvpAreFactionsOpposed(myFac, whoFac))
            {
                notifyMinefieldNear(who);
                addMinefieldTarget(self, who);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (volumeName.equals(VOL_MINEFIELD))
        {
            int myFac = pvpGetAlignedFaction(self);
            int whoFac = pvpGetAlignedFaction(who);
            if (pvpGetType(who) == PVPTYPE_NEUTRAL)
            {
                whoFac = 0;
            }
            if (pvpAreFactionsOpposed(myFac, whoFac))
            {
                notifyMinefieldExited(who);
                removeMinefieldTarget(self, who);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!player_structure.isInstallation(self))
        {
            return SCRIPT_CONTINUE;
        }
        int myFac = pvpGetAlignedFaction(self);
        int whoFac = pvpGetAlignedFaction(player);
        if (pvpGetType(player) == PVPTYPE_NEUTRAL)
        {
            whoFac = 0;
        }
        if (myFac == whoFac)
        {
            if (hasObjVar(self, faction_perk.VAR_MINEFIELD_BASE))
            {
                if (getOwner(self) == player && getGameObjectType(self) == GOT_installation_minefield)
                {
                    mi.addRootMenu(menu_info_types.SERVER_HEAL_WOUND, SID_MINE_INV);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.SERVER_HEAL_WOUND_HEALTH, SID_DONATE_MINES);
                }
            }
        }
        else if (pvpAreFactionsOpposed(myFac, whoFac))
        {
            if (hasObjVar(self, faction_perk.VAR_MINEFIELD_BASE))
            {
                mi.addRootMenu(menu_info_types.SERVER_HEAL_WOUND_ACTION, SID_DEFUSE_MINE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!player_structure.isInstallation(self))
        {
            return SCRIPT_CONTINUE;
        }
        int myFac = pvpGetAlignedFaction(self);
        int whoFac = pvpGetAlignedFaction(player);
        if (pvpGetType(player) == PVPTYPE_NEUTRAL)
        {
            whoFac = 0;
        }
        if (item == menu_info_types.SERVER_HEAL_WOUND)
        {
            if (hasObjVar(self, faction_perk.VAR_MINEFIELD_BASE) && (getContainerType(self) > 0))
            {
                if (myFac == whoFac)
                {
                    utils.requestContainerOpen(player, self);
                }
            }
        }
        else if (item == menu_info_types.SERVER_HEAL_WOUND_HEALTH)
        {
            if (myFac == whoFac)
            {
                String base_path = "donate." + player;
                if (utils.hasScriptVar(self, base_path + ",pid"))
                {
                    int oldpid = utils.getIntScriptVar(self, base_path + ".pid");
                    sui.closeSUI(player, oldpid);
                    utils.removeScriptVarTree(self, base_path);
                }
                obj_id[] mines = utils.getContainedGOTObjects(player, GOT_weapon_heavy_mine, true, false);
                if (mines == null || mines.length == 0)
                {
                    sendSystemMessage(player, SID_NO_MINES_TO_DONATE);
                    return SCRIPT_CONTINUE;
                }
                Vector entries = new Vector();
                entries.setSize(0);
                for (int i = 0; i < mines.length; i++)
                {
                    obj_id container = getContainedBy(mines[i]);
                    String containerName = getTemplateName(container);
                    if (!containerName.equals("object/factory/factory_crate_weapon.iff"))
                    {
                        prose_package ppElement = prose.getPackage(SID_MINE_COUNT, mines[i], getCount(mines[i]));
                        entries = utils.addElement(entries, " \0" + packOutOfBandProsePackage(null, ppElement));
                    }
                }
                if (entries != null && entries.size() > 0)
                {
                    String prompt = utils.packStringId(SID_WISH_TO_DONATE);
                    String title = utils.packStringId(SID_MINE_DONATION);
                    int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, entries, "handleMineDonation");
                    if (pid > -1)
                    {
                        utils.setScriptVar(self, base_path + ".pid", pid);
                        utils.setScriptVar(self, base_path + ".mines", mines);
                        return SCRIPT_CONTINUE;
                    }
                }
                if (entries.size() == 0)
                {
                    sendSystemMessage(player, SID_NO_MINES_FROM_CRATE);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else if (item == menu_info_types.SERVER_HEAL_WOUND_ACTION)
        {
            if (hasObjVar(self, faction_perk.VAR_MINEFIELD_BASE) && (getContainerType(self) > 0))
            {
                if (pvpAreFactionsOpposed(myFac, whoFac))
                {
                    queueCommand(player, (1946944924), self, "", COMMAND_PRIORITY_DEFAULT);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id who) throws InterruptedException
    {
        if (isPlayer(who) && (isGod(who) || getOwner(self) == who))
        {
            sendSystemMessage(who, SID_NO_MINES_OUTSIDE_HQ);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer) && (getOwner(self) != transferer))
        {
            sendSystemMessage(transferer, SID_ADD_ONLY_CONTAINER);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer) && (pvpGetAlignedFaction(self) == pvpGetAlignedFaction(transferer)) && (pvpGetType(transferer) != PVPTYPE_NEUTRAL))
        {
            int got = getGameObjectType(item);
            if (got != GOT_weapon_heavy_mine)
            {
                string_id got_sid = getGameObjectTypeStringId(got);
                prose_package pp = prose.getPackage(PROSE_WRONG_ITEM_TYPE, self, got_sid);
                sendSystemMessageProse(transferer, pp);
                return SCRIPT_OVERRIDE;
            }
            if (!factions.isDeclared(transferer))
            {
                pvpHelpPerformed(transferer, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        if ((contents == null) || (contents.length == 0))
        {
            setMinefieldEmpty(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (hasObjVar(self, VAR_EMPTY_BASE))
        {
            removeObjVar(self, VAR_EMPTY_BASE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        updateMinefieldExtents();
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "faction_perk.hq.defense_object"))
        {
            obj_id[] contents = getContents(self);
            if ((contents == null) || (contents.length == 0))
            {
                player_structure.destroyStructure(self, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (!hasObjVar(self, "pvpCanAttack"))
        {
            setObjVar(self, "pvpCanAttack", 1);
        }
        createMinefieldVolume();
        return SCRIPT_CONTINUE;
    }
    public int handleMinefieldTick(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] targets = utils.getObjIdBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_IDS);
        location[] locs = utils.getLocationBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS);
        if ((targets == null) || (targets.length == 0) || (locs == null) || (locs.length == 0))
        {
            return SCRIPT_CONTINUE;
        }
        if (targets.length != locs.length)
        {
            removeMinefieldVolume();
            createMinefieldVolume();
            return SCRIPT_CONTINUE;
        }
        float minX = getFloatObjVar(self, faction_perk.VAR_MINEFIELD_MIN_X);
        float minZ = getFloatObjVar(self, faction_perk.VAR_MINEFIELD_MIN_Z);
        float maxX = getFloatObjVar(self, faction_perk.VAR_MINEFIELD_MAX_X);
        float maxZ = getFloatObjVar(self, faction_perk.VAR_MINEFIELD_MAX_Z);
        boolean noMines = false;
        Vector toRemove = new Vector();
        toRemove.setSize(0);
        for (int i = 0; i < targets.length; i++)
        {
            if (!isPlayer(targets[i]) && ai_lib.isDead(targets[i]))
            {
                toRemove = utils.addElement(toRemove, targets[i]);
            }
            else 
            {
                location spot = getLocation(targets[i]);
                if (spot != null)
                {
                    if (!noMines)
                    {
                        float dist = getDistance(locs[i], spot);
                        int chance = (int)(dist * faction_perk.CHANCE_PER_METER);
                        int roll = rand(0, 100);
                        if (roll < chance)
                        {
                            if (!detonateMine(self, targets[i]))
                            {
                                noMines = true;
                            }
                        }
                    }
                    locs[i] = spot;
                }
            }
        }
        if (locs != null && locs.length > 0)
        {
            utils.setBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS, locs);
        }
        if ((toRemove != null) && (toRemove.size() > 0))
        {
            for (int i = 0; i < toRemove.size(); i++)
            {
                removeMinefieldTarget(self, ((obj_id)toRemove.get(i)));
            }
        }
        messageTo(self, HANDLER_MINEFIELD_TICK, null, faction_perk.BASE_MINEFIELD_TICK, false);
        return SCRIPT_CONTINUE;
    }
    public int handleBlastCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id blast = params.getObjId("blastId");
        if (isIdValid(blast))
        {
            destroyObject(blast);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMinefieldEmpty(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, VAR_EMPTY_BASE))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] contents = getContents(self);
        if ((contents != null) || (contents.length > 0))
        {
            removeObjVar(self, VAR_EMPTY_BASE);
            return SCRIPT_CONTINUE;
        }
        int cnt = getIntObjVar(self, VAR_EMPTY_COUNT);
        int stamp = getIntObjVar(self, VAR_EMPTY_STAMP);
        int now = getGameTime();
        if (now >= (stamp + 360))
        {
            cnt++;
            if (cnt > 25)
            {
                player_structure.destroyStructure(self, false);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, VAR_EMPTY_COUNT, cnt);
            setObjVar(self, VAR_EMPTY_STAMP, now);
            messageTo(self, HANDLER_MINEFIELD_EMPTY, null, 360, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMineDonation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String base_path = "donate." + player;
        if (!utils.hasScriptVar(self, base_path + ".pid"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] mines = utils.getObjIdArrayScriptVar(self, base_path + ".mines");
        utils.removeScriptVarTree(self, base_path);
        if (mines == null || mines.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx >= mines.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (putIn(mines[idx], self))
        {
            prose_package ppSuccess = prose.getPackage(SID_SUCCESSFULLY_DONATE, self, mines[idx]);
            sendSystemMessageProse(player, ppSuccess);
        }
        else 
        {
            prose_package ppUnsuccess = prose.getPackage(SID_UNSUCCESSFUL_DONATE, self, mines[idx]);
            sendSystemMessageProse(player, ppUnsuccess);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean addMinefieldTarget(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(self))
        {
            return false;
        }
        Vector targets = utils.getResizeableObjIdBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_IDS);
        Vector locs = utils.getResizeableLocationBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS);
        if ((targets == null) || (targets.size() == 0) || (locs == null) || (locs.size() == 0))
        {
            targets = null;
            locs = null;
            messageTo(self, HANDLER_MINEFIELD_TICK, null, faction_perk.BASE_MINEFIELD_TICK, false);
        }
        targets = utils.addElement(targets, target);
        location target_loc = getLocation(target);
        if (target_loc != null)
        {
            locs = utils.addElement(locs, target_loc);
        }
        else 
        {
            return false;
        }
        if ((targets == null) || (targets.size() == 0) || (locs == null) || (locs.size() == 0))
        {
            return false;
        }
        utils.setBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_IDS, targets);
        utils.setBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS, locs);
        return true;
    }
    public boolean removeMinefieldTarget(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(self))
        {
            return false;
        }
        Vector targets = utils.getResizeableObjIdBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_IDS);
        Vector locs = utils.getResizeableLocationBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS);
        if ((targets == null) || (targets.size() == 0) || (locs == null) || (locs.size() == 0))
        {
            return false;
        }
        int idx = utils.getElementPositionInArray(targets, target);
        if (idx < 0)
        {
            return false;
        }
        targets = utils.removeElementAt(targets, idx);
        locs = utils.removeElementAt(locs, idx);
        if ((targets == null) || (targets.size() == 0) || (locs == null) || (locs.size() == 0))
        {
            utils.removeBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_IDS);
            utils.removeBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS);
        }
        else 
        {
            utils.setBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_IDS, targets);
            utils.setBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS, locs);
        }
        return true;
    }
    public boolean createMinefieldVolume() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(self))
        {
            return false;
        }
        int size = 1;
        if (hasObjVar(self, faction_perk.VAR_MINEFIELD_SIZE))
        {
            size = getIntObjVar(self, faction_perk.VAR_MINEFIELD_SIZE);
            CustomerServiceLog("minefields", "Minefield:" + "(" + self + ")" + " has a radius of " + size);
        }
        if (size < 1)
        {
            return false;
        }
        float range = (float)size * 8f * 1.5f;
        createTriggerVolume(VOL_MINEFIELD, range, true);
        return true;
    }
    public void removeMinefieldVolume() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(self))
        {
            return;
        }
        removeTriggerVolume(VOL_MINEFIELD);
    }
    public void notifyMinefieldNear(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target))
        {
            return;
        }
        int cnt = utils.getIntScriptVar(target, SCRIPTVAR_MINEFIELD_NEAR);
        if (cnt == 0)
        {
            sendSystemMessage(target, SID_MINEFIELD_NEAR);
        }
        cnt++;
        utils.setScriptVar(target, SCRIPTVAR_MINEFIELD_NEAR, cnt);
    }
    public void notifyMinefieldExited(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        int cnt = utils.getIntScriptVar(target, SCRIPTVAR_MINEFIELD_NEAR);
        cnt--;
        if (cnt < 1)
        {
            sendSystemMessage(target, SID_MINEFIELD_EXIT);
            utils.removeScriptVar(target, SCRIPTVAR_MINEFIELD_NEAR);
            return;
        }
        utils.setScriptVar(target, SCRIPTVAR_MINEFIELD_NEAR, cnt);
    }
    public void explodeMine(obj_id minefield, obj_id mine, obj_id target, location detonation) throws InterruptedException
    {
        if (!isIdValid(minefield) || !isIdValid(mine) || !isIdValid(target))
        {
            return;
        }
        int fieldFac = pvpGetAlignedFaction(minefield);
        float damageRadius = faction_perk.MINE_DAMAGE_RADIUS;
        Vector combatTargets = new Vector();
        combatTargets.setSize(0);
        obj_id[] creatures = getCreaturesInRange(detonation, damageRadius);
        if ((creatures != null) && (creatures.length > 0))
        {
            for (int i = 0; i < creatures.length; i++)
            {
                int cFac = pvpGetAlignedFaction(creatures[i]);
                if (pvpGetType(creatures[i]) == PVPTYPE_NEUTRAL)
                {
                    cFac = 0;
                }
                if (pvpAreFactionsOpposed(fieldFac, cFac))
                {
                    if (isPlayer(creatures[i]) && getPosture(creatures[i]) == POSTURE_INCAPACITATED)
                    {
                        pclib.coupDeGrace(creatures[i], minefield);
                    }
                    else 
                    {
                        pvpSetFactionEnemyFlag(creatures[i], fieldFac);
                        combatTargets = utils.addElement(combatTargets, creatures[i]);
                    }
                }
            }
        }
        location fieldLoc = getLocation(minefield);
        location tarLoc = getLocation(target);
        obj_id[] targets = utils.toStaticObjIdArray(combatTargets);
        doMineDamage(minefield, mine, target, targets);
        CustomerServiceLog("minefields", "Minefield:" + "(" + minefield + ")" + " is located at " + fieldLoc + " and exploded on " + target + " at location " + tarLoc);
        if (rand(0, 100) < 5)
        {
            obj_id[] possibleTargets = utils.getObjIdBatchScriptVar(minefield, faction_perk.VAR_MINEFIELD_TARGET_IDS);
            int nextIdx = rand(0, possibleTargets.length - 1);
            obj_id nextTarget = possibleTargets[nextIdx];
            if (isIdValid(nextTarget))
            {
                CustomerServiceLog("minefields", "Minefield:" + "(" + minefield + ")" + " is located at " + fieldLoc + " and exploded on " + target + " at location " + tarLoc);
                detonateMine(minefield, nextTarget);
            }
        }
    }
    public obj_id getRandomMine(obj_id minefield) throws InterruptedException
    {
        if (!isIdValid(minefield))
        {
            return null;
        }
        obj_id[] mines = utils.getContainedGOTObjects(minefield, GOT_weapon_heavy_mine, true, false);
        if (mines != null && mines.length > 0)
        {
            int idx = rand(0, mines.length - 1);
            return mines[idx];
        }
        setMinefieldEmpty(minefield);
        return null;
    }
    public boolean detonateMine(obj_id minefield, obj_id target) throws InterruptedException
    {
        if (!isIdValid(minefield) || !isIdValid(target))
        {
            return false;
        }
        int mFac = pvpGetAlignedFaction(minefield);
        int tFac = pvpGetAlignedFaction(target);
        if (pvpGetType(target) == PVPTYPE_NEUTRAL)
        {
            tFac = 0;
        }
        if (!pvpAreFactionsOpposed(mFac, tFac))
        {
            removeMinefieldTarget(minefield, target);
            return false;
        }
        obj_id mine = getRandomMine(minefield);
        if (isIdValid(mine))
        {
            location fieldLoc = getLocation(minefield);
            location tarLoc = getLocation(target);
            float range = rand(0f, 0.75f);
            location loc = utils.getRandomLocationInRing(getLocation(target), 0f, 0.75f);
            explodeMine(minefield, mine, target, loc);
            CustomerServiceLog("minefields", "Minefield:" + "(" + minefield + ")" + " is located at " + fieldLoc + " and exploded on " + target + " at location " + tarLoc);
            return true;
        }
        return false;
    }
    public location getRandomLocationInMinefield(obj_id minefield) throws InterruptedException
    {
        if (!isIdValid(minefield))
        {
            return null;
        }
        location fieldLoc = getLocation(minefield);
        float minX = getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MIN_X);
        float minZ = getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MIN_Z);
        float maxX = getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MAX_X);
        float maxZ = getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MAX_Z);
        float x = rand(minX, maxX);
        float z = rand(minZ, maxZ);
        return new location(x, fieldLoc.y, z);
    }
    public void setMinefieldEmpty(obj_id minefield) throws InterruptedException
    {
        if (!isIdValid(minefield))
        {
            return;
        }
        if (!hasObjVar(minefield, VAR_EMPTY_BASE))
        {
            setObjVar(minefield, VAR_EMPTY_COUNT, 1);
            setObjVar(minefield, VAR_EMPTY_STAMP, getGameTime());
            messageTo(minefield, HANDLER_MINEFIELD_EMPTY, null, 360, true);
        }
    }
    public void updateMinefieldExtents() throws InterruptedException
    {
        obj_id self = getSelf();
        int size = getIntObjVar(self, faction_perk.VAR_MINEFIELD_SIZE);
        if (size < 1)
        {
            return;
        }
        location here = getLocation(self);
        setObjVar(self, faction_perk.VAR_MINEFIELD_MIN_X, here.x - 4f);
        setObjVar(self, faction_perk.VAR_MINEFIELD_MIN_Z, here.z - 4f);
        setObjVar(self, faction_perk.VAR_MINEFIELD_MAX_X, here.x + 4f);
        setObjVar(self, faction_perk.VAR_MINEFIELD_MAX_Z, here.z + 4f);
    }
    public boolean isInMinefield(location here, float minx, float minz, float maxx, float maxz) throws InterruptedException
    {
        if (here == null)
        {
            return false;
        }
        boolean inX = false;
        boolean inZ = false;
        if (here.x >= minx && here.x <= maxx)
        {
            inX = true;
        }
        if (here.z >= minz && here.z <= maxz)
        {
            inZ = true;
        }
        return (inX && inZ);
    }
    public boolean isInMinefield(obj_id target, obj_id minefield) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(minefield))
        {
            return false;
        }
        if (!exists(target) || !target.isLoaded())
        {
            return false;
        }
        if (!hasObjVar(minefield, faction_perk.VAR_MINEFIELD_EXTENTS_BASE))
        {
            return false;
        }
        location here = getLocation(target);
        if (here == null)
        {
            return false;
        }
        location mineLoc = getLocation(minefield);
        float minX = mineLoc.x + getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MIN_X);
        float minZ = mineLoc.z + getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MIN_Z);
        float maxX = mineLoc.x + getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MAX_X);
        float maxZ = mineLoc.z + getFloatObjVar(minefield, faction_perk.VAR_MINEFIELD_MAX_Z);
        return isInMinefield(here, minX, minZ, maxX, maxZ);
    }
    public void doMineDamage(obj_id minefield, obj_id mine, obj_id target, obj_id[] combatTargets) throws InterruptedException
    {
        if (!isIdValid(minefield) || !isIdValid(mine) || !isIdValid(target))
        {
            return;
        }
        final String ANIMATION_ACTION = "";
        final float DAMAGE_MODIFIER = 1.0f;
        final float HEALTH_COST_MODIFIER = 0.0f;
        final float ACTION_COST_MODIFIER = 0.0f;
        final int BASE_TO_HIT_MODIFIER = 75;
        final float AMMO_COST_MODIFIER = 1.0f;
        String[] strTimeMods = 
        {
        };
        String[] strDamageMods = 
        {
        };
        String[] strCostMods = 
        {
        };
        String[] strToHitMods = 
        {
        };
        String[] strBlockMods = 
        {
        };
        String[] strEvadeMods = 
        {
        };
        String[] strCounterAttackMods = 
        {
        };
        int intBlockMod = 1000;
        int intEvadeMod = 1000;
        int intCounterAttackMod = 1000;
        int intAttackerEndPosture = POSTURE_NONE;
        int intDefenderEndPosture = POSTURE_NONE;
        int[] intEffects = 
        {
        };
        float[] fltEffectDurations = 
        {
        };
        int intChanceToApplyEffect = 0;
        int intToHitMod = 0;
        float fltAttackRange = 5;
        float fltRadius = getWeaponDamageRadius(mine);
        obj_id[] objDefenders = combatTargets;
        if (objDefenders == null || objDefenders.length < 1)
        {
            objDefenders = new obj_id[1];
            objDefenders[0] = target;
        }
        objDefenders = truncateTargetArray(objDefenders, target, MAX_TARGET_ARRAY_SIZE);
        objDefenders = validateDefenders(minefield, objDefenders);
        if (objDefenders.length < 1)
        {
            return;
        }
        attacker_data cbtAttackerData = new attacker_data();
        weapon_data cbtWeaponData = new weapon_data();
        defender_data[] cbtDefenderData = new defender_data[objDefenders.length];
        if (!getCombatData(minefield, objDefenders, cbtAttackerData, cbtDefenderData, cbtWeaponData))
        {
            LOG("Combat", "WARNING: field.doMineDamage call to getCombatData returned false");
            return;
        }
        cbtWeaponData = getWeaponData(mine);
        if (cbtWeaponData == null)
        {
        }
        cbtAttackerData.weaponSkillMod = 1000000;
        dictionary dctParams = new dictionary();
        messageTo(mine, "grenadeUsed", dctParams, 0, true);
        hit_result[] cbtHitData = runHitEngine(cbtAttackerData, cbtWeaponData, cbtDefenderData);
        if (cbtHitData == null)
        {
            return;
        }
        attacker_results cbtAttackerResults = new attacker_results();
        cbtAttackerResults.id = mine;
        defender_results[] cbtDefenderResults = createDefenderResultsArray(cbtHitData.length);
        setDefenderCombatResults(cbtWeaponData, cbtAttackerData, cbtAttackerResults, cbtHitData, cbtDefenderData, cbtDefenderResults, strBlockMods, intBlockMod, strEvadeMods, intEvadeMod, strCounterAttackMods, intCounterAttackMod, false);
        applyPostures(cbtAttackerData, cbtAttackerResults, cbtHitData, cbtDefenderData, cbtDefenderResults, intAttackerEndPosture, intDefenderEndPosture, cbtWeaponData);
        int[] intEffectToApply = new int[1];
        finalizeMineDamage(cbtAttackerData.id, cbtWeaponData, cbtDefenderData, cbtHitData, cbtDefenderResults);
        doMineFieldResults("clienteffect/combat_explosion_lair_large.cef", cbtAttackerResults, cbtDefenderResults, cbtHitData, target, cbtWeaponData);
    }
    public int cmdDefuseMinefield(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        int mFac = pvpGetAlignedFaction(self);
        int tFac = pvpGetAlignedFaction(target);
        if (pvpGetType(target) == PVPTYPE_NEUTRAL)
        {
            tFac = 0;
        }
        if (!pvpAreFactionsOpposed(mFac, tFac))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isInMinefield(target, self))
        {
            defuseLocateMineFail(target);
            return SCRIPT_CONTINUE;
        }
        obj_id mine = getRandomMine(self);
        if (!isIdValid(mine))
        {
            defuseLocateMineFail(target);
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = utils.getObjIdBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_IDS);
        location[] locs = utils.getLocationBatchScriptVar(self, faction_perk.VAR_MINEFIELD_TARGET_LOCS);
        if ((targets == null) || (targets.length == 0) || (locs == null) || (locs.length == 0))
        {
            defuseLocateMineFail(target);
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getElementPositionInArray(targets, target);
        if (idx < 0)
        {
            defuseLocateMineFail(target);
            return SCRIPT_CONTINUE;
        }
        if (!factions.isDeclared(target))
        {
            pvpSetFactionEnemyFlag(target, mFac);
        }
        location here = getLocation(target);
        location there = (location)locs[idx].clone();
        float travel = getDistance(here, there);
        location minefield = getLocation(self);
        float range = getDistance(here, minefield);
        float magicRatio = 3f / 2f;
        int chance = (int)(travel * faction_perk.CHANCE_PER_METER * magicRatio) + 10 - (int)range;
        int roll = rand(0, 100);
        float delay = 5f;
        boolean locatedMine = false;
        if (roll == 100)
        {
            locatedMine = true;
        }
        else if (roll < 10)
        {
            int trapping = getSkillStatMod(target, "trapping");
            if (trapping > 0)
            {
                int avoidBadLocate = rand(0, 100);
                if (avoidBadLocate < trapping / 2)
                {
                    sendSystemMessage(target, SID_TRAPPING_AVOID_MINE);
                    delay += 1f;
                    locatedMine = true;
                }
            }
            if (!locatedMine)
            {
                detonateMine(self, target);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            if (roll < chance)
            {
                locatedMine = true;
            }
        }
        int locomotion = getLocomotion(target);
        if (!locatedMine)
        {
            defuseLocateMineFail(target);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(target, SID_SUCCESS_LOCATE_MINE);
            if (locomotion != LOCOMOTION_KNEELING)
            {
                queueCommand(target, (28609318), null, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        dictionary defuseData = new dictionary();
        defuseData.put("target", target);
        defuseData.put("mine", mine);
        messageTo(self, "handleMineDefuse", defuseData, delay, false);
        return SCRIPT_CONTINUE;
    }
    public int cmdDefuseMinefieldFail(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(target, SID_CANNOT_DEFUSE_STATE);
        return SCRIPT_CONTINUE;
    }
    public int handleMineDefuse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        obj_id mine = params.getObjId("mine");
        if (!isIdValid(target) || !isIdValid(mine))
        {
            return SCRIPT_CONTINUE;
        }
        int generalAssembly = getSkillStatMod(target, "general_assembly");
        int roll = rand(0, 100);
        boolean defusedMine = false;
        if (roll == 100)
        {
            defusedMine = true;
        }
        else if (roll < 10)
        {
            int grenadeAssembly = getSkillStatMod(target, "grenade_assembly");
            if (grenadeAssembly > 0)
            {
                int avoidBadDismantle = rand(0, 100);
                if (avoidBadDismantle < grenadeAssembly)
                {
                    sendSystemMessage(target, SID_RECOVER_FUMBLED);
                    defusedMine = true;
                }
            }
            if (!defusedMine)
            {
                defuseMineFail(self, target);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            if (roll < generalAssembly)
            {
                defusedMine = true;
            }
        }
        if (!defusedMine)
        {
            defuseMineFail(self, target);
        }
        else 
        {
            sendSystemMessage(target, SID_SUCCESS_DISARM_MINE);
            messageTo(mine, "grenadeUsed", null, 0, true);
        }
        queueCommand(target, (-1465754503), null, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public void defuseLocateMineFail(obj_id target) throws InterruptedException
    {
        sendSystemMessage(target, SID_NO_LOCATE_MINE);
    }
    public void defuseMineFail(obj_id minefield, obj_id target) throws InterruptedException
    {
        sendSystemMessage(target, SID_NO_SUCCESS_DISARM);
        detonateMine(minefield, target);
    }
}
