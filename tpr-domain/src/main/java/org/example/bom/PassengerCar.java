package org.example.bom;

public class PassengerCar extends Vehicle {

    private final double downPaymentPercentage = 0.2; // 20%

    private final int loanTerm = 5; // Термін кредиту у роках

    private final double annualInterestRate = 0.05; // Річна процентна ставка 5%

    public PassengerCar(Long id, int amount, Model model, Color color, float price, int year, boolean isUsed) {
        super(id, amount, model, color, price, year, isUsed);
    }

    public PassengerCar() {
        super();
    }

    @Override
    public int calculateCredit() {
        double downPayment = getPrice() * downPaymentPercentage;
        double loanAmount = getPrice() - downPayment;
        double monthlyInterestRate = annualInterestRate / 12;
        int numberOfPayments = loanTerm * 12;

        double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        return (int) Math.round(monthlyPayment);
    }
}
