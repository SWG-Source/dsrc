package script.quest.task.pgc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.groundquests;
import script.library.money;
import script.library.pgc_quests;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class quest_control_device extends script.base_script
{
    public quest_control_device()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            int rootMenu = menuInfo.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("saga_system", "qcd_menu_abandon"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int menu_item) throws InterruptedException
    {
        if (menu_item == menu_info_types.SERVER_MENU1)
        {
            if (utils.isNestedWithin(self, player))
            {
                pgc_quests.setQuestAbandoned(self, player);
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
        obj_id questHolocron = pgc_quests.getQuestHolocronFromQCD(self);
        if (isIdValid(questHolocron))
        {
            String questCreatorName = getStringObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_NAME_OBJVAR);
            String questName = pgc_quests.getQuestName(questHolocron);
            String questDescription = pgc_quests.getQuestDescription(questHolocron);
            int questLevel = getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_LEVEL_OBJVAR);
            String questGroupSetting = getStringObjVar(questHolocron, pgc_quests.PCG_QUEST_DIFFICULTY_OBJVAR);
            boolean requiresKashyyyk = getBooleanObjVar(questHolocron, pgc_quests.PCG_QUEST_NEED_KASHYYYK_OBJVAR);
            boolean requiresMustafar = getBooleanObjVar(questHolocron, pgc_quests.PCG_QUEST_NEED_MUSTAFAR_OBJVAR);
            if (questCreatorName != null && questCreatorName.length() > 0)
            {
                names[idx] = "pgc_quest_creator";
                attribs[idx] = questCreatorName;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            names[idx] = "pgc_quest_name";
            attribs[idx] = questName;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pgc_quest_desc";
            attribs[idx] = questDescription;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pgc_quest_level";
            attribs[idx] = "" + questLevel;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "pgc_quest_group";
            attribs[idx] = questGroupSetting;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            if (requiresKashyyyk)
            {
                names[idx] = "pgc_quest_kashyyyk";
                attribs[idx] = "true";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (requiresMustafar)
            {
                names[idx] = "pgc_quest_mustafar";
                attribs[idx] = "true";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
