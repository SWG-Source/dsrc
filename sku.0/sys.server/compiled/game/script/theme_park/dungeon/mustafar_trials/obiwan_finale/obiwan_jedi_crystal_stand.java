package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.mustafar;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class obiwan_jedi_crystal_stand extends script.base_script
{
    public obiwan_jedi_crystal_stand()
    {
    }
    public static final String TRIGGER_VOLUME_CRYSTAL_STAND = "obiwan_crystal_stand_volume";
    public static final float OBI_INTEREST_RADIUS = 8f;
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        crystalStandObjectTriggerVolumeInitializer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        crystalStandObjectTriggerVolumeInitializer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "haveSpawnedObi"))
        {
            if (isPlayer(breacher) && !isIncapacitated(breacher))
            {
                if (volumeName.equals(TRIGGER_VOLUME_CRYSTAL_STAND))
                {
                    debugLogging("//***// OnTriggerVolumeEntered: ", "////>>>> we CAN call obiwan.");
                    obj_id dungeon = utils.getObjIdScriptVar(self, "dungeon");
                    if (!isIdValid(dungeon))
                    {
                        debugLogging("//***// OnTriggerVolumeEntered: ", "////>>>> dungeon obj_id scriptVar invalid. EXITING.");
                        return SCRIPT_CONTINUE;
                    }
                    debugLogging("//***// crystalStandObjectTriggerVolumeInitializer: ", "////>>>> going to spawn obi!");
                    spawnObi(breacher, dungeon, self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_jedi_crystal_stand/" + section, message);
        }
    }
    public void crystalStandObjectTriggerVolumeInitializer(obj_id self) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// crystalStandObjectTriggerVolumeInitializer: ", "////>>>> dungeon obj_id is invalid. BROKEN");
            return;
        }
        if (!utils.hasScriptVar(dungeon, "obiwan") && !utils.hasScriptVar(self, "haveSpawnedObi"))
        {
            if (!hasTriggerVolume(self, TRIGGER_VOLUME_CRYSTAL_STAND))
            {
                debugLogging("//***// crystalStandObjectTriggerVolumeInitializer: ", "////>>>> created new trigger volume TRIGGER_VOLUME_CRYSTAL_STAND");
                createTriggerVolume(TRIGGER_VOLUME_CRYSTAL_STAND, OBI_INTEREST_RADIUS, true);
            }
            else 
            {
                obj_id[] denizens = getTriggerVolumeContents(self, TRIGGER_VOLUME_CRYSTAL_STAND);
                debugLogging("//***// crystalStandObjectTriggerVolumeInitializer: ", "////>>>> got trigger volume contents. There are: " + denizens.length + " things in the trigger volume");
                for (int i = 0; i < denizens.length; i++)
                {
                    if (isPlayer(denizens[i]) && !isIncapacitated(denizens[i]))
                    {
                        debugLogging("//***// crystalStandObjectTriggerVolumeInitializer: ", "////>>>> Found a player who also isn't incapped. Going to spawn Obi.");
                        spawnObi(denizens[i], dungeon, self);
                        return;
                    }
                }
            }
        }
        return;
    }
    public void spawnObi(obj_id player, obj_id dungeon, obj_id landmark) throws InterruptedException
    {
        debugLogging("//***// spawnObi: ", "////>>>> entered.");
        obj_id obiwan = mustafar.callObiwan(player, landmark);
        if (isIdValid(obiwan))
        {
            utils.setScriptVar(dungeon, "obiLocation", getLocation(obiwan));
            clearCondition(obiwan, CONDITION_CONVERSABLE);
            detachScript(obiwan, "conversation.som_kenobi_obi_wan");
        }
        utils.setScriptVar(dungeon, "player", player);
        utils.setScriptVar(dungeon, "obiwan", obiwan);
        utils.setScriptVar(landmark, "haveSpawnedObi", 1);
        messageTo(dungeon, "obiwanSpeechDelay", null, 5, false);
    }
}
