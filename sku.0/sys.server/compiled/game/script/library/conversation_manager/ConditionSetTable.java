package script.library.conversation_manager;

import script.dictionary;
import script.library.datatable_api;
import script.library.conversation_manager.ConversationManagerData.ConditionSet;

public class ConditionSetTable
{
    public static final String DEFAULT_DIRECTORY = "datatables/conversation";
    public static final String TABLE_SUFFIX = "_condition_sets.iff";

    public ConditionSetTable()
    {
    }

    public static String getTableName(String baseName)
    {
        return getTableName(DEFAULT_DIRECTORY, baseName);
    }

    public static String getTableName(String directory, String baseName)
    {
        if (baseName == null)
        {
            return null;
        }
        String normalizedBaseName = normalizeBaseName(baseName);
        String normalizedDirectory = normalizeDirectory(directory);
        if (normalizedDirectory.length() == 0)
        {
            return normalizedBaseName + TABLE_SUFFIX;
        }
        if (normalizedDirectory.endsWith("/"))
        {
            return normalizedDirectory + normalizedBaseName + TABLE_SUFFIX;
        }
        return normalizedDirectory + "/" + normalizedBaseName + TABLE_SUFFIX;
    }

    public static ConditionSet[] getConditionSets(String baseName) throws InterruptedException
    {
        return getConditionSetsFromTable(getTableName(baseName));
    }

    public static ConditionSet[] getConditionSets(String directory, String baseName) throws InterruptedException
    {
        return getConditionSetsFromTable(getTableName(directory, baseName));
    }

    public static ConditionSet[] getConditionSetsById(String directory, String baseName, String id) throws InterruptedException
    {
        return getConditionSetsByIdFromTable(getTableName(directory, baseName), id);
    }

    public static ConditionSet[] getConditionSetsFromTable(String table) throws InterruptedException
    {
        dictionary[] rows = datatable_api.getRows(table);
        return fromDictionaries(rows, false);
    }

    public static ConditionSet[] getConditionSetsByIdFromTable(String table, String id) throws InterruptedException
    {
        dictionary[] rows = datatable_api.findRowsByString(table, ConditionSet.COLUMN_ID, id);
        return fromDictionaries(rows, true);
    }

    private static ConditionSet[] fromDictionaries(dictionary[] rows, boolean sortByOrder)
    {
        ConditionSet[] sets = new ConditionSet[rows.length];
        for (int i = 0; i < rows.length; i++)
        {
            sets[i] = ConditionSet.fromDictionary(rows[i]);
        }
        if (sortByOrder)
        {
            sortByOrder(sets);
        }
        return sets;
    }

    private static void sortByOrder(ConditionSet[] sets)
    {
        for (int i = 1; i < sets.length; i++)
        {
            ConditionSet current = sets[i];
            int j = i - 1;
            while (j >= 0 && sets[j] != null && current != null && sets[j].order > current.order)
            {
                sets[j + 1] = sets[j];
                j--;
            }
            sets[j + 1] = current;
        }
    }

    private static String normalizeDirectory(String directory)
    {
        if (directory == null || directory.length() == 0)
        {
            return DEFAULT_DIRECTORY;
        }
        if (directory.startsWith("datatables/"))
        {
            return directory;
        }
        if (directory.startsWith("conversation/"))
        {
            return "datatables/" + directory;
        }
        return DEFAULT_DIRECTORY + "/" + directory;
    }

    private static String normalizeBaseName(String baseName)
    {
        if (baseName.endsWith("_") && TABLE_SUFFIX.startsWith("_"))
        {
            return baseName.substring(0, baseName.length() - 1);
        }
        return baseName;
    }
}
