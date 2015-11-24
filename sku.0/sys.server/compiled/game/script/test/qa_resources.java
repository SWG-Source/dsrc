package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.utils;
import script.library.qa;
import script.library.sui;

public class qa_resources extends script.base_script
{
    public qa_resources()
    {
    }
    public static final String SCRIPTVAR = "resource";
    public static final String[] MAIN_MENU = 
    {
        "Recycled Resources",
        "Space Resources"
    };
    public static final String[] RECYCLED_MAIN = 
    {
        "Chemical",
        "Creature",
        "Flora",
        "Metal",
        "Ore"
    };
    public static final String[] RECYCLED_CHEMICAL = 
    {
        "chemical",
        "fuel_petrochem_solid",
        "radioactive",
        "water"
    };
    public static final String[] RECYCLED_CREATURE = 
    {
        "bone",
        "bone_horn",
        "hide",
        "meat",
        "milk",
        "seafood"
    };
    public static final String[] RECYCLED_FLORA = 
    {
        "cereal",
        "fruit",
        "vegetable",
        "wood"
    };
    public static final String[] RECYCLED_METAL = 
    {
        "metal_ferrous",
        "metal_nonferrous"
    };
    public static final String[] RECYCLED_ORE = 
    {
        "ore_igneous",
        "ore_sedimentary",
        "gemstone"
    };
    public static final String[] SPACE_RESOURCE_CONST = 
    {
        "space_chemical_acid",
        "space_chemical_cyanomethanic",
        "space_chemical_petrochem",
        "space_chemical_sulfuric",
        "space_gas_methane",
        "space_gas_organometallic",
        "space_gem_crystal",
        "space_gem_diamond",
        "space_metal_carbonaceous",
        "space_metal_ice",
        "space_metal_iron",
        "space_metal_obsidian",
        "space_metal_silicaceous"
    };
    public static final String RESOURCE_TOOL_DESCRIPTION = "This Tool will automatically spawn the space resources selected into the tester inventory.";
    public static final String TITLE = "QA Resource Tool";
    public static final int RECYCLED_AMOUNT = 100000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_resources");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_resources");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if (((toLower(text)).equals("qaresource")) || ((toLower(text)).equals("qaresources")))
            {
                qa.refreshMenu(self, "Select a Resource Type", TITLE, MAIN_MENU, "startingMenuOptions", true, "resource.pid", "resource.mainMenu");
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int startingMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "resource.pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    qa.removePlayer(player, SCRIPTVAR, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    qa.qaToolMainMenu(player);
                    utils.removeScriptVarTree(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String[] previousMainMenuArray = utils.getStringArrayScriptVar(self, "resource.mainMenu");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals("Recycled Resources"))
                    {
                        qa.refreshMenu(self, "Select a Resource Type", TITLE, RECYCLED_MAIN, "recycledMenuOptions", false, "resource.pid", "resource.recycledMain");
                        return SCRIPT_OVERRIDE;
                    }
                    else if (previousSelection.equals("Space Resources"))
                    {
                        qa.refreshMenu(player, "Select a Resource Type", TITLE, SPACE_RESOURCE_CONST, "spaceResourceHandler", false, "resource.pid", "resource.spaceResource");
                    }
                    else if (previousSelection.equals("Common Resources"))
                    {
                        craftinglib.makeBestResource(self, "steel", 1000000);
                        craftinglib.makeBestResource(self, "iron", 1000000);
                        craftinglib.makeBestResource(self, "copper", 1000000);
                        craftinglib.makeBestResource(self, "fuel_petrochem_solid", 1000000);
                        craftinglib.makeBestResource(self, "radioactive", 1000000);
                        craftinglib.makeBestResource(self, "aluminum", 1000000);
                        craftinglib.makeBestResource(self, "ore_extrusive", 1000000);
                        craftinglib.makeBestResource(self, "petrochem_inert", 1000000);
                        craftinglib.makeBestResource(self, "fiberplast", 1000000);
                        craftinglib.makeBestResource(self, "gas_inert", 1000000);
                        craftinglib.makeBestResource(self, "gas_reactive", 1000000);
                        debugSpeakMsg(self, "Completed.");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(player, "Tool Failed.");
                        qa.removePlayer(player, SCRIPTVAR, "");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int recycledMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "resource.pid"))
            {
                qa.checkParams(params, "resource");
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    qa.refreshMenu(self, "Select a Resource Type", TITLE, MAIN_MENU, "startingMenuOptions", true, "resource.pid", "resource.mainMenu");
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    String[] previousMainMenuArray = utils.getStringArrayScriptVar(self, "resource.recycledMain");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals("Chemical"))
                    {
                        qa.refreshMenu(self, "Select a Resource Type", TITLE, RECYCLED_CHEMICAL, "allRecycledMenuOptions", false, "resource.pid", "resource.allRecycled");
                        return SCRIPT_OVERRIDE;
                    }
                    else if (previousSelection.equals("Creature"))
                    {
                        qa.refreshMenu(self, "Select a Resource Type", TITLE, RECYCLED_CREATURE, "allRecycledMenuOptions", false, "resource.pid", "resource.allRecycled");
                        return SCRIPT_OVERRIDE;
                    }
                    else if (previousSelection.equals("Flora"))
                    {
                        qa.refreshMenu(self, "Select a Resource Type", TITLE, RECYCLED_FLORA, "allRecycledMenuOptions", false, "resource.pid", "resource.allRecycled");
                        return SCRIPT_OVERRIDE;
                    }
                    else if (previousSelection.equals("Metal"))
                    {
                        qa.refreshMenu(self, "Select a Resource Type", TITLE, RECYCLED_METAL, "allRecycledMenuOptions", false, "resource.pid", "resource.allRecycled");
                        return SCRIPT_OVERRIDE;
                    }
                    else if (previousSelection.equals("Ore"))
                    {
                        qa.refreshMenu(self, "Select a Resource Type", TITLE, RECYCLED_ORE, "allRecycledMenuOptions", false, "resource.pid", "resource.allRecycled");
                        return SCRIPT_OVERRIDE;
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(player, "Tool Failed.");
                        qa.removePlayer(player, SCRIPTVAR, "");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int allRecycledMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "resource.pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    qa.refreshMenu(self, "Select a Resource Type", TITLE, MAIN_MENU, "startingMenuOptions", true, "resource.pid", "resource.mainMenu");
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    String[] previousMainMenuArray = utils.getStringArrayScriptVar(self, "resource.allRecycled");
                    String previousSelection = previousMainMenuArray[idx];
                    createResourceInInventory(player, previousSelection);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned " + previousSelection + " (a recycled resource) using the QA Resource Tool.");
                    qa.refreshMenu(self, "Select a Resource Type", TITLE, previousMainMenuArray, "allRecycledMenuOptions", false, "resource.pid", "resource.allRecycled");
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spaceResourceHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "resource.pid"))
            {
                qa.checkParams(params, "resource");
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "resource.spaceResource");
                if (btn == sui.BP_CANCEL)
                {
                    qa.refreshMenu(self, "Select a Resource Type", TITLE, MAIN_MENU, "startingMenuOptions", true, "resource.pid", "resource.mainMenu");
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        qa.removePlayer(player, SCRIPTVAR, "");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        obj_id[] rtypes = getResourceTypes(previousSelection);
                        obj_id rtype = rtypes[0];
                        if (!isIdValid(rtype))
                        {
                            sendSystemMessageTestingOnly(self, "No id found");
                            sendSystemMessageTestingOnly(self, "Space Resource " + previousSelection + " could not be spawned. Report this to the tools team.");
                            return SCRIPT_CONTINUE;
                        }
                        String crateTemplate = getResourceContainerForType(rtype);
                        if (!crateTemplate.equals(""))
                        {
                            obj_id pInv = utils.getInventoryContainer(player);
                            if (!isIdNull(pInv))
                            {
                                obj_id crate = createObject(crateTemplate, pInv, "");
                                if (addResourceToContainer(crate, rtype, 100000, self))
                                {
                                    sendSystemMessageTestingOnly(self, "Resource of class " + previousSelection + " placed in inventory.");
                                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned " + previousSelection + " (a space resource) using the QA Resource Tool.");
                                    qa.refreshMenu(player, RESOURCE_TOOL_DESCRIPTION, TITLE, SPACE_RESOURCE_CONST, "spaceResourceHandler", false, "resource.pid", "resource.spaceResource");
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void createResourceInInventory(obj_id player, String resourceTypeName) throws InterruptedException
    {
        obj_id resourceId = pickRandomNonDepeletedResource(resourceTypeName);
        sendSystemMessageTestingOnly(player, "resourceId " + resourceId);
        if (!isIdNull(resourceId))
        {
            obj_id recycle = getRecycledVersionOfResourceType(resourceId);
            obj_id inv = utils.getInventoryContainer(player);
            obj_id generic = createResourceCrate(recycle, RECYCLED_AMOUNT, inv);
            sendSystemMessageTestingOnly(player, "Resource placed in inventory.");
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "The function failed because there were no resources of this type found on the server.");
        }
        qa.removePlayer(player, SCRIPTVAR, "");
    }
}
