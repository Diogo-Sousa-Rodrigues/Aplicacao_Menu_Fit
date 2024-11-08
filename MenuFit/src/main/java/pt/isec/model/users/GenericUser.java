package pt.isec.model.users;

import java.util.Date;

public abstract class GenericUser implements User {
    public GenericUser(String firstName, String lastName, String email,
                       Date birthdate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
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

    private String firstName, lastName, email;

    private Date birthdate;

    private Gender gender;

    private HealthData healthData;

    private String currentRecipe;
}
