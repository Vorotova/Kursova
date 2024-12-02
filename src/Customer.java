class Customer extends Person {
    private int contractId;

    public Customer(String enterpriseName, String fullName, String address, String phoneNumber, int contractId) {
        super(enterpriseName, fullName, address, phoneNumber);
        if (contractId <= 0) {
            throw new IllegalArgumentException("ID контракту повинен бути більшим за 0");
        }
        this.contractId = contractId;
    }

    public int getContractId() { return contractId; }

    @Override
    public void displayInfo() {
        System.out.println("Замовник: " + fullName + ", Підприємство: " + enterpriseName + ", Контракт ID: " + contractId);
    }
}