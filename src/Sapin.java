import java.util.Scanner;
public class Sapin {


    /*
     * méthode remplissant le triangle, on construit à chaque ligne
     * le tableau de char de la dimension
     *  correspondante (1 a la ligne 0, 3 à la ligne 1, etc)
     */
    static void remplirTriangle(char[][] triangle, char c) {
        int nbLigne = triangle.length;
        int nbColonne;

        for (int i = 0; i < nbLigne; ++i) {
            nbColonne = i*2 + 1;
            triangle[i] = new char[nbColonne];
            for (int j = 0; j < nbColonne; ++j) {
                triangle[i][j] = c;
            }
        }

    }


    /*
     * Méthode ajoutant des guirlandes au triangle qui représente un sapin
     * sans decorations.
     */
    static void mettreGuirlande(char[][] sapinTriangle, String guirlande) {
        int nbLigne = sapinTriangle.length;

        // La variable pointeurChar stocke l'indice du prochain caractère de
        // la guirlande à placer
        int pointeurChar = 0;
        int longueurChaine = guirlande.length();

        // Cette variable permet de différencier le cas où l'on pose les guirlandes
        // complètement, et le cas où l'on étend une guirlande sur les lignes
        // supplémentaires
        boolean completerGuirlande = false;

        //les deux variables d'indice que l'on fera varier
        int i = 0;
        int j = 0;


        while(i < nbLigne) {
            // on vérifie d'abord que nous sommes dans une ligne impaire
            // ou alors que l'on est en train de finir d'étendre une
            // guirlande sur les lignes suivantes
            if (i % 2 == 1 || completerGuirlande) {

                // avec ces 2 instructions on remplace le caractère (i,j)
                // du sapin par le prochain caractère à placer
                // de la guirlande. pointeurChar donne la position
                // de ce caractère dans la guirlande. On incrémente ensuite
                // cette position en prenant le reste de la division
                // par rapport à la longueur de la guirlande
                //(quand on dépasse la position maximale possible, pointeurChar
                // reprend la valeur 0)
                sapinTriangle[i][j] = guirlande.charAt(pointeurChar);
                pointeurChar = (pointeurChar + 1) % longueurChaine;

                //ensuite il faut incrémenter les i et j en fonction de la
                //situation

                if ((j >= sapinTriangle[i].length - 1 && pointeurChar != 0) || completerGuirlande) {
                    // Ce if gère le cas assez compliqué où il faut finir d'étendre la
                    // guirlande sur la ligne d'en dessous. On utilise un boolean pour entrer
                    // dans ce if jusqu'à ce que pointeurChar vaille 0; à ce moment on sait
                    // que l'on a finit d'étendre la guirlande et on remet le boolean à faux. On
                    // incrémente alors le j d'une quantité aléatoire. On n'incrémente pas la ligne car il
                    // est tout à fait possible d'avoir couvert la deuxième ligne entièrement et
                    // d'être revenu à une ligne impaire, dans ce cas on doit simplement continuer
                    // avec la guirlande suivante

                    if (pointeurChar == 0) {
                        completerGuirlande = false;
                        j += random() + 1;
                    } else {
                        // sinon on gère les 3 cas possibles:
                        // 1.  on arrive pour la première fois ici et on change de ligne et on affecte j
                        // au dernier index de la ligne d'en dessous
                        //  sinon, on est en train de compléter la guirlande (variable à vrai), dans ce cas :
                        // 2. soit la ligne est paire, auquel cas on remplit de droite à  gauche
                        // 3. elle est impaire et on remplit de gauche à  droite (cas de longues guirlandes où l'on
                        // a recouvert toute la ligne supplémentaire et qu'on continue d'étendre sur la
                        // suivante

                        if (!completerGuirlande) {
                            completerGuirlande = true;
                            ++i;
                            if (i < nbLigne) {
                                j = sapinTriangle[i].length - 1;
                            }
                        } else if (i % 2 == 0) {
                            --j;
                            if (j < 0) {
                                ++i;
                                j = 0;
                            }
                        } else {
                            ++j;
                            if (j >= sapinTriangle[i].length) {
                                ++i;
                                if (i < nbLigne) {
                                    j = sapinTriangle[i].length - 1;
                                }
                            }
                        }
                    }
                } else {
                    // else de la condition
                    // (j >= sapinTriangle[i].length - 1 && pointeurChar != 0) || completerGuirlande)
                    //Dans ce cas on fait l'incrémentation normale de j, et éventuellement on ajoute
                    //le nombre aléatoire pour laisser un peu d'espace
                    if (pointeurChar == 0) {
                        j += random();
                    }
                    ++j;
                }
                //finalement on fait la vérification finale, qui incrémente la ligne et remet j à zéro si nécessaire
                if (i < nbLigne && j >= sapinTriangle[i].length && pointeurChar == 0) {
                    ++i;
                    j = 0;
                }
            } else {
                //si la ligne n'était pas paire, on incrémente la ligne
                j = 0;
                ++i;
            }
        }
    }

