
package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hero extends PositionDimension implements Collidable , MousePress {
    private boolean positionInverted;
    private Sound death_sound ; /*="sample_audio.mp3";*/;
    Image image;
    ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    private static void printDirectoryContents(Path directory) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                System.out.println(path.getFileName());
                if (Files.isDirectory(path)) {
                    printDirectoryContents(path);
                }
            }
        }
    }
    public Hero(){
//        String absolutePathString = "C:\\Users\\baljyot\\OneDrive\\Desktop\\Ap_project\\src\\main\\resources\\images\\character_stickhero_javafx.png";
//        Path absolutePath = Paths.get(absolutePathString);
//
//        // Convert absolute path to relative path
//        Path relativePath = convertToRelativePath(absolutePath);
//        System.out.println("Relative Path: " + relativePath);

        // Convert relative path back to absolute path
        Path currentPath = Paths.get(System.getProperty("user.dir"));

        // Example absolute path
        Path absolutePath = Paths.get("C:\\Users\\baljyot\\OneDrive\\Desktop\\Ap_project\\src\\main\\resources\\images\\character_stickhero_javafx.png");

        // Relativize the absolute path to the current working directory
        Path relativePath = currentPath.relativize(absolutePath);

        // Print the relative path
        System.out.println("Relative Path: " + relativePath);


//        image = new Image(String.valueOf(currentPath.resolve("src").resolve("main").resolve("resources").resolve("images").resolve("character_stickhero_javafx.png")));
        image=new Image(getClass().getResourceAsStream("images/character_stickhero_javafx.png"));
        imageView= new ImageView(image);
        imageView.setFitWidth(33);
        imageView.setFitHeight(30);

    }

    private static Path convertToRelativePath(Path absolutePath) {
        // Assuming there is a base directory, for example, the current working directory
        Path baseDirectory = Paths.get(System.getProperty("user.dir"));

        // Resolve the relative path against the base directory
        Path relativePath = baseDirectory.relativize(absolutePath);

        return relativePath;
    }



    public Pair<TranslateTransition,ImageView>  returnTransition(int position, Rectangle r)
    {
        imageView.setX(r.getX()+r.getWidth()-36);
        imageView.setY(454);
        TranslateTransition imageTransition = new TranslateTransition(Duration.seconds(0.6 ), imageView);
        imageTransition.setFromX(position);
        imageTransition.setToX(0);
        return new Pair<>(imageTransition,imageView);

    }


    @Override
    public boolean didCollide(Collidable c) {
        // Check if c is a cherry or a pillar
        return false;
    }

    @Override
    public void onCollision() {

    }

    private void invertHero() {

    }

    public void handleMouseReleased(MouseEvent event) {
        this.invertHero();
    }

    public void handleMousePress(MouseEvent event){
        this.invertHero();
    }

    public void run() {

    }

    public void onDeath() {

    }
    private void deathSound()
    {

    }

    @Override
    public void add_to_screen(Pane pane,int start_position,int end_position,Hero hero) {


    }

    private void moveHeroToPillarCenter(Pillar p) {

    }
}
