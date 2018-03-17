package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.quests;
import script.library.sui;
import script.library.utils;
import script.library.temp_schematic;

public class fs_crafting1_player extends script.base_script
{
    public fs_crafting1_player()
    {
    }
    public static final string_id MSG_COMP_NOT_INSTALLED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_comp_not_installed");
    public static final string_id MSG_INVENTORY_FULL = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_inventory_full");
    public static final string_id MSG_ALREADY_HAVE_COMPONENT = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_already_have_component");
    public static final string_id MSG_COMPONENT_RETRIEVED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_component_retrieved");
    public static final string_id MSG_COMP_INSTALLED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_comp_installed");
    public static final string_id MSG_DONT_HAVE_COMPONENT = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_dont_have_component");
    public static final string_id MSG_COMPONENT_REPLACED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_component_replaced");
    public static final string_id MSG_COMPONENT_ANALYZED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_component_analyzed");
    public static final string_id MSG_SCHEMATIC_LOADED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_schematic_loaded");
    public static final string_id MSG_SCHEMATIC_ALREADY_LOADED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_schematic_already_loaded");
    public static final string_id MSG_CANT_OPEN_SUI = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_cant_open_sui");
    public static final string_id MSG_QUEST_FAIL_PHASE_DONE = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_quest_fail_phase_done");
    public static final string_id MSG_QUEST_FINISHED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_quest_finished");
    public static final string_id MSG_CALIBRATION_ABORTED = new string_id("quest/force_sensitive/fs_crafting", "phase1_msg_calibration_aborted");
    public static final String QUEST_OBJVAR = "quest.fs_crafting1";
    public static final String SUI_OBJVAR = QUEST_OBJVAR + ".pid";
    public static final String TERMINAL_OBJVAR = QUEST_OBJVAR + ".terminal";
    public static final String STATUS_OBJVAR = QUEST_OBJVAR + ".status";
    public static final String ANALYZER_OBJVAR = QUEST_OBJVAR + ".analyzer";
    public static final String ANALYZED_OBJVAR = QUEST_OBJVAR + ".analyzed";
    public static final String CALIBRATOR_OBJVAR = QUEST_OBJVAR + ".calibrator";
    public static final String COMPONENT_OBJVAR = QUEST_OBJVAR + ".component";
    public static final String DATA_OBJVAR = QUEST_OBJVAR + ".data";
    public static final String MASTER_OBJVAR = QUEST_OBJVAR + ".master";
    public static final String SUI_TITLE = "@quest/force_sensitive/fs_crafting:sui_access_terminal_title";
    public static final String SUI_PROMPT0 = "@quest/force_sensitive/fs_crafting:sui_access_terminal_prompt0";
    public static final String SUI_PROMPT1 = "@quest/force_sensitive/fs_crafting:sui_access_terminal_prompt1";
    public static final String SUI_PROMPT2 = "@quest/force_sensitive/fs_crafting:sui_access_terminal_prompt2";
    public static final String SUI_OFFLINE = "@quest/force_sensitive/fs_crafting:sui_access_terminal_offline";
    public static final String SUI_ONLINE = "@quest/force_sensitive/fs_crafting:sui_access_terminal_online";
    public static final String SUI_EMPTY = "@quest/force_sensitive/fs_crafting:sui_access_terminal_empty";
    public static final String SUI_DAMAGED = "@quest/force_sensitive/fs_crafting:sui_access_terminal_damaged";
    public static final String SUI_OPERATIONAL = "@quest/force_sensitive/fs_crafting:sui_access_terminal_operational";
    public static final String SUI_BTN_REPLACE = "@quest/force_sensitive/fs_crafting:sui_btn_replace";
    public static final String SUI_BTN_RETRIEVE = "@quest/force_sensitive/fs_crafting:sui_btn_retrieve";
    public static final String SUI_ANALYZER_TITLE = "@quest/force_sensitive/fs_crafting:sui_analyzer_title";
    public static final String SUI_ANALYZER_PROMPT0 = "@quest/force_sensitive/fs_crafting:sui_analyzer_prompt0";
    public static final String SUI_ANALYZER_PROMPT1 = "@quest/force_sensitive/fs_crafting:sui_analyzer_prompt1";
    public static final String SUI_ANALYZER_PROMPT2 = "@quest/force_sensitive/fs_crafting:sui_analyzer_prompt2";
    public static final String SUI_ANALYZER_ANALYZE_COMPONENT = "@quest/force_sensitive/fs_crafting:sui_analyzer_analyze_component";
    public static final String SUI_ANALYZER_GET_SCHEMATIC = "@quest/force_sensitive/fs_crafting:sui_analyzer_get_schematic";
    public static final String SUI_ANALYZER_NO_COMP = "@quest/force_sensitive/fs_crafting:sui_analyzer_no_comp";
    public static final String SUI_ANALYZER_NO_SCHEMATIC = "@quest/force_sensitive/fs_crafting:sui_analyzer_no_schematic";
    public static final String SUI_CALIBRATOR_TITLE = "@quest/force_sensitive/fs_crafting:sui_calibrator_title";
    public static final String SUI_CALIBRATOR_PROMPT = "@quest/force_sensitive/fs_crafting:sui_calibrator_prompt";
    public static final String SUI_CALIBRATOR_NO_COMP = "@quest/force_sensitive/fs_crafting:sui_calibrator_no_comp";
    public static final String SUI_ATTEMPTS_REMAINING = "@quest/force_sensitive/fs_crafting:sui_attempts_remaining";
    public static final String SUI_CALIBRATION_SUCCESS = "@quest/force_sensitive/fs_crafting:sui_calibration_success";
    public static final String SUI_CALIBRATION_FAILURE = "@quest/force_sensitive/fs_crafting:sui_calibration_failure";
    public static final String SUI_CONFIG_TITLE = "@quest/force_sensitive/fs_crafting:sui_config_title";
    public static final String SUI_CONFIG_DESCRIPTION = "@quest/force_sensitive/fs_crafting:sui_config_description";
    public static final String SUI_GYRO_TITLE = "@quest/force_sensitive/fs_crafting:sui_gyro_title";
    public static final String SUI_GYRO_DESCRIPTION = "@quest/force_sensitive/fs_crafting:sui_gyro_description";
    public static final String SUI_GYRO_SLIDER1 = "@quest/force_sensitive/fs_crafting:sui_gyro_slider1";
    public static final String SUI_GYRO_SLIDER2 = "@quest/force_sensitive/fs_crafting:sui_gyro_slider2";
    public static final String SUI_GYRO_SLIDER3 = "@quest/force_sensitive/fs_crafting:sui_gyro_slider3";
    public static final String SUI_AMP_TITLE = "@quest/force_sensitive/fs_crafting:sui_amp_title";
    public static final String SUI_AMP_DESCRIPTION = "@quest/force_sensitive/fs_crafting:sui_amp_description";
    public static final String SUI_AMP_SLIDER1 = "@quest/force_sensitive/fs_crafting:sui_amp_slider1";
    public static final String SUI_AMP_SLIDER2 = "@quest/force_sensitive/fs_crafting:sui_amp_slider2";
    public static final String SUI_AMP_SLIDER3 = "@quest/force_sensitive/fs_crafting:sui_amp_slider3";
    public static final String SUI_SSA_TITLE = "@quest/force_sensitive/fs_crafting:sui_ssa_title";
    public static final String SUI_SSA_DESCRIPTION = "@quest/force_sensitive/fs_crafting:sui_ssa_description";
    public static final String SUI_SSA_SLIDER1 = "@quest/force_sensitive/fs_crafting:sui_ssa_slider1";
    public static final String SUI_SSA_SLIDER2 = "@quest/force_sensitive/fs_crafting:sui_ssa_slider2";
    public static final String SUI_SSA_SLIDER3 = "@quest/force_sensitive/fs_crafting:sui_ssa_slider3";
    public static final String SUI_SSA_SLIDER4 = "@quest/force_sensitive/fs_crafting:sui_ssa_slider4";
    public static final String SUI_SSA_SLIDER5 = "@quest/force_sensitive/fs_crafting:sui_ssa_slider5";
    public static final String[] COMPONENTS = 
    {
        "@quest_item_n:fs_craft_puzzle_config_processor",
        "@quest_item_n:fs_craft_puzzle_gyro_receiver",
        "@quest_item_n:fs_craft_puzzle_signal_amp",
        "@quest_item_n:fs_craft_puzzle_solid_state_array"
    };
    public static final String[] TEMPLATES = 
    {
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_config_processor.iff",
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_gyro_receiver.iff",
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_signal_amp.iff",
        "object/tangible/item/quest/force_sensitive/fs_craft_puzzle_solid_state_array.iff"
    };
    public static final String[] SCHEMATICS = 
    {
        "object/draft_schematic/item/quest_item/fs_craft_puzzle_config_processor.iff",
        "object/draft_schematic/item/quest_item/fs_craft_puzzle_gyro_receiver.iff",
        "object/draft_schematic/item/quest_item/fs_craft_puzzle_signal_amp.iff",
        "object/draft_schematic/item/quest_item/fs_craft_puzzle_solid_state_array.iff"
    };
    public static final String[] CONFIG_PLAYER_BUTTONS = 
    {
        "top.triangles.player.right.1",
        "top.triangles.player.right.2",
        "top.triangles.player.right.3",
        "top.triangles.player.left.2",
        "top.triangles.player.left.3",
        "top.triangles.player.left.1"
    };
    public static final String[] CONFIG_SERVER_BUTTONS = 
    {
        "top.triangles.server.right.1",
        "top.triangles.server.right.2",
        "top.triangles.server.right.3",
        "top.triangles.server.left.2",
        "top.triangles.server.left.3",
        "top.triangles.server.left.1"
    };
    public static final int SUI_SSA_LINE_MIN = 20;
    public static final int SUI_SSA_LINE_MAX = 248;
    public static final int SUI_SSA_LINE_RANGE = SUI_SSA_LINE_MAX - SUI_SSA_LINE_MIN;
    public static final int[] DEFAULT_STATUS = 
    {
        -1,
        -1,
        -1,
        -1
    };
    public static final int[] DEFAULT_ANALYZED = 
    {
        0,
        0,
        0,
        0
    };
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, MSG_QUEST_FAIL_PHASE_DONE);
        removeObjVar(self, QUEST_OBJVAR);
        for (int i = 0; i <= 3; i++)
        {
            String name = "fs_craft_puzzle_quest_0" + i;
            int questId = quests.getQuestId(name);
            if (quests.isComplete(name, self))
            {
                clearCompletedQuest(self, questId);
            }
            else if (quests.isActive(name, self))
            {
                quests.deactivate(name, self);
            }
        }
        detachScript(self, "quest.force_sensitive.fs_crafting1_player");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SUI_OBJVAR + ".pid"))
        {
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("config"))
        {
            configProcessorPuzzle(self, self);
        }
        else if (text.equals("gyro"))
        {
            gyroReceiverPuzzle(self, self);
        }
        else if (text.equals("amp"))
        {
            signalAmpPuzzle(self, self);
        }
        else if (text.equals("ssa"))
        {
            solidStateArrayPuzzle(self, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMasterIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER);
        if (isIdValid(master))
        {
            setObjVar(self, MASTER_OBJVAR, master);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAccessTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SUI_OBJVAR + ".pid"))
        {
            sendSystemMessage(self, MSG_CANT_OPEN_SUI);
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, TERMINAL_OBJVAR))
        {
            terminal = utils.getObjIdScriptVar(self, TERMINAL_OBJVAR);
        }
        if (!isIdValid(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        int[] status = 
        {
            -1,
            -1,
            -1,
            -1
        };
        if (hasObjVar(self, STATUS_OBJVAR))
        {
            status = getIntArrayObjVar(self, STATUS_OBJVAR);
        }
        if (status.length != COMPONENTS.length)
        {
            return SCRIPT_CONTINUE;
        }
        boolean win = true;
        String arrayStatus = SUI_ONLINE;
        for (int i = 0; i < status.length; i++)
        {
            if (status[i] != 1)
            {
                arrayStatus = SUI_OFFLINE;
                win = false;
            }
        }
        String prompt = SUI_PROMPT0 + "\n\n";
        prompt += SUI_PROMPT1 + " " + arrayStatus + "\n\n";
        prompt += SUI_PROMPT2;
        String[] statusData = new String[status.length];
        for (int i = 0; i < status.length; i++)
        {
            statusData[i] = COMPONENTS[i] + " - ";
            switch (status[i])
            {
                case -1:
                statusData[i] += SUI_DAMAGED;
                break;
                case 0:
                statusData[i] += SUI_EMPTY;
                break;
                case 1:
                statusData[i] += SUI_OPERATIONAL;
                break;
            }
        }
        int pid = sui.listbox(self, self, prompt, sui.OK_CANCEL_ALL, SUI_TITLE, statusData, "handleAccessTerminalCallback", false, false);
        if (pid < 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
        if (win)
        {
            sendSystemMessage(self, MSG_QUEST_FINISHED);
            removeObjVar(self, QUEST_OBJVAR);
            quests.complete("fs_craft_puzzle_quest_03", self, true);
            fs_quests.unlockBranch(self, "force_sensitive_crafting_mastery_assembly");
            fs_quests.setQuestCompleted(self, "fs_crafting1");
            obj_id createdObject = null;
            createdObject = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_sculpture_4.iff", self);
            detachScript(self, "quest.force_sensitive.fs_crafting1_player");
            setSUIProperty(pid, "btnOk", "Visible", "false");
            setSUIProperty(pid, "btnOther", "Visible", "false");
        }
        else 
        {
            setSUIProperty(pid, "btnOk", "Text", SUI_BTN_REPLACE);
            setSUIProperty(pid, "btnOther", "Text", SUI_BTN_RETRIEVE);
        }
        setSUIAssociatedObject(pid, terminal);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int handleAccessTerminalCallback(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            refreshTerminalSUI(self);
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_OK)
        {
            replaceComponent(self, idx);
        }
        else 
        {
            retrieveComponent(self, idx);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUseAnalyzer(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SUI_OBJVAR + ".pid"))
        {
            sendSystemMessage(self, MSG_CANT_OPEN_SUI);
            return SCRIPT_CONTINUE;
        }
        obj_id analyzer = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, ANALYZER_OBJVAR))
        {
            analyzer = utils.getObjIdScriptVar(self, ANALYZER_OBJVAR);
        }
        if (!isIdValid(analyzer))
        {
            return SCRIPT_CONTINUE;
        }
        int[] analyzed = 
        {
            0,
            0,
            0,
            0
        };
        if (hasObjVar(self, ANALYZED_OBJVAR))
        {
            analyzed = getIntArrayObjVar(self, ANALYZED_OBJVAR);
        }
        String[] choices = 
        {
            SUI_ANALYZER_ANALYZE_COMPONENT,
            SUI_ANALYZER_GET_SCHEMATIC
        };
        int pid = sui.listbox(self, self, SUI_ANALYZER_PROMPT0, sui.OK_CANCEL, SUI_ANALYZER_TITLE, choices, "handleUseAnalyzerCallback", false, false);
        if (pid < 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
        setSUIAssociatedObject(pid, analyzer);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int handleUseAnalyzerCallback(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_OK)
        {
            if (idx == 0)
            {
                analyzeComponent(self, false);
            }
            else 
            {
                accessSchematic(self, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAnalyzeCompCallback(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        String[] data = utils.getStringArrayScriptVar(self, DATA_OBJVAR);
        if (data == null || data.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, DATA_OBJVAR);
        int[] analyzed = 
        {
            0,
            0,
            0,
            0
        };
        if (hasObjVar(self, ANALYZED_OBJVAR))
        {
            analyzed = getIntArrayObjVar(self, ANALYZED_OBJVAR);
        }
        if (analyzed == null || analyzed.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String componentName = data[idx];
        int index = -1;
        for (int i = 0; i < COMPONENTS.length; i++)
        {
            if (componentName.equalsIgnoreCase(COMPONENTS[i]))
            {
                index = i;
            }
        }
        if (index == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id component = utils.getItemPlayerHasByTemplate(self, TEMPLATES[index]);
        if (!isIdValid(component))
        {
            return SCRIPT_CONTINUE;
        }
        if (temp_schematic.grant(self, SCHEMATICS[index], 1))
        {
            analyzed[index] = 1;
            setObjVar(self, ANALYZED_OBJVAR, analyzed);
            destroyObject(component);
            sendSystemMessage(self, MSG_COMPONENT_ANALYZED);
        }
        analyzeComponent(self, true);
        return SCRIPT_CONTINUE;
    }
    public int handleAccessSchematicCallback(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        String[] data = utils.getStringArrayScriptVar(self, DATA_OBJVAR);
        if (data == null || data.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, DATA_OBJVAR);
        int[] analyzed = 
        {
            0,
            0,
            0,
            0
        };
        if (hasObjVar(self, ANALYZED_OBJVAR))
        {
            analyzed = getIntArrayObjVar(self, ANALYZED_OBJVAR);
        }
        if (analyzed == null || analyzed.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String componentName = data[idx];
        int index = -1;
        for (int i = 0; i < COMPONENTS.length; i++)
        {
            if (componentName.equalsIgnoreCase(COMPONENTS[i]))
            {
                index = i;
            }
        }
        if (index == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasSchematic(self, SCHEMATICS[index]))
        {
            sendSystemMessage(self, MSG_SCHEMATIC_ALREADY_LOADED);
            accessSchematic(self, true);
            return SCRIPT_CONTINUE;
        }
        if (temp_schematic.grant(self, SCHEMATICS[index], 1))
        {
            sendSystemMessage(self, MSG_SCHEMATIC_LOADED);
        }
        accessSchematic(self, true);
        return SCRIPT_CONTINUE;
    }
    public int handleUseCalibrator(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SUI_OBJVAR + ".pid"))
        {
            sendSystemMessage(self, MSG_CANT_OPEN_SUI);
            return SCRIPT_CONTINUE;
        }
        obj_id calibrator = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, CALIBRATOR_OBJVAR))
        {
            calibrator = utils.getObjIdScriptVar(self, CALIBRATOR_OBJVAR);
        }
        if (!isIdValid(calibrator))
        {
            return SCRIPT_CONTINUE;
        }
        Vector tempData = new Vector();
        tempData.setSize(0);
        for (int i = 0; i < TEMPLATES.length; i++)
        {
            obj_id[] contents = utils.getAllItemsPlayerHasByTemplate(self, TEMPLATES[i]);
            if (contents != null && contents.length > 0)
            {
                for (int j = 0; j < contents.length; j++)
                {
                    if (!hasObjVar(contents[j], STATUS_OBJVAR))
                    {
                        obj_id crafter = getCrafter(contents[j]);
                        if (!isIdValid(crafter) || crafter != self)
                        {
                            continue;
                        }
                        tempData = utils.addElement(tempData, COMPONENTS[i]);
                        break;
                    }
                }
            }
        }
        if (tempData.size() == 0)
        {
            sui.msgbox(self, self, SUI_CALIBRATOR_NO_COMP, sui.OK_ONLY, SUI_CALIBRATOR_TITLE, 0, "noHandler");
            return SCRIPT_CONTINUE;
        }
        String[] data = new String[tempData.size()];
        for (int i = 0; i < tempData.size(); i++)
        {
            data[i] = (String)tempData.get(i);
        }
        int pid = sui.listbox(self, self, SUI_CALIBRATOR_PROMPT, sui.OK_CANCEL, SUI_CALIBRATOR_TITLE, data, "handleUseCalibratorCallback", false, false);
        if (pid < 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
        utils.setScriptVar(self, DATA_OBJVAR, data);
        setSUIAssociatedObject(pid, calibrator);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int handleUseCalibratorCallback(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        obj_id calibrator = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, CALIBRATOR_OBJVAR))
        {
            calibrator = utils.getObjIdScriptVar(self, CALIBRATOR_OBJVAR);
        }
        if (!isIdValid(calibrator))
        {
            return SCRIPT_CONTINUE;
        }
        String[] data = utils.getStringArrayScriptVar(self, DATA_OBJVAR);
        if (data == null || data.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, DATA_OBJVAR);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String componentName = data[idx];
        int index = -1;
        for (int i = 0; i < COMPONENTS.length; i++)
        {
            if (componentName.equalsIgnoreCase(COMPONENTS[i]))
            {
                index = i;
            }
        }
        if (index == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] contents = utils.getAllItemsPlayerHasByTemplate(self, TEMPLATES[index]);
        obj_id component = null;
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (!hasObjVar(contents[i], STATUS_OBJVAR))
            {
                component = contents[i];
                break;
            }
        }
        if (isIdValid(component))
        {
            utils.setScriptVar(self, COMPONENT_OBJVAR, component);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        switch (index)
        {
            case 0:
            configProcessorPuzzle(self, calibrator);
            break;
            case 1:
            gyroReceiverPuzzle(self, calibrator);
            break;
            case 2:
            signalAmpPuzzle(self, calibrator);
            break;
            case 3:
            solidStateArrayPuzzle(self, calibrator);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int configProcessorPuzzleCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String widgetName = params.getString("eventWidgetName");
        int pid = utils.getIntScriptVar(self, SUI_OBJVAR + ".pid");
        if (widgetName == null || widgetName.equals(""))
        {
            obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
            if (!hasObjVar(component, STATUS_OBJVAR))
            {
                setObjVar(component, STATUS_OBJVAR, -1);
                sendSystemMessage(self, MSG_CALIBRATION_ABORTED);
            }
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            forceCloseSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        int index = -1;
        for (int i = 0; i < CONFIG_PLAYER_BUTTONS.length; i++)
        {
            setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "Color", "#FFFFFF");
            if (widgetName.equalsIgnoreCase(CONFIG_PLAYER_BUTTONS[i]))
            {
                index = i;
            }
        }
        if (index < 0 || index > 5)
        {
            return SCRIPT_CONTINUE;
        }
        int[] current = utils.getIntArrayScriptVar(self, SUI_OBJVAR + ".current");
        int[] goal = utils.getIntArrayScriptVar(self, SUI_OBJVAR + ".goal");
        int tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".tries");
        int max_tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".max_tries");
        if (current == null || goal == null)
        {
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            return SCRIPT_CONTINUE;
        }
        current = toggleButton(current, index);
        for (int i = 0; i < CONFIG_PLAYER_BUTTONS.length; i++)
        {
            if (current[i] == 1)
            {
                setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "Color", "#000000");
            }
        }
        tries--;
        int integrity = (int)(((float)tries / (float)max_tries) * 100);
        boolean win = true;
        for (int i = 0; i < current.length; i++)
        {
            if (current[i] != goal[i])
            {
                win = false;
            }
        }
        if (win)
        {
            obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
            setObjVar(component, STATUS_OBJVAR, 1);
            setSUIProperty(pid, "top.description.desc", "Text", SUI_CALIBRATION_SUCCESS);
            for (int i = 0; i < CONFIG_PLAYER_BUTTONS.length; i++)
            {
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, CONFIG_PLAYER_BUTTONS[i], "noCallback");
                setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "GetsInput", "false");
            }
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        }
        else if (tries <= 0)
        {
            obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
            setObjVar(component, STATUS_OBJVAR, -1);
            setSUIProperty(pid, "top.description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
            setSUIProperty(pid, "top.description.desc", "Text", SUI_CALIBRATION_FAILURE);
            for (int i = 0; i < CONFIG_PLAYER_BUTTONS.length; i++)
            {
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, CONFIG_PLAYER_BUTTONS[i], "noCallback");
                setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "GetsInput", "false");
            }
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
        }
        else 
        {
            setSUIProperty(pid, "top.description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".current", current);
        utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
        flushSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int gyroReceiverPuzzleCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String widgetName = params.getString("eventWidgetName");
        int pid = utils.getIntScriptVar(self, SUI_OBJVAR + ".pid");
        if (widgetName.equalsIgnoreCase("btnCancel") || widgetName.equalsIgnoreCase(""))
        {
            obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
            if (!hasObjVar(component, STATUS_OBJVAR))
            {
                setObjVar(component, STATUS_OBJVAR, -1);
                sendSystemMessage(self, MSG_CALIBRATION_ABORTED);
            }
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            forceCloseSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        else if (widgetName.equalsIgnoreCase("%buttonOK%"))
        {
            int[] current = 
            {
                0,
                0,
                0
            };
            int[] goal = utils.getIntArrayScriptVar(self, SUI_OBJVAR + ".goal");
            int tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".tries");
            int max_tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".max_tries");
            boolean win = true;
            for (int i = 0; i < current.length; i++)
            {
                current[i] = utils.stringToInt(params.getString("top.sliders." + (i + 1) + ".slider.Value"));
                int delta = goal[i] - current[i];
                if (delta < -5 || delta > 5)
                {
                    win = false;
                }
                float pct = (float)current[i] / 100.0f;
                int dec = (int)(255 * pct);
                String hex = Integer.toHexString(dec);
                if (hex.length() == 1)
                {
                    hex = "0" + hex;
                }
                String hexValue = "#" + hex + hex + hex;
                setSUIProperty(pid, "top.bars.player." + (i + 1), "Color", hexValue);
            }
            tries--;
            int integrity = (int)(((float)tries / (float)max_tries) * 100);
            if (win)
            {
                obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
                setObjVar(component, STATUS_OBJVAR, 1);
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "noCallback");
                setSUIProperty(pid, "%buttonOK%", "Visible", "false");
                setSUIProperty(pid, "top.sliders.1.slider", "GetsInput", "false");
                setSUIProperty(pid, "top.sliders.2.slider", "GetsInput", "false");
                setSUIProperty(pid, "top.sliders.3.slider", "GetsInput", "false");
                for (int i = 0; i < current.length; i++)
                {
                    float pct = (float)current[i] / 100.0f;
                    int dec = (int)(255 * pct);
                    String hex = Integer.toHexString(dec);
                    if (hex.length() == 1)
                    {
                        hex = "0" + hex;
                    }
                    String hexValue = "#00" + hex + "00";
                    setSUIProperty(pid, "top.bars.player." + (i + 1), "Color", hexValue);
                    setSUIProperty(pid, "top.bars.server." + (i + 1), "Color", hexValue);
                }
                setSUIProperty(pid, "description.desc", "Text", SUI_CALIBRATION_SUCCESS);
                utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            }
            else if (tries <= 0)
            {
                obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
                setObjVar(component, STATUS_OBJVAR, -1);
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "noCallback");
                setSUIProperty(pid, "%buttonOK%", "Visible", "false");
                setSUIProperty(pid, "top.sliders.1.slider", "GetsInput", "false");
                setSUIProperty(pid, "top.sliders.2.slider", "GetsInput", "false");
                setSUIProperty(pid, "top.sliders.3.slider", "GetsInput", "false");
                setSUIProperty(pid, "description.desc", "Text", SUI_CALIBRATION_FAILURE);
                for (int i = 0; i < current.length; i++)
                {
                    float pct = (float)current[i] / 100.0f;
                    int dec = (int)(255 * pct);
                    String hex = Integer.toHexString(dec);
                    if (hex.length() == 1)
                    {
                        hex = "0" + hex;
                    }
                    String hexValue = "#" + hex + "0000";
                    setSUIProperty(pid, "top.bars.player." + (i + 1), "Color", hexValue);
                }
                for (int i = 0; i < current.length; i++)
                {
                    float pct = (float)goal[i] / 100.0f;
                    int dec = (int)(255 * pct);
                    String hex = Integer.toHexString(dec);
                    if (hex.length() == 1)
                    {
                        hex = "0" + hex;
                    }
                    String hexValue = "#" + hex + "0000";
                    setSUIProperty(pid, "top.bars.server." + (i + 1), "Color", hexValue);
                }
                setSUIProperty(pid, "description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
                utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            }
            else 
            {
                setSUIProperty(pid, "description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
            }
            utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
            flushSUIPage(pid);
        }
        else 
        {
            debugSpeakMsg(self, "calibpuzzle3 got invalid widget, widget = '" + widgetName + "'");
        }
        return SCRIPT_CONTINUE;
    }
    public int signalAmpPuzzleCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String widgetName = params.getString("eventWidgetName");
        int pid = utils.getIntScriptVar(self, SUI_OBJVAR + ".pid");
        if (widgetName.equalsIgnoreCase("%buttonCancel%") || widgetName.equalsIgnoreCase(""))
        {
            obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
            if (!hasObjVar(component, STATUS_OBJVAR))
            {
                setObjVar(component, STATUS_OBJVAR, -1);
                sendSystemMessage(self, MSG_CALIBRATION_ABORTED);
            }
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            forceCloseSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        else if (widgetName.equalsIgnoreCase("%buttonOk%"))
        {
            float sliderPos0 = utils.stringToInt(params.getString("%slider1%.Value"));
            float sliderPos1 = utils.stringToInt(params.getString("%slider2%.Value"));
            float sliderPos2 = utils.stringToInt(params.getString("%slider3%.Value"));
            int bar0 = utils.getIntScriptVar(self, "calibpuzzle2_bar0");
            int bar1 = utils.getIntScriptVar(self, "calibpuzzle2_bar1");
            int bar2 = utils.getIntScriptVar(self, "calibpuzzle2_bar2");
            float c0 = ((bar0 == 0) ? sliderPos0 : ((bar0 == 1) ? sliderPos1 : sliderPos2)) / 100.0f;
            float c1 = ((bar1 == 0) ? sliderPos0 : ((bar1 == 1) ? sliderPos1 : sliderPos2)) / 100.0f;
            float c2 = ((bar2 == 0) ? sliderPos0 : ((bar2 == 1) ? sliderPos1 : sliderPos2)) / 100.0f;
            float coeff0 = utils.getFloatScriptVar(self, "calibpuzzle2_coeff0");
            float coeff1 = utils.getFloatScriptVar(self, "calibpuzzle2_coeff1");
            float coeff2 = utils.getFloatScriptVar(self, "calibpuzzle2_coeff2");
            float n;
            float e = 0.0f;
            for (int i = 0; i < 7; i++)
            {
                n = i / 7.0f;
                float value = (c0 + (c1 * n) + (c2 * n * n)) / 3.0f;
                if (value < 0.0f)
                {
                    value = 0.0f;
                }
                if (value > 1.0f)
                {
                    value = 1.0f;
                }
                setSUIProperty(pid, "%barTop" + (i + 1) + "%", "Value", "" + (100 * value));
                setSUIProperty(pid, "%barTop" + (i + 1) + "%", "RunScript", "");
                float targetValue = (coeff0 + (coeff1 * n) + (coeff2 * n * n)) / 3.0f;
                e += Math.abs(value - targetValue);
            }
            boolean win = false;
            if (e < 0.2f)
            {
                win = true;
            }
            int tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".tries");
            int max_tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".max_tries");
            tries--;
            int integrity = (int)(((float)tries / (float)max_tries) * 100);
            if (win)
            {
                obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
                setObjVar(component, STATUS_OBJVAR, 1);
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "noCallback");
                setSUIProperty(pid, "%slider1%", "GetsInput", "false");
                setSUIProperty(pid, "%slider2%", "GetsInput", "false");
                setSUIProperty(pid, "%slider3%", "GetsInput", "false");
                setSUIProperty(pid, "%buttonOK%", "Visible", "false");
                setSUIProperty(pid, "description.desc", "Text", SUI_CALIBRATION_SUCCESS);
                utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            }
            else if (tries <= 0)
            {
                obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
                setObjVar(component, STATUS_OBJVAR, -1);
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "noCallback");
                setSUIProperty(pid, "%slider1%", "GetsInput", "false");
                setSUIProperty(pid, "%slider2%", "GetsInput", "false");
                setSUIProperty(pid, "%slider3%", "GetsInput", "false");
                setSUIProperty(pid, "%buttonOK%", "Visible", "false");
                setSUIProperty(pid, "description.desc", "Text", SUI_CALIBRATION_FAILURE);
                setSUIProperty(pid, "%attemptsRemaining%", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
                utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            }
            else 
            {
                setSUIProperty(pid, "%attemptsRemaining%", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
            }
            utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
            flushSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            debugSpeakMsg(self, "calibpuzzle2 got invalid widget, widget = '" + widgetName + "'");
        }
        return SCRIPT_CONTINUE;
    }
    public int solidStateArrayPuzzleCallback(obj_id self, dictionary params) throws InterruptedException
    {
        String widgetName = params.getString("eventWidgetName");
        LOG("ssapuzzle", "solidStateArrayPuzzleCallback, widgetName = '" + widgetName + "'");
        int pid = utils.getIntScriptVar(self, SUI_OBJVAR + ".pid");
        if (widgetName.equalsIgnoreCase("btnCancel") || widgetName.equalsIgnoreCase(""))
        {
            obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
            if (!hasObjVar(component, STATUS_OBJVAR))
            {
                setObjVar(component, STATUS_OBJVAR, -1);
                sendSystemMessage(self, MSG_CALIBRATION_ABORTED);
            }
            utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            forceCloseSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        else if (widgetName.equalsIgnoreCase("%buttonOK%"))
        {
            int[] current = 
            {
                0,
                0,
                0,
                0,
                0
            };
            int[] mod = utils.getIntArrayScriptVar(self, SUI_OBJVAR + ".mod");
            int goal = utils.getIntScriptVar(self, SUI_OBJVAR + ".goal");
            int tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".tries");
            int max_tries = utils.getIntScriptVar(self, SUI_OBJVAR + ".max_tries");
            boolean win = true;
            for (int i = 0; i < current.length; i++)
            {
                current[i] = utils.stringToInt(params.getString("comp.sliders." + (i + 1) + ".slider.Value"));
                int pct = current[i] + mod[i];
                if (pct > 100)
                {
                    pct -= 100;
                }
                int delta = goal - pct;
                if (delta < -3 || delta > 2)
                {
                    win = false;
                }
                setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".left", "Value", "" + (pct - 3));
                setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".right", "Value", "" + (100 - (pct + 3)));
                setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".left", "RunScript", "");
                setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".right", "RunScript", "");
            }
            tries--;
            int integrity = (int)(((float)tries / (float)max_tries) * 100);
            if (win)
            {
                obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
                setObjVar(component, STATUS_OBJVAR, 1);
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "noCallback");
                setSUIProperty(pid, "comp.sliders.1.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.2.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.3.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.4.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.5.slider", "GetsInput", "false");
                setSUIProperty(pid, "%buttonOK%", "Visible", "false");
                setSUIProperty(pid, "description.desc", "Text", SUI_CALIBRATION_SUCCESS);
                utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            }
            else if (tries <= 0)
            {
                obj_id component = utils.getObjIdScriptVar(self, COMPONENT_OBJVAR);
                setObjVar(component, STATUS_OBJVAR, -1);
                subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "noCallback");
                setSUIProperty(pid, "comp.sliders.1.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.2.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.3.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.4.slider", "GetsInput", "false");
                setSUIProperty(pid, "comp.sliders.5.slider", "GetsInput", "false");
                setSUIProperty(pid, "%buttonOK%", "Visible", "false");
                setSUIProperty(pid, "description.desc", "Text", SUI_CALIBRATION_FAILURE);
                setSUIProperty(pid, "description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
                utils.removeScriptVar(self, SUI_OBJVAR + ".pid");
            }
            else 
            {
                setSUIProperty(pid, "description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " " + integrity + "%");
            }
            utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
            flushSUIPage(pid);
        }
        else 
        {
            debugSpeakMsg(self, "calibpuzzle1 got invalid widget, widget = '" + widgetName + "'");
        }
        return SCRIPT_CONTINUE;
    }
    public void refreshTerminalSUI(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAccessTerminal", null, 0.0f, false);
    }
    public void retrieveComponent(obj_id self, int row) throws InterruptedException
    {
        if (row >= COMPONENTS.length)
        {
            return;
        }
        int[] status = 
        {
            -1,
            -1,
            -1,
            -1
        };
        if (hasObjVar(self, STATUS_OBJVAR))
        {
            status = getIntArrayObjVar(self, STATUS_OBJVAR);
        }
        if (status == null || status.length == 0)
        {
            refreshTerminalSUI(self);
            return;
        }
        if (status[row] == 0)
        {
            sendSystemMessage(self, MSG_COMP_NOT_INSTALLED);
            refreshTerminalSUI(self);
            return;
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (isIdValid(inv))
        {
            int free_space = getVolumeFree(inv);
            if (free_space <= 0)
            {
                sendSystemMessage(self, MSG_INVENTORY_FULL);
                refreshTerminalSUI(self);
                return;
            }
        }
        else 
        {
            refreshTerminalSUI(self);
            return;
        }
        obj_id component = obj_id.NULL_ID;
        component = createObject(TEMPLATES[row], inv, "");
        if (!isIdValid(component))
        {
            refreshTerminalSUI(self);
            return;
        }
        if (status[row] == 1)
        {
            setObjVar(component, STATUS_OBJVAR, 1);
        }
        else 
        {
            setObjVar(component, STATUS_OBJVAR, -1);
        }
        status[row] = 0;
        setObjVar(self, STATUS_OBJVAR, status);
        sendSystemMessage(self, MSG_COMPONENT_RETRIEVED);
        refreshTerminalSUI(self);
    }
    public void replaceComponent(obj_id self, int row) throws InterruptedException
    {
        if (row >= COMPONENTS.length)
        {
            return;
        }
        int[] status = 
        {
            -1,
            -1,
            -1,
            -1
        };
        if (hasObjVar(self, STATUS_OBJVAR))
        {
            status = getIntArrayObjVar(self, STATUS_OBJVAR);
        }
        if (status == null || status.length == 0)
        {
            refreshTerminalSUI(self);
            return;
        }
        if (status[row] != 0)
        {
            sendSystemMessage(self, MSG_COMP_INSTALLED);
            refreshTerminalSUI(self);
            return;
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (!isIdValid(inv))
        {
            refreshTerminalSUI(self);
            return;
        }
        if (utils.playerHasItemByTemplate(self, TEMPLATES[row]))
        {
            obj_id[] contents = utils.getAllItemsPlayerHasByTemplate(self, TEMPLATES[row]);
            obj_id component = null;
            if (contents == null || contents.length == 0)
            {
                refreshTerminalSUI(self);
                return;
            }
            int compStatus = -1;
            for (int i = 0; i < contents.length; i++)
            {
                if (hasObjVar(contents[i], STATUS_OBJVAR))
                {
                    compStatus = getIntObjVar(contents[i], STATUS_OBJVAR);
                }
                if (compStatus == 1)
                {
                    component = contents[i];
                }
            }
            if (!isIdValid(component))
            {
                for (int j = 0; j < contents.length; j++)
                {
                    if (!hasObjVar(contents[j], STATUS_OBJVAR))
                    {
                        component = contents[j];
                    }
                }
                if (!isIdValid(component))
                {
                    component = contents[0];
                }
            }
            compStatus = -1;
            if (hasObjVar(component, STATUS_OBJVAR))
            {
                compStatus = getIntObjVar(component, STATUS_OBJVAR);
            }
            status[row] = compStatus;
            setObjVar(self, STATUS_OBJVAR, status);
            destroyObject(component);
            sendSystemMessage(self, MSG_COMPONENT_REPLACED);
            refreshTerminalSUI(self);
            return;
        }
        else 
        {
            sendSystemMessage(self, MSG_DONT_HAVE_COMPONENT);
        }
    }
    public void analyzeComponent(obj_id self, boolean callback) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SUI_OBJVAR + ".pid"))
        {
            sendSystemMessage(self, MSG_CANT_OPEN_SUI);
            return;
        }
        obj_id analyzer = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, ANALYZER_OBJVAR))
        {
            analyzer = utils.getObjIdScriptVar(self, ANALYZER_OBJVAR);
        }
        if (!isIdValid(analyzer))
        {
            return;
        }
        int[] analyzed = 
        {
            0,
            0,
            0,
            0
        };
        if (hasObjVar(self, ANALYZED_OBJVAR))
        {
            analyzed = getIntArrayObjVar(self, ANALYZED_OBJVAR);
        }
        Vector tempData = new Vector();
        tempData.setSize(0);
        for (int i = 0; i < TEMPLATES.length; i++)
        {
            if ((analyzed[i] == 0) && (utils.playerHasItemByTemplate(self, TEMPLATES[i])))
            {
                tempData = utils.addElement(tempData, COMPONENTS[i]);
            }
        }
        if (tempData.size() == 0)
        {
            if (!callback)
            {
                sui.msgbox(self, self, SUI_ANALYZER_NO_COMP, sui.OK_ONLY, SUI_ANALYZER_TITLE, 0, "noHandler");
            }
            return;
        }
        else 
        {
            String[] data = new String[tempData.size()];
            for (int i = 0; i < tempData.size(); i++)
            {
                data[i] = (String)tempData.get(i);
            }
            int pid = sui.listbox(self, self, SUI_ANALYZER_PROMPT1, sui.OK_CANCEL, SUI_ANALYZER_TITLE, data, "handleAnalyzeCompCallback", false, false);
            if (pid < 0)
            {
                return;
            }
            utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
            utils.setScriptVar(self, DATA_OBJVAR, data);
            setSUIAssociatedObject(pid, analyzer);
            setSUIMaxRangeToObject(pid, 10.0f);
            showSUIPage(pid);
        }
    }
    public void accessSchematic(obj_id self, boolean callback) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SUI_OBJVAR + ".pid"))
        {
            sendSystemMessage(self, MSG_CANT_OPEN_SUI);
            return;
        }
        obj_id analyzer = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, ANALYZER_OBJVAR))
        {
            analyzer = utils.getObjIdScriptVar(self, ANALYZER_OBJVAR);
        }
        if (!isIdValid(analyzer))
        {
            return;
        }
        int[] analyzed = 
        {
            0,
            0,
            0,
            0
        };
        if (hasObjVar(self, ANALYZED_OBJVAR))
        {
            analyzed = getIntArrayObjVar(self, ANALYZED_OBJVAR);
        }
        Vector tempData = new Vector();
        tempData.setSize(0);
        for (int i = 0; i < TEMPLATES.length; i++)
        {
            if (analyzed[i] == 1)
            {
                tempData = utils.addElement(tempData, COMPONENTS[i]);
            }
        }
        if (tempData.size() == 0)
        {
            if (!callback)
            {
                sui.msgbox(self, self, SUI_ANALYZER_NO_SCHEMATIC, sui.OK_ONLY, SUI_ANALYZER_TITLE, 0, "noHandler");
            }
            return;
        }
        else 
        {
            String[] data = new String[tempData.size()];
            for (int i = 0; i < tempData.size(); i++)
            {
                data[i] = (String)tempData.get(i);
            }
            int pid = sui.listbox(self, self, SUI_ANALYZER_PROMPT2, sui.OK_CANCEL, SUI_ANALYZER_TITLE, data, "handleAccessSchematicCallback", false, false);
            if (pid < 0)
            {
                return;
            }
            utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
            utils.setScriptVar(self, DATA_OBJVAR, data);
            setSUIAssociatedObject(pid, analyzer);
            setSUIMaxRangeToObject(pid, 10.0f);
            showSUIPage(pid);
        }
    }
    public void configProcessorPuzzle(obj_id self, obj_id calibrator) throws InterruptedException
    {
        int pid = createSUIPage("/Script.calibration.game4", self, self, "configProcessorPuzzleCallback");
        if (pid < 0)
        {
            return;
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
        int[] goal = 
        {
            0,
            0,
            0,
            0,
            0,
            0
        };
        int[] current = 
        {
            0,
            0,
            0,
            0,
            0,
            0
        };
        int lastr = -1;
        int r = -1;
        int tries = 10;
        boolean mixed = false;
        while (!mixed)
        {
            for (int h = 0; h < 6; h++)
            {
                goal[h] = 0;
            }
            for (int i = 0; i < 6; i++)
            {
                do
                {
                    r = rand(0, 5);
                } while (r == lastr);
                lastr = r;
                goal = toggleButton(goal, r);
            }
            for (int j = 0; j < 6; j++)
            {
                if (goal[j] != current[j])
                {
                    mixed = true;
                }
            }
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".goal", goal);
        utils.setScriptVar(self, SUI_OBJVAR + ".current", current);
        utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
        utils.setScriptVar(self, SUI_OBJVAR + ".max_tries", tries);
        setSUIProperty(pid, "bg.caption.lbltitle", "Text", SUI_CONFIG_TITLE);
        setSUIProperty(pid, "top.description.desc", "Text", SUI_CONFIG_DESCRIPTION);
        setSUIProperty(pid, "top.description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " 100%");
        for (int i = 0; i < goal.length; i++)
        {
            if (goal[i] == 1)
            {
                setSUIProperty(pid, CONFIG_SERVER_BUTTONS[i], "Color", "#000000");
            }
            setSUIProperty(pid, CONFIG_SERVER_BUTTONS[i], "IsCancelButton", "false");
            setSUIProperty(pid, CONFIG_PLAYER_BUTTONS[i], "IsCancelButton", "false");
        }
        setSUIProperty(pid, "bg.mmc.close", "IsCancelButton", "true");
        for (int i = 0; i < CONFIG_PLAYER_BUTTONS.length; i++)
        {
            subscribeToSUIEvent(pid, sui_event_type.SET_onButton, CONFIG_PLAYER_BUTTONS[i], "configProcessorPuzzleCallback");
        }
        setSUIAssociatedObject(pid, calibrator);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
    }
    public int[] toggleButton(int[] config, int button) throws InterruptedException
    {
        int secondary1 = -1;
        int secondary2 = -1;
        switch (button)
        {
            case 0:
            secondary1 = 3;
            secondary2 = 4;
            break;
            case 1:
            secondary1 = 4;
            secondary2 = 5;
            break;
            case 2:
            secondary1 = 3;
            secondary2 = 5;
            break;
            case 3:
            secondary1 = 0;
            secondary2 = 2;
            break;
            case 4:
            secondary1 = 0;
            secondary2 = 1;
            break;
            case 5:
            secondary1 = 1;
            secondary2 = 2;
            break;
        }
        if (secondary1 == -1 || secondary2 == -1)
        {
            return null;
        }
        if (config[button] == 0)
        {
            config[button] = 1;
        }
        else 
        {
            config[button] = 0;
        }
        if (config[secondary1] == 0)
        {
            config[secondary1] = 1;
        }
        else 
        {
            config[secondary1] = 0;
        }
        if (config[secondary2] == 0)
        {
            config[secondary2] = 1;
        }
        else 
        {
            config[secondary2] = 0;
        }
        return config;
    }
    public void gyroReceiverPuzzle(obj_id self, obj_id calibrator) throws InterruptedException
    {
        int pid = createSUIPage("/Script.calibration.game3", self, self, "gyroReceiverPuzzleCallback");
        if (pid < 0)
        {
            return;
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
        int[] goal = 
        {
            0,
            0,
            0
        };
        int[] current = 
        {
            0,
            0,
            0
        };
        int tries = 10;
        for (int i = 0; i < 3; i++)
        {
            goal[i] = rand(0, 100);
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".goal", goal);
        utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
        utils.setScriptVar(self, SUI_OBJVAR + ".max_tries", tries);
        setSUIProperty(pid, "top.sliders.1.slider", "Value", "100");
        setSUIProperty(pid, "top.sliders.2.slider", "Value", "100");
        setSUIProperty(pid, "top.sliders.3.slider", "Value", "100");
        setSUIProperty(pid, "top.sliders.1.title", "Text", SUI_GYRO_SLIDER1);
        setSUIProperty(pid, "top.sliders.2.title", "Text", SUI_GYRO_SLIDER2);
        setSUIProperty(pid, "top.sliders.3.title", "Text", SUI_GYRO_SLIDER3);
        setSUIProperty(pid, "bg.caption.lbltitle", "Text", SUI_GYRO_TITLE);
        setSUIProperty(pid, "description.desc", "Text", SUI_GYRO_DESCRIPTION);
        setSUIProperty(pid, "description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " 100%");
        for (int i = 0; i < goal.length; i++)
        {
            float pct = (float)goal[i] / 100.0f;
            int dec = (int)(255 * pct);
            String hex = Integer.toHexString(dec);
            if (hex.length() == 1)
            {
                hex = "0" + hex;
            }
            String hexValue = "#" + hex + hex + hex;
            setSUIProperty(pid, "top.bars.server." + (i + 1), "Color", hexValue);
        }
        subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "gyroReceiverPuzzleCallback");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "top.sliders.1.slider", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "top.sliders.2.slider", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "top.sliders.3.slider", "Value");
        setSUIAssociatedObject(pid, calibrator);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
        flushSUIPage(pid);
    }
    public void signalAmpPuzzle(obj_id self, obj_id calibrator) throws InterruptedException
    {
        int pid = createSUIPage("/Script.calibration.game2", self, self, "signalAmpPuzzleCallback");
        if (pid < 0)
        {
            return;
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
        int bar0 = rand(0, 2);
        int bar1 = rand(0, 1);
        if (bar1 == 0)
        {
            bar1 = -1;
        }
        bar1 += bar0;
        if (bar1 == -1)
        {
            bar1 = 2;
        }
        if (bar1 == 3)
        {
            bar1 = 0;
        }
        int i = 0;
        int bar2 = 0;
        for (i = 0; i < 3; i++)
        {
            if ((i != bar0) && (i != bar1))
            {
                bar2 = i;
            }
        }
        utils.setScriptVar(self, "calibpuzzle2_bar0", bar0);
        utils.setScriptVar(self, "calibpuzzle2_bar1", bar1);
        utils.setScriptVar(self, "calibpuzzle2_bar2", bar2);
        float coeff0 = rand();
        float coeff1 = rand();
        float coeff2 = rand();
        utils.setScriptVar(self, "calibpuzzle2_coeff0", coeff0);
        utils.setScriptVar(self, "calibpuzzle2_coeff1", coeff1);
        utils.setScriptVar(self, "calibpuzzle2_coeff2", coeff2);
        LOG("ampPuzzle", "coeff " + coeff0 + "," + coeff1 + "," + coeff2);
        float sliderPos0 = rand();
        float sliderPos1 = rand();
        float sliderPos2 = rand();
        utils.setScriptVar(self, "calibpuzzle2_barPos0", sliderPos0);
        utils.setScriptVar(self, "calibpuzzle2_barPos1", sliderPos1);
        utils.setScriptVar(self, "calibpuzzle2_barPos2", sliderPos2);
        setSUIProperty(pid, "%slider1%", "Value", "" + (100 * sliderPos0));
        setSUIProperty(pid, "%slider2%", "Value", "" + (100 * sliderPos1));
        setSUIProperty(pid, "%slider3%", "Value", "" + (100 * sliderPos2));
        setSUIProperty(pid, "top.sliders.1.title", "Text", SUI_AMP_SLIDER1);
        setSUIProperty(pid, "top.sliders.2.title", "Text", SUI_AMP_SLIDER2);
        setSUIProperty(pid, "top.sliders.3.title", "Text", SUI_AMP_SLIDER3);
        int tries = 25;
        utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
        utils.setScriptVar(self, SUI_OBJVAR + ".max_tries", tries);
        setSUIProperty(pid, "%title%", "Text", SUI_AMP_TITLE);
        setSUIProperty(pid, "description.desc", "Text", SUI_AMP_DESCRIPTION);
        setSUIProperty(pid, "%attemptsRemaining%", "Text", SUI_ATTEMPTS_REMAINING + " 100%");
        float n;
        float c0 = ((bar0 == 0) ? sliderPos0 : ((bar0 == 1) ? sliderPos1 : sliderPos2));
        float c1 = ((bar1 == 0) ? sliderPos0 : ((bar1 == 1) ? sliderPos1 : sliderPos2));
        float c2 = ((bar2 == 0) ? sliderPos0 : ((bar2 == 1) ? sliderPos1 : sliderPos2));
        for (i = 0; i < 7; i++)
        {
            n = i / 7.0f;
            float value = (c0 + (c1 * n) + (c2 * n * n)) / 3.0f;
            if (value < 0.0f)
            {
                value = 0.0f;
            }
            if (value > 1.0f)
            {
                value = 1.0f;
            }
            setSUIProperty(pid, "%barTop" + (i + 1) + "%", "Value", "" + (100 * value));
            setSUIProperty(pid, "%barTop" + (i + 1) + "%", "OnRunScript", "sizeY = (parent.sizeY * Value) / 100\nlocationY = parent.sizeY - sizeY");
            setSUIProperty(pid, "%barTop" + (i + 1) + "%", "RunScript", "");
        }
        for (i = 0; i < 7; i++)
        {
            n = i / 7.0f;
            float value = (coeff0 + (coeff1 * n) + (coeff2 * n * n)) / 3.0f;
            if (value < 0.0f)
            {
                value = 0.0f;
            }
            if (value > 1.0f)
            {
                value = 1.0f;
            }
            LOG("ampPuzzle", "server n = " + n + " value = " + value);
            setSUIProperty(pid, "%barBottom" + (i + 1) + "%", "Value", "" + (100 * value));
            setSUIProperty(pid, "%barBottom" + (i + 1) + "%", "OnRunScript", "sizeY = (parent.sizeY * Value) / 100\nlocationY = parent.sizeY - sizeY");
            setSUIProperty(pid, "%barBottom" + (i + 1) + "%", "RunScript", "");
        }
        setSUIProperty(pid, "%okButton%", "IsDefaultButton", "true");
        subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "signalAmpPuzzleCallback");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "%slider1%", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "%slider2%", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "%slider3%", "Value");
        setSUIAssociatedObject(pid, calibrator);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
        flushSUIPage(pid);
    }
    public void solidStateArrayPuzzle(obj_id self, obj_id calibrator) throws InterruptedException
    {
        int pid = createSUIPage("/Script.calibration.game1", self, self, "solidStateArrayPuzzleCallback");
        if (pid < 0)
        {
            return;
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".pid", pid);
        int goal = rand(0, 100);
        int[] current = 
        {
            0,
            0,
            0,
            0,
            0
        };
        int[] mod = 
        {
            0,
            0,
            0,
            0,
            0
        };
        int tries = 10;
        for (int i = 0; i < current.length; i++)
        {
            current[i] = rand(0, 100);
            mod[i] = rand(0, 100);
        }
        utils.setScriptVar(self, SUI_OBJVAR + ".goal", goal);
        utils.setScriptVar(self, SUI_OBJVAR + ".mod", mod);
        utils.setScriptVar(self, SUI_OBJVAR + ".tries", tries);
        utils.setScriptVar(self, SUI_OBJVAR + ".max_tries", tries);
        setSUIProperty(pid, null, "UserResizable", "false");
        setSUIProperty(pid, "bg.caption.lbltitle", "Text", SUI_SSA_TITLE);
        setSUIProperty(pid, "description.desc", "Text", SUI_SSA_DESCRIPTION);
        setSUIProperty(pid, "description.attempts", "Text", SUI_ATTEMPTS_REMAINING + " 100%");
        setSUIProperty(pid, "comp.sliders.1.title", "Text", SUI_SSA_SLIDER1);
        setSUIProperty(pid, "comp.sliders.2.title", "Text", SUI_SSA_SLIDER2);
        setSUIProperty(pid, "comp.sliders.3.title", "Text", SUI_SSA_SLIDER3);
        setSUIProperty(pid, "comp.sliders.4.title", "Text", SUI_SSA_SLIDER4);
        setSUIProperty(pid, "comp.sliders.5.title", "Text", SUI_SSA_SLIDER5);
        float g0 = (float)goal / 100.0f;
        float g1 = (float)SUI_SSA_LINE_RANGE * g0;
        float g2 = (float)SUI_SSA_LINE_MIN + g1;
        int g = (int)g2;
        setSUIProperty(pid, "line", "OnRunScript", "locationX = Value");
        setSUIProperty(pid, "line", "Value", "" + g);
        setSUIProperty(pid, "line", "RunScript", "");
        for (int i = 0; i < current.length; i++)
        {
            int pct = current[i];
            setSUIProperty(pid, "comp.sliders." + (i + 1) + ".slider", "Value", Integer.toString(pct));
            pct += mod[i];
            if (pct > 100)
            {
                pct -= 100;
            }
            setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".left", "OnRunScript", "sizeX = parent.sizeX * Value / 100.0");
            setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".right", "OnRunScript", "sizeX = parent.sizeX * Value / 100.0\nlocationX = parent.sizeX - sizeX");
            setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".left", "Value", "" + (pct - 3));
            setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".right", "Value", "" + (100 - (pct + 3)));
            setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".left", "RunScript", "");
            setSUIProperty(pid, "comp.bars.bar" + (i + 1) + ".right", "RunScript", "");
        }
        subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "solidStateArrayPuzzleCallback");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "comp.sliders.1.slider", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "comp.sliders.2.slider", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "comp.sliders.3.slider", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "comp.sliders.4.slider", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "comp.sliders.5.slider", "Value");
        setSUIAssociatedObject(pid, calibrator);
        setSUIMaxRangeToObject(pid, 10.0f);
        showSUIPage(pid);
        flushSUIPage(pid);
    }
}
