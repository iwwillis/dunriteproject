package sample;

import javafx.beans.property.*;

public class testReportObj1 {

    private final IntegerProperty empIncidentNumber;
    private final StringProperty empIncidentDescription;
    private final StringProperty empIncidentType;
    private final StringProperty empIncidentStatus;

    public testReportObj1(int a, String b, String c,  String d) {
        this.empIncidentNumber = new SimpleIntegerProperty(a);
        this.empIncidentDescription = new SimpleStringProperty(b);
        this.empIncidentType = new SimpleStringProperty(c);
        this.empIncidentStatus = new SimpleStringProperty(d);

    }

    public int getEmpIncidentNumber() {
        return empIncidentNumber.get();
    }

    public IntegerProperty empIncidentNumberProperty() {
        return empIncidentNumber;
    }

    public StringProperty empIncidentDescriptionProperty() {
        return empIncidentDescription;
    }
    public StringProperty empIncidentTypeProperty() {
        return empIncidentType;
    }

    public StringProperty empIncidentStatusProperty() {
        return empIncidentStatus;
    }
}
