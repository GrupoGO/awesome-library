package es.grupogo.awesomelibrary.StickyHeader;

import java.util.List;

/**
 * Created by jorge_cmata on 27/1/17.
 */

public class Section {

    private String title;
    private List<?> objects;

    public Section(){
    }

    public Section(String title, List<?> objects) {
        this.title = title;
        this.objects = objects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<?> getObjects() {
        return objects;
    }

    public void setObjects(List<?> objects) {
        this.objects = objects;
    }
}
