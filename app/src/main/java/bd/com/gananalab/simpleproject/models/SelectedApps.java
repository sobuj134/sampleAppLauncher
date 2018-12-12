package bd.com.gananalab.simpleproject.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by monir.sobuj on 12/12/2018.
 */

public class SelectedApps extends RealmObject {
    @PrimaryKey
    private long id;
    private String pkName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }


    @Override
    public String toString() {
        return "SelectedApps{" +
                "id=" + id +
                ", pkName='" + pkName + '\'' +
                '}';
    }
}
