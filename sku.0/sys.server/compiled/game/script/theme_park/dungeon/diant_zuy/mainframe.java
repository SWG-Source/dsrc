package script.theme_park.dungeon.diant_zuy;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.locations;
import script.library.sui;
import script.library.colors;

public class mainframe extends script.base_script
{
    public mainframe()
    {
    }
    public static final String DIANT_BUNKER = "dungeon/diant_bunker";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        string_id mainframeUse = new string_id(DIANT_BUNKER, "mainframe_use");
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, mainframeUse);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        int genState = getIntObjVar(top, "diant.generator");
        int securityState = getIntObjVar(top, "diant.security");
        int doorOpen = getIntObjVar(top, "diant.doorOpened");
        if (item == menu_info_types.ITEM_USE)
        {
            if (doorOpen == 1)
            {
                string_id computerMainframeAlready = new string_id(DIANT_BUNKER, "computer_mainframe_already");
                sendSystemMessage(player, computerMainframeAlready);
                return SCRIPT_CONTINUE;
            }
            if (genState == 0 && doorOpen == 0)
            {
                string_id computerMainframeNoPower = new string_id(DIANT_BUNKER, "computer_mainframe_no_power");
                sendSystemMessage(player, computerMainframeNoPower);
                return SCRIPT_CONTINUE;
            }
            if (securityState == 0 && genState == 1 && doorOpen == 0)
            {
                string_id computerMainframeDenied = new string_id(DIANT_BUNKER, "computer_mainframe_denied");
                sendSystemMessage(player, computerMainframeDenied);
                return SCRIPT_CONTINUE;
            }
            if (genState == 1 && securityState == 1 && doorOpen == 0)
            {
                showMainframe(self, player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int showMainframe(obj_id pcd, obj_id player) throws InterruptedException
    {
        string_id textMsg = new string_id(DIANT_BUNKER, "computer_mainframe_text");
        String TEXT = getString(textMsg);
        string_id titleMsg = new string_id(DIANT_BUNKER, "computer_mainframe_title");
        String TITLE = getString(titleMsg);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, pcd, player, "handleMainframe");
        setSUIProperty(pid, "", "Size", "250,175");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT);
        sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, "Info");
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Yes");
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "No");
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
        subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
        sui.showSUIPage(pid);
        return pid;
    }
    public int handleMainframe(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            if (revert != null && !revert.equals(""))
            {
                string_id computerMainframeInfo = new string_id(DIANT_BUNKER, "computer_mainframe_info");
                sendSystemMessage(player, computerMainframeInfo);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id computerMainframeYes = new string_id(DIANT_BUNKER, "computer_mainframe_yes");
                sendSystemMessage(player, computerMainframeYes);
                messageTo(self, "tamperWithMainframe", params, 0, false);
                return SCRIPT_CONTINUE;
            }
            case sui.BP_CANCEL:
            string_id computerMainframeNo = new string_id(DIANT_BUNKER, "computer_mainframe_no");
            sendSystemMessage(player, computerMainframeNo);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int tamperWithMainframe(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id top = getTopMostContainer(self);
        int educationBonus = 0;
        int total = 0;
        int roll = rand(1, 5);
        if (hasSkill(player, "class_medic_phase1_master") || hasSkill(player, "class_munitions_phase1_master") || hasSkill(player, "class_engineering_phase2_novice") || hasSkill(player, "class_bountyhunter_phase2_novice") || hasSkill(player, "class_smuggler_phase1_novice"))
        {
            educationBonus++;
        }
        if (hasSkill(player, "class_medic_phase2_novice") || hasSkill(player, "class_munitions_phase2_master") || hasSkill(player, "class_engineering_phase3_novice") || hasSkill(player, "class_bountyhunter_phase3_novice") || hasSkill(player, "class_smuggler_phase2_novice"))
        {
            educationBonus++;
        }
        if (hasSkill(player, "class_medic_phase3_novice") || hasSkill(player, "class_munitions_phase2_master") || hasSkill(player, "class_engineering_phase4_novice") || hasSkill(player, "class_bountyhunter_phase4_novice") || hasSkill(player, "class_smuggler_phase3_novice"))
        {
            educationBonus++;
        }
        total = roll + educationBonus;
        if (total >= 4)
        {
            string_id computerMainframeSuccess = new string_id(DIANT_BUNKER, "computer_mainframe_success");
            string_id computerMainframeSuccessFly = new string_id(DIANT_BUNKER, "computer_mainframe_success_fly");
            sendSystemMessage(player, computerMainframeSuccess);
            showFlyText(player, computerMainframeSuccessFly, 1.5f, colors.BLUE);
            setObjVar(top, "diant.doorOpened", 1);
            messageTo(top, "openLocks", params, 0, false);
            playMusic(player, "sound/music_acq_academic.snd");
        }
        if (total < 4)
        {
            string_id computerMainframeFailure = new string_id(DIANT_BUNKER, "computer_mainframe_failure");
            string_id computerMainframeFailureFly = new string_id(DIANT_BUNKER, "computer_mainframe_failure_fly");
            sendSystemMessage(player, computerMainframeFailure);
            showFlyText(player, computerMainframeFailureFly, 1.5f, colors.RED);
            messageTo(top, "mainframeFailed", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
