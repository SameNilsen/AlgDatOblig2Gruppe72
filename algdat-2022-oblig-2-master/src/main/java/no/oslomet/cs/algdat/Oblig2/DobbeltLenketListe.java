package no.oslomet.cs.algdat.Oblig2;



////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
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
        
        Node<T> current = this.hode;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != null){
                if (this.hode == null){
                    this.hode = new Node<T>(a[i], null, null);
                    current = this.hode;
                    antall += 1;
                }
                else{
                    Node<T> newNode = new Node<T>(a[i], current, null);
                    current.neste = newNode;
                    this.hale = newNode;
                    antall += 1;
                    current = newNode;
                }
            }
        }
    }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(this.antall, fra, til);
        DobbeltLenketListe<T> nyListe = new DobbeltLenketListe<>();
        for (int i = fra; i < til; i++) {
            nyListe.leggInn(this.hent(i));
        }
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
            this.leggInn(verdi);
        }
        else if (indeks == 0){
            Node<T> nyNode = new Node<T>(verdi, null, this.hode);
            this.hode.forrige = nyNode;
            this.hode = nyNode;
            antall += 1;
            endringer += 1;
        }
        else{
            Node<T> vNode = this.finnNode(indeks-1);
            Node<T> hNode = vNode.neste;
            Node<T> nyNode = new Node<T>(verdi, vNode, hNode);
            vNode.neste = nyNode;
            hNode.forrige = nyNode;
            antall += 1;
            endringer += 1;
        }
    }

    private Node<T> finnNode(int indeks) {
        if (indeks < this.antall/2){
            Node<T> current = this.hode;
            for (int i = 0; i < indeks; i++) {
                current = current.neste;
            }
            return current;
        }
        else{
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

        Node<T> current = this.hode;
        for (int i = 0; i < this.antall; i++) {
            if (current.verdi.equals(verdi)){
                if (i == 0){
                    if (this.antall == 1){
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
        indeksKontroll(indeks, false);
        Node<T> node = finnNode(indeks);
        if (this.antall == 1){
            T verdi = this.hode.verdi;
            this.hode = null;
            this.hale = null;
            antall = 0;
            endringer += 1;
            return verdi;
        }
        Node<T> current = this.hode;
        for (int i = 0; i < indeks; i++) {
            current = current.neste;
        }
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
        //         METODE 1
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

        //         METODE 2
        int ite = this.antall;
        for (int i = 0; i < ite; i++) {
            fjern(0);
        }
    }

    @Override
    public String toString() {
        if (this.antall != 0){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            Node<T> current = this.hode;
            stringBuilder.append(current.verdi);
            for (int i = 0; i < this.antall()-1; i++) {
                current = current.neste;
                stringBuilder.append(", " +current.verdi);
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
            for (int i = 0; i < this.antall()-1; i++) {
                current = current.forrige;
                stringBuilder.append(", " + current.verdi);
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

} // class DobbeltLenketListe


