package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import java.util.StringTokenizer;
import script.library.npe;

public class npe_skip_station extends script.base_script
{
    public npe_skip_station()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id target = getLookAtTarget(self);
        StringTokenizer st = new java.util.StringTokenizer(text);
        int tokens = st.countTokens();
        String command = null;
        if (st.hasMoreTokens())
        {
            command = st.nextToken();
        }
        if (command.equals("leaveStation"))
        {
            string_id stfPrompt = new string_id("npe", "exit_station_prompt");
            string_id stfTitle = new string_id("npe", "exit_station");
            String prompt = utils.packStringId(stfPrompt);
            String title = utils.packStringId(stfTitle);
            int pid = sui.msgbox(self, self, prompt, sui.OK_CANCEL, title, 0, "handTransfer");
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
