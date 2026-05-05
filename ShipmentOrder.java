abstract class ShipmentOrder implements SummaryPrintable {
    private String orderNumber;
    private String customerName;
    private double distanceKm;
    private double baseFee;
    private boolean insured;
    private static double lastCalculatedPrice;

    public ShipmentOrder(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.distanceKm = distanceKm;
        this.baseFee = baseFee;
        this.insured = insured;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public boolean isInsured() {
        return insured;
    }

    public static double getLastCalculatedPrice() {
        return lastCalculatedPrice;
    }

    protected abstract double calculateBasePrice();

    protected abstract double calculateAdditionalFee();

    public abstract String getShipmentType();

    public final void processOrder() {
        validateOrder();
        validateSpecificRules();

        double price = calculateBasePrice();
        price += calculateAdditionalFee();
        price = applyInsurance(price);
        price = applyBusinessDiscount(price);

        lastCalculatedPrice = price;
        printProcessingResult();
    }

    private void validateOrder() {

        if (orderNumber == null) {
            throw new NullPointerException("orderNumber cannot be empty.");
        } if (customerName == null) {
            throw new NullPointerException("customerName cannot be empty");
        } if (distanceKm == 0.0 || distanceKm < 0.0) {
            throw new IllegalArgumentException("distanceKm must have a value above 0.");
        } if (baseFee == 0.0 || baseFee < 0.0) {
            throw new IllegalArgumentException("baseFee must have a value above 0.");
        }
    }

    protected void validateSpecificRules(){}

    private double applyInsurance(double price) {
        if(insured) {
            price = price + (price*1.07);
        } return price;
    }

    protected double applyBusinessDiscount(double price) {
        return price;
    }

    private void printProcessingResult() {
        System.out.println("\nOrder validated successfully");
        System.out.println("price: " + getLastCalculatedPrice() + " PLN");
    }

    @Override
    public String buildSummaryLine() {
        return "ORDER " + getOrderNumber() + "\nCUSTOMER NAME: " + getCustomerName() + " \nLAST CALCULATED PRICE: " + getLastCalculatedPrice();

    }
}
