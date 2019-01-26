package script.theme_park.newbie_tutorial;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.factions;
import script.location;
import script.obj_id;

public class newbie_hall extends script.theme_park.newbie_tutorial.tutorial_base
{
    public newbie_hall()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkForDestroy", null, 300.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int checkForDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (!containsPlayer(self))
        {
            destroyObject(self);
        }
        messageTo(self, "checkForDestroy", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int handleHallSetup(obj_id self, dictionary params) throws InterruptedException
    {
        int setupStep = getIntObjVar(self, "newbie.hallSetupStep");
        setupStep++;
        switch (setupStep)
        {
            case 1:
            spawnGreeters(self);
            spawnTroopers(self);
            break;
            case 2:
            spawnRoomTwoCrate(self);
            spawnCommoners(self);
            break;
            case 3:
            spawnBankTerminal(self);
            break;
            case 4:
            spawnBanker(self);
            break;
            case 5:
            spawnCloneNpc(self);
            break;
            case 6:
            spawnCloneTerminal(self);
            break;
            case 7:
            spawnInsuranceTerminal(self);
            spawnInsuranceUser(self);
            break;
            case 8:
            spawnMouseDroid(self);
            break;
            case 9:
            spawnCombatExplainer(self);
            spawnPanicGuy(self);
            break;
            case 10:
            spawnPirate(self);
            spawnDebris(self);
            break;
            case 11:
            spawnMissionTerminal(self);
            spawnMissionNpc(self);
            break;
            case 12:
            spawnTravelTerminal(self);
            break;
            case 13:
            spawnTravelNpc(self);
            break;
            case 14:
            spawnPilotNpc(self);
            break;
            default:
            removeObjVar(self, "newbie.hallSetupStep");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.hallSetupStep", setupStep);
        messageTo(self, "handleHallSetup", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id room1 = getCellId(self, "r1");
        obj_id yourCell = getContainedBy(item);
        if (room1 == yourCell)
        {
            if (!hasScript(item, NEWBIE_SCRIPT))
            {
                attachScript(item, NEWBIE_SCRIPT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnGreeters(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, GREETER))
        {
            obj_id oldGreeter = getObjIdObjVar(bldg, GREETER);
            destroyObject(oldGreeter);
        }
        if (hasObjVar(bldg, ROOM1_GREETER))
        {
            obj_id oldGreeter = getObjIdObjVar(bldg, ROOM1_GREETER);
            destroyObject(oldGreeter);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = GREETER_LOCATION_X;
        spawnLoc.y = GREETER_LOCATION_Y;
        spawnLoc.z = GREETER_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, GREETER_LOCATION_CELL);
        obj_id greeter = create.object(GREETER_TYPE, spawnLoc);
        removeObjVar(greeter, "ai.diction");
        ai_lib.setDefaultCalmBehavior(greeter, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(greeter, true);
        setCondition(greeter, CONDITION_CONVERSABLE);
        setName(greeter, "");
        setName(greeter, IMPERIAL_NAME);
        setYaw(greeter, -89.0f);
        attachScript(greeter, GREETER_SCRIPT);
        setObjVar(bldg, GREETER, greeter);
        spawnLoc.x = ROOM1_GREETER_LOC_X;
        spawnLoc.y = ROOM1_GREETER_LOC_Y;
        spawnLoc.z = ROOM1_GREETER_LOC_Z;
        spawnLoc.cell = getCellId(bldg, ROOM1_GREETER_LOC_CELL);
        greeter = create.object(ROOM1_GREETER_TYPE, spawnLoc);
        removeObjVar(greeter, "ai.diction");
        ai_lib.setDefaultCalmBehavior(greeter, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(greeter, true);
        setName(greeter, "");
        setName(greeter, IMPERIAL_NAME);
        attachScript(greeter, ROOM1_GREETER_SCRIPT);
        setObjVar(bldg, ROOM1_GREETER, greeter);
    }
    public void spawnRoomTwoCrate(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, CRATE))
        {
            obj_id oldBox = getObjIdObjVar(bldg, CRATE);
            destroyObject(oldBox);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = CRATE_LOCATION_X;
        spawnLoc.y = CRATE_LOCATION_Y;
        spawnLoc.z = CRATE_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, CRATE_LOCATION_CELL);
        obj_id box = create.object(CRATE_TEMPLATE, spawnLoc);
        attachScript(box, CRATE_SCRIPT);
        setObjVar(bldg, CRATE, box);
    }
    public void spawnBazaarTerminal(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, BAZAAR))
        {
            obj_id oldTerminal = getObjIdObjVar(bldg, BAZAAR);
            destroyObject(oldTerminal);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = BAZAAR_LOCATION_X;
        spawnLoc.y = BAZAAR_LOCATION_Y;
        spawnLoc.z = BAZAAR_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, BAZAAR_LOCATION_CELL);
        obj_id terminal = create.object(BAZAAR_TEMPLATE, spawnLoc);
        attachScript(terminal, BAZAAR_SCRIPT);
        setYaw(terminal, BAZAAR_YAW);
        setObjVar(bldg, BAZAAR, terminal);
    }
    public void spawnBankTerminal(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, BANK))
        {
            obj_id oldTerminal = getObjIdObjVar(bldg, BANK);
            destroyObject(oldTerminal);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = BANK_LOCATION_X;
        spawnLoc.y = BANK_LOCATION_Y;
        spawnLoc.z = BANK_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, BANK_LOCATION_CELL);
        obj_id terminal = create.object(BANK_TEMPLATE, spawnLoc);
        attachScript(terminal, BANK_SCRIPT);
        setYaw(terminal, BANK_YAW);
        setObjVar(bldg, BANK, terminal);
    }
    public void spawnBanker(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, BANKER))
        {
            obj_id oldbanker = getObjIdObjVar(bldg, BANKER);
            destroyObject(oldbanker);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = BANKER_LOCATION_X;
        spawnLoc.y = BANKER_LOCATION_Y;
        spawnLoc.z = BANKER_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, BANKER_LOCATION_CELL);
        obj_id banker = create.object(BANKER_TYPE, spawnLoc);
        removeObjVar(banker, "ai.diction");
        ai_lib.setDefaultCalmBehavior(banker, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(banker, true);
        attachScript(banker, BANKER_SCRIPT);
        setCondition(banker, CONDITION_CONVERSABLE);
        setObjVar(bldg, BANKER, banker);
        setName(banker, "");
        setName(banker, IMPERIAL_NAME);
        setCondition(banker, CONDITION_SPACE_INTERESTING);
        if (hasObjVar(bldg, BANK_USER1))
        {
            obj_id oldbanker = getObjIdObjVar(bldg, BANK_USER1);
            destroyObject(oldbanker);
        }
        spawnLoc.x = BANK_USER1_LOCATION_X;
        spawnLoc.y = BANK_USER1_LOCATION_Y;
        spawnLoc.z = BANK_USER1_LOCATION_Z;
        banker = create.object(COMMONER_TYPE, spawnLoc);
        removeObjVar(banker, "ai.diction");
        setYaw(banker, 0.0f);
        setInvulnerable(banker, true);
        attachScript(banker, BANK_USER1_SCRIPT);
        setObjVar(bldg, BANK_USER1, banker);
        if (hasObjVar(bldg, BANK_USER2))
        {
            obj_id oldbanker = getObjIdObjVar(bldg, BANK_USER2);
            destroyObject(oldbanker);
        }
        spawnLoc.x = BANK_USER2_LOCATION_X;
        spawnLoc.y = BANK_USER2_LOCATION_Y;
        spawnLoc.z = BANK_USER2_LOCATION_Z;
        banker = create.object(COMMONER_TYPE, spawnLoc);
        removeObjVar(banker, "ai.diction");
        setYaw(banker, 0.0f);
        setInvulnerable(banker, true);
        attachScript(banker, BANK_USER1_SCRIPT);
        setObjVar(bldg, BANK_USER2, banker);
    }
    public void spawnTroopers(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, TROOPER1))
        {
            obj_id oldTrooper = getObjIdObjVar(bldg, TROOPER1);
            destroyObject(oldTrooper);
        }
        if (hasObjVar(bldg, TROOPER2))
        {
            obj_id oldTrooper = getObjIdObjVar(bldg, TROOPER2);
            destroyObject(oldTrooper);
        }
        if (hasObjVar(bldg, TROOPER3))
        {
            obj_id oldTrooper = getObjIdObjVar(bldg, TROOPER3);
            destroyObject(oldTrooper);
        }
        if (hasObjVar(bldg, TROOPER4))
        {
            obj_id oldTrooper = getObjIdObjVar(bldg, TROOPER4);
            destroyObject(oldTrooper);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = TROOPER1_LOCATION_X;
        spawnLoc.y = TROOPER1_LOCATION_Y;
        spawnLoc.z = TROOPER1_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, TROOPER1_LOCATION_CELL);
        obj_id trooper = create.object(TROOPER_TYPE, spawnLoc);
        removeObjVar(trooper, "ai.diction");
        ai_lib.setDefaultCalmBehavior(trooper, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(trooper, true);
        attachScript(trooper, TROOPER1_SCRIPT);
        setObjVar(bldg, TROOPER1, trooper);
        spawnLoc.x = TROOPER2_LOCATION_X;
        spawnLoc.y = TROOPER2_LOCATION_Y;
        spawnLoc.z = TROOPER2_LOCATION_Z;
        obj_id trooper2 = create.object(TROOPER_TYPE, spawnLoc);
        removeObjVar(trooper2, "ai.diction");
        ai_lib.setDefaultCalmBehavior(trooper2, ai_lib.BEHAVIOR_SENTINEL);
        faceToBehavior(trooper2, trooper);
        setInvulnerable(trooper2, true);
        attachScript(trooper2, TROOPER2_SCRIPT);
        setObjVar(bldg, TROOPER2, trooper2);
        spawnLoc.x = TROOPER3_LOCATION_X;
        spawnLoc.y = TROOPER3_LOCATION_Y;
        spawnLoc.z = TROOPER3_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, TROOPER3_LOCATION_CELL);
        obj_id trooper3 = create.object(TROOPER_TYPE, spawnLoc);
        removeObjVar(trooper3, "ai.diction");
        ai_lib.setDefaultCalmBehavior(trooper3, ai_lib.BEHAVIOR_SENTINEL);
        faceToBehavior(trooper3, trooper);
        setInvulnerable(trooper3, true);
        attachScript(trooper3, TROOPER3_SCRIPT);
        setObjVar(bldg, TROOPER3, trooper3);
        spawnLoc.x = TROOPER4_LOCATION_X;
        spawnLoc.y = TROOPER4_LOCATION_Y;
        spawnLoc.z = TROOPER4_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, TROOPER4_LOCATION_CELL);
        obj_id trooper4 = create.object(TROOPER_TYPE, spawnLoc);
        removeObjVar(trooper4, "ai.diction");
        ai_lib.setDefaultCalmBehavior(trooper4, ai_lib.BEHAVIOR_SENTINEL);
        faceToBehavior(trooper4, trooper);
        setInvulnerable(trooper4, true);
        attachScript(trooper4, TROOPER4_SCRIPT);
        setObjVar(bldg, TROOPER4, trooper4);
    }
    public void spawnCommoners(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, COMMONER1))
        {
            obj_id oldnpc = getObjIdObjVar(bldg, COMMONER1);
            destroyObject(oldnpc);
        }
        if (hasObjVar(bldg, COMMONER2))
        {
            obj_id oldnpc = getObjIdObjVar(bldg, COMMONER2);
            destroyObject(oldnpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = COMMONER1_LOCATION_X;
        spawnLoc.y = COMMONER1_LOCATION_Y;
        spawnLoc.z = COMMONER1_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, COMMONER_LOCATION_CELL);
        obj_id commoner = create.object(COMMONER_TYPE, spawnLoc);
        removeObjVar(commoner, "ai.diction");
        ai_lib.setDefaultCalmBehavior(commoner, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(commoner, true);
        factions.setFaction(commoner, "commoner", false);
        attachScript(commoner, COMMONER1_SCRIPT);
        setObjVar(bldg, COMMONER1, commoner);
        spawnLoc.x = COMMONER2_LOCATION_X;
        spawnLoc.y = COMMONER2_LOCATION_Y;
        spawnLoc.z = COMMONER2_LOCATION_Z;
        obj_id commoner2 = create.object(COMMONER_TYPE, spawnLoc);
        removeObjVar(commoner2, "ai.diction");
        ai_lib.setDefaultCalmBehavior(commoner2, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(commoner2, true);
        factions.setFaction(commoner2, "commoner", false);
        attachScript(commoner2, COMMONER2_SCRIPT);
        setObjVar(bldg, COMMONER2, commoner2);
        faceToBehavior(commoner, commoner2);
        faceToBehavior(commoner2, commoner);
    }
    public void spawnCloneTerminal(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, CLONE_TERMINAL))
        {
            obj_id oldTerminal = getObjIdObjVar(bldg, CLONE_TERMINAL);
            destroyObject(oldTerminal);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = CLONE_TERMINAL_LOCATION_X;
        spawnLoc.y = CLONE_TERMINAL_LOCATION_Y;
        spawnLoc.z = CLONE_TERMINAL_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, CLONE_TERMINAL_LOCATION_CELL);
        obj_id terminal = create.object(CLONE_TERMINAL_TEMPLATE, spawnLoc);
        setYaw(terminal, CLONE_TERMINAL_YAW);
        setObjVar(bldg, CLONE_TERMINAL, terminal);
        detachScript(terminal, "terminal.cloning");
        attachScript(terminal, CLONE_TERMINAL_SCRIPT);
    }
    public void spawnInsuranceTerminal(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, INSURANCE))
        {
            obj_id oldTerminal = getObjIdObjVar(bldg, INSURANCE);
            destroyObject(oldTerminal);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = INSURANCE_LOCATION_X;
        spawnLoc.y = INSURANCE_LOCATION_Y;
        spawnLoc.z = INSURANCE_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, INSURANCE_LOCATION_CELL);
        obj_id terminal = create.object(INSURANCE_TEMPLATE, spawnLoc);
        setYaw(terminal, INSURANCE_YAW);
        setObjVar(bldg, INSURANCE, terminal);
        detachScript(terminal, "terminal.insurance");
        attachScript(terminal, INSURANCE_SCRIPT);
    }
    public void spawnInsuranceUser(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, INS_USER))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, INS_USER);
            destroyObject(oldNpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = INS_USER_LOC_X;
        spawnLoc.y = INS_USER_LOC_Y;
        spawnLoc.z = INS_USER_LOC_Z;
        spawnLoc.cell = getCellId(bldg, INS_USER_LOC_CELL);
        obj_id insUser = create.object(INS_USER_TYPE, spawnLoc);
        removeObjVar(insUser, "ai.diction");
        ai_lib.setDefaultCalmBehavior(insUser, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(insUser, true);
        factions.setFaction(insUser, "commoner", false);
        attachScript(insUser, INS_USER_SCRIPT);
        setObjVar(bldg, INS_USER, insUser);
    }
    public void spawnCloneNpc(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, CLONE_NPC))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, CLONE_NPC);
            destroyObject(oldNpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = CLONE_NPC_LOCATION_X;
        spawnLoc.y = CLONE_NPC_LOCATION_Y;
        spawnLoc.z = CLONE_NPC_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, CLONE_NPC_LOCATION_CELL);
        obj_id cloneNpc = create.object(CLONE_NPC_TYPE, spawnLoc);
        removeObjVar(cloneNpc, "ai.diction");
        ai_lib.setDefaultCalmBehavior(cloneNpc, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(cloneNpc, true);
        factions.setFaction(cloneNpc, "commoner", false);
        setCondition(cloneNpc, CONDITION_CONVERSABLE);
        attachScript(cloneNpc, CLONE_NPC_SCRIPT);
        setObjVar(bldg, CLONE_NPC, cloneNpc);
        setName(cloneNpc, "");
        setName(cloneNpc, DROID_NAME);
        setCondition(cloneNpc, CONDITION_SPACE_INTERESTING);
    }
    public void spawnMouseDroid(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, MOUSE_DROID))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, MOUSE_DROID);
            destroyObject(oldNpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = MOUSE_DROID_LOC_X;
        spawnLoc.y = MOUSE_DROID_LOC_Y;
        spawnLoc.z = MOUSE_DROID_LOC_Z;
        spawnLoc.cell = getCellId(bldg, MOUSE_DROID_LOC_CELL);
        obj_id mouseDroid = create.object(MOUSE_DROID_TYPE, spawnLoc);
        ai_lib.setDefaultCalmBehavior(mouseDroid, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(mouseDroid, true);
        factions.setFaction(mouseDroid, "commoner", false);
        attachScript(mouseDroid, MOUSE_DROID_SCRIPT);
        setObjVar(bldg, MOUSE_DROID, mouseDroid);
        if (hasObjVar(bldg, MOUSE_DROID2))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, MOUSE_DROID2);
            destroyObject(oldNpc);
        }
        spawnLoc.x = MOUSE_DROID2_LOC_X;
        spawnLoc.y = MOUSE_DROID2_LOC_Y;
        spawnLoc.z = MOUSE_DROID2_LOC_Z;
        spawnLoc.cell = getCellId(bldg, MOUSE_DROID2_LOC_CELL);
        mouseDroid = create.object(MOUSE_DROID_TYPE, spawnLoc);
        ai_lib.setDefaultCalmBehavior(mouseDroid, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(mouseDroid, true);
        factions.setFaction(mouseDroid, "commoner", false);
        attachScript(mouseDroid, MOUSE_DROID2_SCRIPT);
        setObjVar(bldg, MOUSE_DROID2, mouseDroid);
    }
    public void spawnCombatExplainer(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, COMBAT_EXPLAINER))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, COMBAT_EXPLAINER);
            destroyObject(oldNpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = COMBAT_EXPLAINER_X;
        spawnLoc.y = COMBAT_EXPLAINER_Y;
        spawnLoc.z = COMBAT_EXPLAINER_Z;
        spawnLoc.cell = getCellId(bldg, COMBAT_EXPLAINER_CELL);
        obj_id newNpc = create.object(COMBAT_EXPLAINER_TYPE, spawnLoc);
        removeObjVar(newNpc, "ai.diction");
        ai_lib.setDefaultCalmBehavior(newNpc, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(newNpc, true);
        setCondition(newNpc, CONDITION_CONVERSABLE);
        attachScript(newNpc, COMBAT_EXPLAINER_SCRIPT);
        factions.setFaction(newNpc, "commoner", false);
        setObjVar(bldg, COMBAT_EXPLAINER, newNpc);
        setYaw(newNpc, COMBAT_EXPLAINER_YAW);
        setName(newNpc, "");
        setName(newNpc, IMPERIAL_NAME);
        setInvulnerable(newNpc, true);
        setCondition(newNpc, CONDITION_SPACE_INTERESTING);
    }
    public void spawnDebris(obj_id bldg) throws InterruptedException
    {
        obj_id player = getPlayer(bldg);
        if (hasObjVar(player, "newbie.killedPirate"))
        {
            return;
        }
        location spawnLoc = getLocation(bldg);
        spawnLoc.cell = getCellId(bldg, DEBRIS_CELL);
        if (hasObjVar(bldg, DEBRIS))
        {
            obj_id oldBox = getObjIdObjVar(bldg, DEBRIS);
            destroyObject(oldBox);
        }
        spawnLoc.x = DEBRIS_X;
        spawnLoc.y = DEBRIS_Y;
        spawnLoc.z = DEBRIS_Z;
        obj_id newBox = createObject(DEBRIS_TEMPLATE, spawnLoc);
        setYaw(newBox, DEBRIS_YAW);
        setObjVar(bldg, DEBRIS, newBox);
    }
    public void spawnPanicGuy(obj_id bldg) throws InterruptedException
    {
        obj_id player = getPlayer(bldg);
        if (hasObjVar(player, "newbie.killedPirate"))
        {
            return;
        }
        location spawnLoc = new location(getLocation(bldg));
        if (!hasObjVar(player, "newbie.panicInitiated"))
        {
            if (hasObjVar(bldg, PANIC_GUY))
            {
                obj_id oldNpc = getObjIdObjVar(bldg, PANIC_GUY);
                destroyObject(oldNpc);
            }
            spawnLoc.x = PANIC_GUY_X;
            spawnLoc.y = PANIC_GUY_Y;
            spawnLoc.z = PANIC_GUY_Z;
            spawnLoc.cell = getCellId(bldg, PANIC_GUY_CELL);
            obj_id newNpc = create.object(PANIC_GUY_TYPE, spawnLoc);
            removeObjVar(newNpc, "ai.diction");
            ai_lib.setDefaultCalmBehavior(newNpc, ai_lib.BEHAVIOR_SENTINEL);
            setInvulnerable(newNpc, true);
            attachScript(newNpc, PANIC_GUY_SCRIPT);
            factions.setFaction(newNpc, "commoner", false);
            setObjVar(bldg, PANIC_GUY, newNpc);
        }
        spawnLoc.cell = getCellId(bldg, NERVOUS_GUY_CELL);
        for (int i = 0; i < NERVOUS_GUY_X.length; i++)
        {
            spawnLoc.x = NERVOUS_GUY_X[i];
            spawnLoc.y = NERVOUS_GUY_Y[i];
            spawnLoc.z = NERVOUS_GUY_Z[i];
            obj_id newNpc = create.object(NERVOUS_GUY_TYPE, spawnLoc);
            setYaw(newNpc, NERVOUS_GUY_YAW[i]);
            attachScript(newNpc, NERVOUS_GUY_SCRIPT);
            ai_lib.setDefaultCalmMood(newNpc, "nervous");
            factions.setFaction(newNpc, "commoner", false);
            removeObjVar(newNpc, "ai.diction");
            ai_lib.setDefaultCalmBehavior(newNpc, ai_lib.BEHAVIOR_SENTINEL);
            setInvulnerable(newNpc, true);
        }
    }
    public void spawnPirate(obj_id bldg) throws InterruptedException
    {
        obj_id player = getPlayer(bldg);
        if (hasObjVar(player, "newbie.killedPirate"))
        {
            return;
        }
        if (hasObjVar(bldg, PIRATE))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, PIRATE);
            destroyObject(oldNpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = PIRATE_LOCATION_X;
        spawnLoc.y = PIRATE_LOCATION_Y;
        spawnLoc.z = PIRATE_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, PIRATE_LOCATION_CELL);
        obj_id newNpc = create.createNpc("hutt_outcast", PIRATE_TEMPLATE, spawnLoc);
        removeObjVar(newNpc, "ai.diction");
        removeObjVar(newNpc, "faction");
        setObjVar(newNpc, "ai.rangedOnly", true);
        obj_id weapon = createObject(PIRATE_WEAPON, newNpc, "");
        obj_id pirateInv = getObjectInSlot(newNpc, "inventory");
        putIn(weapon, pirateInv);
        ai_lib.setDefaultCalmBehavior(newNpc, ai_lib.BEHAVIOR_LOITER);
        attachScript(newNpc, PIRATE_SCRIPT);
        setObjVar(bldg, PIRATE, newNpc);
    }
    public void spawnMissionTerminal(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, MISSION))
        {
            obj_id oldTerminal = getObjIdObjVar(bldg, MISSION);
            destroyObject(oldTerminal);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = MISSION_LOCATION_X;
        spawnLoc.y = MISSION_LOCATION_Y;
        spawnLoc.z = MISSION_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, MISSION_LOCATION_CELL);
        obj_id terminal = create.object(MISSION_TEMPLATE, spawnLoc);
        setYaw(terminal, MISSION_YAW);
        setObjVar(bldg, MISSION, terminal);
        detachScript(terminal, "systems.missions.base.mission_terminal");
        attachScript(terminal, MISSION_SCRIPT);
    }
    public void spawnMissionNpc(obj_id bldg) throws InterruptedException
    {
        return;
    }
    public void spawnTravelTerminal(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, TRAVEL_TERMINAL))
        {
            obj_id oldTerminal = getObjIdObjVar(bldg, TRAVEL_TERMINAL);
            destroyObject(oldTerminal);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = TRAVEL_TERMINAL_LOC_X;
        spawnLoc.y = TRAVEL_TERMINAL_LOC_Y;
        spawnLoc.z = TRAVEL_TERMINAL_LOC_Z;
        spawnLoc.cell = getCellId(bldg, TRAVEL_TERMINAL_LOC_CELL);
        obj_id terminal = create.object(TRAVEL_TERMINAL_TEMPLATE, spawnLoc);
        setObjVar(bldg, TRAVEL_TERMINAL, terminal);
        attachScript(terminal, TRAVEL_TERMINAL_SCRIPT);
        detachScript(terminal, "terminal.terminal_travel");
    }
    public void spawnTravelNpc(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, TRAVEL_NPC))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, TRAVEL_NPC);
            destroyObject(oldNpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = TRAVEL_NPC_LOC_X;
        spawnLoc.y = TRAVEL_NPC_LOC_Y;
        spawnLoc.z = TRAVEL_NPC_LOC_Z;
        spawnLoc.cell = getCellId(bldg, TRAVEL_NPC_LOC_CELL);
        obj_id newNpc = create.object(TRAVEL_NPC_TYPE, spawnLoc);
        removeObjVar(newNpc, "ai.diction");
        ai_lib.setDefaultCalmBehavior(newNpc, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(newNpc, true);
        attachScript(newNpc, TRAVEL_NPC_SCRIPT);
        setCondition(newNpc, CONDITION_CONVERSABLE);
        setObjVar(bldg, TRAVEL_NPC, newNpc);
        setName(newNpc, "");
        setName(newNpc, QUARTERMASTER_NAME);
        setYaw(newNpc, 92.0f);
        setCondition(newNpc, CONDITION_SPACE_INTERESTING);
    }
    public void spawnPilotNpc(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, PILOT))
        {
            obj_id oldNpc = getObjIdObjVar(bldg, PILOT);
            destroyObject(oldNpc);
        }
        location spawnLoc = new location(getLocation(bldg));
        spawnLoc.x = PILOT_LOCATION_X;
        spawnLoc.y = PILOT_LOCATION_Y;
        spawnLoc.z = PILOT_LOCATION_Z;
        spawnLoc.cell = getCellId(bldg, PILOT_LOCATION_CELL);
        obj_id newNpc = create.createNpc(PILOT_TYPE, PILOT_TEMPLATE, spawnLoc);
        removeObjVar(newNpc, "ai.diction");
        ai_lib.setDefaultCalmBehavior(newNpc, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(newNpc, true);
        attachScript(newNpc, PILOT_SCRIPT);
        setCondition(newNpc, CONDITION_CONVERSABLE);
        setObjVar(bldg, PILOT, newNpc);
        setYaw(newNpc, PILOT_YAW);
    }
    public int handleEndTutorial(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
