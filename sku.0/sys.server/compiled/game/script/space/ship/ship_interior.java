package script.space.ship;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_transition;
import script.library.prose;
import script.library.space_crafting;
import script.library.space_utils;
import script.library.space_combat;

public class ship_interior extends script.base_script
{
    public ship_interior()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        space_utils.notifyObject(self, "doInteriorBuildout", dctParams);
        return SCRIPT_CONTINUE;
    }
    public int doInteriorBuildout(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "strBuildout"))
        {
            return SCRIPT_CONTINUE;
        }
        String strChassisType = "";
        if (hasObjVar(self, "strInteriorChassisType"))
        {
            strChassisType = getStringObjVar(self, "strInteriorChassisType");
        }
        else 
        {
            strChassisType = getShipChassisType(self);
        }
        String strDataTable = "datatables/ship_interiors/" + strChassisType + ".iff";
        Vector objSparkers = new Vector();
        objSparkers.setSize(0);
        if (dataTableOpen(strDataTable))
        {
            String[] strTemplates = dataTableGetStringColumn(strDataTable, "strTemplate");
            float[] fltJX = dataTableGetFloatColumn(strDataTable, "fltJX");
            float[] fltJY = dataTableGetFloatColumn(strDataTable, "fltJY");
            float[] fltJZ = dataTableGetFloatColumn(strDataTable, "fltJZ");
            float[] fltKX = dataTableGetFloatColumn(strDataTable, "fltKX");
            float[] fltKY = dataTableGetFloatColumn(strDataTable, "fltKY");
            float[] fltKZ = dataTableGetFloatColumn(strDataTable, "fltKZ");
            float[] fltPX = dataTableGetFloatColumn(strDataTable, "fltPX");
            float[] fltPY = dataTableGetFloatColumn(strDataTable, "fltPY");
            float[] fltPZ = dataTableGetFloatColumn(strDataTable, "fltPZ");
            String[] strCellName = dataTableGetStringColumn(strDataTable, "strCellName");
            String[] strObjVars = dataTableGetStringColumn(strDataTable, "strObjVars");
            String[] strScripts = dataTableGetStringColumn(strDataTable, "strScripts");
            String[] strLocationList = dataTableGetStringColumn(strDataTable, "strLocationList");
            int[] intNoCreate = dataTableGetIntColumn(strDataTable, "intNoCreate");
            for (int intI = 0; intI < strTemplates.length; intI++)
            {
                vector vctJ = new vector(fltJX[intI], fltJY[intI], fltJZ[intI]);
                vector vctK = new vector(fltKX[intI], fltKY[intI], fltKZ[intI]);
                vector vctP = new vector(fltPX[intI], fltPY[intI], fltPZ[intI]);
                transform tr = new transform(vctJ.cross(vctK), vctJ, vctK, vctP);
                obj_id objCell = getCellId(self, strCellName[intI]);
                if (intNoCreate[intI] > 0)
                {
                    if (!strLocationList[intI].equals(""))
                    {
                        Vector tranList = utils.getResizeableTransformArrayScriptVar(self, strLocationList[intI]);
                        Vector objCells = utils.getResizeableObjIdArrayScriptVar(self, strLocationList[intI] + "Cells");
                        if ((tranList == null) || (tranList.size() == 0))
                        {
                            Vector tranTestList = new Vector();
                            tranTestList.setSize(0);
                            Vector objTestCells = new Vector();
                            objTestCells.setSize(0);
                            tranTestList = utils.addElement(tranTestList, tr);
                            objTestCells = utils.addElement(objTestCells, objCell);
                            utils.setScriptVar(self, strLocationList[intI], tranTestList);
                            utils.setScriptVar(self, strLocationList[intI] + "Cells", objTestCells);
                        }
                        else 
                        {
                            objCells = utils.addElement(objCells, objCell);
                            tranList = utils.addElement(tranList, tr);
                            utils.setScriptVar(self, strLocationList[intI], tranList);
                            utils.setScriptVar(self, strLocationList[intI] + "Cells", objCells);
                        }
                    }
                    if (!utils.checkConfigFlag("ScriptFlags", "liveSpaceServer"))
                    {
                        obj_id objTest = createObject(strTemplates[intI], tr, objCell);
                        if (!strObjVars[intI].equals(""))
                        {
                            setPackedObjvars(objTest, strObjVars[intI]);
                        }
                        if (!strScripts[intI].equals(""))
                        {
                            String[] strScriptArray = split(strScripts[intI], ',');
                            for (int intJ = 0; intJ < strScriptArray.length; intJ++)
                            {
                                String script = strScriptArray[intJ];
                                if (script.indexOf("script.") > -1)
                                {
                                    script = script.substring(7);
                                }
                                if (!hasScript(objTest, script))
                                {
                                    attachScript(objTest, script);
                                }
                            }
                        }
                        attachScript(objTest, "space.ship.ship_interior_autocleanup");
                    }
                }
                else 
                {
                    obj_id objTest = createObject(strTemplates[intI], tr, objCell);
                    if (!strObjVars[intI].equals(""))
                    {
                        setPackedObjvars(objTest, strObjVars[intI]);
                    }
                    if (!strScripts[intI].equals(""))
                    {
                        String[] strScriptArray = split(strScripts[intI], ',');
                        for (int intJ = 0; intJ < strScriptArray.length; intJ++)
                        {
                            String script = strScriptArray[intJ];
                            if (script.indexOf("script.") > -1)
                            {
                                script = script.substring(7);
                            }
                            if (!hasScript(objTest, script))
                            {
                                attachScript(objTest, script);
                            }
                        }
                    }
                }
            }
        }
        else 
        {
        }
        utils.setScriptVar(self, "objSparkers", objSparkers);
        return SCRIPT_CONTINUE;
    }
    public int componentDisabledByDamage(obj_id self, dictionary params) throws InterruptedException
    {
        int intSlot = params.getInt("intSlot");
        obj_id objManager = utils.getObjIdScriptVar(self, "objInteriorManager" + intSlot);
        if ((isIdValid(objManager)) && (exists(objManager)))
        {
            setInvulnerableHitpoints(objManager, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkAmmoStatus(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objShip = self;
        int intSlot = params.getInt("intSlot");
        if (!isShipSlotInstalled(objShip, intSlot))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "weapon.objAmmoBay" + intSlot))
        {
            int intCurrentAmmo = getShipWeaponAmmoCurrent(objShip, intSlot);
            if (intCurrentAmmo != 0)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id objBay = getObjIdObjVar(self, "weapon.objAmmoBay");
            if (hasObjVar(objBay, "objBayJammed"))
            {
            }
            obj_id[] objContents = getContents(objBay);
            if ((objContents != null) && (objContents.length > 0))
            {
                obj_id objItem = objContents[0];
                if (!space_crafting.isWeaponAmmo(objItem))
                {
                    string_id strSpam = new string_id("space/space_interaction", "ammo_bay_jam");
                    prose_package pp = prose.getPackage(strSpam, objItem);
                    setObjVar(objBay, "objBayJammed", objItem);
                    return SCRIPT_CONTINUE;
                }
                else if (!space_crafting.isProperAmmoForWeapon(objItem, objShip, intSlot))
                {
                    setObjVar(objBay, "objBayJammed", objItem);
                    string_id strSpam = new string_id("space/space_interaction", "ammo_bay_jam");
                    prose_package pp = prose.getPackage(strSpam, objItem);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    space_crafting.reloadWeaponSlot(self, objItem, intSlot, null, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int componentEfficiencyChanged(obj_id self, dictionary params) throws InterruptedException
    {
        int intSlot = params.getInt("intSlot");
        obj_id objManager = utils.getObjIdScriptVar(self, "objInteriorManager" + intSlot);
        LOG("space", "objManager is " + objManager);
        if ((isIdValid(objManager)) && (exists(objManager)))
        {
            float fltCurrentHitpoints = params.getFloat("fltCurrentHitpoints");
            float fltMaximumHitpoints = params.getFloat("fltMaximumHitpoints");
            if (fltMaximumHitpoints == 0)
            {
                fltMaximumHitpoints = 1;
            }
            float fltPercentage = fltCurrentHitpoints / fltMaximumHitpoints;
            LOG("space", "Max is " + fltMaximumHitpoints + " current is " + fltCurrentHitpoints + " percentage is " + fltPercentage);
            float fltValue = 1000 * fltPercentage;
            int intHitpoints = (int)fltValue;
            if (intHitpoints == 0)
            {
                intHitpoints = 1;
            }
            LOG("space", "Setting manager to " + intHitpoints);
            setInvulnerableHitpoints(objManager, intHitpoints);
        }
        return SCRIPT_CONTINUE;
    }
    public int interiorDamageNotification(obj_id self, dictionary params) throws InterruptedException
    {
        transform[] trSparkers = utils.getTransformArrayScriptVar(self, "locSparkers");
        obj_id[] objCells = utils.getObjIdArrayScriptVar(self, "locSparkersCells");
        int intDamageType = params.getInt("intDamageType");
        int intDamageIntensity = params.getInt("intDamageIntensity");
        LOG("space", "intensity is " + intDamageIntensity);
        LOG("space", "type is " + intDamageType);
        Vector objPlayers = space_utils.getPlayersInShip(self);
        if ((objPlayers == null) || (objPlayers.size() == 0))
        {
            return SCRIPT_CONTINUE;
        }
        if ((trSparkers == null) || (trSparkers.length == 0))
        {
            return SCRIPT_CONTINUE;
        }
        int intTest = rand(0, trSparkers.length - 1);
        transform tr = trSparkers[intTest];
        location locTest = space_utils.getLocationFromTransform(tr);
        locTest.cell = objCells[intTest];
        if (intDamageType == space_combat.SHIELD)
        {
            if (intDamageIntensity == space_combat.LIGHT)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_light.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef"
                };
                int[] intDamageEffects = 
                {
                    0
                };
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 5, 50);
            }
            else if (intDamageIntensity == space_combat.HEAVY)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_light.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_damage_medium.cef"
                };
                int[] intDamageEffects = 
                {
                    0,
                    0,
                    1
                };
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 7, 50);
            }
        }
        else if (intDamageType == space_combat.ARMOR)
        {
            LOG("space", "ARMOR121@");
            if (intDamageIntensity == space_combat.LIGHT)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_light.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_med_damage_smoke.cef",
                    "clienteffect/lair_damage_medium.cef"
                };
                int[] intDamageEffects = 
                {
                    0,
                    0,
                    1
                };
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 5, 50);
                checkForAdditionalDamage(self, 2, space_crafting.NON_COMPONENT_DAMAGEABLES_LIGHT);
            }
            else if (intDamageIntensity == space_combat.HEAVY)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_medium.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_damage_medium.cef",
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_damage_medium.cef",
                    "clienteffect/lair_damage_medium.cef",
                    "clienteffect/lair_damage_medium.cef"
                };
                int[] intDamageEffects = 
                {
                    0,
                    1,
                    0,
                    1,
                    1,
                    1
                };
                checkForAdditionalDamage(self, 5, space_crafting.NON_COMPONENT_DAMAGEABLES_LIGHT);
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 15, 50);
                LOG("space", "checking for additional");
            }
        }
        if (intDamageType == space_combat.COMPONENT)
        {
            if (intDamageIntensity == space_combat.LIGHT)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_light.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_med_damage_smoke.cef",
                    "clienteffect/lair_damage_medium.cef",
                    "clienteffect/combat_ship_hit_shield.cef"
                };
                int[] intDamageEffects = 
                {
                    0,
                    0,
                    1,
                    2
                };
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 10, 50);
                checkForAdditionalDamage(self, 10, space_crafting.NON_COMPONENT_DAMAGEABLES_LIGHT);
            }
            else if (intDamageIntensity == space_combat.HEAVY)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_heavy.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_med_damage_smoke.cef",
                    "clienteffect/lair_damage_medium.cef",
                    "clienteffect/combat_ship_hit_component.cef"
                };
                int[] intDamageEffects = 
                {
                    0,
                    0,
                    1,
                    3
                };
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 20, 50);
                checkForAdditionalDamage(self, 20, space_crafting.NON_COMPONENT_DAMAGEABLES_MEDIUM);
            }
        }
        if (intDamageType == space_combat.CHASSIS)
        {
            if (intDamageIntensity == space_combat.LIGHT)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_heavy.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_med_damage_smoke.cef",
                    "clienteffect/lair_damage_medium.cef",
                    "clienteffect/combat_ship_hit_component.cef"
                };
                int[] intDamageEffects = 
                {
                    0,
                    0,
                    1,
                    3
                };
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 5, 50);
                checkForAdditionalDamage(self, 30, space_crafting.NON_COMPONENT_DAMAGEABLES_MEDIUM);
            }
            else if (intDamageIntensity == space_combat.HEAVY)
            {
                playClientEffectLoc(((obj_id)objPlayers.get(0)), "clienteffect/int_camshake_heavy.cef", locTest, 0);
                String[] strEffects = 
                {
                    "clienteffect/lair_damage_light.cef",
                    "clienteffect/lair_med_damage_smoke.cef",
                    "clienteffect/lair_damage_medium.cef",
                    "clienteffect/combat_ship_hit_death.cef"
                };
                int[] intDamageEffects = 
                {
                    0,
                    0,
                    1,
                    3
                };
                playRandomInteriorClientEffects(((obj_id)objPlayers.get(0)), trSparkers, objCells, strEffects, intDamageEffects, 10, 50);
                checkForAdditionalDamage(self, 50, space_crafting.NON_COMPONENT_DAMAGEABLES_HEAVY);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void playRandomInteriorClientEffects(obj_id objPlayer, transform[] trSparkers, obj_id[] objCells, String[] strEffects, int[] intDamageEffects, int intRolls, int intChance) throws InterruptedException
    {
        final int[] DAMAGE_VALUES = 
        {
            0,
            15,
            30,
            75
        };
        final float[] DAMAGE_RANGES = 
        {
            0,
            2.0f,
            4.0f,
            8.0f
        };
        for (int intI = 0; intI < intRolls; intI++)
        {
            int intRoll = rand(1, 100);
            if (intRoll < intChance)
            {
                int intEffect = rand(0, strEffects.length - 1);
                String strEffect = strEffects[intEffect];
                int intDamageEffect = intDamageEffects[intEffect];
                int intTest = rand(0, trSparkers.length - 1);
                transform tr = trSparkers[intTest];
                location locTest = space_utils.getLocationFromTransform(tr);
                locTest.cell = objCells[intTest];
                playClientEffectLoc(objPlayer, strEffect, locTest, 0);
                if (intDamageEffect > 0)
                {
                    obj_id[] objPlayers = getAllPlayers(locTest, DAMAGE_RANGES[intDamageEffect]);
                    for (int intJ = 0; intJ < objPlayers.length; intJ++)
                    {
                        damage(objPlayers[intJ], DAMAGE_BLAST, rand(0, 5), rand(0, DAMAGE_VALUES[intDamageEffect]));
                    }
                }
            }
        }
    }
    public void checkForAdditionalDamage(obj_id objShip, int intChance, int[] intSlots) throws InterruptedException
    {
        LOG("space", "checkign for additional damage");
        int intRoll = rand(1, 100);
        if (intRoll < intChance)
        {
            int intSlot = intSlots[rand(0, intSlots.length - 1)];
            LOG("space", "chose " + intSlot + " looking for " + space_crafting.PLASMA_CONDUIT);
            if (intSlot == space_crafting.PLASMA_CONDUIT)
            {
                if (utils.hasScriptVar(objShip, "objPlasmaConduits"))
                {
                    obj_id[] objPlasmaConduits = utils.getObjIdArrayScriptVar(objShip, "objPlasmaConduits");
                    intRoll = rand(0, objPlasmaConduits.length - 1);
                    LOG("space", "conduits length are " + objPlasmaConduits.length);
                    LOG("space", "sending to " + objPlasmaConduits[intRoll]);
                    space_crafting.breakPlasmaConduit(objPlasmaConduits[intRoll], objShip, getLocation(objPlasmaConduits[intRoll]), true);
                }
                else 
                {
                    LOG("space", "No conduits");
                }
            }
            if (intSlot == space_crafting.HULL)
            {
                if (utils.hasScriptVar(objShip, "objHullPanels"))
                {
                    obj_id[] objHullPanels = utils.getObjIdArrayScriptVar(objShip, "objHullPanels ");
                    intRoll = rand(0, objHullPanels.length - 1);
                    LOG("space", "objHullPanels  length are " + objHullPanels.length);
                    LOG("space", "sending to " + objHullPanels[intRoll]);
                    space_crafting.breakHullPanel(objHullPanels[intRoll], objShip, getLocation(objHullPanels[intRoll]), true);
                }
            }
        }
    }
    public int doHullBreach(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "intHullBreached"))
        {
            float intDamage = getFloatObjVar(self, "intHullBreached");
            Vector objPlayers = space_transition.getContainedPlayers(self, null);
            if (objPlayers != null)
            {
                for (int intI = 0; intI < objPlayers.size(); intI++)
                {
                    string_id strSpam = new string_id("space/space_interaction", "hull_breach_damage");
                    damage(((obj_id)objPlayers.get(intI)), DAMAGE_BLAST, rand(0, 5), (int)intDamage);
                    sendSystemMessage(((obj_id)objPlayers.get(intI)), strSpam);
                }
            }
            messageTo(self, "doHullBreach", null, space_crafting.HULL_BREACH_DAMAGE_TIMER, false);
        }
        return SCRIPT_CONTINUE;
    }
}
