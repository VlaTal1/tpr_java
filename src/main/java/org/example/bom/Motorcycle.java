package org.example.bom;

public class Motorcycle extends Vehicle {

    private final int loanTerm = 3; // Термін кредиту у роках

    private final double annualInterestRate = 0.08; // Річна процентна ставка 8%

    public Motorcycle(Long id, int amount, Model model, Color color, float price, int year) {
        super(id, amount, model, color, price, year);
    }

    @Override
    public int calculateCredit() {
        double loanAmount = getPrice();
        double monthlyInterestRate = annualInterestRate / 12;
        int numberOfPayments = loanTerm * 12;

        double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        return (int) Math.round(monthlyPayment);
    }
}
