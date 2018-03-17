package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_counterstrike;
import script.library.fs_dyn_village;
import script.library.utils;
import script.library.player_structure;

public class fs_camp_remote extends script.base_script
{
    public fs_camp_remote()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, fs_counterstrike.OBJVAR_BORN_ON, getGameTime());
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        fs_counterstrike.checkPhaseItemDisable(self, 3);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnuUse = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("fs_quest_village", "fs_cs_remote_use"));
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int percentDecay = fs_counterstrike.getPhaseItemPercentDecay(self, 3);
        names[idx] = "fs_p3_item_decay_rate";
        attribs[idx] = "" + percentDecay + "%";
        idx++;
        if (idx < names.length)
        {
            names[idx] = "fs_p3_item_decay_in";
            if (!hasObjVar(self, fs_counterstrike.OBJVAR_BORN_ON))
            {
                return SCRIPT_CONTINUE;
            }
            int bornOn = getIntObjVar(self, fs_counterstrike.OBJVAR_BORN_ON);
            int now = getGameTime();
            int phaseDuration = fs_dyn_village.getPhaseDuration(3);
            int itemAge = now - bornOn;
            int remainingLife = phaseDuration - itemAge;
            if (remainingLife < 1)
            {
                attribs[idx] = "Already Decayed";
            }
            else 
            {
                attribs[idx] = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(remainingLife));
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (fs_counterstrike.getPhaseItemPercentDecay(self, 3) >= 100)
        {
            sendSystemMessage(player, new string_id("fs_quest_village", "p3_item_decayed"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (!hasObjVar(self, fs_counterstrike.OBJVAR_CAMP_NAME))
            {
                sendSystemMessage(player, new string_id("fs_quest_village", "fs_remote_fail"));
                return SCRIPT_CONTINUE;
            }
            if (fs_counterstrike.attemptPowerDownShield(player, getStringObjVar(self, fs_counterstrike.OBJVAR_CAMP_NAME)))
            {
                if (rand(1, 100) <= 90)
                {
                    sendSystemMessage(player, new string_id("fs_quest_village", "remote_break"));
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
