package utils.tags;

import java.util.ArrayList;

public interface Taggable {
    ArrayList<String> getTagList();
    void addTag(String tag);
    void removeTag(String tag);
    void removeAllTags();
}
