class SalesEngineer extends Person {
    private int workExperience;

    public SalesEngineer(String enterpriseName, String fullName, String address, String phoneNumber, int workExperience) {
        super(enterpriseName, fullName, address, phoneNumber);
        if (workExperience < 0) {
            throw new IllegalArgumentException("Досвід роботи не може бути меншим за 0");
        }
        this.workExperience = workExperience;
    }

    public int getWorkExperience() {
        return workExperience;
    }

    @Override
    public void displayInfo() {
        System.out.println("Інженер з продажу: " + fullName + ", Підприємство: " + enterpriseName + ", Стаж: " + workExperience);
    }
}