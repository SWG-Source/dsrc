package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.tcg;
import script.library.space_transition;
import script.library.space_utils;

public class tcg_video_game_series4 extends script.base_script
{
    public tcg_video_game_series4()
    {
    }
    public static final String MINIGAME_PARAM_PREFIX = "minigame_mahjong";
    public static final String MINIGAME_GAMENAME_MAHJONG = "mahjong";
    public static final String MINIGAME_VARNAME_GAME = "game";
    public static final String MINIGAME_VARNAME_TABLE = "table";
    public static final String MINIGAME_PARAM_RESULT = "result";
    public static final String MINIGAME_VARNAME_SCORE = "score";
    public static final String MINIGAME_VARNAME_PLAYER = "player";
    public static final String MINIGAME_VARNAME_DEFAULT_LAYOUT = "layout";
    public static final String[] TCG_MAHJONG_LAYOUTS = 
    {
        "classic",
        "tie_fighter",
        "death_star",
        "jedi_temple"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, tcg.TABLE_HIGHSCORE_SLOTS))
        {
            setupInitialScores(self);
        }
        if (hasObjVar(self, "minigame_table.highscores.exp_death_star"))
        {
            removeObjVar(self, "minigame_table.highscores.exp_death_star");
            tcg.setupDeathStarFix(self);
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
        if (!hasObjVar(self, tcg.TABLE_HIGHSCORE_SLOTS))
        {
            setupInitialScores(self);
        }
        for (int i = 0; i <= TCG_MAHJONG_LAYOUTS.length - 1; i++)
        {
            names[idx] = "mahjong_" + TCG_MAHJONG_LAYOUTS[i] + "_high_scores";
            attribs[idx] = getHighScores(self, TCG_MAHJONG_LAYOUTS[i]);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (inHouse(self))
        {
            menu_info_data data = mi.getMenuItemByType(menu_info_types.SERVER_MENU1);
            if (getPosture(player) == POSTURE_SITTING)
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("ui", "play_minigame"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            playGame(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int clientMinigameResult(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary data = params.getDictionary(MINIGAME_PARAM_RESULT);
        if (data == null)
        {
            return SCRIPT_CONTINUE;
        }
        int score = data.getInt(MINIGAME_VARNAME_SCORE);
        if (score <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = data.getObjId(MINIGAME_VARNAME_PLAYER);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        String layout = data.getString(MINIGAME_VARNAME_DEFAULT_LAYOUT);
        if (layout == null || layout.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        layout = verifyLayOut(self, layout);
        if (layout == null || layout.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String gameTime = getCalendarTimeStringLocal(getCalendarTime());
        String gameData = score + "." + getPlayerFullName(player) + "." + layout + "." + gameTime;
        checkScore(self, score, gameData, layout);
        return SCRIPT_CONTINUE;
    }
    public boolean playGame(obj_id table, obj_id player) throws InterruptedException
    {
        if (!isValidId(table) || !exists(table))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        dictionary data = new dictionary();
        data.put(MINIGAME_VARNAME_GAME, MINIGAME_GAMENAME_MAHJONG);
        data.put(MINIGAME_VARNAME_TABLE, table);
        clientMinigameOpen(player, data);
        return true;
    }
    public boolean setupInitialScores(obj_id table) throws InterruptedException
    {
        if (!isValidId(table) || !exists(table))
        {
            return false;
        }
        for (int i = 0; i < TCG_MAHJONG_LAYOUTS.length; i++)
        {
            int idx = rand(0, tcg.DEFAULT_HIGH_SCORE_MODIFIER.length - 1);
            float modifier = tcg.DEFAULT_HIGH_SCORE_MODIFIER[idx];
            setUpLayOutScores(table, TCG_MAHJONG_LAYOUTS[i], modifier);
        }
        return true;
    }
    public boolean setUpLayOutScores(obj_id table, String layout, float modifier) throws InterruptedException
    {
        if (!isValidId(table) || !exists(table))
        {
            return false;
        }
        if (layout == null || layout.equals(""))
        {
            return false;
        }
        if (modifier < 0)
        {
            return false;
        }
        for (int i = 1; i <= tcg.MAX_NUMBER_OF_HIGH_SCORES; i++)
        {
            int randomPosition = rand(0, tcg.DEFAULT_HIGH_SCORE_LIST.length - 1);
            String name = tcg.DEFAULT_HIGH_SCORE_LIST[randomPosition];
            float score = ((tcg.EST_MAX_SCORE * modifier) / i);
            int scoreInt = (int)score;
            String timeDate = getCalendarTimeStringLocal(getCalendarTime());
            String stringData = scoreInt + "." + name + "." + layout + "." + timeDate;
            setObjVar(table, tcg.TABLE_HIGHSCORE_SLOTS + "." + layout + ".slot_" + i, stringData);
        }
        return true;
    }
    public boolean checkScore(obj_id table, int newScore, String gameData, String layout) throws InterruptedException
    {
        if (!isValidId(table) || !exists(table))
        {
            return false;
        }
        if (newScore <= 0)
        {
            return false;
        }
        if (gameData == null || gameData.equals(""))
        {
            return false;
        }
        if (layout == null || layout.equals(""))
        {
            return false;
        }
        int newHsSlot = -1;
        for (int i = tcg.MAX_NUMBER_OF_HIGH_SCORES - 1; i >= 1; i--)
        {
            String slot = getStringObjVar(table, tcg.TABLE_HIGHSCORE_SLOTS + "." + layout + ".slot_" + i);
            String[] slotContents = split(slot, '.');
            if (slotContents == null || slotContents.length <= 0)
            {
                continue;
            }
            if (newScore > utils.stringToInt(slotContents[0]))
            {
                newHsSlot = i;
            }
            else 
            {
                break;
            }
        }
        if (newHsSlot < 0)
        {
            return true;
        }
        for (int i = tcg.MAX_NUMBER_OF_HIGH_SCORES; i >= newHsSlot; i--)
        {
            if (i < 5 && i > 0)
            {
                String moveMe = getStringObjVar(table, tcg.TABLE_HIGHSCORE_SLOTS + "." + layout + ".slot_" + i);
                setObjVar(table, tcg.TABLE_HIGHSCORE_SLOTS + "." + layout + ".slot_" + (i + 1), moveMe);
            }
        }
        setObjVar(table, tcg.TABLE_HIGHSCORE_SLOTS + "." + layout + ".slot_" + newHsSlot, gameData);
        return true;
    }
    public String getHighScores(obj_id table, String layout) throws InterruptedException
    {
        if (!isValidId(table) || !exists(table))
        {
            return null;
        }
        String data = "\n";
        for (int i = 1; i <= tcg.MAX_NUMBER_OF_HIGH_SCORES; i++)
        {
            String slot = getStringObjVar(table, tcg.TABLE_HIGHSCORE_SLOTS + "." + layout + ".slot_" + i);
            if (slot.indexOf(layout) > 0)
            {
                String[] slotContents = split(slot, '.');
                if (slotContents == null || slotContents.length <= 0)
                {
                    continue;
                }
                data += "\\^005" + i + ". \\%015" + slotContents[1] + " \\%060" + utils.stringToInt(slotContents[0]) + "\n";
            }
        }
        return data;
    }
    public String verifyLayOut(obj_id self, String layout) throws InterruptedException
    {
        if (layout == null || layout.equals(""))
        {
            return null;
        }
        String[] removalSpaces = split(layout, ' ');
        layout = removalSpaces[0].trim();
        layout = toLower(layout);
        for (int i = 0; i < TCG_MAHJONG_LAYOUTS.length; i++)
        {
            if (TCG_MAHJONG_LAYOUTS[i].startsWith(layout))
            {
                return TCG_MAHJONG_LAYOUTS[i];
            }
        }
        return null;
    }
    public boolean inHouse(obj_id device) throws InterruptedException
    {
        obj_id selfContainer = getContainedBy(device);
        obj_id ship = space_transition.getContainingShip(selfContainer);
        String templateName = "";
        if (isIdValid(ship))
        {
            templateName = getTemplateName(ship);
        }
        if ((utils.isInHouseCellSpace(device) || space_utils.isPobType(templateName)) && !utils.isNestedWithinAPlayer(device))
        {
            return true;
        }
        return false;
    }
}