    /* Méthode affichant le tableau sous forme triangulaire.
     */
    static void afficherTriangle(char[][] triangle) {
        int nbLigne = triangle.length;
        int nbColonne;
        int nbEspace;

        for (int i = 0; i < nbLigne; ++i) {

            nbEspace = ((2*nbLigne - 1) - (2*i + 1))/2;
            for (int j = 0; j < nbEspace; ++j) {
                System.out.print(" ");
            }

            nbColonne = triangle[i].length;
            for (int j = 0; j < nbColonne; ++j) {
                System.out.print(triangle[i][j]);
            }

            System.out.println();
        }
    }

    /* Méthode ajoutant un tronc à l'arbre, elle choisit la hauteur et
     * la largeur en fonction du nombre de ligne du sapin.
     */
    static void afficherTronc(int nbLigne) {
        int largeur;
        int hauteur;

        largeur = (nbLigne*2 - 1) / 5;
        if (largeur % 2 == 0)
            ++largeur;
        hauteur = Math.max(1, nbLigne/3);

        for (int i = 0; i < hauteur; ++i) {
            for (int j = 0; j < ((nbLigne*2 -1) - 3)/2; ++j)
                System.out.print(" ");
            for (int j = 0; j < largeur; ++j)
                System.out.print("|");
            System.out.println();
        }
    }

    /*
     * Méthode choisissant aléatoirement entre 2 et 3
     */

    static int random() {
        int val = (int)(Math.random()*2); // 0 or 1
        return (val+2); //return 2 or 3
    }

    /*
     * Programme principal
     * on demande  les données nécessaires à l'utilisateur et vérifie que
     * les informations saisies soient correctes.
     * On appelle ensuite les méthodes dans le bon ordre pour construire puis
     * afficher le sapin
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] triangle;
        char symbole;
        int nbLigne;
        String guirlande;


        System.out.print("Quel symbole voulez-vous pour les épines du sapin? ");
        symbole = sc.nextLine().charAt(0);
        do {
            System.out.print("Combien de ligne (de 8 a 35)? ");
            nbLigne = sc.nextInt();
        } while (nbLigne < 8 || nbLigne > 35);
        do {
            System.out.print("Quelles guirlandes voulez-vous mettre " +
                    "(taille de 3 a 25 caractères et elles ne peuvent \n" +
                    "pas contenir le même caractère que celui utilisé pour les épines)? ");
            guirlande = sc.next();
        } while (guirlande.length() < 3 || guirlande.length() > 25 || guirlande.contains(Character.toString(symbole)));

        System.out.println();

        //on initialise seulement le nombre de lignes que contiendra ce tableau (ligne), on pourra ensuite
        //choisir à chaque ligne le nombre d'éléments (colonne)
        triangle = new char [nbLigne][];

//        for(int i=0;i< 9 ;i++){
//            System.out.println(triangle[i][0]);
//        }
        remplirTriangle(triangle, symbole);
        System.exit(0);
        mettreGuirlande(triangle, guirlande);
        afficherTriangle(triangle);
        afficherTronc(triangle.length);

        /* Note pour la partie 1: il suffit d'invoquer les méthodes suivantes
         * (après la saisie des données utiles) :
         *
         * remplirTriangle(triangle, symbole);
         * afficherTriangle(triangle);
         */
    }

}