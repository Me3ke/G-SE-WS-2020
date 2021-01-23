package de.techfak.se.mmoebius;

import de.techfak.se.mmoebius.model.Board;
import de.techfak.se.mmoebius.model.Game;
import javafx.scene.paint.Color;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

class MainTest {

    @Test
    void test() {
        Assertions.assertThat(true).isTrue();
    }


    private static final String[] args = {"-f","./spielfeld.txt"};
    private static final Game game = new Game(args);
    private static Board board;

    @BeforeAll
    static void beforeAll() {
        game.createBoard();
        board = game.getBoard();
    }

    @AfterEach
    void afterEach() {
        board.printBoard();
        game.createBoard();
        board = game.getBoard();
    }
    // ------------------------Scenario 1--------------------------------
    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn er in sich zusammenhängend ist,
     * d.h. alle angekreuzten Felder grenzen im Sinne der Vierer-Nachbarschaft aneinander an.
     * Äquivalenzklasse: Die angekreuzten Felder hängen zusammen (Normalablauf)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Würfelnummern: 4,1,1
     * Würfelfarben:  o,g,r
     * Zug: F4,E4,E5,D5
     */

    @Test
    public void testNormal() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {2,2,2,2};
        int[] cols2 = {6,5,4,3};
        int[] numbers2 = {4};
        Color[] colors2 = {Color.GREEN};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {3,3,4,4};
        int[] cols3 = {5,4,4,3};
        int[] numbers3 = {4,1,1};
        Color[] colors3 = {Color.ORANGE, Color.GREEN, Color.RED};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(true);
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn er in sich zusammenhängend ist,
     * d.h. alle angekreuzten Felder grenzen im Sinne der Vierer-Nachbarschaft aneinander an.
     * Äquivalenzklasse: Die angekreuzten Felder sind nicht zusammenhängend,
     *                   d.h. mindestens ein Feld steht mit keinem anderen Feld des
     *                   Zuges in Vierer-Nachbarschaft (Eingabefehler #1)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: F4,E4,E5,B5
     */

    @Test
    public void testWithOneOut() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {2,2,2,2};
        int[] cols2 = {6,5,4,3};
        int[] numbers2 = {4};
        Color[] colors2 = {Color.GREEN};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {3,3,4,4};
        int[] cols3 = {5,4,4,1};
        int[] numbers3 = {4};
        Color[] colors3 = {Color.ORANGE};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }

