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
import script.library.space_utils;

public class shipoperations_falcon extends script.base_script
{
    public shipoperations_falcon()
    {
    }
    public static final string_id SID_OPERATIONS = new string_id("space/space_interaction", "operations");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship))
        {
            utils.setScriptVar(ship, "objOperationsChair", self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship))
        {
            utils.setScriptVar(ship, "objOperationsChair", self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(player);
        if (space_utils.playerCanControlShipSlot(objShip, player, true))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_OPERATIONS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id objShip = space_transition.getContainingShip(player);
            obj_id objOwner = getOwner(objShip);
            int intOwnerFaction = space_transition.getPlayerSpaceFaction(objOwner);
            int intMyFaction = space_transition.getPlayerSpaceFaction(player);
            if ((isGCWFaction(intOwnerFaction) || (isGCWFaction(intMyFaction))))
            {
                if (intOwnerFaction != intMyFaction)
                {
                    if (isGCWFaction(intOwnerFaction))
                    {
                        intMyFaction = pvpGetAlignedFaction(player);
                        if (intOwnerFaction != intMyFaction)
                        {
                            string_id strSpam = new string_id("space/space_interaction", "wrong_faction");
                            sendSystemMessage(player, strSpam);
                            return SCRIPT_CONTINUE;
                        }
                    }
                    else 
                    {
                        string_id strSpam = new string_id("space/space_interaction", "wrong_faction");
                        sendSystemMessage(player, strSpam);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (space_utils.playerCanControlShipSlot(objShip, player, false))
            {
                if (equip(player, self, "ship_operations_pob"))
                {
                    space_transition.grantDroidCommands(player);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isGCWFaction(int intFaction) throws InterruptedException
    {
        if (intFaction == (-615855020))
        {
            return true;
        }
        if (intFaction == (370444368))
        {
            return true;
        }
        return false;
    }
}
