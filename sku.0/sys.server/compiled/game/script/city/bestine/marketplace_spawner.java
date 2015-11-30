package script.city.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class marketplace_spawner extends script.base_script
{
    public marketplace_spawner()
    {
    }
    public static final String STF = "bestine";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "city.bestine.politician_event_npc"))
        {
            attachScript(self, "city.bestine.politician_event_npc");
        }
        if (hasObjVar(self, "marketplaceSpawned"))
        {
            removeObjVar(self, "marketplaceSpawned");
        }
        messageTo(self, "spawnMarketplaceNpcs", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "city.bestine.politician_event_npc"))
        {
            attachScript(self, "city.bestine.politician_event_npc");
        }
        if (hasObjVar(self, "marketplaceSpawned"))
        {
            removeObjVar(self, "marketplaceSpawned");
        }
        messageTo(self, "spawnMarketplaceNpcs", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnMarketplaceNpcs(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "marketplaceSpawned"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "bestine.electionWinner"))
        {
            String electionWinner = getStringObjVar(self, "bestine.electionWinner");
            if (electionWinner.equals("sean") || electionWinner.equals("Sean"))
            {
                string_id merchant_titleId = new string_id("bestine", "merchant_title");
                String merchant_title = utils.packStringId(merchant_titleId);
                location merchant01Location = new location(-1135.28f, 0.0f, -3688.03f, "tatooine", null);
                obj_id merchant01 = create.staticObject("commoner", merchant01Location);
                setYaw(merchant01, 61);
                setInvulnerable(merchant01, true);
                setCreatureStatic(merchant01, true);
                String merchant01Template = getTemplateName(merchant01);
                String merchant01Name = generateRandomName(merchant01Template);
                string_id merchant01NameComplete = new string_id(STF, "merchant01");
                setName(merchant01, merchant01NameComplete);
                setObjVar(merchant01, "marketplaceSpawner", self);
                attachScript(merchant01, "city.bestine.marketplace_npc");
                location merchant02Location = new location(-1133.53f, 0.0f, -3693.98f, "tatooine", null);
                obj_id merchant02 = create.staticObject("artisan", merchant02Location);
                setYaw(merchant02, 90);
                setInvulnerable(merchant02, true);
                setCreatureStatic(merchant02, true);
                String merchant02Template = getTemplateName(merchant02);
                String merchant02Name = generateRandomName(merchant02Template);
                string_id merchant02NameComplete = new string_id(STF, "merchant02");
                setName(merchant02, merchant02NameComplete);
                setObjVar(merchant02, "marketplaceSpawner", self);
                attachScript(merchant02, "city.bestine.marketplace_npc");
                int merchant03Chance = rand(1, 5);
                if (merchant03Chance == 2)
                {
                    location merchant03Location = new location(-1126f, 0.0f, -3673.42f, "tatooine", null);
                    obj_id merchant03 = create.staticObject("artisan", merchant03Location);
                    setYaw(merchant03, -110);
                    setInvulnerable(merchant03, true);
                    setCreatureStatic(merchant03, true);
                    String merchant03Template = getTemplateName(merchant03);
                    String merchant03Name = generateRandomName(merchant03Template);
                    string_id merchant03NameComplete = new string_id(STF, "merchant03");
                    setName(merchant03, merchant03NameComplete);
                    setObjVar(merchant03, "marketplaceSpawner", self);
                    attachScript(merchant03, "city.bestine.marketplace_npc");
                    attachScript(merchant03, "conversation.bestine_stone_merchant");
                }
                location merchant04Location = new location(-1115.29f, 0.0f, -3709.47f, "tatooine", null);
                obj_id merchant04 = create.staticObject("scientist", merchant04Location);
                setYaw(merchant04, 7);
                setInvulnerable(merchant04, true);
                setCreatureStatic(merchant04, true);
                String merchant04Template = getTemplateName(merchant04);
                String merchant04Name = generateRandomName(merchant04Template);
                string_id merchant04NameComplete = new string_id(STF, "merchant04");
                setName(merchant04, merchant04NameComplete);
                setObjVar(merchant04, "marketplaceSpawner", self);
                attachScript(merchant04, "city.bestine.marketplace_npc");
                int SpecialChance = rand(1, 5);
                if (SpecialChance == 5)
                {
                    location merchantSpecialLocation = new location(-1136.64f, 0.0f, -3667.40f, "tatooine", null);
                    obj_id merchantSpecial = create.staticObject("noble", merchantSpecialLocation);
                    setYaw(merchantSpecial, -175);
                    setInvulnerable(merchantSpecial, true);
                    setCreatureStatic(merchantSpecial, true);
                    String merchantSpecialTemplate = getTemplateName(merchantSpecial);
                    String merchantSpecialName = generateRandomName(merchantSpecialTemplate);
                    string_id merchantSpecialNameComplete = new string_id(STF, "merchantSpecial");
                    setName(merchantSpecial, merchantSpecialNameComplete);
                    setObjVar(merchantSpecial, "marketplaceSpawner", self);
                    attachScript(merchantSpecial, "city.bestine.marketplace_npc");
                }
                location soundobjectLocation = new location(-1124.35f, 12.0f, -3695.85f, "tatooine", null);
                obj_id soundobject = createObject("object/soundobject/soundobject_marketplace_large.iff", soundobjectLocation);
                setObjVar(soundobject, "marketplaceSpawner", self);
                attachScript(soundobject, "city.bestine.marketplace_npc");
                setObjVar(self, "marketplaceSpawned", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePoliticianEventStatusResponse(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "checkSpawnStatus", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int checkSpawnStatus(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "bestine.electionWinner"))
        {
            String electionWinner = getStringObjVar(self, "bestine.electionWinner");
            if (electionWinner != null)
            {
                if (electionWinner.equals("victor") || electionWinner.equals("Victor"))
                {
                    if (hasObjVar(self, "marketplaceSpawned"))
                    {
                        removeObjVar(self, "marketplaceSpawned");
                    }
                    broadcastMessage("handleRemoveMarketplaceNpcs", null);
                }
                else if (electionWinner.equals("sean") || electionWinner.equals("Sean"))
                {
                    if (!hasObjVar(self, "marketplaceSpawned"))
                    {
                        messageTo(self, "spawnMarketplaceNpcs", null, 10, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
