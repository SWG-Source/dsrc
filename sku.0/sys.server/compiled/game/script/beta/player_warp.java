package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class player_warp extends script.base_script
{
    public player_warp()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.startsWith("warp"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            if (st.countTokens() < 5)
            {
                sendSystemMessageTestingOnly(self, "format: warp <planet> <x> <y> <z>");
                return SCRIPT_CONTINUE;
            }
            LOG("LOG_CHANNEL", "tokens -> " + st.countTokens() + " nextToken ->" + st.nextToken());
            if (st.hasMoreTokens())
            {
                String planet = st.nextToken();
                float x = (float)utils.stringToInt(st.nextToken());
                float y = (float)utils.stringToInt(st.nextToken());
                float z = (float)utils.stringToInt(st.nextToken());
                warpPlayer(self, planet, x, y, z, null, 0.0f, 0.0f, 0.0f);
            }
        }
        if (text.startsWith("create"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            if (st.countTokens() < 2)
            {
                sendSystemMessageTestingOnly(self, "format: create <template>");
                return SCRIPT_CONTINUE;
            }
            obj_id inv = getObjectInSlot(self, "inventory");
            if (inv == null)
            {
                LOG("LOG_CHANNEL", "player_warp: create -- Unable to get an inventory slot for " + self);
                sendSystemMessageTestingOnly(self, "Your inventory object is null!");
            }
            String command = st.nextToken();
            String template = st.nextToken();
            if (template == null || template.length() < 1)
            {
                sendSystemMessageTestingOnly(self, "That is an invalid object.");
                return SCRIPT_CONTINUE;
            }
            LOG("LOG_CHANNEL", "template ->" + template);
            int template_idx = dataTableSearchColumnForString(template, "Template", "datatables/beta/restricted_template.iff");
            LOG("LOG_CHANNEL", "template_idx ->" + template_idx);
            if (template_idx != -1)
            {
                sendSystemMessageTestingOnly(self, "You cannot create that object.");
                return SCRIPT_CONTINUE;
            }
            int num_rows = dataTableGetNumRows("datatables/beta/restricted_template.iff");
            for (int i = 0; i < num_rows; i++)
            {
                dictionary row = dataTableGetRow("datatables/beta/restricted_template.iff", i);
                if (row != null)
                {
                    String directory = row.getString("Directory");
                    if (directory.length() > 0)
                    {
                        if ((template.indexOf(directory)) != -1)
                        {
                            sendSystemMessageTestingOnly(self, "You cannot create that object.");
                            return SCRIPT_CONTINUE;
                        }
                    }
                    else 
                    {
                        break;
                    }
                }
            }
            if (template.length() < 1)
            {
                sendSystemMessageTestingOnly(self, "That is an invalid object.");
            }
            else 
            {
                obj_id object = createObjectOverloaded(template, inv);
                if (!isIdValid(object))
                {
                    sendSystemMessageTestingOnly(self, "That is an invalid object.");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, object + " created.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
