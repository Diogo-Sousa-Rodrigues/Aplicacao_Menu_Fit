package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;


public class RecipesListController implements UserInitializable {
    public Button viewRecipeBtn;
    private User user;
    private SceneSwitcher sceneSwitcher;

    @FXML private ImageView logoImageView;
    @FXML private Label appTitleLabel;
    @FXML private Button goBackBtn;
    @FXML private VBox favoritesVBox;
    @FXML private VBox recentsVBox;

    // Favorites
    @FXML private HBox favoriteRecipe1;
    @FXML private Label favoriteRecipe1Label;
    @FXML private ImageView favoriteRecipe1Star;
    @FXML private ImageView favoriteRecipe1Edit;
    @FXML private ImageView favoriteRecipe1Delete;

    @FXML private HBox favoriteRecipe2;
    @FXML private Label favoriteRecipe2Label;
    @FXML private ImageView favoriteRecipe2Star;
    @FXML private ImageView favoriteRecipe2Edit;
    @FXML private ImageView favoriteRecipe2Delete;

    // Recents
    @FXML private HBox recentRecipe1;
    @FXML private Label recentRecipe1Label;
    @FXML private ImageView recentRecipe1Star;
    @FXML private ImageView recentRecipe1Edit;
    @FXML private ImageView recentRecipe1Delete;

    public RecipesListController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void initializeUser(User user, BDManager bdManager) {
        this.user = user;
        loadFavoriteRecipes();
        loadRecentRecipes();
    }

    private void loadFavoriteRecipes() {

    }

    private void loadRecentRecipes() {

    }


    @FXML
    public void onGoBackClicked(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    @FXML
    public void toggleFavorite(MouseEvent event) {

    }

    @FXML
    public void editRecipe(MouseEvent event) {

    }

    @FXML
    public void deleteRecipe(MouseEvent event) {

    }

    public void handleViewRecipeBtn(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/Recipe.fxml", event, user);
    }
}