package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

    private final IntegerProperty customerId;
    private final StringProperty customerFirst;
    private final StringProperty customerLast;
    private final StringProperty customerOrg;
    private final StringProperty streetAddress;
    private final StringProperty customerCity;
    private final IntegerProperty stateId;
    private final StringProperty customerZipcode;
    private final IntegerProperty countryId;
    private final StringProperty customerPhone;
    private final StringProperty customerEmail;
    private final IntegerProperty customerStatusId;
    private final IntegerProperty customerTypeId;

    public Customer(int id, String first, String last, String org, String address, String city,int state, String zipcode,
                    int country, String phone, String email, int statusId, int typeId){
        this.customerId = new SimpleIntegerProperty(id);
        this.customerFirst = new SimpleStringProperty(first);
        this.customerLast = new SimpleStringProperty(last);
        this.customerOrg = new SimpleStringProperty(org);
        this.streetAddress = new SimpleStringProperty(address);
        this.customerCity = new SimpleStringProperty(city);
        this.stateId = new SimpleIntegerProperty(state);
        this.customerZipcode = new SimpleStringProperty(zipcode);
        this.countryId = new SimpleIntegerProperty(country);
        this.customerPhone = new SimpleStringProperty(phone);
        this.customerEmail = new SimpleStringProperty(email);
        this.customerStatusId = new SimpleIntegerProperty(statusId);
        this.customerTypeId = new SimpleIntegerProperty(typeId);

    }

    public IntegerProperty getCustomerId() {
        return customerId;
    }

    public StringProperty getCustomerFirst() {
        return customerFirst;
    }

    public StringProperty getCustomerLast() {
        return customerLast;
    }

    public StringProperty getCustomerOrg() {
        return customerOrg;
    }


    public StringProperty getStreetAddress() {
        return streetAddress;
    }

    public StringProperty getCustomerCity() {
        return customerCity;
    }

    public IntegerProperty getStateId() {
        return stateId;
    }

    public StringProperty getCustomerZipcode() {
        return customerZipcode;
    }

    public IntegerProperty getCountryId() {
        return countryId;
    }

    public StringProperty getCustomerEmail() {
        return customerPhone;
    }

    public StringProperty getCustomerPhone() {
        return customerEmail;
    }


    public IntegerProperty getCustomerStatusId() {
        return customerStatusId;
    }

    public IntegerProperty getCustomerTypeId() {
        return customerTypeId;
    }
}
