package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeIncident {
    private final IntegerProperty employeeIncidentNumber;
    private final StringProperty employeeIncidentDescription;
    private final StringProperty employeeIncidentResolution;
    private final StringProperty employeeIncidentFollowup;
    private final IntegerProperty employeeIncidentTypeId;
    private final IntegerProperty employeeIncidentStatusId;

    public EmployeeIncident(int empIncidentNumber,String empIncidentDesc, String empIncidentResolution, String empIncidentFollowup,
                            int empIncidentTypeId, int empIncidentStatusId){
        this.employeeIncidentNumber = new SimpleIntegerProperty(empIncidentNumber);
        this.employeeIncidentDescription = new SimpleStringProperty(empIncidentDesc);
        this.employeeIncidentResolution = new SimpleStringProperty(empIncidentResolution);
        this.employeeIncidentFollowup = new SimpleStringProperty(empIncidentFollowup);
        this.employeeIncidentTypeId = new SimpleIntegerProperty(empIncidentTypeId);
        this.employeeIncidentStatusId = new SimpleIntegerProperty(empIncidentStatusId);
    }

    public IntegerProperty getEmployeeIncidentNumber() {
        return employeeIncidentNumber;
    }

    public StringProperty getEmployeeIncidentDescription() {
        return employeeIncidentDescription;
    }


    public StringProperty getEmployeeIncidentResolution() {
        return employeeIncidentResolution;
    }

    public StringProperty getEmployeeIncidentFollowup() {
        return employeeIncidentFollowup;
    }

    public IntegerProperty getEmployeeIncidentTypeId() {
        return employeeIncidentTypeId;
    }
    public IntegerProperty getEmployeeIncidentStatusId() {
        return employeeIncidentStatusId;
    }
}