    // ------------------------Scenario 2--------------------------------
    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn keines der angekreuzten Felder
     * bereits angekreuzt ist.
     * Äquivalenzklasse: Die angekreuzten Felder sind alle nicht angekreuzt (Normalablauf)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: F4,E4,E5,D5
     * @see testNormal()
     */

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn keines der angekreuzten Felder
     * bereits angekreuzt ist.
     * Äquivalenzklasse: Eines der Felder ist schon angekreuzt (nicht das Erste) (Eingabefehler #1)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y G B B B o y y y
     * 2 o g y g y y o o r b b o o g g
     * 3 b g r g g g g r r r y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: J2,K2,K1
     */

    @Test
    public void testOneCrossed() {
        int[] rows1 = {0};
        int[] cols1 = {7};
        int[] numbers1 = {1};
        Color[] colors1 = {Color.GREEN};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {0,0,0};
        int[] cols2 = {8,9,10};
        int[] numbers2 = {3};
        Color[] colors2 = {Color.BLUE};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {1,1,0};
        int[] cols3 = {9,9,10};
        int[] numbers3 = {3};
        Color[] colors3 = {Color.BLUE};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn keines der angekreuzten Felder
     * bereits angekreuzt ist.
     * Äquivalenzklasse: Das erste Feld des Zuges ist schon angekreuzt (Eingabefehler #2)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y G B B B o y y y
     * 2 o g y g y y o o r b b o o g g
     * 3 b g r g g g g r r r y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: J1,I1,J2,K2
     */

    @Test
    public void testFirstCrossed() {
        int[] rows1 = {0};
        int[] cols1 = {7};
        int[] numbers1 = {1};
        Color[] colors1 = {Color.GREEN};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {0,0,0};
        int[] cols2 = {8,9,10};
        int[] numbers2 = {3};
        Color[] colors2 = {Color.BLUE};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {0,0,1,1};
        int[] cols3 = {9,8,9,10};
        int[] numbers3 = {4};
        Color[] colors3 = {Color.BLUE};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }

    // ------------------------Scenario 3--------------------------------
    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn das erste Kreuz des Zuges in Spalte H liegt,
     * oder das erste Kreuz angrenzend zu einem anderen ist.
     * Äquivalenzklasse: Liegt in Spalte H (Normalablauf)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: H6,H7,I6,I7
     */

    @Test
    public void testInColH() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {3,3};
        int[] cols2 = {8,9};
        int[] numbers2 = {2};
        Color[] colors2 = {Color.GREEN};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {5,6,5,6};
        int[] cols3 = {7,7,8,8};
        int[] numbers3 = {4};
        Color[] colors3 = {Color.YELLOW};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(true);
    }

    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn das erste Kreuz des Zuges in Spalte H liegt,
     * oder das erste Kreuz angrenzend zu einem anderen ist.
     * Äquivalenzklasse: Grenzt nicht an ein gesetztes Kreuz und liegt nicht in Spalte H (Eingabefehler #1)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r g g g g R R R y y o g g
     * 4 b r r g o o b b G G y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: F7,E7,D7,C7
     */

    @Test
    public void notInColHAndNotNeighbourToCross() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {3,3};
        int[] cols2 = {8,9};
        int[] numbers2 = {2};
        Color[] colors2 = {Color.GREEN};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {6,6,6,6};
        int[] cols3 = {5,4,3,2};
        int[] numbers3 = {4};
        Color[] colors3 = {Color.BLUE};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }

    // ------------------------Scenario 4--------------------------------
    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn alle angekreuzten Felder die selbe Farbe haben wie
     * das erst angekreuzte Feld.
     * Äquivalenzklasse: Alle Felder haben die selbe Farbe (Normalablauf)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: F4,E4,E5,D5
     * @see testNormal()
     */


    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn alle angekreuzten Felder die selbe Farbe haben wie
     * das erst angekreuzte Feld.
     * Äquivalenzklasse: Eines der Felder hat eine andere Farbe wie das erste (Eingabefehlder #1)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y G b b b o y y y
     * 2 o g y g y y O O R b b o o g g
     * 3 b g r g g g g R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: G1,F1,F2,F3
     */

    @Test
    public void testOneColorDiff() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {0};
        int[] cols2 = {7};
        int[] numbers2 = {1};
        Color[] colors2 = {Color.GREEN};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {0,0,1,2};
        int[] cols3 = {6,5,5,5};
        int[] numbers3 = {4};
        Color[] colors3 = {Color.YELLOW};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }

    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn alle angekreuzten Felder die selbe Farbe haben wie
     * das erst angekreuzte Feld.
     * Äquivalenzklasse: das erste Feld hat eine ander Farbe als alle Anderen (Eingabefehlder #2)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y O O R b b o o g g
     * 3 b g r g g g g R R R y y o g g
     * 4 b r r g o o b B g g y y o r b
     * 5 r o o o o r b B o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Zug: J4,K4,L4,L3
     */

    @Test
    public void testFirstColorDiff() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {3,4};
        int[] cols2 = {7,7};
        int[] numbers2 = {2};
        Color[] colors2 = {Color.BLUE};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {3,3,3,2};
        int[] cols3 = {9,10,11,11};
        int[] numbers3 = {4};
        Color[] colors3 = {Color.YELLOW};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }

    // ------------------------Scenario 5--------------------------------
    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn die Anzahl der angekreuzten Felder einer der Würfel
     * Zahlen entsprechen und die Farbe einer der Würfel Farben
     * Äquivalenzklasse: Zug stimmt mit Beiden Würfeln überein (Normalablauf)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Würfelnummern: 4,1,1
     * Würfelfarben:  o,g,r
     * Zug: F4,E4,E5,D5
     * @see testNormal()
     */


    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn die Anzahl der angekreuzten Felder einer der Würfel
     * Zahlen entsprechen und die Farbe einer der Würfel Farben
     * Äquivalenzklasse: Zug stimmt mit Farbe überein aber nicht mit Zahl (Eingabefehler #1)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Würfelnummern: 4,1,1
     * Würfelfarben:  o,g,r
     * Zug: C3,C4
     */

    @Test
    public void testDiceNumberFalse() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {2,2,2,2};
        int[] cols2 = {6,5,4,3};
        int[] numbers2 = {4};
        Color[] colors2 = {Color.GREEN};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {2,3};
        int[] cols3 = {2,2};
        int[] numbers3 = {4,1,1};
        Color[] colors3 = {Color.ORANGE,Color.GREEN,Color.RED};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }

    /**
     * Akzeptanzkriterium: Der Zug ist dann valide, wenn die Anzahl der angekreuzten Felder einer der Würfel
     * Zahlen entsprechen und die Farbe einer der Würfel Farben
     * Äquivalenzklasse: Zug stimmt mit Zahl überein aber nicht mit Farbe (Eingabefehler #2)
     * Ausgangszustand:
     *
     *   A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R y y o g g
     * 4 b r r g o o b b g g y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     *
     * Würfelnummern: 1,2,3
     * Würfelfarben:  y,y,b
     * Zug: H2,G2
     */

    @Test
    public void testDiceColorFalse() {
        int[] rows1 = {2,2,1,2};
        int[] cols1 = {7,8,8,9};
        int[] numbers1 = {4};
        Color[] colors1 = {Color.RED};
        board.validate(rows1, cols1, numbers1, colors1);
        int[] rows2 = {2,2,2,2};
        int[] cols2 = {6,5,4,3};
        int[] numbers2 = {4};
        Color[] colors2 = {Color.GREEN};
        board.validate(rows2, cols2, numbers2, colors2);
        int[] rows3 = {1,1};
        int[] cols3 = {7,6};
        int[] numbers3 = {1,2,3};
        Color[] colors3 = {Color.YELLOW,Color.YELLOW,Color.BLUE};
        boolean result = board.validate(rows3, cols3, numbers3, colors3);
        Assertions.assertThat(result).isEqualTo(false);
    }
}

