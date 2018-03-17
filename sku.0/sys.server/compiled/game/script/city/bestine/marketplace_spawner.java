package script.city.bestine;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;
import script.string_id;

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
            if (electionWinner.equalsIgnoreCase("sean"))
            {
                createMerchant(self, "commoner", new location(-1135.28f, 0.0f, -3688.03f, "tatooine", null), 61, new string_id(STF, "merchant01"));

                createMerchant(self, "artisan", new location(-1133.53f, 0.0f, -3693.98f, "tatooine", null), 90, new string_id(STF, "merchant02"));

                int merchant03Chance = rand(1, 5);
                if (merchant03Chance == 2)
                {
                    obj_id merchant03 = createMerchant(self, "artisan", new location(-1126f, 0.0f, -3673.42f, "tatooine", null), -110, new string_id(STF, "merchant03"));
                    attachScript(merchant03, "conversation.bestine_stone_merchant");
                }
                createMerchant(self, "scientist", new location(-1115.29f, 0.0f, -3709.47f, "tatooine", null), 7, new string_id(STF, "merchant04"));

                int SpecialChance = rand(1, 5);
                if (SpecialChance == 5)
                {
                    createMerchant(self, "noble", new location(-1136.64f, 0.0f, -3667.40f, "tatooine", null), -175, new string_id(STF, "merchantSpecial"));
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
    private obj_id createMerchant(obj_id self, String type, location loc, int yaw, string_id merchantName) throws InterruptedException{
        obj_id merchant = create.staticObject(type, loc);
        setYaw(merchant, yaw);
        setInvulnerable(merchant, true);
        setCreatureStatic(merchant, true);
        setName(merchant, merchantName);
        setObjVar(merchant, "marketplaceSpawner", self);
        attachScript(merchant, "city.bestine.marketplace_npc");
        return merchant;
    }
}
