package no.oslomet.cs.algdat.Oblig2;


import java.util.ArrayList;

////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        this.hode = null;
        this.hale = null;
        this.antall = 0;
        this.endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        // Objects.requireNonNull(a);
        List<Node<T>> tempList = new ArrayList<Node<T>>();
        // this.hode = new Node<T>(a[0], null, null);
        // tempList.add(this.hode);
        // this.antall += 1;
        for (int i = 0; i < a.length; i++) {
            System.out.println(i);
            for (Node<T> node : tempList) {
                System.out.println(node.verdi);
            }
            if (a[i] != null){
                if (tempList.size() == 0){
                    this.hode = new Node<T>(a[i], null, null);
                    tempList.add(this.hode);
                    this.antall += 1;
                }
                else{
                    Node<T> node = new Node<T>(a[i], tempList.get(tempList.size()-1), null);
                    tempList.add(node);
                    this.antall += 1;
                }
            }
        }
        if (this.antall>0)
        this.hale = tempList.get(tempList.size()-1);

        for (int i = 0; i < this.antall-1; i++) {
            tempList.get(i).neste = tempList.get(i+1);
        }
    }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(this.antall, fra, til);
        DobbeltLenketListe<T> nyListe = new DobbeltLenketListe<>();
        for (int i = fra; i < til; i++) {
            nyListe.leggInn(this.hent(i));
        }
        System.out.println(nyListe.toString());
        return nyListe;
    }

    private static void fratilKontroll(int antall, int fra, int til){              //  Kode fra kompendiet
        if (fra < 0)                                  // fra er negativ
        throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
        throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
        throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall() {
        return this.antall;
    }

    @Override
    public boolean tom() {
        if (this.antall == 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        if (this.antall != 0){
            Node<T> node = new Node<T>(verdi, this.hale, null);
            this.hale.neste = node;
            this.hale = node;
            antall += 1;
            endringer += 1;
        }
        else{
            Node<T> node = new Node<T>(verdi, null, null);
            this.hale = node;
            this.hode = node;
            antall += 1;
            endringer += 1;
        }
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        indeksKontroll(indeks, true);
        Objects.requireNonNull(verdi);
        if (this.antall == 0 || indeks == this.antall){
            System.out.println(1);
            this.leggInn(verdi);
        }
        else if (indeks == 0){
            System.out.println(2);
            Node<T> nyNode = new Node<T>(verdi, null, this.hode);
            this.hode.forrige = nyNode;
            this.hode = nyNode;
            antall += 1;
            endringer += 1;
        }
        else{
            System.out.println(3);
            Node<T> vNode = this.finnNode(indeks-1);
            System.out.println(vNode.verdi);
            Node<T> hNode = vNode.neste;
            System.out.println(hNode.verdi);
            Node<T> nyNode = new Node<T>(verdi, vNode, hNode);
            vNode.neste = nyNode;
            hNode.forrige = nyNode;
            antall += 1;
            endringer += 1;
        }
    }

    private Node<T> finnNode(int indeks) {
        if (indeks < this.antall/2){
            // System.out.println(111);
            Node<T> current = this.hode;
            for (int i = 0; i < indeks; i++) {
                current = current.neste;
            }
            return current;
        }
        else{
            // System.out.println(222);
            Node<T> current = this.hale;
            for (int i = this.antall-1; i > indeks; i--) {
                current = current.forrige;
            }
            return current;
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        if (this.indeksTil(verdi) != -1){
            return true;
        }
        return false;
    }

    @Override
    public T hent(int indeks) {
        this.indeksKontroll(indeks, false);
        return this.finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        Node<T> current = this.hode;
        for (int i = 0; i < this.antall; i++) {
            if (current.verdi.equals(verdi)){
                return i;
            }
            current = current.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        this.indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi);
        Node<T> node = finnNode(indeks);
        
        T gammelverdi = node.verdi;
        node.verdi = nyverdi;
        endringer += 1;
        return gammelverdi;
        
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null){
            return false;
        }
        // if (this.antall == 1){
        //     // System.out.println("eeee");
        //     this.hode = null;
        //     this.hale = null;
        //     antall = 0;
        //     endringer += 1;
        //     return true;
        // }

        /*
        // System.out.println(this.toString() + " " + verdi);
        Node<T> current = this.hode;
        int indeks = 0;
        // this.endringer = 0;
        for (int i = 0; i < this.antall-1; i++) {
            // this.endringer += 1;
            indeks += 1;
            // System.out.println(i + " " + current.verdi);
            if (current.verdi.equals(verdi)){
                indeks = i;
                break;
            }
            current = current.neste;
            // System.out.println(current.verdi);
        }
        // System.out.println("1:Endringer = " + this.endringer);
        if (current == null){
            return false;
        }
        if (!current.verdi.equals(verdi)){
            return false;
        }
        // System.out.println("i = " + " " + indeks);
        if (indeks == 0){
            if (this.antall == 1){
                // System.out.println("eeee");
                this.hode = null;
                this.hale = null;
                antall = 0;
                endringer += 1;
                return true;
            }
            current.neste.forrige = null;
            this.hode = current.neste;
        }
        else if (indeks == this.antall-1){
            current.forrige.neste = null;
            this.hale = current.forrige;
        }
        else{
            current.forrige.neste = current.neste;
            current.neste.forrige = current.forrige;
        }
        antall -= 1;
        endringer += 1;
        return true;
        */

        Node<T> current = this.hode;
        for (int i = 0; i < this.antall; i++) {
            if (current.verdi.equals(verdi)){
                if (i == 0){
                    if (this.antall == 1){
                        // System.out.println("eeee");
                        this.hode = null;
                        this.hale = null;
                        antall = 0;
                        endringer += 1;
                        return true;
                    }
                    current.neste.forrige = null;
                    this.hode = current.neste;
                }
                else if (i == this.antall-1){
                    current.forrige.neste = null;
                    this.hale = current.forrige;
                }
                else{
                    current.forrige.neste = current.neste;
                    current.neste.forrige = current.forrige;
                }
                antall -= 1;
                endringer += 1;
                return true;
            }
            current = current.neste;
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        // Node<T> current = this.hode;
        // for (int i = 0; i < indeks; i++) {
        //     current = current.neste;
        // }
        indeksKontroll(indeks, false);
        if (this.antall == 1){
            // System.out.println("eeee");
            T verdi = this.hode.verdi;
            this.hode = null;
            this.hale = null;
            antall = 0;
            endringer += 1;
            return verdi;
        }
        Node<T> current = this.hode;
        // this.endringer = 0;
        for (int i = 0; i < indeks; i++) {
            // this.endringer += 1;
            current = current.neste;
        }
        // System.out.println("2:Endringer = " + this.endringer);
        Node<T> node = finnNode(indeks);
        // Node<T> node = current;
        if (indeks == 0){
            node.neste.forrige = null;
            this.hode = node.neste;
        }
        else if (indeks == this.antall-1){
            node.forrige.neste = null;
            this.hale = node.forrige;
        }
        else{
            node.forrige.neste = node.neste;
            node.neste.forrige = node.forrige;
        }
        T verdi = node.verdi;
        antall -= 1;
        endringer += 1;
        return verdi;
    }

    @Override
    public void nullstill() {
        // System.out.println("Metode 1:");
        // Node<T> current = this.hode;
        // for (int i = 0; i < this.antall; i++) {
        //     System.out.println(current.verdi);
        //     Node<T> nesteNode = current.neste;
        //     current.verdi = null;
        //     current.forrige = null;
        //     current.neste = null;
        //     current = nesteNode;
        // }
        // this.hode = null;
        // this.hale = null;
        // antall = 0;
        // endringer += 1;
        System.out.println("Metode 2:");
        int ite = this.antall;
        for (int i = 0; i < ite; i++) {
            fjern(0);
            System.out.println(i + " " + this.toString());
        }
    }

    @Override
    public String toString() {
        if (this.antall != 0){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            Node<T> current = this.hode;
            stringBuilder.append(current.verdi);
            // System.out.println(stringBuilder);
            for (int i = 0; i < this.antall()-1; i++) {
                // System.out.println(i);
                current = current.neste;
                stringBuilder.append(", " +current.verdi);
                // System.out.println(stringBuilder);
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        else
        return "[]";
    }

    public String omvendtString() {
        if (this.antall != 0){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            Node<T> current = this.hale;
            stringBuilder.append(current.verdi);
            // System.out.println(stringBuilder);
            for (int i = 0; i < this.antall()-1; i++) {
                // System.out.println("--" + i);
                current = current.forrige;
                stringBuilder.append(", " + current.verdi);
                // System.out.println(stringBuilder);
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        else
        return "[]";
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            // for (int i = 0; i < indeks; i++) {
            //     denne = denne.neste;
            // }
            fjernOK = false;  
            iteratorendringer = endringer; 
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if (iteratorendringer == endringer){
                if (hasNext() ){
                    fjernOK = true;
                    T verdi = denne.verdi;
                    denne = denne.neste;
                    return verdi;
                }
                else{
                    throw new NoSuchElementException();
                }
            }
            else{
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        System.out.println(2334);
        Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());
        String[] s = {"asda", null, "fff", "tre5", "yt", null};
        DobbeltLenketListe<String> liste2 = new DobbeltLenketListe<>(s);
        System.out.println(liste2.antall() + " " + liste2.tom());
        System.out.println(liste2.omvendtString());
        System.out.println(liste2.toString());
        System.out.println(liste.toString());
        liste.leggInn("Hur");
        System.out.println(liste.toString());

        DobbeltLenketListe<Integer> liste3 = new DobbeltLenketListe<>(); 
        System.out.println(liste3.toString() + " " + liste3.omvendtString()); 
        for (int i = 1; i <= 3; i++) { 
            liste3.leggInn(i); 
            System.out.println(liste3.toString() + " " + liste3.omvendtString()); 
        } 
        liste2.leggInn("kiruna");
        System.out.println(liste2.toString());
        System.out.println(liste2.hent(4));
        System.out.println(liste2.oppdater(4, "ghyr"));
        System.out.println(liste2.toString());
        System.out.println(liste2.subliste(1, 5));
        // Character[] c = {'A','B','C','D','E','F','G','H','I','J',}; 
        // DobbeltLenketListe<Character> liste4 = new DobbeltLenketListe<>(c); 
        // System.out.println(liste4.subliste(3,8));  // [D, E, F, G, H] 
        // System.out.println(liste4.subliste(5,5));  // [] 
        // System.out.println(liste4.subliste(8,liste4.antall()));  // [I, J] 
        // // System.out.println(liste4.subliste(0,11));  // skal kaste unntak 
        System.out.println(liste2.indeksTil("asda"));
        System.out.println(liste2.inneholder("kiruna"));
        System.out.println(liste2.toString());
        liste2.leggInn(4, "ryut");
        System.out.println(liste2.toString());
        System.out.println(liste2.fjern(4));
        System.out.println(liste2.toString());

        String[] navn = {"Lars","Dongbert","Bodil","Kari","Per","Berit"}; 
        Liste<String> liste5 = new DobbeltLenketListe<>(navn); 
        
        liste5.forEach(ss -> System.out.print(ss + " ")); 
        System.out.println(); 
        for (String str : liste5) System.out.print(str + " "); 
        System.out.println();
        System.out.println("-------");
        DobbeltLenketListe<String> a = new DobbeltLenketListe<>();
        System.out.println(a.toString()); 
        a.leggInn("C");
        a.leggInn("E");
        a.leggInn("H");
        System.out.println(a.toString());
        System.out.println(a.omvendtString());
        a.fjern("H");
        a.fjern("C");
        System.out.println(a.toString());
        a.fjern("E");
        System.out.println(a.toString());
        // System.out.println(liste2.toString());
        // liste2.nullstill();
        System.out.println(liste2 + "----------");
        liste2.fjern(" ");
        System.out.println(liste2.toString());
    }

} // class DobbeltLenketListe


