package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
    private final IntegerProperty empId;
    private final StringProperty empFirst;
    private final StringProperty empLast;
    private final StringProperty streetAddress;
    private final StringProperty empCity;
    private final IntegerProperty stateId;
    private final StringProperty empZip;
    private final IntegerProperty countryId;
    private final StringProperty empPhone;
    private final StringProperty empEmail;
    private final StringProperty hire;
    private final StringProperty leave;
    private final IntegerProperty empTypeId;
    private final IntegerProperty empStatusId;

    public Employee(int number, String first, String last, String address, String city, int stateId, String empZip,
                    int countryId, String phone, String email, String hire, String leave, int empTypeId, int empStatusId){
        this.empId = new SimpleIntegerProperty(number);
        this.empFirst = new SimpleStringProperty(first);
        this.empLast = new SimpleStringProperty(last);
        this.streetAddress = new SimpleStringProperty(address);
        this.empCity = new SimpleStringProperty(city);
        this.stateId = new SimpleIntegerProperty(stateId);
        this.empZip = new SimpleStringProperty(empZip);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.empPhone = new SimpleStringProperty(phone);
        this.empEmail = new SimpleStringProperty(email);
        this.hire = new SimpleStringProperty(hire);
        this.leave = new SimpleStringProperty(leave);
        this.empTypeId = new SimpleIntegerProperty(empTypeId);
        this.empStatusId = new SimpleIntegerProperty(empStatusId);
    }

    public IntegerProperty getEmpId() {
        return empId;
    }
    public StringProperty getEmpFirst() {
        return empFirst;
    }

    public StringProperty getEmpLast() {
        return empLast;
    }

    public StringProperty getStreetAddress() {
        return streetAddress;
    }

    public StringProperty getEmpCity() {
        return empCity;
    }

    public IntegerProperty getStateId() {
        return stateId;
    }

    public StringProperty getEmpZip() {
        return empZip;
    }

    public IntegerProperty getCountryId() {
        return countryId;
    }

    public StringProperty getEmpPhone() {
        return empPhone;
    }

    public StringProperty getEmpEmail() {
        return empEmail;
    }

    public StringProperty getHire() {
        return hire;
    }

    public StringProperty getLeave() {
        return leave;
    }

    public IntegerProperty getEmpTypeId() {
        return empTypeId;
    }
    public IntegerProperty getEmpStatusId() {
        return empStatusId;
    }
}
