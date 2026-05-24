package script.library.conversation_manager;

import script.dictionary;
import script.library.datatable_api;
import script.library.conversation_manager.ConversationManagerData.ConditionGroup;

public class ConditionGroupTable
{
    public static final String DEFAULT_DIRECTORY = "datatables/conversation";
    public static final String TABLE_SUFFIX = "_condition_groups.iff";

    public ConditionGroupTable()
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

    public static ConditionGroup[] getConditionGroups(String baseName) throws InterruptedException
    {
        return getConditionGroupsFromTable(getTableName(baseName));
    }

    public static ConditionGroup[] getConditionGroups(String directory, String baseName) throws InterruptedException
    {
        return getConditionGroupsFromTable(getTableName(directory, baseName));
    }

    public static ConditionGroup[] getConditionGroupsById(String directory, String baseName, String id) throws InterruptedException
    {
        return getConditionGroupsByIdFromTable(getTableName(directory, baseName), id);
    }

    public static ConditionGroup[] getConditionGroupsFromTable(String table) throws InterruptedException
    {
        dictionary[] rows = datatable_api.getRows(table);
        return fromDictionaries(rows);
    }

    public static ConditionGroup[] getConditionGroupsByIdFromTable(String table, String id) throws InterruptedException
    {
        dictionary[] rows = datatable_api.findRowsByString(table, ConditionGroup.COLUMN_ID, id);
        return fromDictionaries(rows);
    }

    public static ConditionGroup getConditionGroupFromTable(String table, String id, String conditionId) throws InterruptedException
    {
        dictionary[] rows = datatable_api.findRowsByStrings(table, new String[]
        {
            ConditionGroup.COLUMN_ID,
            ConditionGroup.COLUMN_CONDITION_ID
        }, new String[]
        {
            id,
            conditionId
        });
        if (rows.length < 1)
        {
            return null;
        }
        return ConditionGroup.fromDictionary(rows[0]);
    }

    private static ConditionGroup[] fromDictionaries(dictionary[] rows)
    {
        ConditionGroup[] groups = new ConditionGroup[rows.length];
        for (int i = 0; i < rows.length; i++)
        {
            groups[i] = ConditionGroup.fromDictionary(rows[i]);
        }
        return groups;
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
