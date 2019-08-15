import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

public class Joueur{

    public String nom, equipe;
    public int points, rang;
    public BufferedImage img;

    private String mercedesPath = "/img/Mercedes.jpg", mercedesTeamPath = "/img/team/mercedes.jpg", ferrariPath = "/img/Ferrari.jpg", ferrariTeamPath = "/img/team/ferrari.jpg", redbullpath = "/img/RedBull.jpg", redbullTeamPath = "/img/team/redbull.jpg",
    renaultPath = "/img/Renault.jpg", renaultTeamPath = "/img/team/renault.jpg", haasPath = "/img/Haas.jpg",haasTeamPath = "/img/team/haas.jpg", mclarenPath = "/img/McLaren.jpg", mclarenTeamPath = "img/team/mclaren.jpg",
    tororossoPath = "/img/ToroRosso.jpg", tororossoTeamPath = "/img/team/tororosso.jpg", sauberPath = "/img/Sauber.jpg", sauberTeamPath = "/img/team/sauber.jpg", forceindiaPath = "/img/ForceIndia.jpg", forceindiaTeamPath = "/img/team/forceindia.jpg",
    williamsPath = "/img/Williams.jpg", williamsTeamPath = "/img/team/williams.jpg",defaultPath = "/img/default.jpg";

    public Joueur(String n, String e, int p, int r) throws IOException{
        nom = n;
        equipe = e;
        points = p;
        rang = r;
        this.setImg();
    }

    public void setImg() throws IOException {
        switch(equipe){
            case "Mercedes":
            case "Remplaçant Mercedes":
                //img = ImageIO.read(new File("img/Mercedes.jpg"));
                img = ImageIO.read(this.getClass().getResourceAsStream(mercedesPath));
                break;
            case "MERCEDES":
                img = ImageIO.read(this.getClass().getResourceAsStream(mercedesTeamPath));
                break;
            case "Ferrari":
            case "Remplaçant Ferrari":
                img = ImageIO.read(this.getClass().getResourceAsStream(ferrariPath));
                break;
            case "FERRARI":
                img = ImageIO.read(this.getClass().getResourceAsStream(ferrariTeamPath));
                break;
            case "RedBull TAG Heuer":
            case "Red Bull TAG Heuer":
            case "Remplaçant RedBull":
                img = ImageIO.read(this.getClass().getResourceAsStream(redbullpath));
                break;
            case "REDBULL Tag Heuer":
            case "RED BULL Tag Heuer":
                img = ImageIO.read(this.getClass().getResourceAsStream(redbullTeamPath));
                break;
            case "Renault":
            case "Remplaçant Renault":
                img = ImageIO.read(this.getClass().getResourceAsStream(renaultPath));
                break;
            case "RENAULT":
                img = ImageIO.read(this.getClass().getResourceAsStream(renaultTeamPath));
                break;
            case "Haas Ferrari":
            case "Remplaçant Haas":
                img = ImageIO.read(this.getClass().getResourceAsStream(haasPath));
                break;
            case "HAAS Ferrari":
                img = ImageIO.read(this.getClass().getResourceAsStream(haasTeamPath));
                break;
            case"McLaren Renault":
            case "Remplaçant McLaren":
                img = ImageIO.read(this.getClass().getResourceAsStream(mclarenPath));
                break;
            case "MCLAREN Renault":
                img = ImageIO.read(this.getClass().getResourceAsStream(mclarenTeamPath));
                break;
            case "Sauber Ferrari":
            case "Remplaçant Sauber":
                img = ImageIO.read(this.getClass().getResourceAsStream(sauberPath));
                break;
            case "SAUBER Ferrari":
                img = ImageIO.read(this.getClass().getResourceAsStream(sauberTeamPath));
                break;
            case "Force India":
            case "Remplaçant Force India":
                img = ImageIO.read(this.getClass().getResourceAsStream(forceindiaPath));
                break;
            case "FORCE INDIA Mercedes":
                img = ImageIO.read(this.getClass().getResourceAsStream(forceindiaTeamPath));
                break;
            case "Toro Rosso Honda":
            case "Remplaçant Toro Rosso":
                img = ImageIO.read(this.getClass().getResourceAsStream(tororossoPath));
                break;
            case "TORO ROSSO Honda":
                img = ImageIO.read(this.getClass().getResourceAsStream(tororossoTeamPath));
                break;
            case "Williams Mercedes":
            case "Remplaçant Williams":
                img = ImageIO.read(this.getClass().getResourceAsStream(williamsPath));
                break;
            case "WILLIAMS Mercedes":
                img = ImageIO.read(this.getClass().getResourceAsStream(williamsTeamPath));
                break;
            default:
                img = ImageIO.read(this.getClass().getResourceAsStream(defaultPath));
                break;
        }
    }


}
