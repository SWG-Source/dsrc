package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class story_reward_meatlumps_act1 extends script.base_script
{
    public story_reward_meatlumps_act1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "getQuestPlayerName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int getQuestPlayerName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id questPlayer = utils.getContainingPlayer(self);
        if (isIdValid(questPlayer))
        {
            String questPlayerName = getPlayerFullName(questPlayer);
            setObjVar(self, "questPlayerName", questPlayerName);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!hasObjVar(self, "questPlayerName"))
        {
            obj_id questPlayer = utils.getContainingPlayer(self);
            if (isIdValid(questPlayer))
            {
                String questPlayerName = getPlayerFullName(questPlayer);
                setObjVar(self, "questPlayerName", questPlayerName);
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        string_id menu_read = new string_id("theme_park/corellia/quest", "menu_read");
        int menuExtras = mi.addRootMenu(menu_info_types.SERVER_MENU1, menu_read);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            String title = utils.packStringId(new string_id("theme_park/corellia/quest", "act1_story_title"));
            String closeButton = utils.packStringId(new string_id("theme_park/corellia/quest", "button_close"));
            String storyText_intro = utils.packStringId(new string_id("theme_park/corellia/quest", "act1_story_text_intro"));
            String questPlayerName = getStringObjVar(self, "questPlayerName");
            String storyText_middle = utils.packStringId(new string_id("theme_park/corellia/quest", "act1_story_text_middle"));
            String storyText_end = utils.packStringId(new string_id("theme_park/corellia/quest", "act1_story_text_end"));
            String storyText = storyText_intro + " " + questPlayerName + " " + storyText_middle + " " + questPlayerName + " " + storyText_end;
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "noHandler");
            setSUIProperty(pid, "", "Size", "1000,500");
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, storyText);
            sui.msgboxButtonSetup(pid, sui.OK_ONLY);
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, closeButton);
            sui.showSUIPage(pid);
        }
        return SCRIPT_CONTINUE;
    }
}
