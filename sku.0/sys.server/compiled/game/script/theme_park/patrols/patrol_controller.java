package script.theme_park.patrols;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class patrol_controller extends script.base_script {
    private obj_id[] formationSlots;

    public String faction = "neutral";
    public String strFileName = "datatables/spawning/patrol_spawner.iff";

    private String squadClass = "armored";

    private int patrolLevel = 25;
    // patrolLevelTolerance is the span above the patrol level that members can be.
    // for example, if the patrolLevel is set to 66 and the patrolLevelTolerance is 2, patrol
    // members can be 2 levels above level 66.  Therefore, patrol members
    // will be in the range of 66 and 68.
    private int patrolLevelTolerance = 3;

    public patrol_controller(){}

    public int OnAttach(obj_id self) throws InterruptedException{
        setObjVar(self, "currentSize", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException {
        setObjVar(self, "currentSize", 0);
        return SCRIPT_CONTINUE;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException{
        formationSlots = new obj_id[getIntObjVar(self, "size")];
        setObjVar(self, "formationSlots", formationSlots);
        messageTo(self, "spawnGuardPatrol", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnGuardPatrol(obj_id self, dictionary params) throws InterruptedException{
        String patrolName = getStringObjVar(self, "name");
        int maxSize = getIntObjVar(self, "size");
        int currentSize = getIntObjVar(self, "currentSize");
        location spawnStart = getLocationArrayObjVar(self, "patrolPoints")[0];
        if(hasObjVar(self, "patrolLevel")) patrolLevel = getIntObjVar(self, "patrolLevel");
        if(hasObjVar(self, "patrolLevelTolerance")) patrolLevelTolerance = getIntObjVar(self, "patrolLevelTolerance");
        if(hasObjVar(self, "faction")) faction = getStringObjVar(self, "faction");
        float respawnTime = getFloatObjVar(self, "respawnTime");
        squadClass = getStringObjVar(self, "squadClass");

        setName(self, patrolName);

        formationSlots = getObjIdArrayObjVar(getSelf(), "formationSlots");
        for (int i = currentSize; i < maxSize; i++) {
            spawnPatrolMember(self, i, spawnStart);
        }
        setObjVar(self, "formationSlots", formationSlots);
        messageTo(self, "spawnGuardPatrol", null, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
    private int getOpenFormationPosition(int maxSize){
        for(int i = 1; i < formationSlots.length; i++){
            if(formationSlots[i] == null || formationSlots[i] == obj_id.NULL_ID){
                return i;
            }
        }
        return maxSize - 1;
    }
    private void spawnPatrolMember(obj_id self, int currentSize, location spawnStart) throws InterruptedException{
        obj_id patrolMember;
        String memberTemplate = npcToSpawn(self);
        if(memberTemplate == null){
            setName(self, "BROKEN SPAWNER: Can't get squad member template!");
            return;
        }
        int position = 0;
        if(needsLeader(self)){
            String npc = getLeaderTemplate(self);
            if(npc == null){
                setName(self, "BROKEN SPAWNER: Can't get squad leader template!");
                return;
            }
            patrolMember = spawn(npc, spawnStart.x, spawnStart.y, spawnStart.z, 0.0f, null, null);
            setObjVar(patrolMember, "isLeader", true);
            setObjVar(self, "leader", patrolMember);
            tellSquadAboutNewLeader();

            dictionary params = new dictionary();
            params.put("patrolPoints", getLocationArrayObjVar(self, "patrolPoints"));
            params.put("flipPaths", getBooleanObjVar(self, "flipPaths"));

            messageTo(patrolMember, "startPatrol", params, 60, false);
        }
        else{
            // spawn member
            patrolMember = spawn(memberTemplate, spawnStart.x, spawnStart.y, spawnStart.z, 0.0f, null, null);
            position = getOpenFormationPosition(getIntObjVar(self, "size"));
            ai_lib.followInFormation(patrolMember, getObjIdObjVar(self, "leader"), ai_lib.FORMATION_COLUMN, position);
            setMovementPercent(patrolMember, 1.2f);
            setObjVar(patrolMember, "isLeader", false);
        }

        formationSlots[position] = patrolMember;

        setObjVar(self, "formationSlots", formationSlots);

        setLevel(patrolMember, rand(patrolLevel, patrolLevel + patrolLevelTolerance));

        setObjVar(self, "currentSize", currentSize + 1);
    }
    private boolean needsLeader(obj_id self) throws InterruptedException {
        obj_id leader = getObjIdObjVar(self, "leader");

        return leader == null || leader == obj_id.NULL_ID || isDead(leader) || isIncapacitated(leader) || !isIdValid(leader) || !exists(leader);
    }
    public void patrolMemberDied(obj_id self, dictionary params) throws InterruptedException{
        // drop the size of the patrol by one.
        int currentSize = getIntObjVar(self, "currentSize");
        if(currentSize <= 0) currentSize = 1;
        setObjVar(self, "currentSize", currentSize - 1);
        obj_id deadMember = params.getObjId("self");
        formationSlots = getObjIdArrayObjVar(self, "formationSlots");

        if(params.getBoolean("isLeader")){
            setObjVar(self, "leader", obj_id.NULL_ID);
        }

        for(int i=0; i < formationSlots.length; i++){
            if(formationSlots[i] == deadMember){
                formationSlots[i] = obj_id.NULL_ID;
                break;
            }
        }
        setObjVar(self, "formationSlots", formationSlots);
    }
    private void tellSquadAboutNewLeader() throws InterruptedException{
        for(int i = 1; i < formationSlots.length; i++){
            ai_lib.followInFormation(formationSlots[i], getObjIdObjVar(getSelf(), "leader"), ai_lib.FORMATION_COLUMN, i);
            setMovementPercent(formationSlots[i], 1.2f);
        }
    }
    private String getLeaderTemplate(obj_id self){
        if(!dataTableOpen(strFileName)){
            setName(self, "BROKEN SPAWNER: Can't open datatable to retrieve Squad Leader Template!");
            return null;
        }
        int rowCount = dataTableGetNumRows(strFileName);
        squadClass = getStringObjVar(self, "squadClass");
        String tempSquadClass = squadClass;

        dictionary row;
        for(int i=0; i < rowCount; i++){
            row = dataTableGetRow(strFileName, i);
            if(row.getInt("leader") != 1 || !row.getString("faction").equals(faction)){
                continue;
            }
            if(squadClass.equals("mixed") && rand(1,2) == 2) tempSquadClass = "plain";

            if(row.getString("faction").equals(faction) && row.getInt("leader") == 1 && row.getString("squadClass").equals(tempSquadClass)){
                return row.getString("template");
            }
        }
        return null;
    }
    public String npcToSpawn(obj_id self){
        // get NPC type:
        int roll = rand(1,100);

        if(!dataTableOpen(strFileName)){
            setName(self, "BROKEN SPAWNER: Can't open datatable!");
            return null;
        }
        int rowCount = dataTableGetNumRows(strFileName);
        if (rowCount <= 0)
        {
            setName(self, "BROKEN SPAWNER: Can't get datatable rows!");
            return null;
        }
        dictionary row;
        squadClass = getStringObjVar(self, "squadClass");
        String tempSquadClass = squadClass;

        for(int i=0; i < rowCount; i++){
            row = dataTableGetRow(strFileName, i);
            if(!row.getString("faction").equals(faction)){
                continue;
            }
            if(squadClass.equals("mixed") && rand(1,2) == 2) tempSquadClass = "plain";

            if(row.getString("faction").equals(faction) && row.getString("squadClass").equals(tempSquadClass) && roll >= row.getInt("minSpawnChance") && roll <= row.getInt("maxSpawnChance")){
                return row.getString("template");
            }
        }
        return null;
    }
    public obj_id spawn(String obj, float x, float y, float z, float yaw, obj_id cell, String mood) throws InterruptedException{
        obj_id spawnedObject;
        String area = getLocation(getSelf()).area;

        if(obj.contains(".iff")){
            spawnedObject = createObject(obj, new location(x, y, z, area, cell));
        }
        else{
            spawnedObject = create.object(obj, new location(x, y, z, area, cell));
        }
        obj_id self = getSelf();
        setObjVar(spawnedObject, "spawner", self);
        attachScript(spawnedObject, "theme_park.patrol_member");
        setCreatureStatic(spawnedObject, false);
        setInvulnerable(spawnedObject, getBooleanObjVar(self, "invulnerable"));
        setLevel(spawnedObject, rand(65,69));

        if(yaw > 0){
            setYaw(spawnedObject, yaw);
        }

        ai_lib.setDefaultCalmMood(spawnedObject, mood);

        return spawnedObject;
    }

}
