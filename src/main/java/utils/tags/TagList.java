package utils.tags;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class to make searching objects with tags easier. Each tag is mapped to a unique <code>ArrayList</code>
 * that contains all objects with this tag.
 * @param <T> the type of objects that the tags are attached to.
 */
public class TagList<T extends Taggable> {
    protected HashMap<String, ArrayList<T>> tagList;

    public TagList() {
        tagList = new HashMap<>();
    }

    /**
     * Adds a mapping of a <code>tag</code> to an <code>object</code>.
     * @param tag the tag to be mapped.
     * @param object the object to be mapped.
     */
    public void addMap(String tag, T object) {
        if (tagList.get(tag) == null) {
            ArrayList<T> newList = new ArrayList<>();
            newList.add(object);
            tagList.put(tag, newList);
        } else {
            tagList.get(tag).add(object);
        }
    }

    /**
     * Removes the mapping of the <code>tag</code> to the <code>object</code>. Only executes if the tag exists
     * in the <code>tagList</code>.
     * @param tag the tag of the removed mapping.
     * @param object the object of the removed mapping.
     */
    public void removeMap(String tag, T object) {
        if (tagList.get(tag) != null) {
            tagList.get(tag).remove(object);
        }
    }

    public void removeObject(T object) {
        ArrayList<String> tags = object.getTagList();
        for (String tag : tags) {
            removeMap(tag, object);
        }
    }

    /**
     * Removes the <code>tag</code> and all relevant mappings.
     * @param tag the tag to be removed.
     */
    public void removeTag(String tag) {
        if (tagList.get(tag) != null) {
            tagList.remove(tag);
        }
    }

    /**
     * @param tag the tag of objects to be found.
     * @return an <code>ArrayList</code> of all objects mapped to the <code>tag</code>.
     */
    public ArrayList<T> findWithTag(String tag) {
        if (tagList.get(tag) == null) {
            return null;
        }
        return new ArrayList<>(tagList.get(tag));
    }
}
