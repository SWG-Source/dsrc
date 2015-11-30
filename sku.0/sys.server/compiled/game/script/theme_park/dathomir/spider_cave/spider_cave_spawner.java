package script.theme_park.dathomir.spider_cave;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai;
import script.library.ai_lib;
import script.library.create;

public class spider_cave_spawner extends script.base_script
{
    public spider_cave_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnNightsisterElder(self);
        spawnSpiderQueen(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnNightsisterElder(self);
        spawnSpiderQueen(self);
        spawnGiantSpider(self);
        return;
    }
    public void spawnNightsisterElder(obj_id self) throws InterruptedException
    {
        boolean spawnNightsisterElder = true;
        if (hasObjVar(self, "spiderCaveInhabitants.nightsisterElder"))
        {
            obj_id oldNightsisterElder = getObjIdObjVar(self, "spiderCaveInhabitants.nightsisterElder");
            if (oldNightsisterElder.isLoaded())
            {
                spawnNightsisterElder = false;
            }
        }
        if (spawnNightsisterElder)
        {
            obj_id nightsisterElderCell = getCellId(self, "r5");
            location nightsisterElderLocation = new location(-6.61f, -45.02f, -141.92f, "dathomir", nightsisterElderCell);
            obj_id nightsisterElder = create.object("spider_nightsister_elder", nightsisterElderLocation);
            int nightsisterElder_yaw = 36;
            setYaw(nightsisterElder, nightsisterElder_yaw);
            attachScript(nightsisterElder, "theme_park.npc_died.npc_died");
            setObjVar(self, "spiderCaveInhabitants.nightsisterElder", nightsisterElder);
            setObjVar(nightsisterElder, "spawner", self);
            setObjVar(nightsisterElder, "useOnIncapTrigger", self);
        }
        return;
    }
    public void spawnSpiderQueen(obj_id self) throws InterruptedException
    {
        boolean spawnSpiderQueen = true;
        if (hasObjVar(self, "spiderCaveInhabitants.spiderQueen"))
        {
            obj_id oldSpiderQueen = getObjIdObjVar(self, "spiderCaveInhabitants.spiderQueen");
            if (oldSpiderQueen.isLoaded())
            {
                spawnSpiderQueen = false;
            }
        }
        if (spawnSpiderQueen)
        {
            obj_id spiderQueenCell = getCellId(self, "r8");
            location spiderQueenLocation = new location(-75.99f, -99.56f, -159.81f, "dathomir", spiderQueenCell);
            obj_id spiderQueen = create.object("cave_gaping_spider_recluse_queen", spiderQueenLocation);
            int spiderQueen_yaw = -12;
            setYaw(spiderQueen, spiderQueen_yaw);
            attachScript(spiderQueen, "theme_park.npc_died.npc_died");
            setObjVar(self, "spiderCaveInhabitants.spiderQueen", spiderQueen);
            setObjVar(spiderQueen, "spawner", self);
            setObjVar(spiderQueen, "useOnIncapTrigger", self);
        }
        return;
    }
    public void spawnGiantSpider(obj_id self) throws InterruptedException
    {
        boolean spawnGiantSpider = true;
        if (hasObjVar(self, "spiderCaveInhabitants.giantSpider"))
        {
            obj_id oldGiantSpider = getObjIdObjVar(self, "spiderCaveInhabitants.giantSpider");
            if (oldGiantSpider.isLoaded())
            {
                spawnGiantSpider = false;
            }
        }
        if (spawnGiantSpider)
        {
            obj_id giantSpiderCell = getCellId(self, "r11");
            location giantSpiderLocation = new location(-90.83f, -100.78f, -97.38f, "dathomir", giantSpiderCell);
            obj_id giantSpider = create.object("gaping_spider_recluse_giant_kiin_dray", giantSpiderLocation);
            int giantSpider_yaw = 166;
            setYaw(giantSpider, giantSpider_yaw);
            setObjVar(self, "spiderCaveInhabitants.giantSpider", giantSpider);
        }
        return;
    }
    public int npcDied(obj_id self, dictionary params) throws InterruptedException
    {
        String npc_type = params.getString("npc_type");
        if (npc_type.equals("cave_gaping_spider_recluse_queen"))
        {
            spawnGiantSpider(self);
            messageTo(self, "spawnSpiderQueen", null, rand(600, 1200), false);
        }
        if (npc_type.equals("spider_nightsister_elder"))
        {
            messageTo(self, "spawnNightsisterElder", null, rand(1200, 1800), false);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnSpiderQueen(obj_id self, dictionary params) throws InterruptedException
    {
        spawnSpiderQueen(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnNightsisterElder(obj_id self, dictionary params) throws InterruptedException
    {
        spawnNightsisterElder(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm_testing"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("Spawn All"))
        {
            spawnEveryone(self);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("Kill All"))
        {
            killEveryone(self);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("Elder"))
        {
            spawnNightsisterElder(self);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("kill Elder"))
        {
            killNightsisterElder(self);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("Spider Queen"))
        {
            spawnSpiderQueen(self);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("kill Spider Queen"))
        {
            killSpiderQueen(self);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("Giant Spider"))
        {
            spawnGiantSpider(self);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("kill Giant Spider"))
        {
            killGiantSpider(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void killEveryone(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "spiderCaveInhabitants.nightsisterElder"))
        {
            obj_id nightsisterElder = getObjIdObjVar(self, "spiderCaveInhabitants.nightsisterElder");
            detachScript(nightsisterElder, "theme_park.npc_died.npc_died");
            destroyObject(nightsisterElder);
            removeObjVar(self, "spiderCaveInhabitants.nightsisterElder");
        }
        if (hasObjVar(self, "spiderCaveInhabitants.spiderQueen"))
        {
            obj_id spiderQueen = getObjIdObjVar(self, "spiderCaveInhabitants.spiderQueen");
            detachScript(spiderQueen, "theme_park.npc_died.npc_died");
            destroyObject(spiderQueen);
            removeObjVar(self, "spiderCaveInhabitants.spiderQueen");
        }
        if (hasObjVar(self, "spiderCaveInhabitants.giantSpider"))
        {
            obj_id giantSpider = getObjIdObjVar(self, "spiderCaveInhabitants.giantSpider");
            detachScript(giantSpider, "theme_park.npc_died.npc_died");
            destroyObject(giantSpider);
            removeObjVar(self, "spiderCaveInhabitants.giantSpider");
        }
        return;
    }
    public void killNightsisterElder(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "spiderCaveInhabitants.nightsisterElder"))
        {
            obj_id nightsisterElder = getObjIdObjVar(self, "spiderCaveInhabitants.nightsisterElder");
            detachScript(nightsisterElder, "theme_park.npc_died.npc_died");
            destroyObject(nightsisterElder);
            removeObjVar(self, "spiderCaveInhabitants.nightsisterElder");
        }
        return;
    }
    public void killSpiderQueen(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "spiderCaveInhabitants.spiderQueen"))
        {
            obj_id spiderQueen = getObjIdObjVar(self, "spiderCaveInhabitants.spiderQueen");
            detachScript(spiderQueen, "theme_park.npc_died.npc_died");
            destroyObject(spiderQueen);
            removeObjVar(self, "spiderCaveInhabitants.spiderQueen");
        }
        return;
    }
    public void killGiantSpider(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "spiderCaveInhabitants.giantSpider"))
        {
            obj_id giantSpider = getObjIdObjVar(self, "spiderCaveInhabitants.giantSpider");
            detachScript(giantSpider, "theme_park.npc_died.npc_died");
            destroyObject(giantSpider);
            removeObjVar(self, "spiderCaveInhabitants.giantSpider");
        }
        return;
    }
}
