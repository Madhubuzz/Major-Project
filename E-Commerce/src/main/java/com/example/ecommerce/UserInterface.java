package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {

    GridPane loginPage;

    HBox headerBar;
    HBox footerBar;
    Button signInButton;

    Label welcomeLabel;

    VBox body;

    Customer loggedInCustomer;

    ProductList productlist = new ProductList();

    VBox productPage;

    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();



     public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
        //root.getChildren().add(loginPage);// method to add children to pane
         root.setTop(headerBar);


        //root.setCenter(loginPage);
         body = new VBox();
         body.setPadding(new Insets(10));
         body.setAlignment(Pos.CENTER);
         root.setCenter(body);
         productPage = productlist.getAllProducts();
         body.getChildren().add(productPage);

         root.setBottom(footerBar);


        return root;
    }
    public UserInterface(){
         createLoginPage();
         createHeaderBar();
         createFooterBar();
    }

    private void createLoginPage(){
        Text usernameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("madhu@gmail.com");
        userName.setPromptText("Type your UserName Here");
        PasswordField password = new PasswordField();
        password.setText("abc879");
        password.setPromptText("Type your Password Here");
        Label messageLabel = new Label("Hi");

        Button loginButton = new Button("Login");

        loginPage =new GridPane();
        //loginPage.setStyle("-fx-background-color: pink;");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(usernameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(messageLabel,0,2);
        loginPage.add(loginButton,1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name, pass);
                if(loggedInCustomer!=null) {
                    messageLabel.setText("Welcome " + loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome " + loggedInCustomer.getName() + " ! ");
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
                else{
                    messageLabel.setText("Login failed! Please give correct user name and password");
                }

            }
        });
    }

    private void createHeaderBar(){
         Button homeButton = new Button();
         Image image = new Image("C:\\Users\\madhu\\IdeaProjects\\E-Commerce\\src\\Amazon-Logo.jpg");
         ImageView imageView = new ImageView();
         imageView.setImage(image);
         imageView.setFitHeight(20);
         imageView.setFitWidth(60);
         homeButton.setGraphic(imageView);

         TextField searchBar = new TextField();
         searchBar.setPromptText("Search here");
         searchBar.setPrefWidth(300);

         Button searchButton = new Button();
         Image image3 = new Image("C:\\Users\\madhu\\IdeaProjects\\E-Commerce\\src\\yellow-search-icon-button-png-image-11640084015fk4pehouam.png");
         ImageView image3View = new ImageView();
         image3View.setImage(image3);
         image3View.setFitHeight(20);
         image3View.setFitWidth(20);
         searchButton.setGraphic(image3View);

         Button orderButton = new Button("Orders");


         signInButton = new Button("Sign In");
         welcomeLabel = new Label();

         Button cartButton = new Button();
         Image image2 = new Image("C:\\Users\\madhu\\IdeaProjects\\E-Commerce\\src\\images.png");
         ImageView image2View = new ImageView();
         image2View.setImage(image2);
         image2View.setFitHeight(20);
         image2View.setFitWidth(20);
         cartButton.setGraphic(image2View);

         headerBar = new HBox();
         headerBar.setStyle("-fx-background-color: Lavender;");
         headerBar.setPadding(new Insets(10));
         headerBar.setSpacing(10);
         headerBar.setAlignment(Pos.CENTER);
         headerBar.getChildren().addAll(homeButton,searchBar,searchButton, signInButton,cartButton,orderButton);

         signInButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(loginPage);
                 headerBar.getChildren().remove(signInButton);
             }
         });

         cartButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 VBox prodPage = productlist.getProductInCart(itemsInCart);
                 prodPage.setAlignment(Pos.CENTER);
                 prodPage.setSpacing(10);
                 prodPage.getChildren().add(placeOrderButton);
                 body.getChildren().add(prodPage);
                 footerBar.setVisible(false);
             }
         });

         placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 //need list of products and customers

                 if(itemsInCart==null){
                     //please select the order first to place the order
                     showDialog("Please add products in the cart to place the order!");
                     return;
                 }
                 if(loggedInCustomer==null)
                 {
                     showDialog("Please Login to place the order!");
                     return;
                 }
                 int count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                 if(count!=0)
                 {
                     showDialog("Order for "+count+" products placed successfully!");
                 }
                 else{
                     showDialog("Order failed!");
                 }

             }
         });

         homeButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(productPage);
                 footerBar.setVisible(true);
                 if(loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton)==-1);
                 {
                     headerBar.getChildren().add(signInButton);
                 }

             }
         });



    }

    private void createFooterBar(){

        Button buyNowButton = new Button("Buy Now");
        Button addtocartButton = new Button("Add to Cart");

        footerBar = new HBox();
        footerBar.setStyle("-fx-background-color: Lavender;");
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addtocartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productlist.getSelectedProduct();
                if(product==null){
                   //please select the order first to place the order
                    showDialog("Please select the order to place the order!");
                    return;
                }
                if(loggedInCustomer==null)
                {
                    showDialog("Please Login to place the order!");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer,product);
                if(status==true)
                {
                    showDialog("Order placed successfully!");
                }
                else{
                    showDialog("Order failed!");
                }
            }
        });

        addtocartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productlist.getSelectedProduct();
                if(product==null){
                    //please select the order first to place the order
                    showDialog("Please select a product to add to Cart!");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Product added to cart successfully!");

            }
        });
    }

    private void showDialog(String message){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.setTitle("Message");
         alert.showAndWait();
    }
}
