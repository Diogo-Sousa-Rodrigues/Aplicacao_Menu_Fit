package pt.isec.model.users;

import pt.isec.model.meals.Meal;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public abstract class GenericUser implements User {
    public GenericUser(Integer idUser, String firstName, String lastName, String email,
                       Date birthdate, Gender gender) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    @Override
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public Integer getIdUser() {
        return idUser;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Date getBirthdate() {
        return this.birthdate;
    }

    @Override
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public Gender getGender() {
        return this.gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public HealthData getHealthData() { return healthData; }

    @Override
    public void setHealthData(HealthData healthData) {this.healthData = healthData;}

    @Override
    public String getCurrentRecipe(){
        return currentRecipe;
    }

    @Override
    public void setCurrentRecipe(String recipe){
        this.currentRecipe = recipe;
    }

    @Override
    public int getCurrentMealIndex() {
        return currentMealIndex;
    }

    @Override
    public void setCurrentMealIndex(int currentMeal) {
        this.currentMealIndex = currentMeal;
    }

    @Override
    public Meal getCurrentMeal(){
        return currentMeal;
    }

    @Override
    public void setCurrentMeal(Meal currentMeal) {
        this.currentMeal = currentMeal;
    }

    @Override
    public List<Meal> getExtraMeals() {
        return extraMeals;
    }

    @Override
    public void addExtraMeal(Meal meal) {
        extraMeals.add(meal);
    }

    @Override
    public int getAge() {
        LocalDate birthLocalDate = this.birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate currentDate = LocalDate.now();

        return Period.between(birthLocalDate, currentDate).getYears();
    }

    @Override
    public void setPreferedWeightUnit(String preferedWeightUnit) {
        this.preferedWeightUnit = preferedWeightUnit;
    }

    @Override
    public String getPreferedWeightUnit() {
        return preferedWeightUnit;
    }

    @Override
    public void setTimeBudget(TimeBudget timeBudget){
        this.timeBudget = timeBudget;
    }
    @Override
    public TimeBudget getTimeBudget() {
        return timeBudget;
    }

    private String firstName, lastName, email;
    private Integer idUser;

    private Date birthdate;

    private Gender gender;

    public HealthData healthData;

    private String currentRecipe;

    private int currentMealIndex;

    private List<Meal> extraMeals;

    private Meal currentMeal;

    private String preferedWeightUnit;
    private TimeBudget timeBudget;
}
