package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;

public class quest_u10_helper_droid extends script.base_script
{
    public quest_u10_helper_droid()
    {
    }
    public static final String[] HELPER_DROID_QUOTES = 
    {
        "artisan_added",
        "artisan_quest_03",
        "brawler_added",
        "brawler_quest_05",
        "entertainer_added",
        "entertainer_quest_00",
        "entertainer_quest_01",
        "entertainer_quest_05",
        "marksman_added",
        "marksman_quest_03",
        "marksman_quest_05",
        "medic_added",
        "medic_quest_01",
        "medic_quest_02",
        "medic_quest_06",
        "scout_added",
        "scout_quest_01",
        "scout_quest_04",
        "scout_quest_05",
        "space_info_how_to_make_money",
        "quest_u10_helper_droid_01",
        "quest_u10_helper_droid_02",
        "quest_u10_helper_droid_03",
        "quest_u10_helper_droid_04",
        "quest_u10_helper_droid_05"
    };
    public static final String[] HELPER_DROID_QUEEN_QUOTES = 
    {
        "quest_u10_helper_droid_queen_01",
        "quest_u10_helper_droid_queen_02",
        "quest_u10_helper_droid_queen_03"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        chat.setChatMood(self, chat.MOOD_NONE);
        chat.setChatType(self, chat.CHAT_SAY);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "lastUtterance"))
        {
            int lastUtterance = utils.getIntScriptVar(self, "lastUtterance");
            if (lastUtterance + 19 >= getGameTime())
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                utils.removeScriptVar(self, "lastUtterance");
            }
        }
        if (rand(8, 9) == 9)
        {
            String randomChat = HELPER_DROID_QUOTES[rand(0, HELPER_DROID_QUOTES.length)];
            String droidType = getCreatureName(self);
            if (droidType.equals("quest_u10_helper_droid_queen"))
            {
                randomChat = HELPER_DROID_QUEEN_QUOTES[rand(0, HELPER_DROID_QUEEN_QUOTES.length)];
            }
            chat.publicChat(self, attacker, new string_id("new_player", randomChat));
            utils.setScriptVar(self, "lastUtterance", getGameTime());
        }
        return SCRIPT_CONTINUE;
    }
}
