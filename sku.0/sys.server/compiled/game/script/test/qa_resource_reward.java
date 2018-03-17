package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.veteran_deprecated;
import script.library.qa;

public class qa_resource_reward extends script.base_script
{
    public qa_resource_reward()
    {
    }
    public static final int RESOURCE_AMOUNT = 100000;
    public static final String ROOT_RESOURCE_CLASS = "resource";
    public static final String ROOT_ORGANIC_CLASS = "organic";
    public static final String ROOT_INORGANIC_CLASS = "inorganic";
    public static final String RESOURCE_REWARD_TITLE = "QA Resource Reward Tool";
    public static final String SCRIPTVAR_BASE_CLASS = "resource.base";
    public static final String SCRIPTVAR_SUB_CLASSES = "resource.subclass";
    public static final String SCRIPTVAR_TYPES = "resource.types";
    public static final String SCRIPTVAR_RESOURCECHOSEN = "resource.resoucechosen";
    public static final string_id SID_CHOOSE_CLASS = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "choose_class");
    public static final string_id SID_CHOOSE_SUB_CLASS = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "choose_sub_class");
    public static final string_id SID_CHOOSE_TYPE = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "choose_type");
    public static final string_id SID_RESOURCE_NAME = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "resource_name");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "test.qa_resource_reward");
        }
        else if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_resource_reward");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if (((toLower(text)).equals("qarewardresource")) || ((toLower(text)).equals("qarewardresources")))
            {
                chooseResourceClass(self, ROOT_RESOURCE_CLASS, true);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQATool(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.qaToolMainMenu(self);
                utils.removeScriptVarTree(player, "qarewardresource");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.qaToolMainMenu(self);
                utils.removeScriptVarTree(player, "qarewardresource");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                chooseResourceClass(self, ROOT_RESOURCE_CLASS, true);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChooseResourceClass(obj_id self, dictionary params) throws InterruptedException
    {
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
                    qa.qaToolMainMenu(self);
                }
                else 
                {
                    chooseResourceClass(sui.getPlayerId(params), getResourceParentClass(resourceClass), true);
                }
            }
            break;
            default:
            cleanup();
            removePlayer(self, "");
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChooseResourceType(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            
            {
                obj_id[] resourceTypes = (self.getScriptVars()).getObjIdArray(SCRIPTVAR_TYPES);
                int rowSelected = sui.getListboxSelectedRow(params);
                if (rowSelected >= 0 && rowSelected < resourceTypes.length)
                {
                    showResourceStats(sui.getPlayerId(params), resourceTypes[rowSelected]);
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
            removePlayer(self, "");
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChooseResourceTypeStats(obj_id self, dictionary params) throws InterruptedException
    {
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
                        sendSystemMessageTestingOnly(self, "The selected resource has been placed in your inventory");
                    }
                    cleanup();
                    removePlayer(self, "");
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
            removePlayer(self, "");
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void showResourceStats(obj_id player, obj_id resource) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            cleanup();
            removePlayer(player, "");
            return;
        }
        if (!isIdValid(resource))
        {
            cleanup();
            removePlayer(player, "");
            return;
        }
        obj_id self = getSelf();
        resource_attribute[] resourceAttribs = getResourceAttributes(resource);
        Vector attribStrings = new Vector();
        attribStrings.setSize(0);
        if (resourceAttribs == null)
        {
            cleanup();
            removePlayer(player, "");
            return;
        }
        attribStrings = utils.addElement(attribStrings, "@" + SID_RESOURCE_NAME + " = " + getResourceName(resource));
        for (int i = 0; i < resourceAttribs.length; ++i)
        {
            string_id temp = new string_id("obj_attr_n", resourceAttribs[i].getName());
            attribStrings = utils.addElement(attribStrings, "@" + temp + " = " + resourceAttribs[i].getValue());
        }
        int pid = sui.listbox(getSelf(), player, "Selecting this resource will create 100k units in your inventory.", sui.OK_CANCEL, getResourceName(resource), attribStrings, "handleChooseResourceTypeStats", false, false);
        if (pid >= 0)
        {
            sui.setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@back");
            sui.showSUIPage(pid);
            ((getSelf()).getScriptVars()).put(SCRIPTVAR_RESOURCECHOSEN, resource);
        }
        else 
        {
            cleanup();
            removePlayer(player, "");
        }
    }
    public void chooseResourceClass(obj_id player, String parentClass) throws InterruptedException
    {
        chooseResourceClass(player, parentClass, false);
    }
    public void chooseResourceClass(obj_id player, String parentClass, boolean topLevel) throws InterruptedException
    {
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
            removePlayer(player, "");
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
            removePlayer(player, "");
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
        int pid = sui.listbox(getSelf(), player, prompt, sui.OK_CANCEL, RESOURCE_REWARD_TITLE, resourceClassNames, "handleChooseResourceClass", false, false);
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
        removePlayer(player, "");
    }
    public void chooseResourceType(obj_id player, String parentClass) throws InterruptedException
    {
        obj_id[] resourceTypes = getResourceTypes(parentClass);
        if (resourceTypes == null || resourceTypes.length == 0)
        {
            cleanup();
            removePlayer(player, "");
            return;
        }
        String[] typeNames = getResourceNames(resourceTypes);
        if (typeNames == null || typeNames.length == 0)
        {
            cleanup();
            removePlayer(player, "");
            return;
        }
        int pid = sui.listbox(getSelf(), player, "@" + SID_CHOOSE_TYPE + " " + getResourceClassName(parentClass), sui.OK_CANCEL, RESOURCE_REWARD_TITLE, typeNames, "handleChooseResourceType", false, false);
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
            removePlayer(player, "");
        }
    }
    public void cleanup() throws InterruptedException
    {
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_BASE_CLASS);
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_SUB_CLASSES);
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_TYPES);
        ((getSelf()).getScriptVars()).remove(SCRIPTVAR_RESOURCECHOSEN);
    }
    public void removePlayer(obj_id player, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(player, err);
        qa.removeScriptVars(player, "qarewardresource");
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
