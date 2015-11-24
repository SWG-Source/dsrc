package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.hue;
import script.library.player_structure;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class gcw_mulit_image_painting extends script.base_script
{
    public gcw_mulit_image_painting()
    {
    }
    public static final String CUST_TEXTURE_VAR = "/private/index_texture_1";
    public static final String SUI_MENU_PID = "gcw_painting_controls_pid";
    public static final String PAINTING_CUR_MODE_OBJVAR = "gcw_painting_current_mode";
    public static final String PAINTING_CUR_CYCLE_VAR = "gcw_painting_current_cycle";
    public static final int PAINTING_TEXTURE_INDEX_REBEL = 0;
    public static final int PAINTING_TEXTURE_INDEX_IMPERIAL = 1;
    public static final int CYCLE_MIN_INTERVAL = 150;
    public static final int CYCLE_MAX_INTERVAL = 300;
    public static final int MODE_REBEL_PAINTING = 0;
    public static final int MODE_IMPERIAL_PAINTING = 1;
    public static final int MODE_CYCLE_PAINTINGS = 2;
    public static final int MODE_PLANETARY_CONTROL = 3;
    public static final int MODE_GALACTIC_CONTROL = 4;
    public static final String[] MODE_OPTIONS = 
    {
        "Rebel Painting",
        "Imperial Painting",
        "Cycle Paintings",
        "Reflect Planetary Control",
        "Reflect Galactic Control"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleOnAttachSetup", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int handleOnAttachSetup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (isIdValid(player))
        {
            int initialTexture = getStartTexture(self, player);
            hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, initialTexture);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (checkLocation(self))
        {
            int paintingMode = 2;
            if (hasObjVar(self, PAINTING_CUR_MODE_OBJVAR))
            {
                int modeObjVar = getIntObjVar(self, PAINTING_CUR_MODE_OBJVAR);
                if (modeObjVar >= 0 && modeObjVar < MODE_OPTIONS.length)
                {
                    paintingMode = modeObjVar;
                }
            }
            setupPaintingMode(self, obj_id.NULL_ID, paintingMode);
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
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (canUsePainting(self, player))
        {
            int paintingMode = 2;
            if (hasObjVar(self, PAINTING_CUR_MODE_OBJVAR))
            {
                int modeObjVar = getIntObjVar(self, PAINTING_CUR_MODE_OBJVAR);
                if (modeObjVar >= 0 && modeObjVar < MODE_OPTIONS.length)
                {
                    paintingMode = modeObjVar;
                }
            }
            String paintingModeName = MODE_OPTIONS[paintingMode];
            names[idx] = "gcw_loyalty_painting_current_setting";
            attribs[idx] = paintingModeName;
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (checkLocation(self))
        {
            if (canUsePainting(self, player))
            {
                int menu = mi.addRootMenu(menu_info_types.SERVER_MENU8, new string_id("reward_sys", "painting_menu_controls"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU8 && isIdValid(player))
        {
            if (canUsePainting(self, player))
            {
                if (!checkLocation(self))
                {
                    sendSystemMessage(player, new string_id("reward_sys", "painting_not_in_house"));
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    showControlMenu(self, player);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canUsePainting(obj_id painting, obj_id player) throws InterruptedException
    {
        obj_id paintingOwner = getOwner(painting);
        if (isIdValid(paintingOwner) && paintingOwner == player)
        {
            return true;
        }
        if (isGod(player))
        {
            return true;
        }
        return false;
    }
    public boolean checkLocation(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (!utils.isInHouseCellSpace(item))
        {
            return false;
        }
        if (!utils.isNestedWithinAPlayer(item))
        {
            obj_id house = getTopMostContainer(item);
            if (isIdValid(house) && player_structure.isBuilding(house))
            {
                location here = getLocation(item);
                if (isIdValid(here.cell) && getContainedBy(item) == here.cell)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void showControlMenu(obj_id self, obj_id player) throws InterruptedException
    {
        closeOldWindow(self);
        String prompt = "@reward_sys:painting_controls_prompt";
        String title = "@reward_sys:painting_controls_title";
        String[] tempModeOptionsArray = new String[MODE_OPTIONS.length];
        utils.copyArray(MODE_OPTIONS, tempModeOptionsArray);
        if (hasObjVar(self, PAINTING_CUR_MODE_OBJVAR))
        {
            int paintingMode = getIntObjVar(self, PAINTING_CUR_MODE_OBJVAR);
            if (paintingMode >= 0 && paintingMode < MODE_OPTIONS.length)
            {
                tempModeOptionsArray[paintingMode] += "\\#0099FF - Current Mode";
            }
        }
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, tempModeOptionsArray, "paintingControlsHandler", false, false);
        int messageBoxSizeWidth = 484;
        int messageBoxSizeHeight = 312;
        sui.setSizeProperty(pid, messageBoxSizeWidth, messageBoxSizeHeight);
        sui.showSUIPage(pid);
        utils.setScriptVar(self, SUI_MENU_PID, pid);
    }
    public void closeOldWindow(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SUI_MENU_PID))
        {
            int oldpid = utils.getIntScriptVar(self, SUI_MENU_PID);
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(self, SUI_MENU_PID);
        }
    }
    public int paintingControlsHandler(obj_id self, dictionary params) throws InterruptedException
    {
        int button = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        int modeChoice = sui.getListboxSelectedRow(params);
        if (button == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        if (!isIdValid(here.cell) || getContainedBy(player) != here.cell)
        {
            sendSystemMessage(player, new string_id("reward_sys", "painting_not_same_cell"));
            return SCRIPT_CONTINUE;
        }
        setupPaintingMode(self, player, modeChoice);
        return SCRIPT_CONTINUE;
    }
    public void setupPaintingMode(obj_id self, obj_id player, int modeChoice) throws InterruptedException
    {
        if (hasObjVar(self, PAINTING_CUR_CYCLE_VAR))
        {
            removeObjVar(self, PAINTING_CUR_CYCLE_VAR);
        }
        switch (modeChoice)
        {
            case 0:
            setToRebelPaintingMode(self, player);
            break;
            case 1:
            setToImperialPaintingMode(self, player);
            break;
            case 2:
            setToCyclingPaintingMode(self, player);
            break;
            case 3:
            setToPlanetaryControlPaintingMode(self, player);
            break;
            case 4:
            setToGalacticControlPaintingMode(self, player);
            break;
            default:
            setToCyclingPaintingMode(self, player);
            break;
        }
    }
    public void setToRebelPaintingMode(obj_id self, obj_id player) throws InterruptedException
    {
        hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, PAINTING_TEXTURE_INDEX_REBEL);
        setObjVar(self, PAINTING_CUR_MODE_OBJVAR, MODE_REBEL_PAINTING);
        if (isIdValid(player))
        {
            sendSystemMessage(player, new string_id("reward_sys", "painting_set_to_rebel"));
        }
    }
    public void setToImperialPaintingMode(obj_id self, obj_id player) throws InterruptedException
    {
        hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, PAINTING_TEXTURE_INDEX_IMPERIAL);
        setObjVar(self, PAINTING_CUR_MODE_OBJVAR, MODE_IMPERIAL_PAINTING);
        if (isIdValid(player))
        {
            sendSystemMessage(player, new string_id("reward_sys", "painting_set_to_imperial"));
        }
    }
    public void setToCyclingPaintingMode(obj_id self, obj_id player) throws InterruptedException
    {
        int startTexture = getStartTexture(self, player);
        setObjVar(self, PAINTING_CUR_CYCLE_VAR, startTexture);
        hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, startTexture);
        if (!hasMessageTo(self, "cyclingPaintingHandler"))
        {
            messageTo(self, "cyclingPaintingHandler", null, rand(CYCLE_MIN_INTERVAL, CYCLE_MAX_INTERVAL), false);
        }
        setObjVar(self, PAINTING_CUR_MODE_OBJVAR, MODE_CYCLE_PAINTINGS);
        if (isIdValid(player))
        {
            sendSystemMessage(player, new string_id("reward_sys", "painting_set_to_cycle"));
        }
    }
    public void setToPlanetaryControlPaintingMode(obj_id self, obj_id player) throws InterruptedException
    {
        int startTexture = getPlanetaryControlTexture(self, player);
        setObjVar(self, PAINTING_CUR_CYCLE_VAR, startTexture);
        hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, startTexture);
        if (!hasMessageTo(self, "gcwFactionControlPaintingHandler"))
        {
            messageTo(self, "gcwFactionControlPaintingHandler", null, CYCLE_MIN_INTERVAL, false);
        }
        setObjVar(self, PAINTING_CUR_MODE_OBJVAR, MODE_PLANETARY_CONTROL);
        if (isIdValid(player))
        {
            sendSystemMessage(player, new string_id("reward_sys", "painting_set_to_planet_control"));
        }
    }
    public void setToGalacticControlPaintingMode(obj_id self, obj_id player) throws InterruptedException
    {
        int startTexture = getGcwControlTexture(self, player, "galaxy");
        setObjVar(self, PAINTING_CUR_CYCLE_VAR, startTexture);
        hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, startTexture);
        if (!hasMessageTo(self, "gcwFactionControlPaintingHandler"))
        {
            messageTo(self, "gcwFactionControlPaintingHandler", null, CYCLE_MIN_INTERVAL, false);
        }
        setObjVar(self, PAINTING_CUR_MODE_OBJVAR, MODE_GALACTIC_CONTROL);
        if (isIdValid(player))
        {
            sendSystemMessage(player, new string_id("reward_sys", "painting_set_to_galactic_control"));
        }
    }
    public int getStartTexture(obj_id self, obj_id player) throws InterruptedException
    {
        int startingTexture = rand(PAINTING_TEXTURE_INDEX_REBEL, PAINTING_TEXTURE_INDEX_IMPERIAL);
        if (hasObjVar(self, PAINTING_CUR_CYCLE_VAR))
        {
            startingTexture = getIntObjVar(self, PAINTING_CUR_CYCLE_VAR);
        }
        if (isIdValid(player))
        {
            if (factions.isRebel(player))
            {
                startingTexture = PAINTING_TEXTURE_INDEX_REBEL;
            }
            else if (factions.isImperial(player))
            {
                startingTexture = PAINTING_TEXTURE_INDEX_IMPERIAL;
            }
        }
        return startingTexture;
    }
    public int getPlanetaryControlTexture(obj_id self, obj_id player) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        if (planet.startsWith("space"))
        {
            String[] parse = split(planet, '_');
            if (parse.length >= 2)
            {
                planet = parse[1];
            }
        }
        return getGcwControlTexture(self, player, planet);
    }
    public int getGcwControlTexture(obj_id self, obj_id player, String category) throws InterruptedException
    {
        int texture = getStartTexture(self, player);
        int imperialPercentControl = getGcwGroupImperialScorePercentile(category);
        if (imperialPercentControl > 50)
        {
            texture = PAINTING_TEXTURE_INDEX_IMPERIAL;
        }
        else if (imperialPercentControl < 50)
        {
            texture = PAINTING_TEXTURE_INDEX_REBEL;
        }
        return texture;
    }
    public int cyclingPaintingHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (checkLocation(self))
        {
            int paintingMode = getIntObjVar(self, PAINTING_CUR_MODE_OBJVAR);
            if (paintingMode == MODE_CYCLE_PAINTINGS)
            {
                int newCycle = 0;
                int curCycle = getIntObjVar(self, PAINTING_CUR_CYCLE_VAR);
                if (curCycle == 0)
                {
                    newCycle = 1;
                }
                hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, newCycle);
                setObjVar(self, PAINTING_CUR_CYCLE_VAR, newCycle);
                if (!hasMessageTo(self, "cyclingPaintingHandler"))
                {
                    messageTo(self, "cyclingPaintingHandler", null, rand(CYCLE_MIN_INTERVAL, CYCLE_MAX_INTERVAL), false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int gcwFactionControlPaintingHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (checkLocation(self))
        {
            int paintingMode = getIntObjVar(self, PAINTING_CUR_MODE_OBJVAR);
            if (paintingMode == MODE_PLANETARY_CONTROL || paintingMode == MODE_GALACTIC_CONTROL)
            {
                if (paintingMode == MODE_PLANETARY_CONTROL)
                {
                    int planetaryTexture = getPlanetaryControlTexture(self, obj_id.NULL_ID);
                    hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, planetaryTexture);
                }
                else if (paintingMode == MODE_GALACTIC_CONTROL)
                {
                    int galacticTexture = getGcwControlTexture(self, obj_id.NULL_ID, "galaxy");
                    hue.setRangedIntCustomVar(self, CUST_TEXTURE_VAR, galacticTexture);
                }
                if (!hasMessageTo(self, "gcwFactionControlPaintingHandler"))
                {
                    messageTo(self, "gcwFactionControlPaintingHandler", null, CYCLE_MIN_INTERVAL, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
