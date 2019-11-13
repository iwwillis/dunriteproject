package sample;


import javafx.beans.property.*;

public class PaymentMethod {
    private final IntegerProperty paymentMethodNumberId;
    private final StringProperty paymentMethodName;



    public PaymentMethod(int paymentMethodNumberId, String paymentMethodName) {
        this.paymentMethodNumberId = new SimpleIntegerProperty(paymentMethodNumberId);
        this.paymentMethodName = new SimpleStringProperty(paymentMethodName);

    }

    public IntegerProperty getPaymentMethodNumberId() {
        return paymentMethodNumberId;
    }

    public StringProperty getPaymentMethodName() {
        return paymentMethodName;
    }


}
