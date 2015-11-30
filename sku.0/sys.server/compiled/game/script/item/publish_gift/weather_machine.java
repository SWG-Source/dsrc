package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class weather_machine extends script.base_script
{
    public weather_machine()
    {
    }
    public static final string_id SID_WHILE_DEAD = new string_id("spam", "while_dead");
    public static final string_id SID_TURN_ON = new string_id("spam", "weather_machine_on");
    public static final string_id SID_TURN_OFF = new string_id("spam", "weather_machine_off");
    public static final string_id SID_STORM_INTENSITY = new string_id("spam", "weather_machine_intensity");
    public static final string_id SID_STORM_01 = new string_id("spam", "weather_machine_storm1");
    public static final string_id SID_STORM_02 = new string_id("spam", "weather_machine_storm2");
    public static final string_id SID_STORM_03 = new string_id("spam", "weather_machine_storm3");
    public static final String OBJVAR_WEATHER_PARTICLE_01 = "particle_1";
    public static final String OBJVAR_WEATHER_PARTICLE_02 = "particle_2";
    public static final String OBJVAR_WEATHER_PARTICLE_03 = "particle_3";
    public static final String CS_TCG_5_RAIN_MACHINE_CATEGORY = "tcg_set5_rain_machine";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "weather_machine_on"))
        {
            CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Rain Machine Loaded: Attempting to restart rain machine...rainMachine = (" + self + "," + getTemplateName(self) + ")");
            playWeatherParticle(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id weatherMachine = self;
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (utils.isInHouseCellSpace(weatherMachine))
        {
            obj_id structure = getTopMostContainer(weatherMachine);
            if (!player_structure.isAdmin(structure, player))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(weatherMachine, "weather_machine_on"))
            {
                int stormOnOff = mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_OFF);
            }
            else 
            {
                int stormOnOff = mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURN_ON);
            }
            int intensityMain = mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_STORM_INTENSITY);
            mi.addSubMenu(intensityMain, menu_info_types.SERVER_MENU3, SID_STORM_01);
            mi.addSubMenu(intensityMain, menu_info_types.SERVER_MENU4, SID_STORM_02);
            mi.addSubMenu(intensityMain, menu_info_types.SERVER_MENU5, SID_STORM_03);
            sendDirtyObjectMenuNotification(weatherMachine);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id weatherMachine = self;
        if (!isIdValid(player) || !isIdValid(weatherMachine))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (utils.isInHouseCellSpace(weatherMachine))
        {
            if (item == menu_info_types.ITEM_USE && !hasObjVar(weatherMachine, "weather_machine_on"))
            {
                setObjVar(weatherMachine, "weather_machine_on", 1);
                setObjVar(weatherMachine, "unmoveable", 1);
                playWeatherParticle(weatherMachine);
                CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Radial Menu Selection: Rain machine is being turned on (by " + player + ")...rainMachine = (" + self + "," + getTemplateName(self) + ")");
                return SCRIPT_CONTINUE;
            }
            if (item == menu_info_types.ITEM_USE && hasObjVar(weatherMachine, "weather_machine_on"))
            {
                stopWeatherParticle(weatherMachine);
                CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Radial Menu Selection: Rain machine is being turned off (by " + player + ")...rainMachine = (" + self + "," + getTemplateName(self) + ")");
                return SCRIPT_CONTINUE;
            }
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            removeStormObjVar(weatherMachine);
            setObjVar(weatherMachine, "weatherMachine.storm1", 1);
            checkRefreshParticle(weatherMachine);
            CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Radial Menu Selection: Rain machine is being set to low intensity (by " + player + ")...rainMachine = (" + self + "," + getTemplateName(self) + ")");
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            removeStormObjVar(weatherMachine);
            setObjVar(weatherMachine, "weatherMachine.storm2", 1);
            checkRefreshParticle(weatherMachine);
            CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Radial Menu Selection: Rain machine is being set to medium intensity (by " + player + ")...rainMachine = (" + self + "," + getTemplateName(self) + ")");
        }
        if (item == menu_info_types.SERVER_MENU5)
        {
            removeStormObjVar(weatherMachine);
            setObjVar(weatherMachine, "weatherMachine.storm3", 1);
            checkRefreshParticle(weatherMachine);
            CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Radial Menu Selection: Rain machine is being set to high intensity (by " + player + ")...rainMachine = (" + self + "," + getTemplateName(self) + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "weather_machine_on"))
        {
            if (hasObjVar(self, "weatherParticleId"))
            {
                obj_id particleToDelete = getObjIdObjVar(self, "weatherParticleId");
                if (!isIdNull(particleToDelete))
                {
                    CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Rain Machine Moved: rain machine is being picked up (by " + transferer + ") so cleaning up rain particle(" + particleToDelete + "," + getTemplateName(particleToDelete) + ")...rainMachine = (" + self + "," + getTemplateName(self) + ")");
                    destroyObject(particleToDelete);
                    if (!exists(particleToDelete))
                    {
                        messageTo(self, "handleCleanUpScriptsAndSuch", null, 0.2f, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUpScriptsAndSuch(obj_id self, dictionary params) throws InterruptedException
    {
        cleanUpScriptsAndSuch(self);
        return SCRIPT_CONTINUE;
    }
    public void cleanUpScriptsAndSuch(obj_id weatherMachine) throws InterruptedException
    {
        if (hasObjVar(weatherMachine, "weatherParticleId"))
        {
            obj_id particle_id = getObjIdObjVar(weatherMachine, "weatherParticleId");
            if (exists(particle_id))
            {
                CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Rain Machine Clean-up: cleaning up rain particle(" + particle_id + "," + getTemplateName(particle_id) + ")...rainMachine = (" + weatherMachine + "," + getTemplateName(weatherMachine) + ")");
                destroyObject(particle_id);
                if (!exists(particle_id))
                {
                    removeObjVar(weatherMachine, "weatherParticleId");
                }
            }
            else 
            {
                removeObjVar(weatherMachine, "weatherParticleId");
            }
        }
        if (hasObjVar(weatherMachine, "weather_machine_on"))
        {
            removeObjVar(weatherMachine, "weather_machine_on");
        }
        if (hasObjVar(weatherMachine, "unmoveable"))
        {
            removeObjVar(weatherMachine, "unmoveable");
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "weather_machine_on"))
        {
            if (hasObjVar(self, "weatherParticleId"))
            {
                obj_id particleToDelete = getObjIdObjVar(self, "weatherParticleId");
                if (!isIdNull(particleToDelete))
                {
                    destroyObject(particleToDelete);
                    if (!exists(particleToDelete))
                    {
                        cleanUpScriptsAndSuch(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void checkRefreshParticle(obj_id weatherMachine) throws InterruptedException
    {
        if (hasObjVar(weatherMachine, "weather_machine_on"))
        {
            stopWeatherParticle(weatherMachine);
            setObjVar(weatherMachine, "weather_machine_on", 1);
            setObjVar(weatherMachine, "unmoveable", 1);
            playWeatherParticle(weatherMachine);
        }
    }
    public void stopWeatherParticle(obj_id weatherMachine) throws InterruptedException
    {
        if (hasObjVar(weatherMachine, "weatherParticleId"))
        {
            obj_id particleToDelete = getObjIdObjVar(weatherMachine, "weatherParticleId");
            CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Stop Rain Particle: Turning off rain particle(" + particleToDelete + "," + getTemplateName(particleToDelete) + ")...rainMachine = (" + weatherMachine + "," + getTemplateName(weatherMachine) + ")");
            destroyObject(particleToDelete);
            if (!exists(particleToDelete))
            {
                cleanUpScriptsAndSuch(weatherMachine);
            }
        }
    }
    public void playWeatherParticle(obj_id weatherMachine) throws InterruptedException
    {
        if (!isIdValid(weatherMachine))
        {
            return;
        }
        if (hasObjVar(weatherMachine, "weatherParticleId"))
        {
            obj_id particleToDelete = getObjIdObjVar(weatherMachine, "weatherParticleId");
            if (isIdValid(particleToDelete) && exists(particleToDelete))
            {
                String templateName = getTemplateName(particleToDelete);
                if (templateName != null && templateName.startsWith("object/static/particle/particle_rain_machine"))
                {
                    CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Play Rain Particle: Prepping for new rain particle by cleaning up old rain particle(" + particleToDelete + "," + templateName + ")...rainMachine = (" + weatherMachine + "," + getTemplateName(weatherMachine) + ")");
                    destroyObject(particleToDelete);
                    removeObjVar(weatherMachine, "weatherParticleId");
                }
            }
        }
        if (hasObjVar(weatherMachine, "weather_machine_on"))
        {
            if (hasObjVar(weatherMachine, "weatherMachine.storm1"))
            {
                String particle = getStringObjVar(weatherMachine, OBJVAR_WEATHER_PARTICLE_01);
                obj_id weatherParticle = createObject(particle, getLocation(weatherMachine));
                setObjVar(weatherMachine, "weatherParticleId", weatherParticle);
                CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Play Rain Particle: Creating new rain particle(" + weatherParticle + "," + getTemplateName(weatherParticle) + ")...rainMachine = (" + weatherMachine + "," + getTemplateName(weatherMachine) + ")");
                return;
            }
            if (hasObjVar(weatherMachine, "weatherMachine.storm2"))
            {
                String particle = getStringObjVar(weatherMachine, OBJVAR_WEATHER_PARTICLE_02);
                obj_id weatherParticle = createObject(particle, getLocation(weatherMachine));
                setObjVar(weatherMachine, "weatherParticleId", weatherParticle);
                CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Play Weather Particle: Creating new rain particle(" + weatherParticle + "," + getTemplateName(weatherParticle) + ")...rainMachine = (" + weatherMachine + "," + getTemplateName(weatherMachine) + ")");
                return;
            }
            if (hasObjVar(weatherMachine, "weatherMachine.storm3"))
            {
                String particle = getStringObjVar(weatherMachine, OBJVAR_WEATHER_PARTICLE_03);
                obj_id weatherParticle = createObject(particle, getLocation(weatherMachine));
                setObjVar(weatherMachine, "weatherParticleId", weatherParticle);
                CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Play Weather Particle: Creating new rain particle(" + weatherParticle + "," + getTemplateName(weatherParticle) + ")...rainMachine = (" + weatherMachine + "," + getTemplateName(weatherMachine) + ")");
                return;
            }
            String particle = getStringObjVar(weatherMachine, OBJVAR_WEATHER_PARTICLE_02);
            obj_id weatherParticle = createObject(particle, getLocation(weatherMachine));
            setObjVar(weatherMachine, "weatherParticleId", weatherParticle);
            CustomerServiceLog(CS_TCG_5_RAIN_MACHINE_CATEGORY, "Play Weather Particle: Creating new rain particle(" + weatherParticle + "," + getTemplateName(weatherParticle) + ")...rainMachine = (" + weatherMachine + "," + getTemplateName(weatherMachine) + ")");
        }
    }
    public void removeStormObjVar(obj_id weatherMachine) throws InterruptedException
    {
        if (hasObjVar(weatherMachine, "weatherMachine.storm1"))
        {
            removeObjVar(weatherMachine, "weatherMachine.storm1");
        }
        if (hasObjVar(weatherMachine, "weatherMachine.storm2"))
        {
            removeObjVar(weatherMachine, "weatherMachine.storm2");
        }
        if (hasObjVar(weatherMachine, "weatherMachine.storm3"))
        {
            removeObjVar(weatherMachine, "weatherMachine.storm3");
        }
    }
}
