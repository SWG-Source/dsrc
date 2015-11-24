package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.veteran_deprecated;

public class emote_group extends script.base_script
{
    public emote_group()
    {
    }
    public static final String OBJVAR_GROUP_GAINED = "group";
    public static final String OBJVAR_GROUP_REWARDED = "rewarded";
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id destParent = getContainedBy(destContainer);
        if (destParent != null && isPlayer(destParent))
        {
            int group = getIntObjVar(self, OBJVAR_GROUP_GAINED);
            if (group > 0)
            {
                int[] emoteGroups = getIntArrayObjVar(destParent, veteran_deprecated.OBJVAR_VETERAN_EMOTES);
                int[] newGroups;
                if (emoteGroups != null)
                {
                    newGroups = new int[emoteGroups.length + 1];
                    for (int i = 0; i < emoteGroups.length; ++i)
                    {
                        newGroups[i] = emoteGroups[i];
                    }
                }
                else 
                {
                    newGroups = new int[1];
                }
                newGroups[newGroups.length - 1] = group;
                setObjVar(destParent, veteran_deprecated.OBJVAR_VETERAN_EMOTES, newGroups);
                setObjVar(self, OBJVAR_GROUP_REWARDED, true);
            }
            else 
            {
                CustomerServiceLog("veteran", "WARNING: Emote reward object " + self + " does not have a group objvar");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (player == null || names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        int group = getIntObjVar(self, OBJVAR_GROUP_GAINED);
        if (group > 0)
        {
            int[] emotes = dataTableGetIntColumn(veteran_deprecated.EMOTES_DATATABLE, veteran_deprecated.EMOTES_COLUMN_EMOTE);
            int[] groups = dataTableGetIntColumn(veteran_deprecated.EMOTES_DATATABLE, veteran_deprecated.EMOTES_COLUMN_GROUP);
            if (emotes != null && groups != null && emotes.length == groups.length)
            {
                int index = getFirstFreeIndex(names);
                for (int i = 0; i < groups.length; ++i)
                {
                    if (groups[i] == group)
                    {
                        String emoteName = getEmoteFromCrc(emotes[i]);
                        if (emoteName != null)
                        {
                            names[index] = "@ui_text_color:emote";
                            attribs[index++] = emoteName;
                        }
                        else 
                        {
                            CustomerServiceLog("veteran", "WARNING: emotes OnGetAttributes, can't get emote string for crc " + emotes[i]);
                        }
                    }
                }
            }
            else 
            {
                CustomerServiceLog("veteran", "WARNING: emotes OnGetAttributes, bad datatable " + veteran_deprecated.EMOTES_DATATABLE);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
