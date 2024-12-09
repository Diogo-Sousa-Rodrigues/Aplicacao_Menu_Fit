package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

import java.util.ArrayList;
import java.util.List;

public class RecipesListController implements UserInitializable {
    public Button viewRecipeBtn;
    private BasicUser user;
    private SceneSwitcher sceneSwitcher;

    @FXML private ImageView logoImageView;
    @FXML private Label appTitleLabel;
    @FXML private Button goBackBtn;
    @FXML private VBox favoritesVBox;
    @FXML private VBox recentsVBox;
    @FXML private ScrollPane favoritesScrollPane; // Add ScrollPane for Favorites
    @FXML private ScrollPane recentsScrollPane;   // Add ScrollPane for Recents

    private final List<HBox> favoriteRecipes = new ArrayList<>();
    private final List<HBox> recentRecipes = new ArrayList<>();

    // Carregamento das imagens
    private final Image starImage = new Image(getClass().getResourceAsStream("/images/star.png"));
    private final Image starFilledImage = new Image(getClass().getResourceAsStream("/images/star-filled.png"));

    // Array de receitas - para o exemplo, passamos os nomes de receitas como Strings
    private final String[] recipes = {"Recipe 1", "Recipe 2", "Recipe 3", "Recipe 4","a","b"};

    public RecipesListController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {
        // Inicialização básica da interface, se necessário
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        loadRecentRecipes(recipes); // Passa o array de receitas para carregar dinamicamente
        loadFavoriteRecipes();
    }

    private void loadFavoriteRecipes() {
        favoritesVBox.getChildren().clear(); // Limpa a lista de favoritos no VBox

        for (HBox favorite : favoriteRecipes) {
            favoritesVBox.getChildren().add(favorite); // Adiciona cada favorito ao VBox
        }
    }

    private void loadRecentRecipes(String[] recipeNames) {
        recentsVBox.getChildren().clear(); // Limpa a lista de receitas recentes

        // Agora carregamos dinamicamente os nomes das receitas passados
        for (String recipeName : recipeNames) {
            recentsVBox.getChildren().add(createRecipeBox(recipeName)); // Cria e adiciona a caixa de receita
        }
    }

    private HBox createRecipeBox(String recipeName) {
        ImageView starIcon = new ImageView(starImage);
        starIcon.setFitWidth(20);
        starIcon.setFitHeight(20);
        starIcon.setOnMouseClicked(this::toggleFavorite);

        Label recipeLabel = new Label(recipeName);
        recipeLabel.setStyle("-fx-font-size: 16;");

        HBox recipeBox = new HBox(recipeLabel, starIcon);
        recipeBox.setSpacing(10);
        recipeBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");

        return recipeBox;
    }

    @FXML
    public void toggleFavorite(MouseEvent event) {
        ImageView clickedStar = (ImageView) event.getSource();
        HBox recipeBox = (HBox) clickedStar.getParent(); // Obtém o HBox pai da estrela clicada

        // Verifica se a receita está nos recentes ou favoritos
        boolean isFavorito = recentRecipes.remove(recipeBox);
        if (isFavorito) {
            favoriteRecipes.add(recipeBox);
            favoritesVBox.getChildren().add(recipeBox);
            recentsVBox.getChildren().remove(recipeBox);
            clickedStar.setImage(starFilledImage); // Atualiza para estrela preenchida
        } else {
            favoriteRecipes.remove(recipeBox);
            recentRecipes.add(recipeBox);
            recentsVBox.getChildren().add(recipeBox);
            favoritesVBox.getChildren().remove(recipeBox);
            clickedStar.setImage(starImage); // Atualiza para estrela vazia
        }

        System.out.println("Favoritos: " + favoriteRecipes.size());
        System.out.println("Recentes: " + recentRecipes.size());
    }

    @FXML
    public void editRecipe(MouseEvent event) {
        // Lógica para editar a receita (opcional)
    }

    @FXML
    public void deleteRecipe(MouseEvent event) {
        ImageView clickedTrash = (ImageView) event.getSource();
        HBox recipeBox = (HBox) clickedTrash.getParent().getParent(); // Obtém o HBox pai do ícone de lixo

        // Remove a receita das listas correspondentes
        favoriteRecipes.remove(recipeBox);
        recentRecipes.remove(recipeBox);

        // Atualiza a interface
        loadFavoriteRecipes();
        loadRecentRecipes(recipes); // Recarrega as receitas recentes
    }

    @FXML
    public void onGoBackClicked(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    public void handleViewRecipeBtn(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/Recipe.fxml", event, user);
    }
}