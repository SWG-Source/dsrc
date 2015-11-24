package script.theme_park.new_player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.veteran_deprecated;

public class helper_droid_pcd extends script.base_script
{
    public helper_droid_pcd()
    {
    }
    public static final int ALLOW_DELETE_AGE = 3 * 60 * 60;
    public static final String NEW_PLAYER_SCRIPT = "theme_park.new_player.new_player";
    public static final int ACCOUNT_AGE_CAP = 30;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String newPlayerQuestsEnabled = getConfigSetting("New_Player", "NewPlayerQuestsEnabled");
        if (newPlayerQuestsEnabled == null || !newPlayerQuestsEnabled.equals("true"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id datapad = getContainedBy(self);
        if (!isIdValid(datapad))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getContainedBy(datapad);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(player, veteran_deprecated.OBJVAR_TIME_ACTIVE))
        {
            int accountTime = getIntObjVar(player, veteran_deprecated.OBJVAR_TIME_ACTIVE);
            if (accountTime >= ACCOUNT_AGE_CAP)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int playTime = getPlayerPlayedTime(player);
        if (playTime > ALLOW_DELETE_AGE)
        {
            detachScript(player, NEW_PLAYER_SCRIPT);
        }
        else 
        {
            obj_var_list varList = getObjVarList(player, "new_player.quest");
            if (varList == null)
            {
                detachScript(player, NEW_PLAYER_SCRIPT);
            }
            else 
            {
                int numItem = varList.getNumItems();
                for (int i = 0; i < numItem; i++)
                {
                    obj_var var = varList.getObjVar(i);
                    int questNum = var.getIntData();
                    if (questNum > 0)
                    {
                        messageTo(player, "handleHelperDroidCheck", null, 9, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                detachScript(player, NEW_PLAYER_SCRIPT);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
