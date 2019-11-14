package sample;


import javafx.beans.property.*;

public class Payment {
    private final IntegerProperty paymentNumberId;
    private final StringProperty paymentDate;
    private final DoubleProperty originalTotal;
    private final DoubleProperty paymentAmount;
    private final DoubleProperty paymentRemaining;
    private final StringProperty paymentTerms;
    private final StringProperty validationCode;
    private final IntegerProperty jobNumber;
    private final IntegerProperty paymentMethodId;


    public Payment(int paymentNumberId, String paymentDate, Double originalTotal,  Double paymentAmount, Double paymentRemaining,
                   String paymentTerms, String validationCode, Integer jobNumber, int paymentMethodId) {
        this.paymentNumberId = new SimpleIntegerProperty(paymentNumberId);
        this.paymentDate = new SimpleStringProperty(paymentDate);
        this.originalTotal = new SimpleDoubleProperty(originalTotal);
        this.paymentAmount = new SimpleDoubleProperty(paymentAmount);
        this.paymentRemaining = new SimpleDoubleProperty(paymentRemaining);
        this.paymentTerms = new SimpleStringProperty(paymentTerms);
        this.validationCode = new SimpleStringProperty(validationCode);
        this.jobNumber = new SimpleIntegerProperty(jobNumber);
        this.paymentMethodId = new SimpleIntegerProperty(paymentMethodId);
    }

    public IntegerProperty getPaymentNumberId() {
        return paymentNumberId;
    }

    public DoubleProperty getOriginalTotal() {
        return originalTotal;
    }

    public StringProperty getPaymentDate() {
        return paymentDate;
    }

    public DoubleProperty getPaymentAmount() {
        return paymentAmount;
    }

    public DoubleProperty getPaymentRemaining() {
        return paymentRemaining;
    }

    public StringProperty getPaymentTerms() {
        return paymentTerms;
    }

    public StringProperty getValidationCode() {
        return validationCode;
    }

    public IntegerProperty getJobNumber() {
        return jobNumber;
    }

    public IntegerProperty getPaymentMethodId() {
        return paymentMethodId;
    }

}
