package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.gcw;
import script.library.sui;
import script.library.chat;

public class newsnet_terminal extends script.base_script
{
    public newsnet_terminal()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        callAttentionBarker(self);
        return SCRIPT_CONTINUE;
    }
    public static final float MIN_BARK_TIME = 30f;
    public static final float MAX_BARK_TIME = 240f;
    public static final float[] RATINGS = 
    {
        .10f,
        .25f,
        .50f,
        .75f,
        1.0f,
        1.25f,
        1.5f,
        2.0f,
        2.5f
    };
    public static final String[] RATING_STRINGS = 
    {
        "rebel_losing_4",
        "rebel_losing_3",
        "rebel_losing_2",
        "rebel_losing_1",
        "equal",
        "rebel_winning_1",
        "rebel_winning_2",
        "rebel_winning_3",
        "rebel_winning_4"
    };
    public static final String[] BARK_STRINGS = 
    {
        "newsnet_extra",
        "newsnet_read_all"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        int menuOption = item.addRootMenu(menu_info_types.ITEM_USE, new string_id("gcw", "read_headline"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            LOG("LOG_CHANNEL", "newsnet_terminal::OnItemUse");
            doNewsNetUI(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void doNewsNetUI(obj_id self, obj_id player) throws InterruptedException
    {
        float fltRatio = gcw.getRebelRatio(self);
        LOG("gcw", "ratio is " + fltRatio);
        String headlineString = getHeadlineString(fltRatio);
        location locTest = getLocation(self);
        String strPlanetID = locTest.area;
        String planetSTFName = "general";
        if (strPlanetID.equals("naboo") || strPlanetID.equals("corellia"))
        {
            planetSTFName = strPlanetID;
        }
        String strBody = "headline_" + planetSTFName + "_" + headlineString;
        String strTitle = planetSTFName + "_newsnet_name";
        string_id strTitleId = new string_id("gcw", strTitle);
        string_id strBodyID = new string_id("gcw", strBody);
        sui.msgbox(player, player, utils.packStringId(strBodyID), sui.OK_ONLY, utils.packStringId(strTitleId), "noHandler");
    }
    public String getHeadlineString(float fltRatio) throws InterruptedException
    {
        if (fltRatio <= RATINGS[0])
        {
            return RATING_STRINGS[0];
        }
        else if ((fltRatio >= RATINGS[0]) && (fltRatio <= RATINGS[1]))
        {
            return RATING_STRINGS[1];
        }
        else if ((fltRatio >= RATINGS[1]) && (fltRatio <= RATINGS[2]))
        {
            return RATING_STRINGS[2];
        }
        else if ((fltRatio >= RATINGS[2]) && (fltRatio <= RATINGS[3]))
        {
            return RATING_STRINGS[3];
        }
        else if ((fltRatio >= RATINGS[3]) && (fltRatio <= RATINGS[5]))
        {
            return RATING_STRINGS[4];
        }
        else if ((fltRatio >= RATINGS[5]) && (fltRatio <= RATINGS[6]))
        {
            return RATING_STRINGS[5];
        }
        else if ((fltRatio >= RATINGS[6]) && (fltRatio <= RATINGS[7]))
        {
            return RATING_STRINGS[6];
        }
        else if ((fltRatio >= RATINGS[7]) && (fltRatio <= RATINGS[8]))
        {
            return RATING_STRINGS[7];
        }
        else if (fltRatio >= RATINGS[8])
        {
            return RATING_STRINGS[8];
        }
        else 
        {
            return null;
        }
    }
    public void callAttentionBarker(obj_id self) throws InterruptedException
    {
        float bark_delay = rand(MIN_BARK_TIME, MIN_BARK_TIME);
        messageTo(self, "barkForAttention", null, bark_delay, false);
    }
    public int factionBaseUnitRefund(obj_id self, dictionary params) throws InterruptedException
    {
        string_id barkMessage = new string_id("gcw", BARK_STRINGS[rand(0, BARK_STRINGS.length)]);
        chat.chat(self, "exclaim", barkMessage);
        callAttentionBarker(self);
        return SCRIPT_CONTINUE;
    }
}
