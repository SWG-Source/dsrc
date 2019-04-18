package script.test;

import script.location;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class tford_test extends script.base_script
{
    public tford_test()
    {
    }
    public int OnLootLotterySelected(obj_id self, obj_id target_id, obj_id[] selection_ids) throws InterruptedException
    {
        debugConsoleMsg(self, "loot lottery by " + self + " for " + target_id);
        debugSpeakMsg(self, "selected items:");
        for (obj_id item : selection_ids) {
            debugSpeakMsg(self, "[" + item + "]");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("tauntme"))
        {
            prose_package pp = new prose_package();
            pp.stringId = new string_id("ui", "test_pp");
            pp.actor.set(self);
            pp.target.set(self);
            pp.other.set("other_here");
            pp.digitInteger = 666;
            pp.digitFloat = 0.333f;
            dogfightTauntPlayer(self, speaker, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                debugConsoleMsg(self, "command is: " + command);
                switch (command) {
                    case "tmf_showLotteryMyInventory":
                        break;
                    case "tmf_pvpPrepareToBeCovert":
                        debugConsoleMsg(self, "pvpPrepareToBeCovert");
                        pvpPrepareToBeCovert(self);
                        break;
                    case "tmf_pvpPrepareToBeDeclared":
                        debugConsoleMsg(self, "pvpPrepareToBeDeclared");
                        pvpPrepareToBeDeclared(self);
                        break;
                    case "tmf_pvpPrepareToBeNeutral":
                        debugConsoleMsg(self, "pvpPrepareToBeNeutral");
                        pvpPrepareToBeNeutral(self);
                        break;
                }
                if (command.equals("tmf_hideFromClient"))
                {
                    debugConsoleMsg(self, "hit tmf_hideFromClient");
                    obj_id target = getLookAtTarget(self);
                    hideFromClient(target, true);
                }
                switch (command) {
                    case "tmf_noHideFromClient": {
                        debugConsoleMsg(self, "hit tmf_noHideFromClient");
                        obj_id target = getLookAtTarget(self);
                        hideFromClient(target, false);
                        break;
                    }
                    case "tmf_makePrivate": {
                        debugConsoleMsg(self, "hit tmf_makePrivate");
                        location here = getLocation(self);
                        obj_id target = here.cell;
                        permissionsMakePrivate(target);
                        break;
                    }
                    case "tmf_makePublic": {
                        debugConsoleMsg(self, "hit tmf_makePublic");
                        location here = getLocation(self);
                        obj_id target = here.cell;
                        permissionsMakePublic(target);
                        break;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.tford_test");
        }
        else{
            debugSpeakMsg(self, "tford_test.script attached");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLocomotionChanged(obj_id self, int newLocomotion, int oldLocomotion) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnCombatLevelChanged(obj_id self, int oldCombatLevel, int newCombatLevel) throws InterruptedException
    {
        debugSpeakMsg(self, "oldCombatLevel = " + oldCombatLevel + " newCombatLevel = " + newCombatLevel);
        return SCRIPT_CONTINUE;
    }
}
