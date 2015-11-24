package script.theme_park.endor.marauder_base;

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

public class marauderbase_spawner extends script.base_script
{
    public marauderbase_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnCelebs(self);
        spawnGuards(self);
        return;
    }
    public void spawnCelebs(obj_id self) throws InterruptedException
    {
        spawnKingTerak(self);
        spawnCharal(self);
        spawnFightmasterJorak(self);
        spawnScholarSzingo(self);
        return;
    }
    public void spawnGuards(obj_id self) throws InterruptedException
    {
        return;
    }
    public void spawnKingTerak(obj_id self) throws InterruptedException
    {
        location terakLocation = new location(-4570.36f, 99.0f, -2272.94f, "endor", obj_id.NULL_ID);
        obj_id terak = create.staticObject("king_terak", terakLocation);
        int terak_yaw = -90;
        setYaw(terak, terak_yaw);
        setInvulnerable(terak, true);
        setObjVar(self, "MarauderBaseInhabitants.terak", terak);
        setObjVar(terak, "Retreat", self);
        return;
    }
    public void spawnCharal(obj_id self) throws InterruptedException
    {
        location charalLocation = new location(-4563.97f, 99.0f, -2297.43f, "endor", obj_id.NULL_ID);
        obj_id charal = create.staticObject("charal", charalLocation);
        int charal_yaw = 0;
        setYaw(charal, charal_yaw);
        setInvulnerable(charal, true);
        setObjVar(self, "MarauderBaseInhabitants.charal", charal);
        setObjVar(charal, "Retreat", self);
        return;
    }
    public void spawnFightmasterJorak(obj_id self) throws InterruptedException
    {
        location jorakLocation = new location(-4581.55f, 99.0f, -2243.14f, "endor", obj_id.NULL_ID);
        obj_id jorak = create.staticObject("fightmaster_jorak", jorakLocation);
        int jorak_yaw = 177;
        setYaw(jorak, jorak_yaw);
        setInvulnerable(jorak, true);
        setObjVar(self, "MarauderBaseInhabitants.jorak", jorak);
        setObjVar(jorak, "Retreat", self);
        return;
    }
    public void spawnScholarSzingo(obj_id self) throws InterruptedException
    {
        location szingoLocation = new location(-4618.0f, 90.0f, -2295.16f, "endor", obj_id.NULL_ID);
        obj_id szingo = create.staticObject("scholar_szingo", szingoLocation);
        int szingo_yaw = -55;
        setYaw(szingo, szingo_yaw);
        setInvulnerable(szingo, true);
        setObjVar(self, "MarauderBaseInhabitants.szingo", szingo);
        setObjVar(szingo, "Retreat", self);
        return;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!hasObjVar(speaker, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("celebs"))
        {
            spawnCelebs(self);
        }
        if (text.equals("kill celebs"))
        {
            killCelebs(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void killCelebs(obj_id self) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "MarauderBaseInhabitants.terak"));
        removeObjVar(self, "MarauderBaseInhabitants.terak");
        destroyObject(getObjIdObjVar(self, "MarauderBaseInhabitants.charal"));
        removeObjVar(self, "MarauderBaseInhabitants.charal");
        destroyObject(getObjIdObjVar(self, "MarauderBaseInhabitants.jorak"));
        removeObjVar(self, "MarauderBaseInhabitants.jorak");
        destroyObject(getObjIdObjVar(self, "MarauderBaseInhabitants.szingo"));
        removeObjVar(self, "MarauderBaseInhabitants.szingo");
        return;
    }
}
