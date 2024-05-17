package org.example.bom;

public class Truck extends Vehicle {

    private final double fixedDownPayment = 5000; // Фіксований перший внесок

    private final int loanTerm = 7; // Термін кредиту у роках

    private final double annualInterestRate = 0.06; // Річна процентна ставка 6%

    public Truck(Long id, int amount, Model model, Color color, float price, int year) {
        super(id, amount, model, color, price, year);
    }

    @Override
    public int calculateCredit() {
        double loanAmount = getPrice() - fixedDownPayment;
        double monthlyInterestRate = annualInterestRate / 12;
        int numberOfPayments = loanTerm * 12;

        double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        return (int) Math.round(monthlyPayment);
    }
}
