package utils.tags;

import java.util.ArrayList;
import java.util.List;

public interface Taggable {
    ArrayList<String> getTagList();
    void addTag(String tag);
    void removeTag(String tag);
    void removeAllTags();
}
