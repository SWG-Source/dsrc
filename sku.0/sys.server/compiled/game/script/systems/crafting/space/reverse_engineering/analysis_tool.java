package script.systems.crafting.space.reverse_engineering;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.temp_schematic;

public class analysis_tool extends script.base_script
{
    public analysis_tool()
    {
    }
    public static final string_id LOOT_LIST = new string_id("sui", "analyze_loot");
    public static final String SCRIPTVAR_ANALYZE_SUI = "analyzer.sui";
    public static final String SCRIPTVAR_ANALYZE_IDS = "analyzer.ids";
    public static final String STF = "component_analyzer";
    public static final String ANALYZE_TITLE = "@" + STF + ":analyze_title";
    public static final String ANALYZE_PROMPT = "@" + STF + ":analyze_prompt";
    public static final String NO_ITEMS_PROMPT = "@" + STF + ":no_items";
    public static final String BTN_ANALYZE = "@" + STF + ":analyze";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int lootList = mi.addRootMenu(menu_info_types.SERVER_MENU1, LOOT_LIST);
        if (lootList > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
        {
            String template = utils.getTemplateFilenameNoPath(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id[] x = getContents(self);
            if (x[0].hasScript("space.crafting.component_loot"))
            {
                grantSchematic(player, x[0]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showComponentSui(obj_id target, obj_id player, obj_id[] items) throws InterruptedException
    {
        String[] entries = new String[0];
        if (!isIdValid(player))
        {
            return;
        }
        if (items != null && items.length > 0)
        {
            entries = new String[items.length];
        }
        for (int i = 0; i < items.length; i++)
        {
            String template = getTemplateName(items[i]);
            String entry = getString(getNameStringId(items[i]));
            entries[i] = entry;
        }
        if (entries != null && entries.length > 0)
        {
            int pid = sui.listbox(target, player, ANALYZE_PROMPT, sui.OK_ONLY, ANALYZE_TITLE, entries, "handleAnalyzeComponent", false, false);
            if (pid > -1)
            {
                setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_ANALYZE);
                showSUIPage(pid);
                utils.setScriptVar(player, SCRIPTVAR_ANALYZE_SUI, pid);
                utils.setBatchScriptVar(player, SCRIPTVAR_ANALYZE_IDS, items);
            }
        }
        else 
        {
            int msgPid = sui.msgbox(target, player, NO_ITEMS_PROMPT, sui.OK_ONLY, ANALYZE_TITLE, "noHandler");
            cleanupComponentSui(player);
        }
    }
    public void cleanupComponentSui(obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(player, SCRIPTVAR_ANALYZE_SUI);
        utils.removeBatchScriptVar(player, SCRIPTVAR_ANALYZE_IDS);
    }
    public int handleAnalyzeComponent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        obj_id[] items = utils.getObjIdBatchScriptVar(player, SCRIPTVAR_ANALYZE_IDS);
        cleanupComponentSui(player);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            grantSchematic(player, items[idx]);
        }
        return SCRIPT_CONTINUE;
    }
    public void grantSchematic(obj_id player, obj_id item) throws InterruptedException
    {
        boolean condition = playMusic(player, "sound/item_analysis_tool.snd");
        String template = getTemplateName(item);
        int x = template.lastIndexOf("/");
        String newString = "object/draft_schematic/space/reverse_engineering/rev_" + template.substring(x + 1);
        temp_schematic.grant(player, newString, 1);
        destroyObject(item);
    }
}
