package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Job {
    private final IntegerProperty jobNumber;
    private final StringProperty streetAddress;
    private final StringProperty jobCity;
    private final IntegerProperty StateId;
    private final StringProperty zip;
    private final IntegerProperty countryId;
    private final StringProperty start;
    private final StringProperty end;
    private final StringProperty quoteDate;
    private final StringProperty quoteExp;
    private final StringProperty invoiceDate;
    private final IntegerProperty jobSizeId;
    private final IntegerProperty jobStatusId;
    private final IntegerProperty customerId;
    private final IntegerProperty jobTypeId;

    public Job(int jobNumber, String streetAddress, String jobCity, int stateId,
               String zip, int countryId, String start, String end, String quoteDate,
               String quoteExp, String invoiceDate, int jobSizeId, int jobStatusId, int customerId, int jobTypeId) {
        this.jobNumber = new SimpleIntegerProperty(jobNumber);
        this.streetAddress = new SimpleStringProperty(streetAddress);
        this.jobCity = new SimpleStringProperty(jobCity);
        this.StateId = new SimpleIntegerProperty(stateId);
        this.zip = new SimpleStringProperty(zip);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
        this.quoteDate = new SimpleStringProperty(quoteDate);
        this.quoteExp = new SimpleStringProperty(quoteExp);
        this.invoiceDate = new SimpleStringProperty(invoiceDate);
        this.jobSizeId = new SimpleIntegerProperty(jobSizeId);
        this.jobStatusId = new SimpleIntegerProperty(jobStatusId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.jobTypeId = new SimpleIntegerProperty(jobTypeId);
    }

    public IntegerProperty getJobNumber() {
        return jobNumber;
    }


    public StringProperty getStreetAddress() {
        return streetAddress;
    }


    public StringProperty getJobCity() {
        return jobCity;
    }

    public IntegerProperty getStateId() {
        return StateId;
    }

    public StringProperty getZip() {
        return zip;
    }

    public IntegerProperty getCountryId() {
        return countryId;
    }


    public StringProperty getStart() {
        return start;
    }

    public StringProperty getEnd() {
        return end;
    }

    public StringProperty getQuoteDate() {
        return quoteDate;
    }

    public StringProperty getQuoteExp() {
        return quoteExp;
    }

    public StringProperty getInvoiceDate() {
        return invoiceDate;
    }

    public IntegerProperty getJobSizeId() {
        return jobSizeId;
    }

    public IntegerProperty getJobStatusId() {
        return jobStatusId;
    }

    public IntegerProperty getCustomerId() {
        return customerId;
    }

    public IntegerProperty getJobTypeId() {
        return jobTypeId;
    }
}
