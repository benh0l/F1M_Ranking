import java.util.Comparator;

public class RankComparator implements Comparator<Joueur> {

    @Override
    public int compare(Joueur j1, Joueur j2) {
        return j1.rang - j2.rang;
    }
}
