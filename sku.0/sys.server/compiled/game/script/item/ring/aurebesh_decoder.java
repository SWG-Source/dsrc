package script.item.ring;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class aurebesh_decoder extends script.base_script
{
    public aurebesh_decoder()
    {
    }
    public static final string_id SID_AUREBESH_TO_ENGLISH = new string_id("collection", "aurebesh_to_english");
    public static final string_id SID_ENGLISH_TO_AUREBESH = new string_id("collection", "english_to_aurebesh");
    public static final String SID_A_TO_E_PROMPT = "@collection:a_to_e_prompt";
    public static final String SID_E_TO_A_PROMPT = "@collection:e_to_a_prompt";
    public static final String SID_A_TO_E_TITLE = "@collection:a_to_e_title";
    public static final String SID_E_TO_A_TITLE = "@collection:e_to_a_title";
    public static final String SID_TRANSLATED_TEXT = "@collection:translated_text";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_ENGLISH_TO_AUREBESH);
            mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_AUREBESH_TO_ENGLISH);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU3)
        {
            int pid = sui.inputbox(self, player, SID_A_TO_E_PROMPT, SID_A_TO_E_TITLE, "aurebeshToEnglish", 25, false, "");
            setSUIProperty(pid, "txtInput", "Style", "/Styles.textbox.default.Style_aurabesh_12");
            flushSUIPage(pid);
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            int pid = sui.inputbox(self, player, SID_E_TO_A_PROMPT, SID_E_TO_A_TITLE, "englishToAurebesh", 25, false, "");
        }
        return SCRIPT_CONTINUE;
    }
    public int englishToAurebesh(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String inputText = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        int pid = sui.msgbox(self, player, inputText, sui.OK_ONLY, SID_TRANSLATED_TEXT, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Font", "aurabesh_18");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        flushSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int aurebeshToEnglish(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String inputText = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        int pid = sui.msgbox(self, player, inputText, sui.OK_ONLY, SID_TRANSLATED_TEXT, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Font", "bold_15");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        flushSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
}
