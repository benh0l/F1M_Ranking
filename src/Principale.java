import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class Principale {



    public static void main(String args[]) throws IOException {
        String linkFile = null;
        String linkImg = null;

        //FENETRE UTILISATEUR
        JFrame frame = new JFrame("F1Maniacs : Classements v2.2019.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JRadioButton bpilot = new JRadioButton("Pilotes");
        bpilot.setSize(new Dimension(100,50));
        bpilot.setBounds(150,0,100,50);
        bpilot.setSelected(true);
        JRadioButton bteam = new JRadioButton("Constructeurs");
        bteam.setSize(new Dimension(100,50));
        bteam.setBounds(250,0,150,50);
        ButtonGroup bg = new ButtonGroup();
        bg.add(bpilot);
        bg.add(bteam);

        JButton fichier = new JButton("Sélectionner un fichier");
        fichier.setSize(new Dimension(200, 50));
        fichier.setBounds(275, 50, 200, 50);
        JTextField status = new JTextField("");
        status.setEditable(false);
        status.setSize(new Dimension(250, 50));
        status.setBounds(15, 50, 250, 50);
        fichier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setApproveButtonText("Choix");
                jfc.showOpenDialog(null);
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    status.setText(jfc.getSelectedFile().getAbsolutePath());
                }
            }
        });

        JButton save = new JButton("Sauvegarder sous");
        save.setSize(new Dimension(200, 50));
        save.setBounds(275, 125, 200, 50);
        JTextField statusSave = new JTextField("");
        statusSave.setEditable(false);
        statusSave.setSize(new Dimension(250, 50));
        statusSave.setBounds(15, 125, 250, 50);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc2 = new JFileChooser();
                int rVal = jfc2.showSaveDialog(null);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    //statusSave.setText(jfc2.getSelectedFile().getName());
                    statusSave.setText(jfc2.getCurrentDirectory().toString() + "\\" + jfc2.getSelectedFile().getName());
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    //filename.setText("You pressed cancel");
                    statusSave.setText("");
                }
            }
        });

        JLabel nbColonneGauche = new JLabel("Nombre de pilotes dans la 1ère colonne (laisser vide pour 2 colonnes équilibrées) :");
        nbColonneGauche.setBounds(10,200,500,25);
        JFormattedTextField integerField;
        NumberFormat integerFieldFormatter;
        integerFieldFormatter = NumberFormat.getIntegerInstance();
        integerFieldFormatter.setMaximumFractionDigits(0);
        integerFieldFormatter.setMaximumIntegerDigits(2);
        integerField = new JFormattedTextField(integerFieldFormatter);
        integerField.setColumns(2);
        //integerField.setSize(new Dimension(50,50));
        integerField.setBounds(235,250,30,25);

        JButton run = new JButton("Créer l'image");
        run.setSize(150, 50);
        run.setBounds(175, 300, 150, 50);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!status.equals("") && !statusSave.equals("")) {
                    String lFile = status.getText();
                    String lImg = statusSave.getText();
                    try {
                        if(bpilot.isSelected()) {
                            String nb = integerField.getText();
                            int cpt = 0;
                            if(!nb.equals("")){
                                cpt = Integer.parseInt(nb);
                            }
                            Principale.creerImage(lFile, lImg, cpt);
                        }else{
                            Principale.creerImageConstructeur(lFile,lImg);
                        }
                        JOptionPane.showMessageDialog(frame,"Image créée avec succès!");
                        //System.exit(0);
                    }catch(IOException ioe){
                        JOptionPane.showMessageDialog(frame,"Le fichier indiqué n'existe pas ou n'est pas compatible","Inane error",JOptionPane.ERROR_MESSAGE);
                        ioe.printStackTrace();
                    }
                }
            }
        });


        frame.setLayout(null);
        frame.add(bpilot);
        frame.add(bteam);
        frame.add(status);
        frame.add(fichier);
        frame.add(statusSave);
        frame.add(save);
        frame.add(nbColonneGauche);
        frame.add(integerField);
        frame.add(run);

        frame.pack();
        frame.setSize(new Dimension(500, 400));
        //frame.setIconImage(new ImageIcon("/img/logo.png").getImage());
        frame.setVisible(true);
        Image im = new Image();
        im.creerIcone(frame);
    }

    public static void creerImage(String lien1, String lien2,int cpt) throws IOException{
        int width = 1920;
        int height = 1080;
        //TRAITEMENT DES DONNEES
        //File f = new File("src/tableur/tableurActuel.ods");
        File f = new File(lien1);
        Sheet sheet = SpreadSheet.createFromFile(f).getSheet(0);

        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

        for(int i = 3;i<41;i++){
            String nom = sheet.getCellAt(new String("C"+i)).getTextValue();
            if(!nom.equals("")){
                String equipe = sheet.getCellAt(new String("B"+i)).getTextValue();
                String points = sheet.getCellAt(new String("N"+i)).getTextValue();
                String rang = sheet.getCellAt(new String("O"+i)).getTextValue();
                Color couleur = sheet.getCellAt(new String("A"+i)).getStyle().getBackgroundColor();
                Joueur j = new Joueur(nom,equipe,Integer.parseInt(points),Integer.parseInt(rang),couleur);
                joueurs.add(j);
            }
        }

        Collections.sort(joueurs, new RankComparator());

        //TEST LISTE TRIEE PAR RANG
        /*
        for(int jj = 0;jj<joueurs.size();jj++){
            System.out.println(joueurs.get(jj).nom+"  "+joueurs.get(jj).rang);
        }
        */


        //CREATION IMAGE

        Image i = new Image(lien2,joueurs);
        i.creerImage(cpt);

        /*
        BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffImg.createGraphics();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font policeF1 = null;
        Font policeF1Bold = null;
        Font policeF1Wide = null;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        try {
            policeF1Bold = Font.createFont(Font.TRUETYPE_FONT, new File("/img/Formula1-Bold.otf")).deriveFont(25f);
            policeF1 = Font.createFont(Font.TRUETYPE_FONT, new File("/img/Formula1-Regular.otf")).deriveFont(25f);
            policeF1Wide = Font.createFont(Font.TRUETYPE_FONT, new File("/img/Formula1-Wide.otf")).deriveFont(28f);
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/img/Formula1-Bold.otf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/img/Formula1-Regular.otf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/img/Formula1-Wide.otf")));
        }catch(FontFormatException e){
            System.out.println("erreur police");
        }

        int x = 0;
        int y = 0;
        int compteur = 0;
        int limiteCpt = (joueurs.size()+1)/2;
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
        File file = new File(lien2+".png");
        System.out.println(lien2);
        System.out.println(file.getAbsolutePath());
        ImageIO.write(buffImg,"png",file);
        System.out.println("ok");
        */
    }

    public static void creerImageConstructeur(String lien1, String lien2) throws IOException{
        int width = 1920;
        int height = 1080;
        //TRAITEMENT DES DONNEES
        File f = new File(lien1);
        Sheet sheet = SpreadSheet.createFromFile(f).getSheet(1);

        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

        for(int i = 3;i<13;i++){
            String equipe = sheet.getCellAt(new String("C"+i)).getTextValue();
            if(!equipe.equals("")){
                String points = sheet.getCellAt(new String("D"+i)).getTextValue();
                String rang = sheet.getCellAt(new String("E"+i)).getTextValue();
                Joueur j = new Joueur("",equipe,Integer.parseInt(points),Integer.parseInt(rang),Color.WHITE);
                joueurs.add(j);
            }
        }

        Collections.sort(joueurs, new RankComparator());

        /*
        for(int jj = 0;jj<joueurs.size();jj++){
            System.out.println(joueurs.get(jj).nom+"  "+joueurs.get(jj).rang);
        }
        */

        Image i2 = new Image(lien2,joueurs);
        i2.creerImageTeam();
        /*
        BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffImg.createGraphics();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font policeF1 = null;
        Font policeF1Bold = null;
        Font policeF1Wide = null;
        try {
            policeF1Bold = Font.createFont(Font.TRUETYPE_FONT, new File("src/img/Formula1-Bold.otf")).deriveFont(30f);
            policeF1 = Font.createFont(Font.TRUETYPE_FONT, new File("src/img/Formula1-Regular.otf")).deriveFont(30f);
            policeF1Wide = Font.createFont(Font.TRUETYPE_FONT, new File("src/img/Formula1-Wide.otf")).deriveFont(36f);
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/img/Formula1-Bold.otf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/img/Formula1-Regular.otf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/img/Formula1-Wide.otf")));
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
                g2d.drawString(rangString,x+24,y+50);
            }else{
                g2d.drawString(rangString,x+20,y+50);
            }

            g2d.setColor(Color.WHITE);
            //g2d.drawString(j.equipe,x+35,y+16);
            g2d.setFont(policeF1);
            //g2d.drawString(j.equipe,x+120,y+16);
            g2d.setFont(policeF1Wide);

            String pointsString = Integer.toString(j.points);
            if(pointsString.length() == 1){
                g2d.drawString(pointsString,x+950,y+50);
            }else if(pointsString.length() == 2){
                g2d.drawString(pointsString,x+905,y+50);
            }else{
                g2d.drawString(pointsString,x+860,y+50);
            }

            y = y + 85;

        }

        g2d.dispose();

        //File file = new File("Classement.png");
        File file = new File(lien2);
        ImageIO.write(buffImg,"png",file);
        */
    }

}
