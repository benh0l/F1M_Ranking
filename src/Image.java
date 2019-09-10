import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Image {

    String lien2;
    ArrayList<Joueur> joueurs;
    private String policeBoldPath = "/img/Formula1-Bold.otf";
    private String policeRegularPath = "/img/Formula1-Regular.otf";
    private String policeWidePath = "/img/Formula1-Wide.otf";

    public Image(String l,ArrayList<Joueur> a){
        lien2 = l;
        joueurs = a;
    }

    public Image(){

    }

    public void creerIcone(Frame f) throws IOException{
        String iconPath = "/img/logo.png";
        BufferedImage bi = ImageIO.read(this.getClass().getResourceAsStream(iconPath));
        f.setIconImage(bi);
    }

    public void creerImage(int cpt) throws IOException {
        int width = 1920;
        int height = 1080;
        BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffImg.createGraphics();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font policeF1 = null;
        Font policeF1Bold = null;
        Font policeF1Wide = null;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        try {
            policeF1Bold = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeBoldPath)).deriveFont(25f);
            policeF1 = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeRegularPath)).deriveFont(25f);
            policeF1Wide = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeWidePath)).deriveFont(28f);
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeBoldPath)));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeRegularPath)));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeWidePath)));
        }catch(FontFormatException e){
            System.out.println("erreur police");
        }

        int x = 0;
        int y = 0;
        int compteur = 0;
        int limiteCpt = (joueurs.size()+1)/2;
        if(cpt != 0){
            limiteCpt = cpt;
        }

        for(Joueur j : joueurs){

            g2d.drawImage(j.img,null,x,y);

            g2d.setColor(Color.BLACK);
            g2d.setFont(policeF1Bold);
            String rangString = Integer.toString(j.rang);

            if( rangString.length() == 1){
                g2d.drawString(rangString,x+15,y+37);
            }else{
                g2d.drawString(rangString,x+8,y+37);
            }

            g2d.setColor(j.color);
            g2d.fillRect(x+57,y+7,7,42);

            g2d.setColor(Color.WHITE);
            g2d.drawString(j.nom,x+70,y+37);
            g2d.setFont(policeF1);
            g2d.drawString(j.equipe,x+300,y+37);
            g2d.setFont(policeF1Wide);

            String pointsString = Integer.toString(j.points);
            if(pointsString.length() == 1){
                g2d.drawString(pointsString,x+680,y+37);
            }else if(pointsString.length() == 2){
                g2d.drawString(pointsString,x+650,y+37);
            }else{
                g2d.drawString(pointsString,x+620,y+37);
            }



            compteur++;
            if(compteur == limiteCpt){
                y = 0;
                x = width - 736;
            }else {
                y = y + 60;
            }

        }

        //g2d.drawImage(imgMercedes,null,0,0);

        g2d.dispose();

        //File file = new File("Classement.png");
        File file;
        String verifPng = lien2.substring(lien2.length()-4);
        if(verifPng.equals(".png")){
            file = new File(lien2);
        }else{
            file = new File(lien2+".png");
        }
        ImageIO.write(buffImg,"png",file);
    }

    public void creerImageTeam() throws IOException{
        int width = 1920;
        int height = 1080;
        BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffImg.createGraphics();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font policeF1 = null;
        Font policeF1Bold = null;
        Font policeF1Wide = null;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        try {
            policeF1Bold = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeBoldPath)).deriveFont(30f);
            policeF1 = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeRegularPath)).deriveFont(30f);
            policeF1Wide = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeWidePath)).deriveFont(36f);
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeBoldPath)));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeRegularPath)));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(policeWidePath)));
        }catch(FontFormatException e){
            System.out.println("erreur police");
        }

        int x = 450;
        int y = 0;
        //int compteur = 0;
        for(Joueur j : joueurs){

            g2d.drawImage(j.img,null,x,y);


            g2d.setColor(Color.BLACK);
            g2d.setFont(policeF1Bold);
            String rangString = Integer.toString(j.rang);

            if( rangString.length() == 1){
                g2d.drawString(rangString,x+15,y+40);
            }else{
                g2d.drawString(rangString,x+6,y+40);
            }

            g2d.setColor(j.color);
            g2d.fillRect(x+57,y+7,7,42);

            g2d.setColor(Color.WHITE);
            //g2d.drawString(j.equipe,x+35,y+16);
            g2d.setFont(policeF1);
            g2d.drawString(j.equipe,x+120,y+37);
            g2d.setFont(policeF1Wide);

            String pointsString = Integer.toString(j.points);
            if(pointsString.length() == 1){
                g2d.drawString(pointsString,x+650,y+40);
            }else if(pointsString.length() == 2){
                g2d.drawString(pointsString,x+605,y+40);
            }else{
                g2d.drawString(pointsString,x+560,y+40);
            }

            y = y + 85;

        }

        g2d.dispose();

        //File file = new File("Classement.png");
        File file;
        String verifPng = lien2.substring(lien2.length()-4);
        if(verifPng.equals(".png")){
            file = new File(lien2);
        }else{
            file = new File(lien2+".png");
        }
        ImageIO.write(buffImg,"png",file);

    }

}
