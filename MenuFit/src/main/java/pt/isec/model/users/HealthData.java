package pt.isec.model.users;

public class HealthData {
    public HealthData(String weight, String height,
                      String objective, String levelOfFitness,
                      String desiredWeight, String dailyCalorieCount) {
        this.weight = weight;
        this.height = height;
        this.objective = objective;
        this.levelOfFitness = levelOfFitness;
        this.desiredWeight = desiredWeight;
        this.dailyCalorieCount = dailyCalorieCount;
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

    public void setDailyCalorieGoal(String dailyCalorieCount) {
        this.dailyCalorieCount = dailyCalorieCount;
    }

    public Integer getDailyCalorieSum(){
        return dailyCalorieSum;
    }

    public void setDailyCalorieSum(Integer dailyCalorieSum) {
        this.dailyCalorieSum = dailyCalorieSum;
    }

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
}
