package pt.isec.model.users;

public class HealthData {
    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public HealthData(String weight, String height,
                      String objective, String levelOfFitness,
                      String desiredWeight, String dailyCalorieCount,
                      String allergiesOrIntolerances, String medicalReasons,
                      String chronicHealth, String gastrointestinalIssues,String vitaminDeficiencies,
                      String dietType, String medications) {
        this.weight = weight;
        this.height = height;
        this.objective = objective;
        this.levelOfFitness = levelOfFitness;
        this.desiredWeight = desiredWeight;
        this.dailyCalorieCount = dailyCalorieCount;
        this.allergiesOrIntolerances = allergiesOrIntolerances;
        this.medicalReasons = medicalReasons;
        this.chronicHealth = chronicHealth;
        this.gastrointestinalIssues = gastrointestinalIssues;
        this.vitaminDeficiencies = vitaminDeficiencies;
        this.dietType = dietType;
        this.medications = medications;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getLevelOfFitness() {
        return levelOfFitness;
    }

    public void setLevelOfFitness(String levelOfFitness) {
        this.levelOfFitness = levelOfFitness;
    }

    public String getDesiredWeight() {
        return desiredWeight;
    }

    public void setDesiredWeight(String desiredWeight) {
        this.desiredWeight = desiredWeight;
    }

    public String getDailyCalorieCount() {
        return dailyCalorieCount;
    }

    public void setDailyCalorieCount(String dailyCalorieCount) {
        this.dailyCalorieCount = dailyCalorieCount;
    }

    public Integer getDailyCalorieSum(){
        return dailyCalorieSum;
    }

    public void setDailyCalorieSum(Integer dailyCalorieSum) {
        this.dailyCalorieSum = dailyCalorieSum;
    }

    public String getAllergiesOrIntolerances() { return allergiesOrIntolerances; }

    public void setAllergiesOrIntolerances(String allergiesOrIntolerances) { this.allergiesOrIntolerances = allergiesOrIntolerances; }

    public String getMedicalReasons() { return medicalReasons; }

    public void setMedicalReasons(String medicalReasons) { this.medicalReasons = medicalReasons; }

    public String getGastrointestinalIssues() { return gastrointestinalIssues; }

    public void setGastrointestinalIssues(String gastrointestinalIssues) { this.gastrointestinalIssues = gastrointestinalIssues; }

    public String getVitaminDeficiencies() { return vitaminDeficiencies; }

    public void setVitaminDeficiencies(String vitaminDeficiencies) {this.vitaminDeficiencies = vitaminDeficiencies; }


    public String getChronicHealth() { return chronicHealth; }

    public void setChronicHealth(String chronicHealth) { this.chronicHealth = chronicHealth; }

    public String getDietType() { return dietType; }

    public void setDietType(String dietType) { this.dietType = dietType; }

    @Override
    public String toString() {
        return "HealthData{" +
                "weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", objective='" + objective + '\'' +
                ", levelOfFitness='" + levelOfFitness + '\'' +
                ", desiredWeight='" + desiredWeight + '\'' +
                ", dailyCalorieCount='" + dailyCalorieCount + '\'' +
                '}';
    }

    private String weight;
    private String height;
    private String objective;
    private String levelOfFitness;
    private String desiredWeight;
    private String dailyCalorieCount;
    private Integer dailyCalorieSum;
    private String allergiesOrIntolerances;
    private String medicalReasons;
    private String chronicHealth;
    private String gastrointestinalIssues;
    private String vitaminDeficiencies;
    private String dietType;
    private String medications;
}
