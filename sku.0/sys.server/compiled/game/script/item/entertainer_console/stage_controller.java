package script.item.entertainer_console;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.player_structure;
import script.library.sui;
import script.library.static_item;
import script.library.space_utils;
import script.library.space_transition;
import script.library.sui;

public class stage_controller extends script.terminal.base.base_terminal
{
    public stage_controller()
    {
    }
    public static final String[] PROP_TYPE = 
    {
        "Backdrop",
        "Smoke",
        "Pyrotechnics",
        "light",
        "Fog"
    };
    public static final String[] BACKDROP_NUMBER = 
    {
        "Turn Off",
        "Backdrop 1",
        "Backdrop 2",
        "Backdrop 3",
        "Backdrop 4",
        "Backdrop 5",
        "Backdrop 6",
        "Backdrop 7",
        "Backdrop 8",
        "Backdrop 9",
        "Backdrop 10"
    };
    public static final String[] BACKDROP_SET = 
    {
        "Galactic Scenes",
        "Galactic Scenes - Wide"
    };
    public static final string_id SID_START_CONTROLLER = new string_id("spam", "activate_stage_controller");
    public static final string_id SID_NOT_IN_HOUSE = new string_id("spam", "stage_controller_not_in_house");
    public static final string_id SID_NO_PROPS = new string_id("spam", "stage_controller_no_props");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        if (checkLocation(self))
        {
            int startController = mi.addRootMenu(menu_info_types.ITEM_USE, SID_START_CONTROLLER);
        }
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE && isIdValid(player))
        {
            if (checkLocation(self))
            {
                startController(player);
            }
            else 
            {
                sendSystemMessage(player, SID_NOT_IN_HOUSE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void startController(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        obj_id self = getSelf();
        if (!isIdValid(self))
        {
            return;
        }
        obj_id container = getTopMostContainer(self);
        if (!isIdValid(container))
        {
            return;
        }
        if (!checkLocation(self))
        {
            sendSystemMessage(player, SID_NO_PROPS);
            return;
        }
        Vector propList = new Vector();
        Vector backdropList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.backdrop");
        if (backdropList != null && backdropList.size() > 0)
        {
            propList.addElement("Backdrop");
        }
        Vector smokeList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.smoke");
        if (smokeList != null && smokeList.size() > 0)
        {
            propList.addElement("Smoke");
        }
        Vector pyrotechnicList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.pyrotechnic");
        if (pyrotechnicList != null && pyrotechnicList.size() > 0)
        {
            propList.addElement("Pyrotechnic");
        }
        Vector fogList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.fog");
        if (fogList != null && fogList.size() > 0)
        {
            propList.addElement("Fog");
        }
        Vector lightList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.light");
        if (lightList != null && lightList.size() > 0)
        {
            propList.addElement("Light");
        }
        String[] propListArray = new String[propList.size()];
        propList.toArray(propListArray);
        Arrays.sort(propListArray);
        stageMenuStart(self, player, "Select the stage machinery you wish to operate.", "Stage Controler", propListArray, "stageMenuChoice", true, "propList", "propList.propsFound");
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
            obj_id ship = space_transition.getContainingShip(item);
            String templateName = "";
            if (isIdValid(ship))
            {
                templateName = getTemplateName(ship);
            }
            if (isIdValid(house) && (player_structure.isBuilding(house) || space_utils.isPobType(templateName)))
            {
                return true;
            }
        }
        return false;
    }
    public void stageMenuStart(obj_id self, obj_id player, String prompt, String title, String[] options, String myHandler, boolean cancel, String PIDVar, String scriptVar) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, options, myHandler, false, false);
        sui.showSUIPage(pid);
        setWindowPid(player, pid, PIDVar);
        utils.setScriptVar(player, scriptVar, options);
    }
    public void stageMenu(obj_id self, obj_id player, String prompt, String title, String[] options, String myHandler, boolean cancel, String PIDVar, String scriptVar) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL_REFRESH, title, options, myHandler, false, false);
        sui.listboxUseOtherButton(pid, "Back");
        sui.showSUIPage(pid);
        setWindowPid(player, pid, PIDVar);
        utils.setScriptVar(player, scriptVar, options);
    }
    public void closeOldWindow(obj_id player, String scriptVar) throws InterruptedException
    {
        if (utils.hasScriptVar(player, scriptVar))
        {
            int oldpid = utils.getIntScriptVar(player, scriptVar);
            forceCloseSUIPage(oldpid);
            utils.removeScriptVarTree(player, scriptVar);
        }
    }
    public void setWindowPid(obj_id player, int pid, String scriptVar) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, scriptVar, pid);
        }
    }
    public int stageMenuChoice(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id container = getTopMostContainer(getSelf());
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(container))
        {
            return SCRIPT_CONTINUE;
        }
        String propMenuArray[] = utils.getStringArrayScriptVar(player, "propList.propsFound");
        String propSelected = propMenuArray[idx];
        String propTemplate = toLower("entertainer_console." + propSelected);
        String methodName = "handle" + propSelected;
        Vector objectList = utils.getResizeableObjIdArrayScriptVar(container, propTemplate);
        String[] itemList = new String[objectList.size() + 1];
        itemList[0] = "All";
        int n = 0;
        for (int i = 1; i < itemList.length; i++)
        {
            if (!isIdValid(((obj_id)objectList.get(n))))
            {
                continue;
            }
            String tempName = getEncodedName(((obj_id)objectList.get(n)));
            itemList[i] = tempName;
            n++;
        }
        stageMenu(self, player, "Please select the " + propSelected + " machine you wish to activate.", "Stage Controler", itemList, methodName, true, "choice", "prop.arrayList");
        return SCRIPT_CONTINUE;
    }
    public int handleBackdrop(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id container = getTopMostContainer(self);
        int backdropNumber = 0;
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(container))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        Vector backdropList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.backdrop");
        int idx = sui.getListboxSelectedRow(params);
        if (idx >= 0 && idx < backdropList.size() + 1)
        {
            utils.setScriptVar(self, "backdrop.propSelection", idx);
            stageMenu(self, player, "Please Select the backdrop design.", "Stage Controler", BACKDROP_SET, "handleBackdropDesign", true, "backdropChoice", "backdrop.backdropListArray");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBackdropDesign(obj_id self, dictionary params) throws InterruptedException
    {
        int backdropNumber = 0;
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        utils.setScriptVar(self, "backdrop.backdropSet", idx);
        stageMenu(self, player, "Please Select the backdrop design.", "Stage Controler", BACKDROP_NUMBER, "handleAllBackdropPicture", true, "backdropChoice", "backdrop.backdropListArray");
        return SCRIPT_CONTINUE;
    }
    public int handleSmoke(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id container = getTopMostContainer(getSelf());
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        Vector smokeObjectList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.smoke");
        int idx = sui.getListboxSelectedRow(params);
        if (idx == 0)
        {
            for (int i = 0; i < smokeObjectList.size(); i++)
            {
                if (isIdValid(((obj_id)smokeObjectList.get(i))))
                {
                    boolean playEffect = playSmokeEffect(((obj_id)smokeObjectList.get(i)));
                }
            }
        }
        else 
        {
            idx--;
            if (isIdValid(((obj_id)smokeObjectList.get(idx))))
            {
                boolean playEffect = playSmokeEffect(((obj_id)smokeObjectList.get(idx)));
            }
        }
        String[] smokeItemList = new String[smokeObjectList.size() + 1];
        smokeItemList[0] = "All";
        int n = 0;
        for (int i = 1; i < smokeItemList.length; i++)
        {
            if (!isIdValid(((obj_id)smokeObjectList.get(n))))
            {
                continue;
            }
            String tempName = getEncodedName(((obj_id)smokeObjectList.get(n)));
            smokeItemList[i] = tempName;
            n++;
        }
        stageMenu(self, player, "Please Select the Smoke Machine you wish to activate.", "Stage Controler", smokeItemList, "handleSmoke", true, "smokeChoice", "smoke.smokeListArray");
        return SCRIPT_CONTINUE;
    }
    public int handlePyrotechnic(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        obj_id container = getTopMostContainer(getSelf());
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        Vector pyroObjectList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.pyrotechnic");
        int idx = sui.getListboxSelectedRow(params);
        if (idx == 0)
        {
            for (int i = 0; i < pyroObjectList.size(); i++)
            {
                if (isIdValid(((obj_id)pyroObjectList.get(i))))
                {
                    boolean playEffect = playFireEffect(((obj_id)pyroObjectList.get(i)));
                }
            }
        }
        else 
        {
            idx--;
            if (isIdValid(((obj_id)pyroObjectList.get(idx))))
            {
                boolean playEffect = playFireEffect(((obj_id)pyroObjectList.get(idx)));
            }
        }
        String[] pyroItemList = new String[pyroObjectList.size() + 1];
        pyroItemList[0] = "All";
        int n = 0;
        for (int i = 1; i < pyroItemList.length; i++)
        {
            if (!isIdValid(((obj_id)pyroObjectList.get(n))))
            {
                continue;
            }
            String tempName = getEncodedName(((obj_id)pyroObjectList.get(n)));
            pyroItemList[i] = tempName;
            n++;
        }
        stageMenu(self, player, "Please Select the Pyrotechnic Machine you wish to activate.", "Stage Controler", pyroItemList, "handlePyrotechnic", true, "pyroChoice", "pyro.pyroListArray");
        return SCRIPT_CONTINUE;
    }
    public int handleFog(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        obj_id container = getTopMostContainer(getSelf());
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        Vector fogObjectList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.fog");
        int idx = sui.getListboxSelectedRow(params);
        if (idx == 0)
        {
            for (int i = 0; i < fogObjectList.size(); i++)
            {
                if (isIdValid(((obj_id)fogObjectList.get(i))))
                {
                    boolean playEffect = startFogEffect(((obj_id)fogObjectList.get(i)));
                }
            }
        }
        else 
        {
            idx--;
            if (isIdValid(((obj_id)fogObjectList.get(idx))))
            {
                boolean playEffect = startFogEffect(((obj_id)fogObjectList.get(idx)));
            }
        }
        String[] fogItemList = new String[fogObjectList.size() + 1];
        fogItemList[0] = "All";
        int n = 0;
        for (int i = 1; i < fogItemList.length; i++)
        {
            if (!isIdValid(((obj_id)fogObjectList.get(n))))
            {
                continue;
            }
            String tempName = getEncodedName(((obj_id)fogObjectList.get(n)));
            fogItemList[i] = tempName;
            n++;
        }
        stageMenu(self, player, "Please Select the Fog Machine you wish to activate.", "Stage Controler", fogItemList, "handleFog", true, "fogChoice", "fog.fogListArray");
        return SCRIPT_CONTINUE;
    }
    public int handleLight(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        Vector lightList = utils.getResizeableObjIdArrayScriptVar(self, "item.entertainer_console.light");
        int idx = sui.getListboxSelectedRow(params);
        boolean playEffect = playLightEffect(((obj_id)lightList.get(idx)));
        Vector lightObjectList = utils.getResizeableObjIdArrayScriptVar(self, "item.entertainer_console.light");
        String[] lightItemList = new String[lightObjectList.size()];
        for (int i = 0; i < lightItemList.length; i++)
        {
            if (!isIdValid(((obj_id)lightObjectList.get(i))))
            {
                String tempName = getEncodedName(((obj_id)lightObjectList.get(i)));
                lightItemList[i] = tempName;
            }
        }
        stageMenu(self, player, "Please Select the Light Machine you wish to activate.", "Stage Controler", lightItemList, "handleLightList", true, "lightChoice", "light.lightListArray");
        return SCRIPT_CONTINUE;
    }
    public int handleAllBackdropPicture(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id container = getTopMostContainer(getSelf());
        int backdropNumber = sui.getListboxSelectedRow(params);
        if (!isIdValid(container))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            startController(player);
            return SCRIPT_CONTINUE;
        }
        Vector backdropList = utils.getResizeableObjIdArrayScriptVar(container, "entertainer_console.backdrop");
        String backdropObjectString = "item_generated_backdrop_01_";
        int propSelection = 0;
        if (utils.hasScriptVar(self, "backdrop.backdropSet"))
        {
            int backdropSet = utils.getIntScriptVar(self, "backdrop.backdropSet");
            utils.removeScriptVar(self, "backdrop.backdropSet");
            if (backdropSet == 1)
            {
                backdropObjectString = "item_generated_backdrop_wide_01_";
            }
        }
        if (utils.hasScriptVar(self, "backdrop.propSelection"))
        {
            propSelection = utils.getIntScriptVar(self, "backdrop.propSelection");
            utils.removeScriptVar(self, "backdrop.propSelection");
        }
        if (propSelection == 0)
        {
            for (int i = 0; i < backdropList.size(); i++)
            {
                if (isIdValid(((obj_id)backdropList.get(i))))
                {
                    boolean changed = changeBackdrop(((obj_id)backdropList.get(i)), backdropObjectString, backdropNumber);
                }
            }
        }
        else 
        {
            propSelection--;
            if (isIdValid(((obj_id)backdropList.get(propSelection))))
            {
                boolean changed = changeBackdrop(((obj_id)backdropList.get(propSelection)), backdropObjectString, backdropNumber);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean changeBackdrop(obj_id prop, String backdropObject, int backdropNumber) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id house = getTopMostContainer(self);
        obj_id owner = getOwner(house);
        if (!isIdValid(prop) || !isIdValid(self) || !isIdValid(house) || !isIdValid(owner))
        {
            return false;
        }
        if (!checkLocation(prop))
        {
            return false;
        }
        if (hasObjVar(prop, "paintingId"))
        {
            obj_id oldBackdrop = getObjIdObjVar(prop, "paintingId");
            destroyObject(oldBackdrop);
        }
        if (backdropNumber == 0)
        {
            return true;
        }
        String staticString = backdropObject + backdropNumber;
        location backdropLocation = getLocation(prop);
        if (backdropLocation != null)
        {
            float myYaw = getYaw(prop);
            backdropLocation.y += 0.3f;
            obj_id backdropId = static_item.createNewItemFunction(staticString, house, backdropLocation);
            if (isIdValid(backdropId) || isIdNull(backdropId))
            {
                setYaw(backdropId, myYaw);
                setObjVar(prop, "paintingType", staticString);
                setObjVar(prop, "paintingId", backdropId);
                setObjVar(backdropId, "generatorId", self);
                return true;
            }
        }
        return false;
    }
    public boolean playSmokeEffect(obj_id prop) throws InterruptedException
    {
        if (!isIdValid(prop))
        {
            return false;
        }
        if (!checkLocation(prop))
        {
            return false;
        }
        location loc = getLocation(prop);
        if (loc == null)
        {
            return false;
        }
        obj_id[] players = getAllPlayers(loc, 20.0f);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "appearance/pt_partydroid_fogmachine.prt", loc, 0);
            }
        }
        return true;
    }
    public boolean playFireEffect(obj_id prop) throws InterruptedException
    {
        if (!isIdValid(prop))
        {
            return false;
        }
        if (!checkLocation(prop))
        {
            return false;
        }
        location loc = getLocation(prop);
        if (loc == null)
        {
            return false;
        }
        obj_id[] players = getAllPlayers(loc, 20.0f);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/stage_controller_explosion_1.cef", loc, 0);
            }
        }
        return true;
    }
    public boolean playLightEffect(obj_id prop) throws InterruptedException
    {
        if (!isIdValid(prop))
        {
            return false;
        }
        if (!checkLocation(prop))
        {
            return false;
        }
        location loc = getLocation(prop);
        if (loc == null)
        {
            return false;
        }
        obj_id[] players = getAllPlayers(loc, 20.0f);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clientdata/item/client_shared_item_treasure_gem_red_s01.cdf", loc, 0);
            }
        }
        return true;
    }
    public boolean startFogEffect(obj_id prop) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(prop))
        {
            return false;
        }
        if (!checkLocation(prop))
        {
            return false;
        }
        location loc = getLocation(prop);
        if (loc == null)
        {
            return false;
        }
        if (hasObjVar(prop, "entertainer_console.fogId"))
        {
            obj_id fogObject = getObjIdObjVar(prop, "entertainer_console.fogId");
            if (!isIdValid(fogObject))
            {
                return false;
            }
            destroyObject(fogObject);
            removeObjVar(prop, "entertainer_console.fogId");
            return false;
        }
        if (!hasObjVar(prop, "entertainer_console.fogId"))
        {
            obj_id fogObject = createObject("object/static/particle/pt_miasma_of_fog_gray.iff", getLocation(prop));
            setObjVar(prop, "entertainer_console.fogId", fogObject);
            return true;
        }
        return false;
    }
}
