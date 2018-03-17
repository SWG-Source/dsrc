package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.sui;
import script.library.target_dummy;
import script.library.utils;
import script.library.veteran_deprecated;

public class resource extends script.base_script
{
    public resource()
    {
    }
    public static final int RESOURCE_AMOUNT = 30000;
    public static final String ROOT_RESOURCE_CLASS = "resource";
    public static final String ROOT_ORGANIC_CLASS = "organic";
    public static final String ROOT_INORGANIC_CLASS = "inorganic";
    public static final String OBJVAR_RESOURCE_REWARDED = "rewarded";
    public static final String SCRIPTVAR_INUSE = "inuse";
    public static final string_id SID_RESOURCE_TITLE = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "resource_title");
    public static final string_id SID_CHOOSE_CLASS = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "choose_class");
    public static final string_id SID_CHOOSE_SUB_CLASS = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "choose_sub_class");
    public static final string_id SID_CHOOSE_TYPE = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "choose_type");
    public static final string_id SID_CONFIRM_RESOURCE_SELECTION = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "confirm_choose_type");
    public static final string_id SID_RESOURCE_NAME = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "resource_name");
    public static final string_id SID_USE = new string_id("ui_radial", "item_use");
    public static final String SCRIPTVAR_BASE_CLASS = "resource.base";
    public static final String SCRIPTVAR_SUB_CLASSES = "resource.subclass";
    public static final String SCRIPTVAR_TYPES = "resource.types";
    public static final String SCRIPTVAR_RESOURCECHOSEN = "resource.resoucechosen";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_RESOURCE_REWARDED))
        {
            messageTo(self, "handleVeteranHarvestDestroy", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_RESOURCE_REWARDED))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPTVAR_INUSE))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.getContainingPlayer(self) == player)
        {
            item.addRootMenu(menu_info_types.ITEM_USE, SID_USE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (utils.hasScriptVar(self, SCRIPTVAR_INUSE))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, OBJVAR_RESOURCE_REWARDED))
            {
                return SCRIPT_CONTINUE;
            }
            ((getSelf()).getScriptVars()).put(SCRIPTVAR_INUSE, 1);
            chooseResourceClass(player, ROOT_RESOURCE_CLASS, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleVeteranHarvestDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_RESOURCE_REWARDED))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChooseResourceClass(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_INUSE))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            
            {
                String[] resourceClasses = (self.getScriptVars()).getStringArray(SCRIPTVAR_SUB_CLASSES);
                int rowSelected = sui.getListboxSelectedRow(params);
                if (rowSelected >= 0 && rowSelected < resourceClasses.length)
                {
                    chooseResourceClass(sui.getPlayerId(params), resourceClasses[rowSelected]);
                }
                else 
                {
                    String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                    chooseResourceClass(sui.getPlayerId(params), resourceClass);
                }
            }
            break;
            case sui.BP_CANCEL:
            
            {
                String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                if (!resourceClass.equals(ROOT_ORGANIC_CLASS) && !resourceClass.equals(ROOT_INORGANIC_CLASS))
                {
                    chooseResourceClass(sui.getPlayerId(params), getResourceParentClass(resourceClass));
                }
                else 
                {
                    chooseResourceClass(sui.getPlayerId(params), getResourceParentClass(resourceClass), true);
                }
            }
            break;
            default:
            cleanup();
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChooseResourceType(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_INUSE))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
                obj_id[] resourceTypes = (self.getScriptVars()).getObjIdArray(SCRIPTVAR_TYPES);
                int rowSelected = sui.getListboxSelectedRow(params);
                if (rowSelected >= 0 && rowSelected < resourceTypes.length)
                {
                    showResourceStats(sui.getPlayerId(params), resourceTypes[rowSelected]);
                    //cleanup();
                }
                else
                {
                    String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                    chooseResourceClass(sui.getPlayerId(params), resourceClass);
                }
                break;
            case sui.BP_CANCEL:
                String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                if (!resourceClass.equals(ROOT_RESOURCE_CLASS))
                {
                    chooseResourceClass(sui.getPlayerId(params), getResourceParentClass(resourceClass));
                    break;
                }
                // note: break is purposefully missing here such to carry over to default case and cleanup.
            default:
                cleanup();
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChooseResourceTypeStats(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_INUSE))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            
            {
                obj_id resourceChosen = (self.getScriptVars()).getObjId(SCRIPTVAR_RESOURCECHOSEN);
                if (isIdValid(resourceChosen))
                {
                    String title = utils.packStringId(new string_id("veteran", "resource_deed_confirmation_title"));
                    String testMsg = utils.packStringId(new string_id("veteran", "resource_deed_confirmation_prompt"));
                    resource_attribute[] resourceAttribs = getResourceAttributes(resourceChosen);
                    if (resourceAttribs == null)
                    {
                        cleanup();
                        return SCRIPT_CONTINUE;
                    }
                    testMsg += target_dummy.addLineBreaks(2);
                    testMsg += target_dummy.ORANGE + "@" + SID_RESOURCE_NAME + " = " + target_dummy.YELLOW + getResourceName(resourceChosen);
                    testMsg += target_dummy.addLineBreaks(1);
                    for (int i = 0; i < resourceAttribs.length; ++i)
                    {
                        string_id temp = new string_id("obj_attr_n", resourceAttribs[i].getName());
                        testMsg += target_dummy.GREEN + "@" + temp + " = " + target_dummy.WHITE + resourceAttribs[i].getValue();
                        testMsg += target_dummy.addLineBreaks(1);
                    }
                    int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, sui.getPlayerId(params), "handleCreateChosenResourceConfirm");
                    setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
                    setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, testMsg);
                    sui.msgboxButtonSetup(pid, sui.OK_CANCEL);
                    setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Confirm");
                    setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "@back");
                    sui.showSUIPage(pid);
                }
                else 
                {
                    String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                    chooseResourceClass(sui.getPlayerId(params), resourceClass);
                }
            }
            break;
            case sui.BP_CANCEL:
            
            {
                String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                if (!resourceClass.equals(ROOT_RESOURCE_CLASS))
                {
                    chooseResourceClass(sui.getPlayerId(params), getResourceParentClass(resourceClass));
                    break;
                }
            }
            default:
            cleanup();
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCreateChosenResourceConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPTVAR_INUSE))
        {
            cleanup();
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            
            {
                obj_id resourceChosen = (self.getScriptVars()).getObjId(SCRIPTVAR_RESOURCECHOSEN);
                if (isIdValid(resourceChosen))
                {
                    obj_id player = sui.getPlayerId(params);
                    obj_id crate = createResourceCrate(resourceChosen, RESOURCE_AMOUNT, utils.getInventoryContainer(player));
                    if (isIdValid(crate))
                    {
                        setObjVar(self, OBJVAR_RESOURCE_REWARDED, true);
                        messageTo(self, "handleVeteranHarvestDestroy", null, 1, false);
                        CustomerServiceLog("veteran", "Giving player %TU " + RESOURCE_AMOUNT + " of resource type " + resourceChosen + ". They have TWICE confirmed that they wish to consume this deed in order to get a crate of their chosen resource.", player);
                    }
                    cleanup();
                }
                else 
                {
                    String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                    chooseResourceClass(sui.getPlayerId(params), resourceClass);
                }
            }
            break;
            case sui.BP_CANCEL:
            
            {
                String resourceClass = (self.getScriptVars()).getString(SCRIPTVAR_BASE_CLASS);
                chooseResourceClass(sui.getPlayerId(params), resourceClass);
                break;
            }
            default:
            cleanup();
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void showResourceStats(obj_id player, obj_id resource) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            cleanup();
            return;
        }
        if (!isIdValid(resource))
        {
            cleanup();
            return;
        }
        obj_id self = getSelf();
        if (!utils.hasScriptVar(self, SCRIPTVAR_INUSE))
        {
            cleanup();
            return;
        }
        resource_attribute[] resourceAttribs = getResourceAttributes(resource);
        Vector attribStrings = new Vector();
        attribStrings.setSize(0);
        if (resourceAttribs == null)
        {
            cleanup();
            return;
        }
        attribStrings = utils.addElement(attribStrings, "@" + SID_RESOURCE_NAME + " = " + getResourceName(resource));
        for (int i = 0; i < resourceAttribs.length; ++i)
        {
            string_id temp = new string_id("obj_attr_n", resourceAttribs[i].getName());
            attribStrings = utils.addElement(attribStrings, "@" + temp + " = " + resourceAttribs[i].getValue());
        }
        int pid = sui.listbox(self, player, "@" + SID_CONFIRM_RESOURCE_SELECTION, sui.OK_CANCEL, "@" + SID_RESOURCE_TITLE, attribStrings, "handleChooseResourceTypeStats", false, false);
        if (pid >= 0)
        {
            sui.setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@back");
            sui.showSUIPage(pid);
            ((self).getScriptVars()).put(SCRIPTVAR_RESOURCECHOSEN, resource);
        }
        else 
        {
            cleanup();
        }
    }
    public void chooseResourceClass(obj_id player, String parentClass) throws InterruptedException
    {
        chooseResourceClass(player, parentClass, false);
    }
    public void chooseResourceClass(obj_id player, String parentClass, boolean topLevel) throws InterruptedException
    {
        if (!utils.hasScriptVar(getSelf(), SCRIPTVAR_INUSE))
        {
            cleanup();
            return;
        }
        String[] resourceClasses = null;
        if (topLevel)
        {
            resourceClasses = filterTopLevelResourceList(parentClass);
        }
        else 
        {
            resourceClasses = getImmediateResourceChildClasses(parentClass);
        }
        if (resourceClasses == null)
        {
            cleanup();
            return;
        }
        if (resourceClasses.length == 0)
        {
            chooseResourceType(player, parentClass);
            return;
        }
        int goodResources = 0;
        for (int i = 0; i < resourceClasses.length; ++i)
        {
            if (!hasResourceType(resourceClasses[i]))
            {
                resourceClasses[i] = null;
            }
            else 
            {
                ++goodResources;
            }
        }
        String[] temp = new String[goodResources];
        goodResources = 0;
        for (int i = 0; i < resourceClasses.length; ++i)
        {
            if (resourceClasses[i] != null)
            {
                temp[goodResources++] = resourceClasses[i];
            }
        }
        resourceClasses = temp;
        temp = null;
        String[] resourceClassNames = getResourceClassNames(resourceClasses);
        if (resourceClassNames == null)
        {
            cleanup();
            return;
        }
        String prompt;
        if (parentClass.equals(ROOT_RESOURCE_CLASS))
        {
            prompt = "@" + SID_CHOOSE_CLASS;
        }
        else 
        {
            prompt = "@" + SID_CHOOSE_SUB_CLASS + " " + getResourceClassName(parentClass);
        }
        int pid = sui.listbox(getSelf(), player, prompt, sui.OK_CANCEL, "@" + SID_RESOURCE_TITLE, resourceClassNames, "handleChooseResourceClass", false, false);
        if (!parentClass.equals(ROOT_RESOURCE_CLASS) && pid >= 0)
        {
            sui.setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@back");
        }
        if (pid >= 0)
        {
            sui.showSUIPage(pid);
            ((getSelf()).getScriptVars()).put(SCRIPTVAR_BASE_CLASS, parentClass);
            ((getSelf()).getScriptVars()).put(SCRIPTVAR_SUB_CLASSES, resourceClasses);
        }
        else 
        {
            cleanup();
        }
    }
    public void chooseResourceType(obj_id player, String parentClass) throws InterruptedException
    {
        if (!utils.hasScriptVar(getSelf(), SCRIPTVAR_INUSE))
        {
            cleanup();
            return;
        }
        obj_id[] resourceTypes = getResourceTypes(parentClass);
        if (resourceTypes == null || resourceTypes.length == 0)
        {
            cleanup();
            return;
        }
        String[] typeNames = getResourceNames(resourceTypes);
        if (typeNames == null || typeNames.length == 0)
        {
            cleanup();
            return;
        }
        int pid = sui.listbox(getSelf(), player, "@" + SID_CHOOSE_TYPE + " " + getResourceClassName(parentClass), sui.OK_CANCEL, "@" + SID_RESOURCE_TITLE, typeNames, "handleChooseResourceType", false, false);
        if (pid >= 0)
        {
            sui.setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@back");
            sui.showSUIPage(pid);
            ((getSelf()).getScriptVars()).put(SCRIPTVAR_BASE_CLASS, parentClass);
            ((getSelf()).getScriptVars()).put(SCRIPTVAR_TYPES, resourceTypes);
        }
        else 
        {
            cleanup();
        }
    }
    public void cleanup() throws InterruptedException
    {
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_INUSE);
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_BASE_CLASS);
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_SUB_CLASSES);
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_TYPES);
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_RESOURCECHOSEN);
    }
    public String[] filterTopLevelResourceList(String parentClass) throws InterruptedException
    {
        String[] resourceClasses = null;
        String[] tempResourceClass = getImmediateResourceChildClasses(parentClass);
        Vector tempResourceClassTwo = null;
        for (int x = 0; x < tempResourceClass.length; ++x)
        {
            if (!tempResourceClass[x].equals("energy") && !tempResourceClass[x].equals("space_resource"))
            {
                tempResourceClassTwo = utils.addElement(tempResourceClassTwo, tempResourceClass[x]);
            }
        }
        resourceClasses = new String[tempResourceClassTwo.size()];
        tempResourceClassTwo.toArray(resourceClasses);
        return resourceClasses;
    }
}
