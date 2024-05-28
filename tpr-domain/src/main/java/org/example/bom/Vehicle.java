package org.example.bom;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Motorcycle.class, name = "MOTORCYCLE"),
        @JsonSubTypes.Type(value = Truck.class, name = "TRUCK"),
        @JsonSubTypes.Type(value = PassengerCar.class, name = "PASSENGER_CAR")
})
public abstract class Vehicle {

    private Long id;

    private int amount;

    private Model model;

    private Color color;

    private float price;

    private int year;

    private boolean isUsed;

    public abstract int calculateCredit();
}

// OLD
//@Getter
//@Setter
//@AllArgsConstructor
//public class Vehicle {
//
//    private Long id;
//
//    private String amount;
//
//    private Model model;
//
//    private Color color;
//
//    private String type;
//
//    private double price;
//
//    private int year;
//
//    public double calculateCredit() {
//        if (Objects.equals(type, "PassengerCar")) {
//            double downPaymentPercentage = 0.2; // 20%
//            double downPayment = price * downPaymentPercentage;
//            double loanAmount = price - downPayment;
//            int loanTerm = 5; // Термін кредиту у роках
//            double annualInterestRate = 0.05; // Річна процентна ставка 5%
//            double monthlyInterestRate = annualInterestRate / 12;
//            int numberOfPayments = loanTerm * 12;
//
//            return (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
//        } else if (Objects.equals(type, "Truck")) {
//            double downPayment = 5000; // Фіксований перший внесок
//            double loanAmount = price - downPayment;
//            int loanTerm = 7; // Термін кредиту у роках
//            double annualInterestRate = 0.06; // Річна процентна ставка 6%
//            double monthlyInterestRate = annualInterestRate / 12;
//            int numberOfPayments = loanTerm * 12;
//
//            return (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
//        } else if (Objects.equals(type, "Motorcycle")) {
//            double loanAmount = price;
//            int loanTerm = 3; // Термін кредиту у роках
//            double annualInterestRate = 0.08; // Річна процентна ставка 8%
//            double monthlyInterestRate = annualInterestRate / 12;
//            int numberOfPayments = loanTerm * 12;
//
//            return (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
//        }
//        return 0;
//    }
//
//    public String generatePrintableString() {
//        return "Vehicle ID: " + id + "\n" +
//                "Amount: " + amount + "\n" +
//                "Model: " + model.getName() + "\n" +
//                "Color: " + color.getName() + "\n" +
//                "Type: " + type + "\n" +
//                "Price: " + price + "\n" +
//                "Year: " + year + "\n" +
//                "Credit: " + calculateCredit() + "\n";
//    }
//}
