package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Unit {
    private final IntegerProperty unitId;
    private final StringProperty unit;
    private final StringProperty unitAbbreviation;

    public Unit(int id, String u, String abb) {
        this.unitId = new SimpleIntegerProperty(id);
        this.unit = new SimpleStringProperty(u);
        this.unitAbbreviation = new SimpleStringProperty(abb);
    }

    public IntegerProperty getUnitId() {
        return unitId;
    }

    public StringProperty getUnit() {
        return unit;
    }

    public StringProperty getUnitAbbreviation() {
        return unitAbbreviation;
    }
}
