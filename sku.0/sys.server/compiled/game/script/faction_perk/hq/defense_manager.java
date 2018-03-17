package script.faction_perk.hq;

import script.*;
import script.library.*;

import java.util.Vector;

public class defense_manager extends script.base_script
{
    public defense_manager()
    {
    }
    private static final float RESOURCE_REPAIR_RATIO = .5f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isInvulnerable(self))
        {
            setInvulnerable(self, true);
        }
        hq.prepareHqDefenses(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isInvulnerable(self))
        {
            setInvulnerable(self, true);
        }
        messageTo(self, "handleDefenseValidation", null, 10f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        hq.cleanupHqDefenses(self);
        hq.cleanupHqSecurityTeam(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCreateMinefield(obj_id self, dictionary params) throws InterruptedException
    {
        hq.createMinefield(self);
        return SCRIPT_CONTINUE;
    }
    public int OnMaintenanceLoop(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleRepairDefenses", null, 10f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRepairDefenses(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, hq.VAR_DEFENSE_BASE))
        {
            return SCRIPT_CONTINUE;
        }
        int ireserve = getIntObjVar(self, hq.VAR_HQ_RESOURCE_CNT);
        if (ireserve < 1)
        {
            messageTo(self, "handleDefenseValidation", null, 10f, false);
            return SCRIPT_CONTINUE;
        }
        float reserve = (float)ireserve;
        obj_var_list ovl = getObjVarList(self, hq.VAR_DEFENSE_BASE);
        if (ovl == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numType = ovl.getNumItems();
        obj_var ov;
        obj_id[] defenses;
        for (int i = 0; i < numType; i++)
        {
            if (reserve < 1f)
            {
                break;
            }
            ov = ovl.getObjVar(i);
            defenses = ov.getObjIdArrayData();
            if (defenses != null && defenses.length > 0)
            {
                for (obj_id defense : defenses) {
                    int curres = getIntObjVar(self, hq.VAR_HQ_RESOURCE_CNT);
                    if (isIdValid(defense)) {
                        int hp = getHitpoints(defense);
                        int max = getMaxHitpoints(defense);
                        if (hp < 1) {
                            destroyObject(defense);
                        } else if (hp < max) {
                            int diff = max - hp;
                            float cost = diff * RESOURCE_REPAIR_RATIO;
                            if (cost > curres) {
                                diff = (int) (curres / RESOURCE_REPAIR_RATIO);
                                cost = curres;
                            }
                            curres -= cost;
                            setHitpoints(defense, hp + diff);
                            int used = (int) cost;
                            int total = curres - used;
                            if (total < 0) {
                                removeObjVar(self, hq.VAR_HQ_RESOURCE_CNT);
                                break;
                            }
                            setObjVar(self, hq.VAR_HQ_RESOURCE_CNT, total);
                            if (curres < 1f) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        messageTo(self, "handleDefenseValidation", null, 10f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAddDefense(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        String defenseType = params.getString("type");
        if (defenseType == null || defenseType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        else if (defenseType.equals("mine"))
        {
            int mineType = params.getInt("mineType");
            addMine(self, mineType);
            return SCRIPT_CONTINUE;
        }
        String template = params.getString("template");
        if (template == null || template.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!hasObjVar(self, hq.VAR_DEFENSE_BASE))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] data = getObjIdArrayObjVar(self, hq.VAR_DEFENSE_BASE + "." + defenseType);
        if (data == null || data.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int pos = utils.getFirstNonValidIdIndex(data);
        if (pos < 0 || pos > data.length - 1)
        {
            return SCRIPT_CONTINUE;
        }
        else if (isIdValid(data[pos]))
        {
            return SCRIPT_CONTINUE;
        }
        String tbl = hq.TBL_DEFENSE_PATH + utils.getTemplateFilenameNoPath(self);
        String locData = dataTableGetString(tbl, pos, toUpper(defenseType));
        if (locData == null || locData.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String[] locSplit = split(locData, ',');
        if (locSplit == null || locSplit.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        float dx = utils.stringToFloat(locSplit[0]);
        if (dx == Float.NEGATIVE_INFINITY)
        {
            return SCRIPT_CONTINUE;
        }
        float dy = utils.stringToFloat(locSplit[1]);
        if (dy == Float.NEGATIVE_INFINITY)
        {
            return SCRIPT_CONTINUE;
        }
        float dz = utils.stringToFloat(locSplit[2]);
        if (dz == Float.NEGATIVE_INFINITY)
        {
            return SCRIPT_CONTINUE;
        }
        float dyaw = utils.stringToFloat(locSplit[3]);
        if (dyaw == Float.NEGATIVE_INFINITY)
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        float yaw = getYaw(self);
        location there = player_structure.transformDeltaWorldCoord(here, dx, dz, getYaw(self));
        there.y = here.y;
        int myFac = pvpGetAlignedFaction(self);
        String myFacName = factions.getFaction(self);
        obj_id defense;
        if (defenseType.equals("turret"))
        {
            int turretType = advanced_turret.TYPE_BLOCK;
            int turretSize = advanced_turret.SIZE_SMALL;
            int turretMinDam = 3500;
            int turretMaxDam = 4500;
            int turretHitpoints = 200000;
            float turretRange = 64f;
            float turretSpeed = 2f;
            if (template.contains("tower"))
            {
                turretType = advanced_turret.TYPE_TOWER;
                turretMinDam = 5000;
                turretMaxDam = 7000;
                turretSpeed = 3f;
            }
            else if (template.contains("dish"))
            {
                turretType = advanced_turret.TYPE_DISH;
                turretMinDam = 1750;
                turretMaxDam = 2250;
                turretSpeed = 1f;
            }
            else if (template.contains("lg"))
            {
                turretSize = advanced_turret.SIZE_LARGE;
                turretHitpoints = 600000;
                turretRange = 96f;
            }
            else if (template.contains("med"))
            {
                turretSize = advanced_turret.SIZE_MEDIUM;
                turretHitpoints = 400000;
                turretRange = 80f;
            }
            defense = advanced_turret.createTurret(there, (yaw + dyaw), turretType, turretSize, DAMAGE_ENERGY, turretMinDam, turretMaxDam, turretHitpoints, turretRange, turretSpeed, myFacName);
        }
        else 
        {
            defense = createObject(template, there);
            if (isIdValid(defense))
            {
                setYaw(defense, yaw + dyaw);
                pvpSetAlignedFaction(defense, myFac);
                pvpMakeDeclared(defense);
                if (myFacName != null && myFacName.equals(""))
                {
                    factions.setFaction(defense, myFacName);
                }
                String temp = getTemplateName(defense);
                int index = player_structure.getStructureTableIndex(temp);
                if (index != -1)
                {
                    int condition = dataTableGetInt(player_structure.PLAYER_STRUCTURE_DATATABLE, index, player_structure.DATATABLE_COL_CONDITION);
                    if (condition > 0)
                    {
                        setMaxHitpoints(defense, condition);
                        setInvulnerableHitpoints(defense, condition);
                    }
                }
            }
        }
        if (isIdValid(defense))
        {
            persistObject(defense);
            attachScript(defense, hq.SCRIPT_DEFENSE_OBJECT);
            setObjVar(defense, hq.VAR_DEFENSE_PARENT, self);
            setOwner(defense, self);
            data[pos] = defense;
            setObjVar(self, hq.VAR_DEFENSE_BASE + "." + defenseType, data);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveDefense(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id sender = params.getObjId("sender");
        if (!isIdValid(sender))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!hasObjVar(self, hq.VAR_DEFENSE_BASE))
        {
            return SCRIPT_CONTINUE;
        }
        obj_var_list ovl = getObjVarList(self, hq.VAR_DEFENSE_BASE);
        if (ovl == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numTypes = ovl.getNumItems();
        obj_var ov;
        obj_id[] data;
        for (int i = 0; i < numTypes; i++)
        {
            ov = ovl.getObjVar(i);
            data = ov.getObjIdArrayData();
            int idx = utils.getElementPositionInArray(data, sender);
            if (idx > -1)
            {
                data[idx] = obj_id.NULL_ID;
                setObjVar(self, hq.VAR_DEFENSE_BASE + "." + ov.getName(), data);
                break;
            }
        }
        messageTo(self, "terminalOff", null, 1, false);
        obj_id[] numTur = getObjIdArrayObjVar(self, "hq.defense.turret");
        if (numTur == null || numTur.length == 0)
        {
            detachScript(self, "faction_perk.hq.base_block");
        }
        int pos = utils.getFirstValidIdIndex(numTur);
        if (pos < 0 || (numTur != null && pos > numTur.length - 1))
        {
            detachScript(self, "faction_perk.hq.base_block");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDefenseValidation(obj_id self, dictionary params) throws InterruptedException
    {
        hq.validateDefenseTracking(self);
        return SCRIPT_CONTINUE;
    }
    public int handleMinefieldValidation(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "mines"))
        {
            int[] mines = getIntArrayObjVar(self, "mines");
            if (mines.length >= hq.MAX_MINE_TYPES)
            {
                return SCRIPT_CONTINUE;
            }
            int[] new_mines = new int[hq.MAX_MINE_TYPES];
            // this may bomb... depends on how big "mines" ends up being... if mines.length is bigger than hq.MAX_MINE_TYPES
            // then it will bomb.  Correct the issue by making arraycopy only go to hq.MAX_MINE_TYPES instead of mines.length
            System.arraycopy(mines, 0, new_mines, 0, mines.length);
            setObjVar(self, "mines", new_mines);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, hq.VAR_DEFENSE_BASE + ".minefield"))
        {
            obj_id[] old_minefields = getObjIdArrayObjVar(self, hq.VAR_DEFENSE_BASE + ".minefield");
            for (obj_id old_minefield : old_minefields) {
                if (isIdValid(old_minefield)) {
                    destroyObject(old_minefield);
                }
            }
        }
        messageTo(self, "handleCreateMinefield", null, 5f, false);
        setObjVar(self, "mines", new int[hq.MAX_MINE_TYPES]);
        return SCRIPT_CONTINUE;
    }
    public int handleTurretControl(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id turret = params.getObjId("defense");
        if (!isIdValid(turret))
        {
            return SCRIPT_CONTINUE;
        }
        obj_var_list ovl = getObjVarList(self, hq.VAR_DEFENSE_BASE);
        if (ovl == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numTypes = ovl.getNumItems();
        obj_id[] data;
        for (int i = 0; i < numTypes; i++)
        {
            data = ovl.getObjVar(i).getObjIdArrayData();
            int pos = utils.getElementPositionInArray(data, turret);
            if (pos > -1 && pos < 4)
            {
                setObjVar(self, "turret" + (pos + 1), turret);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleResetTurretControl(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id turret = params.getObjId("sender");
        if (!isIdValid(turret))
        {
            return SCRIPT_CONTINUE;
        }
        if (!exists(turret))
        {
            return SCRIPT_CONTINUE;
        }
        obj_var_list ovl = getObjVarList(self, hq.VAR_DEFENSE_BASE);
        if (ovl == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numTypes = ovl.getNumItems();
        obj_id[] data;
        for (int i = 0; i < numTypes; i++)
        {
            data = ovl.getObjVar(i).getObjIdArrayData();
            int pos = utils.getElementPositionInArray(data, turret);
            if (pos > -1 && pos < 4)
            {
                setObjVar(self, "turret" + (pos + 1), turret);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnSecurityRover(obj_id self, dictionary params) throws InterruptedException
    {
        String guardType = params.getString("guard");
        location start = params.getLocation("start");
        obj_id guard = create.object(guardType, start);
        if (isIdValid(guard))
        {
            ai_lib.setPatrolPath(guard, params.getLocationArray("locs"));
            if (utils.hasScriptVar(self, "hq.spawn.security"))
            {
                Vector securityTeam = utils.getResizeableObjIdArrayScriptVar(self, "hq.spawn.security");
                securityTeam = utils.addElement(securityTeam, guard);
                utils.setScriptVar(self, "hq.spawn.security", securityTeam);
            }
            else 
            {
                Vector securityTeam = new Vector();
                securityTeam.setSize(0);
                securityTeam = utils.addElement(securityTeam, guard);
                utils.setScriptVar(self, "hq.spawn.security", securityTeam);
            }
        }
        return SCRIPT_CONTINUE;
    }
    private boolean addMine(obj_id self, int mineType) throws InterruptedException
    {
        if (mineType == -1)
        {
            return false;
        }
        int[] mines = new int[hq.MAX_MINE_TYPES];
        if (hasObjVar(self, "mines"))
        {
            mines = getIntArrayObjVar(self, "mines");
        }
        if (mineType >= mines.length)
        {
            return false;
        }
        mines[mineType]++;
        if (mines[mineType] <= 0)
        {
            mines[mineType] = 1;
        }
        setObjVar(self, "mines", mines);
        return true;
    }
}
