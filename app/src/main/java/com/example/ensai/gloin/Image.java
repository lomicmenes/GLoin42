package com.example.ensai.gloin;

/**
 * Created by ensai on 22/05/16.
 */
public class Image {
    public static final String IMAGE = "image";
    public static final String NAME = "name";
    public static final String BUSINESS_PLAN = "business-plan";
    public static final String PROFIT = "profit";
    public static final String MIN_PRICE = "min-price";
    public static final String CURRENT_PRICE = "current-price";
    public static final String MAX_PRICE = "max-price";
    public static final String STATUS = "status";
    public static final String NB_BUYER = "nb-buyer";
    public static final String SELLER = "seller";
    public static final String DUE_TO_SELLER = "due-to-seller";

    private String image;
    private String name;
    private String seller;
    private int minPrice;
    private int profit;
    private int currentPrice;
    private int maxPrice;
    private int nbBuyer = 0;
    private int dueToSeller = 0;



    private int price ;



    private  String  pseudo ;

    public Image(String name,int profit, int minPrice, int maxPrice){
        super();
        this.name=name;
        this.profit=profit;
        this.minPrice=minPrice;
        this.maxPrice=maxPrice;
        this.currentPrice=maxPrice;
    }

    public Image(String name,String seller, int profit, int minPrice, int maxPrice, int currentPrice, int nbBuyer, int dueToSeller){
        this.name=name;
        this.seller = seller;
        this.profit=profit;
        this.minPrice=minPrice;
        this.maxPrice=maxPrice;
        this.currentPrice=currentPrice;
        this.nbBuyer=nbBuyer;
        this.dueToSeller=dueToSeller;
    }


    public Image(String name , int currentPrice , String pseudo){
        this.name  = name ;
        this.price = currentPrice ;
        this.pseudo = pseudo ;
    };

    public Image(){

    }

    public Image clone(){
        Image image = new Image(this.name, this.seller, this.profit,this.minPrice,this.maxPrice,this.currentPrice,this.nbBuyer,this.dueToSeller);
        return image;
    }

    public void update(){
        nbBuyer++;
        int lastPrice=currentPrice;
        updateCurrentPrice();
        updateDueToSeller(lastPrice);
    }

    private void updateCurrentPrice(){
        if((nbBuyer+1)*currentPrice>profit){
            currentPrice=Math.max(profit/nbBuyer,minPrice);
        }
    }

    /**
     * Met à jour le profit reél du vendeur
     * Doit être utilisé après la mise à jour du nombre d'acheteur
     * Utilise le dernier prix.
     * @param lastPrice
     */
    private void updateDueToSeller(int lastPrice){
        dueToSeller+=currentPrice*nbBuyer-lastPrice*(nbBuyer-1);

    }




    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getNbBuyer() {
        return nbBuyer;
    }

    public void setNbBuyer(int nbBuyer) {
        this.nbBuyer = nbBuyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getDueToSeller() {
        return dueToSeller;
    }

    public void setDueToSeller(int dueToSeller) {
        this.dueToSeller = dueToSeller;
    }


    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
