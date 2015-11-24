package script.holocron;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.planetary_map;
import script.library.locations;
import script.library.utils;
import script.library.prose;
import script.library.create;
import script.library.ai_lib;
import script.library.regions;

public class newbie_handoff extends script.base_script
{
    public newbie_handoff()
    {
    }
    public static final int MAX_DESTROY_TARGETS = 10;
    public static final int MAX_SEARCH_RANGE = 500;
    public static final String STF_PLANET_MAP_CAT = "map_loc_cat_n";
    public static final String TARGET_DATATABLE = "datatables/newbie_handoff/destroy_targets.iff";
    public static final String DATATABLE_HOLOCRON_EVENTS = "datatables/holocron/events.iff";
    public static final String VAR_FIND_BASE = "find";
    public static final String VAR_FIND_WAYPOINT = VAR_FIND_BASE + ".waypoint";
    public static final String[] NPC_TYPES = 
    {
        "artisan",
        "bodyguard",
        "businessman",
        "bothan_diplomat",
        "entertainer",
        "explorer",
        "farmer",
        "gambler",
        "info_broker",
        "miner",
        "medic",
        "noble",
        "pilot",
        "scientist"
    };
    public static final string_id SID_NEWBIE_MISSION_DESTROY_START = new string_id("newbie_handoff/messages", "destroy_mission_start");
    public static final string_id SID_NEWBIE_MISSION_DESTROY_COMPLETE = new string_id("newbie_handoff/messages", "destroy_mission_complete");
    public static final string_id PROSE_NEWBIE_MISSION_SURVEY_START = new string_id("newbie_handoff/messages", "survey_mission_start");
    public static final string_id SID_NEWBIE_MISSION_SURVEY_COMPLETE = new string_id("newbie_handoff/messages", "survey_mission_complete");
    public static final string_id SID_NEWBIE_MISSION_GIG_DANCE_START = new string_id("newbie_handoff/messages", "gig_mission_dance_start");
    public static final string_id SID_NEWBIE_MISSION_GIG_MUSIC_START = new string_id("newbie_handoff/messages", "gig_mission_music_start");
    public static final string_id SID_NEWBIE_MISSION_GIG_ARRIVAL = new string_id("newbie_handoff/messages", "gig_mission_arrival");
    public static final string_id SID_NEWBIE_MISSION_GIG_COMPLETE = new string_id("newbie_handoff/messages", "gig_mission_complete");
    public static final string_id PROSE_NEWBIE_MISSION_HEAL_START = new string_id("newbie_handoff/messages", "heal_mission_start");
    public static final string_id PROSE_NEWBIE_MISSION_HEAL_ARRIVAL = new string_id("newbie_handoff/messages", "heal_mission_arrival");
    public static final string_id SID_NEWBIE_MISSION_HEAL_COMPLETE = new string_id("newbie_handoff/messages", "heal_mission_complete");
    public static final string_id SID_NEWBIE_MISSION_HEAL_MENU = new string_id("newbie_handoff/messages", "heal_mission_menu");
    public static final string_id SID_NEWBIE_MISSION_HEAL_USE = new string_id("newbie_handoff/messages", "heal_mission_use");
    public static final string_id SID_NEWBIE_MISSION_HEAL_NEED_TARGET = new string_id("newbie_handoff/messages", "heal_mission_need_target");
    public static final string_id SID_NEWBIE_MISSION_HEAL_WRONG_TARGET = new string_id("newbie_handoff/messages", "heal_mission_wrong_target");
    public static final string_id SID_NEWBIE_MISSION_HARVEST_START = new string_id("newbie_handoff/messages", "harvest_mission_start");
    public static final string_id SID_NEWBIE_MISSION_HARVEST_KILL = new string_id("newbie_handoff/messages", "harvest_mission_target_dead");
    public static final string_id SID_NEWBIE_MISSION_HARVEST_COMPLETE = new string_id("newbie_handoff/messages", "harvest_mission_complete");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isJedi(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!hasObjVar(self, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        String arg;
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        if (st.countTokens() > 0)
        {
            arg = st.nextToken();
        }
        else 
        {
            arg = text;
        }
        if (arg == null || arg.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (arg.equals("findBank"))
        {
            LOG("newbie_handoff", "Atempting to find closest Bank");
            messageTo(self, "tourBank", null, 0, false);
        }
        if (arg.equals("findCantina"))
        {
            LOG("newbie_handoff", "Atempting to find closest Cantina");
            messageTo(self, "tourCantina", null, 0, false);
        }
        if (arg.equals("findHospital"))
        {
            LOG("newbie_handoff", "Atempting to find closest Hospital");
            messageTo(self, "tourHospital", null, 0, false);
        }
        if (arg.equals("findSkillTrainer"))
        {
            LOG("newbie_handoff", "Atempting to find closest Skill Trainer");
            messageTo(self, "tourSkillTrainer", null, 0, false);
        }
        if (arg.equals("holocronProfession"))
        {
            LOG("newbie_handoff", "Opening Holocron to my profession");
            messageTo(self, "openHolocronToProfession", null, 0, false);
        }
        if (arg.equals("missionArtisan"))
        {
            LOG("newbie_handoff", "Creating new Artisan Mission");
            messageTo(self, "missionArtisanSurvey", null, 0, false);
        }
        if (arg.equals("missionDancer"))
        {
            LOG("newbie_handoff", "Creating new Dance Mission");
            messageTo(self, "missionEntertainerDanceGig", null, 0, false);
        }
        if (arg.equals("missionMusician"))
        {
            LOG("newbie_handoff", "Creating new Music Mission");
            messageTo(self, "missionEntertainerMusicGig", null, 0, false);
        }
        if (arg.equals("missionMedic"))
        {
            LOG("newbie_handoff", "Creating new Medic Mission");
            messageTo(self, "missionMedicHeal", null, 0, false);
        }
        if (arg.equals("missionBrawler"))
        {
            LOG("newbie_handoff", "Creating new Brawler Mission");
            messageTo(self, "missionBrawlerDestroy", null, 0, false);
        }
        if (arg.equals("missionMarksman"))
        {
            LOG("newbie_handoff", "Creating new Marksman Mission");
            messageTo(self, "missionMarksmanDestroy", null, 0, false);
        }
        if (arg.equals("missionScout"))
        {
            LOG("newbie_handoff", "Creating new Scout Mission");
            messageTo(self, "missionScoutHarvest", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("tour.bank"))
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Exploring.FindBank.ArrivedBank");
            clearHolocronDatapadEntry(self, name);
        }
        else if (name.equals("tour.cloning_center"))
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Exploring.FindCloning.ArrivedCloning");
            clearHolocronDatapadEntry(self, name);
        }
        else if (name.equals("tour.hospital"))
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Exploring.FindHospital.ArrivedHospital");
            clearHolocronDatapadEntry(self, name);
        }
        else if (name.equals("tour.cantina"))
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Exploring.FindCantina.ArrivedCantina");
            clearHolocronDatapadEntry(self, name);
        }
        else if (name.equals("tour.skill_trainer"))
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Exploring.FindSkillTrainer.ArrivedSkillTrainer");
            clearHolocronDatapadEntry(self, name);
        }
        else if (name.equals("mission.destroy"))
        {
            removeLocationTarget(name);
        }
        else if (name.equals("mission.destroy.spawnGuy"))
        {
            removeLocationTarget(name);
            location spawnLoc = getLocationObjVar(self, "newbie_handoff.mission.destroy.loc");
            String npcToSpawn = getStringObjVar(self, "newbie_handoff.mission.destroy.npc");
            String missionType = getStringObjVar(self, "newbie_handoff.mission.destroy.type");
            if (npcToSpawn == null)
            {
                debugSpeakMsg(self, "Can't find anyone to spawn");
                return SCRIPT_CONTINUE;
            }
            obj_id npc = create.object(npcToSpawn, spawnLoc);
            setObjVar(self, "newbie_handoff.mission.destroy.npc_id", npc);
            pvpSetAlignedFaction(npc, (84709322));
            pvpSetPermanentPersonalEnemyFlag(npc, self);
            setObjVar(npc, "newbie_handoff.mission.type", missionType);
            setObjVar(npc, "newbie_handoff.mission.player", self);
            attachScript(npc, "holocron.destroy_target");
        }
        else if (name.equals("mission.harvest"))
        {
            removeLocationTarget(name);
        }
        else if (name.equals("mission.harvest.spawnGuy"))
        {
            removeLocationTarget(name);
            location spawnLoc = getLocationObjVar(self, "newbie_handoff.mission.harvest.loc");
            String npcToSpawn = getStringObjVar(self, "newbie_handoff.mission.harvest.npc");
            String missionType = getStringObjVar(self, "newbie_handoff.mission.harvest.type");
            if (npcToSpawn == null)
            {
                debugSpeakMsg(self, "Can't find anyone to spawn");
                return SCRIPT_CONTINUE;
            }
            obj_id npc = create.object(npcToSpawn, spawnLoc);
            setObjVar(self, "newbie_handoff.mission.harvest.npc_id", npc);
            pvpSetAlignedFaction(npc, (84709322));
            pvpSetPermanentPersonalEnemyFlag(npc, self);
            setObjVar(npc, "newbie_handoff.mission.type", missionType);
            setObjVar(npc, "newbie_handoff.mission.player", self);
            attachScript(npc, "holocron.destroy_target");
        }
        else if (name.equals("mission.gig"))
        {
            removeLocationTarget(name);
            sendSystemMessage(self, SID_NEWBIE_MISSION_GIG_ARRIVAL);
        }
        else if (name.equals("mission.heal"))
        {
            removeLocationTarget(name);
            obj_id npc = getObjIdObjVar(self, "newbie_handoff.mission.heal.npc_id");
            prose_package pp = prose.getPackage(PROSE_NEWBIE_MISSION_HEAL_ARRIVAL, npc);
            sendSystemMessageProse(self, pp);
        }
        else 
        {
            clearHolocronDatapadEntry(self, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int startHandoff(obj_id self, dictionary params) throws InterruptedException
    {
        playMusic(self, "sound/tut_00_holocron.snd");
        openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin");
        return SCRIPT_CONTINUE;
    }
    public int disableNewbieHandoff(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "newbie_handoff.disabled", 1);
        return SCRIPT_CONTINUE;
    }
    public int enableNewbieHandoff(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "newbie_handoff.disabled");
        return SCRIPT_CONTINUE;
    }
    public int tourBank(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.tour.bank"))
        {
            clearHolocronDatapadEntry(self, "tour.bank");
            messageTo(self, "tourBank", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        map_location destination = planetary_map.findClosestLocation(self, "bank", "");
        location loc = new location(destination.getX(), 0, destination.getY());
        obj_id waypoint = addHolocronDatapadWaypoint(self, "tour.bank", "City Tour: Bank", loc, 20);
        addHolocronWaypointPath(self, waypoint);
        closeHolocron(self);
        return SCRIPT_CONTINUE;
    }
    public int tourCloningCenter(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.tour.cloning_center"))
        {
            clearHolocronDatapadEntry(self, "tour.cloning_center");
            messageTo(self, "tourCloningCenter", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        clearHolocronDatapadEntry(self, "tour.cloning_center");
        map_location destination = planetary_map.findClosestLocation(self, "cloningfacility", "");
        location loc = new location(destination.getX(), 0, destination.getY());
        obj_id waypoint = addHolocronDatapadWaypoint(self, "tour.cloning_center", "City Tour: Cloning Center", loc, 5);
        addHolocronWaypointPath(self, waypoint);
        closeHolocron(self);
        return SCRIPT_CONTINUE;
    }
    public int tourHospital(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.tour.hospital"))
        {
            clearHolocronDatapadEntry(self, "tour.hospital");
            messageTo(self, "tourHospital", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        String[] priSearchList = 
        {
            "hospital",
            "medicalcenter"
        };
        String[] secSearchList = 
        {
            "tavern",
            "barracks"
        };
        location loc = findClosestStructure(self, priSearchList, secSearchList);
        obj_id waypoint = addHolocronDatapadWaypoint(self, "tour.hospital", "City Tour: Hospital", loc, 5);
        addHolocronWaypointPath(self, waypoint);
        closeHolocron(self);
        return SCRIPT_CONTINUE;
    }
    public int tourCantina(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.tour.cantina"))
        {
            clearHolocronDatapadEntry(self, "tour.cantina");
            messageTo(self, "tourCantina", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        String[] priSearchList = 
        {
            "cantina",
            "guild_theater",
            "hotel"
        };
        String[] secSearchList = 
        {
            "tavern",
            "barracks"
        };
        location loc = findClosestStructure(self, priSearchList, secSearchList);
        obj_id waypoint = addHolocronDatapadWaypoint(self, "tour.cantina", "City Tour: Cantina", loc, 5);
        addHolocronWaypointPath(self, waypoint);
        closeHolocron(self);
        return SCRIPT_CONTINUE;
    }
    public int tourSkillTrainer(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.tour.skill_trainer"))
        {
            clearHolocronDatapadEntry(self, "tour.skill_trainer");
            messageTo(self, "tourSkillTrainer", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        String profession = getStartingProfession(self);
        String trainer_type = getTrainerType(profession);
        LOG("newbie_handoff", "Looking for trainer: " + profession + ", " + trainer_type);
        map_location destination = planetary_map.findClosestLocation(self, "trainer", trainer_type);
        if (destination == null)
        {
            return SCRIPT_CONTINUE;
        }
        location loc = new location(destination.getX(), 0, destination.getY());
        obj_id waypoint = addHolocronDatapadWaypoint(self, "tour.skill_trainer", "City Tour: Skill Trainer", loc, 5);
        addHolocronWaypointPath(self, waypoint);
        closeHolocron(self);
        return SCRIPT_CONTINUE;
    }
    public int openHolocronToProfession(obj_id self, dictionary params) throws InterruptedException
    {
        String profession = getStartingProfession(self);
        profession = getSimpleProfession(profession);
        profession = toUpper(profession, 0);
        String page = "WelcomeToSWG.WhereDoIBegin.Professions.Playing" + profession;
        openHolocronToNewbiePage(self, page);
        return SCRIPT_CONTINUE;
    }
    public int missionArtisanSurvey(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.mission.survey"))
        {
            clearHolocronDatapadEntry(self, "mission.survey");
            messageTo(self, "missionArtisanSurvey", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        final int MAX_RESOURCES = 10;
        obj_id[] resource = new obj_id[MAX_RESOURCES];
        float[] efficiency = new float[MAX_RESOURCES];
        obj_id resourceMax = null;
        float efficiencyMax = 0;
        int resourceCount = 0;
        location here = getLocation(self);
        for (int x = 0; x < 50; x++)
        {
            obj_id curResource = pickRandomNonDepeletedResource("mineral");
            float curEfficiency = getResourceEfficiency(curResource, here);
            if ((curEfficiency > 0.4) && (curEfficiency < 0.55))
            {
                resource[resourceCount] = curResource;
                efficiency[resourceCount] = curEfficiency;
                resourceCount++;
            }
            if ((curEfficiency > efficiencyMax) && (curEfficiency < 0.55))
            {
                resourceMax = curResource;
                efficiencyMax = curEfficiency;
            }
            if (resourceCount >= MAX_RESOURCES)
            {
                break;
            }
        }
        int randResource = rand(0, resourceCount - 1);
        obj_id targetResource = resource[randResource];
        float targetEfficiency = efficiency[randResource];
        if (resourceCount == 0)
        {
            randResource = -1;
            targetResource = resourceMax;
            targetEfficiency = efficiencyMax;
        }
        targetEfficiency += (0.7 - targetEfficiency) * 0.15f;
        LOG("newbie_handoff", "Survey Mission Create:  Using Resource #" + randResource + " of " + resourceCount + " resources.");
        LOG("newbie_handoff", "			Target Resource: " + targetResource + "; Target Efficiency: " + targetEfficiency);
        addHolocronDatapadEntry(self, "mission.survey", "Artisan Survey Mission");
        setObjVar(self, "newbie_handoff.mission.survey.type", "artisan");
        setObjVar(self, "newbie_handoff.mission.survey.loc", here);
        setObjVar(self, "newbie_handoff.mission.survey.resource", targetResource);
        setObjVar(self, "newbie_handoff.mission.survey.efficiency", targetEfficiency);
        closeHolocron(self);
        String resourceName = "INSERT NAME HERE";
        String resourceClass = "INSERT CLASS HERE";
        int resourceEfficiency = (int)(targetEfficiency * 100);
        prose_package pp = prose.getPackage(PROSE_NEWBIE_MISSION_SURVEY_START, null, null, null, null, resourceName, null, null, resourceClass, null, resourceEfficiency, 0f);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int missionEntertainerDanceGig(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.mission.gig"))
        {
            clearHolocronDatapadEntry(self, "mission.gig");
            messageTo(self, "missionEntertainerDanceGig", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        map_location destination = planetary_map.findClosestLocation(self, "cantina", "");
        location loc = new location(destination.getX(), 0, destination.getY());
        addHolocronDatapadWaypoint(self, "mission.gig", "Entertainer Dance Mission", loc, 25);
        setObjVar(self, "newbie_handoff.mission.gig.type", "dancer");
        setObjVar(self, "newbie_handoff.mission.gig.loc", loc);
        setObjVar(self, "newbie_handoff.mission.gig.obj", destination.getLocationId());
        setObjVar(self, "newbie_handoff.mission.gig.duration", 120);
        closeHolocron(self);
        sendSystemMessage(self, SID_NEWBIE_MISSION_GIG_DANCE_START);
        return SCRIPT_CONTINUE;
    }
    public int missionEntertainerMusicGig(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.mission.gig"))
        {
            clearHolocronDatapadEntry(self, "mission.gig");
            messageTo(self, "missionEntertainerMusicGig", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        map_location destination = planetary_map.findClosestLocation(self, "cantina", "");
        location loc = new location(destination.getX(), 0, destination.getY());
        addHolocronDatapadWaypoint(self, "mission.gig", "Entertainer Music Mission", loc, 25);
        setObjVar(self, "newbie_handoff.mission.gig.type", "musician");
        setObjVar(self, "newbie_handoff.mission.gig.loc", loc);
        setObjVar(self, "newbie_handoff.mission.gig.obj", destination.getLocationId());
        setObjVar(self, "newbie_handoff.mission.gig.duration", 120);
        closeHolocron(self);
        sendSystemMessage(self, SID_NEWBIE_MISSION_GIG_MUSIC_START);
        return SCRIPT_CONTINUE;
    }
    public int missionMedicHeal(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.mission.heal"))
        {
            clearHolocronDatapadEntry(self, "mission.heal");
            messageTo(self, "missionMedicHeal", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        String planet = getCurrentSceneName();
        region city = locations.getCityRegion(getLocation(self));
        if (city == null || planet == null)
        {
            return SCRIPT_CONTINUE;
        }
        location destination = null;
        int x = 0;
        while (x < 10)
        {
            LOG("newbie_handoff", "Medic Mission Create:  Attepmt #" + (x + 1) + "; Distance=" + (10 + (x * 10)));
            destination = locations.getGoodCityLocation(city, planet);
            if (destination != null)
            {
                break;
            }
            x += 1;
        }
        if (destination == null)
        {
            return SCRIPT_CONTINUE;
        }
        addHolocronDatapadWaypoint(self, "mission.heal", "Medic Heal Mission", destination, 5);
        obj_id npc = create.object(NPC_TYPES[rand(0, NPC_TYPES.length - 1)], destination);
        setObjVar(npc, "newbie_handoff.mission.type", "medic");
        setObjVar(npc, "newbie_handoff.mission.player", self);
        attachScript(npc, "holocron.heal_target");
        obj_id inv = getObjectInSlot(self, "inventory");
        obj_id med = createObject("object/tangible/medicine/newbie_heal_mission_medicine.iff", inv, "");
        setObjVar(med, "newbie_handoff.mission.type", "medic");
        setObjVar(med, "newbie_handoff.mission.player", self);
        setObjVar(self, "newbie_handoff.mission.heal.type", "medic");
        setObjVar(self, "newbie_handoff.mission.heal.loc", destination);
        setObjVar(self, "newbie_handoff.mission.heal.npc_id", npc);
        setObjVar(self, "newbie_handoff.mission.heal.med_id", med);
        closeHolocron(self);
        prose_package pp = prose.getPackage(PROSE_NEWBIE_MISSION_HEAL_START, npc);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int missionBrawlerDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.mission.destroy"))
        {
            clearHolocronDatapadEntry(self, "mission.destroy");
            messageTo(self, "missionBrawlerDestroy", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        region city = locations.getCityRegion(getLocation(self));
        if (city == null)
        {
            return SCRIPT_CONTINUE;
        }
        location destination = null;
        int x = 0;
        while (x < 10)
        {
            LOG("newbie_handoff", "Brawler Mission Create:  Attepmt #" + (x + 1) + "; Distance=" + (10 + (x * 10)));
            destination = locations.getGoodLocationOutsideOfRegion(city, 30f, 30f, 10 + (x * 10), false, true);
            if (destination != null)
            {
                break;
            }
            x += 1;
        }
        if (destination == null)
        {
            return SCRIPT_CONTINUE;
        }
        addHolocronDatapadWaypoint(self, "mission.destroy", "Brawler Destroy Mission", destination, 25);
        addLocationTarget("mission.destroy.spawnGuy", destination, 100);
        setObjVar(self, "newbie_handoff.mission.destroy.type", "brawler");
        setObjVar(self, "newbie_handoff.mission.destroy.loc", destination);
        String npc = determineDestroyTarget(self);
        setObjVar(self, "newbie_handoff.mission.destroy.npc", npc);
        closeHolocron(self);
        sendSystemMessage(self, SID_NEWBIE_MISSION_DESTROY_START);
        return SCRIPT_CONTINUE;
    }
    public int missionMarksmanDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.mission.destroy"))
        {
            clearHolocronDatapadEntry(self, "mission.destroy");
            messageTo(self, "missionMarksmanDestroy", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        region city = locations.getCityRegion(getLocation(self));
        if (city == null)
        {
            return SCRIPT_CONTINUE;
        }
        location destination = null;
        int x = 0;
        while (x < 10)
        {
            LOG("newbie_handoff", "Marksman Mission Create:  Attepmt #" + (x + 1) + "; Distance=" + (10 + (x * 10)));
            destination = locations.getGoodLocationOutsideOfRegion(city, 30f, 30f, 10 + (x * 10), false, true);
            if (destination != null)
            {
                break;
            }
            x += 1;
        }
        if (destination == null)
        {
            return SCRIPT_CONTINUE;
        }
        addHolocronDatapadWaypoint(self, "mission.destroy", "Marksman Destroy Mission", destination, 25);
        addLocationTarget("mission.destroy.spawnGuy", destination, 100);
        setObjVar(self, "newbie_handoff.mission.destroy.type", "marksman");
        setObjVar(self, "newbie_handoff.mission.destroy.loc", destination);
        String npc = determineDestroyTarget(self);
        setObjVar(self, "newbie_handoff.mission.destroy.npc", npc);
        closeHolocron(self);
        sendSystemMessage(self, SID_NEWBIE_MISSION_DESTROY_START);
        return SCRIPT_CONTINUE;
    }
    public int missionScoutHarvest(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie_handoff.mission.harvest"))
        {
            clearHolocronDatapadEntry(self, "mission.harvest");
            messageTo(self, "missionScoutHarvest", null, 1f, false);
            return SCRIPT_CONTINUE;
        }
        region city = locations.getCityRegion(getLocation(self));
        if (city == null)
        {
            return SCRIPT_CONTINUE;
        }
        location destination = null;
        int x = 0;
        while (x < 10)
        {
            LOG("newbie_handoff", "Scout Mission Create:  Attepmt #" + (x + 1) + "; Distance=" + (10 + (x * 10)));
            destination = locations.getGoodLocationOutsideOfRegion(city, 30f, 30f, 10 + (x * 10), false, true);
            if (destination != null)
            {
                break;
            }
            x += 1;
        }
        if (destination == null)
        {
            return SCRIPT_CONTINUE;
        }
        addHolocronDatapadWaypoint(self, "mission.harvest", "Scout Harvest Mission", destination, 25);
        addLocationTarget("mission.harvest.spawnGuy", destination, 100);
        setObjVar(self, "newbie_handoff.mission.harvest.type", "scout");
        setObjVar(self, "newbie_handoff.mission.harvest.loc", destination);
        String npc = determineDestroyTarget(self);
        setObjVar(self, "newbie_handoff.mission.harvest.npc", npc);
        closeHolocron(self);
        sendSystemMessage(self, SID_NEWBIE_MISSION_HARVEST_START);
        return SCRIPT_CONTINUE;
    }
    public void commandOpenCraftingTool(obj_id player) throws InterruptedException
    {
    }
    public void prototypeCompleted() throws InterruptedException
    {
    }
    public int eventEarnedSkillBox(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        dictionary holocronParams = new dictionary();
        holocronParams.put("eventName", "EarnedSkillBox");
        messageTo(self, "handleHolocronEvent", holocronParams, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int eventTrainedSkillBox(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary holocronParams = new dictionary();
        holocronParams.put("eventName", "TrainedSkillBox");
        messageTo(self, "handleHolocronEvent", holocronParams, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleHolocronEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie_handoff.disabled"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isJedi(self))
        {
            return SCRIPT_CONTINUE;
        }
        String event = params.getString("eventName");
        int eventFlag = dataTableGetInt(DATATABLE_HOLOCRON_EVENTS, event, "eventFlag");
        String eventEntry = dataTableGetString(DATATABLE_HOLOCRON_EVENTS, event, "eventEntry");
        if ((eventFlag == -1) || (eventEntry == null) || (eventEntry.equals("")))
        {
            return SCRIPT_CONTINUE;
        }
        int bitFieldNum = (int)eventFlag / 32;
        int bitFlag = eventFlag % 32;
        int bitField = getIntObjVar(self, "newbie_handoff.events.bitField" + bitFieldNum);
        if (!utils.checkBit(bitField, bitFlag))
        {
            bitField = utils.setBit(bitField, bitFlag);
            setObjVar(self, "newbie_handoff.events.bitField" + bitFieldNum, bitField);
            openHolocronToNewbiePage(self, eventEntry);
        }
        return SCRIPT_CONTINUE;
    }
    public int missionSurveyComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Survey Mission Complete");
        LOG("newbie_handoff", "	Clearing Mission Data");
        clearHolocronDatapadEntry(self, "mission.survey");
        sendSystemMessage(self, SID_NEWBIE_MISSION_SURVEY_COMPLETE);
        LOG("newbie_handoff", "Opening Holocron to Artisan Survey Complete Section");
        openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingArtisan.Resources.Survey.Complete");
        return SCRIPT_CONTINUE;
    }
    public int missionGigComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Gig Mission Complete");
        LOG("newbie_handoff", "	Clearing Mission Data");
        clearHolocronDatapadEntry(self, "mission.gig");
        sendSystemMessage(self, SID_NEWBIE_MISSION_GIG_COMPLETE);
        messageTo(self, "openHolocronAfterGigMission", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int missionHealComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Heal Mission Complete");
        LOG("newbie_handoff", "	Clearing Mission Data");
        clearHolocronDatapadEntry(self, "mission.heal");
        sendSystemMessage(self, SID_NEWBIE_MISSION_HEAL_COMPLETE);
        messageTo(self, "openHolocronAfterHealMission", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int missionDestroyComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Destroy Mission Complete");
        LOG("newbie_handoff", "	Clearing Location Targets");
        clearHolocronDatapadEntry(self, "mission.destroy");
        String profession = params.getString("type");
        LOG("newbie_handoff", "	Retreiving destroy mission type: " + profession);
        if (profession.equals("brawler"))
        {
            LOG("newbie_handoff", "	Sending messeage to open Holocron");
            sendSystemMessage(self, SID_NEWBIE_MISSION_DESTROY_COMPLETE);
            messageTo(self, "openHolocronAfterBrawlerMission", null, 5, false);
        }
        else 
        {
            LOG("newbie_handoff", "	Sending messeage to open Holocron");
            sendSystemMessage(self, SID_NEWBIE_MISSION_DESTROY_COMPLETE);
            messageTo(self, "openHolocronAfterMarksmanMission", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int missionHarvestTargetDead(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff_harvest", "Creature Killed: Telling player to harvest.");
        sendSystemMessage(self, SID_NEWBIE_MISSION_HARVEST_KILL);
        return SCRIPT_CONTINUE;
    }
    public int missionHarvestComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Harvest Mission Complete");
        LOG("newbie_handoff", "	Clearing Location Targets");
        clearHolocronDatapadEntry(self, "mission.harvest");
        LOG("newbie_handoff", "	Sending messeage to open Holocron");
        sendSystemMessage(self, SID_NEWBIE_MISSION_HARVEST_COMPLETE);
        messageTo(self, "openHolocronAfterScoutMission", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int openHolocronAfterGigMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Opening Holocron to Entertainer Gig Complete Section");
        openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingEntertainer.Instruments.Gig.Complete");
        return SCRIPT_CONTINUE;
    }
    public int openHolocronAfterHealMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Opening Holocron to Medic Heal Complete Section");
        openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMedic");
        return SCRIPT_CONTINUE;
    }
    public int openHolocronAfterBrawlerMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Opening Holocron to Brawler Destroy Complete Section");
        if (ai_lib.isInCombat(self))
        {
            LOG("newbie_handoff", "	Still in combat, wait 15 seconds");
            messageTo(self, "openHolocronAfterBrawlerMission", null, 15, false);
        }
        else 
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingBrawler.MeleeTactics.Destroy.Complete");
        }
        return SCRIPT_CONTINUE;
    }
    public int openHolocronAfterMarksmanMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Opening Holocron to Marksman Destroy Complete Section");
        if (ai_lib.isInCombat(self))
        {
            LOG("newbie_handoff", "	Still in combat, wait 15 seconds");
            messageTo(self, "openHolocronAfterMarksmanMission", null, 15, false);
        }
        else 
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMarksman.RangedTactics.Destroy.Complete");
        }
        return SCRIPT_CONTINUE;
    }
    public int openHolocronAfterScoutMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newbie_handoff", "Opening Holocron to Scout Harvest Complete Section");
        if (ai_lib.isInCombat(self))
        {
            LOG("newbie_handoff", "	Still in combat, wait 15 seconds");
            messageTo(self, "openHolocronAfterScoutMission", null, 15, false);
        }
        else 
        {
            openHolocronToNewbiePage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout.Harvesting.Harvest.Complete");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnWaypointDestroyed(obj_id self, obj_id waypoint) throws InterruptedException
    {
        LOG("newbie_handoff", "	Trying to remove data for Waypoint: " + waypoint);
        if (hasObjVar(self, "newbie_handoff.waypoint." + waypoint))
        {
            dictionary params = new dictionary();
            params.put("waypoint", waypoint);
            params.put("name", getStringObjVar(self, "newbie_handoff.waypoint." + waypoint));
            params.put("index", -1);
            LOG("newbie_handoff", "	Sending message to remove data");
            messageTo(self, "removeDatapadEntryData", params, 0, false);
        }
        else 
        {
            Vector waypointIds = getResizeableObjIdArrayObjVar(self, "newbie_handoff.waypoint.idList");
            Vector waypointNames = getResizeableStringArrayObjVar(self, "newbie_handoff.waypoint.nameList");
            int idx = findWaypointIndex(waypointIds, waypoint);
            if (idx != -1)
            {
                dictionary params = new dictionary();
                params.put("waypoint", waypoint);
                params.put("name", ((String)waypointNames.get(idx)));
                params.put("index", idx);
                LOG("newbie_handoff", "	Sending message to remove data");
                messageTo(self, "removeDatapadEntryData", params, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int removeDatapadEntryData(obj_id self, dictionary params) throws InterruptedException
    {
        String name = params.getString("name");
        obj_id waypoint = params.getObjId("waypoint");
        int index = params.getInt("index");
        LOG("newbie_handoff", "Destroying Datapad Entry " + name);
        if (name.equals("mission.destroy") || name.equals("mission.harvest") || name.equals("mission.heal"))
        {
            LOG("newbie_handoff", "	Cleaning up spawned stuff");
            obj_id spawnedNpc = getObjIdObjVar(self, name + ".npc_id");
            if (isIdValid(spawnedNpc))
            {
                destroyObject(spawnedNpc);
            }
        }
        if (name.equals("mission.destroy") || name.equals("mission.harvest") || name.equals("mission.heal"))
        {
            LOG("newbie_handoff", "	Removing mission spawner trigger");
            removeLocationTarget(name + ".spawnGuy");
        }
        LOG("newbie_handoff", "	Removing obj_var data");
        if (hasObjVar(self, "newbie_handoff." + name))
        {
            LOG("newbie_handoff", "	Removing " + name + " data.");
            removeObjVar(self, "newbie_handoff." + name);
        }
        if (waypoint != null)
        {
            LOG("newbie_handoff", "	Removing waypoint data.");
            if (index != -1)
            {
                Vector waypointIds = getResizeableObjIdArrayObjVar(self, "newbie_handoff.waypoint.idList");
                Vector waypointNames = getResizeableStringArrayObjVar(self, "newbie_handoff.waypoint.nameList");
                waypointIds = utils.removeElementAt(waypointIds, index);
                waypointNames = utils.removeElementAt(waypointNames, index);
                if (waypointIds.size() > 0 && waypointNames.size() > 0)
                {
                    setObjVar(self, "newbie_handoff.waypoint.idList", waypointIds);
                    setObjVar(self, "newbie_handoff.waypoint.nameList", waypointNames);
                }
                else 
                {
                    removeObjVar(self, "newbie_handoff.waypoint.idList");
                    removeObjVar(self, "newbie_handoff.waypoint.nameList");
                }
            }
            else 
            {
                if (hasObjVar(self, "newbie_handoff.waypoint." + waypoint))
                {
                    removeObjVar(self, "newbie_handoff.waypoint." + waypoint);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id addHolocronDatapadWaypoint(obj_id player, String name, String display, location loc, float radius) throws InterruptedException
    {
        LOG("newbie_handoff", "Creating Waypoint: '" + display + "' in Datapad...");
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointName(waypoint, display);
        addLocationTarget(name, loc, radius);
        setWaypointActive(waypoint, true);
        setObjVar(player, "newbie_handoff." + name + ".waypoint", waypoint);
        Vector waypointIds = new Vector();
        waypointIds.setSize(0);
        Vector waypointNames = new Vector();
        waypointNames.setSize(0);
        if (hasObjVar(player, "newbie_handoff.waypoint.idList"))
        {
            waypointIds = getResizeableObjIdArrayObjVar(player, "newbie_handoff.waypoint.idList");
            waypointNames = getResizeableStringArrayObjVar(player, "newbie_handoff.waypoint.nameList");
        }
        waypointIds = utils.addElement(waypointIds, waypoint);
        waypointNames = utils.addElement(waypointNames, name);
        if (waypointIds.size() > 0 && waypointNames.size() > 0)
        {
            setObjVar(player, "newbie_handoff.waypoint.idList", waypointIds);
            setObjVar(player, "newbie_handoff.waypoint.nameList", waypointNames);
        }
        return waypoint;
    }
    public obj_id addHolocronDatapadEntry(obj_id player, String name, String display) throws InterruptedException
    {
        LOG("newbie_handoff", "Creating Entry: '" + display + "' in Datapad...");
        obj_id datapad = getObjectInSlot(player, "datapad");
        obj_id entry = createObject("object/intangible/holocron/newbie_mission.iff", datapad, "");
        setName(entry, display);
        LOG("newbie_handoff", "	Created entry: " + entry + " in datapad object: " + datapad);
        LOG("newbie_handoff", "	Datapad contains object: " + utils.isNestedWithin(entry, datapad));
        setObjVar(player, "newbie_handoff." + name + ".dataEntry", entry);
        attachScript(entry, "holocron.datapad_entry");
        setObjVar(entry, "newbie_handoff.name", name);
        setObjVar(entry, "newbie_handoff.player", player);
        return entry;
    }
    public void clearHolocronDatapadEntry(obj_id player, String name) throws InterruptedException
    {
        if (hasObjVar(player, "newbie_handoff." + name + ".dataEntry"))
        {
            obj_id dataEntry = getObjIdObjVar(player, "newbie_handoff." + name + ".dataEntry");
            if (isIdValid(dataEntry))
            {
                destroyObject(dataEntry);
            }
            return;
        }
        if (hasObjVar(player, "newbie_handoff." + name + ".waypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player, "newbie_handoff." + name + ".waypoint");
            if (isIdValid(waypoint))
            {
                setWaypointActive(waypoint, false);
                destroyWaypointInDatapad(waypoint, player);
            }
            removeLocationTarget(name);
            return;
        }
    }
    public void addHolocronWaypointPath(obj_id player, obj_id waypoint) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "hasClientPath"))
        {
            destroyClientPath(player);
            obj_id oldWaypoint = utils.getObjIdScriptVar(player, "hasClientPath");
            if (isIdValid(oldWaypoint))
            {
                destroyWaypointInDatapad(oldWaypoint, player);
            }
            utils.removeScriptVar(player, "hasClientPath");
        }
        location here = getLocation(player);
        location there = getWaypointLocation(waypoint);
        region[] hereCity = getRegionsWithGeographicalAtPoint(here, regions.GEO_CITY);
        region[] thereCity = getRegionsWithGeographicalAtPoint(there, regions.GEO_CITY);
        if (hereCity != null && hereCity.length > 0 && thereCity != null && thereCity.length > 0)
        {
            boolean areInSameCity = false;
            for (int a = 0; a < hereCity.length; a++)
            {
                for (int b = 0; b < thereCity.length; b++)
                {
                    if (((hereCity[a].getName())).equals(thereCity[b].getName()))
                    {
                        areInSameCity = true;
                        break;
                    }
                }
                if (areInSameCity)
                {
                    break;
                }
            }
            if (areInSameCity)
            {
                there.y = getHeightAtLocation(there.x, there.z);
                location start = here;
                if (isIdValid(start.cell))
                {
                    start = getBuildingEjectLocation(getTopMostContainer(player));
                }
                if (!createClientPath(player, start, there))
                {
                    sendSystemMessageTestingOnly(player, "The system was unable to create a client path for you.");
                }
                else 
                {
                    if (here.cell != start.cell)
                    {
                        sendSystemMessageTestingOnly(player, "Your find path has been started near the entrance of the structure you are currenly in.");
                    }
                    utils.setScriptVar(player, "hasClientPath", waypoint);
                }
            }
        }
    }
    public int findWaypointIndex(Vector idList, obj_id waypoint) throws InterruptedException
    {
        if (idList != null && idList.size() > 0)
        {
            for (int i = 0; i < idList.size(); i++)
            {
                if (((obj_id)idList.get(i)) == waypoint)
                {
                    return i;
                }
            }
        }
        return -1;
    }
    public void openHolocronToNewbiePage(obj_id player, String page) throws InterruptedException
    {
        if (!hasObjVar(player, "newbie_handoff.disabled"))
        {
            openHolocronToPage(player, page);
        }
    }
    public location findClosestStructure(obj_id player, String[] priSearchList, String[] secSearchList) throws InterruptedException
    {
        float priDist = Float.POSITIVE_INFINITY;
        location priLoc = null;
        float secDist = Float.POSITIVE_INFINITY;
        location secLoc = null;
        for (int i = 0; i < priSearchList.length; i++)
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(priSearchList[i], "_");
            String cat = st.nextToken();
            String sub = "";
            if (st.hasMoreTokens())
            {
                sub = st.nextToken();
            }
            map_location destination = planetary_map.findClosestLocation(player, cat, sub);
            if (destination == null)
            {
                continue;
            }
            location loc = new location(destination.getX(), 0, destination.getY());
            float dist = utils.getDistance2D(getLocation(player), loc);
            if (dist < priDist)
            {
                priDist = dist;
                priLoc = loc;
            }
        }
        if (priDist < MAX_SEARCH_RANGE)
        {
            return priLoc;
        }
        for (int i = 0; i < secSearchList.length; i++)
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(secSearchList[i], "_");
            String cat = st.nextToken();
            String sub = "";
            if (st.hasMoreTokens())
            {
                sub = st.nextToken();
            }
            map_location destination = planetary_map.findClosestLocation(player, cat, sub);
            if (destination == null)
            {
                continue;
            }
            location loc = new location(destination.getX(), 0, destination.getY());
            float dist = utils.getDistance2D(getLocation(player), loc);
            if (dist < secDist)
            {
                secDist = dist;
                secLoc = loc;
            }
        }
        if (secDist < MAX_SEARCH_RANGE)
        {
            return secLoc;
        }
        if (priDist < secDist)
        {
            return priLoc;
        }
        else 
        {
            return secLoc;
        }
    }
    public String getStartingProfession(obj_id player) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(player);
        if (skillList == null)
        {
            return ("");
        }
        if (skillList.length < 1)
        {
            return ("");
        }
        String skillName = null;
        for (int i = 0; i < skillList.length; i++)
        {
            if (skillList[i].endsWith("_novice"))
            {
                skillName = skillList[i];
            }
        }
        if ((skillName == null) || (skillName.equals("")))
        {
            return ("");
        }
        String profession = getSkillProfession(skillName);
        if (profession == null)
        {
            return ("");
        }
        return profession;
    }
    public String getSimpleProfession(String profession) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(profession, "_");
        String tmp = st.nextToken();
        if (st.hasMoreTokens())
        {
            tmp = st.nextToken();
        }
        return tmp;
    }
    public String getTrainerType(String profession) throws InterruptedException
    {
        if (profession == null || profession.equals(""))
        {
            return ("");
        }
        String simpleProfession = getSimpleProfession(profession);
        return ("trainer_" + simpleProfession);
    }
    public String determineDestroyTarget(obj_id player) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        String city = locations.getCityName(getLocation(player));
        LOG("newbie_handoff", "Seraching for destroy mission target at " + planet + ": " + city);
        String target = loadRandomDestroyTarget(planet, city);
        return target;
    }
    public String loadRandomDestroyTarget(String planet, String city) throws InterruptedException
    {
        String[] targets = new String[MAX_DESTROY_TARGETS];
        String[] entries = dataTableGetStringColumn(TARGET_DATATABLE, "strPlanet");
        if (entries == null || entries.length == 0)
        {
            LOG("newbie_handoff", "WARNING: No targets defined for " + planet + ": " + city + ", using default target");
            return dataTableGetString(TARGET_DATATABLE, 0, "strTarget");
        }
        LOG("newbie_handoff", "Reading " + TARGET_DATATABLE + ":" + entries.length + "entries read.");
        int targetCount = 0;
        for (int i = 0; i < entries.length; i++)
        {
            if (entries[i] == null || entries[i].equals(""))
            {
                entries[i] = "nullPlanet";
            }
            if (entries[i].equals(planet))
            {
                if ((dataTableGetString(TARGET_DATATABLE, i, "strCity")).equals(city))
                {
                    targets[targetCount] = dataTableGetString(TARGET_DATATABLE, i, "strTarget");
                    LOG("newbie_handoff", "Possible target found: " + targets[targetCount]);
                    targetCount++;
                    if (targetCount >= MAX_DESTROY_TARGETS)
                    {
                        LOG("newbie_handoff", "WARNING: Datatable contains more targets than MAX_DESTROY_TARGETS(" + MAX_DESTROY_TARGETS + ")");
                        break;
                    }
                }
            }
        }
        if (targetCount == 0)
        {
            LOG("newbie_handoff", "WARNING: No targets defined for " + planet + ": " + city + ", using default target");
            return dataTableGetString(TARGET_DATATABLE, 0, "strTarget");
        }
        return targets[rand(0, targetCount - 1)];
    }
}
