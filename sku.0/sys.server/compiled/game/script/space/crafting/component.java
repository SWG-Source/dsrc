package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.space_transition;
import script.library.space_utils;
import script.library.static_item;
import script.library.utils;

public class component extends script.base_script
{
    public component()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String template = getTemplateName(self);
        if (template == null || template.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk") && !template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk5"))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + getName(self) + " was discovered and has initiated a collection reactor deletion/replacement messageTo.");
            messageTo(self, "startCollectionReactorReplacement", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        string_id strSpam = new string_id("space/space_interaction", "repair_single_kit");
        mi.addRootMenu(menu_info_types.ITEM_USE, strSpam);
        strSpam = new string_id("space/space_interaction", "repair_all_kits");
        mi.addRootMenu(menu_info_types.ITEM_ACTIVATE, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id objPlayer, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String strComponentType = getShipComponentDescriptorTypeName(getShipComponentDescriptorType(self));
            Vector objKits = space_crafting.getRepairKitsForItem(objPlayer, strComponentType);
            if ((objKits == null) || (objKits.size() == 0))
            {
                string_id strSpam = new string_id("space/space_interaction", "no_kits");
                sendSystemMessage(objPlayer, strSpam);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                space_crafting.repairComponentInInventory(self, objKits, objPlayer);
            }
        }
        else if (item == menu_info_types.ITEM_ACTIVATE)
        {
            String strComponentType = getShipComponentDescriptorTypeName(getShipComponentDescriptorType(self));
            Vector objKits = space_crafting.getRepairKitsForItem(objPlayer, strComponentType);
            if ((objKits == null) || (objKits.size() == 0))
            {
                string_id strSpam = new string_id("space/space_interaction", "no_kits");
                sendSystemMessage(objPlayer, strSpam);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                Vector objNewKits = new Vector();
                objNewKits.setSize(1);
                objNewKits = utils.addElement(objNewKits, ((obj_id)objKits.get(0)));
                space_crafting.repairComponentInInventory(self, objKits, objPlayer);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        blog("component_fix", "INIT - OnAboutToBeTransferred Test check");
        String template = getTemplateName(self);
        blog("component_fix", "INIT - OnAboutToBeTransferred template: " + template);
        if (template == null || template.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (!template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk"))
        {
            blog("component_fix", "INIT - OnTransferred I am not collection_reward_reactor_01_mk");
            return SCRIPT_CONTINUE;
        }
        else if (template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk5"))
        {
            blog("component_fix", "INIT - OnTransferred I am collection_reward_reactor_01_mk5");
            return SCRIPT_CONTINUE;
        }
        blog("component_fix", "INIT - OnAboutToBeTransferred I am a bad reactor");
        String objectName = getName(self);
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " was discovered and is about to be transferred.");
        if (utils.isBazaarObject(destContainer) || utils.isVendorObject(destContainer))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " was discovered and was about to be placed for sale. ABORTING!");
            blog("component_fix", "INIT - IS VENDOR/BAZAAR ABORT");
            messageTo(self, "handlerReInitialize", null, 0, false);
            return SCRIPT_OVERRIDE;
        }
        messageTo(self, "handlerReInitialize", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int forceCollectionReactorInit(obj_id self, dictionary params) throws InterruptedException
    {
        String template = getTemplateName(self);
        if (template == null || template.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk") && !template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk5"))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + getName(self) + " has been forced by someone in godmode. The reactor was discovered and has initiated a collection reactor deletion/replacement messageTo.");
            messageTo(self, "startCollectionReactorReplacement", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int startCollectionReactorReplacement(obj_id self, dictionary params) throws InterruptedException
    {
        String objectName = getName(self);
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " has started the eval stage of the collection reactor deletion/replacement.");
        String template = getTemplateName(self);
        if (template == null || template.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk"))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is template: " + template);
            if (template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk5"))
            {
                CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is not the template we want, bailing out.  This object remains unchanged: " + template);
                return SCRIPT_CONTINUE;
            }
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is template: " + template + " and needs to be swapped out with a schematic.");
            blog("component_fix", "INIT - FOUND COLLECTION REACTOR: " + template);
            obj_id container = utils.getContainedBy(self);
            blog("component_fix", "INIT - CONTAINER: " + container);
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in container: " + container);
            if (!isIdValid(container))
            {
                CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in container: " + container + " is not valid. Bailing out");
                return SCRIPT_CONTINUE;
            }
            if (!utils.isContainer(container))
            {
                CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in container: " + container + " and this is not a backpack or other player container.  Maybe it is a cell in a house.");
                if (!utils.isNestedWithinAPlayer(container))
                {
                    CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in container: " + container + " and is not nested in player.");
                    blog("component_fix", "INIT - ISNT IN PLAYER: " + container);
                    boolean success = getUsableContainer(self, container);
                    if (success)
                    {
                        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " will be destroyed and has been replaced with a schematic.");
                        blog("component_fix", "INIT - ITEM PLACED IN CELL: " + container);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " could not be replaced and will not be destroyed. This is a major issue.");
                        blog("component_fix", "INIT - ITEM FAILED TO BE CREATED IN CELL: " + container);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (utils.isInBazaar(self))
            {
                CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in a Vendor. We will try to force reinit of the object so that the reactor becomes a schematic upon receipt by the player.");
                blog("component_fix", "INIT - IN BAZAAR");
                messageTo(self, "handlerReInitialize", null, 5.0f, false);
                return SCRIPT_CONTINUE;
            }
            else if (utils.isInVendor(self))
            {
                CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in a Vendor. We will try to force reinit of the object so that the reactor becomes a schematic upon receipt by the player.");
                blog("component_fix", "INIT - IN VENDOR");
                messageTo(self, "handlerReInitialize", null, 5.0f, false);
                return SCRIPT_CONTINUE;
            }
            if (getContainerType(container) != 2)
            {
                container = utils.getInventoryContainer(container);
                if (getContainerType(container) != 2)
                {
                    CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " could not be replaced because it resides in a psuedo container. Container type: " + getContainerType(container));
                    LOG("component_fix", objectName + "(" + self + ") could not be made because we failed the inventory container check. Container type: " + getContainerType(container));
                    return SCRIPT_CONTINUE;
                }
            }
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " will be destroyed and has been replaced with a schematic.");
            blog("component_fix", "INIT - PLAYER CONTAINER VALID: " + container);
            boolean finalStatus = uninstallDeleteReplaceCollectionReactor(self, container);
            if (!finalStatus)
            {
                blog("component_fix", "INIT - OPJECT NOT CREATED IN PLAYER");
            }
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " was discovered and has initiated a collection reactor deletion/replacement.");
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyDeprecatedCollectionReactor(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + getName(self) + " is about to be destroyed. Good bye world!");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handlerReInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + getName(self) + " is being reinitialized. This message is currently initiated due to the object being in a vendor.");
        Object[] newParams = new Object[1];
        newParams[0] = self;
        blog("component_fix", "INIT - REININT HANDLER");
        utils.callTrigger("OnInitialize", newParams);
        return SCRIPT_CONTINUE;
    }
    public boolean getUsableContainer(obj_id self, obj_id container) throws InterruptedException
    {
        String objectName = getName(self);
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in an unknown location: (" + container + ") so we go through this function to see if we can swap out the object safely");
        if (!isIdValid(container))
        {
            return false;
        }
        if (utils.isInBazaar(self))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in a Vendor. We will try to force reinit of the object so that the reactor becomes a schematic upon receipt by the player.");
            blog("component_fix", "INIT - IN BAZAAR");
            messageTo(self, "handlerReInitialize", null, 0, false);
            return false;
        }
        else if (utils.isInVendor(self))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in a Vendor. We will try to force reinit of the object so that the reactor becomes a schematic upon receipt by the player.");
            blog("component_fix", "INIT - IN VENDOR");
            messageTo(self, "handlerReInitialize", null, 0, false);
            return false;
        }
        String template = getTemplateName(container);
        blog("component_fix", "INIT - CELL TEMPLATE: " + template);
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in an unknown location: (" + container + "). The template is (" + template + ")");
        if (template.equals("object/cell/cell.iff"))
        {
            blog("component_fix", "INIT - CELL FOUND");
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in a Cell.  We will swap out the reactor with the correct schematic at the location of the reactor.");
            location compLoc = getLocation(self);
            blog("component_fix", "INIT - LOCATION: " + compLoc);
            if (!isValidLocation(compLoc, 1.f))
            {
                CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in a Cell and the location received is not valid. We have no choice but to abort. The bugged reactor will remain in: " + container);
                blog("component_fix", "INIT - LOCATION NOT VALID");
                return false;
            }
            boolean finalStatus = uninstallDeleteReplaceCollectionReactor(self, container, compLoc);
            if (!finalStatus)
            {
                CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " could not be replaced with a schematic for some reason. We have no choice but to abort. The bugged reactor will remain in: " + container);
                blog("component_fix", "INIT - FAILED TO CREATE OBJECT IN CELL!!!!");
                return false;
            }
            blog("component_fix", "INIT - CREATED/REPLACED IN CELL !!!!");
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " Was successfully replaced with a schematic. We are green for deletion from container: " + container);
            messageTo(self, "destroyDeprecatedCollectionReactor", null, 0, false);
            return true;
        }
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is in a location we didn't plan for: " + container + ". We must abort the process and hope the player moves the reactor to a new location soon.");
        blog("component_fix", "INIT - THIS OBJECT IN UNKNOWN LOCATION!!!!");
        return false;
    }
    public boolean uninstallDeleteReplaceCollectionReactor(obj_id self, obj_id container) throws InterruptedException
    {
        String objectName = getName(self);
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is about to start the replacement process.  We are assuming the object is in the player inventory or a container somewhere in a structure.");
        if (!isIdValid(container))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " somehow shows it is in an invalid container. Aborting.");
            return false;
        }
        return uninstallDeleteReplaceCollectionReactor(self, container, null);
    }
    public boolean uninstallDeleteReplaceCollectionReactor(obj_id self, obj_id container, location objLocation) throws InterruptedException
    {
        String objectName = getName(self);
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " has started the replacement process.  ");
        if (!isIdValid(container))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " somehow shows it is in an invalid container. Aborting.");
            return false;
        }
        blog("component_fix", "INIT - uninstallDeleteReplaceCollectionReactor CONTAINER: " + container);
        String template = getTemplateName(self);
        if (!template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk"))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " somehow mistakingly made it this far. It is not the reactor we want. Aborting.");
            return false;
        }
        if (template.startsWith("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk5"))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " somehow mistakingly made it this far. Since it is the Mark 5 reactor it is not the reactor we want. Aborting.");
            return false;
        }
        boolean issuedNew = false;
        obj_id newItem = null;
        String markVersion = "";
        String replacementSchematic = "";
        if (template.equals("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk1.iff"))
        {
            markVersion = "1";
            replacementSchematic = space_crafting.COLLECTION_REWARD_REACTOR_MK1_V2;
        }
        else if (template.equals("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk2.iff"))
        {
            markVersion = "2";
            replacementSchematic = space_crafting.COLLECTION_REWARD_REACTOR_MK2_V2;
        }
        else if (template.equals("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk3.iff"))
        {
            markVersion = "3";
            replacementSchematic = space_crafting.COLLECTION_REWARD_REACTOR_MK3_V2;
        }
        else if (template.equals("object/tangible/ship/crafted/reactor/collection_reward_reactor_01_mk4.iff"))
        {
            markVersion = "4";
            replacementSchematic = space_crafting.COLLECTION_REWARD_REACTOR_MK4_V2;
        }
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " IS MARK " + markVersion + " and will be replace with " + replacementSchematic + ".");
        blog("component_fix", "Component: (" + self + ") " + objectName + " IS MARK " + markVersion + " and will be replace with " + replacementSchematic + ".");
        if (markVersion == null || markVersion.equals(""))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " WAS NOT REPLACED with a new schematic because the reactor version could not be determined. Could have been a Mark 5 reactor and we dont care about mark 5.");
            blog("component_fix", "INIT - Could not find correct reactor version, maybe this is mark 5.");
            return false;
        }
        if (replacementSchematic == null || replacementSchematic.equals(""))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " WAS NOT REPLACED with a new schematic because the replacemen schematic was not resolved.");
            blog("component_fix", "INIT - Could not find correct schematic version.");
            return false;
        }
        blog("component_fix", "INIT - FOUND Mark " + markVersion + " REACTOR");
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is a Mark " + markVersion + " Reactor (AKA Koensayr Star Synthesis Archtype) and will be replaced with a new Mark " + markVersion + " schematic.");
        if (objLocation != null)
        {
            blog("component_fix", "INIT - LOCATION NOT NULL, CREATING IN CELL: ");
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is a Mark " + markVersion + " Reactor and is located at: (" + objLocation + ") in container: (" + container + ") will be replaced with a new Mark " + markVersion + " schematic: " + replacementSchematic + ".");
            newItem = static_item.createNewItemFunction(replacementSchematic, container, objLocation);
        }
        else 
        {
            blog("component_fix", "INIT - CREATE IN PLAYER CONTAINER: " + container);
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is a Mark " + markVersion + " Reactor in container: (" + container + ") will be replaced with a new Mark 1 schematic: " + replacementSchematic + ".");
            newItem = static_item.createNewItemFunction(replacementSchematic, container);
        }
        if (isIdValid(newItem))
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " is a Mark " + markVersion + " Reactor in container: (" + container + ") and HAS BEEN REPLACED with a new Mark 1 schematic: " + replacementSchematic + ".");
            blog("component_fix", "INIT - CREATED NEW SCHEMATIC: ");
            issuedNew = true;
        }
        if (!issuedNew)
        {
            CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " WAS NOT REPLACED with a new schematic.");
            blog("component_fix", "INIT - FAILED TO CREATE NEW SCHEMATIC!!!!!!!!!!!! ");
            return false;
        }
        if (isObjectPersisted(self) && !isObjectPersisted(newItem))
        {
            persistObject(newItem);
        }
        CustomerServiceLog("ShipComponents", "Component: (" + self + ") " + objectName + " Was successfully replaced with a schematic. We are green for deletion from container: " + container);
        messageTo(self, "destroyDeprecatedCollectionReactor", null, 0, false);
        return true;
    }
    public boolean blog(String category, String msg) throws InterruptedException
    {
        return true;
    }
}
