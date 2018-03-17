package script.systems.crafting.droid.modules;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.resource;
import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.craftinglib;
import script.library.player_structure;

public class interplanetary_survey extends script.base_script
{
    public interplanetary_survey()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public static final string_id SID_PLANET_TITLE = new string_id(STF_FILE, "survey_planet_title");
    public static final string_id SID_PLANET_PROMPT = new string_id(STF_FILE, "survey_planet_prompt");
    public static final string_id SID_SURVEY_TOOL_TITLE = new string_id(STF_FILE, "survey_select_tool_title");
    public static final string_id SID_SURVEY_TOOL_PROMPT = new string_id(STF_FILE, "survey_select_tool_prompt");
    public static final string_id SID_NO_SURVEY_SKILL = new string_id(STF_FILE, "survey_no_survey_skill");
    public static final string_id SID_NO_SURVEY_TOOLS = new string_id(STF_FILE, "survey_no_survey_tools");
    public static final string_id SID_YOU_MUST_BE_OUTDOORS = new string_id(STF_FILE, "you_must_be_outdoors");
    public static final string_id SID_SURVEY_DROID_LAUNCHED = new string_id(STF_FILE, "survey_droid_launched");
    public static final String[] PLANET_NAMES = 
    {
        "Tatooine",
        "Naboo",
        "Corellia",
        "Rori",
        "Talus",
        "Endor",
        "Dantooine",
        "Dathomir",
        "Lok",
        "Yavin IV"
    };
    public static final String[] PLANET_INTERNAL = 
    {
        "tatooine",
        "naboo",
        "corellia",
        "rori",
        "talus",
        "endor",
        "dantooine",
        "dathomir",
        "lok",
        "yavin4"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasSkill(player, "class_trader"))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
            }
            else 
            {
                mid.setServerNotify(true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (utils.isProfession(player, utils.TRADER))
            {
                if (getTopMostContainer(player) != player)
                {
                    sendSystemMessage(player, SID_YOU_MUST_BE_OUTDOORS);
                    return SCRIPT_OVERRIDE;
                }
                String title = utils.packStringId(SID_PLANET_TITLE);
                String prompt = utils.packStringId(SID_PLANET_PROMPT);
                String[] planetNames = new String[PLANET_INTERNAL.length];
                for (int i = 0; i < PLANET_INTERNAL.length; i++)
                {
                    planetNames[i] = utils.packStringId(new string_id("planet_n", PLANET_INTERNAL[i]));
                }
                sui.listbox(self, player, prompt, sui.OK_CANCEL, title, planetNames, "handlePlanetSelection");
            }
            else 
            {
                sendSystemMessage(player, SID_NO_SURVEY_SKILL);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlanetSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        String planet = PLANET_INTERNAL[idx];
        if (planet == null || planet.equals(""))
        {
            sendSystemMessageTestingOnly(player, "Error retrieving planet data");
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "planet", planet);
        String[] surveyTools = getAvailableSurveyTools(player);
        if (surveyTools == null || surveyTools.length == 0)
        {
            sendSystemMessage(player, SID_NO_SURVEY_TOOLS);
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        String title = utils.packStringId(SID_SURVEY_TOOL_TITLE);
        String prompt = utils.packStringId(SID_SURVEY_TOOL_PROMPT);
        sui.listbox(self, player, prompt, sui.OK_CANCEL, title, surveyTools, "handleSurveySelection");
        return SCRIPT_CONTINUE;
    }
    public int handleSurveySelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            cleanScriptVars();
            return SCRIPT_CONTINUE;
        }
        String planet = utils.getStringScriptVar(self, "planet");
        Vector surveyResourceList = utils.getResizeableStringArrayScriptVar(self, "surveyResource");
        Vector surveyToolList = utils.getResizeableObjIdArrayScriptVar(self, "surveyToolId");
        obj_id[] resourceIdList = getAvailablePlanetResources(planet, (String)surveyResourceList.get(idx));
        int time = getSurveyTime();
        if (isGod(player))
        {
            String delayStr = getConfigSetting("GameServer", "InterplanetarySurveyDelay");
            if (delayStr != null && !delayStr.equals(""))
            {
                time = Integer.parseInt(delayStr);
            }
        }
        dictionary data = new dictionary();
        data.put("resourceList", resourceIdList);
        data.put("planetName", planet);
        data.put("resourceClass", ((String)surveyResourceList.get(idx)));
        messageTo(player, "handleSurveyDroidReport", data, time, true);
        destroyObject(((obj_id)surveyToolList.get(idx)));
        int charges = getCount(self);
        if (charges > 1)
        {
            incrementCount(self, -1);
        }
        else 
        {
            destroyObject(self);
        }
        String runTime = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(time));
        prose_package ppDroidLaunched = prose.getPackage(SID_SURVEY_DROID_LAUNCHED);
        prose.setTT(ppDroidLaunched, runTime);
        sendSystemMessageProse(player, ppDroidLaunched);
        return SCRIPT_CONTINUE;
    }
    public void cleanScriptVars() throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVar(self, "surveyToolId");
        utils.removeScriptVar(self, "surveyResource");
        utils.removeScriptVar(self, "planet");
    }
    public String[] getAvailableSurveyTools(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return null;
        }
        Vector surveyToolList = new Vector();
        surveyToolList.setSize(0);
        Vector surveyTemplateList = new Vector();
        surveyTemplateList.setSize(0);
        Vector surveyResourceList = new Vector();
        surveyResourceList.setSize(0);
        Vector surveyToolIdList = new Vector();
        surveyToolIdList.setSize(0);
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return null;
        }
        obj_id[] contents = utils.getContents(inv, true);
        if (contents == null || contents.length == 0)
        {
            return null;
        }
        for (int i = 0; i < contents.length; i++)
        {
            String template = getTemplateName(contents[i]);
            if (template.indexOf("survey_tool") > -1)
            {
                if (surveyTemplateList.indexOf(template) == -1)
                {
                    String resourceClass = getStringObjVar(contents[i], "survey.resource_class");
                    surveyToolList.add(utils.packStringId(getNameFromTemplate(template)));
                    surveyTemplateList.add(template);
                    surveyResourceList.add(resourceClass);
                    surveyToolIdList.add(contents[i]);
                    continue;
                }
            }
        }
        utils.setScriptVar(getSelf(), "surveyToolId", surveyToolIdList);
        utils.setScriptVar(getSelf(), "surveyResource", surveyResourceList);
        String[] _surveyToolList = new String[0];
        if (surveyToolList != null)
        {
            _surveyToolList = new String[surveyToolList.size()];
            surveyToolList.toArray(_surveyToolList);
        }
        return _surveyToolList;
    }
    public obj_id[] getAvailablePlanetResources(String planet, String resourceClass) throws InterruptedException
    {
        resource_density[] resources = requestResourceList(new location(0, 0, 0, toLower(planet)), 0.0f, 1.0f, resourceClass);
        obj_id[] resourceList = new obj_id[resources.length];
        for (int i = 0; i < resourceList.length; i++)
        {
            resourceList[i] = resources[i].getResourceType();
        }
        return resourceList;
    }
    public int getSurveyTime() throws InterruptedException
    {
        final int minTime = 15 * 60;
        final int maxTime = 60 * 60;
        obj_id self = getSelf();
        float quality = getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality");
        if (quality > 100)
        {
            quality = 100;
        }
        int time = minTime + (int)((maxTime - minTime) * ((100 - quality) / 100));
        return time;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        if (count > 0)
        {
            names[idx] = "quantity";
            attribs[idx] = Integer.toString(count);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality"))
        {
            names[idx] = "mechanism_quality";
            int value = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality");
            attribs[idx] = "" + value;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
