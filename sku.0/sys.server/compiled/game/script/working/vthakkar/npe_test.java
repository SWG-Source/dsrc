package script.working.vthakkar;

import script.dictionary;
import script.library.npe;
import script.library.utils;
import script.location;
import script.obj_id;

public class npe_test extends script.base_script
{
    public npe_test()
    {
    }
    
    public boolean checkGod(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            sendSystemMessageTestingOnly(self, "Please turn off god mode when moving between npe locations. God mode and instances do not get along");
            return true;
        }
        return false;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String arg = st.nextToken();
        if (arg.equals("npeHelp"))
        {
            sendSystemMessageTestingOnly(self, "Commands:");
            sendSystemMessageTestingOnly(self, "setupNpe, gotoHangar, gotoTurret, gotoSpaceStation, gotoOrdSpaceFromSharedStation, gotoOrdStationFromOrdSpace, gotoOrdSpaceFromOrdStation, gotoSharedStationFromOrdSpace, gotoStaging, finishNpe");
            sendSystemMessageTestingOnly(self, "These commands must be run in order, and finished before starting again!");
            sendSystemMessageTestingOnly(self, "There is a lot of cleanup that gets done in the between steps");
        }
        else if (arg.equals("gotoTurret"))
        {
            if (checkGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to turret...");
            npe.movePlayerFromHangarToFalcon(self);
        }
        else if (arg.equals("gotoSpaceStation"))
        {
            if (checkGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to space station...");
            npe.movePlayerFromFalconToSharedStation(self);
        }
        else if (arg.equals("gotoOrdSpaceFromSharedStation"))
        {
            if (checkGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to ord space...");
            npe.movePlayerFromSharedStationToOrdMantellSpace(self, new location(0, 0, 0));
        }
        else if (arg.equals("gotoOrdStationFromOrdSpace"))
        {
            if (checkGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to space station...");
            npe.movePlayerFromOrdMantellSpaceToOrdMantellDungeon(self);
        }
        else if (arg.equals("gotoOrdSpaceFromOrdStation"))
        {
            if (checkGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to ord space...");
            npe.movePlayerFromOrdMantellDungeonToOrdMantellSpace(self, new location(0, 0, 0));
        }
        else if (arg.equals("gotoSharedStationFromOrdSpace"))
        {
            if (checkGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to space station...");
            npe.movePlayerFromOrdMantellSpaceToSharedStation(self);
        }
        else if (arg.equals("finishNpe"))
        {
            if (checkGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to post staging area...");
            npe.movePlayerFromSharedStationToFinishLocation(self);
        }
        else if (arg.equals("count"))
        {
            obj_id[] tempArray;
            tempArray = getObjectsInRange(self, 20000);
            sendSystemMessageTestingOnly(self, "Found " + tempArray.length + " objects");
            tempArray = getCreaturesInRange(self, 20000);
            sendSystemMessageTestingOnly(self, "Found " + tempArray.length + " creatures");
            tempArray = getNonCreaturesInRange(self, 20000);
            sendSystemMessageTestingOnly(self, "Found " + tempArray.length + " non creatures");
            tempArray = getNPCsInRange(self, 10000);
            sendSystemMessageTestingOnly(self, "Found " + tempArray.length + " NPCs");
            tempArray = getPlayerCreaturesInRange(self, 20000);
            sendSystemMessageTestingOnly(self, "Found " + tempArray.length + " player creatures");
        }
        else if (arg.equals("countType"))
        {
            String type = st.nextToken();
            if (type == null || type.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id[] tempArray;
            int counter = 0;
            tempArray = getObjectsInRange(self, 20000);
            for (obj_id obj_id : tempArray) {
                if (type.equals(getTemplateName(obj_id))) {
                    ++counter;
                    sendSystemMessageTestingOnly(self, "" + counter + ": " + obj_id);
                }
            }
            sendSystemMessageTestingOnly(self, "Found " + counter + " objects");
        }
        else if (arg.equals("getAllWorldObjects"))
        {
            obj_id[] tempArray;
            int counter = 0;
            tempArray = getObjectsInRange(self, 20000);
            for (obj_id obj_id : tempArray) {
                String template = getTemplateName(obj_id);
                location loc = getLocation(obj_id);
                if (loc.cell == null || loc.cell == obj_id.NULL_ID) {
                    debugConsoleMsg(self, loc.x + " " + loc.y + " " + loc.z + " " + template);
                    ++counter;
                }
            }
            sendSystemMessageTestingOnly(self, "Found " + counter + " objects");
        }
        else if (arg.equals("makeNpeDungeons"))
        {
            String currentScene = getCurrentSceneName();
            if (!currentScene.equals("dungeon1"))
            {
                sendSystemMessageTestingOnly(self, "You should be on dungeon1 to run this command, you're on: " + currentScene);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Creating dungeon objects...");
            createObject("object/building/general/npe_space_station.iff", new location(-5000, 0, -1000));
            createObject("object/building/general/npe_space_station.iff", new location(0, 0, -1000));
            createObject("object/building/general/npe_space_station.iff", new location(5000, 0, -1000));
            createObject("object/building/general/npe_space_dungeon.iff", new location(-6000, 0, -3000));
            createObject("object/building/general/npe_space_dungeon.iff", new location(-3000, 0, -3000));
            createObject("object/building/general/npe_space_dungeon.iff", new location(3000, 0, -3000));
            createObject("object/building/general/npe_space_dungeon.iff", new location(6000, 0, -3000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-7000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-6000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-5000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-4000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-3000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-2000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-1000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(0, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(7000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(6000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(5000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(4000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(3000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(2000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(1000, 0, -4000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-7000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-6000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-5000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-4000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-3000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-2000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(-1000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(0, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(7000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(6000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(5000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(4000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(3000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(2000, 0, -5000));
            createObject("object/building/general/npe_hangar_2.iff", new location(1000, 0, -5000));
        }
        else if (arg.equals("setTutorial") && st.countTokens() == 1)
        {
            String val = st.nextToken();
            boolean tutVal = false;
            if (val.equals("true"))
            {
                tutVal = true;
            }
            sendSystemMessageTestingOnly(self, "Setting tutorial bit to " + tutVal);
            setCompletedTutorial(self, tutVal);
        }
        else if (arg.equals("getWorkingSkill"))
        {
            sendSystemMessageTestingOnly(self, "Your current working skill is " + getWorkingSkill(self));
        }
        else if (arg.equals("getClusterLock") && st.countTokens() == 2)
        {
            String manager = st.nextToken();
            String regex = st.nextToken();
            sendSystemMessageTestingOnly(self, "Acquiring cluster lock");
            getClusterWideData(manager, regex, true, self);
        }
        else if (arg.equals("releaseClusterLock") && st.countTokens() == 2)
        {
            String manager = st.nextToken();
            int lock = utils.stringToInt(st.nextToken());
            sendSystemMessageTestingOnly(self, "Releasing cluster lock");
            releaseClusterWideDataLock(manager, lock);
        }
        else if (arg.equals("enableUI"))
        {
            sendSystemMessageTestingOnly(self, "Enabling NPE UI");
            newbieTutorialEnableHudElement(self, "radar", true, 0);
            newbieTutorialEnableHudElement(self, "chatbox", true, 0);
        }
        else if (arg.equals("getConfig") && st.countTokens() == 2)
        {
            String section = st.nextToken();
            String key = st.nextToken();
            String value = getConfigSetting(section, key);
            if (value != null)
            {
                sendSystemMessageTestingOnly(self, "Config setting for " + section + ":" + key + " is " + value);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Didn't find a config setting for " + section + ":" + key);
            }
        }
        else if (arg.equals("getPlayers") && st.countTokens() == 5)
        {
            float x = utils.stringToFloat(st.nextToken());
            float y = utils.stringToFloat(st.nextToken());
            float z = utils.stringToFloat(st.nextToken());
            String scene = st.nextToken();
            int range = utils.stringToInt(st.nextToken());
            location loc = new location(x, y, z, scene);
            obj_id[] players_range = getPlayerCreaturesInRange(loc, range);
            for (obj_id obj_id : players_range) {
                if (isIdValid(obj_id)) {
                    sendSystemMessageTestingOnly(self, "Got player " + obj_id + " " + getPlayerFullName(obj_id));
                }
            }
            sendSystemMessageTestingOnly(self, "Found " + players_range.length + " players");
        }
        else if (arg.equals("getSceneName"))
        {
            String scene = getCurrentSceneName();
            if (scene != null)
            {
                sendSystemMessageTestingOnly(self, "Current scene name is " + scene);
            }
        }
        else if (arg.equals("gotoTutorial"))
        {
            sendSystemMessageTestingOnly(self, "Back to tutorial with ya!");
            sendPlayerToTutorial(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "OnClusterWideDataResponse: " + manage_name + ", name: " + name + ", lock key: " + lock_key);
        return SCRIPT_CONTINUE;
    }

}
