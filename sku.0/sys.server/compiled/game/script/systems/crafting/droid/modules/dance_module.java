package script.systems.crafting.droid.modules;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.pet_lib;
import script.library.sui;
import script.library.utils;

public class dance_module extends script.base_script
{
    public dance_module()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public static final String PROMPT = "@pet/droid_modules:dance_prompt";
    public static final String TITLE = "@pet/droid_modules:dance_title";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithinAPlayer(self) && utils.getContainingPlayer(self) == player)
        {
            int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(STF_FILE, "load_module"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (utils.isNestedWithinAPlayer(self) && utils.getContainingPlayer(self) == player)
            {
                obj_id[] droidIds = pet_lib.getPcdsForType(player, pet_lib.PET_TYPE_DROID);
                Vector resizAbleDroidIds = new Vector(Arrays.asList(droidIds));
                Vector droidNames = new Vector();
                droidNames.setSize(0);
                for (int i = 0; i < droidIds.length; ++i)
                {
                    if (hasObjVar(droidIds[i], "module_data.dancing_droid"))
                    {
                        utils.removeElement(resizAbleDroidIds, droidIds[i]);
                        continue;
                    }
                    String droidName = getName(droidIds[i]);
                    String[] splitName = split(droidName, '/');
                    if (splitName.length > 1)
                    {
                        utils.addElement(droidNames, "@" + droidName);
                    }
                    else 
                    {
                        utils.addElement(droidNames, droidName);
                    }
                }
                utils.setScriptVar(player, "dancing_droid.ids", resizAbleDroidIds);
                int pid = sui.listbox(self, player, PROMPT, sui.OK_CANCEL, TITLE, droidNames, "onDanceDroidLoaded", true, true);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int onDanceDroidLoaded(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        obj_id[] droidIds = utils.getObjIdArrayScriptVar(player, "dancing_droid.ids");
        utils.removeScriptVar(player, "dancing_droid.ids");
        int bp = sui.getIntButtonPressed(params);
        int row = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_OK)
        {
            setObjVar(droidIds[row], "module_data.dancing_droid", 1);
            if (utils.hasScriptVar(droidIds[row], callable.OBJVAR_CALLABLE_CALLED))
            {
                obj_id droid = utils.getObjIdScriptVar(droidIds[row], callable.OBJVAR_CALLABLE_CALLED);
                attachScript(droid, "ai.dancing_droid");
            }
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
