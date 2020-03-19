package pl.goreit.blog;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HackerRankTest {

    @Test
    public final void testHackerRank() {
        int[] integers = {3, 2, 1, 3};
        int bab = fewestCoins("rfkqyuqfjkxyqvnrtysfrzrmzlygfveulqfpdbhlqdqrrcrwdnxeuoqqeklaitgdphcspijthbsfyfvladzpbfudkklrwqaozmixrpifeffeclhbvfukbyeqfqojwtwosileeztxwjlkngbqqmbxqcqptkhhqrqdwfcayssyoqcjomwufbdfxudzhiftakczvhsybloetswcrfhpxprbsshsjxdfilebxwbctoayaxzfbjbkrxirimqpzwmshlpjhtazhbuxhwadlptoyeziwkmgsovqzgdixrpddzplcrwnqwqecyjyibfjykmjfqwltvzkqtpvolphckcyufdqmlglimklfzktgygdttnhcvpfdfbrpzlkvshwywshtdgmbqbkkxcvgumonmwvytbytnuqhmfjaqtgngcwkuzyamnerphfmwevhwlezohyeehbrcewjxvceziftiqtntfsrptugtiznorvonzjfeacgamayapwlmbzitzszhzkosvnknberbltlkggdgpljfisyltmmfvhybljvkypcflsaqevcijcyrgmqirzniaxakholawoydvchveigttxwpukzjfhxbrtspfttotafsngqvoijxuvqbztvaalsehzxbshnrvbykjqlrzzfmlvyoshiktodnsjjpqplciklzqrxloqxrudygjtyzleizmeainxslwhhjwslqendjvxjyghrveuvphknqtsdtwxcktmwwwsdthzmlmbhjkmouhpbqurqfxgqlojmwsomowsjvpvhznbsilhhdkbdxqgrgedpzchrgefeukmcowoeznwhpiiduxdnnlbnmyjyssbsococdzcuunkrfduvouaghhcyvmlkzaajpfpyljtyjjpyntsefxiswjutenuycpbcnmhfuqmmidmvknyxmywegmtunodvuzygvguxtrdsdfzfssmeluodjgdgzfmrazvndtaurdkugsbdpawxitivdubbqeonycaegxfjkklrfkraoheu{-truncated-}");

        System.out.println(bab);
    }

    public static int fewestCoins(String coins) {
        List<String> allLetters = Arrays.asList(coins.split(""))
                .stream()
                .distinct()
                .collect(Collectors.toList());

        String smallestString = coins;

        for (int i = 0; i < coins.length() - allLetters.size(); i++) {
            for (int j = i + allLetters.size(); j <= coins.length(); j++) {

                String substring = coins.substring(i, j);

                boolean stop = false;

                for (String letter : allLetters) {
                    if (!substring.contains(letter)) {
                        stop = true;
                    }
                }

                if (!stop) {
                    if( smallestString.length() > substring.length() ){
                        smallestString = substring;
                    }
                }
            }
        }
        return smallestString.length();
    }
}
