package script.systems.crafting.bio_engineer.dna_template;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.bio_engineer;

public class crafting_dna_template_kahmurra extends script.systems.crafting.bio_engineer.crafting_base_dna_template
{
    public crafting_dna_template_kahmurra()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS = 
    {
        "outdoors_bio_engineer_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "medicine_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "medicine_experimentation",
        "medicine_complexity"
    };
    public static final String[] CUSTOMIZATION_SKILL_MODS = 
    {
        "bio_engineer_customization"
    };
    public static final String[] APPEARANCES = 
    {
        "object/tangible/component/dna/dna_template_kahmurra.iff"
    };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hardiness", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_HARDINESS, 1)
        }),
        new resource_weight("fortitude", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FORTITUDE, 1)
        }),
        new resource_weight("dexterity", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEXTERITY, 1)
        }),
        new resource_weight("endurance", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_ENDURANCE, 1)
        }),
        new resource_weight("intellect", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_INTELLECT, 1)
        }),
        new resource_weight("cleverness", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_CLEVERNESS, 1)
        }),
        new resource_weight("dependability", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEPENDABILITY, 1)
        }),
        new resource_weight("courage", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_COURAGE, 1)
        }),
        new resource_weight("fierceness", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FIERCENESS, 1)
        }),
        new resource_weight("power", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_POWER, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hardiness", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_HARDINESS, 1)
        }),
        new resource_weight("fortitude", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FORTITUDE, 1)
        }),
        new resource_weight("dexterity", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEXTERITY, 1)
        }),
        new resource_weight("endurance", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_ENDURANCE, 1)
        }),
        new resource_weight("intellect", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_INTELLECT, 1)
        }),
        new resource_weight("cleverness", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_CLEVERNESS, 1)
        }),
        new resource_weight("dependability", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEPENDABILITY, 1)
        }),
        new resource_weight("courage", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_COURAGE, 1)
        }),
        new resource_weight("fierceness", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FIERCENESS, 1)
        }),
        new resource_weight("power", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_POWER, 1)
        })
    };
    public static final resource_weight[] OBJ_SLOT_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("physique_profile", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_HARDINESS, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FORTITUDE, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEXTERITY, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_ENDURANCE, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_INTELLECT, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_CLEVERNESS, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEPENDABILITY, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_COURAGE, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FIERCENESS, 4),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_POWER, 4)
        }),
        new resource_weight("prowess_profile", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_HARDINESS, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FORTITUDE, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEXTERITY, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_ENDURANCE, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_INTELLECT, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_CLEVERNESS, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEPENDABILITY, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_COURAGE, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FIERCENESS, 4),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_POWER, 4)
        }),
        new resource_weight("mental_profile", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_HARDINESS, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FORTITUDE, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEXTERITY, 4),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_ENDURANCE, 4),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_INTELLECT, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_CLEVERNESS, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEPENDABILITY, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_COURAGE, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FIERCENESS, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_POWER, 2)
        }),
        new resource_weight("psychological_profile", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_HARDINESS, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FORTITUDE, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEXTERITY, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_ENDURANCE, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_INTELLECT, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_CLEVERNESS, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEPENDABILITY, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_COURAGE, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FIERCENESS, 4),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_POWER, 4)
        }),
        new resource_weight("aggression_profile", new resource_weight.weight[]
        {
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_HARDINESS, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FORTITUDE, 6),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEXTERITY, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_ENDURANCE, 2),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_INTELLECT, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_CLEVERNESS, 1),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_DEPENDABILITY, 4),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_COURAGE, 4),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_FIERCENESS, 10),
            new resource_weight.weight(bio_engineer.CREATURE_ATTRIB_POWER, 10)
        })
    };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_DISPLAY = 
    {
        new resource_weight("hardiness", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 6)
        }),
        new resource_weight("fortitude", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 6)
        }),
        new resource_weight("dexterity", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 2)
        }),
        new resource_weight("endurance", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 2)
        }),
        new resource_weight("intellect", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 1)
        }),
        new resource_weight("cleverness", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 1)
        }),
        new resource_weight("dependability", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 4)
        }),
        new resource_weight("courage", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 1),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 6),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 10),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 4)
        }),
        new resource_weight("fierceness", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 10)
        }),
        new resource_weight("power", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_01, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_02, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_03, 2),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_04, 4),
            new resource_weight.weight(craftinglib.SLOT_WEIGHT_SLOT_05, 10)
        })
    };
    public String[] getRequiredSkills() throws InterruptedException
    {
        return REQUIRED_SKILLS;
    }
    public String[] getAssemblySkillMods() throws InterruptedException
    {
        return ASSEMBLY_SKILL_MODS;
    }
    public String[] getExperimentSkillMods() throws InterruptedException
    {
        return EXPERIMENT_SKILL_MODS;
    }
    public resource_weight[] getResourceMaxResourceWeights() throws InterruptedException
    {
        return OBJ_MAX_ATTRIBUTE_RESOURCES;
    }
    public resource_weight[] getAssemblyResourceWeights() throws InterruptedException
    {
        return OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES;
    }
    public resource_weight[] getSlotResourceWeights() throws InterruptedException
    {
        return OBJ_SLOT_ATTRIBUTE_RESOURCES;
    }
    public resource_weight[] getAssemblyDisplayWeights() throws InterruptedException
    {
        return OBJ_ASSEMBLY_ATTRIBUTE_DISPLAY;
    }
}
