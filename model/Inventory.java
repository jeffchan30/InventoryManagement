package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Inventory class. */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** This method looks up Part by ID.
     @param partId Looks up Part by ID.
     @return returns Part.
     */

    public static Part lookupPart(int partId){

        ObservableList<Part> allParts = getAllParts();

        for(int i = 0; i < allParts.size(); i++){
            Part part = allParts.get(i);

            if(part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /** This method looks up Product by ID.
     @param productId Looks up Part by ID.
     @return returns Product.
     */

    public static Product lookupProduct(int productId){

        ObservableList<Product> allProducts = getAllProducts();

        for(int i = 0; i < allProducts.size(); i++){
            Product prod = allProducts.get(i);

            if(prod.getId() == productId){
                return prod;
            }
        }
        return null;
    }

    /** This method looks up Product by string.
     @param partName Looks up Part by string.
     @return returns Part.
     */

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = getAllParts();

        for(Part part : allParts){
            if(part.getName().contains(partName)){
                namedParts.add(part);

            }

        }

        return namedParts;

    }

    /** This method looks up Product by string.
     @param productName Looks up Part by string.
     @return returns Product.
     */

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        
        ObservableList<Product> allProducts = getAllProducts();

        for(Product prod : allProducts){
            if(prod.getName().contains(productName)){
                namedProducts.add(prod);

            }

        }

        return namedProducts;

    }

/** This method updates the product.
 @param newProduct updates the product.
 */

    public static void updateProduct(int id, Product newProduct){
        int index = -1;

        for(Product prod : Inventory.getAllProducts()){
            index++;

            if(prod.getId() == id){
                Inventory.getAllProducts().set(index, newProduct);
            }

        }

    }

    /** This method deletes the product.
     @param selectedProduct selects a product to delete.
     @return returns boolean.
     */

    public static boolean deleteProduct(Product selectedProduct){
        for(Product product : Inventory.getAllProducts()){
            
            if(product.getId() == selectedProduct.getId()){
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }

    /** This method deletes a part.
     @param selectedPart selects a part to delete.
     @return returns boolean.
     */

    public static boolean deletePart(Part selectedPart){
        for(Part part: Inventory.getAllParts()){

            if(part.getId() == selectedPart.getId()){
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }

    /** This method updates the part.
     @param newPart updates the part.
     */

    public static void updatePart(int id, Part newPart){
        int index = -1;

        for(Part part : Inventory.getAllParts()){
            index++;

            if(part.getId() == id){
                Inventory.getAllParts().set(index, newPart);
            }

        }


    }

    /**
     This method gets all parts.
     @return returns all parts.
     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     This method gets all products.
     @return returns all products.
     */

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}








