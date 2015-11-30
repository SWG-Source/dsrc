package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.space_utils;
import script.library.utils;
import script.library.space_transition;

public class interior_component extends script.base_script
{
    public interior_component()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("space", "ONATTACH GOING OFF ON INTERIOR COMPONETNES");
        setInvulnerable(self, true);
        String strManagerName = getStringObjVar(self, "strManagerName");
        dictionary dctInfo = dataTableGetRow("datatables/space_crafting/interior_component_lookup.iff", strManagerName);
        if (dctInfo == null)
        {
            LOG("space", "MAJOR PROBLEM WITH " + self + " no component type specified!");
            return SCRIPT_CONTINUE;
        }
        String strOverloadName = dctInfo.getString("strOverloadName");
        String strSlot = dctInfo.getString("strSlot");
        int intSlot = space_crafting.getExtendedComponentType(strSlot);
        obj_id objShip = space_transition.getContainingShip(self);
        if (!strOverloadName.equals(""))
        {
            string_id strSpam = new string_id("space/space_item", strOverloadName);
            setName(self, strSpam);
        }
        LOG("space", "Slot is " + intSlot);
        setObjVar(self, "intSlot", intSlot);
        if (intSlot > -1)
        {
            if (isShipSlotInstalled(objShip, intSlot))
            {
                if (utils.hasScriptVar(objShip, "objInteriorManage" + intSlot))
                {
                    LOG("space", "2 objects of type " + intSlot);
                }
                utils.setScriptVar(objShip, "objInteriorManager" + intSlot, self);
                LOG("space", "setting local var to objInteriorManager" + intSlot);
                float fltCurrentHitpoints = getShipComponentHitpointsCurrent(objShip, intSlot);
                if (fltCurrentHitpoints == 0)
                {
                    setInvulnerableHitpoints(self, 1);
                }
            }
            else 
            {
                LOG("space", "Component " + intSlot + " is not installed on " + objShip + " cleaning up interior piece");
                if (utils.checkConfigFlag("ScriptFlags", "liveSpaceServer"))
                {
                    destroyObject(self);
                }
            }
        }
        else 
        {
            LOG("space", "NON REAL COMPONENT");
            if (intSlot == space_crafting.PLASMA_CONDUIT)
            {
                LOG("space", "plasma conduit");
                if (utils.hasScriptVar(objShip, "objPlasmaConduits"))
                {
                    Vector objPlasmaConduits = utils.getResizeableObjIdArrayScriptVar(objShip, "objPlasmaConduits");
                    objPlasmaConduits = utils.addElement(objPlasmaConduits, self);
                    utils.setScriptVar(objShip, "objPlasmaConduits", objPlasmaConduits);
                    LOG("space", "plasma already has a conduit");
                }
                else 
                {
                    LOG("space", "no conduits ");
                    Vector objPlasmaConduits = new Vector();
                    objPlasmaConduits.setSize(0);
                    objPlasmaConduits = utils.addElement(objPlasmaConduits, self);
                    utils.setScriptVar(objShip, "objPlasmaConduits", objPlasmaConduits);
                }
                String strConduitSlot = dctInfo.getString("strConduitSlot");
                if (!strConduitSlot.equals(""))
                {
                    int intConduitSlot = space_crafting.getExtendedComponentType(strConduitSlot);
                    setObjVar(self, "intConduitSlot", intConduitSlot);
                    string_id strSpam = new string_id("space/space_item", "conduit_" + strConduitSlot);
                    setName(self, strSpam);
                }
                location locTest = getLocation(self);
                locTest.area = "";
                location[] locBrokenComponents = getLocationArrayObjVar(objShip, "locBrokenComponents");
                int intIndex = utils.getElementPositionInArray(locBrokenComponents, locTest);
                if (intIndex > -1)
                {
                    space_crafting.breakPlasmaConduit(self, objShip, locTest, false);
                }
            }
            else if (intSlot == space_crafting.HULL)
            {
                LOG("space", "plasma objHullPanels");
                if (utils.hasScriptVar(objShip, "objHullPanels"))
                {
                    Vector objHullPanels = utils.getResizeableObjIdArrayScriptVar(objShip, "objHullPanels");
                    objHullPanels = utils.addElement(objHullPanels, self);
                    utils.setScriptVar(objShip, "objHullPanels", objHullPanels);
                    LOG("space", "plasma already has a conduit");
                }
                else 
                {
                    LOG("space", "no objHullPanels ");
                    Vector objHullPanels = new Vector();
                    objHullPanels.setSize(0);
                    objHullPanels = utils.addElement(objHullPanels, self);
                    utils.setScriptVar(objShip, "objHullPanels", objHullPanels);
                }
                location locTest = getLocation(self);
                locTest.area = "";
                location[] locBrokenComponents = getLocationArrayObjVar(objShip, "locBrokenComponents");
                int intIndex = utils.getElementPositionInArray(locBrokenComponents, locTest);
                if (intIndex > -1)
                {
                    space_crafting.breakHullPanel(self, objShip, locTest, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, "intSlot"))
        {
            menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            string_id strSpam = new string_id("space/space_interaction", "repair");
            mi.addRootMenu(menu_info_types.ITEM_USE, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id objPlayer, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasObjVar(self, "intSlot"))
            {
                return SCRIPT_CONTINUE;
            }
            location locTest1 = getLocation(self);
            location locTest2 = getLocation(objPlayer);
            float fltDistance = getDistance(locTest1, locTest2);
            if (fltDistance > 10)
            {
                string_id strSpam = new string_id("space/space_interaction", "target_too_far");
                sendSystemMessage(objPlayer, strSpam);
                return SCRIPT_CONTINUE;
            }
            int intSlot = getIntObjVar(self, "intSlot");
            Vector objKits = space_crafting.getRepairKitsForItem(objPlayer, intSlot);
            if ((objKits == null) || (objKits.size() < 1))
            {
                string_id strSpam = new string_id("space/space_interaction", "no_kits");
                sendSystemMessage(objPlayer, strSpam);
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(objPlayer, "component_repair_time"))
            {
                int lastRepairTime = utils.getIntScriptVar(objPlayer, "component_repair_time");
                if (lastRepairTime + 30 > getGameTime())
                {
                    string_id strSpam = new string_id("space/space_interaction", "cannot_repair_yet");
                    sendSystemMessage(objPlayer, strSpam);
                    return SCRIPT_CONTINUE;
                }
            }
            if (intSlot < 0)
            {
                String strKitType = space_crafting.getExtendedComponentType(intSlot);
                if (intSlot == space_crafting.PLASMA_CONDUIT)
                {
                    if (strKitType.equals("plasma_conduit"))
                    {
                        if (hasCondition(self, CONDITION_ON))
                        {
                            space_crafting.fixPlasmaConduit(self, space_transition.getContainingShip(objPlayer), getLocation(self));
                            utils.setScriptVar(objPlayer, "component_repair_time", getGameTime());
                            int intCount = getCount(((obj_id)objKits.get(0)));
                            intCount = intCount - 1;
                            if (intCount < 1)
                            {
                                string_id strSpam = new string_id("space/space_interaction", "fix_plasma_conduit_destroy");
                                sendSystemMessage(objPlayer, strSpam);
                                destroyObject(((obj_id)objKits.get(0)));
                            }
                            else 
                            {
                                string_id strSpam = new string_id("space/space_interaction", "fix_plasma_conduit");
                                sendSystemMessage(objPlayer, strSpam);
                                setCount(((obj_id)objKits.get(0)), intCount);
                            }
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            string_id strSpam = new string_id("space/space_interaction", "no_damage_to_repair");
                            sendSystemMessage(objPlayer, strSpam);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
            else 
            {
                LOG("space", "name is " + getShipComponentName(space_transition.getContainingShip(objPlayer), intSlot));
                space_crafting.repairComponentOnShip(intSlot, objKits, objPlayer, space_transition.getContainingShip(objPlayer));
                utils.setScriptVar(objPlayer, "component_repair_time", getGameTime());
            }
        }
        return SCRIPT_CONTINUE;
    }
}
