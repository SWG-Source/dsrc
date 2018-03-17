package script.csr;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class get_resource_crate extends script.base_script
{
    public get_resource_crate()
    {
    }
    public static final int MAX_AMOUNT = 100000;
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isPlayer(self) && !isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        if (tok.hasMoreTokens())
        {
            String command = tok.nextToken();
            boolean notEnoughParams = false;
            if (command.equals("getResourceCrate"))
            {
                if (tok.hasMoreTokens())
                {
                    String resourceName = tok.nextToken();
                    if (tok.hasMoreTokens())
                    {
                        int amount = utils.stringToInt(tok.nextToken());
                        if (amount < 0)
                        {
                            sendSystemMessage(self, "getResourceCrate: FAILURE - Negative amounts of resources not allowed", "");
                            return SCRIPT_CONTINUE;
                        }
                        if (amount > MAX_AMOUNT)
                        {
                            sendSystemMessage(self, "getResourceCrate: FAILURE - Requested amount greater than the " + MAX_AMOUNT + " maximum.", "");
                            return SCRIPT_CONTINUE;
                        }
                        getResourceCrate(self, resourceName, amount);
                    }
                    else 
                    {
                        notEnoughParams = true;
                    }
                }
                else 
                {
                    notEnoughParams = true;
                }
                if (notEnoughParams)
                {
                    debugConsoleMsg(self, "Usage: getResourceCrate <resourceName> <amount>.  Example: getResourceCrate Evom 4000");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void getResourceCrate(obj_id player, String resourceName, int amount) throws InterruptedException
    {
        obj_id resourceType = getResourceTypeByName(resourceName);
        if (isIdValid(resourceType))
        {
            String crateTemplate = getResourceContainerForType(resourceType);
            if (!crateTemplate.equals(""))
            {
                obj_id pInv = utils.getInventoryContainer(player);
                if (pInv != null)
                {
                    obj_id crate = createObject(crateTemplate, pInv, "");
                    if ((crate == null) || (crate == obj_id.NULL_ID))
                    {
                        sendSystemMessage(player, "getResourceCrate: FAILURE - Could not create crate.", "");
                    }
                    else 
                    {
                        if (addResourceToContainer(crate, resourceType, amount, player))
                        {
                            sendSystemMessage(player, "getResourceCrate: Crate of " + resourceName + " created and filled with " + amount + " units.  It has been placed in your inventory.", "");
                        }
                        else 
                        {
                            sendSystemMessage(player, "getResourceCrate: FAILURE - Crate created, but could not be filled.", "");
                        }
                    }
                }
                else 
                {
                    sendSystemMessage(player, "getResourceCrate: FAILURE - Could not get your inventroy to put the container in.", "");
                }
            }
            else 
            {
                sendSystemMessage(player, "getResourceCrate: FAILURE - Could not get the crate template.", "");
            }
        }
        else 
        {
            sendSystemMessage(player, "getResourceCrate: FAILURE - Could not find resource type by name, check resource name.", "");
        }
    }
}
